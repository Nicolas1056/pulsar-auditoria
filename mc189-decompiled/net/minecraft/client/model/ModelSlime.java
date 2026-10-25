package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelSlime extends ModelBase {
   ModelRenderer a;
   ModelRenderer b;
   ModelRenderer c;
   ModelRenderer d;

   public ModelSlime(int var1) {
      this.a = new ModelRenderer(this, 0, var1);
      this.a.a(-4.0F, 16.0F, -4.0F, 8, 8, 8);
      if (var1 > 0) {
         this.a = new ModelRenderer(this, 0, var1);
         this.a.a(-3.0F, 17.0F, -3.0F, 6, 6, 6);
         this.b = new ModelRenderer(this, 32, 0);
         this.b.a(-3.25F, 18.0F, -3.5F, 2, 2, 2);
         this.c = new ModelRenderer(this, 32, 4);
         this.c.a(1.25F, 18.0F, -3.5F, 2, 2, 2);
         this.d = new ModelRenderer(this, 32, 8);
         this.d.a(0.0F, 21.0F, -3.5F, 1, 1, 1);
      }
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);
      this.a.a(var7);
      if (this.b != null) {
         this.b.a(var7);
         this.c.a(var7);
         this.d.a(var7);
      }
   }
}
