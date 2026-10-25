package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemRedstone extends Item {
   public ItemRedstone() {
      this.a(CreativeTabs.d);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      boolean var9 = var3.p(var4).c().a(var3, var4);
      BlockPos var10 = var9 ? var4 : var4.a(var5);
      if (!var2.a(var10, var5, var1)) {
         return false;
      } else {
         Block var11 = var3.p(var10).c();
         if (!var3.a(var11, var10, false, var5, null, var1)) {
            return false;
         } else if (Blocks.af.d(var3, var10)) {
            var1.b--;
            var3.a(var10, Blocks.af.Q());
            return true;
         } else {
            return false;
         }
      }
   }
}
