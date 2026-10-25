package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderXPOrb extends Render<EntityXPOrb> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/experience_orb.png");

   public RenderXPOrb(RenderManager var1) {
      super(var1);
      this.c = 0.15F;
      this.d = 0.75F;
   }

   public void a(EntityXPOrb var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      this.c(var1);
      int var10 = var1.l();
      float var11 = (var10 % 4 * 16 + 0) / 64.0F;
      float var12 = (var10 % 4 * 16 + 16) / 64.0F;
      float var13 = (var10 / 4 * 16 + 0) / 64.0F;
      float var14 = (var10 / 4 * 16 + 16) / 64.0F;
      float var15 = 1.0F;
      float var16 = 0.5F;
      float var17 = 0.25F;
      int var18 = var1.b(var9);
      int var19 = var18 % 65536;
      int var20 = var18 / 65536;
      OpenGlHelper.a(OpenGlHelper.r, var19 / 1.0F, var20 / 1.0F);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      float var26 = 255.0F;
      float var27 = (var1.a + var9) / 2.0F;
      var20 = (int)((MathHelper.a(var27 + 0.0F) + 1.0F) * 0.5F * 255.0F);
      short var21 = 255;
      int var22 = (int)((MathHelper.a(var27 + (float) (Math.PI * 4.0 / 3.0)) + 1.0F) * 0.1F * 255.0F);
      GlStateManager.b(180.0F - this.b.e, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(-this.b.f, 1.0F, 0.0F, 0.0F);
      float var23 = 0.3F;
      GlStateManager.a(0.3F, 0.3F, 0.3F);
      Tessellator var24 = Tessellator.a();
      WorldRenderer var25 = var24.c();
      var25.a(7, DefaultVertexFormats.l);
      var25.b((double)(0.0F - var16), (double)(0.0F - var17), 0.0).a((double)var11, (double)var14).b(var20, 255, var22, 128).c(0.0F, 1.0F, 0.0F).d();
      var25.b((double)(var15 - var16), (double)(0.0F - var17), 0.0).a((double)var12, (double)var14).b(var20, 255, var22, 128).c(0.0F, 1.0F, 0.0F).d();
      var25.b((double)(var15 - var16), (double)(1.0F - var17), 0.0).a((double)var12, (double)var13).b(var20, 255, var22, 128).c(0.0F, 1.0F, 0.0F).d();
      var25.b((double)(0.0F - var16), (double)(1.0F - var17), 0.0).a((double)var11, (double)var13).b(var20, 255, var22, 128).c(0.0F, 1.0F, 0.0F).d();
      var24.b();
      GlStateManager.k();
      GlStateManager.C();
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityXPOrb var1) {
      return a;
   }
}
