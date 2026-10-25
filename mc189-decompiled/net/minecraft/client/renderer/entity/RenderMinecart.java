package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelMinecart;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

public class RenderMinecart<T extends EntityMinecart> extends Render<T> {
   private static final ResourceLocation e = new ResourceLocation("textures/entity/minecart.png");
   protected ModelBase a = new ModelMinecart();

   public RenderMinecart(RenderManager var1) {
      super(var1);
      this.c = 0.5F;
   }

   public void a(T var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      this.c((T)var1);
      long var10 = var1.F() * 493286711L;
      var10 = var10 * var10 * 4392167121L + var10 * 98761L;
      float var12 = (((float)(var10 >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float var13 = (((float)(var10 >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      float var14 = (((float)(var10 >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
      GlStateManager.b(var12, var13, var14);
      double var15 = var1.P + (var1.s - var1.P) * var9;
      double var17 = var1.Q + (var1.t - var1.Q) * var9;
      double var19 = var1.R + (var1.u - var1.R) * var9;
      double var21 = 0.3F;
      Vec3 var23 = var1.k(var15, var17, var19);
      float var24 = var1.B + (var1.z - var1.B) * var9;
      if (var23 != null) {
         Vec3 var25 = var1.a(var15, var17, var19, var21);
         Vec3 var26 = var1.a(var15, var17, var19, -var21);
         if (var25 == null) {
            var25 = var23;
         }

         if (var26 == null) {
            var26 = var23;
         }

         var2 += var23.a - var15;
         var4 += (var25.b + var26.b) / 2.0 - var17;
         var6 += var23.c - var19;
         Vec3 var27 = var26.b(-var25.a, -var25.b, -var25.c);
         if (var27.b() != 0.0) {
            var27 = var27.a();
            var8 = (float)(Math.atan2(var27.c, var27.a) * 180.0 / Math.PI);
            var24 = (float)(Math.atan(var27.b) * 73.0);
         }
      }

      GlStateManager.b((float)var2, (float)var4 + 0.375F, (float)var6);
      GlStateManager.b(180.0F - var8, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(-var24, 0.0F, 0.0F, 1.0F);
      float var31 = var1.q() - var9;
      float var32 = var1.p() - var9;
      if (var32 < 0.0F) {
         var32 = 0.0F;
      }

      if (var31 > 0.0F) {
         GlStateManager.b(MathHelper.a(var31) * var31 * var32 / 10.0F * var1.r(), 1.0F, 0.0F, 0.0F);
      }

      int var34 = var1.v();
      IBlockState var28 = var1.t();
      if (var28.c().b() != -1) {
         GlStateManager.E();
         this.a(TextureMap.g);
         float var29 = 0.75F;
         GlStateManager.a(var29, var29, var29);
         GlStateManager.b(-0.5F, (var34 - 8) / 16.0F, 0.5F);
         this.a((T)var1, var9, var28);
         GlStateManager.F();
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.c((T)var1);
      }

      GlStateManager.a(-1.0F, -1.0F, 1.0F);
      this.a.a(var1, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);
      GlStateManager.F();
      super.a((T)var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(T var1) {
      return e;
   }

   protected void a(T var1, float var2, IBlockState var3) {
      GlStateManager.E();
      Minecraft.A().ae().a(var3, var1.c(var2));
      GlStateManager.F();
   }
}
