package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSoulSand extends Block {
   public BlockSoulSand() {
      super(Material.p, MapColor.B);
      this.a(CreativeTabs.b);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      float var4 = 0.125F;
      return new AxisAlignedBB(var2.n(), var2.o(), var2.p(), var2.n() + 1, var2.o() + 1 - var4, var2.p() + 1);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      var4.v *= 0.4;
      var4.x *= 0.4;
   }
}
