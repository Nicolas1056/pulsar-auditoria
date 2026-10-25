package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWitch;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerHeldItemWitch;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.util.ResourceLocation;

public class RenderWitch extends RenderLiving<EntityWitch> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/witch.png");

   public RenderWitch(RenderManager var1) {
      super(var1, new ModelWitch(0.0F), 0.5F);
      this.a(new LayerHeldItemWitch(this));
   }

   public void a(EntityWitch var1, double var2, double var4, double var6, float var8, float var9) {
      ((ModelWitch)this.f).g = var1.bA() != null;
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityWitch var1) {
      return a;
   }

   @Override
   public void C_() {
      GlStateManager.b(0.0F, 0.1875F, 0.0F);
   }

   protected void a(EntityWitch var1, float var2) {
      float var3 = 0.9375F;
      GlStateManager.a(var3, var3, var3);
   }
}
