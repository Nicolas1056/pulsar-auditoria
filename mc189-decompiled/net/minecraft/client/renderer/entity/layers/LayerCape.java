package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.util.MathHelper;

public class LayerCape implements LayerRenderer<AbstractClientPlayer> {
   private final RenderPlayer a;

   public LayerCape(RenderPlayer var1) {
      this.a = var1;
   }

   public void a(AbstractClientPlayer var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.a() && !var1.ax() && var1.a(EnumPlayerModelParts.a) && var1.k() != null) {
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.a.a(var1.k());
         GlStateManager.E();
         GlStateManager.b(0.0F, 0.0F, 0.125F);
         double var9 = var1.bq + (var1.bt - var1.bq) * var4 - (var1.p + (var1.s - var1.p) * var4);
         double var11 = var1.br + (var1.bu - var1.br) * var4 - (var1.q + (var1.t - var1.q) * var4);
         double var13 = var1.bs + (var1.bv - var1.bs) * var4 - (var1.r + (var1.u - var1.r) * var4);
         float var15 = var1.aJ + (var1.aI - var1.aJ) * var4;
         double var16 = MathHelper.a(var15 * (float) Math.PI / 180.0F);
         double var18 = -MathHelper.b(var15 * (float) Math.PI / 180.0F);
         float var20 = (float)var11 * 10.0F;
         var20 = MathHelper.a(var20, -6.0F, 32.0F);
         float var21 = (float)(var9 * var16 + var13 * var18) * 100.0F;
         float var22 = (float)(var9 * var18 - var13 * var16) * 100.0F;
         if (var21 < 0.0F) {
            var21 = 0.0F;
         }

         float var23 = var1.bn + (var1.bo - var1.bn) * var4;
         var20 += MathHelper.a((var1.L + (var1.M - var1.L) * var4) * 6.0F) * 32.0F * var23;
         if (var1.av()) {
            var20 += 25.0F;
         }

         GlStateManager.b(6.0F + var21 / 2.0F + var20, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(var22 / 2.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.b(-var22 / 2.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
         this.a.g().c(0.0625F);
         GlStateManager.F();
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
