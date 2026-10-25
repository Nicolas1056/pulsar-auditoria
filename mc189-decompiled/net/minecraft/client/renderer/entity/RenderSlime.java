package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerSlimeGel;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.util.ResourceLocation;

public class RenderSlime extends RenderLiving<EntitySlime> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/slime/slime.png");

   public RenderSlime(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
      this.a(new LayerSlimeGel(this));
   }

   public void a(EntitySlime var1, double var2, double var4, double var6, float var8, float var9) {
      this.c = 0.25F * var1.cm();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected void a(EntitySlime var1, float var2) {
      float var3 = var1.cm();
      float var4 = (var1.c + (var1.b - var1.c) * var2) / (var3 * 0.5F + 1.0F);
      float var5 = 1.0F / (var4 + 1.0F);
      GlStateManager.a(var5 * var3, 1.0F / var5 * var3, var5 * var3);
   }

   protected ResourceLocation a(EntitySlime var1) {
      return a;
   }
}
