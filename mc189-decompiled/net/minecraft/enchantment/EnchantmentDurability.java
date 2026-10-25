package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentDurability extends Enchantment {
   protected EnchantmentDurability(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.j);
      this.c("durability");
   }

   @Override
   public int a(int var1) {
      return 5 + (var1 - 1) * 8;
   }

   @Override
   public int b(int var1) {
      return super.a(var1) + 50;
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public boolean a(ItemStack var1) {
      return var1.e() ? true : super.a(var1);
   }

   public static boolean a(ItemStack var0, int var1, Random var2) {
      return var0.b() instanceof ItemArmor && var2.nextFloat() < 0.6F ? false : var2.nextInt(var1 + 1) > 0;
   }
}
