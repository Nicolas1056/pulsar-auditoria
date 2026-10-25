package net.minecraft.client.model;

public class ModelCow extends ModelQuadruped {
   public ModelCow() {
      super(12, 0.0F);
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
      this.a.a(0.0F, 4.0F, -8.0F);
      this.a.a(22, 0).a(-5.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
      this.a.a(22, 0).a(4.0F, -5.0F, -4.0F, 1, 3, 1, 0.0F);
      this.b = new ModelRenderer(this, 18, 4);
      this.b.a(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
      this.b.a(0.0F, 5.0F, 2.0F);
      this.b.a(52, 0).a(-2.0F, 2.0F, -8.0F, 4, 6, 1);
      this.c.c--;
      this.d.c++;
      this.c.e += 0.0F;
      this.d.e += 0.0F;
      this.e.c--;
      this.f.c++;
      this.e.e--;
      this.f.e--;
      this.h += 2.0F;
   }
}
