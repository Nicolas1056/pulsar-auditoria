package net.minecraft.client.resources;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Maps;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.InsecureTextureException;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IImageBuffer;
import net.minecraft.client.renderer.ImageBufferDownload;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public class SkinManager {
   private static final ExecutorService a = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
   private final TextureManager b;
   private final File c;
   private final MinecraftSessionService d;
   private final LoadingCache<GameProfile, Map<Type, MinecraftProfileTexture>> e;

   public SkinManager(TextureManager var1, File var2, MinecraftSessionService var3) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.e = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader<GameProfile, Map<Type, MinecraftProfileTexture>>() {
         public Map<Type, MinecraftProfileTexture> a(GameProfile var1) throws Exception {
            return Minecraft.A().aa().getTextures(var1, false);
         }
      });
   }

   public ResourceLocation a(MinecraftProfileTexture var1, Type var2) {
      return this.a(var1, var2, null);
   }

   public ResourceLocation a(final MinecraftProfileTexture var1, final Type var2, final SkinManager.SkinAvailableCallback var3) {
      final ResourceLocation var4 = new ResourceLocation("skins/" + var1.getHash());
      ITextureObject var5 = this.b.b(var4);
      if (var5 != null) {
         if (var3 != null) {
            var3.a(var2, var4, var1);
         }
      } else {
         File var6 = new File(this.c, var1.getHash().length() > 2 ? var1.getHash().substring(0, 2) : "xx");
         File var7 = new File(var6, var1.getHash());
         final ImageBufferDownload var8 = var2 == Type.SKIN ? new ImageBufferDownload() : null;
         ThreadDownloadImageData var9 = new ThreadDownloadImageData(var7, var1.getUrl(), DefaultPlayerSkin.a(), new IImageBuffer() {
            @Override
            public BufferedImage a(BufferedImage var1x) {
               if (var8 != null) {
                  var1x = var8.a(var1x);
               }

               return var1x;
            }

            @Override
            public void a() {
               if (var8 != null) {
                  var8.a();
               }

               if (var3 != null) {
                  var3.a(var2, var4, var1);
               }
            }
         });
         this.b.a(var4, var9);
      }

      return var4;
   }

   public void a(final GameProfile var1, final SkinManager.SkinAvailableCallback var2, final boolean var3) {
      a.submit(new Runnable() {
         @Override
         public void run() {
            final HashMap var1x = Maps.newHashMap();

            try {
               var1x.putAll(SkinManager.this.d.getTextures(var1, var3));
            } catch (InsecureTextureException var3x) {
            }

            if (var1x.isEmpty() && var1.getId().equals(Minecraft.A().L().e().getId())) {
               var1.getProperties().clear();
               var1.getProperties().putAll(Minecraft.A().N());
               var1x.putAll(SkinManager.this.d.getTextures(var1, false));
            }

            Minecraft.A().a(new Runnable() {
               @Override
               public void run() {
                  if (var1x.containsKey(Type.SKIN)) {
                     SkinManager.this.a((MinecraftProfileTexture)var1x.get(Type.SKIN), Type.SKIN, var2);
                  }

                  if (var1x.containsKey(Type.CAPE)) {
                     SkinManager.this.a((MinecraftProfileTexture)var1x.get(Type.CAPE), Type.CAPE, var2);
                  }
               }
            });
         }
      });
   }

   public Map<Type, MinecraftProfileTexture> a(GameProfile var1) {
      return (Map<Type, MinecraftProfileTexture>)this.e.getUnchecked(var1);
   }

   public interface SkinAvailableCallback {
      void a(Type var1, ResourceLocation var2, MinecraftProfileTexture var3);
   }
}
