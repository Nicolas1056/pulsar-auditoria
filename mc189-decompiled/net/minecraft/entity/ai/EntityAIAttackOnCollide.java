package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class EntityAIAttackOnCollide extends EntityAIBase {
   World a;
   protected EntityCreature b;
   int c;
   double d;
   boolean e;
   PathEntity f;
   Class<? extends Entity> g;
   private int h;
   private double i;
   private double j;
   private double k;

   public EntityAIAttackOnCollide(EntityCreature var1, Class<? extends Entity> var2, double var3, boolean var5) {
      this(var1, var3, var5);
      this.g = var2;
   }

   public EntityAIAttackOnCollide(EntityCreature var1, double var2, boolean var4) {
      this.b = var1;
      this.a = var1.o;
      this.d = var2;
      this.e = var4;
      this.a(3);
   }

   @Override
   public boolean a() {
      EntityLivingBase var1 = this.b.u();
      if (var1 == null) {
         return false;
      } else if (!var1.ai()) {
         return false;
      } else if (this.g != null && !this.g.isAssignableFrom(var1.getClass())) {
         return false;
      } else {
         this.f = this.b.s().a(var1);
         return this.f != null;
      }
   }

   @Override
   public boolean b() {
      EntityLivingBase var1 = this.b.u();
      if (var1 == null) {
         return false;
      } else if (!var1.ai()) {
         return false;
      } else {
         return !this.e ? !this.b.s().m() : this.b.e(new BlockPos(var1));
      }
   }

   @Override
   public void c() {
      this.b.s().a(this.f, this.d);
      this.h = 0;
   }

   @Override
   public void d() {
      this.b.s().n();
   }

   @Override
   public void e() {
      EntityLivingBase var1 = this.b.u();
      this.b.p().a(var1, 30.0F, 30.0F);
      double var2 = this.b.e(var1.s, var1.aR().b, var1.u);
      double var4 = this.a(var1);
      this.h--;
      if ((this.e || this.b.t().a(var1))
         && this.h <= 0
         && (this.i == 0.0 && this.j == 0.0 && this.k == 0.0 || var1.e(this.i, this.j, this.k) >= 1.0 || this.b.bc().nextFloat() < 0.05F)) {
         this.i = var1.s;
         this.j = var1.aR().b;
         this.k = var1.u;
         this.h = 4 + this.b.bc().nextInt(7);
         if (var2 > 1024.0) {
            this.h += 10;
         } else if (var2 > 256.0) {
            this.h += 5;
         }

         if (!this.b.s().a(var1, this.d)) {
            this.h += 15;
         }
      }

      this.c = Math.max(this.c - 1, 0);
      if (var2 <= var4 && this.c <= 0) {
         this.c = 20;
         if (this.b.bA() != null) {
            this.b.bw();
         }

         this.b.r(var1);
      }
   }

   protected double a(EntityLivingBase var1) {
      return this.b.J * 2.0F * (this.b.J * 2.0F) + var1.J;
   }
}
