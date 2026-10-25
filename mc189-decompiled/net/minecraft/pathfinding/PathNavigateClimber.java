package net.minecraft.pathfinding;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class PathNavigateClimber extends PathNavigateGround {
   private BlockPos f;

   public PathNavigateClimber(EntityLiving var1, World var2) {
      super(var1, var2);
   }

   @Override
   public PathEntity a(BlockPos var1) {
      this.f = var1;
      return super.a(var1);
   }

   @Override
   public PathEntity a(Entity var1) {
      this.f = new BlockPos(var1);
      return super.a(var1);
   }

   @Override
   public boolean a(Entity var1, double var2) {
      PathEntity var4 = this.a(var1);
      if (var4 != null) {
         return this.a(var4, var2);
      } else {
         this.f = new BlockPos(var1);
         this.e = var2;
         return true;
      }
   }

   @Override
   public void k() {
      if (!this.m()) {
         super.k();
      } else {
         if (this.f != null) {
            double var1 = this.b.J * this.b.J;
            if (!(this.b.c(this.f) < var1) && (!(this.b.t > this.f.o()) || !(this.b.c(new BlockPos(this.f.n(), MathHelper.c(this.b.t), this.f.p())) < var1))) {
               this.b.q().a(this.f.n(), this.f.o(), this.f.p(), this.e);
            } else {
               this.f = null;
            }
         }
      }
   }
}
