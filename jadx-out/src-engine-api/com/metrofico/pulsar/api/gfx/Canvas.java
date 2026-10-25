package com.metrofico.pulsar.api.gfx;

import com.metrofico.pulsar.api.hud.RichText;

public interface Canvas {
   int width();

   int height();

   void fill(int var1, int var2, int var3, int var4, int var5);

   int text(Font var1, String var2, int var3, int var4, float var5, int var6);

   int textWidth(Font var1, String var2, float var3);

   default int lineHeight(Font font, float size) {
      return (int)Math.ceil(size * 1.25);
   }

   void icon(String var1, int var2, int var3, int var4, int var5);

   void logo(int var1, int var2, int var3);

   void clip(int var1, int var2, int var3, int var4);

   void unclip();

   void push();

   void translate(float var1, float var2);

   void pop();

   void pointer(boolean var1);

   int vtext(String var1, int var2, int var3, int var4, boolean var5);

   int vwidth(String var1);

   int vlineHeight();

   default int rich(String s, int x, int y, int baseColor, boolean shadow) {
      int cx = x;

      for (RichText.Span span : RichText.parse(s, baseColor)) {
         if (!span.text().isEmpty()) {
            this.vtext(span.text(), cx, y, RichText.colorOf(span, baseColor), shadow);
            cx += this.vwidth(span.text());
         }
      }

      return cx - x;
   }

   boolean image(String var1, int var2, int var3, int var4, int var5);

   void scale(float var1, float var2);

   default int guiScale() {
      return 1;
   }

   default boolean imageInline(String key, String base64, int x, int y, int w, int h) {
      return false;
   }

   default void cached(Object slot, Object key, int x0, int y0, int x1, int y1, Runnable draw) {
      draw.run();
   }

   default void outline(int x, int y, int w, int h, int argb) {
      this.fill(x, y, x + w, y + 1, argb);
      this.fill(x, y + h - 1, x + w, y + h, argb);
      this.fill(x, y, x + 1, y + h, argb);
      this.fill(x + w - 1, y, x + w, y + h, argb);
   }
}
