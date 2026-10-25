package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowInfinite extends Enchantment {
   public EnchantmentArrowInfinite(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.k);
      this.c("arrowInfinite");
   }

   @Override
   public int a(int var1) {
      return 20;
   }

   @Override
   public int b(int var1) {
      return 50;
   }

   @Override
   public int b() {
      return 1;
   }
}
