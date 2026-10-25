package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;

public class LayerSaddle implements LayerRenderer<EntityPig> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/pig/pig_saddle.png");
   private final RenderPig b;
   private final ModelPig c = new ModelPig(0.5F);

   public LayerSaddle(RenderPig var1) {
      this.b = var1;
   }

   public void a(EntityPig var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.cl()) {
         this.b.a(a);
         this.c.a(this.b.b());
         this.c.a(var1, var2, var3, var5, var6, var7, var8);
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
