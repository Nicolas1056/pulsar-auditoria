package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockCactus extends Block {
   public static final PropertyInteger a = PropertyInteger.a("age", 0, 15);

   protected BlockCactus() {
      super(Material.A);
      this.j(this.M.b().a(a, 0));
      this.a(true);
      this.a(CreativeTabs.c);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      BlockPos var5 = var2.a();
      if (var1.d(var5)) {
         int var6 = 1;

         while (var1.p(var2.c(var6)).c() == this) {
            var6++;
         }

         if (var6 < 3) {
            int var7 = var3.b(a);
            if (var7 == 15) {
               var1.a(var5, this.Q());
               IBlockState var8 = var3.a(a, 0);
               var1.a(var2, var8, 4);
               this.a(var1, var5, var8, this);
            } else {
               var1.a(var2, var3.a(a, var7 + 1), 4);
            }
         }
      }
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      float var4 = 0.0625F;
      return new AxisAlignedBB(var2.n() + var4, var2.o(), var2.p() + var4, var2.n() + 1 - var4, var2.o() + 1 - var4, var2.p() + 1 - var4);
   }

   @Override
   public AxisAlignedBB b(World var1, BlockPos var2) {
      float var3 = 0.0625F;
      return new AxisAlignedBB(var2.n() + var3, var2.o(), var2.p() + var3, var2.n() + 1 - var3, var2.o() + 1, var2.p() + 1 - var3);
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return super.d(var1, var2) ? this.e(var1, var2) : false;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!this.e(var1, var2)) {
         var1.b(var2, true);
      }
   }

   public boolean e(World var1, BlockPos var2) {
      for (EnumFacing var4 : EnumFacing.Plane.a) {
         if (var1.p(var2.a(var4)).c().t().a()) {
            return false;
         }
      }

      Block var5 = var1.p(var2.b()).c();
      return var5 == Blocks.aK || var5 == Blocks.m;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      var4.a(DamageSource.h, 1.0F);
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, var1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
