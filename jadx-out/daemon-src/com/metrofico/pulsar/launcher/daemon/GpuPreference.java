package com.metrofico.pulsar.launcher.daemon;

import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public final class GpuPreference {
   static final int NVIDIA = 4318;
   static final int AMD = 4098;
   static final int INTEL = 32902;
   static final long DISCRETE_VRAM = 2147483648L;
   static final String WINDOWS_KEY = "HKCU\\Software\\Microsoft\\DirectX\\UserGpuPreferences";
   static final String HIGH_PERFORMANCE = "GpuPreference=2;";
   private static final Pattern PCI = Pattern.compile("[0-9a-fA-F]{4}:[0-9a-fA-F]{2}:[0-9a-fA-F]{2}\\.[0-7]");
   private static final Pattern CARD = Pattern.compile("card[0-9]+");
   private static final List<String> USER_PRIME_VARS = List.of("DRI_PRIME", "__NV_PRIME_RENDER_OFFLOAD", "__GLX_VENDOR_LIBRARY_NAME");

   private GpuPreference() {
   }

   public static GpuPreference.Applied apply(List<String> commandLine) {
      return apply(commandLine, System.getProperty("os.name", ""), System.getenv(), Path.of("/sys/class/drm"), GpuPreference::reg);
   }

   static GpuPreference.Applied apply(List<String> commandLine, String osName, Map<String, String> env, Path drm, GpuPreference.Registry registry) {
      if ("default".equalsIgnoreCase(env.getOrDefault("PULSAR_GPU", ""))) {
         return new GpuPreference.Applied(Map.of(), "default (PULSAR_GPU)");
      } else {
         String os = osName.toLowerCase(Locale.ROOT);
         if (os.contains("win")) {
            return new GpuPreference.Applied(Map.of(), windows(commandLine, registry));
         } else if (!os.contains("linux")) {
            return new GpuPreference.Applied(Map.of(), "system (macOS switches by itself)");
         } else {
            for (String var : USER_PRIME_VARS) {
               if (env.containsKey(var)) {
                  return new GpuPreference.Applied(Map.of(), "user's own " + var);
               }
            }

            List<GpuPreference.Gpu> gpus = scan(drm);
            GpuPreference.Gpu pick = pickDiscrete(gpus);
            if (pick == null) {
               return new GpuPreference.Applied(Map.of(), "default (" + gpus.size() + " GPU, no better discrete one)");
            } else {
               Map<String, String> vars = linuxEnv(pick);
               return new GpuPreference.Applied(
                  vars, "discrete " + pick.address() + " " + vendorName(pick.vendor()) + " (" + pick.driver() + "): " + vars.keySet()
               );
            }
         }
      }
   }

   static List<GpuPreference.Gpu> scan(Path drm) {
      List<GpuPreference.Gpu> out = new ArrayList<>();
      if (!Files.isDirectory(drm)) {
         return out;
      } else {
         try {
            try (Stream<Path> cards = Files.list(drm)) {
               for (Path card : cards.sorted().toList()) {
                  if (CARD.matcher(card.getFileName().toString()).matches()) {
                     Path device = card.resolve("device");
                     String address = device.toRealPath().getFileName().toString();
                     if (PCI.matcher(address).matches() && !out.stream().anyMatch(g -> g.address().equals(address))) {
                        int vendor = Integer.decode(read(device.resolve("vendor"), "0"));
                        boolean boot = "1".equals(read(device.resolve("boot_vga"), "0"));
                        Path driver = device.resolve("driver");
                        String driverName = Files.exists(driver) ? driver.toRealPath().getFileName().toString() : "?";
                        long vram = Long.parseLong(read(device.resolve("mem_info_vram_total"), "0"));
                        out.add(new GpuPreference.Gpu(address, vendor, driverName, boot, vram));
                     }
                  }
               }
            }

            return out;
         } catch (RuntimeException | IOException var15) {
            LauncherLog.warn("gpu scan failed: " + var15);
            return List.of();
         }
      }
   }

   static GpuPreference.Gpu pickDiscrete(List<GpuPreference.Gpu> gpus) {
      GpuPreference.Gpu boot = gpus.stream().filter(GpuPreference.Gpu::boot).findFirst().orElse(null);
      if (boot != null && gpus.size() >= 2) {
         GpuPreference.Gpu best = null;

         for (GpuPreference.Gpu g : gpus) {
            if (!g.boot() && betterThan(g, boot) && (best == null || g.vendor() == 4318 && best.vendor() != 4318 || g.vram() > best.vram())) {
               best = g;
            }
         }

         return best;
      } else {
         return null;
      }
   }

   private static boolean betterThan(GpuPreference.Gpu g, GpuPreference.Gpu boot) {
      return switch (g.vendor()) {
         case 4098 -> boot.vendor() == 32902 || boot.vendor() == 4098 && g.vram() >= 2147483648L && g.vram() > boot.vram();
         case 4318 -> boot.vendor() != 4318;
         default -> false;
      };
   }

   static Map<String, String> linuxEnv(GpuPreference.Gpu g) {
      Map<String, String> vars = new HashMap<>();
      if (g.vendor() == 4318 && "nvidia".equals(g.driver())) {
         vars.put("__NV_PRIME_RENDER_OFFLOAD", "1");
         vars.put("__GLX_VENDOR_LIBRARY_NAME", "nvidia");
         vars.put("__VK_LAYER_NV_optimus", "NVIDIA_only");
      } else {
         vars.put("DRI_PRIME", "pci-" + g.address().replace(':', '_').replace('.', '_'));
      }

      return vars;
   }

   static String windows(List<String> commandLine, GpuPreference.Registry registry) {
      String java = commandLine.stream()
         .filter(a -> a.toLowerCase(Locale.ROOT).endsWith("java.exe") || a.toLowerCase(Locale.ROOT).endsWith("javaw.exe"))
         .findFirst()
         .orElse(null);
      if (java == null) {
         return "windows: no java.exe in the command line";
      } else {
         try {
            if (registry.run(List.of("query", "HKCU\\Software\\Microsoft\\DirectX\\UserGpuPreferences", "/v", java)) == 0) {
               return "windows: user's own preference for " + java;
            } else {
               int code = registry.run(
                  List.of("add", "HKCU\\Software\\Microsoft\\DirectX\\UserGpuPreferences", "/v", java, "/t", "REG_SZ", "/d", "GpuPreference=2;", "/f")
               );
               return code == 0 ? "windows: high performance for " + java : "windows: reg add failed (" + code + ")";
            }
         } catch (IOException var4) {
            return "windows: reg.exe failed (" + var4.getMessage() + ")";
         } catch (InterruptedException var5) {
            Thread.currentThread().interrupt();
            return "windows: interrupted";
         }
      }
   }

   private static int reg(List<String> args) throws IOException, InterruptedException {
      List<String> command = new ArrayList<>(args.size() + 1);
      command.add("reg");
      command.addAll(args);
      Process p = new ProcessBuilder(command).redirectErrorStream(true).redirectOutput(Redirect.DISCARD).start();
      if (!p.waitFor(5L, TimeUnit.SECONDS)) {
         p.destroyForcibly();
         return -1;
      } else {
         return p.exitValue();
      }
   }

   private static String read(Path file, String fallback) {
      try {
         return Files.isReadable(file) ? Files.readString(file).trim() : fallback;
      } catch (IOException var3) {
         return fallback;
      }
   }

   private static String vendorName(int vendor) {
      return switch (vendor) {
         case 4098 -> "AMD";
         case 4318 -> "NVIDIA";
         case 32902 -> "Intel";
         default -> String.format("0x%04x", vendor);
      };
   }

   public record Applied(Map<String, String> environment, String mode) {
   }

   record Gpu(String address, int vendor, String driver, boolean boot, long vram) {
   }

   interface Registry {
      int run(List<String> var1) throws IOException, InterruptedException;
   }
}
