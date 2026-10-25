package net.minecraft.enchantment;

import net.minecraft.util.ResourceLocation;

public class EnchantmentWaterWalker extends Enchantment {
   public EnchantmentWaterWalker(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.c);
      this.c("waterWalker");
   }

   @Override
   public int a(int var1) {
      return var1 * 10;
   }

   @Override
   public int b(int var1) {
      return this.a(var1) + 15;
   }

   @Override
   public int b() {
      return 3;
   }
}
