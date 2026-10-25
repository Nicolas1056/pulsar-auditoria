package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSnowMan;
import net.minecraft.client.renderer.entity.layers.LayerSnowmanHead;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.util.ResourceLocation;

public class RenderSnowMan extends RenderLiving<EntitySnowman> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/snowman.png");

   public RenderSnowMan(RenderManager var1) {
      super(var1, new ModelSnowMan(), 0.5F);
      this.a(new LayerSnowmanHead(this));
   }

   protected ResourceLocation a(EntitySnowman var1) {
      return a;
   }

   public ModelSnowMan g() {
      return (ModelSnowMan)super.b();
   }
}
