package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelIronGolem;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderIronGolem;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.init.Blocks;

public class LayerIronGolemFlower implements LayerRenderer<EntityIronGolem> {
   private final RenderIronGolem a;

   public LayerIronGolemFlower(RenderIronGolem var1) {
      this.a = var1;
   }

   public void a(EntityIronGolem var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (var1.cm() != 0) {
         BlockRendererDispatcher var9 = Minecraft.A().ae();
         GlStateManager.B();
         GlStateManager.E();
         GlStateManager.b(5.0F + 180.0F * ((ModelIronGolem)this.a.b()).c.f / (float) Math.PI, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(90.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(-0.9375F, -0.625F, -0.9375F);
         float var10 = 0.5F;
         GlStateManager.a(var10, -var10, var10);
         int var11 = var1.b(var4);
         int var12 = var11 % 65536;
         int var13 = var11 / 65536;
         OpenGlHelper.a(OpenGlHelper.r, var12 / 1.0F, var13 / 1.0F);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.a.a(TextureMap.g);
         var9.a(Blocks.O.Q(), 1.0F);
         GlStateManager.F();
         GlStateManager.C();
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
