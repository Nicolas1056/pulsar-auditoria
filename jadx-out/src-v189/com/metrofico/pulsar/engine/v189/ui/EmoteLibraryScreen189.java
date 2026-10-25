package com.metrofico.pulsar.engine.v189.ui;

import com.metrofico.pulsar.api.emote.EmoteLibraryView;
import com.metrofico.pulsar.engine.v189.emote.V189EmoteHost;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public final class EmoteLibraryScreen189 extends axu implements V189EmoteHost.EmoteScreen189 {
   private final EmoteLibraryView view;

   public EmoteLibraryScreen189(int slot) {
      this.view = new EmoteLibraryView(slot, () -> ave.A().a(null));
   }

   public void a(int mouseX, int mouseY, float tickDelta) {
      Canvas189 g = new Canvas189();
      this.view.render(g, mouseX, mouseY);
      g.flush();
   }

   protected void a(int x, int y, int button) {
      this.view.mouseClicked(x, y, button);
   }

   public void k() {
      super.k();
      int wheel = Mouse.getEventDWheel();
      if (wheel != 0) {
         this.view.mouseScrolled(wheel > 0 ? 1.0 : -1.0);
      }
   }

   public void l() {
      int code = Keyboard.getEventKey();
      char c = Keyboard.getEventCharacter();
      if (Keyboard.getEventKeyState()) {
         char key = EmoteWheelScreen189.keyChar(code);
         if (!this.view.keyPressed(key) && c >= ' ') {
            this.view.charTyped(c);
         }
      } else {
         this.view.keyReleased(EmoteWheelScreen189.keyChar(code));
      }

      this.j.Z();
   }

   protected void a(char character, int code) {
   }

   public void m() {
      this.view.dispose();
   }

   public boolean d() {
      return false;
   }
}
