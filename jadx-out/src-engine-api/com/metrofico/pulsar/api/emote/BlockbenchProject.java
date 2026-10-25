package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Json;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class BlockbenchProject {
   private static final Map<String, String> CHANNELS = Map.of("rotation", "rotation", "position", "position", "scale", "scale");

   private BlockbenchProject() {
   }

   public static boolean looksLike(Map<String, Object> root) {
      return root.get("meta") != null && root.get("outliner") != null;
   }

   // $VF: One or more variable merging failures!
   // $VF: Could not properly define all variable types!
   // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
   public static EmoteImport.Result from(Map<String, Object> root, EmoteImport.Options opts) {
      List<String> warnings = new ArrayList<>();
      Map<String, Map<String, Object>> elements = new HashMap<>();

      <unknown> var10000;
      for (Object e : var10000_1) {
         if (e instanceof Map<?, ?> m && m.get("uuid") instanceof String u) {
            elements.put(u, (Map<String, Object>)m);
         }
      }

      List<String> textures = new ArrayList<>();

      <unknown> var90;
      for (Object t : var90_1) {
         String src = t instanceof Map<?, ?> m && m.get("source") instanceof String s ? s : "";
         textures.add(src.replaceFirst("^data:[^,]*,", ""));
      }

      int texW = 16;
      int texH = 16;
      if (root.get("resolution") instanceof Map<?, ?> res) {
         texW = res.get("width") instanceof Number n ? n.intValue() : 16;
         texH = res.get("height") instanceof Number nx ? nx.intValue() : 16;
      }

      boolean boxUv = root.get("meta") instanceof Map<?, ?> meta && Boolean.TRUE.equals(meta.get("box_uv"));
      List<Object> outliner = (List<Object>)(root.get("outliner") instanceof List<?> l ? l : List.of());
      Map<String, String> groupNames = new HashMap<>();
      List<Emote.Prop> props = new ArrayList<>();
      new BlockbenchProject.Walker(elements, textures, texW, texH, boxUv, outliner, groupNames, props, warnings).walk(outliner, null, false);
      List<Object> anims = (List<Object>)(root.get("animations") instanceof List<?> lx ? lx : List.of());
      if (anims.isEmpty()) {
         throw new IllegalArgumentException("el .bbmodel no tiene animaciones: crea una en la pestaña Animate y guarda el proyecto");
      } else {
         Map<String, Object> bedrock = new LinkedHashMap<>();

         for (Object ao : anims) {
            Map<String, Object> a = Json.object(ao, "bbmodel.animations[]");
            Map<String, Map<String, Map<String, Object>>> bones = new LinkedHashMap<>();
            Map<String, Object> soundEffects = new LinkedHashMap<>();
            boolean bezier = false;
            Map<String, Object> animators = (Map<String, Object>)(a.get("animators") instanceof Map<?, ?> m ? m : Map.of());

            for (Entry<String, Object> en : animators.entrySet()) {
               Map<String, Object> animator = Json.object(en.getValue(), "bbmodel.animators");
               Map<String, Object> el = elements.get(en.getKey());
               Object elType = el != null ? el.get("type") : null;
               if (!"null_object".equals(elType) && !"locator".equals(elType)) {
                  String name = animator.get("name") instanceof String s ? s : groupNames.getOrDefault(en.getKey(), en.getKey());
                  boolean effect = "effect".equals(animator.get("type"));

                  <unknown> var99;
                  for (Object ko : var99_1) {
                     Map<String, Object> kf = Json.object(ko, "bbmodel.keyframes[]");
                     String t = EmoteImport.fmt(EmoteImport.round(kf.get("time") instanceof Number n ? n.doubleValue() : 0.0));
                     List<Object> dps = (List<Object>)(kf.get("data_points") instanceof List<?> lxx ? lxx : List.of());
                     String channel = kf.get("channel") instanceof String sx ? sx : "";
                     if (!effect && !channel.equals("sound") && !channel.equals("particle") && !channel.equals("timeline")) {
                        String ch = CHANNELS.get(channel);
                        if (ch != null) {
                           Map<String, Object> key = new LinkedHashMap<>();
                           key.put("post", vec(dps.isEmpty() ? Map.of() : Json.object(dps.get(dps.size() - 1), "bbmodel.key")));
                           if (dps.size() > 1) {
                              key.put("pre", vec(Json.object(dps.get(0), "bbmodel.key")));
                           }

                           Object interp = kf.get("interpolation");
                           if ("catmullrom".equals(interp)) {
                              key.put("lerp_mode", "catmullrom");
                           } else if ("step".equals(interp)) {
                              key.put("easing", "step");
                           } else if ("bezier".equals(interp)) {
                              bezier = true;
                           }

                           if (kf.get("easing") instanceof String es && !es.equals("linear")) {
                              key.put("easing", es);
                           }

                           bones.computeIfAbsent(name, k -> new LinkedHashMap<>()).computeIfAbsent(ch, k -> new LinkedHashMap<>()).put(t, key);
                        }
                     } else if (channel.equals("sound")) {
                        Map<String, Object> dp = dps.isEmpty() ? Map.of() : Json.object(dps.get(0), "bbmodel.sound");
                        String eff = dp.get("effect") instanceof String sxx && !sxx.isEmpty()
                           ? sxx
                           : (dp.get("file") instanceof String f ? f.replaceAll(".*[\\\\/]", "").replaceAll("(?i)\\.ogg$", "") : "");
                        if (!eff.isEmpty()) {
                           soundEffects.put(t, Map.of("effect", eff));
                        }
                     }
                  }
               }
            }

            if (bezier) {
               warnings.add("animación " + a.get("name") + ": las curvas Bézier se importan como lineales (usa las curvas del plugin GeckoLib)");
            }

            Map<String, Object> anim = new LinkedHashMap<>();
            double length = a.get("length") instanceof Number n ? n.doubleValue() : 0.0;
            if (length > 0.0) {
               anim.put("animation_length", length);
            }

            anim.put("loop", "loop".equals(a.get("loop")));
            anim.put("bones", bones);
            if (!soundEffects.isEmpty()) {
               anim.put("sound_effects", soundEffects);
            }

            bedrock.put(String.valueOf(a.get("name") == null ? "emote" : a.get("name")), anim);
         }

         EmoteImport.Result r = BedrockAnimation.from(Map.of("animations", bedrock), opts);
         List<String> all = new ArrayList<>(warnings);
         all.addAll(r.warnings());
         Emote ex = r.emote();
         if (!props.isEmpty()) {
            ex = PemoteJson.parse(
               PemoteJson.write(
                  new Emote(
                     ex.id(), ex.name(), ex.author(), ex.duration(), ex.loop(), ex.cancelOnMove(), ex.camera(), ex.bones(), List.copyOf(props), ex.sounds()
                  )
               )
            );
         }

         return new EmoteImport.Result(ex, List.copyOf(all));
      }
   }

   private static List<Object> vec(Map<String, Object> dp) {
      List<Object> v = new ArrayList<>(3);

      for (String k : new String[]{"x", "y", "z"}) {
         v.add(dp.get(k) == null ? 0 : dp.get(k));
      }

      return v;
   }

   private record Walker(
      Map<String, Map<String, Object>> elements,
      List<String> textures,
      int texW,
      int texH,
      boolean boxUv,
      List<Object> outliner,
      Map<String, String> groupNames,
      List<Emote.Prop> props,
      List<String> warnings
   ) {
      void walk(List<Object> nodes, String anchor, boolean inProp) {
         for (Object n : nodes) {
            if (n instanceof Map<?, ?> gm) {
               String name = gm.get("name") instanceof String s ? s : null;
               if (gm.get("uuid") instanceof String u && name != null) {
                  this.groupNames.put(u, name);
               }

               String bone = name != null ? EmoteImport.boneAlias(name) : null;
               List<Object> children = (List<Object>)(gm.get("children") instanceof List<?> l ? l : List.of());
               if (bone != null) {
                  this.walk(children, bone, false);
               } else if (inProp) {
                  this.warnings.add("grupo " + name + " dentro de un prop: sus cubos se aplanan en el prop");
                  this.walk(children, anchor, true);
               } else {
                  if (this.props.size() >= 3) {
                     throw new IllegalArgumentException("más de 3 props (grupos que no son huesos del jugador)");
                  }

                  double[] origin = nums(gm.get("origin"), 3, 0.0);
                  List<Emote.Prop.Cube> cubes = new ArrayList<>();
                  int[] texture = new int[]{-1};
                  this.collect(children, (Map<String, Object>)gm, origin, cubes, texture);
                  if (cubes.isEmpty()) {
                     this.warnings.add("grupo " + name + " sin cubos: se ignora");
                  } else {
                     String tex = this.textures.isEmpty() ? "" : this.textures.get(texture[0] < 0 ? 0 : Math.min(texture[0], this.textures.size() - 1));
                     if (tex.isEmpty()) {
                        throw new IllegalArgumentException("prop " + name + ": sin textura embebida en el proyecto");
                     }

                     double[] parent = anchorOrigin(this.outliner, anchor);
                     float[] offset = new float[]{(float)(origin[0] - parent[0]), (float)(-(origin[1] - parent[1])), (float)(origin[2] - parent[2])};
                     double[] rot = nums(gm.get("rotation"), 3, 0.0);
                     float[] rotation = new float[]{(float)rot[0], (float)rot[1], (float)rot[2]};
                     String id = EmoteImport.slug(name == null ? "prop" : name);
                     this.props
                        .add(
                           new Emote.Prop(
                              id.isEmpty() ? "prop-" + (this.props.size() + 1) : id,
                              anchor,
                              offset,
                              rotation,
                              1.0,
                              List.copyOf(cubes),
                              Base64.getDecoder().decode(tex),
                              this.texW,
                              this.texH,
                              null
                           )
                        );
                  }
               }
            }
         }
      }

      private void collect(List<Object> kids, Map<String, Object> g, double[] origin, List<Emote.Prop.Cube> cubes, int[] texture) {
         for (Object k : kids) {
            if (k instanceof String uuid) {
               Map<String, Object> el = this.elements.get(uuid);
               if (el != null && el.get("from") instanceof List && el.get("to") instanceof List) {
                  boolean elBox = el.get("box_uv") instanceof Boolean b ? b : this.boxUv;
                  if (!elBox) {
                     throw new IllegalArgumentException("prop " + g.get("name") + ": usa Box UV (per-face UV no está soportado en props)");
                  }

                  double[] elRot = nums(el.get("rotation"), 3, 0.0);
                  if (elRot[0] != 0.0 || elRot[1] != 0.0 || elRot[2] != 0.0) {
                     this.warnings.add("prop " + g.get("name") + ": la rotación del cubo " + el.get("name") + " se ignora");
                  }

                  double[] from = nums(el.get("from"), 3, 0.0);
                  double[] to = nums(el.get("to"), 3, 0.0);
                  double[] uv = nums(el.get("uv_offset"), 2, 0.0);
                  double inflate = el.get("inflate") instanceof Number n ? n.doubleValue() : 0.0;
                  double rx = from[0] - origin[0];
                  double ry = from[1] - origin[1];
                  double rz = from[2] - origin[2];
                  double sx = to[0] - from[0];
                  double sy = to[1] - from[1];
                  double sz = to[2] - from[2];
                  cubes.add(
                     new Emote.Prop.Cube(
                        new float[]{(float)rx, (float)(-(ry + sy)), (float)rz},
                        new float[]{(float)sx, (float)sy, (float)sz},
                        new float[]{(float)uv[0], (float)uv[1]},
                        (float)inflate
                     )
                  );
                  if (texture[0] < 0 && el.get("faces") instanceof Map<?, ?> faces) {
                     Iterator var44 = faces.values().iterator();

                     while (var44.hasNext()) {
                        if (var44.next() instanceof Map<?, ?> fm && fm.get("texture") instanceof Number nx) {
                           texture[0] = nx.intValue();
                           break;
                        }
                     }
                  }
               }
            } else if (k instanceof Map<?, ?> sm) {
               if (sm.get("name") instanceof String sn && EmoteImport.boneAlias(sn) != null) {
                  throw new IllegalArgumentException("el hueso del jugador " + sn + " está dentro del prop " + g.get("name"));
               }

               double[] sr = nums(sm.get("rotation"), 3, 0.0);
               if (sr[0] != 0.0 || sr[1] != 0.0 || sr[2] != 0.0) {
                  this.warnings.add("prop " + g.get("name") + ": la rotación del grupo " + sm.get("name") + " se ignora (se aplana)");
               }

               this.collect((List<Object>)(sm.get("children") instanceof List<?> l ? l : List.of()), g, origin, cubes, texture);
            }
         }
      }

      private static double[] anchorOrigin(List<Object> nodes, String bone) {
         if (bone == null) {
            return new double[]{0.0, 0.0, 0.0};
         } else {
            for (Object n : nodes) {
               if (n instanceof Map<?, ?> gm) {
                  if (gm.get("name") instanceof String s && bone.equals(EmoteImport.boneAlias(s))) {
                     return nums(gm.get("origin"), 3, 0.0);
                  }

                  double[] r = anchorOrigin((List<Object>)(gm.get("children") instanceof List<?> l ? l : List.of()), bone);
                  if (r != null) {
                     return r;
                  }
               }
            }

            return null;
         }
      }

      private static double[] nums(Object v, int n, double def) {
         double[] out = new double[n];
         Arrays.fill(out, def);
         if (v instanceof List<?> l) {
            for (int i = 0; i < n && i < l.size(); i++) {
               if (l.get(i) instanceof Number num) {
                  out[i] = num.doubleValue();
               }
            }
         }

         return out;
      }
   }
}
