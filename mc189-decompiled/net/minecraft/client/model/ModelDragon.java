package net.minecraft.client.model;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.boss.EntityDragon;

public class ModelDragon extends ModelBase {
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
   private float m;

   public ModelDragon(float var1) {
      this.t = 256;
      this.u = 256;
      this.a("body.body", 0, 0);
      this.a("wing.skin", -56, 88);
      this.a("wingtip.skin", -56, 144);
      this.a("rearleg.main", 0, 0);
      this.a("rearfoot.main", 112, 0);
      this.a("rearlegtip.main", 196, 0);
      this.a("head.upperhead", 112, 30);
      this.a("wing.bone", 112, 88);
      this.a("head.upperlip", 176, 44);
      this.a("jaw.jaw", 176, 65);
      this.a("frontleg.main", 112, 104);
      this.a("wingtip.bone", 112, 136);
      this.a("frontfoot.main", 144, 104);
      this.a("neck.box", 192, 104);
      this.a("frontlegtip.main", 226, 138);
      this.a("body.scale", 220, 53);
      this.a("head.scale", 0, 0);
      this.a("neck.scale", 48, 0);
      this.a("head.nostril", 112, 0);
      float var2 = -16.0F;
      this.a = new ModelRenderer(this, "head");
      this.a.a("upperlip", -6.0F, -1.0F, -8.0F + var2, 12, 5, 16);
      this.a.a("upperhead", -8.0F, -8.0F, 6.0F + var2, 16, 16, 16);
      this.a.i = true;
      this.a.a("scale", -5.0F, -12.0F, 12.0F + var2, 2, 4, 6);
      this.a.a("nostril", -5.0F, -3.0F, -6.0F + var2, 2, 2, 4);
      this.a.i = false;
      this.a.a("scale", 3.0F, -12.0F, 12.0F + var2, 2, 4, 6);
      this.a.a("nostril", 3.0F, -3.0F, -6.0F + var2, 2, 2, 4);
      this.c = new ModelRenderer(this, "jaw");
      this.c.a(0.0F, 4.0F, 8.0F + var2);
      this.c.a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16);
      this.a.a(this.c);
      this.b = new ModelRenderer(this, "neck");
      this.b.a("box", -5.0F, -5.0F, -5.0F, 10, 10, 10);
      this.b.a("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6);
      this.d = new ModelRenderer(this, "body");
      this.d.a(0.0F, 4.0F, 8.0F);
      this.d.a("body", -12.0F, 0.0F, -16.0F, 24, 24, 64);
      this.d.a("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12);
      this.d.a("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12);
      this.d.a("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12);
      this.k = new ModelRenderer(this, "wing");
      this.k.a(-12.0F, 5.0F, 2.0F);
      this.k.a("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8);
      this.k.a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.l = new ModelRenderer(this, "wingtip");
      this.l.a(-56.0F, 0.0F, 0.0F);
      this.l.a("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4);
      this.l.a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56);
      this.k.a(this.l);
      this.f = new ModelRenderer(this, "frontleg");
      this.f.a(-12.0F, 20.0F, 2.0F);
      this.f.a("main", -4.0F, -4.0F, -4.0F, 8, 24, 8);
      this.h = new ModelRenderer(this, "frontlegtip");
      this.h.a(0.0F, 20.0F, -1.0F);
      this.h.a("main", -3.0F, -1.0F, -3.0F, 6, 24, 6);
      this.f.a(this.h);
      this.j = new ModelRenderer(this, "frontfoot");
      this.j.a(0.0F, 23.0F, 0.0F);
      this.j.a("main", -4.0F, 0.0F, -12.0F, 8, 4, 16);
      this.h.a(this.j);
      this.e = new ModelRenderer(this, "rearleg");
      this.e.a(-16.0F, 16.0F, 42.0F);
      this.e.a("main", -8.0F, -4.0F, -8.0F, 16, 32, 16);
      this.g = new ModelRenderer(this, "rearlegtip");
      this.g.a(0.0F, 32.0F, -4.0F);
      this.g.a("main", -6.0F, -2.0F, 0.0F, 12, 32, 12);
      this.e.a(this.g);
      this.i = new ModelRenderer(this, "rearfoot");
      this.i.a(0.0F, 31.0F, 4.0F);
      this.i.a("main", -9.0F, 0.0F, -20.0F, 18, 6, 24);
      this.g.a(this.i);
   }

   @Override
   public void a(EntityLivingBase var1, float var2, float var3, float var4) {
      this.m = var4;
   }

   @Override
   public void a(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      GlStateManager.E();
      EntityDragon var8 = (EntityDragon)var1;
      float var9 = var8.bu + (var8.bv - var8.bu) * this.m;
      this.c.f = (float)(Math.sin(var9 * (float) Math.PI * 2.0F) + 1.0) * 0.2F;
      float var10 = (float)(Math.sin(var9 * (float) Math.PI * 2.0F - 1.0F) + 1.0);
      var10 = (var10 * var10 * 1.0F + var10 * 2.0F) * 0.05F;
      GlStateManager.b(0.0F, var10 - 2.0F, -3.0F);
      GlStateManager.b(var10 * 2.0F, 1.0F, 0.0F, 0.0F);
      float var11 = -30.0F;
      float var13 = 0.0F;
      float var14 = 1.5F;
      double[] var15 = var8.b(6, this.m);
      float var16 = this.a(var8.b(5, this.m)[0] - var8.b(10, this.m)[0]);
      float var17 = this.a(var8.b(5, this.m)[0] + var16 / 2.0F);
      var11 += 2.0F;
      float var18 = var9 * (float) Math.PI * 2.0F;
      var11 = 20.0F;
      float var12 = -12.0F;

      for (int var19 = 0; var19 < 5; var19++) {
         double[] var20 = var8.b(5 - var19, this.m);
         float var21 = (float)Math.cos(var19 * 0.45F + var18) * 0.15F;
         this.b.g = this.a(var20[0] - var15[0]) * (float) Math.PI / 180.0F * var14;
         this.b.f = var21 + (float)(var20[1] - var15[1]) * (float) Math.PI / 180.0F * var14 * 5.0F;
         this.b.h = -this.a(var20[0] - var17) * (float) Math.PI / 180.0F * var14;
         this.b.d = var11;
         this.b.e = var12;
         this.b.c = var13;
         var11 = (float)(var11 + Math.sin(this.b.f) * 10.0);
         var12 = (float)(var12 - Math.cos(this.b.g) * Math.cos(this.b.f) * 10.0);
         var13 = (float)(var13 - Math.sin(this.b.g) * Math.cos(this.b.f) * 10.0);
         this.b.a(var7);
      }

      this.a.d = var11;
      this.a.e = var12;
      this.a.c = var13;
      double[] var30 = var8.b(0, this.m);
      this.a.g = this.a(var30[0] - var15[0]) * (float) Math.PI / 180.0F * 1.0F;
      this.a.h = -this.a(var30[0] - var17) * (float) Math.PI / 180.0F * 1.0F;
      this.a.a(var7);
      GlStateManager.E();
      GlStateManager.b(0.0F, 1.0F, 0.0F);
      GlStateManager.b(-var16 * var14 * 1.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(0.0F, -1.0F, 0.0F);
      this.d.h = 0.0F;
      this.d.a(var7);

      for (int var32 = 0; var32 < 2; var32++) {
         GlStateManager.o();
         float var34 = var9 * (float) Math.PI * 2.0F;
         this.k.f = 0.125F - (float)Math.cos(var34) * 0.2F;
         this.k.g = 0.25F;
         this.k.h = (float)(Math.sin(var34) + 0.125) * 0.8F;
         this.l.h = -((float)(Math.sin(var34 + 2.0F) + 0.5)) * 0.75F;
         this.e.f = 1.0F + var10 * 0.1F;
         this.g.f = 0.5F + var10 * 0.1F;
         this.i.f = 0.75F + var10 * 0.1F;
         this.f.f = 1.3F + var10 * 0.1F;
         this.h.f = -0.5F - var10 * 0.1F;
         this.j.f = 0.75F + var10 * 0.1F;
         this.k.a(var7);
         this.f.a(var7);
         this.e.a(var7);
         GlStateManager.a(-1.0F, 1.0F, 1.0F);
         if (var32 == 0) {
            GlStateManager.e(1028);
         }
      }

      GlStateManager.F();
      GlStateManager.e(1029);
      GlStateManager.p();
      float var33 = -((float)Math.sin(var9 * (float) Math.PI * 2.0F)) * 0.0F;
      var18 = var9 * (float) Math.PI * 2.0F;
      var11 = 10.0F;
      var12 = 60.0F;
      var13 = 0.0F;
      var15 = var8.b(11, this.m);

      for (int var35 = 0; var35 < 12; var35++) {
         var30 = var8.b(12 + var35, this.m);
         var33 = (float)(var33 + Math.sin(var35 * 0.45F + var18) * 0.05F);
         this.b.g = (this.a(var30[0] - var15[0]) * var14 + 180.0F) * (float) Math.PI / 180.0F;
         this.b.f = var33 + (float)(var30[1] - var15[1]) * (float) Math.PI / 180.0F * var14 * 5.0F;
         this.b.h = this.a(var30[0] - var17) * (float) Math.PI / 180.0F * var14;
         this.b.d = var11;
         this.b.e = var12;
         this.b.c = var13;
         var11 = (float)(var11 + Math.sin(this.b.f) * 10.0);
         var12 = (float)(var12 - Math.cos(this.b.g) * Math.cos(this.b.f) * 10.0);
         var13 = (float)(var13 - Math.sin(this.b.g) * Math.cos(this.b.f) * 10.0);
         this.b.a(var7);
      }

      GlStateManager.F();
   }

   private float a(double var1) {
      while (var1 >= 180.0) {
         var1 -= 360.0;
      }

      while (var1 < -180.0) {
         var1 += 360.0;
      }

      return (float)var1;
   }
}
