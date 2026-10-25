package com.metrofico.pulsar.bootstrap.legit;

import com.metrofico.pulsar.api.legit.EntryPass;
import com.metrofico.pulsar.api.pack.Json;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.api.perf.FrameStats;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class MetricsSummary {
   private MetricsSummary() {
   }

   public static void installShutdownHook(Path dataDir, String mcVersion) {
      Path file = dataDir.resolve("metrics-session.json");
      Runtime.getRuntime().addShutdownHook(new Thread(() -> write(file, mcVersion), "pulsar-metrics-session"));
   }

   static void write(Path file, String mcVersion) {
      try {
         Map<String, Object> body = new LinkedHashMap<>(FrameStats.sessionSummary());
         body.put("game", mcVersion);
         body.put("mc", mcVersion);
         body.put("flags", activeFlags());
         body.put("withLegit", EntryPass.activeHost() != null || System.getProperty("pulsar.realtime.url", "").length() > 0);
         Files.createDirectories(file.getParent());
         Path tmp = file.resolveSibling(file.getFileName() + ".part");
         Files.writeString(tmp, Json.write(body), StandardCharsets.UTF_8);
         Files.move(tmp, file, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
      } catch (RuntimeException | IOException var4) {
         BootstrapLog.info("metrics: no se pudo escribir el resumen (" + var4.getMessage() + ")");
      }
   }

   private static List<String> activeFlags() {
      List<String> out = new ArrayList<>();

      for (Field f : FrameFlags.class.getDeclaredFields()) {
         if (f.getType() == boolean.class && !Modifier.isFinal(f.getModifiers())) {
            try {
               if (f.getBoolean(null)) {
                  out.add(f.getName());
               }
            } catch (IllegalAccessException var6) {
            }
         }
      }

      return out;
   }
}
