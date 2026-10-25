package com.metrofico.pulsar.launcher;

import com.metrofico.pulsar.launcher.download.AssetDownloader;
import com.metrofico.pulsar.launcher.download.DownloadFailedException;
import com.metrofico.pulsar.launcher.download.DownloadRequest;
import com.metrofico.pulsar.launcher.download.DownloadResult;
import com.metrofico.pulsar.launcher.download.VerifiedDownloader;
import com.metrofico.pulsar.launcher.install.GameDirectories;
import com.metrofico.pulsar.launcher.install.LibraryResolver;
import com.metrofico.pulsar.launcher.install.NativesExtractor;
import com.metrofico.pulsar.launcher.launch.GameLauncher;
import com.metrofico.pulsar.launcher.launch.LaunchOptions;
import com.metrofico.pulsar.launcher.launch.LaunchSession;
import com.metrofico.pulsar.launcher.meta.PistonMetaClient;
import com.metrofico.pulsar.launcher.meta.VersionDetail;
import com.metrofico.pulsar.launcher.meta.VersionManifest;
import com.metrofico.pulsar.launcher.rules.Platform;
import com.metrofico.pulsar.launcher.runtime.JavaPolicy;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.stream.Stream;

public final class PulsarLauncher {
   public static final String BRAND = "Pulsar Launcher";
   public static final String VERSION = readVersion();
   public static final String SERVICE_URL = serviceUrl();
   public static final String REALTIME_URL = property("PULSAR_REALTIME_URL", "realtimeUrl");
   public static final String BOOTSTRAP_MAIN = "com.metrofico.pulsar.bootstrap.Main";
   private final HttpClient http = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build();
   private final PistonMetaClient meta = new PistonMetaClient(this.http);
   private final VerifiedDownloader downloader = VerifiedDownloader.createDefault(this.http);
   private final GameDirectories dirs = GameDirectories.at(Path.of("run")).withGameDir(Path.of("run", "game"));
   private final Platform platform = Platform.current();

   private static String serviceUrl() {
      return property("PULSAR_SERVICE_URL", "serviceUrl").replaceAll("/+$", "");
   }

   private static String property(String envName, String key) {
      String env = System.getenv(envName);
      if (env != null && !env.isBlank()) {
         return env.trim();
      } else {
         try {
            String var6;
            try (InputStream in = PulsarLauncher.class.getResourceAsStream("/pulsar-version.properties")) {
               Properties p = new Properties();
               if (in != null) {
                  p.load(in);
               }

               String v = p.getProperty(key, "").trim();
               var6 = v.contains("${") ? "" : v;
            }

            return var6;
         } catch (IOException var9) {
            return "";
         }
      }
   }

   private static String readVersion() {
      try {
         String var3;
         try (InputStream in = PulsarLauncher.class.getResourceAsStream("/pulsar-version.properties")) {
            if (in == null) {
               return "dev";
            }

            Properties p = new Properties();
            p.load(in);
            String v = p.getProperty("version", "dev").trim();
            var3 = !v.isEmpty() && !v.contains("${") ? v : "dev";
         }

         return var3;
      } catch (IOException var6) {
         return "dev";
      }
   }

   public static void main(String[] args) throws Exception {
      System.out.println("Pulsar Launcher " + VERSION);
      String command = args.length > 0 ? args[0] : "prepare";
      String versionId = args.length > 1 && !args[1].startsWith("--") ? args[1] : null;
      Path clientDir = null;
      Path engineDir = null;
      String quickPlay = null;

      for (int i = 1; i < args.length - 1; i++) {
         if (args[i].equals("--client")) {
            clientDir = Path.of(args[i + 1]);
         } else if (args[i].equals("--server")) {
            quickPlay = args[i + 1];
         } else if (args[i].equals("--engine")) {
            engineDir = Path.of(args[i + 1]);
         }
      }

      PulsarLauncher launcher = new PulsarLauncher();

      try {
         switch (command) {
            case "versions":
               launcher.versions();
               break;
            case "prepare":
               launcher.prepare(versionId);
               break;
            case "launch":
               launcher.launch(versionId, clientDir, engineDir, quickPlay);
               break;
            default:
               fail(2, "Unknown command '" + command + "'. Use: versions | prepare [version] | launch [version]");
         }
      } catch (DownloadFailedException var9) {
         fail(3, "Download failed: " + var9.getMessage() + "\nCheck your connection and retry; verified files are kept.");
      } catch (ConnectException var10) {
         fail(3, "Cannot reach Mojang's servers (" + var10.getMessage() + "). Check your connection and retry.");
      } catch (IllegalStateException | UnsupportedOperationException | IllegalArgumentException var11) {
         fail(4, var11.getMessage());
      } catch (IOException var12) {
         fail(5, "I/O error: " + var12.getMessage());
      }
   }

   private static void fail(int code, String message) {
      System.err.println("error: " + message);
      System.exit(code);
   }

   private void versions() throws Exception {
      VersionManifest manifest = this.meta.fetchVersionManifest();
      System.out.println("Latest release " + manifest.latest().release() + ", latest snapshot " + manifest.latest().snapshot());
      manifest.versions().stream().filter(v -> "release".equals(v.type())).limit(15L).forEach(v -> System.out.println("  " + v.id()));
   }

   private static List<Path> clientJars(Path dir) throws IOException {
      if (!Files.isDirectory(dir)) {
         throw new IllegalArgumentException("Client directory does not exist: " + dir);
      } else {
         List var3;
         try (Stream<Path> files = Files.list(dir)) {
            List<Path> jars = files.filter(f -> f.toString().endsWith(".jar")).map(Path::toAbsolutePath).sorted().toList();
            if (jars.isEmpty()) {
               throw new IllegalArgumentException("No jars in client directory " + dir + " (run ./gradlew :bootstrap:installDist)");
            }

            var3 = jars;
         }

         return var3;
      }
   }

   private PulsarLauncher.Prepared prepare(String versionId) throws Exception {
      VersionManifest manifest = this.meta.fetchVersionManifest();
      String id = versionId != null ? versionId : manifest.latest().release();
      VersionManifest.Version version = manifest.findVersion(id)
         .orElseThrow(() -> new IllegalArgumentException("Version '" + id + "' is not in Mojang's manifest"));
      VersionDetail detail = this.meta.fetchVersionDetail(version);
      Files.createDirectories(this.dirs.versionDir(detail.id()));
      Files.writeString(this.dirs.versionJson(detail.id()), this.meta.fetchVersionDetailJson(version));
      System.out
         .println(
            "Version "
               + detail.id()
               + ": Java "
               + JavaPolicy.requiredMajor(detail.id(), detail.javaVersion().majorVersion())
               + (
                  detail.javaVersion().majorVersion() < 25
                     ? " (Mojang declara " + detail.javaVersion().majorVersion() + "; el cliente de Pulsar es Java 25, ADR 0005)"
                     : ""
               )
               + " | "
               + detail.libraries().size()
               + " libraries | client "
               + detail.downloads().client().size() / 1000000L
               + " MB | assets "
               + detail.assetIndex().totalSize() / 1000000L
               + " MB"
         );
      this.dirs.createAll();
      VersionDetail.Artifact client = detail.downloads().client();
      DownloadResult jar = this.downloader.download(DownloadRequest.of(client.url(), this.dirs.clientJar(detail.id()), client.sha1(), client.size()));
      System.out.println("Client jar " + jar.outcome().name().toLowerCase() + " (" + jar.bytes() / 1000000L + " MB, SHA-1 verified)");
      LibraryResolver.Resolution libraries = new LibraryResolver(this.dirs).resolve(detail, this.platform);
      long start = System.nanoTime();
      List<DownloadResult> libResults = this.downloader.downloadAll(libraries.downloadRequests(), (r, done, total) -> {});
      long libDownloaded = libResults.stream().filter(r -> r.outcome() == DownloadResult.Outcome.DOWNLOADED).count();
      System.out
         .println(
            "Libraries for "
               + this.platform.os()
               + "/"
               + this.platform.arch()
               + ": "
               + libraries.classpath().size()
               + " on classpath ("
               + libDownloaded
               + " downloaded, "
               + libraries.skipped().size()
               + " skipped by rules) in "
               + (System.nanoTime() - start) / 1000000L
               + " ms"
         );
      if (!libraries.legacyNatives().isEmpty()) {
         int n = new NativesExtractor().extract(libraries.legacyNatives(), this.dirs.nativesFor(detail.id()));
         System.out.println("Natives (LWJGL 2): " + n + " files extracted to " + this.dirs.nativesFor(detail.id()));
      }

      start = System.nanoTime();
      AssetDownloader.Summary assets = new AssetDownloader(this.downloader, this.dirs).install(detail, (r, done, total) -> {
         if (done % 1000 == 0) {
            System.out.println("  " + done + "/" + total + " assets verified");
         }
      });
      System.out
         .println(
            "Assets: index "
               + assets.indexId()
               + ", "
               + assets.objects()
               + " objects, "
               + assets.downloadedBytes() / 1000000L
               + " of "
               + assets.bytes() / 1000000L
               + " MB downloaded in "
               + (System.nanoTime() - start) / 1000000L
               + " ms"
         );
      return new PulsarLauncher.Prepared(detail, libraries);
   }

   private void launch(String versionId, Path clientDir, Path engineDir, String quickPlay) throws Exception {
      PulsarLauncher.Prepared prepared = this.prepare(versionId);
      LaunchOptions options = LaunchOptions.defaults(System.getenv("PULSAR_GC"));
      String server = quickPlay != null ? quickPlay : System.getenv("PULSAR_QUICKPLAY");
      if (server != null && !server.isBlank()) {
         options = options.withQuickPlay(server.trim());
      }

      if (clientDir != null) {
         options = options.withBootstrap(clientJars(clientDir), "com.metrofico.pulsar.bootstrap.Main");
         System.out.println("Pulsar Client bootstrap from " + clientDir + " (" + options.extraClasspath().size() + " jars)");
      }

      if (engineDir != null) {
         if (clientDir == null) {
            throw new IllegalArgumentException("--engine requires --client (the engine runs inside the Pulsar Client)");
         }

         options = options.withGameClasspath(clientJars(engineDir));
         System.out.println("Engine backend jars from " + engineDir + " (" + options.extraGameClasspath().size() + " jars)");
      }

      GameLauncher launcher = new GameLauncher(this.dirs);
      GameLauncher.Command command = launcher.buildCommand(
         prepared.detail(), prepared.libraries(), this.platform, LaunchSession.developmentOffline("Dev"), options
      );
      Process game = launcher.launch(command);
      System.out.println("Game started (pid " + game.pid() + "), log: " + command.logFile());
      int exit = game.waitFor();
      System.out.println("Game exited with code " + exit);
      if (exit != 0) {
         List<String> lines = Files.readAllLines(command.logFile());
         System.err.println("The game closed with an error. Last lines of " + command.logFile().getFileName() + ":");
         lines.stream().skip(Math.max(0, lines.size() - 15)).forEach(l -> System.err.println("  " + l));
         System.exit(exit);
      }
   }

   private record Prepared(VersionDetail detail, LibraryResolver.Resolution libraries) {
   }
}
