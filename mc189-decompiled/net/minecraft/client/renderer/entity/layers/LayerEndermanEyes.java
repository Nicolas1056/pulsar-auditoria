package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.ResourceLocation;

public class LayerEndermanEyes implements LayerRenderer<EntityEnderman> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
   private final RenderEnderman b;

   public LayerEndermanEyes(RenderEnderman var1) {
      this.b = var1;
   }

   public void a(EntityEnderman var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.b.a(a);
      GlStateManager.l();
      GlStateManager.c();
      GlStateManager.b(1, 1);
      GlStateManager.f();
      GlStateManager.a(!var1.ax());
      char var9 = '\uf0f0';
      int var10 = var9 % 65536;
      int var11 = var9 / 65536;
      OpenGlHelper.a(OpenGlHelper.r, var10 / 1.0F, var11 / 1.0F);
      GlStateManager.e();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      this.b.b().a(var1, var2, var3, var5, var6, var7, var8);
      this.b.a(var1, var4);
      GlStateManager.a(true);
      GlStateManager.k();
      GlStateManager.d();
   }

   @Override
   public boolean b() {
      return false;
   }
}
