package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockRedstoneOre extends Block {
   private final boolean a;

   public BlockRedstoneOre(boolean var1) {
      super(Material.e);
      if (var1) {
         this.a(true);
      }

      this.a = var1;
   }

   @Override
   public int a(World var1) {
      return 30;
   }

   @Override
   public void a(World var1, BlockPos var2, EntityPlayer var3) {
      this.e(var1, var2);
      super.a(var1, var2, var3);
   }

   @Override
   public void a(World var1, BlockPos var2, Entity var3) {
      this.e(var1, var2);
      super.a(var1, var2, var3);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      this.e(var1, var2);
      return super.a(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   private void e(World var1, BlockPos var2) {
      this.f(var1, var2);
      if (this == Blocks.aC) {
         var1.a(var2, Blocks.aD.Q());
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this == Blocks.aD) {
         var1.a(var2, Blocks.aC.Q());
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.aC;
   }

   @Override
   public int a(int var1, Random var2) {
      return this.a(var2) + var2.nextInt(var1 + 1);
   }

   @Override
   public int a(Random var1) {
      return 4 + var1.nextInt(2);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      super.a(var1, var2, var3, var4, var5);
      if (this.a(var3, var1.s, var5) != Item.a(this)) {
         int var6 = 1 + var1.s.nextInt(5);
         this.b(var1, var2, var6);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.a) {
         this.f(var1, var2);
      }
   }

   private void f(World var1, BlockPos var2) {
      Random var3 = var1.s;
      double var4 = 0.0625;

      for (int var6 = 0; var6 < 6; var6++) {
         double var7 = var2.n() + var3.nextFloat();
         double var9 = var2.o() + var3.nextFloat();
         double var11 = var2.p() + var3.nextFloat();
         if (var6 == 0 && !var1.p(var2.a()).c().c()) {
            var9 = var2.o() + var4 + 1.0;
         }

         if (var6 == 1 && !var1.p(var2.b()).c().c()) {
            var9 = var2.o() - var4;
         }

         if (var6 == 2 && !var1.p(var2.d()).c().c()) {
            var11 = var2.p() + var4 + 1.0;
         }

         if (var6 == 3 && !var1.p(var2.c()).c().c()) {
            var11 = var2.p() - var4;
         }

         if (var6 == 4 && !var1.p(var2.f()).c().c()) {
            var7 = var2.n() + var4 + 1.0;
         }

         if (var6 == 5 && !var1.p(var2.e()).c().c()) {
            var7 = var2.n() - var4;
         }

         if (var7 < var2.n() || var7 > var2.n() + 1 || var9 < 0.0 || var9 > var2.o() + 1 || var11 < var2.p() || var11 > var2.p() + 1) {
            var1.a(EnumParticleTypes.E, var7, var9, var11, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   protected ItemStack i(IBlockState var1) {
      return new ItemStack(Blocks.aC);
   }
}
