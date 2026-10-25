package net.minecraft.client;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.util.IProgressUpdate;
import net.minecraft.util.MinecraftError;

public class LoadingScreenRenderer implements IProgressUpdate {
   private String a = "";
   private Minecraft b;
   private String c = "";
   private long d = Minecraft.J();
   private boolean e;
   private ScaledResolution f;
   private Framebuffer g;

   public LoadingScreenRenderer(Minecraft var1) {
      this.b = var1;
      this.f = new ScaledResolution(var1);
      this.g = new Framebuffer(var1.d, var1.e, false);
      this.g.a(9728);
   }

   @Override
   public void b(String var1) {
      this.e = false;
      this.d(var1);
   }

   @Override
   public void a(String var1) {
      this.e = true;
      this.d(var1);
   }

   private void d(String var1) {
      this.c = var1;
      if (!this.b.B) {
         if (!this.e) {
            throw new MinecraftError();
         }
      } else {
         GlStateManager.m(256);
         GlStateManager.n(5889);
         GlStateManager.D();
         if (OpenGlHelper.i()) {
            int var2 = this.f.e();
            GlStateManager.a(0.0, this.f.a() * var2, this.f.b() * var2, 0.0, 100.0, 300.0);
         } else {
            ScaledResolution var3 = new ScaledResolution(this.b);
            GlStateManager.a(0.0, var3.c(), var3.d(), 0.0, 100.0, 300.0);
         }

         GlStateManager.n(5888);
         GlStateManager.D();
         GlStateManager.b(0.0F, 0.0F, -200.0F);
      }
   }

   @Override
   public void c(String var1) {
      if (!this.b.B) {
         if (!this.e) {
            throw new MinecraftError();
         }
      } else {
         this.d = 0L;
         this.a = var1;
         this.a(-1);
         this.d = 0L;
      }
   }

   @Override
   public void a(int var1) {
      if (!this.b.B) {
         if (!this.e) {
            throw new MinecraftError();
         }
      } else {
         long var2 = Minecraft.J();
         if (var2 - this.d >= 100L) {
            this.d = var2;
            ScaledResolution var4 = new ScaledResolution(this.b);
            int var5 = var4.e();
            int var6 = var4.a();
            int var7 = var4.b();
            if (OpenGlHelper.i()) {
               this.g.f();
            } else {
               GlStateManager.m(256);
            }

            this.g.a(false);
            GlStateManager.n(5889);
            GlStateManager.D();
            GlStateManager.a(0.0, var4.c(), var4.d(), 0.0, 100.0, 300.0);
            GlStateManager.n(5888);
            GlStateManager.D();
            GlStateManager.b(0.0F, 0.0F, -200.0F);
            if (!OpenGlHelper.i()) {
               GlStateManager.m(16640);
            }

            Tessellator var8 = Tessellator.a();
            WorldRenderer var9 = var8.c();
            this.b.P().a(Gui.b);
            float var10 = 32.0F;
            var9.a(7, DefaultVertexFormats.i);
            var9.b(0.0, (double)var7, 0.0).a(0.0, (double)(var7 / var10)).b(64, 64, 64, 255).d();
            var9.b((double)var6, (double)var7, 0.0).a((double)(var6 / var10), (double)(var7 / var10)).b(64, 64, 64, 255).d();
            var9.b((double)var6, 0.0, 0.0).a((double)(var6 / var10), 0.0).b(64, 64, 64, 255).d();
            var9.b(0.0, 0.0, 0.0).a(0.0, 0.0).b(64, 64, 64, 255).d();
            var8.b();
            if (var1 >= 0) {
               byte var11 = 100;
               byte var12 = 2;
               int var13 = var6 / 2 - var11 / 2;
               int var14 = var7 / 2 + 16;
               GlStateManager.x();
               var9.a(7, DefaultVertexFormats.f);
               var9.b((double)var13, (double)var14, 0.0).b(128, 128, 128, 255).d();
               var9.b((double)var13, (double)(var14 + var12), 0.0).b(128, 128, 128, 255).d();
               var9.b((double)(var13 + var11), (double)(var14 + var12), 0.0).b(128, 128, 128, 255).d();
               var9.b((double)(var13 + var11), (double)var14, 0.0).b(128, 128, 128, 255).d();
               var9.b((double)var13, (double)var14, 0.0).b(128, 255, 128, 255).d();
               var9.b((double)var13, (double)(var14 + var12), 0.0).b(128, 255, 128, 255).d();
               var9.b((double)(var13 + var1), (double)(var14 + var12), 0.0).b(128, 255, 128, 255).d();
               var9.b((double)(var13 + var1), (double)var14, 0.0).b(128, 255, 128, 255).d();
               var8.b();
               GlStateManager.w();
            }

            GlStateManager.l();
            GlStateManager.a(770, 771, 1, 0);
            this.b.k.a(this.c, (float)((var6 - this.b.k.a(this.c)) / 2), (float)(var7 / 2 - 4 - 16), 16777215);
            this.b.k.a(this.a, (float)((var6 - this.b.k.a(this.a)) / 2), (float)(var7 / 2 - 4 + 8), 16777215);
            this.g.e();
            if (OpenGlHelper.i()) {
               this.g.c(var6 * var5, var7 * var5);
            }

            this.b.h();

            try {
               Thread.yield();
            } catch (Exception var15) {
            }
         }
      }
   }

   @Override
   public void a() {
   }
}
