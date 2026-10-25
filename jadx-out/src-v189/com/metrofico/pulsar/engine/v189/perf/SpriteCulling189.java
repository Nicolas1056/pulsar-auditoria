package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.perf.FrameFlags;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class SpriteCulling189 {
   private static final int GRACE_FRAMES = 12;
   private static final ThreadLocal<Set<bmi>> RECORDING = ThreadLocal.withInitial(HashSet::new);
   private static volatile bmi[] animated = new bmi[0];
   private static volatile long[] grid = new long[64];
   private static volatile float[] rects = new float[0];
   private static volatile int frame;
   private static volatile int lastTicked;
   private static volatile int lastSkipped;
   private static int ticked;
   private static int skipped;

   public static void usedByWorker(bmi sprite) {
      RECORDING.get().add(sprite);
   }

   private SpriteCulling189() {
   }

   public static boolean enabled() {
      return FrameFlags.spriteCulling;
   }

   public static void nextFrame() {
      frame++;
      lastTicked = ticked;
      lastSkipped = skipped;
   }

   public static void atlasLoaded(List<bmi> animatedSprites) {
      bmi[] a = animatedSprites.toArray(new bmi[0]);
      float[] r = new float[a.length * 4];

      for (int i = 0; i < a.length; i++) {
         r[i * 4] = a[i].e();
         r[i * 4 + 1] = a[i].f();
         r[i * 4 + 2] = a[i].g();
         r[i * 4 + 3] = a[i].h();
         ((SpriteCulling189.SpriteExt)a[i]).pulsar$animated(true);
         ((SpriteCulling189.SpriteExt)a[i]).pulsar$lastActive(frame);
      }

      long[] g = new long[64];

      for (int i = 0; i < a.length; i++) {
         int u0 = Math.max(0, (int)(r[i * 4] * 64.0F));
         int u1 = Math.min(63, (int)(r[i * 4 + 1] * 64.0F));
         int v0 = Math.max(0, (int)(r[i * 4 + 2] * 64.0F));
         int v1 = Math.min(63, (int)(r[i * 4 + 3] * 64.0F));

         for (int cv = v0; cv <= v1; cv++) {
            for (int cu = u0; cu <= u1; cu++) {
               g[cv] |= 1L << cu;
            }
         }
      }

      grid = g;
      rects = r;
      animated = a;
   }

   public static void quad(float u, float v, boolean renderThread) {
      long[] g = grid;
      int cu = (int)(u * 64.0F);
      int cv = (int)(v * 64.0F);
      if (cu >= 0 && cu <= 63 && cv >= 0 && cv <= 63 && (g[cv] & 1L << cu) != 0L) {
         float[] r = rects;
         bmi[] a = animated;

         for (int i = 0; i < a.length; i++) {
            int o = i * 4;
            if (u >= r[o] && u <= r[o + 1] && v >= r[o + 2] && v <= r[o + 3]) {
               if (renderThread) {
                  ((SpriteCulling189.SpriteExt)a[i]).pulsar$lastActive(frame);
               } else {
                  RECORDING.get().add(a[i]);
               }

               return;
            }
         }
      }
   }

   public static void sectionCompiled(SpriteCulling189.SectionExt section) {
      Set<bmi> set = RECORDING.get();
      if (!set.isEmpty()) {
         bmi[] prev = section.pulsar$sprites();
         Set<bmi> merged = new HashSet<>(set);
         if (prev != null) {
            for (bmi s : prev) {
               merged.add(s);
            }
         }

         section.pulsar$sprites(merged.toArray(new bmi[0]));
         set.clear();
      }
   }

   public static void sectionVisible(SpriteCulling189.SectionExt section) {
      bmi[] sprites = section.pulsar$sprites();
      if (sprites != null) {
         int f = frame;

         for (bmi s : sprites) {
            ((SpriteCulling189.SpriteExt)s).pulsar$lastActive(f);
         }
      }
   }

   public static void sectionsVisible(bht[] sections, int count) {
      if ((frame & 3) == 0) {
         int f = frame;
         pk cam = ave.A().ac();
         boolean all = cam == null || FrameFlags.animDistanceSq <= 0.0 || Zoom.active();
         double cx = all ? 0.0 : cam.s - 8.0;
         double cy = all ? 0.0 : cam.t - 8.0;
         double cz = all ? 0.0 : cam.u - 8.0;
         double limit = FrameFlags.animDistanceSq;

         for (int i = 0; i < count; i++) {
            bmi[] sprites = ((SpriteCulling189.SectionExt)sections[i].g()).pulsar$sprites();
            if (sprites != null && !all) {
               cj o = sections[i].j();
               double dx = o.n() - cx;
               double dy = o.o() - cy;
               double dz = o.p() - cz;
               if (dx * dx + dy * dy + dz * dz > limit) {
                  continue;
               }
            }

            if (sprites != null) {
               for (bmi s : sprites) {
                  ((SpriteCulling189.SpriteExt)s).pulsar$lastActive(f);
               }
            }
         }
      }
   }

   public static boolean shouldTick(bmi sprite) {
      if (!animationAllowed(sprite.i())) {
         skipped++;
         return false;
      } else {
         boolean active = frame - ((SpriteCulling189.SpriteExt)sprite).pulsar$lastActive() <= 12;
         if (active) {
            ticked++;
         } else {
            skipped++;
         }

         return active;
      }
   }

   public static boolean animationAllowed(String name) {
      if (name == null) {
         return true;
      } else if (name.contains("water")) {
         return FrameFlags.animWater;
      } else if (name.contains("lava")) {
         return FrameFlags.animLava;
      } else if (name.contains("fire")) {
         return FrameFlags.animFire;
      } else {
         return name.contains("portal") ? FrameFlags.animPortal : true;
      }
   }

   public static void tickBegin() {
      ticked = 0;
      skipped = 0;
   }

   public static int frame() {
      return frame;
   }

   public static boolean onRenderThread() {
      return EntityBatch189.onRenderThread();
   }

   public static int lastTicked() {
      return lastTicked;
   }

   public static int lastSkipped() {
      return lastSkipped;
   }

   public interface SectionExt {
      bmi[] pulsar$sprites();

      void pulsar$sprites(bmi[] var1);
   }

   public interface SpriteExt {
      int pulsar$lastActive();

      void pulsar$lastActive(int var1);

      boolean pulsar$animated();

      void pulsar$animated(boolean var1);
   }
}
