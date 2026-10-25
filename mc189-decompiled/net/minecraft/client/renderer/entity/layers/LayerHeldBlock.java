package net.minecraft.client.renderer.entity.layers;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.RenderEnderman;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.monster.EntityEnderman;

public class LayerHeldBlock implements LayerRenderer<EntityEnderman> {
   private final RenderEnderman a;

   public LayerHeldBlock(RenderEnderman var1) {
      this.a = var1;
   }

   public void a(EntityEnderman var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      IBlockState var9 = var1.cm();
      if (var9.c().t() != Material.a) {
         BlockRendererDispatcher var10 = Minecraft.A().ae();
         GlStateManager.B();
         GlStateManager.E();
         GlStateManager.b(0.0F, 0.6875F, -0.75F);
         GlStateManager.b(20.0F, 1.0F, 0.0F, 0.0F);
         GlStateManager.b(45.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(0.25F, 0.1875F, 0.25F);
         float var11 = 0.5F;
         GlStateManager.a(-var11, -var11, var11);
         int var12 = var1.b(var4);
         int var13 = var12 % 65536;
         int var14 = var12 / 65536;
         OpenGlHelper.a(OpenGlHelper.r, var13 / 1.0F, var14 / 1.0F);
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         this.a.a(TextureMap.g);
         var10.a(var9, 1.0F);
         GlStateManager.F();
         GlStateManager.C();
      }
   }

   @Override
   public boolean b() {
      return false;
   }
}
