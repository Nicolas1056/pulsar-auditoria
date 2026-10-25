package com.metrofico.pulsar.engine.v189.ui;

import com.metrofico.pulsar.api.ui.MenuView;
import com.metrofico.pulsar.api.ui.MenuView.Key;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.pack.Pack189;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class MenuScreen189 extends axu {
   private final MenuView view;

   public MenuScreen189(String screenId) {
      this.view = new MenuView(Pack189.get(), screenId, () -> ave.A().a(null));
   }

   public String screenId() {
      return this.view.screenId();
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
      this.view.mouseReleased();
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
         case 15 -> Key.TAB;
         case 28 -> Key.ENTER;
         case 57 -> Key.SPACE;
         default -> Key.OTHER;
      };
      this.view.keyPressed(key, keyName(code));
   }

   public static String keyName(int code) {
      String name = Keyboard.getKeyName(code);
      if (name == null) {
         return null;
      } else if (name.length() == 1 && name.charAt(0) >= 'A' && name.charAt(0) <= 'Z') {
         return name;
      } else {
         return name.matches("F[0-9]{1,2}") ? name : null;
      }
   }

   public boolean d() {
      return false;
   }
}
