package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Json;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

public final class PemoteJson {
   public static final int FORMAT = 1;
   private static final Pattern ID = Pattern.compile("[a-z0-9][a-z0-9-]{1,63}");
   private static final Pattern SHA256 = Pattern.compile("[0-9a-f]{64}");

   private PemoteJson() {
   }

   public static Emote parse(String json) {
      if (json.getBytes(StandardCharsets.UTF_8).length > 262144) {
         throw new IllegalArgumentException("pemote: larger than 262144 bytes");
      } else {
         Map<String, Object> o = Json.object(Json.parse(json), "pemote");
         return from(o);
      }
   }

   public static Emote from(Map<String, Object> o) {
      long format = Json.integer(o, "format", "pemote");
      if (format != 1L) {
         throw new IllegalArgumentException("pemote: unsupported format " + format);
      } else {
         String id = Json.string(o, "id", "pemote");
         if (!ID.matcher(id).matches()) {
            throw new IllegalArgumentException("pemote: bad id \"" + id + "\"");
         } else {
            String name = o.get("name") instanceof String s && !s.isBlank() ? s.strip() : id;
            if (name.length() > 48) {
               throw new IllegalArgumentException("pemote: name longer than 48");
            } else {
               String author = o.get("author") instanceof String sx ? sx.strip() : "";
               if (author.length() > 64) {
                  throw new IllegalArgumentException("pemote: author longer than 64");
               } else {
                  double duration = number(o.get("duration"), "duration");
                  if (duration > 0.0 && !(duration > 30.0)) {
                     boolean loop = o.get("loop") instanceof Boolean b && b;
                     boolean cancelOnMove = !(o.get("cancelOnMove") instanceof Boolean bx && !bx);
                     Emote.Camera camera = camera(o.get("camera"));
                     Map<String, Object> bonesJson = Json.object(o.get("bones"), "pemote.bones");
                     Map<String, Emote.Bone> bones = new LinkedHashMap<>();
                     int keys = 0;

                     for (String bone : Emote.BONE_ORDER) {
                        Object raw = bonesJson.get(bone);
                        if (raw != null) {
                           Map<String, Object> bj = Json.object(raw, "pemote.bones." + bone);
                           double maxOffset = bone.equals("root") ? 160.0 : 32.0;
                           Emote.Bone bxx = new Emote.Bone(
                              channel(bj.get("rot"), bone + ".rot", duration, -720.0, 720.0),
                              channel(bj.get("pos"), bone + ".pos", duration, -maxOffset, maxOffset),
                              channel(bj.get("scale"), bone + ".scale", duration, 0.05, 4.0)
                           );
                           if (!bxx.isEmpty()) {
                              bones.put(bone, bxx);
                              keys += bxx.keyCount();
                           }
                        }
                     }

                     for (String k : bonesJson.keySet()) {
                        if (!Emote.BONES.contains(k)) {
                           throw new IllegalArgumentException("pemote: unknown bone \"" + k + "\"");
                        }
                     }

                     if (bones.isEmpty()) {
                        throw new IllegalArgumentException("pemote: no animated bones");
                     } else if (keys > 2000) {
                        throw new IllegalArgumentException("pemote: more than 2000 keyframes");
                     } else {
                        List<Emote.Prop> props = props(o.get("props"), duration);
                        List<Emote.Sound> sounds = new ArrayList<>();
                        if (o.get("sounds") != null) {
                           List<Object> arr = Json.array(o.get("sounds"), "pemote.sounds");
                           if (arr.size() > 4) {
                              throw new IllegalArgumentException("pemote.sounds: more than 4");
                           }

                           for (Object item : arr) {
                              Map<String, Object> sj = Json.object(item, "pemote.sounds[]");
                              String sha = Json.string(sj, "sha256", "pemote.sounds[]");
                              if (!SHA256.matcher(sha).matches()) {
                                 throw new IllegalArgumentException("pemote.sounds: bad sha256");
                              }

                              double at = sj.get("at") == null ? 0.0 : number(sj.get("at"), "sounds.at");
                              if (at < 0.0 || at > duration) {
                                 throw new IllegalArgumentException("pemote.sounds: at out of range");
                              }

                              double volume = sj.get("volume") == null ? 1.0 : number(sj.get("volume"), "sounds.volume");
                              double pitch = sj.get("pitch") == null ? 1.0 : number(sj.get("pitch"), "sounds.pitch");
                              if (volume <= 0.0 || volume > 1.0 || pitch < 0.5 || pitch > 2.0) {
                                 throw new IllegalArgumentException("pemote.sounds: volume (0,1] and pitch [0.5,2]");
                              }

                              sounds.add(new Emote.Sound(sha, at, (float)volume, (float)pitch));
                           }
                        }

                        return new Emote(id, name, author, duration, loop, cancelOnMove, camera, Map.copyOf(bones), props, List.copyOf(sounds));
                     }
                  } else {
                     throw new IllegalArgumentException("pemote: duration must be in (0, 30.0]");
                  }
               }
            }
         }
      }
   }

   private static List<Emote.Prop> props(Object raw, double duration) {
      if (raw == null) {
         return List.of();
      } else {
         List<Object> arr = Json.array(raw, "pemote.props");
         if (arr.size() > 3) {
            throw new IllegalArgumentException("pemote.props: more than 3");
         } else {
            List<Emote.Prop> out = new ArrayList<>();
            int cubesTotal = 0;
            int trianglesTotal = 0;

            for (Object item : arr) {
               Map<String, Object> p = Json.object(item, "pemote.props[]");
               String id = Json.string(p, "id", "pemote.props[]");
               if (!ID.matcher(id).matches()) {
                  throw new IllegalArgumentException("pemote.props: bad id \"" + id + "\"");
               }

               String bone = p.get("bone") instanceof String b && !b.isEmpty() ? b : "root";
               if (!Emote.BONES.contains(bone)) {
                  throw new IllegalArgumentException("pemote.props." + id + ": unknown bone \"" + bone + "\"");
               }

               float[] offset = vec3(p.get("offset"), id + ".offset", -160.0, 160.0, 0.0);
               float[] rotation = vec3(p.get("rotation"), id + ".rotation", -720.0, 720.0, 0.0);
               double scale = p.get("scale") == null ? 1.0 : number(p.get("scale"), id + ".scale");
               if (!(scale < 0.05) && !(scale > 4.0)) {
                  boolean hasMesh = p.get("mesh") != null;
                  List<Emote.Prop.Cube> cubes = new ArrayList<>();
                  Emote.Prop.Mesh mesh = null;
                  if (hasMesh && p.get("cubes") instanceof List<?> cl && !cl.isEmpty()) {
                     throw new IllegalArgumentException("pemote.props." + id + ": cubes or mesh, not both");
                  }

                  if (!hasMesh) {
                     for (Object c : Json.array(p.get("cubes"), "pemote.props." + id + ".cubes")) {
                        Map<String, Object> cj = Json.object(c, id + ".cubes[]");
                        float inflate = cj.get("inflate") == null ? 0.0F : (float)number(cj.get("inflate"), id + ".inflate");
                        if (inflate < -8.0F || inflate > 8.0F) {
                           throw new IllegalArgumentException("pemote.props." + id + ": inflate out of range");
                        }

                        cubes.add(
                           new Emote.Prop.Cube(
                              vec3(cj.get("origin"), id + ".origin", -160.0, 160.0, 0.0),
                              vec3(cj.get("size"), id + ".size", 0.0, 64.0, 1.0),
                              vec3uv(cj.get("uv"), id + ".uv"),
                              inflate
                           )
                        );
                     }

                     if (cubes.isEmpty()) {
                        throw new IllegalArgumentException("pemote.props." + id + ": no cubes");
                     }

                     cubesTotal += cubes.size();
                     if (cubesTotal > 48) {
                        throw new IllegalArgumentException("pemote.props: more than 48 cubes");
                     }
                  } else {
                     Map<String, Object> m = Json.object(p.get("mesh"), "pemote.props." + id + ".mesh");
                     float[] pos = numbers(m.get("pos"), "pemote.props." + id + ".mesh.pos", -160.0, 160.0, false);
                     if (pos.length % 3 != 0) {
                        throw new IllegalArgumentException("pemote.props." + id + ".mesh.pos: length must be a multiple of 3");
                     }

                     int vertices = pos.length / 3;
                     float[] uv = numbers(m.get("uv"), "pemote.props." + id + ".mesh.uv", -0.01, 1.01, false);
                     if (uv.length != vertices * 2) {
                        throw new IllegalArgumentException("pemote.props." + id + ".mesh.uv: expected 2 per vertex");
                     }

                     float[] idxF = numbers(m.get("idx"), "pemote.props." + id + ".mesh.idx", 0.0, vertices - 1, true);
                     if (idxF.length % 3 != 0) {
                        throw new IllegalArgumentException("pemote.props." + id + ".mesh.idx: length must be a multiple of 3");
                     }

                     int[] idx = new int[idxF.length];

                     for (int i = 0; i < idx.length; i++) {
                        idx[i] = (int)idxF[i];
                     }

                     trianglesTotal += idx.length / 3;
                     if (trianglesTotal > 1500) {
                        throw new IllegalArgumentException("pemote.props: more than 1500 triangles");
                     }

                     mesh = new Emote.Prop.Mesh(pos, uv, idx);
                  }

                  byte[] texture;
                  try {
                     texture = Base64.getDecoder().decode(Json.string(p, "texture", "pemote.props." + id));
                  } catch (IllegalArgumentException var28) {
                     throw new IllegalArgumentException("pemote.props." + id + ": texture is not base64");
                  }

                  int maxTexBytes = mesh != null ? 65536 : '耀';
                  int maxTexSize = mesh != null ? 256 : 128;
                  if (texture.length > maxTexBytes) {
                     throw new IllegalArgumentException("pemote.props." + id + ": texture larger than " + maxTexBytes + " bytes");
                  }

                  if (texture.length >= 8 && (texture[1] & 255) == 80 && (texture[2] & 255) == 78 && (texture[3] & 255) == 71) {
                     List<Object> ts = Json.array(p.get("textureSize"), "pemote.props." + id + ".textureSize");
                     int tw = (int)number(ts.get(0), id + ".textureSize");
                     int th = (int)number(ts.get(1), id + ".textureSize");
                     if (tw >= 8 && th >= 8 && tw <= maxTexSize && th <= maxTexSize && Integer.bitCount(tw) == 1 && Integer.bitCount(th) == 1) {
                        Emote.Bone anim = null;
                        if (p.get("anim") instanceof Map<?, ?> am) {
                           Map<String, Object> aj = Json.object(am, id + ".anim");
                           anim = new Emote.Bone(
                              channel(aj.get("rot"), id + ".anim.rot", duration, -720.0, 720.0),
                              channel(aj.get("pos"), id + ".anim.pos", duration, -160.0, 160.0),
                              channel(aj.get("scale"), id + ".anim.scale", duration, 0.05, 4.0)
                           );
                        }

                        out.add(new Emote.Prop(id, bone, offset, rotation, scale, List.copyOf(cubes), texture, tw, th, anim, mesh));
                        continue;
                     }

                     throw new IllegalArgumentException("pemote.props." + id + ": textureSize must be a power of two ≤ " + maxTexSize);
                  }

                  throw new IllegalArgumentException("pemote.props." + id + ": texture is not a PNG");
               }

               throw new IllegalArgumentException("pemote.props." + id + ": scale out of range");
            }

            return List.copyOf(out);
         }
      }
   }

   private static float[] numbers(Object raw, String what, double min, double max, boolean integer) {
      List<Object> l = Json.array(raw, what);
      if (l.isEmpty()) {
         throw new IllegalArgumentException(what + ": expected non-empty array");
      } else {
         float[] out = new float[l.size()];

         for (int i = 0; i < out.length; i++) {
            double n = number(l.get(i), what);
            if (n < min || n > max || integer && n != Math.rint(n)) {
               throw new IllegalArgumentException(what + ": out of range");
            }

            out[i] = (float)n;
         }

         return out;
      }
   }

   private static float[] vec3(Object raw, String what, double min, double max, double def) {
      if (raw == null) {
         return new float[]{(float)def, (float)def, (float)def};
      } else {
         List<Object> l = Json.array(raw, "pemote." + what);
         if (l.size() != 3) {
            throw new IllegalArgumentException("pemote." + what + ": expected 3 numbers");
         } else {
            float[] out = new float[3];

            for (int i = 0; i < 3; i++) {
               double d = number(l.get(i), what);
               if (d < min || d > max) {
                  throw new IllegalArgumentException("pemote." + what + ": " + d + " out of range");
               }

               out[i] = (float)d;
            }

            return out;
         }
      }
   }

   private static float[] vec3uv(Object raw, String what) {
      List<Object> l = Json.array(raw, "pemote." + what);
      if (l.size() != 2) {
         throw new IllegalArgumentException("pemote." + what + ": expected [u, v]");
      } else {
         double u = number(l.get(0), what);
         double v = number(l.get(1), what);
         if (!(u < 0.0) && !(v < 0.0) && !(u > 256.0) && !(v > 256.0)) {
            return new float[]{(float)u, (float)v, 0.0F};
         } else {
            throw new IllegalArgumentException("pemote." + what + ": uv out of range");
         }
      }
   }

   public static String write(Emote e) {
      Map<String, Object> o = new LinkedHashMap<>();
      o.put("format", 1L);
      o.put("id", e.id());
      o.put("name", e.name());
      if (!e.author().isEmpty()) {
         o.put("author", e.author());
      }

      o.put("duration", e.duration());
      if (e.loop()) {
         o.put("loop", true);
      }

      if (!e.cancelOnMove()) {
         o.put("cancelOnMove", false);
      }

      if (e.camera() != Emote.Camera.FRONT) {
         o.put("camera", e.camera().name().toLowerCase(Locale.ROOT));
      }

      Map<String, Object> bones = new LinkedHashMap<>();

      for (String name : Emote.BONE_ORDER) {
         Emote.Bone b = e.bone(name);
         if (b != null && !b.isEmpty()) {
            Map<String, Object> bj = new LinkedHashMap<>();
            if (!b.rot().isEmpty()) {
               bj.put("rot", keys(b.rot()));
            }

            if (!b.pos().isEmpty()) {
               bj.put("pos", keys(b.pos()));
            }

            if (!b.scale().isEmpty()) {
               bj.put("scale", keys(b.scale()));
            }

            bones.put(name, bj);
         }
      }

      o.put("bones", bones);
      if (!e.props().isEmpty()) {
         List<Object> props = new ArrayList<>();

         for (Emote.Prop p : e.props()) {
            Map<String, Object> pj = new LinkedHashMap<>();
            pj.put("id", p.id());
            if (!p.bone().equals("root")) {
               pj.put("bone", p.bone());
            }

            if (p.offset()[0] != 0.0F || p.offset()[1] != 0.0F || p.offset()[2] != 0.0F) {
               pj.put("offset", floats(p.offset()));
            }

            if (p.rotation()[0] != 0.0F || p.rotation()[1] != 0.0F || p.rotation()[2] != 0.0F) {
               pj.put("rotation", floats(p.rotation()));
            }

            if (p.scale() != 1.0) {
               pj.put("scale", p.scale());
            }

            if (p.mesh() != null) {
               Map<String, Object> mj = new LinkedHashMap<>();
               List<Object> pos = new ArrayList<>();
               List<Object> uv = new ArrayList<>();
               List<Object> idx = new ArrayList<>();

               for (float f : p.mesh().pos()) {
                  pos.add(fl(f));
               }

               for (float f : p.mesh().uv()) {
                  uv.add(fl(f));
               }

               for (int i : p.mesh().idx()) {
                  idx.add((long)i);
               }

               mj.put("pos", pos);
               mj.put("uv", uv);
               mj.put("idx", idx);
               pj.put("mesh", mj);
            } else {
               List<Object> cubes = new ArrayList<>();

               for (Emote.Prop.Cube c : p.cubes()) {
                  Map<String, Object> cj = new LinkedHashMap<>();
                  cj.put("origin", floats(c.origin()));
                  cj.put("size", floats(c.size()));
                  cj.put("uv", List.of(fl(c.uv()[0]), fl(c.uv()[1])));
                  if (c.inflate() != 0.0F) {
                     cj.put("inflate", fl(c.inflate()));
                  }

                  cubes.add(cj);
               }

               pj.put("cubes", cubes);
            }

            pj.put("texture", Base64.getEncoder().encodeToString(p.texture()));
            pj.put("textureSize", List.of((long)p.texWidth(), (long)p.texHeight()));
            if (p.anim() != null && !p.anim().isEmpty()) {
               Map<String, Object> aj = new LinkedHashMap<>();
               if (!p.anim().rot().isEmpty()) {
                  aj.put("rot", keys(p.anim().rot()));
               }

               if (!p.anim().pos().isEmpty()) {
                  aj.put("pos", keys(p.anim().pos()));
               }

               if (!p.anim().scale().isEmpty()) {
                  aj.put("scale", keys(p.anim().scale()));
               }

               pj.put("anim", aj);
            }

            props.add(pj);
         }

         o.put("props", props);
      }

      if (!e.sounds().isEmpty()) {
         List<Object> sounds = new ArrayList<>();

         for (Emote.Sound snd : e.sounds()) {
            Map<String, Object> s = new LinkedHashMap<>();
            s.put("sha256", snd.sha256());
            if (snd.at() != 0.0) {
               s.put("at", snd.at());
            }

            if (snd.volume() != 1.0F) {
               s.put("volume", fl(snd.volume()));
            }

            if (snd.pitch() != 1.0F) {
               s.put("pitch", fl(snd.pitch()));
            }

            sounds.add(s);
         }

         o.put("sounds", sounds);
      }

      return Json.canonical(o);
   }

   private static List<Object> keys(List<Emote.Key> keys) {
      List<Object> out = new ArrayList<>(keys.size());

      for (int i = 0; i < keys.size(); i++) {
         Emote.Key k = keys.get(i);
         Map<String, Object> kj = new LinkedHashMap<>();
         kj.put("t", k.t());
         kj.put("v", List.of(fl(k.x()), fl(k.y()), fl(k.z())));
         if (i < keys.size() - 1 && k.easing() != Easing.LINEAR) {
            kj.put("e", k.easing().jsonName());
         }

         out.add(kj);
      }

      return out;
   }

   private static List<Object> floats(float[] v) {
      List<Object> out = new ArrayList<>(v.length);

      for (float f : v) {
         out.add(fl(f));
      }

      return out;
   }

   private static double fl(float v) {
      double d = Double.parseDouble(Float.toString(v));
      return d == 0.0 ? 0.0 : d;
   }

   private static List<Emote.Key> channel(Object raw, String what, double duration, double min, double max) {
      if (raw == null) {
         return List.of();
      } else {
         List<Object> arr = Json.array(raw, "pemote." + what);
         List<Emote.Key> keys = new ArrayList<>(arr.size());
         double lastT = -1.0;

         for (Object item : arr) {
            Map<String, Object> kj = Json.object(item, "pemote." + what);
            double t = number(kj.get("t"), what + ".t");
            if (!(t < 0.0) && !(t > duration)) {
               if (t <= lastT) {
                  throw new IllegalArgumentException("pemote." + what + ": keys must be in increasing time order");
               }

               lastT = t;
               List<Object> v = Json.array(kj.get("v"), "pemote." + what + ".v");
               if (v.size() != 3) {
                  throw new IllegalArgumentException("pemote." + what + ".v: expected 3 numbers");
               }

               float[] c = new float[3];

               for (int i = 0; i < 3; i++) {
                  double d = number(v.get(i), what + ".v");
                  if (d < min || d > max) {
                     throw new IllegalArgumentException("pemote." + what + ".v: " + d + " out of [" + min + ", " + max + "]");
                  }

                  c[i] = (float)d;
               }

               Easing easing = Easing.LINEAR;
               if (kj.get("e") == null || kj.get("e") instanceof String es && (easing = Easing.byName(es)) != null) {
                  keys.add(new Emote.Key(t, c[0], c[1], c[2], easing));
                  continue;
               }

               throw new IllegalArgumentException("pemote." + what + ": unknown easing " + kj.get("e"));
            }

            throw new IllegalArgumentException("pemote." + what + ": t out of [0, duration]");
         }

         return List.copyOf(keys);
      }
   }

   private static Emote.Camera camera(Object v) {
      if (v == null) {
         return Emote.Camera.FRONT;
      } else {
         if (v instanceof String s) {
            String var2 = s.toLowerCase(Locale.ROOT);
            switch (var2) {
               case "front":
                  return Emote.Camera.FRONT;
               case "back":
                  return Emote.Camera.BACK;
               case "none":
                  return Emote.Camera.NONE;
            }
         }

         throw new IllegalArgumentException("pemote: camera must be front|back|none");
      }
   }

   private static double number(Object v, String what) {
      if (v instanceof Number n) {
         double d = n.doubleValue();
         if (!Double.isNaN(d) && !Double.isInfinite(d)) {
            return d;
         } else {
            throw new IllegalArgumentException("pemote." + what + ": not a finite number");
         }
      } else {
         throw new IllegalArgumentException("pemote." + what + ": expected number");
      }
   }
}
