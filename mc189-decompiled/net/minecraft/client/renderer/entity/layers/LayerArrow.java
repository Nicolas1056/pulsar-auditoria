package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.MathHelper;

public class LayerArrow implements LayerRenderer<EntityLivingBase> {
   private final RendererLivingEntity a;

   public LayerArrow(RendererLivingEntity var1) {
      this.a = var1;
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      int var9 = var1.bv();
      if (var9 > 0) {
         EntityArrow var10 = new EntityArrow(var1.o, var1.s, var1.t, var1.u);
         Random var11 = new Random(var1.F());
         RenderHelper.a();

         for (int var12 = 0; var12 < var9; var12++) {
            GlStateManager.E();
            ModelRenderer var13 = this.a.b().a(var11);
            ModelBox var14 = var13.l.get(var11.nextInt(var13.l.size()));
            var13.c(0.0625F);
            float var15 = var11.nextFloat();
            float var16 = var11.nextFloat();
            float var17 = var11.nextFloat();
            float var18 = (var14.a + (var14.d - var14.a) * var15) / 16.0F;
            float var19 = (var14.b + (var14.e - var14.b) * var16) / 16.0F;
            float var20 = (var14.c + (var14.f - var14.c) * var17) / 16.0F;
            GlStateManager.b(var18, var19, var20);
            var15 = var15 * 2.0F - 1.0F;
            var16 = var16 * 2.0F - 1.0F;
            var17 = var17 * 2.0F - 1.0F;
            var15 *= -1.0F;
            var16 *= -1.0F;
            var17 *= -1.0F;
            float var21 = MathHelper.c(var15 * var15 + var17 * var17);
            var10.A = var10.y = (float)(Math.atan2(var15, var17) * 180.0 / (float) Math.PI);
            var10.B = var10.z = (float)(Math.atan2(var16, var21) * 180.0 / (float) Math.PI);
            double var22 = 0.0;
            double var24 = 0.0;
            double var26 = 0.0;
            this.a.d().a(var10, var22, var24, var26, 0.0F, var4);
            GlStateManager.F();
         }

         RenderHelper.b();
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
