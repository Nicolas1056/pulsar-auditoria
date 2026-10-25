package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFirework {
   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         EntityFirework.SparkFX var16 = new EntityFirework.SparkFX(var2, var3, var5, var7, var9, var11, var13, Minecraft.A().j);
         var16.i(0.99F);
         return var16;
      }
   }

   public static class OverlayFX extends EntityFX {
      protected OverlayFX(World var1, double var2, double var4, double var6) {
         super(var1, var2, var4, var6);
         this.g = 4;
      }

      @Override
      public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
         float var9 = 0.25F;
         float var10 = 0.5F;
         float var11 = 0.125F;
         float var12 = 0.375F;
         float var13 = 7.1F * MathHelper.a((this.f + var3 - 1.0F) * 0.25F * (float) Math.PI);
         this.au = 0.6F - (this.f + var3 - 1.0F) * 0.25F * 0.5F;
         float var14 = (float)(this.p + (this.s - this.p) * var3 - aw);
         float var15 = (float)(this.q + (this.t - this.q) * var3 - ax);
         float var16 = (float)(this.r + (this.u - this.r) * var3 - ay);
         int var17 = this.b(var3);
         int var18 = var17 >> 16 & 65535;
         int var19 = var17 & 65535;
         var1.b((double)(var14 - var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 - var6 * var13 - var8 * var13))
            .a(0.5, 0.375)
            .a(this.ar, this.as, this.at, this.au)
            .a(var18, var19)
            .d();
         var1.b((double)(var14 - var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 - var6 * var13 + var8 * var13))
            .a(0.5, 0.125)
            .a(this.ar, this.as, this.at, this.au)
            .a(var18, var19)
            .d();
         var1.b((double)(var14 + var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 + var6 * var13 + var8 * var13))
            .a(0.25, 0.125)
            .a(this.ar, this.as, this.at, this.au)
            .a(var18, var19)
            .d();
         var1.b((double)(var14 + var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 + var6 * var13 - var8 * var13))
            .a(0.25, 0.375)
            .a(this.ar, this.as, this.at, this.au)
            .a(var18, var19)
            .d();
      }
   }

   public static class SparkFX extends EntityFX {
      private int a = 160;
      private boolean az;
      private boolean aA;
      private final EffectRenderer aB;
      private float aC;
      private float aD;
      private float aE;
      private boolean aF;

      public SparkFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, EffectRenderer var14) {
         super(var1, var2, var4, var6);
         this.v = var8;
         this.w = var10;
         this.x = var12;
         this.aB = var14;
         this.h *= 0.75F;
         this.g = 48 + this.V.nextInt(12);
         this.T = false;
      }

      public void a(boolean var1) {
         this.az = var1;
      }

      public void i(boolean var1) {
         this.aA = var1;
      }

      public void a(int var1) {
         float var2 = ((var1 & 0xFF0000) >> 16) / 255.0F;
         float var3 = ((var1 & 0xFF00) >> 8) / 255.0F;
         float var4 = ((var1 & 0xFF) >> 0) / 255.0F;
         float var5 = 1.0F;
         this.b(var2 * var5, var3 * var5, var4 * var5);
      }

      public void b(int var1) {
         this.aC = ((var1 & 0xFF0000) >> 16) / 255.0F;
         this.aD = ((var1 & 0xFF00) >> 8) / 255.0F;
         this.aE = ((var1 & 0xFF) >> 0) / 255.0F;
         this.aF = true;
      }

      @Override
      public AxisAlignedBB S() {
         return null;
      }

      @Override
      public boolean ae() {
         return false;
      }

      @Override
      public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
         if (!this.aA || this.f < this.g / 3 || (this.f + this.g) / 3 % 2 == 0) {
            super.a(var1, var2, var3, var4, var5, var6, var7, var8);
         }
      }

      @Override
      public void t_() {
         this.p = this.s;
         this.q = this.t;
         this.r = this.u;
         if (this.f++ >= this.g) {
            this.J();
         }

         if (this.f > this.g / 2) {
            this.i(1.0F - ((float)this.f - this.g / 2) / this.g);
            if (this.aF) {
               this.ar = this.ar + (this.aC - this.ar) * 0.2F;
               this.as = this.as + (this.aD - this.as) * 0.2F;
               this.at = this.at + (this.aE - this.at) * 0.2F;
            }
         }

         this.k(this.a + (7 - this.f * 8 / this.g));
         this.w -= 0.004;
         this.d(this.v, this.w, this.x);
         this.v *= 0.91F;
         this.w *= 0.91F;
         this.x *= 0.91F;
         if (this.C) {
            this.v *= 0.7F;
            this.x *= 0.7F;
         }

         if (this.az && this.f < this.g / 2 && (this.f + this.g) % 2 == 0) {
            EntityFirework.SparkFX var1 = new EntityFirework.SparkFX(this.o, this.s, this.t, this.u, 0.0, 0.0, 0.0, this.aB);
            var1.i(0.99F);
            var1.b(this.ar, this.as, this.at);
            var1.f = var1.g / 2;
            if (this.aF) {
               var1.aF = true;
               var1.aC = this.aC;
               var1.aD = this.aD;
               var1.aE = this.aE;
            }

            var1.aA = this.aA;
            this.aB.a(var1);
         }
      }

      @Override
      public int b(float var1) {
         return 15728880;
      }

      @Override
      public float c(float var1) {
         return 1.0F;
      }
   }

   public static class StarterFX extends EntityFX {
      private int az;
      private final EffectRenderer aA;
      private NBTTagList aB;
      boolean a;

      public StarterFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, EffectRenderer var14, NBTTagCompound var15) {
         super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
         this.v = var8;
         this.w = var10;
         this.x = var12;
         this.aA = var14;
         this.g = 8;
         if (var15 != null) {
            this.aB = var15.c("Explosions", 10);
            if (this.aB.c() == 0) {
               this.aB = null;
            } else {
               this.g = this.aB.c() * 2 - 1;

               for (int var16 = 0; var16 < this.aB.c(); var16++) {
                  NBTTagCompound var17 = this.aB.b(var16);
                  if (var17.n("Flicker")) {
                     this.a = true;
                     this.g += 15;
                     break;
                  }
               }
            }
         }
      }

      @Override
      public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      }

      @Override
      public void t_() {
         if (this.az == 0 && this.aB != null) {
            boolean var1 = this.l();
            boolean var2 = false;
            if (this.aB.c() >= 3) {
               var2 = true;
            } else {
               for (int var3 = 0; var3 < this.aB.c(); var3++) {
                  NBTTagCompound var4 = this.aB.b(var3);
                  if (var4.d("Type") == 1) {
                     var2 = true;
                     break;
                  }
               }
            }

            String var17 = "fireworks." + (var2 ? "largeBlast" : "blast") + (var1 ? "_far" : "");
            this.o.a(this.s, this.t, this.u, var17, 20.0F, 0.95F + this.V.nextFloat() * 0.1F, true);
         }

         if (this.az % 2 == 0 && this.aB != null && this.az / 2 < this.aB.c()) {
            int var13 = this.az / 2;
            NBTTagCompound var15 = this.aB.b(var13);
            byte var18 = var15.d("Type");
            boolean var19 = var15.n("Trail");
            boolean var5 = var15.n("Flicker");
            int[] var6 = var15.l("Colors");
            int[] var7 = var15.l("FadeColors");
            if (var6.length == 0) {
               var6 = new int[]{ItemDye.a[0]};
            }

            if (var18 == 1) {
               this.a(0.5, 4, var6, var7, var19, var5);
            } else if (var18 == 2) {
               this.a(
                  0.5,
                  new double[][]{
                     {0.0, 1.0},
                     {0.3455, 0.309},
                     {0.9511, 0.309},
                     {0.3795918367346939, -0.12653061224489795},
                     {0.6122448979591837, -0.8040816326530612},
                     {0.0, -0.35918367346938773}
                  },
                  var6,
                  var7,
                  var19,
                  var5,
                  false
               );
            } else if (var18 == 3) {
               this.a(
                  0.5,
                  new double[][]{
                     {0.0, 0.2},
                     {0.2, 0.2},
                     {0.2, 0.6},
                     {0.6, 0.6},
                     {0.6, 0.2},
                     {0.2, 0.2},
                     {0.2, 0.0},
                     {0.4, 0.0},
                     {0.4, -0.6},
                     {0.2, -0.6},
                     {0.2, -0.4},
                     {0.0, -0.4}
                  },
                  var6,
                  var7,
                  var19,
                  var5,
                  true
               );
            } else if (var18 == 4) {
               this.a(var6, var7, var19, var5);
            } else {
               this.a(0.25, 2, var6, var7, var19, var5);
            }

            int var8 = var6[0];
            float var9 = ((var8 & 0xFF0000) >> 16) / 255.0F;
            float var10 = ((var8 & 0xFF00) >> 8) / 255.0F;
            float var11 = ((var8 & 0xFF) >> 0) / 255.0F;
            EntityFirework.OverlayFX var12 = new EntityFirework.OverlayFX(this.o, this.s, this.t, this.u);
            var12.b(var9, var10, var11);
            this.aA.a(var12);
         }

         this.az++;
         if (this.az > this.g) {
            if (this.a) {
               boolean var14 = this.l();
               String var16 = "fireworks." + (var14 ? "twinkle_far" : "twinkle");
               this.o.a(this.s, this.t, this.u, var16, 20.0F, 0.9F + this.V.nextFloat() * 0.15F, true);
            }

            this.J();
         }
      }

      private boolean l() {
         Minecraft var1 = Minecraft.A();
         return var1 == null || var1.ac() == null || !(var1.ac().e(this.s, this.t, this.u) < 256.0);
      }

      private void a(double var1, double var3, double var5, double var7, double var9, double var11, int[] var13, int[] var14, boolean var15, boolean var16) {
         EntityFirework.SparkFX var17 = new EntityFirework.SparkFX(this.o, var1, var3, var5, var7, var9, var11, this.aA);
         var17.i(0.99F);
         var17.a(var15);
         var17.i(var16);
         int var18 = this.V.nextInt(var13.length);
         var17.a(var13[var18]);
         if (var14 != null && var14.length > 0) {
            var17.b(var14[this.V.nextInt(var14.length)]);
         }

         this.aA.a(var17);
      }

      private void a(double var1, int var3, int[] var4, int[] var5, boolean var6, boolean var7) {
         double var8 = this.s;
         double var10 = this.t;
         double var12 = this.u;

         for (int var14 = -var3; var14 <= var3; var14++) {
            for (int var15 = -var3; var15 <= var3; var15++) {
               for (int var16 = -var3; var16 <= var3; var16++) {
                  double var17 = var15 + (this.V.nextDouble() - this.V.nextDouble()) * 0.5;
                  double var19 = var14 + (this.V.nextDouble() - this.V.nextDouble()) * 0.5;
                  double var21 = var16 + (this.V.nextDouble() - this.V.nextDouble()) * 0.5;
                  double var23 = MathHelper.a(var17 * var17 + var19 * var19 + var21 * var21) / var1 + this.V.nextGaussian() * 0.05;
                  this.a(var8, var10, var12, var17 / var23, var19 / var23, var21 / var23, var4, var5, var6, var7);
                  if (var14 != -var3 && var14 != var3 && var15 != -var3 && var15 != var3) {
                     var16 += var3 * 2 - 1;
                  }
               }
            }
         }
      }

      private void a(double var1, double[][] var3, int[] var4, int[] var5, boolean var6, boolean var7, boolean var8) {
         double var9 = var3[0][0];
         double var11 = var3[0][1];
         this.a(this.s, this.t, this.u, var9 * var1, var11 * var1, 0.0, var4, var5, var6, var7);
         float var13 = this.V.nextFloat() * (float) Math.PI;
         double var14 = var8 ? 0.034 : 0.34;

         for (int var16 = 0; var16 < 3; var16++) {
            double var17 = var13 + var16 * (float) Math.PI * var14;
            double var19 = var9;
            double var21 = var11;

            for (int var23 = 1; var23 < var3.length; var23++) {
               double var24 = var3[var23][0];
               double var26 = var3[var23][1];

               for (double var28 = 0.25; var28 <= 1.0; var28 += 0.25) {
                  double var30 = (var19 + (var24 - var19) * var28) * var1;
                  double var32 = (var21 + (var26 - var21) * var28) * var1;
                  double var34 = var30 * Math.sin(var17);
                  var30 *= Math.cos(var17);

                  for (double var36 = -1.0; var36 <= 1.0; var36 += 2.0) {
                     this.a(this.s, this.t, this.u, var30 * var36, var32, var34 * var36, var4, var5, var6, var7);
                  }
               }

               var19 = var24;
               var21 = var26;
            }
         }
      }

      private void a(int[] var1, int[] var2, boolean var3, boolean var4) {
         double var5 = this.V.nextGaussian() * 0.05;
         double var7 = this.V.nextGaussian() * 0.05;

         for (int var9 = 0; var9 < 70; var9++) {
            double var10 = this.v * 0.5 + this.V.nextGaussian() * 0.15 + var5;
            double var12 = this.x * 0.5 + this.V.nextGaussian() * 0.15 + var7;
            double var14 = this.w * 0.5 + this.V.nextDouble() * 0.5;
            this.a(this.s, this.t, this.u, var10, var14, var12, var1, var2, var3, var4);
         }
      }

      @Override
      public int a() {
         return 0;
      }
   }
}
