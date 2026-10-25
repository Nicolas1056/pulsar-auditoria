package com.metrofico.pulsar.api.guard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public final class MacGuard implements Guard {
   @Override
   public boolean supported() {
      return Guard.osName().equals("macos");
   }

   @Override
   public GuardReport scan() {
      boolean debugger = debuggerFromSysctl();
      List<GuardReport.Module> modules = images();
      return new GuardReport(debugger, modules, 0, 0);
   }

   static boolean debuggerFromSysctl() {
      try {
         Process p = new ProcessBuilder("sysctl", "-n", "kern.proc.pid." + ProcessHandle.current().pid()).redirectErrorStream(false).start();
         if (!p.waitFor(2L, TimeUnit.SECONDS)) {
            p.destroyForcibly();
            return false;
         } else {
            String out = new String(p.getInputStream().readAllBytes()).toLowerCase(Locale.ROOT);
            return out.contains("p_traced");
         }
      } catch (InterruptedException | RuntimeException | IOException var2) {
         return false;
      }
   }

   static List<GuardReport.Module> images() {
      List<GuardReport.Module> out = new ArrayList<>();

      try {
         Process p = new ProcessBuilder("vmmap", "-interleaved", String.valueOf(ProcessHandle.current().pid())).redirectErrorStream(false).start();
         if (!p.waitFor(3L, TimeUnit.SECONDS)) {
            p.destroyForcibly();
            return out;
         }

         for (String line : new String(p.getInputStream().readAllBytes()).split("\n")) {
            int slash = line.lastIndexOf(47);
            if (slash >= 0) {
               String path = line.substring(slash).trim();
               String name = path.substring(path.lastIndexOf(47) + 1).toLowerCase(Locale.ROOT);
               if ((name.endsWith(".dylib") || name.endsWith(".so")) && !KnownModules.isKnown(name)) {
                  out.add(new GuardReport.Module(name, "", false));
               }
            }
         }
      } catch (InterruptedException | RuntimeException | IOException var9) {
      }

      return out;
   }
}
