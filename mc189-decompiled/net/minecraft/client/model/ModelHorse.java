package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.MathHelper;

public class ModelHorse extends ModelBase {
   private ModelRenderer a;
   private ModelRenderer b;
   private ModelRenderer c;
   private ModelRenderer d;
   private ModelRenderer e;
   private ModelRenderer f;
   private ModelRenderer g;
   private ModelRenderer h;
   private ModelRenderer i;
   private ModelRenderer j;
   private ModelRenderer k;
   private ModelRenderer l;
   private ModelRenderer m;
   private ModelRenderer n;
   private ModelRenderer o;
   private ModelRenderer v;
   private ModelRenderer w;
   private ModelRenderer x;
   private ModelRenderer y;
   private ModelRenderer z;
   private ModelRenderer A;
   private ModelRenderer B;
   private ModelRenderer C;
   private ModelRenderer D;
   private ModelRenderer E;
   private ModelRenderer F;
   private ModelRenderer G;
   private ModelRenderer H;
   private ModelRenderer I;
   private ModelRenderer J;
   private ModelRenderer K;
   private ModelRenderer L;
   private ModelRenderer M;
   private ModelRenderer N;
   private ModelRenderer O;
   private ModelRenderer P;
   private ModelRenderer Q;
   private ModelRenderer R;
   private ModelRenderer S;

   public ModelHorse() {
      this.t = 128;
      this.u = 128;
      this.k = new ModelRenderer(this, 0, 34);
      this.k.a(-5.0F, -8.0F, -19.0F, 10, 10, 24);
      this.k.a(0.0F, 11.0F, 9.0F);
      this.l = new ModelRenderer(this, 44, 0);
      this.l.a(-1.0F, -1.0F, 0.0F, 2, 2, 3);
      this.l.a(0.0F, 3.0F, 14.0F);
      this.a(this.l, -1.134464F, 0.0F, 0.0F);
      this.m = new ModelRenderer(this, 38, 7);
      this.m.a(-1.5F, -2.0F, 3.0F, 3, 4, 7);
      this.m.a(0.0F, 3.0F, 14.0F);
      this.a(this.m, -1.134464F, 0.0F, 0.0F);
      this.n = new ModelRenderer(this, 24, 3);
      this.n.a(-1.5F, -4.5F, 9.0F, 3, 4, 7);
      this.n.a(0.0F, 3.0F, 14.0F);
      this.a(this.n, -1.40215F, 0.0F, 0.0F);
      this.o = new ModelRenderer(this, 78, 29);
      this.o.a(-2.5F, -2.0F, -2.5F, 4, 9, 5);
      this.o.a(4.0F, 9.0F, 11.0F);
      this.v = new ModelRenderer(this, 78, 43);
      this.v.a(-2.0F, 0.0F, -1.5F, 3, 5, 3);
      this.v.a(4.0F, 16.0F, 11.0F);
      this.w = new ModelRenderer(this, 78, 51);
      this.w.a(-2.5F, 5.1F, -2.0F, 4, 3, 4);
      this.w.a(4.0F, 16.0F, 11.0F);
      this.x = new ModelRenderer(this, 96, 29);
      this.x.a(-1.5F, -2.0F, -2.5F, 4, 9, 5);
      this.x.a(-4.0F, 9.0F, 11.0F);
      this.y = new ModelRenderer(this, 96, 43);
      this.y.a(-1.0F, 0.0F, -1.5F, 3, 5, 3);
      this.y.a(-4.0F, 16.0F, 11.0F);
      this.z = new ModelRenderer(this, 96, 51);
      this.z.a(-1.5F, 5.1F, -2.0F, 4, 3, 4);
      this.z.a(-4.0F, 16.0F, 11.0F);
      this.A = new ModelRenderer(this, 44, 29);
      this.A.a(-1.9F, -1.0F, -2.1F, 3, 8, 4);
      this.A.a(4.0F, 9.0F, -8.0F);
      this.B = new ModelRenderer(this, 44, 41);
      this.B.a(-1.9F, 0.0F, -1.6F, 3, 5, 3);
      this.B.a(4.0F, 16.0F, -8.0F);
      this.C = new ModelRenderer(this, 44, 51);
      this.C.a(-2.4F, 5.1F, -2.1F, 4, 3, 4);
      this.C.a(4.0F, 16.0F, -8.0F);
      this.D = new ModelRenderer(this, 60, 29);
      this.D.a(-1.1F, -1.0F, -2.1F, 3, 8, 4);
      this.D.a(-4.0F, 9.0F, -8.0F);
      this.E = new ModelRenderer(this, 60, 41);
      this.E.a(-1.1F, 0.0F, -1.6F, 3, 5, 3);
      this.E.a(-4.0F, 16.0F, -8.0F);
      this.F = new ModelRenderer(this, 60, 51);
      this.F.a(-1.6F, 5.1F, -2.1F, 4, 3, 4);
      this.F.a(-4.0F, 16.0F, -8.0F);
      this.a = new ModelRenderer(this, 0, 0);
      this.a.a(-2.5F, -10.0F, -1.5F, 5, 5, 7);
      this.a.a(0.0F, 4.0F, -10.0F);
      this.a(this.a, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.b = new ModelRenderer(this, 24, 18);
      this.b.a(-2.0F, -10.0F, -7.0F, 4, 3, 6);
      this.b.a(0.0F, 3.95F, -10.0F);
      this.a(this.b, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.c = new ModelRenderer(this, 24, 27);
      this.c.a(-2.0F, -7.0F, -6.5F, 4, 2, 5);
      this.c.a(0.0F, 4.0F, -10.0F);
      this.a(this.c, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.a.a(this.b);
      this.a.a(this.c);
      this.d = new ModelRenderer(this, 0, 0);
      this.d.a(0.45F, -12.0F, 4.0F, 2, 3, 1);
      this.d.a(0.0F, 4.0F, -10.0F);
      this.a(this.d, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.e = new ModelRenderer(this, 0, 0);
      this.e.a(-2.45F, -12.0F, 4.0F, 2, 3, 1);
      this.e.a(0.0F, 4.0F, -10.0F);
      this.a(this.e, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.f = new ModelRenderer(this, 0, 12);
      this.f.a(-2.0F, -16.0F, 4.0F, 2, 7, 1);
      this.f.a(0.0F, 4.0F, -10.0F);
      this.a(this.f, (float) (Math.PI / 6), 0.0F, (float) (Math.PI / 12));
      this.g = new ModelRenderer(this, 0, 12);
      this.g.a(0.0F, -16.0F, 4.0F, 2, 7, 1);
      this.g.a(0.0F, 4.0F, -10.0F);
      this.a(this.g, (float) (Math.PI / 6), 0.0F, (float) (-Math.PI / 12));
      this.h = new ModelRenderer(this, 0, 12);
      this.h.a(-2.05F, -9.8F, -2.0F, 4, 14, 8);
      this.h.a(0.0F, 4.0F, -10.0F);
      this.a(this.h, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.G = new ModelRenderer(this, 0, 34);
      this.G.a(-3.0F, 0.0F, 0.0F, 8, 8, 3);
      this.G.a(-7.5F, 3.0F, 10.0F);
      this.a(this.G, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.H = new ModelRenderer(this, 0, 47);
      this.H.a(-3.0F, 0.0F, 0.0F, 8, 8, 3);
      this.H.a(4.5F, 3.0F, 10.0F);
      this.a(this.H, 0.0F, (float) (Math.PI / 2), 0.0F);
      this.I = new ModelRenderer(this, 80, 0);
      this.I.a(-5.0F, 0.0F, -3.0F, 10, 1, 8);
      this.I.a(0.0F, 2.0F, 2.0F);
      this.J = new ModelRenderer(this, 106, 9);
      this.J.a(-1.5F, -1.0F, -3.0F, 3, 1, 2);
      this.J.a(0.0F, 2.0F, 2.0F);
      this.K = new ModelRenderer(this, 80, 9);
      this.K.a(-4.0F, -1.0F, 3.0F, 8, 1, 2);
      this.K.a(0.0F, 2.0F, 2.0F);
      this.M = new ModelRenderer(this, 74, 0);
      this.M.a(-0.5F, 6.0F, -1.0F, 1, 2, 2);
      this.M.a(5.0F, 3.0F, 2.0F);
      this.L = new ModelRenderer(this, 70, 0);
      this.L.a(-0.5F, 0.0F, -0.5F, 1, 6, 1);
      this.L.a(5.0F, 3.0F, 2.0F);
      this.O = new ModelRenderer(this, 74, 4);
      this.O.a(-0.5F, 6.0F, -1.0F, 1, 2, 2);
      this.O.a(-5.0F, 3.0F, 2.0F);
      this.N = new ModelRenderer(this, 80, 0);
      this.N.a(-0.5F, 0.0F, -0.5F, 1, 6, 1);
      this.N.a(-5.0F, 3.0F, 2.0F);
      this.P = new ModelRenderer(this, 74, 13);
      this.P.a(1.5F, -8.0F, -4.0F, 1, 2, 2);
      this.P.a(0.0F, 4.0F, -10.0F);
      this.a(this.P, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.Q = new ModelRenderer(this, 74, 13);
      this.Q.a(-2.5F, -8.0F, -4.0F, 1, 2, 2);
      this.Q.a(0.0F, 4.0F, -10.0F);
      this.a(this.Q, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.R = new ModelRenderer(this, 44, 10);
      this.R.a(2.6F, -6.0F, -6.0F, 0, 3, 16);
      this.R.a(0.0F, 4.0F, -10.0F);
      this.S = new ModelRenderer(this, 44, 5);
      this.S.a(-2.6F, -6.0F, -6.0F, 0, 3, 16);
      this.S.a(0.0F, 4.0F, -10.0F);
      this.j = new ModelRenderer(this, 58, 0);
      this.j.a(-1.0F, -11.5F, 5.0F, 2, 16, 4);
      this.j.a(0.0F, 4.0F, -10.0F);
      this.a(this.j, (float) (Math.PI / 6), 0.0F, 0.0F);
      this.i = new ModelRenderer(this, 80, 12);
      this.i.a(-2.5F, -10.1F, -7.0F, 5, 5, 12, 0.2F);
      this.i.a(0.0F, 4.0F, -10.0F);
      this.a(this.i, (float) (Math.PI / 6), 0.0F, 0.0F);
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      EntityHorse var8 = (EntityHorse)var1;
      int var9 = var8.cl();
      float var10 = var8.p(0.0F);
      boolean var11 = var8.cn();
      boolean var12 = var11 && var8.cG();
      boolean var13 = var11 && var8.cw();
      boolean var14 = var9 == 1 || var9 == 2;
      float var15 = var8.cu();
      boolean var16 = var8.l != null;
      if (var12) {
         this.i.a(var7);
         this.I.a(var7);
         this.J.a(var7);
         this.K.a(var7);
         this.L.a(var7);
         this.M.a(var7);
         this.N.a(var7);
         this.O.a(var7);
         this.P.a(var7);
         this.Q.a(var7);
         if (var16) {
            this.R.a(var7);
            this.S.a(var7);
         }
      }

      if (!var11) {
         GlStateManager.E();
         GlStateManager.a(var15, 0.5F + var15 * 0.5F, var15);
         GlStateManager.b(0.0F, 0.95F * (1.0F - var15), 0.0F);
      }

      this.o.a(var7);
      this.v.a(var7);
      this.w.a(var7);
      this.x.a(var7);
      this.y.a(var7);
      this.z.a(var7);
      this.A.a(var7);
      this.B.a(var7);
      this.C.a(var7);
      this.D.a(var7);
      this.E.a(var7);
      this.F.a(var7);
      if (!var11) {
         GlStateManager.F();
         GlStateManager.E();
         GlStateManager.a(var15, var15, var15);
         GlStateManager.b(0.0F, 1.35F * (1.0F - var15), 0.0F);
      }

      this.k.a(var7);
      this.l.a(var7);
      this.m.a(var7);
      this.n.a(var7);
      this.h.a(var7);
      this.j.a(var7);
      if (!var11) {
         GlStateManager.F();
         GlStateManager.E();
         float var17 = 0.5F + var15 * var15 * 0.5F;
         GlStateManager.a(var17, var17, var17);
         if (var10 <= 0.0F) {
            GlStateManager.b(0.0F, 1.35F * (1.0F - var15), 0.0F);
         } else {
            GlStateManager.b(0.0F, 0.9F * (1.0F - var15) * var10 + 1.35F * (1.0F - var15) * (1.0F - var10), 0.15F * (1.0F - var15) * var10);
         }
      }

      if (var14) {
         this.f.a(var7);
         this.g.a(var7);
      } else {
         this.d.a(var7);
         this.e.a(var7);
      }

      this.a.a(var7);
      if (!var11) {
         GlStateManager.F();
      }

      if (var13) {
         this.G.a(var7);
         this.H.a(var7);
      }
   }

   private void a(ModelRenderer var1, float var2, float var3, float var4) {
      var1.f = var2;
      var1.g = var3;
      var1.h = var4;
   }

   private float a(float var1, float var2, float var3) {
      float var4 = var2 - var1;

      while (var4 < -180.0F) {
         var4 += 360.0F;
      }

      while (var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return var1 + var3 * var4;
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
      super.a(var1, var2, var3, var4);
      float var5 = this.a(var1.aJ, var1.aI, var4);
      float var6 = this.a(var1.aL, var1.aK, var4);
      float var7 = var1.B + (var1.z - var1.B) * var4;
      float var8 = var6 - var5;
      float var9 = var7 / (180.0F / (float)Math.PI);
      if (var8 > 20.0F) {
         var8 = 20.0F;
      }

      if (var8 < -20.0F) {
         var8 = -20.0F;
      }

      if (var3 > 0.2F) {
         var9 += MathHelper.b(var2 * 0.4F) * 0.15F * var3;
      }

      EntityHorse var10 = (EntityHorse)var1;
      float var11 = var10.p(var4);
      float var12 = var10.q(var4);
      float var13 = 1.0F - var12;
      float var14 = var10.r(var4);
      boolean var15 = var10.bm != 0;
      boolean var16 = var10.cG();
      boolean var17 = var10.l != null;
      float var18 = var1.W + var4;
      float var19 = MathHelper.b(var2 * 0.6662F + (float) Math.PI);
      float var20 = var19 * 0.8F * var3;
      this.a.d = 4.0F;
      this.a.e = -10.0F;
      this.l.d = 3.0F;
      this.m.e = 14.0F;
      this.H.d = 3.0F;
      this.H.e = 10.0F;
      this.k.f = 0.0F;
      this.a.f = (float) (Math.PI / 6) + var9;
      this.a.g = var8 / (180.0F / (float)Math.PI);
      this.a.f = var12 * ((float) (Math.PI / 12) + var9) + var11 * 2.18166F + (1.0F - Math.max(var12, var11)) * this.a.f;
      this.a.g = var12 * var8 / (180.0F / (float)Math.PI) + (1.0F - Math.max(var12, var11)) * this.a.g;
      this.a.d = var12 * -6.0F + var11 * 11.0F + (1.0F - Math.max(var12, var11)) * this.a.d;
      this.a.e = var12 * -1.0F + var11 * -10.0F + (1.0F - Math.max(var12, var11)) * this.a.e;
      this.l.d = var12 * 9.0F + var13 * this.l.d;
      this.m.e = var12 * 18.0F + var13 * this.m.e;
      this.H.d = var12 * 5.5F + var13 * this.H.d;
      this.H.e = var12 * 15.0F + var13 * this.H.e;
      this.k.f = var12 * -45.0F / (180.0F / (float)Math.PI) + var13 * this.k.f;
      this.d.d = this.a.d;
      this.e.d = this.a.d;
      this.f.d = this.a.d;
      this.g.d = this.a.d;
      this.h.d = this.a.d;
      this.b.d = 0.02F;
      this.c.d = 0.0F;
      this.j.d = this.a.d;
      this.d.e = this.a.e;
      this.e.e = this.a.e;
      this.f.e = this.a.e;
      this.g.e = this.a.e;
      this.h.e = this.a.e;
      this.b.e = 0.02F - var14 * 1.0F;
      this.c.e = 0.0F + var14 * 1.0F;
      this.j.e = this.a.e;
      this.d.f = this.a.f;
      this.e.f = this.a.f;
      this.f.f = this.a.f;
      this.g.f = this.a.f;
      this.h.f = this.a.f;
      this.b.f = 0.0F - 0.09424778F * var14;
      this.c.f = 0.0F + (float) (Math.PI / 20) * var14;
      this.j.f = this.a.f;
      this.d.g = this.a.g;
      this.e.g = this.a.g;
      this.f.g = this.a.g;
      this.g.g = this.a.g;
      this.h.g = this.a.g;
      this.b.g = 0.0F;
      this.c.g = 0.0F;
      this.j.g = this.a.g;
      this.G.f = var20 / 5.0F;
      this.H.f = -var20 / 5.0F;
      float var21 = (float) (Math.PI / 2);
      float var22 = (float) (Math.PI * 3.0 / 2.0);
      float var23 = (float) (-Math.PI / 3);
      float var24 = (float) (Math.PI / 12) * var12;
      float var25 = MathHelper.b(var18 * 0.6F + (float) Math.PI);
      this.A.d = -2.0F * var12 + 9.0F * var13;
      this.A.e = -2.0F * var12 + -8.0F * var13;
      this.D.d = this.A.d;
      this.D.e = this.A.e;
      this.v.d = this.o.d + MathHelper.a((float) (Math.PI / 2) + var24 + var13 * -var19 * 0.5F * var3) * 7.0F;
      this.v.e = this.o.e + MathHelper.b((float) (Math.PI * 3.0 / 2.0) + var24 + var13 * -var19 * 0.5F * var3) * 7.0F;
      this.y.d = this.x.d + MathHelper.a((float) (Math.PI / 2) + var24 + var13 * var19 * 0.5F * var3) * 7.0F;
      this.y.e = this.x.e + MathHelper.b((float) (Math.PI * 3.0 / 2.0) + var24 + var13 * var19 * 0.5F * var3) * 7.0F;
      float var26 = ((float) (-Math.PI / 3) + var25) * var12 + var20 * var13;
      float var27 = ((float) (-Math.PI / 3) + -var25) * var12 + -var20 * var13;
      this.B.d = this.A.d + MathHelper.a((float) (Math.PI / 2) + var26) * 7.0F;
      this.B.e = this.A.e + MathHelper.b((float) (Math.PI * 3.0 / 2.0) + var26) * 7.0F;
      this.E.d = this.D.d + MathHelper.a((float) (Math.PI / 2) + var27) * 7.0F;
      this.E.e = this.D.e + MathHelper.b((float) (Math.PI * 3.0 / 2.0) + var27) * 7.0F;
      this.o.f = var24 + -var19 * 0.5F * var3 * var13;
      this.v.f = -0.08726646F * var12 + (-var19 * 0.5F * var3 - Math.max(0.0F, var19 * 0.5F * var3)) * var13;
      this.w.f = this.v.f;
      this.x.f = var24 + var19 * 0.5F * var3 * var13;
      this.y.f = -0.08726646F * var12 + (var19 * 0.5F * var3 - Math.max(0.0F, -var19 * 0.5F * var3)) * var13;
      this.z.f = this.y.f;
      this.A.f = var26;
      this.B.f = (this.A.f + (float) Math.PI * Math.max(0.0F, 0.2F + var25 * 0.2F)) * var12 + (var20 + Math.max(0.0F, var19 * 0.5F * var3)) * var13;
      this.C.f = this.B.f;
      this.D.f = var27;
      this.E.f = (this.D.f + (float) Math.PI * Math.max(0.0F, 0.2F - var25 * 0.2F)) * var12 + (-var20 + Math.max(0.0F, -var19 * 0.5F * var3)) * var13;
      this.F.f = this.E.f;
      this.w.d = this.v.d;
      this.w.e = this.v.e;
      this.z.d = this.y.d;
      this.z.e = this.y.e;
      this.C.d = this.B.d;
      this.C.e = this.B.e;
      this.F.d = this.E.d;
      this.F.e = this.E.e;
      if (var16) {
         this.I.d = var12 * 0.5F + var13 * 2.0F;
         this.I.e = var12 * 11.0F + var13 * 2.0F;
         this.J.d = this.I.d;
         this.K.d = this.I.d;
         this.L.d = this.I.d;
         this.N.d = this.I.d;
         this.M.d = this.I.d;
         this.O.d = this.I.d;
         this.G.d = this.H.d;
         this.J.e = this.I.e;
         this.K.e = this.I.e;
         this.L.e = this.I.e;
         this.N.e = this.I.e;
         this.M.e = this.I.e;
         this.O.e = this.I.e;
         this.G.e = this.H.e;
         this.I.f = this.k.f;
         this.J.f = this.k.f;
         this.K.f = this.k.f;
         this.R.d = this.a.d;
         this.S.d = this.a.d;
         this.i.d = this.a.d;
         this.P.d = this.a.d;
         this.Q.d = this.a.d;
         this.R.e = this.a.e;
         this.S.e = this.a.e;
         this.i.e = this.a.e;
         this.P.e = this.a.e;
         this.Q.e = this.a.e;
         this.R.f = var9;
         this.S.f = var9;
         this.i.f = this.a.f;
         this.P.f = this.a.f;
         this.Q.f = this.a.f;
         this.i.g = this.a.g;
         this.P.g = this.a.g;
         this.R.g = this.a.g;
         this.Q.g = this.a.g;
         this.S.g = this.a.g;
         if (var17) {
            this.L.f = (float) (-Math.PI / 3);
            this.M.f = (float) (-Math.PI / 3);
            this.N.f = (float) (-Math.PI / 3);
            this.O.f = (float) (-Math.PI / 3);
            this.L.h = 0.0F;
            this.M.h = 0.0F;
            this.N.h = 0.0F;
            this.O.h = 0.0F;
         } else {
            this.L.f = var20 / 3.0F;
            this.M.f = var20 / 3.0F;
            this.N.f = var20 / 3.0F;
            this.O.f = var20 / 3.0F;
            this.L.h = var20 / 5.0F;
            this.M.h = var20 / 5.0F;
            this.N.h = -var20 / 5.0F;
            this.O.h = -var20 / 5.0F;
         }
      }

      var21 = -1.3089F + var3 * 1.5F;
      if (var21 > 0.0F) {
         var21 = 0.0F;
      }

      if (var15) {
         this.l.g = MathHelper.b(var18 * 0.7F);
         var21 = 0.0F;
      } else {
         this.l.g = 0.0F;
      }

      this.m.g = this.l.g;
      this.n.g = this.l.g;
      this.m.d = this.l.d;
      this.n.d = this.l.d;
      this.m.e = this.l.e;
      this.n.e = this.l.e;
      this.l.f = var21;
      this.m.f = var21;
      this.n.f = -0.2618F + var21;
   }
}
