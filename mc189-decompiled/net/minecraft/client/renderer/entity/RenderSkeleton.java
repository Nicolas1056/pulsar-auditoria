package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelSkeleton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.util.ResourceLocation;

public class RenderSkeleton extends RenderBiped<EntitySkeleton> {
   private static final ResourceLocation j = new ResourceLocation("textures/entity/skeleton/skeleton.png");
   private static final ResourceLocation k = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");

   public RenderSkeleton(RenderManager var1) {
      super(var1, new ModelSkeleton(), 0.5F);
      this.a(new LayerHeldItem(this));
      this.a(new LayerBipedArmor(this) {
         @Override
         protected void a() {
            this.c = new ModelSkeleton(0.5F, true);
            this.d = new ModelSkeleton(1.0F, true);
         }
      });
   }

   protected void a(EntitySkeleton var1, float var2) {
      if (var1.cm() == 1) {
         GlStateManager.a(1.2F, 1.2F, 1.2F);
      }
   }

   @Override
   public void C_() {
      GlStateManager.b(0.09375F, 0.1875F, 0.0F);
   }

   protected ResourceLocation a(EntitySkeleton var1) {
      return var1.cm() == 1 ? k : j;
   }
}
