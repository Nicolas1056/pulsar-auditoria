package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderMooshroom;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;

public class LayerMooshroomMushroom implements LayerRenderer<EntityMooshroom> {
   private final RenderMooshroom a;

   public LayerMooshroomMushroom(RenderMooshroom var1) {
      this.a = var1;
   }

   public void a(EntityMooshroom var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!var1.j_() && !var1.ax()) {
         BlockRendererDispatcher var9 = Minecraft.A().ae();
         this.a.a(TextureMap.g);
         GlStateManager.o();
         GlStateManager.e(1028);
         GlStateManager.E();
         GlStateManager.a(1.0F, -1.0F, 1.0F);
         GlStateManager.b(0.2F, 0.35F, 0.5F);
         GlStateManager.b(42.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.E();
         GlStateManager.b(-0.5F, -0.5F, 0.5F);
         var9.a(Blocks.Q.Q(), 1.0F);
         GlStateManager.F();
         GlStateManager.E();
         GlStateManager.b(0.1F, 0.0F, -0.6F);
         GlStateManager.b(42.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(-0.5F, -0.5F, 0.5F);
         var9.a(Blocks.Q.Q(), 1.0F);
         GlStateManager.F();
         GlStateManager.F();
         GlStateManager.E();
         ((ModelQuadruped)this.a.b()).a.c(0.0625F);
         GlStateManager.a(1.0F, -1.0F, 1.0F);
         GlStateManager.b(0.0F, 0.7F, -0.2F);
         GlStateManager.b(12.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.b(-0.5F, -0.5F, 0.5F);
         var9.a(Blocks.Q.Q(), 1.0F);
         GlStateManager.F();
         GlStateManager.e(1029);
         GlStateManager.p();
      }
   }

   @Override
   public boolean b() {
      return true;
   }
}
