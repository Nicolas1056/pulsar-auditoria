package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;

public class EntityAITempt extends EntityAIBase {
   private EntityCreature a;
   private double b;
   private double c;
   private double d;
   private double e;
   private double f;
   private double g;
   private EntityPlayer h;
   private int i;
   private boolean j;
   private Item k;
   private boolean l;
   private boolean m;

   public EntityAITempt(EntityCreature var1, double var2, Item var4, boolean var5) {
      this.a = var1;
      this.b = var2;
      this.k = var4;
      this.l = var5;
      this.a(3);
      if (!(var1.s() instanceof PathNavigateGround)) {
         throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
      }
   }

   @Override
   public boolean a() {
      if (this.i > 0) {
         this.i--;
         return false;
      } else {
         this.h = this.a.o.a(this.a, 10.0);
         if (this.h == null) {
            return false;
         } else {
            ItemStack var1 = this.h.bZ();
            return var1 == null ? false : var1.b() == this.k;
         }
      }
   }

   @Override
   public boolean b() {
      if (this.l) {
         if (this.a.h(this.h) < 36.0) {
            if (this.h.e(this.c, this.d, this.e) > 0.010000000000000002) {
               return false;
            }

            if (Math.abs(this.h.z - this.f) > 5.0 || Math.abs(this.h.y - this.g) > 5.0) {
               return false;
            }
         } else {
            this.c = this.h.s;
            this.d = this.h.t;
            this.e = this.h.u;
         }

         this.f = this.h.z;
         this.g = this.h.y;
      }

      return this.a();
   }

   @Override
   public void c() {
      this.c = this.h.s;
      this.d = this.h.t;
      this.e = this.h.u;
      this.j = true;
      this.m = ((PathNavigateGround)this.a.s()).e();
      ((PathNavigateGround)this.a.s()).a(false);
   }

   @Override
   public void d() {
      this.h = null;
      this.a.s().n();
      this.i = 100;
      this.j = false;
      ((PathNavigateGround)this.a.s()).a(this.m);
   }

   @Override
   public void e() {
      this.a.p().a(this.h, 30.0F, this.a.bQ());
      if (this.a.h(this.h) < 6.25) {
         this.a.s().n();
      } else {
         this.a.s().a(this.h, this.b);
      }
   }

   public boolean f() {
      return this.j;
   }
}
