package net.minecraft.client.resources;

import java.io.InputStream;
import net.minecraft.client.resources.data.IMetadataSection;
import net.minecraft.util.ResourceLocation;

public interface IResource {
   ResourceLocation a();

   InputStream b();

   boolean c();

   <T extends IMetadataSection> T a(String var1);

   String d();
}
