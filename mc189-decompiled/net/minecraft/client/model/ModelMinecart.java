package net.minecraft.client.model;

import net.minecraft.entity.Entity;

public class ModelMinecart extends ModelBase {
   public ModelRenderer[] a = new ModelRenderer[7];

   public ModelMinecart() {
      this.a[0] = new ModelRenderer(this, 0, 10);
      this.a[1] = new ModelRenderer(this, 0, 0);
      this.a[2] = new ModelRenderer(this, 0, 0);
      this.a[3] = new ModelRenderer(this, 0, 0);
      this.a[4] = new ModelRenderer(this, 0, 0);
      this.a[5] = new ModelRenderer(this, 44, 10);
      byte var1 = 20;
      byte var2 = 8;
      byte var3 = 16;
      byte var4 = 4;
      this.a[0].a(-var1 / 2, -var3 / 2, -1.0F, var1, var3, 2, 0.0F);
      this.a[0].a(0.0F, var4, 0.0F);
      this.a[5].a(-var1 / 2 + 1, -var3 / 2 + 1, -1.0F, var1 - 2, var3 - 2, 1, 0.0F);
      this.a[5].a(0.0F, var4, 0.0F);
      this.a[1].a(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
      this.a[1].a(-var1 / 2 + 1, var4, 0.0F);
      this.a[2].a(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
      this.a[2].a(var1 / 2 - 1, var4, 0.0F);
      this.a[3].a(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
      this.a[3].a(0.0F, var4, -var3 / 2 + 1);
      this.a[4].a(-var1 / 2 + 2, -var2 - 1, -1.0F, var1 - 4, var2, 2, 0.0F);
      this.a[4].a(0.0F, var4, var3 / 2 - 1);
      this.a[0].f = (float) (Math.PI / 2);
      this.a[1].g = (float) (Math.PI * 3.0 / 2.0);
      this.a[2].g = (float) (Math.PI / 2);
      this.a[3].g = (float) Math.PI;
      this.a[5].f = (float) (-Math.PI / 2);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a[5].d = 4.0F - var4;

      for (int var8 = 0; var8 < 6; var8++) {
         this.a[var8].a(var7);
      }
   }
}
