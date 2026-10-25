package com.metrofico.pulsar.launcher.daemon;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.metrofico.pulsar.launcher.PulsarLauncher;
import com.metrofico.pulsar.launcher.launch.LaunchOptions;
import com.metrofico.pulsar.launcher.update.Semver;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public final class Commands {
   private final CoreService core;
   private final ProfilesStore profiles;
   private final PreferencesStore preferences;
   private final Consumer<String> events;
   private final Path dataDir;
   private final PacksStore packs;
   private final AuthService auth;
   private final UpdateService updates;
   private final PushService push;
   private final EmoteService emotes;
   private final LegitService legit;
   private final MetricsService metrics;
   private volatile GameSession session = GameSession.idle();
   private final DiscordPresence discord;
   private volatile boolean busy;

   public Commands(CoreService core, Path dataDir, Consumer<String> events) {
      this.core = core;
      this.profiles = new ProfilesStore(dataDir);
      if (Semver.isValidPublic(PulsarLauncher.VERSION)) {
         this.profiles.retarget(EngineDirs::supportedFor);
      }

      this.preferences = new PreferencesStore(dataDir);
      this.events = events;
      this.dataDir = dataDir;
      this.packs = new PacksStore(dataDir);
      this.auth = new AuthService(core.microsoftAuth(), core::microsoftAuth, core.secretStore(), new AccountStore(dataDir), events);
      this.updates = core.updateService(dataDir.resolve("updates"), events);
      this.push = new PushService(PulsarLauncher.SERVICE_URL, core.secretStore(), this.preferences, this.packs, this.auth, events, dataDir);
      this.emotes = new EmoteService(PulsarLauncher.SERVICE_URL, this.push::token, () -> "running".equals(this.session.state()), events, dataDir, null);
      this.push.onSession(this.emotes::refreshAsync);
      this.legit = new LegitService(PulsarLauncher.SERVICE_URL, this.push::token, () -> "running".equals(this.session.state()), dataDir, null);
      this.metrics = new MetricsService(PulsarLauncher.SERVICE_URL, this.push::token, dataDir, null);
      this.discord = new DiscordPresence(
         () -> this.preferences.current().discordPresenceOn(),
         () -> this.preferences.current().language(),
         () -> this.auth.account().map(a -> new String[]{a.uuid(), a.name()}).orElse(null),
         PulsarLauncher.SERVICE_URL
      );
   }

   private <T> T discordSettingChanged(T saved) {
      this.discord.settingChanged();
      return saved;
   }

   public void startPresence() {
      new RealtimePresence(PulsarLauncher.REALTIME_URL, PulsarLauncher.VERSION, () -> this.preferences.current().metricsOptInOn() ? this.push.token() : null)
         .start();
      this.discord.start();
   }

   public void checkUpdatesAsync() {
      Thread t = new Thread(() -> {
         this.updates.cleanup();
         this.updates.check(this.preferences.current().channel());
         this.updates.schedulePeriodicChecks(() -> this.preferences.current().channel(), 1800000L);
      }, "pulsar-update-check");
      t.setDaemon(true);
      t.start();
   }

   private void restoreSelectedAsync() {
      Thread t = new Thread(() -> {
         this.auth.restore();
         this.events.accept(Protocol.event("auth", Map.of("state", "restored", "current", this.auth.current())));
      }, "pulsar-auth-select");
      t.setDaemon(true);
      t.start();
   }

   public void restoreSessionAsync() {
      Thread t = new Thread(() -> {
         this.auth.restore();
         this.events.accept(Protocol.event("auth", Map.of("state", "restored", "current", this.auth.current())));
         this.push.start();
      }, "pulsar-auth-restore");
      t.setDaemon(true);
      t.start();
   }

   public Object handle(Protocol.Request request) {
      String var2 = request.cmd();

      return switch (var2) {
         case "hello" -> Map.of(
            "brand",
            "Pulsar Launcher",
            "version",
            PulsarLauncher.VERSION,
            "protocol",
            1,
            "java",
            Runtime.version().feature(),
            "defaultMemoryMb",
            LaunchOptions.defaultMemoryMb(),
            "dataDir",
            this.dataDir.toAbsolutePath().toString(),
            "logsDir",
            this.core.logsDir().toAbsolutePath().toString(),
            "serviceUrl",
            PulsarLauncher.SERVICE_URL,
            "offlinePlayAllowed",
            this.core.offlinePlayAllowed(),
            "runtime",
            this.core.runtimes().stream().filter(rx -> "bundled".equals(rx.source())).findFirst().orElse(CoreService.JavaInfo.unknown())
         );
         case "java.list" -> this.core.runtimes();
         case "logs.open" -> this.openLog(request.stringOr("path", null));
         case "report.create" -> this.createReport();
         case "auth.begin" -> this.auth.begin();
         case "auth.window.begin" -> this.auth.beginWindow();
         case "auth.window.complete" -> this.auth.completeWindow(request.string("url"));
         case "auth.cancel" -> {
            this.auth.cancel();
            yield Map.of("cancelled", true);
         }
         case "auth.logout" -> {
            this.auth.logout();
            this.restoreSelectedAsync();
            yield this.auth.current();
         }
         case "auth.select" -> {
            if (!this.auth.selectAccount(request.string("uuid"))) {
               throw new DaemonException("bad_request", "Cuenta desconocida", "Esa cuenta no está en la lista.");
            }

            this.restoreSelectedAsync();
            yield this.auth.current();
         }
         case "auth.remove" -> {
            this.auth.removeAccount(request.string("uuid"));
            this.restoreSelectedAsync();
            yield this.auth.current();
         }
         case "auth.current" -> this.auth.current();
         case "url.open" -> {
            String url = request.string("url");
            if (!url.startsWith("https://")) {
               throw new DaemonException("bad_request", "Enlace no permitido", "Solo se abren enlaces https: " + url);
            }

            this.core.openUrl(url);
            yield Map.of("opened", url);
         }
         case "data.open" -> {
            this.core.open(this.dataDir.toAbsolutePath());
            yield Map.of("opened", this.dataDir.toAbsolutePath().toString());
         }
         case "versions" -> this.core.versions();
         case "profiles.list" -> Map.of(
            "profiles", this.profiles.all().stream().map(this::withJava).toList(), "selectedId", this.profiles.selected().map(Profile::id).orElse("")
         );
         case "profiles.save" -> this.saveProfile(request);
         case "profiles.delete" -> {
            this.profiles.delete(request.string("id"));
            yield Map.of("deleted", request.string("id"));
         }
         case "profiles.select" -> {
            this.profiles.select(request.string("id"));
            yield Map.of("selectedId", request.string("id"));
         }
         case "preferences.get" -> this.preferences.current();
         case "preferences.set" -> (Preferences)this.discordSettingChanged(
            this.preferences
               .update(
                  p -> new Preferences(
                     request.stringOr("language", p.language()),
                     request.stringOr("theme", p.theme()),
                     bool(request, "minimizeWhilePlaying", p.minimizeWhilePlaying()),
                     bool(request, "sendErrorReports", p.sendErrorReports()),
                     request.stringOr("playerName", p.playerName()),
                     bool(request, "onboarded", p.onboarded()),
                     request.stringOr("updateChannel", p.channel()),
                     bool(request, "autoUpdate", p.autoUpdateOn()),
                     p.pushEnabled(),
                     p.pushMuted(),
                     bool(request, "closeToTray", p.closeToTrayOn()),
                     bool(request, "minimizeToTray", p.minimizeToTrayOn()),
                     bool(request, "gamePriority", p.gamePriorityOn()),
                     request.stringOr("gc", p.gcMode()),
                     request.args().has("metricsOptIn") && !request.args().get("metricsOptIn").isJsonNull()
                        ? bool(request, "metricsOptIn", false)
                        : p.metricsOptIn(),
                     request.stringOr("changelogSeen", p.changelogSeen()),
                     bool(request, "discordPresence", p.discordPresenceOn())
                  )
               )
         );
         case "push.state" -> this.push.state();
         case "push.set" -> this.push
            .set(
               request.args().has("enabled") ? bool(request, "enabled", true) : null,
               request.stringOr("server", null),
               request.args().has("muted") ? bool(request, "muted", false) : null
            );
         case "push.dismiss" -> {
            this.push.dismissAll();
            yield this.push.state();
         }
         case "metrics.status" -> this.metricsStatus(this.preferences.current(), request);
         case "metrics.sendHw" -> this.metricsSendHw(request);
         case "metrics.sendSession" -> this.metricsSendSession(request);
         case "metrics.resetId" -> Map.of("installId", this.metrics.resetInstallId());
         case "metrics.forget" -> Map.of("removed", this.metrics.forget());
         case "legit.status" -> this.legit.status();
         case "legit.mark" -> this.legit.mark();
         case "legit.appeal" -> this.legit.appeal(request.string("caseId"), request.string("text"));
         case "emotes.state" -> this.emotes.state();
         case "emotes.refresh" -> this.emotes.refresh();
         case "emotes.claim" -> this.emotes.claim(request.string("id"));
         case "emotes.options" -> this.emotes.options();
         case "emotes.setOptions" -> {
            List<String> wheel = null;
            if (request.args().has("wheel") && request.args().get("wheel").isJsonArray()) {
               wheel = new ArrayList<>();

               for (JsonElement e : request.args().getAsJsonArray("wheel")) {
                  wheel.add(e.isJsonPrimitive() ? e.getAsString() : "");
               }
            }

            yield this.emotes
               .setOptions(
                  request.args().has("showMine") ? bool(request, "showMine", true) : null,
                  request.args().has("seeOthers") ? bool(request, "seeOthers", true) : null,
                  wheel
               );
         }
         case "update.check" -> this.updates.check(this.preferences.current().channel());
         case "update.download" -> this.updates.startDownload();
         case "update.retry" -> this.updates.retry();
         case "update.verify" -> this.updates.verifyInstall(this.preferences.current().channel());
         case "update.apply" -> {
            boolean running = "running".equals(this.session.state());
            boolean relaunching = this.updates.applyOrWait(running);
            yield Map.of("relaunching", relaunching, "waiting", running);
         }
         case "update.state" -> this.updates.state();
         case "packs.list" -> this.packs.list();
         case "packs.revoke" -> {
            Object r = this.packs.revoke(request.string("serverId"));
            this.push.syncSubscriptions();
            yield r;
         }
         case "packs.clearCache" -> this.packs.clearCache();
         case "prepare" -> this.prepare(request, request.string("version"), request.stringOr("javaPath", null));
         case "launch" -> this.launch(request);
         case "input.grant" -> {
            this.core.grantInputPermission();
            yield Map.of("granted", true);
         }
         case "input.status" -> Map.of("setupNeeded", this.core.inputSetupNeeded());
         case "cancel" -> {
            this.core.cancel();
            this.busy = false;
            yield Map.of("cancelled", true);
         }
         case "game.status" -> this.session;
         case "shutdown" -> Map.of("bye", true);
         default -> throw new DaemonException("bad_request", "Comando desconocido", "No existe el comando \"" + request.cmd() + "\"");
      };
   }

   private Object saveProfile(Protocol.Request r) {
      String id = r.stringOr("id", null);
      Profile existing = id == null ? null : this.profiles.all().stream().filter(p -> p.id().equals(id)).findFirst().orElse(null);
      Profile base = existing != null ? existing : Profile.create(r.string("name"), r.string("version"));
      Integer memory = !r.args().has("memoryMb") ? base.memoryMb() : (r.args().get("memoryMb").isJsonNull() ? null : r.args().get("memoryMb").getAsInt());
      String javaPath = !r.args().has("javaPath") ? base.javaPath() : (r.args().get("javaPath").isJsonNull() ? null : r.args().get("javaPath").getAsString());
      String gameDir = !r.args().has("gameDir")
         ? base.gameDir()
         : (!r.args().get("gameDir").isJsonNull() && !r.args().get("gameDir").getAsString().isBlank() ? r.args().get("gameDir").getAsString().strip() : null);
      String name = r.stringOr("name", base.name()).strip();
      if (!name.isEmpty() && name.length() <= 32) {
         Profile saved = new Profile(base.id(), name, r.stringOr("version", base.version()), memory, javaPath, base.lastPlayedEpochMs(), gameDir);
         return this.profiles.save(saved);
      } else {
         throw new DaemonException("bad_request", "Nombre no válido", "Escribe un nombre de 1 a 32 caracteres.");
      }
   }

   private Object createReport() {
      Preferences prefs = this.preferences.current();
      Map<String, Object> system = new LinkedHashMap<>();
      system.put("launcher", "Pulsar Launcher " + PulsarLauncher.VERSION);
      system.put("os", System.getProperty("os.name") + " " + System.getProperty("os.version") + " " + System.getProperty("os.arch"));
      system.put("daemonJava", Runtime.version().toString());
      system.put("runtimes", this.core.runtimes());
      system.put("session", this.session);
      List<Map<String, Object>> profileRows = this.profiles.all().stream().map(p -> {
         Map<String, Object> m = new LinkedHashMap<>();
         m.put("version", p.version());
         m.put("memoryMb", p.memoryMb());
         m.put("customJava", p.javaPath() != null);
         m.put("java", this.core.javaFor(p.version(), p.javaPath()));
         return m;
      }).toList();
      Map<String, Object> prefRows = Map.of(
         "language",
         prefs.language(),
         "theme",
         prefs.theme(),
         "minimizeWhilePlaying",
         prefs.minimizeWhilePlaying(),
         "sendErrorReports",
         prefs.sendErrorReports()
      );

      try {
         DiagnosticReport report = new DiagnosticReport(this.dataDir.resolve("reports"), this.core.logsDir(), this.core.crashReportsDir(), prefs.playerName());
         DiagnosticReport.Created created = report.create(system, profileRows, prefRows);
         LauncherLog.info("report created · " + created.entries());
         this.core.open(Path.of(created.dir()));
         return created;
      } catch (IOException var7) {
         throw new DaemonException("io", "No se pudo crear el paquete de diagnóstico", var7.getMessage(), null, var7);
      }
   }

   private String newestCrashReportSince(long sinceEpochMs) {
      Path dir = this.core.crashReportsDir();
      if (!Files.isDirectory(dir)) {
         return null;
      } else {
         try {
            String var5;
            try (Stream<Path> s = Files.list(dir)) {
               var5 = s.filter(p -> p.getFileName().toString().endsWith(".txt"))
                  .filter(p -> p.toFile().lastModified() >= sinceEpochMs - 1000L)
                  .max(Comparator.comparingLong(p -> p.toFile().lastModified()))
                  .map(p -> p.toAbsolutePath().toString())
                  .orElse(null);
            }

            return var5;
         } catch (IOException var9) {
            return null;
         }
      }
   }

   private Object openLog(String path) {
      Path logs = this.core.logsDir().toAbsolutePath().normalize();
      Path crashes = this.core.crashReportsDir().toAbsolutePath().normalize();
      Path target = path == null ? logs : Path.of(path).toAbsolutePath().normalize();
      if (!target.startsWith(logs) && !target.startsWith(crashes)) {
         throw new DaemonException("bad_request", "Ruta no permitida", "Solo se pueden abrir archivos de registros o crash reports: " + logs);
      } else {
         this.core.open(target);
         return Map.of("opened", target.toString());
      }
   }

   private JsonObject withJava(Profile p) {
      JsonObject o = Protocol.GSON.toJsonTree(p).getAsJsonObject();
      o.add("java", Protocol.GSON.toJsonTree(this.core.javaFor(p.version(), p.javaPath())));
      return o;
   }

   private Object prepare(Protocol.Request r, String version, String javaPath) {
      if (this.busy) {
         throw new DaemonException("busy", "Ya hay una preparación en curso", "Espera a que termine o cancélala.");
      } else {
         this.busy = true;

         Map var4;
         try {
            this.core.prepare(version, javaPath, new Commands.ProgressThrottle(p -> this.events.accept(Protocol.event("progress", p.toEvent(r.id())))));
            var4 = Map.of("prepared", version);
         } finally {
            this.busy = false;
         }

         return var4;
      }
   }

   private Object launch(Protocol.Request r) {
      Profile profile = r.args().has("profileId")
         ? this.profiles
            .all()
            .stream()
            .filter(p -> p.id().equals(r.string("profileId")))
            .findFirst()
            .orElseThrow(() -> new DaemonException("bad_request", "Perfil desconocido", "No existe el perfil " + r.string("profileId")))
         : this.profiles
            .selected()
            .orElseThrow(() -> new DaemonException("no_profile", "Aún no tienes perfiles", "Crea el primero para elegir tu versión de Minecraft."));
      if ("running".equals(this.session.state())) {
         throw new DaemonException("busy", "El juego ya está abierto", "Cierra el juego antes de abrir otro.");
      } else {
         if (this.updates.offline()) {
            this.updates.check(this.preferences.current().channel());
         }

         if (this.updates.requiredPending()) {
            throw new DaemonException("update_required", "Actualización obligatoria", "Instala la versión nueva de Pulsar para seguir jugando.");
         } else {
            Identity identity = this.auth.playIdentity(this.preferences.current().playerName(), this.core.offlinePlayAllowed());
            this.prepare(r, profile.version(), profile.javaPath());
            long startedAt = System.currentTimeMillis();
            LauncherLog.info("launch · " + (identity.online() ? "cuenta Microsoft" : "sesión local"));
            String server = r.stringOr("server", null);
            if (server != null && !server.matches("[A-Za-z0-9.-]{1,253}(:\\d{1,5})?")) {
               throw new DaemonException("bad_request", "Dirección no válida", server);
            } else {
               CoreService.Launched launched = this.core.launch(profile, this.preferences.current(), identity, server, code -> {
                  String crashReport = this.newestCrashReportSince(startedAt);
                  this.session = this.session.exited(code, crashReport);
                  LauncherLog.info("game exited · code " + code + (crashReport != null ? " · crash report " + Path.of(crashReport).getFileName() : ""));
                  this.events.accept(Protocol.event("game", this.session));
                  this.discord.inLauncher();
                  this.updates.onGameExited(this.preferences.current().autoUpdateOn());
                  this.push.syncSubscriptions();
                  this.sendMetricsSessionIfAny();
               });
               this.profiles.save(profile.played(System.currentTimeMillis()));
               this.session = GameSession.started(launched.pid(), launched.logFile(), profile.id());
               this.events.accept(Protocol.event("game", this.session));
               this.discord.playing(profile.version());
               return this.session;
            }
         }
      }
   }

   private Map<String, Object> metricsStatus(Preferences p, Protocol.Request request) {
      Map<String, Object> out = new LinkedHashMap<>();
      out.put("installId", this.metrics.installId());
      out.put("optIn", p.metricsOptIn());
      if (request.args().has("screen") && request.args().get("screen").isJsonObject()) {
         JsonObject sc = request.args().getAsJsonObject("screen");
         out.put(
            "exampleHw",
            this.metrics
               .buildHw(
                  PulsarLauncher.VERSION,
                  sc.get("w").getAsInt(),
                  sc.get("h").getAsInt(),
                  sc.has("hz") ? sc.get("hz").getAsDouble() : null,
                  request.stringOr("locale", Locale.getDefault().toLanguageTag())
               )
         );
      }

      return out;
   }

   private Map<String, Object> metricsSendHw(Protocol.Request request) {
      if (!this.preferences.current().metricsOptInOn()) {
         throw new DaemonException("declined", "Métricas desactivadas", "Actívalas en Ajustes · Privacidad");
      } else {
         long now = System.currentTimeMillis();
         if (bool(request, "ifDue", false) && !this.metrics.hwDue(PulsarLauncher.VERSION, now)) {
            return Map.of("skipped", true);
         } else {
            JsonObject screen = request.args().getAsJsonObject("screen");
            Map<String, Object> hw = this.metrics
               .buildHw(
                  PulsarLauncher.VERSION,
                  screen.get("w").getAsInt(),
                  screen.get("h").getAsInt(),
                  screen.has("hz") && !screen.get("hz").isJsonNull() ? screen.get("hz").getAsDouble() : null,
                  request.stringOr("locale", Locale.getDefault().toLanguageTag())
               );
            this.metrics.send(hw);
            this.metrics.markHwSent(PulsarLauncher.VERSION, now);
            return Map.of("sentAt", hw.get("sentAt"));
         }
      }
   }

   private Map<String, Object> metricsSendSession(Protocol.Request request) {
      if (!this.preferences.current().metricsOptInOn()) {
         return Map.of("skipped", true);
      } else {
         Map<String, Object> session = this.metrics.buildSession(PulsarLauncher.VERSION, MetricsService.fromJson(request.args().getAsJsonObject("session")));
         this.metrics.send(session);
         return Map.of("sentAt", session.get("sentAt"));
      }
   }

   private void sendMetricsSessionIfAny() {
      Path file = this.dataDir.resolve("metrics-session.json");
      if (Files.isRegularFile(file)) {
         try {
            if (this.preferences.current().metricsOptInOn()) {
               JsonObject game = JsonParser.parseString(Files.readString(file)).getAsJsonObject();
               this.metrics.send(this.metrics.buildSession(PulsarLauncher.VERSION, MetricsService.fromJson(game)));
            }
         } catch (IOException | RuntimeException var11) {
            LauncherLog.info("metrics · session no enviada (" + var11.getMessage() + ")");
         } finally {
            try {
               Files.deleteIfExists(file);
            } catch (IOException var10) {
            }
         }
      }
   }

   private static boolean bool(Protocol.Request r, String key, boolean fallback) {
      return r.args().has(key) && !r.args().get(key).isJsonNull() ? r.args().get(key).getAsBoolean() : fallback;
   }

   public List<Profile> profiles() {
      return this.profiles.all();
   }

   static final class ProgressThrottle implements Consumer<PrepareProgress> {
      private final Consumer<PrepareProgress> sink;
      private PrepareProgress.Stage lastStage;
      private int lastPercent = -1;
      private long lastEmitNanos;

      ProgressThrottle(Consumer<PrepareProgress> sink) {
         this.sink = sink;
      }

      public synchronized void accept(PrepareProgress p) {
         boolean stageChanged = p.stage() != this.lastStage;
         boolean finished = p.total() > 0 && p.done() >= p.total();
         boolean percentChanged = p.percent() != this.lastPercent;
         long now = System.nanoTime();
         if (stageChanged || finished || percentChanged && now - this.lastEmitNanos > 100000000L) {
            this.lastStage = p.stage();
            this.lastPercent = p.percent();
            this.lastEmitNanos = now;
            this.sink.accept(p);
         }
      }
   }
}
