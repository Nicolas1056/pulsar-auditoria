package net.minecraft.dispenser;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;

public interface IBlockSource extends ILocatableSource {
   @Override
   double a();

   @Override
   double b();

   @Override
   double c();

   BlockPos d();

   int f();

   <T extends TileEntity> T h();
}
