package net.minecraft.util;

import java.util.Random;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

public class WeightedRandomFishable extends WeightedRandom.Item {
   private final ItemStack b;
   private float c;
   private boolean d;

   public WeightedRandomFishable(ItemStack var1, int var2) {
      super(var2);
      this.b = var1;
   }

   public ItemStack a(Random var1) {
      ItemStack var2 = this.b.k();
      if (this.c > 0.0F) {
         int var3 = (int)(this.c * this.b.j());
         int var4 = var2.j() - var1.nextInt(var1.nextInt(var3) + 1);
         if (var4 > var3) {
            var4 = var3;
         }

         if (var4 < 1) {
            var4 = 1;
         }

         var2.b(var4);
      }

      if (this.d) {
         EnchantmentHelper.a(var1, var2, 30);
      }

      return var2;
   }

   public WeightedRandomFishable a(float var1) {
      this.c = var1;
      return this;
   }

   public WeightedRandomFishable a() {
      this.d = true;
      return this;
   }
}
