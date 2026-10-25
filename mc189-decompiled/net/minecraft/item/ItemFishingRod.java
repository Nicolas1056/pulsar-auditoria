package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemFishingRod extends Item {
   public ItemFishingRod() {
      this.d(64);
      this.c(1);
      this.a(CreativeTabs.i);
   }

   @Override
   public boolean w_() {
      return true;
   }

   @Override
   public boolean e() {
      return true;
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      if (var3.bG != null) {
         int var4 = var3.bG.l();
         var1.a(var4, var3);
         var3.bw();
      } else {
         var2.a((Entity)var3, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
         if (!var2.D) {
            var2.d(new EntityFishHook(var2, var3));
         }

         var3.bw();
         var3.b(StatList.ad[Item.b(this)]);
      }

      return var1;
   }

   @Override
   public boolean f_(ItemStack var1) {
      return super.f_(var1);
   }

   @Override
   public int b() {
      return 1;
   }
}
