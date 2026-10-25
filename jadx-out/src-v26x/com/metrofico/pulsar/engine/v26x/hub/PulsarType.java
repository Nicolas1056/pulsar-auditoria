package com.metrofico.pulsar.engine.v26x.hub;

import com.metrofico.pulsar.api.pack.Json;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.mixin.AbstractTextureAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.GuiGraphicsExtractorAccessor;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.FilterMode;
import it.unimi.dsi.fastutil.ints.Int2FloatOpenHashMap;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2f;

public final class PulsarType {
   public static final PulsarType REGULAR = new PulsarType("font");
   public static final PulsarType MEDIUM = new PulsarType("font-medium");
   public static final PulsarType BOLD = new PulsarType("font-bold");
   private final String name;
   private Identifier texture;
   private Identifier[] texLevels;
   private int levels = 1;
   private int atlasSize;
   private int ascent;
   private Map<Character, float[]> glyphs;
   private float[][] table;
   private Int2FloatOpenHashMap kernTable;
   private Map<Integer, Float> kern;
   private int cell;
   private int pad;
   private int texW;
   private int texH;
   private float[] quadBuffer = new float[512];
   private boolean batched = true;
   private static Field scissorField;
   private static Method peekMethod;

   private PulsarType(String name) {
      this.name = name;
   }

   static byte[] resource(String path) {
      try {
         label101: {
            byte[] zip;
            try (InputStream in = PulsarType.class.getResourceAsStream(path)) {
               if (in == null) {
                  break label101;
               }

               zip = in.readAllBytes();
            }

            return zip;
         }
      } catch (Exception var14) {
      }

      try {
         URL loc = PulsarType.class.getProtectionDomain().getCodeSource().getLocation();

         byte[] var5;
         try (ZipFile zip = new ZipFile(new File(loc.toURI()))) {
            ZipEntry entry = zip.getEntry(path.startsWith("/") ? path.substring(1) : path);
            if (entry == null) {
               return null;
            }

            try (InputStream in = zip.getInputStream(entry)) {
               var5 = in.readAllBytes();
            }
         }

         return var5;
      } catch (Exception var12) {
         BootstrapLog.info("pulsar resource failed " + path + ": " + var12);
         return null;
      }
   }

   private boolean load() {
      if (this.texture != null) {
         return true;
      } else {
         try {
            byte[] pngBytes = resource("/pulsar/" + this.name + ".png");
            byte[] jsonBytes = resource("/pulsar/" + this.name + ".json");
            if (pngBytes != null && jsonBytes != null) {
               NativeImage image = NativeImage.read(new ByteArrayInputStream(pngBytes));
               this.texW = image.getWidth();
               this.texH = image.getHeight();
               Identifier id = Identifier.fromNamespaceAndPath("pulsar", "type/" + this.name);
               DynamicTexture dyn = new DynamicTexture(() -> "pulsar type " + this.name, image);
               linear(dyn);
               Minecraft.getInstance().getTextureManager().register(id, dyn);
               this.texLevels = new Identifier[3];
               this.texLevels[0] = id;
               NativeImage prev = image;

               for (int l = 1; l < this.texLevels.length; l++) {
                  NativeImage half = halve(prev);
                  if (half == null) {
                     break;
                  }

                  String label = "pulsar type " + this.name + " mip" + l;
                  Identifier lid = Identifier.fromNamespaceAndPath("pulsar", "type/" + this.name + "-mip" + l);
                  DynamicTexture ldyn = new DynamicTexture(() -> label, half);
                  linear(ldyn);
                  Minecraft.getInstance().getTextureManager().register(lid, ldyn);
                  this.texLevels[l] = lid;
                  this.levels = l + 1;
                  prev = half;
               }

               Map<String, Object> root = Json.object(Json.parse(new String(jsonBytes, StandardCharsets.UTF_8)), "font");
               this.atlasSize = (int)Json.integer(root, "size", "font");
               this.ascent = (int)Json.integer(root, "ascent", "font");
               this.cell = root.get("cell") instanceof Number n ? n.intValue() : this.atlasSize + 12;
               this.pad = root.get("pad") instanceof Number nx ? nx.intValue() : 4;
               this.glyphs = new HashMap<>();

               for (Entry<String, Object> e : Json.object(root.get("glyphs"), "glyphs").entrySet()) {
                  List<Object> v = (List<Object>)e.getValue();
                  this.glyphs
                     .put(e.getKey().charAt(0), new float[]{((Number)v.get(0)).intValue(), ((Number)v.get(1)).intValue(), ((Number)v.get(2)).floatValue()});
               }

               this.kern = new HashMap<>();
               if (root.get("kern") instanceof Map<?, ?> k) {
                  for (Entry<?, ?> e : k.entrySet()) {
                     String key = (String)e.getKey();
                     if (key.length() == 2 && e.getValue() instanceof Number nxx) {
                        this.kern.put(key.charAt(0) << 16 | key.charAt(1), nxx.floatValue());
                     }
                  }
               }

               this.table = new float[65536][];

               for (Entry<Character, float[]> ex : this.glyphs.entrySet()) {
                  this.table[ex.getKey()] = ex.getValue();
               }

               this.kernTable = new Int2FloatOpenHashMap(Math.max(16, this.kern.size() * 2));
               this.kernTable.defaultReturnValue(0.0F);

               for (Entry<Integer, Float> ex : this.kern.entrySet()) {
                  this.kernTable.put(ex.getKey(), ex.getValue());
               }

               this.texture = id;
               return true;
            } else {
               return false;
            }
         } catch (Exception var14) {
            BootstrapLog.info("pulsar type failed: " + var14);
            return false;
         }
      }
   }

   private static NativeImage halve(NativeImage src) {
      int w = src.getWidth() / 2;
      int h = src.getHeight() / 2;
      if (w >= 8 && h >= 8) {
         NativeImage out = new NativeImage(w, h, false);

         for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
               int a = src.getPixel(x * 2, y * 2);
               int b = src.getPixel(x * 2 + 1, y * 2);
               int c = src.getPixel(x * 2, y * 2 + 1);
               int d = src.getPixel(x * 2 + 1, y * 2 + 1);
               int px = 0;

               for (int shift = 0; shift <= 24; shift += 8) {
                  int sum = (a >>> shift & 0xFF) + (b >>> shift & 0xFF) + (c >>> shift & 0xFF) + (d >>> shift & 0xFF);
                  px |= (sum + 2) / 4 << shift;
               }

               out.setPixel(x, y, px);
            }
         }

         return out;
      } else {
         return null;
      }
   }

   static void linear(AbstractTexture t) {
      try {
         ((AbstractTextureAccessor)t).pulsar$setSampler(RenderSystem.getSamplerCache().getClampToEdge(FilterMode.LINEAR));
      } catch (Throwable var2) {
         BootstrapLog.info("pulsar type: linear sampler unavailable: " + var2);
      }
   }

   private double advance(String text) {
      double w = 0.0;
      char prev = 0;

      for (int i = 0; i < text.length(); i++) {
         char c = text.charAt(i);
         float[] g = this.table[c];
         if (g != null) {
            if (prev != 0) {
               w += this.kernTable.get(prev << 16 | c);
            }

            w += g[2];
            prev = c;
         }
      }

      return w;
   }

   public int width(String text, float size) {
      return !this.load() ? 0 : (int)Math.ceil(this.advance(text) * size / this.atlasSize);
   }

   public int draw(GuiGraphicsExtractor gfx, String text, int x, int y, float size, int argb) {
      if (!this.load()) {
         return 0;
      } else {
         int scale = Math.max(1, Minecraft.getInstance().getWindow().getGuiScale());
         double s = size * scale / this.atlasSize;
         float dw = (float)(this.cell * s);
         double cx = (double)x * scale;
         float py = (float)((double)y * scale - this.pad * s);
         int n = text.length();
         if (this.quadBuffer.length < n * 8) {
            this.quadBuffer = new float[n * 8];
         }

         float[] q = this.quadBuffer;
         int count = 0;
         char prev = 0;

         for (int i = 0; i < n; i++) {
            char c = text.charAt(i);
            float[] g = this.table[c];
            if (g != null) {
               if (prev != 0) {
                  cx += this.kernTable.get(prev << 16 | c) * s;
               }

               int o = count * 8;
               float gx = (float)(cx - this.pad * s);
               q[o] = gx;
               q[o + 1] = py;
               q[o + 2] = gx + dw;
               q[o + 3] = py + dw;
               q[o + 4] = g[0] / this.texW;
               q[o + 5] = g[1] / this.texH;
               q[o + 6] = (g[0] + this.cell) / this.texW;
               q[o + 7] = (g[1] + this.cell) / this.texH;
               count++;
               cx += g[2] * s;
               prev = c;
            }
         }

         if (count > 0) {
            int level = 0;
            double px = size * scale;

            while (level + 1 < this.levels && this.atlasSize >> level + 1 >= px) {
               level++;
            }

            this.submit(gfx, q, count, argb, 1.0F / scale, level);
         }

         return (int)Math.ceil((cx - (double)x * scale) / scale);
      }
   }

   private void submit(GuiGraphicsExtractor gfx, float[] q, int count, int argb, float inv, int level) {
      Identifier tex = this.texLevels != null && this.texLevels[level] != null ? this.texLevels[level] : this.texture;
      if (this.batched) {
         try {
            AbstractTexture t = Minecraft.getInstance().getTextureManager().getTexture(tex);
            Matrix3x2f pose = new Matrix3x2f(gfx.pose()).scale(inv, inv);
            ScreenRectangle scissor = scissorOf(gfx);
            ((GuiGraphicsExtractorAccessor)gfx)
               .pulsar$guiRenderState()
               .addGuiElement(
                  new PulsarTextElement(
                     RenderPipelines.GUI_TEXTURED,
                     TextureSetup.singleTexture(t.getTextureView(), t.getSampler()),
                     pose,
                     Arrays.copyOf(q, count * 8),
                     count,
                     argb,
                     scissor
                  )
               );
            return;
         } catch (Throwable var13) {
            this.batched = false;
            BootstrapLog.info("pulsar type: batched text unavailable, falling back to per-glyph blits: " + var13);
         }
      }

      gfx.pose().pushMatrix();
      gfx.pose().scale(inv, inv);
      int lw = this.texW >> level;
      int lh = this.texH >> level;
      int lcell = Math.max(1, this.cell >> level);

      for (int i = 0; i < count; i++) {
         int o = i * 8;
         gfx.blit(
            RenderPipelines.GUI_TEXTURED,
            tex,
            Math.round(q[o]),
            Math.round(q[o + 1]),
            q[o + 4] * lw,
            q[o + 5] * lh,
            Math.round(q[o + 2] - q[o]),
            Math.round(q[o + 3] - q[o + 1]),
            lcell,
            lcell,
            lw,
            lh,
            argb
         );
      }

      gfx.pose().popMatrix();
   }

   private static ScreenRectangle scissorOf(GuiGraphicsExtractor gfx) throws Exception {
      if (scissorField == null) {
         Field f = GuiGraphicsExtractor.class.getDeclaredField("scissorStack");
         f.setAccessible(true);
         Method m = f.getType().getDeclaredMethod("peek");
         m.setAccessible(true);
         peekMethod = m;
         scissorField = f;
      }

      return (ScreenRectangle)peekMethod.invoke(scissorField.get(gfx));
   }

   public int lineHeight(float size) {
      return (int)Math.ceil(size * 1.25);
   }
}
