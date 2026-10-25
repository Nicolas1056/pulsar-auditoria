package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.engine.v26x.mixin.BufferBuilderAccessor;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.util.ARGB;
import net.minecraft.world.phys.Vec3;
import org.lwjgl.system.MemoryUtil;

public final class PoseCache26 {
   private static final int VERTEX = 36;
   private static final boolean LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
   private static final Int2ObjectMap<PoseCache26.Entry> ENTRIES = new Int2ObjectOpenHashMap();
   private static volatile boolean enabled;
   private static volatile int frame;
   private static volatile double camX;
   private static volatile double camY;
   private static volatile double camZ;
   private static volatile double pixelsPerUnit;
   private static volatile boolean zoom;
   private static volatile int localId = Integer.MIN_VALUE;
   private static PoseCache26.Slot capturingSlot;
   private static PoseCache26.Entry capturingEntry;
   private static int captureStartVertices;
   private static AvatarRenderState capturingState;
   private static volatile int frameReplays;
   private static volatile int frameCaptures;

   private PoseCache26() {
   }

   public static void beginFrame() {
      ClientOptions o = ClientOptions.get();
      enabled = o.enabled("perf") && o.bool("perf.poseCache", true);
      frame++;
      Minecraft mc = Minecraft.getInstance();
      Vec3 cam = mc.gameRenderer.mainCamera().position();
      camX = cam.x;
      camY = cam.y;
      camZ = cam.z;
      double fov = Math.toRadians(Math.max(10, Math.min((Integer)mc.options.fov().get(), 110)));
      pixelsPerUnit = mc.getWindow().getHeight() / (2.0 * Math.tan(fov / 2.0));
      zoom = Zoom.active();
      localId = mc.player != null ? mc.player.getId() : Integer.MIN_VALUE;
      if ((frame & 1023) == 0) {
         evict();
      }
   }

   public static boolean beginModel(
      Object state, Object model, Object renderType, Object sprite, Object decal, Object consumer, int light, int overlay, int color
   ) {
      capturingSlot = null;
      if (enabled
         && state instanceof AvatarRenderState a
         && a.id != localId
         && sprite == null
         && decal == null
         && a.outlineColor == 0
         && consumer instanceof BufferBuilderAccessor bb
         && bb.pulsar$entityFormat()) {
         PoseCache26.Entry e = (PoseCache26.Entry)ENTRIES.get(a.id);
         if (e == null) {
            e = new PoseCache26.Entry();
            ENTRIES.put(a.id, e);
         }

         int f = frame;
         if (e.decidedFrame != f) {
            e.decidedFrame = f;
            e.lastFrame = f;

            for (int i = 0; i < e.slotCount; i++) {
               e.slots[i].usedFrame = -1;
            }

            int sig = signature(a);
            long now = System.nanoTime();
            boolean animating = sig != e.signature;
            e.capturing = e.forceCapture || e.capturedAt == 0L || now - e.capturedAt >= interval(a, animating);
            e.forceCapture = false;
            if (e.capturing) {
               e.capturedAt = firstCapture(e, a, now);
               e.signature = sig;
            }
         }

         PoseCache26.Slot s = slot(e, model, renderType, f);
         if (!e.capturing && s.valid) {
            replay(s, bb, a, light, overlay, color);
            frameReplays++;
            return true;
         } else {
            if (!e.capturing) {
               e.forceCapture = true;
            }

            capturingSlot = s;
            capturingEntry = e;
            capturingState = a;
            captureStartVertices = bb.pulsar$vertices();
            s.light = light;
            s.overlay = overlay;
            s.color = color;
            return false;
         }
      } else {
         return false;
      }
   }

   public static void endModel(Object consumer) {
      PoseCache26.Slot s = capturingSlot;
      if (s != null) {
         capturingSlot = null;
         BufferBuilderAccessor bb = (BufferBuilderAccessor)consumer;
         int n = bb.pulsar$vertices() - captureStartVertices;
         if (n < 0) {
            s.valid = false;
         } else {
            int bytes = n * 36;
            if (s.capacity < bytes) {
               int cap = Math.max(bytes + (bytes >> 2), 1024);
               s.address = s.address == 0L ? MemoryUtil.nmemAlloc(cap) : MemoryUtil.nmemRealloc(s.address, cap);
               s.capacity = cap;
            }

            if (n > 0) {
               long first = bb.pulsar$vertexPointer() - (n - 1) * 36L;
               MemoryUtil.memCopy(first, s.address, bytes);
            }

            s.vertices = n;
            AvatarRenderState a = capturingState;
            s.capX = a.x - camX;
            s.capY = a.y - camY;
            s.capZ = a.z - camZ;
            s.valid = true;
            frameCaptures++;
         }
      }
   }

   private static void replay(PoseCache26.Slot s, BufferBuilderAccessor bb, AvatarRenderState a, int light, int overlay, int color) {
      int n = s.vertices;
      if (n != 0) {
         for (int i = 0; i < n; i++) {
            bb.pulsar$beginVertex();
            bb.pulsar$setElementsToFill(0);
         }

         long first = bb.pulsar$vertexPointer() - (n - 1) * 36L;
         MemoryUtil.memCopy(s.address, first, n * 36L);
         float dx = (float)(a.x - camX - s.capX);
         float dy = (float)(a.y - camY - s.capY);
         float dz = (float)(a.z - camZ - s.capZ);
         boolean move = dx != 0.0F || dy != 0.0F || dz != 0.0F;
         boolean relight = light != s.light;
         boolean reoverlay = overlay != s.overlay;
         boolean recolor = color != s.color;
         int abgr = 0;
         if (recolor) {
            int c = ARGB.toABGR(color);
            abgr = LITTLE_ENDIAN ? c : Integer.reverseBytes(c);
         }

         if (move || relight || reoverlay || recolor) {
            long p = first;

            for (long end = first + n * 36L; p < end; p += 36L) {
               if (move) {
                  MemoryUtil.memPutFloat(p, MemoryUtil.memGetFloat(p) + dx);
                  MemoryUtil.memPutFloat(p + 4L, MemoryUtil.memGetFloat(p + 4L) + dy);
                  MemoryUtil.memPutFloat(p + 8L, MemoryUtil.memGetFloat(p + 8L) + dz);
               }

               if (recolor) {
                  MemoryUtil.memPutInt(p + 12L, abgr);
               }

               if (reoverlay) {
                  putPackedUv(p + 24L, overlay);
               }

               if (relight) {
                  putPackedUv(p + 28L, light);
               }
            }
         }
      }
   }

   private static void putPackedUv(long ptr, int packed) {
      if (LITTLE_ENDIAN) {
         MemoryUtil.memPutInt(ptr, packed);
      } else {
         MemoryUtil.memPutShort(ptr, (short)(packed & 65535));
         MemoryUtil.memPutShort(ptr + 2L, (short)(packed >> 16 & 65535));
      }
   }

   private static PoseCache26.Slot slot(PoseCache26.Entry e, Object model, Object renderType, int f) {
      int occurrence = 0;

      for (int i = 0; i < e.slotCount; i++) {
         PoseCache26.Slot s = e.slots[i];
         if (s.model == model && s.renderType == renderType) {
            if (s.usedFrame != f) {
               s.usedFrame = f;
               return s;
            }

            occurrence++;
         }
      }

      if (e.slotCount == e.slots.length) {
         e.slots = Arrays.copyOf(e.slots, e.slots.length * 2);
      }

      PoseCache26.Slot s = new PoseCache26.Slot();
      s.model = model;
      s.renderType = renderType;
      s.occurrence = occurrence;
      s.usedFrame = f;
      e.slots[e.slotCount++] = s;
      return s;
   }

   private static int signature(AvatarRenderState a) {
      int h = Float.floatToIntBits(a.bodyRot);
      h = h * 31 + Float.floatToIntBits(a.yRot);
      h = h * 31 + Float.floatToIntBits(a.xRot);
      h = h * 31 + Float.floatToIntBits(a.walkAnimationPos);
      h = h * 31 + Float.floatToIntBits(a.walkAnimationSpeed);
      h = h * 31 + Float.floatToIntBits(a.attackTime);
      h = h * 31 + Float.floatToIntBits(a.swimAmount);
      h = h * 31 + Float.floatToIntBits(a.ticksUsingItem);
      h = h * 31 + Float.floatToIntBits(a.elytraRotX);
      h = h * 31 + (a.isCrouching ? 1 : 0);
      return h * 31 + (a.pose == null ? 0 : a.pose.ordinal());
   }

   private static long interval(AvatarRenderState a, boolean animating) {
      double dist = Math.sqrt(a.distanceToCameraSq);
      double px = !zoom && !(dist < 0.5) ? a.boundingBoxHeight / dist * pixelsPerUnit : 1.0E9;
      if (animating) {
         return px >= 96.0 ? 8333333L : 16666666L;
      } else {
         return px >= 300.0 ? 8333333L : (px >= 96.0 ? 16666666L : (px >= 40.0 ? 33333333L : 50000000L));
      }
   }

   private static long firstCapture(PoseCache26.Entry e, AvatarRenderState a, long now) {
      return e.capturedAt == 0L ? now - (a.id * -1640531527 >>> 12) % 8000000L : now;
   }

   private static void evict() {
      int f = frame;
      Iterator<it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry<PoseCache26.Entry>> it = ENTRIES.int2ObjectEntrySet().iterator();

      while (it.hasNext()) {
         PoseCache26.Entry e = (PoseCache26.Entry)it.next().getValue();
         if (f - e.lastFrame > 2048) {
            for (int i = 0; i < e.slotCount; i++) {
               if (e.slots[i].address != 0L) {
                  MemoryUtil.nmemFree(e.slots[i].address);
               }
            }

            it.remove();
         }
      }
   }

   public static String line() {
      int r = frameReplays;
      int c = frameCaptures;
      frameCaptures = 0;
      frameReplays = 0;
      return r + c == 0 ? "" : " · pose " + r + " repro/" + c + " capt";
   }

   private static final class Entry {
      PoseCache26.Slot[] slots = new PoseCache26.Slot[8];
      int slotCount;
      int decidedFrame = -1;
      boolean capturing;
      boolean forceCapture;
      long capturedAt;
      int signature;
      int lastFrame;
   }

   private static final class Slot {
      Object model;
      Object renderType;
      int occurrence;
      long address;
      int capacity;
      int vertices;
      int light;
      int overlay;
      int color;
      double capX;
      double capY;
      double capZ;
      boolean valid;
      int usedFrame;
   }
}
