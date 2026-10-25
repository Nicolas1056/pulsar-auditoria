package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.BlockPos;
import net.minecraft.village.Village;
import net.minecraft.world.World;

public class EntityAIVillagerMate extends EntityAIBase {
   private EntityVillager b;
   private EntityVillager c;
   private World d;
   private int e;
   Village a;

   public EntityAIVillagerMate(EntityVillager var1) {
      this.b = var1;
      this.d = var1.o;
      this.a(3);
   }

   @Override
   public boolean a() {
      if (this.b.l() != 0) {
         return false;
      } else if (this.b.bc().nextInt(500) != 0) {
         return false;
      } else {
         this.a = this.d.ae().a(new BlockPos(this.b), 0);
         if (this.a == null) {
            return false;
         } else if (this.f() && this.b.n(true)) {
            Entity var1 = this.d.a(EntityVillager.class, this.b.aR().b(8.0, 3.0, 8.0), this.b);
            if (var1 == null) {
               return false;
            } else {
               this.c = (EntityVillager)var1;
               return this.c.l() == 0 && this.c.n(true);
            }
         } else {
            return false;
         }
      }
   }

   @Override
   public void c() {
      this.e = 300;
      this.b.l(true);
   }

   @Override
   public void d() {
      this.a = null;
      this.c = null;
      this.b.l(false);
   }

   @Override
   public boolean b() {
      return this.e >= 0 && this.f() && this.b.l() == 0 && this.b.n(false);
   }

   @Override
   public void e() {
      this.e--;
      this.b.p().a(this.c, 10.0F, 30.0F);
      if (this.b.h(this.c) > 2.25) {
         this.b.s().a(this.c, 0.25);
      } else if (this.e == 0 && this.c.cm()) {
         this.g();
      }

      if (this.b.bc().nextInt(35) == 0) {
         this.d.a(this.b, (byte)12);
      }
   }

   private boolean f() {
      if (!this.a.i()) {
         return false;
      } else {
         int var1 = (int)(this.a.c() * 0.35);
         return this.a.e() < var1;
      }
   }

   private void g() {
      EntityVillager var1 = this.b.b((EntityAgeable)this.c);
      this.c.b(6000);
      this.b.b(6000);
      this.c.o(false);
      this.b.o(false);
      var1.b(-24000);
      var1.b(this.b.s, this.b.t, this.b.u, 0.0F, 0.0F);
      this.d.d(var1);
      this.d.a(var1, (byte)12);
   }
}
