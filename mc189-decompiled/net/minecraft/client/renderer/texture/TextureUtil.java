package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.Buffer;
import java.nio.IntBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;

public class TextureUtil {
   private static final Logger c = LogManager.getLogger();
   private static final IntBuffer d = GLAllocation.f(4194304);
   public static final DynamicTexture a = new DynamicTexture(16, 16);
   public static final int[] b = a.e();
   private static final int[] e;

   public static int a() {
      return GlStateManager.y();
   }

   public static void a(int var0) {
      GlStateManager.h(var0);
   }

   public static int a(int var0, BufferedImage var1) {
      return a(var0, var1, false, false);
   }

   public static void a(int var0, int[] var1, int var2, int var3) {
      b(var0);
      a(0, var1, var2, var3, 0, 0, false, false, false);
   }

   public static int[][] a(int var0, int var1, int[][] var2) {
      int[][] var3 = new int[var0 + 1][];
      var3[0] = var2[0];
      if (var0 > 0) {
         boolean var4 = false;

         for (int var5 = 0; var5 < var2.length; var5++) {
            if (var2[0][var5] >> 24 == 0) {
               var4 = true;
               break;
            }
         }

         for (int var14 = 1; var14 <= var0; var14++) {
            if (var2[var14] != null) {
               var3[var14] = var2[var14];
            } else {
               int[] var6 = var3[var14 - 1];
               int[] var7 = new int[var6.length >> 2];
               int var8 = var1 >> var14;
               int var9 = var7.length / var8;
               int var10 = var8 << 1;

               for (int var11 = 0; var11 < var8; var11++) {
                  for (int var12 = 0; var12 < var9; var12++) {
                     int var13 = 2 * (var11 + var12 * var10);
                     var7[var11 + var12 * var8] = a(var6[var13 + 0], var6[var13 + 1], var6[var13 + 0 + var10], var6[var13 + 1 + var10], var4);
                  }
               }

               var3[var14] = var7;
            }
         }
      }

      return var3;
   }

   private static int a(int var0, int var1, int var2, int var3, boolean var4) {
      if (!var4) {
         int var14 = a(var0, var1, var2, var3, 24);
         int var16 = a(var0, var1, var2, var3, 16);
         int var18 = a(var0, var1, var2, var3, 8);
         int var20 = a(var0, var1, var2, var3, 0);
         return var14 << 24 | var16 << 16 | var18 << 8 | var20;
      } else {
         e[0] = var0;
         e[1] = var1;
         e[2] = var2;
         e[3] = var3;
         float var5 = 0.0F;
         float var6 = 0.0F;
         float var7 = 0.0F;
         float var8 = 0.0F;

         for (int var9 = 0; var9 < 4; var9++) {
            if (e[var9] >> 24 != 0) {
               var5 += (float)Math.pow((e[var9] >> 24 & 0xFF) / 255.0F, 2.2);
               var6 += (float)Math.pow((e[var9] >> 16 & 0xFF) / 255.0F, 2.2);
               var7 += (float)Math.pow((e[var9] >> 8 & 0xFF) / 255.0F, 2.2);
               var8 += (float)Math.pow((e[var9] >> 0 & 0xFF) / 255.0F, 2.2);
            }
         }

         var5 /= 4.0F;
         var6 /= 4.0F;
         var7 /= 4.0F;
         var8 /= 4.0F;
         int var21 = (int)(Math.pow(var5, 0.45454545454545453) * 255.0);
         int var10 = (int)(Math.pow(var6, 0.45454545454545453) * 255.0);
         int var11 = (int)(Math.pow(var7, 0.45454545454545453) * 255.0);
         int var12 = (int)(Math.pow(var8, 0.45454545454545453) * 255.0);
         if (var21 < 96) {
            var21 = 0;
         }

         return var21 << 24 | var10 << 16 | var11 << 8 | var12;
      }
   }

   private static int a(int var0, int var1, int var2, int var3, int var4) {
      float var5 = (float)Math.pow((var0 >> var4 & 0xFF) / 255.0F, 2.2);
      float var6 = (float)Math.pow((var1 >> var4 & 0xFF) / 255.0F, 2.2);
      float var7 = (float)Math.pow((var2 >> var4 & 0xFF) / 255.0F, 2.2);
      float var8 = (float)Math.pow((var3 >> var4 & 0xFF) / 255.0F, 2.2);
      float var9 = (float)Math.pow((var5 + var6 + var7 + var8) * 0.25, 0.45454545454545453);
      return (int)(var9 * 255.0);
   }

   public static void a(int[][] var0, int var1, int var2, int var3, int var4, boolean var5, boolean var6) {
      for (int var7 = 0; var7 < var0.length; var7++) {
         int[] var8 = var0[var7];
         a(var7, var8, var1 >> var7, var2 >> var7, var3 >> var7, var4 >> var7, var5, var6, var0.length > 1);
      }
   }

   private static void a(int var0, int[] var1, int var2, int var3, int var4, int var5, boolean var6, boolean var7, boolean var8) {
      int var9 = 4194304 / var2;
      a(var6, var8);
      a(var7);
      int var10 = 0;

      while (var10 < var2 * var3) {
         int var11 = var10 / var2;
         int var12 = Math.min(var9, var3 - var11);
         int var13 = var2 * var12;
         b(var1, var10, var13);
         GL11.glTexSubImage2D(3553, var0, var4, var5 + var11, var2, var12, 32993, 33639, d);
         var10 += var2 * var12;
      }
   }

   public static int a(int var0, BufferedImage var1, boolean var2, boolean var3) {
      a(var0, var1.getWidth(), var1.getHeight());
      return a(var0, var1, 0, 0, var2, var3);
   }

   public static void a(int var0, int var1, int var2) {
      a(var0, 0, var1, var2);
   }

   public static void a(int var0, int var1, int var2, int var3) {
      a(var0);
      b(var0);
      if (var1 >= 0) {
         GL11.glTexParameteri(3553, 33085, var1);
         GL11.glTexParameterf(3553, 33082, 0.0F);
         GL11.glTexParameterf(3553, 33083, var1);
         GL11.glTexParameterf(3553, 34049, 0.0F);
      }

      for (int var4 = 0; var4 <= var1; var4++) {
         GL11.glTexImage2D(3553, var4, 6408, var2 >> var4, var3 >> var4, 0, 32993, 33639, (IntBuffer)null);
      }
   }

   public static int a(int var0, BufferedImage var1, int var2, int var3, boolean var4, boolean var5) {
      b(var0);
      a(var1, var2, var3, var4, var5);
      return var0;
   }

   private static void a(BufferedImage var0, int var1, int var2, boolean var3, boolean var4) {
      int var5 = var0.getWidth();
      int var6 = var0.getHeight();
      int var7 = 4194304 / var5;
      int[] var8 = new int[var7 * var5];
      b(var3);
      a(var4);

      for (int var9 = 0; var9 < var5 * var6; var9 += var5 * var7) {
         int var10 = var9 / var5;
         int var11 = Math.min(var7, var6 - var10);
         int var12 = var5 * var11;
         var0.getRGB(0, var10, var5, var11, var8, 0, var5);
         a(var8, var12);
         GL11.glTexSubImage2D(3553, 0, var1, var2 + var10, var5, var11, 32993, 33639, d);
      }
   }

   private static void a(boolean var0) {
      if (var0) {
         GL11.glTexParameteri(3553, 10242, 10496);
         GL11.glTexParameteri(3553, 10243, 10496);
      } else {
         GL11.glTexParameteri(3553, 10242, 10497);
         GL11.glTexParameteri(3553, 10243, 10497);
      }
   }

   private static void b(boolean var0) {
      a(var0, false);
   }

   private static void a(boolean var0, boolean var1) {
      if (var0) {
         GL11.glTexParameteri(3553, 10241, var1 ? 9987 : 9729);
         GL11.glTexParameteri(3553, 10240, 9729);
      } else {
         GL11.glTexParameteri(3553, 10241, var1 ? 9986 : 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
      }
   }

   private static void a(int[] var0, int var1) {
      b(var0, 0, var1);
   }

   private static void b(int[] var0, int var1, int var2) {
      int[] var3 = var0;
      if (Minecraft.A().t.e) {
         var3 = a(var0);
      }

      ((Buffer)d).clear();
      d.put(var3, var1, var2);
      ((Buffer)d).position(0).limit(var2);
   }

   static void b(int var0) {
      GlStateManager.i(var0);
   }

   public static int[] a(IResourceManager var0, ResourceLocation var1) throws IOException {
      BufferedImage var2 = a(var0.a(var1).b());
      int var3 = var2.getWidth();
      int var4 = var2.getHeight();
      int[] var5 = new int[var3 * var4];
      var2.getRGB(0, 0, var3, var4, var5, 0, var3);
      return var5;
   }

   public static BufferedImage a(InputStream var0) throws IOException {
      BufferedImage var1;
      try {
         var1 = ImageIO.read(var0);
      } finally {
         IOUtils.closeQuietly(var0);
      }

      return var1;
   }

   public static int[] a(int[] var0) {
      int[] var1 = new int[var0.length];

      for (int var2 = 0; var2 < var0.length; var2++) {
         var1[var2] = c(var0[var2]);
      }

      return var1;
   }

   public static int c(int var0) {
      int var1 = var0 >> 24 & 0xFF;
      int var2 = var0 >> 16 & 0xFF;
      int var3 = var0 >> 8 & 0xFF;
      int var4 = var0 & 0xFF;
      int var5 = (var2 * 30 + var3 * 59 + var4 * 11) / 100;
      int var6 = (var2 * 30 + var3 * 70) / 100;
      int var7 = (var2 * 30 + var4 * 70) / 100;
      return var1 << 24 | var5 << 16 | var6 << 8 | var7;
   }

   public static void a(int[] var0, int var1, int var2) {
      int[] var3 = new int[var1];
      int var4 = var2 / 2;

      for (int var5 = 0; var5 < var4; var5++) {
         System.arraycopy(var0, var5 * var1, var3, 0, var1);
         System.arraycopy(var0, (var2 - 1 - var5) * var1, var0, var5 * var1, var1);
         System.arraycopy(var3, 0, var0, (var2 - 1 - var5) * var1, var1);
      }
   }

   static {
      int var0 = -16777216;
      int var1 = -524040;
      int[] var2 = new int[]{-524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040};
      int[] var3 = new int[]{-16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216};
      int var4 = var2.length;

      for (int var5 = 0; var5 < 16; var5++) {
         System.arraycopy(var5 < var4 ? var2 : var3, 0, b, 16 * var5, var4);
         System.arraycopy(var5 < var4 ? var3 : var2, 0, b, 16 * var5 + var4, var4);
      }

      a.d();
      e = new int[4];
   }
}
