package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelDragon;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonDeath;
import net.minecraft.client.renderer.entity.layers.LayerEnderDragonEyes;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class RenderDragon extends RenderLiving<EntityDragon> {
   private static final ResourceLocation e = new ResourceLocation("textures/entity/endercrystal/endercrystal_beam.png");
   private static final ResourceLocation j = new ResourceLocation("textures/entity/enderdragon/dragon_exploding.png");
   private static final ResourceLocation k = new ResourceLocation("textures/entity/enderdragon/dragon.png");
   protected ModelDragon a = (ModelDragon)this.f;

   public RenderDragon(RenderManager var1) {
      super(var1, new ModelDragon(0.0F), 0.5F);
      this.a(new LayerEnderDragonEyes(this));
      this.a(new LayerEnderDragonDeath());
   }

   protected void a(EntityDragon var1, float var2, float var3, float var4) {
      float var5 = (float)var1.b(7, var4)[0];
      float var6 = (float)(var1.b(5, var4)[1] - var1.b(10, var4)[1]);
      GlStateManager.b(-var5, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(var6 * 10.0F, 1.0F, 0.0F, 0.0F);
      GlStateManager.b(0.0F, 0.0F, 1.0F);
      if (var1.ax > 0) {
         float var7 = (var1.ax + var4 - 1.0F) / 20.0F * 1.6F;
         var7 = MathHelper.c(var7);
         if (var7 > 1.0F) {
            var7 = 1.0F;
         }

         GlStateManager.b(var7 * this.b(var1), 0.0F, 0.0F, 1.0F);
      }
   }

   protected void a(EntityDragon var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      if (var1.by > 0) {
         float var8 = var1.by / 200.0F;
         GlStateManager.c(515);
         GlStateManager.d();
         GlStateManager.a(516, var8);
         this.a(j);
         this.f.a(var1, var2, var3, var4, var5, var6, var7);
         GlStateManager.a(516, 0.1F);
         GlStateManager.c(514);
      }

      this.c(var1);
      this.f.a(var1, var2, var3, var4, var5, var6, var7);
      if (var1.au > 0) {
         GlStateManager.c(514);
         GlStateManager.x();
         GlStateManager.l();
         GlStateManager.b(770, 771);
         GlStateManager.c(1.0F, 0.0F, 0.0F, 0.5F);
         this.f.a(var1, var2, var3, var4, var5, var6, var7);
         GlStateManager.w();
         GlStateManager.k();
         GlStateManager.c(515);
      }
   }

   public void a(EntityDragon var1, double var2, double var4, double var6, float var8, float var9) {
      BossStatus.a(var1, false);
      super.a(var1, var2, var4, var6, var8, var9);
      if (var1.bz != null) {
         this.a(var1, var2, var4, var6, var9);
      }
   }

   protected void a(EntityDragon var1, double var2, double var4, double var6, float var8) {
      float var9 = var1.bz.a + var8;
      float var10 = MathHelper.a(var9 * 0.2F) / 2.0F + 0.5F;
      var10 = (var10 * var10 + var10) * 0.2F;
      float var11 = (float)(var1.bz.s - var1.s - (var1.p - var1.s) * (1.0F - var8));
      float var12 = (float)(var10 + var1.bz.t - 1.0 - var1.t - (var1.q - var1.t) * (1.0F - var8));
      float var13 = (float)(var1.bz.u - var1.u - (var1.r - var1.u) * (1.0F - var8));
      float var14 = MathHelper.c(var11 * var11 + var13 * var13);
      float var15 = MathHelper.c(var11 * var11 + var12 * var12 + var13 * var13);
      GlStateManager.E();
      GlStateManager.b((float)var2, (float)var4 + 2.0F, (float)var6);
      GlStateManager.b((float)(-Math.atan2(var13, var11)) * 180.0F / (float) Math.PI - 90.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.b((float)(-Math.atan2(var14, var12)) * 180.0F / (float) Math.PI - 90.0F, 1.0F, 0.0F, 0.0F);
      Tessellator var16 = Tessellator.a();
      WorldRenderer var17 = var16.c();
      RenderHelper.a();
      GlStateManager.p();
      this.a(e);
      GlStateManager.j(7425);
      float var18 = 0.0F - (var1.W + var8) * 0.01F;
      float var19 = MathHelper.c(var11 * var11 + var12 * var12 + var13 * var13) / 32.0F - (var1.W + var8) * 0.01F;
      var17.a(5, DefaultVertexFormats.i);
      byte var20 = 8;

      for (int var21 = 0; var21 <= 8; var21++) {
         float var22 = MathHelper.a(var21 % 8 * (float) Math.PI * 2.0F / 8.0F) * 0.75F;
         float var23 = MathHelper.b(var21 % 8 * (float) Math.PI * 2.0F / 8.0F) * 0.75F;
         float var24 = var21 % 8 * 1.0F / 8.0F;
         var17.b((double)(var22 * 0.2F), (double)(var23 * 0.2F), 0.0).a((double)var24, (double)var19).b(0, 0, 0, 255).d();
         var17.b((double)var22, (double)var23, (double)var15).a((double)var24, (double)var18).b(255, 255, 255, 255).d();
      }

      var16.b();
      GlStateManager.o();
      GlStateManager.j(7424);
      RenderHelper.b();
      GlStateManager.F();
   }

   protected ResourceLocation a(EntityDragon var1) {
      return k;
   }
}
