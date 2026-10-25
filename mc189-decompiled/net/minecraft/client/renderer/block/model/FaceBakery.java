package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.EnumFaceDirection;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3i;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.util.vector.Vector4f;

public class FaceBakery {
   private static final float a = 1.0F / (float)Math.cos((float) (Math.PI / 8)) - 1.0F;
   private static final float b = 1.0F / (float)Math.cos((float) (Math.PI / 4)) - 1.0F;

   public BakedQuad a(
      Vector3f var1,
      Vector3f var2,
      BlockPartFace var3,
      TextureAtlasSprite var4,
      EnumFacing var5,
      ModelRotation var6,
      BlockPartRotation var7,
      boolean var8,
      boolean var9
   ) {
      int[] var10 = this.a(var3, var4, var5, this.a(var1, var2), var6, var7, var8, var9);
      EnumFacing var11 = a(var10);
      if (var8) {
         this.a(var10, var11, var3.e, var4);
      }

      if (var7 == null) {
         this.a(var10, var11);
      }

      return new BakedQuad(var10, var3.c, var11);
   }

   private int[] a(
      BlockPartFace var1, TextureAtlasSprite var2, EnumFacing var3, float[] var4, ModelRotation var5, BlockPartRotation var6, boolean var7, boolean var8
   ) {
      int[] var9 = new int[28];

      for (int var10 = 0; var10 < 4; var10++) {
         this.a(var9, var10, var3, var1, var4, var2, var5, var6, var7, var8);
      }

      return var9;
   }

   private int a(EnumFacing var1) {
      float var2 = this.b(var1);
      int var3 = MathHelper.a((int)(var2 * 255.0F), 0, 255);
      return 0xFF000000 | var3 << 16 | var3 << 8 | var3;
   }

   private float b(EnumFacing var1) {
      switch (var1) {
         case a:
            return 0.5F;
         case b:
            return 1.0F;
         case c:
         case d:
            return 0.8F;
         case e:
         case f:
            return 0.6F;
         default:
            return 1.0F;
      }
   }

   private float[] a(Vector3f var1, Vector3f var2) {
      float[] var3 = new float[EnumFacing.values().length];
      var3[EnumFaceDirection.Constants.f] = var1.x / 16.0F;
      var3[EnumFaceDirection.Constants.e] = var1.y / 16.0F;
      var3[EnumFaceDirection.Constants.d] = var1.z / 16.0F;
      var3[EnumFaceDirection.Constants.c] = var2.x / 16.0F;
      var3[EnumFaceDirection.Constants.b] = var2.y / 16.0F;
      var3[EnumFaceDirection.Constants.a] = var2.z / 16.0F;
      return var3;
   }

   private void a(
      int[] var1,
      int var2,
      EnumFacing var3,
      BlockPartFace var4,
      float[] var5,
      TextureAtlasSprite var6,
      ModelRotation var7,
      BlockPartRotation var8,
      boolean var9,
      boolean var10
   ) {
      EnumFacing var11 = var7.a(var3);
      int var12 = var10 ? this.a(var11) : -1;
      EnumFaceDirection.VertexInformation var13 = EnumFaceDirection.a(var3).a(var2);
      Vector3f var14 = new Vector3f(var5[var13.a], var5[var13.b], var5[var13.c]);
      this.a(var14, var8);
      int var15 = this.a(var14, var3, var2, var7, var9);
      this.a(var1, var15, var2, var14, var12, var6, var4.e);
   }

   private void a(int[] var1, int var2, int var3, Vector3f var4, int var5, TextureAtlasSprite var6, BlockFaceUV var7) {
      int var8 = var2 * 7;
      var1[var8] = Float.floatToRawIntBits(var4.x);
      var1[var8 + 1] = Float.floatToRawIntBits(var4.y);
      var1[var8 + 2] = Float.floatToRawIntBits(var4.z);
      var1[var8 + 3] = var5;
      var1[var8 + 4] = Float.floatToRawIntBits(var6.a((double)var7.a(var3)));
      var1[var8 + 4 + 1] = Float.floatToRawIntBits(var6.b((double)var7.b(var3)));
   }

   private void a(Vector3f var1, BlockPartRotation var2) {
      if (var2 != null) {
         Matrix4f var3 = this.a();
         Vector3f var4 = new Vector3f(0.0F, 0.0F, 0.0F);
         switch (var2.b) {
            case a:
               Matrix4f.rotate(var2.c * (float) (Math.PI / 180.0), new Vector3f(1.0F, 0.0F, 0.0F), var3, var3);
               var4.set(0.0F, 1.0F, 1.0F);
               break;
            case b:
               Matrix4f.rotate(var2.c * (float) (Math.PI / 180.0), new Vector3f(0.0F, 1.0F, 0.0F), var3, var3);
               var4.set(1.0F, 0.0F, 1.0F);
               break;
            case c:
               Matrix4f.rotate(var2.c * (float) (Math.PI / 180.0), new Vector3f(0.0F, 0.0F, 1.0F), var3, var3);
               var4.set(1.0F, 1.0F, 0.0F);
         }

         if (var2.d) {
            if (Math.abs(var2.c) == 22.5F) {
               var4.scale(a);
            } else {
               var4.scale(b);
            }

            Vector3f.add(var4, new Vector3f(1.0F, 1.0F, 1.0F), var4);
         } else {
            var4.set(1.0F, 1.0F, 1.0F);
         }

         this.a(var1, new Vector3f(var2.a), var3, var4);
      }
   }

   public int a(Vector3f var1, EnumFacing var2, int var3, ModelRotation var4, boolean var5) {
      if (var4 == ModelRotation.a) {
         return var3;
      } else {
         this.a(var1, new Vector3f(0.5F, 0.5F, 0.5F), var4.a(), new Vector3f(1.0F, 1.0F, 1.0F));
         return var4.a(var2, var3);
      }
   }

   private void a(Vector3f var1, Vector3f var2, Matrix4f var3, Vector3f var4) {
      Vector4f var5 = new Vector4f(var1.x - var2.x, var1.y - var2.y, var1.z - var2.z, 1.0F);
      Matrix4f.transform(var3, var5, var5);
      var5.x = var5.x * var4.x;
      var5.y = var5.y * var4.y;
      var5.z = var5.z * var4.z;
      var1.set(var5.x + var2.x, var5.y + var2.y, var5.z + var2.z);
   }

   private Matrix4f a() {
      Matrix4f var1 = new Matrix4f();
      var1.setIdentity();
      return var1;
   }

   public static EnumFacing a(int[] var0) {
      Vector3f var1 = new Vector3f(Float.intBitsToFloat(var0[0]), Float.intBitsToFloat(var0[1]), Float.intBitsToFloat(var0[2]));
      Vector3f var2 = new Vector3f(Float.intBitsToFloat(var0[7]), Float.intBitsToFloat(var0[8]), Float.intBitsToFloat(var0[9]));
      Vector3f var3 = new Vector3f(Float.intBitsToFloat(var0[14]), Float.intBitsToFloat(var0[15]), Float.intBitsToFloat(var0[16]));
      Vector3f var4 = new Vector3f();
      Vector3f var5 = new Vector3f();
      Vector3f var6 = new Vector3f();
      Vector3f.sub(var1, var2, var4);
      Vector3f.sub(var3, var2, var5);
      Vector3f.cross(var5, var4, var6);
      float var7 = (float)Math.sqrt(var6.x * var6.x + var6.y * var6.y + var6.z * var6.z);
      var6.x /= var7;
      var6.y /= var7;
      var6.z /= var7;
      EnumFacing var8 = null;
      float var9 = 0.0F;

      for (EnumFacing var13 : EnumFacing.values()) {
         Vec3i var14 = var13.m();
         Vector3f var15 = new Vector3f(var14.n(), var14.o(), var14.p());
         float var16 = Vector3f.dot(var6, var15);
         if (var16 >= 0.0F && var16 > var9) {
            var9 = var16;
            var8 = var13;
         }
      }

      return var8 == null ? EnumFacing.b : var8;
   }

   public void a(int[] var1, EnumFacing var2, BlockFaceUV var3, TextureAtlasSprite var4) {
      for (int var5 = 0; var5 < 4; var5++) {
         this.a(var5, var1, var2, var3, var4);
      }
   }

   private void a(int[] var1, EnumFacing var2) {
      int[] var3 = new int[var1.length];
      System.arraycopy(var1, 0, var3, 0, var1.length);
      float[] var4 = new float[EnumFacing.values().length];
      var4[EnumFaceDirection.Constants.f] = 999.0F;
      var4[EnumFaceDirection.Constants.e] = 999.0F;
      var4[EnumFaceDirection.Constants.d] = 999.0F;
      var4[EnumFaceDirection.Constants.c] = -999.0F;
      var4[EnumFaceDirection.Constants.b] = -999.0F;
      var4[EnumFaceDirection.Constants.a] = -999.0F;

      for (int var5 = 0; var5 < 4; var5++) {
         int var6 = 7 * var5;
         float var7 = Float.intBitsToFloat(var3[var6]);
         float var8 = Float.intBitsToFloat(var3[var6 + 1]);
         float var9 = Float.intBitsToFloat(var3[var6 + 2]);
         if (var7 < var4[EnumFaceDirection.Constants.f]) {
            var4[EnumFaceDirection.Constants.f] = var7;
         }

         if (var8 < var4[EnumFaceDirection.Constants.e]) {
            var4[EnumFaceDirection.Constants.e] = var8;
         }

         if (var9 < var4[EnumFaceDirection.Constants.d]) {
            var4[EnumFaceDirection.Constants.d] = var9;
         }

         if (var7 > var4[EnumFaceDirection.Constants.c]) {
            var4[EnumFaceDirection.Constants.c] = var7;
         }

         if (var8 > var4[EnumFaceDirection.Constants.b]) {
            var4[EnumFaceDirection.Constants.b] = var8;
         }

         if (var9 > var4[EnumFaceDirection.Constants.a]) {
            var4[EnumFaceDirection.Constants.a] = var9;
         }
      }

      EnumFaceDirection var17 = EnumFaceDirection.a(var2);

      for (int var18 = 0; var18 < 4; var18++) {
         int var19 = 7 * var18;
         EnumFaceDirection.VertexInformation var20 = var17.a(var18);
         float var21 = var4[var20.a];
         float var10 = var4[var20.b];
         float var11 = var4[var20.c];
         var1[var19] = Float.floatToRawIntBits(var21);
         var1[var19 + 1] = Float.floatToRawIntBits(var10);
         var1[var19 + 2] = Float.floatToRawIntBits(var11);

         for (int var12 = 0; var12 < 4; var12++) {
            int var13 = 7 * var12;
            float var14 = Float.intBitsToFloat(var3[var13]);
            float var15 = Float.intBitsToFloat(var3[var13 + 1]);
            float var16 = Float.intBitsToFloat(var3[var13 + 2]);
            if (MathHelper.a(var21, var14) && MathHelper.a(var10, var15) && MathHelper.a(var11, var16)) {
               var1[var19 + 4] = var3[var13 + 4];
               var1[var19 + 4 + 1] = var3[var13 + 4 + 1];
            }
         }
      }
   }

   private void a(int var1, int[] var2, EnumFacing var3, BlockFaceUV var4, TextureAtlasSprite var5) {
      int var6 = 7 * var1;
      float var7 = Float.intBitsToFloat(var2[var6]);
      float var8 = Float.intBitsToFloat(var2[var6 + 1]);
      float var9 = Float.intBitsToFloat(var2[var6 + 2]);
      if (var7 < -0.1F || var7 >= 1.1F) {
         var7 -= MathHelper.d(var7);
      }

      if (var8 < -0.1F || var8 >= 1.1F) {
         var8 -= MathHelper.d(var8);
      }

      if (var9 < -0.1F || var9 >= 1.1F) {
         var9 -= MathHelper.d(var9);
      }

      float var10 = 0.0F;
      float var11 = 0.0F;
      switch (var3) {
         case a:
            var10 = var7 * 16.0F;
            var11 = (1.0F - var9) * 16.0F;
            break;
         case b:
            var10 = var7 * 16.0F;
            var11 = var9 * 16.0F;
            break;
         case c:
            var10 = (1.0F - var7) * 16.0F;
            var11 = (1.0F - var8) * 16.0F;
            break;
         case d:
            var10 = var7 * 16.0F;
            var11 = (1.0F - var8) * 16.0F;
            break;
         case e:
            var10 = var9 * 16.0F;
            var11 = (1.0F - var8) * 16.0F;
            break;
         case f:
            var10 = (1.0F - var9) * 16.0F;
            var11 = (1.0F - var8) * 16.0F;
      }

      int var12 = var4.c(var1) * 7;
      var2[var12 + 4] = Float.floatToRawIntBits(var5.a((double)var10));
      var2[var12 + 4 + 1] = Float.floatToRawIntBits(var5.b((double)var11));
   }
}
