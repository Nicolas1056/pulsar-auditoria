package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemLead extends Item {
   public ItemLead() {
      this.a(CreativeTabs.i);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      Block var9 = var3.p(var4).c();
      if (var9 instanceof BlockFence) {
         if (var3.D) {
            return true;
         } else {
            a(var2, var3, var4);
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean a(EntityPlayer var0, World var1, BlockPos var2) {
      EntityLeashKnot var3 = EntityLeashKnot.b(var1, var2);
      boolean var4 = false;
      double var5 = 7.0;
      int var7 = var2.n();
      int var8 = var2.o();
      int var9 = var2.p();

      for (EntityLiving var12 : var1.a(EntityLiving.class, new AxisAlignedBB(var7 - var5, var8 - var5, var9 - var5, var7 + var5, var8 + var5, var9 + var5))) {
         if (var12.cc() && var12.cd() == var0) {
            if (var3 == null) {
               var3 = EntityLeashKnot.a(var1, var2);
            }

            var12.a(var3, true);
            var4 = true;
         }
      }

      return var4;
   }
}
