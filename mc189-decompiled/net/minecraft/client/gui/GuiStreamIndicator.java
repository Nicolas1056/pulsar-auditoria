package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class GuiStreamIndicator {
   private static final ResourceLocation a = new ResourceLocation("textures/gui/stream_indicator.png");
   private final Minecraft b;
   private float c = 1.0F;
   private int d = 1;

   public GuiStreamIndicator(Minecraft var1) {
      this.b = var1;
   }

   public void a(int var1, int var2) {
      if (this.b.Y().k()) {
         GlStateManager.l();
         int var3 = this.b.Y().x();
         if (var3 > 0) {
            String var4 = "" + var3;
            int var5 = this.b.k.a(var4);
            byte var6 = 20;
            int var7 = var1 - var5 - 1;
            int var8 = var2 + 20 - 1;
            int var10 = var2 + 20 + this.b.k.a - 1;
            GlStateManager.x();
            Tessellator var11 = Tessellator.a();
            WorldRenderer var12 = var11.c();
            GlStateManager.c(0.0F, 0.0F, 0.0F, (0.65F + 0.35000002F * this.c) / 2.0F);
            var12.a(7, DefaultVertexFormats.e);
            var12.b((double)var7, (double)var10, 0.0).d();
            var12.b((double)var1, (double)var10, 0.0).d();
            var12.b((double)var1, (double)var8, 0.0).d();
            var12.b((double)var7, (double)var8, 0.0).d();
            var11.b();
            GlStateManager.w();
            this.b.k.a(var4, var1 - var5, var2 + 20, 16777215);
         }

         this.a(var1, var2, this.b(), 0);
         this.a(var1, var2, this.c(), 17);
      }
   }

   private void a(int var1, int var2, int var3, int var4) {
      GlStateManager.c(1.0F, 1.0F, 1.0F, 0.65F + 0.35000002F * this.c);
      this.b.P().a(a);
      float var5 = 150.0F;
      float var6 = 0.0F;
      float var7 = var3 * 0.015625F;
      float var8 = 1.0F;
      float var9 = (var3 + 16) * 0.015625F;
      Tessellator var10 = Tessellator.a();
      WorldRenderer var11 = var10.c();
      var11.a(7, DefaultVertexFormats.g);
      var11.b((double)(var1 - 16 - var4), (double)(var2 + 16), (double)var5).a((double)var6, (double)var9).d();
      var11.b((double)(var1 - var4), (double)(var2 + 16), (double)var5).a((double)var8, (double)var9).d();
      var11.b((double)(var1 - var4), (double)(var2 + 0), (double)var5).a((double)var8, (double)var7).d();
      var11.b((double)(var1 - 16 - var4), (double)(var2 + 0), (double)var5).a((double)var6, (double)var7).d();
      var10.b();
      GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private int b() {
      return this.b.Y().l() ? 16 : 0;
   }

   private int c() {
      return this.b.Y().D() ? 48 : 32;
   }

   public void a() {
      if (this.b.Y().k()) {
         this.c = this.c + 0.025F * this.d;
         if (this.c < 0.0F) {
            this.d *= -1;
            this.c = 0.0F;
         } else if (this.c > 1.0F) {
            this.d *= -1;
            this.c = 1.0F;
         }
      } else {
         this.c = 1.0F;
         this.d = 1;
      }
   }
}
