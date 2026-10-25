package com.metrofico.pulsar.engine.v189.controls;

import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;

public final class ToggleKeys189 {
   private static boolean sneakLatched;
   private static boolean sprintLatched;

   private ToggleKeys189() {
   }

   public static void pressed(int code) {
      ave mc = ave.A();
      avh o = mc.t;
      if (code != 0 && o != null && mc.m == null) {
         if (ToggleSneakSprint.sneak() && code == o.ad.i() && !holdOnly(mc.h)) {
            sneakLatched = !sneakLatched;
         }

         if (ToggleSneakSprint.sprint() && code == o.ae.i()) {
            sprintLatched = !sprintLatched;
         }
      }
   }

   public static int state(avb key) {
      avh o = ave.A().t;
      if (o == null) {
         return -1;
      } else if (key == o.ad) {
         if (ToggleSneakSprint.sneak() && !holdOnly(ave.A().h)) {
            return sneakLatched ? 1 : 0;
         } else {
            sneakLatched = false;
            return -1;
         }
      } else if (key == o.ae) {
         if (!ToggleSneakSprint.sprint()) {
            sprintLatched = false;
            return -1;
         } else {
            return sprintLatched ? 1 : 0;
         }
      } else {
         return -1;
      }
   }

   public static boolean sprintActive() {
      return ToggleSneakSprint.sprint() && sprintLatched;
   }

   public static boolean sneakActive() {
      return ToggleSneakSprint.sneak() && sneakLatched && !holdOnly(ave.A().h);
   }

   private static boolean holdOnly(bew p) {
      return p != null && (p.bA.b || ToggleSneakSprint.sneakInWater() && p.V());
   }
}
