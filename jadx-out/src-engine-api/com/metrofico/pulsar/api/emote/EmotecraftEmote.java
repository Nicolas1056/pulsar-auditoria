package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Json;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class EmotecraftEmote {
   private static final String[][] PARTS = new String[][]{
      {"head", "head"}, {"torso", "body"}, {"rightArm", "right_arm"}, {"leftArm", "left_arm"}, {"rightLeg", "right_leg"}, {"leftLeg", "left_leg"}
   };

   private EmotecraftEmote() {
   }

   public static EmoteImport.Result parse(String json, EmoteImport.Options opts) {
      return from(Json.object(Json.parse(json), "emotecraft"), opts);
   }

   public static EmoteImport.Result from(Map<String, Object> root, EmoteImport.Options opts) {
      Map<String, Object> emote = (Map<String, Object>)(root.get("emote") instanceof Map<?, ?> m ? m : root);
      if (!(emote.get("moves") instanceof List<?> movesRaw)) {
         throw new IllegalArgumentException("no es un emote de Emotecraft (falta \"emote.moves\")");
      } else {
         ArrayList var41 = new ArrayList();
         boolean degrees = Boolean.TRUE.equals(emote.get("degrees"));
         double endTick = emote.get("endTick") instanceof Number n ? n.doubleValue() : 0.0;
         List<Map<String, Object>> moves = new ArrayList<>();

         for (Object o : movesRaw) {
            if (o instanceof Map<?, ?> mx) {
               moves.add((Map<String, Object>)mx);
            }
         }

         moves.sort((a, bx) -> Double.compare(tick((Map<String, Object>)a), tick(bx)));
         double lastTick = 0.0;

         for (Map<String, Object> mv : moves) {
            lastTick = Math.max(lastTick, tick(mv));
         }

         double durationTicks = endTick > 0.0 ? endTick : lastTick;
         double duration = EmoteImport.round(Math.max(0.05, durationTicks / 20.0));
         if (duration > 30.0) {
            throw new IllegalArgumentException("dura " + duration + " s; el máximo es 30.0");
         } else {
            if (Boolean.TRUE.equals(emote.get("isLoop")) && emote.get("returnTick") instanceof Number rt && rt.doubleValue() > 0.0) {
               var41.add("returnTick no soportado: el bucle vuelve al principio");
            }

            Map<String, Map<String, List<Object>>> bones = new LinkedHashMap<>();
            Map<String, double[][]> last = new HashMap<>();
            boolean bendWarned = false;

            for (Map<String, Object> mv : moves) {
               double tk = tick(mv);
               double t = EmoteImport.round(tk / 20.0);
               if (!(t > duration)) {
                  Easing e = null;
                  if (mv.get("easing") instanceof String es) {
                     e = Easing.byName(es);
                     if (e == null) {
                        e = Easing.byName("ease" + es);
                     }

                     if (e == null) {
                        throw new IllegalArgumentException("tick " + (long)tk + ": curva desconocida " + es);
                     }
                  }

                  for (String[] part : PARTS) {
                     if (mv.get(part[0]) instanceof Map<?, ?> pRaw) {
                        double[][] prev = last.computeIfAbsent(part[1], k -> new double[][]{{0.0, 0.0, 0.0}, {0.0, 0.0, 0.0}});
                        String child = childOf(part[1]);
                        if (child != null && has((Map<String, Object>)pRaw, "bend")) {
                           double bendDeg = deg(pRaw.get("bend"), degrees);
                           double axis = pRaw.get("axis") instanceof Number ax ? ax.doubleValue() : 0.0;
                           double[] rot = new double[]{EmoteImport.round(bendDeg * Math.cos(axis)), 0.0, EmoteImport.round(bendDeg * Math.sin(axis))};
                           push(bones.computeIfAbsent(child, k -> new LinkedHashMap<>()).computeIfAbsent("rot", k -> new ArrayList<>()), t, rot, e);
                           if (!bendWarned) {
                              var41.add("bends importados de forma aproximada (codo/rodilla/cadera de la malla v2)");
                              bendWarned = true;
                           }
                        }

                        Map<String, List<Object>> b = bones.computeIfAbsent(part[1], k -> new LinkedHashMap<>());
                        boolean torso = part[1].equals("body");
                        double[] rs = torso ? new double[]{-1.0, -1.0, 1.0} : new double[]{1.0, 1.0, 1.0};
                        double[] ps = torso ? new double[]{-16.0, -16.0, 16.0} : new double[]{1.0, 1.0, 1.0};
                        if (has((Map<String, Object>)pRaw, "pitch") || has((Map<String, Object>)pRaw, "yaw") || has((Map<String, Object>)pRaw, "roll")) {
                           double[] rot = new double[]{
                              EmoteImport.round(has((Map<String, Object>)pRaw, "pitch") ? deg(pRaw.get("pitch"), degrees) * rs[0] : prev[0][0]),
                              EmoteImport.round(has((Map<String, Object>)pRaw, "yaw") ? deg(pRaw.get("yaw"), degrees) * rs[1] : prev[0][1]),
                              EmoteImport.round(has((Map<String, Object>)pRaw, "roll") ? deg(pRaw.get("roll"), degrees) * rs[2] : prev[0][2])
                           };
                           prev[0] = rot;
                           push(b.computeIfAbsent("rot", k -> new ArrayList<>()), t, rot, e);
                        }

                        if (has((Map<String, Object>)pRaw, "x") || has((Map<String, Object>)pRaw, "y") || has((Map<String, Object>)pRaw, "z")) {
                           double[] pos = new double[]{
                              EmoteImport.round(has((Map<String, Object>)pRaw, "x") ? ((Number)pRaw.get("x")).doubleValue() * ps[0] : prev[1][0]),
                              EmoteImport.round(has((Map<String, Object>)pRaw, "y") ? ((Number)pRaw.get("y")).doubleValue() * ps[1] : prev[1][1]),
                              EmoteImport.round(has((Map<String, Object>)pRaw, "z") ? ((Number)pRaw.get("z")).doubleValue() * ps[2] : prev[1][2])
                           };
                           prev[1] = pos;
                           push(b.computeIfAbsent("pos", k -> new ArrayList<>()), t, pos, e);
                        }
                     }
                  }
               }
            }

            bones.values().removeIf(bx -> {
               bx.values().removeIf(List::isEmpty);
               return bx.isEmpty();
            });
            if (bones.isEmpty()) {
               throw new IllegalArgumentException("el emote no mueve ninguna parte del jugador");
            } else {
               for (Map<String, List<Object>> bx : bones.values()) {
                  for (List<Object> ch : bx.values()) {
                     ((Map)ch.get(ch.size() - 1)).remove("e");
                  }
               }

               String base = root.get("name") instanceof String nx ? nx : "emote";
               Map<String, Object> ox = new LinkedHashMap<>();
               ox.put("format", 1L);
               ox.put("id", opts.id() != null ? opts.id() : EmoteImport.slug(base));
               ox.put(
                  "name",
                  opts.name() != null
                     ? opts.name()
                     : (root.get("name") instanceof String nxx ? (nxx.length() > 48 ? nxx.substring(0, 48) : nxx) : EmoteImport.titleOf(base))
               );
               String author = opts.author() != null ? opts.author() : (root.get("author") instanceof String a ? a : null);
               if (author != null) {
                  ox.put("author", author.length() > 64 ? author.substring(0, 64) : author);
               }

               ox.put("duration", duration);
               if (Boolean.TRUE.equals(emote.get("isLoop"))) {
                  ox.put("loop", true);
               }

               Map<String, Object> bonesOut = new LinkedHashMap<>();

               for (Entry<String, Map<String, List<Object>>> e : bones.entrySet()) {
                  bonesOut.put(e.getKey(), new LinkedHashMap<>(e.getValue()));
               }

               ox.put("bones", bonesOut);
               return new EmoteImport.Result(PemoteJson.from(ox), var41);
            }
         }
      }
   }

   private static String childOf(String bone) {
      for (Entry<String, String> e : Emote.PARENT.entrySet()) {
         if (e.getValue().equals(bone)) {
            return e.getKey();
         }
      }

      return null;
   }

   private static double tick(Map<String, Object> mv) {
      return mv.get("tick") instanceof Number n ? n.doubleValue() : 0.0;
   }

   private static boolean has(Map<String, Object> p, String k) {
      return p.get(k) instanceof Number;
   }

   private static double deg(Object v, boolean degrees) {
      double d = ((Number)v).doubleValue();
      return degrees ? d : d * 180.0 / Math.PI;
   }

   private static void push(List<Object> keys, double t, double[] v, Easing e) {
      List<Object> vl = List.of(v[0], v[1], v[2]);
      if (!keys.isEmpty()) {
         Map<String, Object> prev = (Map<String, Object>)keys.get(keys.size() - 1);
         if ((Double)prev.get("t") == t) {
            prev.put("v", vl);
            if (e != null && e != Easing.LINEAR) {
               prev.put("e", e.jsonName());
            } else {
               prev.remove("e");
            }

            return;
         }
      }

      keys.add(BedrockAnimation.key(t, vl, e != null && e != Easing.LINEAR ? e : null));
   }
}
