package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class WorldGenBigTree extends WorldGenAbstractTree {
   private Random k;
   private World l;
   private BlockPos m = BlockPos.a;
   int a;
   int b;
   double c = 0.618;
   double d = 0.381;
   double e = 1.0;
   double f = 1.0;
   int g = 1;
   int h = 12;
   int i = 4;
   List<WorldGenBigTree.FoliageCoordinates> j;

   public WorldGenBigTree(boolean var1) {
      super(var1);
   }

   void a() {
      this.b = (int)(this.a * this.c);
      if (this.b >= this.a) {
         this.b = this.a - 1;
      }

      int var1 = (int)(1.382 + Math.pow(this.f * this.a / 13.0, 2.0));
      if (var1 < 1) {
         var1 = 1;
      }

      int var2 = this.m.o() + this.b;
      int var3 = this.a - this.i;
      this.j = Lists.newArrayList();
      this.j.add(new WorldGenBigTree.FoliageCoordinates(this.m.b(var3), var2));

      for (; var3 >= 0; var3--) {
         float var4 = this.a(var3);
         if (!(var4 < 0.0F)) {
            for (int var5 = 0; var5 < var1; var5++) {
               double var6 = this.e * var4 * (this.k.nextFloat() + 0.328);
               double var8 = this.k.nextFloat() * 2.0F * Math.PI;
               double var10 = var6 * Math.sin(var8) + 0.5;
               double var12 = var6 * Math.cos(var8) + 0.5;
               BlockPos var14 = this.m.a(var10, (double)(var3 - 1), var12);
               BlockPos var15 = var14.b(this.i);
               if (this.a(var14, var15) == -1) {
                  int var16 = this.m.n() - var14.n();
                  int var17 = this.m.p() - var14.p();
                  double var18 = var14.o() - Math.sqrt(var16 * var16 + var17 * var17) * this.d;
                  int var20 = var18 > var2 ? var2 : (int)var18;
                  BlockPos var21 = new BlockPos(this.m.n(), var20, this.m.p());
                  if (this.a(var21, var14) == -1) {
                     this.j.add(new WorldGenBigTree.FoliageCoordinates(var14, var21.o()));
                  }
               }
            }
         }
      }
   }

   void a(BlockPos var1, float var2, IBlockState var3) {
      int var4 = (int)(var2 + 0.618);

      for (int var5 = -var4; var5 <= var4; var5++) {
         for (int var6 = -var4; var6 <= var4; var6++) {
            if (Math.pow(Math.abs(var5) + 0.5, 2.0) + Math.pow(Math.abs(var6) + 0.5, 2.0) <= var2 * var2) {
               BlockPos var7 = var1.a(var5, 0, var6);
               Material var8 = this.l.p(var7).c().t();
               if (var8 == Material.a || var8 == Material.j) {
                  this.a(this.l, var7, var3);
               }
            }
         }
      }
   }

   float a(int var1) {
      if (var1 < this.a * 0.3F) {
         return -1.0F;
      } else {
         float var2 = this.a / 2.0F;
         float var3 = var2 - var1;
         float var4 = MathHelper.c(var2 * var2 - var3 * var3);
         if (var3 == 0.0F) {
            var4 = var2;
         } else if (Math.abs(var3) >= var2) {
            return 0.0F;
         }

         return var4 * 0.5F;
      }
   }

   float b(int var1) {
      if (var1 < 0 || var1 >= this.i) {
         return -1.0F;
      } else {
         return var1 != 0 && var1 != this.i - 1 ? 3.0F : 2.0F;
      }
   }

   void a(BlockPos var1) {
      for (int var2 = 0; var2 < this.i; var2++) {
         this.a(var1.b(var2), this.b(var2), Blocks.t.Q().a(BlockLeaves.b, false));
      }
   }

   void a(BlockPos var1, BlockPos var2, Block var3) {
      BlockPos var4 = var2.a(-var1.n(), -var1.o(), -var1.p());
      int var5 = this.b(var4);
      float var6 = (float)var4.n() / var5;
      float var7 = (float)var4.o() / var5;
      float var8 = (float)var4.p() / var5;

      for (int var9 = 0; var9 <= var5; var9++) {
         BlockPos var10 = var1.a((double)(0.5F + var9 * var6), (double)(0.5F + var9 * var7), (double)(0.5F + var9 * var8));
         BlockLog.EnumAxis var11 = this.b(var1, var10);
         this.a(this.l, var10, var3.Q().a(BlockLog.a, var11));
      }
   }

   private int b(BlockPos var1) {
      int var2 = MathHelper.a(var1.n());
      int var3 = MathHelper.a(var1.o());
      int var4 = MathHelper.a(var1.p());
      if (var4 > var2 && var4 > var3) {
         return var4;
      } else {
         return var3 > var2 ? var3 : var2;
      }
   }

   private BlockLog.EnumAxis b(BlockPos var1, BlockPos var2) {
      BlockLog.EnumAxis var3 = BlockLog.EnumAxis.b;
      int var4 = Math.abs(var2.n() - var1.n());
      int var5 = Math.abs(var2.p() - var1.p());
      int var6 = Math.max(var4, var5);
      if (var6 > 0) {
         if (var4 == var6) {
            var3 = BlockLog.EnumAxis.a;
         } else if (var5 == var6) {
            var3 = BlockLog.EnumAxis.c;
         }
      }

      return var3;
   }

   void b() {
      for (WorldGenBigTree.FoliageCoordinates var2 : this.j) {
         this.a(var2);
      }
   }

   boolean c(int var1) {
      return var1 >= this.a * 0.2;
   }

   void c() {
      BlockPos var1 = this.m;
      BlockPos var2 = this.m.b(this.b);
      Block var3 = Blocks.r;
      this.a(var1, var2, var3);
      if (this.g == 2) {
         this.a(var1.f(), var2.f(), var3);
         this.a(var1.f().d(), var2.f().d(), var3);
         this.a(var1.d(), var2.d(), var3);
      }
   }

   void d() {
      for (WorldGenBigTree.FoliageCoordinates var2 : this.j) {
         int var3 = var2.q();
         BlockPos var4 = new BlockPos(this.m.n(), var3, this.m.p());
         if (!var4.equals(var2) && this.c(var3 - this.m.o())) {
            this.a(var4, var2, Blocks.r);
         }
      }
   }

   int a(BlockPos var1, BlockPos var2) {
      BlockPos var3 = var2.a(-var1.n(), -var1.o(), -var1.p());
      int var4 = this.b(var3);
      float var5 = (float)var3.n() / var4;
      float var6 = (float)var3.o() / var4;
      float var7 = (float)var3.p() / var4;
      if (var4 == 0) {
         return -1;
      } else {
         for (int var8 = 0; var8 <= var4; var8++) {
            BlockPos var9 = var1.a((double)(0.5F + var8 * var5), (double)(0.5F + var8 * var6), (double)(0.5F + var8 * var7));
            if (!this.a(this.l.p(var9).c())) {
               return var8;
            }
         }

         return -1;
      }
   }

   @Override
   public void e() {
      this.i = 5;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      this.l = var1;
      this.m = var3;
      this.k = new Random(var2.nextLong());
      if (this.a == 0) {
         this.a = 5 + this.k.nextInt(this.h);
      }

      if (!this.f()) {
         return false;
      } else {
         this.a();
         this.b();
         this.c();
         this.d();
         return true;
      }
   }

   private boolean f() {
      Block var1 = this.l.p(this.m.b()).c();
      if (var1 != Blocks.d && var1 != Blocks.c && var1 != Blocks.ak) {
         return false;
      } else {
         int var2 = this.a(this.m, this.m.b(this.a - 1));
         if (var2 == -1) {
            return true;
         } else if (var2 < 6) {
            return false;
         } else {
            this.a = var2;
            return true;
         }
      }
   }

   static class FoliageCoordinates extends BlockPos {
      private final int c;

      public FoliageCoordinates(BlockPos var1, int var2) {
         super(var1.n(), var1.o(), var1.p());
         this.c = var2;
      }

      public int q() {
         return this.c;
      }
   }
}
