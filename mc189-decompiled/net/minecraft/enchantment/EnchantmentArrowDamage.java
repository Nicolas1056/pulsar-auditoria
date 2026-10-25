package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentArrowDamage extends Enchantment {
   public EnchantmentArrowDamage(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.k);
      this.c("arrowDamage");
   }

   @Override
   public int a(int var1) {
      return 1 + (var1 - 1) * 10;
   }

   @Override
   public int b(int var1) {
      return this.a(var1) + 15;
   }

   @Override
   public int b() {
      return 5;
   }
}
