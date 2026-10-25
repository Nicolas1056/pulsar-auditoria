package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hub.HubOption;
import com.metrofico.pulsar.api.hub.HubOptions;
import com.metrofico.pulsar.api.pack.PackClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class HudEditView {
   private static final int DIM = 1711276032;
   private static final int GRID_DOT = 587202559;
   private static final int RESERVED = 587164523;
   private static final int GUIDE = -855649946;
   private static final int OK = -10688932;
   private static final int BAD = -45747;
   private static final int SELECTED = 1728041318;
   private static final double SCALE_MIN = 0.5;
   private static final double SCALE_MAX = 6.0;
   private static final int CHIP_W = 16;
   private static final int CHIP_H = 14;
   private static final int BUTTON_W = 100;
   private static final int BUTTON_H = 20;
   private final HudEditView.Host host;
   private final HudRenderer renderer;
   private final PackClient pack;
   private int chipMinusX = -1000;
   private int chipPlusX = -1000;
   private int chipY = -1000;
   private final List<int[]> buttons = new ArrayList<>();
   private String selected;
   private PanelLayout.Box dragging;
   private double grabDx;
   private double grabDy;
   private double rawX;
   private double rawY;
   private HudLayout.Snap snap;
   private HudLayout.Rect resolved;
   private int width;
   private int height;

   public HudEditView(HudEditView.Host host, HudRenderer renderer, PackClient pack) {
      this.host = host;
      this.renderer = renderer;
      this.pack = pack;
      HudModules.editing = true;
      Canvas m = host.measure();
      this.width = m.width();
      this.height = m.height();
   }

   private static void resetModules() {
      List<String> keys = new ArrayList<>();

      for (HudModule m : HudModules.all()) {
         keys.add(m.id() + ".pos");
         keys.add(m.id() + ".zone");
      }

      ClientOptions.get().reset(keys);
   }

   private List<PanelLayout.Box> boxes() {
      return this.renderer.boxesNow(this.host.measure());
   }

   private static boolean movable(PanelLayout.Box box) {
      return box.panel() != null || !box.id().startsWith("overlay:") && !box.id().startsWith("toast:");
   }

   private static HudLayout.Rect rect(PanelLayout.Box b) {
      return new HudLayout.Rect(b.x(), b.y(), b.width(), b.height());
   }

   private static List<HudLayout.Rect> others(String exceptId, List<PanelLayout.Box> boxes) {
      List<HudLayout.Rect> out = new ArrayList<>();

      for (PanelLayout.Box b : boxes) {
         if (!b.id().equals(exceptId)) {
            out.add(rect(b));
         }
      }

      return out;
   }

   private void recompute(List<PanelLayout.Box> boxes) {
      if (this.dragging != null) {
         List<HudLayout.Rect> others = others(this.dragging.id(), boxes);
         HudLayout.Rect moving = new HudLayout.Rect((int)Math.round(this.rawX), (int)Math.round(this.rawY), this.dragging.width(), this.dragging.height());
         this.snap = HudLayout.snap(moving, others, this.width, this.height, !this.host.shiftDown());
         this.resolved = HudLayout.resolve(moving.at(this.snap.x(), this.snap.y()), others, this.width, this.height);
      }
   }

   public void render(Canvas g, int mouseX, int mouseY) {
      this.width = g.width();
      this.height = g.height();
      g.fill(0, 0, this.width, this.height, 1711276032);
      int coarse = 32;

      for (int x = coarse; x < this.width; x += coarse) {
         g.fill(x, 0, x + 1, this.height, 587202559);
      }

      for (int y = coarse; y < this.height; y += coarse) {
         g.fill(0, y, this.width, y + 1, 587202559);
      }

      if (this.dragging != null && this.snap != null) {
         int fine = 8;
         int radius = 64;
         int cx = this.snap.x() + this.dragging.width() / 2;
         int cy = this.snap.y() + this.dragging.height() / 2;
         int x0 = Math.max(fine, (cx - radius) / fine * fine);
         int y0 = Math.max(fine, (cy - radius) / fine * fine);

         for (int x = x0; x < Math.min(this.width, cx + radius); x += fine) {
            for (int y = y0; y < Math.min(this.height, cy + radius); y += fine) {
               g.fill(x, y, x + 1, y + 1, 587202559);
            }
         }
      }

      for (HudLayout.Rect r : HudLayout.reserved(this.width, this.height)) {
         g.fill(r.x(), r.y(), r.right(), r.bottom(), 587164523);
      }

      List<PanelLayout.Box> boxes = this.renderer.boxesNow(g);
      this.recompute(boxes);

      for (PanelLayout.Box box : boxes) {
         if (this.dragging == null || !box.id().equals(this.dragging.id())) {
            if (box.id().equals(this.selected)) {
               g.fill(box.x() - 2, box.y() - 2, box.x() + box.width() + 2, box.y() + box.height() + 2, 1728041318);
            }

            this.renderer.draw(g, box, box.x(), box.y());
         }
      }

      this.chipMinusX = this.chipPlusX = this.chipY = -1000;
      if (this.dragging == null && this.selected != null) {
         for (PanelLayout.Box boxx : boxes) {
            if (boxx.id().equals(this.selected) && scalable(boxx)) {
               this.drawSizeChips(g, boxx, mouseX, mouseY);
            }
         }
      }

      if (this.dragging != null && this.snap != null) {
         for (int x : this.snap.vertical()) {
            g.fill(x, 0, x + 1, this.height, -855649946);
         }

         for (int y : this.snap.horizontal()) {
            g.fill(0, y, this.width, y + 1, -855649946);
         }

         HudLayout.Rect show = this.resolved != null
            ? this.resolved
            : new HudLayout.Rect(this.snap.x(), this.snap.y(), this.dragging.width(), this.dragging.height());
         int color = this.resolved != null ? -10688932 : -45747;
         g.outline(show.x() - 2, show.y() - 2, show.w() + 4, show.h() + 4, color);
         this.renderer.draw(g, this.dragging, show.x(), show.y());
         String pos = this.resolved != null
            ? Placement.of(show.x(), show.y(), show.w(), show.h(), this.width, this.height).serialize().replace(':', ' ')
            : "sin hueco";
         g.vtext(pos, show.x(), show.y() - 12, color, true);
      }

      String hint1 = this.dragging != null
         ? "Suelta donde esté en verde · Shift: sin rejilla"
         : "Arrastra cualquier elemento: se ajusta a la rejilla y a los demás, sin pisarlos";
      String hint2 = "Tab elige · flechas mueven (Shift: 1 px) · rueda o +/− cambian el tamaño · Esc sale";
      int hy = this.height - 64 - 24;
      g.vtext(hint1, this.width / 2 - g.vwidth(hint1) / 2, hy, -5000269, true);
      g.vtext(hint2, this.width / 2 - g.vwidth(hint2) / 2, hy + 11, -7697782, true);
      this.drawButtons(g, mouseX, mouseY);
   }

   private void drawButtons(Canvas g, int mouseX, int mouseY) {
      this.buttons.clear();
      List<String> labels = new ArrayList<>(List.of("Restablecer"));
      if (this.host.keysScreen() != null) {
         labels.add("Teclas");
      }

      labels.add("Listo");
      int total = labels.size() * 100 + (labels.size() - 1) * 4;
      int x = this.width / 2 - total / 2;
      int y = 6;

      for (int i = 0; i < labels.size(); i++) {
         boolean hover = mouseX >= x && mouseX < x + 100 && mouseY >= y && mouseY < y + 20;
         g.fill(x, y, x + 100, y + 20, hover ? -868464036 : -870441442);
         g.outline(x, y, 100, 20, hover ? -11930 : -7697782);
         String label = labels.get(i);
         g.vtext(label, x + (100 - g.vwidth(label)) / 2, y + 6, hover ? -96 : -2039584, true);
         this.buttons.add(new int[]{x, y, 100, 20, i == 0 ? 0 : (label.equals("Teclas") ? 1 : 2)});
         x += 104;
      }
   }

   private boolean clickButtons(double x, double y) {
      for (int[] b : this.buttons) {
         if (x >= b[0] && x < b[0] + b[2] && y >= b[1] && y < b[1] + b[3]) {
            switch (b[4]) {
               case 0:
                  this.pack.resetLayout();
                  resetModules();
                  break;
               case 1:
                  Runnable keys = this.host.keysScreen();
                  if (keys != null) {
                     keys.run();
                  }
                  break;
               default:
                  this.close();
            }

            return true;
         }
      }

      return false;
   }

   private static boolean scalable(PanelLayout.Box box) {
      return HudModules.isModule(box.widgetId());
   }

   private static double scaleOf(PanelLayout.Box box) {
      HudModule m = HudModules.byWidget(box.widgetId());
      return m == null ? 1.0 : m.scale();
   }

   private void drawSizeChips(Canvas g, PanelLayout.Box box, int mouseX, int mouseY) {
      String label = moduleTitle(box) + " · ×" + trim(scaleOf(box));
      int lw = Math.max(120, g.vwidth(label));
      int total = 24 + lw + 8 + 16;
      int x = this.width / 2 - total / 2;
      int y = 32;
      g.fill(x - 6, y - 3, x + total + 6, y + 14 + 3, -1291845632);
      this.chipMinusX = x;
      this.chipPlusX = x + 16 + 8 + lw + 8;
      this.chipY = y;
      chip(g, this.chipMinusX, y, "−", mouseX, mouseY);
      g.vtext(label, x + 16 + 8 + (lw - g.vwidth(label)) / 2, y + 3, -1, true);
      chip(g, this.chipPlusX, y, "+", mouseX, mouseY);
   }

   private static String moduleTitle(PanelLayout.Box box) {
      String id = box.widgetId().substring("pulsar:".length());

      for (HubOption o : HubOptions.all()) {
         if (o.id().equals(id) || o.params().stream().anyMatch(p -> p.id().equals(id + ".enabled"))) {
            return o.title();
         }
      }

      return id;
   }

   private static void chip(Canvas g, int x, int y, String glyph, int mouseX, int mouseY) {
      boolean hover = mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 14;
      g.fill(x, y, x + 16, y + 14, hover ? -11930 : -870441442);
      g.outline(x, y, 16, 14, hover ? -11930 : -7697782);
      g.vtext(glyph, x + (16 - g.vwidth(glyph)) / 2, y + 3, hover ? -14803426 : -1, false);
   }

   private static String trim(double v) {
      String s = String.format(Locale.ROOT, "%.2f", v);
      return s.endsWith("0") ? s.substring(0, s.length() - 1) : s;
   }

   private void resize(PanelLayout.Box box, double delta) {
      HudModule m = HudModules.byWidget(box.widgetId());
      if (m != null) {
         double next = Math.max(0.5, Math.min(6.0, Math.round((m.scale() + delta) * 100.0) / 100.0));
         if (next != m.scale()) {
            ClientOptions.get().set(m.id() + ".scale", next);
            List<PanelLayout.Box> boxes = this.boxes();

            for (PanelLayout.Box b : boxes) {
               if (b.id().equals(box.id())) {
                  HudLayout.Rect fixed = HudLayout.resolve(rect(b), others(b.id(), boxes), this.width, this.height);
                  if (fixed != null && (fixed.x() != b.x() || fixed.y() != b.y())) {
                     this.pack.movePanel(b.id(), Placement.of(fixed.x(), fixed.y(), fixed.w(), fixed.h(), this.width, this.height));
                  }
               }
            }
         }
      }
   }

   private PanelLayout.Box boxById(String id) {
      for (PanelLayout.Box b : this.boxes()) {
         if (b.id().equals(id)) {
            return b;
         }
      }

      return null;
   }

   public boolean mouseScrolled(double x, double y, double dy) {
      PanelLayout.Box target = null;

      for (PanelLayout.Box box : this.boxes()) {
         if (scalable(box) && x >= box.x() && x < box.x() + box.width() && y >= box.y() && y < box.y() + box.height()) {
            target = box;
         }
      }

      if (target == null && this.selected != null) {
         target = this.boxById(this.selected);
      }

      if (target != null && scalable(target) && dy != 0.0) {
         this.selected = target.id();
         this.resize(target, dy > 0.0 ? 0.1 : -0.1);
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (button != 0) {
         return false;
      } else if (this.clickButtons(x, y)) {
         return true;
      } else {
         if (this.selected != null && y >= this.chipY && y < this.chipY + 14) {
            PanelLayout.Box sel = this.boxById(this.selected);
            if (sel != null && scalable(sel)) {
               if (x >= this.chipMinusX && x < this.chipMinusX + 16) {
                  this.resize(sel, -0.25);
                  return true;
               }

               if (x >= this.chipPlusX && x < this.chipPlusX + 16) {
                  this.resize(sel, 0.25);
                  return true;
               }
            }
         }

         for (PanelLayout.Box box : this.boxes()) {
            if (movable(box) && x >= box.x() && x < box.x() + box.width() && y >= box.y() && y < box.y() + box.height()) {
               this.dragging = box;
               this.selected = box.id();
               this.grabDx = x - box.x();
               this.grabDy = y - box.y();
               this.rawX = box.x();
               this.rawY = box.y();
               return true;
            }
         }

         return false;
      }
   }

   public boolean mouseDragged(double x, double y) {
      if (this.dragging == null) {
         return false;
      } else {
         this.rawX = x - this.grabDx;
         this.rawY = y - this.grabDy;
         return true;
      }
   }

   public boolean mouseReleased() {
      if (this.dragging == null) {
         return false;
      } else {
         this.recompute(this.boxes());
         if (this.resolved != null) {
            this.pack
               .movePanel(this.dragging.id(), Placement.of(this.resolved.x(), this.resolved.y(), this.resolved.w(), this.resolved.h(), this.width, this.height));
         }

         this.dragging = null;
         this.snap = null;
         this.resolved = null;
         return true;
      }
   }

   public boolean keyPressed(HudEditView.Key key) {
      if (key == HudEditView.Key.ESC) {
         this.close();
         return true;
      } else {
         List<PanelLayout.Box> boxes = new ArrayList<>();

         for (PanelLayout.Box b : this.boxes()) {
            if (movable(b)) {
               boxes.add(b);
            }
         }

         if (key == HudEditView.Key.TAB && !boxes.isEmpty()) {
            int i = 0;

            for (int k = 0; k < boxes.size(); k++) {
               if (boxes.get(k).id().equals(this.selected)) {
                  i = k + 1;
               }
            }

            this.selected = boxes.get(i % boxes.size()).id();
            return true;
         } else {
            if (this.selected != null && (key == HudEditView.Key.PLUS || key == HudEditView.Key.MINUS)) {
               PanelLayout.Box sel = this.boxById(this.selected);
               if (sel != null && scalable(sel)) {
                  this.resize(sel, key == HudEditView.Key.MINUS ? -0.25 : 0.25);
                  return true;
               }
            }

            int dx = key == HudEditView.Key.RIGHT ? 1 : (key == HudEditView.Key.LEFT ? -1 : 0);
            int dy = key == HudEditView.Key.DOWN ? 1 : (key == HudEditView.Key.UP ? -1 : 0);
            if ((dx != 0 || dy != 0) && this.selected != null) {
               int step = this.host.shiftDown() ? 1 : 4;

               for (PanelLayout.Box bx : boxes) {
                  if (bx.id().equals(this.selected)) {
                     HudLayout.Rect target = rect(bx).at(bx.x() + dx * step, bx.y() + dy * step);
                     HudLayout.Rect fixed = HudLayout.resolve(target, others(bx.id(), boxes), this.width, this.height);
                     if (fixed != null) {
                        this.pack.movePanel(bx.id(), Placement.of(fixed.x(), fixed.y(), fixed.w(), fixed.h(), this.width, this.height));
                     }
                  }
               }

               return true;
            } else {
               return false;
            }
         }
      }
   }

   public void close() {
      HudModules.editing = false;
      ClientOptions.LOG.accept("pulsar:v1 hud edit closed");
      this.host.close();
   }

   public interface Host {
      Canvas measure();

      boolean shiftDown();

      void close();

      default Runnable keysScreen() {
         return null;
      }
   }

   public static enum Key {
      TAB,
      LEFT,
      RIGHT,
      UP,
      DOWN,
      MINUS,
      PLUS,
      ESC,
      OTHER;
   }
}
