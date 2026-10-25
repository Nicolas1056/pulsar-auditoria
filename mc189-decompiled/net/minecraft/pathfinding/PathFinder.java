package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.pathfinder.NodeProcessor;

public class PathFinder {
   private Path a = new Path();
   private PathPoint[] b = new PathPoint[32];
   private NodeProcessor c;

   public PathFinder(NodeProcessor var1) {
      this.c = var1;
   }

   public PathEntity a(IBlockAccess var1, Entity var2, Entity var3, float var4) {
      return this.a(var1, var2, var3.s, var3.aR().b, var3.u, var4);
   }

   public PathEntity a(IBlockAccess var1, Entity var2, BlockPos var3, float var4) {
      return this.a(var1, var2, var3.n() + 0.5F, var3.o() + 0.5F, var3.p() + 0.5F, var4);
   }

   private PathEntity a(IBlockAccess var1, Entity var2, double var3, double var5, double var7, float var9) {
      this.a.a();
      this.c.a(var1, var2);
      PathPoint var10 = this.c.a(var2);
      PathPoint var11 = this.c.a(var2, var3, var5, var7);
      PathEntity var12 = this.a(var2, var10, var11, var9);
      this.c.a();
      return var12;
   }

   private PathEntity a(Entity var1, PathPoint var2, PathPoint var3, float var4) {
      var2.e = 0.0F;
      var2.f = var2.b(var3);
      var2.g = var2.f;
      this.a.a();
      this.a.a(var2);
      PathPoint var5 = var2;

      while (!this.a.e()) {
         PathPoint var6 = this.a.c();
         if (var6.equals(var3)) {
            return this.a(var2, var3);
         }

         if (var6.b(var3) < var5.b(var3)) {
            var5 = var6;
         }

         var6.i = true;
         int var7 = this.c.a(this.b, var1, var6, var3, var4);

         for (int var8 = 0; var8 < var7; var8++) {
            PathPoint var9 = this.b[var8];
            float var10 = var6.e + var6.b(var9);
            if (var10 < var4 * 2.0F && (!var9.a() || var10 < var9.e)) {
               var9.h = var6;
               var9.e = var10;
               var9.f = var9.b(var3);
               if (var9.a()) {
                  this.a.a(var9, var9.e + var9.f);
               } else {
                  var9.g = var9.e + var9.f;
                  this.a.a(var9);
               }
            }
         }
      }

      return var5 == var2 ? null : this.a(var2, var5);
   }

   private PathEntity a(PathPoint var1, PathPoint var2) {
      int var3 = 1;

      for (PathPoint var4 = var2; var4.h != null; var4 = var4.h) {
         var3++;
      }

      PathPoint[] var5 = new PathPoint[var3];
      PathPoint var7 = var2;

      for (var5[--var3] = var2; var7.h != null; var5[--var3] = var7) {
         var7 = var7.h;
      }

      return new PathEntity(var5);
   }
}
