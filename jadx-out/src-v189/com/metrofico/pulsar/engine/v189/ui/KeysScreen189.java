package com.metrofico.pulsar.engine.v189.ui;

import com.metrofico.pulsar.api.ui.KeysView;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.pack.Pack189;

public final class KeysScreen189 extends axu {
   private final axu parent;
   private final KeysView view;

   public KeysScreen189(axu parent) {
      this.parent = parent;
      this.view = new KeysView(Pack189.get(), () -> ave.A().a(parent));
   }

   public void a(int mouseX, int mouseY, float tickDelta) {
      Canvas189 g = new Canvas189();
      this.view.render(g, mouseX, mouseY);
      g.flush();
   }

   protected void a(int x, int y, int button) {
      this.view.mouseClicked(x, y, button);
   }

   protected void a(char character, int code) {
      this.view.keyPressed(code == 1, MenuScreen189.keyName(code));
   }

   public boolean d() {
      return false;
   }
}
