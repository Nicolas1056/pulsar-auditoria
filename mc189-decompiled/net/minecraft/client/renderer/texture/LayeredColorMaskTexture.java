package net.minecraft.client.renderer.texture;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LayeredColorMaskTexture extends AbstractTexture {
   private static final Logger f = LogManager.getLogger();
   private final ResourceLocation g;
   private final List<String> h;
   private final List<EnumDyeColor> i;

   public LayeredColorMaskTexture(ResourceLocation var1, List<String> var2, List<EnumDyeColor> var3) {
      this.g = var1;
      this.h = var2;
      this.i = var3;
   }

   @Override
   public void a(IResourceManager var1) throws IOException {
      this.c();

      BufferedImage var2;
      try {
         BufferedImage var3 = TextureUtil.a(var1.a(this.g).b());
         int var4 = var3.getType();
         if (var4 == 0) {
            var4 = 6;
         }

         var2 = new BufferedImage(var3.getWidth(), var3.getHeight(), var4);
         Graphics var5 = var2.getGraphics();
         var5.drawImage(var3, 0, 0, null);

         for (int var6 = 0; var6 < 17 && var6 < this.h.size() && var6 < this.i.size(); var6++) {
            String var7 = this.h.get(var6);
            MapColor var8 = this.i.get(var6).e();
            if (var7 != null) {
               InputStream var9 = var1.a(new ResourceLocation(var7)).b();
               BufferedImage var10 = TextureUtil.a(var9);
               if (var10.getWidth() == var2.getWidth() && var10.getHeight() == var2.getHeight() && var10.getType() == 6) {
                  for (int var11 = 0; var11 < var10.getHeight(); var11++) {
                     for (int var12 = 0; var12 < var10.getWidth(); var12++) {
                        int var13 = var10.getRGB(var12, var11);
                        if ((var13 & 0xFF000000) != 0) {
                           int var14 = (var13 & 0xFF0000) << 8 & 0xFF000000;
                           int var15 = var3.getRGB(var12, var11);
                           int var16 = MathHelper.d(var15, var8.L) & 16777215;
                           var10.setRGB(var12, var11, var14 | var16);
                        }
                     }
                  }

                  var2.getGraphics().drawImage(var10, 0, 0, null);
               }
            }
         }
      } catch (IOException var17) {
         f.error("Couldn't load layered image", var17);
         return;
      }

      TextureUtil.a(this.b(), var2);
   }
}
