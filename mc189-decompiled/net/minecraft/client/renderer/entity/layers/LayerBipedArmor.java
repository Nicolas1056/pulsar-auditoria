package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RendererLivingEntity;

public class LayerBipedArmor extends LayerArmorBase<ModelBiped> {
   public LayerBipedArmor(RendererLivingEntity<?> var1) {
      super(var1);
   }

   @Override
   protected void a() {
      this.c = new ModelBiped(0.5F);
      this.d = new ModelBiped(1.0F);
   }

   protected void a(ModelBiped var1, int var2) {
      this.a(var1);
      switch (var2) {
         case 1:
            var1.j.j = true;
            var1.k.j = true;
            break;
         case 2:
            var1.g.j = true;
            var1.j.j = true;
            var1.k.j = true;
            break;
         case 3:
            var1.g.j = true;
            var1.h.j = true;
            var1.i.j = true;
            break;
         case 4:
            var1.e.j = true;
            var1.f.j = true;
      }
   }

   protected void a(ModelBiped var1) {
      var1.a(false);
   }
}
