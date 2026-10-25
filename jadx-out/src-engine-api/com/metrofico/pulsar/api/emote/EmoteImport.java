package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Json;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class EmoteImport {
   private EmoteImport() {
   }

   public static EmoteImport.Result importBytes(byte[] bytes, EmoteImport.Options opts) {
      return GltfEmote.looksLike(bytes) ? GltfEmote.from(bytes, opts) : importAny(new String(bytes, StandardCharsets.UTF_8), opts);
   }

   public static EmoteImport.Result importAny(String text, EmoteImport.Options opts) {
      if (text.startsWith("Z2xURg")) {
         return GltfEmote.from(Base64.getDecoder().decode(text.strip()), opts);
      } else {
         Map<String, Object> o = Json.object(Json.parse(text), "emote");
         if (BlockbenchProject.looksLike(o)) {
            return BlockbenchProject.from(o, opts);
         } else if (Long.valueOf(1L).equals(o.get("format")) && o.get("bones") != null) {
            if (opts.id() != null) {
               o.put("id", opts.id());
            }

            if (opts.name() != null) {
               o.put("name", opts.name());
            }

            if (opts.author() != null) {
               o.put("author", opts.author());
            }

            return new EmoteImport.Result(PemoteJson.from(o), List.of());
         } else if (o.get("animations") != null) {
            return BedrockAnimation.from(o, opts);
         } else if (o.get("emote") == null && o.get("moves") == null) {
            throw new IllegalArgumentException(
               "formato no reconocido: se esperaba .glb de Blender, .bbmodel de Blockbench, .pemote, animation.json de Bedrock o JSON de Emotecraft"
            );
         } else {
            return EmotecraftEmote.from(o, opts);
         }
      }
   }

   static String fmt(double n) {
      return n == Math.rint(n) && Math.abs(n) < 1.0E15 ? String.valueOf((long)n) : String.valueOf(n);
   }

   static double round(double n) {
      double r = Math.round(n * 10000.0) / 10000.0;
      return r == 0.0 ? 0.0 : r;
   }

   static String slug(String s) {
      String x = s.toLowerCase(Locale.ROOT).replaceFirst("^animation\\.", "").replaceAll("[^a-z0-9]+", "-").replaceAll("^-+|-+$", "");
      if (!x.matches("^[a-z0-9].*")) {
         x = "e-" + x;
      }

      if (x.length() > 64) {
         x = x.substring(0, 64);
      }

      while (x.length() < 2) {
         x = x + "0";
      }

      return x;
   }

   static String titleOf(String s) {
      String base = s.replaceFirst("^animation\\.", "");
      String last = base.substring(base.lastIndexOf(46) + 1);
      String words = last.replaceAll("[_-]+", " ").strip();
      if (words.isEmpty()) {
         return "Emote";
      } else {
         String t = Character.toUpperCase(words.charAt(0)) + words.substring(1);
         return t.length() > 48 ? t.substring(0, 48) : t;
      }
   }

   static double component(Object v, String where) {
      if (v instanceof Number n && Double.isFinite(n.doubleValue())) {
         return n.doubleValue();
      } else if (v instanceof String s && s.matches("\\s*[-+]?(\\d+\\.?\\d*|\\.\\d+)([eE][-+]?\\d+)?\\s*")) {
         return Double.parseDouble(s.strip());
      } else {
         throw new IllegalArgumentException(where + ": valor no numérico (Molang no soportado): " + Json.write(v));
      }
   }

   static double[] vec(Object v, String where) {
      if (!(v instanceof Number) && !(v instanceof String)) {
         if (v instanceof List<?> l && l.size() == 3) {
            return new double[]{component(l.get(0), where), component(l.get(1), where), component(l.get(2), where)};
         } else {
            throw new IllegalArgumentException(where + ": se esperaban 3 componentes");
         }
      } else {
         double c = component(v, where);
         return new double[]{c, c, c};
      }
   }

   static String boneAlias(String name) {
      String n = name.replaceAll("[^A-Za-z0-9]", "").toLowerCase(Locale.ROOT);

      return switch (n) {
         case "head" -> "head";
         case "body", "torso", "chest" -> "body";
         case "rightarm", "armright", "rarm", "armr", "rightarmpart", "rightupperarm", "upperarmright" -> "right_arm";
         case "leftarm", "armleft", "larm", "arml", "leftupperarm", "upperarmleft" -> "left_arm";
         case "rightleg", "legright", "rleg", "legr", "rightupperleg", "upperlegright", "rightthigh" -> "right_leg";
         case "leftleg", "legleft", "lleg", "legl", "leftupperleg", "upperlegleft", "leftthigh" -> "left_leg";
         case "root" -> "root";
         case "bodylower", "lowerbody", "lowertorso", "torsolower" -> "body_lower";
         case "rightforearm", "forearmright", "forearmr", "rforearm", "lowerarmright", "rightlowerarm", "rightelbow" -> "right_forearm";
         case "leftforearm", "forearmleft", "forearml", "lforearm", "lowerarmleft", "leftlowerarm", "leftelbow" -> "left_forearm";
         case "rightshin", "shinright", "shinr", "rshin", "lowerlegright", "rightlowerleg", "rightcalf", "rightknee" -> "right_shin";
         case "leftshin", "shinleft", "shinl", "lshin", "lowerlegleft", "leftlowerleg", "leftcalf", "leftknee" -> "left_shin";
         default -> null;
      };
   }

   public record Options(String id, String name, String author, String animation, EmoteImport.SoundResolver sounds) {
      public static final EmoteImport.Options NONE = new EmoteImport.Options(null, null, null, null, null);

      public static EmoteImport.Options id(String id) {
         return new EmoteImport.Options(id, null, null, null, null);
      }

      public static EmoteImport.Options dev(String id, EmoteImport.SoundResolver sounds) {
         return new EmoteImport.Options(id, null, null, null, sounds);
      }
   }

   public record Result(Emote emote, List<String> warnings) {
   }

   @FunctionalInterface
   public interface SoundResolver {
      String sha256(String var1);
   }
}
