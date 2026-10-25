package net.minecraft.client.resources;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import java.awt.image.BufferedImage;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreenWorking;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.client.resources.data.PackMetadataSection;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.HttpUtil;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourcePackRepository {
   private static final Logger c = LogManager.getLogger();
   private static final FileFilter d = new FileFilter() {
      @Override
      public boolean accept(File var1) {
         boolean var2 = var1.isFile() && var1.getName().endsWith(".zip");
         boolean var3 = var1.isDirectory() && new File(var1, "pack.mcmeta").isFile();
         return var2 || var3;
      }
   };
   private final File e;
   public final IResourcePack a;
   private final File f;
   public final IMetadataSerializer b;
   private IResourcePack g;
   private final ReentrantLock h = new ReentrantLock();
   private ListenableFuture<Object> i;
   private List<ResourcePackRepository.Entry> j = Lists.newArrayList();
   private List<ResourcePackRepository.Entry> k = Lists.newArrayList();

   public ResourcePackRepository(File var1, File var2, IResourcePack var3, IMetadataSerializer var4, GameSettings var5) {
      this.e = var1;
      this.f = var2;
      this.a = var3;
      this.b = var4;
      this.g();
      this.a();
      Iterator var6 = var5.k.iterator();

      while (var6.hasNext()) {
         String var7 = (String)var6.next();

         for (ResourcePackRepository.Entry var9 : this.j) {
            if (var9.d().equals(var7)) {
               if (var9.f() == 1 || var5.l.contains(var9.d())) {
                  this.k.add(var9);
                  break;
               }

               var6.remove();
               c.warn("Removed selected resource pack {} because it's no longer compatible", new Object[]{var9.d()});
            }
         }
      }
   }

   private void g() {
      if (this.e.exists()) {
         if (!this.e.isDirectory() && (!this.e.delete() || !this.e.mkdirs())) {
            c.warn("Unable to recreate resourcepack folder, it exists but is not a directory: " + this.e);
         }
      } else if (!this.e.mkdirs()) {
         c.warn("Unable to create resourcepack folder: " + this.e);
      }
   }

   private List<File> h() {
      return this.e.isDirectory() ? Arrays.asList(this.e.listFiles(d)) : Collections.emptyList();
   }

   public void a() {
      ArrayList var1 = Lists.newArrayList();

      for (File var3 : this.h()) {
         ResourcePackRepository.Entry var4 = new ResourcePackRepository.Entry(var3);
         if (!this.j.contains(var4)) {
            try {
               var4.a();
               var1.add(var4);
            } catch (Exception var6) {
               var1.remove(var4);
            }
         } else {
            int var5 = this.j.indexOf(var4);
            if (var5 > -1 && var5 < this.j.size()) {
               var1.add(this.j.get(var5));
            }
         }
      }

      this.j.removeAll(var1);

      for (ResourcePackRepository.Entry var8 : this.j) {
         var8.b();
      }

      this.j = var1;
   }

   public List<ResourcePackRepository.Entry> b() {
      return ImmutableList.copyOf(this.j);
   }

   public List<ResourcePackRepository.Entry> c() {
      return ImmutableList.copyOf(this.k);
   }

   public void a(List<ResourcePackRepository.Entry> var1) {
      this.k.clear();
      this.k.addAll(var1);
   }

   public File d() {
      return this.e;
   }

   public ListenableFuture<Object> a(String var1, String var2) {
      String var3;
      if (var2.matches("^[a-f0-9]{40}$")) {
         var3 = var2;
      } else {
         var3 = "legacy";
      }

      final File var4 = new File(this.f, var3);
      this.h.lock();

      try {
         this.f();
         if (var4.exists() && var2.length() == 40) {
            try {
               String var5 = Hashing.sha1().hashBytes(Files.toByteArray(var4)).toString();
               if (var5.equals(var2)) {
                  return this.a(var4);
               }

               c.warn("File " + var4 + " had wrong hash (expected " + var2 + ", found " + var5 + "). Deleting it.");
               FileUtils.deleteQuietly(var4);
            } catch (IOException var13) {
               c.warn("File " + var4 + " couldn't be hashed. Deleting it.", var13);
               FileUtils.deleteQuietly(var4);
            }
         }

         this.i();
         final GuiScreenWorking var15 = new GuiScreenWorking();
         Map var6 = Minecraft.ak();
         final Minecraft var7 = Minecraft.A();
         Futures.getUnchecked(var7.a(new Runnable() {
            @Override
            public void run() {
               var7.a(var15);
            }
         }));
         final SettableFuture var8 = SettableFuture.create();
         this.i = HttpUtil.a(var4, var1, var6, 52428800, var15, var7.O());
         Futures.addCallback(this.i, new FutureCallback<Object>() {
            public void onSuccess(Object var1) {
               ResourcePackRepository.this.a(var4);
               var8.set(null);
            }

            public void onFailure(Throwable var1) {
               var8.setException(var1);
            }
         });
         return this.i;
      } finally {
         this.h.unlock();
      }
   }

   private void i() {
      ArrayList var1 = Lists.newArrayList(FileUtils.listFiles(this.f, TrueFileFilter.TRUE, null));
      Collections.sort(var1, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
      int var2 = 0;

      for (File var4 : var1) {
         if (var2++ >= 10) {
            c.info("Deleting old server resource pack " + var4.getName());
            FileUtils.deleteQuietly(var4);
         }
      }
   }

   public ListenableFuture<Object> a(File var1) {
      this.g = new FileResourcePack(var1);
      return Minecraft.A().B();
   }

   public IResourcePack e() {
      return this.g;
   }

   public void f() {
      this.h.lock();

      try {
         if (this.i != null) {
            this.i.cancel(true);
         }

         this.i = null;
         if (this.g != null) {
            this.g = null;
            Minecraft.A().B();
         }
      } finally {
         this.h.unlock();
      }
   }

   public class Entry {
      private final File b;
      private IResourcePack c;
      private PackMetadataSection d;
      private BufferedImage e;
      private ResourceLocation f;

      private Entry(File var2) {
         this.b = var2;
      }

      public void a() throws IOException {
         this.c = (IResourcePack)(this.b.isDirectory() ? new FolderResourcePack(this.b) : new FileResourcePack(this.b));
         this.d = this.c.a(ResourcePackRepository.this.b, "pack");

         try {
            this.e = this.c.a();
         } catch (IOException var2) {
         }

         if (this.e == null) {
            this.e = ResourcePackRepository.this.a.a();
         }

         this.b();
      }

      public void a(TextureManager var1) {
         if (this.f == null) {
            this.f = var1.a("texturepackicon", new DynamicTexture(this.e));
         }

         var1.a(this.f);
      }

      public void b() {
         if (this.c instanceof Closeable) {
            IOUtils.closeQuietly((Closeable)this.c);
         }
      }

      public IResourcePack c() {
         return this.c;
      }

      public String d() {
         return this.c.b();
      }

      public String e() {
         return this.d == null ? EnumChatFormatting.m + "Invalid pack.mcmeta (or missing 'pack' section)" : this.d.a().d();
      }

      public int f() {
         return this.d.b();
      }

      @Override
      public boolean equals(Object var1) {
         if (this == var1) {
            return true;
         } else {
            return var1 instanceof ResourcePackRepository.Entry ? this.toString().equals(var1.toString()) : false;
         }
      }

      @Override
      public int hashCode() {
         return this.toString().hashCode();
      }

      @Override
      public String toString() {
         return String.format("%s:%s:%d", this.b.getName(), this.b.isDirectory() ? "folder" : "zip", this.b.lastModified());
      }
   }
}
