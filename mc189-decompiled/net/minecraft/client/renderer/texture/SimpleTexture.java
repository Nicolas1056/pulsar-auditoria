package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture extends AbstractTexture {
   private static final Logger g = LogManager.getLogger();
   protected final ResourceLocation f;

   public SimpleTexture(ResourceLocation var1) {
      this.f = var1;
   }

   @Override
   public void a(IResourceManager var1) throws IOException {
      this.c();
      InputStream var2 = null;

      try {
         IResource var3 = var1.a(this.f);
         var2 = var3.b();
         BufferedImage var4 = TextureUtil.a(var2);
         boolean var5 = false;
         boolean var6 = false;
         if (var3.c()) {
            try {
               TextureMetadataSection var7 = var3.a("texture");
               if (var7 != null) {
                  var5 = var7.a();
                  var6 = var7.b();
               }
            } catch (RuntimeException var11) {
               g.warn("Failed reading metadata of: " + this.f, var11);
            }
         }

         TextureUtil.a(this.b(), var4, var5, var6);
      } finally {
         if (var2 != null) {
            var2.close();
         }
      }
   }
}
