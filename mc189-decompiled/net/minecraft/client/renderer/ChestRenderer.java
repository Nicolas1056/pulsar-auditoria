package net.minecraft.client.renderer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;

public class ChestRenderer {
   public void a(Block var1, float var2) {
      GlStateManager.c(var2, var2, var2, 1.0F);
      GlStateManager.b(90.0F, 0.0F, 1.0F, 0.0F);
      TileEntityItemStackRenderer.a.a(new ItemStack(var1));
   }
}
