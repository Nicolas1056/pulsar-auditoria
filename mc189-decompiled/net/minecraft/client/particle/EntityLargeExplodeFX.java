package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityLargeExplodeFX extends EntityFX {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/explosion.png");
   private static final VertexFormat az = new VertexFormat()
      .a(DefaultVertexFormats.m)
      .a(DefaultVertexFormats.o)
      .a(DefaultVertexFormats.n)
      .a(DefaultVertexFormats.p)
      .a(DefaultVertexFormats.q)
      .a(DefaultVertexFormats.r);
   private int aA;
   private int aB;
   private TextureManager aC;
   private float aD;

   protected EntityLargeExplodeFX(TextureManager var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13) {
      super(var2, var3, var5, var7, 0.0, 0.0, 0.0);
      this.aC = var1;
      this.aB = 6 + this.V.nextInt(4);
      this.ar = this.as = this.at = this.V.nextFloat() * 0.6F + 0.4F;
      this.aD = 1.0F - (float)var9 * 0.5F;
   }

   @Override
   public void a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      int var9 = (int)((this.aA + var3) * 15.0F / this.aB);
      if (var9 <= 15) {
         this.aC.a(a);
         float var10 = var9 % 4 / 4.0F;
         float var11 = var10 + 0.24975F;
         float var12 = var9 / 4 / 4.0F;
         float var13 = var12 + 0.24975F;
         float var14 = 2.0F * this.aD;
         float var15 = (float)(this.p + (this.s - this.p) * var3 - aw);
         float var16 = (float)(this.q + (this.t - this.q) * var3 - ax);
         float var17 = (float)(this.r + (this.u - this.r) * var3 - ay);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.f();
         RenderHelper.a();
         var1.a(7, az);
         var1.b((double)(var15 - var4 * var14 - var7 * var14), (double)(var16 - var5 * var14), (double)(var17 - var6 * var14 - var8 * var14))
            .a((double)var11, (double)var13)
            .a(this.ar, this.as, this.at, 1.0F)
            .a(0, 240)
            .c(0.0F, 1.0F, 0.0F)
            .d();
         var1.b((double)(var15 - var4 * var14 + var7 * var14), (double)(var16 + var5 * var14), (double)(var17 - var6 * var14 + var8 * var14))
            .a((double)var11, (double)var12)
            .a(this.ar, this.as, this.at, 1.0F)
            .a(0, 240)
            .c(0.0F, 1.0F, 0.0F)
            .d();
         var1.b((double)(var15 + var4 * var14 + var7 * var14), (double)(var16 + var5 * var14), (double)(var17 + var6 * var14 + var8 * var14))
            .a((double)var10, (double)var12)
            .a(this.ar, this.as, this.at, 1.0F)
            .a(0, 240)
            .c(0.0F, 1.0F, 0.0F)
            .d();
         var1.b((double)(var15 + var4 * var14 - var7 * var14), (double)(var16 - var5 * var14), (double)(var17 + var6 * var14 - var8 * var14))
            .a((double)var10, (double)var13)
            .a(this.ar, this.as, this.at, 1.0F)
            .a(0, 240)
            .c(0.0F, 1.0F, 0.0F)
            .d();
         Tessellator.a().b();
         GlStateManager.e();
      }
   }

   @Override
   public int b(float var1) {
      return 61680;
   }

   @Override
   public void t_() {
      this.p = this.s;
      this.q = this.t;
      this.r = this.u;
      this.aA++;
      if (this.aA == this.aB) {
         this.J();
      }
   }

   @Override
   public int a() {
      return 3;
   }

   public static class Factory implements IParticleFactory {
      @Override
      public EntityFX a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new EntityLargeExplodeFX(Minecraft.A().P(), var2, var3, var5, var7, var9, var11, var13);
      }
   }
}
