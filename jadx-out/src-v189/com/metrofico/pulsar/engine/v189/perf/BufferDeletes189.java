package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.gfx.IdBatch;
import com.metrofico.pulsar.api.perf.FrameFlags;
import java.nio.IntBuffer;
import java.util.function.Consumer;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;

public final class BufferDeletes189 {
   static final int PER_FRAME = 1024;
   private static final IdBatch BATCH = new IdBatch(65536);
   private static final Consumer<IntBuffer> DELETE = GL15::glDeleteBuffers;
   private static boolean active;
   private static Boolean gl15;

   private BufferDeletes189() {
   }

   public static void begin() {
      if (gl15 == null) {
         gl15 = GLContext.getCapabilities().OpenGL15;
      }

      active = FrameFlags.batchDeletes && gl15;
   }

   public static boolean defer(int id) {
      if (!active) {
         return false;
      } else {
         BATCH.add(id);
         return true;
      }
   }

   public static void end() {
      active = false;
   }

   public static void frame() {
      if (BATCH.size() > 0) {
         BATCH.drainTo(1024, DELETE);
      }
   }
}
