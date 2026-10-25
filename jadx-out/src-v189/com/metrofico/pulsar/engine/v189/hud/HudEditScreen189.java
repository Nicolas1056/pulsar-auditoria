package com.metrofico.pulsar.engine.v189.hud;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hud.HudEditView;
import com.metrofico.pulsar.api.hud.HudEditView.Host;
import com.metrofico.pulsar.api.hud.HudEditView.Key;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.pack.Pack189;
import com.metrofico.pulsar.engine.v189.ui.KeysScreen189;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class HudEditScreen189 extends axu implements Host {
   private final axu parent;
   private final HudEditView view;

   public HudEditScreen189(axu parent) {
      this.parent = parent;
      this.view = new HudEditView(this, Hud189.RENDERER, Pack189.get());
   }

   public Canvas measure() {
      return new Canvas189();
   }

   public boolean shiftDown() {
      return Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54);
   }

   public Runnable keysScreen() {
      return () -> ave.A().a(new KeysScreen189(this));
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
         this.view.mouseDragged(x, y);
      }
   }

   protected void b(int x, int y, int button) {
      if (button == 0) {
         this.view.mouseReleased();
      }
   }

   public void k() {
      super.k();
      int wheel = Mouse.getEventDWheel();
      if (wheel != 0) {
         int x = Mouse.getEventX() * this.l / this.j.d;
         int y = this.m - Mouse.getEventY() * this.m / this.j.e - 1;
         this.view.mouseScrolled(x, y, wheel > 0 ? 1.0 : -1.0);
      }
   }

   protected void a(char character, int code) {
      Key key = switch (code) {
         case 1 -> Key.ESC;
         case 12, 74 -> Key.MINUS;
         case 13, 78 -> Key.PLUS;
         case 15 -> Key.TAB;
         case 200 -> Key.UP;
         case 203 -> Key.LEFT;
         case 205 -> Key.RIGHT;
         case 208 -> Key.DOWN;
         default -> Key.OTHER;
      };
      this.view.keyPressed(key);
   }

   public void close() {
      ave.A().a(this.parent);
   }

   public boolean d() {
      return false;
   }
}
