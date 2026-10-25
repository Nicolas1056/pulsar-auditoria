package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;

public final class ToggleSneakSprint {
   private static volatile boolean sneak = true;
   private static volatile boolean sprint = true;
   private static volatile boolean doubleTap = true;
   private static volatile boolean sneakInWater = true;
   private static volatile boolean flyBoost = true;
   private static volatile float flyBoostAmount = 6.5F;
   private static volatile int rampTicks = 30;
   private static int boostTicks;

   private ToggleSneakSprint() {
   }

   public static void refresh() {
      ClientOptions o = ClientOptions.get();
      boolean on = o.enabled("controls");
      sneak = on && o.bool("controls.toggleSneak", true);
      sprint = on && o.bool("controls.toggleSprint", true);
      doubleTap = !on || o.bool("controls.doubleTap", true);
      sneakInWater = o.bool("controls.sneakInWater", true);
      flyBoost = on && o.bool("controls.flyBoost", true);
      flyBoostAmount = (float)o.dbl("controls.flyBoostAmount", 6.5);
      rampTicks = (int)Math.round(o.dbl("controls.flyBoostRamp", 1.5) * 20.0);
   }

   public static boolean sneak() {
      return sneak;
   }

   public static boolean sprint() {
      return sprint;
   }

   public static boolean doubleTap() {
      return doubleTap;
   }

   public static boolean sneakInWater() {
      return sneakInWater;
   }

   public static void tick(boolean flying, boolean sprinting) {
      boostTicks = flyBoost && flying && sprinting ? Math.min(boostTicks + 1, Math.max(rampTicks, 1)) : 0;
   }

   public static float flySpeedFactor(boolean flying, boolean sprinting) {
      if (flyBoost && flying && sprinting) {
         float ramp = rampTicks <= 0 ? 1.0F : Math.min(1.0F, (float)boostTicks / rampTicks);
         return 1.0F + (flyBoostAmount - 1.0F) * ramp;
      } else {
         return 1.0F;
      }
   }

   public static float flyBoostAmount() {
      return flyBoostAmount;
   }
}
