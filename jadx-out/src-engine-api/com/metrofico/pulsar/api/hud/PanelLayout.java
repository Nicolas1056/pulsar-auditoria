package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.pack.ImageRef;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.ToIntFunction;

public final class PanelLayout {
   public static final int PADDING = 4;
   public static final int MARGIN = 4;
   public static final int GAP = 4;
   public static final int HOTBAR_RESERVE = 64;
   private final ToIntFunction<String> textWidth;
   private final int lineHeight;

   public PanelLayout(ToIntFunction<String> textWidth, int lineHeight) {
      this.textWidth = textWidth;
      this.lineHeight = lineHeight;
   }

   public List<PanelLayout.Box> layout(List<Panel> panels, int guiWidth, int guiHeight) {
      return this.layout(panels, List.of(), guiWidth, guiHeight);
   }

   public List<PanelLayout.Box> layout(List<Panel> panels, List<PanelLayout.Widget> widgets, int guiWidth, int guiHeight) {
      return this.layout(panels, widgets, Map.of(), guiWidth, guiHeight);
   }

   public List<PanelLayout.Box> layout(List<Panel> panels, List<PanelLayout.Widget> widgets, Map<String, Placement> free, int guiWidth, int guiHeight) {
      Map<Zone, List<PanelLayout.Box>> byZone = new EnumMap<>(Zone.class);
      List<PanelLayout.Box> placed = new ArrayList<>();
      List<HudLayout.Rect> occupied = new ArrayList<>();

      for (PanelLayout.Widget w : widgets) {
         PanelLayout.Box b = new PanelLayout.Box(null, w.id(), 0, 0, w.width(), w.height(), List.of(), List.of());
         Placement pl = free.get(w.id());
         if (pl != null && pl.free()) {
            placed.add(placeFree(b, pl, occupied, guiWidth, guiHeight));
         } else {
            byZone.computeIfAbsent(w.zone(), z -> new ArrayList<>()).add(b);
         }
      }

      for (Panel p : panels) {
         PanelLayout.Box b = this.measure(p);
         Placement pl = free.get(p.id());
         if (pl != null && pl.free()) {
            placed.add(placeFree(b, pl, occupied, guiWidth, guiHeight));
         } else {
            byZone.computeIfAbsent(p.zone(), z -> new ArrayList<>()).add(b);
         }
      }

      List<PanelLayout.Box> out = new ArrayList<>(placed);

      for (Entry<Zone, List<PanelLayout.Box>> e : byZone.entrySet()) {
         Zone zone = e.getKey();
         List<PanelLayout.Box> boxes = e.getValue();
         int total = boxes.stream().mapToInt(PanelLayout.Box::height).sum() + 4 * (boxes.size() - 1);

         int y = switch (zone.row()) {
            case 0 -> 4;
            case 1 -> (guiHeight - total) / 2;
            default -> guiHeight - 4 - total - (zone.column() == 1 ? 64 : 0);
         };

         for (PanelLayout.Box b : boxes) {
            int x = switch (zone.column()) {
               case 0 -> 4;
               case 1 -> (guiWidth - b.width()) / 2;
               default -> guiWidth - 4 - b.width();
            };
            HudLayout.Rect r = new HudLayout.Rect(x, y, b.width(), b.height());
            if (!occupied.isEmpty()) {
               HudLayout.Rect fixed = HudLayout.resolve(r, occupied, guiWidth, guiHeight);
               if (fixed != null) {
                  r = fixed;
               }
            }

            out.add(new PanelLayout.Box(b.panel(), b.widgetId(), r.x(), r.y(), b.width(), b.height(), b.title(), b.lines()));
            y += b.height() + 4;
         }
      }

      return out;
   }

   private static PanelLayout.Box placeFree(PanelLayout.Box b, Placement pl, List<HudLayout.Rect> occupied, int guiWidth, int guiHeight) {
      HudLayout.Rect r = new HudLayout.Rect(pl.x(b.width(), guiWidth), pl.y(b.height(), guiHeight), b.width(), b.height());
      HudLayout.Rect fixed = HudLayout.resolve(r, occupied, guiWidth, guiHeight);
      if (fixed != null) {
         r = fixed;
      } else {
         r = HudLayout.clamp(r, guiWidth, guiHeight);
      }

      occupied.add(r);
      return new PanelLayout.Box(b.panel(), b.widgetId(), r.x(), r.y(), b.width(), b.height(), b.title(), b.lines());
   }

   public static Zone nearestZone(double cx, double cy, int guiWidth, int guiHeight) {
      int column = cx < guiWidth / 3.0 ? 0 : (cx < guiWidth * 2 / 3.0 ? 1 : 2);
      int row = cy < guiHeight / 3.0 ? 0 : (cy < guiHeight * 2 / 3.0 ? 1 : 2);
      if (column == 1 && row == 1) {
         double dx = Math.min(cx, guiWidth - cx);
         double dy = Math.min(cy, guiHeight - cy);
         if (dx < dy) {
            column = cx < guiWidth / 2.0 ? 0 : 2;
         } else {
            row = cy < guiHeight / 2.0 ? 0 : 2;
         }
      }

      for (Zone z : Zone.values()) {
         if (z.column() == column && z.row() == row) {
            return z;
         }
      }

      throw new IllegalStateException();
   }

   private int lineWidth(String raw) {
      Object[] parts = ImageRef.splitLine(raw);
      return this.textWidth.applyAsInt((String)parts[1]) + (parts[0] != null ? this.lineHeight + 2 : 0);
   }

   private PanelLayout.Box measure(Panel p) {
      String title = p.resolvedTitle();
      List<String> titleLines = title.isEmpty() ? List.of() : List.of(title);
      List<String> lines = p.resolvedLines();
      int width = 0;

      for (String s : titleLines) {
         width = Math.max(width, this.lineWidth(s));
      }

      for (String s : lines) {
         width = Math.max(width, this.lineWidth(s));
      }

      int rows = titleLines.size() + lines.size();
      int height = rows == 0 ? 0 : rows * this.lineHeight + (titleLines.isEmpty() ? 0 : 2) + 8;
      return new PanelLayout.Box(p, null, 0, 0, width + 8, height, titleLines, lines);
   }

   public record Box(Panel panel, String widgetId, int x, int y, int width, int height, List<String> title, List<String> lines) {
      public String id() {
         return this.panel != null ? this.panel.id() : this.widgetId;
      }
   }

   public record Widget(String id, Zone zone, int width, int height) {
   }
}
