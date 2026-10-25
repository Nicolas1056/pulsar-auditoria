package net.minecraft.client.renderer.tileentity;

import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.tileentity.TileEntityEndPortal;
import net.minecraft.util.ResourceLocation;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer<TileEntityEndPortal> {
   private static final ResourceLocation d = new ResourceLocation("textures/environment/end_sky.png");
   private static final ResourceLocation e = new ResourceLocation("textures/entity/end_portal.png");
   private static final Random f = new Random(31100L);
   FloatBuffer c = GLAllocation.h(16);

   public void a(TileEntityEndPortal var1, double var2, double var4, double var6, float var8, int var9) {
      float var10 = (float)this.b.j;
      float var11 = (float)this.b.k;
      float var12 = (float)this.b.l;
      GlStateManager.f();
      f.setSeed(31100L);
      float var13 = 0.75F;

      for (int var14 = 0; var14 < 16; var14++) {
         GlStateManager.E();
         float var15 = 16 - var14;
         float var16 = 0.0625F;
         float var17 = 1.0F / (var15 + 1.0F);
         if (var14 == 0) {
            this.a(d);
            var17 = 0.1F;
            var15 = 65.0F;
            var16 = 0.125F;
            GlStateManager.l();
            GlStateManager.b(770, 771);
         }

         if (var14 >= 1) {
            this.a(e);
         }

         if (var14 == 1) {
            GlStateManager.l();
            GlStateManager.b(1, 1);
            var16 = 0.5F;
         }

         float var18 = (float)(-(var4 + var13));
         float var19 = var18 + (float)ActiveRenderInfo.a().b;
         float var20 = var18 + var15 + (float)ActiveRenderInfo.a().b;
         float var21 = var19 / var20;
         var21 = (float)(var4 + var13) + var21;
         GlStateManager.b(var10, var21, var12);
         GlStateManager.a(GlStateManager.TexGen.a, 9217);
         GlStateManager.a(GlStateManager.TexGen.b, 9217);
         GlStateManager.a(GlStateManager.TexGen.c, 9217);
         GlStateManager.a(GlStateManager.TexGen.d, 9216);
         GlStateManager.a(GlStateManager.TexGen.a, 9473, this.a(1.0F, 0.0F, 0.0F, 0.0F));
         GlStateManager.a(GlStateManager.TexGen.b, 9473, this.a(0.0F, 0.0F, 1.0F, 0.0F));
         GlStateManager.a(GlStateManager.TexGen.c, 9473, this.a(0.0F, 0.0F, 0.0F, 1.0F));
         GlStateManager.a(GlStateManager.TexGen.d, 9474, this.a(0.0F, 1.0F, 0.0F, 0.0F));
         GlStateManager.a(GlStateManager.TexGen.a);
         GlStateManager.a(GlStateManager.TexGen.b);
         GlStateManager.a(GlStateManager.TexGen.c);
         GlStateManager.a(GlStateManager.TexGen.d);
         GlStateManager.F();
         GlStateManager.n(5890);
         GlStateManager.E();
         GlStateManager.D();
         GlStateManager.b(0.0F, (float)(Minecraft.J() % 700000L) / 700000.0F, 0.0F);
         GlStateManager.a(var16, var16, var16);
         GlStateManager.b(0.5F, 0.5F, 0.0F);
         GlStateManager.b((var14 * var14 * 4321 + var14 * 9) * 2.0F, 0.0F, 0.0F, 1.0F);
         GlStateManager.b(-0.5F, -0.5F, 0.0F);
         GlStateManager.b(-var10, -var12, -var11);
         var19 = var18 + (float)ActiveRenderInfo.a().b;
         GlStateManager.b((float)ActiveRenderInfo.a().a * var15 / var19, (float)ActiveRenderInfo.a().c * var15 / var19, -var11);
         Tessellator var26 = Tessellator.a();
         WorldRenderer var28 = var26.c();
         var28.a(7, DefaultVertexFormats.f);
         float var22 = (f.nextFloat() * 0.5F + 0.1F) * var17;
         float var23 = (f.nextFloat() * 0.5F + 0.4F) * var17;
         float var24 = (f.nextFloat() * 0.5F + 0.5F) * var17;
         if (var14 == 0) {
            var22 = var23 = var24 = 1.0F * var17;
         }

         var28.b(var2, var4 + var13, var6).a(var22, var23, var24, 1.0F).d();
         var28.b(var2, var4 + var13, var6 + 1.0).a(var22, var23, var24, 1.0F).d();
         var28.b(var2 + 1.0, var4 + var13, var6 + 1.0).a(var22, var23, var24, 1.0F).d();
         var28.b(var2 + 1.0, var4 + var13, var6).a(var22, var23, var24, 1.0F).d();
         var26.b();
         GlStateManager.F();
         GlStateManager.n(5888);
         this.a(d);
      }

      GlStateManager.k();
      GlStateManager.b(GlStateManager.TexGen.a);
      GlStateManager.b(GlStateManager.TexGen.b);
      GlStateManager.b(GlStateManager.TexGen.c);
      GlStateManager.b(GlStateManager.TexGen.d);
      GlStateManager.e();
   }

   private FloatBuffer a(float var1, float var2, float var3, float var4) {
      ((Buffer)this.c).clear();
      this.c.put(var1).put(var2).put(var3).put(var4);
      ((Buffer)this.c).flip();
      return this.c;
   }
}
