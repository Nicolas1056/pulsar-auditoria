package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityReddustFX extends EntityFX {
   float a;

   protected EntityReddustFX(World var1, double var2, double var4, double var6, float var8, float var9, float var10) {
      this(var1, var2, var4, var6, 1.0F, var8, var9, var10);
   }

   protected EntityReddustFX(World var1, double var2, double var4, double var6, float var8, float var9, float var10, float var11) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      this.v *= 0.1F;
      this.w *= 0.1F;
      this.x *= 0.1F;
      if (var9 == 0.0F) {
         var9 = 1.0F;
      }

      float var12 = (float)Math.random() * 0.4F + 0.6F;
      this.ar = ((float)(Math.random() * 0.2F) + 0.8F) * var9 * var12;
      this.as = ((float)(Math.random() * 0.2F) + 0.8F) * var10 * var12;
      this.at = ((float)(Math.random() * 0.2F) + 0.8F) * var11 * var12;
      this.h *= 0.75F;
      this.h *= var8;
      this.a = this.h;
      this.g = (int)(8.0 / (Math.random() * 0.8 + 0.2));
      this.g = (int)(this.g * var8);
      this.T = false;
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (this.f + var3) / this.g * 32.0F;
      var9 = MathHelper.a(var9, 0.0F, 1.0F);
      this.h = this.a * var9;
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

      this.k(7 - this.f * 8 / this.g);
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

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityReddustFX(var2, var3, var5, var7, (float)var9, (float)var11, (float)var13);
      }
   }
}
