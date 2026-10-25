package net.minecraft.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAIWatchClosest extends EntityAIBase {
   protected EntityLiving a;
   protected Entity b;
   protected float c;
   private int e;
   private float f;
   protected Class<? extends Entity> d;

   public EntityAIWatchClosest(EntityLiving var1, Class<? extends Entity> var2, float var3) {
      this.a = var1;
      this.d = var2;
      this.c = var3;
      this.f = 0.02F;
      this.a(2);
   }

   public EntityAIWatchClosest(EntityLiving var1, Class<? extends Entity> var2, float var3, float var4) {
      this.a = var1;
      this.d = var2;
      this.c = var3;
      this.f = var4;
      this.a(2);
   }

   @Override
   public boolean a() {
      if (this.a.bc().nextFloat() >= this.f) {
         return false;
      } else {
         if (this.a.u() != null) {
            this.b = this.a.u();
         }

         if (this.d == EntityPlayer.class) {
            this.b = this.a.o.a(this.a, (double)this.c);
         } else {
            this.b = this.a.o.a(this.d, this.a.aR().b(this.c, 3.0, this.c), this.a);
         }

         return this.b != null;
      }
   }

   @Override
   public boolean b() {
      if (!this.b.ai()) {
         return false;
      } else {
         return this.a.h(this.b) > this.c * this.c ? false : this.e > 0;
      }
   }

   @Override
   public void c() {
      this.e = 40 + this.a.bc().nextInt(40);
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void e() {
      this.a.p().a(this.b.s, this.b.t + this.b.aS(), this.b.u, 10.0F, this.a.bQ());
      this.e--;
   }
}
