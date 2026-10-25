package net.minecraft.item;

import net.minecraft.block.BlockStandingSign;
import net.minecraft.block.BlockWallSign;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemSign extends Item {
   public ItemSign() {
      this.h = 16;
      this.a(CreativeTabs.c);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var5 == EnumFacing.a) {
         return false;
      } else if (!var3.p(var4).c().t().a()) {
         return false;
      } else {
         var4 = var4.a(var5);
         if (!var2.a(var4, var5, var1)) {
            return false;
         } else if (!Blocks.an.d(var3, var4)) {
            return false;
         } else if (var3.D) {
            return true;
         } else {
            if (var5 == EnumFacing.b) {
               int var9 = MathHelper.c((var2.y + 180.0F) * 16.0F / 360.0F + 0.5) & 15;
               var3.a(var4, Blocks.an.Q().a(BlockStandingSign.a, var9), 3);
            } else {
               var3.a(var4, Blocks.ax.Q().a(BlockWallSign.a, var5), 3);
            }

            var1.b--;
            TileEntity var11 = var3.s(var4);
            if (var11 instanceof TileEntitySign && !ItemBlock.a(var3, var2, var4, var1)) {
               var2.a((TileEntitySign)var11);
            }

            return true;
         }
      }
   }
}
