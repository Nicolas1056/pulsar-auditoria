package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hud.HudData;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.HudRenderer;
import com.metrofico.pulsar.api.hud.RichText;
import com.metrofico.pulsar.api.map.FogOfWar;
import com.metrofico.pulsar.api.map.MapArea;
import com.metrofico.pulsar.api.map.MapDefinition;
import com.metrofico.pulsar.api.map.MapView;
import com.metrofico.pulsar.api.pack.Capability;
import com.metrofico.pulsar.api.pack.PackClient;
import com.metrofico.pulsar.api.world.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class NodeView {
   private final PackClient pack;
   private final Map<String, MapView> mapViews = new HashMap<>();

   public NodeView(PackClient pack) {
      this.pack = pack;
   }

   public Map<String, MapView> mapViews() {
      return this.mapViews;
   }

   public static Layout layout() {
      HudData d = HudModules.data();
      return new Layout(s -> d.textWidth(RichText.plain(s)), d.lineHeight());
   }

   public void draw(Canvas g, Layout.Box box, int mouseX, int mouseY, String focused) {
      Node n = box.node();
      int x = box.x();
      int y = box.y();
      int w = box.w();
      int h = box.h();
      int bg = n.color("bg", 0);
      if (n.type() != Node.Type.BUTTON && bg >>> 24 != 0) {
         g.fill(x, y, x + w, y + h, bg);
      }

      int border = n.color("border", 0);
      if (border >>> 24 != 0) {
         g.outline(x, y, w, h, border);
      }

      switch (n.type()) {
         case TEXT:
            this.text(g, n.text(), x, y, w, n, n.color("color", -855310), n.string("align", "left"));
            break;
         case BUTTON:
            boolean hover = n.enabled() && (box.contains(mouseX, mouseY) || n.id().equals(focused));
            int base = n.color("bg", -13747638);
            int fill = hover ? n.color("hover", lighten(base)) : base;
            if (!n.enabled()) {
               fill = fill & 16777215 | 1610612736;
            }

            g.fill(x, y, x + w, y + h, fill);
            if (n.id().equals(focused)) {
               g.outline(x, y, w, h, -11930);
            }

            int color = n.enabled() ? n.color("color", -855310) : -7697782;
            this.text(g, n.text(), x, y + (h - (int)Math.ceil(g.vlineHeight() * scale(n))) / 2, w, n, color, "center");
            break;
         case PROGRESS:
            g.fill(x, y, x + w, y + h, n.color("bg", -15066598));
            int fw = (int)Math.round(w * Math.max(0.0, Math.min(1.0, n.number("value", 0.0))));
            g.fill(x, y, x + fw, y + h, n.color("color", -8587872));
            break;
         case IMAGE:
            if (!g.image(n.string("image", ""), x, y, w, h)) {
               g.fill(x, y, x + w, y + h, 1090519039);
            }
            break;
         case MAP:
            this.map(g, box);
      }
   }

   public void text(Canvas g, String s, int x, int y, int w, Node n, int color, String align) {
      double sc = scale(n);
      int tw = (int)Math.ceil(g.vwidth(RichText.plain(s)) * sc);
      int tx = align.equals("center") ? x + (w - tw) / 2 : (align.equals("right") ? x + w - tw : x);
      boolean shadow = n.bool("shadow", true);
      if (sc == 1.0) {
         g.rich(s, tx, y, color, shadow);
      } else {
         g.push();
         g.scale((float)sc, (float)sc);
         g.rich(s, (int)Math.round(tx / sc), (int)Math.round(y / sc), color, shadow);
         g.pop();
      }
   }

   public static double scale(Node n) {
      return Math.max(0.5, Math.min(3.0, n.number("scale", 1.0)));
   }

   private static int lighten(int argb) {
      int r = Math.min(255, (argb >> 16 & 0xFF) + 40);
      int gg = Math.min(255, (argb >> 8 & 0xFF) + 40);
      int b = Math.min(255, (argb & 0xFF) + 40);
      return argb & 0xFF000000 | r << 16 | gg << 8 | b;
   }

   private MapView mapView(Layout.Box box, MapDefinition def, boolean auto) {
      MapView v = this.mapViews.get(box.node().id());
      if (v != null && v.viewW() == box.w() && v.viewH() == box.h() && v.viewX() == box.x() && v.viewY() == box.y()) {
         v.map(def);
      } else {
         if (auto) {
            HudData.PlayerExact p = HudModules.data().playerExact();
            double px = p != null ? p.x() : 0.0;
            double pz = p != null ? p.z() : 0.0;
            double zoom = v != null ? v.zoom() : 1.0;
            v = MapView.unbounded(def, box.x(), box.y(), box.w(), box.h(), px, pz, zoom);
         } else {
            v = new MapView(def, box.x(), box.y(), box.w(), box.h());
         }

         this.mapViews.put(box.node().id(), v);
      }

      return v;
   }

   private static MapDefinition autoBounds(MapDefinition base, List<MapDefinition.Tile> tiles) {
      double minX = Double.MAX_VALUE;
      double minZ = Double.MAX_VALUE;
      double maxX = -Double.MAX_VALUE;
      double maxZ = -Double.MAX_VALUE;

      for (MapDefinition.Tile t : tiles) {
         minX = Math.min(minX, t.minX());
         minZ = Math.min(minZ, t.minZ());
         maxX = Math.max(maxX, t.maxX());
         maxZ = Math.max(maxZ, t.maxZ());
      }

      HudData.PlayerExact p = HudModules.data().playerExact();
      if (p != null) {
         minX = Math.min(minX, p.x() - 64.0);
         minZ = Math.min(minZ, p.z() - 64.0);
         maxX = Math.max(maxX, p.x() + 64.0);
         maxZ = Math.max(maxZ, p.z() + 64.0);
      }

      return minX >= maxX
         ? base
         : new MapDefinition(
            base.title(),
            base.image(),
            minX,
            minZ,
            maxX,
            maxZ,
            base.background(),
            base.grid(),
            base.gridStep(),
            base.ownPosition(),
            base.minZoom(),
            base.maxZoom(),
            base.fogColor()
         );
   }

   private void map(Canvas g, Layout.Box box) {
      Node n = box.node();
      MapDefinition def = MapDefinition.merge(MapDefinition.empty(), n.props());
      List<MapDefinition.Tile> tiles = MapDefinition.tiles(n.props());
      boolean auto = !def.defined();
      if (auto) {
         def = autoBounds(def, tiles);
      }

      if (this.pack.granted(Capability.UI_MAP) && def.defined()) {
         MapView v = this.mapView(box, def, auto);
         g.clip(box.x(), box.y(), box.x() + box.w(), box.y() + box.h());
         g.fill(box.x(), box.y(), box.x() + box.w(), box.y() + box.h(), def.background());
         int[] r = v.imageRect();
         if (def.image() != null) {
            g.image(def.image(), r[0], r[1], r[2] - r[0], r[3] - r[1]);
         }

         for (MapDefinition.Tile tile : tiles) {
            double[] a = v.toScreen(tile.minX(), tile.minZ());
            double[] b = v.toScreen(tile.maxX(), tile.maxZ());
            int tx = (int)Math.floor(a[0]);
            int ty = (int)Math.floor(a[1]);
            int tw = (int)Math.ceil(b[0]) - tx;
            int th = (int)Math.ceil(b[1]) - ty;
            if (tx + tw >= box.x() && tx <= box.x() + box.w() && ty + th >= box.y() && ty <= box.y() + box.h()) {
               if (tile.png() != null) {
                  g.imageInline("tile:" + tile.image(), tile.png(), tx, ty, tw, th);
               } else {
                  g.image(tile.image(), tx, ty, tw, th);
               }
            }
         }

         if (def.grid()) {
            int gridColor = 822083583;

            for (double gx = Math.ceil(def.minX() / def.gridStep()) * def.gridStep(); gx <= def.maxX(); gx += def.gridStep()) {
               int sx = (int)Math.round(v.toScreen(gx, def.minZ())[0]);
               g.fill(sx, r[1], sx + 1, r[3], gridColor);
            }

            for (double gz = Math.ceil(def.minZ() / def.gridStep()) * def.gridStep(); gz <= def.maxZ(); gz += def.gridStep()) {
               int sy = (int)Math.round(v.toScreen(def.minX(), gz)[1]);
               g.fill(r[0], sy, r[2], sy + 1, gridColor);
            }
         }

         if (FogOfWar.enabled(n.props())) {
            Set<Long> revealed = FogOfWar.parse(n.props().get("revealed"));

            for (double[] rect : FogOfWar.hiddenRects(def, revealed, FogOfWar.cell(n.props()))) {
               double[] a = v.toScreen(rect[0], rect[1]);
               double[] b = v.toScreen(rect[2], rect[3]);
               int x0 = Math.max(box.x(), (int)Math.floor(a[0]));
               int y0 = Math.max(box.y(), (int)Math.floor(a[1]));
               int x1 = Math.min(box.x() + box.w(), (int)Math.ceil(b[0]));
               int y1 = Math.min(box.y() + box.h(), (int)Math.ceil(b[1]));
               if (x1 > x0 && y1 > y0) {
                  g.fill(x0, y0, x1, y1, def.fogColor());
               }
            }
         }

         List<MapArea> areas = new ArrayList<>();
         double[] s = (double[])n.props().get("areas");
         if (s instanceof List) {
            for (Object o : (List)s) {
               if (o instanceof Map<?, ?> m) {
                  MapArea a = MapArea.from((Map<String, Object>)m, null);
                  if (a.valid()) {
                     areas.add(a);
                  }
               }
            }
         }

         for (MapArea a : areas) {
            List<double[]> pts = v.project(a.points());
            int top = Math.max(box.y(), (int)pts.stream().mapToDouble(pxx -> pxx[1]).min().orElse(box.y()));
            int bottom = Math.min(box.y() + box.h(), (int)Math.ceil(pts.stream().mapToDouble(pxx -> pxx[1]).max().orElse(box.y())));

            for (int row = top; row < bottom; row++) {
               for (int[] span : MapView.spans(pts, row)) {
                  g.fill(Math.max(box.x(), span[0]), row, Math.min(box.x() + box.w(), span[1]), row + 1, a.fill());
               }
            }

            if (!a.label().isEmpty()) {
               double[] c = v.toScreen(a.center()[0], a.center()[1]);
               g.rich(a.label(), (int)c[0] - g.vwidth(RichText.plain(a.label())) / 2, (int)c[1] - 4, a.outline(), true);
            }
         }

         if (this.pack.granted(Capability.WORLD_MARKERS)) {
            for (Marker mx : this.pack.markers().all()) {
               double[] sx = v.toScreen(mx.x(), mx.z());
               if (v.inView(sx[0], sx[1])) {
                  HudRenderer.icon(g, mx, (int)sx[0], (int)sx[1]);
                  if (!mx.label().isEmpty()) {
                     g.rich(mx.label(), (int)sx[0] - g.vwidth(RichText.plain(mx.label())) / 2, (int)sx[1] + 3, mx.color(), true);
                  }
               }
            }
         }

         HudData.PlayerExact p = HudModules.data().playerExact();
         if (def.ownPosition() && p != null) {
            s = v.toScreen(p.x(), p.z());
            if (v.inView(s[0], s[1])) {
               int px = (int)s[0];
               int py = (int)s[1];
               double yaw = Math.toRadians(p.yaw());
               String own = n.string("ownImage", "player:" + p.uuid());
               boolean drawn = false;
               if (!own.equals("dot")) {
                  g.fill(px - 6, py - 6, px + 6, py + 6, -16777216);
                  g.fill(px - 5, py - 5, px + 5, py + 5, -1);
                  drawn = g.image(own, px - 4, py - 4, 8, 8);
               }

               if (!drawn) {
                  g.fill(px - 2, py - 2, px + 3, py + 3, -16777216);
                  g.fill(px - 1, py - 1, px + 2, py + 2, -1);
               }

               int reach = drawn ? 9 : 5;
               int dx = (int)Math.round(-Math.sin(yaw) * reach);
               int dz = (int)Math.round(Math.cos(yaw) * reach);
               g.fill(px + dx - 1, py + dz - 1, px + dx + 1, py + dz + 1, -16777216);
               g.fill(px + dx, py + dz, px + dx + 1, py + dz + 1, -1);
            }
         }

         g.unclip();
      } else {
         g.fill(box.x(), box.y(), box.x() + box.w(), box.y() + box.h(), 1090519039);
      }
   }
}
