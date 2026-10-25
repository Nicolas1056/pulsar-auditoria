package net.minecraft.enchantment;

import net.minecraft.util.WeightedRandom;

public class EnchantmentData extends WeightedRandom.Item {
   public final Enchantment b;
   public final int c;

   public EnchantmentData(Enchantment var1, int var2) {
      super(var1.d());
      this.b = var1;
      this.c = var2;
   }
}
