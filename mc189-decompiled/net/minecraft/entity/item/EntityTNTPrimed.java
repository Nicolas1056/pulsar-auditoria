package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityTNTPrimed extends Entity {
   public int a;
   private EntityLivingBase b;

   public EntityTNTPrimed(World var1) {
      super(var1);
      this.k = true;
      this.a(0.98F, 0.98F);
   }

   public EntityTNTPrimed(World var1, double var2, double var4, double var6, EntityLivingBase var8) {
      this(var1);
      this.b(var2, var4, var6);
      float var9 = (float)(Math.random() * (float) Math.PI * 2.0);
      this.v = -((float)Math.sin(var9)) * 0.02F;
      this.w = 0.2F;
      this.x = -((float)Math.cos(var9)) * 0.02F;
      this.a = 80;
      this.p = var2;
      this.q = var4;
      this.r = var6;
      this.b = var8;
   }

   @Override
   protected void h() {
   }

   @Override
   protected boolean s_() {
      return false;
   }

   @Override
   public boolean ad() {
      return !this.I;
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      this.w -= 0.04F;
      this.d(this.v, this.w, this.x);
      this.v *= 0.98F;
      this.w *= 0.98F;
      this.x *= 0.98F;
      if (this.C) {
         this.v *= 0.7F;
         this.x *= 0.7F;
         this.w *= -0.5;
      }

      if (this.a-- <= 0) {
         this.J();
         if (!this.o.D) {
            this.l();
         }
      } else {
         this.W();
         this.o.a(EnumParticleTypes.l, this.s, this.t + 0.5, this.u, 0.0, 0.0, 0.0);
      }
   }

   private void l() {
      float var1 = 4.0F;
      this.o.a(this, this.s, this.t + this.K / 16.0F, this.u, var1, true);
   }

   @Override
   protected void b(NBTTagCompound var1) {
      var1.a("Fuse", (byte)this.a);
   }

   @Override
   protected void a(NBTTagCompound var1) {
      this.a = var1.d("Fuse");
   }

   public EntityLivingBase j() {
      return this.b;
   }

   @Override
   public float aS() {
      return 0.0F;
   }
}
