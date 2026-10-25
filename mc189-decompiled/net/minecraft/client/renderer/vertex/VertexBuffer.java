package net.minecraft.client.renderer.vertex;

import java.nio.ByteBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class VertexBuffer {
   private int a;
   private final VertexFormat b;
   private int c;

   public VertexBuffer(VertexFormat var1) {
      this.b = var1;
      this.a = OpenGlHelper.e();
   }

   public void a() {
      OpenGlHelper.g(OpenGlHelper.R, this.a);
   }

   public void a(ByteBuffer var1) {
      this.a();
      OpenGlHelper.a(OpenGlHelper.R, var1, 35044);
      this.b();
      this.c = var1.limit() / this.b.g();
   }

   public void a(int var1) {
      GL11.glDrawArrays(var1, 0, this.c);
   }

   public void b() {
      OpenGlHelper.g(OpenGlHelper.R, 0);
   }

   public void c() {
      if (this.a >= 0) {
         OpenGlHelper.g(this.a);
         this.a = -1;
      }
   }
}
