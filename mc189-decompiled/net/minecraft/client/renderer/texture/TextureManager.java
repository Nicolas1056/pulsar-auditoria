package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureManager implements ITickable, IResourceManagerReloadListener {
   private static final Logger a = LogManager.getLogger();
   private final Map<ResourceLocation, ITextureObject> b = Maps.newHashMap();
   private final List<ITickable> c = Lists.newArrayList();
   private final Map<String, Integer> d = Maps.newHashMap();
   private IResourceManager e;

   public TextureManager(IResourceManager var1) {
      this.e = var1;
   }

   public void a(ResourceLocation var1) {
      Object var2 = this.b.get(var1);
      if (var2 == null) {
         var2 = new SimpleTexture(var1);
         this.a(var1, (ITextureObject)var2);
      }

      TextureUtil.b(((ITextureObject)var2).b());
   }

   public boolean a(ResourceLocation var1, ITickableTextureObject var2) {
      if (this.a(var1, (ITextureObject)var2)) {
         this.c.add(var2);
         return true;
      } else {
         return false;
      }
   }

   public boolean a(ResourceLocation var1, final ITextureObject var2) {
      boolean var3 = true;

      try {
         ((ITextureObject)var2).a(this.e);
      } catch (IOException var8) {
         a.warn("Failed to load texture: " + var1, var8);
         var2 = TextureUtil.a;
         this.b.put(var1, (ITextureObject)var2);
         var3 = false;
      } catch (Throwable var9) {
         CrashReport var5 = CrashReport.a(var9, "Registering texture");
         CrashReportCategory var6 = var5.a("Resource location being registered");
         var6.a("Resource location", var1);
         var6.a("Texture object class", new Callable<String>() {
            public String a() throws Exception {
               return var2.getClass().getName();
            }
         });
         throw new ReportedException(var5);
      }

      this.b.put(var1, (ITextureObject)var2);
      return var3;
   }

   public ITextureObject b(ResourceLocation var1) {
      return this.b.get(var1);
   }

   public ResourceLocation a(String var1, DynamicTexture var2) {
      Integer var3 = this.d.get(var1);
      if (var3 == null) {
         var3 = 1;
      } else {
         var3 = var3 + 1;
      }

      this.d.put(var1, var3);
      ResourceLocation var4 = new ResourceLocation(String.format("dynamic/%s_%d", var1, var3));
      this.a(var4, var2);
      return var4;
   }

   @Override
   public void e() {
      for (ITickable var2 : this.c) {
         var2.e();
      }
   }

   public void c(ResourceLocation var1) {
      ITextureObject var2 = this.b(var1);
      if (var2 != null) {
         TextureUtil.a(var2.b());
      }
   }

   @Override
   public void a(IResourceManager var1) {
      for (Entry var3 : this.b.entrySet()) {
         this.a((ResourceLocation)var3.getKey(), (ITextureObject)var3.getValue());
      }
   }
}
