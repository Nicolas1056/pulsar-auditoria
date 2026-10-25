package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v189.mixin.ModelBoxAccessor;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public final class MeshSubdivider189 {
   public static final String[] NAMES = new String[]{"body_lower", "right_forearm", "left_forearm", "right_shin", "left_shin"};
   private static final int HALF = 6;
   private static final Map<bbj, MeshSubdivider189.Joints> JOINTS = new IdentityHashMap<>();

   private MeshSubdivider189() {
   }

   public static MeshSubdivider189.Joints joints(bbj model) {
      return JOINTS.get(model);
   }

   public static void subdividePlayer(bbr m) {
      subdivide(m, new bct[][]{{m.g, m.v}, {m.h, m.b}, {m.i, m.a}, {m.j, m.d}, {m.k, m.c}});
   }

   public static void subdivideArmor(bbj m) {
      subdivide(m, new bct[][]{{m.g}, {m.h}, {m.i}, {m.j}, {m.k}});
   }

   public static void syncOverlays(bbj model) {
      MeshSubdivider189.Joints j = JOINTS.get(model);
      if (j != null) {
         for (bct[] pair : j.overlays) {
            pair[1].j = pair[0].j;
         }
      }
   }

   private static synchronized void subdivide(bbj model, bct[][] groups) {
      if (!JOINTS.containsKey(model)) {
         MeshSubdivider189.Joints joints = new MeshSubdivider189.Joints();
         int done = 0;

         for (int i = 0; i < groups.length; i++) {
            bct base = groups[i][0];
            MeshSubdivider189.Box b = twelve(base);
            if (b != null) {
               float cx = b.x() + b.w() / 2.0F;
               float jointY = b.y() + 6.0F;
               bct joint = new bct(model, NAMES[i]);
               joint.i = base.i;
               joint.a(cx, jointY, 0.0F);
               base.a(joint);
               split(base, b, cx, joint);
               joints.parts[i] = joint;
               joints.pivots[i] = new float[]{cx, jointY, 0.0F};
               done++;
               ((JointLink189.Host)base).pulsar$joint(new JointLink189(joint, true, cx, jointY, 0.0F));
               ((JointLink189.Host)joint).pulsar$joint(new JointLink189(joint, false, 0.0F, 0.0F, 0.0F));

               for (int k = 1; k < groups[i].length; k++) {
                  bct overlay = groups[i][k];
                  MeshSubdivider189.Box ob = twelve(overlay);
                  if (ob != null) {
                     bct lower = new bct(model, NAMES[i] + "_overlay");
                     lower.i = overlay.i;
                     joint.a(lower);
                     split(overlay, ob, cx, lower);
                     joints.overlays.add(new bct[]{overlay, lower});
                     ((JointLink189.Host)overlay).pulsar$joint(new JointLink189(joint, true, cx, jointY, 0.0F));
                     ((JointLink189.Host)lower).pulsar$joint(new JointLink189(joint, false, 0.0F, 0.0F, 0.0F));
                  }
               }
            }
         }

         JOINTS.put(model, joints);
         ClientOptions.LOG
            .accept(
               "emotes v2: malla subdividida (" + done + " articulaciones, " + joints.overlays.size() + " overlays) en " + model.getClass().getSimpleName()
            );
      }
   }

   private static MeshSubdivider189.Box twelve(bct part) {
      for (bcr box : part.l) {
         bbs[] q = ((ModelBoxAccessor)box).pulsar$quads();
         if (q != null && q.length == 6) {
            float tw = part.a;
            float th = part.b;
            int d = Math.round((maxU(q[0]) - minU(q[0])) * tw);
            int h = Math.round((maxV(q[0]) - minV(q[0])) * th);
            int w = Math.round((maxU(q[2]) - minU(q[2])) * tw);
            if (h == 12) {
               int u = Math.round(minU(q[2]) * tw) - d;
               int v = Math.round(minV(q[2]) * th);
               float g = (float)(maxY(q[0]) - minY(q[0]) - h) / 2.0F;
               return new MeshSubdivider189.Box(box, u, v, box.a, box.b, box.c, w, h, d, g, part.i);
            }
         }
      }

      return null;
   }

   private static void split(bct upperHost, MeshSubdivider189.Box b, float cx, bct lowerHost) {
      bcr upper = new bcr(upperHost, b.u(), b.v(), b.x(), b.y(), b.z(), b.w(), 6, b.d(), b.g(), b.mirror());
      keep(upper, 0, 1, 2, 4, 5);
      upperHost.l.remove(b.box());
      upperHost.l.add(upper);
      bcr lower = new bcr(lowerHost, b.u(), b.v() + 6, b.x() - cx, 0.0F, b.z(), b.w(), 6, b.d(), b.g(), b.mirror());
      keep(lower, 0, 1, 4, 5);
      bcr cap = new bcr(lowerHost, b.u(), b.v(), b.x() - cx, 6.0F, b.z(), b.w(), 0, b.d(), b.g(), b.mirror());
      keep(cap, 3);
      lowerHost.l.add(lower);
      lowerHost.l.add(cap);
   }

   private static void keep(bcr box, int... indices) {
      bbs[] all = ((ModelBoxAccessor)box).pulsar$quads();
      bbs[] kept = new bbs[indices.length];

      for (int i = 0; i < indices.length; i++) {
         kept[i] = all[indices[i]];
      }

      ((ModelBoxAccessor)box).pulsar$quads(kept);
   }

   private static double minY(bbs q) {
      double m = Double.MAX_VALUE;

      for (bcg p : q.a) {
         m = Math.min(m, p.a.b);
      }

      return m;
   }

   private static double maxY(bbs q) {
      double m = -Double.MAX_VALUE;

      for (bcg p : q.a) {
         m = Math.max(m, p.a.b);
      }

      return m;
   }

   private static float minU(bbs q) {
      float m = Float.MAX_VALUE;

      for (bcg p : q.a) {
         m = Math.min(m, p.b);
      }

      return m;
   }

   private static float maxU(bbs q) {
      float m = -Float.MAX_VALUE;

      for (bcg p : q.a) {
         m = Math.max(m, p.b);
      }

      return m;
   }

   private static float minV(bbs q) {
      float m = Float.MAX_VALUE;

      for (bcg p : q.a) {
         m = Math.min(m, p.c);
      }

      return m;
   }

   private static float maxV(bbs q) {
      float m = -Float.MAX_VALUE;

      for (bcg p : q.a) {
         m = Math.max(m, p.c);
      }

      return m;
   }

   private record Box(bcr box, int u, int v, float x, float y, float z, int w, int h, int d, float g, boolean mirror) {
   }

   public static final class Joints {
      public final bct[] parts = new bct[5];
      public final float[][] pivots = new float[5][3];
      final List<bct[]> overlays = new ArrayList<>();
   }
}
