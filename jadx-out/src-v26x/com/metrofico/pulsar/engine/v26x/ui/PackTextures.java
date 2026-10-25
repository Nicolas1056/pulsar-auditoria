package com.metrofico.pulsar.engine.v26x.ui;

import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.mojang.blaze3d.platform.NativeImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;

public final class PackTextures {
   private static final Map<String, PackTextures.Texture> LOADED = new HashMap<>();
   private static final Map<String, Boolean> FAILED = new HashMap<>();

   private PackTextures() {
   }

   public static PackTextures.Texture get(String sha256, Path file) {
      PackTextures.Texture t = LOADED.get(sha256);
      if (t != null) {
         return t;
      } else if (FAILED.containsKey(sha256)) {
         return null;
      } else {
         try {
            NativeImage image = NativeImage.read(Files.readAllBytes(file));
            Identifier id = Identifier.fromNamespaceAndPath("pulsar", "pack/" + sha256);
            Minecraft.getInstance().getTextureManager().register(id, new DynamicTexture(() -> "pulsar pack " + sha256, image));
            t = new PackTextures.Texture(id, image.getWidth(), image.getHeight());
            LOADED.put(sha256, t);
            return t;
         } catch (Exception var5) {
            BootstrapLog.info("pulsar:v1 image ignored " + sha256 + ": " + var5);
            FAILED.put(sha256, true);
            return null;
         }
      }
   }

   public static PackTextures.Texture inline(String key, String base64) {
      PackTextures.Texture t = LOADED.get(key);
      if (t != null) {
         return t;
      } else if (FAILED.containsKey(key)) {
         return null;
      } else {
         try {
            NativeImage image = NativeImage.read(Base64.getDecoder().decode(base64));
            Identifier id = Identifier.fromNamespaceAndPath("pulsar", "inline/" + Integer.toHexString(key.hashCode()) + "_" + LOADED.size());
            Minecraft.getInstance().getTextureManager().register(id, new DynamicTexture(() -> "pulsar inline " + key, image));
            t = new PackTextures.Texture(id, image.getWidth(), image.getHeight());
            LOADED.put(key, t);
            return t;
         } catch (Exception var5) {
            BootstrapLog.info("pulsar:v1 inline image ignored " + key + ": " + var5);
            FAILED.put(key, true);
            return null;
         }
      }
   }

   public static void releaseAll() {
      for (PackTextures.Texture t : LOADED.values()) {
         Minecraft.getInstance().getTextureManager().release(t.id());
      }

      LOADED.clear();
      FAILED.clear();
   }

   public record Texture(Identifier id, int width, int height) {
   }
}
