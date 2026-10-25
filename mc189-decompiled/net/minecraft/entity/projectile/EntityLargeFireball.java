package net.minecraft.entity.projectile;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityLargeFireball extends EntityFireball {
   public int e = 1;

   public EntityLargeFireball(World var1) {
      super(var1);
   }

   public EntityLargeFireball(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, var8, var10, var12);
   }

   public EntityLargeFireball(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(var1, var2, var3, var5, var7);
   }

   @Override
   protected void a(MovingObjectPosition var1) {
      if (!this.o.D) {
         if (var1.d != null) {
            var1.d.a(DamageSource.a((EntityFireball)this, this.a), 6.0F);
            this.a(this.a, var1.d);
         }

         boolean var2 = this.o.Q().b("mobGriefing");
         this.o.a(null, this.s, this.t, this.u, this.e, var2, var2);
         this.J();
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      super.b(var1);
      var1.a("ExplosionPower", this.e);
   }

   @Override
   public void a(NBTTagCompound var1) {
      super.a(var1);
      if (var1.b("ExplosionPower", 99)) {
         this.e = var1.f("ExplosionPower");
      }
   }
}
