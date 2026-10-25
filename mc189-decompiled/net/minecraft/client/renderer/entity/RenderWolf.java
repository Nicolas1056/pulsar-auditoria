package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWolfCollar;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;

public class RenderWolf extends RenderLiving<EntityWolf> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/wolf/wolf.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
   private static final ResourceLocation j = new ResourceLocation("textures/entity/wolf/wolf_angry.png");

   public RenderWolf(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
      this.a(new LayerWolfCollar(this));
   }

   protected float a(EntityWolf var1, float var2) {
      return var1.cu();
   }

   public void a(EntityWolf var1, double var2, double var4, double var6, float var8, float var9) {
      if (var1.ct()) {
         float var10 = var1.c(var9) * var1.p(var9);
         GlStateManager.c(var10, var10, var10);
      }

      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityWolf var1) {
      if (var1.cl()) {
         return e;
      } else {
         return var1.cv() ? j : a;
      }
   }
}
