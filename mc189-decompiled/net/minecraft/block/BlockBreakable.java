package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;

public class BlockBreakable extends Block {
   private boolean a;

   protected BlockBreakable(Material var1, boolean var2) {
      this(var1, var2, var1.r());
   }

   protected BlockBreakable(Material var1, boolean var2, MapColor var3) {
      super(var1, var3);
      this.a = var2;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      IBlockState var4 = var1.p(var2);
      Block var5 = var4.c();
      if (this == Blocks.w || this == Blocks.cG) {
         if (var1.p(var2.a(var3.d())) != var4) {
            return true;
         }

         if (var5 == this) {
            return false;
         }
      }

      return !this.a && var5 == this ? false : super.a(var1, var2, var3);
   }
}
