package net.minecraft.entity.item;

import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityEnderEye extends Entity {
   private double a;
   private double b;
   private double c;
   private int d;
   private boolean e;

   public EntityEnderEye(World var1) {
      super(var1);
      this.a(0.25F, 0.25F);
   }

   @Override
   protected void h() {
   }

   @Override
   public boolean a(double var1) {
      double var3 = this.aR().a() * 4.0;
      if (Double.isNaN(var3)) {
         var3 = 4.0;
      }

      var3 *= 64.0;
      return var1 < var3 * var3;
   }

   public EntityEnderEye(World var1, double var2, double var4, double var6) {
      super(var1);
      this.d = 0;
      this.a(0.25F, 0.25F);
      this.b(var2, var4, var6);
   }

   public void a(BlockPos var1) {
      double var2 = var1.n();
      int var4 = var1.o();
      double var5 = var1.p();
      double var7 = var2 - this.s;
      double var9 = var5 - this.u;
      float var11 = MathHelper.a(var7 * var7 + var9 * var9);
      if (var11 > 12.0F) {
         this.a = this.s + var7 / var11 * 12.0;
         this.c = this.u + var9 / var11 * 12.0;
         this.b = this.t + 8.0;
      } else {
         this.a = var2;
         this.b = var4;
         this.c = var5;
      }

      this.d = 0;
      this.e = this.V.nextInt(5) > 0;
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
      this.s = this.s + this.v;
      this.t = this.t + this.w;
      this.u = this.u + this.x;
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
      if (!this.o.D) {
         double var2 = this.a - this.s;
         double var4 = this.c - this.u;
         float var6 = (float)Math.sqrt(var2 * var2 + var4 * var4);
         float var7 = (float)MathHelper.b(var4, var2);
         double var8 = var1 + (var6 - var1) * 0.0025;
         if (var6 < 1.0F) {
            var8 *= 0.8;
            this.w *= 0.8;
         }

         this.v = Math.cos(var7) * var8;
         this.x = Math.sin(var7) * var8;
         if (this.t < this.b) {
            this.w = this.w + (1.0 - this.w) * 0.015F;
         } else {
            this.w = this.w + (-1.0 - this.w) * 0.015F;
         }
      }

      float var10 = 0.25F;
      if (this.V()) {
         for (int var3 = 0; var3 < 4; var3++) {
            this.o.a(EnumParticleTypes.e, this.s - this.v * var10, this.t - this.w * var10, this.u - this.x * var10, this.v, this.w, this.x);
         }
      } else {
         this.o
            .a(
               EnumParticleTypes.y,
               this.s - this.v * var10 + this.V.nextDouble() * 0.6 - 0.3,
               this.t - this.w * var10 - 0.5,
               this.u - this.x * var10 + this.V.nextDouble() * 0.6 - 0.3,
               this.v,
               this.w,
               this.x
            );
      }

      if (!this.o.D) {
         this.b(this.s, this.t, this.u);
         this.d++;
         if (this.d > 80 && !this.o.D) {
            this.J();
            if (this.e) {
               this.o.d(new EntityItem(this.o, this.s, this.t, this.u, new ItemStack(Items.bH)));
            } else {
               this.o.b(2003, new BlockPos(this), 0);
            }
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
   }

   @Override
   public void a(NBTTagCompound var1) {
   }

   @Override
   public float c(float var1) {
      return 1.0F;
   }

   @Override
   public int b(float var1) {
      return 15728880;
   }

   @Override
   public boolean aD() {
      return false;
   }
}
