package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.keys.PulsarKeys;

public final class Zoom {
   private static Zoom.Hooks hooks = new Zoom.Hooks() {
      @Override
      public boolean smoothCamera() {
         return false;
      }

      @Override
      public void smoothCamera(boolean on) {
      }
   };
   private static boolean held;
   private static double current = 1.0;
   private static double sessionFactor;
   private static long lastNanos;
   private static boolean smoothCameraBefore;

   public static void hooks(Zoom.Hooks h) {
      hooks = h;
   }

   private Zoom() {
   }

   public static boolean enabled() {
      return ClientOptions.get().enabled("zoom");
   }

   public static boolean handleKey(String key, boolean pressed) {
      if (enabled() && PulsarKeys.is("zoom", key)) {
         if (pressed && !held) {
            held = true;
            lastNanos = System.nanoTime();
            sessionFactor = ClientOptions.get().dbl("zoom.factor", 3.0);
            if (ClientOptions.get().bool("zoom.cinematic", true)) {
               smoothCameraBefore = hooks.smoothCamera();
               hooks.smoothCamera(true);
            }
         } else if (!pressed && held) {
            release();
         }

         return true;
      } else {
         return false;
      }
   }

   public static void release() {
      if (held) {
         held = false;
         lastNanos = System.nanoTime();
         if (ClientOptions.get().bool("zoom.cinematic", true)) {
            hooks.smoothCamera(smoothCameraBefore);
         }
      }
   }

   public static boolean scroll(double dy) {
      if (held && ClientOptions.get().bool("zoom.scroll", true)) {
         sessionFactor = Math.max(1.5, Math.min(20.0, sessionFactor + (dy > 0.0 ? 0.5 : -0.5)));
         return true;
      } else {
         return false;
      }
   }

   public static boolean active() {
      return held || current > 1.001;
   }

   public static double currentFactor() {
      return Math.max(1.0, current);
   }

   public static double factor() {
      double target = held ? sessionFactor : 1.0;
      if (!ClientOptions.get().bool("zoom.smooth", true)) {
         current = target;
         return current;
      } else {
         long now = System.nanoTime();
         double dt = lastNanos == 0L ? 0.0 : Math.min(0.1, (now - lastNanos) / 1.0E9);
         lastNanos = now;
         double tau = Math.max(30, ClientOptions.get().integer("zoom.duration", 150)) / 1000.0;
         current = current + (target - current) * Math.min(1.0, dt / tau);
         if (Math.abs(current - target) < 0.005) {
            current = target;
         }

         return current;
      }
   }

   public static double sensitivity(double base) {
      if (!active()) {
         return base;
      } else {
         double pct = Math.max(10, Math.min(100, ClientOptions.get().integer("zoom.sensitivity", 80))) / 100.0;
         double target = Math.max(1.5, sessionFactor);
         double progress = Math.max(0.0, Math.min(1.0, (current - 1.0) / (target - 1.0)));
         return base * (1.0 - progress * (1.0 - pct));
      }
   }

   public interface Hooks {
      boolean smoothCamera();

      void smoothCamera(boolean var1);
   }
}
