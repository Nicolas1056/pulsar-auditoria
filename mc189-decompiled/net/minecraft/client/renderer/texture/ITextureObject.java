package net.minecraft.client.renderer.texture;

import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;

public interface ITextureObject {
   void b(boolean var1, boolean var2);

   void a();

   void a(IResourceManager var1) throws IOException;

   int b();
}
