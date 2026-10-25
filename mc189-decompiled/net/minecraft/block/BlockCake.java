package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.stats.StatList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCake extends Block {
   public static final PropertyInteger a = PropertyInteger.a("bites", 0, 6);

   protected BlockCake() {
      super(Material.F);
      this.j(this.M.b().a(a, 0));
      this.a(true);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.0625F;
      float var4 = (1 + var1.p(var2).b(a) * 2) / 16.0F;
      float var5 = 0.5F;
      this.a(var4, 0.0F, var3, 1.0F - var3, var5, 1.0F - var3);
   }

   @Override
   public void j() {
      float var1 = 0.0625F;
      float var2 = 0.5F;
      this.a(var1, 0.0F, var1, 1.0F - var1, var2, 1.0F - var1);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      float var4 = 0.0625F;
      float var5 = (1 + var3.b(a) * 2) / 16.0F;
      float var6 = 0.5F;
      return new AxisAlignedBB(var2.n() + var5, var2.o(), var2.p() + var4, var2.n() + 1 - var4, var2.o() + var6, var2.p() + 1 - var4);
   }

   @Override
   public AxisAlignedBB b(World var1, BlockPos var2) {
      return this.a(var1, var2, var1.p(var2));
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
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      this.b(var1, var2, var3, var4);
      return true;
   }

   @Override
   public void a(World var1, BlockPos var2, EntityPlayer var3) {
      this.b(var1, var2, var1.p(var2), var3);
   }

   private void b(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (var4.j(false)) {
         var4.b(StatList.H);
         var4.cl().a(2, 0.1F);
         int var5 = var3.b(a);
         if (var5 < 6) {
            var1.a(var2, var3.a(a, var5 + 1), 3);
         } else {
            var1.g(var2);
         }
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return super.d(var1, var2) ? this.e(var1, var2) : false;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!this.e(var1, var2)) {
         var1.g(var2);
      }
   }

   private boolean e(World var1, BlockPos var2) {
      return var1.p(var2.b()).c().t().a();
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return null;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.aZ;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
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

   @Override
   public int l(World var1, BlockPos var2) {
      return (7 - var1.p(var2).b(a)) * 2;
   }

   @Override
   public boolean O() {
      return true;
   }
}
