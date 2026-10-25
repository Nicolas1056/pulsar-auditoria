package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;

public class RenderOcelot extends RenderLiving<EntityOcelot> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/cat/black.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/cat/ocelot.png");
   private static final ResourceLocation j = new ResourceLocation("textures/entity/cat/red.png");
   private static final ResourceLocation k = new ResourceLocation("textures/entity/cat/siamese.png");

   public RenderOcelot(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected ResourceLocation a(EntityOcelot var1) {
      switch (var1.ct()) {
         case 0:
         default:
            return e;
         case 1:
            return a;
         case 2:
            return j;
         case 3:
            return k;
      }
   }

   protected void a(EntityOcelot var1, float var2) {
      super.a(var1, var2);
      if (var1.cl()) {
         GlStateManager.a(0.8F, 0.8F, 0.8F);
      }
   }
}
