package com.metrofico.pulsar.engine.v189.hub;

import com.metrofico.pulsar.api.hub.HubHost;
import com.metrofico.pulsar.api.hub.HubView;
import com.metrofico.pulsar.api.hub.HubView.Key;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.hud.HudEditScreen189;
import org.lwjgl.input.Mouse;

public final class HubScreen189 extends axu implements HubHost {
   private final axu parent;
   private final HubView view;

   public HubScreen189(axu parent) {
      this.parent = parent;
      this.view = new HubView(this);
   }

   public HubScreen189(axu parent, String optionId) {
      this.parent = parent;
      this.view = new HubView(this, optionId);
   }

   public void a(int mouseX, int mouseY, float tickDelta) {
      Canvas189 g = new Canvas189();
      this.view.render(g, mouseX, mouseY);
      g.flush();
   }

   protected void a(int x, int y, int button) {
      this.view.mouseClicked(x, y, button);
   }

   protected void a(int x, int y, int button, long duration) {
      if (button == 0) {
         this.view.mouseDragged(x);
      }
   }

   protected void b(int x, int y, int button) {
      if (button == 0) {
         this.view.mouseReleased(x);
      }
   }

   public void k() {
      super.k();
      int wheel = Mouse.getEventDWheel();
      if (wheel != 0) {
         this.view.mouseScrolled(wheel > 0 ? 1.0 : -1.0);
      }
   }

   protected void a(char character, int code) {
      Key key = switch (code) {
         case 1 -> Key.ESC;
         case 14 -> Key.BACKSPACE;
         case 15 -> Key.TAB;
         case 28 -> Key.ENTER;
         default -> Key.OTHER;
      };
      if (!this.view.keyPressed(key, KeyCode.fromLwjgl2(code))) {
         if (character >= ' ' && key == Key.OTHER) {
            this.view.charTyped(character);
         }
      }
   }

   public void close() {
      this.view.closed();
      this.j.a(this.parent);
   }

   public void openHudEditor() {
      this.j.a(new HudEditScreen189(this));
   }

   public boolean d() {
      return true;
   }
}
