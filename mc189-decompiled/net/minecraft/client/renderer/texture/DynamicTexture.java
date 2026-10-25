package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;

public class DynamicTexture extends AbstractTexture {
   private final int[] f;
   private final int g;
   private final int h;

   public DynamicTexture(BufferedImage var1) {
      this(var1.getWidth(), var1.getHeight());
      var1.getRGB(0, 0, var1.getWidth(), var1.getHeight(), this.f, 0, var1.getWidth());
      this.d();
   }

   public DynamicTexture(int var1, int var2) {
      this.g = var1;
      this.h = var2;
      this.f = new int[var1 * var2];
      TextureUtil.a(this.b(), var1, var2);
   }

   @Override
   public void a(IResourceManager var1) throws IOException {
   }

   public void d() {
      TextureUtil.a(this.b(), this.f, this.g, this.h);
   }

   public int[] e() {
      return this.f;
   }
}
