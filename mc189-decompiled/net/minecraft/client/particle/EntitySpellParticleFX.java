package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySpellParticleFX extends EntityFX {
   private static final Random a = new Random();
   private int az = 128;

   protected EntitySpellParticleFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, 0.5 - a.nextDouble(), var10, 0.5 - a.nextDouble());
      this.w *= 0.2F;
      if (var8 == 0.0 && var12 == 0.0) {
         this.v *= 0.1F;
         this.x *= 0.1F;
      }

      this.h *= 0.75F;
      this.g = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.T = false;
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.f + var3) / this.g * 32.0F;
      var9 = MathHelper.a(var9, 0.0F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      if (this.f++ >= this.g) {
         this.J();
      }

      this.k(this.az + (7 - this.f * 8 / this.g));
      this.w += 0.004;
      this.d(this.v, this.w, this.x);
      if (this.t == this.q) {
         this.v *= 1.1;
         this.x *= 1.1;
      }

      this.v *= 0.96F;
      this.w *= 0.96F;
      this.x *= 0.96F;
      if (this.C) {
         this.v *= 0.7F;
         this.x *= 0.7F;
      }
   }

   public void a(int var1) {
      this.az = var1;
   }

   public static class AmbientMobFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         EntitySpellParticleFX var16 = new EntitySpellParticleFX(var2, var3, var5, var7, var9, var11, var13);
         var16.i(0.15F);
         var16.b((float)var9, (float)var11, (float)var13);
         return var16;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntitySpellParticleFX(var2, var3, var5, var7, var9, var11, var13);
      }
   }

   public static class InstantFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         EntitySpellParticleFX var16 = new EntitySpellParticleFX(var2, var3, var5, var7, var9, var11, var13);
         var16.a(144);
         return var16;
      }
   }

   public static class MobFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         EntitySpellParticleFX var16 = new EntitySpellParticleFX(var2, var3, var5, var7, var9, var11, var13);
         var16.b((float)var9, (float)var11, (float)var13);
         return var16;
      }
   }

   public static class WitchFactory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         EntitySpellParticleFX var16 = new EntitySpellParticleFX(var2, var3, var5, var7, var9, var11, var13);
         var16.a(144);
         float var17 = var2.s.nextFloat() * 0.5F + 0.35F;
         var16.b(1.0F * var17, 0.0F * var17, 1.0F * var17);
         return var16;
      }
   }
}
