package com.metrofico.pulsar.launcher.launch;

import com.metrofico.pulsar.launcher.PulsarLauncher;
import com.metrofico.pulsar.launcher.install.GameDirectories;
import com.metrofico.pulsar.launcher.install.LibraryResolver;
import com.metrofico.pulsar.launcher.install.ResolvedLibrary;
import com.metrofico.pulsar.launcher.meta.VersionDetail;
import com.metrofico.pulsar.launcher.rules.Platform;
import com.metrofico.pulsar.launcher.runtime.JavaPolicy;
import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public final class GameLauncher {
   private final GameDirectories dirs;

   public GameLauncher(GameDirectories dirs) {
      this.dirs = dirs;
   }

   public GameLauncher.Command buildCommand(
      VersionDetail version, LibraryResolver.Resolution libraries, Platform platform, LaunchSession session, LaunchOptions options
   ) throws IOException {
      requireJava(version, options);
      Path natives = this.dirs.nativesFor(version.id());
      Files.createDirectories(natives);
      Files.createDirectories(this.dirs.gameDir());
      Files.createDirectories(this.dirs.logs());
      List<Path> gameClasspath = new ArrayList<>(options.extraGameClasspath());
      libraries.classpath().stream().map(ResolvedLibrary::target).forEach(gameClasspath::add);
      gameClasspath.add(this.dirs.clientJar(version.id()));
      String gameClasspathString = join(gameClasspath);
      boolean bootstrapped = options.mainClassOverride() != null;
      String classpathString = bootstrapped ? join(options.extraClasspath()) : gameClasspathString;
      Set<String> features = new HashSet<>();
      if (options.hasCustomResolution()) {
         features.add("has_custom_resolution");
      }

      if (options.quickPlayServer() != null) {
         features.add("is_quick_play_multiplayer");
      }

      ArgumentBuilder args = new ArgumentBuilder(platform, features, this.placeholders(version, session, options, natives, classpathString));
      List<String> command = new ArrayList<>();
      command.add(options.javaExecutable().toString());
      command.add("-Xms" + options.maxMemoryMb() + "m");
      command.add("-Xmx" + options.maxMemoryMb() + "m");
      command.addAll(options.extraJvmArgs());
      String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
      if (System.getenv("PULSAR_JFR") != null) {
         command.add("-XX:FlightRecorderOptions=stackdepth=128");
         command.add(
            "-XX:StartFlightRecording=filename="
               + this.dirs.logs().resolve("game-" + version.id() + "-" + stamp + ".jfr")
               + ",settings=profile,dumponexit=true,maxsize=512m"
         );
      }

      if (version.usesLegacyArguments()) {
         command.addAll(JavaPolicy.LEGACY_JVM_OPENS);
      }

      if (bootstrapped) {
         command.add("-Dpulsar.game.classpath=" + gameClasspathString);
         command.add("-Dpulsar.game.main=" + version.mainClass());
         command.add("-Dpulsar.game.version=" + version.id());
         command.add("-Dpulsar.data.dir=" + this.dirs.root().resolve("pulsar"));
         command.add("-Dpulsar.service.url=" + PulsarLauncher.SERVICE_URL);
         command.add("-Dpulsar.client.version=" + PulsarLauncher.VERSION);
         command.add("-Dpulsar.game.sha1=" + version.downloads().client().sha1());
         if (!"1".equals(System.getenv("PULSAR_ALLOW_ATTACH"))) {
            command.add("-XX:+DisableAttachMechanism");
         }

         command.add("-Dpulsar.realtime.url=" + PulsarLauncher.REALTIME_URL);
         Path agent = bootstrapJar(options.extraClasspath());
         if (agent != null && !"0".equals(System.getenv("PULSAR_CLASS_WATCH"))) {
            command.add("-javaagent:" + agent);
         }
      }

      command.addAll(args.jvm(version));
      command.add(options.mainClassOverride() != null ? options.mainClassOverride() : version.mainClass());
      command.addAll(args.game(version));
      if (options.quickPlayServer() != null && version.usesLegacyArguments()) {
         String[] hp = options.quickPlayServer().split(":");
         command.add("--server");
         command.add(hp[0]);
         command.add("--port");
         command.add(hp.length > 1 ? hp[1] : "25565");
      }

      return new GameLauncher.Command(
         List.copyOf(command),
         this.dirs.gameDir(),
         this.dirs.logs().resolve("game-" + version.id() + "-" + stamp + ".log"),
         legacyDisplayEnvironment(version, this.dirs.gameDir())
      );
   }

   static Path bootstrapJar(List<Path> clientJars) {
      for (Path jar : clientJars) {
         String name = jar.getFileName() == null ? "" : jar.getFileName().toString();
         if (name.startsWith("bootstrap") && name.endsWith(".jar")) {
            return jar;
         }
      }

      return null;
   }

   public Process launch(GameLauncher.Command command) throws IOException {
      return builder(command).start();
   }

   public Process launch(GameLauncher.Command command, List<String> detector) throws IOException {
      ProcessBuilder input = new ProcessBuilder(detector).directory(command.workingDir().toFile()).redirectError(Redirect.DISCARD);
      List<Process> pipeline = ProcessBuilder.startPipeline(List.of(input, builder(command)));
      return pipeline.get(1);
   }

   private static ProcessBuilder builder(GameLauncher.Command command) {
      ProcessBuilder builder = new ProcessBuilder(command.commandLine())
         .directory(command.workingDir().toFile())
         .redirectErrorStream(true)
         .redirectOutput(command.logFile().toFile());
      builder.environment().putAll(command.environment());
      return builder;
   }

   static Map<String, String> legacyDisplayEnvironment(VersionDetail version, Path gameDir) {
      if (version.usesLegacyArguments() && System.getProperty("os.name", "").toLowerCase(Locale.ROOT).contains("linux")) {
         try {
            Path options = gameDir.resolve("options.txt");
            if (Files.isRegularFile(options) && Files.readAllLines(options).stream().anyMatch(l -> l.trim().equals("enableVsync:true"))) {
               return Map.of();
            }
         } catch (IOException var3) {
         }

         Map<String, String> env = new LinkedHashMap<>();
         if (System.getenv("vblank_mode") == null) {
            env.put("vblank_mode", "0");
         }

         if (System.getenv("__GL_SYNC_TO_VBLANK") == null) {
            env.put("__GL_SYNC_TO_VBLANK", "0");
         }

         return env;
      } else {
         return Map.of();
      }
   }

   private static String join(List<Path> paths) {
      return paths.stream().map(p -> p.toAbsolutePath().toString()).collect(Collectors.joining(File.pathSeparator));
   }

   private Map<String, String> placeholders(VersionDetail version, LaunchSession session, LaunchOptions options, Path natives, String classpath) {
      Map<String, String> v = new HashMap<>();
      v.put("auth_player_name", session.playerName());
      v.put("auth_uuid", session.uuid().toString().replace("-", ""));
      v.put("auth_access_token", session.accessToken());
      v.put("auth_session", session.accessToken());
      v.put("auth_xuid", session.xuid());
      v.put("clientid", session.clientId());
      v.put("user_type", session.userType());
      v.put("user_properties", "{}");
      v.put("version_name", version.id());
      v.put("version_type", version.type() == null ? "release" : version.type());
      v.put("game_directory", this.dirs.gameDir().toString());
      v.put("assets_root", this.dirs.assets().toString());
      v.put("game_assets", this.dirs.assets().toString());
      v.put("assets_index_name", version.assetIndex().id());
      v.put("natives_directory", natives.toString());
      v.put("library_directory", this.dirs.libraries().toString());
      v.put("classpath", classpath);
      v.put("classpath_separator", File.pathSeparator);
      v.put("launcher_name", "Pulsar Launcher");
      v.put("launcher_version", PulsarLauncher.VERSION);
      v.put("resolution_width", options.width() == null ? "" : options.width().toString());
      v.put("resolution_height", options.height() == null ? "" : options.height().toString());
      v.put("quickPlayPath", "");
      v.put("quickPlaySingleplayer", "");
      v.put("quickPlayMultiplayer", options.quickPlayServer() == null ? "" : options.quickPlayServer());
      v.put("quickPlayRealms", "");
      return v;
   }

   private static void requireJava(VersionDetail version, LaunchOptions options) {
      if (version.javaVersion() != null && options.javaExecutable().equals(LaunchOptions.currentJava())) {
         int current = Runtime.version().feature();
         if (current < version.javaVersion().majorVersion()) {
            throw new IllegalStateException(
               "Minecraft " + version.id() + " requires Java " + version.javaVersion().majorVersion() + " but the launcher is running on Java " + current
            );
         }
      }
   }

   public record Command(List<String> commandLine, Path workingDir, Path logFile, Map<String, String> environment) {
      public Command(List<String> commandLine, Path workingDir, Path logFile) {
         this(commandLine, workingDir, logFile, Map.of());
      }
   }
}
