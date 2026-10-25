package com.metrofico.pulsar.launcher.runtime;

import com.metrofico.pulsar.launcher.download.DownloadFailedException;
import com.metrofico.pulsar.launcher.download.DownloadRequest;
import com.metrofico.pulsar.launcher.download.VerifiedDownloader;
import com.metrofico.pulsar.launcher.rules.Platform;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public final class RuntimeInstaller {
   private final RuntimeSource source;
   private final VerifiedDownloader downloader;
   private final RuntimeDirectories dirs;
   private final Platform platform;
   private final JavaDetector detector;

   public RuntimeInstaller(RuntimeSource source, VerifiedDownloader downloader, RuntimeDirectories dirs, Platform platform, JavaDetector detector) {
      this.source = source;
      this.downloader = downloader;
      this.dirs = dirs;
      this.platform = platform;
      this.detector = detector;
   }

   public JavaRuntime install(int major, RuntimeInstaller.Progress progress) throws IOException, InterruptedException {
      RuntimeRelease release = this.source.latest(major, this.platform);
      Path finalDir = this.dirs.managed(this.source.vendor(), major, this.platform);
      Files.createDirectories(this.dirs.runtimes());
      Path archive = this.dirs.runtimes().resolve(release.fileName());
      Path staging = this.dirs.runtimes().resolve(finalDir.getFileName() + ".partial");
      progress.on("download", 0L, release.size());

      try {
         this.downloader.download(DownloadRequest.sha256(release.url(), archive, release.sha256(), release.size()));
      } catch (DownloadFailedException var14) {
         throw new IOException("could not download Java " + major + ": " + var14.getMessage(), var14);
      }

      progress.on("download", release.size(), release.size());
      deleteTree(staging);
      deleteTree(finalDir);

      JavaRuntime var9;
      try {
         progress.on("extract", 0L, 1L);
         ArchiveExtractor.extract(archive, staging, release.format());
         Path home = flatten(staging);
         markExecutable(home.resolve("bin"));
         JavaRuntime runtime = this.detector
            .inspect(home, JavaRuntime.Source.MANAGED)
            .orElseThrow(() -> new IOException("downloaded archive is not a usable Java runtime: " + release.fileName()));
         if (runtime.major() != major) {
            throw new IOException("expected Java " + major + " but the archive contains " + runtime.major());
         }

         Files.writeString(
            RuntimeDirectories.marker(home),
            "{\"vendor\":\""
               + this.source.vendor()
               + "\",\"major\":"
               + major
               + ",\"release\":\""
               + release.releaseName()
               + "\",\"sha256\":\""
               + release.sha256()
               + "\",\"installedAtEpochMs\":"
               + System.currentTimeMillis()
               + "}\n"
         );
         Files.move(home, finalDir, StandardCopyOption.ATOMIC_MOVE);
         progress.on("extract", 1L, 1L);
         Files.deleteIfExists(archive);
         var9 = new JavaRuntime(finalDir, runtime.major(), runtime.implementor(), JavaRuntime.Source.MANAGED);
      } catch (IOException var15) {
         deleteTree(staging);
         throw var15;
      } finally {
         deleteTree(staging);
      }

      return var9;
   }

   static Path flatten(Path staging) throws IOException {
      List<Path> children;
      try (Stream<Path> s = Files.list(staging)) {
         children = s.toList();
      }

      Path root = children.size() == 1 && Files.isDirectory(children.get(0)) ? children.get(0) : staging;
      Path mac = root.resolve("Contents").resolve("Home");
      return Files.isDirectory(mac) ? mac : root;
   }

   private static void markExecutable(Path bin) {
      if (Files.isDirectory(bin)) {
         try (Stream<Path> s = Files.list(bin)) {
            for (Path p : s.filter(x$0 -> Files.isRegularFile(x$0)).toList()) {
               try {
                  EnumSet<PosixFilePermission> perms = EnumSet.copyOf(Files.getPosixFilePermissions(p));
                  perms.add(PosixFilePermission.OWNER_EXECUTE);
                  Files.setPosixFilePermissions(p, perms);
               } catch (IOException | UnsupportedOperationException var6) {
               }
            }
         } catch (IOException var8) {
         }
      }
   }

   static void deleteTree(Path dir) throws IOException {
      if (Files.exists(dir, LinkOption.NOFOLLOW_LINKS)) {
         try (Stream<Path> walk = Files.walk(dir)) {
            for (Path p : walk.sorted(Comparator.reverseOrder()).toList()) {
               Files.deleteIfExists(p);
            }
         }
      }
   }

   public Optional<JavaRuntime> installed(int major) {
      return this.detector
         .inspect(this.dirs.managed(this.source.vendor(), major, this.platform), JavaRuntime.Source.MANAGED)
         .filter(r -> Files.isRegularFile(RuntimeDirectories.marker(r.home())));
   }

   public interface Progress {
      RuntimeInstaller.Progress NONE = (stage, done, total) -> {};

      void on(String var1, long var2, long var4);
   }
}
