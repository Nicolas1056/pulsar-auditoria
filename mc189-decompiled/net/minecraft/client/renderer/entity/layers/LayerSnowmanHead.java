package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderSnowMan;
import net.minecraft.entity.monster.EntitySnowman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

public class LayerSnowmanHead implements LayerRenderer<EntitySnowman> {
   private final RenderSnowMan a;

   public LayerSnowmanHead(RenderSnowMan var1) {
      this.a = var1;
   }

   public void a(EntitySnowman var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      if (!var1.ax()) {
         GlStateManager.E();
         this.a.g().c.c(0.0625F);
         float var9 = 0.625F;
         GlStateManager.b(0.0F, -0.34375F, 0.0F);
         GlStateManager.b(180.0F, 0.0F, 1.0F, 0.0F);
         GlStateManager.a(var9, -var9, -var9);
         Minecraft.A().ah().a(var1, new ItemStack(Blocks.aU, 1), ItemCameraTransforms.TransformType.d);
         GlStateManager.F();
      }
   }

   @Override
   public boolean b() {
      return true;
   }
}
