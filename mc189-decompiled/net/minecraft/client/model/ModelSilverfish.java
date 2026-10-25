package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSilverfish extends ModelBase {
   private ModelRenderer[] a;
   private ModelRenderer[] b;
   private float[] c = new float[7];
   private static final int[][] d = new int[][]{{3, 2, 2}, {4, 3, 2}, {6, 4, 3}, {3, 3, 3}, {2, 2, 3}, {2, 1, 2}, {1, 1, 2}};
   private static final int[][] e = new int[][]{{0, 0}, {0, 4}, {0, 9}, {0, 16}, {0, 22}, {11, 0}, {13, 4}};

   public ModelSilverfish() {
      this.a = new ModelRenderer[7];
      float var1 = -3.5F;

      for (int var2 = 0; var2 < this.a.length; var2++) {
         this.a[var2] = new ModelRenderer(this, e[var2][0], e[var2][1]);
         this.a[var2].a(d[var2][0] * -0.5F, 0.0F, d[var2][2] * -0.5F, d[var2][0], d[var2][1], d[var2][2]);
         this.a[var2].a(0.0F, 24 - d[var2][1], var1);
         this.c[var2] = var1;
         if (var2 < this.a.length - 1) {
            var1 += (d[var2][2] + d[var2 + 1][2]) * 0.5F;
         }
      }

      this.b = new ModelRenderer[3];
      this.b[0] = new ModelRenderer(this, 20, 0);
      this.b[0].a(-5.0F, 0.0F, d[2][2] * -0.5F, 10, 8, d[2][2]);
      this.b[0].a(0.0F, 16.0F, this.c[2]);
      this.b[1] = new ModelRenderer(this, 20, 11);
      this.b[1].a(-3.0F, 0.0F, d[4][2] * -0.5F, 6, 4, d[4][2]);
      this.b[1].a(0.0F, 20.0F, this.c[4]);
      this.b[2] = new ModelRenderer(this, 20, 18);
      this.b[2].a(-3.0F, 0.0F, d[4][2] * -0.5F, 6, 5, d[1][2]);
      this.b[2].a(0.0F, 19.0F, this.c[1]);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);

      for (int var8 = 0; var8 < this.a.length; var8++) {
         this.a[var8].a(var7);
      }

      for (int var9 = 0; var9 < this.b.length; var9++) {
         this.b[var9].a(var7);
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for (int var8 = 0; var8 < this.a.length; var8++) {
         this.a[var8].g = MathHelper.b(var3 * 0.9F + var8 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.05F * (1 + Math.abs(var8 - 2));
         this.a[var8].c = MathHelper.a(var3 * 0.9F + var8 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.2F * Math.abs(var8 - 2);
      }

      this.b[0].g = this.a[2].g;
      this.b[1].g = this.a[4].g;
      this.b[1].c = this.a[4].c;
      this.b[2].g = this.a[1].g;
      this.b[2].c = this.a[1].c;
   }
}
