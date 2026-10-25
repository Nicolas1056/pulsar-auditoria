package com.metrofico.pulsar.engine.v189.emote;

import com.metrofico.pulsar.api.emote.JointBend;

public final class JointLink189 {
   private static final float[] H = new float[9];
   private final bct joint;
   private final boolean upper;
   private final float px;
   private final float py;
   private final float pz;
   private float[] source;
   private boolean[] ring;
   private float[] out;

   JointLink189(bct joint, boolean upper, float px, float py, float pz) {
      this.joint = joint;
      this.upper = upper;
      this.px = px;
      this.py = py;
      this.pz = pz;
   }

   public boolean bent() {
      return JointBend.bent(this.joint.f, this.joint.g, this.joint.h);
   }

   public float[] bend(float[] v) {
      if (!this.bent()) {
         return v;
      } else {
         if (this.source != v) {
            this.prepare(v);
         }

         JointBend.half(this.joint.f, this.joint.g, this.joint.h, H);
         float[] o = this.out;
         int i = 0;

         for (int k = 0; i < v.length; k++) {
            if (!this.ring[k]) {
               o[i] = v[i];
               o[i + 1] = v[i + 1];
               o[i + 2] = v[i + 2];
            } else if (this.upper) {
               JointBend.upper(H, this.px, this.py, this.pz, v[i], v[i + 1], v[i + 2], o, i);
            } else {
               JointBend.lower(H, v[i], v[i + 1], v[i + 2], o, i);
            }

            i += 8;
         }

         for (int q = 0; q < v.length; q += 32) {
            float ax = o[q] - o[q + 8];
            float ay = o[q + 1] - o[q + 9];
            float az = o[q + 2] - o[q + 10];
            float bx = o[q + 16] - o[q + 8];
            float by = o[q + 17] - o[q + 9];
            float bz = o[q + 18] - o[q + 10];
            float nx = by * az - bz * ay;
            float ny = bz * ax - bx * az;
            float nz = bx * ay - by * ax;
            float len = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);
            if (len < 1.0E-9F) {
               nx = v[q + 5];
               ny = v[q + 6];
               nz = v[q + 7];
            } else {
               if (nx * v[q + 5] + ny * v[q + 6] + nz * v[q + 7] < 0.0F) {
                  len = -len;
               }

               nx /= len;
               ny /= len;
               nz /= len;
            }

            for (int ix = q; ix < q + 32; ix += 8) {
               o[ix + 3] = v[ix + 3];
               o[ix + 4] = v[ix + 4];
               o[ix + 5] = nx;
               o[ix + 6] = ny;
               o[ix + 7] = nz;
            }
         }

         return o;
      }
   }

   public void drawImmediate(float[] v, float scale) {
      float[] o = this.bend(v);
      bfx tess = bfx.a();
      bfd b = tess.c();
      b.a(7, bms.j);

      for (int i = 0; i < o.length; i += 8) {
         b.b(o[i] * scale, o[i + 1] * scale, o[i + 2] * scale).a(o[i + 3], o[i + 4]).c(o[i + 5], o[i + 6], o[i + 7]).d();
      }

      tess.b();
   }

   private void prepare(float[] v) {
      int n = v.length / 8;
      this.ring = new boolean[n];
      this.out = new float[v.length];
      float mid = this.upper ? this.py - 3.0F : 3.0F;

      for (int k = 0; k < n; k++) {
         float y = v[k * 8 + 1];
         this.ring[k] = this.upper ? y > mid : y < mid;
      }

      this.source = v;
   }

   public interface Host {
      JointLink189 pulsar$joint();

      void pulsar$joint(JointLink189 var1);
   }
}
