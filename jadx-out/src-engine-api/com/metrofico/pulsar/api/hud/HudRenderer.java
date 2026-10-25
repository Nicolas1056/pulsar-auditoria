package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.pack.ImageRef;
import com.metrofico.pulsar.api.pack.PackClient;
import com.metrofico.pulsar.api.world.CompassLayout;
import com.metrofico.pulsar.api.world.Marker;
import com.metrofico.pulsar.api.world.Projection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class HudRenderer {
   private static final int TITLE_GAP = 2;
   private static final int ICON = 7;
   private static final int MAX_DISTANCE = 512;
   private static final int TOAST_PAD = 6;
   private static final int TOAST_IMAGE = 16;
   private final PackClient pack;
   private HudRenderer.Extras extras = new HudRenderer.Extras() {
      {
         Objects.requireNonNull(HudRenderer.this);
      }

      @Override
      public List<PanelLayout.Widget> widgets(Canvas g) {
         return List.of();
      }

      @Override
      public void draw(Canvas g, PanelLayout.Box box) {
      }
   };
   private List<PanelLayout.Box> cachedBoxes = List.of();
   private int cacheTick = -1;
   private int cacheW;
   private int cacheH;
   private int cacheRev;
   private int cachePack;

   public HudRenderer(PackClient pack) {
      this.pack = pack;
   }

   public void extras(HudRenderer.Extras extras) {
      this.extras = extras;
   }

   private HudData data() {
      return HudModules.data();
   }

   public void render(Canvas g) {
      if (!this.data().showDebug()) {
         this.markers(g);

         for (PanelLayout.Box box : this.boxes(g)) {
            this.draw(g, box, box.x(), box.y());
         }
      }
   }

   public List<PanelLayout.Box> boxes(Canvas g) {
      int tick = HudModules.TICK;
      int rev = ClientOptions.revision;
      int packRev = this.pack.revision();
      if (tick != this.cacheTick
         || g.width() != this.cacheW
         || g.height() != this.cacheH
         || rev != this.cacheRev
         || packRev != this.cachePack
         || HudModules.editing) {
         this.cachedBoxes = this.boxesNow(g);
         this.cacheTick = tick;
         this.cacheW = g.width();
         this.cacheH = g.height();
         this.cacheRev = rev;
         this.cachePack = packRev;
      }

      return this.cachedBoxes;
   }

   public List<PanelLayout.Box> boxesNow(Canvas g) {
      List<PanelLayout.Widget> widgets = new ArrayList<>();
      if (this.pack.compassEnabled()) {
         CompassDefinition c = this.pack.compass();
         widgets.add(new PanelLayout.Widget(c.id(), c.zone(), c.width(), c.height()));
      }

      widgets.addAll(this.extras.widgets(g));

      for (Toast t : this.pack.toasts().tick()) {
         widgets.add(new PanelLayout.Widget("toast:" + t.id(), t.zone(), 160, toastHeight(g, t)));
      }

      widgets.addAll(HudModules.widgets(g));
      Map<String, Placement> free = new LinkedHashMap<>(this.pack.freePlacements());
      free.putAll(HudModules.freePlacements(g));
      return new PanelLayout(s -> g.vwidth(RichText.plain(s)), g.vlineHeight()).layout(this.pack.panels().visible(), widgets, free, g.width(), g.height());
   }

   public void draw(Canvas g, PanelLayout.Box box, int x, int y) {
      if (box.panel() == null) {
         String id = box.widgetId();
         if (HudModules.isModule(id)) {
            HudModules.draw(g, id, x, y);
         } else if (id.startsWith("toast:")) {
            for (Toast t : this.pack.toasts().tick()) {
               if (t.id().equals(id.substring(6))) {
                  int[] off = slide(t.zone(), t.progress(System.currentTimeMillis()), box, g.width(), g.height());
                  toast(g, t, box.x() + off[0], box.y() + off[1], box.width(), box.height());
               }
            }
         } else if (id.startsWith("overlay:")) {
            this.extras.draw(g, box);
         } else {
            this.compass(g, this.pack.compass(), x, y);
         }
      } else {
         PanelDefinition d = box.panel().definition();
         if (d.background() >>> 24 != 0) {
            g.fill(x, y, x + box.width(), y + box.height(), d.background());
         }

         int ty = y + 4;

         for (String title : box.title()) {
            this.line(g, title, x, ty, box, d);
            ty += g.vlineHeight() + 2;
         }

         for (String l : box.lines()) {
            this.line(g, l, x, ty, box, d);
            ty += g.vlineHeight();
         }
      }
   }

   private void line(Canvas g, String raw, int x, int y, PanelLayout.Box box, PanelDefinition d) {
      Object[] parts = ImageRef.splitLine(raw);
      String text = (String)parts[1];
      int size = g.vlineHeight();
      int width = g.vwidth(RichText.plain(text)) + (parts[0] != null ? size + 2 : 0);
      int inner = box.width() - 8;

      int tx = x + 4 + switch (d.align()) {
         case LEFT -> 0;
         case CENTER -> (inner - width) / 2;
         case RIGHT -> inner - width;
      };
      if (parts[0] != null) {
         if (!g.image(raw.substring("[img:".length(), raw.indexOf(93)), tx, y, size, size)) {
            g.fill(tx, y, tx + size, y + size, 1090519039);
         }

         tx += size + 2;
      }

      g.rich(text, tx, y, d.color(), true);
   }

   private void markers(Canvas g) {
      HudData.CameraPose cam = this.data().camera();
      if (this.pack.active() && cam != null) {
         int w = g.width();
         int h = g.height();

         for (Marker m : this.pack.markers().all()) {
            Optional<Projection.Point> p = Projection.project(
               m.x(), m.y(), m.z(), cam.x(), cam.y(), cam.z(), cam.qx(), cam.qy(), cam.qz(), cam.qw(), cam.fov(), w, h
            );
            if (!p.isEmpty() && !(p.get().distance() > 512.0)) {
               int x = (int)Math.round(p.get().x());
               int y = (int)Math.round(p.get().y());
               if (x >= -20 && x <= w + 20 && y >= -20 && y <= h + 20) {
                  icon(g, m, x, y);
                  String label = m.label();
                  String dist = Math.round(p.get().distance()) + " m";
                  if (!label.isEmpty()) {
                     g.vtext(label, x - g.vwidth(label) / 2, y + 7 + 2, m.color(), true);
                  }

                  g.vtext(dist, x - g.vwidth(dist) / 2, y + 7 + 2 + (label.isEmpty() ? 0 : g.vlineHeight()), -2236963, true);
               }
            }
         }
      }
   }

   public void compass(Canvas g, CompassDefinition d, int x, int y) {
      HudData.CameraPose cam = this.data().camera();
      if (cam != null) {
         List<CompassLayout.Item> items = CompassLayout.items(this.pack.markers().all(), cam.x(), cam.y(), cam.z(), cam.yaw());
         List<CompassLayout.Cardinal> cardinals = d.cardinals() ? CompassLayout.cardinals(cam.yaw()) : List.of();
         int w = d.width();
         int h = d.height();
         if (d.background() >>> 24 != 0) {
            g.fill(x, y, x + w, y + h, d.background());
         }

         switch (d.form()) {
            case BAR:
               int cx = x + w / 2;
               g.fill(cx, y, cx + 1, y + h, d.color());

               for (CompassLayout.Cardinal c : cardinals) {
                  CompassLayout.barOffset(c.relativeYaw(), w, d.span())
                     .ifPresent(off -> g.vtext(c.label(), cx + off - g.vwidth(c.label()) / 2, y + 3, d.color(), false));
               }

               for (CompassLayout.Item itemx : items) {
                  CompassLayout.barOffset(itemx.relativeYaw(), w, d.span()).ifPresent(off -> {
                     if (item.marker().image() == null || !g.image(item.marker().image(), cx + off - 4, y + 3, 8, 8)) {
                        g.fill(cx + off - 2, y + 2, cx + off + 2, y + h - 2, item.marker().color());
                     }

                     if (d.distances()) {
                        String distx = Math.round(item.distance()) + " m";
                        g.vtext(distx, cx + off - g.vwidth(distx) / 2, y + h + 1, d.color(), true);
                     }
                  });
               }
               break;
            case RING:
               int cx = x + w / 2;
               int cy = y + h / 2;
               double r = w / 2.0 - 3.0;

               for (int a = 0; a < 360; a += 6) {
                  double[] o = CompassLayout.ringOffset(a, r);
                  g.fill(cx + (int)Math.round(o[0]), cy + (int)Math.round(o[1]), cx + (int)Math.round(o[0]) + 1, cy + (int)Math.round(o[1]) + 1, d.color());
               }

               g.fill(cx - 1, cy - 1, cx + 2, cy + 2, d.accent());
               g.fill(cx, cy - 4, cx + 1, cy - 1, d.accent());

               for (CompassLayout.Cardinal c : cardinals) {
                  double[] o = CompassLayout.ringOffset(c.relativeYaw(), r - 6.0);
                  g.vtext(c.label(), cx + (int)Math.round(o[0]) - g.vwidth(c.label()) / 2, cy + (int)Math.round(o[1]) - 4, d.color(), false);
               }

               for (CompassLayout.Item item : items) {
                  double[] o = CompassLayout.ringOffset(item.relativeYaw(), r);
                  int ix = cx + (int)Math.round(o[0]);
                  int iy = cy + (int)Math.round(o[1]);
                  if (item.marker().image() == null || !g.image(item.marker().image(), ix - 4, iy - 4, 8, 8)) {
                     g.fill(ix - 2, iy - 2, ix + 2, iy + 2, item.marker().color());
                  }
               }
               break;
            case NEEDLE:
               int cx = x + w / 2;
               int cy = y + Math.max(d.size(), 40) / 2;
               CompassLayout.Item target = CompassLayout.needleTarget(items, d.target());
               if (target != null) {
                  double len = Math.max(d.size(), 40) / 2.0 - 4.0;

                  for (int i = 0; i < len; i++) {
                     double[] o = CompassLayout.ringOffset(target.relativeYaw(), i);
                     g.fill(
                        cx + (int)Math.round(o[0]),
                        cy + (int)Math.round(o[1]),
                        cx + (int)Math.round(o[0]) + 2,
                        cy + (int)Math.round(o[1]) + 2,
                        target.marker().color()
                     );
                  }

                  if (d.distances()) {
                     String dist = Math.round(target.distance()) + " m";
                     g.vtext(dist, cx - g.vwidth(dist) / 2, y + h - 9, d.color(), true);
                  }
               }

               g.fill(cx - 1, cy - 1, cx + 2, cy + 2, d.accent());
         }
      }
   }

   public static void icon(Canvas g, Marker m, int x, int y) {
      if (m.image() != null && g.image(m.image(), x - 5, y - 10, 10, 10)) {
         g.fill(x - 6, y - 11, x + 5, y - 10, -872415232);
         g.fill(x - 6, y, x + 5, y + 1, -872415232);
         g.fill(x - 6, y - 11, x - 5, y + 1, -872415232);
         g.fill(x + 4, y - 11, x + 5, y + 1, -872415232);
      } else {
         int c = m.color();
         int outline = -872415232;
         switch (m.icon()) {
            case FLAG:
               g.fill(x - 1, y - 7, x + 1, y + 1, outline);
               g.fill(x, y - 7, x + 1, y, -1118482);
               g.fill(x + 1, y - 7, x + 7, y - 3, c);
               break;
            case PIN:
               g.fill(x - 3, y - 7, x + 4, y - 1, outline);
               g.fill(x - 2, y - 7 + 1, x + 3, y - 2, c);
               g.fill(x, y - 1, x + 1, y + 1, outline);
               break;
            case DOT:
               g.fill(x - 3, y - 3, x + 4, y + 4, outline);
               g.fill(x - 2, y - 2, x + 3, y + 3, c);
               break;
            case STAR:
               g.fill(x - 1, y - 7, x + 2, y + 1, c);
               g.fill(x - 3, y - 3 - 1, x + 3 + 1, y - 3 + 2, c);
         }
      }
   }

   public static int[] slide(Zone zone, double progress, PanelLayout.Box box, int guiWidth, int guiHeight) {
      double k = 1.0 - progress;
      int dx = zone.column() == 2 ? (int)Math.round((guiWidth - box.x()) * k) : (zone.column() == 0 ? -((int)Math.round((box.x() + box.width()) * k)) : 0);
      int dy = zone.column() == 1
         ? (zone.row() == 0 ? -((int)Math.round((box.y() + box.height()) * k)) : (zone.row() == 2 ? (int)Math.round((guiHeight - box.y()) * k) : 0))
         : 0;
      return new int[]{dx, dy};
   }

   private static List<String> wrap(Canvas g, String text, int width) {
      List<String> lines = new ArrayList<>();

      for (String paragraph : text.split("\n")) {
         StringBuilder line = new StringBuilder();

         for (String word : paragraph.split(" ")) {
            String candidate = line.length() == 0 ? word : line + " " + word;
            if (g.vwidth(RichText.plain(candidate)) > width && line.length() > 0) {
               lines.add(line.toString());
               line = new StringBuilder(word);
            } else {
               line = new StringBuilder(candidate);
            }
         }

         lines.add(line.toString());
      }

      return lines;
   }

   private static int toastHeight(Canvas g, Toast t) {
      int textWidth = 148 - (t.image() != null ? 20 : 0);
      int lines = (t.title().isEmpty() ? 0 : 1) + (t.text().isEmpty() ? 0 : wrap(g, t.text(), textWidth).size());
      int textHeight = lines * g.vlineHeight() + (!t.title().isEmpty() && !t.text().isEmpty() ? 2 : 0);
      return Math.max(16, textHeight) + 12;
   }

   private static void toast(Canvas g, Toast t, int x, int y, int w, int h) {
      g.fill(x, y, x + w, y + h, t.background());
      g.outline(x, y, w, h, t.border());
      int tx = x + 6;
      if (t.image() != null) {
         if (!g.image(t.image(), tx, y + (h - 16) / 2, 16, 16)) {
            g.fill(tx, y + (h - 16) / 2, tx + 16, y + (h + 16) / 2, 1090519039);
         }

         tx += 20;
      }

      int textWidth = w - (tx - x) - 6;
      int ty = y + 6;
      if (!t.title().isEmpty()) {
         g.rich(t.title(), tx, ty, t.border(), true);
         ty += g.vlineHeight() + 2;
      }

      for (String line : wrap(g, t.text(), textWidth)) {
         g.rich(line, tx, ty, t.color(), true);
         ty += g.vlineHeight();
      }
   }

   public interface Extras {
      List<PanelLayout.Widget> widgets(Canvas var1);

      void draw(Canvas var1, PanelLayout.Box var2);
   }
}
