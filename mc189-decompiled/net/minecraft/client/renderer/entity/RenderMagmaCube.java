package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelMagmaCube;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityMagmaCube;
import net.minecraft.util.ResourceLocation;

public class RenderMagmaCube extends RenderLiving<EntityMagmaCube> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/slime/magmacube.png");

   public RenderMagmaCube(RenderManager var1) {
      super(var1, new ModelMagmaCube(), 0.25F);
   }

   protected ResourceLocation a(EntityMagmaCube var1) {
      return a;
   }

   protected void a(EntityMagmaCube var1, float var2) {
      int var3 = var1.cm();
      float var4 = (var1.c + (var1.b - var1.c) * var2) / (var3 * 0.5F + 1.0F);
      float var5 = 1.0F / (var4 + 1.0F);
      float var6 = var3;
      GlStateManager.a(var5 * var6, 1.0F / var5 * var6, var5 * var6);
   }
}
