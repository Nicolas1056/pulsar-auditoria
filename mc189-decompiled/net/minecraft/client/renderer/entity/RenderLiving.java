package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityHanging;
import net.minecraft.entity.EntityLiving;

public abstract class RenderLiving<T extends EntityLiving> extends RendererLivingEntity<T> {
   public RenderLiving(RenderManager var1, ModelBase var2, float var3) {
      super(var1, var2, var3);
   }

   protected boolean b(T var1) {
      return super.a((T)var1) && (var1.aO() || var1.l_() && var1 == this.b.d);
   }

   public boolean a(T var1, ICamera var2, double var3, double var5, double var7) {
      if (super.a((T)var1, var2, var3, var5, var7)) {
         return true;
      } else if (var1.cc() && var1.cd() != null) {
         Entity var9 = var1.cd();
         return var2.a(var9.aR());
      } else {
         return false;
      }
   }

   public void a(T var1, double var2, double var4, double var6, float var8, float var9) {
      super.a((T)var1, var2, var4, var6, var8, var9);
      this.b((T)var1, var2, var4, var6, var8, var9);
   }

   public void a(T var1, float var2) {
      int var3 = var1.b(var2);
      int var4 = var3 % 65536;
      int var5 = var3 / 65536;
      OpenGlHelper.a(OpenGlHelper.r, var4 / 1.0F, var5 / 1.0F);
   }

   private double a(double var1, double var3, double var5) {
      return var1 + (var3 - var1) * var5;
   }

   protected void b(T var1, double var2, double var4, double var6, float var8, float var9) {
      Entity var10 = var1.cd();
      if (var10 != null) {
         var4 -= (1.6 - var1.K) * 0.5;
         Tessellator var11 = Tessellator.a();
         WorldRenderer var12 = var11.c();
         double var13 = this.a(var10.A, var10.y, var9 * 0.5F) * (float) (Math.PI / 180.0);
         double var15 = this.a(var10.B, var10.z, var9 * 0.5F) * (float) (Math.PI / 180.0);
         double var17 = Math.cos(var13);
         double var19 = Math.sin(var13);
         double var21 = Math.sin(var15);
         if (var10 instanceof EntityHanging) {
            var17 = 0.0;
            var19 = 0.0;
            var21 = -1.0;
         }

         double var23 = Math.cos(var15);
         double var25 = this.a(var10.p, var10.s, var9) - var17 * 0.7 - var19 * 0.5 * var23;
         double var27 = this.a(var10.q + var10.aS() * 0.7, var10.t + var10.aS() * 0.7, var9) - var21 * 0.5 - 0.25;
         double var29 = this.a(var10.r, var10.u, var9) - var19 * 0.7 + var17 * 0.5 * var23;
         double var31 = this.a(var1.aJ, var1.aI, var9) * (float) (Math.PI / 180.0) + (Math.PI / 2);
         var17 = Math.cos(var31) * var1.J * 0.4;
         var19 = Math.sin(var31) * var1.J * 0.4;
         double var33 = this.a(var1.p, var1.s, var9) + var17;
         double var35 = this.a(var1.q, var1.t, var9);
         double var37 = this.a(var1.r, var1.u, var9) + var19;
         var2 += var17;
         var6 += var19;
         double var39 = (float)(var25 - var33);
         double var41 = (float)(var27 - var35);
         double var43 = (float)(var29 - var37);
         GlStateManager.x();
         GlStateManager.f();
         GlStateManager.p();
         byte var45 = 24;
         double var46 = 0.025;
         var12.a(5, DefaultVertexFormats.f);

         for (int var48 = 0; var48 <= 24; var48++) {
            float var49 = 0.5F;
            float var50 = 0.4F;
            float var51 = 0.3F;
            if (var48 % 2 == 0) {
               var49 *= 0.7F;
               var50 *= 0.7F;
               var51 *= 0.7F;
            }

            float var52 = var48 / 24.0F;
            var12.b(var2 + var39 * var52 + 0.0, var4 + var41 * (var52 * var52 + var52) * 0.5 + ((24.0F - var48) / 18.0F + 0.125F), var6 + var43 * var52)
               .a(var49, var50, var51, 1.0F)
               .d();
            var12.b(
                  var2 + var39 * var52 + 0.025, var4 + var41 * (var52 * var52 + var52) * 0.5 + ((24.0F - var48) / 18.0F + 0.125F) + 0.025, var6 + var43 * var52
               )
               .a(var49, var50, var51, 1.0F)
               .d();
         }

         var11.b();
         var12.a(5, DefaultVertexFormats.f);

         for (int var58 = 0; var58 <= 24; var58++) {
            float var59 = 0.5F;
            float var60 = 0.4F;
            float var61 = 0.3F;
            if (var58 % 2 == 0) {
               var59 *= 0.7F;
               var60 *= 0.7F;
               var61 *= 0.7F;
            }

            float var62 = var58 / 24.0F;
            var12.b(var2 + var39 * var62 + 0.0, var4 + var41 * (var62 * var62 + var62) * 0.5 + ((24.0F - var58) / 18.0F + 0.125F) + 0.025, var6 + var43 * var62)
               .a(var59, var60, var61, 1.0F)
               .d();
            var12.b(
                  var2 + var39 * var62 + 0.025, var4 + var41 * (var62 * var62 + var62) * 0.5 + ((24.0F - var58) / 18.0F + 0.125F), var6 + var43 * var62 + 0.025
               )
               .a(var59, var60, var61, 1.0F)
               .d();
         }

         var11.b();
         GlStateManager.e();
         GlStateManager.w();
         GlStateManager.o();
      }
   }
}
