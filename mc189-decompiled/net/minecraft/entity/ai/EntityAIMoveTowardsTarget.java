package net.minecraft.entity.ai;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.Vec3;

public class EntityAIMoveTowardsTarget extends EntityAIBase {
   private EntityCreature a;
   private EntityLivingBase b;
   private double c;
   private double d;
   private double e;
   private double f;
   private float g;

   public EntityAIMoveTowardsTarget(EntityCreature var1, double var2, float var4) {
      this.a = var1;
      this.f = var2;
      this.g = var4;
      this.a(1);
   }

   @Override
   public boolean a() {
      this.b = this.a.u();
      if (this.b == null) {
         return false;
      } else if (this.b.h(this.a) > this.g * this.g) {
         return false;
      } else {
         Vec3 var1 = RandomPositionGenerator.a(this.a, 16, 7, new Vec3(this.b.s, this.b.t, this.b.u));
         if (var1 == null) {
            return false;
         } else {
            this.c = var1.a;
            this.d = var1.b;
            this.e = var1.c;
            return true;
         }
      }
   }

   @Override
   public boolean b() {
      return !this.a.s().m() && this.b.ai() && this.b.h(this.a) < this.g * this.g;
   }

   @Override
   public void d() {
      this.b = null;
   }

   @Override
   public void c() {
      this.a.s().a(this.c, this.d, this.e, this.f);
   }
}
