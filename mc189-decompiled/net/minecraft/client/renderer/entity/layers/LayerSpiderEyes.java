package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class LayerSpiderEyes implements LayerRenderer<EntitySpider> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/spider_eyes.png");
   private final RenderSpider b;

   public LayerSpiderEyes(RenderSpider var1) {
      this.b = var1;
   }

   public void a(EntitySpider var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.b.a(a);
      GlStateManager.l();
      GlStateManager.c();
      GlStateManager.b(1, 1);
      if (var1.ax()) {
         GlStateManager.a(false);
      } else {
         GlStateManager.a(true);
      }

      int var9 = 61680;
      int var10 = var9 % 65536;
      int var11 = var9 / 65536;
      OpenGlHelper.a(OpenGlHelper.r, var10 / 1.0F, var11 / 1.0F);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.b.b().a(var1, var2, var3, var5, var6, var7, var8);
      var9 = var1.b(var4);
      var10 = var9 % 65536;
      var11 = var9 / 65536;
      OpenGlHelper.a(OpenGlHelper.r, var10 / 1.0F, var11 / 1.0F);
      this.b.a(var1, var4);
      GlStateManager.k();
      GlStateManager.d();
   }

   @Override
   public boolean b() {
      return false;
   }
}
