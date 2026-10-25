package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityCloudFX extends EntityFX {
   float a;

   protected EntityCloudFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      float var14 = 2.5F;
      this.v *= 0.1F;
      this.w *= 0.1F;
      this.x *= 0.1F;
      this.v += var8;
      this.w += var10;
      this.x += var12;
      this.ar = this.as = this.at = 1.0F - (float)(Math.random() * 0.3F);
      this.h *= 0.75F;
      this.h *= var14;
      this.a = this.h;
      this.g = (int)(8.0 / (Math.random() * 0.8 + 0.3));
      this.g = (int)(this.g * var14);
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
      this.v *= 0.96F;
      this.w *= 0.96F;
      this.x *= 0.96F;
      EntityPlayer var1 = this.o.a(this, 2.0);
      if (var1 != null && this.t > var1.aR().b) {
         this.t = this.t + (var1.aR().b - this.t) * 0.2;
         this.w = this.w + (var1.w - this.w) * 0.2;
         this.b(this.s, this.t, this.u);
      }

      if (this.C) {
         this.v *= 0.7F;
         this.x *= 0.7F;
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityCloudFX(var2, var3, var5, var7, var9, var11, var13);
      }
   }
}
