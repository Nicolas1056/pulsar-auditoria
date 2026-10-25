package com.metrofico.pulsar.launcher.daemon;

import com.metrofico.pulsar.launcher.PulsarLauncher;
import com.metrofico.pulsar.launcher.download.ResumableDownloader;
import com.metrofico.pulsar.launcher.download.VerifiedDownloader;
import com.metrofico.pulsar.launcher.runtime.ArchiveExtractor;
import com.metrofico.pulsar.launcher.update.InstallTree;
import com.metrofico.pulsar.launcher.update.ReleaseManifest;
import com.metrofico.pulsar.launcher.update.Semver;
import com.metrofico.pulsar.launcher.update.TreeUpdater;
import com.metrofico.pulsar.launcher.update.UpdatePlan;
import com.metrofico.pulsar.launcher.update.InstallTree.Entry;
import com.metrofico.pulsar.launcher.update.ReleaseManifest.Package;
import com.metrofico.pulsar.launcher.update.TreeUpdater.Plan;
import com.metrofico.pulsar.launcher.update.UpdatePlan.Apply;
import com.metrofico.pulsar.launcher.update.UpdatePlan.Installation;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.channels.UnresolvedAddressException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermission;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class UpdateService {
   private final HttpClient http;
   private final VerifiedDownloader downloader;
   private final String serviceUrl;
   private final Supplier<PublicKey> publicKey;
   private final Installation installation;
   private final Path downloadsDir;
   private final UpdateService.Relauncher relauncher;
   private final Consumer<String> events;
   private final Consumer<Path> opener;
   private volatile ReleaseManifest latest;
   private volatile UpdatePlan plan;
   private volatile Path downloaded;
   private final ResumableDownloader resumable;
   private final ExecutorService worker = Executors.newSingleThreadExecutor(r -> {
      Thread t = new Thread(r, "pulsar-update");
      t.setDaemon(true);
      return t;
   });
   private ScheduledExecutorService periodic;
   private Future<?> task;
   private volatile String phase = "idle";
   private volatile double fraction;
   private volatile long bytes;
   private volatile int attempt;
   private volatile long nextRetryAt;
   private volatile String lastError;
   private volatile boolean offline;
   private volatile boolean retryNow;
   private volatile boolean applyWhenGameCloses;
   private volatile long lastProgressEvent;
   private long backoffBaseMs = 1000L;
   private long backoffMaxMs = 60000L;
   private int maxAttempts = Integer.MAX_VALUE;
   private boolean watchdogEnabled = true;
   private volatile InstallTree tree;
   private volatile Plan treePlan;
   private volatile Path treeNewDir;

   public UpdateService(
      HttpClient http,
      VerifiedDownloader downloader,
      String serviceUrl,
      Supplier<PublicKey> publicKey,
      Installation installation,
      Path downloadsDir,
      UpdateService.Relauncher relauncher,
      Consumer<Path> opener,
      Consumer<String> events
   ) {
      this.http = http;
      this.downloader = downloader;
      this.serviceUrl = serviceUrl;
      this.publicKey = publicKey;
      this.installation = installation;
      this.downloadsDir = downloadsDir;
      this.relauncher = relauncher;
      this.opener = opener;
      this.events = events;
      this.resumable = new ResumableDownloader(http);
   }

   public UpdateService withWatchdog(boolean enabled) {
      this.watchdogEnabled = enabled;
      return this;
   }

   public UpdateService withRetryPolicy(long baseMs, long maxMs, int maxAttempts) {
      this.backoffBaseMs = baseMs;
      this.backoffMaxMs = maxMs;
      this.maxAttempts = maxAttempts;
      return this;
   }

   public static Installation currentInstallation() {
      String exe = System.getenv("PULSAR_EXECUTABLE");
      return Installation.detect(
         System.getProperty("os.name", ""),
         System.getProperty("os.arch", ""),
         System.getenv("PULSAR_INSTALL_FORMAT"),
         System.getenv("APPIMAGE"),
         exe != null && !exe.isBlank() ? Path.of(exe) : null
      );
   }

   public UpdateService.Status check(String channel) {
      String current = PulsarLauncher.VERSION;
      if ("msix".equals(this.installation.format())) {
         return new UpdateService.Status(current, null, false, false, false, "", channel, null, null, "store_managed");
      } else if (this.serviceUrl != null && !this.serviceUrl.isBlank()) {
         try {
            URI uri = URI.create(
               this.serviceUrl
                  + "/api/v1/releases.json?channel="
                  + channel
                  + "&os="
                  + this.installation.os()
                  + "&arch="
                  + this.installation.arch()
                  + "&v="
                  + current
            );
            HttpResponse<String> r = this.http.send(HttpRequest.newBuilder(uri).header("Accept", "application/json").GET().build(), BodyHandlers.ofString());
            this.offline = false;
            if (r.statusCode() != 200) {
               return new UpdateService.Status(current, null, false, false, false, "", channel, null, null, "http_" + r.statusCode());
            } else {
               ReleaseManifest m = ReleaseManifest.parse(r.body(), this.publicKey.get());
               if (!m.signed()) {
                  LauncherLog.warn("update check · manifest not signed or bad signature");
                  return new UpdateService.Status(current, m.version(), false, false, false, "", channel, null, null, "update_unsigned");
               } else {
                  Optional<UpdatePlan> p = UpdatePlan.plan(m, this.installation);
                  boolean newer = Semver.isValidPublic(current) && Semver.parse(m.version()).newerThan(Semver.parse(current));
                  boolean required = newer && m.minVersion() != null && Semver.parse(m.minVersion()).newerThan(Semver.parse(current));
                  this.latest = m;
                  this.plan = newer ? p.orElse(null) : null;
                  this.tree = null;
                  this.treePlan = null;
                  if (newer && p.isPresent() && treeCapable(p.get())) {
                     this.loadTree(m.version(), channel, p.get().installDir());
                  }

                  Plan tp = this.treePlan;
                  UpdateService.Status s = new UpdateService.Status(
                     current,
                     m.version(),
                     newer && p.isPresent(),
                     required,
                     true,
                     m.notes(),
                     m.channel(),
                     tp != null ? "tree" : p.<String>map(x -> x.pkg().format()).orElse(null),
                     tp != null ? tp.bytesToDownload() : p.<Long>map(x -> x.pkg().size()).orElse(null),
                     newer && p.isEmpty() ? "no_package" : null
                  );
                  if (s.available()) {
                     LauncherLog.info("update available · " + m.version() + " (" + s.format() + ")");
                     if (this.downloaded != null && "ready".equals(this.phase)) {
                        this.emit("ready");
                     } else if (!"downloading".equals(this.phase) && !"retrying".equals(this.phase) && !"waiting_game".equals(this.phase)) {
                        this.phase = "available";
                        this.events.accept(Protocol.event("update", Map.of("state", "available", "status", s)));
                     }

                     if (s.required() && this.downloaded == null) {
                        this.startDownload();
                     }
                  } else if ("available".equals(this.phase)) {
                     this.phase = "idle";
                  }

                  return s;
               }
            }
         } catch (IOException var11) {
            this.offline = true;
            return new UpdateService.Status(current, null, false, false, false, "", channel, null, null, "offline");
         } catch (InterruptedException var12) {
            Thread.currentThread().interrupt();
            return new UpdateService.Status(current, null, false, false, false, "", channel, null, null, "cancelled");
         } catch (RuntimeException var13) {
            LauncherLog.error("update check failed", var13);
            return new UpdateService.Status(current, null, false, false, false, "", channel, null, null, "bad_manifest");
         }
      } else {
         return new UpdateService.Status(current, null, false, false, false, "", channel, null, null, "no_service");
      }
   }

   public synchronized Map<String, Object> startDownload() {
      UpdatePlan p = this.plan;
      if (p == null) {
         throw new DaemonException("no_update", "No hay actualización pendiente", "Busca actualizaciones primero.");
      } else if (this.downloaded != null && Files.exists(this.downloaded)) {
         this.phase = "ready";
         return this.state();
      } else if (this.task != null && !this.task.isDone()) {
         return this.state();
      } else {
         this.attempt = 0;
         this.fraction = 0.0;
         this.bytes = 0L;
         this.lastError = null;
         this.phase = "downloading";
         this.emit("downloading");
         this.task = this.worker.submit(() -> this.downloadLoop(p));
         return this.state();
      }
   }

   public Map<String, Object> retry() {
      if (this.task != null && !this.task.isDone()) {
         this.retryNow = true;
         return this.state();
      } else {
         return this.plan == null ? this.state() : this.startDownload();
      }
   }

   public Path download() {
      this.startDownload();
      Future<?> t = this.task;

      try {
         if (t != null) {
            t.get();
         }
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         throw new DaemonException("cancelled", "Descarga cancelada", "");
      } catch (ExecutionException var4) {
         throw new DaemonException("update_download_failed", "No se pudo descargar la actualización", String.valueOf(var4.getCause()), null, var4.getCause());
      }

      Path d = this.downloaded;
      if (d == null) {
         throw new DaemonException(
            "update_download_failed",
            "No se pudo descargar la actualización",
            this.lastError == null ? "La descarga no pasó la verificación o se interrumpió." : this.lastError
         );
      } else {
         return d;
      }
   }

   private void downloadLoop(UpdatePlan p) {
      String name = p.pkg().url().substring(p.pkg().url().lastIndexOf(47) + 1);
      Path target = this.downloadsDir.resolve(name);
      String url = p.pkg().url().startsWith("http") ? p.pkg().url() : this.serviceUrl + "/downloads/" + p.pkg().url();
      long size = p.pkg().size();

      while (true) {
         long before = this.bytes;

         try {
            Files.createDirectories(this.downloadsDir);
            if (this.tree != null && this.treePlan != null) {
               this.fetchTree(p);
            } else {
               this.resumable.download(url, target, p.pkg().sha256(), size, (b, total) -> this.progress(b, total));
               this.downloaded = target;
            }

            this.fraction = 1.0;
            this.phase = "ready";
            LauncherLog.info("update downloaded · " + name);
            this.emit("ready");
            return;
         } catch (IOException var14) {
            this.lastError = var14.getMessage();
            this.offline = isConnectivity(var14);
            if (this.bytes - before >= 1048576L) {
               this.attempt = 0;
            }

            this.attempt++;
            LauncherLog.warn("update download failed (intento " + this.attempt + ") · " + var14.getMessage());
            if (this.attempt >= this.maxAttempts) {
               this.phase = "error";
               this.emit("error");
               return;
            }

            long wait = Math.min(this.backoffMaxMs, this.backoffBaseMs * (1L << Math.min(20, this.attempt - 1)));
            this.nextRetryAt = System.currentTimeMillis() + wait;
            this.phase = "retrying";
            this.emit("retrying");

            try {
               while (System.currentTimeMillis() < this.nextRetryAt && !this.retryNow) {
                  Thread.sleep(Math.min(100L, Math.max(1L, this.nextRetryAt - System.currentTimeMillis())));
               }
            } catch (InterruptedException var13) {
               Thread.currentThread().interrupt();
               this.phase = "idle";
               return;
            }

            this.retryNow = false;
            this.phase = "downloading";
            this.emit("downloading");
         } catch (InterruptedException var15) {
            Thread.currentThread().interrupt();
            this.phase = "idle";
            return;
         }
      }
   }

   private void progress(long b, long total) {
      this.bytes = b;
      this.fraction = total > 0L ? Math.min(1.0, (double)b / total) : 0.0;
      long now = System.currentTimeMillis();
      if (now - this.lastProgressEvent >= 250L) {
         this.lastProgressEvent = now;
         this.emit("downloading");
      }
   }

   private static boolean treeCapable(UpdatePlan p) {
      return p.installDir() != null && Files.isDirectory(p.installDir()) && Files.isWritable(p.installDir())
         ? p.apply() == Apply.SWAP_DIRECTORY || p.apply() == Apply.MOUNT_AND_COPY || p.installation().os().equals("windows")
         : false;
   }

   private void loadTree(String version, String channel, Path installDir) {
      try {
         URI uri = URI.create(
            this.serviceUrl + "/api/v1/tree?channel=" + channel + "&os=" + this.installation.os() + "&arch=" + this.installation.arch() + "&version=" + version
         );
         HttpResponse<String> r = this.http.send(HttpRequest.newBuilder(uri).header("Accept", "application/json").GET().build(), BodyHandlers.ofString());
         if (r.statusCode() != 200) {
            LauncherLog.info("update tree · no disponible (" + r.statusCode() + "): se usará el paquete completo");
            return;
         }

         InstallTree t = InstallTree.parse(r.body(), this.publicKey.get());
         if (!t.signed() || !t.version().equals(version) || !t.os().equals(this.installation.os()) || !t.arch().equals(this.installation.arch())) {
            LauncherLog.warn("update tree · firma o versión no válidas: se usará el paquete completo");
            return;
         }

         long t0 = System.currentTimeMillis();
         Plan tp = TreeUpdater.plan(t, installDir);
         this.tree = t;
         this.treePlan = tp;
         LauncherLog.info(
            "update tree · "
               + version
               + " · bajar "
               + tp.fetch().size()
               + " archivos ("
               + tp.bytesToDownload() / 1024L
               + " KB), reutilizar "
               + tp.reuse().size()
               + " ("
               + tp.bytesReused() / 1048576L
               + " MB) · hash en "
               + (System.currentTimeMillis() - t0)
               + " ms"
         );
      } catch (RuntimeException | IOException var10) {
         LauncherLog.warn("update tree · " + var10.getMessage() + ": se usará el paquete completo");
      } catch (InterruptedException var11) {
         Thread.currentThread().interrupt();
      }
   }

   private void fetchTree(UpdatePlan p) throws IOException, InterruptedException {
      InstallTree t = this.tree;
      Plan tp = this.treePlan;
      Path objects = this.downloadsDir.resolve("objects");
      long total = tp.bytesToDownload();
      long done = 0L;

      for (Entry e : tp.fetch()) {
         Path target = objects.resolve(e.object());
         long base = done;
         this.resumable
            .download(this.serviceUrl + "/downloads/objects/" + e.object(), target, e.objectSha256(), e.objectSize(), (b, sz) -> this.progress(base + b, total));
         done += e.objectSize();
         this.progress(done, total);
      }

      Path dir = p.installDir();
      Path fresh = dir.resolveSibling(dir.getFileName() + ".new");
      TreeUpdater.assemble(t, tp, dir, objects, fresh);
      List<String> problems = TreeUpdater.verify(t, fresh);
      if (!problems.isEmpty()) {
         TreeUpdater.deleteTree(fresh);
         this.treePlan = TreeUpdater.plan(t, dir);
         throw new IOException(
            "la instalación montada no coincide con el árbol: " + problems.get(0) + (problems.size() > 1 ? " (+" + (problems.size() - 1) + ")" : "")
         );
      } else {
         this.treeNewDir = fresh;
         this.downloaded = fresh;
         LauncherLog.info("update tree · montada y verificada · " + t.files().size() + " archivos");
      }
   }

   private boolean applyTree(UpdatePlan p) throws IOException {
      Path dir = p.installDir();
      Path fresh = this.treeNewDir;
      Path old = dir.resolveSibling(dir.getFileName() + ".old");
      Files.writeString(this.dataDir().resolve("install-tree.json"), this.tree.rawJson());
      if (this.installation.os().equals("windows")) {
         return this.windowsWatchdog(dir, fresh, old, dir.resolve("pulsar-launcher.exe"));
      } else {
         deleteTree(old);
         Files.move(dir, old, StandardCopyOption.ATOMIC_MOVE);
         Files.move(fresh, dir, StandardCopyOption.ATOMIC_MOVE);
         Files.writeString(this.dataDir().resolve("install-tree.json"), this.tree.rawJson());
         List<String> relaunch;
         if (this.installation.os().equals("macos")) {
            relaunch = List.of("open", "-n", dir.toString());
         } else {
            Path exe = dir.resolve(this.installation.os().equals("windows") ? "pulsar-launcher.exe" : "pulsar-launcher");
            executable(exe);
            relaunch = List.of(exe.toString());
         }

         LauncherLog.info("update applied · tree swapped · relaunch");
         if (!this.watchdog("dir", dir, old, relaunch)) {
            this.relauncher.relaunch(relaunch);
         }

         return true;
      }
   }

   public Map<String, Object> verifyInstall(String channel) {
      Map<String, Object> out = new LinkedHashMap<>();
      UpdatePlan p = this.plan;
      Path dir = this.installation.target() == null
         ? null
         : (Files.isRegularFile(this.installation.target()) ? this.installation.target().getParent() : this.installation.target());
      if (dir != null
         && Files.isDirectory(dir)
         && (this.installation.format().equals("tar.gz") || this.installation.format().equals("zip") || this.installation.format().equals("dmg"))) {
         try {
            InstallTree t = null;
            Path saved = this.dataDir().resolve("install-tree.json");
            if (Files.isRegularFile(saved)) {
               InstallTree st = InstallTree.parse(Files.readString(saved), this.publicKey.get());
               if (st.signed() && st.version().equals(PulsarLauncher.VERSION)) {
                  t = st;
               }
            }

            if (t == null) {
               URI uri = URI.create(
                  this.serviceUrl
                     + "/api/v1/tree?channel="
                     + channel
                     + "&os="
                     + this.installation.os()
                     + "&arch="
                     + this.installation.arch()
                     + "&version="
                     + PulsarLauncher.VERSION
               );
               HttpResponse<String> r = this.http.send(HttpRequest.newBuilder(uri).GET().build(), BodyHandlers.ofString());
               if (r.statusCode() == 200) {
                  InstallTree st = InstallTree.parse(r.body(), this.publicKey.get());
                  if (st.signed() && st.version().equals(PulsarLauncher.VERSION)) {
                     t = st;
                  }
               }
            }

            if (t == null) {
               out.put("supported", false);
               return out;
            } else {
               Plan tp = TreeUpdater.plan(t, dir);
               out.put("supported", true);
               out.put("files", t.files().size());
               out.put("damaged", tp.fetch().size());
               out.put("bytes", tp.bytesToDownload());
               if (!tp.fetch().isEmpty()) {
                  UpdatePlan repair = new UpdatePlan(
                     new Package(this.installation.os(), this.installation.arch(), this.installation.format(), "", "", 0L),
                     this.installation,
                     this.installation.os().equals("macos") ? Apply.MOUNT_AND_COPY : Apply.SWAP_DIRECTORY
                  );
                  if (p == null) {
                     this.plan = repair;
                  }

                  this.tree = t;
                  this.treePlan = tp;
                  this.phase = "available";
                  this.emit("available");
                  LauncherLog.warn("install verify · " + tp.fetch().size() + " archivos dañados: reparación lista (" + tp.bytesToDownload() / 1024L + " KB)");
               }

               return out;
            }
         } catch (RuntimeException | IOException var10) {
            throw new DaemonException("io", "No se pudo verificar la instalación", var10.getMessage(), null, var10);
         } catch (InterruptedException var11) {
            Thread.currentThread().interrupt();
            throw new DaemonException("cancelled", "Verificación cancelada", "");
         }
      } else {
         out.put("supported", false);
         return out;
      }
   }

   private void emit(String state) {
      Map<String, Object> m = new LinkedHashMap<>(this.state());
      m.put("state", state);
      this.events.accept(Protocol.event("update", m));
   }

   public boolean applyOrWait(boolean gameRunning) {
      if (gameRunning) {
         this.applyWhenGameCloses = true;
         this.phase = "waiting_game";
         this.emit("waiting_game");
         return false;
      } else {
         return this.apply();
      }
   }

   public void onGameExited(boolean autoUpdate) {
      if (this.downloaded != null && Files.exists(this.downloaded) && (this.applyWhenGameCloses || autoUpdate)) {
         this.applyWhenGameCloses = false;

         try {
            this.phase = "applying";
            this.emit("applying");
            if (this.apply()) {
               this.emit("relaunching");
            }
         } catch (RuntimeException var3) {
            LauncherLog.error("update apply after game exit failed", var3);
            this.phase = "ready";
            this.lastError = var3.getMessage();
            this.emit("ready");
         }
      }
   }

   public synchronized void schedulePeriodicChecks(Supplier<String> channel, long periodMs) {
      if (this.periodic == null) {
         this.periodic = Executors.newSingleThreadScheduledExecutor(r -> {
            Thread t = new Thread(r, "pulsar-update-periodic");
            t.setDaemon(true);
            return t;
         });
         this.periodic.scheduleAtFixedRate(() -> {
            try {
               if (!"ready".equals(this.phase) && !"downloading".equals(this.phase)) {
                  this.check(channel.get());
               }
            } catch (RuntimeException var3) {
               LauncherLog.warn("periodic update check failed · " + var3.getMessage());
            }
         }, periodMs, periodMs, TimeUnit.MILLISECONDS);
      }
   }

   public String phase() {
      return this.phase;
   }

   public boolean apply() {
      UpdatePlan p = this.plan;
      Path file = this.downloaded;
      if (p != null && file != null && Files.exists(file)) {
         this.phase = "applying";

         try {
            if (this.treeNewDir != null && Files.isDirectory(this.treeNewDir)) {
               return this.applyTree(p);
            } else {
               switch (p.apply()) {
                  case REPLACE_APPIMAGE:
                     Path target = p.installation().target();
                     Path staged = target.resolveSibling(target.getFileName() + ".new");
                     Files.copy(file, staged, StandardCopyOption.REPLACE_EXISTING);
                     executable(staged);
                     Path backup = target.resolveSibling(target.getFileName() + ".old");
                     Files.copy(target, backup, StandardCopyOption.REPLACE_EXISTING);
                     Files.move(staged, target, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
                     Files.deleteIfExists(file);
                     LauncherLog.info("update applied · appimage replaced · relaunch");
                     if (!this.watchdog("file", target, backup, List.of(target.toString()))) {
                        this.relauncher.relaunch(List.of(target.toString()));
                     }

                     return true;
                  case SWAP_DIRECTORY:
                     Path dir = p.installDir();
                     Path fresh = dir.resolveSibling(dir.getFileName() + ".new");
                     Path old = dir.resolveSibling(dir.getFileName() + ".old");
                     deleteTree(fresh);
                     deleteTree(old);
                     ArchiveExtractor.extract(file, fresh, ArchiveExtractor.formatOf(file.getFileName().toString()));
                     Path root = singleChild(fresh).orElse(fresh);
                     Files.move(dir, old, StandardCopyOption.ATOMIC_MOVE);
                     Files.move(root, dir, StandardCopyOption.ATOMIC_MOVE);
                     deleteTree(fresh);
                     Path exe = dir.resolve(this.installation.os().equals("windows") ? "pulsar-launcher.exe" : "pulsar-launcher");
                     executable(exe);
                     Files.deleteIfExists(file);
                     LauncherLog.info("update applied · directory swapped · relaunch");
                     if (!this.watchdog("dir", dir, old, List.of(exe.toString()))) {
                        this.relauncher.relaunch(List.of(exe.toString()));
                     }

                     return true;
                  case OPEN_PACKAGE:
                     if (installDeb(file)) {
                        Files.deleteIfExists(file);
                        Path exe = p.installation().target() != null ? p.installation().target() : Path.of("/usr/bin/pulsar-launcher");
                        LauncherLog.info("update applied · deb installed with pkexec · relaunch");
                        if (!this.watchdog("file", exe, exe.resolveSibling(exe.getFileName() + ".no-backup"), List.of(exe.toString()))) {
                           this.relauncher.relaunch(List.of(exe.toString()));
                        }

                        return true;
                     }

                     this.opener.accept(file);
                     LauncherLog.info("update · package opened for the system installer");
                     return false;
                  case RUN_INSTALLER:
                     this.relauncher.relaunch(List.of(file.toString(), "/SILENT", "/NORESTART"));
                     LauncherLog.info("update · installer started");
                     return true;
                  case MOUNT_AND_COPY:
                     Path mount = Files.createTempDirectory("pulsar-update-");
                     run(List.of("hdiutil", "attach", "-nobrowse", "-quiet", "-mountpoint", mount.toString(), file.toString()));
                     Path app = p.installation().target() != null ? appBundle(p.installation().target()) : Path.of("/Applications/Pulsar Launcher.app");
                     Path fresh = app.resolveSibling(app.getFileName() + ".new");
                     run(List.of("ditto", mount.resolve("Pulsar Launcher.app").toString(), fresh.toString()));
                     run(List.of("hdiutil", "detach", "-quiet", mount.toString()));
                     Path old = app.resolveSibling(app.getFileName() + ".old");
                     deleteTree(old);
                     Files.move(app, old, StandardCopyOption.ATOMIC_MOVE);
                     Files.move(fresh, app, StandardCopyOption.ATOMIC_MOVE);
                     Files.deleteIfExists(file);
                     LauncherLog.info("update applied · app bundle replaced · relaunch");
                     if (!this.watchdog("dir", app, old, List.of("open", "-n", app.toString()))) {
                        this.relauncher.relaunch(List.of("open", "-n", app.toString()));
                     }

                     return true;
                  default:
                     return false;
               }
            }
         } catch (IOException var8) {
            this.phase = "ready";
            throw new DaemonException("update_apply_failed", "No se pudo aplicar la actualización", var8.getMessage(), null, var8);
         }
      } else {
         throw new DaemonException("no_update", "No hay actualización descargada", "Descárgala primero.");
      }
   }

   public void cleanup() {
      try {
         Files.createDirectories(this.dataDir());
         Files.writeString(this.markerFile(), PulsarLauncher.VERSION);
      } catch (IOException var6) {
         LauncherLog.warn("update marker not written · " + var6.getMessage());
      }

      Path dir = this.installation.target() == null
         ? null
         : (Files.isRegularFile(this.installation.target()) ? this.installation.target().getParent() : this.installation.target());
      if (dir != null && dir.getParent() != null) {
         try (Stream<Path> s = Files.list(dir.getParent())) {
            for (Path p : s.filter(x -> x.getFileName().toString().endsWith(".old")).toList()) {
               deleteTree(p);
            }
         } catch (IOException var8) {
         }
      }
   }

   Path dataDir() {
      Path parent = this.downloadsDir.toAbsolutePath().getParent();
      return parent == null ? this.downloadsDir.toAbsolutePath() : parent;
   }

   Path markerFile() {
      return this.dataDir().resolve("update-ok");
   }

   static String watchdogScript() {
      return "#!/bin/sh\n# Pulsar update watchdog: marker version timeoutSec mode(dir|file) target backup oldPid relaunch...\n# 1) espera a que el launcher viejo cierre (instancia única: si el nuevo arrancara antes, cedería al viejo y saldría)\n# 2) lanza el nuevo  3) espera su marca  4) si no llega, restaura la copia y relanza la versión anterior\nmarker=$1; version=$2; timeout=$3; mode=$4; target=$5; backup=$6; oldpid=$7; shift 7\nif [ \"$oldpid\" -gt 0 ] 2>/dev/null; then\n  n=0; while kill -0 \"$oldpid\" 2>/dev/null && [ $n -lt 150 ]; do sleep 0.1; n=$((n+1)); done\nfi\nrm -f \"$marker\"\nnohup \"$@\" >/dev/null 2>&1 &\necho \"lanzado: $*\"\nend=$(( $(date +%s) + timeout ))\nwhile [ \"$(date +%s)\" -lt \"$end\" ]; do\n  if [ -f \"$marker\" ] && grep -qx \"$version\" \"$marker\"; then echo \"ok: $version arranco\"; exit 0; fi\n  sleep 1\ndone\necho \"sin marca de $version en $timeout s: restaurando $backup\"\nif [ ! -e \"$backup\" ]; then echo \"no hay copia: nada que restaurar\"; exit 1; fi\nif [ \"$mode\" = dir ]; then\n  rm -rf \"$target.failed\"; mv \"$target\" \"$target.failed\" 2>/dev/null; mv \"$backup\" \"$target\" || exit 1\nelse\n  cp \"$backup\" \"$target\" && chmod +x \"$target\" || exit 1\nfi\nrm -f \"$marker\"\nnohup \"$@\" >/dev/null 2>&1 &\necho \"restaurado y relanzado\"\n";
   }

   static String windowsWatchdogScript() {
      return "@echo off\nrem Pulsar update watchdog: marker version dir fresh old oldPid exe\nset \"marker=%~1\" & set \"version=%~2\" & set \"dir=%~3\" & set \"fresh=%~4\" & set \"old=%~5\" & set \"oldpid=%~6\" & set \"exe=%~7\"\nif not \"%oldpid%\"==\"0\" (\n  for /l %%i in (1,1,150) do (\n    tasklist /fi \"PID eq %oldpid%\" 2>nul | find \"%oldpid%\" >nul || goto swap\n    timeout /t 1 /nobreak >nul\n  )\n)\n:swap\nif exist \"%marker%\" del /q \"%marker%\"\nif exist \"%old%\" rmdir /s /q \"%old%\"\nmove \"%dir%\" \"%old%\" >nul || (echo no se pudo apartar la instalacion & exit /b 1)\nmove \"%fresh%\" \"%dir%\" >nul || (move \"%old%\" \"%dir%\" >nul & echo no se pudo colocar la nueva & exit /b 1)\nstart \"\" \"%exe%\"\nfor /l %%i in (1,1,20) do (\n  if exist \"%marker%\" (findstr /x /c:\"%version%\" \"%marker%\" >nul && (echo ok: %version% arranco & exit /b 0))\n  timeout /t 1 /nobreak >nul\n)\necho sin marca de %version%: restaurando\nif exist \"%dir%.failed\" rmdir /s /q \"%dir%.failed\"\nmove \"%dir%\" \"%dir%.failed\" >nul\nmove \"%old%\" \"%dir%\" >nul\nif exist \"%marker%\" del /q \"%marker%\"\nstart \"\" \"%exe%\"\necho restaurado y relanzado\n";
   }

   private boolean windowsWatchdog(Path dir, Path fresh, Path old, Path exe) {
      try {
         Path script = this.dataDir().resolve("update-watchdog.cmd");
         Files.writeString(script, windowsWatchdogScript());
         List<String> cmd = List.of(
            "cmd",
            "/c",
            "start",
            "",
            "/min",
            "cmd",
            "/c",
            script.toString(),
            this.markerFile().toString(),
            this.latest.version(),
            dir.toString(),
            fresh.toString(),
            old.toString(),
            String.valueOf(parentLauncherPid()),
            exe.toString()
         );
         new ProcessBuilder(cmd).redirectErrorStream(true).redirectOutput(Redirect.appendTo(this.dataDir().resolve("update-watchdog.log").toFile())).start();
         LauncherLog.info("update watchdog (cmd) armed · " + this.latest.version() + " · " + dir);
         return true;
      } catch (RuntimeException | IOException var7) {
         LauncherLog.error("update watchdog (cmd) not started", var7);
         throw new DaemonException("update_apply_failed", "No se pudo preparar el intercambio", var7.getMessage(), null, var7);
      }
   }

   static long parentLauncherPid() {
      return ProcessHandle.current()
         .parent()
         .filter(p -> p.info().command().map(c -> c.contains("pulsar-launcher") || c.contains("Pulsar Launcher")).orElse(false))
         .map(ProcessHandle::pid)
         .orElse(0L);
   }

   private boolean watchdog(String mode, Path target, Path backup, List<String> relaunch) {
      if (this.watchdogEnabled && !"windows".equals(this.installation.os()) && this.latest != null) {
         try {
            Files.deleteIfExists(this.markerFile());
            Path script = this.dataDir().resolve("update-watchdog.sh");
            Files.writeString(script, watchdogScript());
            List<String> cmd = new ArrayList<>(
               List.of(
                  "sh",
                  script.toString(),
                  this.markerFile().toString(),
                  this.latest.version(),
                  "20",
                  mode,
                  target.toString(),
                  backup.toString(),
                  String.valueOf(parentLauncherPid())
               )
            );
            cmd.addAll(relaunch);
            new ProcessBuilder(GameDetacher.detach(cmd))
               .redirectErrorStream(true)
               .redirectOutput(Redirect.appendTo(this.dataDir().resolve("update-watchdog.log").toFile()))
               .start();
            LauncherLog.info("update watchdog armed · " + this.latest.version() + " · " + mode + " " + target + " · shell pid " + parentLauncherPid());
            return true;
         } catch (RuntimeException | IOException var7) {
            LauncherLog.warn("update watchdog not started · " + var7.getMessage());
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean requiredPending() {
      ReleaseManifest m = this.latest;
      return !this.offline
         && this.plan != null
         && m != null
         && m.minVersion() != null
         && Semver.isValidPublic(PulsarLauncher.VERSION)
         && Semver.parse(m.minVersion()).newerThan(Semver.parse(PulsarLauncher.VERSION));
   }

   public boolean offline() {
      return this.offline;
   }

   static boolean isConnectivity(Throwable e) {
      for (Throwable t = e; t != null; t = t.getCause()) {
         if (t instanceof UnknownHostException
            || t instanceof SocketException
            || t instanceof SocketTimeoutException
            || t instanceof HttpTimeoutException
            || t instanceof UnresolvedAddressException) {
            return true;
         }
      }

      return false;
   }

   public Map<String, Object> state() {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("current", PulsarLauncher.VERSION);
      m.put("installation", Map.of("os", this.installation.os(), "arch", this.installation.arch(), "format", this.installation.format()));
      m.put("latest", this.latest == null ? null : this.latest.version());
      m.put("downloaded", this.downloaded == null ? null : this.downloaded.toString());
      UpdatePlan p = this.plan;
      m.put("available", p != null);
      m.put("required", this.requiredPending());
      m.put("size", p == null ? null : p.pkg().size());
      m.put("format", p == null ? null : p.pkg().format());
      m.put("notes", this.latest == null ? "" : this.latest.notes());
      Plan tp = this.treePlan;
      m.put("mode", tp != null ? "tree" : "package");
      m.put("reusedBytes", tp == null ? 0L : tp.bytesReused());
      m.put("filesToFetch", tp == null ? null : tp.fetch().size());
      m.put("phase", this.phase);
      m.put("fraction", this.fraction);
      m.put("bytes", this.bytes);
      m.put("attempt", this.attempt);
      m.put("nextRetryAt", this.nextRetryAt);
      m.put("lastError", this.lastError);
      m.put("offline", this.offline);
      return m;
   }

   private static boolean installDeb(Path file) {
      Path pkexec = Path.of("/usr/bin/pkexec");
      Path apt = Path.of("/usr/bin/apt-get");
      if (file.getFileName().toString().endsWith(".deb") && Files.isExecutable(pkexec) && Files.isExecutable(apt)) {
         try {
            run(List.of(pkexec.toString(), apt.toString(), "install", "-y", "--allow-downgrades", file.toAbsolutePath().toString()));
            return true;
         } catch (IOException var4) {
            LauncherLog.warn("update · pkexec apt-get install failed: " + var4.getMessage());
            return false;
         }
      } else {
         return false;
      }
   }

   private static void run(List<String> command) throws IOException {
      try {
         Process p = new ProcessBuilder(command).redirectErrorStream(true).start();
         String out = new String(p.getInputStream().readAllBytes());
         if (p.waitFor() != 0) {
            throw new IOException(command.get(0) + " failed: " + out.trim());
         }
      } catch (InterruptedException var3) {
         Thread.currentThread().interrupt();
         throw new IOException("interrupted");
      }
   }

   private static Path appBundle(Path exe) {
      Path p = exe;

      while (p != null && !p.getFileName().toString().endsWith(".app")) {
         p = p.getParent();
      }

      return p == null ? Path.of("/Applications/Pulsar Launcher.app") : p;
   }

   private static Optional<Path> singleChild(Path dir) throws IOException {
      Optional var3;
      try (Stream<Path> s = Files.list(dir)) {
         List<Path> children = s.toList();
         var3 = children.size() == 1 && Files.isDirectory(children.get(0)) ? Optional.of(children.get(0)) : Optional.empty();
      }

      return var3;
   }

   private static void executable(Path p) {
      try {
         EnumSet<PosixFilePermission> perms = EnumSet.copyOf(Files.getPosixFilePermissions(p));
         perms.add(PosixFilePermission.OWNER_EXECUTE);
         perms.add(PosixFilePermission.GROUP_EXECUTE);
         perms.add(PosixFilePermission.OTHERS_EXECUTE);
         Files.setPosixFilePermissions(p, perms);
      } catch (IOException | UnsupportedOperationException var2) {
      }
   }

   static void deleteTree(Path dir) throws IOException {
      if (Files.exists(dir, LinkOption.NOFOLLOW_LINKS)) {
         List<Path> all = new ArrayList<>();

         try (Stream<Path> walk = Files.walk(dir)) {
            walk.sorted(Comparator.reverseOrder()).forEach(all::add);
         }

         for (Path p : all) {
            Files.deleteIfExists(p);
         }
      }
   }

   public interface Relauncher {
      void relaunch(List<String> var1) throws IOException;
   }

   public record Status(
      String current, String latest, boolean available, boolean required, boolean signed, String notes, String channel, String format, Long size, String error
   ) {
   }
}
