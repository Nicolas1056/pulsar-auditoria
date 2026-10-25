package com.metrofico.pulsar.launcher.daemon;

import com.metrofico.pulsar.launcher.PulsarLauncher;
import com.metrofico.pulsar.launcher.auth.AuthEndpoints;
import com.metrofico.pulsar.launcher.auth.MicrosoftAuth;
import com.metrofico.pulsar.launcher.auth.SecretStore;
import com.metrofico.pulsar.launcher.auth.SecretStores;
import com.metrofico.pulsar.launcher.download.AssetDownloader;
import com.metrofico.pulsar.launcher.download.DownloadFailedException;
import com.metrofico.pulsar.launcher.download.DownloadRequest;
import com.metrofico.pulsar.launcher.download.VerifiedDownloader;
import com.metrofico.pulsar.launcher.install.GameDirectories;
import com.metrofico.pulsar.launcher.install.LibraryResolver;
import com.metrofico.pulsar.launcher.install.NativesExtractor;
import com.metrofico.pulsar.launcher.install.LibraryResolver.Resolution;
import com.metrofico.pulsar.launcher.launch.GameLauncher;
import com.metrofico.pulsar.launcher.launch.LaunchOptions;
import com.metrofico.pulsar.launcher.launch.LaunchSession;
import com.metrofico.pulsar.launcher.launch.GameLauncher.Command;
import com.metrofico.pulsar.launcher.meta.PistonMetaClient;
import com.metrofico.pulsar.launcher.meta.VersionDetail;
import com.metrofico.pulsar.launcher.meta.VersionManifest;
import com.metrofico.pulsar.launcher.meta.VersionDetail.Artifact;
import com.metrofico.pulsar.launcher.meta.VersionManifest.Version;
import com.metrofico.pulsar.launcher.rules.Platform;
import com.metrofico.pulsar.launcher.runtime.AdoptiumClient;
import com.metrofico.pulsar.launcher.runtime.JavaDetector;
import com.metrofico.pulsar.launcher.runtime.JavaPolicy;
import com.metrofico.pulsar.launcher.runtime.JavaRuntime;
import com.metrofico.pulsar.launcher.runtime.RuntimeDirectories;
import com.metrofico.pulsar.launcher.runtime.RuntimeInstaller;
import com.metrofico.pulsar.launcher.runtime.RuntimeResolver;
import com.metrofico.pulsar.launcher.update.ReleaseManifest;
import com.metrofico.pulsar.launcher.update.Semver;
import java.io.IOException;
import java.io.InputStream;
import java.net.ConnectException;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HexFormat;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.zip.ZipFile;

public final class LauncherCore implements CoreService {
   private final HttpClient http = HttpClient.newBuilder().followRedirects(Redirect.NORMAL).build();
   private final PistonMetaClient meta = new PistonMetaClient(this.http);
   private final VerifiedDownloader downloader = VerifiedDownloader.createDefault(this.http);
   private final GameDirectories dirs;
   private final Platform platform = Platform.current();
   private final Path clientDir;
   private final Path engineRoot;
   private final Path repo;
   private final JavaDetector javaDetector;
   private final RuntimeResolver runtimes;
   private volatile JavaRuntime lastJava;
   private volatile VersionDetail lastPrepared;
   private volatile Resolution lastLibraries;
   private volatile Thread preparing;
   static final List<String> INSTALL_ENV = List.of("PULSAR_CLIENT_DIR", "PULSAR_ENGINE_DIR", "PULSAR_EXECUTABLE", "APPDIR", "APPIMAGE", "ARGV0", "OWD");
   private SecretStore secretStore;
   private volatile Path lastGameDir;

   public LauncherCore(Path root) {
      this.dirs = GameDirectories.at(root);
      this.repo = root.toAbsolutePath().getParent();
      this.clientDir = existing(env("PULSAR_CLIENT_DIR", this.repo.resolve("bootstrap/build/install/bootstrap/lib")));
      String engineEnv = System.getenv("PULSAR_ENGINE_DIR");
      this.engineRoot = engineEnv != null && !engineEnv.isBlank() ? Path.of(engineEnv) : null;
      RuntimeDirectories runtimeDirs = new RuntimeDirectories(this.dirs.root());
      this.javaDetector = JavaDetector.current(runtimeDirs);
      this.runtimes = new RuntimeResolver(
         this.javaDetector, new RuntimeInstaller(AdoptiumClient.createDefault(), this.downloader, runtimeDirs, this.platform, this.javaDetector)
      );
   }

   @Override
   public List<CoreService.JavaInfo> runtimes() {
      return this.javaDetector.all().stream().map(LauncherCore::info).toList();
   }

   @Override
   public CoreService.JavaInfo javaFor(String version, String javaPath) {
      int required = this.requiredJava(version);
      if (required < 0 && javaPath == null) {
         return CoreService.JavaInfo.unknown();
      } else {
         com.metrofico.pulsar.launcher.runtime.RuntimeResolver.Resolution r = this.runtimes.resolve(required, javaPath == null ? null : Path.of(javaPath));
         return r.needsInstall() ? CoreService.JavaInfo.download(r.installMajor()) : info(r.runtime());
      }
   }

   private int requiredJava(String version) {
      try {
         Path json = this.dirs.versionJson(version);
         if (!Files.isRegularFile(json)) {
            return -1;
         } else {
            VersionDetail detail = VersionDetail.fromJson(Files.readString(json));
            return JavaPolicy.requiredMajor(version, detail.javaVersion() == null ? 0 : detail.javaVersion().majorVersion());
         }
      } catch (RuntimeException | IOException var4) {
         return -1;
      }
   }

   private static CoreService.JavaInfo info(JavaRuntime r) {
      return new CoreService.JavaInfo(r.major(), r.home().toString(), r.source().name().toLowerCase(), r.implementor());
   }

   private static Path env(String name, Path fallback) {
      String value = System.getenv(name);
      return value != null && !value.isBlank() ? Path.of(value) : fallback;
   }

   @Override
   public Path logsDir() {
      return this.dirs.logs();
   }

   @Override
   public Path crashReportsDir() {
      Path g = this.lastGameDir;
      return (g != null ? g : this.dirs.gameDir()).resolve("crash-reports");
   }

   @Override
   public List<CoreService.Release> versions() {
      try {
         boolean published = Semver.isValidPublic(PulsarLauncher.VERSION);
         return this.meta
            .fetchVersionManifest()
            .versions()
            .stream()
            .filter(v -> !published || EngineDirs.supported(v.id()))
            .map(v -> new CoreService.Release(v.id(), v.type()))
            .toList();
      } catch (ConnectException var2) {
         throw offline(var2);
      } catch (InterruptedException | IOException var3) {
         throw new DaemonException("io", "No se pudo consultar las versiones", var3.getMessage(), null, var3);
      }
   }

   @Override
   public void prepare(String version, String javaPath, Consumer<PrepareProgress> progress) {
      if (!EngineDirs.supported(version) && Semver.isValidPublic(PulsarLauncher.VERSION)) {
         throw new DaemonException("unsupported_version", "Versión no disponible", "Edita el perfil y elige otra versión.");
      } else {
         this.preparing = Thread.currentThread();

         try {
            progress.accept(new PrepareProgress(PrepareProgress.Stage.MANIFEST, 0, 0));
            VersionManifest manifest = this.meta.fetchVersionManifest();
            Version v = (Version)manifest.findVersion(version)
               .orElseThrow(
                  () -> new DaemonException("unknown_version", "Versión no disponible", "La versión " + version + " no está en el manifiesto de Mojang.")
               );
            String detailJson = this.meta.fetchVersionDetailJson(v);
            VersionDetail detail = VersionDetail.fromJson(detailJson);
            this.dirs.createAll();
            Files.createDirectories(this.dirs.versionDir(detail.id()));
            Files.writeString(this.dirs.versionJson(detail.id()), detailJson);
            progress.accept(new PrepareProgress(PrepareProgress.Stage.RUNTIME, 0, 0));
            int required = JavaPolicy.requiredMajor(detail.id(), detail.javaVersion() == null ? 0 : detail.javaVersion().majorVersion());
            this.lastJava = this.runtimes
               .ensure(
                  required,
                  javaPath == null ? null : Path.of(javaPath),
                  (stage, done, total) -> {
                     if ("download".equals(stage)) {
                        progress.accept(
                           new PrepareProgress(
                              PrepareProgress.Stage.RUNTIME, (int)Math.min(2147483647L, done / 1024L), (int)Math.min(2147483647L, total / 1024L)
                           )
                        );
                     }
                  }
               );
            progress.accept(new PrepareProgress(PrepareProgress.Stage.RUNTIME, 1, 1));
            progress.accept(new PrepareProgress(PrepareProgress.Stage.CLIENT, 0, 1));
            Artifact client = detail.downloads().client();
            this.downloader.download(DownloadRequest.of(client.url(), this.dirs.clientJar(detail.id()), client.sha1(), client.size()));
            Resolution libraries = new LibraryResolver(this.dirs).resolve(detail, this.platform);
            List<DownloadRequest> libRequests = libraries.downloadRequests();
            progress.accept(new PrepareProgress(PrepareProgress.Stage.LIBRARIES, 0, libRequests.size()));
            this.downloader.downloadAll(libRequests, (r, done, total) -> progress.accept(new PrepareProgress(PrepareProgress.Stage.LIBRARIES, done, total)));
            if (!libraries.legacyNatives().isEmpty()) {
               new NativesExtractor().extract(libraries.legacyNatives(), this.dirs.nativesFor(detail.id()));
            }

            progress.accept(new PrepareProgress(PrepareProgress.Stage.ASSETS, 0, 0));
            new AssetDownloader(this.downloader, this.dirs)
               .install(detail, (r, done, total) -> progress.accept(new PrepareProgress(PrepareProgress.Stage.ASSETS, done, total)));
            progress.accept(new PrepareProgress(PrepareProgress.Stage.ASSETS, 1, 1));
            this.lastPrepared = detail;
            this.lastLibraries = libraries;
         } catch (DownloadFailedException var18) {
            throw new DaemonException(
               "download_failed",
               "No se pudo verificar la descarga de " + version,
               "Revisa tu conexión y reintenta; lo ya verificado no se vuelve a descargar.",
               var18.getMessage(),
               var18
            );
         } catch (ConnectException var19) {
            throw offline(var19);
         } catch (InterruptedException var20) {
            Thread.currentThread().interrupt();
            throw new DaemonException("cancelled", "Preparación cancelada", "Puedes volver a pulsar Jugar cuando quieras.");
         } catch (IOException var21) {
            throw new DaemonException("io", "Algo falló al preparar " + version, var21.getMessage(), null, var21);
         } finally {
            this.preparing = null;
         }
      }
   }

   @Override
   public MicrosoftAuth microsoftAuth() {
      return new MicrosoftAuth(this.http, AuthEndpoints.fromEnvironment());
   }

   @Override
   public MicrosoftAuth microsoftAuth(String clientId) {
      return new MicrosoftAuth(this.http, AuthEndpoints.forClient(clientId));
   }

   @Override
   public UpdateService updateService(Path downloadsDir, Consumer<String> events) {
      return new UpdateService(this.http, this.downloader, PulsarLauncher.SERVICE_URL, () -> {
         try {
            return ReleaseManifest.embeddedPublicKey();
         } catch (Exception var1x) {
            return null;
         }
      }, UpdateService.currentInstallation(), downloadsDir, command -> {
         List<String> line = new ArrayList<>(GameDetacher.detach(command));
         ProcessBuilder pb = new ProcessBuilder(line).redirectErrorStream(true).redirectOutput(java.lang.ProcessBuilder.Redirect.DISCARD);
         INSTALL_ENV.forEach(pb.environment()::remove);
         pb.start();
      }, this::open, events);
   }

   @Override
   public synchronized SecretStore secretStore() {
      if (this.secretStore == null) {
         long t0 = System.nanoTime();
         this.secretStore = SecretStores.detect(this.dirs.root().resolve("pulsar"), LauncherLog::info);
         LauncherLog.info("secret store: " + this.secretStore.describe() + " · " + (System.nanoTime() - t0) / 1000000L + " ms");
      }

      return this.secretStore;
   }

   @Override
   public CoreService.Launched launch(Profile profile, Preferences preferences, Identity identity, Consumer<Integer> onExit) {
      return this.launch(profile, preferences, identity, null, onExit);
   }

   @Override
   public CoreService.Launched launch(Profile profile, Preferences preferences, Identity identity, String server, Consumer<Integer> onExit) {
      VersionDetail detail = this.lastPrepared;
      if (detail != null && detail.id().equals(profile.version())) {
         try {
            LaunchOptions options = LaunchOptions.defaults(preferences.gcMode());
            if (profile.memoryMb() != null) {
               options = new LaunchOptions(
                  options.javaExecutable(),
                  profile.memoryMb(),
                  null,
                  null,
                  options.extraJvmArgs(),
                  options.extraClasspath(),
                  options.mainClassOverride(),
                  options.extraGameClasspath()
               );
            }

            if (this.lastJava != null) {
               options = options.withJava(this.lastJava.executable());
            }

            String quickPlay = server != null ? server : System.getenv("PULSAR_QUICKPLAY");
            if (quickPlay != null && !quickPlay.isBlank()) {
               options = options.withQuickPlay(quickPlay.trim());
            }

            if (this.clientDir == null && Semver.isValidPublic(PulsarLauncher.VERSION)) {
               LauncherLog.error("launch · Pulsar client not found (PULSAR_CLIENT_DIR=" + System.getenv("PULSAR_CLIENT_DIR") + ")", null);
               throw new DaemonException(
                  "install_incomplete",
                  "No se encontró el cliente de Pulsar",
                  "Cierra Pulsar por completo y ábrelo de nuevo. Si sigue pasando, reinstálalo desde pulsarlauncher.com."
               );
            } else {
               Path session = this.sessionCopyDir();
               List<String> detectorLine = null;
               InputDetector detector = InputDetector.locate(this.repo);
               if (detector == null) {
                  if (InputDetector.requiredHere() && Semver.isValidPublic(PulsarLauncher.VERSION)) {
                     LauncherLog.error("launch · pulsar-input not found next to " + System.getenv("PULSAR_EXECUTABLE"), null);
                     throw new DaemonException(
                        "install_incomplete",
                        "Falta un componente de Pulsar",
                        "Cierra Pulsar por completo y ábrelo de nuevo. Si sigue pasando, reinstálalo desde pulsarlauncher.com."
                     );
                  }

                  LauncherLog.warn("launch · pulsar-input not found: mouse lock off");
               } else {
                  String missing = detector.missingPermission();
                  if (missing != null) {
                     LauncherLog.warn("launch · pulsar-input cannot read pointers:\n" + missing);
                     throw new DaemonException(
                        "input_permission",
                        "Falta terminar la instalación",
                        "Pulsar necesita configurar un componente del sistema. Tu equipo te pedirá la contraseña una sola vez."
                     );
                  }

                  Path inputLog = this.dirs.logs().resolve("input-devices.log");
                  detectorLine = detector.prepare(session.resolve("input"), inputLog);
                  options = options.withExtraJvmArgs(
                     InputDetector.jvmArgs(Path.of(detectorLine.get(0)), inputLog, Semver.isValidPublic(PulsarLauncher.VERSION))
                  );
               }

               if (this.clientDir != null) {
                  options = options.withBootstrap(copyJars(jars(this.clientDir), session.resolve("client")), "com.metrofico.pulsar.bootstrap.Main");
                  Path engineDir = EngineDirs.resolve(this.engineRoot, this.repo, profile.version());
                  if (engineDir != null) {
                     options = options.withGameClasspath(copyJars(jars(engineDir), session.resolve("engine")));
                  } else {
                     LauncherLog.warn(
                        "Sin backend para "
                           + profile.version()
                           + " (solo mixins del bootstrap): "
                           + (this.engineRoot != null ? this.engineRoot : "sin PULSAR_ENGINE_DIR")
                     );
                  }
               }

               Path gameDir = this.gameDirFor(profile);
               this.lastGameDir = gameDir;
               GameLauncher launcher = new GameLauncher(this.dirs.withGameDir(gameDir));
               Command command = detach(
                  launcher.buildCommand(
                     detail,
                     this.lastLibraries,
                     this.platform,
                     identity.online()
                        ? new LaunchSession(identity.name(), UUID.fromString(identity.uuid()), identity.accessToken(), "msa", identity.xuid(), "")
                        : LaunchSession.developmentOffline(identity.name()),
                     options
                  )
               );
               GpuPreference.Applied gpu = GpuPreference.apply(command.commandLine());
               LauncherLog.info("gpu · " + gpu.mode());
               if (!gpu.environment().isEmpty()) {
                  Map<String, String> env = new HashMap<>(command.environment());
                  env.putAll(gpu.environment());
                  command = new Command(command.commandLine(), command.workingDir(), command.logFile(), env);
               }

               if (preferences.gamePriorityOn()) {
                  GamePriority.Applied applied = GamePriority.wrap(command.commandLine(), "pulsar-game-" + System.currentTimeMillis());
                  command = new Command(applied.commandLine(), command.workingDir(), command.logFile(), command.environment());
                  System.err.println("[launcher] game priority: " + applied.mode());
               }

               Process process = detectorLine == null ? launcher.launch(command) : launcher.launch(command, GameDetacher.detach(detectorLine));
               if (preferences.gamePriorityOn()) {
                  GamePriority.boostWindows(process.pid());
               }

               process.onExit().thenAccept(p -> onExit.accept(p.exitValue()));
               return new CoreService.Launched(process.pid(), command.logFile().toString());
            }
         } catch (IllegalStateException var17) {
            throw new DaemonException("java_required", "No se puede iniciar " + profile.version(), var17.getMessage());
         } catch (IOException var18) {
            throw new DaemonException("io", "No se pudo iniciar el juego", var18.getMessage(), null, var18);
         }
      } else {
         throw new DaemonException("bad_request", "La versión no está preparada", "Llama a prepare antes de launch.");
      }
   }

   @Override
   public boolean inputSetupNeeded() {
      if (!InputDetector.requiredHere()) {
         return false;
      } else {
         InputDetector detector = InputDetector.locate(this.repo);
         return detector != null && detector.missingPermission() != null;
      }
   }

   @Override
   public void grantInputPermission() {
      InputDetector detector = InputDetector.locate(this.repo);
      if (detector == null) {
         throw new DaemonException(
            "install_incomplete",
            "Falta un componente de Pulsar",
            "Cierra Pulsar por completo y ábrelo de nuevo. Si sigue pasando, reinstálalo desde pulsarlauncher.com."
         );
      } else {
         String failure = detector.grantPermission();
         if ("cancelled".equals(failure)) {
            throw new DaemonException("cancelled", "Instalación sin terminar", "No se ha cambiado nada.");
         } else if (failure != null) {
            LauncherLog.warn("input · udev rule not installed: " + failure);
            throw new DaemonException("input_permission", "No se pudo terminar la instalación", failure);
         } else {
            String missing = detector.missingPermission();
            if (missing != null) {
               LauncherLog.warn("input · rule installed but pointers still unreadable:\n" + missing);
               throw new DaemonException("input_permission", "La instalación aún no se aplica", "Cierra sesión en tu equipo y vuelve a entrar.");
            } else {
               LauncherLog.info("input · udev rule installed");
            }
         }
      }
   }

   Path gameDirFor(Profile profile) {
      if (profile.gameDir() != null && !profile.gameDir().isBlank()) {
         return Path.of(profile.gameDir()).toAbsolutePath().normalize();
      } else {
         String env = System.getenv("PULSAR_GAME_DIR");
         return env != null && !env.isBlank() ? Path.of(env).toAbsolutePath().normalize() : this.dirs.gameDir();
      }
   }

   private static Command detach(Command command) {
      return new Command(GameDetacher.detach(command.commandLine()), command.workingDir(), command.logFile(), command.environment());
   }

   @Override
   public void cancel() {
      Thread t = this.preparing;
      if (t != null) {
         t.interrupt();
      }
   }

   private static DaemonException offline(Exception e) {
      return new DaemonException("offline", "Sin conexión con los servidores de Mojang", "Revisa tu conexión y reintenta.", e.getMessage(), e);
   }

   private static Path existing(Path p) {
      return Files.isDirectory(p) ? p.toAbsolutePath() : null;
   }

   private Path sessionCopyDir() throws IOException {
      Path base = this.dirs.root().resolve("cache").resolve("sessions");
      Files.createDirectories(base);
      List<Path> old = new ArrayList<>();

      try (Stream<Path> s = Files.list(base)) {
         s.filter(x$0 -> Files.isDirectory(x$0)).sorted().forEach(old::add);
      }

      while (old.size() > 2) {
         deleteTree(old.remove(0));
      }

      return Files.createDirectories(base.resolve(String.valueOf(System.currentTimeMillis())));
   }

   private static void deleteTree(Path dir) {
      try (Stream<Path> s = Files.walk(dir)) {
         s.sorted(Comparator.reverseOrder()).forEach(p -> {
            try {
               Files.deleteIfExists(p);
            } catch (IOException var2) {
            }
         });
      } catch (IOException var6) {
      }
   }

   private static List<Path> copyJars(List<Path> jars, Path into) throws IOException {
      Files.createDirectories(into);
      List<Path> out = new ArrayList<>();

      for (Path jar : jars) {
         Path target = into.resolve(jar.getFileName());
         IOException last = null;

         for (int attempt = 0; attempt < 3; attempt++) {
            long before = Files.size(jar);
            Files.copy(jar, target, StandardCopyOption.REPLACE_EXISTING);
            if (Files.size(jar) == before && Files.size(target) == before) {
               try (ZipFile zip = new ZipFile(target.toFile())) {
                  if (zip.size() > 0) {
                     last = null;
                     break;
                  }
               } catch (IOException var16) {
                  last = var16;
               }
            }

            last = last != null ? last : new IOException("el jar cambió durante la copia: " + jar);

            try {
               Thread.sleep(300L);
            } catch (InterruptedException var14) {
               Thread.currentThread().interrupt();
               break;
            }
         }

         if (last != null) {
            throw new IOException("copia de " + jar.getFileName() + " no verificada: " + last.getMessage(), last);
         }

         out.add(target);
      }

      for (Path jar : out) {
         if (jar.getFileName().toString().startsWith("v26x") || jar.getFileName().toString().startsWith("bootstrap")) {
            long age = (System.currentTimeMillis() - Files.getLastModifiedTime(jar).toMillis()) / 1000L;
            LauncherLog.info(
               "launch · "
                  + jar.getFileName()
                  + " sha256="
                  + sha256(jar).substring(0, 12)
                  + " · construido hace "
                  + (age < 120L ? age + " s" : age / 60L + " min")
            );
         }
      }

      return out;
   }

   private static String sha256(Path file) {
      try {
         MessageDigest md = MessageDigest.getInstance("SHA-256");

         try (InputStream in = Files.newInputStream(file)) {
            byte[] buf = new byte[65536];

            int n;
            while ((n = in.read(buf)) > 0) {
               md.update(buf, 0, n);
            }
         }

         return HexFormat.of().formatHex(md.digest());
      } catch (Exception var7) {
         return "?";
      }
   }

   private static List<Path> jars(Path dir) throws IOException {
      List var2;
      try (Stream<Path> s = Files.list(dir)) {
         var2 = s.filter(f -> f.toString().endsWith(".jar")).map(Path::toAbsolutePath).sorted().toList();
      }

      return var2;
   }
}
