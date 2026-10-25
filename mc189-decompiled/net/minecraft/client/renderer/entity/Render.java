package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public abstract class Render<T extends Entity> {
   private static final ResourceLocation a = new ResourceLocation("textures/misc/shadow.png");
   protected final RenderManager b;
   protected float c;
   protected float d = 1.0F;

   protected Render(RenderManager var1) {
      this.b = var1;
   }

   public boolean a(T var1, ICamera var2, double var3, double var5, double var7) {
      AxisAlignedBB var9 = var1.aR();
      if (var9.b() || var9.a() == 0.0) {
         var9 = new AxisAlignedBB(var1.s - 2.0, var1.t - 2.0, var1.u - 2.0, var1.s + 2.0, var1.t + 2.0, var1.u + 2.0);
      }

      return var1.h(var3, var5, var7) && (var1.ah || var2.a(var9));
   }

   public void a(T var1, double var2, double var4, double var6, float var8, float var9) {
      this.a((T)var1, var2, var4, var6);
   }

   protected void a(T var1, double var2, double var4, double var6) {
      if (this.b(var1)) {
         this.a((T)var1, var1.f_().d(), var2, var4, var6, 64);
      }
   }

   protected boolean b(T var1) {
      return var1.aO() && var1.l_();
   }

   protected void a(T var1, double var2, double var4, double var6, String var8, float var9, double var10) {
      this.a((T)var1, var8, var2, var4, var6, 64);
   }

   protected abstract ResourceLocation a(T var1);

   protected boolean c(T var1) {
      ResourceLocation var2 = this.a((T)var1);
      if (var2 == null) {
         return false;
      } else {
         this.a(var2);
         return true;
      }
   }

   public void a(ResourceLocation var1) {
      this.b.a.a(var1);
   }

   private void a(Entity var1, double var2, double var4, double var6, float var8) {
      GlStateManager.f();
      TextureMap var9 = Minecraft.A().T();
      TextureAtlasSprite var10 = var9.a("minecraft:blocks/fire_layer_0");
      TextureAtlasSprite var11 = var9.a("minecraft:blocks/fire_layer_1");
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      float var12 = var1.J * 1.4F;
      GlStateManager.a(var12, var12, var12);
      Tessellator var13 = Tessellator.a();
      WorldRenderer var14 = var13.c();
      float var15 = 0.5F;
      float var16 = 0.0F;
      float var17 = var1.K / var12;
      float var18 = (float)(var1.t - var1.aR().b);
      GlStateManager.b(-this.b.e, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(0.0F, 0.0F, -0.3F + (int)var17 * 0.02F);
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      float var19 = 0.0F;
      int var20 = 0;
      var14.a(7, DefaultVertexFormats.g);

      while (var17 > 0.0F) {
         TextureAtlasSprite var21 = var20 % 2 == 0 ? var10 : var11;
         this.a(TextureMap.g);
         float var22 = var21.e();
         float var23 = var21.g();
         float var24 = var21.f();
         float var25 = var21.h();
         if (var20 / 2 % 2 == 0) {
            float var26 = var24;
            var24 = var22;
            var22 = var26;
         }

         var14.b((double)(var15 - var16), (double)(0.0F - var18), (double)var19).a((double)var24, (double)var25).d();
         var14.b((double)(-var15 - var16), (double)(0.0F - var18), (double)var19).a((double)var22, (double)var25).d();
         var14.b((double)(-var15 - var16), (double)(1.4F - var18), (double)var19).a((double)var22, (double)var23).d();
         var14.b((double)(var15 - var16), (double)(1.4F - var18), (double)var19).a((double)var24, (double)var23).d();
         var17 -= 0.45F;
         var18 -= 0.45F;
         var15 *= 0.9F;
         var19 += 0.03F;
         var20++;
      }

      var13.b();
      GlStateManager.F();
      GlStateManager.e();
   }

   private void c(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.l();
      GlStateManager.b(770, 771);
      this.b.a.a(a);
      World var10 = this.a();
      GlStateManager.a(false);
      float var11 = this.c;
      if (var1 instanceof EntityLiving) {
         EntityLiving var12 = (EntityLiving)var1;
         var11 *= var12.bT();
         if (var12.j_()) {
            var11 *= 0.5F;
         }
      }

      double var35 = var1.P + (var1.s - var1.P) * var9;
      double var14 = var1.Q + (var1.t - var1.Q) * var9;
      double var16 = var1.R + (var1.u - var1.R) * var9;
      int var18 = MathHelper.c(var35 - var11);
      int var19 = MathHelper.c(var35 + var11);
      int var20 = MathHelper.c(var14 - var11);
      int var21 = MathHelper.c(var14);
      int var22 = MathHelper.c(var16 - var11);
      int var23 = MathHelper.c(var16 + var11);
      double var24 = var2 - var35;
      double var26 = var4 - var14;
      double var28 = var6 - var16;
      Tessellator var30 = Tessellator.a();
      WorldRenderer var31 = var30.c();
      var31.a(7, DefaultVertexFormats.i);

      for (BlockPos var33 : BlockPos.b(new BlockPos(var18, var20, var22), new BlockPos(var19, var21, var23))) {
         Block var34 = var10.p(var33.b()).c();
         if (var34.b() != -1 && var10.l(var33) > 3) {
            this.a(var34, var2, var4, var6, var33, var8, var11, var24, var26, var28);
         }
      }

      var30.b();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.k();
      GlStateManager.a(true);
   }

   private World a() {
      return this.b.b;
   }

   private void a(Block var1, double var2, double var4, double var6, BlockPos var8, float var9, float var10, double var11, double var13, double var15) {
      if (var1.d()) {
         Tessellator var17 = Tessellator.a();
         WorldRenderer var18 = var17.c();
         double var19 = (var9 - (var4 - (var8.o() + var13)) / 2.0) * 0.5 * this.a().o(var8);
         if (!(var19 < 0.0)) {
            if (var19 > 1.0) {
               var19 = 1.0;
            }

            double var21 = var8.n() + var1.B() + var11;
            double var23 = var8.n() + var1.C() + var11;
            double var25 = var8.o() + var1.D() + var13 + 0.015625;
            double var27 = var8.p() + var1.F() + var15;
            double var29 = var8.p() + var1.G() + var15;
            float var31 = (float)((var2 - var21) / 2.0 / var10 + 0.5);
            float var32 = (float)((var2 - var23) / 2.0 / var10 + 0.5);
            float var33 = (float)((var6 - var27) / 2.0 / var10 + 0.5);
            float var34 = (float)((var6 - var29) / 2.0 / var10 + 0.5);
            var18.b(var21, var25, var27).a((double)var31, (double)var33).a(1.0F, 1.0F, 1.0F, (float)var19).d();
            var18.b(var21, var25, var29).a((double)var31, (double)var34).a(1.0F, 1.0F, 1.0F, (float)var19).d();
            var18.b(var23, var25, var29).a((double)var32, (double)var34).a(1.0F, 1.0F, 1.0F, (float)var19).d();
            var18.b(var23, var25, var27).a((double)var32, (double)var33).a(1.0F, 1.0F, 1.0F, (float)var19).d();
         }
      }
   }

   public static void a(AxisAlignedBB var0, double var1, double var3, double var5) {
      GlStateManager.x();
      Tessellator var7 = Tessellator.a();
      WorldRenderer var8 = var7.c();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      var8.c(var1, var3, var5);
      var8.a(7, DefaultVertexFormats.h);
      var8.b(var0.a, var0.e, var0.c).c(0.0F, 0.0F, -1.0F).d();
      var8.b(var0.d, var0.e, var0.c).c(0.0F, 0.0F, -1.0F).d();
      var8.b(var0.d, var0.b, var0.c).c(0.0F, 0.0F, -1.0F).d();
      var8.b(var0.a, var0.b, var0.c).c(0.0F, 0.0F, -1.0F).d();
      var8.b(var0.a, var0.b, var0.f).c(0.0F, 0.0F, 1.0F).d();
      var8.b(var0.d, var0.b, var0.f).c(0.0F, 0.0F, 1.0F).d();
      var8.b(var0.d, var0.e, var0.f).c(0.0F, 0.0F, 1.0F).d();
      var8.b(var0.a, var0.e, var0.f).c(0.0F, 0.0F, 1.0F).d();
      var8.b(var0.a, var0.b, var0.c).c(0.0F, -1.0F, 0.0F).d();
      var8.b(var0.d, var0.b, var0.c).c(0.0F, -1.0F, 0.0F).d();
      var8.b(var0.d, var0.b, var0.f).c(0.0F, -1.0F, 0.0F).d();
      var8.b(var0.a, var0.b, var0.f).c(0.0F, -1.0F, 0.0F).d();
      var8.b(var0.a, var0.e, var0.f).c(0.0F, 1.0F, 0.0F).d();
      var8.b(var0.d, var0.e, var0.f).c(0.0F, 1.0F, 0.0F).d();
      var8.b(var0.d, var0.e, var0.c).c(0.0F, 1.0F, 0.0F).d();
      var8.b(var0.a, var0.e, var0.c).c(0.0F, 1.0F, 0.0F).d();
      var8.b(var0.a, var0.b, var0.f).c(-1.0F, 0.0F, 0.0F).d();
      var8.b(var0.a, var0.e, var0.f).c(-1.0F, 0.0F, 0.0F).d();
      var8.b(var0.a, var0.e, var0.c).c(-1.0F, 0.0F, 0.0F).d();
      var8.b(var0.a, var0.b, var0.c).c(-1.0F, 0.0F, 0.0F).d();
      var8.b(var0.d, var0.b, var0.c).c(1.0F, 0.0F, 0.0F).d();
      var8.b(var0.d, var0.e, var0.c).c(1.0F, 0.0F, 0.0F).d();
      var8.b(var0.d, var0.e, var0.f).c(1.0F, 0.0F, 0.0F).d();
      var8.b(var0.d, var0.b, var0.f).c(1.0F, 0.0F, 0.0F).d();
      var7.b();
      var8.c(0.0, 0.0, 0.0);
      GlStateManager.w();
   }

   public void b(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      if (this.b.g != null) {
         if (this.b.g.W && this.c > 0.0F && !var1.ax() && this.b.a()) {
            double var10 = this.b.b(var1.s, var1.t, var1.u);
            float var12 = (float)((1.0 - var10 / 256.0) * this.d);
            if (var12 > 0.0F) {
               this.c(var1, var2, var4, var6, var12, var9);
            }
         }

         if (var1.aJ() && (!(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).v())) {
            this.a(var1, var2, var4, var6, var9);
         }
      }
   }

   public FontRenderer c() {
      return this.b.c();
   }

   protected void a(T var1, String var2, double var3, double var5, double var7, int var9) {
      double var10 = var1.h(this.b.c);
      if (!(var10 > var9 * var9)) {
         FontRenderer var12 = this.c();
         float var13 = 1.6F;
         float var14 = 0.016666668F * var13;
         GlStateManager.E();
         GlStateManager.b((float)var3 + 0.0F, (float)var5 + var1.K + 0.5F, (float)var7);
         GL11.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.b(-this.b.e, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(this.b.f, 1.0F, 0.0F, 0.0F);
         GlStateManager.a(-var14, -var14, var14);
         GlStateManager.f();
         GlStateManager.a(false);
         GlStateManager.i();
         GlStateManager.l();
         GlStateManager.a(770, 771, 1, 0);
         Tessellator var15 = Tessellator.a();
         WorldRenderer var16 = var15.c();
         byte var17 = 0;
         if (var2.equals("deadmau5")) {
            var17 = -10;
         }

         int var18 = var12.a(var2) / 2;
         GlStateManager.x();
         var16.a(7, DefaultVertexFormats.f);
         var16.b((double)(-var18 - 1), (double)(-1 + var17), 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
         var16.b((double)(-var18 - 1), (double)(8 + var17), 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
         var16.b((double)(var18 + 1), (double)(8 + var17), 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
         var16.b((double)(var18 + 1), (double)(-1 + var17), 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
         var15.b();
         GlStateManager.w();
         var12.a(var2, -var12.a(var2) / 2, var17, 553648127);
         GlStateManager.j();
         GlStateManager.a(true);
         var12.a(var2, -var12.a(var2) / 2, var17, -1);
         GlStateManager.e();
         GlStateManager.k();
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.F();
      }
   }

   public RenderManager d() {
      return this.b;
   }
}
