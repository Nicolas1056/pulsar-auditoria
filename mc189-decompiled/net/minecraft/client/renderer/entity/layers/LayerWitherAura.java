package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class LayerWitherAura implements LayerRenderer<EntityWither> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/wither/wither_armor.png");
   private final RenderWither b;
   private final ModelWither c = new ModelWither(0.5F);

   public LayerWitherAura(RenderWither var1) {
      this.b = var1;
   }

   public void a(EntityWither var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.cm()) {
         GlStateManager.a(!var1.ax());
         this.b.a(a);
         GlStateManager.n(5890);
         GlStateManager.D();
         float var9 = var1.W + var4;
         float var10 = MathHelper.b(var9 * 0.02F) * 3.0F;
         float var11 = var9 * 0.01F;
         GlStateManager.b(var10, var11, 0.0F);
         GlStateManager.n(5888);
         GlStateManager.l();
         float var12 = 0.5F;
         GlStateManager.c(var12, var12, var12, 1.0F);
         GlStateManager.f();
         GlStateManager.b(1, 1);
         this.c.a(var1, var2, var3, var4);
         this.c.a(this.b.b());
         this.c.a(var1, var2, var3, var5, var6, var7, var8);
         GlStateManager.n(5890);
         GlStateManager.D();
         GlStateManager.n(5888);
         GlStateManager.e();
         GlStateManager.k();
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
