package com.metrofico.pulsar.launcher.download;

@FunctionalInterface
public interface ProgressListener {
   ProgressListener NONE = (result, done, total) -> {};

   void onFileCompleted(DownloadResult var1, int var2, int var3);
}
