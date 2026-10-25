package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;

public class RenderFireball extends Render<EntityFireball> {
   private float a;

   public RenderFireball(RenderManager var1, float var2) {
      super(var1);
      this.a = var2;
   }

   public void a(EntityFireball var1, double var2, double var4, double var6, float var8, float var9) {
      GlStateManager.E();
      this.c(var1);
      GlStateManager.b((float)var2, (float)var4, (float)var6);
      GlStateManager.B();
      GlStateManager.a(this.a, this.a, this.a);
      TextureAtlasSprite var10 = Minecraft.A().ag().a().a(Items.bL);
      Tessellator var11 = Tessellator.a();
      WorldRenderer var12 = var11.c();
      float var13 = var10.e();
      float var14 = var10.f();
      float var15 = var10.g();
      float var16 = var10.h();
      float var17 = 1.0F;
      float var18 = 0.5F;
      float var19 = 0.25F;
      GlStateManager.b(180.0F - this.b.e, 0.0F, 1.0F, 0.0F);
      GlStateManager.b(-this.b.f, 1.0F, 0.0F, 0.0F);
      var12.a(7, DefaultVertexFormats.j);
      var12.b(-0.5, -0.25, 0.0).a((double)var13, (double)var16).c(0.0F, 1.0F, 0.0F).d();
      var12.b(0.5, -0.25, 0.0).a((double)var14, (double)var16).c(0.0F, 1.0F, 0.0F).d();
      var12.b(0.5, 0.75, 0.0).a((double)var14, (double)var15).c(0.0F, 1.0F, 0.0F).d();
      var12.b(-0.5, 0.75, 0.0).a((double)var13, (double)var15).c(0.0F, 1.0F, 0.0F).d();
      var11.b();
      GlStateManager.C();
      GlStateManager.F();
      super.a(var1, var2, var4, var6, var8, var9);
   }

   protected ResourceLocation a(EntityFireball var1) {
      return TextureMap.g;
   }
}
