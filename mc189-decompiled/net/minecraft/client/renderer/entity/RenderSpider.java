package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSpider;
import net.minecraft.client.renderer.entity.layers.LayerSpiderEyes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.util.ResourceLocation;

public class RenderSpider<T extends EntitySpider> extends RenderLiving<T> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/spider/spider.png");

   public RenderSpider(RenderManager var1) {
      super(var1, new ModelSpider(), 1.0F);
      this.a(new LayerSpiderEyes(this));
   }

   protected float b(T var1) {
      return 180.0F;
   }

   protected ResourceLocation a(T var1) {
      return a;
   }
}
