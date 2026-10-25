package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BlockContainer extends Block implements ITileEntityProvider {
   protected BlockContainer(Material var1) {
      this(var1, var1.r());
   }

   protected BlockContainer(Material var1, MapColor var2) {
      super(var1, var2);
      this.A = true;
   }

   protected boolean a(World var1, BlockPos var2, EnumFacing var3) {
      return var1.p(var2.a(var3)).c().t() == Material.A;
   }

   protected boolean e(World var1, BlockPos var2) {
      return this.a(var1, var2, EnumFacing.c) || this.a(var1, var2, EnumFacing.d) || this.a(var1, var2, EnumFacing.e) || this.a(var1, var2, EnumFacing.f);
   }

   @Override
   public int b() {
      return -1;
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      super.b(var1, var2, var3);
      var1.t(var2);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, int var4, int var5) {
      super.a(var1, var2, var3, var4, var5);
      TileEntity var6 = var1.s(var2);
      return var6 == null ? false : var6.c(var4, var5);
   }
}
