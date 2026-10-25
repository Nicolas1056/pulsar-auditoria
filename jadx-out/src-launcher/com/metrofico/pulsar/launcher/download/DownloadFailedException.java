package com.metrofico.pulsar.launcher.download;

import java.io.IOException;

public final class DownloadFailedException extends IOException {
   private final DownloadRequest request;
   private final int attempts;

   public DownloadFailedException(DownloadRequest request, int attempts, String reason, Throwable cause) {
      super("Download of " + request.url() + " failed after " + attempts + " attempt(s): " + reason, cause);
      this.request = request;
      this.attempts = attempts;
   }

   public DownloadRequest request() {
      return this.request;
   }

   public int attempts() {
      return this.attempts;
   }
}
