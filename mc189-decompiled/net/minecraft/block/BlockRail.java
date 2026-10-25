package net.minecraft.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockRail extends BlockRailBase {
   public static final PropertyEnum<BlockRailBase.EnumRailDirection> b = PropertyEnum.a("shape", BlockRailBase.EnumRailDirection.class);

   protected BlockRail() {
      super(false);
      this.j(this.M.b().a(b, BlockRailBase.EnumRailDirection.a));
   }

   @Override
   protected void b(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (var4.i() && new BlockRailBase.Rail(var1, var2, var3).a() == 3) {
         this.a(var1, var2, var3, false);
      }
   }

   @Override
   public IProperty<BlockRailBase.EnumRailDirection> n() {
      return b;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(b, BlockRailBase.EnumRailDirection.a(var1));
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(b).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, b);
   }
}
