package net.minecraft.block;

import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWallSign extends BlockSign {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);

   public BlockWallSign() {
      this.j(this.M.b().a(a, EnumFacing.c));
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      EnumFacing var3 = var1.p(var2).b(a);
      float var4 = 0.28125F;
      float var5 = 0.78125F;
      float var6 = 0.0F;
      float var7 = 1.0F;
      float var8 = 0.125F;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      switch (var3) {
         case c:
            this.a(var6, var4, 1.0F - var8, var7, var5, 1.0F);
            break;
         case d:
            this.a(var6, var4, 0.0F, var7, var5, var8);
            break;
         case e:
            this.a(1.0F - var8, var4, var6, 1.0F, var5, var7);
            break;
         case f:
            this.a(0.0F, var4, var6, var8, var5, var7);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = var3.b(a);
      if (!var1.p(var2.a(var5.d())).c().t().a()) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
      }

      super.a(var1, var2, var3, var4);
   }

   @Override
   public IBlockState a(int var1) {
      EnumFacing var2 = EnumFacing.a(var1);
      if (var2.k() == EnumFacing.Axis.b) {
         var2 = EnumFacing.c;
      }

      return this.Q().a(a, var2);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
