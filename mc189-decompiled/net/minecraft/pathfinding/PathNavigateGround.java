package net.minecraft.pathfinding;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.WalkNodeProcessor;

public class PathNavigateGround extends PathNavigate {
   protected WalkNodeProcessor a;
   private boolean f;

   public PathNavigateGround(EntityLiving var1, World var2) {
      super(var1, var2);
   }

   @Override
   protected PathFinder a() {
      this.a = new WalkNodeProcessor();
      this.a.a(true);
      return new PathFinder(this.a);
   }

   @Override
   protected boolean b() {
      return this.b.C || this.h() && this.o() || this.b.au() && this.b instanceof EntityZombie && this.b.m instanceof EntityChicken;
   }

   @Override
   protected Vec3 c() {
      return new Vec3(this.b.s, this.p(), this.b.u);
   }

   private int p() {
      if (this.b.V() && this.h()) {
         int var1 = (int)this.b.aR().b;
         Block var2 = this.c.p(new BlockPos(MathHelper.c(this.b.s), var1, MathHelper.c(this.b.u))).c();
         int var3 = 0;

         while (var2 == Blocks.i || var2 == Blocks.j) {
            var2 = this.c.p(new BlockPos(MathHelper.c(this.b.s), ++var1, MathHelper.c(this.b.u))).c();
            if (++var3 > 16) {
               return (int)this.b.aR().b;
            }
         }

         return var1;
      } else {
         return (int)(this.b.aR().b + 0.5);
      }
   }

   @Override
   protected void d() {
      super.d();
      if (this.f) {
         if (this.c.i(new BlockPos(MathHelper.c(this.b.s), (int)(this.b.aR().b + 0.5), MathHelper.c(this.b.u)))) {
            return;
         }

         for (int var1 = 0; var1 < this.d.d(); var1++) {
            PathPoint var2 = this.d.a(var1);
            if (this.c.i(new BlockPos(var2.a, var2.b, var2.c))) {
               this.d.b(var1 - 1);
               return;
            }
         }
      }
   }

   @Override
   protected boolean a(Vec3 var1, Vec3 var2, int var3, int var4, int var5) {
      int var6 = MathHelper.c(var1.a);
      int var7 = MathHelper.c(var1.c);
      double var8 = var2.a - var1.a;
      double var10 = var2.c - var1.c;
      double var12 = var8 * var8 + var10 * var10;
      if (var12 < 1.0E-8) {
         return false;
      } else {
         double var14 = 1.0 / Math.sqrt(var12);
         var8 *= var14;
         var10 *= var14;
         var3 += 2;
         var5 += 2;
         if (!this.a(var6, (int)var1.b, var7, var3, var4, var5, var1, var8, var10)) {
            return false;
         } else {
            var3 -= 2;
            var5 -= 2;
            double var16 = 1.0 / Math.abs(var8);
            double var18 = 1.0 / Math.abs(var10);
            double var20 = var6 * 1 - var1.a;
            double var22 = var7 * 1 - var1.c;
            if (var8 >= 0.0) {
               var20++;
            }

            if (var10 >= 0.0) {
               var22++;
            }

            var20 /= var8;
            var22 /= var10;
            int var24 = var8 < 0.0 ? -1 : 1;
            int var25 = var10 < 0.0 ? -1 : 1;
            int var26 = MathHelper.c(var2.a);
            int var27 = MathHelper.c(var2.c);
            int var28 = var26 - var6;
            int var29 = var27 - var7;

            while (var28 * var24 > 0 || var29 * var25 > 0) {
               if (var20 < var22) {
                  var20 += var16;
                  var6 += var24;
                  var28 = var26 - var6;
               } else {
                  var22 += var18;
                  var7 += var25;
                  var29 = var27 - var7;
               }

               if (!this.a(var6, (int)var1.b, var7, var3, var4, var5, var1, var8, var10)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private boolean a(int var1, int var2, int var3, int var4, int var5, int var6, Vec3 var7, double var8, double var10) {
      int var12 = var1 - var4 / 2;
      int var13 = var3 - var6 / 2;
      if (!this.b(var12, var2, var13, var4, var5, var6, var7, var8, var10)) {
         return false;
      } else {
         for (int var14 = var12; var14 < var12 + var4; var14++) {
            for (int var15 = var13; var15 < var13 + var6; var15++) {
               double var16 = var14 + 0.5 - var7.a;
               double var18 = var15 + 0.5 - var7.c;
               if (!(var16 * var8 + var18 * var10 < 0.0)) {
                  Block var20 = this.c.p(new BlockPos(var14, var2 - 1, var15)).c();
                  Material var21 = var20.t();
                  if (var21 == Material.a) {
                     return false;
                  }

                  if (var21 == Material.h && !this.b.V()) {
                     return false;
                  }

                  if (var21 == Material.i) {
                     return false;
                  }
               }
            }
         }

         return true;
      }
   }

   private boolean b(int var1, int var2, int var3, int var4, int var5, int var6, Vec3 var7, double var8, double var10) {
      for (BlockPos var13 : BlockPos.a(new BlockPos(var1, var2, var3), new BlockPos(var1 + var4 - 1, var2 + var5 - 1, var3 + var6 - 1))) {
         double var14 = var13.n() + 0.5 - var7.a;
         double var16 = var13.p() + 0.5 - var7.c;
         if (!(var14 * var8 + var16 * var10 < 0.0)) {
            Block var18 = this.c.p(var13).c();
            if (!var18.b((IBlockAccess)this.c, var13)) {
               return false;
            }
         }
      }

      return true;
   }

   public void a(boolean var1) {
      this.a.c(var1);
   }

   public boolean e() {
      return this.a.e();
   }

   public void b(boolean var1) {
      this.a.b(var1);
   }

   public void c(boolean var1) {
      this.a.a(var1);
   }

   public boolean g() {
      return this.a.b();
   }

   public void d(boolean var1) {
      this.a.d(var1);
   }

   public boolean h() {
      return this.a.d();
   }

   public void e(boolean var1) {
      this.f = var1;
   }
}
