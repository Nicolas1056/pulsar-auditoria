package net.minecraft.block;

import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDynamicLiquid extends BlockLiquid {
   int a;

   protected BlockDynamicLiquid(Material var1) {
      super(var1);
   }

   private void f(World var1, BlockPos var2, IBlockState var3) {
      var1.a(var2, b(this.J).Q().a(b, var3.b(b)), 2);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = var3.b(b);
      byte var6 = 1;
      if (this.J == Material.i && !var1.t.n()) {
         var6 = 2;
      }

      int var7 = this.a(var1);
      if (var5 > 0) {
         int var8 = -100;
         this.a = 0;

         for (EnumFacing var10 : EnumFacing.Plane.a) {
            var8 = this.a(var1, var2.a(var10), var8);
         }

         int var15 = var8 + var6;
         if (var15 >= 8 || var8 < 0) {
            var15 = -1;
         }

         if (this.e((IBlockAccess)var1, var2.a()) >= 0) {
            int var17 = this.e((IBlockAccess)var1, var2.a());
            if (var17 >= 8) {
               var15 = var17;
            } else {
               var15 = var17 + 8;
            }
         }

         if (this.a >= 2 && this.J == Material.h) {
            IBlockState var18 = var1.p(var2.b());
            if (var18.c().t().a()) {
               var15 = 0;
            } else if (var18.c().t() == this.J && var18.b(b) == 0) {
               var15 = 0;
            }
         }

         if (this.J == Material.i && var5 < 8 && var15 < 8 && var15 > var5 && var4.nextInt(4) != 0) {
            var7 *= 4;
         }

         if (var15 == var5) {
            this.f(var1, var2, var3);
         } else {
            var5 = var15;
            if (var15 < 0) {
               var1.g(var2);
            } else {
               var3 = var3.a(b, var15);
               var1.a(var2, var3, 2);
               var1.a(var2, this, var7);
               var1.c(var2, this);
            }
         }
      } else {
         this.f(var1, var2, var3);
      }

      IBlockState var14 = var1.p(var2.b());
      if (this.h(var1, var2.b(), var14)) {
         if (this.J == Material.i && var1.p(var2.b()).c().t() == Material.h) {
            var1.a(var2.b(), Blocks.b.Q());
            this.e(var1, var2.b());
            return;
         }

         if (var5 >= 8) {
            this.a(var1, var2.b(), var14, var5);
         } else {
            this.a(var1, var2.b(), var14, var5 + 8);
         }
      } else if (var5 >= 0 && (var5 == 0 || this.g(var1, var2.b(), var14))) {
         Set var16 = this.f(var1, var2);
         int var19 = var5 + var6;
         if (var5 >= 8) {
            var19 = 1;
         }

         if (var19 >= 8) {
            return;
         }

         for (EnumFacing var12 : var16) {
            this.a(var1, var2.a(var12), var1.p(var2.a(var12)), var19);
         }
      }
   }

   private void a(World var1, BlockPos var2, IBlockState var3, int var4) {
      if (this.h(var1, var2, var3)) {
         if (var3.c() != Blocks.a) {
            if (this.J == Material.i) {
               this.e(var1, var2);
            } else {
               var3.c().b(var1, var2, var3, 0);
            }
         }

         var1.a(var2, this.Q().a(b, var4), 3);
      }
   }

   private int a(World var1, BlockPos var2, int var3, EnumFacing var4) {
      int var5 = 1000;

      for (EnumFacing var7 : EnumFacing.Plane.a) {
         if (var7 != var4) {
            BlockPos var8 = var2.a(var7);
            IBlockState var9 = var1.p(var8);
            if (!this.g(var1, var8, var9) && (var9.c().t() != this.J || var9.b(b) > 0)) {
               if (!this.g(var1, var8.b(), var9)) {
                  return var3;
               }

               if (var3 < 4) {
                  int var10 = this.a(var1, var8, var3 + 1, var7.d());
                  if (var10 < var5) {
                     var5 = var10;
                  }
               }
            }
         }
      }

      return var5;
   }

   private Set<EnumFacing> f(World var1, BlockPos var2) {
      int var3 = 1000;
      EnumSet var4 = EnumSet.noneOf(EnumFacing.class);

      for (EnumFacing var6 : EnumFacing.Plane.a) {
         BlockPos var7 = var2.a(var6);
         IBlockState var8 = var1.p(var7);
         if (!this.g(var1, var7, var8) && (var8.c().t() != this.J || var8.b(b) > 0)) {
            int var9;
            if (this.g(var1, var7.b(), var1.p(var7.b()))) {
               var9 = this.a(var1, var7, 1, var6.d());
            } else {
               var9 = 0;
            }

            if (var9 < var3) {
               var4.clear();
            }

            if (var9 <= var3) {
               var4.add(var6);
               var3 = var9;
            }
         }
      }

      return var4;
   }

   private boolean g(World var1, BlockPos var2, IBlockState var3) {
      Block var4 = var1.p(var2).c();
      if (var4 instanceof BlockDoor || var4 == Blocks.an || var4 == Blocks.au || var4 == Blocks.aM) {
         return true;
      } else {
         return var4.J == Material.E ? true : var4.J.c();
      }
   }

   protected int a(World var1, BlockPos var2, int var3) {
      int var4 = this.e((IBlockAccess)var1, var2);
      if (var4 < 0) {
         return var3;
      } else {
         if (var4 == 0) {
            this.a++;
         }

         if (var4 >= 8) {
            var4 = 0;
         }

         return var3 >= 0 && var4 >= var3 ? var3 : var4;
      }
   }

   private boolean h(World var1, BlockPos var2, IBlockState var3) {
      Material var4 = var3.c().t();
      return var4 != this.J && var4 != Material.i && !this.g(var1, var2, var3);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      if (!this.e(var1, var2, var3)) {
         var1.a(var2, this, this.a(var1));
      }
   }
}
