package com.metrofico.pulsar.engine.v26x.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.client.CloudStatus;
import net.minecraft.client.GraphicsPreset;
import net.minecraft.client.Minecraft;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.PreferredGraphicsApi;
import net.minecraft.server.level.ParticleStatus;

public final class PerfBooster {
   private PerfBooster() {
   }

   public static void apply() {
      Minecraft mc = Minecraft.getInstance();
      if (mc.options != null) {
         ClientOptions co = ClientOptions.get();
         Options o = mc.options;
         boolean on = co.enabled("perf");
         Map<String, Object> backup = co.object("perf.backup");
         if (!on) {
            if (backup != null) {
               restore(o, backup);
               co.set("perf.backup", null);
               o.save();
               BootstrapLog.info("perf: restored vanilla options");
            }
         } else {
            if (backup == null) {
               backup = snapshot(o);
               co.set("perf.backup", backup);
               BootstrapLog.info("perf: vanilla options saved " + backup);
            }

            if (co.isSet("perf.particles")) {
               OptionInstance var10000 = o.particles();
               String n = co.string("perf.particles", "all");

               var10000.set(switch (n) {
                  case "all" -> ParticleStatus.ALL;
                  case "decreased" -> ParticleStatus.DECREASED;
                  default -> ParticleStatus.MINIMAL;
               });
            } else if (backup.get("particles") instanceof String s) {
               o.particles().set(ParticleStatus.valueOf(s));
            }

            if (co.isSet("perf.shadows")) {
               o.entityShadows().set(co.bool("perf.shadows", true));
            } else if (backup.get("entityShadows") instanceof Boolean b) {
               o.entityShadows().set(b);
            }

            if (co.isSet("perf.clouds")) {
               OptionInstance var36 = o.cloudStatus();
               String var21 = co.string("perf.clouds", "vanilla");

               var36.set(switch (var21) {
                  case "fast" -> CloudStatus.FAST;
                  case "vanilla" -> CloudStatus.FANCY;
                  default -> CloudStatus.OFF;
               });
            } else if (backup.get("cloudStatus") instanceof String s) {
               o.cloudStatus().set(CloudStatus.valueOf(s));
            }

            if (co.isSet("perf.biomeBlend")) {
               o.biomeBlendRadius().set(co.integer("perf.biomeBlend", 2));
            } else if (backup.get("biomeBlendRadius") instanceof Number n) {
               o.biomeBlendRadius().set(n.intValue());
            }

            if (co.bool("perf.limitDistance", false)) {
               o.renderDistance().set(Math.max(2, Math.min(32, co.integer("perf.renderDistance", 8))));
            } else if (backup.get("renderDistance") instanceof Number n) {
               o.renderDistance().set(n.intValue());
            }

            if (co.isSet("perf.entityDistance")) {
               o.entityDistanceScaling().set(Math.max(0.5, Math.min(1.5, co.dbl("perf.entityDistance", 1.0))));
            } else if (backup.get("entityDistanceScaling") instanceof Number n) {
               o.entityDistanceScaling().set(n.doubleValue());
            }

            if (co.isSet("perf.smoothLighting")) {
               o.ambientOcclusion().set(co.bool("perf.smoothLighting", true));
            } else if (backup.get("ambientOcclusion") instanceof Boolean b) {
               o.ambientOcclusion().set(b);
            }

            if (co.isSet("perf.fastLeaves")) {
               o.cutoutLeaves().set(!co.bool("perf.fastLeaves", false));
            } else if (backup.get("cutoutLeaves") instanceof Boolean b) {
               o.cutoutLeaves().set(b);
            }

            if (co.isSet("perf.improvedTransparency")) {
               o.improvedTransparency().set(co.bool("perf.improvedTransparency", false));
            } else if (backup.get("improvedTransparency") instanceof Boolean b) {
               o.improvedTransparency().set(b);
            }

            if (co.isSet("perf.vsync")) {
               o.enableVsync().set(co.bool("perf.vsync", false));
            } else if (backup.get("enableVsync") instanceof Boolean b) {
               o.enableVsync().set(b);
            }

            String var30 = co.string("perf.backend", "auto");

            PreferredGraphicsApi api = switch (var30) {
               case "opengl" -> PreferredGraphicsApi.OPENGL;
               case "vulkan" -> PreferredGraphicsApi.VULKAN;
               default -> null;
            };
            if (api != null && o.preferredGraphicsBackend().get() != api) {
               o.preferredGraphicsBackend().set(api);
               BootstrapLog.info("perf: preferred graphics backend -> " + api + " (restart required)");
            }

            if (co.isSet("perf.fpsLimit")) {
               int limit = co.integer("perf.fpsLimit", 260);
               o.framerateLimit().set(limit >= 260 ? 260 : limit);
            } else if (backup.get("framerateLimit") instanceof Number n) {
               o.framerateLimit().set(n.intValue());
            }

            o.save();
         }
      }
   }

   private static Map<String, Object> snapshot(Options o) {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("graphicsPreset", ((GraphicsPreset)o.graphicsPreset().get()).name());
      m.put("particles", ((ParticleStatus)o.particles().get()).name());
      m.put("entityShadows", o.entityShadows().get());
      m.put("cloudStatus", ((CloudStatus)o.cloudStatus().get()).name());
      m.put("biomeBlendRadius", o.biomeBlendRadius().get());
      m.put("renderDistance", o.renderDistance().get());
      m.put("entityDistanceScaling", o.entityDistanceScaling().get());
      m.put("ambientOcclusion", o.ambientOcclusion().get());
      m.put("cutoutLeaves", o.cutoutLeaves().get());
      m.put("improvedTransparency", o.improvedTransparency().get());
      m.put("enableVsync", o.enableVsync().get());
      m.put("framerateLimit", o.framerateLimit().get());
      m.put("preferredGraphicsBackend", ((PreferredGraphicsApi)o.preferredGraphicsBackend().get()).name());
      return m;
   }

   private static void restore(Options o, Map<String, Object> m) {
      try {
         if (m.get("particles") instanceof String s) {
            o.particles().set(ParticleStatus.valueOf(s));
         }

         if (m.get("entityShadows") instanceof Boolean b) {
            o.entityShadows().set(b);
         }

         if (m.get("cloudStatus") instanceof String s) {
            o.cloudStatus().set(CloudStatus.valueOf(s));
         }

         if (m.get("biomeBlendRadius") instanceof Number n) {
            o.biomeBlendRadius().set(n.intValue());
         }

         if (m.get("renderDistance") instanceof Number n) {
            o.renderDistance().set(n.intValue());
         }

         if (m.get("entityDistanceScaling") instanceof Number n) {
            o.entityDistanceScaling().set(n.doubleValue());
         }

         if (m.get("ambientOcclusion") instanceof Boolean b) {
            o.ambientOcclusion().set(b);
         }

         if (m.get("cutoutLeaves") instanceof Boolean b) {
            o.cutoutLeaves().set(b);
         }

         if (m.get("improvedTransparency") instanceof Boolean b) {
            o.improvedTransparency().set(b);
         }

         if (m.get("enableVsync") instanceof Boolean b) {
            o.enableVsync().set(b);
         }

         if (m.get("framerateLimit") instanceof Number n) {
            o.framerateLimit().set(n.intValue());
         }

         if (m.get("preferredGraphicsBackend") instanceof String s) {
            o.preferredGraphicsBackend().set(PreferredGraphicsApi.valueOf(s));
         }

         if (m.get("graphicsPreset") instanceof String s && !"CUSTOM".equals(s)) {
            o.graphicsPreset().set(GraphicsPreset.valueOf(s));
         }
      } catch (RuntimeException var4) {
         BootstrapLog.info("perf: restore failed: " + var4);
      }
   }
}
