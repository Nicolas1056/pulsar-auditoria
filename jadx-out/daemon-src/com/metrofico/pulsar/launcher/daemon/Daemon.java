package com.metrofico.pulsar.launcher.daemon;

import com.metrofico.pulsar.launcher.PulsarLauncher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public final class Daemon {
   private final Commands commands;
   private final Consumer<String> emit;
   private final ExecutorService workers = Executors.newCachedThreadPool(r -> {
      Thread t = new Thread(r, "pulsar-daemon-worker");
      t.setDaemon(true);
      return t;
   });
   private final BootProgress boot;

   public Daemon(CoreService core, Path dataDir, Consumer<String> emit) {
      this(core, dataDir, emit, new BootProgress(emit));
   }

   Daemon(CoreService core, Path dataDir, Consumer<String> emit, BootProgress boot) {
      this.emit = emit;
      this.boot = boot;
      LauncherLog.init(core.logsDir());
      LauncherLog.info(
         "daemon start · Pulsar Launcher "
            + PulsarLauncher.VERSION
            + " · java "
            + Runtime.version()
            + " · "
            + System.getProperty("os.name")
            + " "
            + System.getProperty("os.arch")
      );
      boot.step("keyring");
      core.secretStore();
      boot.step("services");
      this.commands = new Commands(core, dataDir, emit);
      this.commands.restoreSessionAsync();
      this.commands.checkUpdatesAsync();
      this.commands.startPresence();

      try {
         List<Path> removed = LauncherLog.retainGameLogs(core.logsDir(), 10);
         if (!removed.isEmpty()) {
            LauncherLog.info("game logs pruned: " + removed.size());
         }
      } catch (IOException var6) {
         LauncherLog.error("could not prune game logs", var6);
      }

      boot.ready();
   }

   public static void main(String[] args) throws Exception {
      Path root = Path.of(System.getProperty("pulsar.root", System.getenv().getOrDefault("PULSAR_ROOT", "run"))).toAbsolutePath().normalize();
      Path dataDir = root.resolve("pulsar");
      CoreService core = new LauncherCore(root);
      int wsPort = -1;

      for (int i = 0; i < args.length - 1; i++) {
         if (args[i].equals("--ws")) {
            wsPort = Integer.parseInt(args[i + 1]);
         }
      }

      if (wsPort > 0) {
         WebSocketTransport.serve(wsPort, core, dataDir);
      } else {
         stdio(System.in, System.out, core, dataDir);
      }
   }

   public static void stdio(InputStream in, OutputStream out, CoreService core, Path dataDir) throws IOException {
      PrintStream printer = new PrintStream(out, true, StandardCharsets.UTF_8);
      Consumer<String> emit = linex -> {
         synchronized (printer) {
            printer.println(linex);
         }
      };
      BootProgress boot = new BootProgress(emit);
      CompletableFuture<Daemon> ready = new CompletableFuture<>();
      Thread init = new Thread(() -> {
         try {
            ready.complete(new Daemon(core, dataDir, emit, boot));
         } catch (Throwable var6x) {
            LauncherLog.error("daemon init failed", var6x);
            ready.completeExceptionally(var6x);
         }
      }, "pulsar-daemon-init");
      init.setDaemon(true);
      init.start();
      ExecutorService inbox = Executors.newSingleThreadExecutor(r -> new Thread(r, "pulsar-daemon-inbox"));
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8));

      String line;
      while ((line = reader.readLine()) != null) {
         Protocol.Request parsed = parseQuietly(line);
         if (parsed != null && parsed.cmd().equals("boot.status")) {
            emit.accept(Protocol.ok(parsed.id(), boot.status()));
         } else if (parsed != null && parsed.cmd().equals("shutdown")) {
            inbox.shutdown();

            try {
               inbox.awaitTermination(60L, TimeUnit.SECONDS);
            } catch (InterruptedException var15) {
               Thread.currentThread().interrupt();
            }

            if (!ready.join().handle(line)) {
               break;
            }
         } else {
            inbox.submit(
               () -> {
                  try {
                     ready.join().handle(line);
                  } catch (RuntimeException var5x) {
                     emit.accept(
                        Protocol.error(
                           parsed != null ? parsed.id() : -1L, new DaemonException("daemon_unavailable", "No se pudo iniciar el launcher", var5x.getMessage())
                        )
                     );
                  }
               }
            );
         }
      }

      inbox.shutdownNow();
      if (ready.isDone() && !ready.isCompletedExceptionally()) {
         ready.join().close();
      }
   }

   private static Protocol.Request parseQuietly(String line) {
      try {
         return line.isBlank() ? null : Protocol.Request.parse(line);
      } catch (DaemonException var2) {
         return null;
      }
   }

   public boolean handle(String line) {
      if (line.isBlank()) {
         return true;
      } else {
         Protocol.Request request;
         try {
            request = Protocol.Request.parse(line);
         } catch (DaemonException var5) {
            LauncherLog.warn("bad request: " + var5.getMessage());
            this.emit.accept(Protocol.error(-1L, var5));
            return true;
         }

         if (request.cmd().equals("boot.status")) {
            this.emit.accept(Protocol.ok(request.id(), this.boot.status()));
            return true;
         } else if (request.cmd().equals("shutdown")) {
            LauncherLog.info("shutdown");
            this.workers.shutdown();

            try {
               if (!this.workers.awaitTermination(30L, TimeUnit.SECONDS)) {
                  this.workers.shutdownNow();
               }
            } catch (InterruptedException var4) {
               Thread.currentThread().interrupt();
            }

            this.emit.accept(Protocol.ok(request.id(), this.commands.handle(request)));
            LauncherLog.close();
            return false;
         } else {
            if (!request.cmd().equals("prepare") && !request.cmd().equals("launch")) {
               this.dispatch(request);
            } else {
               this.workers.submit(() -> this.dispatch(request));
            }

            return true;
         }
      }
   }

   public void close() {
      this.workers.shutdownNow();
   }

   private void dispatch(Protocol.Request request) {
      long start = System.nanoTime();

      try {
         this.emit.accept(Protocol.ok(request.id(), this.commands.handle(request)));
         LauncherLog.info(request.cmd() + " ok · " + (System.nanoTime() - start) / 1000000L + " ms");
      } catch (DaemonException var5) {
         LauncherLog.warn(request.cmd() + " failed · " + var5.code() + " · " + var5.title() + (var5.detail() != null ? " · " + var5.detail() : ""));
         this.emit.accept(Protocol.error(request.id(), var5));
      } catch (RuntimeException var6) {
         LauncherLog.error(request.cmd() + " crashed", var6);
         this.emit.accept(Protocol.error(request.id(), new DaemonException("internal", "Algo falló en el launcher", var6.toString(), stack(var6), var6)));
      }
   }

   private static String stack(Throwable t) {
      StringWriter sw = new StringWriter();
      t.printStackTrace(new PrintWriter(sw));
      return sw.toString();
   }
}
