package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentFishingSpeed extends Enchantment {
   protected EnchantmentFishingSpeed(int var1, ResourceLocation var2, int var3, EnumEnchantmentType var4) {
      super(var1, var2, var3, var4);
      this.c("fishingSpeed");
   }

   @Override
   public int a(int var1) {
      return 15 + (var1 - 1) * 9;
   }

   @Override
   public int b(int var1) {
      return super.a(var1) + 50;
   }

   @Override
   public int b() {
      return 3;
   }
}
