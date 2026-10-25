package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockBarrier extends Block {
   protected BlockBarrier() {
      super(Material.I);
      this.x();
      this.b(6000001.0F);
      this.K();
      this.t = true;
   }

   @Override
   public int b() {
      return -1;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public float h() {
      return 1.0F;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
   }
}
