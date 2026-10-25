package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;

public final class Visuals17 {
   public static final int DEFAULT_ARMOR_COLOR = 1308557312;
   private static int seen = Integer.MIN_VALUE;
   private static boolean active;
   private static boolean armorHit;
   private static boolean blockHit;
   private static boolean sneaking;
   private static boolean bow;
   private static boolean potionDrinking;
   private static boolean potions;
   private static boolean swords;
   private static boolean fishingRod;
   private static boolean healthFlashing;
   private static boolean glint;
   private static boolean carpets;
   private static boolean thirdPersonItems;
   private static boolean useWhileDigging;
   private static float sneakDrop;
   private static float prevSneakDrop;
   private static int armorColor = 1308557312;
   public static final int GLINT_COLOR_17 = -10407781;
   static final float GLINT_SCALE_189 = 8.0F;
   static final float SNEAK_DROP = 0.08F;

   private Visuals17() {
   }

   private static void refresh() {
      int rev = ClientOptions.revision;
      if (rev != seen) {
         ClientOptions o = ClientOptions.get();
         boolean on = o.enabled("visuals17");
         active = on;
         armorHit = on && o.bool("visuals17.armorHit", true);
         blockHit = on && o.bool("visuals17.blockHit", true);
         sneaking = on && o.bool("visuals17.sneaking", true);
         bow = on && o.bool("visuals17.bow", true);
         potionDrinking = on && o.bool("visuals17.potionDrinking", true);
         potions = on && o.bool("visuals17.potions", true);
         swords = on && o.bool("visuals17.swords", true);
         fishingRod = on && o.bool("visuals17.fishingRod", true);
         healthFlashing = on && o.bool("visuals17.healthFlashing", true);
         glint = on && o.bool("visuals17.glint", true);
         carpets = on && o.bool("visuals17.carpets", true);
         thirdPersonItems = on && o.bool("visuals17.thirdPersonItems", true);
         useWhileDigging = on && o.bool("visuals17.useWhileDigging", false);
         armorColor = 1291845632 | o.color("visuals17.armorColor", 1308557312) & 16777215;
         seen = rev;
      }
   }

   public static boolean active() {
      refresh();
      return active;
   }

   public static boolean armorHit() {
      refresh();
      return armorHit;
   }

   public static boolean blockHit() {
      refresh();
      return blockHit;
   }

   public static boolean sneaking() {
      refresh();
      return sneaking;
   }

   public static boolean bow() {
      refresh();
      return bow;
   }

   public static boolean potionDrinking() {
      refresh();
      return potionDrinking;
   }

   public static boolean potions() {
      refresh();
      return potions;
   }

   public static boolean useWhileDigging() {
      refresh();
      return useWhileDigging;
   }

   public static boolean swords() {
      refresh();
      return swords;
   }

   public static boolean fishingRod() {
      refresh();
      return fishingRod;
   }

   public static boolean healthFlashing() {
      refresh();
      return healthFlashing;
   }

   public static boolean heartsFlash17(int timeUntilRegen) {
      return timeUntilRegen >= 10 && timeUntilRegen / 3 % 2 == 1;
   }

   public static boolean carpets() {
      refresh();
      return carpets;
   }

   public static boolean thirdPersonItems() {
      refresh();
      return thirdPersonItems;
   }

   public static boolean glint() {
      refresh();
      return glint;
   }

   public static float glintScale(float spriteSpanU) {
      return spriteSpanU > 0.0F ? 0.125F / spriteSpanU : 8.0F;
   }

   public static float glintShift(float vanillaShift, float scale) {
      return vanillaShift * 8.0F / scale;
   }

   public static void tickSneak(boolean isSneaking) {
      if (!sneaking()) {
         prevSneakDrop = 0.0F;
         sneakDrop = 0.0F;
      } else if (isSneaking) {
         prevSneakDrop = 0.08F;
         sneakDrop = 0.08F;
      } else {
         prevSneakDrop = sneakDrop;
         sneakDrop = sneakDrop * 0.4F < 5.0E-4F ? 0.0F : sneakDrop * 0.4F;
      }
   }

   public static float cameraEyeHeight(float vanilla, boolean isSneaking, float tickDelta) {
      if (!sneaking()) {
         return vanilla;
      } else {
         float standing = isSneaking ? vanilla + 0.08F : vanilla;
         return standing - (prevSneakDrop + (sneakDrop - prevSneakDrop) * tickDelta);
      }
   }

   public static int armorColor() {
      refresh();
      return armorColor;
   }

   public static float component(int argb, int i) {
      int shift = i == 3 ? 24 : 16 - 8 * i;
      return (argb >>> shift & 0xFF) / 255.0F;
   }

   public static boolean localSwing(boolean usingItem, boolean attackHeld, boolean onBlock) {
      return usingItem && attackHeld && onBlock && blockHit();
   }
}
