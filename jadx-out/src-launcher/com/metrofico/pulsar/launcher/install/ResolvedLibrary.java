package com.metrofico.pulsar.launcher.install;

import com.metrofico.pulsar.launcher.download.DownloadRequest;
import com.metrofico.pulsar.launcher.meta.VersionDetail;
import java.nio.file.Path;

public record ResolvedLibrary(VersionDetail.Library library, VersionDetail.Artifact artifact, Path target) {
   public DownloadRequest downloadRequest() {
      return DownloadRequest.of(this.artifact.url(), this.target, this.artifact.sha1(), this.artifact.size());
   }
}
