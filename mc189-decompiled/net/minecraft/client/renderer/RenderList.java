package net.minecraft.client.renderer;

import net.minecraft.client.renderer.chunk.ListedRenderChunk;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.EnumWorldBlockLayer;
import org.lwjgl.opengl.GL11;

public class RenderList extends ChunkRenderContainer {
   @Override
   public void a(EnumWorldBlockLayer var1) {
      if (this.b) {
         for (RenderChunk var3 : this.a) {
            ListedRenderChunk var4 = (ListedRenderChunk)var3;
            GlStateManager.E();
            this.a(var3);
            GL11.glCallList(var4.a(var1, var4.g()));
            GlStateManager.F();
         }

         GlStateManager.G();
         this.a.clear();
      }
   }
}
