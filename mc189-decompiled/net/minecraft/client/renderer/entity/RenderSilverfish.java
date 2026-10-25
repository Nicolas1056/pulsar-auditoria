package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSilverfish;
import net.minecraft.entity.monster.EntitySilverfish;
import net.minecraft.util.ResourceLocation;

public class RenderSilverfish extends RenderLiving<EntitySilverfish> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/silverfish.png");

   public RenderSilverfish(RenderManager var1) {
      super(var1, new ModelSilverfish(), 0.3F);
   }

   protected float a(EntitySilverfish var1) {
      return 180.0F;
   }

   protected ResourceLocation b(EntitySilverfish var1) {
      return a;
   }
}
