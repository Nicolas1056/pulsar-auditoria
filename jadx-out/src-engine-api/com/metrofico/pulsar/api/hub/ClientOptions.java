package com.metrofico.pulsar.api.hub;

import com.metrofico.pulsar.api.hud.Zone;
import com.metrofico.pulsar.api.pack.Json;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Consumer;

public final class ClientOptions {
   private static final ClientOptions INSTANCE = new ClientOptions();
   public static volatile Consumer<String> LOG = s -> {};
   private volatile ClientOptions.Hooks hooks = id -> {};
   private final Path file = Path.of(System.getProperty("pulsar.data.dir", "pulsar")).resolve("client-options.json");
   private final Map<String, Object> values = new LinkedHashMap<>();
   public static volatile int revision;
   private boolean loaded;
   private volatile String serverCombat;

   public void hooks(ClientOptions.Hooks hooks) {
      this.hooks = hooks;
   }

   public static ClientOptions get() {
      return INSTANCE;
   }

   private synchronized void load() {
      if (!this.loaded) {
         this.loaded = true;

         try {
            if (Files.isRegularFile(this.file)) {
               this.values.putAll(Json.object(Json.parse(Files.readString(this.file, StandardCharsets.UTF_8)), "client-options"));
               if ("show".equals(this.values.remove("ui.fps"))) {
                  this.values.putIfAbsent("fps.enabled", Boolean.TRUE);
               }

               this.migratePerfDefaults();
            }
         } catch (Exception var2) {
            LOG.accept("client-options unreadable: " + var2);
         }
      }
   }

   public String string(String id, String def) {
      this.load();
      return this.values.get(id) instanceof String s ? s : def;
   }

   private void migratePerfDefaults() {
      int done = this.values.get("perf.migrated") instanceof Number n ? n.intValue() : 0;
      if (done < 3) {
         List<String> removed = new ArrayList<>();
         if (done < 2) {
            Map<String, Object> stale = Map.of(
               "perf.smoothLighting",
               Boolean.FALSE,
               "perf.shadows",
               Boolean.FALSE,
               "perf.particles",
               "decreased",
               "perf.clouds",
               "off",
               "perf.entityDistance",
               0.75,
               "perf.renderDistance",
               12,
               "perf.improvedTransparency",
               Boolean.FALSE,
               "perf.biomeBlend",
               0
            );

            for (Entry<String, Object> e : stale.entrySet()) {
               Object v = this.values.get(e.getKey());
               boolean same = v != null
                  && (v instanceof Number a && e.getValue() instanceof Number b ? a.doubleValue() == b.doubleValue() : v.equals(e.getValue()));
               if (same) {
                  this.values.remove(e.getKey());
                  removed.add(e.getKey());
               }
            }

            if ("minimal".equals(this.values.get("perf.particles"))) {
               this.values.remove("perf.particles");
               removed.add("perf.particles");
            }
         }

         if (this.values.get("perf.backup") instanceof Map<?, ?> b
            && Boolean.FALSE.equals(b.get("ambientOcclusion"))
            && Boolean.FALSE.equals(b.get("entityShadows"))
            && "DECREASED".equals(b.get("particles"))
            && "OFF".equals(b.get("cloudStatus"))) {
            this.values.remove("perf.backup");
            removed.add("perf.backup");
         }

         this.values.put("perf.migrated", 3);
         if (!removed.isEmpty()) {
            LOG.accept("client-options: defaults antiguos del booster retirados " + removed + " (vuelven los valores vanilla)");
         }

         try {
            Files.createDirectories(this.file.getParent());
            Files.writeString(this.file, Json.write(this.values), StandardCharsets.UTF_8);
         } catch (Exception var11) {
            LOG.accept("client-options save failed: " + var11);
         }
      }
   }

   public boolean isSet(String id) {
      return this.values.containsKey(id);
   }

   public boolean bool(String id, boolean def) {
      this.load();
      return this.values.get(id) instanceof Boolean b ? b : def;
   }

   public int integer(String id, int def) {
      this.load();
      return this.values.get(id) instanceof Number n ? n.intValue() : def;
   }

   public double dbl(String id, double def) {
      this.load();
      return this.values.get(id) instanceof Number n ? n.doubleValue() : def;
   }

   public int color(String id, int def) {
      String s = this.string(id, null);
      return s != null && s.matches("#[0-9a-fA-F]{6}") ? 0xFF000000 | Integer.parseInt(s.substring(1), 16) : def;
   }

   public Zone zone(String id, Zone def) {
      return Zone.byId(this.string(id, def.id())).orElse(def);
   }

   public List<Object> list(String id) {
      this.load();
      return (List<Object>)(this.values.get(id) instanceof List<?> l ? l : List.of());
   }

   public Map<String, Object> object(String id) {
      this.load();
      return (Map<String, Object>)(this.values.get(id) instanceof Map<?, ?> m ? m : null);
   }

   public Object value(Param p) {
      return switch (p) {
         case Param.Toggle t -> this.bool(t.id(), t.def());
         case Param.Choice c -> this.string(c.id(), c.def());
         case Param.Range r -> this.dbl(r.id(), r.def());
         case Param.ZonePick z -> this.zone(z.id(), z.def()).id();
         case Param.Color cx -> this.color(cx.id(), cx.def());
         case Param.KeyBind k -> this.string(k.id(), "");
         default -> throw new MatchException(null, null);
      };
   }

   public synchronized void preview(String id, Object value) {
      revision++;
      this.load();
      this.values.put(id, value);
      this.apply(id);
   }

   public synchronized void reset(Collection<String> ids) {
      revision++;
      this.load();
      if (ids.contains("perf") && this.enabled("perf")) {
         this.values.put("perf.enabled", Boolean.FALSE);
         this.apply("perf.enabled");
      }

      this.values.keySet().removeIf(k -> ids.stream().anyMatch(id -> k.equals(id) || k.startsWith(id + ".")));

      try {
         Files.createDirectories(this.file.getParent());
         Files.writeString(this.file, Json.write(this.values), StandardCharsets.UTF_8);
      } catch (Exception var3) {
         LOG.accept("client-options save failed: " + var3);
      }

      this.apply(null);
   }

   public synchronized void set(String id, Object value) {
      this.load();
      revision++;
      if (value == null ? this.values.get(id) != null : !value.equals(this.values.get(id))) {
         if (value == null) {
            this.values.remove(id);
         } else {
            this.values.put(id, value);
         }

         try {
            Files.createDirectories(this.file.getParent());
            Files.writeString(this.file, Json.write(this.values), StandardCharsets.UTF_8);
         } catch (Exception var4) {
            LOG.accept("client-options save failed: " + var4);
         }

         this.apply(id);
      } else {
         this.apply(id);
      }
   }

   public void serverCombat(String style) {
      this.serverCombat = !"classic".equals(style) && !"modern".equals(style) ? null : style;
      this.apply("pvp.style");
   }

   public boolean serverCombatActive() {
      return this.serverCombat != null;
   }

   public boolean classicPvp() {
      String effective = this.serverCombat != null ? this.serverCombat : this.string("pvp.style", "modern");
      return "classic".equals(effective);
   }

   public boolean hideTutorial() {
      return "hide".equals(this.string("ui.tutorial", "show"));
   }

   public boolean pulsarTitleScreen() {
      return !"vanilla".equals(this.string("ui.titleScreen", "pulsar"));
   }

   public boolean enabled(String module) {
      return this.bool(
         module + ".enabled",
         "perf".equals(module)
            || "zoom".equals(module)
            || "controls".equals(module)
            || "toggles".equals(module)
            || "ping".equals(module)
            || "emotes".equals(module)
            || "scoreboard".equals(module)
            || "nametag".equals(module)
            || "freecam".equals(module)
            || "legit".equals(module)
            || "dynamicFov".equals(module)
            || "pulsarMark".equals(module)
      );
   }

   public void apply(String id) {
      this.hooks.apply(id);
   }

   public interface Hooks {
      void apply(String var1);
   }
}
