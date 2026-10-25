package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockPressurePlate extends BlockBasePressurePlate {
   public static final PropertyBool a = PropertyBool.a("powered");
   private final BlockPressurePlate.Sensitivity b;

   protected BlockPressurePlate(Material var1, BlockPressurePlate.Sensitivity var2) {
      super(var1);
      this.j(this.M.b().a(a, false));
      this.b = var2;
   }

   @Override
   protected int e(IBlockState var1) {
      return var1.b(a) ? 15 : 0;
   }

   @Override
   protected IBlockState a(IBlockState var1, int var2) {
      return var1.a(a, var2 > 0);
   }

   @Override
   protected int f(World var1, BlockPos var2) {
      AxisAlignedBB var3 = this.a(var2);
      List var4;
      switch (this.b) {
         case a:
            var4 = var1.b(null, var3);
            break;
         case b:
            var4 = var1.a(EntityLivingBase.class, var3);
            break;
         default:
            return 0;
      }

      if (!var4.isEmpty()) {
         for (Entity var6 : var4) {
            if (!var6.aI()) {
               return 15;
            }
         }
      }

      return 0;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, var1 == 1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a) ? 1 : 0;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }

   public static enum Sensitivity {
      a,
      b;
   }
}
