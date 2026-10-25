package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.emote.JointLink189;
import com.metrofico.pulsar.engine.v189.mixin.ModelBoxAccessor;
import java.nio.FloatBuffer;
import java.util.IdentityHashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class EntityBatch189 {
   private static final Map<bct, EntityBatch189.Baked> BAKED = new IdentityHashMap<>();
   private static final FloatBuffer MATRIX = BufferUtils.createFloatBuffer(16);
   private static final float[] M = new float[16];
   private static final float[] LOCAL = new float[16];
   private static final RawStream189 STREAM = new RawStream189(65536);
   private static boolean pending;
   private static boolean flushing;
   private static int depthAtStart;
   private static int depth;
   private static int framePartsBatched;
   private static int frameFlushes;
   static volatile int lastParts;
   static volatile int lastFlushes;
   static volatile int lastQuadsCulledPct;
   private static int frameQuads;
   private static int frameQuadsCulled;
   private static volatile Thread renderThread;

   static boolean ownsVbo(int id) {
      return id == STREAM.vbo();
   }

   public static boolean onRenderThread() {
      return Thread.currentThread() == renderThread;
   }

   private EntityBatch189() {
   }

   public static boolean enabled() {
      return FrameFlags.entityBatch;
   }

   public static boolean cpuParts() {
      return FrameFlags.entityBatch && !flushing;
   }

   public static void push() {
      depth++;
   }

   public static void pop() {
      if (pending && depth <= depthAtStart) {
         flush();
      }

      depth--;
   }

   public static void part(bct part, int list, float scale) {
      JointLink189 joint = ((JointLink189.Host)part).pulsar$joint();
      if (enabled() && !flushing) {
         EntityBatch189.Baked b = baked(part);
         if (b != null && b.v.length != 0) {
            float[] M = MatrixMirror189.matrix();
            boolean capture = EntityCache189.capturing();
            RawStream189 stream;
            float[] W;
            if (capture) {
               stream = EntityCache189.stream(6);
               W = EntityCache189.localMatrix(M, LOCAL);
               pending = true;
            } else {
               stream = STREAM;
               W = M;
               if (!pending) {
                  STREAM.reset();
                  pending = true;
                  depthAtStart = depth;
               }
            }

            float[] v = joint != null ? joint.bend(b.v) : b.v;
            int o = stream.reserve(v.length / 8 * 6);
            int[] out = stream.data;
            float m0 = W[0];
            float m1 = W[1];
            float m2 = W[2];
            float m4 = W[4];
            float m5 = W[5];
            float m6 = W[6];
            float m8 = W[8];
            float m9 = W[9];
            float m10 = W[10];
            float m12 = W[12];
            float m13 = W[13];
            float m14 = W[14];
            boolean reduce = EntityLod189.reduce();
            int written = 0;

            for (int q = 0; q < v.length; q += 32) {
               float nx = v[q + 5];
               float ny = v[q + 6];
               float nz = v[q + 7];
               if (reduce) {
                  float ex = M[0] * nx + M[4] * ny + M[8] * nz;
                  float ey = M[1] * nx + M[5] * ny + M[9] * nz;
                  float ez = M[2] * nx + M[6] * ny + M[10] * nz;
                  float x0 = v[q] * scale;
                  float y0 = v[q + 1] * scale;
                  float z0 = v[q + 2] * scale;
                  float px = M[0] * x0 + M[4] * y0 + M[8] * z0 + M[12];
                  float py = M[1] * x0 + M[5] * y0 + M[9] * z0 + M[13];
                  float pz = M[2] * x0 + M[6] * y0 + M[10] * z0 + M[14];
                  if (ex * px + ey * py + ez * pz > 0.0F) {
                     frameQuadsCulled++;
                     continue;
                  }
               }

               float tx = m0 * nx + m4 * ny + m8 * nz;
               float ty = m1 * nx + m5 * ny + m9 * nz;
               float tz = m2 * nx + m6 * ny + m10 * nz;
               float len = (float)Math.sqrt(tx * tx + ty * ty + tz * tz);
               if (len > 1.0E-6F) {
                  tx /= len;
                  ty /= len;
                  tz /= len;
               }

               int normal = (int)(tx * 127.0F) & 0xFF | ((int)(ty * 127.0F) & 0xFF) << 8 | ((int)(tz * 127.0F) & 0xFF) << 16;

               for (int i = q; i < q + 32; i += 8) {
                  float x = v[i] * scale;
                  float y = v[i + 1] * scale;
                  float z = v[i + 2] * scale;
                  out[o] = Float.floatToRawIntBits(m0 * x + m4 * y + m8 * z + m12);
                  out[o + 1] = Float.floatToRawIntBits(m1 * x + m5 * y + m9 * z + m13);
                  out[o + 2] = Float.floatToRawIntBits(m2 * x + m6 * y + m10 * z + m14);
                  out[o + 3] = Float.floatToRawIntBits(v[i + 3]);
                  out[o + 4] = Float.floatToRawIntBits(v[i + 4]);
                  out[o + 5] = normal;
                  o += 6;
               }

               written += 4;
            }

            stream.size -= (v.length / 8 - written) * 6;
            frameQuads += v.length / 32;
            framePartsBatched++;
         }
      } else {
         if (joint != null && joint.bent()) {
            joint.drawImmediate(baked(part).v, scale);
         } else {
            bfl.o(list);
         }
      }
   }

   public static void markPending() {
      pending = true;
   }

   public static void flush() {
      if (pending && !flushing) {
         if (EntityCache189.capturing()) {
            pending = false;
            EntityCache189.closeBatch();
         } else {
            flushing = true;
            pending = false;

            try {
               int vertices = STREAM.size / 6;
               if (vertices > 0) {
                  bfl.E();
                  bfl.D();
                  STREAM.bindAndUpload();
                  GL11.glEnableClientState(32884);
                  GL11.glEnableClientState(32888);
                  GL11.glEnableClientState(32885);
                  GL11.glVertexPointer(3, 5126, 24, 0L);
                  GL11.glTexCoordPointer(2, 5126, 24, 12L);
                  GL11.glNormalPointer(5120, 24, 20L);
                  GL11.glDrawArrays(7, 0, vertices);
                  GL11.glDisableClientState(32885);
                  GL11.glDisableClientState(32888);
                  GL11.glDisableClientState(32884);
                  bqs.g(bqs.R, 0);
                  bfl.F();
                  frameFlushes++;
               }

               STREAM.reset();
            } finally {
               flushing = false;
            }
         }
      }
   }

   public static boolean pending() {
      return pending;
   }

   private static EntityBatch189.Baked baked(bct part) {
      EntityBatch189.Baked b = BAKED.get(part);
      if (b != null && b.cuboids == part.l.size()) {
         return b;
      } else {
         int quads = 0;

         for (bcr box : part.l) {
            bbs[] q = ((ModelBoxAccessor)box).pulsar$quads();
            quads += q == null ? 0 : q.length;
         }

         float[] v = new float[quads * 4 * 8];
         int o = 0;

         for (bcr box : part.l) {
            bbs[] qs = ((ModelBoxAccessor)box).pulsar$quads();
            if (qs != null) {
               for (bbs q : qs) {
                  bcg[] p = q.a;
                  aui a = p[1].a;
                  aui b0 = p[0].a;
                  aui c = p[2].a;
                  double ux = b0.a - a.a;
                  double uy = b0.b - a.b;
                  double uz = b0.c - a.c;
                  double wx = c.a - a.a;
                  double wy = c.b - a.b;
                  double wz = c.c - a.c;
                  double nx = wy * uz - wz * uy;
                  double ny = wz * ux - wx * uz;
                  double nz = wx * uy - wy * ux;
                  double len = Math.sqrt(nx * nx + ny * ny + nz * nz);
                  if (len > 1.0E-12) {
                     nx /= len;
                     ny /= len;
                     nz /= len;
                  }

                  if (((EntityBatch189.TexturedQuadInvert)q).pulsar$inverted()) {
                     nx = -nx;
                     ny = -ny;
                     nz = -nz;
                  }

                  for (bcg tp : p) {
                     v[o++] = (float)tp.a.a;
                     v[o++] = (float)tp.a.b;
                     v[o++] = (float)tp.a.c;
                     v[o++] = tp.b;
                     v[o++] = tp.c;
                     v[o++] = (float)nx;
                     v[o++] = (float)ny;
                     v[o++] = (float)nz;
                  }
               }
            }
         }

         b = new EntityBatch189.Baked(v, part.l.size());
         BAKED.put(part, b);
         return b;
      }
   }

   public static float[] vertices(bct part) {
      return baked(part).v;
   }

   public static void frame() {
      renderThread = Thread.currentThread();
      lastQuadsCulledPct = frameQuads > 0 ? frameQuadsCulled * 100 / frameQuads : 0;
      frameQuads = 0;
      frameQuadsCulled = 0;
      lastParts = framePartsBatched;
      lastFlushes = frameFlushes;
      framePartsBatched = 0;
      frameFlushes = 0;
   }

   private static final class Baked {
      final float[] v;
      final int cuboids;

      Baked(float[] v, int cuboids) {
         this.v = v;
         this.cuboids = cuboids;
      }
   }

   public interface TexturedQuadInvert {
      boolean pulsar$inverted();
   }
}
