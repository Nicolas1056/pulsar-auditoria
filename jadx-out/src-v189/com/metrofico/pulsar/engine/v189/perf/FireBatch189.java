package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public final class FireBatch189 {
   private static final int POSITION_TEXTURE_BYTES = 20;
   private static final int POSITION_TEXTURE_COLOR_BYTES = 24;
   private static final int SHADOW_INTS = 6;
   private static final int[] SHADOW_STATE = new int[24];
   private static final Map<Long, FireBatch189.IntList> SHADOWS = new LinkedHashMap<>();
   private static boolean hasShadowState;
   private static final int FLOATS_PER_VERTEX = 5;
   private static final int[] STATE = new int[24];
   private static final int[] SAVED = new int[24];
   private static final Map<Long, FireBatch189.FloatList> BY_LIGHT = new LinkedHashMap<>();
   private static ByteBuffer upload = BufferUtils.createByteBuffer(4096).order(ByteOrder.nativeOrder());
   private static IntBuffer uploadInts = upload.asIntBuffer();
   private static boolean afterEntityBody;
   private static boolean hasState;
   private static int lightUnit;
   private static float lightU;
   private static float lightV;

   private FireBatch189() {
   }

   public static void light(int unit, float u, float v) {
      lightUnit = unit;
      lightU = u;
      lightV = v;
   }

   public static void entityBodyDone() {
      afterEntityBody = true;
   }

   public static void entityDone() {
      afterEntityBody = false;
   }

   public static boolean collecting() {
      return afterEntityBody && FrameFlags.perf && EntityCache189.inWorldPass();
   }

   public static boolean collect(bfd buffer) {
      int vertices = buffer.h();
      if (buffer.i() == 7 && vertices != 0 && vertices % 4 == 0) {
         ByteBuffer in = buffer.f();
         if (in.limit() / vertices == 24 && FrameFlags.shadowBatch && GlState189.blendEnabled() && !GlState189.depthMaskEnabled()) {
            return collectShadow(in, vertices);
         } else if (in.limit() / vertices == 20 && !GlState189.lightingEnabled()) {
            if (!hasState) {
               GlState189.snapshot(STATE);
               hasState = true;
            } else if (!GlState189.same(STATE)) {
               return false;
            }

            float[] modelView = MatrixMirror189.matrix();
            long key = (long)Float.floatToRawIntBits(lightU) << 32 | Float.floatToRawIntBits(lightV) & 4294967295L;
            FireBatch189.FloatList list = BY_LIGHT.computeIfAbsent(key, k -> new FireBatch189.FloatList());

            for (int i = 0; i < vertices; i++) {
               int p = i * 20;
               float x = in.getFloat(p);
               float y = in.getFloat(p + 4);
               float z = in.getFloat(p + 8);
               list.add(
                  modelView[0] * x + modelView[4] * y + modelView[8] * z + modelView[12],
                  modelView[1] * x + modelView[5] * y + modelView[9] * z + modelView[13],
                  modelView[2] * x + modelView[6] * y + modelView[10] * z + modelView[14],
                  in.getFloat(p + 12),
                  in.getFloat(p + 16)
               );
            }

            list.lightU = lightU;
            list.lightV = lightV;
            return true;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private static boolean collectShadow(ByteBuffer in, int vertices) {
      if (!hasShadowState) {
         GlState189.snapshot(SHADOW_STATE);
         hasShadowState = true;
      } else if (!GlState189.same(SHADOW_STATE)) {
         return false;
      }

      float[] m = MatrixMirror189.matrix();
      long key = (long)Float.floatToRawIntBits(lightU) << 32 | Float.floatToRawIntBits(lightV) & 4294967295L;
      FireBatch189.IntList list = SHADOWS.computeIfAbsent(key, k -> new FireBatch189.IntList());
      int o = list.reserve(vertices * 6);
      int[] out = list.data;

      for (int i = 0; i < vertices; o += 6) {
         int p = i * 24;
         float x = in.getFloat(p);
         float y = in.getFloat(p + 4);
         float z = in.getFloat(p + 8);
         out[o] = Float.floatToRawIntBits(m[0] * x + m[4] * y + m[8] * z + m[12]);
         out[o + 1] = Float.floatToRawIntBits(m[1] * x + m[5] * y + m[9] * z + m[13]);
         out[o + 2] = Float.floatToRawIntBits(m[2] * x + m[6] * y + m[10] * z + m[14]);
         out[o + 3] = in.getInt(p + 12);
         out[o + 4] = in.getInt(p + 16);
         out[o + 5] = in.getInt(p + 20);
         i++;
      }

      list.lightU = lightU;
      list.lightV = lightV;
      return true;
   }

   private static void flushShadows() {
      hasShadowState = false;
      boolean any = false;

      for (FireBatch189.IntList l : SHADOWS.values()) {
         any |= l.size > 0;
      }

      if (any) {
         ArrayBufferGuard189.beforeClientArrays();
         GlState189.snapshot(SAVED);
         GlState189.apply(SHADOW_STATE);
         bfl.E();
         bfl.D();
         GL11.glEnableClientState(32884);
         GL11.glEnableClientState(32888);
         GL11.glEnableClientState(32886);

         for (FireBatch189.IntList list : SHADOWS.values()) {
            if (list.size != 0) {
               bqs.a(lightUnit, list.lightU, list.lightV);
               int bytes = list.size * 4;
               if (upload.capacity() < bytes) {
                  upload = BufferUtils.createByteBuffer(Integer.highestOneBit(bytes) << 1).order(ByteOrder.nativeOrder());
                  uploadInts = upload.asIntBuffer();
               }

               uploadInts.clear();
               uploadInts.put(list.data, 0, list.size);
               upload.position(0).limit(bytes);
               GL11.glVertexPointer(3, 5126, 24, upload);
               upload.position(12);
               GL11.glTexCoordPointer(2, 5126, 24, upload);
               upload.position(20);
               GL11.glColorPointer(4, 5121, 24, upload);
               GL11.glDrawArrays(7, 0, list.size / 6);
               upload.clear();
               list.size = 0;
            }
         }

         GL11.glDisableClientState(32886);
         GL11.glDisableClientState(32888);
         GL11.glDisableClientState(32884);
         bfl.G();
         bqs.a(lightUnit, lightU, lightV);
         bfl.F();
         GlState189.apply(SAVED);
      }
   }

   public static void flush() {
      flushShadows();
      if (BY_LIGHT.isEmpty()) {
         hasState = false;
      } else {
         ArrayBufferGuard189.beforeClientArrays();
         GlState189.snapshot(SAVED);
         GlState189.apply(STATE);
         bfl.E();
         bfl.D();
         GL11.glEnableClientState(32884);
         GL11.glEnableClientState(32888);

         for (FireBatch189.FloatList list : BY_LIGHT.values()) {
            if (list.size != 0) {
               bqs.a(lightUnit, list.lightU, list.lightV);
               ByteBuffer data = fill(list);
               data.position(0);
               GL11.glVertexPointer(3, 5126, 20, data);
               data.position(12);
               GL11.glTexCoordPointer(2, 5126, 20, data);
               GL11.glDrawArrays(7, 0, list.size / 5);
               list.size = 0;
            }
         }

         GL11.glDisableClientState(32888);
         GL11.glDisableClientState(32884);
         bqs.a(lightUnit, lightU, lightV);
         bfl.F();
         GlState189.apply(SAVED);
         hasState = false;
      }
   }

   private static ByteBuffer fill(FireBatch189.FloatList list) {
      int bytes = list.size * 4;
      if (upload.capacity() < bytes) {
         upload = BufferUtils.createByteBuffer(Integer.highestOneBit(bytes) << 1).order(ByteOrder.nativeOrder());
         uploadInts = upload.asIntBuffer();
      }

      upload.clear();

      for (int i = 0; i < list.size; i++) {
         upload.putFloat(list.data[i]);
      }

      upload.flip();
      return upload;
   }

   private static final class FloatList {
      float[] data = new float[320];
      int size;
      float lightU;
      float lightV;

      void add(float x, float y, float z, float u, float v) {
         if (this.size + 5 > this.data.length) {
            this.data = Arrays.copyOf(this.data, this.data.length * 2);
         }

         this.data[this.size++] = x;
         this.data[this.size++] = y;
         this.data[this.size++] = z;
         this.data[this.size++] = u;
         this.data[this.size++] = v;
      }
   }

   private static final class IntList {
      int[] data = new int[384];
      int size;
      float lightU;
      float lightV;

      int reserve(int n) {
         if (this.size + n > this.data.length) {
            this.data = Arrays.copyOf(this.data, Math.max(this.data.length * 2, this.size + n));
         }

         int o = this.size;
         this.size += n;
         return o;
      }
   }
}
