package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelEnderMite;
import net.minecraft.entity.monster.EntityEndermite;
import net.minecraft.util.ResourceLocation;

public class RenderEndermite extends RenderLiving<EntityEndermite> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/endermite.png");

   public RenderEndermite(RenderManager var1) {
      super(var1, new ModelEnderMite(), 0.3F);
   }

   protected float a(EntityEndermite var1) {
      return 180.0F;
   }

   protected ResourceLocation b(EntityEndermite var1) {
      return a;
   }
}
