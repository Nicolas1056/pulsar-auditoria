package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredTexture extends AbstractTexture {
   private static final Logger g = LogManager.getLogger();
   public final List<String> f;

   public LayeredTexture(String... var1) {
      this.f = Lists.newArrayList(var1);
   }

   @Override
   public void a(IResourceManager var1) throws IOException {
      this.c();
      BufferedImage var2 = null;

      try {
         for (String var4 : this.f) {
            if (var4 != null) {
               InputStream var5 = var1.a(new ResourceLocation(var4)).b();
               BufferedImage var6 = TextureUtil.a(var5);
               if (var2 == null) {
                  var2 = new BufferedImage(var6.getWidth(), var6.getHeight(), 2);
               }

               var2.getGraphics().drawImage(var6, 0, 0, null);
            }
         }
      } catch (IOException var7) {
         g.error("Couldn't load layered image", var7);
         return;
      }

      TextureUtil.a(this.b(), var2);
   }
}
