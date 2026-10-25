package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentOxygen extends Enchantment {
   public EnchantmentOxygen(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.f);
      this.c("oxygen");
   }

   @Override
   public int a(int var1) {
      return 10 * var1;
   }

   @Override
   public int b(int var1) {
      return this.a(var1) + 30;
   }

   @Override
   public int b() {
      return 3;
   }
}
