package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelZombieVillager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

public class LayerVillagerArmor extends LayerBipedArmor {
   public LayerVillagerArmor(RendererLivingEntity<?> var1) {
      super(var1);
   }

   @Override
   protected void a() {
      this.c = new ModelZombieVillager(0.5F, 0.0F, true);
      this.d = new ModelZombieVillager(1.0F, 0.0F, true);
   }
}
