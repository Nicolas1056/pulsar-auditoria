package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;

public class EntityAILookIdle extends EntityAIBase {
   private EntityLiving a;
   private double b;
   private double c;
   private int d;

   public EntityAILookIdle(EntityLiving var1) {
      this.a = var1;
      this.a(3);
   }

   @Override
   public boolean a() {
      return this.a.bc().nextFloat() < 0.02F;
   }

   @Override
   public boolean b() {
      return this.d >= 0;
   }

   @Override
   public void c() {
      double var1 = (Math.PI * 2) * this.a.bc().nextDouble();
      this.b = Math.cos(var1);
      this.c = Math.sin(var1);
      this.d = 20 + this.a.bc().nextInt(20);
   }

   @Override
   public void e() {
      this.d--;
      this.a.p().a(this.a.s + this.b, this.a.t + this.a.aS(), this.a.u + this.c, 10.0F, this.a.bQ());
   }
}
