package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCocoa extends BlockDirectional implements IGrowable {
   public static final PropertyInteger a = PropertyInteger.a("age", 0, 2);

   public BlockCocoa() {
      super(Material.k);
      this.j(this.M.b().a(O, EnumFacing.c).a(a, 0));
      this.a(true);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!this.e(var1, var2, var3)) {
         this.f(var1, var2, var3);
      } else if (var1.s.nextInt(5) == 0) {
         int var5 = var3.b(a);
         if (var5 < 2) {
            var1.a(var2, var3.a(a, var5 + 1), 2);
         }
      }
   }

   public boolean e(World var1, BlockPos var2, IBlockState var3) {
      var2 = var2.a(var3.b(O));
      IBlockState var4 = var1.p(var2);
      return var4.c() == Blocks.r && var4.b(BlockPlanks.a) == BlockPlanks.EnumType.d;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      this.a(var1, var2);
      return super.a(var1, var2, var3);
   }

   @Override
   public AxisAlignedBB b(World var1, BlockPos var2) {
      this.a(var1, var2);
      return super.b(var1, var2);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      EnumFacing var4 = var3.b(O);
      int var5 = var3.b(a);
      int var6 = 4 + var5 * 2;
      int var7 = 5 + var5 * 2;
      float var8 = var6 / 2.0F;
      switch (var4) {
         case d:
            this.a((8.0F - var8) / 16.0F, (12.0F - var7) / 16.0F, (15.0F - var6) / 16.0F, (8.0F + var8) / 16.0F, 0.75F, 0.9375F);
            break;
         case c:
            this.a((8.0F - var8) / 16.0F, (12.0F - var7) / 16.0F, 0.0625F, (8.0F + var8) / 16.0F, 0.75F, (1.0F + var6) / 16.0F);
            break;
         case e:
            this.a(0.0625F, (12.0F - var7) / 16.0F, (8.0F - var8) / 16.0F, (1.0F + var6) / 16.0F, 0.75F, (8.0F + var8) / 16.0F);
            break;
         case f:
            this.a((15.0F - var6) / 16.0F, (12.0F - var7) / 16.0F, (8.0F - var8) / 16.0F, 0.9375F, 0.75F, (8.0F + var8) / 16.0F);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      EnumFacing var6 = EnumFacing.a((double)var4.y);
      var1.a(var2, var3.a(O, var6), 2);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      if (!var3.k().c()) {
         var3 = EnumFacing.c;
      }

      return this.Q().a(O, var3.d()).a(a, 0);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!this.e(var1, var2, var3)) {
         this.f(var1, var2, var3);
      }
   }

   private void f(World var1, BlockPos var2, IBlockState var3) {
      var1.a(var2, Blocks.a.Q(), 3);
      this.b(var1, var2, var3, 0);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      int var6 = var3.b(a);
      byte var7 = 1;
      if (var6 >= 2) {
         var7 = 3;
      }

      for (int var8 = 0; var8 < var7; var8++) {
         a(var1, var2, new ItemStack(Items.aW, 1, EnumDyeColor.m.b()));
      }
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.aW;
   }

   @Override
   public int j(World var1, BlockPos var2) {
      return EnumDyeColor.m.b();
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var3.b(a) < 2;
   }

   @Override
   public boolean a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      var1.a(var3, var4.a(a, var4.b(a) + 1), 2);
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(O, EnumFacing.b(var1)).a(a, (var1 & 15) >> 2);
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(O).b();
      return var2 | var1.b(a) << 2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, O, a);
   }
}
