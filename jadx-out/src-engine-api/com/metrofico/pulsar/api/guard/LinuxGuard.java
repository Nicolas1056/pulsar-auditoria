package com.metrofico.pulsar.api.guard;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class LinuxGuard implements Guard {
   private static final List<String> INITIAL_UID_MAP = List.of("0", "0", "4294967295");
   private final Map<String, GuardReport.Module> measured = new ConcurrentHashMap<>();
   private final Map<String, Boolean> rootOnly = new ConcurrentHashMap<>();
   private final boolean initialUserNamespace = initialUserNamespace();

   @Override
   public boolean supported() {
      return Guard.osName().equals("linux");
   }

   @Override
   public GuardReport scan() {
      boolean debugger = tracerPid() > 0;

      List<GuardReport.Module> modules;
      try {
         modules = this.unknownModules();
      } catch (RuntimeException | IOException var4) {
         modules = List.of();
      }

      return new GuardReport(debugger, modules, 0, 0);
   }

   static int tracerPid() {
      try {
         for (String line : Files.readAllLines(Path.of("/proc/self/status"))) {
            if (line.startsWith("TracerPid:")) {
               return Integer.parseInt(line.substring("TracerPid:".length()).trim());
            }
         }
      } catch (RuntimeException | IOException var2) {
      }

      return 0;
   }

   List<GuardReport.Module> unknownModules() throws IOException {
      Set<String> seen = new HashSet<>();
      List<GuardReport.Module> out = new ArrayList<>();

      for (String line : Files.readAllLines(Path.of("/proc/self/maps"))) {
         String[] f = line.trim().split("\\s+", 6);
         if (f.length >= 6 && f[1].length() >= 3 && f[1].charAt(2) == 'x' && f[5].startsWith("/")) {
            String path = f[5];
            boolean deleted = path.endsWith(" (deleted)");
            if (deleted) {
               path = path.substring(0, path.length() - " (deleted)".length());
            }

            if (path.endsWith(".so") || path.contains(".so.")) {
               String key = path + "@" + f[4];
               if (seen.add(key)) {
                  if (deleted) {
                     out.add(new GuardReport.Module(fileName(path), "", false));
                  } else {
                     String file = path;
                     long inode = parseInode(f[4]);
                     if (!this.rootOnly.computeIfAbsent(key, k -> this.initialUserNamespace && rootOnly(Path.of(file), inode))) {
                        out.add(this.measured.computeIfAbsent(key, k -> new GuardReport.Module(fileName(file), sha256(Path.of(file)), false)));
                     }
                  }
               }
            }
         }
      }

      return List.copyOf(out);
   }

   static boolean rootOnly(Path file, long inode) {
      try {
         if (Files.getAttribute(file, "unix:ino", LinkOption.NOFOLLOW_LINKS) instanceof Long ino && ino == inode) {
            for (Path p = file; p != null; p = p.getParent()) {
               Map<String, Object> a = Files.readAttributes(p, "unix:uid,mode", LinkOption.NOFOLLOW_LINKS);
               if (!(a.get("uid") instanceof Integer uid)
                  || uid != 0
                  || !(a.get("mode") instanceof Integer mode)
                  || (mode & 18) != 0
                  || Files.isSymbolicLink(p)) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      } catch (RuntimeException | IOException var9) {
         return false;
      }
   }

   static boolean initialUserNamespace() {
      try {
         List<String> lines = Files.readAllLines(Path.of("/proc/self/uid_map"));
         return lines.size() == 1 && List.of(lines.get(0).trim().split("\\s+")).equals(INITIAL_UID_MAP);
      } catch (RuntimeException | IOException var1) {
         return false;
      }
   }

   private static long parseInode(String field) {
      try {
         return Long.parseLong(field);
      } catch (NumberFormatException var2) {
         return -1L;
      }
   }

   private static String fileName(String path) {
      int slash = path.lastIndexOf(47);
      return (slash >= 0 ? path.substring(slash + 1) : path).toLowerCase(Locale.ROOT);
   }

   private static String sha256(Path file) {
      try {
         String var8;
         try (InputStream in = Files.newInputStream(file)) {
            MessageDigest sha = MessageDigest.getInstance("SHA-256");
            byte[] buf = new byte[65536];

            while ((n = in.read(buf)) > 0) {
               sha.update(buf, 0, n);
            }

            var8 = HexFormat.of().formatHex(sha.digest());
         }

         return var8;
      } catch (NoSuchAlgorithmException | RuntimeException | IOException var7) {
         return "";
      }
   }
}
