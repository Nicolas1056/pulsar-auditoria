package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowKnockback extends Enchantment {
   public EnchantmentArrowKnockback(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.k);
      this.c("arrowKnockback");
   }

   @Override
   public int a(int var1) {
      return 12 + (var1 - 1) * 20;
   }

   @Override
   public int b(int var1) {
      return this.a(var1) + 25;
   }

   @Override
   public int b() {
      return 2;
   }
}
