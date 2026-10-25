package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.emote.JointBend;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v26x.emote.JointLink;
import com.metrofico.pulsar.engine.v26x.mixin.BufferBuilderAccessor;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.PoseStack.Pose;
import java.nio.ByteOrder;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.ModelPart.Cube;
import net.minecraft.client.model.geom.ModelPart.Polygon;
import net.minecraft.client.model.geom.ModelPart.Vertex;
import net.minecraft.util.ARGB;
import net.minecraft.util.Mth;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public final class EntityBake {
   private static final boolean LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
   private static volatile boolean frameEnabled;
   private static final ThreadLocal<float[]> CORNERS = ThreadLocal.withInitial(() -> new float[24]);
   private static final ThreadLocal<Vector3f> NORMAL = ThreadLocal.withInitial(Vector3f::new);
   private static final ThreadLocal<float[]> HALF = ThreadLocal.withInitial(() -> new float[9]);
   private static final ThreadLocal<float[]> FACES = ThreadLocal.withInitial(() -> new float[18]);
   private static final ThreadLocal<float[]> BENT = ThreadLocal.withInitial(() -> new float[24]);

   private EntityBake() {
   }

   public static void beginFrame() {
      ClientOptions o = ClientOptions.get();
      frameEnabled = o.enabled("perf") && o.bool("perf.entityBake", true);
   }

   public static boolean enabled() {
      return frameEnabled;
   }

   public static boolean compile(EntityBake.Tables t, Pose pose, BufferBuilderAccessor bb, int light, int overlay, int color) {
      if (!bb.pulsar$entityFormat()) {
         return false;
      } else {
         Matrix4f m = pose.pose();
         float ox = m.m00() * t.x0 + m.m10() * t.y0 + m.m20() * t.z0 + m.m30();
         float oy = m.m01() * t.x0 + m.m11() * t.y0 + m.m21() * t.z0 + m.m31();
         float oz = m.m02() * t.x0 + m.m12() * t.y0 + m.m22() * t.z0 + m.m32();
         float axx = m.m00() * t.dx;
         float axy = m.m01() * t.dx;
         float axz = m.m02() * t.dx;
         float ayx = m.m10() * t.dy;
         float ayy = m.m11() * t.dy;
         float ayz = m.m12() * t.dy;
         float azx = m.m20() * t.dz;
         float azy = m.m21() * t.dz;
         float azz = m.m22() * t.dz;
         float[] c = CORNERS.get();

         for (int i = 0; i < 8; i++) {
            float x = ox;
            float y = oy;
            float z = oz;
            if ((i & 1) != 0) {
               x = ox + axx;
               y = oy + axy;
               z = oz + axz;
            }

            if ((i & 2) != 0) {
               x += ayx;
               y += ayy;
               z += ayz;
            }

            if ((i & 4) != 0) {
               x += azx;
               y += azy;
               z += azz;
            }

            c[i * 3] = x;
            c[i * 3 + 1] = y;
            c[i * 3 + 2] = z;
         }

         emit(t, c, null, pose, bb, light, overlay, color);
         return true;
      }
   }

   public static void compileBent(EntityBake.Tables t, JointLink link, Pose pose, VertexConsumer consumer, int light, int overlay, int color) {
      float[] h = HALF.get();
      ModelPart j = link.joint;
      JointBend.half(j.xRot, j.yRot, j.zRot, h);
      float[] local = BENT.get();

      for (int i = 0; i < 8; i++) {
         float x = t.x0 + ((i & 1) != 0 ? t.dx : 0.0F);
         float y = t.y0 + ((i & 2) != 0 ? t.dy : 0.0F);
         float z = t.z0 + ((i & 4) != 0 ? t.dz : 0.0F);
         boolean ring = link.upper == ((i & 2) != 0);
         if (!ring) {
            local[i * 3] = x;
            local[i * 3 + 1] = y;
            local[i * 3 + 2] = z;
         } else if (link.upper) {
            JointBend.upper(h, link.px, link.py, link.pz, x, y, z, local, i * 3);
         } else {
            JointBend.lower(h, x, y, z, local, i * 3);
         }
      }

      int polys = t.polyStart.length - 1;
      float[] faces = FACES.get();
      if (faces.length < polys * 3) {
         faces = new float[polys * 3];
         FACES.set(faces);
      }

      for (int p = 0; p < polys; p++) {
         int s = t.polyStart[p];
         if (t.polyStart[p + 1] - s < 4) {
            faces[p * 3] = t.normals[p * 3];
            faces[p * 3 + 1] = t.normals[p * 3 + 1];
            faces[p * 3 + 2] = t.normals[p * 3 + 2];
         } else {
            int a = t.corner[s] * 3;
            int b = t.corner[s + 1] * 3;
            int c = t.corner[s + 2] * 3;
            int d = t.corner[s + 3] * 3;
            float ux = local[c] - local[a];
            float uy = local[c + 1] - local[a + 1];
            float uz = local[c + 2] - local[a + 2];
            float vx = local[d] - local[b];
            float vy = local[d + 1] - local[b + 1];
            float vz = local[d + 2] - local[b + 2];
            float nx = uy * vz - uz * vy;
            float ny = uz * vx - ux * vz;
            float nz = ux * vy - uy * vx;
            float len = (float)Math.sqrt(nx * nx + ny * ny + nz * nz);
            if (len < 1.0E-9F) {
               nx = t.normals[p * 3];
               ny = t.normals[p * 3 + 1];
               nz = t.normals[p * 3 + 2];
            } else {
               if (nx * t.normals[p * 3] + ny * t.normals[p * 3 + 1] + nz * t.normals[p * 3 + 2] < 0.0F) {
                  len = -len;
               }

               nx /= len;
               ny /= len;
               nz /= len;
            }

            faces[p * 3] = nx;
            faces[p * 3 + 1] = ny;
            faces[p * 3 + 2] = nz;
         }
      }

      Matrix4f m = pose.pose();
      float[] w = CORNERS.get();

      for (int ix = 0; ix < 8; ix++) {
         float x = local[ix * 3];
         float y = local[ix * 3 + 1];
         float z = local[ix * 3 + 2];
         w[ix * 3] = m.m00() * x + m.m10() * y + m.m20() * z + m.m30();
         w[ix * 3 + 1] = m.m01() * x + m.m11() * y + m.m21() * z + m.m31();
         w[ix * 3 + 2] = m.m02() * x + m.m12() * y + m.m22() * z + m.m32();
      }

      if (consumer instanceof BufferBuilderAccessor bb && bb.pulsar$entityFormat()) {
         emit(t, w, faces, pose, bb, light, overlay, color);
      } else {
         Vector3f n = NORMAL.get();

         for (int px = 0; px < polys; px++) {
            pose.transformNormal(faces[px * 3], faces[px * 3 + 1], faces[px * 3 + 2], n);
            int v = t.polyStart[px];

            for (int end = t.polyStart[px + 1]; v < end; v++) {
               int ci = t.corner[v] * 3;
               consumer.addVertex(w[ci], w[ci + 1], w[ci + 2], color, t.uv[v * 2], t.uv[v * 2 + 1], overlay, light, n.x, n.y, n.z);
            }
         }
      }
   }

   private static void emit(EntityBake.Tables t, float[] c, float[] faces, Pose pose, BufferBuilderAccessor bb, int light, int overlay, int color) {
      float[] normals = faces != null ? faces : t.normals;
      int a = ARGB.toABGR(color);
      int abgr = LITTLE_ENDIAN ? a : Integer.reverseBytes(a);
      Vector3f n = NORMAL.get();
      int polys = t.polyStart.length - 1;

      for (int p = 0; p < polys; p++) {
         pose.transformNormal(normals[p * 3], normals[p * 3 + 1], normals[p * 3 + 2], n);
         byte nx = normalByte(n.x);
         byte ny = normalByte(n.y);
         byte nz = normalByte(n.z);
         int v = t.polyStart[p];

         for (int end = t.polyStart[p + 1]; v < end; v++) {
            int ci = t.corner[v] * 3;
            long ptr = bb.pulsar$beginVertex();
            bb.pulsar$setElementsToFill(0);
            MemoryUtil.memPutFloat(ptr, c[ci]);
            MemoryUtil.memPutFloat(ptr + 4L, c[ci + 1]);
            MemoryUtil.memPutFloat(ptr + 8L, c[ci + 2]);
            MemoryUtil.memPutInt(ptr + 12L, abgr);
            MemoryUtil.memPutFloat(ptr + 16L, t.uv[v * 2]);
            MemoryUtil.memPutFloat(ptr + 20L, t.uv[v * 2 + 1]);
            putPackedUv(ptr + 24L, overlay);
            putPackedUv(ptr + 28L, light);
            MemoryUtil.memPutByte(ptr + 32L, nx);
            MemoryUtil.memPutByte(ptr + 33L, ny);
            MemoryUtil.memPutByte(ptr + 34L, nz);
         }
      }
   }

   private static byte normalByte(float v) {
      return (byte)(Mth.clamp(v, -1.0F, 1.0F) * 127.0F);
   }

   private static void putPackedUv(long ptr, int packed) {
      if (LITTLE_ENDIAN) {
         MemoryUtil.memPutInt(ptr, packed);
      } else {
         MemoryUtil.memPutShort(ptr, (short)(packed & 65535));
         MemoryUtil.memPutShort(ptr + 2L, (short)(packed >> 16 & 65535));
      }
   }

   public static final class Tables {
      final float x0;
      final float y0;
      final float z0;
      final float dx;
      final float dy;
      final float dz;
      final float[] normals;
      final byte[] corner;
      final float[] uv;
      final int[] polyStart;

      public Tables(Cube cube) {
         float minX = Float.MAX_VALUE;
         float minY = Float.MAX_VALUE;
         float minZ = Float.MAX_VALUE;
         float maxX = -Float.MAX_VALUE;
         float maxY = -Float.MAX_VALUE;
         float maxZ = -Float.MAX_VALUE;
         int polys = cube.polygons.length;
         int n = 0;

         for (Polygon p : cube.polygons) {
            for (Vertex vert : p.vertices()) {
               minX = Math.min(minX, vert.x());
               maxX = Math.max(maxX, vert.x());
               minY = Math.min(minY, vert.y());
               maxY = Math.max(maxY, vert.y());
               minZ = Math.min(minZ, vert.z());
               maxZ = Math.max(maxZ, vert.z());
            }

            n += p.vertices().length;
         }

         this.x0 = minX / 16.0F;
         this.y0 = minY / 16.0F;
         this.z0 = minZ / 16.0F;
         this.dx = (maxX - minX) / 16.0F;
         this.dy = (maxY - minY) / 16.0F;
         this.dz = (maxZ - minZ) / 16.0F;
         this.normals = new float[polys * 3];
         this.corner = new byte[n];
         this.uv = new float[n * 2];
         this.polyStart = new int[polys + 1];
         int v = 0;

         for (int i = 0; i < polys; i++) {
            Polygon p = cube.polygons[i];
            this.normals[i * 3] = p.normal().x();
            this.normals[i * 3 + 1] = p.normal().y();
            this.normals[i * 3 + 2] = p.normal().z();
            this.polyStart[i] = v;

            for (Vertex vert : p.vertices()) {
               this.corner[v] = (byte)(
                  (vert.x() == maxX && maxX != minX ? 1 : 0) | (vert.y() == maxY && maxY != minY ? 2 : 0) | (vert.z() == maxZ && maxZ != minZ ? 4 : 0)
               );
               this.uv[v * 2] = vert.u();
               this.uv[v * 2 + 1] = vert.v();
               v++;
            }
         }

         this.polyStart[polys] = v;
      }
   }
}
