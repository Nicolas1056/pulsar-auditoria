package com.metrofico.pulsar.api.pack;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public final class ConsentStore {
   private final Path file;

   public ConsentStore(Path dataDir) {
      this.file = dataDir.resolve("packs").resolve("consent.json");
   }

   public Path file() {
      return this.file;
   }

   public Optional<ConsentStore.Decision> lookup(String serverId, Set<Capability> requested) {
      return this.all().stream().filter(d -> d.serverId().equals(serverId) && d.requested().equals(requested)).findFirst();
   }

   public synchronized void remember(ConsentStore.Decision decision) {
      List<ConsentStore.Decision> all = new ArrayList<>(this.all());
      all.removeIf(d -> d.serverId().equals(decision.serverId()));
      all.add(decision);
      this.save(all);
   }

   public synchronized void forget(String serverId) {
      List<ConsentStore.Decision> all = new ArrayList<>(this.all());
      all.removeIf(d -> d.serverId().equals(serverId));
      this.save(all);
   }

   public synchronized List<ConsentStore.Decision> all() {
      if (!Files.isRegularFile(this.file)) {
         return List.of();
      } else {
         try {
            Object root = Json.parse(Files.readString(this.file, StandardCharsets.UTF_8));
            List<ConsentStore.Decision> out = new ArrayList<>();

            for (Object o : Json.array(Json.object(root, "consent").getOrDefault("servers", List.of()), "servers")) {
               Map<String, Object> d = Json.object(o, "decision");
               Set<Capability> caps = EnumSet.noneOf(Capability.class);

               for (Object c : (List)d.getOrDefault("requested", List.of())) {
                  Capability.byId(String.valueOf(c)).ifPresent(caps::add);
               }

               out.add(
                  new ConsentStore.Decision(
                     Json.string(d, "serverId", "decision"),
                     String.valueOf(d.getOrDefault("serverName", "")),
                     Json.integer(d, "packVersion", "decision"),
                     caps,
                     PackSession.Consent.valueOf(Json.string(d, "consent", "decision")),
                     String.valueOf(d.getOrDefault("decidedAt", ""))
                  )
               );
            }

            return out;
         } catch (RuntimeException | IOException var9) {
            return List.of();
         }
      }
   }

   private void save(List<ConsentStore.Decision> all) {
      List<Object> servers = new ArrayList<>();

      for (ConsentStore.Decision d : all) {
         Map<String, Object> m = new LinkedHashMap<>();
         m.put("serverId", d.serverId());
         m.put("serverName", d.serverName());
         m.put("packVersion", d.packVersion());
         List<Object> caps = new ArrayList<>();
         d.requested().forEach(c -> caps.add(c.id()));
         m.put("requested", caps);
         m.put("consent", d.consent().name());
         m.put("decidedAt", d.decidedAt());
         servers.add(m);
      }

      try {
         Files.createDirectories(this.file.getParent());
         Files.writeString(this.file, Json.write(Map.of("version", 1L, "servers", servers)), StandardCharsets.UTF_8);
      } catch (IOException var7) {
         throw new IllegalStateException("cannot save consent", var7);
      }
   }

   public record Decision(String serverId, String serverName, long packVersion, Set<Capability> requested, PackSession.Consent consent, String decidedAt) {
   }
}
