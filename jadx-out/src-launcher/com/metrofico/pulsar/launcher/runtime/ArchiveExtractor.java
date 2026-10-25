package com.metrofico.pulsar.launcher.runtime;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.EnumSet;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ArchiveExtractor {
   private static final int BLOCK = 512;

   public static ArchiveExtractor.Format formatOf(String fileName) {
      String n = fileName.toLowerCase();
      if (n.endsWith(".tar.gz") || n.endsWith(".tgz")) {
         return ArchiveExtractor.Format.TAR_GZ;
      } else if (n.endsWith(".zip")) {
         return ArchiveExtractor.Format.ZIP;
      } else {
         throw new IllegalArgumentException("unsupported archive: " + fileName);
      }
   }

   public static void extract(Path archive, Path destination, ArchiveExtractor.Format format) throws IOException {
      Files.createDirectories(destination);

      try (InputStream in = Files.newInputStream(archive)) {
         switch (format) {
            case TAR_GZ:
               extractTar(new GZIPInputStream(in, 65536), destination);
               break;
            case ZIP:
               extractZip(new ZipInputStream(in), destination);
         }
      }
   }

   static void extractZip(ZipInputStream zip, Path destination) throws IOException {
      ZipEntry entry;
      while ((entry = zip.getNextEntry()) != null) {
         Path target = resolveInside(destination, entry.getName());
         if (entry.isDirectory()) {
            Files.createDirectories(target);
         } else {
            Files.createDirectories(target.getParent());
            Files.copy(zip, target, StandardCopyOption.REPLACE_EXISTING);
         }

         zip.closeEntry();
      }
   }

   static void extractTar(InputStream in, Path destination) throws IOException {
      byte[] header = new byte[512];
      String pendingLongName = null;
      String pendingLongLink = null;

      while (readFully(in, header)) {
         if (!isZeroBlock(header)) {
            String name = field(header, 0, 100);
            int mode = (int)octal(header, 100, 8);
            long size = octal(header, 124, 12);
            char type = (char)header[156];
            String link = field(header, 157, 100);
            String magic = field(header, 257, 6);
            if (magic.startsWith("ustar")) {
               String prefix = field(header, 345, 155);
               if (!prefix.isEmpty()) {
                  name = prefix + "/" + name;
               }
            }

            if (pendingLongName != null) {
               name = pendingLongName;
               pendingLongName = null;
            }

            if (pendingLongLink != null) {
               link = pendingLongLink;
               pendingLongLink = null;
            }

            switch (type) {
               case '\u0000':
               case '0':
               case '7': {
                  Path target = resolveInside(destination, name);
                  Files.createDirectories(target.getParent());
                  copy(in, target, size);
                  applyMode(target, mode);
                  break;
               }
               case '2': {
                  Path target = resolveInside(destination, name);
                  Files.createDirectories(target.getParent());
                  Path linkTarget = Path.of(link);
                  Path linkParent = destination.relativize(target.getParent());
                  resolveInside(destination, linkParent.resolve(link).toString());
                  Files.deleteIfExists(target);

                  try {
                     Files.createSymbolicLink(target, linkTarget);
                  } catch (IOException | UnsupportedOperationException var16) {
                  }

                  skip(in, size);
                  break;
               }
               case '5':
                  Files.createDirectories(resolveInside(destination, name));
                  skip(in, size);
                  break;
               case 'K':
                  pendingLongLink = readString(in, size);
                  break;
               case 'L':
                  pendingLongName = readString(in, size);
                  break;
               default:
                  skip(in, size);
            }

            skip(in, padding(size));
         }
      }
   }

   private static void copy(InputStream in, Path target, long size) throws IOException {
      try (OutputStream out = Files.newOutputStream(target)) {
         byte[] buffer = new byte[65536];
         long remaining = size;

         while (remaining > 0L) {
            int read = in.read(buffer, 0, (int)Math.min((long)buffer.length, remaining));
            if (read < 0) {
               throw new IOException("truncated tar entry " + target.getFileName());
            }

            out.write(buffer, 0, read);
            remaining -= read;
         }
      }
   }

   private static String readString(InputStream in, long size) throws IOException {
      byte[] data = in.readNBytes((int)size);
      int end = 0;

      while (end < data.length && data[end] != 0) {
         end++;
      }

      return new String(data, 0, end, StandardCharsets.UTF_8);
   }

   private static void applyMode(Path target, int mode) {
      if ((mode & 73) != 0) {
         try {
            Set<PosixFilePermission> perms = EnumSet.copyOf(Files.getPosixFilePermissions(target));
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            if ((mode & 8) != 0) {
               perms.add(PosixFilePermission.GROUP_EXECUTE);
            }

            if ((mode & 1) != 0) {
               perms.add(PosixFilePermission.OTHERS_EXECUTE);
            }

            Files.setPosixFilePermissions(target, perms);
         } catch (IOException | UnsupportedOperationException var3) {
         }
      }
   }

   static Path resolveInside(Path root, String name) throws IOException {
      String clean = name.replace('\\', '/');

      while (clean.startsWith("/")) {
         clean = clean.substring(1);
      }

      Path resolved = root.resolve(clean).normalize();
      if (!resolved.startsWith(root.normalize())) {
         throw new IOException("archive entry escapes destination: " + name);
      } else {
         return resolved;
      }
   }

   private static boolean readFully(InputStream in, byte[] buffer) throws IOException {
      int n = in.readNBytes(buffer, 0, buffer.length);
      if (n == 0) {
         return false;
      } else if (n < buffer.length) {
         throw new IOException("truncated tar header");
      } else {
         return true;
      }
   }

   private static boolean isZeroBlock(byte[] b) {
      for (byte x : b) {
         if (x != 0) {
            return false;
         }
      }

      return true;
   }

   private static String field(byte[] b, int offset, int length) {
      int end = offset;

      while (end < offset + length && b[end] != 0) {
         end++;
      }

      return new String(b, offset, end - offset, StandardCharsets.UTF_8);
   }

   private static long octal(byte[] b, int offset, int length) {
      long value = 0L;

      for (int i = offset; i < offset + length; i++) {
         byte c = b[i];
         if (c != 0 && c != 32) {
            if (c < 48 || c > 55) {
               break;
            }

            value = value * 8L + (c - 48);
         } else if (value > 0L || c == 0) {
            break;
         }
      }

      return value;
   }

   private static long padding(long size) {
      long rem = size % 512L;
      return rem == 0L ? 0L : 512L - rem;
   }

   private static void skip(InputStream in, long n) throws IOException {
      long remaining = n;

      while (remaining > 0L) {
         long skipped = in.skip(remaining);
         if (skipped <= 0L) {
            if (in.read() < 0) {
               throw new IOException("truncated tar");
            }

            skipped = 1L;
         }

         remaining -= skipped;
      }
   }

   private ArchiveExtractor() {
   }

   public static enum Format {
      TAR_GZ,
      ZIP;
   }
}
