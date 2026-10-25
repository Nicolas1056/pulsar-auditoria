package net.minecraft.enchantment;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentUntouching extends Enchantment {
   protected EnchantmentUntouching(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.h);
      this.c("untouching");
   }

   @Override
   public int a(int var1) {
      return 15;
   }

   @Override
   public int b(int var1) {
      return super.a(var1) + 50;
   }

   @Override
   public int b() {
      return 1;
   }

   @Override
   public boolean a(Enchantment var1) {
      return super.a(var1) && var1.B != u.B;
   }

   @Override
   public boolean a(ItemStack var1) {
      return var1.b() == Items.be ? true : super.a(var1);
   }
}
