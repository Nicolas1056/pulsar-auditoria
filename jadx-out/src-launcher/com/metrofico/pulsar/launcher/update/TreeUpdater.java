package com.metrofico.pulsar.launcher.update;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.zip.GZIPInputStream;

public final class TreeUpdater {
   private TreeUpdater() {
   }

   public static TreeUpdater.Plan plan(InstallTree tree, Path installDir) throws IOException {
      List<InstallTree.Entry> reuse = new ArrayList<>();
      List<InstallTree.Entry> fetch = new ArrayList<>();
      long down = 0L;
      long kept = 0L;

      for (InstallTree.Entry e : tree.files()) {
         Path local = installDir.resolve(e.path());
         if (Files.isRegularFile(local) && Files.size(local) == e.size() && hashOf(local).equals(e.sha256())) {
            reuse.add(e);
            kept += e.size();
         } else {
            fetch.add(e);
            down += e.objectSize();
         }
      }

      return new TreeUpdater.Plan(List.copyOf(reuse), List.copyOf(fetch), down, kept);
   }

   public static List<InstallTree.Entry> damaged(InstallTree tree, Path installDir) throws IOException {
      return plan(tree, installDir).fetch();
   }

   public static void assemble(InstallTree tree, TreeUpdater.Plan plan, Path installDir, Path objectsDir, Path newDir) throws IOException {
      deleteTree(newDir);
      Files.createDirectories(newDir);

      for (InstallTree.Entry e : plan.reuse()) {
         Path from = installDir.resolve(e.path());
         Path to = newDir.resolve(e.path());
         Files.createDirectories(to.getParent());

         try {
            Files.createLink(to, from);
         } catch (UnsupportedOperationException | IOException var10) {
            Files.copy(from, to, StandardCopyOption.REPLACE_EXISTING);
         }

         applyMode(to, e);
      }

      for (InstallTree.Entry e : plan.fetch()) {
         extract(objectsDir.resolve(e.object()), newDir.resolve(e.path()), e);
      }
   }

   public static void extract(Path object, Path target, InstallTree.Entry e) throws IOException {
      Files.createDirectories(target.toAbsolutePath().getParent());
      Path tmp = target.resolveSibling(target.getFileName() + ".tmp");
      MessageDigest md = sha256();
      long n = 0L;
      InputStream in = new GZIPInputStream(Files.newInputStream(object));

      try (OutputStream out = Files.newOutputStream(tmp)) {
         byte[] buf = new byte[65536];

         int r;
         while ((r = in.read(buf)) > 0) {
            out.write(buf, 0, r);
            md.update(buf, 0, r);
            n += r;
         }
      } catch (Throwable var14) {
         try {
            in.close();
         } catch (Throwable var11) {
            var14.addSuppressed(var11);
         }

         throw var14;
      }

      in.close();
      String var15 = HexFormat.of().formatHex(md.digest());
      if (n == e.size() && var15.equals(e.sha256())) {
         Files.move(tmp, target, StandardCopyOption.REPLACE_EXISTING);
         applyMode(target, e);
      } else {
         Files.deleteIfExists(tmp);
         Files.deleteIfExists(object);
         throw new IOException("objeto " + e.object() + " no produce " + e.path() + " (" + n + " B, " + var15 + ")");
      }
   }

   public static List<String> verify(InstallTree tree, Path dir) throws IOException {
      List<String> problems = new ArrayList<>();

      for (InstallTree.Entry e : tree.files()) {
         Path p = dir.resolve(e.path());
         if (!Files.isRegularFile(p)) {
            problems.add("falta " + e.path());
         } else if (Files.size(p) != e.size()) {
            problems.add("tamaño " + e.path());
         } else if (!hashOf(p).equals(e.sha256())) {
            problems.add("hash " + e.path());
         }
      }

      return problems;
   }

   private static void applyMode(Path p, InstallTree.Entry e) {
      try {
         if (e.executable()) {
            Set<PosixFilePermission> perms = Files.getPosixFilePermissions(p);
            perms.add(PosixFilePermission.OWNER_EXECUTE);
            perms.add(PosixFilePermission.GROUP_EXECUTE);
            perms.add(PosixFilePermission.OTHERS_EXECUTE);
            Files.setPosixFilePermissions(p, perms);
         }
      } catch (UnsupportedOperationException | IOException var3) {
      }
   }

   public static String hashOf(Path file) throws IOException {
      MessageDigest md = sha256();

      try (InputStream in = Files.newInputStream(file)) {
         byte[] buf = new byte[65536];

         int n;
         while ((n = in.read(buf)) > 0) {
            md.update(buf, 0, n);
         }
      }

      return HexFormat.of().formatHex(md.digest());
   }

   private static MessageDigest sha256() {
      try {
         return MessageDigest.getInstance("SHA-256");
      } catch (NoSuchAlgorithmException var1) {
         throw new IllegalStateException(var1);
      }
   }

   public static void deleteTree(Path dir) throws IOException {
      if (Files.exists(dir, LinkOption.NOFOLLOW_LINKS)) {
         List<Path> all = new ArrayList<>();

         try (Stream<Path> walk = Files.walk(dir)) {
            walk.sorted(Comparator.reverseOrder()).forEach(all::add);
         }

         for (Path p : all) {
            Files.deleteIfExists(p);
         }
      }
   }

   public record Plan(List<InstallTree.Entry> reuse, List<InstallTree.Entry> fetch, long bytesToDownload, long bytesReused) {
      public boolean upToDate() {
         return this.fetch.isEmpty();
      }
   }
}
