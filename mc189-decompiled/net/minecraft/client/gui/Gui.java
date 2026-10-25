package net.minecraft.client.gui;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class Gui {
   public static final ResourceLocation b = new ResourceLocation("textures/gui/options_background.png");
   public static final ResourceLocation c = new ResourceLocation("textures/gui/container/stats_icons.png");
   public static final ResourceLocation d = new ResourceLocation("textures/gui/icons.png");
   protected float e;

   protected void a(int var1, int var2, int var3, int var4) {
      if (var2 < var1) {
         int var5 = var1;
         var1 = var2;
         var2 = var5;
      }

      a(var1, var3, var2 + 1, var3 + 1, var4);
   }

   protected void b(int var1, int var2, int var3, int var4) {
      if (var3 < var2) {
         int var5 = var2;
         var2 = var3;
         var3 = var5;
      }

      a(var1, var2 + 1, var1 + 1, var3, var4);
   }

   public static void a(int var0, int var1, int var2, int var3, int var4) {
      if (var0 < var2) {
         int var5 = var0;
         var0 = var2;
         var2 = var5;
      }

      if (var1 < var3) {
         int var11 = var1;
         var1 = var3;
         var3 = var11;
      }

      float var12 = (var4 >> 24 & 0xFF) / 255.0F;
      float var6 = (var4 >> 16 & 0xFF) / 255.0F;
      float var7 = (var4 >> 8 & 0xFF) / 255.0F;
      float var8 = (var4 & 0xFF) / 255.0F;
      Tessellator var9 = Tessellator.a();
      WorldRenderer var10 = var9.c();
      GlStateManager.l();
      GlStateManager.x();
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.c(var6, var7, var8, var12);
      var10.a(7, DefaultVertexFormats.e);
      var10.b((double)var0, (double)var3, 0.0).d();
      var10.b((double)var2, (double)var3, 0.0).d();
      var10.b((double)var2, (double)var1, 0.0).d();
      var10.b((double)var0, (double)var1, 0.0).d();
      var9.b();
      GlStateManager.w();
      GlStateManager.k();
   }

   protected void a(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = (var5 >> 24 & 0xFF) / 255.0F;
      float var8 = (var5 >> 16 & 0xFF) / 255.0F;
      float var9 = (var5 >> 8 & 0xFF) / 255.0F;
      float var10 = (var5 & 0xFF) / 255.0F;
      float var11 = (var6 >> 24 & 0xFF) / 255.0F;
      float var12 = (var6 >> 16 & 0xFF) / 255.0F;
      float var13 = (var6 >> 8 & 0xFF) / 255.0F;
      float var14 = (var6 & 0xFF) / 255.0F;
      GlStateManager.x();
      GlStateManager.l();
      GlStateManager.c();
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.j(7425);
      Tessellator var15 = Tessellator.a();
      WorldRenderer var16 = var15.c();
      var16.a(7, DefaultVertexFormats.f);
      var16.b((double)var3, (double)var2, (double)this.e).a(var8, var9, var10, var7).d();
      var16.b((double)var1, (double)var2, (double)this.e).a(var8, var9, var10, var7).d();
      var16.b((double)var1, (double)var4, (double)this.e).a(var12, var13, var14, var11).d();
      var16.b((double)var3, (double)var4, (double)this.e).a(var12, var13, var14, var11).d();
      var15.b();
      GlStateManager.j(7424);
      GlStateManager.k();
      GlStateManager.d();
      GlStateManager.w();
   }

   public void a(FontRenderer var1, String var2, int var3, int var4, int var5) {
      var1.a(var2, (float)(var3 - var1.a(var2) / 2), (float)var4, var5);
   }

   public void c(FontRenderer var1, String var2, int var3, int var4, int var5) {
      var1.a(var2, (float)var3, (float)var4, var5);
   }

   public void b(int var1, int var2, int var3, int var4, int var5, int var6) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.a();
      WorldRenderer var10 = var9.c();
      var10.a(7, DefaultVertexFormats.g);
      var10.b((double)(var1 + 0), (double)(var2 + var6), (double)this.e).a((double)((var3 + 0) * var7), (double)((var4 + var6) * var8)).d();
      var10.b((double)(var1 + var5), (double)(var2 + var6), (double)this.e).a((double)((var3 + var5) * var7), (double)((var4 + var6) * var8)).d();
      var10.b((double)(var1 + var5), (double)(var2 + 0), (double)this.e).a((double)((var3 + var5) * var7), (double)((var4 + 0) * var8)).d();
      var10.b((double)(var1 + 0), (double)(var2 + 0), (double)this.e).a((double)((var3 + 0) * var7), (double)((var4 + 0) * var8)).d();
      var9.b();
   }

   public void a(float var1, float var2, int var3, int var4, int var5, int var6) {
      float var7 = 0.00390625F;
      float var8 = 0.00390625F;
      Tessellator var9 = Tessellator.a();
      WorldRenderer var10 = var9.c();
      var10.a(7, DefaultVertexFormats.g);
      var10.b((double)(var1 + 0.0F), (double)(var2 + var6), (double)this.e).a((double)((var3 + 0) * var7), (double)((var4 + var6) * var8)).d();
      var10.b((double)(var1 + var5), (double)(var2 + var6), (double)this.e).a((double)((var3 + var5) * var7), (double)((var4 + var6) * var8)).d();
      var10.b((double)(var1 + var5), (double)(var2 + 0.0F), (double)this.e).a((double)((var3 + var5) * var7), (double)((var4 + 0) * var8)).d();
      var10.b((double)(var1 + 0.0F), (double)(var2 + 0.0F), (double)this.e).a((double)((var3 + 0) * var7), (double)((var4 + 0) * var8)).d();
      var9.b();
   }

   public void a(int var1, int var2, TextureAtlasSprite var3, int var4, int var5) {
      Tessellator var6 = Tessellator.a();
      WorldRenderer var7 = var6.c();
      var7.a(7, DefaultVertexFormats.g);
      var7.b((double)(var1 + 0), (double)(var2 + var5), (double)this.e).a((double)var3.e(), (double)var3.h()).d();
      var7.b((double)(var1 + var4), (double)(var2 + var5), (double)this.e).a((double)var3.f(), (double)var3.h()).d();
      var7.b((double)(var1 + var4), (double)(var2 + 0), (double)this.e).a((double)var3.f(), (double)var3.g()).d();
      var7.b((double)(var1 + 0), (double)(var2 + 0), (double)this.e).a((double)var3.e(), (double)var3.g()).d();
      var6.b();
   }

   public static void a(int var0, int var1, float var2, float var3, int var4, int var5, float var6, float var7) {
      float var8 = 1.0F / var6;
      float var9 = 1.0F / var7;
      Tessellator var10 = Tessellator.a();
      WorldRenderer var11 = var10.c();
      var11.a(7, DefaultVertexFormats.g);
      var11.b((double)var0, (double)(var1 + var5), 0.0).a((double)(var2 * var8), (double)((var3 + var5) * var9)).d();
      var11.b((double)(var0 + var4), (double)(var1 + var5), 0.0).a((double)((var2 + var4) * var8), (double)((var3 + var5) * var9)).d();
      var11.b((double)(var0 + var4), (double)var1, 0.0).a((double)((var2 + var4) * var8), (double)(var3 * var9)).d();
      var11.b((double)var0, (double)var1, 0.0).a((double)(var2 * var8), (double)(var3 * var9)).d();
      var10.b();
   }

   public static void a(int var0, int var1, float var2, float var3, int var4, int var5, int var6, int var7, float var8, float var9) {
      float var10 = 1.0F / var8;
      float var11 = 1.0F / var9;
      Tessellator var12 = Tessellator.a();
      WorldRenderer var13 = var12.c();
      var13.a(7, DefaultVertexFormats.g);
      var13.b((double)var0, (double)(var1 + var7), 0.0).a((double)(var2 * var10), (double)((var3 + var5) * var11)).d();
      var13.b((double)(var0 + var6), (double)(var1 + var7), 0.0).a((double)((var2 + var4) * var10), (double)((var3 + var5) * var11)).d();
      var13.b((double)(var0 + var6), (double)var1, 0.0).a((double)((var2 + var4) * var10), (double)(var3 * var11)).d();
      var13.b((double)var0, (double)var1, 0.0).a((double)(var2 * var10), (double)(var3 * var11)).d();
      var12.b();
   }
}
