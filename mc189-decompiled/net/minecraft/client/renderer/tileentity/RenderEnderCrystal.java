package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelEnderCrystal;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderEnderCrystal extends Render<EntityEnderCrystal> {
   private static final ResourceLocation a = new ResourceLocation("textures/entity/endercrystal/endercrystal.png");
   private ModelBase e = new ModelEnderCrystal(0.0F, true);

   public RenderEnderCrystal(RenderManager var1) {
      super(var1);
      this.c = 0.5F;
   }

   public void a(EntityEnderCrystal var1, double var2, double var4, double var6, float var8, float var9) {
      float var10 = var1.a + var9;
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      this.a(a);
      float var11 = MathHelper.a(var10 * 0.2F) / 2.0F + 0.5F;
      var11 = var11 * var11 + var11;
      this.e.a(var1, 0.0F, var10 * 3.0F, var11 * 0.2F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityEnderCrystal var1) {
      return a;
   }
}
