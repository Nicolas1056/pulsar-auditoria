package com.metrofico.pulsar.api.gfx;

import com.metrofico.pulsar.api.pack.Json;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class TypeAtlas {
   private final int atlasSize;
   private final int ascent;
   private final int cell;
   private final int pad;
   private final float[][] table = new float[65536][];
   private final Map<Integer, Float> kern = new HashMap<>();
   private final int texW;
   private final int texH;

   public TypeAtlas(byte[] json, int texW, int texH) {
      Map<String, Object> root = Json.object(Json.parse(new String(json, StandardCharsets.UTF_8)), "font");
      this.atlasSize = (int)Json.integer(root, "size", "font");
      this.ascent = (int)Json.integer(root, "ascent", "font");
      this.cell = root.get("cell") instanceof Number n ? n.intValue() : this.atlasSize + 12;
      this.pad = root.get("pad") instanceof Number nx ? nx.intValue() : 4;
      this.texW = texW;
      this.texH = texH;

      for (Entry<String, Object> e : Json.object(root.get("glyphs"), "glyphs").entrySet()) {
         List<Object> v = (List<Object>)e.getValue();
         this.table[e.getKey().charAt(0)] = new float[]{((Number)v.get(0)).intValue(), ((Number)v.get(1)).intValue(), ((Number)v.get(2)).floatValue()};
      }

      if (root.get("kern") instanceof Map<?, ?> k) {
         for (Entry<?, ?> e : k.entrySet()) {
            String key = (String)e.getKey();
            if (key.length() == 2 && e.getValue() instanceof Number nxx) {
               this.kern.put(key.charAt(0) << 16 | key.charAt(1), nxx.floatValue());
            }
         }
      }
   }

   public int atlasSize() {
      return this.atlasSize;
   }

   public int ascent() {
      return this.ascent;
   }

   public int cell() {
      return this.cell;
   }

   public int pad() {
      return this.pad;
   }

   public int texW() {
      return this.texW;
   }

   public int texH() {
      return this.texH;
   }

   private float kern(char a, char b) {
      Float k = this.kern.get(a << 16 | b);
      return k == null ? 0.0F : k;
   }

   public double advance(String text) {
      double w = 0.0;
      char prev = 0;

      for (int i = 0; i < text.length(); i++) {
         char c = text.charAt(i);
         float[] g = this.table[c];
         if (g != null) {
            if (prev != 0) {
               w += this.kern(prev, c);
            }

            w += g[2];
            prev = c;
         }
      }

      return w;
   }

   public int width(String text, float size) {
      return (int)Math.ceil(this.advance(text) * size / this.atlasSize);
   }

   public TypeAtlas.Layout layout(String text, int x, int y, float size, int scale) {
      double s = size * scale / this.atlasSize;
      float dw = (float)(this.cell * s);
      double cx = (double)x * scale;
      float py = (float)((double)y * scale - this.pad * s);
      int n = text.length();
      float[] q = new float[n * 8];
      int count = 0;
      char prev = 0;

      for (int i = 0; i < n; i++) {
         char c = text.charAt(i);
         float[] g = this.table[c];
         if (g != null) {
            if (prev != 0) {
               cx += this.kern(prev, c) * s;
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

      return new TypeAtlas.Layout(q, count, (int)Math.ceil((cx - (double)x * scale) / scale));
   }

   public int mipLevel(float size, int scale, int levels) {
      int level = 0;
      double px = size * scale;

      while (level + 1 < levels && this.atlasSize >> level + 1 >= px) {
         level++;
      }

      return level;
   }

   public record Layout(float[] quads, int count, int widthGui) {
   }
}
