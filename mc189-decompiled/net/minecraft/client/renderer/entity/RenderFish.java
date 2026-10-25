package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderFish extends Render<EntityFishHook> {
   private static final ResourceLocation a = new ResourceLocation("textures/particle/particles.png");

   public RenderFish(RenderManager var1) {
      super(var1);
   }

   public void a(EntityFishHook var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      GlStateManager.B();
      GlStateManager.a(0.5F, 0.5F, 0.5F);
      this.c(var1);
      Tessellator var10 = Tessellator.a();
      WorldRenderer var11 = var10.c();
      boolean var12 = true;
      byte var13 = 2;
      float var14 = 0.0625F;
      float var15 = 0.125F;
      float var16 = 0.125F;
      float var17 = 0.1875F;
      float var18 = 1.0F;
      float var19 = 0.5F;
      float var20 = 0.5F;
      GlStateManager.b(180.0F - this.b.e, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(-this.b.f, 1.0F, 0.0F, 0.0F);
      var11.a(7, DefaultVertexFormats.j);
      var11.b(-0.5, -0.5, 0.0).a(0.0625, 0.1875).c(0.0F, 1.0F, 0.0F).d();
      var11.b(0.5, -0.5, 0.0).a(0.125, 0.1875).c(0.0F, 1.0F, 0.0F).d();
      var11.b(0.5, 0.5, 0.0).a(0.125, 0.125).c(0.0F, 1.0F, 0.0F).d();
      var11.b(-0.5, 0.5, 0.0).a(0.0625, 0.125).c(0.0F, 1.0F, 0.0F).d();
      var10.b();
      GlStateManager.C();
      GlStateManager.F();
      if (var1.b != null) {
         float var21 = var1.b.l(var9);
         float var22 = MathHelper.a(MathHelper.c(var21) * (float) Math.PI);
         Vec3 var23 = new Vec3(-0.36, 0.03, 0.35);
         var23 = var23.a(-(var1.b.B + (var1.b.z - var1.b.B) * var9) * (float) Math.PI / 180.0F);
         var23 = var23.b(-(var1.b.A + (var1.b.y - var1.b.A) * var9) * (float) Math.PI / 180.0F);
         var23 = var23.b(var22 * 0.5F);
         var23 = var23.a(-var22 * 0.7F);
         double var24 = var1.b.p + (var1.b.s - var1.b.p) * var9 + var23.a;
         double var26 = var1.b.q + (var1.b.t - var1.b.q) * var9 + var23.b;
         double var28 = var1.b.r + (var1.b.u - var1.b.r) * var9 + var23.c;
         double var30 = var1.b.aS();
         if (this.b.g != null && this.b.g.aB > 0 || var1.b != Minecraft.A().h) {
            float var32 = (var1.b.aJ + (var1.b.aI - var1.b.aJ) * var9) * (float) Math.PI / 180.0F;
            double var33 = MathHelper.a(var32);
            double var35 = MathHelper.b(var32);
            double var37 = 0.35;
            double var39 = 0.8;
            var24 = var1.b.p + (var1.b.s - var1.b.p) * var9 - var35 * 0.35 - var33 * 0.8;
            var26 = var1.b.q + var30 + (var1.b.t - var1.b.q) * var9 - 0.45;
            var28 = var1.b.r + (var1.b.u - var1.b.r) * var9 - var33 * 0.35 + var35 * 0.8;
            var30 = var1.b.av() ? -0.1875 : 0.0;
         }

         double var51 = var1.p + (var1.s - var1.p) * var9;
         double var34 = var1.q + (var1.t - var1.q) * var9 + 0.25;
         double var36 = var1.r + (var1.u - var1.r) * var9;
         double var38 = (float)(var24 - var51);
         double var40 = (float)(var26 - var34) + var30;
         double var42 = (float)(var28 - var36);
         GlStateManager.x();
         GlStateManager.f();
         var11.a(3, DefaultVertexFormats.f);
         byte var44 = 16;

         for (int var45 = 0; var45 <= 16; var45++) {
            float var46 = var45 / 16.0F;
            var11.b(var2 + var38 * var46, var4 + var40 * (var46 * var46 + var46) * 0.5 + 0.25, var6 + var42 * var46).b(0, 0, 0, 255).d();
         }

         var10.b();
         GlStateManager.e();
         GlStateManager.w();
         super.a(var1, var2, var4, var6, var8, var9);
      }
   }

   protected ResourceLocation a(EntityFishHook var1) {
      return a;
   }
}
