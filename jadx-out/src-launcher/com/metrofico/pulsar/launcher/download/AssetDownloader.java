package com.metrofico.pulsar.launcher.download;

import com.metrofico.pulsar.launcher.install.GameDirectories;
import com.metrofico.pulsar.launcher.meta.AssetIndex;
import com.metrofico.pulsar.launcher.meta.VersionDetail;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class AssetDownloader {
   private final VerifiedDownloader downloader;
   private final GameDirectories dirs;
   private final URI resourcesBase;

   public AssetDownloader(VerifiedDownloader downloader, GameDirectories dirs) {
      this(downloader, dirs, null);
   }

   public AssetDownloader(VerifiedDownloader downloader, GameDirectories dirs, URI resourcesBase) {
      this.downloader = downloader;
      this.dirs = dirs;
      this.resourcesBase = resourcesBase;
   }

   public AssetIndex fetchIndex(VersionDetail.AssetIndex indexMeta) throws IOException, InterruptedException {
      Path target = this.dirs.assetIndex(indexMeta.id());
      this.downloader.download(DownloadRequest.of(indexMeta.url(), target, indexMeta.sha1(), indexMeta.size()));
      return AssetIndex.fromJson(Files.readString(target, StandardCharsets.UTF_8));
   }

   public List<DownloadRequest> requestsFor(AssetIndex index) {
      List<DownloadRequest> requests = new ArrayList<>();

      for (AssetIndex.AssetObject object : index.uniqueObjects().values()) {
         String url = this.resourcesBase == null ? object.url() : this.resourcesBase.resolve(object.hash().substring(0, 2) + "/" + object.hash()).toString();
         requests.add(DownloadRequest.of(url, this.dirs.assetObject(object.hash()), object.hash(), object.size()));
      }

      return requests;
   }

   public AssetDownloader.Summary install(VersionDetail version, ProgressListener listener) throws IOException, InterruptedException {
      AssetIndex index = this.fetchIndex(version.assetIndex());
      if (index.isLegacyLayout()) {
         throw new UnsupportedOperationException("Legacy asset layout (virtual/map_to_resources) is not supported yet: " + version.assetIndex().id());
      } else {
         List<DownloadRequest> requests = this.requestsFor(index);
         List<DownloadResult> results = this.downloader.downloadAll(requests, listener);
         long downloaded = results.stream().filter(r -> r.outcome() == DownloadResult.Outcome.DOWNLOADED).mapToLong(DownloadResult::bytes).sum();
         return new AssetDownloader.Summary(version.assetIndex().id(), results.size(), index.totalSize(), downloaded);
      }
   }

   public record Summary(String indexId, int objects, long bytes, long downloadedBytes) {
   }
}
