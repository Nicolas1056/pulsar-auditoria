package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderBiped<T extends EntityLiving> extends RenderLiving<T> {
   private static final ResourceLocation j = new ResourceLocation("textures/entity/steve.png");
   protected ModelBiped a;
   protected float e;

   public RenderBiped(RenderManager var1, ModelBiped var2, float var3) {
      this(var1, var2, var3, 1.0F);
      this.a(new LayerHeldItem(this));
   }

   public RenderBiped(RenderManager var1, ModelBiped var2, float var3, float var4) {
      super(var1, var2, var3);
      this.a = var2;
      this.e = var4;
      this.a(new LayerCustomHead(var2.e));
   }

   protected ResourceLocation a(T var1) {
      return j;
   }

   @Override
   public void C_() {
      GlStateManager.b(0.0F, 0.1875F, 0.0F);
   }
}
