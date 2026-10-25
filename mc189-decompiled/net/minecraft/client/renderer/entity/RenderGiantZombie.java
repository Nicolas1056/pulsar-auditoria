package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelZombie;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.monster.EntityGiantZombie;
import net.minecraft.util.ResourceLocation;

public class RenderGiantZombie extends RenderLiving<EntityGiantZombie> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/zombie/zombie.png");
   private float e;

   public RenderGiantZombie(RenderManager var1, ModelBase var2, float var3, float var4) {
      super(var1, var2, var3 * var4);
      this.e = var4;
      this.a(new LayerHeldItem(this));
      this.a(new LayerBipedArmor(this) {
         @Override
         protected void a() {
            this.c = new ModelZombie(0.5F, true);
            this.d = new ModelZombie(1.0F, true);
         }
      });
   }

   @Override
   public void C_() {
      GlStateManager.b(0.0F, 0.1875F, 0.0F);
   }

   protected void a(EntityGiantZombie var1, float var2) {
      GlStateManager.a(this.e, this.e, this.e);
   }

   protected ResourceLocation a(EntityGiantZombie var1) {
      return a;
   }
}
