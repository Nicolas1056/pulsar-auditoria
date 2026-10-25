package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ItemNameTag extends Item {
   public ItemNameTag() {
      this.a(CreativeTabs.i);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, EntityLivingBase var3) {
      if (!var1.s()) {
         return false;
      } else if (var3 instanceof EntityLiving) {
         EntityLiving var4 = (EntityLiving)var3;
         var4.a(var1.q());
         var4.bX();
         var1.b--;
         return true;
      } else {
         return super.a(var1, var2, var3);
      }
   }
}
