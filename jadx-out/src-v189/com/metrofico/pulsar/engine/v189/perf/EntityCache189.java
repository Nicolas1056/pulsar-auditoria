package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.modules.Visuals17;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.api.social.PulsarPlayers;
import com.metrofico.pulsar.engine.v189.mixin.LivingEntityRendererAccessor;
import com.metrofico.pulsar.engine.v189.visuals.LayerTint189;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

public final class EntityCache189 {
   private static final int ENTITY_INTS = 6;
   private static final int ITEM_INTS = 7;
   public static final int SEG_NONE = 0;
   public static final int SEG_MODEL = 1;
   public static final int SEG_FEATURE_COMBINE = 2;
   public static final int SEG_FEATURE_PLAIN = 3;
   private static final int GLINT_NONE = 0;
   private static final int GLINT_ARMOR_0 = 1;
   private static final int GLINT_ARMOR_1 = 2;
   private static final int GLINT_ITEM_0 = 3;
   private static final int GLINT_ITEM_1 = 4;
   private static final int GLINT_UNKNOWN = -1;
   private static final Map<pk, EntityCache189.Cached> CACHE = new IdentityHashMap<>();
   private static pk capturingFor;
   private static int vbo;
   private static int arenaCapacity;
   private static int arenaUsed;
   private static ByteBuffer uploadBytes = ByteBuffer.allocateDirect(65536).order(ByteOrder.nativeOrder());
   private static IntBuffer uploadInts = uploadBytes.asIntBuffer();
   private static EntityCache189.Cached capturing;
   private static boolean failed;
   private static String failReason;
   private static final RawStream189 STREAM = new RawStream189(16384);
   private static int batchStart;
   private static int batchStride;
   private static final float[] TO_LOCAL = new float[16];
   private static final float[] TMP = new float[16];
   private static double capX;
   private static double capY;
   private static double capZ;
   private static boolean inWorldPass;
   private static final long CAPTURE_BUDGET_NANOS = 150000L;
   private static long frameCaptureNanos;
   private static long captureStart;
   private static boolean firstCapture;
   private static final Set<String> REASONS = new HashSet<>();
   private static int segment;
   private static boolean textureMode;
   private static final float[] TEX_OPS = new float[40];
   private static int texOps;
   private static final int[] TEX_STACK = new int[8];
   private static int texSp;
   private static final int OP_SCALE = 1;
   private static final int OP_ROTATE = 2;
   private static final int OP_TRANSLATE = 3;
   private static int currentGlint;
   private static int frame;
   private static int frameReplayed;
   private static int frameCaptured;
   private static int frameLive;
   private static int frameBatches;
   private static int frameMerged;
   static volatile int lastReplayed;
   static volatile int lastCaptured;
   static volatile int lastLive;
   static volatile int lastBatches;
   static volatile long lastCaptureNanos;
   private static long sumCaptureNanos;
   private static long sumReplayNanos;
   private static long sumFrames;
   private static long sumEntityNanos;
   private static long entityStartNanos;
   private static long bodyDoneNanos;
   private static long sumPostNanos;
   private static long sumFlushNanos;
   private static long flushStart;
   private static long sumFireNanos;
   static final int[] WHY = new int[7];
   static volatile String lastWhy = "";
   private static int[] order = new int[64];
   private static int[] scratch = new int[16384];
   private static final List<EntityCache189.Batch> MERGED = new ArrayList<>();
   private static int mergeStamp;
   private static float glintScaleNow = 8.0F;
   private static final float[] TMP2 = new float[16];
   private static final int ALIGN = 42;

   static boolean ownsVbo(int id) {
      return id == vbo;
   }

   public static void entityStart() {
      entityStartNanos = System.nanoTime();
   }

   public static void entityEnd() {
      long now = System.nanoTime();
      sumEntityNanos = sumEntityNanos + (now - entityStartNanos);
      if (bodyDoneNanos != 0L) {
         sumPostNanos = sumPostNanos + (now - bodyDoneNanos);
         bodyDoneNanos = 0L;
      }
   }

   public static void bodyDone() {
      bodyDoneNanos = System.nanoTime();
   }

   public static void flushStart() {
      flushStart = System.nanoTime();
   }

   public static void flushMid() {
      sumFireNanos = sumFireNanos + (System.nanoTime() - flushStart);
   }

   public static void flushEnd() {
      sumFlushNanos = sumFlushNanos + (System.nanoTime() - flushStart);
   }

   static String averages() {
      long f = Math.max(1L, sumFrames);
      String s = String.format(
         Locale.ROOT,
         "capt %.3f · repro %.3f · por entidad %.3f (tras el cuerpo %.3f) · vaciados %.3f (sombras y fuego %.3f) ms/frame",
         sumCaptureNanos / 1000000.0 / f,
         sumReplayNanos / 1000000.0 / f,
         sumEntityNanos / 1000000.0 / f,
         sumPostNanos / 1000000.0 / f,
         sumFlushNanos / 1000000.0 / f,
         sumFireNanos / 1000000.0 / f
      );
      sumFireNanos = 0L;
      sumFlushNanos = 0L;
      sumPostNanos = 0L;
      sumEntityNanos = 0L;
      sumFrames = 0L;
      sumReplayNanos = 0L;
      sumCaptureNanos = 0L;
      return s;
   }

   private EntityCache189() {
   }

   public static boolean enabled() {
      return FrameFlags.entityCache && FrameFlags.entityBatch;
   }

   public static int currentFrame() {
      return frame;
   }

   public static void worldPass(boolean in) {
      inWorldPass = in;
   }

   public static boolean inWorldPass() {
      return inWorldPass;
   }

   public static boolean capturing() {
      return capturing != null && !failed;
   }

   public static void unsupported(String reason) {
      if (capturing != null && !failed) {
         failed = true;
         failReason = reason;
      }
   }

   public static void frame() {
      frame++;
      lastReplayed = frameReplayed;
      lastCaptured = frameCaptured;
      lastLive = frameLive;
      lastBatches = frameBatches;
      lastCaptureNanos = frameCaptureNanos;
      sumCaptureNanos = sumCaptureNanos + frameCaptureNanos;
      sumFrames++;
      if ((frame & 0xFF) == 0) {
         lastWhy = "apagada "
            + WHY[0]
            + " · sin pasada "
            + WHY[1]
            + " · cámara "
            + WHY[2]
            + " · daño "
            + WHY[3]
            + " · brillo "
            + WHY[6]
            + " · anidada "
            + WHY[4]
            + " · espera "
            + WHY[5];
         Arrays.fill(WHY, 0);
      }

      frameBatches = 0;
      frameLive = 0;
      frameCaptured = 0;
      frameReplayed = 0;
      frameCaptureNanos = 0L;
      if ((frame & 1023) == 0) {
         Iterator<Entry<pk, EntityCache189.Cached>> it = CACHE.entrySet().iterator();

         while (it.hasNext()) {
            Entry<pk, EntityCache189.Cached> e = it.next();
            if (e.getKey().I || frame - e.getValue().lastFrame > 2048) {
               it.remove();
            }
         }
      }
   }

   private static long intervalNanos(pk entity, double x, double y, double z) {
      ave mc = ave.A();
      double dist = Math.sqrt(x * x + y * y + z * z);
      double fov = Math.toRadians(Math.max(10.0F, Math.min(mc.t.aI, 110.0F)));
      double px = dist < 0.5 ? 1.0E9 : entity.K / dist * (mc.e / (2.0 * Math.tan(fov / 2.0)));
      if (Zoom.active()) {
         px = 1.0E9;
      }

      if (animating(entity)) {
         return px >= 96.0 ? 8333333L : 16666666L;
      } else {
         return px >= 300.0 ? 8333333L : (px >= 96.0 ? 16666666L : (px >= 40.0 ? 33333333L : 50000000L));
      }
   }

   private static boolean animating(pk e) {
      if (e.s != e.p || e.t != e.q || e.u != e.r || e.y != e.A || e.z != e.B) {
         return true;
      } else {
         return !(e instanceof pr l) ? false : l.ar || l.aK != l.aL || l.aB > 0.01F || l.aA > 0.01F;
      }
   }

   private static boolean eligible(pk entity) {
      if (!enabled()) {
         WHY[0]++;
         return false;
      } else if (!inWorldPass) {
         WHY[1]++;
         return false;
      } else if (entity == ave.A().ac()) {
         WHY[2]++;
         return false;
      } else if (entity instanceof pr l && (l.ax > 0 || l.au > 0 && !FrameFlags.entityCacheCombat)) {
         WHY[3]++;
         return false;
      } else if (!FrameFlags.entityCacheCombat && entity instanceof pr l && glints(l)) {
         WHY[6]++;
         return false;
      } else {
         return true;
      }
   }

   private static boolean glints(pr l) {
      zx held = l.bA();
      if (held != null && held.t()) {
         return true;
      } else {
         zx[] armor = l.as();
         if (armor != null) {
            for (zx st : armor) {
               if (st != null && st.t()) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public static boolean replayOrBegin(pk entity, Object renderer, float tickDelta, double x, double y, double z) {
      if (capturing != null) {
         WHY[4]++;
         frameLive++;
         return false;
      } else if (!eligible(entity)) {
         frameLive++;
         return false;
      } else {
         EntityCache189.Cached c = CACHE.get(entity);
         long now = System.nanoTime();
         if (c == null) {
            c = new EntityCache189.Cached();
            CACHE.put(entity, c);
         }

         c.lastFrame = frame;
         if (c.retryAt != 0L) {
            if (now < c.retryAt) {
               WHY[5]++;
               frameLive++;
               return false;
            }

            c.retryAt = 0L;
         }

         if (c.valid) {
            long interval = intervalNanos(entity, x, y, z);
            long age = now - c.capturedAt;
            if (age < interval || frameCaptureNanos > 150000L && age < interval * 3L) {
               replay(c, entity, renderer, tickDelta, x, y, z);
               sumReplayNanos = sumReplayNanos + (System.nanoTime() - now);
               frameReplayed++;
               return true;
            }
         }

         captureStart = now;
         firstCapture = c.capturedAt == 0L;
         EntityBatch189.flush();
         capturing = c;
         capturingFor = entity;
         failed = false;
         c.valid = false;
         c.batchCount = 0;
         c.labelCount = 0;
         STREAM.reset();
         batchStart = 0;
         batchStride = 0;
         segment = 0;
         textureMode = false;
         texOps = 0;
         texSp = 0;
         currentGlint = 0;
         capX = x;
         capY = y;
         capZ = z;
         invertAffine(MatrixMirror189.matrix(), TMP);
         System.arraycopy(TMP, 0, TO_LOCAL, 0, 16);
         TO_LOCAL[12] = TO_LOCAL[12] - (float)x;
         TO_LOCAL[13] = TO_LOCAL[13] - (float)y;
         TO_LOCAL[14] = TO_LOCAL[14] - (float)z;
         return false;
      }
   }

   public static void end(pk entity, Object renderer, float tickDelta, double x, double y, double z) {
      EntityCache189.Cached c = capturing;
      if (c != null && entity == capturingFor) {
         capturingFor = null;
         EntityBatch189.flush();
         closeBatch();
         capturing = null;
         GlState189.snapshot(c.endState);
         if (failed) {
            c.retryAt = System.nanoTime() + (2000000000L << Math.min(5, c.failures++));
            String why = entity.getClass().getSimpleName() + ": " + failReason;
            if (REASONS.size() < 12 && REASONS.add(why)) {
               ClientOptions.LOG.accept("perf: caché de entidades — no se guarda " + why);
            }
         }

         if (!failed && FrameFlags.entityCacheMerge) {
            merge(c);
         }

         c.aligned = false;
         if (!failed && FrameFlags.drawFirst) {
            align(c);
         }

         c.size = STREAM.size;
         if (c.size > 0) {
            upload(c);
         }

         long done = System.nanoTime();
         frameCaptureNanos = frameCaptureNanos + (done - captureStart);
         c.capturedAt = firstCapture ? done - (entity.F() * -1640531527 >>> 12) % 16000000L : done;
         c.valid = !failed;
         if (!failed) {
            c.failures = 0;
         }

         replay(c, entity, renderer, tickDelta, x, y, z);
         frameCaptured++;
      }
   }

   private static void merge(EntityCache189.Cached c) {
      int n = c.batchCount;
      if (n >= 3) {
         for (int i = 0; i < n; i++) {
            EntityCache189.Batch b = c.batches.get(i);
            if (b.glint != 0 && !GlState189.equalDepthOverlay(b.state)) {
               mergeSkipped("brillo " + b.glint + " sin profundidad igual: " + GlState189.describe(b.state));
               return;
            }
         }

         if (order.length < n) {
            order = new int[n * 2];
         }

         int stamp = ++mergeStamp;
         int k = 0;
         int ix = 0;

         while (ix < n) {
            int end = ix;

            while (end < n && (c.batches.get(end).glint != 0 || GlState189.opaque(c.batches.get(end).state))) {
               end++;
            }

            if (end == ix) {
               order[k++] = ix++;
            } else {
               k = group(c, ix, end, false, stamp, k);
               k = group(c, ix, end, true, stamp, k);
               ix = end;
            }
         }

         ix = STREAM.size;
         if (scratch.length < ix) {
            scratch = new int[Integer.highestOneBit(ix) * 2];
         }

         while (MERGED.size() < n) {
            MERGED.add(new EntityCache189.Batch());
         }

         int out = 0;
         int m = 0;
         EntityCache189.Batch last = null;

         for (int j = 0; j < k; j++) {
            EntityCache189.Batch b = c.batches.get(order[j]);
            int len = b.vertices * b.stride;
            System.arraycopy(STREAM.data, b.first, scratch, out, len);
            if (last != null && last.sameKey(b)) {
               last.vertices = last.vertices + b.vertices;
            } else {
               last = MERGED.get(m++);
               last.copy(b);
               last.first = out;
            }

            out += len;
         }

         if (out == ix) {
            System.arraycopy(scratch, 0, STREAM.data, 0, ix);

            for (int j = 0; j < m; j++) {
               c.batches.get(j).copy(MERGED.get(j));
            }

            frameMerged += n - m;
            c.batchCount = m;
         }
      }
   }

   private static int group(EntityCache189.Cached c, int from, int to, boolean glints, int stamp, int k) {
      for (int a = from; a < to; a++) {
         EntityCache189.Batch x = c.batches.get(a);
         if (x.glint != 0 == glints && x.mark != stamp) {
            for (int y = a; y < to; y++) {
               EntityCache189.Batch z = c.batches.get(y);
               if (z.glint != 0 == glints && z.mark != stamp && z.sameKey(x)) {
                  z.mark = stamp;
                  order[k++] = y;
               }
            }
         }
      }

      return k;
   }

   private static void mergeSkipped(String why) {
      if (REASONS.size() < 24 && REASONS.add("merge:" + why)) {
         ClientOptions.LOG.accept("perf: caché de entidades — sin fusión de lotes: " + why);
      }
   }

   public static float[] localMatrix(float[] modelview, float[] out) {
      for (int col = 0; col < 4; col++) {
         for (int row = 0; row < 4; row++) {
            out[col * 4 + row] = TO_LOCAL[row] * modelview[col * 4]
               + TO_LOCAL[4 + row] * modelview[col * 4 + 1]
               + TO_LOCAL[8 + row] * modelview[col * 4 + 2]
               + TO_LOCAL[12 + row] * modelview[col * 4 + 3];
         }
      }

      return out;
   }

   static RawStream189 stream(int strideInts) {
      if (batchStride != 0 && batchStride != strideInts) {
         closeBatch();
      }

      batchStride = strideInts;
      return STREAM;
   }

   public static void closeBatch() {
      EntityCache189.Cached c = capturing;
      if (c != null && batchStride != 0 && STREAM.size != batchStart) {
         EntityCache189.Batch b;
         if (c.batchCount < c.batches.size()) {
            b = c.batches.get(c.batchCount);
         } else {
            b = new EntityCache189.Batch();
            c.batches.add(b);
         }

         c.batchCount++;
         b.segment = segment;
         b.glint = glintNow();
         b.glintScale = glintScaleNow;
         if (b.glint == -1) {
            unsupported("matriz de textura desconocida");
         }

         b.first = batchStart;
         b.stride = batchStride;
         b.vertices = (STREAM.size - batchStart) / batchStride;
         GlState189.snapshot(b.state);
         batchStart = STREAM.size;
         batchStride = 0;
      } else {
         batchStride = batchStride == 0 ? 0 : batchStride;
         batchStart = STREAM.size;
      }
   }

   public static void segment(int seg) {
      if (capturing != null && seg != segment) {
         EntityBatch189.flush();
         closeBatch();
         segment = seg;
      }
   }

   public static void matrixMode(int mode) {
      if (mode == 5889) {
         unsupported("matriz de proyección");
      } else if (!FrameFlags.entityCacheCombat && mode == 5890) {
         unsupported("matriz de textura");
      }

      textureMode = mode == 5890;
   }

   private static boolean recordingTexture() {
      if (capturing != null && !failed && textureMode) {
         EntityBatch189.flush();
         closeBatch();
         return true;
      } else {
         return false;
      }
   }

   public static void textureIdentity() {
      if (recordingTexture()) {
         texOps = 0;
         currentGlint = 0;
      }
   }

   public static void texturePush() {
      if (recordingTexture()) {
         if (texSp == TEX_STACK.length) {
            unsupported("pila de textura");
            return;
         }

         TEX_STACK[texSp++] = texOps;
      }
   }

   public static void texturePop() {
      if (recordingTexture()) {
         texOps = texSp > 0 ? TEX_STACK[--texSp] : 0;
         currentGlint = glintNow();
      }
   }

   public static void textureOp(int op, float a, float b, float c, float d) {
      if (recordingTexture()) {
         if (texOps * 5 == TEX_OPS.length) {
            unsupported("matriz de textura larga");
            return;
         }

         int o = texOps++ * 5;
         TEX_OPS[o] = op;
         TEX_OPS[o + 1] = a;
         TEX_OPS[o + 2] = b;
         TEX_OPS[o + 3] = c;
         TEX_OPS[o + 4] = d;
         currentGlint = glintNow();
      }
   }

   public static void textureScale(float x, float y, float z) {
      textureOp(1, x, y, z, 0.0F);
   }

   public static void textureRotate(float angle, float x, float y, float z) {
      textureOp(2, angle, x, y, z);
   }

   public static void textureTranslate(float x, float y, float z) {
      textureOp(3, x, y, z, 0.0F);
   }

   private static boolean op(int i, int code, float a, float b, float c, float d) {
      int o = i * 5;
      return TEX_OPS[o] == code && TEX_OPS[o + 1] == a && TEX_OPS[o + 2] == b && TEX_OPS[o + 3] == c && TEX_OPS[o + 4] == d;
   }

   private static int glintNow() {
      if (texOps == 0) {
         return 0;
      } else {
         if (texOps == 3) {
            float third = 0.33333334F;
            int t = 10;
            if (op(0, 1, third, third, third, 0.0F) && TEX_OPS[t] == 3.0F && TEX_OPS[t + 1] == 0.0F && TEX_OPS[t + 3] == 0.0F) {
               if (op(1, 2, 30.0F, 0.0F, 0.0F, 1.0F)) {
                  return 1;
               }

               if (op(1, 2, -30.0F, 0.0F, 0.0F, 1.0F)) {
                  return 2;
               }
            }

            int var3 = 5;
            float s = TEX_OPS[1];
            if (TEX_OPS[0] == 1.0F
               && s > 0.0F
               && TEX_OPS[2] == s
               && TEX_OPS[3] == s
               && TEX_OPS[4] == 0.0F
               && TEX_OPS[var3] == 3.0F
               && TEX_OPS[var3 + 2] == 0.0F
               && TEX_OPS[var3 + 3] == 0.0F) {
               glintScaleNow = s;
               if (op(2, 2, -50.0F, 0.0F, 0.0F, 1.0F)) {
                  return 3;
               }

               if (op(2, 2, 10.0F, 0.0F, 0.0F, 1.0F)) {
                  return 4;
               }
            }
         }

         return -1;
      }
   }

   private static void applyGlint(int glint, float scale, pk entity, float tickDelta) {
      bfl.n(5890);
      bfl.D();
      if (glint != 1 && glint != 2) {
         bfl.a(scale, scale, scale);
         if (glint == 3) {
            float shift = (float)(ave.J() % 3000L) / 3000.0F / 8.0F;
            bfl.b(scale == 8.0F ? shift : Visuals17.glintShift(shift, scale), 0.0F, 0.0F);
            bfl.b(-50.0F, 0.0F, 0.0F, 1.0F);
         } else {
            float shift = -((float)(ave.J() % 4873L) / 4873.0F / 8.0F);
            bfl.b(scale == 8.0F ? shift : Visuals17.glintShift(shift, scale), 0.0F, 0.0F);
            bfl.b(10.0F, 0.0F, 0.0F, 1.0F);
         }
      } else {
         int i = glint == 1 ? 0 : 1;
         float f = (entity instanceof pr l ? l.W : 0.0F) + tickDelta;
         bfl.a(0.33333334F, 0.33333334F, 0.33333334F);
         bfl.b(30.0F - i * 60.0F, 0.0F, 0.0F, 1.0F);
         bfl.b(0.0F, f * (0.001F + i * 0.003F) * 20.0F, 0.0F);
      }

      bfl.n(5888);
   }

   private static void clearGlint() {
      bfl.n(5890);
      bfl.D();
      bfl.n(5888);
   }

   public static boolean captureBuffer(bfd buffer) {
      if (!capturing()) {
         return false;
      } else if (buffer.i() == 7 && buffer.g() == bms.g && FrameFlags.entityCacheMaps) {
         return capturePositionTexture(buffer);
      } else if (buffer.g() == bms.b && buffer.i() == 7) {
         EntityBatch189.flush();
         int n = buffer.h();
         if (n == 0) {
            return true;
         } else {
            float[] m = localMatrix(MatrixMirror189.matrix(), TMP2);
            RawStream189 s = stream(7);
            int o = s.reserve(n * 7);
            int[] out = s.data;
            ByteBuffer in = buffer.f();

            for (int i = 0; i < n; i++) {
               int p = i * 28;
               float x = in.getFloat(p);
               float y = in.getFloat(p + 4);
               float z = in.getFloat(p + 8);
               out[o] = Float.floatToRawIntBits(m[0] * x + m[4] * y + m[8] * z + m[12]);
               out[o + 1] = Float.floatToRawIntBits(m[1] * x + m[5] * y + m[9] * z + m[13]);
               out[o + 2] = Float.floatToRawIntBits(m[2] * x + m[6] * y + m[10] * z + m[14]);
               out[o + 3] = in.getInt(p + 12);
               out[o + 4] = in.getInt(p + 16);
               out[o + 5] = in.getInt(p + 20);
               int nb = in.getInt(p + 24);
               float nx = (byte)nb / 127.0F;
               float ny = (byte)(nb >> 8) / 127.0F;
               float nz = (byte)(nb >> 16) / 127.0F;
               float tx = m[0] * nx + m[4] * ny + m[8] * nz;
               float ty = m[1] * nx + m[5] * ny + m[9] * nz;
               float tz = m[2] * nx + m[6] * ny + m[10] * nz;
               float len = (float)Math.sqrt(tx * tx + ty * ty + tz * tz);
               if (len > 1.0E-6F) {
                  tx /= len;
                  ty /= len;
                  tz /= len;
               }

               out[o + 6] = (int)(tx * 127.0F) & 0xFF | ((int)(ty * 127.0F) & 0xFF) << 8 | ((int)(tz * 127.0F) & 0xFF) << 16;
               o += 7;
            }

            return true;
         }
      } else {
         unsupported("formato " + buffer.g());
         return false;
      }
   }

   private static boolean capturePositionTexture(bfd buffer) {
      if (GlState189.lightingEnabled()) {
         unsupported("posición y UV con luz");
         return false;
      } else {
         EntityBatch189.flush();
         int n = buffer.h();
         if (n == 0) {
            return true;
         } else {
            float[] m = localMatrix(MatrixMirror189.matrix(), TMP2);
            int color = GlState189.packedColor();
            int up = 32512;
            RawStream189 s = stream(7);
            int o = s.reserve(n * 7);
            int[] out = s.data;
            ByteBuffer in = buffer.f();

            for (int i = 0; i < n; i++) {
               int p = i * 20;
               float x = in.getFloat(p);
               float y = in.getFloat(p + 4);
               float z = in.getFloat(p + 8);
               out[o] = Float.floatToRawIntBits(m[0] * x + m[4] * y + m[8] * z + m[12]);
               out[o + 1] = Float.floatToRawIntBits(m[1] * x + m[5] * y + m[9] * z + m[13]);
               out[o + 2] = Float.floatToRawIntBits(m[2] * x + m[6] * y + m[10] * z + m[14]);
               out[o + 3] = color;
               out[o + 4] = in.getInt(p + 12);
               out[o + 5] = in.getInt(p + 16);
               out[o + 6] = up;
               o += 7;
            }

            return true;
         }
      }
   }

   public static boolean label(String text, double d, double e, double f, float height, int k, int maxDistance, UUID markPlayer) {
      EntityCache189.Cached c = capturing;
      if (c != null && !failed) {
         EntityCache189.Label l;
         if (c.labelCount < c.labels.size()) {
            l = c.labels.get(c.labelCount);
         } else {
            l = new EntityCache189.Label();
            c.labels.add(l);
         }

         c.labelCount++;
         l.text = text;
         l.markPlayer = markPlayer;
         l.dx = d - capX;
         l.dy = e - capY;
         l.dz = f - capZ;
         l.height = height;
         l.k = k;
         l.maxDistance = maxDistance;
         return true;
      } else {
         return false;
      }
   }

   private static int roundUp(int v) {
      return (v + 42 - 1) / 42 * 42;
   }

   private static void align(EntityCache189.Cached c) {
      int n = c.batchCount;
      int need = 0;

      for (int i = 0; i < n; i++) {
         EntityCache189.Batch b = c.batches.get(i);
         need = roundUp(need) + b.vertices * b.stride;
      }

      if (scratch.length < need) {
         scratch = new int[Integer.highestOneBit(need) * 2];
      }

      Arrays.fill(scratch, 0, need, 0);
      int out = 0;

      for (int i = 0; i < n; i++) {
         EntityCache189.Batch b = c.batches.get(i);
         out = roundUp(out);
         int len = b.vertices * b.stride;
         System.arraycopy(STREAM.data, b.first, scratch, out, len);
         b.first = out;
         out += len;
      }

      STREAM.size = 0;
      STREAM.reserve(need);
      System.arraycopy(scratch, 0, STREAM.data, 0, need);
      c.aligned = true;
   }

   private static void upload(EntityCache189.Cached c) {
      if (c.offset < 0 || c.capacity < c.size) {
         int want = roundUp(Math.max(c.size + (c.size >> 2), 256));
         arenaUsed = roundUp(arenaUsed);
         if (arenaUsed + want > arenaCapacity) {
            growArena(want);
         }

         c.offset = arenaUsed;
         c.capacity = want;
         arenaUsed += want;
      }

      if (uploadInts.capacity() < c.size) {
         uploadBytes = ByteBuffer.allocateDirect(Integer.highestOneBit(c.size) * 8).order(ByteOrder.nativeOrder());
         uploadInts = uploadBytes.asIntBuffer();
      }

      uploadInts.clear();
      uploadInts.put(STREAM.data, 0, c.size);
      uploadBytes.position(0).limit(c.size * 4);
      bqs.g(bqs.R, vbo);
      GL15.glBufferSubData(bqs.R, c.offset * 4L, uploadBytes);
      uploadBytes.clear();
      bqs.g(bqs.R, 0);
   }

   private static void growArena(int want) {
      arenaCapacity = Math.max(1048576, Math.max(arenaCapacity * 2, want * 2));
      if (vbo != 0) {
         bqs.g(vbo);
      }

      vbo = bqs.e();
      bqs.g(bqs.R, vbo);
      GL15.glBufferData(bqs.R, arenaCapacity * 4L, 35048);
      bqs.g(bqs.R, 0);
      arenaUsed = 0;

      for (EntityCache189.Cached other : CACHE.values()) {
         other.offset = -1;
         other.capacity = 0;
         other.valid = false;
      }
   }

   private static void replay(EntityCache189.Cached c, pk entity, Object renderer, float tickDelta, double x, double y, double z) {
      EntityBatch189.flush();
      if (c.batchCount > 0 && c.size > 0 && c.offset >= 0) {
         LivingEntityRendererAccessor hurt = entity instanceof pr l && l.au > 0 && renderer instanceof LivingEntityRendererAccessor a ? a : null;
         int openSeg = 0;
         boolean overlay = false;
         boolean glintSet = false;
         bfl.E();
         bfl.b((float)x, (float)y, (float)z);
         bqs.g(bqs.R, vbo);
         GL11.glEnableClientState(32884);
         GL11.glEnableClientState(32888);
         GL11.glEnableClientState(32885);
         boolean colors = false;
         int pointerStride = 0;

         try {
            for (int i = 0; i < c.batchCount; i++) {
               EntityCache189.Batch b = c.batches.get(i);
               if (hurt != null && b.segment != openSeg) {
                  if (overlay) {
                     hurt.pulsar$overlayEnd();
                     overlay = false;
                  }

                  openSeg = b.segment;
                  if (openSeg != 0) {
                     LayerTint189.active = openSeg == 2;

                     try {
                        overlay = hurt.pulsar$overlayBegin((pr)entity, tickDelta, openSeg != 3);
                     } finally {
                        LayerTint189.active = false;
                     }
                  }
               }

               if (b.glint != 0) {
                  glintSet = true;
                  applyGlint(b.glint, b.glintScale, entity, tickDelta);
               } else if (glintSet) {
                  clearGlint();
                  glintSet = false;
               }

               GlState189.apply(b.state);
               if (c.aligned && FrameFlags.drawFirst) {
                  boolean item = b.stride == 7;
                  if (b.stride != pointerStride) {
                     int stride = b.stride * 4;
                     GL11.glVertexPointer(3, 5126, stride, 0L);
                     if (item) {
                        GL11.glColorPointer(4, 5121, stride, 12L);
                        GL11.glTexCoordPointer(2, 5126, stride, 16L);
                        GL11.glNormalPointer(5120, stride, 24L);
                     } else {
                        GL11.glTexCoordPointer(2, 5126, stride, 12L);
                        GL11.glNormalPointer(5120, stride, 20L);
                     }

                     pointerStride = b.stride;
                  }

                  if (item && !colors) {
                     GL11.glEnableClientState(32886);
                     colors = true;
                  } else if (!item && colors) {
                     GL11.glDisableClientState(32886);
                     bfl.G();
                     GlState189.apply(b.state);
                     colors = false;
                  }

                  GL11.glDrawArrays(7, (c.offset + b.first) / b.stride, b.vertices);
               } else {
                  long base = ((long)c.offset + b.first) * 4L;
                  int stride = b.stride * 4;
                  GL11.glVertexPointer(3, 5126, stride, base);
                  if (b.stride == 7) {
                     if (!colors) {
                        GL11.glEnableClientState(32886);
                        colors = true;
                     }

                     GL11.glColorPointer(4, 5121, stride, base + 12L);
                     GL11.glTexCoordPointer(2, 5126, stride, base + 16L);
                     GL11.glNormalPointer(5120, stride, base + 24L);
                  } else {
                     if (colors) {
                        GL11.glDisableClientState(32886);
                        bfl.G();
                        GlState189.apply(b.state);
                        colors = false;
                     }

                     GL11.glTexCoordPointer(2, 5126, stride, base + 12L);
                     GL11.glNormalPointer(5120, stride, base + 20L);
                  }

                  GL11.glDrawArrays(7, 0, b.vertices);
               }
            }

            frameBatches = frameBatches + c.batchCount;
         } finally {
            if (glintSet) {
               clearGlint();
            }

            if (overlay) {
               hurt.pulsar$overlayEnd();
            }

            if (colors) {
               GL11.glDisableClientState(32886);
               bfl.G();
            }

            GL11.glDisableClientState(32885);
            GL11.glDisableClientState(32888);
            GL11.glDisableClientState(32884);
            bqs.g(bqs.R, 0);
            bfl.F();
         }
      }

      GlState189.apply(c.endState);
      if (c.labelCount > 0) {
         ave mc = ave.A();
         biu dispatcher = mc.af();

         for (int i = 0; i < c.labelCount; i++) {
            EntityCache189.Label l = c.labels.get(i);
            double lx = x + l.dx;
            double ly = y + l.dy;
            double lz = z + l.dz;
            if (!(lx * lx + ly * ly + lz * lz > l.maxDistance * l.maxDistance)) {
               LabelBatch189.capture(mc.k, l.text, lx, ly, lz, l.height, dispatcher.e, dispatcher.f, l.k, PulsarPlayers.nametag(l.markPlayer));
            }
         }
      }
   }

   private static void invertAffine(float[] m, float[] out) {
      float a = m[0];
      float b = m[4];
      float c = m[8];
      float d = m[1];
      float e = m[5];
      float f = m[9];
      float g = m[2];
      float h = m[6];
      float i = m[10];
      float det = a * (e * i - f * h) - b * (d * i - f * g) + c * (d * h - e * g);
      float inv = 1.0F / det;
      float r00 = (e * i - f * h) * inv;
      float r01 = (c * h - b * i) * inv;
      float r02 = (b * f - c * e) * inv;
      float r10 = (f * g - d * i) * inv;
      float r11 = (a * i - c * g) * inv;
      float r12 = (c * d - a * f) * inv;
      float r20 = (d * h - e * g) * inv;
      float r21 = (b * g - a * h) * inv;
      float r22 = (a * e - b * d) * inv;
      out[0] = r00;
      out[4] = r01;
      out[8] = r02;
      out[1] = r10;
      out[5] = r11;
      out[9] = r12;
      out[2] = r20;
      out[6] = r21;
      out[10] = r22;
      out[3] = 0.0F;
      out[7] = 0.0F;
      out[11] = 0.0F;
      out[15] = 1.0F;
      float tx = m[12];
      float ty = m[13];
      float tz = m[14];
      out[12] = -(r00 * tx + r01 * ty + r02 * tz);
      out[13] = -(r10 * tx + r11 * ty + r12 * tz);
      out[14] = -(r20 * tx + r21 * ty + r22 * tz);
   }

   public static void clear() {
      CACHE.clear();
      arenaUsed = 0;
   }

   private static final class Batch {
      int first;
      int vertices;
      int stride;
      int segment;
      int glint;
      float glintScale = 8.0F;
      int mark;
      final int[] state = new int[24];

      boolean sameKey(EntityCache189.Batch o) {
         return this.stride == o.stride
            && this.segment == o.segment
            && this.glint == o.glint
            && this.glintScale == o.glintScale
            && Arrays.equals(this.state, o.state);
      }

      void copy(EntityCache189.Batch o) {
         this.first = o.first;
         this.vertices = o.vertices;
         this.stride = o.stride;
         this.segment = o.segment;
         this.glint = o.glint;
         this.glintScale = o.glintScale;
         System.arraycopy(o.state, 0, this.state, 0, this.state.length);
      }
   }

   private static final class Cached {
      final List<EntityCache189.Batch> batches = new ArrayList<>();
      final List<EntityCache189.Label> labels = new ArrayList<>();
      final int[] endState = new int[24];
      int batchCount;
      int labelCount;
      int offset = -1;
      int capacity;
      int size;
      long capturedAt;
      long retryAt;
      int failures;
      int lastFrame;
      boolean valid;
      boolean aligned;
   }

   private static final class Label {
      String text;
      UUID markPlayer;
      double dx;
      double dy;
      double dz;
      float height;
      int k;
      int maxDistance;
   }
}
