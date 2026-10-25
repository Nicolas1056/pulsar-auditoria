package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBoat;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderBoat extends Render<EntityBoat> {
   private static final ResourceLocation e = new ResourceLocation("textures/entity/boat.png");
   protected ModelBase a = new ModelBoat();

   public RenderBoat(RenderManager var1) {
      super(var1);
      this.c = 0.5F;
   }

   public void a(EntityBoat var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4 + 0.25F, (float)var6);
      GlStateManager.b(180.0F - var8, 0.0F, 1.0F, 0.0F);
      float var10 = var1.l() - var9;
      float var11 = var1.j() - var9;
      if (var11 < 0.0F) {
         var11 = 0.0F;
      }

      if (var10 > 0.0F) {
         GlStateManager.b(MathHelper.a(var10) * var10 * var11 / 10.0F * var1.m(), 1.0F, 0.0F, 0.0F);
      }

      float var12 = 0.75F;
      GlStateManager.a(var12, var12, var12);
      GlStateManager.a(1.0F / var12, 1.0F / var12, 1.0F / var12);
      this.c(var1);
      GlStateManager.a(-1.0F, -1.0F, 1.0F);
      this.a.a(var1, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityBoat var1) {
      return e;
   }
}
