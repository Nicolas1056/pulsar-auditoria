package net.minecraft.item;

import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class ItemEnderEye extends Item {
   public ItemEnderEye() {
      this.a(CreativeTabs.f);
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      IBlockState var9 = var3.p(var4);
      if (!var2.a(var4.a(var5), var5, var1) || var9.c() != Blocks.bG || var9.b(BlockEndPortalFrame.b)) {
         return false;
      } else if (var3.D) {
         return true;
      } else {
         var3.a(var4, var9.a(BlockEndPortalFrame.b, true), 2);
         var3.e(var4, Blocks.bG);
         var1.b--;

         for (int var10 = 0; var10 < 16; var10++) {
            double var11 = var4.n() + (5.0F + g.nextFloat() * 6.0F) / 16.0F;
            double var13 = var4.o() + 0.8125F;
            double var15 = var4.p() + (5.0F + g.nextFloat() * 6.0F) / 16.0F;
            double var17 = 0.0;
            double var19 = 0.0;
            double var21 = 0.0;
            var3.a(EnumParticleTypes.l, var11, var13, var15, var17, var19, var21);
         }

         EnumFacing var23 = var9.b(BlockEndPortalFrame.a);
         int var24 = 0;
         int var12 = 0;
         boolean var25 = false;
         boolean var14 = true;
         EnumFacing var26 = var23.e();

         for (int var16 = -2; var16 <= 2; var16++) {
            BlockPos var30 = var4.a(var26, var16);
            IBlockState var18 = var3.p(var30);
            if (var18.c() == Blocks.bG) {
               if (!var18.b(BlockEndPortalFrame.b)) {
                  var14 = false;
                  break;
               }

               var12 = var16;
               if (!var25) {
                  var24 = var16;
                  var25 = true;
               }
            }
         }

         if (var14 && var12 == var24 + 2) {
            BlockPos var27 = var4.a(var23, 4);

            for (int var31 = var24; var31 <= var12; var31++) {
               BlockPos var34 = var27.a(var26, var31);
               IBlockState var37 = var3.p(var34);
               if (var37.c() != Blocks.bG || !var37.b(BlockEndPortalFrame.b)) {
                  var14 = false;
                  break;
               }
            }

            for (int var32 = var24 - 1; var32 <= var12 + 1; var32 += 4) {
               var27 = var4.a(var26, var32);

               for (int var35 = 1; var35 <= 3; var35++) {
                  BlockPos var38 = var27.a(var23, var35);
                  IBlockState var20 = var3.p(var38);
                  if (var20.c() != Blocks.bG || !var20.b(BlockEndPortalFrame.b)) {
                     var14 = false;
                     break;
                  }
               }
            }

            if (var14) {
               for (int var33 = var24; var33 <= var12; var33++) {
                  var27 = var4.a(var26, var33);

                  for (int var36 = 1; var36 <= 3; var36++) {
                     BlockPos var39 = var27.a(var23, var36);
                     var3.a(var39, Blocks.bF.Q(), 2);
                  }
               }
            }
         }

         return true;
      }
   }

   @Override
   public ItemStack a(ItemStack var1, World var2, EntityPlayer var3) {
      MovingObjectPosition var4 = this.a(var2, var3, false);
      if (var4 != null && var4.a == MovingObjectPosition.MovingObjectType.b && var2.p(var4.a()).c() == Blocks.bG) {
         return var1;
      } else {
         if (!var2.D) {
            BlockPos var5 = var2.a("Stronghold", new BlockPos(var3));
            if (var5 != null) {
               EntityEnderEye var6 = new EntityEnderEye(var2, var3.s, var3.t, var3.u);
               var6.a(var5);
               var2.d(var6);
               var2.a((Entity)var3, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
               var2.a(null, 1002, new BlockPos(var3), 0);
               if (!var3.bA.d) {
                  var1.b--;
               }

               var3.b(StatList.ad[Item.b(this)]);
            }
         }

         return var1;
      }
   }
}
