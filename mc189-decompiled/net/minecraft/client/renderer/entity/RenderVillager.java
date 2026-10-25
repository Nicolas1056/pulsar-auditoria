package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelVillager;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.util.ResourceLocation;

public class RenderVillager extends RenderLiving<EntityVillager> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/villager/villager.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/villager/farmer.png");
   private static final ResourceLocation j = new ResourceLocation("textures/entity/villager/librarian.png");
   private static final ResourceLocation k = new ResourceLocation("textures/entity/villager/priest.png");
   private static final ResourceLocation l = new ResourceLocation("textures/entity/villager/smith.png");
   private static final ResourceLocation m = new ResourceLocation("textures/entity/villager/butcher.png");

   public RenderVillager(RenderManager var1) {
      super(var1, new ModelVillager(0.0F), 0.5F);
      this.a(new LayerCustomHead(this.g().a));
   }

   public ModelVillager g() {
      return (ModelVillager)super.b();
   }

   protected ResourceLocation a(EntityVillager var1) {
      switch (var1.cl()) {
         case 0:
            return e;
         case 1:
            return j;
         case 2:
            return k;
         case 3:
            return l;
         case 4:
            return m;
         default:
            return a;
      }
   }

   protected void a(EntityVillager var1, float var2) {
      float var3 = 0.9375F;
      if (var1.l() < 0) {
         var3 = (float)(var3 * 0.5);
         this.c = 0.25F;
      } else {
         this.c = 0.5F;
      }

      GlStateManager.a(var3, var3, var3);
   }
}
