package net.minecraft.client.renderer.entity.layers;

import java.util.Random;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.EntityDragon;

public class LayerEnderDragonDeath implements LayerRenderer<EntityDragon> {
   public void a(EntityDragon var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.by > 0) {
         Tessellator var9 = Tessellator.a();
         WorldRenderer var10 = var9.c();
         RenderHelper.a();
         float var11 = (var1.by + var4) / 200.0F;
         float var12 = 0.0F;
         if (var11 > 0.8F) {
            var12 = (var11 - 0.8F) / 0.2F;
         }

         Random var13 = new Random(432L);
         GlStateManager.x();
         GlStateManager.j(7425);
         GlStateManager.l();
         GlStateManager.b(770, 1);
         GlStateManager.c();
         GlStateManager.o();
         GlStateManager.a(false);
         GlStateManager.E();
         GlStateManager.b(0.0F, -1.0F, -2.0F);

         for (int var14 = 0; var14 < (var11 + var11 * var11) / 2.0F * 60.0F; var14++) {
            GlStateManager.b(var13.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(var13.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.b(var13.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GlStateManager.b(var13.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GlStateManager.b(var13.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.b(var13.nextFloat() * 360.0F + var11 * 90.0F, 0.0F, 0.0F, 1.0F);
            float var15 = var13.nextFloat() * 20.0F + 5.0F + var12 * 10.0F;
            float var16 = var13.nextFloat() * 2.0F + 1.0F + var12 * 2.0F;
            var10.a(6, DefaultVertexFormats.f);
            var10.b(0.0, 0.0, 0.0).b(255, 255, 255, (int)(255.0F * (1.0F - var12))).d();
            var10.b(-0.866 * var16, (double)var15, (double)(-0.5F * var16)).b(255, 0, 255, 0).d();
            var10.b(0.866 * var16, (double)var15, (double)(-0.5F * var16)).b(255, 0, 255, 0).d();
            var10.b(0.0, (double)var15, (double)(1.0F * var16)).b(255, 0, 255, 0).d();
            var10.b(-0.866 * var16, (double)var15, (double)(-0.5F * var16)).b(255, 0, 255, 0).d();
            var9.b();
         }

         GlStateManager.F();
         GlStateManager.a(true);
         GlStateManager.p();
         GlStateManager.k();
         GlStateManager.j(7424);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.w();
         GlStateManager.d();
         RenderHelper.b();
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
