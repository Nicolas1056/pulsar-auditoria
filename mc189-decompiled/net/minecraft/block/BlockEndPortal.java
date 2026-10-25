package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockEndPortal extends BlockContainer {
   protected BlockEndPortal(Material var1) {
      super(var1);
      this.a(1.0F);
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityEndPortal();
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.0625F;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.a ? super.a(var1, var2, var3) : false;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
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
   public int a(Random var1) {
      return 0;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (var4.m == null && var4.l == null && !var1.D) {
         var4.c(1);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      double var5 = var2.n() + var4.nextFloat();
      double var7 = var2.o() + 0.8F;
      double var9 = var2.p() + var4.nextFloat();
      double var11 = 0.0;
      double var13 = 0.0;
      double var15 = 0.0;
      var1.a(EnumParticleTypes.l, var5, var7, var9, var11, var13, var15);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return null;
   }

   @Override
   public MapColor g(IBlockState var1) {
      return MapColor.E;
   }
}
