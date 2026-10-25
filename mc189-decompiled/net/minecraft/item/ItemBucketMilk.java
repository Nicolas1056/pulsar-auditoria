package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemBucketMilk extends Item {
   public ItemBucketMilk() {
      this.c(1);
      this.a(CreativeTabs.f);
   }

   @Override
   public ItemStack b(ItemStack var1, World var2, EntityPlayer var3) {
      if (!var3.bA.d) {
         var1.b--;
      }

      if (!var2.D) {
         var3.bk();
      }

      var3.b(StatList.ad[Item.b(this)]);
      return var1.b <= 0 ? new ItemStack(Items.aw) : var1;
   }

   @Override
   public int d(ItemStack var1) {
      return 32;
   }

   @Override
   public EnumAction e(ItemStack var1) {
      return EnumAction.c;
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      var3.a(var1, this.d(var1));
      return var1;
   }
}
