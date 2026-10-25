package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockLilyPad extends BlockBush {
   protected BlockLilyPad() {
      float var1 = 0.5F;
      float var2 = 0.015625F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var2, 0.5F + var1);
      this.a(CreativeTabs.c);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      if (var6 == null || !(var6 instanceof EntityBoat)) {
         super.a(var1, var2, var3, var4, var5, var6);
      }
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return new AxisAlignedBB(var2.n() + this.B, var2.o() + this.C, var2.p() + this.D, var2.n() + this.E, var2.o() + this.F, var2.p() + this.G);
   }

   @Override
   public int H() {
      return 7455580;
   }

   @Override
   public int h(IBlockState var1) {
      return 7455580;
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return 2129968;
   }

   @Override
   protected boolean c(Block var1) {
      return var1 == Blocks.j;
   }

   @Override
   public boolean f(World var1, BlockPos var2, IBlockState var3) {
      if (var2.o() >= 0 && var2.o() < 256) {
         IBlockState var4 = var1.p(var2.b());
         return var4.c().t() == Material.h && var4.b(BlockLiquid.b) == 0;
      } else {
         return false;
      }
   }

   @Override
   public int c(IBlockState var1) {
      return 0;
   }
}
