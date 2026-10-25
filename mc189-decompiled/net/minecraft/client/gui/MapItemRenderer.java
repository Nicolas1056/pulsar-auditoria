package net.minecraft.client.gui;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec4b;
import net.minecraft.world.storage.MapData;

public class MapItemRenderer {
   private static final ResourceLocation a = new ResourceLocation("textures/map/map_icons.png");
   private final TextureManager b;
   private final Map<String, MapItemRenderer.Instance> c = Maps.newHashMap();

   public MapItemRenderer(TextureManager var1) {
      this.b = var1;
   }

   public void a(MapData var1) {
      this.b(var1).a();
   }

   public void a(MapData var1, boolean var2) {
      this.b(var1).a(var2);
   }

   private MapItemRenderer.Instance b(MapData var1) {
      MapItemRenderer.Instance var2 = this.c.get(var1.a);
      if (var2 == null) {
         var2 = new MapItemRenderer.Instance(var1);
         this.c.put(var1.a, var2);
      }

      return var2;
   }

   public void a() {
      for (MapItemRenderer.Instance var2 : this.c.values()) {
         this.b.c(var2.d);
      }

      this.c.clear();
   }

   class Instance {
      private final MapData b;
      private final DynamicTexture c;
      private final ResourceLocation d;
      private final int[] e;

      private Instance(MapData var2) {
         this.b = var2;
         this.c = new DynamicTexture(128, 128);
         this.e = this.c.e();
         this.d = MapItemRenderer.this.b.a("map/" + var2.a, this.c);

         for (int var3 = 0; var3 < this.e.length; var3++) {
            this.e[var3] = 0;
         }
      }

      private void a() {
         for (int var1 = 0; var1 < 16384; var1++) {
            int var2 = this.b.f[var1] & 255;
            if (var2 / 4 == 0) {
               this.e[var1] = (var1 + var1 / 128 & 1) * 8 + 16 << 24;
            } else {
               this.e[var1] = MapColor.a[var2 / 4].a(var2 & 3);
            }
         }

         this.c.d();
      }

      private void a(boolean var1) {
         byte var2 = 0;
         byte var3 = 0;
         Tessellator var4 = Tessellator.a();
         WorldRenderer var5 = var4.c();
         float var6 = 0.0F;
         MapItemRenderer.this.b.a(this.d);
         GlStateManager.l();
         GlStateManager.a(1, 771, 0, 1);
         GlStateManager.c();
         var5.a(7, DefaultVertexFormats.g);
         var5.b((double)(var2 + 0 + var6), (double)(var3 + 128 - var6), -0.01F).a(0.0, 1.0).d();
         var5.b((double)(var2 + 128 - var6), (double)(var3 + 128 - var6), -0.01F).a(1.0, 1.0).d();
         var5.b((double)(var2 + 128 - var6), (double)(var3 + 0 + var6), -0.01F).a(1.0, 0.0).d();
         var5.b((double)(var2 + 0 + var6), (double)(var3 + 0 + var6), -0.01F).a(0.0, 0.0).d();
         var4.b();
         GlStateManager.d();
         GlStateManager.k();
         MapItemRenderer.this.b.a(MapItemRenderer.a);
         int var7 = 0;

         for (Vec4b var9 : this.b.h.values()) {
            if (!var1 || var9.a() == 1) {
               GlStateManager.E();
               GlStateManager.b(var2 + var9.b() / 2.0F + 64.0F, var3 + var9.c() / 2.0F + 64.0F, -0.02F);
               GlStateManager.b(var9.d() * 360 / 16.0F, 0.0F, 0.0F, 1.0F);
               GlStateManager.a(4.0F, 4.0F, 3.0F);
               GlStateManager.b(-0.125F, 0.125F, 0.0F);
               byte var10 = var9.a();
               float var11 = (var10 % 4 + 0) / 4.0F;
               float var12 = (var10 / 4 + 0) / 4.0F;
               float var13 = (var10 % 4 + 1) / 4.0F;
               float var14 = (var10 / 4 + 1) / 4.0F;
               var5.a(7, DefaultVertexFormats.g);
               float var15 = -0.001F;
               var5.b(-1.0, 1.0, (double)(var7 * -0.001F)).a((double)var11, (double)var12).d();
               var5.b(1.0, 1.0, (double)(var7 * -0.001F)).a((double)var13, (double)var12).d();
               var5.b(1.0, -1.0, (double)(var7 * -0.001F)).a((double)var13, (double)var14).d();
               var5.b(-1.0, -1.0, (double)(var7 * -0.001F)).a((double)var11, (double)var14).d();
               var4.b();
               GlStateManager.F();
               var7++;
            }
         }

         GlStateManager.E();
         GlStateManager.b(0.0F, 0.0F, -0.04F);
         GlStateManager.a(1.0F, 1.0F, 1.0F);
         GlStateManager.F();
      }
   }
}
