package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public final class PerfBooster189 {
   private static final String BACKUP_KEY = "perf.backup";
   private static final List<PerfBooster189.ManagedOption> OPTIONS = List.of(
      new PerfBooster189.ManagedOption("particle", o -> o.aM, (o, v) -> o.aM = asInt(v), co -> {
         Integer var3;
         if (co.isSet("perf.particles")) {
            String s0$ = co.string("perf.particles", "all");

            var3 = switch (s0$) {
               case "decreased" -> 1;
               case "minimal" -> 2;
               default -> 0;
            };
         } else {
            var3 = null;
         }

         return var3;
      }),
      new PerfBooster189.ManagedOption(
         "entityShadows", o -> o.W, (o, v) -> o.W = (Boolean)v, co -> co.isSet("perf.shadows") ? co.bool("perf.shadows", true) : null
      ),
      new PerfBooster189.ManagedOption("cloudMode", o -> o.h, (o, v) -> o.h = asInt(v), co -> {
         Integer var3;
         if (co.isSet("perf.clouds")) {
            String s2$ = co.string("perf.clouds", "vanilla");

            var3 = switch (s2$) {
               case "off" -> 0;
               case "fast" -> 1;
               default -> 2;
            };
         } else {
            var3 = null;
         }

         return var3;
      }),
      new PerfBooster189.ManagedOption(
         "viewDistance",
         o -> o.c,
         (o, v) -> o.c = asInt(v),
         co -> co.bool("perf.limitDistance", false) ? Math.max(2, Math.min(32, co.integer("perf.renderDistance", 8))) : null
      ),
      new PerfBooster189.ManagedOption(
         "ao", o -> o.j, (o, v) -> o.j = asInt(v), co -> co.isSet("perf.smoothLighting") ? co.bool("perf.smoothLighting", true) ? 2 : 0 : null
      ),
      new PerfBooster189.ManagedOption("vsync", o -> o.t, (o, v) -> o.t = (Boolean)v, co -> co.isSet("perf.vsync") ? co.bool("perf.vsync", false) : null),
      new PerfBooster189.ManagedOption(
         "maxFramerate",
         o -> o.g,
         (o, v) -> o.g = asInt(v),
         co -> co.isSet("perf.fpsLimit") ? Math.max(10, Math.min(260, co.integer("perf.fpsLimit", 260))) : null
      )
   );
   private static final Map<PerfBooster189.ManagedOption, Object> BOOSTED_DURING_SAVE = new HashMap<>();

   private PerfBooster189() {
   }

   public static void apply() {
      avh options = ave.A().t;
      if (options != null) {
         ClientOptions clientOptions = ClientOptions.get();
         Map<String, Object> savedVanilla = clientOptions.object("perf.backup");
         if (!clientOptions.enabled("perf")) {
            if (savedVanilla != null) {
               OPTIONS.forEach(optionx -> writeIfValid(optionx, options, savedVanilla.get(optionx.key())));
               clientOptions.set("perf.backup", null);
               ClientOptions.LOG.accept("perf: restored vanilla options");
            }
         } else {
            Map<String, Object> vanilla = savedVanilla;
            if (savedVanilla == null) {
               vanilla = snapshot(options);
               clientOptions.set("perf.backup", vanilla);
               ClientOptions.LOG.accept("perf: vanilla options saved " + vanilla);
            }

            for (PerfBooster189.ManagedOption option : OPTIONS) {
               Object boosted = option.override().apply(clientOptions);
               writeIfValid(option, options, boosted != null ? boosted : vanilla.get(option.key()));
            }
         }
      }
   }

   public static void beforeSave(avh options) {
      BOOSTED_DURING_SAVE.clear();
      ClientOptions clientOptions = ClientOptions.get();
      Map<String, Object> vanilla = clientOptions.object("perf.backup");
      if (clientOptions.enabled("perf") && vanilla != null) {
         Map<String, Object> updatedVanilla = new LinkedHashMap<>(vanilla);

         for (PerfBooster189.ManagedOption option : OPTIONS) {
            if (option.override().apply(clientOptions) != null) {
               BOOSTED_DURING_SAVE.put(option, option.read().apply(options));
               writeIfValid(option, options, vanilla.get(option.key()));
            } else {
               updatedVanilla.put(option.key(), option.read().apply(options));
            }
         }

         if (!updatedVanilla.equals(vanilla)) {
            clientOptions.set("perf.backup", updatedVanilla);
         }
      }
   }

   public static void afterSave(avh options) {
      BOOSTED_DURING_SAVE.forEach((option, boosted) -> option.write().accept(options, boosted));
      BOOSTED_DURING_SAVE.clear();
   }

   private static Map<String, Object> snapshot(avh options) {
      Map<String, Object> values = new LinkedHashMap<>();
      OPTIONS.forEach(option -> values.put(option.key(), option.read().apply(options)));
      return values;
   }

   private static void writeIfValid(PerfBooster189.ManagedOption option, avh options, Object value) {
      Object current = option.read().apply(options);
      boolean sameKind = current instanceof Boolean && value instanceof Boolean || current instanceof Number && value instanceof Number;
      if (sameKind) {
         option.write().accept(options, value);
      }
   }

   private static int asInt(Object value) {
      return ((Number)value).intValue();
   }

   private record ManagedOption(String key, Function<avh, Object> read, BiConsumer<avh, Object> write, Function<ClientOptions, Object> override) {
   }
}
