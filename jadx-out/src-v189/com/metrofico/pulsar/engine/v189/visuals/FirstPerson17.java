package com.metrofico.pulsar.engine.v189.visuals;

import com.metrofico.pulsar.api.hud.modules.Visuals17;

public final class FirstPerson17 {
   private FirstPerson17() {
   }

   public static boolean applies(zx stack) {
      if (stack != null && Visuals17.active()) {
         zw item = stack.b();
         aba action = stack.m();
         return action == aba.d
            ? Visuals17.swords()
            : (action == aba.e ? Visuals17.bow() : (item instanceof aai ? Visuals17.potions() : item instanceof zq && Visuals17.fishingRod()));
      } else {
         return false;
      }
   }

   public static void transform(zx stack) {
      if (stack.b().e()) {
         bfl.b(180.0F, 0.0F, 1.0F, 0.0F);
      }

      if (!ave.A().ag().a(stack)) {
         Item17.flat();
      }
   }
}
