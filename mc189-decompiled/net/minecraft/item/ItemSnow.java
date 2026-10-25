package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemSnow extends ItemBlock {
   public ItemSnow(Block var1) {
      super(var1);
      this.d(0);
      this.a(true);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.b == 0) {
         return false;
      } else if (!var2.a(var4, var5, var1)) {
         return false;
      } else {
         IBlockState var9 = var3.p(var4);
         Block var10 = var9.c();
         BlockPos var11 = var4;
         if ((var5 != EnumFacing.b || var10 != this.a) && !var10.a(var3, var4)) {
            var11 = var4.a(var5);
            var9 = var3.p(var11);
            var10 = var9.c();
         }

         if (var10 == this.a) {
            int var12 = var9.b(BlockSnow.a);
            if (var12 <= 7) {
               IBlockState var13 = var9.a(BlockSnow.a, var12 + 1);
               AxisAlignedBB var14 = this.a.a(var3, var11, var13);
               if (var14 != null && var3.b(var14) && var3.a(var11, var13, 2)) {
                  var3.a(var11.n() + 0.5F, var11.o() + 0.5F, var11.p() + 0.5F, this.a.H.b(), (this.a.H.d() + 1.0F) / 2.0F, this.a.H.e() * 0.8F);
                  var1.b--;
                  return true;
               }
            }
         }

         return super.a(var1, var2, var3, var11, var5, var6, var7, var8);
      }
   }

   @Override
   public int a(int var1) {
      return var1;
   }
}
