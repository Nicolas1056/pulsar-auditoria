package net.minecraft.client.renderer.texture;

import org.lwjgl.opengl.GL11;

public abstract class AbstractTexture implements ITextureObject {
   protected int a = -1;
   protected boolean b;
   protected boolean c;
   protected boolean d;
   protected boolean e;

   public void a(boolean var1, boolean var2) {
      this.b = var1;
      this.c = var2;
      int var3 = -1;
      short var4 = -1;
      if (var1) {
         var3 = var2 ? 9987 : 9729;
         var4 = 9729;
      } else {
         var3 = var2 ? 9986 : 9728;
         var4 = 9728;
      }

      GL11.glTexParameteri(3553, 10241, var3);
      GL11.glTexParameteri(3553, 10240, var4);
   }

   @Override
   public void b(boolean var1, boolean var2) {
      this.d = this.b;
      this.e = this.c;
      this.a(var1, var2);
   }

   @Override
   public void a() {
      this.a(this.d, this.e);
   }

   @Override
   public int b() {
      if (this.a == -1) {
         this.a = TextureUtil.a();
      }

      return this.a;
   }

   public void c() {
      if (this.a != -1) {
         TextureUtil.a(this.a);
         this.a = -1;
      }
   }
}
