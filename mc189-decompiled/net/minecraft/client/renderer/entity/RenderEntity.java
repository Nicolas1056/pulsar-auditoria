package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

public class RenderEntity extends Render<Entity> {
   public RenderEntity(RenderManager var1) {
      super(var1);
   }

   @Override
   public void a(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      a(var1.aR(), var2 - var1.P, var4 - var1.Q, var6 - var1.R);
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   @Override
   protected ResourceLocation a(Entity var1) {
      return null;
   }
}
