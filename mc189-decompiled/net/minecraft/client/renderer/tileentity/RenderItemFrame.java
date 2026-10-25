package net.minecraft.client.renderer.tileentity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureCompass;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.MapData;
import org.lwjgl.opengl.GL11;

public class RenderItemFrame extends Render<EntityItemFrame> {
   private static final ResourceLocation a = new ResourceLocation("textures/map/map_background.png");
   private final Minecraft e = Minecraft.A();
   private final ModelResourceLocation f = new ModelResourceLocation("item_frame", "normal");
   private final ModelResourceLocation g = new ModelResourceLocation("item_frame", "map");
   private RenderItem h;

   public RenderItemFrame(RenderManager var1, RenderItem var2) {
      super(var1);
      this.h = var2;
   }

   public void a(EntityItemFrame var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      BlockPos var10 = var1.n();
      double var11 = var10.n() - var1.s + var2;
      double var13 = var10.o() - var1.t + var4;
      double var15 = var10.p() - var1.u + var6;
      GlStateManager.b(var11 + 0.5, var13 + 0.5, var15 + 0.5);
      GlStateManager.b(180.0F - var1.y, 0.0F, 1.0F, 0.0F);
      this.b.a.a(TextureMap.g);
      BlockRendererDispatcher var17 = this.e.ae();
      ModelManager var18 = var17.a().b();
      IBakedModel var19;
      if (var1.o() != null && var1.o().b() == Items.bd) {
         var19 = var18.a(this.g);
      } else {
         var19 = var18.a(this.f);
      }

      GlStateManager.E();
      GlStateManager.b(-0.5F, -0.5F, -0.5F);
      var17.b().a(var19, 1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.F();
      GlStateManager.b(0.0F, 0.0F, 0.4375F);
      this.b(var1);
      GlStateManager.F();
      this.a(var1, var2 + var1.b.g() * 0.3F, var4 - 0.25, var6 + var1.b.i() * 0.3F);
   }

   protected ResourceLocation a(EntityItemFrame var1) {
      return null;
   }

   private void b(EntityItemFrame var1) {
      ItemStack var2 = var1.o();
      if (var2 != null) {
         EntityItem var3 = new EntityItem(var1.o, 0.0, 0.0, 0.0, var2);
         Item var4 = var3.l().b();
         var3.l().b = 1;
         var3.a = 0.0F;
         GlStateManager.E();
         GlStateManager.f();
         int var5 = var1.p();
         if (var4 == Items.bd) {
            var5 = var5 % 4 * 2;
         }

         GlStateManager.b(var5 * 360.0F / 8.0F, 0.0F, 0.0F, 1.0F);
         if (var4 == Items.bd) {
            this.b.a.a(a);
            GlStateManager.b(180.0F, 0.0F, 0.0F, 1.0F);
            float var6 = 0.0078125F;
            GlStateManager.a(var6, var6, var6);
            GlStateManager.b(-64.0F, -64.0F, 0.0F);
            MapData var7 = Items.bd.a(var3.l(), var1.o);
            GlStateManager.b(0.0F, 0.0F, -1.0F);
            if (var7 != null) {
               this.e.o.k().a(var7, true);
            }
         } else {
            TextureAtlasSprite var12 = null;
            if (var4 == Items.aQ) {
               var12 = this.e.T().a(TextureCompass.l);
               this.e.P().a(TextureMap.g);
               if (var12 instanceof TextureCompass) {
                  TextureCompass var13 = (TextureCompass)var12;
                  double var8 = var13.j;
                  double var10 = var13.k;
                  var13.j = 0.0;
                  var13.k = 0.0;
                  var13.a(var1.o, var1.s, var1.u, MathHelper.g((float)(180 + var1.b.b() * 90)), false, true);
                  var13.j = var8;
                  var13.k = var10;
               } else {
                  var12 = null;
               }
            }

            GlStateManager.a(0.5F, 0.5F, 0.5F);
            if (!this.h.a(var3.l()) || var4 instanceof ItemSkull) {
               GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
            }

            GlStateManager.a();
            RenderHelper.b();
            this.h.a(var3.l(), ItemCameraTransforms.TransformType.g);
            RenderHelper.a();
            GlStateManager.b();
            if (var12 != null && var12.k() > 0) {
               var12.j();
            }
         }

         GlStateManager.e();
         GlStateManager.F();
      }
   }

   protected void a(EntityItemFrame var1, double var2, double var4, double var6) {
      if (Minecraft.v() && var1.o() != null && var1.o().s() && this.b.d == var1) {
         float var8 = 1.6F;
         float var9 = 0.016666668F * var8;
         double var10 = var1.h(this.b.c);
         float var12 = var1.av() ? 32.0F : 64.0F;
         if (var10 < var12 * var12) {
            String var13 = var1.o().q();
            if (var1.av()) {
               FontRenderer var14 = this.c();
               GlStateManager.E();
               GlStateManager.b((float)var2 + 0.0F, (float)var4 + var1.K + 0.5F, (float)var6);
               GL11.glNormal3f(0.0F, 1.0F, 0.0F);
               GlStateManager.b(-this.b.e, 0.0F, 1.0F, 0.0F);
               GlStateManager.b(this.b.f, 1.0F, 0.0F, 0.0F);
               GlStateManager.a(-var9, -var9, var9);
               GlStateManager.f();
               GlStateManager.b(0.0F, 0.25F / var9, 0.0F);
               GlStateManager.a(false);
               GlStateManager.l();
               GlStateManager.b(770, 771);
               Tessellator var15 = Tessellator.a();
               WorldRenderer var16 = var15.c();
               int var17 = var14.a(var13) / 2;
               GlStateManager.x();
               var16.a(7, DefaultVertexFormats.f);
               var16.b((double)(-var17 - 1), -1.0, 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
               var16.b((double)(-var17 - 1), 8.0, 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
               var16.b((double)(var17 + 1), 8.0, 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
               var16.b((double)(var17 + 1), -1.0, 0.0).a(0.0F, 0.0F, 0.0F, 0.25F).d();
               var15.b();
               GlStateManager.w();
               GlStateManager.a(true);
               var14.a(var13, -var14.a(var13) / 2, 0, 553648127);
               GlStateManager.e();
               GlStateManager.k();
               GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.F();
            } else {
               this.a(var1, var13, var2, var4, var6, 64);
            }
         }
      }
   }
}
