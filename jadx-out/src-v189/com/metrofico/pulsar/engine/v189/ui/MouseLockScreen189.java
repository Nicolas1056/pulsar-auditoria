package com.metrofico.pulsar.engine.v189.ui;

import com.metrofico.pulsar.api.input.MouseLock;
import com.metrofico.pulsar.api.input.MouseLockView;
import com.metrofico.pulsar.api.input.MouseLock.Block;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;

public final class MouseLockScreen189 extends axu {
   private final MouseLockView view;

   public MouseLockScreen189(Block block) {
      this.view = new MouseLockView(block, () -> ave.A().m());
   }

   public static void install() {
      MouseLock lock = MouseLock.current();
      if (lock != null) {
         lock.onBlock(b -> ave.A().a(() -> ave.A().a(new MouseLockScreen189(b))));
      }
   }

   public static axu enforce(axu screen) {
      MouseLock lock = MouseLock.current();
      return (axu)(lock != null && lock.blocked() && !(screen instanceof MouseLockScreen189) ? new MouseLockScreen189(lock.block()) : screen);
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
   }

   public boolean d() {
      return false;
   }
}
