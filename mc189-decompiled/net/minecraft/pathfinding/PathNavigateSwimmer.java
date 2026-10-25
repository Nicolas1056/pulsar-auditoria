package net.minecraft.pathfinding;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraft.world.pathfinder.SwimNodeProcessor;

public class PathNavigateSwimmer extends PathNavigate {
   public PathNavigateSwimmer(EntityLiving var1, World var2) {
      super(var1, var2);
   }

   @Override
   protected PathFinder a() {
      return new PathFinder(new SwimNodeProcessor());
   }

   @Override
   protected boolean b() {
      return this.o();
   }

   @Override
   protected Vec3 c() {
      return new Vec3(this.b.s, this.b.t + this.b.K * 0.5, this.b.u);
   }

   @Override
   protected void l() {
      Vec3 var1 = this.c();
      float var2 = this.b.J * this.b.J;
      byte var3 = 6;
      if (var1.g(this.d.a(this.b, this.d.e())) < var2) {
         this.d.a();
      }

      for (int var4 = Math.min(this.d.e() + var3, this.d.d() - 1); var4 > this.d.e(); var4--) {
         Vec3 var5 = this.d.a(this.b, var4);
         if (!(var5.g(var1) > 36.0) && this.a(var1, var5, 0, 0, 0)) {
            this.d.c(var4);
            break;
         }
      }

      this.a(var1);
   }

   @Override
   protected void d() {
      super.d();
   }

   @Override
   protected boolean a(Vec3 var1, Vec3 var2, int var3, int var4, int var5) {
      MovingObjectPosition var6 = this.c.a(var1, new Vec3(var2.a, var2.b + this.b.K * 0.5, var2.c), false, true, false);
      return var6 == null || var6.a == MovingObjectPosition.MovingObjectType.a;
   }
}
