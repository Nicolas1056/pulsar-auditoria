package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Json;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class BedrockAnimation {
   public static final double[] ROT_SIGN = new double[]{1.0, 1.0, 1.0};
   public static final double[] POS_SIGN = new double[]{1.0, -1.0, 1.0};
   private static final double[] ONE = new double[]{1.0, 1.0, 1.0};

   private BedrockAnimation() {
   }

   public static EmoteImport.Result parse(String json, EmoteImport.Options opts) {
      return from(Json.object(Json.parse(json), "animation.json"), opts);
   }

   public static EmoteImport.Result from(Map<String, Object> root, EmoteImport.Options opts) {
      if (!(root.get("animations") instanceof Map<?, ?> animsRaw)) {
         throw new IllegalArgumentException("no es un animation.json de Bedrock (falta \"animations\")");
      } else if (animsRaw.isEmpty()) {
         throw new IllegalArgumentException("el archivo no tiene animaciones");
      } else {
         String chosen = opts.animation() != null ? opts.animation() : (String)animsRaw.keySet().iterator().next();
         if (!(animsRaw.get(chosen) instanceof Map<?, ?> animRaw)) {
            throw new IllegalArgumentException(
               "no existe la animación \"" + chosen + "\" (hay: " + String.join(", ", (Iterable<? extends CharSequence>)animsRaw.keySet()) + ")"
            );
         } else {
            ArrayList warnings = new ArrayList();
            if (animsRaw.size() > 1 && opts.animation() == null) {
               warnings.add("el archivo tiene " + animsRaw.size() + " animaciones; se usó " + chosen);
            }

            Map<String, Object> bones = new LinkedHashMap<>();
            List<String> unknown = new ArrayList<>();
            double maxT = 0.0;
            Map<String, Object> bonesRaw = (Map<String, Object>)(animRaw.get("bones") instanceof Map<?, ?> m ? m : Map.of());

            for (Entry<String, Object> e : bonesRaw.entrySet()) {
               String bone = EmoteImport.boneAlias(e.getKey());
               if (bone != null) {
                  if (!(e.getValue() instanceof Map<?, ?> bRaw)) {
                     throw new IllegalArgumentException("hueso " + e.getKey() + ": inválido");
                  }

                  List o = toKeys(channel(bRaw.get("rotation"), e.getKey() + ".rotation"), ROT_SIGN, false);
                  List pos = toKeys(channel(bRaw.get("position"), e.getKey() + ".position"), POS_SIGN, false);
                  List ex = toKeys(channel(bRaw.get("scale"), e.getKey() + ".scale"), ONE, true);
                  LinkedHashMap at = new LinkedHashMap();
                  if (!o.isEmpty()) {
                     at.put("rot", o);
                  }

                  if (!pos.isEmpty()) {
                     at.put("pos", pos);
                  }

                  if (!ex.isEmpty()) {
                     at.put("scale", ex);
                  }

                  if (!at.isEmpty()) {
                     if (bones.containsKey(bone)) {
                        warnings.add("hueso " + e.getKey() + " repetido (" + bone + "): se conservó el primero");
                     } else {
                        bones.put(bone, at);
                     }

                     for (List<Object> ch : List.of(o, pos, ex)) {
                        for (Object k : ch) {
                           maxT = Math.max(maxT, (Double)((Map)k).get("t"));
                        }
                     }
                  }
               } else {
                  unknown.add(e.getKey());
               }
            }

            if (!unknown.isEmpty()) {
               warnings.add("huesos ignorados (no son del jugador): " + String.join(", ", unknown));
            }

            if (bones.isEmpty()) {
               throw new IllegalArgumentException("ninguno de los huesos es del jugador (head, body, right_arm, left_arm, right_leg, left_leg)");
            } else {
               double length = animRaw.get("animation_length") instanceof Number n ? n.doubleValue() : maxT;
               double duration = EmoteImport.round(Math.max(0.05, length));
               if (duration > 30.0) {
                  throw new IllegalArgumentException("dura " + duration + " s; el máximo es 30.0");
               } else {
                  for (Object b : bones.values()) {
                     for (Object ch : ((Map)b).values()) {
                        for (Object k : (List)ch) {
                           if ((Double)((Map)k).get("t") > duration) {
                              throw new IllegalArgumentException("hay claves después de animation_length (" + duration + " s)");
                           }
                        }
                     }
                  }

                  List<Object> sounds = new ArrayList<>();
                  if (animRaw.get("sound_effects") instanceof Map<?, ?> seRaw) {
                     Iterator var42 = seRaw.entrySet().iterator();

                     label174:
                     while (true) {
                        Entry<String, Object> exx;
                        double atx;
                        while (true) {
                           if (!var42.hasNext()) {
                              break label174;
                           }

                           exx = (Entry<String, Object>)var42.next();

                           try {
                              atx = EmoteImport.round(Double.parseDouble(exx.getKey()));
                              break;
                           } catch (NumberFormatException var28) {
                           }
                        }

                        String effect = (exx.getValue() instanceof List<?> l && !l.isEmpty() ? l.get(0) : exx.getValue()) instanceof Map<?, ?> mx
                              && mx.get("effect") instanceof String ef
                           ? ef
                           : null;
                        if (effect != null && !(atx < 0.0) && !(atx > duration)) {
                           String sha = opts.sounds() == null ? null : opts.sounds().sha256(effect);
                           if (sha == null) {
                              warnings.add("sonido «" + effect + "» ignorado (sin archivo .ogg)");
                           } else if (sounds.size() < 4) {
                              Map<String, Object> sj = new LinkedHashMap<>();
                              sj.put("sha256", sha);
                              if (atx != 0.0) {
                                 sj.put("at", atx);
                              }

                              sounds.add(sj);
                           }
                        }
                     }
                  }

                  Map<String, Object> ox = new LinkedHashMap<>();
                  ox.put("format", 1L);
                  ox.put("id", opts.id() != null ? opts.id() : EmoteImport.slug(chosen));
                  ox.put("name", opts.name() != null ? opts.name() : EmoteImport.titleOf(chosen));
                  if (opts.author() != null) {
                     ox.put("author", opts.author());
                  }

                  ox.put("duration", duration);
                  if (Boolean.TRUE.equals(animRaw.get("loop"))) {
                     ox.put("loop", true);
                  }

                  ox.put("bones", bones);
                  if (!sounds.isEmpty()) {
                     ox.put("sounds", sounds);
                  }

                  return new EmoteImport.Result(PemoteJson.from(ox), warnings);
               }
            }
         }
      }
   }

   private static List<BedrockAnimation.RawKey> channel(Object raw, String where) {
      List<BedrockAnimation.RawKey> keys = new ArrayList<>();
      if (raw == null) {
         return keys;
      } else if (!(raw instanceof List) && !(raw instanceof Number) && !(raw instanceof String)) {
         if (!(raw instanceof Map<?, ?> m)) {
            throw new IllegalArgumentException(where + ": canal inválido");
         } else {
            for (Entry<String, Object> e : m.entrySet()) {
               double t;
               try {
                  t = Double.parseDouble(e.getKey());
               } catch (NumberFormatException var16) {
                  t = Double.NaN;
               }

               if (Double.isNaN(t) || t < 0.0) {
                  throw new IllegalArgumentException(where + ": tiempo inválido \"" + e.getKey() + "\"");
               }

               String w = where + "@" + e.getKey();
               Object val = e.getValue();
               if (val instanceof Map<?, ?> oRaw) {
                  Object post = oRaw.get("vector") != null ? oRaw.get("vector") : oRaw.get("post");
                  if (post == null) {
                     throw new IllegalArgumentException(w + ": clave sin valor");
                  }

                  Easing easing = null;
                  if (oRaw.get("easing") instanceof String es) {
                     easing = Easing.byName(es);
                     if (easing == null) {
                        throw new IllegalArgumentException(w + ": curva desconocida " + es);
                     }
                  }

                  keys.add(
                     new BedrockAnimation.RawKey(
                        t,
                        EmoteImport.vec(post, w),
                        oRaw.get("pre") != null ? EmoteImport.vec(oRaw.get("pre"), w) : null,
                        easing,
                        "catmullrom".equals(oRaw.get("lerp_mode"))
                     )
                  );
               } else {
                  keys.add(new BedrockAnimation.RawKey(t, EmoteImport.vec(val, w), null, null, false));
               }
            }

            keys.sort((a, b) -> Double.compare(a.t(), b.t()));
            return keys;
         }
      } else {
         keys.add(new BedrockAnimation.RawKey(0.0, EmoteImport.vec(raw, where), null, null, false));
         return keys;
      }
   }

   private static List<Object> toKeys(List<BedrockAnimation.RawKey> raw, double[] signs, boolean isScale) {
      List<Object> out = new ArrayList<>();

      for (int i = 0; i < raw.size(); i++) {
         BedrockAnimation.RawKey k = raw.get(i);
         BedrockAnimation.RawKey next = i + 1 < raw.size() ? raw.get(i + 1) : null;
         Easing e = next != null ? next.easingIn() : null;
         if (k.catmull() || next != null && next.catmull()) {
            e = Easing.CATMULLROM;
         }

         if (k.pre() != null && k.t() >= 0.002 && (k.pre()[0] != k.v()[0] || k.pre()[1] != k.v()[1] || k.pre()[2] != k.v()[2])) {
            out.add(key(EmoteImport.round(k.t() - 0.001), conv(k.pre(), signs, isScale), Easing.STEP));
         }

         out.add(key(EmoteImport.round(k.t()), conv(k.v(), signs, isScale), e != null && e != Easing.LINEAR && next != null ? e : null));
      }

      return out;
   }

   private static List<Object> conv(double[] v, double[] signs, boolean isScale) {
      List<Object> l = new ArrayList<>(3);

      for (int i = 0; i < 3; i++) {
         l.add(EmoteImport.round(isScale ? v[i] : v[i] * signs[i]));
      }

      return l;
   }

   static Map<String, Object> key(double t, List<Object> v, Easing e) {
      Map<String, Object> k = new LinkedHashMap<>();
      k.put("t", t);
      k.put("v", v);
      if (e != null) {
         k.put("e", e.jsonName());
      }

      return k;
   }

   private record RawKey(double t, double[] v, double[] pre, Easing easingIn, boolean catmull) {
   }
}
