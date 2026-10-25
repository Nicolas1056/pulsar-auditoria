package net.minecraft.client.renderer.tileentity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBanner;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.LayeredColorMaskTexture;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class TileEntityBannerRenderer extends TileEntitySpecialRenderer<TileEntityBanner> {
   private static final Map<String, TileEntityBannerRenderer.TimedBannerTexture> c = Maps.newHashMap();
   private static final ResourceLocation d = new ResourceLocation("textures/entity/banner_base.png");
   private ModelBanner e = new ModelBanner();

   public void a(TileEntityBanner var1, double var2, double var4, double var6, float var8, int var9) {
      boolean var10 = var1.z() != null;
      boolean var11 = !var10 || var1.w() == Blocks.cK;
      int var12 = var10 ? var1.u() : 0;
      long var13 = var10 ? var1.z().K() : 0L;
      GlStateManager.E();
      float var15 = 0.6666667F;
      if (var11) {
         GlStateManager.b((float)var2 + 0.5F, (float)var4 + 0.75F * var15, (float)var6 + 0.5F);
         float var16 = var12 * 360 / 16.0F;
         GlStateManager.b(-var16, 0.0F, 1.0F, 0.0F);
         this.e.b.j = true;
      } else {
         float var17 = 0.0F;
         if (var12 == 2) {
            var17 = 180.0F;
         }

         if (var12 == 4) {
            var17 = 90.0F;
         }

         if (var12 == 5) {
            var17 = -90.0F;
         }

         GlStateManager.b((float)var2 + 0.5F, (float)var4 - 0.25F * var15, (float)var6 + 0.5F);
         GlStateManager.b(-var17, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(0.0F, -0.3125F, -0.4375F);
         this.e.b.j = false;
      }

      BlockPos var19 = var1.v();
      float var20 = var19.n() * 7 + var19.o() * 9 + var19.p() * 13 + (float)var13 + var8;
      this.e.a.f = (-0.0125F + 0.01F * MathHelper.b(var20 * (float) Math.PI * 0.02F)) * (float) Math.PI;
      GlStateManager.B();
      ResourceLocation var18 = this.a(var1);
      if (var18 != null) {
         this.a(var18);
         GlStateManager.E();
         GlStateManager.a(var15, -var15, -var15);
         this.e.a();
         GlStateManager.F();
      }

      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.F();
   }

   private ResourceLocation a(TileEntityBanner var1) {
      String var2 = var1.g();
      if (var2.isEmpty()) {
         return null;
      } else {
         TileEntityBannerRenderer.TimedBannerTexture var3 = c.get(var2);
         if (var3 == null) {
            if (c.size() >= 256) {
               long var4 = System.currentTimeMillis();
               Iterator var6 = c.keySet().iterator();

               while (var6.hasNext()) {
                  String var7 = (String)var6.next();
                  TileEntityBannerRenderer.TimedBannerTexture var8 = c.get(var7);
                  if (var4 - var8.a > 60000L) {
                     Minecraft.A().P().c(var8.b);
                     var6.remove();
                  }
               }

               if (c.size() >= 256) {
                  return null;
               }
            }

            List var9 = var1.c();
            List var5 = var1.e();
            ArrayList var10 = Lists.newArrayList();

            for (TileEntityBanner.EnumBannerPattern var12 : var9) {
               var10.add("textures/entity/banner/" + var12.a() + ".png");
            }

            var3 = new TileEntityBannerRenderer.TimedBannerTexture();
            var3.b = new ResourceLocation(var2);
            Minecraft.A().P().a(var3.b, new LayeredColorMaskTexture(d, var10, var5));
            c.put(var2, var3);
         }

         var3.a = System.currentTimeMillis();
         return var3.b;
      }
   }

   static class TimedBannerTexture {
      public long a;
      public ResourceLocation b;

      private TimedBannerTexture() {
      }
   }
}
