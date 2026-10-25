package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelArmorStand;
import net.minecraft.client.model.ModelArmorStandArmor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerCustomHead;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.util.ResourceLocation;

public class ArmorStandRenderer extends RendererLivingEntity<EntityArmorStand> {
   public static final ResourceLocation a = new ResourceLocation("textures/entity/armorstand/wood.png");

   public ArmorStandRenderer(RenderManager var1) {
      super(var1, new ModelArmorStand(), 0.0F);
      LayerBipedArmor var2 = new LayerBipedArmor(this) {
         @Override
         protected void a() {
            this.c = new ModelArmorStandArmor(0.5F);
            this.d = new ModelArmorStandArmor(1.0F);
         }
      };
      this.a(var2);
      this.a(new LayerHeldItem(this));
      this.a(new LayerCustomHead(this.a().e));
   }

   protected ResourceLocation a(EntityArmorStand var1) {
      return a;
   }

   public ModelArmorStand a() {
      return (ModelArmorStand)super.b();
   }

   protected void a(EntityArmorStand var1, float var2, float var3, float var4) {
      GlStateManager.b(180.0F - var3, 0.0F, 1.0F, 0.0F);
   }

   protected boolean b(EntityArmorStand var1) {
      return var1.aN();
   }
}
