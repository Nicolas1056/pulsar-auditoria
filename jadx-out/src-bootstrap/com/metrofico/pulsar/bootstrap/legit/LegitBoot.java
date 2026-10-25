package com.metrofico.pulsar.bootstrap.legit;

import com.metrofico.pulsar.api.legit.CloudLink;
import com.metrofico.pulsar.api.legit.LegitSession;
import com.metrofico.pulsar.api.legit.ProbeEngine;
import com.metrofico.pulsar.api.legit.RealtimeCloudLink;
import com.metrofico.pulsar.api.legit.LegitSession.Exam;
import com.metrofico.pulsar.api.pack.Json;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.bootstrap.loader.GameClassLoader;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public final class LegitBoot {
   private LegitBoot() {
   }

   public static void start(String mcVersion, List<Path> gameClasspath, Class<?> backend, Path dataDir, GameClassLoader loader) {
      if ("off".equals(System.getProperty("pulsar.legit"))) {
         BootstrapLog.info("legit: module off (-Dpulsar.legit=off)");
      } else {
         CompletableFuture<Exam> exam = CompletableFuture.supplyAsync(() -> BootSeal.exam(mcVersion, gameClasspath, backend), runnable -> {
            Thread t = new Thread(runnable, "pulsar-legit-seal");
            t.setDaemon(true);
            t.start();
         });
         exam.exceptionally(e -> {
            BootstrapLog.error("legit: exam could not be prepared", e);
            return null;
         });
         Path sessionFile = dataDir.resolve("emotes").resolve("session.json");
         LegitSession session = new LegitSession(
            () -> link(sessionFile), exam, ProbeEngine.current(), message -> BootstrapLog.info(message), new SelfGuard(loader)
         );
         Path stateFile = dataDir.resolve("legit-state.json");
         session.onState(state -> writeState(stateFile, state.name(), session.score()));
         writeState(stateFile, session.state().name(), 0);
         LegitSession.install(session);
         MetricsSummary.installShutdownHook(dataDir, mcVersion);
      }
   }

   private static void writeState(Path file, String state, int score) {
      try {
         Path tmp = file.resolveSibling(file.getFileName() + ".part");
         Files.writeString(
            tmp,
            Json.write(Map.of("state", state, "score", (long)score, "updatedAt", Instant.now().toString(), "pid", ProcessHandle.current().pid())),
            StandardCharsets.UTF_8
         );
         Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
      } catch (RuntimeException | IOException var4) {
         BootstrapLog.info("legit: cannot write " + file + ": " + var4.getMessage());
      }
   }

   private static CloudLink link(Path sessionFile) throws IOException {
      if (!Files.isRegularFile(sessionFile)) {
         return null;
      } else {
         Map<String, Object> o = Json.object(Json.parse(Files.readString(sessionFile, StandardCharsets.UTF_8)), "session");
         String token = o.get("token") instanceof String t && !t.isEmpty() ? t : null;
         URI endpoint = RealtimeCloudLink.endpoint();
         return token != null && endpoint != null ? new RealtimeCloudLink(endpoint, token) : null;
      }
   }
}
