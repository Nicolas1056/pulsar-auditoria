package net.minecraft.item;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemDye extends Item {
   public static final int[] a = new int[]{
      1973019, 11743532, 3887386, 5320730, 2437522, 8073150, 2651799, 11250603, 4408131, 14188952, 4312372, 14602026, 6719955, 12801229, 15435844, 15790320
   };

   public ItemDye() {
      this.a(true);
      this.d(0);
      this.a(CreativeTabs.l);
   }

   @Override
   public String e_(ItemStack var1) {
      int var2 = var1.i();
      return super.a() + "." + EnumDyeColor.a(var2).d();
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var2.a(var4.a(var5), var5, var1)) {
         return false;
      } else {
         EnumDyeColor var9 = EnumDyeColor.a(var1.i());
         if (var9 == EnumDyeColor.a) {
            if (a(var1, var3, var4)) {
               if (!var3.D) {
                  var3.b(2005, var4, 0);
               }

               return true;
            }
         } else if (var9 == EnumDyeColor.m) {
            IBlockState var10 = var3.p(var4);
            Block var11 = var10.c();
            if (var11 == Blocks.r && var10.b(BlockPlanks.a) == BlockPlanks.EnumType.d) {
               if (var5 == EnumFacing.a) {
                  return false;
               }

               if (var5 == EnumFacing.b) {
                  return false;
               }

               var4 = var4.a(var5);
               if (var3.d(var4)) {
                  IBlockState var12 = Blocks.bN.a(var3, var4, var5, var6, var7, var8, 0, var2);
                  var3.a(var4, var12, 2);
                  if (!var2.bA.d) {
                     var1.b--;
                  }
               }

               return true;
            }
         }

         return false;
      }
   }

   public static boolean a(ItemStack var0, World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      if (var3.c() instanceof IGrowable) {
         IGrowable var4 = (IGrowable)var3.c();
         if (var4.a(var1, var2, var3, var1.D)) {
            if (!var1.D) {
               if (var4.a(var1, var1.s, var2, var3)) {
                  var4.b(var1, var1.s, var2, var3);
               }

               var0.b--;
            }

            return true;
         }
      }

      return false;
   }

   public static void a(World var0, BlockPos var1, int var2) {
      if (var2 == 0) {
         var2 = 15;
      }

      Block var3 = var0.p(var1).c();
      if (var3.t() != Material.a) {
         var3.a((IBlockAccess)var0, var1);

         for (int var4 = 0; var4 < var2; var4++) {
            double var5 = g.nextGaussian() * 0.02;
            double var7 = g.nextGaussian() * 0.02;
            double var9 = g.nextGaussian() * 0.02;
            var0.a(EnumParticleTypes.v, var1.n() + g.nextFloat(), var1.o() + g.nextFloat() * var3.E(), var1.p() + g.nextFloat(), var5, var7, var9);
         }
      }
   }

   @Override
   public boolean a(ItemStack var1, EntityPlayer var2, EntityLivingBase var3) {
      if (var3 instanceof EntitySheep) {
         EntitySheep var4 = (EntitySheep)var3;
         EnumDyeColor var5 = EnumDyeColor.a(var1.i());
         if (!var4.cm() && var4.cl() != var5) {
            var4.b(var5);
            var1.b--;
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (int var4 = 0; var4 < 16; var4++) {
         var3.add(new ItemStack(var1, 1, var4));
      }
   }
}
