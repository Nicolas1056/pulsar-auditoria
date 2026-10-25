package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.keys.PulsarKeys;
import java.util.function.Consumer;
import java.util.function.Supplier;

public final class Freecam {
   private static Supplier<String> cameraType = () -> "first";
   private static Consumer<String> setCameraType = type -> {};
   private static boolean active;
   private static boolean emoteOrbit;
   private static float yaw;
   private static float pitch;
   private static String typeBefore = "first";

   private Freecam() {
   }

   public static void hooks(Supplier<String> get, Consumer<String> set) {
      cameraType = get;
      setCameraType = set;
   }

   public static boolean enabled() {
      return ClientOptions.get().enabled("freecam");
   }

   public static boolean handleKey(String key, boolean pressed, float playerYaw, float playerPitch) {
      if (enabled() && PulsarKeys.is("freecam", key)) {
         if (pressed && !active) {
            active = true;
            yaw = playerYaw;
            pitch = playerPitch;
            typeBefore = cameraType.get();
            setCameraType.accept("back");
         } else if (!pressed && active) {
            release();
         }

         return true;
      } else {
         return false;
      }
   }

   public static void release() {
      if (active) {
         active = false;
         setCameraType.accept(typeBefore);
      }
   }

   public static boolean active() {
      return active || emoteOrbit;
   }

   public static void beginEmoteOrbit(float playerYaw, float playerPitch) {
      if (!active && !emoteOrbit) {
         emoteOrbit = true;
         yaw = playerYaw;
         pitch = playerPitch;
      }
   }

   public static void endEmoteOrbit() {
      emoteOrbit = false;
   }

   public static void turn(float dYaw, float dPitch) {
      yaw += dYaw;
      pitch = Math.max(-90.0F, Math.min(90.0F, pitch + dPitch));
   }

   public static float yaw() {
      return yaw;
   }

   public static float pitch() {
      return pitch;
   }
}
