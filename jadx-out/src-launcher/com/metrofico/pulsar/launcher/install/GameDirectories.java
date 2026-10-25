package com.metrofico.pulsar.launcher.install;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

public record GameDirectories(Path root, Path gameDir) {
   public static GameDirectories at(Path root) {
      return new GameDirectories(root.toAbsolutePath().normalize(), standardMinecraftDir());
   }

   public GameDirectories withGameDir(Path dir) {
      return new GameDirectories(this.root, dir.toAbsolutePath().normalize());
   }

   public static Path standardMinecraftDir() {
      return standardMinecraftDir(System.getProperty("os.name", ""), System.getenv("APPDATA"), System.getProperty("user.home", "."));
   }

   static Path standardMinecraftDir(String osName, String appData, String home) {
      String os = osName.toLowerCase(Locale.ROOT);
      if (!os.contains("win")) {
         return !os.contains("mac") && !os.contains("darwin")
            ? Path.of(home, ".minecraft").toAbsolutePath().normalize()
            : Path.of(home, "Library", "Application Support", "minecraft").toAbsolutePath().normalize();
      } else {
         Path base = appData != null && !appData.isBlank() ? Path.of(appData) : Path.of(home, "AppData", "Roaming");
         return base.resolve(".minecraft").toAbsolutePath().normalize();
      }
   }

   public Path versions() {
      return this.root.resolve("versions");
   }

   public Path libraries() {
      return this.root.resolve("libraries");
   }

   public Path assets() {
      return this.root.resolve("assets");
   }

   public Path natives() {
      return this.root.resolve("natives");
   }

   public Path logs() {
      return this.root.resolve("logs");
   }

   public Path versionDir(String versionId) {
      return this.versions().resolve(versionId);
   }

   public Path clientJar(String versionId) {
      return this.versionDir(versionId).resolve(versionId + ".jar");
   }

   public Path versionJson(String versionId) {
      return this.versionDir(versionId).resolve(versionId + ".json");
   }

   public Path library(String relativePath) {
      return this.libraries().resolve(relativePath);
   }

   public Path assetIndex(String indexId) {
      return this.assets().resolve("indexes").resolve(indexId + ".json");
   }

   public Path assetObject(String hash) {
      return this.assets().resolve("objects").resolve(hash.substring(0, 2)).resolve(hash);
   }

   public Path nativesFor(String versionId) {
      return this.natives().resolve(versionId);
   }

   public void createAll() throws IOException {
      for (Path p : new Path[]{
         this.versions(), this.libraries(), this.assets().resolve("indexes"), this.assets().resolve("objects"), this.natives(), this.logs(), this.gameDir()
      }) {
         Files.createDirectories(p);
      }
   }
}
