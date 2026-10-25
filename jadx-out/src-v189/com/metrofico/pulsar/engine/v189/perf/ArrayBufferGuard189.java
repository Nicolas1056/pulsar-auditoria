package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import org.lwjgl.opengl.GLContext;

public final class ArrayBufferGuard189 {
   private static final MethodHandle REFERENCES;
   private static final MethodHandle ARRAY_BUFFER;
   private static boolean reported;

   private ArrayBufferGuard189() {
   }

   public static void beforeClientArrays() {
      if (ARRAY_BUFFER != null) {
         int bound;
         try {
            Object references = (Object)REFERENCES.invokeExact((Object)GLContext.getCapabilities());
            bound = (int)ARRAY_BUFFER.invokeExact((Object)references);
         } catch (Throwable var4) {
            return;
         }

         if (bound != 0) {
            if (!reported) {
               reported = true;
               StringBuilder where = new StringBuilder();
               StackTraceElement[] stack = new Throwable().getStackTrace();

               for (int i = 1; i < Math.min(stack.length, 14); i++) {
                  where.append("\n    at ").append(stack[i]);
               }

               ClientOptions.LOG
                  .accept(
                     "perf: ARRAY_BUFFER "
                        + bound
                        + " ("
                        + owner(bound)
                        + ") still bound before a vanilla client-array draw; unbinding (reported once)"
                        + where
                  );
            }

            bqs.g(bqs.R, 0);
         }
      }
   }

   private static String owner(int id) {
      if (EntityCache189.ownsVbo(id)) {
         return "entity cache arena";
      } else if (EntityBatch189.ownsVbo(id)) {
         return "entity batch stream";
      } else if (LabelBatch189.ownsVbo(id)) {
         return "label batch stream";
      } else if (TerrainRegions189.ownsVbo(id)) {
         return "terrain regions";
      } else if (GpuOcclusion189.ownsVbo(id)) {
         return "occlusion cubes";
      } else {
         return Clouds189.ownsVbo(id) ? "clouds" : "not Pulsar's: vanilla VertexBuffer (terrain section or sky) or unknown";
      }
   }

   static {
      MethodHandle refs = null;
      MethodHandle array = null;

      try {
         ClassLoader lwjgl = GLContext.class.getClassLoader();
         Class<?> caps = Class.forName("org.lwjgl.opengl.ContextCapabilities", false, lwjgl);
         Method getReferences = Class.forName("org.lwjgl.opengl.StateTracker", false, lwjgl).getDeclaredMethod("getReferences", caps);
         getReferences.setAccessible(true);
         Field field = Class.forName("org.lwjgl.opengl.BaseReferences", false, lwjgl).getDeclaredField("arrayBuffer");
         field.setAccessible(true);
         Lookup lookup = MethodHandles.lookup();
         refs = lookup.unreflect(getReferences).asType(MethodType.methodType(Object.class, Object.class));
         array = lookup.unreflectGetter(field).asType(MethodType.methodType(int.class, Object.class));
      } catch (RuntimeException | ReflectiveOperationException var7) {
         ClientOptions.LOG.accept("perf: array buffer guard unavailable (" + var7 + ")");
      }

      REFERENCES = refs;
      ARRAY_BUFFER = array;
   }
}
