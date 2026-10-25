package net.minecraft.world;

import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;

public interface IBlockAccess {
   TileEntity s(BlockPos var1);

   int b(BlockPos var1, int var2);

   IBlockState p(BlockPos var1);

   boolean d(BlockPos var1);

   BiomeGenBase b(BlockPos var1);

   boolean W();

   int a(BlockPos var1, EnumFacing var2);

   WorldType G();
}
