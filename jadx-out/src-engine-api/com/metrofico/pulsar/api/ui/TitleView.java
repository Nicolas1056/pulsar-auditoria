package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.engine.PulsarVersion;
import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.gfx.Font;
import java.util.ArrayList;
import java.util.List;

public final class TitleView {
   static final int BG = -15658735;
   static final int SURFACE = -15198184;
   static final int SURFACE_RAISED = -14671840;
   static final int LINE = -14013910;
   static final int LINE_STRONG = -12961222;
   static final int INK = -855310;
   static final int INK_SECONDARY = -5000269;
   static final int INK_MUTED = -8750470;
   static final int ACCENT = -1;
   private static final int COLUMN_W = 200;
   private static final int BUTTON_H = 20;
   private static final int GAP = 8;
   private static final int LOGO = 40;
   private static final int MARGIN = 16;
   private static final long PULSE_MS = 4000L;
   private static final long FADE_MS = 180L;
   private static final double HOVER_MS = 180.0;
   private static final int RADIUS = 4;
   private static final int VEIL = -1726934767;
   private final TitleHost host;
   private final List<TitleView.Button> buttons = new ArrayList<>();
   private final List<TitleView.Hit> hits = new ArrayList<>();
   private final long openedAt = System.currentTimeMillis();
   private int focus = -1;
   private final double[] hoverAnim = new double[5];
   private long lastFrame = System.nanoTime();
   private boolean pointer;
   private double frameDt;

   public TitleView(TitleHost host) {
      this.host = host;
      this.buttons.add(new TitleView.Button(host.translate("menu.multiplayer"), "globe", TitleView.Kind.PRIMARY, host::multiplayer));
      this.buttons.add(new TitleView.Button(host.translate("menu.singleplayer"), "box", TitleView.Kind.SECONDARY, host::singleplayer));
      this.buttons
         .add(
            new TitleView.Button(host.translate("menu.options").replace("...", "").replace("…", "").trim(), "settings", TitleView.Kind.SECONDARY, host::options)
         );
      this.buttons.add(new TitleView.Button("Pulsar", "orbit", TitleView.Kind.SECONDARY, host::hub));
      this.buttons.add(new TitleView.Button(host.translate("menu.quit"), "x", TitleView.Kind.TERTIARY, host::quit));
   }

   public void render(Canvas c, int mouseX, int mouseY) {
      int w = c.width();
      int h = c.height();
      this.hits.clear();
      long now = System.nanoTime();
      this.frameDt = Math.min(0.1, (now - this.lastFrame) / 1.0E9);
      this.lastFrame = now;
      c.fill(0, 0, w, h, -1726934767);
      int wordSize = 18;
      int captionSize = 8;
      int blockH = 56 + c.lineHeight(Font.BOLD, wordSize) + 4 + c.lineHeight(Font.REGULAR, captionSize) + 32 + 60 + 16 + 16 + c.lineHeight(Font.MEDIUM, 9.0F);
      int top = Math.max(16, (h - blockH) / 2);
      int cx = w / 2;
      this.pulse(c, cx, top + 20);
      this.mark(c, cx, top + 20);
      int y = top + 40 + 16;
      String brand = "Pulsar Client";
      c.text(Font.BOLD, brand, cx - c.textWidth(Font.BOLD, brand, wordSize) / 2, y, wordSize, -855310);
      y += c.lineHeight(Font.BOLD, wordSize) + 4;
      String game = "Minecraft " + this.host.gameVersion();
      c.text(Font.REGULAR, game, cx - c.textWidth(Font.REGULAR, game, captionSize) / 2, y, captionSize, -8750470);
      y += c.lineHeight(Font.REGULAR, captionSize) + 32;
      int colW = Math.min(200, w - 32);
      int x0 = cx - colW / 2;
      boolean anyHover = false;
      anyHover |= this.button(c, 0, x0, y, colW, 20, mouseX, mouseY);
      y += 28;
      anyHover |= this.button(c, 1, x0, y, colW, 20, mouseX, mouseY);
      y += 28;
      int half = (colW - 8) / 2;
      anyHover |= this.button(c, 2, x0, y, half, 20, mouseX, mouseY);
      anyHover |= this.button(c, 3, x0 + colW - half, y, half, 20, mouseX, mouseY);
      y += 36;
      anyHover |= this.button(c, 4, x0, y, colW, c.lineHeight(Font.MEDIUM, 9.0F), mouseX, mouseY);
      String name = this.host.playerName();
      if (name != null && !name.isEmpty()) {
         c.text(Font.MEDIUM, name, w - 16 - c.textWidth(Font.MEDIUM, name, 8.0F), 16, 8.0F, -5000269);
      }

      int footY = h - 16 - c.lineHeight(Font.REGULAR, 7.0F);
      c.text(Font.REGULAR, PulsarVersion.full(), 16, footY, 7.0F, -8750470);
      String legal = "Copyright Mojang AB. Do not distribute!";
      c.text(Font.REGULAR, legal, w - 16 - c.textWidth(Font.REGULAR, legal, 7.0F), footY, 7.0F, -8750470);
      if (anyHover != this.pointer) {
         this.pointer = anyHover;
         c.pointer(anyHover);
      }

      long age = System.currentTimeMillis() - this.openedAt;
      if (age < 180L) {
         int a = (int)(255.0 * (1.0 - age / 180.0));
         c.fill(0, 0, w, h, a << 24 | 1118481);
      }
   }

   private boolean button(Canvas c, int index, int x, int y, int bw, int bh, int mouseX, int mouseY) {
      TitleView.Button b = this.buttons.get(index);
      boolean hover = mouseX >= x && mouseX < x + bw && mouseY >= y && mouseY < y + bh;
      this.hits.add(new TitleView.Hit(x, y, bw, bh, index));
      double target = !hover && index != this.focus ? 0.0 : 1.0;
      this.hoverAnim[index] = this.hoverAnim[index] + (target - this.hoverAnim[index]) * (1.0 - Math.exp(-this.frameDt / 0.06));
      double k = this.hoverAnim[index];
      int s = Math.max(1, c.guiScale());
      double lift = b.kind() == TitleView.Kind.TERTIARY ? 0.0 : Math.round(k * s);
      double px0 = x * s;
      double py0 = y * s - lift;
      double px1 = (x + bw) * s;
      double py1 = (y + bh) * s - lift;
      double r = 4 * s;
      c.push();
      c.scale(1.0F / s, 1.0F / s);

      int label = switch (b.kind()) {
         case PRIMARY -> {
            this.shadow(c, px0, py0, px1, py1, r, k, s);
            rrect(c, px0, py0, px1, py1, r, -855310);
            yield -15658735;
         }
         case SECONDARY -> {
            this.shadow(c, px0, py0, px1, py1, r, k, s);
            rrect(c, px0, py0, px1, py1, r, mix(-14013910, -12961222, k));
            rrect(c, px0 + 1.0, py0 + 1.0, px1 - 1.0, py1 - 1.0, Math.max(0.0, r - 1.0), mix(-15198184, -14671840, k));
            yield -855310;
         }
         default -> mix(-5000269, -855310, k);
      };
      if (index == this.focus) {
         int o = 2 * s;
         c.outline((int)px0 - o, (int)py0 - o, (int)(px1 - px0) + 2 * o, (int)(py1 - py0) + 2 * o, -1);
      }

      c.pop();
      int size = 9;
      int icon = 10;
      int gap = 6;
      int tw = c.textWidth(Font.MEDIUM, b.label(), size);
      boolean withIcon = b.kind() != TitleView.Kind.TERTIARY;
      int total = tw + (withIcon ? icon + gap : 0);
      int tx = x + (bw - total) / 2;
      int ty = y + (bh - c.lineHeight(Font.MEDIUM, size)) / 2 + 1;
      c.push();
      c.translate(0.0F, (float)(-lift / s));
      if (withIcon) {
         c.icon(b.icon(), tx, y + (bh - icon) / 2, icon, label);
         tx += icon + gap;
      }

      c.text(Font.MEDIUM, b.label(), tx, ty, size, label);
      c.pop();
      return hover;
   }

   private void shadow(Canvas c, double x0, double y0, double x1, double y1, double r, double k, int s) {
      if (!(k < 0.02)) {
         for (int i = 3; i >= 1; i--) {
            double g = i * s;
            double dy = 2 * s;
            int a = (int)(71.4 * k / (i + 1));
            rrect(c, x0 - g, y0 - g + dy, x1 + g, y1 + g + dy, r + g, a << 24);
         }
      }
   }

   private static int mix(int a, int b, double k) {
      int r = (int)Math.round((a >> 16 & 0xFF) + ((b >> 16 & 0xFF) - (a >> 16 & 0xFF)) * k);
      int g = (int)Math.round((a >> 8 & 0xFF) + ((b >> 8 & 0xFF) - (a >> 8 & 0xFF)) * k);
      int bl = (int)Math.round((a & 0xFF) + ((b & 0xFF) - (a & 0xFF)) * k);
      int al = (int)Math.round((a >>> 24 & 0xFF) + ((b >>> 24 & 0xFF) - (a >>> 24 & 0xFF)) * k);
      return al << 24 | r << 16 | g << 8 | bl;
   }

   private static void rrect(Canvas c, double x0, double y0, double x1, double y1, double r, int argb) {
      if (argb >>> 24 != 0 && !(x1 <= x0) && !(y1 <= y0)) {
         r = Math.min(r, Math.min(x1 - x0, y1 - y0) / 2.0);
         int ix0 = (int)Math.round(x0);
         int iy0 = (int)Math.round(y0);
         int ix1 = (int)Math.round(x1);
         int iy1 = (int)Math.round(y1);
         int ri = (int)Math.ceil(r);
         if (ri <= 0) {
            c.fill(ix0, iy0, ix1, iy1, argb);
         } else {
            c.fill(ix0 + ri, iy0, ix1 - ri, iy1, argb);
            c.fill(ix0, iy0 + ri, ix0 + ri, iy1 - ri, argb);
            c.fill(ix1 - ri, iy0 + ri, ix1, iy1 - ri, argb);
            corner(c, ix0, iy0, ri, r, 0, argb);
            corner(c, ix1 - ri, iy0, ri, r, 1, argb);
            corner(c, ix0, iy1 - ri, ri, r, 2, argb);
            corner(c, ix1 - ri, iy1 - ri, ri, r, 3, argb);
         }
      }
   }

   private static void corner(Canvas c, int x, int y, int ri, double r, int q, int argb) {
      double cx = (q & 1) == 0 ? x + ri : x;
      double cy = (q & 2) == 0 ? y + ri : y;

      for (int py = y; py < y + ri; py++) {
         int runStart = -1;
         int runAlpha = -1;

         for (int px = x; px <= x + ri; px++) {
            int cov = 0;
            if (px < x + ri) {
               for (int k = 0; k < 4; k++) {
                  double dx = px + 0.25 + 0.5 * (k & 1) - cx;
                  double dy = py + 0.25 + 0.5 * (k >> 1) - cy;
                  if (dx * dx + dy * dy <= r * r) {
                     cov++;
                  }
               }
            }

            runStart = span(c, py, px, cov, runStart, runAlpha, argb);
            runAlpha = runStart >= 0 ? cov : -1;
         }
      }
   }

   private void mark(Canvas c, int cx, int cy) {
      int s = Math.max(1, c.guiScale());
      double u = 40 * s / 24.0;
      c.push();
      c.scale(1.0F / s, 1.0F / s);
      double pcx = cx * s;
      double pcy = cy * s;
      this.disc(c, pcx, pcy, 5.0 * u, -855310);
      this.ring(c, pcx, pcy, 10.0 * u, 2.0 * u, true, -855310);
      c.pop();
   }

   private void pulse(Canvas c, int cx, int cy) {
      double t = System.currentTimeMillis() % 4000L / 4000.0;
      double breath = 0.5 - 0.5 * Math.cos((Math.PI * 2) * t);
      int s = Math.max(1, c.guiScale());
      double radius = 26.0 * (1.0 + 0.06 * breath) * s;
      int alpha = (int)(150.0 * (1.0 - 0.8 * breath));
      c.push();
      c.scale(1.0F / s, 1.0F / s);
      this.ring(c, cx * s, cy * s, radius, Math.max(1.0, 0.75 * s), false, alpha << 24 | 16777215);
      c.pop();
   }

   private void disc(Canvas c, double cx, double cy, double r, int argb) {
      int x0 = (int)Math.floor(cx - r - 1.0);
      int x1 = (int)Math.ceil(cx + r + 1.0);
      int y0 = (int)Math.floor(cy - r - 1.0);
      int y1 = (int)Math.ceil(cy + r + 1.0);

      for (int y = y0; y < y1; y++) {
         int runStart = -1;
         int runAlpha = -1;

         for (int x = x0; x <= x1; x++) {
            int cov = 0;
            if (x < x1) {
               for (int k = 0; k < 4; k++) {
                  double px = x + 0.25 + 0.5 * (k & 1) - cx;
                  double py = y + 0.25 + 0.5 * (k >> 1) - cy;
                  if (px * px + py * py <= r * r) {
                     cov++;
                  }
               }
            }

            runStart = span(c, y, x, cov, runStart, runAlpha, argb);
            runAlpha = runStart >= 0 ? cov : -1;
         }
      }
   }

   private void ring(Canvas c, double cx, double cy, double r, double width, boolean open, int argb) {
      double half = width / 2.0;
      double outer = r + half + 1.0;
      double capAx = cx + r * Math.cos(Math.toRadians(-15.0));
      double capAy = cy + r * Math.sin(Math.toRadians(-15.0));
      double capBx = cx + r * Math.cos(Math.toRadians(-75.0));
      double capBy = cy + r * Math.sin(Math.toRadians(-75.0));
      int x0 = (int)Math.floor(cx - outer);
      int x1 = (int)Math.ceil(cx + outer);
      int y0 = (int)Math.floor(cy - outer);
      int y1 = (int)Math.ceil(cy + outer);

      for (int y = y0; y < y1; y++) {
         int runStart = -1;
         int runAlpha = -1;

         for (int x = x0; x <= x1; x++) {
            int cov = 0;
            if (x < x1) {
               for (int k = 0; k < 4; k++) {
                  double px = x + 0.25 + 0.5 * (k & 1);
                  double py = y + 0.25 + 0.5 * (k >> 1);
                  double dx = px - cx;
                  double dy = py - cy;
                  double d = Math.sqrt(dx * dx + dy * dy);
                  boolean in = Math.abs(d - r) <= half;
                  if (in && open) {
                     double a = Math.toDegrees(Math.atan2(dy, dx));
                     in = !(a > -75.0) || !(a < -15.0);
                  }

                  if (!in && open) {
                     in = (px - capAx) * (px - capAx) + (py - capAy) * (py - capAy) <= half * half
                        || (px - capBx) * (px - capBx) + (py - capBy) * (py - capBy) <= half * half;
                  }

                  if (in) {
                     cov++;
                  }
               }
            }

            runStart = span(c, y, x, cov, runStart, runAlpha, argb);
            runAlpha = runStart >= 0 ? cov : -1;
         }
      }
   }

   private static int span(Canvas c, int y, int x, int cov, int runStart, int runAlpha, int argb) {
      if (runStart >= 0 && cov == runAlpha) {
         return runStart;
      } else {
         if (runStart >= 0 && runAlpha > 0) {
            int a = (argb >>> 24) * runAlpha / 4;
            c.fill(runStart, y, x, y + 1, a << 24 | argb & 16777215);
         }

         return cov > 0 ? x : -1;
      }
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (button != 0) {
         return false;
      } else {
         for (TitleView.Hit hit : this.hits) {
            if (hit.contains(x, y)) {
               this.activate(hit.index());
               return true;
            }
         }

         return false;
      }
   }

   public boolean keyPressed(TitleView.Key key) {
      int n = this.buttons.size();
      switch (key) {
         case TAB:
         case DOWN:
            this.focus = (this.focus + 1 + n) % n;
            break;
         case BACK_TAB:
         case UP:
            this.focus = this.focus < 0 ? n - 1 : (this.focus - 1 + n) % n;
            break;
         case ENTER:
            if (this.focus < 0) {
               return false;
            }

            this.activate(this.focus);
            break;
         default:
            return false;
      }

      return true;
   }

   private void activate(int index) {
      this.buttons.get(index).action().run();
   }

   public void closed() {
      this.pointer = false;
   }

   private record Button(String label, String icon, TitleView.Kind kind, Runnable action) {
   }

   private record Hit(int x, int y, int w, int h, int index) {
      boolean contains(double px, double py) {
         return px >= this.x && px < this.x + this.w && py >= this.y && py < this.y + this.h;
      }
   }

   public static enum Key {
      TAB,
      BACK_TAB,
      UP,
      DOWN,
      ENTER,
      OTHER;
   }

   private static enum Kind {
      PRIMARY,
      SECONDARY,
      TERTIARY;
   }
}
