package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkRocket extends Entity {
   private int a;
   private int b;

   public EntityFireworkRocket(World var1) {
      super(var1);
      this.a(0.25F, 0.25F);
   }

   @Override
   protected void h() {
      this.ac.a(8, 5);
   }

   @Override
   public boolean a(double var1) {
      return var1 < 4096.0;
   }

   public EntityFireworkRocket(World var1, double var2, double var4, double var6, ItemStack var8) {
      super(var1);
      this.a = 0;
      this.a(0.25F, 0.25F);
      this.b(var2, var4, var6);
      byte var9 = 1;
      if (var8 != null && var8.n()) {
         this.ac.b(8, var8);
         NBTTagCompound var10 = var8.o();
         NBTTagCompound var11 = var10.m("Fireworks");
         if (var11 != null) {
            var9 += var11.d("Flight");
         }
      }

      this.v = this.V.nextGaussian() * 0.001;
      this.x = this.V.nextGaussian() * 0.001;
      this.w = 0.05;
      this.b = 10 * var9 + this.V.nextInt(6) + this.V.nextInt(7);
   }

   @Override
   public void i(double var1, double var3, double var5) {
      this.v = var1;
      this.w = var3;
      this.x = var5;
      if (this.B == 0.0F && this.A == 0.0F) {
         float var7 = MathHelper.a(var1 * var1 + var5 * var5);
         this.A = this.y = (float)(MathHelper.b(var1, var5) * 180.0 / (float) Math.PI);
         this.B = this.z = (float)(MathHelper.b(var3, (double)var7) * 180.0 / (float) Math.PI);
      }
   }

   @Override
   public void t_() {
      this.P = this.s;
      this.Q = this.t;
      this.R = this.u;
      super.t_();
      this.v *= 1.15;
      this.x *= 1.15;
      this.w += 0.04;
      this.d(this.v, this.w, this.x);
      float var1 = MathHelper.a(this.v * this.v + this.x * this.x);
      this.y = (float)(MathHelper.b(this.v, this.x) * 180.0 / (float) Math.PI);
      this.z = (float)(MathHelper.b(this.w, (double)var1) * 180.0 / (float) Math.PI);

      while (this.z - this.B < -180.0F) {
         this.B -= 360.0F;
      }

      while (this.z - this.B >= 180.0F) {
         this.B += 360.0F;
      }

      while (this.y - this.A < -180.0F) {
         this.A -= 360.0F;
      }

      while (this.y - this.A >= 180.0F) {
         this.A += 360.0F;
      }

      this.z = this.B + (this.z - this.B) * 0.2F;
      this.y = this.A + (this.y - this.A) * 0.2F;
      if (this.a == 0 && !this.R()) {
         this.o.a(this, "fireworks.launch", 3.0F, 1.0F);
      }

      this.a++;
      if (this.o.D && this.a % 2 < 2) {
         this.o.a(EnumParticleTypes.d, this.s, this.t - 0.3, this.u, this.V.nextGaussian() * 0.05, -this.w * 0.5, this.V.nextGaussian() * 0.05);
      }

      if (!this.o.D && this.a > this.b) {
         this.o.a(this, (byte)17);
         this.J();
      }
   }

   @Override
   public void a(byte var1) {
      if (var1 == 17 && this.o.D) {
         ItemStack var2 = this.ac.f(8);
         NBTTagCompound var3 = null;
         if (var2 != null && var2.n()) {
            var3 = var2.o().m("Fireworks");
         }

         this.o.a(this.s, this.t, this.u, this.v, this.w, this.x, var3);
      }

      super.a(var1);
   }

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("Life", this.a);
      var1.a("LifeTime", this.b);
      ItemStack var2 = this.ac.f(8);
      if (var2 != null) {
         NBTTagCompound var3 = new NBTTagCompound();
         var2.b(var3);
         var1.a("FireworksItem", var3);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.a = var1.f("Life");
      this.b = var1.f("LifeTime");
      NBTTagCompound var2 = var1.m("FireworksItem");
      if (var2 != null) {
         ItemStack var3 = ItemStack.a(var2);
         if (var3 != null) {
            this.ac.b(8, var3);
         }
      }
   }

   @Override
   public float c(float var1) {
      return super.c(var1);
   }

   @Override
   public int b(float var1) {
      return super.b(var1);
   }

   @Override
   public boolean aD() {
      return false;
   }
}
