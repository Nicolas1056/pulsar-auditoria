package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.perf.FrameFlags;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GLContext;

public final class GpuOcclusion189 {
   private static final Map<bht, GpuOcclusion189.State> STATES = new IdentityHashMap<>();
   private static final List<Integer> QUERY_POOL = new ArrayList<>();
   private static Boolean supported;
   private static int target;
   private static int cubeVbo;
   private static int frame;
   private static int frameHidden;
   private static int frameQueried;
   static volatile int lastHidden;
   static volatile int lastQueried;
   private static double camX;
   private static double camY;
   private static double camZ;
   private static final int INTERVAL = 8;

   static boolean ownsVbo(int id) {
      return id == cubeVbo;
   }

   private GpuOcclusion189() {
   }

   public static boolean enabled() {
      if (!FrameFlags.gpuOcclusion) {
         return false;
      } else {
         if (supported == null) {
            try {
               ContextCapabilities caps = GLContext.getCapabilities();
               supported = caps.OpenGL15;
               target = caps.OpenGL33 ? '谯' : '褔';
            } catch (Throwable var1) {
               supported = false;
            }

            if (!supported) {
               ClientOptions.LOG.accept("perf: GPU occlusion needs GL 1.5 occlusion queries; off");
            }
         }

         return supported;
      }
   }

   public static void frame(double x, double y, double z) {
      camX = x;
      camY = y;
      camZ = z;
      frame++;
      lastHidden = frameHidden;
      lastQueried = frameQueried;
      frameHidden = 0;
      frameQueried = 0;
   }

   public static void revealAll() {
      for (GpuOcclusion189.State state : STATES.values()) {
         state.hidden = false;
         state.hiddenStreak = 0;
      }
   }

   public static boolean hidden(bht chunk) {
      if (!enabled()) {
         return false;
      } else {
         GpuOcclusion189.State s = STATES.get(chunk);
         if (s == null) {
            return false;
         } else {
            if (s.pending && GL15.glGetQueryObjecti(s.query, 34919) != 0) {
               s.pending = false;
               boolean any = GL15.glGetQueryObjecti(s.query, 34918) != 0;
               s.hiddenStreak = any ? 0 : s.hiddenStreak + 1;
               s.hidden = s.hiddenStreak >= 2;
            }

            if (s.hidden && !nearCamera(chunk)) {
               frameHidden++;
               return true;
            } else {
               return false;
            }
         }
      }
   }

   private static boolean nearCamera(bht chunk) {
      cj p = chunk.j();
      return Math.abs(camX - (p.n() + 8)) < 24.0 && Math.abs(camY - (p.o() + 8)) < 24.0 && Math.abs(camZ - (p.p() + 8)) < 24.0;
   }

   public static void issue(List<bht> visible, double viewX, double viewY, double viewZ) {
      if (enabled() && !visible.isEmpty()) {
         if (cubeVbo == 0) {
            cubeVbo = cube();
         }

         bfl.a(false, false, false, false);
         bfl.a(false);
         bfl.x();
         bfl.c();
         bfl.p();
         GL11.glEnableClientState(32884);
         bqs.g(bqs.R, cubeVbo);
         GL11.glVertexPointer(3, 5126, 12, 0L);

         for (bht chunk : visible) {
            GpuOcclusion189.State s = STATES.get(chunk);
            if (s == null) {
               s = new GpuOcclusion189.State();
               s.query = QUERY_POOL.isEmpty() ? GL15.glGenQueries() : QUERY_POOL.remove(QUERY_POOL.size() - 1);
               STATES.put(chunk, s);
            }

            s.lastSeen = frame;
            if (!s.pending && frame - s.lastIssued >= 8 && (s.hidden || !nearCamera(chunk))) {
               s.lastIssued = frame;
               cj p = chunk.j();
               bfl.E();
               bfl.b((float)(p.n() - viewX), (float)(p.o() - viewY), (float)(p.p() - viewZ));
               GL15.glBeginQuery(target, s.query);
               GL11.glDrawArrays(7, 0, 24);
               GL15.glEndQuery(target);
               bfl.F();
               s.pending = true;
               frameQueried++;
            }
         }

         bqs.g(bqs.R, 0);
         GL11.glDisableClientState(32884);
         bfl.o();
         bfl.d();
         bfl.w();
         bfl.a(true);
         bfl.a(true, true, true, true);
         if ((frame & 0xFF) == 0) {
            prune();
         }
      }
   }

   private static void prune() {
      Iterator<Entry<bht, GpuOcclusion189.State>> it = STATES.entrySet().iterator();

      while (it.hasNext()) {
         Entry<bht, GpuOcclusion189.State> e = it.next();
         if (frame - e.getValue().lastSeen > 600) {
            QUERY_POOL.add(e.getValue().query);
            it.remove();
         }
      }
   }

   public static void forget(bht chunk) {
      GpuOcclusion189.State s = STATES.remove(chunk);
      if (s != null) {
         QUERY_POOL.add(s.query);
      }
   }

   public static void clear() {
      for (GpuOcclusion189.State s : STATES.values()) {
         QUERY_POOL.add(s.query);
      }

      STATES.clear();
   }

   private static int cube() {
      float[] v = new float[]{
         0.0F,
         0.0F,
         0.0F,
         0.0F,
         16.0F,
         0.0F,
         16.0F,
         16.0F,
         0.0F,
         16.0F,
         0.0F,
         0.0F,
         0.0F,
         0.0F,
         16.0F,
         16.0F,
         0.0F,
         16.0F,
         16.0F,
         16.0F,
         16.0F,
         0.0F,
         16.0F,
         16.0F,
         0.0F,
         0.0F,
         0.0F,
         0.0F,
         0.0F,
         16.0F,
         0.0F,
         16.0F,
         16.0F,
         0.0F,
         16.0F,
         0.0F,
         16.0F,
         0.0F,
         0.0F,
         16.0F,
         16.0F,
         0.0F,
         16.0F,
         16.0F,
         16.0F,
         16.0F,
         0.0F,
         16.0F,
         0.0F,
         0.0F,
         0.0F,
         16.0F,
         0.0F,
         0.0F,
         16.0F,
         0.0F,
         16.0F,
         0.0F,
         0.0F,
         16.0F,
         0.0F,
         16.0F,
         0.0F,
         0.0F,
         16.0F,
         16.0F,
         16.0F,
         16.0F,
         16.0F,
         16.0F,
         16.0F,
         0.0F
      };
      ByteBuffer buf = ByteBuffer.allocateDirect(v.length * 4).order(ByteOrder.nativeOrder());

      for (float f : v) {
         buf.putFloat(f);
      }

      buf.flip();
      int vbo = bqs.e();
      bqs.g(bqs.R, vbo);
      GL15.glBufferData(bqs.R, buf, 35044);
      bqs.g(bqs.R, 0);
      return vbo;
   }

   private static final class State {
      int query;
      boolean pending;
      int hiddenStreak;
      boolean hidden;
      int lastSeen;
      int lastIssued = -1000;
   }
}
