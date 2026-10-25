package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelEnderMite extends ModelBase {
   private static final int[][] a = new int[][]{{4, 3, 2}, {6, 4, 5}, {3, 3, 1}, {1, 2, 1}};
   private static final int[][] b = new int[][]{{0, 0}, {0, 5}, {0, 14}, {0, 18}};
   private static final int c = a.length;
   private final ModelRenderer[] d = new ModelRenderer[c];

   public ModelEnderMite() {
      float var1 = -3.5F;

      for (int var2 = 0; var2 < this.d.length; var2++) {
         this.d[var2] = new ModelRenderer(this, b[var2][0], b[var2][1]);
         this.d[var2].a(a[var2][0] * -0.5F, 0.0F, a[var2][2] * -0.5F, a[var2][0], a[var2][1], a[var2][2]);
         this.d[var2].a(0.0F, 24 - a[var2][1], var1);
         if (var2 < this.d.length - 1) {
            var1 += (a[var2][2] + a[var2 + 1][2]) * 0.5F;
         }
      }
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.a(var2, var3, var4, var5, var6, var7, var1);

      for (int var8 = 0; var8 < this.d.length; var8++) {
         this.d[var8].a(var7);
      }
   }

   @Override
   public void a(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for (int var8 = 0; var8 < this.d.length; var8++) {
         this.d[var8].g = MathHelper.b(var3 * 0.9F + var8 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.01F * (1 + Math.abs(var8 - 2));
         this.d[var8].c = MathHelper.a(var3 * 0.9F + var8 * 0.15F * (float) Math.PI) * (float) Math.PI * 0.1F * Math.abs(var8 - 2);
      }
   }
}
