package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.util.ResourceLocation;

public class RenderPigZombie extends RenderBiped<EntityPigZombie> {
   private static final ResourceLocation j = new ResourceLocation("textures/entity/zombie_pigman.png");

   public RenderPigZombie(RenderManager var1) {
      super(var1, new ModelZombie(), 0.5F, 1.0F);
      this.a(new LayerHeldItem(this));
      this.a(new LayerBipedArmor(this) {
         @Override
         protected void a() {
            this.c = new ModelZombie(0.5F, true);
            this.d = new ModelZombie(1.0F, true);
         }
      });
   }

   protected ResourceLocation a(EntityPigZombie var1) {
      return j;
   }
}
