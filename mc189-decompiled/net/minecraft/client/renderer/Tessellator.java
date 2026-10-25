package net.minecraft.client.renderer;

public class Tessellator {
   private WorldRenderer a;
   private WorldVertexBufferUploader b = new WorldVertexBufferUploader();
   private static final Tessellator c = new Tessellator(2097152);

   public static Tessellator a() {
      return c;
   }

   public Tessellator(int var1) {
      this.a = new WorldRenderer(var1);
   }

   public void b() {
      this.a.e();
      this.b.a(this.a);
   }

   public WorldRenderer c() {
      return this.a;
   }
}
