package net.minecraft.entity.ai;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.util.MathHelper;

public class EntityAIArrowAttack extends EntityAIBase {
   private final EntityLiving a;
   private final IRangedAttackMob b;
   private EntityLivingBase c;
   private int d = -1;
   private double e;
   private int f;
   private int g;
   private int h;
   private float i;
   private float j;

   public EntityAIArrowAttack(IRangedAttackMob var1, double var2, int var4, float var5) {
      this(var1, var2, var4, var4, var5);
   }

   public EntityAIArrowAttack(IRangedAttackMob var1, double var2, int var4, int var5, float var6) {
      if (!(var1 instanceof EntityLivingBase)) {
         throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
      } else {
         this.b = var1;
         this.a = (EntityLiving)var1;
         this.e = var2;
         this.g = var4;
         this.h = var5;
         this.i = var6;
         this.j = var6 * var6;
         this.a(3);
      }
   }

   @Override
   public boolean a() {
      EntityLivingBase var1 = this.a.u();
      if (var1 == null) {
         return false;
      } else {
         this.c = var1;
         return true;
      }
   }

   @Override
   public boolean b() {
      return this.a() || !this.a.s().m();
   }

   @Override
   public void d() {
      this.c = null;
      this.f = 0;
      this.d = -1;
   }

   @Override
   public void e() {
      double var1 = this.a.e(this.c.s, this.c.aR().b, this.c.u);
      boolean var3 = this.a.t().a(this.c);
      if (var3) {
         this.f++;
      } else {
         this.f = 0;
      }

      if (!(var1 > this.j) && this.f >= 20) {
         this.a.s().n();
      } else {
         this.a.s().a(this.c, this.e);
      }

      this.a.p().a(this.c, 30.0F, 30.0F);
      if (--this.d == 0) {
         if (var1 > this.j || !var3) {
            return;
         }

         float var4 = MathHelper.a(var1) / this.i;
         float var5 = MathHelper.a(var4, 0.1F, 1.0F);
         this.b.a(this.c, var5);
         this.d = MathHelper.d(var4 * (this.h - this.g) + this.g);
      } else if (this.d < 0) {
         float var6 = MathHelper.a(var1) / this.i;
         this.d = MathHelper.d(var6 * (this.h - this.g) + this.g);
      }
   }
}
