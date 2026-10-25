package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.pack.PackClient;
import java.util.List;
import java.util.Map;

public final class MenuView {
   private final PackClient pack;
   private final String screenId;
   private final Runnable close;
   private final NodeView renderer;
   private List<Layout.Box> boxes = List.of();
   private String focused;
   private String draggingMap;
   private double lastMouseX;
   private double lastMouseY;

   public MenuView(PackClient pack, String screenId, Runnable close) {
      this.pack = pack;
      this.screenId = screenId;
      this.close = close;
      this.renderer = new NodeView(pack);
   }

   public String screenId() {
      return this.screenId;
   }

   private ScreenDefinition definition() {
      return this.pack.screens().get(this.screenId).orElse(null);
   }

   public void render(Canvas g, int mouseX, int mouseY) {
      ScreenDefinition def = this.definition();
      if (def == null) {
         this.close.run();
      } else {
         if (def.dim()) {
            g.fill(0, 0, g.width(), g.height(), -2013265920);
         }

         this.boxes = NodeView.layout().layout(def.root(), 0, 0, g.width(), g.height());

         for (Layout.Box box : this.boxes) {
            this.renderer.draw(g, box, mouseX, mouseY, this.focused);
         }
      }
   }

   private Layout.Box hit(double mx, double my, Node.Type type) {
      for (int i = this.boxes.size() - 1; i >= 0; i--) {
         Layout.Box b = this.boxes.get(i);
         if (b.node().type() == type && b.contains(mx, my) && b.node().visible()) {
            return b;
         }
      }

      return null;
   }

   private void activate(Node button) {
      if (button.enabled()) {
         String action = button.string("action", "");
         if (!action.isEmpty()) {
            this.pack.sendEvent(ScreenRegistry.event(this.screenId, button.id(), action, Map.of()));
         }
      }
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (button != 0) {
         return false;
      } else {
         Layout.Box b = this.hit(x, y, Node.Type.BUTTON);
         if (b != null) {
            this.focused = b.node().id();
            this.activate(b.node());
            return true;
         } else {
            Layout.Box m = this.hit(x, y, Node.Type.MAP);
            if (m != null) {
               this.draggingMap = m.node().id();
               this.lastMouseX = x;
               this.lastMouseY = y;
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public boolean mouseDragged(double x, double y) {
      if (this.draggingMap != null && this.renderer.mapViews().containsKey(this.draggingMap)) {
         this.renderer.mapViews().get(this.draggingMap).panPixels(x - this.lastMouseX, y - this.lastMouseY);
         this.lastMouseX = x;
         this.lastMouseY = y;
         return true;
      } else {
         return false;
      }
   }

   public void mouseReleased() {
      this.draggingMap = null;
   }

   public boolean mouseScrolled(double mx, double my, double dy) {
      Layout.Box m = this.hit(mx, my, Node.Type.MAP);
      if (m != null && this.renderer.mapViews().containsKey(m.node().id())) {
         this.renderer.mapViews().get(m.node().id()).zoomBy(dy > 0.0 ? 1.25 : 0.8, mx, my);
         return true;
      } else {
         return false;
      }
   }

   public boolean keyPressed(MenuView.Key key, String name) {
      ScreenDefinition def = this.definition();
      if (def != null && def.key() != null && def.key().equals(name)) {
         this.close.run();
         return true;
      } else if (key == MenuView.Key.ESC) {
         if (def != null && !def.closeOnEsc()) {
            return true;
         } else {
            this.close.run();
            return true;
         }
      } else if (key == MenuView.Key.TAB) {
         List<Layout.Box> buttons = this.boxes
            .stream()
            .filter(bx -> bx.node().type() == Node.Type.BUTTON && bx.node().enabled() && bx.node().visible())
            .toList();
         if (!buttons.isEmpty()) {
            int i = 0;

            for (int k = 0; k < buttons.size(); k++) {
               if (buttons.get(k).node().id().equals(this.focused)) {
                  i = k + 1;
               }
            }

            this.focused = buttons.get(i % buttons.size()).node().id();
         }

         return true;
      } else if ((key == MenuView.Key.ENTER || key == MenuView.Key.SPACE) && this.focused != null && def != null) {
         Node b = def.root().find(this.focused);
         if (b != null) {
            this.activate(b);
         }

         return true;
      } else {
         return false;
      }
   }

   public static enum Key {
      ESC,
      TAB,
      ENTER,
      SPACE,
      OTHER;
   }
}
