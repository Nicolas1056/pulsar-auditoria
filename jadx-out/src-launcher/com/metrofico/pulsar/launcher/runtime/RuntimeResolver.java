package com.metrofico.pulsar.launcher.runtime;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

public final class RuntimeResolver {
   private final JavaDetector detector;
   private final RuntimeInstaller installer;

   public RuntimeResolver(JavaDetector detector, RuntimeInstaller installer) {
      this.detector = detector;
      this.installer = installer;
   }

   public RuntimeResolver.Resolution resolve(int requiredMajor, Path profileJava) {
      if (profileJava != null) {
         Optional<JavaRuntime> custom = this.detector.inspect(profileJava, JavaRuntime.Source.PROFILE);
         if (custom.isPresent()) {
            return RuntimeResolver.Resolution.of(custom.get());
         }
      }

      int required = requiredMajor <= 0 ? this.detector.bundled().map(JavaRuntime::major).orElse(0) : requiredMajor;
      Optional<JavaRuntime> managed = this.detector.managed().stream().filter(r -> r.major() == required).findFirst();
      if (managed.isPresent()) {
         return RuntimeResolver.Resolution.of(managed.get());
      } else {
         Optional<JavaRuntime> bundled = this.detector.bundled().filter(r -> r.major() >= required);
         return bundled.isPresent() ? RuntimeResolver.Resolution.of(bundled.get()) : RuntimeResolver.Resolution.install(required);
      }
   }

   public JavaRuntime ensure(int requiredMajor, Path profileJava, RuntimeInstaller.Progress progress) throws IOException, InterruptedException {
      RuntimeResolver.Resolution r = this.resolve(requiredMajor, profileJava);
      if (!r.needsInstall()) {
         return r.runtime();
      } else if (this.installer == null) {
         throw new IOException("Java " + r.installMajor() + " is required and no runtime installer is available");
      } else {
         return this.installer.install(r.installMajor(), progress);
      }
   }

   public record Resolution(JavaRuntime runtime, int installMajor) {
      public boolean needsInstall() {
         return this.runtime == null;
      }

      static RuntimeResolver.Resolution of(JavaRuntime r) {
         return new RuntimeResolver.Resolution(r, -1);
      }

      static RuntimeResolver.Resolution install(int major) {
         return new RuntimeResolver.Resolution(null, major);
      }
   }
}
