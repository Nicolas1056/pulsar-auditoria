package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import org.lwjgl.glfw.GLFW;

public final class NoErrorContext {
   private static boolean applied;

   private NoErrorContext() {
   }

   public static boolean wanted() {
      ClientOptions o = ClientOptions.get();
      return o.enabled("perf") && o.bool("perf.noErrorContext", true);
   }

   public static void applyHint() {
      applied = false;
      if (wanted()) {
         GLFW.glfwWindowHint(139274, 1);
         applied = true;
      }
   }

   public static long create(int width, int height, CharSequence title, long monitor, long share) {
      long handle = GLFW.glfwCreateWindow(width, height, title, monitor, share);
      if (handle == 0L && applied) {
         applied = false;
         GLFW.glfwWindowHint(139274, 0);
         BootstrapLog.info("perf: no-error GL context not supported by this driver, retrying without it");
         handle = GLFW.glfwCreateWindow(width, height, title, monitor, share);
      } else if (applied) {
         BootstrapLog.info("perf: OpenGL context created with GL_KHR_no_error");
      }

      return handle;
   }
}
