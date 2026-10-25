package com.metrofico.pulsar.engine.v189.gfx;

import com.metrofico.pulsar.api.gfx.AtlasPixels;
import com.metrofico.pulsar.api.gfx.Font;
import com.metrofico.pulsar.api.gfx.IconAtlas;
import com.metrofico.pulsar.api.gfx.TypeAtlas;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import org.lwjgl.opengl.GL11;

public final class PulsarAtlases {
   private static final Map<Font, PulsarAtlases.Type> TYPES = new EnumMap<>(Font.class);
   private static final Map<Font, Boolean> TRIED = new EnumMap<>(Font.class);
   private static PulsarAtlases.Icons icons;
   private static boolean iconsTried;
   private static int logo;
   private static boolean logoTried;
   private static int mark;
   private static boolean markTried;
   private static final Map<String, CompletableFuture<PulsarAtlases.Prepared>> PREPARED = new ConcurrentHashMap<>();

   private PulsarAtlases() {
   }

   public static void prefetch() {
      Thread t = new Thread(() -> {
         for (Font font : Font.values()) {
            prepared(font.resource(), 3, true);
         }

         prepared("icons", 1, true);
         prepared("logo", 1, false);
         prepared("mark", 1, false);
      }, "pulsar-atlases");
      t.setDaemon(true);
      t.setPriority(1);
      t.start();
   }

   private static PulsarAtlases.Prepared prepared(String name, int levels, boolean withJson) {
      CompletableFuture<PulsarAtlases.Prepared> mine = new CompletableFuture<>();
      CompletableFuture<PulsarAtlases.Prepared> f = PREPARED.putIfAbsent(name, mine);
      if (f == null) {
         f = mine;

         try {
            mine.complete(decode(name, levels, withJson));
         } catch (Throwable var6) {
            BootstrapLog.info("pulsar atlas failed " + name + ": " + var6);
            mine.complete(null);
         }
      }

      return f.join();
   }

   private static PulsarAtlases.Prepared decode(String name, int maxLevels, boolean withJson) throws Exception {
      byte[] png = resource("/pulsar/" + name + ".png");
      byte[] json = withJson ? resource("/pulsar/" + name + ".json") : null;
      if (png != null && (!withJson || json != null)) {
         BufferedImage img = ImageIO.read(new ByteArrayInputStream(png));
         int w = img.getWidth();
         int h = img.getHeight();
         int[] px = img.getRGB(0, 0, w, h, null, 0, w);
         BufferedImage[] levels = new BufferedImage[maxLevels];

         int n;
         for (n = 0; px != null && n < maxLevels; h /= 2) {
            levels[n++] = argb(px, w, h);
            px = AtlasPixels.halve(px, w, h);
            w /= 2;
         }

         return new PulsarAtlases.Prepared(Arrays.copyOf(levels, n), json);
      } else {
         return null;
      }
   }

   private static BufferedImage argb(int[] px, int w, int h) {
      BufferedImage out = new BufferedImage(w, h, 2);
      int[] data = ((DataBufferInt)out.getRaster().getDataBuffer()).getData();
      System.arraycopy(px, 0, data, 0, px.length);
      return out;
   }

   static byte[] resource(String path) {
      try {
         byte[] var2;
         try (InputStream in = PulsarAtlases.class.getResourceAsStream(path)) {
            var2 = in == null ? null : in.readAllBytes();
         }

         return var2;
      } catch (Exception var6) {
         BootstrapLog.info("pulsar resource failed " + path + ": " + var6);
         return null;
      }
   }

   private static int upload(BufferedImage img) {
      int id = GL11.glGenTextures();
      bml.a(id, img, true, true);
      GL11.glBindTexture(3553, id);
      GL11.glTexParameteri(3553, 10241, 9729);
      GL11.glTexParameteri(3553, 10240, 9729);
      return id;
   }

   public static synchronized PulsarAtlases.Type type(Font font) {
      PulsarAtlases.Type t = TYPES.get(font);
      if (t == null && !TRIED.getOrDefault(font, false)) {
         TRIED.put(font, true);

         try {
            PulsarAtlases.Prepared p = prepared(font.resource(), 3, true);
            if (p == null) {
               return null;
            } else {
               TypeAtlas atlas = new TypeAtlas(p.json(), p.levels()[0].getWidth(), p.levels()[0].getHeight());
               int[] tex = new int[3];
               int levels = 0;

               for (BufferedImage level : p.levels()) {
                  tex[levels++] = upload(level);
               }

               t = new PulsarAtlases.Type(atlas, tex, levels);
               TYPES.put(font, t);
               return t;
            }
         } catch (Exception var10) {
            BootstrapLog.info("pulsar type failed: " + var10);
            return null;
         }
      } else {
         return t;
      }
   }

   public static synchronized PulsarAtlases.Icons icons() {
      if (icons == null && !iconsTried) {
         iconsTried = true;

         try {
            PulsarAtlases.Prepared p = prepared("icons", 1, true);
            if (p == null) {
               return null;
            }

            BufferedImage img = p.levels()[0];
            icons = new PulsarAtlases.Icons(new IconAtlas(p.json()), upload(img), img.getWidth(), img.getHeight());
         } catch (Exception var2) {
            BootstrapLog.info("pulsar icons failed: " + var2);
         }

         return icons;
      } else {
         return icons;
      }
   }

   public static synchronized int logo() {
      if (logo == 0 && !logoTried) {
         logoTried = true;

         try {
            PulsarAtlases.Prepared p = prepared("logo", 1, false);
            if (p != null) {
               logo = upload(p.levels()[0]);
            }
         } catch (Exception var1) {
            BootstrapLog.info("pulsar logo failed: " + var1);
         }

         return logo;
      } else {
         return logo;
      }
   }

   public static synchronized int mark() {
      if (mark == 0 && !markTried) {
         markTried = true;

         try {
            PulsarAtlases.Prepared p = prepared("mark", 1, false);
            if (p != null) {
               mark = upload(p.levels()[0]);
            }
         } catch (Exception var1) {
            BootstrapLog.info("pulsar mark failed: " + var1);
         }

         return mark;
      } else {
         return mark;
      }
   }

   public record Icons(IconAtlas atlas, int texture, int texW, int texH) {
   }

   private record Prepared(BufferedImage[] levels, byte[] json) {
   }

   public record Type(TypeAtlas atlas, int[] textures, int levels) {
      public int texture(int level) {
         return this.textures[Math.min(level, this.levels - 1)];
      }
   }
}
