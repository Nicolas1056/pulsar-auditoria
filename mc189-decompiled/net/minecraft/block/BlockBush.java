package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockBush extends Block {
   protected BlockBush() {
      this(Material.k);
   }

   protected BlockBush(Material var1) {
      this(var1, var1.r());
   }

   protected BlockBush(Material var1, MapColor var2) {
      super(var1, var2);
      this.a(true);
      float var3 = 0.2F;
      this.a(0.5F - var3, 0.0F, 0.5F - var3, 0.5F + var3, var3 * 3.0F, 0.5F + var3);
      this.a(CreativeTabs.c);
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return super.d(var1, var2) && this.c(var1.p(var2.b()).c());
   }

   protected boolean c(Block var1) {
      return var1 == Blocks.c || var1 == Blocks.d || var1 == Blocks.ak;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      super.a(var1, var2, var3, var4);
      this.e(var1, var2, var3);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.e(var1, var2, var3);
   }

   protected void e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.f(var1, var2, var3)) {
         this.b(var1, var2, var3, 0);
         var1.a(var2, Blocks.a.Q(), 3);
      }
   }

   public boolean f(World var1, BlockPos var2, IBlockState var3) {
      return this.c(var1.p(var2.b()).c());
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }
}
