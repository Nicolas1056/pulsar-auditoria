package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.EnumWorldBlockLayer;
import org.lwjgl.opengl.GL11;

public class VboRenderList extends ChunkRenderContainer {
   @Override
   public void a(EnumWorldBlockLayer var1) {
      if (this.b) {
         for (RenderChunk var3 : this.a) {
            VertexBuffer var4 = var3.b(var1.ordinal());
            GlStateManager.E();
            this.a(var3);
            var3.f();
            var4.a();
            this.a();
            var4.a(7);
            GlStateManager.F();
         }

         OpenGlHelper.g(OpenGlHelper.R, 0);
         GlStateManager.G();
         this.a.clear();
      }
   }

   private void a() {
      GL11.glVertexPointer(3, 5126, 28, 0L);
      GL11.glColorPointer(4, 5121, 28, 12L);
      GL11.glTexCoordPointer(2, 5126, 28, 16L);
      OpenGlHelper.l(OpenGlHelper.r);
      GL11.glTexCoordPointer(2, 5122, 28, 24L);
      OpenGlHelper.l(OpenGlHelper.q);
   }
}
