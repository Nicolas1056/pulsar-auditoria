package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.util.ResourceLocation;

public class LayerCreeperCharge implements LayerRenderer<EntityCreeper> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
   private final RenderCreeper b;
   private final ModelCreeper c = new ModelCreeper(2.0F);

   public LayerCreeperCharge(RenderCreeper var1) {
      this.b = var1;
   }

   public void a(EntityCreeper var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.n()) {
         boolean var9 = var1.ax();
         GlStateManager.a(!var9);
         this.b.a(a);
         GlStateManager.n(5890);
         GlStateManager.D();
         float var10 = var1.W + var4;
         GlStateManager.b(var10 * 0.01F, var10 * 0.01F, 0.0F);
         GlStateManager.n(5888);
         GlStateManager.l();
         float var11 = 0.5F;
         GlStateManager.c(var11, var11, var11, 1.0F);
         GlStateManager.f();
         GlStateManager.b(1, 1);
         this.c.a(this.b.b());
         this.c.a(var1, var2, var3, var5, var6, var7, var8);
         GlStateManager.n(5890);
         GlStateManager.D();
         GlStateManager.n(5888);
         GlStateManager.e();
         GlStateManager.k();
         GlStateManager.a(var9);
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
