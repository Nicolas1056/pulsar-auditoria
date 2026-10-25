package net.minecraft.world.pathfinder;

import net.minecraft.entity.Entity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;

public abstract class NodeProcessor {
   protected IBlockAccess a;
   protected IntHashMap<PathPoint> b = new IntHashMap<>();
   protected int c;
   protected int d;
   protected int e;

   public void a(IBlockAccess var1, Entity var2) {
      this.a = var1;
      this.b.c();
      this.c = MathHelper.d(var2.J + 1.0F);
      this.d = MathHelper.d(var2.K + 1.0F);
      this.e = MathHelper.d(var2.J + 1.0F);
   }

   public void a() {
   }

   protected PathPoint a(int var1, int var2, int var3) {
      int var4 = PathPoint.a(var1, var2, var3);
      PathPoint var5 = this.b.a(var4);
      if (var5 == null) {
         var5 = new PathPoint(var1, var2, var3);
         this.b.a(var4, var5);
      }

      return var5;
   }

   public abstract PathPoint a(Entity var1);

   public abstract PathPoint a(Entity var1, double var2, double var4, double var6);

   public abstract int a(PathPoint[] var1, Entity var2, PathPoint var3, PathPoint var4, float var5);
}
