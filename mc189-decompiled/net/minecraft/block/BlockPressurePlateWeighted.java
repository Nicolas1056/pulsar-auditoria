package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockPressurePlateWeighted extends BlockBasePressurePlate {
   public static final PropertyInteger a = PropertyInteger.a("power", 0, 15);
   private final int b;

   protected BlockPressurePlateWeighted(Material var1, int var2) {
      this(var1, var2, var1.r());
   }

   protected BlockPressurePlateWeighted(Material var1, int var2, MapColor var3) {
      super(var1, var3);
      this.j(this.M.b().a(a, 0));
      this.b = var2;
   }

   @Override
   protected int f(World var1, BlockPos var2) {
      int var3 = Math.min(var1.a(Entity.class, this.a(var2)).size(), this.b);
      if (var3 > 0) {
         float var4 = (float)Math.min(this.b, var3) / this.b;
         return MathHelper.f(var4 * 15.0F);
      } else {
         return 0;
      }
   }

   @Override
   protected int e(IBlockState var1) {
      return var1.b(a);
   }

   @Override
   protected IBlockState a(IBlockState var1, int var2) {
      return var1.a(a, var2);
   }

   @Override
   public int a(World var1) {
      return 10;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, var1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
