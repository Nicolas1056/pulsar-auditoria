package net.minecraft.client.model;

import net.minecraft.client.renderer.WorldRenderer;

public class ModelBox {
   private PositionTextureVertex[] h;
   private TexturedQuad[] i;
   public final float a;
   public final float b;
   public final float c;
   public final float d;
   public final float e;
   public final float f;
   public String g;

   public ModelBox(ModelRenderer var1, int var2, int var3, float var4, float var5, float var6, int var7, int var8, int var9, float var10) {
      this(var1, var2, var3, var4, var5, var6, var7, var8, var9, var10, var1.i);
   }

   public ModelBox(ModelRenderer var1, int var2, int var3, float var4, float var5, float var6, int var7, int var8, int var9, float var10, boolean var11) {
      this.a = var4;
      this.b = var5;
      this.c = var6;
      this.d = var4 + var7;
      this.e = var5 + var8;
      this.f = var6 + var9;
      this.h = new PositionTextureVertex[8];
      this.i = new TexturedQuad[6];
      float var12 = var4 + var7;
      float var13 = var5 + var8;
      float var14 = var6 + var9;
      var4 -= var10;
      var5 -= var10;
      var6 -= var10;
      var12 += var10;
      var13 += var10;
      var14 += var10;
      if (var11) {
         float var15 = var12;
         var12 = var4;
         var4 = var15;
      }

      PositionTextureVertex var30 = new PositionTextureVertex(var4, var5, var6, 0.0F, 0.0F);
      PositionTextureVertex var16 = new PositionTextureVertex(var12, var5, var6, 0.0F, 8.0F);
      PositionTextureVertex var17 = new PositionTextureVertex(var12, var13, var6, 8.0F, 8.0F);
      PositionTextureVertex var18 = new PositionTextureVertex(var4, var13, var6, 8.0F, 0.0F);
      PositionTextureVertex var19 = new PositionTextureVertex(var4, var5, var14, 0.0F, 0.0F);
      PositionTextureVertex var20 = new PositionTextureVertex(var12, var5, var14, 0.0F, 8.0F);
      PositionTextureVertex var21 = new PositionTextureVertex(var12, var13, var14, 8.0F, 8.0F);
      PositionTextureVertex var22 = new PositionTextureVertex(var4, var13, var14, 8.0F, 0.0F);
      this.h[0] = var30;
      this.h[1] = var16;
      this.h[2] = var17;
      this.h[3] = var18;
      this.h[4] = var19;
      this.h[5] = var20;
      this.h[6] = var21;
      this.h[7] = var22;
      this.i[0] = new TexturedQuad(
         new PositionTextureVertex[]{var20, var16, var17, var21},
         var2 + var9 + var7,
         var3 + var9,
         var2 + var9 + var7 + var9,
         var3 + var9 + var8,
         var1.a,
         var1.b
      );
      this.i[1] = new TexturedQuad(new PositionTextureVertex[]{var30, var19, var22, var18}, var2, var3 + var9, var2 + var9, var3 + var9 + var8, var1.a, var1.b);
      this.i[2] = new TexturedQuad(new PositionTextureVertex[]{var20, var19, var30, var16}, var2 + var9, var3, var2 + var9 + var7, var3 + var9, var1.a, var1.b);
      this.i[3] = new TexturedQuad(
         new PositionTextureVertex[]{var17, var18, var22, var21}, var2 + var9 + var7, var3 + var9, var2 + var9 + var7 + var7, var3, var1.a, var1.b
      );
      this.i[4] = new TexturedQuad(
         new PositionTextureVertex[]{var16, var30, var18, var17}, var2 + var9, var3 + var9, var2 + var9 + var7, var3 + var9 + var8, var1.a, var1.b
      );
      this.i[5] = new TexturedQuad(
         new PositionTextureVertex[]{var19, var20, var21, var22},
         var2 + var9 + var7 + var9,
         var3 + var9,
         var2 + var9 + var7 + var9 + var7,
         var3 + var9 + var8,
         var1.a,
         var1.b
      );
      if (var11) {
         for (int var23 = 0; var23 < this.i.length; var23++) {
            this.i[var23].a();
         }
      }
   }

   public void a(WorldRenderer var1, float var2) {
      for (int var3 = 0; var3 < this.i.length; var3++) {
         this.i[var3].a(var1, var2);
      }
   }

   public ModelBox a(String var1) {
      this.g = var1;
      return this;
   }
}
