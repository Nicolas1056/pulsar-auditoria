package net.minecraft.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;

public class ItemSaddle extends Item {
   public ItemSaddle() {
      this.h = 1;
      this.a(CreativeTabs.e);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, EntityLivingBase var3) {
      if (var3 instanceof EntityPig) {
         EntityPig var4 = (EntityPig)var3;
         if (!var4.cl() && !var4.j_()) {
            var4.l(true);
            var4.o.a(var4, "mob.horse.leather", 0.5F, 1.0F);
            var1.b--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean a(ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      this.a(var1, null, var2);
      return true;
   }
}
