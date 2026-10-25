package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelLeashKnot;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.util.ResourceLocation;

public class RenderLeashKnot extends Render<EntityLeashKnot> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/lead_knot.png");
   private ModelLeashKnot e = new ModelLeashKnot();

   public RenderLeashKnot(RenderManager var1) {
      super(var1);
   }

   public void a(EntityLeashKnot var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      GlStateManager.p();
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      float var10 = 0.0625F;
      GlStateManager.B();
      GlStateManager.a(-1.0F, -1.0F, 1.0F);
      GlStateManager.d();
      this.c(var1);
      this.e.a(var1, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, var10);
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityLeashKnot var1) {
      return a;
   }
}
