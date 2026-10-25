package com.metrofico.pulsar.api.hud;

import java.util.ArrayList;
import java.util.List;

public final class HudLayout {
   public static final int GRID = 4;
   public static final int SNAP = 6;
   public static final int HOTBAR_WIDTH = 182;
   public static final int CROSSHAIR_RESERVE = 24;

   private HudLayout() {
   }

   public static List<HudLayout.Rect> reserved(int guiWidth, int guiHeight) {
      List<HudLayout.Rect> r = new ArrayList<>();
      r.add(new HudLayout.Rect((guiWidth - 182) / 2 - 2, guiHeight - 64, 186, 64));
      r.add(new HudLayout.Rect(guiWidth / 2 - 12, guiHeight / 2 - 12, 24, 24));
      return r;
   }

   public static int grid(int v, int grid) {
      return grid <= 1 ? v : (int)Math.round((double)v / grid) * grid;
   }

   public static HudLayout.Snap snap(HudLayout.Rect moving, List<HudLayout.Rect> others, int guiWidth, int guiHeight, boolean useGrid) {
      int x = useGrid ? grid(moving.x, 4) : moving.x;
      int y = useGrid ? grid(moving.y, 4) : moving.y;
      List<Integer> vs = new ArrayList<>();
      List<Integer> hs = new ArrayList<>();
      List<int[]> vcand = new ArrayList<>();
      List<int[]> hcand = new ArrayList<>();
      int m = 4;

      for (int line : new int[]{m, guiWidth / 2, guiWidth - m}) {
         vcand.add(new int[]{line, line});
         vcand.add(new int[]{line, line - moving.w});
         vcand.add(new int[]{line, line - moving.w / 2});
      }

      for (int line : new int[]{m, guiHeight / 2, guiHeight - m}) {
         hcand.add(new int[]{line, line});
         hcand.add(new int[]{line, line - moving.h});
         hcand.add(new int[]{line, line - moving.h / 2});
      }

      for (HudLayout.Rect o : others) {
         for (int line : new int[]{o.x, o.right(), o.cx()}) {
            vcand.add(new int[]{line, line});
            vcand.add(new int[]{line, line - moving.w});
            vcand.add(new int[]{line, line - moving.w / 2});
         }

         vcand.add(new int[]{o.right() + 4, o.right() + 4});
         vcand.add(new int[]{o.x - 4, o.x - 4 - moving.w});

         for (int line : new int[]{o.y, o.bottom(), o.cy()}) {
            hcand.add(new int[]{line, line});
            hcand.add(new int[]{line, line - moving.h});
            hcand.add(new int[]{line, line - moving.h / 2});
         }

         hcand.add(new int[]{o.bottom() + 4, o.bottom() + 4});
         hcand.add(new int[]{o.y - 4, o.y - 4 - moving.h});
      }

      int bestDx = 7;
      int bestX = x;
      int bestVLine = Integer.MIN_VALUE;

      for (int[] c : vcand) {
         int d = Math.abs(c[1] - x);
         if (d < bestDx) {
            bestDx = d;
            bestX = c[1];
            bestVLine = c[0];
         }
      }

      int bestDy = 7;
      int bestY = y;
      int bestHLine = Integer.MIN_VALUE;

      for (int[] cx : hcand) {
         int d = Math.abs(cx[1] - y);
         if (d < bestDy) {
            bestDy = d;
            bestY = cx[1];
            bestHLine = cx[0];
         }
      }

      if (bestDx <= 6) {
         x = bestX;
         vs.add(bestVLine);
      }

      if (bestDy <= 6) {
         y = bestY;
         hs.add(bestHLine);
      }

      return new HudLayout.Snap(x, y, vs, hs);
   }

   public static HudLayout.Rect clamp(HudLayout.Rect r, int guiWidth, int guiHeight) {
      int m = 4;
      int x = Math.max(m, Math.min(guiWidth - m - r.w, r.x));
      int y = Math.max(m, Math.min(guiHeight - m - r.h, r.y));
      return r.at(x, y);
   }

   public static HudLayout.Rect resolve(HudLayout.Rect moving, List<HudLayout.Rect> obstacles, int guiWidth, int guiHeight) {
      HudLayout.Rect current = clamp(moving, guiWidth, guiHeight);
      List<HudLayout.Rect> all = new ArrayList<>(obstacles);
      all.addAll(reserved(guiWidth, guiHeight));

      for (int iteration = 0; iteration < 8; iteration++) {
         HudLayout.Rect hit = null;

         for (HudLayout.Rect o : all) {
            if (current.overlaps(o)) {
               hit = o;
               break;
            }
         }

         if (hit == null) {
            return current;
         }

         HudLayout.Rect best = null;
         double bestD = Double.MAX_VALUE;
         int g = 4;

         for (HudLayout.Rect ox : all) {
            if (current.overlaps(ox)) {
               HudLayout.Rect[] options = new HudLayout.Rect[]{
                  current.at(ox.right() + g, current.y),
                  current.at(ox.x - g - current.w, current.y),
                  current.at(current.x, ox.bottom() + g),
                  current.at(current.x, ox.y - g - current.h)
               };

               for (HudLayout.Rect c : options) {
                  HudLayout.Rect cl = clamp(c, guiWidth, guiHeight);
                  if (cl.equals(c)) {
                     boolean clear = true;

                     for (HudLayout.Rect other : all) {
                        if (c.overlaps(other)) {
                           clear = false;
                           break;
                        }
                     }

                     double d = Math.hypot(c.x - moving.x, c.y - moving.y);
                     if (d < bestD && (clear || best == null) && (clear || bestD == Double.MAX_VALUE)) {
                        best = c;
                        bestD = clear ? d : d + 10000.0;
                     }
                  }
               }
            }
         }

         if (best == null) {
            return null;
         }

         current = best;
      }

      return null;
   }

   public record Rect(int x, int y, int w, int h) {
      public int right() {
         return this.x + this.w;
      }

      public int bottom() {
         return this.y + this.h;
      }

      public int cx() {
         return this.x + this.w / 2;
      }

      public int cy() {
         return this.y + this.h / 2;
      }

      public boolean overlaps(HudLayout.Rect o) {
         return this.x < o.right() && o.x < this.right() && this.y < o.bottom() && o.y < this.bottom();
      }

      public HudLayout.Rect at(int nx, int ny) {
         return new HudLayout.Rect(nx, ny, this.w, this.h);
      }
   }

   public record Snap(int x, int y, List<Integer> vertical, List<Integer> horizontal) {
   }
}
