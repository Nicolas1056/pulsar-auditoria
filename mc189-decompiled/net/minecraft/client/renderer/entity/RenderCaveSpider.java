package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.monster.EntityCaveSpider;
import net.minecraft.util.ResourceLocation;

public class RenderCaveSpider extends RenderSpider<EntityCaveSpider> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/spider/cave_spider.png");

   public RenderCaveSpider(RenderManager var1) {
      super(var1);
      this.c *= 0.7F;
   }

   protected void a(EntityCaveSpider var1, float var2) {
      GlStateManager.a(0.7F, 0.7F, 0.7F);
   }

   protected ResourceLocation a(EntityCaveSpider var1) {
      return a;
   }
}
