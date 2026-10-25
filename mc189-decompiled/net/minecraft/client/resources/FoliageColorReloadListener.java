package net.minecraft.client.resources;

import java.io.IOException;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.ColorizerFoliage;

public class FoliageColorReloadListener implements IResourceManagerReloadListener {
   private static final ResourceLocation a = new ResourceLocation("textures/colormap/foliage.png");

   @Override
   public void a(IResourceManager var1) {
      try {
         ColorizerFoliage.a(TextureUtil.a(var1, a));
      } catch (IOException var3) {
      }
   }
}
