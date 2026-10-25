package com.metrofico.pulsar.engine.v189.gfx;

import com.metrofico.pulsar.api.pack.ImageRef;
import com.metrofico.pulsar.api.pack.PackClient;
import com.metrofico.pulsar.api.pack.RemoteImages;
import com.metrofico.pulsar.api.pack.ImageRef.Kind;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v189.pack.Pack189;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public final class Textures189 {
   private static final Map<String, Textures189.Texture> LOADED = new HashMap<>();
   private static final Map<String, Boolean> FAILED = new HashMap<>();

   private Textures189() {
   }

   public static Textures189.Texture load(String key, byte[] png) {
      Textures189.Texture t = LOADED.get(key);
      if (t != null) {
         return t;
      } else if (FAILED.containsKey(key)) {
         return null;
      } else {
         try {
            BufferedImage img = ImageIO.read(new ByteArrayInputStream(png));
            int id = GL11.glGenTextures();
            bml.a(id, img, false, true);
            t = new Textures189.Texture(id, img.getWidth(), img.getHeight());
            LOADED.put(key, t);
            return t;
         } catch (Exception var5) {
            BootstrapLog.info("pulsar:v1 image ignored " + key + ": " + var5);
            FAILED.put(key, true);
            return null;
         }
      }
   }

   public static Textures189.Texture file(String key, Path file) {
      Textures189.Texture t = LOADED.get(key);
      if (t == null && !FAILED.containsKey(key)) {
         try {
            return load(key, Files.readAllBytes(file));
         } catch (Exception var4) {
            FAILED.put(key, true);
            return null;
         }
      } else {
         return t;
      }
   }

   public static Textures189.Texture inline(String key, String base64) {
      Textures189.Texture t = LOADED.get(key);
      if (t == null && !FAILED.containsKey(key)) {
         try {
            return load(key, Base64.getDecoder().decode(base64));
         } catch (Exception var4) {
            FAILED.put(key, true);
            return null;
         }
      } else {
         return t;
      }
   }

   public static void releaseAll() {
      for (Textures189.Texture t : LOADED.values()) {
         GL11.glDeleteTextures(t.id());
      }

      LOADED.clear();
      FAILED.clear();
   }

   public static boolean draw(Canvas189 g, String ref, int x, int y, int w, int h) {
      Optional<ImageRef> parsed = ImageRef.parse(ref);
      if (parsed.isEmpty()) {
         return false;
      } else {
         ImageRef r = parsed.get();
         int[] region = r.region();
         switch (r.kind()) {
            case PLAYER:
               jy skin = skinOf(UUID.fromString(r.value()));
               if (skin == null) {
                  return false;
               }

               g.flush();
               ave.A().P().a(skin);
               g.texturedBound(x, y, w, h, region[0] / 64.0F, region[1] / 64.0F, (region[0] + region[2]) / 64.0F, (region[1] + region[3]) / 64.0F);
               if (r.isFace()) {
                  g.texturedBound(x, y, w, h, 0.625F, 0.125F, 0.75F, 0.25F);
               }

               return true;
            case RESOURCE:
            case URL:
               PackClient pack = Pack189.get();
               Textures189.Texture t;
               if (r.kind() == Kind.RESOURCE) {
                  String sha = pack.resourceSha(r.value());
                  Path file = sha == null ? null : pack.resourceFile(sha);
                  t = file == null ? null : file(sha, file);
               } else {
                  Path file = RemoteImages.remote(pack.dataDir(), r.value(), BootstrapLog::info);
                  t = file == null ? null : file("url:" + RemoteImages.sha256(r.value()), file);
               }

               if (t == null) {
                  return false;
               }

               int u = region == null ? 0 : Math.min(region[0], t.width());
               int v = region == null ? 0 : Math.min(region[1], t.height());
               int uw = region == null ? t.width() : Math.min(region[2], t.width() - u);
               int vh = region == null ? t.height() : Math.min(region[3], t.height() - v);
               g.flush();
               bfl.i(t.id());
               g.texturedBound(x, y, w, h, (float)u / t.width(), (float)v / t.height(), (float)(u + uw) / t.width(), (float)(v + vh) / t.height());
               return true;
            default:
               return false;
         }
      }
   }

   private static jy skinOf(UUID id) {
      bcy conn = ave.A().u();
      if (conn == null) {
         return null;
      } else {
         bdc info = conn.a(id);
         return info == null ? null : info.g();
      }
   }

   public record Texture(int id, int width, int height) {
   }
}
