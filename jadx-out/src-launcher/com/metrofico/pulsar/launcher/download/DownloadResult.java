package com.metrofico.pulsar.launcher.download;

public record DownloadResult(DownloadRequest request, DownloadResult.Outcome outcome, long bytes) {
   public static enum Outcome {
      CACHED,
      DOWNLOADED;
   }
}
