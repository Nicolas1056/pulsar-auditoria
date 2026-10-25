package net.minecraft.client.resources;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;

public interface IResourcePack {
   InputStream a(ResourceLocation var1) throws IOException;

   boolean b(ResourceLocation var1);

   Set<String> c();

   <T extends IMetadataSection> T a(IMetadataSerializer var1, String var2) throws IOException;

   BufferedImage a() throws IOException;

   String b();
}
