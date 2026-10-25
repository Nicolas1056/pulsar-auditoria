package net.minecraft.world.pathfinder;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockRailBase;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public class WalkNodeProcessor extends NodeProcessor {
   private boolean f;
   private boolean g;
   private boolean h;
   private boolean i;
   private boolean j;

   @Override
   public void a(IBlockAccess var1, Entity var2) {
      super.a(var1, var2);
      this.j = this.h;
   }

   @Override
   public void a() {
      super.a();
      this.h = this.j;
   }

   @Override
   public PathPoint a(Entity var1) {
      int var2;
      if (this.i && var1.V()) {
         var2 = (int)var1.aR().b;
         BlockPos.MutableBlockPos var3 = new BlockPos.MutableBlockPos(MathHelper.c(var1.s), var2, MathHelper.c(var1.u));

         for (Block var4 = this.a.p(var3).c(); var4 == Blocks.i || var4 == Blocks.j; var4 = this.a.p(var3).c()) {
            var3.c(MathHelper.c(var1.s), ++var2, MathHelper.c(var1.u));
         }

         this.h = false;
      } else {
         var2 = MathHelper.c(var1.aR().b + 0.5);
      }

      return this.a(MathHelper.c(var1.aR().a), var2, MathHelper.c(var1.aR().c));
   }

   @Override
   public PathPoint a(Entity var1, double var2, double var4, double var6) {
      return this.a(MathHelper.c(var2 - var1.J / 2.0F), MathHelper.c(var4), MathHelper.c(var6 - var1.J / 2.0F));
   }

   @Override
   public int a(PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5) {
      int var6 = 0;
      byte var7 = 0;
      if (this.a(var2, var3.a, var3.b + 1, var3.c) == 1) {
         var7 = 1;
      }

      PathPoint var8 = this.a(var2, var3.a, var3.b, var3.c + 1, var7);
      PathPoint var9 = this.a(var2, var3.a - 1, var3.b, var3.c, var7);
      PathPoint var10 = this.a(var2, var3.a + 1, var3.b, var3.c, var7);
      PathPoint var11 = this.a(var2, var3.a, var3.b, var3.c - 1, var7);
      if (var8 != null && !var8.i && var8.a(var4) < var5) {
         var1[var6++] = var8;
      }

      if (var9 != null && !var9.i && var9.a(var4) < var5) {
         var1[var6++] = var9;
      }

      if (var10 != null && !var10.i && var10.a(var4) < var5) {
         var1[var6++] = var10;
      }

      if (var11 != null && !var11.i && var11.a(var4) < var5) {
         var1[var6++] = var11;
      }

      return var6;
   }

   private PathPoint a(Entity var1, int var2, int var3, int var4, int var5) {
      PathPoint var6 = null;
      int var7 = this.a(var1, var2, var3, var4);
      if (var7 == 2) {
         return this.a(var2, var3, var4);
      } else {
         if (var7 == 1) {
            var6 = this.a(var2, var3, var4);
         }

         if (var6 == null && var5 > 0 && var7 != -3 && var7 != -4 && this.a(var1, var2, var3 + var5, var4) == 1) {
            var6 = this.a(var2, var3 + var5, var4);
            var3 += var5;
         }

         if (var6 != null) {
            int var8 = 0;

            int var9;
            for (var9 = 0; var3 > 0; var6 = this.a(var2, var3, var4)) {
               var9 = this.a(var1, var2, var3 - 1, var4);
               if (this.h && var9 == -1) {
                  return null;
               }

               if (var9 != 1) {
                  break;
               }

               if (var8++ >= var1.aE()) {
                  return null;
               }

               if (--var3 <= 0) {
                  return null;
               }
            }

            if (var9 == -2) {
               return null;
            }
         }

         return var6;
      }
   }

   private int a(Entity var1, int var2, int var3, int var4) {
      return a(this.a, var1, var2, var3, var4, this.c, this.d, this.e, this.h, this.g, this.f);
   }

   public static int a(IBlockAccess var0, Entity var1, int var2, int var3, int var4, int var5, int var6, int var7, boolean var8, boolean var9, boolean var10) {
      boolean var11 = false;
      BlockPos var12 = new BlockPos(var1);
      BlockPos.MutableBlockPos var13 = new BlockPos.MutableBlockPos();

      for (int var14 = var2; var14 < var2 + var5; var14++) {
         for (int var15 = var3; var15 < var3 + var6; var15++) {
            for (int var16 = var4; var16 < var4 + var7; var16++) {
               var13.c(var14, var15, var16);
               Block var17 = var0.p(var13).c();
               if (var17.t() != Material.a) {
                  if (var17 == Blocks.bd || var17 == Blocks.cw) {
                     var11 = true;
                  } else if (var17 != Blocks.i && var17 != Blocks.j) {
                     if (!var10 && var17 instanceof BlockDoor && var17.t() == Material.d) {
                        return 0;
                     }
                  } else {
                     if (var8) {
                        return -1;
                     }

                     var11 = true;
                  }

                  if (var1.o.p(var13).c() instanceof BlockRailBase) {
                     if (!(var1.o.p(var12).c() instanceof BlockRailBase) && !(var1.o.p(var12.b()).c() instanceof BlockRailBase)) {
                        return -3;
                     }
                  } else if (!var17.b(var0, var13) && (!var9 || !(var17 instanceof BlockDoor) || var17.t() != Material.d)) {
                     if (var17 instanceof BlockFence || var17 instanceof BlockFenceGate || var17 instanceof BlockWall) {
                        return -3;
                     }

                     if (var17 == Blocks.bd || var17 == Blocks.cw) {
                        return -4;
                     }

                     Material var18 = var17.t();
                     if (var18 != Material.i) {
                        return 0;
                     }

                     if (!var1.ab()) {
                        return -2;
                     }
                  }
               }
            }
         }
      }

      return var11 ? 2 : 1;
   }

   public void a(boolean var1) {
      this.f = var1;
   }

   public void b(boolean var1) {
      this.g = var1;
   }

   public void c(boolean var1) {
      this.h = var1;
   }

   public void d(boolean var1) {
      this.i = var1;
   }

   public boolean b() {
      return this.f;
   }

   public boolean d() {
      return this.i;
   }

   public boolean e() {
      return this.h;
   }
}
