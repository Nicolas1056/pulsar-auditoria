package net.minecraft.enchantment;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;

public class EnchantmentThorns extends Enchantment {
   public EnchantmentThorns(int var1, ResourceLocation var2, int var3) {
      super(var1, var2, var3, EnumEnchantmentType.e);
      this.c("thorns");
   }

   @Override
   public int a(int var1) {
      return 10 + 20 * (var1 - 1);
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
      return var1.b() instanceof ItemArmor ? true : super.a(var1);
   }

   @Override
   public void b(EntityLivingBase var1, Entity var2, int var3) {
      Random var4 = var1.bc();
      ItemStack var5 = EnchantmentHelper.a(Enchantment.j, var1);
      if (a(var3, var4)) {
         if (var2 != null) {
            var2.a(DamageSource.a((Entity)var1), (float)b(var3, var4));
            var2.a("damage.thorns", 0.5F, 1.0F);
         }

         if (var5 != null) {
            var5.a(3, var1);
         }
      } else if (var5 != null) {
         var5.a(1, var1);
      }
   }

   public static boolean a(int var0, Random var1) {
      return var0 <= 0 ? false : var1.nextFloat() < 0.15F * var0;
   }

   public static int b(int var0, Random var1) {
      return var0 > 10 ? var0 - 10 : 1 + var1.nextInt(4);
   }
}
