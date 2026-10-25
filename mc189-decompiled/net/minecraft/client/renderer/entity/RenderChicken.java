package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderChicken extends RenderLiving<EntityChicken> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/chicken.png");

   public RenderChicken(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected ResourceLocation a(EntityChicken var1) {
      return a;
   }

   protected float a(EntityChicken var1, float var2) {
      float var3 = var1.bq + (var1.bm - var1.bq) * var2;
      float var4 = var1.bp + (var1.bo - var1.bp) * var2;
      return (MathHelper.a(var3) + 1.0F) * var4;
   }
}
