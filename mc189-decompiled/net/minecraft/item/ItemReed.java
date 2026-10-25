package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSnow;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class ItemReed extends Item {
   private Block a;

   public ItemReed(Block var1) {
      this.a = var1;
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.p(var4);
      Block var10 = var9.c();
      if (var10 == Blocks.aH && var9.b(BlockSnow.a) < 1) {
         var5 = EnumFacing.b;
      } else if (!var10.a(var3, var4)) {
         var4 = var4.a(var5);
      }

      if (!var2.a(var4, var5, var1)) {
         return false;
      } else if (var1.b == 0) {
         return false;
      } else {
         if (var3.a(this.a, var4, false, var5, null, var1)) {
            IBlockState var11 = this.a.a(var3, var4, var5, var6, var7, var8, 0, var2);
            if (var3.a(var4, var11, 3)) {
               var11 = var3.p(var4);
               if (var11.c() == this.a) {
                  ItemBlock.a(var3, var2, var4, var1);
                  var11.c().a(var3, var4, var11, var2, var1);
               }

               var3.a(var4.n() + 0.5F, var4.o() + 0.5F, var4.p() + 0.5F, this.a.H.b(), (this.a.H.d() + 1.0F) / 2.0F, this.a.H.e() * 0.8F);
               var1.b--;
               return true;
            }
         }

         return false;
      }
   }
}
