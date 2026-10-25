package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;

public class ModelEnderCrystal extends ModelBase {
   private ModelRenderer a;
   private ModelRenderer b = new ModelRenderer(this, "glass");
   private ModelRenderer c;

   public ModelEnderCrystal(float var1, boolean var2) {
      this.b.a(0, 0).a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
      this.a = new ModelRenderer(this, "cube");
      this.a.a(32, 0).a(-4.0F, -4.0F, -4.0F, 8, 8, 8);
      if (var2) {
         this.c = new ModelRenderer(this, "base");
         this.c.a(0, 16).a(-6.0F, 0.0F, -6.0F, 12, 4, 12);
      }
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GlStateManager.E();
      GlStateManager.a(2.0F, 2.0F, 2.0F);
      GlStateManager.b(0.0F, -0.5F, 0.0F);
      if (this.c != null) {
         this.c.a(var7);
      }

      GlStateManager.b(var3, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(0.0F, 0.8F + var4, 0.0F);
      GlStateManager.b(60.0F, 0.7071F, 0.0F, 0.7071F);
      this.b.a(var7);
      float var8 = 0.875F;
      GlStateManager.a(var8, var8, var8);
      GlStateManager.b(60.0F, 0.7071F, 0.0F, 0.7071F);
      GlStateManager.b(var3, 0.0F, 1.0F, 0.0F);
      this.b.a(var7);
      GlStateManager.a(var8, var8, var8);
      GlStateManager.b(60.0F, 0.7071F, 0.0F, 0.7071F);
      GlStateManager.b(var3, 0.0F, 1.0F, 0.0F);
      this.a.a(var7);
      GlStateManager.F();
   }
}
