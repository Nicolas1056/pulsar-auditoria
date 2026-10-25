package net.minecraft.entity.ai;

import java.util.List;
import java.util.Random;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityAIMate extends EntityAIBase {
   private EntityAnimal d;
   World a;
   private EntityAnimal e;
   int b;
   double c;

   public EntityAIMate(EntityAnimal var1, double var2) {
      this.d = var1;
      this.a = var1.o;
      this.c = var2;
      this.a(3);
   }

   @Override
   public boolean a() {
      if (!this.d.cr()) {
         return false;
      } else {
         this.e = this.f();
         return this.e != null;
      }
   }

   @Override
   public boolean b() {
      return this.e.ai() && this.e.cr() && this.b < 60;
   }

   @Override
   public void d() {
      this.e = null;
      this.b = 0;
   }

   @Override
   public void e() {
      this.d.p().a(this.e, 10.0F, this.d.bQ());
      this.d.s().a(this.e, this.c);
      this.b++;
      if (this.b >= 60 && this.d.h(this.e) < 9.0) {
         this.g();
      }
   }

   private EntityAnimal f() {
      float var1 = 8.0F;
      List var2 = this.a.a(this.d.getClass(), this.d.aR().b(var1, var1, var1));
      double var3 = Double.MAX_VALUE;
      EntityAnimal var5 = null;

      for (EntityAnimal var7 : var2) {
         if (this.d.a(var7) && this.d.h(var7) < var3) {
            var5 = var7;
            var3 = this.d.h(var7);
         }
      }

      return var5;
   }

   private void g() {
      EntityAgeable var1 = this.d.a((EntityAgeable)this.e);
      if (var1 != null) {
         EntityPlayer var2 = this.d.cq();
         if (var2 == null && this.e.cq() != null) {
            var2 = this.e.cq();
         }

         if (var2 != null) {
            var2.b(StatList.A);
            if (this.d instanceof EntityCow) {
               var2.b(AchievementList.H);
            }
         }

         this.d.b(6000);
         this.e.b(6000);
         this.d.cs();
         this.e.cs();
         var1.b(-24000);
         var1.b(this.d.s, this.d.t, this.d.u, 0.0F, 0.0F);
         this.a.d(var1);
         Random var3 = this.d.bc();

         for (int var4 = 0; var4 < 7; var4++) {
            double var5 = var3.nextGaussian() * 0.02;
            double var7 = var3.nextGaussian() * 0.02;
            double var9 = var3.nextGaussian() * 0.02;
            double var11 = var3.nextDouble() * this.d.J * 2.0 - this.d.J;
            double var13 = 0.5 + var3.nextDouble() * this.d.K;
            double var15 = var3.nextDouble() * this.d.J * 2.0 - this.d.J;
            this.a.a(EnumParticleTypes.I, this.d.s + var11, this.d.t + var13, this.d.u + var15, var5, var7, var9);
         }

         if (this.a.Q().b("doMobLoot")) {
            this.a.d(new EntityXPOrb(this.a, this.d.s, this.d.t, this.d.u, var3.nextInt(7) + 1));
         }
      }
   }
}
