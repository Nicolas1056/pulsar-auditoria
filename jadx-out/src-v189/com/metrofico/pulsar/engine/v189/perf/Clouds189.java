package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;
import java.nio.ByteBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public final class Clouds189 {
   private static final int STRIDE = 28;
   private static int vbo;
   private static int vertices;
   private static int keyCellX = Integer.MIN_VALUE;
   private static int keyCellZ;
   private static int keyColor;
   private static int keyFaces = -1;
   private static ByteBuffer scratch;
   static volatile int rebuilds;

   static boolean ownsVbo(int id) {
      return id == vbo;
   }

   private Clouds189() {
   }

   public static boolean enabled() {
      return FrameFlags.cloudCache;
   }

   public static void render(float tickDelta, double camX, float camY, double camZ, int ticks, float cloudHeight, float r, float g, float b) {
      bfl.p();
      double d = ticks + tickDelta;
      double e = (camX + d * 0.03F) / 12.0;
      double k = camZ / 12.0 + 0.33F;
      float l = cloudHeight - camY + 0.33F;
      int m = ns.c(e / 2048.0);
      int n = ns.c(k / 2048.0);
      e -= m * 2048;
      k -= n * 2048;
      int cellX = ns.c(e);
      int cellZ = ns.c(k);
      float ag = (float)(e - cellX);
      float ah = (float)(k - cellZ);
      int faces = (l > -5.0F ? 1 : 0) | (l <= 5.0F ? 2 : 0);
      int color = (int)(r * 255.0F) << 16 | (int)(g * 255.0F) << 8 | (int)(b * 255.0F);
      if (vbo == 0 || cellX != keyCellX || cellZ != keyCellZ || faces != keyFaces || color != keyColor) {
         build(cellX, cellZ, faces, r, g, b);
         keyCellX = cellX;
         keyCellZ = cellZ;
         keyFaces = faces;
         keyColor = color;
      }

      bfl.l();
      bfl.a(770, 771, 1, 0);
      bfl.E();
      bfl.a(12.0F, 1.0F, 12.0F);
      bfl.b(-ag, l, -ah);
      GL15.glBindBuffer(34962, vbo);
      GL11.glEnableClientState(32884);
      GL11.glEnableClientState(32888);
      GL11.glEnableClientState(32886);
      GL11.glEnableClientState(32885);
      GL11.glVertexPointer(3, 5126, 28, 0L);
      GL11.glTexCoordPointer(2, 5126, 28, 12L);
      GL11.glColorPointer(4, 5121, 28, 20L);
      GL11.glNormalPointer(5120, 28, 24L);
      bfl.a(false, false, false, false);
      GL11.glDrawArrays(7, 0, vertices);
      bfl.a(true, true, true, true);
      GL11.glDrawArrays(7, 0, vertices);
      GL11.glDisableClientState(32884);
      GL11.glDisableClientState(32888);
      GL11.glDisableClientState(32886);
      GL11.glDisableClientState(32885);
      GL15.glBindBuffer(34962, 0);
      bfl.F();
      bfl.c(1.0F, 1.0F, 1.0F, 1.0F);
      bfl.k();
      bfl.o();
   }

   private static void build(int cellX, int cellZ, int faces, float o, float p, float q) {
      float u = o * 0.9F;
      float v = p * 0.9F;
      float w = q * 0.9F;
      float x = o * 0.7F;
      float y = p * 0.7F;
      float z = q * 0.7F;
      float aa = o * 0.8F;
      float ab = p * 0.8F;
      float ac = q * 0.8F;
      float ae = cellX * 0.00390625F;
      float af = cellZ * 0.00390625F;
      int max = 243712;
      if (scratch == null || scratch.capacity() < max) {
         scratch = BufferUtils.createByteBuffer(max);
      }

      ByteBuffer buf = scratch;
      buf.clear();
      int count = 0;

      for (int am = -3; am <= 4; am++) {
         for (int an = -3; an <= 4; an++) {
            float ao = am * 8;
            float ap = an * 8;
            float aq = ao;
            float ar = ap;
            if ((faces & 1) != 0) {
               vertex(buf, ao + 0.0F, 0.0F, ap + 8.0F, (ao + 0.0F) * 0.00390625F + ae, (ap + 8.0F) * 0.00390625F + af, x, y, z, 0, -1, 0);
               vertex(buf, ao + 8.0F, 0.0F, ap + 8.0F, (ao + 8.0F) * 0.00390625F + ae, (ap + 8.0F) * 0.00390625F + af, x, y, z, 0, -1, 0);
               vertex(buf, ao + 8.0F, 0.0F, ap + 0.0F, (ao + 8.0F) * 0.00390625F + ae, (ap + 0.0F) * 0.00390625F + af, x, y, z, 0, -1, 0);
               vertex(buf, ao + 0.0F, 0.0F, ap + 0.0F, (ao + 0.0F) * 0.00390625F + ae, (ap + 0.0F) * 0.00390625F + af, x, y, z, 0, -1, 0);
               count += 4;
            }

            if ((faces & 2) != 0) {
               float top = 3.9990234F;
               vertex(buf, ao + 0.0F, top, ap + 8.0F, (ao + 0.0F) * 0.00390625F + ae, (ap + 8.0F) * 0.00390625F + af, o, p, q, 0, 1, 0);
               vertex(buf, ao + 8.0F, top, ap + 8.0F, (ao + 8.0F) * 0.00390625F + ae, (ap + 8.0F) * 0.00390625F + af, o, p, q, 0, 1, 0);
               vertex(buf, ao + 8.0F, top, ap + 0.0F, (ao + 8.0F) * 0.00390625F + ae, (ap + 0.0F) * 0.00390625F + af, o, p, q, 0, 1, 0);
               vertex(buf, ao + 0.0F, top, ap + 0.0F, (ao + 0.0F) * 0.00390625F + ae, (ap + 0.0F) * 0.00390625F + af, o, p, q, 0, 1, 0);
               count += 4;
            }

            if (am > -1) {
               for (int as = 0; as < 8; as++) {
                  float sx = aq + as + 0.0F;
                  float su = (ao + as + 0.5F) * 0.00390625F + ae;
                  vertex(buf, sx, 0.0F, ar + 8.0F, su, (ap + 8.0F) * 0.00390625F + af, u, v, w, -1, 0, 0);
                  vertex(buf, sx, 4.0F, ar + 8.0F, su, (ap + 8.0F) * 0.00390625F + af, u, v, w, -1, 0, 0);
                  vertex(buf, sx, 4.0F, ar + 0.0F, su, (ap + 0.0F) * 0.00390625F + af, u, v, w, -1, 0, 0);
                  vertex(buf, sx, 0.0F, ar + 0.0F, su, (ap + 0.0F) * 0.00390625F + af, u, v, w, -1, 0, 0);
                  count += 4;
               }
            }

            if (am <= 1) {
               for (int at = 0; at < 8; at++) {
                  float sx = aq + at + 1.0F - 9.765625E-4F;
                  float su = (ao + at + 0.5F) * 0.00390625F + ae;
                  vertex(buf, sx, 0.0F, ar + 8.0F, su, (ap + 8.0F) * 0.00390625F + af, u, v, w, 1, 0, 0);
                  vertex(buf, sx, 4.0F, ar + 8.0F, su, (ap + 8.0F) * 0.00390625F + af, u, v, w, 1, 0, 0);
                  vertex(buf, sx, 4.0F, ar + 0.0F, su, (ap + 0.0F) * 0.00390625F + af, u, v, w, 1, 0, 0);
                  vertex(buf, sx, 0.0F, ar + 0.0F, su, (ap + 0.0F) * 0.00390625F + af, u, v, w, 1, 0, 0);
                  count += 4;
               }
            }

            if (an > -1) {
               for (int au = 0; au < 8; au++) {
                  float sz = ar + au + 0.0F;
                  float sv = (ap + au + 0.5F) * 0.00390625F + af;
                  vertex(buf, aq + 0.0F, 4.0F, sz, (ao + 0.0F) * 0.00390625F + ae, sv, aa, ab, ac, 0, 0, -1);
                  vertex(buf, aq + 8.0F, 4.0F, sz, (ao + 8.0F) * 0.00390625F + ae, sv, aa, ab, ac, 0, 0, -1);
                  vertex(buf, aq + 8.0F, 0.0F, sz, (ao + 8.0F) * 0.00390625F + ae, sv, aa, ab, ac, 0, 0, -1);
                  vertex(buf, aq + 0.0F, 0.0F, sz, (ao + 0.0F) * 0.00390625F + ae, sv, aa, ab, ac, 0, 0, -1);
                  count += 4;
               }
            }

            if (an <= 1) {
               for (int av = 0; av < 8; av++) {
                  float sz = ar + av + 1.0F - 9.765625E-4F;
                  float sv = (ap + av + 0.5F) * 0.00390625F + af;
                  vertex(buf, aq + 0.0F, 4.0F, sz, (ao + 0.0F) * 0.00390625F + ae, sv, aa, ab, ac, 0, 0, 1);
                  vertex(buf, aq + 8.0F, 4.0F, sz, (ao + 8.0F) * 0.00390625F + ae, sv, aa, ab, ac, 0, 0, 1);
                  vertex(buf, aq + 8.0F, 0.0F, sz, (ao + 8.0F) * 0.00390625F + ae, sv, aa, ab, ac, 0, 0, 1);
                  vertex(buf, aq + 0.0F, 0.0F, sz, (ao + 0.0F) * 0.00390625F + ae, sv, aa, ab, ac, 0, 0, 1);
                  count += 4;
               }
            }
         }
      }

      buf.flip();
      if (vbo == 0) {
         vbo = GL15.glGenBuffers();
      }

      GL15.glBindBuffer(34962, vbo);
      GL15.glBufferData(34962, buf, 35044);
      GL15.glBindBuffer(34962, 0);
      vertices = count;
      rebuilds++;
   }

   private static void vertex(ByteBuffer buf, float x, float y, float z, float u, float v, float r, float g, float b, int nx, int ny, int nz) {
      buf.putFloat(x).putFloat(y).putFloat(z).putFloat(u).putFloat(v);
      buf.put((byte)(r * 255.0F)).put((byte)(g * 255.0F)).put((byte)(b * 255.0F)).put((byte)-52);
      buf.put((byte)(nx * 127)).put((byte)(ny * 127)).put((byte)(nz * 127)).put((byte)0);
   }

   public static void clear() {
      if (vbo != 0) {
         GL15.glDeleteBuffers(vbo);
         vbo = 0;
      }

      keyCellX = Integer.MIN_VALUE;
   }
}
