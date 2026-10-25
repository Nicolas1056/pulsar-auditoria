package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockSourceImpl implements IBlockSource {
   private final World a;
   private final BlockPos b;

   public BlockSourceImpl(World var1, BlockPos var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public World i() {
      return this.a;
   }

   @Override
   public double a() {
      return this.b.n() + 0.5;
   }

   @Override
   public double b() {
      return this.b.o() + 0.5;
   }

   @Override
   public double c() {
      return this.b.p() + 0.5;
   }

   @Override
   public BlockPos d() {
      return this.b;
   }

   @Override
   public int f() {
      IBlockState var1 = this.a.p(this.b);
      return var1.c().c(var1);
   }

   @Override
   public <T extends TileEntity> T h() {
      return (T)this.a.s(this.b);
   }
}
