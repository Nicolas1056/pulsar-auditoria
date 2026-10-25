package net.minecraft.entity.item;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityEnderPearl extends EntityThrowable {
   private EntityLivingBase c;

   public EntityEnderPearl(World var1) {
      super(var1);
   }

   public EntityEnderPearl(World var1, EntityLivingBase var2) {
      super(var1, var2);
      this.c = var2;
   }

   public EntityEnderPearl(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   protected void a(MovingObjectPosition var1) {
      EntityLivingBase var2 = this.n();
      if (var1.d != null) {
         if (var1.d == this.c) {
            return;
         }

         var1.d.a(DamageSource.a(this, var2), 0.0F);
      }

      for (int var3 = 0; var3 < 32; var3++) {
         this.o.a(EnumParticleTypes.y, this.s, this.t + this.V.nextDouble() * 2.0, this.u, this.V.nextGaussian(), 0.0, this.V.nextGaussian());
      }

      if (!this.o.D) {
         if (var2 instanceof EntityPlayerMP) {
            EntityPlayerMP var5 = (EntityPlayerMP)var2;
            if (var5.a.a().g() && var5.o == this.o && !var5.bJ()) {
               if (this.V.nextFloat() < 0.05F && this.o.Q().b("doMobSpawning")) {
                  EntityEndermite var4 = new EntityEndermite(this.o);
                  var4.a(true);
                  var4.b(var2.s, var2.t, var2.u, var2.y, var2.z);
                  this.o.d(var4);
               }

               if (var2.au()) {
                  var2.a(null);
               }

               var2.a(this.s, this.t, this.u);
               var2.O = 0.0F;
               var2.a(DamageSource.i, 5.0F);
            }
         } else if (var2 != null) {
            var2.a(this.s, this.t, this.u);
            var2.O = 0.0F;
         }

         this.J();
      }
   }

   @Override
   public void t_() {
      EntityLivingBase var1 = this.n();
      if (var1 != null && var1 instanceof EntityPlayer && !var1.ai()) {
         this.J();
      } else {
         super.t_();
      }
   }
}
