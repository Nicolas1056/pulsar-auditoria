package net.minecraft.client.renderer.entity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.item.EntityMinecartTNT;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;

public class RenderTntMinecart extends RenderMinecart<EntityMinecartTNT> {
   public RenderTntMinecart(RenderManager var1) {
      super(var1);
   }

   protected void a(EntityMinecartTNT var1, float var2, IBlockState var3) {
      int var4 = var1.l();
      if (var4 > -1 && var4 - var2 + 1.0F < 10.0F) {
         float var5 = 1.0F - (var4 - var2 + 1.0F) / 10.0F;
         var5 = MathHelper.a(var5, 0.0F, 1.0F);
         var5 *= var5;
         var5 *= var5;
         float var6 = 1.0F + var5 * 0.3F;
         GlStateManager.a(var6, var6, var6);
      }

      super.a(var1, var2, var3);
      if (var4 > -1 && var4 / 5 % 2 == 0) {
         BlockRendererDispatcher var10 = Minecraft.A().ae();
         GlStateManager.x();
         GlStateManager.f();
         GlStateManager.l();
         GlStateManager.b(770, 772);
         GlStateManager.c(1.0F, 1.0F, 1.0F, (1.0F - (var4 - var2 + 1.0F) / 100.0F) * 0.8F);
         GlStateManager.E();
         var10.a(Blocks.W.Q(), 1.0F);
         GlStateManager.F();
         GlStateManager.c(1.0F, 1.0F, 1.0F, 1.0F);
         GlStateManager.k();
         GlStateManager.e();
         GlStateManager.w();
      }
   }
}
