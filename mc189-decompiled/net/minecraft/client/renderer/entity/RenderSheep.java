package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.layers.LayerSheepWool;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

public class RenderSheep extends RenderLiving<EntitySheep> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/sheep/sheep.png");

   public RenderSheep(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
      this.a(new LayerSheepWool(this));
   }

   protected ResourceLocation a(EntitySheep var1) {
      return a;
   }
}
