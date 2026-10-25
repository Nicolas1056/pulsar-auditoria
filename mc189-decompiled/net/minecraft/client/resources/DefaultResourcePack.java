package net.minecraft.client.resources;

import com.google.common.collect.ImmutableSet;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public class DefaultResourcePack implements IResourcePack {
   public static final Set<String> a = ImmutableSet.of("minecraft", "realms");
   private final Map<String, File> b;

   public DefaultResourcePack(Map<String, File> var1) {
      this.b = var1;
   }

   @Override
   public InputStream a(ResourceLocation var1) throws IOException {
      InputStream var2 = this.d(var1);
      if (var2 != null) {
         return var2;
      } else {
         InputStream var3 = this.c(var1);
         if (var3 != null) {
            return var3;
         } else {
            throw new FileNotFoundException(var1.a());
         }
      }
   }

   public InputStream c(ResourceLocation var1) throws FileNotFoundException {
      File var2 = this.b.get(var1.toString());
      return var2 != null && var2.isFile() ? new FileInputStream(var2) : null;
   }

   private InputStream d(ResourceLocation var1) {
      return DefaultResourcePack.class.getResourceAsStream("/assets/" + var1.b() + "/" + var1.a());
   }

   @Override
   public boolean b(ResourceLocation var1) {
      return this.d(var1) != null || this.b.containsKey(var1.toString());
   }

   @Override
   public Set<String> c() {
      return a;
   }

   @Override
   public <T extends IMetadataSection> T a(IMetadataSerializer var1, String var2) throws IOException {
      try {
         FileInputStream var3 = new FileInputStream(this.b.get("pack.mcmeta"));
         return AbstractResourcePack.a(var1, var3, var2);
      } catch (RuntimeException var4) {
         return null;
      } catch (FileNotFoundException var5) {
         return null;
      }
   }

   @Override
   public BufferedImage a() throws IOException {
      return TextureUtil.a(DefaultResourcePack.class.getResourceAsStream("/" + new ResourceLocation("pack.png").a()));
   }

   @Override
   public String b() {
      return "Default";
   }
}
