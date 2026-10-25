package com.metrofico.pulsar.engine.v189.visuals;

import com.metrofico.pulsar.api.hud.modules.Visuals17;

public final class ThirdPerson17 {
   private static zx stick;

   private ThirdPerson17() {
   }

   public static zx shown(pr entity, zx stack) {
      if (Visuals17.active() && Visuals17.fishingRod() && stack != null && stack.b() instanceof zq && entity instanceof wn p && p.bG != null) {
         if (stick == null) {
            stick = new zx(zy.y);
         }

         return stick;
      } else {
         return stack;
      }
   }

   public static boolean applies(zx stack) {
      if (stack != null && Visuals17.active()) {
         zw item = stack.b();
         if (item instanceof yo) {
            afh block = afh.a(item);
            if (block.b() == 2) {
               return false;
            } else {
               return block instanceof aks ? Visuals17.carpets() : Visuals17.thirdPersonItems();
            }
         } else if (item instanceof aay) {
            return Visuals17.swords();
         } else if (item instanceof yt) {
            return Visuals17.bow();
         } else {
            return !(item instanceof zq) && stack != stick ? Visuals17.thirdPersonItems() : Visuals17.fishingRod();
         }
      } else {
         return false;
      }
   }

   public static void transform(pr entity, zx stack) {
      zw item = stack.b();
      if (item instanceof yo && ave.A().ag().a(stack)) {
         bfl.b(0.0F, 0.1875F, -0.3125F);
         bfl.b(20.0F, 1.0F, 0.0F, 0.0F);
         bfl.b(45.0F, 0.0F, 1.0F, 0.0F);
         bfl.a(-0.375F, -0.375F, 0.375F);
      } else {
         if (item instanceof yt) {
            bfl.b(0.0F, 0.125F, 0.3125F);
            bfl.b(-20.0F, 0.0F, 1.0F, 0.0F);
            bfl.a(0.625F, -0.625F, 0.625F);
            bfl.b(-100.0F, 1.0F, 0.0F, 0.0F);
            bfl.b(45.0F, 0.0F, 1.0F, 0.0F);
         } else if (item.w_()) {
            if (item.e()) {
               bfl.b(180.0F, 0.0F, 0.0F, 1.0F);
               bfl.b(0.0F, -0.125F, 0.0F);
            }

            if (entity instanceof wn p && p.bR() > 0 && stack.m() == aba.d) {
               bfl.b(0.05F, 0.0F, -0.1F);
               bfl.b(-50.0F, 0.0F, 1.0F, 0.0F);
               bfl.b(-10.0F, 1.0F, 0.0F, 0.0F);
               bfl.b(-60.0F, 0.0F, 0.0F, 1.0F);
            }

            bfl.b(0.0F, 0.1875F, 0.0F);
            bfl.a(0.625F, -0.625F, 0.625F);
            bfl.b(-100.0F, 1.0F, 0.0F, 0.0F);
            bfl.b(45.0F, 0.0F, 1.0F, 0.0F);
         } else {
            bfl.b(0.25F, 0.1875F, -0.1875F);
            bfl.a(0.375F, 0.375F, 0.375F);
            bfl.b(60.0F, 0.0F, 0.0F, 1.0F);
            bfl.b(-90.0F, 1.0F, 0.0F, 0.0F);
            bfl.b(20.0F, 0.0F, 0.0F, 1.0F);
         }

         Item17.flat();
      }
   }
}
