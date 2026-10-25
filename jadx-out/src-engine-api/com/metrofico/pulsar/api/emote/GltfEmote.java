package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.pack.Json;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.regex.Pattern;

final class GltfEmote {
   static final Map<String, double[]> PIVOTS = Map.ofEntries(
      Map.entry("head", new double[]{0.0, 24.0, 0.0}),
      Map.entry("body", new double[]{0.0, 24.0, 0.0}),
      Map.entry("body_lower", new double[]{0.0, 18.0, 0.0}),
      Map.entry("right_arm", new double[]{-5.0, 22.0, 0.0}),
      Map.entry("right_forearm", new double[]{-6.0, 18.0, 0.0}),
      Map.entry("left_arm", new double[]{5.0, 22.0, 0.0}),
      Map.entry("left_forearm", new double[]{6.0, 18.0, 0.0}),
      Map.entry("right_leg", new double[]{-1.9, 12.0, 0.0}),
      Map.entry("right_shin", new double[]{-2.0, 6.0, 0.0}),
      Map.entry("left_leg", new double[]{1.9, 12.0, 0.0}),
      Map.entry("left_shin", new double[]{2.0, 6.0, 0.0}),
      Map.entry("root", new double[]{0.0, 12.0, 0.0})
   );
   private static final double EPS_DEG = 0.5;
   private static final double EPS_PX = 0.1;
   private static final int[] EPS_STEPS = new int[]{1, 2, 4, 8, 16};
   private static final Pattern HELPER = Pattern.compile("^(ik_|pole_|mch_|ctrl_|def_|org_|vis_)", 2);

   private GltfEmote() {
   }

   static boolean looksLike(byte[] bytes) {
      return bytes.length >= 4 && bytes[0] == 103 && bytes[1] == 108 && bytes[2] == 84 && bytes[3] == 70;
   }

   private static double[] qinv(double[] q) {
      return new double[]{-q[0], -q[1], -q[2], q[3]};
   }

   private static double[] qnorm(double[] q) {
      double l = Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
      if (l == 0.0) {
         l = 1.0;
      }

      return new double[]{q[0] / l, q[1] / l, q[2] / l, q[3] / l};
   }

   private static double[][] qmat(double[] q) {
      double x = q[0];
      double y = q[1];
      double z = q[2];
      double w = q[3];
      return new double[][]{
         {1.0 - 2.0 * (y * y + z * z), 2.0 * (x * y - z * w), 2.0 * (x * z + y * w)},
         {2.0 * (x * y + z * w), 1.0 - 2.0 * (x * x + z * z), 2.0 * (y * z - x * w)},
         {2.0 * (x * z - y * w), 2.0 * (y * z + x * w), 1.0 - 2.0 * (x * x + y * y)}
      };
   }

   private static double[][] mmul(double[][] a, double[][] b) {
      double[][] r = new double[3][3];

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            r[i][j] = a[i][0] * b[0][j] + a[i][1] * b[1][j] + a[i][2] * b[2][j];
         }
      }

      return r;
   }

   private static double[] mvec(double[][] m, double[] v) {
      return new double[]{
         m[0][0] * v[0] + m[0][1] * v[1] + m[0][2] * v[2], m[1][0] * v[0] + m[1][1] * v[1] + m[1][2] * v[2], m[2][0] * v[0] + m[2][1] * v[1] + m[2][2] * v[2]
      };
   }

   private static double[][] transpose(double[][] m) {
      return new double[][]{{m[0][0], m[1][0], m[2][0]}, {m[0][1], m[1][1], m[2][1]}, {m[0][2], m[1][2], m[2][2]}};
   }

   private static double[][] rotOf(List<Object> m4) {
      double[][] r = new double[3][3];

      for (int c = 0; c < 3; c++) {
         double x = num(m4.get(c * 4));
         double y = num(m4.get(c * 4 + 1));
         double z = num(m4.get(c * 4 + 2));
         double l = Math.sqrt(x * x + y * y + z * z);
         if (l == 0.0) {
            l = 1.0;
         }

         r[0][c] = x / l;
         r[1][c] = y / l;
         r[2][c] = z / l;
      }

      return r;
   }

   private static double[] mquat(double[][] m) {
      double tr = m[0][0] + m[1][1] + m[2][2];
      double[] q;
      if (tr > 0.0) {
         double s = Math.sqrt(tr + 1.0) * 2.0;
         q = new double[]{(m[2][1] - m[1][2]) / s, (m[0][2] - m[2][0]) / s, (m[1][0] - m[0][1]) / s, 0.25 * s};
      } else if (m[0][0] > m[1][1] && m[0][0] > m[2][2]) {
         double s = Math.sqrt(1.0 + m[0][0] - m[1][1] - m[2][2]) * 2.0;
         q = new double[]{0.25 * s, (m[0][1] + m[1][0]) / s, (m[0][2] + m[2][0]) / s, (m[2][1] - m[1][2]) / s};
      } else if (m[1][1] > m[2][2]) {
         double s = Math.sqrt(1.0 + m[1][1] - m[0][0] - m[2][2]) * 2.0;
         q = new double[]{(m[0][1] + m[1][0]) / s, 0.25 * s, (m[1][2] + m[2][1]) / s, (m[0][2] - m[2][0]) / s};
      } else {
         double s = Math.sqrt(1.0 + m[2][2] - m[0][0] - m[1][1]) * 2.0;
         q = new double[]{(m[0][2] + m[2][0]) / s, (m[1][2] + m[2][1]) / s, 0.25 * s, (m[1][0] - m[0][1]) / s};
      }

      return qnorm(q);
   }

   private static double[][] toModel(double[][] m) {
      double[] c = new double[]{1.0, -1.0, -1.0};
      double[][] r = new double[3][3];

      for (int i = 0; i < 3; i++) {
         for (int j = 0; j < 3; j++) {
            r[i][j] = c[i] * m[i][j] * c[j];
         }
      }

      return r;
   }

   private static double[] eulerZYX(double[][] m) {
      double sy = Math.max(-1.0, Math.min(1.0, -m[2][0]));
      double y = Math.asin(sy);
      double x;
      double z;
      if (Math.abs(Math.cos(y)) < 1.0E-6) {
         x = 0.0;
         z = Math.atan2(-m[0][1], m[1][1]);
      } else {
         x = Math.atan2(m[2][1], m[2][2]);
         z = Math.atan2(m[1][0], m[0][0]);
      }

      double d = 180.0 / Math.PI;
      return new double[]{x * d, y * d, z * d};
   }

   private static double[] unwrap(double[] v, double[] prev) {
      if (prev == null) {
         return v;
      } else {
         double[] out = new double[3];

         for (int i = 0; i < 3; i++) {
            double a = v[i];

            while (a - prev[i] > 180.0) {
               a -= 360.0;
            }

            while (a - prev[i] < -180.0) {
               a += 360.0;
            }

            out[i] = a;
         }

         return out;
      }
   }

   private static int integer(Map<String, Object> o, String key, int def) {
      return o.get(key) instanceof Number n ? n.intValue() : def;
   }

   private static double num(Object v) {
      return v instanceof Number n ? n.doubleValue() : 0.0;
   }

   private static double[] vec(Object raw, int n, double[] def) {
      if (raw instanceof List<?> l && l.size() == n) {
         double[] out = new double[n];

         for (int i = 0; i < n; i++) {
            out[i] = num(l.get(i));
         }

         return out;
      } else {
         return def;
      }
   }

   private static Map<String, Object> key(double t, double[] v) {
      Map<String, Object> k = new LinkedHashMap<>();
      k.put("t", EmoteImport.round(t));
      k.put("v", List.of(EmoteImport.round(v[0]), EmoteImport.round(v[1]), EmoteImport.round(v[2])));
      return k;
   }

   private static List<Object> reduce(List<double[]> timesL, List<double[]> values, double eps) {
      int n = timesL.size();
      List<Object> out = new ArrayList<>();
      if (n == 0) {
         return out;
      } else {
         double[] times = new double[n];

         for (int i = 0; i < n; i++) {
            times[i] = timesL.get(i)[0];
         }

         List<Integer> keep = new ArrayList<>();
         keep.add(0);
         int anchor = 0;

         for (int j = 2; j <= n; j++) {
            int end = Math.min(j, n - 1);
            boolean ok = true;

            for (int k = anchor + 1; k < end && ok; k++) {
               double span = times[end] - times[anchor];
               double f = (times[k] - times[anchor]) / (span == 0.0 ? 1.0 : span);

               for (int c = 0; c < 3 && ok; c++) {
                  double lerp = values.get(anchor)[c] + (values.get(end)[c] - values.get(anchor)[c]) * f;
                  if (Math.abs(lerp - values.get(k)[c]) > eps) {
                     ok = false;
                  }
               }
            }

            if (!ok) {
               anchor = j - 1;
               keep.add(anchor);
            }

            if (end == n - 1 && ok) {
               break;
            }
         }

         if (keep.get(keep.size() - 1) != n - 1) {
            keep.add(n - 1);
         }

         for (int i : keep) {
            out.add(key(times[i], values.get(i)));
         }

         return out;
      }
   }

   private static boolean constant(List<double[]> values, double eps) {
      for (double[] v : values) {
         if (Math.abs(v[0]) > eps || Math.abs(v[1]) > eps || Math.abs(v[2]) > eps) {
            return false;
         }
      }

      return true;
   }

   private static String slug(String s) {
      String out = s.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]+", "-").replaceAll("^-+|-+$", "");
      return out.length() > 64 ? out.substring(0, 64) : out;
   }

   private static double round2(double n) {
      double r = Math.round(n * 100.0) / 100.0;
      return r == 0.0 ? 0.0 : r;
   }

   static EmoteImport.Result from(byte[] bytes, EmoteImport.Options opts) {
      GltfEmote.Glb g = GltfEmote.Glb.parse(bytes);
      List<Object> nodes = g.list("nodes");
      List<String> warnings = new ArrayList<>();
      int N = nodes.size();
      int[] parent = new int[N];
      Arrays.fill(parent, -1);
      Map<String, Object>[] node = new Map[N];

      for (int i = 0; i < N; i++) {
         node[i] = Json.object(nodes.get(i), "glb.nodes[]");
         Object worldRot = node[i].get("children");
         if (worldRot instanceof List) {
            for (Object c : (List)worldRot) {
               parent[(int)num(c)] = i;
            }
         }
      }

      double[][] restQ = new double[N][];
      double[][] restT = new double[N][];

      for (int ix = 0; ix < N; ix++) {
         if (node[ix].get("matrix") instanceof List<?> m && m.size() == 16) {
            List<Object> m4 = Json.array(m, "matrix");
            restQ[ix] = mquat(rotOf(m4));
            restT[ix] = new double[]{num(m4.get(12)), num(m4.get(13)), num(m4.get(14))};
         } else {
            restQ[ix] = qnorm(vec(node[ix].get("rotation"), 4, new double[]{0.0, 0.0, 0.0, 1.0}));
            restT[ix] = vec(node[ix].get("translation"), 3, new double[]{0.0, 0.0, 0.0});
         }
      }

      double[][][] worldRot = new double[N][][];
      double[][] worldPos = new double[N][];

      for (int ixx = 0; ixx < N; ixx++) {
         world(ixx, parent, restQ, restT, worldRot, worldPos);
      }

      Map<Integer, String> boneOf = new HashMap<>();
      Map<String, Integer> seen = new HashMap<>();
      List<String> unknown = new ArrayList<>();

      for (int ixx = 0; ixx < N; ixx++) {
         String name = node[ixx].get("name") instanceof String s ? s : "";
         if (node[ixx].get("mesh") == null && !name.isEmpty()) {
            String b = EmoteImport.boneAlias(name);
            if (b != null) {
               if (seen.containsKey(b)) {
                  warnings.add("hueso " + name + " repetido (" + b + "): se conservó el primero");
               } else {
                  seen.put(b, ixx);
                  boneOf.put(ixx, b);
               }
            } else if (!HELPER.matcher(name).find() && !name.equals("player") && !name.equalsIgnoreCase("armature")) {
               unknown.add(name);
            }
         }
      }

      if (seen.isEmpty()) {
         throw new IllegalArgumentException("ningún hueso del rig es del jugador (head, body, right_arm, left_arm, right_leg, left_leg, root…)");
      } else {
         List<Object> anims = g.list("animations");
         if (anims.isEmpty()) {
            throw new IllegalArgumentException("el .glb no tiene animación: exporta con «Animation» activado y una acción en el rig");
         } else {
            Map<String, Object> anim = Json.object(anims.get(0), "glb.animations[]");
            if (opts.animation() != null) {
               Map<String, Object> found = null;
               List<String> names = new ArrayList<>();

               for (Object a : anims) {
                  Map<String, Object> am = Json.object(a, "glb.animations[]");
                  names.add(String.valueOf(am.get("name")));
                  if (opts.animation().equals(am.get("name"))) {
                     found = am;
                  }
               }

               if (found == null) {
                  throw new IllegalArgumentException("animación «" + opts.animation() + "» no está en el .glb (hay: " + String.join(", ", names) + ")");
               }

               anim = found;
            } else if (anims.size() > 1) {
               warnings.add("el .glb trae " + anims.size() + " animaciones: se importa «" + anim.getOrDefault("name", "0") + "»");
            }

            double t0 = Double.POSITIVE_INFINITY;
            double t1 = Double.NEGATIVE_INFINITY;

            record Channel(int node, String path, double[][] times, double[][] values) {
            }

            List<Channel> channels = new ArrayList<>();
            List<Object> samplers = Json.array(anim.get("samplers"), "animation.samplers");

            for (Object co : Json.array(anim.get("channels"), "animation.channels")) {
               Map<String, Object> ch = Json.object(co, "channel");
               Map<String, Object> target = Json.object(ch.get("target"), "channel.target");
               if (target.get("node") != null) {
                  Map<String, Object> s = Json.object(samplers.get(integer(ch, "sampler", 0)), "sampler");
                  double[][] times = g.accessor(integer(s, "input", 0));
                  double[][] values = g.accessor(integer(s, "output", 0));
                  if ("CUBICSPLINE".equals(s.get("interpolation"))) {
                     double[][] v = new double[values.length / 3][];

                     for (int k = 0; k < v.length; k++) {
                        v[k] = values[k * 3 + 1];
                     }

                     values = v;
                  }

                  if (times.length != 0) {
                     channels.add(new Channel(integer(target, "node", 0), String.valueOf(target.get("path")), times, values));
                     t0 = Math.min(t0, times[0][0]);
                     t1 = Math.max(t1, times[times.length - 1][0]);
                  }
               }
            }

            if (!channels.isEmpty() && t1 > t0) {
               List<GltfEmote.Sampled> sampled = new ArrayList<>();
               Set<Integer> animatedNodes = new HashSet<>();
               List<Entry<Integer, String>> boneEntries = new ArrayList<>(boneOf.entrySet());
               boneEntries.sort(Entry.comparingByKey());

               for (Entry<Integer, String> en : boneEntries) {
                  int ixxx = en.getKey();
                  String bone = en.getValue();
                  int p = parent[ixxx];
                  double[][] Wp = p < 0 ? new double[][]{{1.0, 0.0, 0.0}, {0.0, 1.0, 0.0}, {0.0, 0.0, 1.0}} : worldRot[p];
                  double[][] WpInv = transpose(Wp);
                  Channel rotCh = null;
                  Channel posCh = null;
                  Channel scaleCh = null;

                  for (Channel c : channels) {
                     if (c.node() == ixxx) {
                        if (rotCh == null && c.path().equals("rotation")) {
                           rotCh = c;
                        } else if (posCh == null && c.path().equals("translation")) {
                           posCh = c;
                        } else if (scaleCh == null && c.path().equals("scale")) {
                           scaleCh = c;
                        }
                     }
                  }

                  List<double[]> rotT = null;
                  List<double[]> rotV = null;
                  List<double[]> posT = null;
                  List<double[]> posV = null;
                  if (rotCh != null) {
                     double[][] restInv = qmat(qinv(restQ[ixxx]));
                     List<double[]> vals = new ArrayList<>();
                     double[] prev = null;

                     for (double[] r : rotCh.values()) {
                        double[][] qa = qmat(qnorm(r));
                        double[][] D = mmul(mmul(Wp, mmul(qa, restInv)), WpInv);
                        double[] e = unwrap(eulerZYX(toModel(D)), prev);
                        vals.add(e);
                        prev = e;
                     }

                     if (!constant(vals, 0.5)) {
                        rotT = shift(rotCh.times(), t0);
                        rotV = vals;
                     }
                  }

                  if (posCh != null) {
                     List<double[]> vals = new ArrayList<>();

                     for (double[] r : posCh.values()) {
                        double[] d = mvec(Wp, new double[]{r[0] - restT[ixxx][0], r[1] - restT[ixxx][1], r[2] - restT[ixxx][2]});
                        vals.add(new double[]{16.0 * d[0], -16.0 * d[1], -16.0 * d[2]});
                     }

                     if (!constant(vals, 0.1)) {
                        posT = shift(posCh.times(), t0);
                        posV = vals;
                     }
                  }

                  if (scaleCh != null) {
                     for (double[] r : scaleCh.values()) {
                        if (Math.abs(r[0] - 1.0) > 0.01 || Math.abs(r[1] - 1.0) > 0.01 || Math.abs(r[2] - 1.0) > 0.01) {
                           warnings.add("hueso " + bone + ": la escala se ignora (el formato no escala huesos desde Blender)");
                           break;
                        }
                     }
                  }

                  if (rotV != null || posV != null) {
                     sampled.add(new GltfEmote.Sampled(bone, rotT, rotV, posT, posV));
                     animatedNodes.add(ixxx);
                  }
               }

               for (Channel cx : channels) {
                  Map<String, Object> n = cx.node() < N ? node[cx.node()] : null;
                  String name = n != null && n.get("name") instanceof String sx ? sx : null;
                  if (!boneOf.containsKey(cx.node()) && name != null && !HELPER.matcher(name).find() && n.get("mesh") == null && !unknown.contains(name)) {
                     unknown.add(name);
                  }
               }

               if (!unknown.isEmpty()) {
                  warnings.add("huesos ignorados (no son del jugador): " + String.join(", ", unknown));
               }

               if (sampled.isEmpty()) {
                  throw new IllegalArgumentException("ningún hueso del jugador se mueve en la animación");
               } else {
                  Map<String, Object> bones = new LinkedHashMap<>();
                  int factor = 1;
                  int[] steps = new int[EPS_STEPS.length + 1];
                  steps[0] = 1;
                  System.arraycopy(EPS_STEPS, 0, steps, 1, EPS_STEPS.length);

                  for (int f : steps) {
                     factor = f;
                     int keys = 0;
                     bones.clear();

                     for (GltfEmote.Sampled sx : sampled) {
                        Map<String, Object> b = new LinkedHashMap<>();
                        if (sx.rotV() != null) {
                           List<Object> rx = reduce(sx.rotT(), sx.rotV(), 0.5 * f);
                           b.put("rot", rx);
                           keys += rx.size();
                        }

                        if (sx.posV() != null) {
                           List<Object> pk = reduce(sx.posT(), sx.posV(), 0.1 * f);
                           b.put("pos", pk);
                           keys += pk.size();
                        }

                        bones.put(sx.bone(), b);
                     }

                     if (keys <= 2000) {
                        break;
                     }
                  }

                  if (factor > 1) {
                     warnings.add(
                        "animación muy densa: claves simplificadas con tolerancia ×"
                           + factor
                           + " ("
                           + EmoteImport.fmt(0.5 * factor)
                           + "°, "
                           + EmoteImport.fmt(0.1 * factor)
                           + " px)"
                     );
                  }

                  List<Object> props = new ArrayList<>();
                  int triangles = 0;
                  List<Object> meshes = g.list("meshes");
                  List<Object> materials = g.list("materials");
                  List<Object> textures = g.list("textures");
                  List<Object> images = g.list("images");

                  for (int ixxx = 0; ixxx < N; ixxx++) {
                     Map<String, Object> n = node[ixxx];
                     if (n.get("mesh") != null && n.get("skin") == null) {
                        String nname = n.get("name") instanceof String sx ? sx : "prop";
                        String anchor = "root";

                        for (int p = parent[ixxx]; p >= 0; p = parent[p]) {
                           String bx = boneOf.get(p);
                           if (bx != null) {
                              anchor = bx;
                              break;
                           }
                        }

                        if (props.size() >= 3) {
                           throw new IllegalArgumentException("más de 3 props (mallas sin skin)");
                        }

                        Map<String, Object> mesh = g.obj(meshes, integer(n, "mesh", 0), "mesh");
                        double[] pb = PIVOTS.get(anchor);
                        double[] pivot = anchor.equals("root") ? new double[]{0.0, 1.5, 0.0} : new double[]{pb[0] / 16.0, pb[1] / 16.0, -pb[2] / 16.0};
                        List<Object> pos = new ArrayList<>();
                        List<Object> uv = new ArrayList<>();
                        List<Object> idx = new ArrayList<>();
                        Integer image = null;

                        for (Object po : Json.array(mesh.get("primitives"), "mesh.primitives")) {
                           Map<String, Object> prim = Json.object(po, "primitive");
                           if (integer(prim, "mode", 4) != 4) {
                              throw new IllegalArgumentException("prop " + nname + ": solo triángulos (mode 4)");
                           }

                           Map<String, Object> attrs = Json.object(prim.get("attributes"), "primitive.attributes");
                           double[][] P = g.accessor(integer(attrs, "POSITION", 0));
                           if (attrs.get("TEXCOORD_0") == null) {
                              throw new IllegalArgumentException("prop " + nname + ": la malla no tiene UV (desenvuelve y asigna la textura)");
                           }

                           double[][] T = g.accessor(integer(attrs, "TEXCOORD_0", 0));
                           int base = pos.size() / 3;

                           for (int k = 0; k < P.length; k++) {
                              double[] w = mvec(worldRot[ixxx], P[k]);
                              double[] v = new double[]{
                                 worldPos[ixxx][0] + w[0] - pivot[0], worldPos[ixxx][1] + w[1] - pivot[1], worldPos[ixxx][2] + w[2] - pivot[2]
                              };
                              pos.add(round2(16.0 * v[0]));
                              pos.add(round2(-16.0 * v[1]));
                              pos.add(round2(-16.0 * v[2]));
                              uv.add(EmoteImport.round(T[k][0]));
                              uv.add(EmoteImport.round(T[k][1]));
                           }

                           if (prim.get("indices") != null) {
                              for (double[] rx : g.accessor(integer(prim, "indices", 0))) {
                                 idx.add((double)(base + (int)rx[0]));
                              }
                           } else {
                              for (int k = 0; k < P.length; k++) {
                                 idx.add((double)(base + k));
                              }
                           }

                           Integer img = null;
                           if (prim.get("material") != null) {
                              Map<String, Object> mat = g.obj(materials, integer(prim, "material", 0), "material");
                              if (mat.get("pbrMetallicRoughness") instanceof Map<?, ?> pbr
                                 && pbr.get("baseColorTexture") instanceof Map<?, ?> bct
                                 && bct.get("index") instanceof Number ti) {
                                 Map<String, Object> tex = g.obj(textures, ti.intValue(), "texture");
                                 if (tex.get("source") instanceof Number src) {
                                    img = src.intValue();
                                 }
                              }
                           }

                           if (img == null) {
                              throw new IllegalArgumentException("prop " + nname + ": el material no tiene textura de imagen (Base Color ← Image Texture)");
                           }

                           if (image != null && !image.equals(img)) {
                              throw new IllegalArgumentException("prop " + nname + ": una sola textura por prop");
                           }

                           image = img;
                        }

                        if (idx.isEmpty()) {
                           throw new IllegalArgumentException("prop " + nname + ": malla vacía");
                        }

                        triangles += idx.size() / 3;
                        if (triangles > 1500) {
                           throw new IllegalArgumentException("props: más de 1500 triángulos en total");
                        }

                        Map<String, Object> im = g.obj(images, image, "image");
                        if (im.get("bufferView") == null || !"image/png".equals(im.get("mimeType"))) {
                           throw new IllegalArgumentException("prop " + nname + ": la textura debe ir embebida como PNG");
                        }

                        byte[] png = g.view(integer(im, "bufferView", 0));
                        if (png.length < 24 || png[1] != 80 || png[2] != 78 || png[3] != 71) {
                           throw new IllegalArgumentException("la textura no es un PNG");
                        }

                        ByteBuffer hdr = ByteBuffer.wrap(png).order(ByteOrder.BIG_ENDIAN);
                        int tw = hdr.getInt(16);
                        int th = hdr.getInt(20);
                        if (png.length > 65536) {
                           throw new IllegalArgumentException("prop " + nname + ": textura de más de 64 KB");
                        }

                        Map<String, Object> prop = new LinkedHashMap<>();
                        String id = slug(nname);
                        prop.put("id", id.isEmpty() ? "prop-" + props.size() : id);
                        Map<String, Object> m = new LinkedHashMap<>();
                        m.put("pos", pos);
                        m.put("uv", uv);
                        m.put("idx", idx);
                        prop.put("mesh", m);
                        prop.put("texture", Base64.getEncoder().encodeToString(png));
                        prop.put("textureSize", List.of((double)tw, (double)th));
                        if (!anchor.equals("root")) {
                           prop.put("bone", anchor);
                        }

                        if (animatedNodes.contains(ixxx)) {
                           warnings.add("prop " + nname + ": la animación del propio prop se ignora (anímalo con su hueso)");
                        }

                        props.add(prop);
                     }
                  }

                  double duration = EmoteImport.round(t1 - t0);
                  String name = anim.get("name") instanceof String sx ? sx : "emote";
                  boolean loop = name.matches("(?i).*(^|[_\\-. ])loop$") || name.matches("(?i)^loop([_\\-. ].*|$)");
                  Map<String, Object> raw = new LinkedHashMap<>();
                  raw.put("format", 1L);
                  String baseId = slug(name.replaceAll("(?i)[_\\-. ]?loop$", ""));
                  raw.put("id", opts.id() != null ? opts.id() : (baseId.isEmpty() ? "emote" : baseId));
                  raw.put("name", opts.name() != null ? opts.name() : name);
                  if (opts.author() != null) {
                     raw.put("author", opts.author());
                  }

                  raw.put("duration", duration);
                  if (loop) {
                     raw.put("loop", true);
                  }

                  raw.put("bones", bones);
                  if (!props.isEmpty()) {
                     raw.put("props", props);
                  }

                  return new EmoteImport.Result(PemoteJson.from(raw), warnings);
               }
            } else {
               throw new IllegalArgumentException("la animación no tiene muestras (¿la acción está vacía?)");
            }
         }
      }
   }

   private static List<double[]> shift(double[][] times, double t0) {
      List<double[]> out = new ArrayList<>();

      for (double[] t : times) {
         out.add(new double[]{t[0] - t0});
      }

      return out;
   }

   private static void world(int i, int[] parent, double[][] restQ, double[][] restT, double[][][] worldRot, double[][] worldPos) {
      if (worldRot[i] == null) {
         int p = parent[i];
         double[][] lr = qmat(restQ[i]);
         if (p < 0) {
            worldRot[i] = lr;
            worldPos[i] = restT[i];
         } else {
            world(p, parent, restQ, restT, worldRot, worldPos);
            worldRot[i] = mmul(worldRot[p], lr);
            double[] off = mvec(worldRot[p], restT[i]);
            worldPos[i] = new double[]{worldPos[p][0] + off[0], worldPos[p][1] + off[1], worldPos[p][2] + off[2]};
         }
      }
   }

   private record Glb(Map<String, Object> json, ByteBuffer bin) {
      static GltfEmote.Glb parse(byte[] bytes) {
         if (bytes.length < 20) {
            throw new IllegalArgumentException("no es un .glb (cabecera glTF)");
         } else {
            ByteBuffer bb = ByteBuffer.wrap(bytes).order(ByteOrder.LITTLE_ENDIAN);
            if (bb.getInt(0) != 1179937895) {
               throw new IllegalArgumentException("no es un .glb (cabecera glTF)");
            } else if (bb.getInt(4) != 2) {
               throw new IllegalArgumentException("solo glTF 2.0");
            } else {
               int off = 12;
               Map<String, Object> json = null;
               ByteBuffer bin = ByteBuffer.wrap(new byte[0]);

               while (off + 8 <= bytes.length) {
                  int len = bb.getInt(off);
                  int type = bb.getInt(off + 4);
                  int start = off + 8;
                  int end = Math.min(bytes.length, start + len);
                  if (type == 1313821514) {
                     json = Json.object(Json.parse(new String(bytes, start, end - start, StandardCharsets.UTF_8)), "glb.json");
                  } else if (type == 5130562) {
                     bin = ByteBuffer.wrap(bytes, start, end - start).slice().order(ByteOrder.LITTLE_ENDIAN);
                  }

                  off = start + len;
               }

               if (json == null) {
                  throw new IllegalArgumentException(".glb sin bloque JSON");
               } else {
                  return new GltfEmote.Glb(json, bin);
               }
            }
         }
      }

      List<Object> list(String key) {
         return this.json.get(key) instanceof List<?> l ? Json.array(l, "glb." + key) : List.of();
      }

      Map<String, Object> obj(List<Object> arr, int i, String what) {
         if (i >= 0 && i < arr.size()) {
            return Json.object(arr.get(i), what);
         } else {
            throw new IllegalArgumentException(what + " " + i + " no existe");
         }
      }

      byte[] view(int i) {
         Map<String, Object> bv = this.obj(this.list("bufferViews"), i, "bufferView");
         if (GltfEmote.integer(bv, "buffer", 0) != 0) {
            throw new IllegalArgumentException("solo se admite el búfer binario embebido");
         } else {
            int o = GltfEmote.integer(bv, "byteOffset", 0);
            int len = GltfEmote.integer(bv, "byteLength", 0);
            byte[] out = new byte[len];
            this.bin.duplicate().position(o).get(out, 0, len);
            return out;
         }
      }

      double[][] accessor(int i) {
         Map<String, Object> a = this.obj(this.list("accessors"), i, "accessor");
         int count = GltfEmote.integer(a, "count", 0);
         if (a.get("bufferView") == null) {
            return new double[count][0];
         } else {
            Map<String, Object> bv = this.obj(this.list("bufferViews"), GltfEmote.integer(a, "bufferView", 0), "bufferView");
            String type = String.valueOf(a.get("type"));

            int n = switch (type) {
               case "SCALAR" -> 1;
               case "VEC2" -> 2;
               case "VEC3" -> 3;
               case "VEC4" -> 4;
               case "MAT4" -> 16;
               default -> 0;
            };
            if (n == 0) {
               throw new IllegalArgumentException("accessor de tipo " + type);
            } else {
               int ct = GltfEmote.integer(a, "componentType", 0);

               int size = switch (ct) {
                  case 5120, 5121 -> 1;
                  case 5122, 5123 -> 2;
                  default -> 0;
                  case 5125, 5126 -> 4;
               };
               if (size == 0) {
                  throw new IllegalArgumentException("componentType " + ct);
               } else {
                  boolean normalized = Boolean.TRUE.equals(a.get("normalized"));
                  int base = GltfEmote.integer(bv, "byteOffset", 0) + GltfEmote.integer(a, "byteOffset", 0);
                  int stride = bv.get("byteStride") != null ? GltfEmote.integer(bv, "byteStride", 0) : n * size;
                  double[][] out = new double[count][n];

                  for (int k = 0; k < count; k++) {
                     for (int c = 0; c < n; c++) {
                        int o = base + k * stride + c * size;

                        out[k][c] = switch (ct) {
                           case 5120 -> normalized ? Math.max(this.bin.get(o) / 127.0, -1.0) : this.bin.get(o);
                           case 5121 -> normalized ? (this.bin.get(o) & 255) / 255.0 : this.bin.get(o) & 255;
                           case 5122 -> normalized ? Math.max(this.bin.getShort(o) / 32767.0, -1.0) : this.bin.getShort(o);
                           case 5123 -> normalized ? (this.bin.getShort(o) & '\uffff') / 65535.0 : this.bin.getShort(o) & '\uffff';
                           default -> this.bin.getInt(o) & 4294967295L;
                           case 5126 -> this.bin.getFloat(o);
                        };
                     }
                  }

                  return out;
               }
            }
         }
      }
   }

   private record Sampled(String bone, List<double[]> rotT, List<double[]> rotV, List<double[]> posT, List<double[]> posV) {
   }
}
