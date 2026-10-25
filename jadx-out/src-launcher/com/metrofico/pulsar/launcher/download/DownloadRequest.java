package com.metrofico.pulsar.launcher.download;

import com.metrofico.pulsar.launcher.integrity.Sha1;
import com.metrofico.pulsar.launcher.integrity.Sha256;
import java.net.URI;
import java.nio.file.Path;
import java.security.MessageDigest;

public record DownloadRequest(URI url, Path target, String sha1, String sha256, long size) {
   public DownloadRequest(URI url, Path target, String sha1, String sha256, long size) {
      if (url != null && target != null) {
         if (sha1 != null && sha1.length() != 40) {
            throw new IllegalArgumentException("sha1 must be 40 hex chars, got: " + sha1);
         } else if (sha256 != null && sha256.length() != 64) {
            throw new IllegalArgumentException("sha256 must be 64 hex chars, got: " + sha256);
         } else if (sha1 != null && sha256 != null) {
            throw new IllegalArgumentException("use sha1 or sha256, not both");
         } else {
            this.url = url;
            this.target = target;
            this.sha1 = sha1;
            this.sha256 = sha256;
            this.size = size;
         }
      } else {
         throw new IllegalArgumentException("url and target are required");
      }
   }

   public DownloadRequest(URI url, Path target, String sha1, long size) {
      this(url, target, sha1, null, size);
   }

   public static DownloadRequest of(String url, Path target, String sha1, long size) {
      return new DownloadRequest(URI.create(url), target, sha1, null, size);
   }

   public static DownloadRequest sha256(String url, Path target, String sha256, long size) {
      return new DownloadRequest(URI.create(url), target, null, sha256, size);
   }

   public String expectedHash() {
      return this.sha256 != null ? this.sha256 : this.sha1;
   }

   public MessageDigest newDigest() {
      return this.sha256 != null ? Sha256.digest() : Sha1.digest();
   }

   public String algorithm() {
      return this.sha256 != null ? "SHA-256" : "SHA-1";
   }
}
