package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.util.ResourceLocation;

public class RenderSquid extends RenderLiving<EntitySquid> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/squid.png");

   public RenderSquid(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected ResourceLocation a(EntitySquid var1) {
      return a;
   }

   protected void a(EntitySquid var1, float var2, float var3, float var4) {
      float var5 = var1.b + (var1.a - var1.b) * var4;
      float var6 = var1.bk + (var1.c - var1.bk) * var4;
      GlStateManager.b(0.0F, 0.5F, 0.0F);
      GlStateManager.b(180.0F - var3, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(var5, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(var6, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(0.0F, -1.2F, 0.0F);
   }

   protected float a(EntitySquid var1, float var2) {
      return var1.bo + (var1.bn - var1.bo) * var2;
   }
}
