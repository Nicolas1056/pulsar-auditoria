package net.minecraft.client.shader;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderUniform {
   private static final Logger a = LogManager.getLogger();
   private int b;
   private final int c;
   private final int d;
   private final IntBuffer e;
   private final FloatBuffer f;
   private final String g;
   private boolean h;
   private final ShaderManager i;

   public ShaderUniform(String var1, int var2, int var3, ShaderManager var4) {
      this.g = var1;
      this.c = var3;
      this.d = var2;
      this.i = var4;
      if (var2 <= 3) {
         this.e = BufferUtils.createIntBuffer(var3);
         this.f = null;
      } else {
         this.e = null;
         this.f = BufferUtils.createFloatBuffer(var3);
      }

      this.b = -1;
      this.h();
   }

   private void h() {
      this.h = true;
      if (this.i != null) {
         this.i.d();
      }
   }

   public static int a(String var0) {
      byte var1 = -1;
      if (var0.equals("int")) {
         var1 = 0;
      } else if (var0.equals("float")) {
         var1 = 4;
      } else if (var0.startsWith("matrix")) {
         if (var0.endsWith("2x2")) {
            var1 = 8;
         } else if (var0.endsWith("3x3")) {
            var1 = 9;
         } else if (var0.endsWith("4x4")) {
            var1 = 10;
         }
      }

      return var1;
   }

   public void b(int var1) {
      this.b = var1;
   }

   public String a() {
      return this.g;
   }

   public void a(float var1) {
      ((Buffer)this.f).position(0);
      this.f.put(0, var1);
      this.h();
   }

   public void a(float var1, float var2) {
      ((Buffer)this.f).position(0);
      this.f.put(0, var1);
      this.f.put(1, var2);
      this.h();
   }

   public void a(float var1, float var2, float var3) {
      ((Buffer)this.f).position(0);
      this.f.put(0, var1);
      this.f.put(1, var2);
      this.f.put(2, var3);
      this.h();
   }

   public void a(float var1, float var2, float var3, float var4) {
      ((Buffer)this.f).position(0);
      this.f.put(var1);
      this.f.put(var2);
      this.f.put(var3);
      this.f.put(var4);
      ((Buffer)this.f).flip();
      this.h();
   }

   public void b(float var1, float var2, float var3, float var4) {
      ((Buffer)this.f).position(0);
      if (this.d >= 4) {
         this.f.put(0, var1);
      }

      if (this.d >= 5) {
         this.f.put(1, var2);
      }

      if (this.d >= 6) {
         this.f.put(2, var3);
      }

      if (this.d >= 7) {
         this.f.put(3, var4);
      }

      this.h();
   }

   public void a(int var1, int var2, int var3, int var4) {
      ((Buffer)this.e).position(0);
      if (this.d >= 0) {
         this.e.put(0, var1);
      }

      if (this.d >= 1) {
         this.e.put(1, var2);
      }

      if (this.d >= 2) {
         this.e.put(2, var3);
      }

      if (this.d >= 3) {
         this.e.put(3, var4);
      }

      this.h();
   }

   public void a(float[] var1) {
      if (var1.length < this.c) {
         a.warn("Uniform.set called with a too-small value array (expected " + this.c + ", got " + var1.length + "). Ignoring.");
      } else {
         ((Buffer)this.f).position(0);
         this.f.put(var1);
         ((Buffer)this.f).position(0);
         this.h();
      }
   }

   public void a(
      float var1,
      float var2,
      float var3,
      float var4,
      float var5,
      float var6,
      float var7,
      float var8,
      float var9,
      float var10,
      float var11,
      float var12,
      float var13,
      float var14,
      float var15,
      float var16
   ) {
      ((Buffer)this.f).position(0);
      this.f.put(0, var1);
      this.f.put(1, var2);
      this.f.put(2, var3);
      this.f.put(3, var4);
      this.f.put(4, var5);
      this.f.put(5, var6);
      this.f.put(6, var7);
      this.f.put(7, var8);
      this.f.put(8, var9);
      this.f.put(9, var10);
      this.f.put(10, var11);
      this.f.put(11, var12);
      this.f.put(12, var13);
      this.f.put(13, var14);
      this.f.put(14, var15);
      this.f.put(15, var16);
      this.h();
   }

   public void a(Matrix4f var1) {
      this.a(
         var1.m00,
         var1.m01,
         var1.m02,
         var1.m03,
         var1.m10,
         var1.m11,
         var1.m12,
         var1.m13,
         var1.m20,
         var1.m21,
         var1.m22,
         var1.m23,
         var1.m30,
         var1.m31,
         var1.m32,
         var1.m33
      );
   }

   public void b() {
      if (!this.h) {
      }

      this.h = false;
      if (this.d <= 3) {
         this.i();
      } else if (this.d <= 7) {
         this.j();
      } else {
         if (this.d > 10) {
            a.warn("Uniform.upload called, but type value (" + this.d + ") is not " + "a valid type. Ignoring.");
            return;
         }

         this.k();
      }
   }

   private void i() {
      switch (this.d) {
         case 0:
            OpenGlHelper.a(this.b, this.e);
            break;
         case 1:
            OpenGlHelper.b(this.b, this.e);
            break;
         case 2:
            OpenGlHelper.c(this.b, this.e);
            break;
         case 3:
            OpenGlHelper.d(this.b, this.e);
            break;
         default:
            a.warn("Uniform.upload called, but count value (" + this.c + ") is " + " not in the range of 1 to 4. Ignoring.");
      }
   }

   private void j() {
      switch (this.d) {
         case 4:
            OpenGlHelper.a(this.b, this.f);
            break;
         case 5:
            OpenGlHelper.b(this.b, this.f);
            break;
         case 6:
            OpenGlHelper.c(this.b, this.f);
            break;
         case 7:
            OpenGlHelper.d(this.b, this.f);
            break;
         default:
            a.warn("Uniform.upload called, but count value (" + this.c + ") is " + "not in the range of 1 to 4. Ignoring.");
      }
   }

   private void k() {
      switch (this.d) {
         case 8:
            OpenGlHelper.a(this.b, true, this.f);
            break;
         case 9:
            OpenGlHelper.b(this.b, true, this.f);
            break;
         case 10:
            OpenGlHelper.c(this.b, true, this.f);
      }
   }
}
