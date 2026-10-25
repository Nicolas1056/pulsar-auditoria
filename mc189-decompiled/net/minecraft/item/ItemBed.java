package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class ItemBed extends Item {
   public ItemBed() {
      this.a(CreativeTabs.c);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var3.D) {
         return true;
      } else if (var5 != EnumFacing.b) {
         return false;
      } else {
         IBlockState var9 = var3.p(var4);
         Block var10 = var9.c();
         boolean var11 = var10.a(var3, var4);
         if (!var11) {
            var4 = var4.a();
         }

         int var12 = MathHelper.c(var2.y * 4.0F / 360.0F + 0.5) & 3;
         EnumFacing var13 = EnumFacing.b(var12);
         BlockPos var14 = var4.a(var13);
         if (var2.a(var4, var5, var1) && var2.a(var14, var5, var1)) {
            boolean var15 = var3.p(var14).c().a(var3, var14);
            boolean var16 = var11 || var3.d(var4);
            boolean var17 = var15 || var3.d(var14);
            if (var16 && var17 && World.a(var3, var4.b()) && World.a(var3, var14.b())) {
               IBlockState var18 = Blocks.C.Q().a(BlockBed.b, false).a(BlockBed.O, var13).a(BlockBed.a, BlockBed.EnumPartType.b);
               if (var3.a(var4, var18, 3)) {
                  IBlockState var19 = var18.a(BlockBed.a, BlockBed.EnumPartType.a);
                  var3.a(var14, var19, 3);
               }

               var1.b--;
               return true;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }
}
