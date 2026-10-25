package com.metrofico.pulsar.launcher.install;

import com.metrofico.pulsar.launcher.download.DownloadRequest;
import com.metrofico.pulsar.launcher.meta.VersionDetail;
import com.metrofico.pulsar.launcher.rules.Platform;
import com.metrofico.pulsar.launcher.rules.RuleEvaluator;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public final class LibraryResolver {
   private final GameDirectories dirs;

   public LibraryResolver(GameDirectories dirs) {
      this.dirs = dirs;
   }

   public LibraryResolver.Resolution resolve(VersionDetail version, Platform platform) {
      List<ResolvedLibrary> classpath = new ArrayList<>();
      List<ResolvedLibrary> legacyNatives = new ArrayList<>();
      List<String> skipped = new ArrayList<>();

      for (VersionDetail.Library library : version.libraries()) {
         if (!RuleEvaluator.allows(library.rules(), platform, Set.of())) {
            skipped.add(library.name());
         } else {
            VersionDetail.LibraryDownloads downloads = library.downloads();
            if (downloads != null && downloads.artifact() != null) {
               classpath.add(this.resolved(library, downloads.artifact()));
            }

            if (library.hasLegacyNatives()) {
               String classifier = library.natives().get(platform.os());
               if (classifier != null) {
                  classifier = classifier.replace("${arch}", platform.is64Bit() ? "64" : "32");
                  VersionDetail.Artifact nativeJar = downloads != null && downloads.classifiers() != null ? downloads.classifiers().get(classifier) : null;
                  if (nativeJar == null) {
                     throw new IllegalStateException(
                        "Library " + library.name() + " declares native classifier '" + classifier + "' for " + platform.os() + " but has no such download"
                     );
                  }

                  legacyNatives.add(this.resolved(library, nativeJar));
               }
            }
         }
      }

      return new LibraryResolver.Resolution(List.copyOf(classpath), List.copyOf(legacyNatives), List.copyOf(skipped));
   }

   private ResolvedLibrary resolved(VersionDetail.Library library, VersionDetail.Artifact artifact) {
      Path target = artifact.path() != null ? this.dirs.library(artifact.path()) : this.dirs.library(mavenPath(library.name()));
      return new ResolvedLibrary(library, artifact, target);
   }

   static String mavenPath(String coordinates) {
      String[] parts = coordinates.split(":");
      if (parts.length < 3) {
         throw new IllegalArgumentException("Not Maven coordinates: " + coordinates);
      } else {
         String file = parts[1] + "-" + parts[2] + (parts.length > 3 ? "-" + parts[3] : "") + ".jar";
         return parts[0].replace('.', '/') + "/" + parts[1] + "/" + parts[2] + "/" + file;
      }
   }

   public record Resolution(List<ResolvedLibrary> classpath, List<ResolvedLibrary> legacyNatives, List<String> skipped) {
      public List<DownloadRequest> downloadRequests() {
         List<DownloadRequest> requests = new ArrayList<>();
         this.classpath.forEach(l -> requests.add(l.downloadRequest()));
         this.legacyNatives.forEach(l -> requests.add(l.downloadRequest()));
         return requests;
      }
   }
}
