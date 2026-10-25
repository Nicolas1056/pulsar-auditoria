package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.gui.Font.GlyphVisitor;
import net.minecraft.client.gui.Font.PreparedText;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public final class TextBake {
   private static final ConcurrentHashMap<TextBake.Key, TextBake.Entry> CACHE = new ConcurrentHashMap<>();
   private static final int TTL_FRAMES = 400;
   private static volatile int frame;
   private static Object language;
   private static final ThreadLocal<TextBake.Entry> CURRENT = new ThreadLocal<>();
   private static final ThreadLocal<TextBake.Key> PENDING = new ThreadLocal<>();

   private TextBake() {
   }

   public static boolean enabled() {
      ClientOptions o = ClientOptions.get();
      return o.enabled("perf") && o.bool("perf.textCache", true);
   }

   public static void tick() {
      Object lang = Language.getInstance();
      if (lang != language) {
         language = lang;
         CACHE.clear();
      }

      int f = ++frame;
      if (f % 256 == 0) {
         Iterator<Map.Entry<TextBake.Key, TextBake.Entry>> it = CACHE.entrySet().iterator();

         while (it.hasNext()) {
            if (f - it.next().getValue().lastFrame > 400) {
               it.remove();
            }
         }
      }
   }

   public static PreparedText get(Component text, float x, float y, int color, int background) {
      TextBake.Key key = new TextBake.Key(text, x, y, color, background);
      PENDING.set(key);
      CURRENT.set(null);
      TextBake.Entry e = CACHE.get(key);
      if (e != null && e.prepared != null) {
         e.lastFrame = frame;
         CURRENT.set(e);
         return e.prepared;
      } else {
         return null;
      }
   }

   public static void put(Component text, float x, float y, int color, int background, PreparedText prepared) {
      TextBake.Key key = PENDING.get();
      if (key == null) {
         key = new TextBake.Key(text, x, y, color, background);
      }

      PENDING.set(null);
      TextBake.Entry old = CACHE.get(key);
      if (old != null && old.prepared == null) {
         old.lastFrame = frame;
      } else {
         TextBake.Entry e = new TextBake.Entry(key.obfuscated ? null : prepared, frame);
         CACHE.put(key, e);
         CURRENT.set(key.obfuscated ? null : e);
      }
   }

   public static void attach(Component text, float x, float y, int color, int background, PreparedText prepared) {
      TextBake.Key key = new TextBake.Key(text, x, y, color, background);
      TextBake.Entry e = CACHE.get(key);
      if (e == null) {
         e = new TextBake.Entry(key.obfuscated ? null : prepared, frame);
         CACHE.put(key, e);
      } else {
         e.lastFrame = frame;
      }

      CURRENT.set(e.prepared == null ? null : e);
   }

   public static void visitTag(PreparedText text, GlyphVisitor glyphRenderer) {
      TextBake.Entry e = CURRENT.get();
      CURRENT.set(null);
      if (e != null && TextReplay.enabled()) {
         try {
            TextReplay.Baked b = e.baked;
            if (b == null) {
               e.baked = TextReplay.recordAndRender(text, glyphRenderer);
               return;
            }

            TextReplay.replay(b, glyphRenderer);
         } catch (Throwable var4) {
            TextReplay.disable(var4);
            text.visit(glyphRenderer);
         }
      } else {
         text.visit(glyphRenderer);
      }
   }

   public static void clear() {
      CACHE.clear();
   }

   static final class Entry {
      final PreparedText prepared;
      volatile TextReplay.Baked baked;
      volatile int lastFrame;

      Entry(PreparedText prepared, int frame) {
         this.prepared = prepared;
         this.lastFrame = frame;
      }
   }

   private static final class Key {
      final long hash;
      final int length;
      final float x;
      final float y;
      final int color;
      final int background;
      final boolean obfuscated;

      Key(Component text, float x, float y, int color, int background) {
         long[] h = new long[]{-7046029254386353131L};
         int[] n = new int[]{0};
         boolean[] obf = new boolean[]{false};
         text.visit(
            (style, str) -> {
               long k = h[0];
               k = k * 1099511628211L ^ (style.getColor() == null ? 7 : style.getColor().getValue());
               k = k * 1099511628211L
                  ^ (style.isBold() ? 1 : 0)
                  ^ (style.isItalic() ? 2 : 0)
                  ^ (style.isUnderlined() ? 4 : 0)
                  ^ (style.isStrikethrough() ? 8 : 0)
                  ^ (style.isObfuscated() ? 16 : 0);
               k = k * 1099511628211L ^ style.getFont().hashCode();
               k = k * 1099511628211L ^ (style.getShadowColor() == null ? 11 : style.getShadowColor());

               for (int i = 0; i < str.length(); i++) {
                  k = k * 31L + str.charAt(i);
               }

               h[0] = k * 1099511628211L ^ 1540483477L;
               n[0] += str.length();
               obf[0] |= style.isObfuscated();
               return Optional.empty();
            },
            Style.EMPTY
         );
         this.hash = h[0];
         this.length = n[0];
         this.obfuscated = obf[0];
         this.x = x;
         this.y = y;
         this.color = color;
         this.background = background;
      }

      @Override
      public boolean equals(Object o) {
         return o instanceof TextBake.Key k
            && k.hash == this.hash
            && k.length == this.length
            && k.x == this.x
            && k.y == this.y
            && k.color == this.color
            && k.background == this.background;
      }

      @Override
      public int hashCode() {
         return (int)(this.hash ^ this.hash >>> 32) * 31 + this.color;
      }
   }
}
