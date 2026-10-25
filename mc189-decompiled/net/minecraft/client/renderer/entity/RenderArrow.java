package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderArrow extends Render<EntityArrow> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/arrow.png");

   public RenderArrow(RenderManager var1) {
      super(var1);
   }

   public void a(EntityArrow var1, double var2, double var4, double var6, float var8, float var9) {
      this.c(var1);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      GlStateManager.b(var1.A + (var1.y - var1.A) * var9 - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(var1.B + (var1.z - var1.B) * var9, 0.0F, 0.0F, 1.0F);
      Tessellator var10 = Tessellator.a();
      WorldRenderer var11 = var10.c();
      byte var12 = 0;
      float var13 = 0.0F;
      float var14 = 0.5F;
      float var15 = (0 + var12 * 10) / 32.0F;
      float var16 = (5 + var12 * 10) / 32.0F;
      float var17 = 0.0F;
      float var18 = 0.15625F;
      float var19 = (5 + var12 * 10) / 32.0F;
      float var20 = (10 + var12 * 10) / 32.0F;
      float var21 = 0.05625F;
      GlStateManager.B();
      float var22 = var1.b - var9;
      if (var22 > 0.0F) {
         float var23 = -MathHelper.a(var22 * 3.0F) * var22;
         GlStateManager.b(var23, 0.0F, 0.0F, 1.0F);
      }

      GlStateManager.b(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.a(var21, var21, var21);
      GlStateManager.b(-4.0F, 0.0F, 0.0F);
      GL11.glNormal3f(var21, 0.0F, 0.0F);
      var11.a(7, DefaultVertexFormats.g);
      var11.b(-7.0, -2.0, -2.0).a((double)var17, (double)var19).d();
      var11.b(-7.0, -2.0, 2.0).a((double)var18, (double)var19).d();
      var11.b(-7.0, 2.0, 2.0).a((double)var18, (double)var20).d();
      var11.b(-7.0, 2.0, -2.0).a((double)var17, (double)var20).d();
      var10.b();
      GL11.glNormal3f(-var21, 0.0F, 0.0F);
      var11.a(7, DefaultVertexFormats.g);
      var11.b(-7.0, 2.0, -2.0).a((double)var17, (double)var19).d();
      var11.b(-7.0, 2.0, 2.0).a((double)var18, (double)var19).d();
      var11.b(-7.0, -2.0, 2.0).a((double)var18, (double)var20).d();
      var11.b(-7.0, -2.0, -2.0).a((double)var17, (double)var20).d();
      var10.b();

      for (int var24 = 0; var24 < 4; var24++) {
         GlStateManager.b(90.0F, 1.0F, 0.0F, 0.0F);
         GL11.glNormal3f(0.0F, 0.0F, var21);
         var11.a(7, DefaultVertexFormats.g);
         var11.b(-8.0, -2.0, 0.0).a((double)var13, (double)var15).d();
         var11.b(8.0, -2.0, 0.0).a((double)var14, (double)var15).d();
         var11.b(8.0, 2.0, 0.0).a((double)var14, (double)var16).d();
         var11.b(-8.0, 2.0, 0.0).a((double)var13, (double)var16).d();
         var10.b();
      }

      GlStateManager.C();
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityArrow var1) {
      return a;
   }
}
