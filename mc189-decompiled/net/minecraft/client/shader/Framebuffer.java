package net.minecraft.client.shader;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class Framebuffer {
   public int a;
   public int b;
   public int c;
   public int d;
   public boolean e;
   public int f;
   public int g;
   public int h;
   public float[] i;
   public int j;

   public Framebuffer(int var1, int var2, boolean var3) {
      this.e = var3;
      this.f = -1;
      this.g = -1;
      this.h = -1;
      this.i = new float[4];
      this.i[0] = 1.0F;
      this.i[1] = 1.0F;
      this.i[2] = 1.0F;
      this.i[3] = 0.0F;
      this.a(var1, var2);
   }

   public void a(int var1, int var2) {
      if (!OpenGlHelper.i()) {
         this.c = var1;
         this.d = var2;
      } else {
         GlStateManager.j();
         if (this.f >= 0) {
            this.a();
         }

         this.b(var1, var2);
         this.b();
         OpenGlHelper.h(OpenGlHelper.c, 0);
      }
   }

   public void a() {
      if (OpenGlHelper.i()) {
         this.d();
         this.e();
         if (this.h > -1) {
            OpenGlHelper.h(this.h);
            this.h = -1;
         }

         if (this.g > -1) {
            TextureUtil.a(this.g);
            this.g = -1;
         }

         if (this.f > -1) {
            OpenGlHelper.h(OpenGlHelper.c, 0);
            OpenGlHelper.i(this.f);
            this.f = -1;
         }
      }
   }

   public void b(int var1, int var2) {
      this.c = var1;
      this.d = var2;
      this.a = var1;
      this.b = var2;
      if (!OpenGlHelper.i()) {
         this.f();
      } else {
         this.f = OpenGlHelper.g();
         this.g = TextureUtil.a();
         if (this.e) {
            this.h = OpenGlHelper.h();
         }

         this.a(9728);
         GlStateManager.i(this.g);
         GL11.glTexImage2D(3553, 0, 32856, this.a, this.b, 0, 6408, 5121, (ByteBuffer)null);
         OpenGlHelper.h(OpenGlHelper.c, this.f);
         OpenGlHelper.a(OpenGlHelper.c, OpenGlHelper.e, 3553, this.g, 0);
         if (this.e) {
            OpenGlHelper.i(OpenGlHelper.d, this.h);
            OpenGlHelper.a(OpenGlHelper.d, 33190, this.a, this.b);
            OpenGlHelper.b(OpenGlHelper.c, OpenGlHelper.f, OpenGlHelper.d, this.h);
         }

         this.f();
         this.d();
      }
   }

   public void a(int var1) {
      if (OpenGlHelper.i()) {
         this.j = var1;
         GlStateManager.i(this.g);
         GL11.glTexParameterf(3553, 10241, var1);
         GL11.glTexParameterf(3553, 10240, var1);
         GL11.glTexParameterf(3553, 10242, 10496.0F);
         GL11.glTexParameterf(3553, 10243, 10496.0F);
         GlStateManager.i(0);
      }
   }

   public void b() {
      int var1 = OpenGlHelper.j(OpenGlHelper.c);
      if (var1 != OpenGlHelper.g) {
         if (var1 == OpenGlHelper.h) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_ATTACHMENT");
         } else if (var1 == OpenGlHelper.i) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_MISSING_ATTACHMENT");
         } else if (var1 == OpenGlHelper.j) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_DRAW_BUFFER");
         } else if (var1 == OpenGlHelper.k) {
            throw new RuntimeException("GL_FRAMEBUFFER_INCOMPLETE_READ_BUFFER");
         } else {
            throw new RuntimeException("glCheckFramebufferStatus returned unknown status:" + var1);
         }
      }
   }

   public void c() {
      if (OpenGlHelper.i()) {
         GlStateManager.i(this.g);
      }
   }

   public void d() {
      if (OpenGlHelper.i()) {
         GlStateManager.i(0);
      }
   }

   public void a(boolean var1) {
      if (OpenGlHelper.i()) {
         OpenGlHelper.h(OpenGlHelper.c, this.f);
         if (var1) {
            GlStateManager.b(0, 0, this.c, this.d);
         }
      }
   }

   public void e() {
      if (OpenGlHelper.i()) {
         OpenGlHelper.h(OpenGlHelper.c, 0);
      }
   }

   public void a(float var1, float var2, float var3, float var4) {
      this.i[0] = var1;
      this.i[1] = var2;
      this.i[2] = var3;
      this.i[3] = var4;
   }

   public void c(int var1, int var2) {
      this.a(var1, var2, true);
   }

   public void a(int var1, int var2, boolean var3) {
      if (OpenGlHelper.i()) {
         GlStateManager.a(true, true, true, false);
         GlStateManager.i();
         GlStateManager.a(false);
         GlStateManager.n(5889);
         GlStateManager.D();
         GlStateManager.a(0.0, var1, var2, 0.0, 1000.0, 3000.0);
         GlStateManager.n(5888);
         GlStateManager.D();
         GlStateManager.b(0.0F, 0.0F, -2000.0F);
         GlStateManager.b(0, 0, var1, var2);
         GlStateManager.w();
         GlStateManager.f();
         GlStateManager.c();
         if (var3) {
            GlStateManager.k();
            GlStateManager.g();
         }

         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.c();
         float var4 = var1;
         float var5 = var2;
         float var6 = (float)this.c / this.a;
         float var7 = (float)this.d / this.b;
         Tessellator var8 = Tessellator.a();
         WorldRenderer var9 = var8.c();
         var9.a(7, DefaultVertexFormats.i);
         var9.b(0.0, (double)var5, 0.0).a(0.0, 0.0).b(255, 255, 255, 255).d();
         var9.b((double)var4, (double)var5, 0.0).a((double)var6, 0.0).b(255, 255, 255, 255).d();
         var9.b((double)var4, 0.0, 0.0).a((double)var6, (double)var7).b(255, 255, 255, 255).d();
         var9.b(0.0, 0.0, 0.0).a(0.0, (double)var7).b(255, 255, 255, 255).d();
         var8.b();
         this.d();
         GlStateManager.a(true);
         GlStateManager.a(true, true, true, true);
      }
   }

   public void f() {
      this.a(true);
      GlStateManager.a(this.i[0], this.i[1], this.i[2], this.i[3]);
      short var1 = 16384;
      if (this.e) {
         GlStateManager.a(1.0);
         var1 |= 256;
      }

      GlStateManager.m(var1);
      this.e();
   }
}
