package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWorker extends Enchantment {
   public EnchantmentWaterWorker(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.f);
      this.c("waterWorker");
   }

   @Override
   public int a(int var1) {
      return 1;
   }

   @Override
   public int b(int var1) {
      return this.a(var1) + 40;
   }

   @Override
   public int b() {
      return 1;
   }
}
