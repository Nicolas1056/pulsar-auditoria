package net.minecraft.client.resources;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import net.minecraft.util.ResourceLocation;

public interface IResourceManager {
   Set<String> a();

   IResource a(ResourceLocation var1) throws IOException;

   List<IResource> b(ResourceLocation var1) throws IOException;
}
