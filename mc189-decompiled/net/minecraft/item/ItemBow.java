package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBow extends Item {
   public static final String[] a = new String[]{"pulling_0", "pulling_1", "pulling_2"};

   public ItemBow() {
      this.h = 1;
      this.d(384);
      this.a(CreativeTabs.j);
   }

   @Override
   public void a(ItemStack var1, World var2, EntityPlayer var3, int var4) {
      boolean var5 = var3.bA.d || EnchantmentHelper.a(Enchantment.y.B, var1) > 0;
      if (var5 || var3.bi.b(Items.g)) {
         int var6 = this.d(var1) - var4;
         float var7 = var6 / 20.0F;
         var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;
         if (var7 < 0.1) {
            return;
         }

         if (var7 > 1.0F) {
            var7 = 1.0F;
         }

         EntityArrow var8 = new EntityArrow(var2, var3, var7 * 2.0F);
         if (var7 == 1.0F) {
            var8.a(true);
         }

         int var9 = EnchantmentHelper.a(Enchantment.v.B, var1);
         if (var9 > 0) {
            var8.b(var8.j() + var9 * 0.5 + 0.5);
         }

         int var10 = EnchantmentHelper.a(Enchantment.w.B, var1);
         if (var10 > 0) {
            var8.a(var10);
         }

         if (EnchantmentHelper.a(Enchantment.x.B, var1) > 0) {
            var8.e(100);
         }

         var1.a(1, var3);
         var2.a((Entity)var3, "random.bow", 1.0F, 1.0F / (g.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
         if (var5) {
            var8.a = 2;
         } else {
            var3.bi.a(Items.g);
         }

         var3.b(StatList.ad[Item.b(this)]);
         if (!var2.D) {
            var2.d(var8);
         }
      }
   }

   @Override
   public ItemStack b(ItemStack var1, World var2, EntityPlayer var3) {
      return var1;
   }

   @Override
   public int d(ItemStack var1) {
      return 72000;
   }

   @Override
   public EnumAction e(ItemStack var1) {
      return EnumAction.e;
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      if (var3.bA.d || var3.bi.b(Items.g)) {
         var3.a(var1, this.d(var1));
      }

      return var1;
   }

   @Override
   public int b() {
      return 1;
   }
}
