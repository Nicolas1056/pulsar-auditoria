package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;

public class ItemCarrotOnAStick extends Item {
   public ItemCarrotOnAStick() {
      this.a(CreativeTabs.e);
      this.c(1);
      this.d(25);
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
      if (var3.au() && var3.m instanceof EntityPig) {
         EntityPig var4 = (EntityPig)var3.m;
         if (var4.cm().h() && var1.j() - var1.i() >= 7) {
            var4.cm().g();
            var1.a(7, var3);
            if (var1.b == 0) {
               ItemStack var5 = new ItemStack(Items.aR);
               var5.d(var1.o());
               return var5;
            }
         }
      }

      var3.b(StatList.ad[Item.b(this)]);
      return var1;
   }
}
