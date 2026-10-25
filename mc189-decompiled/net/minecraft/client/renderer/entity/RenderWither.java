package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerWitherAura;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityWither;
import net.minecraft.util.ResourceLocation;

public class RenderWither extends RenderLiving<EntityWither> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/wither/wither_invulnerable.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/wither/wither.png");

   public RenderWither(RenderManager var1) {
      super(var1, new ModelWither(0.0F), 1.0F);
      this.a(new LayerWitherAura(this));
   }

   public void a(EntityWither var1, double var2, double var4, double var6, float var8, float var9) {
      BossStatus.a(var1, true);
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityWither var1) {
      int var2 = var1.cl();
      return var2 > 0 && (var2 > 80 || var2 / 5 % 2 != 1) ? a : e;
   }

   protected void a(EntityWither var1, float var2) {
      float var3 = 2.0F;
      int var4 = var1.cl();
      if (var4 > 0) {
         var3 -= (var4 - var2) / 220.0F * 0.5F;
      }

      GlStateManager.a(var3, var3, var3);
   }
}
