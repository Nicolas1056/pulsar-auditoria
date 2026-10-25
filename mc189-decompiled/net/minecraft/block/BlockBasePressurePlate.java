package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockBasePressurePlate extends Block {
   protected BlockBasePressurePlate(Material var1) {
      this(var1, var1.r());
   }

   protected BlockBasePressurePlate(Material var1, MapColor var2) {
      super(var1, var2);
      this.a(CreativeTabs.d);
      this.a(true);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.d(var1.p(var2));
   }

   protected void d(IBlockState var1) {
      boolean var2 = this.e(var1) > 0;
      float var3 = 0.0625F;
      if (var2) {
         this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.03125F, 0.9375F);
      } else {
         this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.0625F, 0.9375F);
      }
   }

   @Override
   public int a(World var1) {
      return 20;
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return true;
   }

   @Override
   public boolean g() {
      return true;
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return this.m(var1, var2.b());
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!this.m(var1, var2.b())) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
      }
   }

   private boolean m(World var1, BlockPos var2) {
      return World.a(var1, var2) || var1.p(var2).c() instanceof BlockFence;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         int var5 = this.e(var3);
         if (var5 > 0) {
            this.a(var1, var2, var3, var5);
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (!var1.D) {
         int var5 = this.e(var3);
         if (var5 == 0) {
            this.a(var1, var2, var3, var5);
         }
      }
   }

   protected void a(World var1, BlockPos var2, IBlockState var3, int var4) {
      int var5 = this.f(var1, var2);
      boolean var6 = var4 > 0;
      boolean var7 = var5 > 0;
      if (var4 != var5) {
         var3 = this.a(var3, var5);
         var1.a(var2, var3, 2);
         this.e(var1, var2);
         var1.b(var2, var2);
      }

      if (!var7 && var6) {
         var1.a(var2.n() + 0.5, var2.o() + 0.1, var2.p() + 0.5, "random.click", 0.3F, 0.5F);
      } else if (var7 && !var6) {
         var1.a(var2.n() + 0.5, var2.o() + 0.1, var2.p() + 0.5, "random.click", 0.3F, 0.6F);
      }

      if (var7) {
         var1.a(var2, this, this.a(var1));
      }
   }

   protected AxisAlignedBB a(BlockPos var1) {
      float var2 = 0.125F;
      return new AxisAlignedBB(var1.n() + 0.125F, var1.o(), var1.p() + 0.125F, var1.n() + 1 - 0.125F, var1.o() + 0.25, var1.p() + 1 - 0.125F);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      if (this.e(var3) > 0) {
         this.e(var1, var2);
      }

      super.b(var1, var2, var3);
   }

   protected void e(World var1, BlockPos var2) {
      var1.c(var2, this);
      var1.c(var2.b(), this);
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return this.e(var3);
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var4 == EnumFacing.b ? this.e(var3) : 0;
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public void j() {
      float var1 = 0.5F;
      float var2 = 0.125F;
      float var3 = 0.5F;
      this.a(0.0F, 0.375F, 0.0F, 1.0F, 0.625F, 1.0F);
   }

   @Override
   public int k() {
      return 1;
   }

   protected abstract int f(World var1, BlockPos var2);

   protected abstract int e(IBlockState var1);

   protected abstract IBlockState a(IBlockState var1, int var2);
}
