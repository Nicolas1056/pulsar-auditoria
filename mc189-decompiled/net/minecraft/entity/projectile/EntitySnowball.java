package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntitySnowball extends EntityThrowable {
   public EntitySnowball(World var1) {
      super(var1);
   }

   public EntitySnowball(World var1, EntityLivingBase var2) {
      super(var1, var2);
   }

   public EntitySnowball(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
   }

   @Override
   protected void a(MovingObjectPosition var1) {
      if (var1.d != null) {
         byte var2 = 0;
         if (var1.d instanceof EntityBlaze) {
            var2 = 3;
         }

         var1.d.a(DamageSource.a(this, this.n()), (float)var2);
      }

      for (int var3 = 0; var3 < 8; var3++) {
         this.o.a(EnumParticleTypes.F, this.s, this.t, this.u, 0.0, 0.0, 0.0);
      }

      if (!this.o.D) {
         this.J();
      }
   }
}
