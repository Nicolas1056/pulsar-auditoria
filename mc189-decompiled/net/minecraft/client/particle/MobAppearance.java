package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MobAppearance extends EntityFX {
   private EntityLivingBase a;

   protected MobAppearance(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6, 0.0, 0.0, 0.0);
      this.ar = this.as = this.at = 1.0F;
      this.v = this.w = this.x = 0.0;
      this.i = 0.0F;
      this.g = 30;
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public void t_() {
      super.t_();
      if (this.a == null) {
         EntityGuardian var1 = new EntityGuardian(this.o);
         var1.co();
         this.a = var1;
      }
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (this.a != null) {
         RenderManager var9 = Minecraft.A().af();
         var9.a(EntityFX.aw, EntityFX.ax, EntityFX.ay);
         float var10 = 0.42553192F;
         float var11 = (this.f + var3) / this.g;
         GlStateManager.a(true);
         GlStateManager.l();
         GlStateManager.j();
         GlStateManager.b(770, 771);
         float var12 = 240.0F;
         OpenGlHelper.a(OpenGlHelper.r, var12, var12);
         GlStateManager.E();
         float var13 = 0.05F + 0.5F * MathHelper.a(var11 * (float) Math.PI);
         GlStateManager.c(1.0F, 1.0F, 1.0F, var13);
         GlStateManager.b(0.0F, 1.8F, 0.0F);
         GlStateManager.b(180.0F - var2.y, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(60.0F - 150.0F * var11 - var2.z, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(0.0F, -0.4F, -1.5F);
         GlStateManager.a(var10, var10, var10);
         this.a.y = this.a.A = 0.0F;
         this.a.aK = this.a.aL = 0.0F;
         var9.a(this.a, 0.0, 0.0, 0.0, 0.0F, var3);
         GlStateManager.F();
         GlStateManager.j();
      }
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new MobAppearance(var2, var3, var5, var7);
      }
   }
}
