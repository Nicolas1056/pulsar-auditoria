package net.minecraft.client.resources;

import com.google.common.collect.Lists;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FallbackResourceManager implements IResourceManager {
   private static final Logger b = LogManager.getLogger();
   protected final List<IResourcePack> a = Lists.newArrayList();
   private final IMetadataSerializer c;

   public FallbackResourceManager(IMetadataSerializer var1) {
      this.c = var1;
   }

   public void a(IResourcePack var1) {
      this.a.add(var1);
   }

   @Override
   public Set<String> a() {
      return null;
   }

   @Override
   public IResource a(ResourceLocation var1) throws IOException {
      IResourcePack var2 = null;
      ResourceLocation var3 = c(var1);

      for (int var4 = this.a.size() - 1; var4 >= 0; var4--) {
         IResourcePack var5 = this.a.get(var4);
         if (var2 == null && var5.b(var3)) {
            var2 = var5;
         }

         if (var5.b(var1)) {
            InputStream var6 = null;
            if (var2 != null) {
               var6 = this.a(var3, var2);
            }

            return new SimpleResource(var5.b(), var1, this.a(var1, var5), var6, this.c);
         }
      }

      throw new FileNotFoundException(var1.toString());
   }

   protected InputStream a(ResourceLocation var1, IResourcePack var2) throws IOException {
      InputStream var3 = var2.a(var1);
      return (InputStream)(b.isDebugEnabled() ? new FallbackResourceManager.InputStreamLeakedResourceLogger(var3, var1, var2.b()) : var3);
   }

   @Override
   public List<IResource> b(ResourceLocation var1) throws IOException {
      ArrayList var2 = Lists.newArrayList();
      ResourceLocation var3 = c(var1);

      for (IResourcePack var5 : this.a) {
         if (var5.b(var1)) {
            InputStream var6 = var5.b(var3) ? this.a(var3, var5) : null;
            var2.add(new SimpleResource(var5.b(), var1, this.a(var1, var5), var6, this.c));
         }
      }

      if (var2.isEmpty()) {
         throw new FileNotFoundException(var1.toString());
      } else {
         return var2;
      }
   }

   static ResourceLocation c(ResourceLocation var0) {
      return new ResourceLocation(var0.b(), var0.a() + ".mcmeta");
   }

   static class InputStreamLeakedResourceLogger extends InputStream {
      private final InputStream a;
      private final String b;
      private boolean c = false;

      public InputStreamLeakedResourceLogger(InputStream var1, ResourceLocation var2, String var3) {
         this.a = var1;
         ByteArrayOutputStream var4 = new ByteArrayOutputStream();
         new Exception().printStackTrace(new PrintStream(var4));
         this.b = "Leaked resource: '" + var2 + "' loaded from pack: '" + var3 + "'\n" + var4.toString();
      }

      @Override
      public void close() throws IOException {
         this.a.close();
         this.c = true;
      }

      @Override
      protected void finalize() throws Throwable {
         if (!this.c) {
            FallbackResourceManager.b.warn(this.b);
         }

         super.finalize();
      }

      @Override
      public int read() throws IOException {
         return this.a.read();
      }
   }
}
