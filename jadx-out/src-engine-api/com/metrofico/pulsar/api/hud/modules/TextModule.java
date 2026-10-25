package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.gfx.Font;
import com.metrofico.pulsar.api.hud.HudBackground;
import com.metrofico.pulsar.api.hud.HudModule;
import com.metrofico.pulsar.api.hud.HudText;
import java.util.Locale;

abstract class TextModule implements HudModule {
   static final int PIXEL_COLOR = -1;
   static final int PAD = 2;
   private String opacityOption;
   private static final int CAP_HEIGHT = 7;

   protected abstract String[] lines();

   protected float size() {
      return (float)(8.0 * this.scale());
   }

   protected int color() {
      return -857150222;
   }

   protected boolean pixel() {
      return false;
   }

   protected boolean background() {
      return false;
   }

   protected boolean smallWords() {
      return false;
   }

   private String[] shown() {
      String[] l = this.lines();
      if (!this.pixel()) {
         return l;
      } else {
         String[] up = new String[l.length];

         for (int i = 0; i < l.length; i++) {
            up[i] = l[i].toUpperCase(Locale.ROOT);
         }

         return up;
      }
   }

   private double crisp(Canvas g) {
      int gui = Math.max(1, g.guiScale());
      return (double)Math.max(1L, Math.round(this.scale() * gui)) / gui;
   }

   private float wordRatio(Canvas g) {
      return this.smallWords() ? HudText.crispRatio(g, this.scale(), 0.6666666666666666) : 1.0F;
   }

   private static boolean wordAt(String l, int i) {
      return Character.isLetter(l.charAt(i));
   }

   private static int runEnd(String l, int i) {
      boolean word = wordAt(l, i);
      int j = i + 1;

      while (j < l.length() && wordAt(l, j) == word) {
         j++;
      }

      return j;
   }

   private static float lineWidth(Canvas g, String l, float ratio) {
      if (ratio == 1.0F) {
         return g.vwidth(l);
      } else {
         float w = 0.0F;
         int i = 0;

         while (i < l.length()) {
            int j = runEnd(l, i);
            int rw = g.vwidth(l.substring(i, j));
            w += wordAt(l, i) ? rw * ratio : rw;
            i = j;
         }

         return w;
      }
   }

   private int innerWidth(Canvas g, String[] lines) {
      float ratio = this.wordRatio(g);
      int w = 0;

      for (String l : lines) {
         w = Math.max(w, (int)Math.ceil(lineWidth(g, l, ratio)));
      }

      return w + (this.background() ? 4 : 1);
   }

   private int innerHeight(Canvas g, String[] lines) {
      return Math.max(1, lines.length) * g.vlineHeight() + (this.background() ? 3 : 0);
   }

   @Override
   public int width(Canvas g) {
      if (this.pixel()) {
         return (int)Math.ceil(this.innerWidth(g, this.shown()) * this.crisp(g));
      } else {
         int w = 0;

         for (String l : this.lines()) {
            w = Math.max(w, g.textWidth(Font.MEDIUM, l, this.size()));
         }

         return w + 2;
      }
   }

   @Override
   public int height(Canvas g) {
      return this.pixel()
         ? (int)Math.ceil(this.innerHeight(g, this.shown()) * this.crisp(g))
         : Math.max(1, this.lines().length) * g.lineHeight(Font.MEDIUM, this.size());
   }

   @Override
   public void draw(Canvas g, int x, int y) {
      if (this.pixel()) {
         this.drawPixel(g, x, y);
      } else {
         int lh = g.lineHeight(Font.MEDIUM, this.size());
         int i = 0;

         for (String l : this.lines()) {
            g.text(Font.MEDIUM, l, x + 1, y + i++ * lh, this.size(), this.color());
         }
      }
   }

   private static void drawRuns(Canvas g, String l, int x0, int y0, float ratio, boolean shadow) {
      float x = x0;
      int i = 0;

      while (i < l.length()) {
         int j = runEnd(l, i);
         String run = l.substring(i, j);
         if (wordAt(l, i)) {
            g.push();
            g.translate(x, y0 + 7.0F * (1.0F - ratio));
            g.scale(ratio, ratio);
            g.vtext(run, 0, 0, -1, shadow);
            g.pop();
            x += g.vwidth(run) * ratio;
         } else {
            g.push();
            g.translate(x, y0);
            g.vtext(run, 0, 0, -1, shadow);
            g.pop();
            x += g.vwidth(run);
         }

         i = j;
      }
   }

   private void drawPixel(Canvas g, int x, int y) {
      String[] lines = this.shown();
      if (lines.length != 0) {
         float factor = (float)this.crisp(g);
         boolean bg = this.background();
         if (this.opacityOption == null) {
            this.opacityOption = HudBackground.option(this.id());
         }

         int bgColor = bg ? HudBackground.color(this.opacityOption) : 0;
         int iw = this.innerWidth(g, lines);
         int ih = this.innerHeight(g, lines);
         int w = (int)Math.ceil(iw * factor);
         int h = (int)Math.ceil(ih * factor);
         g.cached(this.id(), new TextModule.Key(String.join("\n", lines), factor, bgColor, x, y, g.width(), g.height()), x, y, x + w, y + h, () -> {
            g.push();
            g.translate(x, y);
            g.scale(factor, factor);
            if (bg) {
               g.fill(0, 0, iw, ih, bgColor);
            }

            int lh = g.vlineHeight();
            int pad = bg ? 2 : 0;
            float ratio = this.wordRatio(g);

            for (int i = 0; i < lines.length; i++) {
               int x0 = pad + (bg ? 0 : 1);
               int y0 = pad + i * lh;
               if (ratio == 1.0F) {
                  g.vtext(lines[i], x0, y0, -1, !bg);
               } else {
                  drawRuns(g, lines[i], x0, y0, ratio, !bg);
               }
            }

            g.pop();
         });
      }
   }

   private record Key(String text, float factor, int background, int x, int y, int guiWidth, int guiHeight) {
   }
}
