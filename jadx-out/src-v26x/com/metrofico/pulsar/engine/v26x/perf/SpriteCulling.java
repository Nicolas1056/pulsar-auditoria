package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

public final class SpriteCulling {
   private static final int MARK_EVERY = 4;
   private static final int GRACE_FRAMES = 12;
   private static volatile Thread renderThread;
   private static final ThreadLocal<Set<TextureAtlasSprite>> RECORDING = new ThreadLocal<>();
   private static volatile int frame;
   private static int ticked;
   private static int skipped;
   private static volatile int lastTicked;
   private static volatile int lastSkipped;

   private SpriteCulling() {
   }

   public static boolean enabled() {
      ClientOptions o = ClientOptions.get();
      return o.enabled("perf") && o.bool("perf.spriteCulling", true);
   }

   public static void nextFrame() {
      frame++;
      renderThread = Thread.currentThread();
   }

   public static void touch(TextureAtlasSprite sprite) {
      if (sprite != null) {
         if (Thread.currentThread() == renderThread) {
            if (sprite instanceof SpriteCulling.SpriteExt e) {
               int f = frame;
               if (e.pulsar$lastActive() != f) {
                  e.pulsar$setLastActive(f);
               }
            }
         } else {
            Set<TextureAtlasSprite> rec = RECORDING.get();
            if (rec != null) {
               if (sprite.isAnimated()) {
                  rec.add(sprite);
               }
            } else {
               if (sprite instanceof SpriteCulling.SpriteExt ex) {
                  ex.pulsar$setLastActive(frame);
               }
            }
         }
      }
   }

   public static void beginRecording() {
      RECORDING.set(new HashSet<>());
   }

   public static List<TextureAtlasSprite> endRecording() {
      Set<TextureAtlasSprite> rec = RECORDING.get();
      RECORDING.remove();
      return rec != null && !rec.isEmpty() ? List.copyOf(rec) : List.of();
   }

   public static void markVisible(Iterable<RenderSection> sections) {
      int f = frame;
      if (f % 4 == 0) {
         for (RenderSection section : sections) {
            if (section.getSectionMesh() instanceof FaceGroups.Holder h) {
               List<TextureAtlasSprite> sprites = h.pulsar$sprites();
               if (sprites != null) {
                  for (TextureAtlasSprite s : sprites) {
                     if (s instanceof SpriteCulling.SpriteExt e) {
                        e.pulsar$setLastActive(f);
                     }
                  }
               }
            }
         }
      }
   }

   public static boolean shouldTick(TextureAtlasSprite sprite) {
      if (sprite != null && enabled()) {
         boolean tick = !(sprite instanceof SpriteCulling.SpriteExt e && frame - e.pulsar$lastActive() > 12);
         if (tick) {
            ticked++;
         } else {
            skipped++;
         }

         return tick;
      } else {
         return true;
      }
   }

   public static void endTick() {
      lastTicked = ticked;
      lastSkipped = skipped;
      ticked = 0;
      skipped = 0;
   }

   public static int lastTicked() {
      return lastTicked;
   }

   public static int lastSkipped() {
      return lastSkipped;
   }

   public interface SpriteExt {
      int pulsar$lastActive();

      void pulsar$setLastActive(int var1);
   }

   public interface StateExt {
      TextureAtlasSprite pulsar$sprite();

      void pulsar$setSprite(TextureAtlasSprite var1);
   }
}
