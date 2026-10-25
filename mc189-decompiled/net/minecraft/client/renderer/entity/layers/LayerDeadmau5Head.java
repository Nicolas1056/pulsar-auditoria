package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;

public class LayerDeadmau5Head implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer a;

   public LayerDeadmau5Head(RenderPlayer var1) {
      this.a = var1;
   }

   public void a(AbstractClientPlayer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.e_().equals("deadmau5") && var1.g() && !var1.ax()) {
         this.a.a(var1.i());

         for (int var9 = 0; var9 < 2; var9++) {
            float var10 = var1.A + (var1.y - var1.A) * var4 - (var1.aJ + (var1.aI - var1.aJ) * var4);
            float var11 = var1.B + (var1.z - var1.B) * var4;
            GlStateManager.E();
            GlStateManager.b(var10, 0.0F, 1.0F, 0.0F);
            GlStateManager.b(var11, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(0.375F * (var9 * 2 - 1), 0.0F, 0.0F);
            GlStateManager.b(0.0F, -0.375F, 0.0F);
            GlStateManager.b(-var11, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(-var10, 0.0F, 1.0F, 0.0F);
            float var12 = 1.3333334F;
            GlStateManager.a(var12, var12, var12);
            this.a.g().b(0.0625F);
            GlStateManager.F();
         }
      }
   }

   @Override
   public boolean b() {
      return true;
   }
}
