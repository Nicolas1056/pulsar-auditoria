package net.minecraft.client.resources;

import java.util.List;

public interface IReloadableResourceManager extends IResourceManager {
   void a(List<IResourcePack> var1);

   void a(IResourceManagerReloadListener var1);
}
