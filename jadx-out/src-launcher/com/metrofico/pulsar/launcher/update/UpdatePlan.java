package com.metrofico.pulsar.launcher.update;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.Optional;

public record UpdatePlan(ReleaseManifest.Package pkg, UpdatePlan.Installation installation, UpdatePlan.Apply apply) {
   public static final String MSIX = "msix";

   // $VF: One or more variable merging failures!
   // $VF: Could not properly define all variable types!
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static Optional<UpdatePlan> plan(ReleaseManifest manifest, UpdatePlan.Installation inst) {
      String var3 = inst.format();

      <unknown> var10000;
      for (String f : var10000_1) {
         Optional<ReleaseManifest.Package> p = manifest.packages()
            .stream()
            .filter(x -> x.os().equals(inst.os()) && x.arch().equals(inst.arch()) && x.format().equals(f))
            .findFirst();
         if (p.isPresent()) {
            return Optional.of(new UpdatePlan(p.get(), inst, applyFor(p.get().format())));
         }
      }

      return Optional.empty();
   }

   static UpdatePlan.Apply applyFor(String format) {
      return switch (format) {
         case "appimage" -> UpdatePlan.Apply.REPLACE_APPIMAGE;
         case "tar.gz", "zip" -> UpdatePlan.Apply.SWAP_DIRECTORY;
         case "deb" -> UpdatePlan.Apply.OPEN_PACKAGE;
         case "setup.exe" -> UpdatePlan.Apply.RUN_INSTALLER;
         default -> UpdatePlan.Apply.MOUNT_AND_COPY;
      };
   }

   public Path installDir() {
      Path t = this.installation.target();
      return t != null && Files.isRegularFile(t) ? t.getParent() : t;
   }

   public static enum Apply {
      REPLACE_APPIMAGE,
      SWAP_DIRECTORY,
      OPEN_PACKAGE,
      RUN_INSTALLER,
      MOUNT_AND_COPY;
   }

   public record Installation(String os, String arch, String format, Path target) {
      public static UpdatePlan.Installation detect(String osName, String osArch, String formatEnv, String appImagePath, Path executable) {
         String os = osName.toLowerCase(Locale.ROOT).contains("win") ? "windows" : (osName.toLowerCase(Locale.ROOT).contains("mac") ? "macos" : "linux");
         String arch = !osArch.toLowerCase(Locale.ROOT).contains("aarch64") && !osArch.toLowerCase(Locale.ROOT).contains("arm64") ? "x86_64" : "arm64";
         String format = formatEnv;
         Path target = executable;
         if (formatEnv == null || formatEnv.isBlank()) {
            if (appImagePath != null && !appImagePath.isBlank()) {
               format = "appimage";
               target = Path.of(appImagePath);
            } else if (os.equals("linux") && executable != null && executable.startsWith("/opt/pulsar-launcher")) {
               format = "deb";
            } else if (os.equals("linux")) {
               format = "tar.gz";
            } else if (os.equals("windows")) {
               format = "setup.exe";
            } else {
               format = "dmg";
            }
         }

         return new UpdatePlan.Installation(os, arch, format, target);
      }
   }
}
