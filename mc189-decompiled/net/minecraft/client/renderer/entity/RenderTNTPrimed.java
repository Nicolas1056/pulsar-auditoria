package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderTNTPrimed extends Render<EntityTNTPrimed> {
   public RenderTNTPrimed(RenderManager var1) {
      super(var1);
      this.c = 0.5F;
   }

   public void a(EntityTNTPrimed var1, double var2, double var4, double var6, float var8, float var9) {
      BlockRendererDispatcher var10 = Minecraft.A().ae();
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4 + 0.5F, (float)var6);
      if (var1.a - var9 + 1.0F < 10.0F) {
         float var11 = 1.0F - (var1.a - var9 + 1.0F) / 10.0F;
         var11 = MathHelper.a(var11, 0.0F, 1.0F);
         var11 *= var11;
         var11 *= var11;
         float var12 = 1.0F + var11 * 0.3F;
         GlStateManager.a(var12, var12, var12);
      }

      float var16 = (1.0F - (var1.a - var9 + 1.0F) / 100.0F) * 0.8F;
      this.c(var1);
      GlStateManager.b(-0.5F, -0.5F, 0.5F);
      var10.a(Blocks.W.Q(), var1.c(var9));
      GlStateManager.b(0.0F, 0.0F, 1.0F);
      if (var1.a / 5 % 2 == 0) {
         GlStateManager.x();
         GlStateManager.f();
         GlStateManager.l();
         GlStateManager.b(770, 772);
         GlStateManager.c(1.0F, 1.0F, 1.0F, var16);
         GlStateManager.a(-3.0F, -3.0F);
         GlStateManager.q();
         var10.a(Blocks.W.Q(), 1.0F);
         GlStateManager.a(0.0F, 0.0F);
         GlStateManager.r();
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.k();
         GlStateManager.e();
         GlStateManager.w();
      }

      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityTNTPrimed var1) {
      return TextureMap.g;
   }
}
