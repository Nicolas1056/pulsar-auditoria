package net.minecraft.client.resources;

import com.google.common.base.Charsets;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractResourcePack implements IResourcePack {
   private static final Logger b = LogManager.getLogger();
   protected final File a;

   public AbstractResourcePack(File var1) {
      this.a = var1;
   }

   private static String c(ResourceLocation var0) {
      return String.format("%s/%s/%s", "assets", var0.b(), var0.a());
   }

   protected static String a(File var0, File var1) {
      return var0.toURI().relativize(var1.toURI()).getPath();
   }

   @Override
   public InputStream a(ResourceLocation var1) throws IOException {
      return this.a(c(var1));
   }

   @Override
   public boolean b(ResourceLocation var1) {
      return this.b(c(var1));
   }

   protected abstract InputStream a(String var1) throws IOException;

   protected abstract boolean b(String var1);

   protected void c(String var1) {
      b.warn("ResourcePack: ignored non-lowercase namespace: %s in %s", new Object[]{var1, this.a});
   }

   @Override
   public <T extends IMetadataSection> T a(IMetadataSerializer var1, String var2) throws IOException {
      return a(var1, this.a("pack.mcmeta"), var2);
   }

   static <T extends IMetadataSection> T a(IMetadataSerializer var0, InputStream var1, String var2) {
      Object var3 = null;
      BufferedReader var4 = null;

      try {
         var4 = new BufferedReader(new InputStreamReader(var1, Charsets.UTF_8));
         var3 = new JsonParser().parse(var4).getAsJsonObject();
      } catch (RuntimeException var9) {
         throw new JsonParseException(var9);
      } finally {
         IOUtils.closeQuietly(var4);
      }

      return var0.a(var2, (JsonObject)var3);
   }

   @Override
   public BufferedImage a() throws IOException {
      return TextureUtil.a(this.a("pack.png"));
   }

   @Override
   public String b() {
      return this.a.getName();
   }
}
