package com.metrofico.pulsar.engine.v189.ui;

import com.metrofico.pulsar.api.emote.EmoteWheel;
import com.metrofico.pulsar.api.emote.EmoteWheelView;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.engine.v189.emote.V189EmoteHost;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import org.lwjgl.input.Keyboard;

public final class EmoteWheelScreen189 extends axu implements V189EmoteHost.EmoteScreen189 {
   private final EmoteWheelView view;

   public EmoteWheelScreen189(boolean hold) {
      this.view = new EmoteWheelView(hold, () -> ave.A().a(null));
   }

   static char keyChar(int code) {
      if (EmoteWheel.isKey(KeyCode.fromLwjgl2(code))) {
         return '\u0001';
      } else if (code == 1) {
         return '\u001b';
      } else if (code == 14) {
         return '\b';
      } else {
         String name = Keyboard.getKeyName(code);
         return name != null && name.length() == 1 ? name.charAt(0) : '\u0000';
      }
   }

   public void a(int mouseX, int mouseY, float tickDelta) {
      Canvas189 g = new Canvas189();
      this.view.render(g, mouseX, mouseY);
      g.flush();
   }

   protected void a(int x, int y, int button) {
      if (EmoteWheel.isKey(KeyCode.fromMouse(button))) {
         this.view.keyPressed('\u0001');
      } else {
         this.view.mouseClicked(x, y, button);
      }
   }

   protected void b(int x, int y, int button) {
      if (EmoteWheel.isKey(KeyCode.fromMouse(button))) {
         this.view.keyReleased('\u0001');
      }
   }

   public void l() {
      int code = Keyboard.getEventKey();
      if (Keyboard.getEventKeyState()) {
         this.a(Keyboard.getEventCharacter(), code);
      } else {
         this.view.keyReleased(keyChar(code));
      }

      this.j.Z();
   }

   protected void a(char character, int code) {
      this.view.keyPressed(keyChar(code));
   }

   public boolean d() {
      return false;
   }
}
