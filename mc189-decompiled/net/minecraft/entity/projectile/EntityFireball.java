package net.minecraft.entity.projectile;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public abstract class EntityFireball extends Entity {
   private int e = -1;
   private int f = -1;
   private int g = -1;
   private Block h;
   private boolean i;
   public EntityLivingBase a;
   private int ar;
   private int as;
   public double b;
   public double c;
   public double d;

   public EntityFireball(World var1) {
      super(var1);
      this.a(1.0F, 1.0F);
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

   public EntityFireball(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1);
      this.a(1.0F, 1.0F);
      this.b(var2, var4, var6, this.y, this.z);
      this.b(var2, var4, var6);
      double var14 = MathHelper.a(var8 * var8 + var10 * var10 + var12 * var12);
      this.b = var8 / var14 * 0.1;
      this.c = var10 / var14 * 0.1;
      this.d = var12 / var14 * 0.1;
   }

   public EntityFireball(World var1, EntityLivingBase var2, double var3, double var5, double var7) {
      super(var1);
      this.a = var2;
      this.a(1.0F, 1.0F);
      this.b(var2.s, var2.t, var2.u, var2.y, var2.z);
      this.b(this.s, this.t, this.u);
      this.v = this.w = this.x = 0.0;
      var3 += this.V.nextGaussian() * 0.4;
      var5 += this.V.nextGaussian() * 0.4;
      var7 += this.V.nextGaussian() * 0.4;
      double var9 = MathHelper.a(var3 * var3 + var5 * var5 + var7 * var7);
      this.b = var3 / var9 * 0.1;
      this.c = var5 / var9 * 0.1;
      this.d = var7 / var9 * 0.1;
   }

   @Override
   public void t_() {
      if (this.o.D || (this.a == null || !this.a.I) && this.o.e(new BlockPos(this))) {
         super.t_();
         this.e(1);
         if (this.i) {
            if (this.o.p(new BlockPos(this.e, this.f, this.g)).c() == this.h) {
               this.ar++;
               if (this.ar == 600) {
                  this.J();
               }

               return;
            }

            this.i = false;
            this.v = this.v * (this.V.nextFloat() * 0.2F);
            this.w = this.w * (this.V.nextFloat() * 0.2F);
            this.x = this.x * (this.V.nextFloat() * 0.2F);
            this.ar = 0;
            this.as = 0;
         } else {
            this.as++;
         }

         Vec3 var1 = new Vec3(this.s, this.t, this.u);
         Vec3 var2 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
         MovingObjectPosition var3 = this.o.a(var1, var2);
         var1 = new Vec3(this.s, this.t, this.u);
         var2 = new Vec3(this.s + this.v, this.t + this.w, this.u + this.x);
         if (var3 != null) {
            var2 = new Vec3(var3.c.a, var3.c.b, var3.c.c);
         }

         Entity var4 = null;
         List var5 = this.o.b(this, this.aR().a(this.v, this.w, this.x).b(1.0, 1.0, 1.0));
         double var6 = 0.0;

         for (int var8 = 0; var8 < var5.size(); var8++) {
            Entity var9 = (Entity)var5.get(var8);
            if (var9.ad() && (!var9.k(this.a) || this.as >= 25)) {
               float var10 = 0.3F;
               AxisAlignedBB var11 = var9.aR().b(var10, var10, var10);
               MovingObjectPosition var12 = var11.a(var1, var2);
               if (var12 != null) {
                  double var13 = var1.g(var12.c);
                  if (var13 < var6 || var6 == 0.0) {
                     var4 = var9;
                     var6 = var13;
                  }
               }
            }
         }

         if (var4 != null) {
            var3 = new MovingObjectPosition(var4);
         }

         if (var3 != null) {
            this.a(var3);
         }

         this.s = this.s + this.v;
         this.t = this.t + this.w;
         this.u = this.u + this.x;
         float var17 = MathHelper.a(this.v * this.v + this.x * this.x);
         this.y = (float)(MathHelper.b(this.x, this.v) * 180.0 / (float) Math.PI) + 90.0F;
         this.z = (float)(MathHelper.b((double)var17, this.w) * 180.0 / (float) Math.PI) - 90.0F;

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
         float var18 = this.j();
         if (this.V()) {
            for (int var19 = 0; var19 < 4; var19++) {
               float var20 = 0.25F;
               this.o.a(EnumParticleTypes.e, this.s - this.v * var20, this.t - this.w * var20, this.u - this.x * var20, this.v, this.w, this.x);
            }

            var18 = 0.8F;
         }

         this.v = this.v + this.b;
         this.w = this.w + this.c;
         this.x = this.x + this.d;
         this.v *= var18;
         this.w *= var18;
         this.x *= var18;
         this.o.a(EnumParticleTypes.l, this.s, this.t + 0.5, this.u, 0.0, 0.0, 0.0);
         this.b(this.s, this.t, this.u);
      } else {
         this.J();
      }
   }

   protected float j() {
      return 0.95F;
   }

   protected abstract void a(MovingObjectPosition var1);

   @Override
   public void b(NBTTagCompound var1) {
      var1.a("xTile", (short)this.e);
      var1.a("yTile", (short)this.f);
      var1.a("zTile", (short)this.g);
      ResourceLocation var2 = Block.c.c(this.h);
      var1.a("inTile", var2 == null ? "" : var2.toString());
      var1.a("inGround", (byte)(this.i ? 1 : 0));
      var1.a("direction", this.a(new double[]{this.v, this.w, this.x}));
   }

   @Override
   public void a(NBTTagCompound var1) {
      this.e = var1.e("xTile");
      this.f = var1.e("yTile");
      this.g = var1.e("zTile");
      if (var1.b("inTile", 8)) {
         this.h = Block.b(var1.j("inTile"));
      } else {
         this.h = Block.c(var1.d("inTile") & 255);
      }

      this.i = var1.d("inGround") == 1;
      if (var1.b("direction", 9)) {
         NBTTagList var2 = var1.c("direction", 6);
         this.v = var2.d(0);
         this.w = var2.d(1);
         this.x = var2.d(2);
      } else {
         this.J();
      }
   }

   @Override
   public boolean ad() {
      return true;
   }

   @Override
   public float ao() {
      return 1.0F;
   }

   @Override
   public boolean a(DamageSource var1, float var2) {
      if (this.b(var1)) {
         return false;
      } else {
         this.ac();
         if (var1.j() != null) {
            Vec3 var3 = var1.j().ap();
            if (var3 != null) {
               this.v = var3.a;
               this.w = var3.b;
               this.x = var3.c;
               this.b = this.v * 0.1;
               this.c = this.w * 0.1;
               this.d = this.x * 0.1;
            }

            if (var1.j() instanceof EntityLivingBase) {
               this.a = (EntityLivingBase)var1.j();
            }

            return true;
         } else {
            return false;
         }
      }
   }

   @Override
   public float c(float var1) {
      return 1.0F;
   }

   @Override
   public int b(float var1) {
      return 15728880;
   }
}
