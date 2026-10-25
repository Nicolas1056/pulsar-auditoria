package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockReed extends Block {
   public static final PropertyInteger a = PropertyInteger.a("age", 0, 15);

   protected BlockReed() {
      super(Material.k);
      this.j(this.M.b().a(a, 0));
      float var1 = 0.375F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 1.0F, 0.5F + var1);
      this.a(true);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var1.p(var2.b()).c() == Blocks.aM || this.e(var1, var2, var3)) {
         if (var1.d(var2.a())) {
            int var5 = 1;

            while (var1.p(var2.c(var5)).c() == this) {
               var5++;
            }

            if (var5 < 3) {
               int var6 = var3.b(a);
               if (var6 == 15) {
                  var1.a(var2.a(), this.Q());
                  var1.a(var2, var3.a(a, 0), 4);
               } else {
                  var1.a(var2, var3.a(a, var6 + 1), 4);
               }
            }
         }
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      Block var3 = var1.p(var2.b()).c();
      if (var3 == this) {
         return true;
      } else if (var3 != Blocks.c && var3 != Blocks.d && var3 != Blocks.m) {
         return false;
      } else {
         for (EnumFacing var5 : EnumFacing.Plane.a) {
            if (var1.p(var2.a(var5).b()).c().t() == Material.h) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.e(var1, var2, var3);
   }

   protected final boolean e(World var1, BlockPos var2, IBlockState var3) {
      if (this.e(var1, var2)) {
         return true;
      } else {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
         return false;
      }
   }

   public boolean e(World var1, BlockPos var2) {
      return this.d(var1, var2);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.aJ;
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
   public Item c(World var1, BlockPos var2) {
      return Items.aJ;
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return var1.b(var2).b(var2);
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
}
