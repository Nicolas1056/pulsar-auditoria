package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemSnowball extends Item {
   public ItemSnowball() {
      this.h = 16;
      this.a(CreativeTabs.f);
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      if (!var3.bA.d) {
         var1.b--;
      }

      var2.a((Entity)var3, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
      if (!var2.D) {
         var2.d(new EntitySnowball(var2, var3));
      }

      var3.b(StatList.ad[Item.b(this)]);
      return var1;
   }
}
