package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudData;
import com.metrofico.pulsar.api.hud.HudModules;

public final class CrosshairRenderer {
   private CrosshairRenderer() {
   }

   public static boolean enabled() {
      return ClientOptions.get().enabled("crosshair");
   }

   public static void draw(Canvas g, int cx, int cy) {
      ClientOptions o = ClientOptions.get();
      String style = o.string("crosshair.style", "cross");
      if (!style.equals("none")) {
         int size = o.integer("crosshair.size", 5);
         int thick = o.integer("crosshair.thickness", 1);
         int gap = o.integer("crosshair.gap", 1);
         int alpha = Math.round(255 * Math.max(1, Math.min(100, o.integer("crosshair.opacity", 100))) / 100.0F);
         boolean tstyle = o.bool("crosshair.tstyle", false);
         int dotSize = o.integer("crosshair.dotSize", 0);
         int color = alpha << 24 | o.color("crosshair.color", -1) & 16777215;
         int edge = alpha << 24;
         boolean outline = o.bool("crosshair.outline", true);
         int half = thick / 2;
         int x0 = cx - half;
         int y0 = cy - half;
         boolean cross = style.equals("cross") || style.equals("cross-dot");
         boolean dot = style.equals("dot") || style.equals("cross-dot");
         if (cross) {
            rect(g, cx - half - gap - size, y0, size, thick, color, edge, outline);
            rect(g, cx - half + thick + gap, y0, size, thick, color, edge, outline);
            if (!tstyle) {
               rect(g, x0, cy - half - gap - size, thick, size, color, edge, outline);
            }

            rect(g, x0, cy - half + thick + gap, thick, size, color, edge, outline);
         }

         if (dot) {
            int d = dotSize > 0 ? dotSize : Math.max(1, thick);
            rect(g, cx - d / 2, cy - d / 2, d, d, color, edge, outline);
         }

         if (style.equals("circle")) {
            int r = size;

            for (int t = 0; t < thick; t++) {
               ring(g, cx, cy, r - t, outline ? edge : 0, true);
            }

            for (int t = 0; t < thick; t++) {
               ring(g, cx, cy, r - t, color, false);
            }
         }

         attackIndicator(g, cx, cy + size + gap + thick + 2);
      }
   }

   private static void rect(Canvas g, int x, int y, int w, int h, int color, int edge, boolean outline) {
      if (outline) {
         g.fill(x - 1, y - 1, x + w + 1, y + h + 1, edge);
      }

      g.fill(x, y, x + w, y + h, color);
   }

   private static void ring(Canvas g, int cx, int cy, int r, int color, boolean fat) {
      if (color != 0 && r > 0) {
         int x = r;
         int y = 0;
         int err = 1 - r;

         while (x >= y) {
            int[][] pts = new int[][]{{x, y}, {y, x}, {-y, x}, {-x, y}, {-x, -y}, {-y, -x}, {y, -x}, {x, -y}};

            for (int[] p : pts) {
               int px = cx + p[0];
               int py = cy + p[1];
               if (fat) {
                  g.fill(px - 1, py - 1, px + 2, py + 2, color);
               } else {
                  g.fill(px, py, px + 1, py + 1, color);
               }
            }

            y++;
            if (err < 0) {
               err += 2 * y + 1;
            } else {
               x--;
               err += 2 * (y - x) + 1;
            }
         }
      }
   }

   private static void attackIndicator(Canvas g, int cx, int y) {
      HudData d = HudModules.data();
      if (d.inGame() && !ClientOptions.get().classicPvp() && d.attackIndicatorCrosshair()) {
         float scale = d.attackStrength();
         if (!(scale >= 1.0F)) {
            int w = 16;
            int x = cx - w / 2;
            g.fill(x - 1, y - 1, x + w + 1, y + 3, Integer.MIN_VALUE);
            g.fill(x, y, x + w, y + 2, 1728053247);
            g.fill(x, y, x + Math.round(w * scale), y + 2, -1);
         }
      }
   }

   public static void preview(Canvas g, int x, int y, int w, int h) {
      draw(g, x + w / 2, y + h / 2);
   }
}
