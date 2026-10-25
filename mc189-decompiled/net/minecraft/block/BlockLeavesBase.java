package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockLeavesBase extends Block {
   protected boolean R;

   protected BlockLeavesBase(Material var1, boolean var2) {
      super(var1);
      this.R = var2;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return !this.R && var1.p(var2).c() == this ? false : super.a(var1, var2, var3);
   }
}
