package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityBat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderBat extends RenderLiving<EntityBat> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/bat.png");

   public RenderBat(RenderManager var1) {
      super(var1, new ModelBat(), 0.25F);
   }

   protected ResourceLocation a(EntityBat var1) {
      return a;
   }

   protected void a(EntityBat var1, float var2) {
      GlStateManager.a(0.35F, 0.35F, 0.35F);
   }

   protected void a(EntityBat var1, float var2, float var3, float var4) {
      if (!var1.n()) {
         GlStateManager.b(0.0F, MathHelper.b(var2 * 0.3F) * 0.1F, 0.0F);
      } else {
         GlStateManager.b(0.0F, -0.1F, 0.0F);
      }

      super.a(var1, var2, var3, var4);
   }
}
