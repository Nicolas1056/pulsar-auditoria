package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.api.perf.FramePacer;

public final class ChunkUpdates189 {
   private static int uploadsThisFrame;
   private static double lastX;
   private static double lastY;
   private static double lastZ;
   private static boolean moving;

   private ChunkUpdates189() {
   }

   public static void frame() {
      uploadsThisFrame = 0;
      pk e = ave.A().ac();
      if (e == null) {
         moving = false;
      } else {
         double dx = e.s - lastX;
         double dy = e.t - lastY;
         double dz = e.u - lastZ;
         moving = dx * dx + dy * dy + dz * dz > 1.0E-6;
         lastX = e.s;
         lastY = e.t;
         lastZ = e.u;
      }
   }

   public static boolean allowUpload() {
      int pacer = FramePacer.chunkUploadCap();
      int cap = FrameFlags.chunkUpdates;
      if (cap <= 0 || !moving && FrameFlags.dynamicUpdates) {
         cap = Integer.MAX_VALUE;
      }

      cap = Math.min(cap, pacer);
      return uploadsThisFrame++ < cap;
   }
}
