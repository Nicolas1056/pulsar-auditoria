package net.minecraft.client.renderer;

import net.minecraft.client.renderer.vertex.VertexBuffer;

public class VertexBufferUploader extends WorldVertexBufferUploader {
   private VertexBuffer a = null;

   @Override
   public void a(WorldRenderer var1) {
      var1.b();
      this.a.a(var1.f());
   }

   public void a(VertexBuffer var1) {
      this.a = var1;
   }
}
