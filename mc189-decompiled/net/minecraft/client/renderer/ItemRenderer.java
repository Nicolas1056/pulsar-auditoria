package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

public class ItemRenderer {
   private static final ResourceLocation a = new ResourceLocation("textures/map/map_background.png");
   private static final ResourceLocation b = new ResourceLocation("textures/misc/underwater.png");
   private final Minecraft c;
   private ItemStack d;
   private float e;
   private float f;
   private final RenderManager g;
   private final RenderItem h;
   private int i = -1;

   public ItemRenderer(Minecraft var1) {
      this.c = var1;
      this.g = var1.af();
      this.h = var1.ag();
   }

   public void a(EntityLivingBase var1, ItemStack var2, ItemCameraTransforms.TransformType var3) {
      if (var2 != null) {
         Item var4 = var2.b();
         Block var5 = Block.a(var4);
         GlStateManager.E();
         if (this.h.a(var2)) {
            GlStateManager.a(2.0F, 2.0F, 2.0F);
            if (this.a(var5)) {
               GlStateManager.a(false);
            }
         }

         this.h.a(var2, var1, var3);
         if (this.a(var5)) {
            GlStateManager.a(true);
         }

         GlStateManager.F();
      }
   }

   private boolean a(Block var1) {
      return var1 != null && var1.m() == EnumWorldBlockLayer.d;
   }

   private void a(float var1, float var2) {
      GlStateManager.E();
      GlStateManager.b(var1, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(var2, 0.0F, 1.0F, 0.0F);
      RenderHelper.b();
      GlStateManager.F();
   }

   private void a(AbstractClientPlayer var1) {
      int var2 = this.c.f.b(new BlockPos(var1.s, var1.t + var1.aS(), var1.u), 0);
      float var3 = var2 & 65535;
      float var4 = var2 >> 16;
      OpenGlHelper.a(OpenGlHelper.r, var3, var4);
   }

   private void a(EntityPlayerSP var1, float var2) {
      float var3 = var1.i + (var1.g - var1.i) * var2;
      float var4 = var1.h + (var1.f - var1.h) * var2;
      GlStateManager.b((var1.z - var3) * 0.1F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b((var1.y - var4) * 0.1F, 0.0F, 1.0F, 0.0F);
   }

   private float c(float var1) {
      float var2 = 1.0F - var1 / 45.0F + 0.1F;
      var2 = MathHelper.a(var2, 0.0F, 1.0F);
      return -MathHelper.b(var2 * (float) Math.PI) * 0.5F + 0.5F;
   }

   private void a(RenderPlayer var1) {
      GlStateManager.E();
      GlStateManager.b(54.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(64.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(-62.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(0.25F, -0.85F, 0.75F);
      var1.b(this.c.h);
      GlStateManager.F();
   }

   private void b(RenderPlayer var1) {
      GlStateManager.E();
      GlStateManager.b(92.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(45.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(41.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(-0.3F, -1.1F, 0.45F);
      var1.c(this.c.h);
      GlStateManager.F();
   }

   private void b(AbstractClientPlayer var1) {
      this.c.P().a(var1.i());
      Render var2 = this.g.a(this.c.h);
      RenderPlayer var3 = (RenderPlayer)var2;
      if (!var1.ax()) {
         GlStateManager.p();
         this.a(var3);
         this.b(var3);
         GlStateManager.o();
      }
   }

   private void a(AbstractClientPlayer var1, float var2, float var3, float var4) {
      float var5 = -0.4F * MathHelper.a(MathHelper.c(var4) * (float) Math.PI);
      float var6 = 0.2F * MathHelper.a(MathHelper.c(var4) * (float) Math.PI * 2.0F);
      float var7 = -0.2F * MathHelper.a(var4 * (float) Math.PI);
      GlStateManager.b(var5, var6, var7);
      float var8 = this.c(var2);
      GlStateManager.b(0.0F, 0.04F, -0.72F);
      GlStateManager.b(0.0F, var3 * -1.2F, 0.0F);
      GlStateManager.b(0.0F, var8 * -0.5F, 0.0F);
      GlStateManager.b(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(var8 * -85.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(0.0F, 1.0F, 0.0F, 0.0F);
      this.b(var1);
      float var9 = MathHelper.a(var4 * var4 * (float) Math.PI);
      float var10 = MathHelper.a(MathHelper.c(var4) * (float) Math.PI);
      GlStateManager.b(var9 * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(var10 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(var10 * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.a(0.38F, 0.38F, 0.38F);
      GlStateManager.b(90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(0.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(-1.0F, -1.0F, 0.0F);
      GlStateManager.a(0.015625F, 0.015625F, 0.015625F);
      this.c.P().a(a);
      Tessellator var11 = Tessellator.a();
      WorldRenderer var12 = var11.c();
      GL11.glNormal3f(0.0F, 0.0F, -1.0F);
      var12.a(7, DefaultVertexFormats.g);
      var12.b(-7.0, 135.0, 0.0).a(0.0, 1.0).d();
      var12.b(135.0, 135.0, 0.0).a(1.0, 1.0).d();
      var12.b(135.0, -7.0, 0.0).a(1.0, 0.0).d();
      var12.b(-7.0, -7.0, 0.0).a(0.0, 0.0).d();
      var11.b();
      MapData var13 = Items.bd.a(this.d, this.c.f);
      if (var13 != null) {
         this.c.o.k().a(var13, false);
      }
   }

   private void a(AbstractClientPlayer var1, float var2, float var3) {
      float var4 = -0.3F * MathHelper.a(MathHelper.c(var3) * (float) Math.PI);
      float var5 = 0.4F * MathHelper.a(MathHelper.c(var3) * (float) Math.PI * 2.0F);
      float var6 = -0.4F * MathHelper.a(var3 * (float) Math.PI);
      GlStateManager.b(var4, var5, var6);
      GlStateManager.b(0.64000005F, -0.6F, -0.71999997F);
      GlStateManager.b(0.0F, var2 * -0.6F, 0.0F);
      GlStateManager.b(45.0F, 0.0F, 1.0F, 0.0F);
      float var7 = MathHelper.a(var3 * var3 * (float) Math.PI);
      float var8 = MathHelper.a(MathHelper.c(var3) * (float) Math.PI);
      GlStateManager.b(var8 * 70.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(var7 * -20.0F, 0.0F, 0.0F, 1.0F);
      this.c.P().a(var1.i());
      GlStateManager.b(-1.0F, 3.6F, 3.5F);
      GlStateManager.b(120.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(200.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.a(1.0F, 1.0F, 1.0F);
      GlStateManager.b(5.6F, 0.0F, 0.0F);
      Render var9 = this.g.a(this.c.h);
      GlStateManager.p();
      RenderPlayer var10 = (RenderPlayer)var9;
      var10.b(this.c.h);
      GlStateManager.o();
   }

   private void d(float var1) {
      float var2 = -0.4F * MathHelper.a(MathHelper.c(var1) * (float) Math.PI);
      float var3 = 0.2F * MathHelper.a(MathHelper.c(var1) * (float) Math.PI * 2.0F);
      float var4 = -0.2F * MathHelper.a(var1 * (float) Math.PI);
      GlStateManager.b(var2, var3, var4);
   }

   private void a(AbstractClientPlayer var1, float var2) {
      float var3 = var1.bR() - var2 + 1.0F;
      float var4 = var3 / this.d.l();
      float var5 = MathHelper.e(MathHelper.b(var3 / 4.0F * (float) Math.PI) * 0.1F);
      if (var4 >= 0.8F) {
         var5 = 0.0F;
      }

      GlStateManager.b(0.0F, var5, 0.0F);
      float var6 = 1.0F - (float)Math.pow(var4, 27.0);
      GlStateManager.b(var6 * 0.6F, var6 * -0.5F, var6 * 0.0F);
      GlStateManager.b(var6 * 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(var6 * 30.0F, 0.0F, 0.0F, 1.0F);
   }

   private void b(float var1, float var2) {
      GlStateManager.b(0.56F, -0.52F, -0.71999997F);
      GlStateManager.b(0.0F, var1 * -0.6F, 0.0F);
      GlStateManager.b(45.0F, 0.0F, 1.0F, 0.0F);
      float var3 = MathHelper.a(var2 * var2 * (float) Math.PI);
      float var4 = MathHelper.a(MathHelper.c(var2) * (float) Math.PI);
      GlStateManager.b(var3 * -20.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(var4 * -20.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(var4 * -80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.a(0.4F, 0.4F, 0.4F);
   }

   private void a(float var1, AbstractClientPlayer var2) {
      GlStateManager.b(-18.0F, 0.0F, 0.0F, 1.0F);
      GlStateManager.b(-12.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(-8.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(-0.9F, 0.2F, 0.0F);
      float var3 = this.d.l() - (var2.bR() - var1 + 1.0F);
      float var4 = var3 / 20.0F;
      var4 = (var4 * var4 + var4 * 2.0F) / 3.0F;
      if (var4 > 1.0F) {
         var4 = 1.0F;
      }

      if (var4 > 0.1F) {
         float var5 = MathHelper.a((var3 - 0.1F) * 1.3F);
         float var6 = var4 - 0.1F;
         float var7 = var5 * var6;
         GlStateManager.b(var7 * 0.0F, var7 * 0.01F, var7 * 0.0F);
      }

      GlStateManager.b(var4 * 0.0F, var4 * 0.0F, var4 * 0.1F);
      GlStateManager.a(1.0F, 1.0F, 1.0F + var4 * 0.2F);
   }

   private void d() {
      GlStateManager.b(-0.5F, 0.2F, 0.0F);
      GlStateManager.b(30.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(-80.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(60.0F, 0.0F, 1.0F, 0.0F);
   }

   public void a(float var1) {
      float var2 = 1.0F - (this.f + (this.e - this.f) * var1);
      EntityPlayerSP var3 = this.c.h;
      float var4 = var3.l(var1);
      float var5 = var3.B + (var3.z - var3.B) * var1;
      float var6 = var3.A + (var3.y - var3.A) * var1;
      this.a(var5, var6);
      this.a(var3);
      this.a(var3, var1);
      GlStateManager.B();
      GlStateManager.E();
      if (this.d != null) {
         if (this.d.b() == Items.bd) {
            this.a(var3, var5, var2, var4);
         } else if (var3.bR() > 0) {
            EnumAction var7 = this.d.m();
            switch (var7) {
               case a:
                  this.b(var2, 0.0F);
                  break;
               case b:
               case c:
                  this.a((AbstractClientPlayer)var3, var1);
                  this.b(var2, 0.0F);
                  break;
               case d:
                  this.b(var2, 0.0F);
                  this.d();
                  break;
               case e:
                  this.b(var2, 0.0F);
                  this.a(var1, var3);
            }
         } else {
            this.d(var4);
            this.b(var2, var4);
         }

         this.a(var3, this.d, ItemCameraTransforms.TransformType.c);
      } else if (!var3.ax()) {
         this.a(var3, var2, var4);
      }

      GlStateManager.F();
      GlStateManager.C();
      RenderHelper.a();
   }

   public void b(float var1) {
      GlStateManager.c();
      if (this.c.h.aj()) {
         IBlockState var2 = this.c.f.p(new BlockPos(this.c.h));
         EntityPlayerSP var3 = this.c.h;

         for (int var4 = 0; var4 < 8; var4++) {
            double var5 = var3.s + ((var4 >> 0) % 2 - 0.5F) * var3.J * 0.8F;
            double var7 = var3.t + ((var4 >> 1) % 2 - 0.5F) * 0.1F;
            double var9 = var3.u + ((var4 >> 2) % 2 - 0.5F) * var3.J * 0.8F;
            BlockPos var11 = new BlockPos(var5, var7 + var3.aS(), var9);
            IBlockState var12 = this.c.f.p(var11);
            if (var12.c().w()) {
               var2 = var12;
            }
         }

         if (var2.c().b() != -1) {
            this.a(var1, this.c.ae().a().a(var2));
         }
      }

      if (!this.c.h.v()) {
         if (this.c.h.a(Material.h)) {
            this.e(var1);
         }

         if (this.c.h.at()) {
            this.f(var1);
         }
      }

      GlStateManager.d();
   }

   private void a(float var1, TextureAtlasSprite var2) {
      this.c.P().a(TextureMap.g);
      Tessellator var3 = Tessellator.a();
      WorldRenderer var4 = var3.c();
      float var5 = 0.1F;
      GlStateManager.c(0.1F, 0.1F, 0.1F, 0.5F);
      GlStateManager.E();
      float var6 = -1.0F;
      float var7 = 1.0F;
      float var8 = -1.0F;
      float var9 = 1.0F;
      float var10 = -0.5F;
      float var11 = var2.e();
      float var12 = var2.f();
      float var13 = var2.g();
      float var14 = var2.h();
      var4.a(7, DefaultVertexFormats.g);
      var4.b(-1.0, -1.0, -0.5).a((double)var12, (double)var14).d();
      var4.b(1.0, -1.0, -0.5).a((double)var11, (double)var14).d();
      var4.b(1.0, 1.0, -0.5).a((double)var11, (double)var13).d();
      var4.b(-1.0, 1.0, -0.5).a((double)var12, (double)var13).d();
      var3.b();
      GlStateManager.F();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private void e(float var1) {
      this.c.P().a(b);
      Tessellator var2 = Tessellator.a();
      WorldRenderer var3 = var2.c();
      float var4 = this.c.h.c(var1);
      GlStateManager.c(var4, var4, var4, 0.5F);
      GlStateManager.l();
      GlStateManager.a(770, 771, 1, 0);
      GlStateManager.E();
      float var5 = 4.0F;
      float var6 = -1.0F;
      float var7 = 1.0F;
      float var8 = -1.0F;
      float var9 = 1.0F;
      float var10 = -0.5F;
      float var11 = -this.c.h.y / 64.0F;
      float var12 = this.c.h.z / 64.0F;
      var3.a(7, DefaultVertexFormats.g);
      var3.b(-1.0, -1.0, -0.5).a((double)(4.0F + var11), (double)(4.0F + var12)).d();
      var3.b(1.0, -1.0, -0.5).a((double)(0.0F + var11), (double)(4.0F + var12)).d();
      var3.b(1.0, 1.0, -0.5).a((double)(0.0F + var11), (double)(0.0F + var12)).d();
      var3.b(-1.0, 1.0, -0.5).a((double)(4.0F + var11), (double)(0.0F + var12)).d();
      var2.b();
      GlStateManager.F();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.k();
   }

   private void f(float var1) {
      Tessellator var2 = Tessellator.a();
      WorldRenderer var3 = var2.c();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 0.9F);
      GlStateManager.c(519);
      GlStateManager.a(false);
      GlStateManager.l();
      GlStateManager.a(770, 771, 1, 0);
      float var4 = 1.0F;

      for (int var5 = 0; var5 < 2; var5++) {
         GlStateManager.E();
         TextureAtlasSprite var6 = this.c.T().a("minecraft:blocks/fire_layer_1");
         this.c.P().a(TextureMap.g);
         float var7 = var6.e();
         float var8 = var6.f();
         float var9 = var6.g();
         float var10 = var6.h();
         float var11 = (0.0F - var4) / 2.0F;
         float var12 = var11 + var4;
         float var13 = 0.0F - var4 / 2.0F;
         float var14 = var13 + var4;
         float var15 = -0.5F;
         GlStateManager.b(-(var5 * 2 - 1) * 0.24F, -0.3F, 0.0F);
         GlStateManager.b((var5 * 2 - 1) * 10.0F, 0.0F, 1.0F, 0.0F);
         var3.a(7, DefaultVertexFormats.g);
         var3.b((double)var11, (double)var13, (double)var15).a((double)var8, (double)var10).d();
         var3.b((double)var12, (double)var13, (double)var15).a((double)var7, (double)var10).d();
         var3.b((double)var12, (double)var14, (double)var15).a((double)var7, (double)var9).d();
         var3.b((double)var11, (double)var14, (double)var15).a((double)var8, (double)var9).d();
         var2.b();
         GlStateManager.F();
      }

      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.k();
      GlStateManager.a(true);
      GlStateManager.c(515);
   }

   public void a() {
      this.f = this.e;
      EntityPlayerSP var1 = this.c.h;
      ItemStack var2 = var1.bi.h();
      boolean var3 = false;
      if (this.d != null && var2 != null) {
         if (!this.d.c(var2)) {
            var3 = true;
         }
      } else if (this.d == null && var2 == null) {
         var3 = false;
      } else {
         var3 = true;
      }

      float var4 = 0.4F;
      float var5 = var3 ? 0.0F : 1.0F;
      float var6 = MathHelper.a(var5 - this.e, -var4, var4);
      this.e += var6;
      if (this.e < 0.1F) {
         this.d = var2;
         this.i = var1.bi.c;
      }
   }

   public void b() {
      this.e = 0.0F;
   }

   public void c() {
      this.e = 0.0F;
   }
}
