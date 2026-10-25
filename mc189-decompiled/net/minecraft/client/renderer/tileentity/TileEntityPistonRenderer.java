package net.minecraft.client.renderer.tileentity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityPiston;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class TileEntityPistonRenderer extends TileEntitySpecialRenderer<TileEntityPiston> {
   private final BlockRendererDispatcher c = Minecraft.A().ae();

   public void a(TileEntityPiston var1, double var2, double var4, double var6, float var8, int var9) {
      BlockPos var10 = var1.v();
      IBlockState var11 = var1.b();
      Block var12 = var11.c();
      if (var12.t() != Material.a && !(var1.a(var8) >= 1.0F)) {
         Tessellator var13 = Tessellator.a();
         WorldRenderer var14 = var13.c();
         this.a(TextureMap.g);
         RenderHelper.a();
         GlStateManager.b(770, 771);
         GlStateManager.l();
         GlStateManager.p();
         if (Minecraft.x()) {
            GlStateManager.j(7425);
         } else {
            GlStateManager.j(7424);
         }

         var14.a(7, DefaultVertexFormats.a);
         var14.c(
            (double)((float)var2 - var10.n() + var1.b(var8)),
            (double)((float)var4 - var10.o() + var1.c(var8)),
            (double)((float)var6 - var10.p() + var1.d(var8))
         );
         World var15 = this.b();
         if (var12 == Blocks.K && var1.a(var8) < 0.5F) {
            var11 = var11.a(BlockPistonExtension.N, true);
            this.c.b().a(var15, this.c.a(var11, var15, var10), var11, var10, var14, true);
         } else if (var1.g() && !var1.d()) {
            BlockPistonExtension.EnumPistonType var16 = var12 == Blocks.F ? BlockPistonExtension.EnumPistonType.b : BlockPistonExtension.EnumPistonType.a;
            IBlockState var17 = Blocks.K.Q().a(BlockPistonExtension.b, var16).a(BlockPistonExtension.a, var11.b(BlockPistonBase.a));
            var17 = var17.a(BlockPistonExtension.N, var1.a(var8) >= 0.5F);
            this.c.b().a(var15, this.c.a(var17, var15, var10), var17, var10, var14, true);
            var14.c((double)((float)var2 - var10.n()), (double)((float)var4 - var10.o()), (double)((float)var6 - var10.p()));
            var11.a(BlockPistonBase.b, true);
            this.c.b().a(var15, this.c.a(var11, var15, var10), var11, var10, var14, true);
         } else {
            this.c.b().a(var15, this.c.a(var11, var15, var10), var11, var10, var14, false);
         }

         var14.c(0.0, 0.0, 0.0);
         var13.b();
         RenderHelper.b();
      }
   }
}
