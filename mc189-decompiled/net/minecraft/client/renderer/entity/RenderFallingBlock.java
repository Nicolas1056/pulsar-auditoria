package net.minecraft.client.renderer.entity;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderFallingBlock extends Render<EntityFallingBlock> {
   public RenderFallingBlock(RenderManager var1) {
      super(var1);
      this.c = 0.5F;
   }

   public void a(EntityFallingBlock var1, double var2, double var4, double var6, float var8, float var9) {
      if (var1.l() != null) {
         this.a(TextureMap.g);
         IBlockState var10 = var1.l();
         Block var11 = var10.c();
         BlockPos var12 = new BlockPos(var1);
         World var13 = var1.j();
         if (var10 != var13.p(var12) && var11.b() != -1) {
            if (var11.b() == 3) {
               GlStateManager.E();
               GlStateManager.b((float)var2, (float)var4, (float)var6);
               GlStateManager.f();
               Tessellator var14 = Tessellator.a();
               WorldRenderer var15 = var14.c();
               var15.a(7, DefaultVertexFormats.a);
               int var16 = var12.n();
               int var17 = var12.o();
               int var18 = var12.p();
               var15.c((double)(-var16 - 0.5F), (double)(-var17), (double)(-var18 - 0.5F));
               BlockRendererDispatcher var19 = Minecraft.A().ae();
               IBakedModel var20 = var19.a(var10, var13, null);
               var19.b().a(var13, var20, var10, var12, var15, false);
               var15.c(0.0, 0.0, 0.0);
               var14.b();
               GlStateManager.e();
               GlStateManager.F();
               super.a(var1, var2, var4, var6, var8, var9);
            }
         }
      }
   }

   protected ResourceLocation a(EntityFallingBlock var1) {
      return TextureMap.g;
   }
}
