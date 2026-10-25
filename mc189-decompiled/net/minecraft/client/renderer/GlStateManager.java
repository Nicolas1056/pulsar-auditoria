package net.minecraft.client.renderer;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;

public class GlStateManager {
   private static GlStateManager.AlphaState a = new GlStateManager.AlphaState();
   private static GlStateManager.BooleanState b = new GlStateManager.BooleanState(2896);
   private static GlStateManager.BooleanState[] c = new GlStateManager.BooleanState[8];
   private static GlStateManager.ColorMaterialState d = new GlStateManager.ColorMaterialState();
   private static GlStateManager.BlendState e = new GlStateManager.BlendState();
   private static GlStateManager.DepthState f = new GlStateManager.DepthState();
   private static GlStateManager.FogState g = new GlStateManager.FogState();
   private static GlStateManager.CullState h = new GlStateManager.CullState();
   private static GlStateManager.PolygonOffsetState i = new GlStateManager.PolygonOffsetState();
   private static GlStateManager.ColorLogicState j = new GlStateManager.ColorLogicState();
   private static GlStateManager.TexGenState k = new GlStateManager.TexGenState();
   private static GlStateManager.ClearState l = new GlStateManager.ClearState();
   private static GlStateManager.StencilState m = new GlStateManager.StencilState();
   private static GlStateManager.BooleanState n = new GlStateManager.BooleanState(2977);
   private static int o = 0;
   private static GlStateManager.TextureState[] p = new GlStateManager.TextureState[8];
   private static int q = 7425;
   private static GlStateManager.BooleanState r = new GlStateManager.BooleanState(32826);
   private static GlStateManager.ColorMask s = new GlStateManager.ColorMask();
   private static GlStateManager.Color t = new GlStateManager.Color();

   public static void a() {
      GL11.glPushAttrib(8256);
   }

   public static void b() {
      GL11.glPopAttrib();
   }

   public static void c() {
      a.a.a();
   }

   public static void d() {
      a.a.b();
   }

   public static void a(int var0, float var1) {
      if (var0 != a.b || var1 != a.c) {
         a.b = var0;
         a.c = var1;
         GL11.glAlphaFunc(var0, var1);
      }
   }

   public static void e() {
      b.b();
   }

   public static void f() {
      b.a();
   }

   public static void a(int var0) {
      c[var0].b();
   }

   public static void b(int var0) {
      c[var0].a();
   }

   public static void g() {
      d.a.b();
   }

   public static void h() {
      d.a.a();
   }

   public static void a(int var0, int var1) {
      if (var0 != d.b || var1 != d.c) {
         d.b = var0;
         d.c = var1;
         GL11.glColorMaterial(var0, var1);
      }
   }

   public static void i() {
      f.a.a();
   }

   public static void j() {
      f.a.b();
   }

   public static void c(int var0) {
      if (var0 != f.c) {
         f.c = var0;
         GL11.glDepthFunc(var0);
      }
   }

   public static void a(boolean var0) {
      if (var0 != f.b) {
         f.b = var0;
         GL11.glDepthMask(var0);
      }
   }

   public static void k() {
      e.a.a();
   }

   public static void l() {
      e.a.b();
   }

   public static void b(int var0, int var1) {
      if (var0 != e.b || var1 != e.c) {
         e.b = var0;
         e.c = var1;
         GL11.glBlendFunc(var0, var1);
      }
   }

   public static void a(int var0, int var1, int var2, int var3) {
      if (var0 != e.b || var1 != e.c || var2 != e.d || var3 != e.e) {
         e.b = var0;
         e.c = var1;
         e.d = var2;
         e.e = var3;
         OpenGlHelper.c(var0, var1, var2, var3);
      }
   }

   public static void m() {
      g.a.b();
   }

   public static void n() {
      g.a.a();
   }

   public static void d(int var0) {
      if (var0 != g.b) {
         g.b = var0;
         GL11.glFogi(2917, var0);
      }
   }

   public static void a(float var0) {
      if (var0 != g.c) {
         g.c = var0;
         GL11.glFogf(2914, var0);
      }
   }

   public static void b(float var0) {
      if (var0 != g.d) {
         g.d = var0;
         GL11.glFogf(2915, var0);
      }
   }

   public static void c(float var0) {
      if (var0 != g.e) {
         g.e = var0;
         GL11.glFogf(2916, var0);
      }
   }

   public static void o() {
      h.a.b();
   }

   public static void p() {
      h.a.a();
   }

   public static void e(int var0) {
      if (var0 != h.b) {
         h.b = var0;
         GL11.glCullFace(var0);
      }
   }

   public static void q() {
      i.a.b();
   }

   public static void r() {
      i.a.a();
   }

   public static void a(float var0, float var1) {
      if (var0 != i.c || var1 != i.d) {
         i.c = var0;
         i.d = var1;
         GL11.glPolygonOffset(var0, var1);
      }
   }

   public static void u() {
      j.a.b();
   }

   public static void v() {
      j.a.a();
   }

   public static void f(int var0) {
      if (var0 != j.b) {
         j.b = var0;
         GL11.glLogicOp(var0);
      }
   }

   public static void a(GlStateManager.TexGen var0) {
      c(var0).a.b();
   }

   public static void b(GlStateManager.TexGen var0) {
      c(var0).a.a();
   }

   public static void a(GlStateManager.TexGen var0, int var1) {
      GlStateManager.TexGenCoord var2 = c(var0);
      if (var1 != var2.c) {
         var2.c = var1;
         GL11.glTexGeni(var2.b, 9472, var1);
      }
   }

   public static void a(GlStateManager.TexGen var0, int var1, FloatBuffer var2) {
      GL11.glTexGen(c(var0).b, var1, var2);
   }

   private static GlStateManager.TexGenCoord c(GlStateManager.TexGen var0) {
      switch (var0) {
         case a:
            return k.a;
         case b:
            return k.b;
         case c:
            return k.c;
         case d:
            return k.d;
         default:
            return k.a;
      }
   }

   public static void g(int var0) {
      if (o != var0 - OpenGlHelper.q) {
         o = var0 - OpenGlHelper.q;
         OpenGlHelper.k(var0);
      }
   }

   public static void w() {
      p[o].a.b();
   }

   public static void x() {
      p[o].a.a();
   }

   public static int y() {
      return GL11.glGenTextures();
   }

   public static void h(int var0) {
      GL11.glDeleteTextures(var0);

      for (GlStateManager.TextureState var4 : p) {
         if (var4.b == var0) {
            var4.b = -1;
         }
      }
   }

   public static void i(int var0) {
      if (var0 != p[o].b) {
         p[o].b = var0;
         GL11.glBindTexture(3553, var0);
      }
   }

   public static void z() {
      n.b();
   }

   public static void A() {
      n.a();
   }

   public static void j(int var0) {
      if (var0 != q) {
         q = var0;
         GL11.glShadeModel(var0);
      }
   }

   public static void B() {
      r.b();
   }

   public static void C() {
      r.a();
   }

   public static void b(int var0, int var1, int var2, int var3) {
      GL11.glViewport(var0, var1, var2, var3);
   }

   public static void a(boolean var0, boolean var1, boolean var2, boolean var3) {
      if (var0 != s.a || var1 != s.b || var2 != s.c || var3 != s.d) {
         s.a = var0;
         s.b = var1;
         s.c = var2;
         s.d = var3;
         GL11.glColorMask(var0, var1, var2, var3);
      }
   }

   public static void a(double var0) {
      if (var0 != l.a) {
         l.a = var0;
         GL11.glClearDepth(var0);
      }
   }

   public static void a(float var0, float var1, float var2, float var3) {
      if (var0 != l.b.a || var1 != l.b.b || var2 != l.b.c || var3 != l.b.d) {
         l.b.a = var0;
         l.b.b = var1;
         l.b.c = var2;
         l.b.d = var3;
         GL11.glClearColor(var0, var1, var2, var3);
      }
   }

   public static void m(int var0) {
      GL11.glClear(var0);
   }

   public static void n(int var0) {
      GL11.glMatrixMode(var0);
   }

   public static void D() {
      GL11.glLoadIdentity();
   }

   public static void E() {
      GL11.glPushMatrix();
   }

   public static void F() {
      GL11.glPopMatrix();
   }

   public static void a(int var0, FloatBuffer var1) {
      GL11.glGetFloat(var0, var1);
   }

   public static void a(double var0, double var2, double var4, double var6, double var8, double var10) {
      GL11.glOrtho(var0, var2, var4, var6, var8, var10);
   }

   public static void b(float var0, float var1, float var2, float var3) {
      GL11.glRotatef(var0, var1, var2, var3);
   }

   public static void a(float var0, float var1, float var2) {
      GL11.glScalef(var0, var1, var2);
   }

   public static void a(double var0, double var2, double var4) {
      GL11.glScaled(var0, var2, var4);
   }

   public static void b(float var0, float var1, float var2) {
      GL11.glTranslatef(var0, var1, var2);
   }

   public static void b(double var0, double var2, double var4) {
      GL11.glTranslated(var0, var2, var4);
   }

   public static void a(FloatBuffer var0) {
      GL11.glMultMatrix(var0);
   }

   public static void c(float var0, float var1, float var2, float var3) {
      if (var0 != t.a || var1 != t.b || var2 != t.c || var3 != t.d) {
         t.a = var0;
         t.b = var1;
         t.c = var2;
         t.d = var3;
         GL11.glColor4f(var0, var1, var2, var3);
      }
   }

   public static void c(float var0, float var1, float var2) {
      c(var0, var1, var2, 1.0F);
   }

   public static void G() {
      t.a = t.b = t.c = t.d = -1.0F;
   }

   public static void o(int var0) {
      GL11.glCallList(var0);
   }

   static {
      for (int var0 = 0; var0 < 8; var0++) {
         c[var0] = new GlStateManager.BooleanState(16384 + var0);
      }

      for (int var1 = 0; var1 < 8; var1++) {
         p[var1] = new GlStateManager.TextureState();
      }
   }

   static class AlphaState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(3008);
      public int b = 519;
      public float c = -1.0F;

      private AlphaState() {
      }
   }

   static class BlendState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(3042);
      public int b = 1;
      public int c = 0;
      public int d = 1;
      public int e = 0;

      private BlendState() {
      }
   }

   static class BooleanState {
      private final int a;
      private boolean b = false;

      public BooleanState(int var1) {
         this.a = var1;
      }

      public void a() {
         this.a(false);
      }

      public void b() {
         this.a(true);
      }

      public void a(boolean var1) {
         if (var1 != this.b) {
            this.b = var1;
            if (var1) {
               GL11.glEnable(this.a);
            } else {
               GL11.glDisable(this.a);
            }
         }
      }
   }

   static class ClearState {
      public double a = 1.0;
      public GlStateManager.Color b = new GlStateManager.Color(0.0F, 0.0F, 0.0F, 0.0F);
      public int c = 0;

      private ClearState() {
      }
   }

   static class Color {
      public float a = 1.0F;
      public float b = 1.0F;
      public float c = 1.0F;
      public float d = 1.0F;

      public Color() {
      }

      public Color(float var1, float var2, float var3, float var4) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
         this.d = var4;
      }
   }

   static class ColorLogicState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(3058);
      public int b = 5379;

      private ColorLogicState() {
      }
   }

   static class ColorMask {
      public boolean a = true;
      public boolean b = true;
      public boolean c = true;
      public boolean d = true;

      private ColorMask() {
      }
   }

   static class ColorMaterialState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(2903);
      public int b = 1032;
      public int c = 5634;

      private ColorMaterialState() {
      }
   }

   static class CullState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(2884);
      public int b = 1029;

      private CullState() {
      }
   }

   static class DepthState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(2929);
      public boolean b = true;
      public int c = 513;

      private DepthState() {
      }
   }

   static class FogState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(2912);
      public int b = 2048;
      public float c = 1.0F;
      public float d = 0.0F;
      public float e = 1.0F;

      private FogState() {
      }
   }

   static class PolygonOffsetState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(32823);
      public GlStateManager.BooleanState b = new GlStateManager.BooleanState(10754);
      public float c = 0.0F;
      public float d = 0.0F;

      private PolygonOffsetState() {
      }
   }

   static class StencilFunc {
      public int a = 519;
      public int b = 0;
      public int c = -1;

      private StencilFunc() {
      }
   }

   static class StencilState {
      public GlStateManager.StencilFunc a = new GlStateManager.StencilFunc();
      public int b = -1;
      public int c = 7680;
      public int d = 7680;
      public int e = 7680;

      private StencilState() {
      }
   }

   public static enum TexGen {
      a,
      b,
      c,
      d;
   }

   static class TexGenCoord {
      public GlStateManager.BooleanState a;
      public int b;
      public int c = -1;

      public TexGenCoord(int var1, int var2) {
         this.b = var1;
         this.a = new GlStateManager.BooleanState(var2);
      }
   }

   static class TexGenState {
      public GlStateManager.TexGenCoord a = new GlStateManager.TexGenCoord(8192, 3168);
      public GlStateManager.TexGenCoord b = new GlStateManager.TexGenCoord(8193, 3169);
      public GlStateManager.TexGenCoord c = new GlStateManager.TexGenCoord(8194, 3170);
      public GlStateManager.TexGenCoord d = new GlStateManager.TexGenCoord(8195, 3171);

      private TexGenState() {
      }
   }

   static class TextureState {
      public GlStateManager.BooleanState a = new GlStateManager.BooleanState(3553);
      public int b = 0;

      private TextureState() {
      }
   }
}
