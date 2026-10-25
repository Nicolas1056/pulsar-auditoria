package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;

public class TextureAtlasSprite {
   private final String j;
   protected List<int[][]> a = Lists.newArrayList();
   protected int[][] b;
   private AnimationMetadataSection k;
   protected boolean c;
   protected int d;
   protected int e;
   protected int f;
   protected int g;
   private float l;
   private float m;
   private float n;
   private float o;
   protected int h;
   protected int i;
   private static String p = "builtin/clock";
   private static String q = "builtin/compass";

   protected TextureAtlasSprite(String var1) {
      this.j = var1;
   }

   protected static TextureAtlasSprite a(ResourceLocation var0) {
      String var1 = var0.toString();
      if (p.equals(var1)) {
         return new TextureClock(var1);
      } else {
         return (TextureAtlasSprite)(q.equals(var1) ? new TextureCompass(var1) : new TextureAtlasSprite(var1));
      }
   }

   public static void a(String var0) {
      p = var0;
   }

   public static void b(String var0) {
      q = var0;
   }

   public void a(int var1, int var2, int var3, int var4, boolean var5) {
      this.d = var3;
      this.e = var4;
      this.c = var5;
      float var6 = (float)(0.01F / var1);
      float var7 = (float)(0.01F / var2);
      this.l = var3 / (float)var1 + var6;
      this.m = (var3 + this.f) / (float)var1 - var6;
      this.n = (float)var4 / var2 + var7;
      this.o = (float)(var4 + this.g) / var2 - var7;
   }

   public void a(TextureAtlasSprite var1) {
      this.d = var1.d;
      this.e = var1.e;
      this.f = var1.f;
      this.g = var1.g;
      this.c = var1.c;
      this.l = var1.l;
      this.m = var1.m;
      this.n = var1.n;
      this.o = var1.o;
   }

   public int a() {
      return this.d;
   }

   public int b() {
      return this.e;
   }

   public int c() {
      return this.f;
   }

   public int d() {
      return this.g;
   }

   public float e() {
      return this.l;
   }

   public float f() {
      return this.m;
   }

   public float a(double var1) {
      float var3 = this.m - this.l;
      return this.l + var3 * (float)var1 / 16.0F;
   }

   public float g() {
      return this.n;
   }

   public float h() {
      return this.o;
   }

   public float b(double var1) {
      float var3 = this.o - this.n;
      return this.n + var3 * ((float)var1 / 16.0F);
   }

   public String i() {
      return this.j;
   }

   public void j() {
      this.i++;
      if (this.i >= this.k.a(this.h)) {
         int var1 = this.k.c(this.h);
         int var2 = this.k.c() == 0 ? this.a.size() : this.k.c();
         this.h = (this.h + 1) % var2;
         this.i = 0;
         int var3 = this.k.c(this.h);
         if (var1 != var3 && var3 >= 0 && var3 < this.a.size()) {
            TextureUtil.a(this.a.get(var3), this.f, this.g, this.d, this.e, false, false);
         }
      } else if (this.k.e()) {
         this.n();
      }
   }

   private void n() {
      double var1 = 1.0 - (double)this.i / this.k.a(this.h);
      int var3 = this.k.c(this.h);
      int var4 = this.k.c() == 0 ? this.a.size() : this.k.c();
      int var5 = this.k.c((this.h + 1) % var4);
      if (var3 != var5 && var5 >= 0 && var5 < this.a.size()) {
         int[][] var6 = this.a.get(var3);
         int[][] var7 = this.a.get(var5);
         if (this.b == null || this.b.length != var6.length) {
            this.b = new int[var6.length][];
         }

         for (int var8 = 0; var8 < var6.length; var8++) {
            if (this.b[var8] == null) {
               this.b[var8] = new int[var6[var8].length];
            }

            if (var8 < var7.length && var7[var8].length == var6[var8].length) {
               for (int var9 = 0; var9 < var6[var8].length; var9++) {
                  int var10 = var6[var8][var9];
                  int var11 = var7[var8][var9];
                  int var12 = (int)(((var10 & 0xFF0000) >> 16) * var1 + ((var11 & 0xFF0000) >> 16) * (1.0 - var1));
                  int var13 = (int)(((var10 & 0xFF00) >> 8) * var1 + ((var11 & 0xFF00) >> 8) * (1.0 - var1));
                  int var14 = (int)((var10 & 0xFF) * var1 + (var11 & 0xFF) * (1.0 - var1));
                  this.b[var8][var9] = var10 & 0xFF000000 | var12 << 16 | var13 << 8 | var14;
               }
            }
         }

         TextureUtil.a(this.b, this.f, this.g, this.d, this.e, false, false);
      }
   }

   public int[][] a(int var1) {
      return this.a.get(var1);
   }

   public int k() {
      return this.a.size();
   }

   public void b(int var1) {
      this.f = var1;
   }

   public void c(int var1) {
      this.g = var1;
   }

   public void a(BufferedImage[] var1, AnimationMetadataSection var2) throws IOException {
      this.o();
      int var3 = var1[0].getWidth();
      int var4 = var1[0].getHeight();
      this.f = var3;
      this.g = var4;
      int[][] var5 = new int[var1.length][];

      for (int var6 = 0; var6 < var1.length; var6++) {
         BufferedImage var7 = var1[var6];
         if (var7 != null) {
            if (var6 > 0 && (var7.getWidth() != var3 >> var6 || var7.getHeight() != var4 >> var6)) {
               throw new RuntimeException(
                  String.format(
                     "Unable to load miplevel: %d, image is size: %dx%d, expected %dx%d", var6, var7.getWidth(), var7.getHeight(), var3 >> var6, var4 >> var6
                  )
               );
            }

            var5[var6] = new int[var7.getWidth() * var7.getHeight()];
            var7.getRGB(0, 0, var7.getWidth(), var7.getHeight(), var5[var6], 0, var7.getWidth());
         }
      }

      if (var2 == null) {
         if (var4 != var3) {
            throw new RuntimeException("broken aspect ratio and not an animation");
         }

         this.a.add(var5);
      } else {
         int var11 = var4 / var3;
         int var12 = var3;
         int var8 = var3;
         this.g = this.f;
         if (var2.c() > 0) {
            for (int var10 : var2.f()) {
               if (var10 >= var11) {
                  throw new RuntimeException("invalid frameindex " + var10);
               }

               this.e(var10);
               this.a.set(var10, a(var5, var12, var8, var10));
            }

            this.k = var2;
         } else {
            ArrayList var13 = Lists.newArrayList();

            for (int var14 = 0; var14 < var11; var14++) {
               this.a.add(a(var5, var12, var8, var14));
               var13.add(new AnimationFrame(var14, -1));
            }

            this.k = new AnimationMetadataSection(var13, this.f, this.g, var2.d(), var2.e());
         }
      }
   }

   public void d(int var1) {
      ArrayList var2 = Lists.newArrayList();

      for (int var3 = 0; var3 < this.a.size(); var3++) {
         final int[][] var4 = this.a.get(var3);
         if (var4 != null) {
            try {
               var2.add(TextureUtil.a(var1, this.f, var4));
            } catch (Throwable var8) {
               CrashReport var6 = CrashReport.a(var8, "Generating mipmaps for frame");
               CrashReportCategory var7 = var6.a("Frame being iterated");
               var7.a("Frame index", var3);
               var7.a("Frame sizes", new Callable<String>() {
                  public String a() throws Exception {
                     StringBuilder var1x = new StringBuilder();

                     for (int[] var5 : var4) {
                        if (var1x.length() > 0) {
                           var1x.append(", ");
                        }

                        var1x.append(var5 == null ? "null" : var5.length);
                     }

                     return var1x.toString();
                  }
               });
               throw new ReportedException(var6);
            }
         }
      }

      this.a(var2);
   }

   private void e(int var1) {
      if (this.a.size() <= var1) {
         for (int var2 = this.a.size(); var2 <= var1; var2++) {
            this.a.add(null);
         }
      }
   }

   private static int[][] a(int[][] var0, int var1, int var2, int var3) {
      int[][] var4 = new int[var0.length][];

      for (int var5 = 0; var5 < var0.length; var5++) {
         int[] var6 = var0[var5];
         if (var6 != null) {
            var4[var5] = new int[(var1 >> var5) * (var2 >> var5)];
            System.arraycopy(var6, var3 * var4[var5].length, var4[var5], 0, var4[var5].length);
         }
      }

      return var4;
   }

   public void l() {
      this.a.clear();
   }

   public boolean m() {
      return this.k != null;
   }

   public void a(List<int[][]> var1) {
      this.a = var1;
   }

   private void o() {
      this.k = null;
      this.a(Lists.newArrayList());
      this.h = 0;
      this.i = 0;
   }

   @Override
   public String toString() {
      return "TextureAtlasSprite{name='"
         + this.j
         + '\''
         + ", frameCount="
         + this.a.size()
         + ", rotated="
         + this.c
         + ", x="
         + this.d
         + ", y="
         + this.e
         + ", height="
         + this.g
         + ", width="
         + this.f
         + ", u0="
         + this.l
         + ", u1="
         + this.m
         + ", v0="
         + this.n
         + ", v1="
         + this.o
         + '}';
   }
}
