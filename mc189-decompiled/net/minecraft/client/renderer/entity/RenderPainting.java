package net.minecraft.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderPainting extends Render<EntityPainting> {
   private static final ResourceLocation a = new ResourceLocation("textures/painting/paintings_kristoffer_zetterstrand.png");

   public RenderPainting(RenderManager var1) {
      super(var1);
   }

   public void a(EntityPainting var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      GlStateManager.b(var2, var4, var6);
      GlStateManager.b(180.0F - var8, 0.0F, 1.0F, 0.0F);
      GlStateManager.B();
      this.c(var1);
      EntityPainting.EnumArt var10 = var1.c;
      float var11 = 0.0625F;
      GlStateManager.a(var11, var11, var11);
      this.a(var1, var10.C, var10.D, var10.E, var10.F);
      GlStateManager.C();
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityPainting var1) {
      return a;
   }

   private void a(EntityPainting var1, int var2, int var3, int var4, int var5) {
      float var6 = -var2 / 2.0F;
      float var7 = -var3 / 2.0F;
      float var8 = 0.5F;
      float var9 = 0.75F;
      float var10 = 0.8125F;
      float var11 = 0.0F;
      float var12 = 0.0625F;
      float var13 = 0.75F;
      float var14 = 0.8125F;
      float var15 = 0.001953125F;
      float var16 = 0.001953125F;
      float var17 = 0.7519531F;
      float var18 = 0.7519531F;
      float var19 = 0.0F;
      float var20 = 0.0625F;

      for (int var21 = 0; var21 < var2 / 16; var21++) {
         for (int var22 = 0; var22 < var3 / 16; var22++) {
            float var23 = var6 + (var21 + 1) * 16;
            float var24 = var6 + var21 * 16;
            float var25 = var7 + (var22 + 1) * 16;
            float var26 = var7 + var22 * 16;
            this.a(var1, (var23 + var24) / 2.0F, (var25 + var26) / 2.0F);
            float var27 = (var4 + var2 - var21 * 16) / 256.0F;
            float var28 = (var4 + var2 - (var21 + 1) * 16) / 256.0F;
            float var29 = (var5 + var3 - var22 * 16) / 256.0F;
            float var30 = (var5 + var3 - (var22 + 1) * 16) / 256.0F;
            Tessellator var31 = Tessellator.a();
            WorldRenderer var32 = var31.c();
            var32.a(7, DefaultVertexFormats.j);
            var32.b((double)var23, (double)var26, (double)(-var8)).a((double)var28, (double)var29).c(0.0F, 0.0F, -1.0F).d();
            var32.b((double)var24, (double)var26, (double)(-var8)).a((double)var27, (double)var29).c(0.0F, 0.0F, -1.0F).d();
            var32.b((double)var24, (double)var25, (double)(-var8)).a((double)var27, (double)var30).c(0.0F, 0.0F, -1.0F).d();
            var32.b((double)var23, (double)var25, (double)(-var8)).a((double)var28, (double)var30).c(0.0F, 0.0F, -1.0F).d();
            var32.b((double)var23, (double)var25, (double)var8).a((double)var9, (double)var11).c(0.0F, 0.0F, 1.0F).d();
            var32.b((double)var24, (double)var25, (double)var8).a((double)var10, (double)var11).c(0.0F, 0.0F, 1.0F).d();
            var32.b((double)var24, (double)var26, (double)var8).a((double)var10, (double)var12).c(0.0F, 0.0F, 1.0F).d();
            var32.b((double)var23, (double)var26, (double)var8).a((double)var9, (double)var12).c(0.0F, 0.0F, 1.0F).d();
            var32.b((double)var23, (double)var25, (double)(-var8)).a((double)var13, (double)var15).c(0.0F, 1.0F, 0.0F).d();
            var32.b((double)var24, (double)var25, (double)(-var8)).a((double)var14, (double)var15).c(0.0F, 1.0F, 0.0F).d();
            var32.b((double)var24, (double)var25, (double)var8).a((double)var14, (double)var16).c(0.0F, 1.0F, 0.0F).d();
            var32.b((double)var23, (double)var25, (double)var8).a((double)var13, (double)var16).c(0.0F, 1.0F, 0.0F).d();
            var32.b((double)var23, (double)var26, (double)var8).a((double)var13, (double)var15).c(0.0F, -1.0F, 0.0F).d();
            var32.b((double)var24, (double)var26, (double)var8).a((double)var14, (double)var15).c(0.0F, -1.0F, 0.0F).d();
            var32.b((double)var24, (double)var26, (double)(-var8)).a((double)var14, (double)var16).c(0.0F, -1.0F, 0.0F).d();
            var32.b((double)var23, (double)var26, (double)(-var8)).a((double)var13, (double)var16).c(0.0F, -1.0F, 0.0F).d();
            var32.b((double)var23, (double)var25, (double)var8).a((double)var18, (double)var19).c(-1.0F, 0.0F, 0.0F).d();
            var32.b((double)var23, (double)var26, (double)var8).a((double)var18, (double)var20).c(-1.0F, 0.0F, 0.0F).d();
            var32.b((double)var23, (double)var26, (double)(-var8)).a((double)var17, (double)var20).c(-1.0F, 0.0F, 0.0F).d();
            var32.b((double)var23, (double)var25, (double)(-var8)).a((double)var17, (double)var19).c(-1.0F, 0.0F, 0.0F).d();
            var32.b((double)var24, (double)var25, (double)(-var8)).a((double)var18, (double)var19).c(1.0F, 0.0F, 0.0F).d();
            var32.b((double)var24, (double)var26, (double)(-var8)).a((double)var18, (double)var20).c(1.0F, 0.0F, 0.0F).d();
            var32.b((double)var24, (double)var26, (double)var8).a((double)var17, (double)var20).c(1.0F, 0.0F, 0.0F).d();
            var32.b((double)var24, (double)var25, (double)var8).a((double)var17, (double)var19).c(1.0F, 0.0F, 0.0F).d();
            var31.b();
         }
      }
   }

   private void a(EntityPainting var1, float var2, float var3) {
      int var4 = MathHelper.c(var1.s);
      int var5 = MathHelper.c(var1.t + var3 / 16.0F);
      int var6 = MathHelper.c(var1.u);
      EnumFacing var7 = var1.b;
      if (var7 == EnumFacing.c) {
         var4 = MathHelper.c(var1.s + var2 / 16.0F);
      }

      if (var7 == EnumFacing.e) {
         var6 = MathHelper.c(var1.u - var2 / 16.0F);
      }

      if (var7 == EnumFacing.d) {
         var4 = MathHelper.c(var1.s - var2 / 16.0F);
      }

      if (var7 == EnumFacing.f) {
         var6 = MathHelper.c(var1.u + var2 / 16.0F);
      }

      int var8 = this.b.b.b(new BlockPos(var4, var5, var6), 0);
      int var9 = var8 % 65536;
      int var10 = var8 / 65536;
      OpenGlHelper.a(OpenGlHelper.r, var9, var10);
      GlStateManager.c(1.0F, 1.0F, 1.0F);
   }
}
