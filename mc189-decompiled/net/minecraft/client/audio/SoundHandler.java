package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SoundHandler implements IResourceManagerReloadListener, ITickable {
   private static final Logger b = LogManager.getLogger();
   private static final Gson c = new GsonBuilder().registerTypeAdapter(SoundList.class, new SoundListSerializer()).create();
   private static final ParameterizedType d = new ParameterizedType() {
      @Override
      public Type[] getActualTypeArguments() {
         return new Type[]{String.class, SoundList.class};
      }

      @Override
      public Type getRawType() {
         return Map.class;
      }

      @Override
      public Type getOwnerType() {
         return null;
      }
   };
   public static final SoundPoolEntry a = new SoundPoolEntry(new ResourceLocation("meta:missing_sound"), 0.0, 0.0, false);
   private final SoundRegistry e = new SoundRegistry();
   private final SoundManager f;
   private final IResourceManager g;

   public SoundHandler(IResourceManager var1, GameSettings var2) {
      this.g = var1;
      this.f = new SoundManager(this, var2);
   }

   @Override
   public void a(IResourceManager var1) {
      this.f.a();
      this.e.a();

      for (String var3 : var1.a()) {
         try {
            for (IResource var6 : var1.b(new ResourceLocation(var3, "sounds.json"))) {
               try {
                  Map var7 = this.a(var6.b());

                  for (Entry var9 : var7.entrySet()) {
                     this.a(new ResourceLocation(var3, (String)var9.getKey()), (SoundList)var9.getValue());
                  }
               } catch (RuntimeException var10) {
                  b.warn("Invalid sounds.json", var10);
               }
            }
         } catch (IOException var11) {
         }
      }
   }

   protected Map<String, SoundList> a(InputStream var1) {
      Map var2;
      try {
         var2 = (Map)c.fromJson(new InputStreamReader(var1), d);
      } finally {
         IOUtils.closeQuietly(var1);
      }

      return var2;
   }

   private void a(ResourceLocation var1, SoundList var2) {
      boolean var4 = !this.e.d(var1);
      SoundEventAccessorComposite var3;
      if (!var4 && !var2.b()) {
         var3 = this.e.a(var1);
      } else {
         if (!var4) {
            b.debug("Replaced sound event location {}", new Object[]{var1});
         }

         var3 = new SoundEventAccessorComposite(var1, 1.0, 1.0, var2.c());
         this.e.a(var3);
      }

      for (final SoundList.SoundEntry var6 : var2.a()) {
         String var7 = var6.a();
         ResourceLocation var8 = new ResourceLocation(var7);
         final String var9 = var7.contains(":") ? var8.b() : var1.b();
         Object var10;
         switch (var6.e()) {
            case a:
               ResourceLocation var11 = new ResourceLocation(var9, "sounds/" + var8.a() + ".ogg");
               InputStream var12 = null;

               try {
                  var12 = this.g.a(var11).b();
               } catch (FileNotFoundException var18) {
                  b.warn("File {} does not exist, cannot add it to event {}", new Object[]{var11, var1});
                  continue;
               } catch (IOException var19) {
                  b.warn("Could not load sound file " + var11 + ", cannot add it to event " + var1, var19);
                  continue;
               } finally {
                  IOUtils.closeQuietly(var12);
               }

               var10 = new SoundEventAccessor(new SoundPoolEntry(var11, var6.c(), var6.b(), var6.f()), var6.d());
               break;
            case b:
               var10 = new ISoundEventAccessor<SoundPoolEntry>() {
                  final ResourceLocation a = new ResourceLocation(var9, var6.a());

                  @Override
                  public int a() {
                     SoundEventAccessorComposite var1x = SoundHandler.this.e.a(this.a);
                     return var1x == null ? 0 : var1x.a();
                  }

                  public SoundPoolEntry b() {
                     SoundEventAccessorComposite var1x = SoundHandler.this.e.a(this.a);
                     return var1x == null ? SoundHandler.a : var1x.b();
                  }
               };
               break;
            default:
               throw new IllegalStateException("IN YOU FACE");
         }

         var3.a((ISoundEventAccessor<SoundPoolEntry>)var10);
      }
   }

   public SoundEventAccessorComposite a(ResourceLocation var1) {
      return this.e.a(var1);
   }

   public void a(ISound var1) {
      this.f.c(var1);
   }

   public void a(ISound var1, int var2) {
      this.f.a(var1, var2);
   }

   public void a(EntityPlayer var1, float var2) {
      this.f.a(var1, var2);
   }

   public void a() {
      this.f.e();
   }

   public void b() {
      this.f.c();
   }

   public void d() {
      this.f.b();
   }

   @Override
   public void c() {
      this.f.d();
   }

   public void e() {
      this.f.f();
   }

   public void a(SoundCategory var1, float var2) {
      if (var1 == SoundCategory.a && var2 <= 0.0F) {
         this.b();
      }

      this.f.a(var1, var2);
   }

   public void b(ISound var1) {
      this.f.b(var1);
   }

   public SoundEventAccessorComposite a(SoundCategory... var1) {
      ArrayList var2 = Lists.newArrayList();

      for (ResourceLocation var4 : this.e.c()) {
         SoundEventAccessorComposite var5 = this.e.a(var4);
         if (ArrayUtils.contains(var1, var5.d())) {
            var2.add(var5);
         }
      }

      return var2.isEmpty() ? null : (SoundEventAccessorComposite)var2.get(new Random().nextInt(var2.size()));
   }

   public boolean c(ISound var1) {
      return this.f.a(var1);
   }
}
