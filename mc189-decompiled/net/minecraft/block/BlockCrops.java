package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class BlockCrops extends BlockBush implements IGrowable {
   public static final PropertyInteger a = PropertyInteger.a("age", 0, 7);

   protected BlockCrops() {
      this.j(this.M.b().a(a, 0));
      this.a(true);
      float var1 = 0.5F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
      this.a(null);
      this.c(0.0F);
      this.a(h);
      this.K();
   }

   @Override
   protected boolean c(Block var1) {
      return var1 == Blocks.ak;
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.b(var1, var2, var3, var4);
      if (var1.l(var2.a()) >= 9) {
         int var5 = var3.b(a);
         if (var5 < 7) {
            float var6 = a(this, var1, var2);
            if (var4.nextInt((int)(25.0F / var6) + 1) == 0) {
               var1.a(var2, var3.a(a, var5 + 1), 2);
            }
         }
      }
   }

   public void g(World var1, BlockPos var2, IBlockState var3) {
      int var4 = var3.b(a) + MathHelper.a(var1.s, 2, 5);
      if (var4 > 7) {
         var4 = 7;
      }

      var1.a(var2, var3.a(a, var4), 2);
   }

   protected static float a(Block var0, World var1, BlockPos var2) {
      float var3 = 1.0F;
      BlockPos var4 = var2.b();

      for (int var5 = -1; var5 <= 1; var5++) {
         for (int var6 = -1; var6 <= 1; var6++) {
            float var7 = 0.0F;
            IBlockState var8 = var1.p(var4.a(var5, 0, var6));
            if (var8.c() == Blocks.ak) {
               var7 = 1.0F;
               if (var8.b(BlockFarmland.a) > 0) {
                  var7 = 3.0F;
               }
            }

            if (var5 != 0 || var6 != 0) {
               var7 /= 4.0F;
            }

            var3 += var7;
         }
      }

      BlockPos var12 = var2.c();
      BlockPos var13 = var2.d();
      BlockPos var14 = var2.e();
      BlockPos var15 = var2.f();
      boolean var9 = var0 == var1.p(var14).c() || var0 == var1.p(var15).c();
      boolean var10 = var0 == var1.p(var12).c() || var0 == var1.p(var13).c();
      if (var9 && var10) {
         var3 /= 2.0F;
      } else {
         boolean var11 = var0 == var1.p(var14.c()).c() || var0 == var1.p(var15.c()).c() || var0 == var1.p(var15.d()).c() || var0 == var1.p(var14.d()).c();
         if (var11) {
            var3 /= 2.0F;
         }
      }

      return var3;
   }

   @Override
   public boolean f(World var1, BlockPos var2, IBlockState var3) {
      return (var1.k(var2) >= 8 || var1.i(var2)) && this.c(var1.p(var2.b()).c());
   }

   protected Item l() {
      return Items.N;
   }

   protected Item n() {
      return Items.O;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      super.a(var1, var2, var3, var4, 0);
      if (!var1.D) {
         int var6 = var3.b(a);
         if (var6 >= 7) {
            int var7 = 3 + var5;

            for (int var8 = 0; var8 < var7; var8++) {
               if (var1.s.nextInt(15) <= var6) {
                  a(var1, var2, new ItemStack(this.l(), 1, 0));
               }
            }
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return var1.b(a) == 7 ? this.n() : this.l();
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return this.l();
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var3.b(a) < 7;
   }

   @Override
   public boolean a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.g(var1, var3, var4);
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
