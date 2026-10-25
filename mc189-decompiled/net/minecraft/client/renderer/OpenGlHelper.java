package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;
import oshi.SystemInfo;
import oshi.hardware.Processor;

public class OpenGlHelper {
   public static boolean a;
   public static boolean b;
   public static int c;
   public static int d;
   public static int e;
   public static int f;
   public static int g;
   public static int h;
   public static int i;
   public static int j;
   public static int k;
   private static int T;
   public static boolean l;
   private static boolean U;
   private static boolean V;
   public static int m;
   public static int n;
   public static int o;
   public static int p;
   private static boolean W;
   public static int q;
   public static int r;
   public static int s;
   private static boolean X;
   public static int t;
   public static int u;
   public static int v;
   public static int w;
   public static int x;
   public static int y;
   public static int z;
   public static int A;
   public static int B;
   public static int C;
   public static int D;
   public static int E;
   public static int F;
   public static int G;
   public static int H;
   public static int I;
   public static int J;
   public static int K;
   public static int L;
   private static boolean Y;
   public static boolean M;
   public static boolean N;
   public static boolean O;
   private static String Z = "";
   private static String aa;
   public static boolean P;
   public static boolean Q;
   private static boolean ab;
   public static int R;
   public static int S;

   public static void a() {
      ContextCapabilities var0 = GLContext.getCapabilities();
      W = var0.GL_ARB_multitexture && !var0.OpenGL13;
      X = var0.GL_ARB_texture_env_combine && !var0.OpenGL13;
      if (W) {
         Z = Z + "Using ARB_multitexture.\n";
         q = 33984;
         r = 33985;
         s = 33986;
      } else {
         Z = Z + "Using GL 1.3 multitexturing.\n";
         q = 33984;
         r = 33985;
         s = 33986;
      }

      if (X) {
         Z = Z + "Using ARB_texture_env_combine.\n";
         t = 34160;
         u = 34165;
         v = 34167;
         w = 34166;
         x = 34168;
         y = 34161;
         z = 34176;
         A = 34177;
         B = 34178;
         C = 34192;
         D = 34193;
         E = 34194;
         F = 34162;
         G = 34184;
         H = 34185;
         I = 34186;
         J = 34200;
         K = 34201;
         L = 34202;
      } else {
         Z = Z + "Using GL 1.3 texture combiners.\n";
         t = 34160;
         u = 34165;
         v = 34167;
         w = 34166;
         x = 34168;
         y = 34161;
         z = 34176;
         A = 34177;
         B = 34178;
         C = 34192;
         D = 34193;
         E = 34194;
         F = 34162;
         G = 34184;
         H = 34185;
         I = 34186;
         J = 34200;
         K = 34201;
         L = 34202;
      }

      M = var0.GL_EXT_blend_func_separate && !var0.OpenGL14;
      Y = var0.OpenGL14 || var0.GL_EXT_blend_func_separate;
      l = Y && (var0.GL_ARB_framebuffer_object || var0.GL_EXT_framebuffer_object || var0.OpenGL30);
      if (l) {
         Z = Z + "Using framebuffer objects because ";
         if (var0.OpenGL30) {
            Z = Z + "OpenGL 3.0 is supported and separate blending is supported.\n";
            T = 0;
            c = 36160;
            d = 36161;
            e = 36064;
            f = 36096;
            g = 36053;
            h = 36054;
            i = 36055;
            j = 36059;
            k = 36060;
         } else if (var0.GL_ARB_framebuffer_object) {
            Z = Z + "ARB_framebuffer_object is supported and separate blending is supported.\n";
            T = 1;
            c = 36160;
            d = 36161;
            e = 36064;
            f = 36096;
            g = 36053;
            i = 36055;
            h = 36054;
            j = 36059;
            k = 36060;
         } else if (var0.GL_EXT_framebuffer_object) {
            Z = Z + "EXT_framebuffer_object is supported.\n";
            T = 2;
            c = 36160;
            d = 36161;
            e = 36064;
            f = 36096;
            g = 36053;
            i = 36055;
            h = 36054;
            j = 36059;
            k = 36060;
         }
      } else {
         Z = Z + "Not using framebuffer objects because ";
         Z = Z + "OpenGL 1.4 is " + (var0.OpenGL14 ? "" : "not ") + "supported, ";
         Z = Z + "EXT_blend_func_separate is " + (var0.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
         Z = Z + "OpenGL 3.0 is " + (var0.OpenGL30 ? "" : "not ") + "supported, ";
         Z = Z + "ARB_framebuffer_object is " + (var0.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
         Z = Z + "EXT_framebuffer_object is " + (var0.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
      }

      N = var0.OpenGL21;
      U = N || var0.GL_ARB_vertex_shader && var0.GL_ARB_fragment_shader && var0.GL_ARB_shader_objects;
      Z = Z + "Shaders are " + (U ? "" : "not ") + "available because ";
      if (U) {
         if (var0.OpenGL21) {
            Z = Z + "OpenGL 2.1 is supported.\n";
            V = false;
            m = 35714;
            n = 35713;
            o = 35633;
            p = 35632;
         } else {
            Z = Z + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
            V = true;
            m = 35714;
            n = 35713;
            o = 35633;
            p = 35632;
         }
      } else {
         Z = Z + "OpenGL 2.1 is " + (var0.OpenGL21 ? "" : "not ") + "supported, ";
         Z = Z + "ARB_shader_objects is " + (var0.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
         Z = Z + "ARB_vertex_shader is " + (var0.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
         Z = Z + "ARB_fragment_shader is " + (var0.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
      }

      O = l && U;
      String var1 = GL11.glGetString(7936).toLowerCase();
      a = var1.contains("nvidia");
      ab = !var0.OpenGL15 && var0.GL_ARB_vertex_buffer_object;
      P = var0.OpenGL15 || ab;
      Z = Z + "VBOs are " + (P ? "" : "not ") + "available because ";
      if (P) {
         if (ab) {
            Z = Z + "ARB_vertex_buffer_object is supported.\n";
            S = 35044;
            R = 34962;
         } else {
            Z = Z + "OpenGL 1.5 is supported.\n";
            S = 35044;
            R = 34962;
         }
      }

      b = var1.contains("ati");
      if (b) {
         if (P) {
            Q = true;
         } else {
            GameSettings.Options.f.a(16.0F);
         }
      }

      try {
         Processor[] var2 = new SystemInfo().getHardware().getProcessors();
         aa = String.format("%dx %s", var2.length, var2[0]).replaceAll("\\s+", " ");
      } catch (Throwable var3) {
      }
   }

   public static boolean b() {
      return O;
   }

   public static String c() {
      return Z;
   }

   public static int a(int var0, int var1) {
      return V ? ARBShaderObjects.glGetObjectParameteriARB(var0, var1) : GL20.glGetProgrami(var0, var1);
   }

   public static void b(int var0, int var1) {
      if (V) {
         ARBShaderObjects.glAttachObjectARB(var0, var1);
      } else {
         GL20.glAttachShader(var0, var1);
      }
   }

   public static void a(int var0) {
      if (V) {
         ARBShaderObjects.glDeleteObjectARB(var0);
      } else {
         GL20.glDeleteShader(var0);
      }
   }

   public static int b(int var0) {
      return V ? ARBShaderObjects.glCreateShaderObjectARB(var0) : GL20.glCreateShader(var0);
   }

   public static void a(int var0, ByteBuffer var1) {
      if (V) {
         ARBShaderObjects.glShaderSourceARB(var0, var1);
      } else {
         GL20.glShaderSource(var0, var1);
      }
   }

   public static void c(int var0) {
      if (V) {
         ARBShaderObjects.glCompileShaderARB(var0);
      } else {
         GL20.glCompileShader(var0);
      }
   }

   public static int c(int var0, int var1) {
      return V ? ARBShaderObjects.glGetObjectParameteriARB(var0, var1) : GL20.glGetShaderi(var0, var1);
   }

   public static String d(int var0, int var1) {
      return V ? ARBShaderObjects.glGetInfoLogARB(var0, var1) : GL20.glGetShaderInfoLog(var0, var1);
   }

   public static String e(int var0, int var1) {
      return V ? ARBShaderObjects.glGetInfoLogARB(var0, var1) : GL20.glGetProgramInfoLog(var0, var1);
   }

   public static void d(int var0) {
      if (V) {
         ARBShaderObjects.glUseProgramObjectARB(var0);
      } else {
         GL20.glUseProgram(var0);
      }
   }

   public static int d() {
      return V ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
   }

   public static void e(int var0) {
      if (V) {
         ARBShaderObjects.glDeleteObjectARB(var0);
      } else {
         GL20.glDeleteProgram(var0);
      }
   }

   public static void f(int var0) {
      if (V) {
         ARBShaderObjects.glLinkProgramARB(var0);
      } else {
         GL20.glLinkProgram(var0);
      }
   }

   public static int a(int var0, CharSequence var1) {
      return V ? ARBShaderObjects.glGetUniformLocationARB(var0, var1) : GL20.glGetUniformLocation(var0, var1);
   }

   public static void a(int var0, IntBuffer var1) {
      if (V) {
         ARBShaderObjects.glUniform1ARB(var0, var1);
      } else {
         GL20.glUniform1(var0, var1);
      }
   }

   public static void f(int var0, int var1) {
      if (V) {
         ARBShaderObjects.glUniform1iARB(var0, var1);
      } else {
         GL20.glUniform1i(var0, var1);
      }
   }

   public static void a(int var0, FloatBuffer var1) {
      if (V) {
         ARBShaderObjects.glUniform1ARB(var0, var1);
      } else {
         GL20.glUniform1(var0, var1);
      }
   }

   public static void b(int var0, IntBuffer var1) {
      if (V) {
         ARBShaderObjects.glUniform2ARB(var0, var1);
      } else {
         GL20.glUniform2(var0, var1);
      }
   }

   public static void b(int var0, FloatBuffer var1) {
      if (V) {
         ARBShaderObjects.glUniform2ARB(var0, var1);
      } else {
         GL20.glUniform2(var0, var1);
      }
   }

   public static void c(int var0, IntBuffer var1) {
      if (V) {
         ARBShaderObjects.glUniform3ARB(var0, var1);
      } else {
         GL20.glUniform3(var0, var1);
      }
   }

   public static void c(int var0, FloatBuffer var1) {
      if (V) {
         ARBShaderObjects.glUniform3ARB(var0, var1);
      } else {
         GL20.glUniform3(var0, var1);
      }
   }

   public static void d(int var0, IntBuffer var1) {
      if (V) {
         ARBShaderObjects.glUniform4ARB(var0, var1);
      } else {
         GL20.glUniform4(var0, var1);
      }
   }

   public static void d(int var0, FloatBuffer var1) {
      if (V) {
         ARBShaderObjects.glUniform4ARB(var0, var1);
      } else {
         GL20.glUniform4(var0, var1);
      }
   }

   public static void a(int var0, boolean var1, FloatBuffer var2) {
      if (V) {
         ARBShaderObjects.glUniformMatrix2ARB(var0, var1, var2);
      } else {
         GL20.glUniformMatrix2(var0, var1, var2);
      }
   }

   public static void b(int var0, boolean var1, FloatBuffer var2) {
      if (V) {
         ARBShaderObjects.glUniformMatrix3ARB(var0, var1, var2);
      } else {
         GL20.glUniformMatrix3(var0, var1, var2);
      }
   }

   public static void c(int var0, boolean var1, FloatBuffer var2) {
      if (V) {
         ARBShaderObjects.glUniformMatrix4ARB(var0, var1, var2);
      } else {
         GL20.glUniformMatrix4(var0, var1, var2);
      }
   }

   public static int b(int var0, CharSequence var1) {
      return V ? ARBVertexShader.glGetAttribLocationARB(var0, var1) : GL20.glGetAttribLocation(var0, var1);
   }

   public static int e() {
      return ab ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
   }

   public static void g(int var0, int var1) {
      if (ab) {
         ARBVertexBufferObject.glBindBufferARB(var0, var1);
      } else {
         GL15.glBindBuffer(var0, var1);
      }
   }

   public static void a(int var0, ByteBuffer var1, int var2) {
      if (ab) {
         ARBVertexBufferObject.glBufferDataARB(var0, var1, var2);
      } else {
         GL15.glBufferData(var0, var1, var2);
      }
   }

   public static void g(int var0) {
      if (ab) {
         ARBVertexBufferObject.glDeleteBuffersARB(var0);
      } else {
         GL15.glDeleteBuffers(var0);
      }
   }

   public static boolean f() {
      return P && Minecraft.A().t.u;
   }

   public static void h(int var0, int var1) {
      if (l) {
         switch (T) {
            case 0:
               GL30.glBindFramebuffer(var0, var1);
               break;
            case 1:
               ARBFramebufferObject.glBindFramebuffer(var0, var1);
               break;
            case 2:
               EXTFramebufferObject.glBindFramebufferEXT(var0, var1);
         }
      }
   }

   public static void i(int var0, int var1) {
      if (l) {
         switch (T) {
            case 0:
               GL30.glBindRenderbuffer(var0, var1);
               break;
            case 1:
               ARBFramebufferObject.glBindRenderbuffer(var0, var1);
               break;
            case 2:
               EXTFramebufferObject.glBindRenderbufferEXT(var0, var1);
         }
      }
   }

   public static void h(int var0) {
      if (l) {
         switch (T) {
            case 0:
               GL30.glDeleteRenderbuffers(var0);
               break;
            case 1:
               ARBFramebufferObject.glDeleteRenderbuffers(var0);
               break;
            case 2:
               EXTFramebufferObject.glDeleteRenderbuffersEXT(var0);
         }
      }
   }

   public static void i(int var0) {
      if (l) {
         switch (T) {
            case 0:
               GL30.glDeleteFramebuffers(var0);
               break;
            case 1:
               ARBFramebufferObject.glDeleteFramebuffers(var0);
               break;
            case 2:
               EXTFramebufferObject.glDeleteFramebuffersEXT(var0);
         }
      }
   }

   public static int g() {
      if (!l) {
         return -1;
      } else {
         switch (T) {
            case 0:
               return GL30.glGenFramebuffers();
            case 1:
               return ARBFramebufferObject.glGenFramebuffers();
            case 2:
               return EXTFramebufferObject.glGenFramebuffersEXT();
            default:
               return -1;
         }
      }
   }

   public static int h() {
      if (!l) {
         return -1;
      } else {
         switch (T) {
            case 0:
               return GL30.glGenRenderbuffers();
            case 1:
               return ARBFramebufferObject.glGenRenderbuffers();
            case 2:
               return EXTFramebufferObject.glGenRenderbuffersEXT();
            default:
               return -1;
         }
      }
   }

   public static void a(int var0, int var1, int var2, int var3) {
      if (l) {
         switch (T) {
            case 0:
               GL30.glRenderbufferStorage(var0, var1, var2, var3);
               break;
            case 1:
               ARBFramebufferObject.glRenderbufferStorage(var0, var1, var2, var3);
               break;
            case 2:
               EXTFramebufferObject.glRenderbufferStorageEXT(var0, var1, var2, var3);
         }
      }
   }

   public static void b(int var0, int var1, int var2, int var3) {
      if (l) {
         switch (T) {
            case 0:
               GL30.glFramebufferRenderbuffer(var0, var1, var2, var3);
               break;
            case 1:
               ARBFramebufferObject.glFramebufferRenderbuffer(var0, var1, var2, var3);
               break;
            case 2:
               EXTFramebufferObject.glFramebufferRenderbufferEXT(var0, var1, var2, var3);
         }
      }
   }

   public static int j(int var0) {
      if (!l) {
         return -1;
      } else {
         switch (T) {
            case 0:
               return GL30.glCheckFramebufferStatus(var0);
            case 1:
               return ARBFramebufferObject.glCheckFramebufferStatus(var0);
            case 2:
               return EXTFramebufferObject.glCheckFramebufferStatusEXT(var0);
            default:
               return -1;
         }
      }
   }

   public static void a(int var0, int var1, int var2, int var3, int var4) {
      if (l) {
         switch (T) {
            case 0:
               GL30.glFramebufferTexture2D(var0, var1, var2, var3, var4);
               break;
            case 1:
               ARBFramebufferObject.glFramebufferTexture2D(var0, var1, var2, var3, var4);
               break;
            case 2:
               EXTFramebufferObject.glFramebufferTexture2DEXT(var0, var1, var2, var3, var4);
         }
      }
   }

   public static void k(int var0) {
      if (W) {
         ARBMultitexture.glActiveTextureARB(var0);
      } else {
         GL13.glActiveTexture(var0);
      }
   }

   public static void l(int var0) {
      if (W) {
         ARBMultitexture.glClientActiveTextureARB(var0);
      } else {
         GL13.glClientActiveTexture(var0);
      }
   }

   public static void a(int var0, float var1, float var2) {
      if (W) {
         ARBMultitexture.glMultiTexCoord2fARB(var0, var1, var2);
      } else {
         GL13.glMultiTexCoord2f(var0, var1, var2);
      }
   }

   public static void c(int var0, int var1, int var2, int var3) {
      if (Y) {
         if (M) {
            EXTBlendFuncSeparate.glBlendFuncSeparateEXT(var0, var1, var2, var3);
         } else {
            GL14.glBlendFuncSeparate(var0, var1, var2, var3);
         }
      } else {
         GL11.glBlendFunc(var0, var1);
      }
   }

   public static boolean i() {
      return l && Minecraft.A().t.f;
   }

   public static String j() {
      return aa == null ? "<unknown>" : aa;
   }
}
