package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDigging extends Enchantment {
   protected EnchantmentDigging(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.h);
      this.c("digging");
   }

   @Override
   public int a(int var1) {
      return 1 + 10 * (var1 - 1);
   }

   @Override
   public int b(int var1) {
      return super.a(var1) + 50;
   }

   @Override
   public int b() {
      return 5;
   }

   @Override
   public boolean a(ItemStack var1) {
      return var1.b() == Items.be ? true : super.a(var1);
   }
}
