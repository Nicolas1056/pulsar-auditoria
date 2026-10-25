package com.metrofico.pulsar.api.ui;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.pack.PackClient;
import java.util.ArrayList;
import java.util.List;

public final class KeysView {
   private static final int BUTTON_H = 20;
   private final PackClient pack;
   private final Runnable close;
   private final List<int[]> buttons = new ArrayList<>();
   private final List<String> buttonScreens = new ArrayList<>();
   private String waiting;
   private String error;

   public KeysView(PackClient pack, Runnable close) {
      this.pack = pack;
      this.close = close;
   }

   private List<ScreenDefinition> screens() {
      return this.pack.screens().all().stream().filter(s -> !s.overlay()).toList();
   }

   public void render(Canvas g, int mouseX, int mouseY) {
      int width = g.width();
      int height = g.height();
      g.fill(0, 0, width, height, -871296751);
      String title = "Teclas de " + this.pack.serverName();
      g.vtext(title, width / 2 - g.vwidth(title) / 2, 20, -855310, true);
      this.buttons.clear();
      this.buttonScreens.clear();
      int y = 50;

      for (ScreenDefinition s : this.screens()) {
         String key = this.pack.screens().keyOf(s);
         boolean changed = this.pack.screens().chosenKeys().containsKey(s.id());
         g.vtext(s.title().isEmpty() ? s.id() : s.title(), width / 2 - 140, y, -855310, true);
         String shown = s.id().equals(this.waiting) ? "pulsa una tecla…" : (key == null ? "—" : key + (changed ? " (tuya)" : ""));
         g.vtext(shown, width / 2 - 20, y, s.id().equals(this.waiting) ? -11930 : (changed ? -8587872 : -5000269), true);
         this.button(g, "Cambiar", width / 2 + 40, y - 4, 70, 18, mouseX, mouseY, this.buttonScreens.size());
         this.buttonScreens.add(s.id());
         y += 22;
      }

      String hint = this.error != null ? this.error : "Letras A–Z (salvo W A S D E Q F T) o F5–F12 · Esc cancela";
      g.vtext(hint, width / 2 - g.vwidth(hint) / 2, height - 60, this.error != null ? -38037 : -7697782, true);
      this.button(g, "Restablecer", width / 2 - 102, height - 40, 100, 20, mouseX, mouseY, -1);
      this.button(g, "Listo", width / 2 + 2, height - 40, 100, 20, mouseX, mouseY, -2);
   }

   private void button(Canvas g, String label, int x, int y, int w, int h, int mouseX, int mouseY, int index) {
      boolean hover = mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + h;
      g.fill(x, y, x + w, y + h, hover ? -868464036 : -870441442);
      g.outline(x, y, w, h, hover ? -11930 : -7697782);
      g.vtext(label, x + (w - g.vwidth(label)) / 2, y + (h - 8) / 2, hover ? -96 : -2039584, true);
      this.buttons.add(new int[]{x, y, w, h, index});
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (button != 0) {
         return false;
      } else {
         for (int[] b : this.buttons) {
            if (x >= b[0] && x < b[0] + b[2] && y >= b[1] && y < b[1] + b[3]) {
               if (b[4] == -2) {
                  this.close.run();
               } else if (b[4] == -1) {
                  this.pack.resetKeys();
                  this.waiting = null;
               } else {
                  this.waiting = this.buttonScreens.get(b[4]);
                  this.error = null;
               }

               return true;
            }
         }

         return false;
      }
   }

   public boolean keyPressed(boolean esc, String name) {
      if (this.waiting == null) {
         if (esc) {
            this.close.run();
            return true;
         } else {
            return false;
         }
      } else if (esc) {
         this.waiting = null;
         return true;
      } else {
         String key = ScreenDefinition.normalizeKey(name);
         if (key == null) {
            this.error = "Esa tecla no se puede usar";
            return true;
         } else {
            for (ScreenDefinition other : this.screens()) {
               if (!other.id().equals(this.waiting) && key.equals(this.pack.screens().keyOf(other))) {
                  this.error = "La " + key + " ya abre " + (other.title().isEmpty() ? other.id() : other.title());
                  return true;
               }
            }

            if (key.equals("H")) {
               this.error = "La H es para mover paneles";
               return true;
            } else {
               this.pack.bindKey(this.waiting, key);
               this.waiting = null;
               this.error = null;
               return true;
            }
         }
      }
   }
}
