package net.minecraft.client.resources;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleReloadableResourceManager implements IReloadableResourceManager {
   private static final Logger a = LogManager.getLogger();
   private static final Joiner b = Joiner.on(", ");
   private final Map<String, FallbackResourceManager> c = Maps.newHashMap();
   private final List<IResourceManagerReloadListener> d = Lists.newArrayList();
   private final Set<String> e = Sets.newLinkedHashSet();
   private final IMetadataSerializer f;

   public SimpleReloadableResourceManager(IMetadataSerializer var1) {
      this.f = var1;
   }

   public void a(IResourcePack var1) {
      for (String var3 : var1.c()) {
         this.e.add(var3);
         FallbackResourceManager var4 = this.c.get(var3);
         if (var4 == null) {
            var4 = new FallbackResourceManager(this.f);
            this.c.put(var3, var4);
         }

         var4.a(var1);
      }
   }

   @Override
   public Set<String> a() {
      return this.e;
   }

   @Override
   public IResource a(ResourceLocation var1) throws IOException {
      IResourceManager var2 = this.c.get(var1.b());
      if (var2 != null) {
         return var2.a(var1);
      } else {
         throw new FileNotFoundException(var1.toString());
      }
   }

   @Override
   public List<IResource> b(ResourceLocation var1) throws IOException {
      IResourceManager var2 = this.c.get(var1.b());
      if (var2 != null) {
         return var2.b(var1);
      } else {
         throw new FileNotFoundException(var1.toString());
      }
   }

   private void b() {
      this.c.clear();
      this.e.clear();
   }

   @Override
   public void a(List<IResourcePack> var1) {
      this.b();
      a.info("Reloading ResourceManager: " + b.join(Iterables.transform(var1, new Function<IResourcePack, String>() {
         public String a(IResourcePack var1) {
            return var1.b();
         }
      })));

      for (IResourcePack var3 : var1) {
         this.a(var3);
      }

      this.c();
   }

   @Override
   public void a(IResourceManagerReloadListener var1) {
      this.d.add(var1);
      var1.a(this);
   }

   private void c() {
      for (IResourceManagerReloadListener var2 : this.d) {
         var2.a(this);
      }
   }
}
