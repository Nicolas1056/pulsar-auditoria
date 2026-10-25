package com.metrofico.pulsar.api.pack;

import com.metrofico.pulsar.api.gfx.Canvas;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Consumer;

public final class ConsentView {
   private static final Map<String, String> DOMAINS = Map.of(
      "ui", "Interfaz", "camera", "Cámara", "world", "Mundo", "fx", "Efectos", "audio", "Audio", "xp", "Experiencia"
   );
   private static final int BUTTON_W = 100;
   private static final int BUTTON_H = 20;
   private final ServerRecord server;
   private final PackManifest manifest;
   private final Consumer<PackSession.Consent> onDecision;
   private final Runnable closeScreen;
   private final List<String> lines = new ArrayList<>();
   private final List<int[]> buttons = new ArrayList<>();
   private boolean decided;

   public ConsentView(ServerRecord server, PackManifest manifest, Consumer<PackSession.Consent> onDecision, Runnable closeScreen) {
      this.server = server;
      this.manifest = manifest;
      this.onDecision = onDecision;
      this.closeScreen = closeScreen;
      Map<String, List<String>> byDomain = new TreeMap<>();

      for (Capability c : manifest.capabilities()) {
         byDomain.computeIfAbsent(c.domain(), k -> new ArrayList<>()).add(c.title() + ": " + c.description());
      }

      byDomain.forEach((domain, items) -> {
         this.lines.add(DOMAINS.getOrDefault(domain, domain));
         items.forEach(i -> this.lines.add("  · " + i));
      });
   }

   public void choose(PackSession.Consent c) {
      if (!this.decided) {
         this.decided = true;
         this.closeScreen.run();
         this.onDecision.accept(c);
      }
   }

   public void render(Canvas g, int mouseX, int mouseY) {
      int width = g.width();
      int height = g.height();
      g.fill(0, 0, width, height, -871296751);
      int cx = width / 2;
      String title = this.server.name() + " quiere personalizar tu juego";
      g.vtext(title, cx - g.vwidth(title) / 2, 24, -855310, true);
      g.vtext("Pack \"" + this.manifest.name() + "\" · solo cambia lo que ves y oyes; nunca cómo se juega.", cx - 150, 40, -5000269, true);
      int y = 62;

      for (String line : this.lines) {
         if (y > height - 72) {
            g.vtext("…", cx - 150, y, -8750470, true);
            break;
         }

         g.vtext(line, cx - 150, y, line.startsWith("  ") ? -5000269 : -855310, true);
         y += 11;
      }

      g.vtext("Puedes cambiarlo luego en Pulsar · Ajustes · Servidores. Tecla H: mover los paneles.", cx - 150, height - 70, -8750470, true);
      this.buttons.clear();
      int by = height - 56;
      this.button(g, "Aceptar", cx - 154, by, mouseX, mouseY, 0);
      this.button(g, "Solo informativo", cx - 50, by, mouseX, mouseY, 1);
      this.button(g, "Sin pack", cx + 54, by, mouseX, mouseY, 2);
   }

   private void button(Canvas g, String label, int x, int y, int mouseX, int mouseY, int index) {
      boolean hover = mouseX >= x && mouseX < x + 100 && mouseY >= y && mouseY < y + 20;
      g.fill(x, y, x + 100, y + 20, hover ? -868464036 : -870441442);
      g.outline(x, y, 100, 20, hover ? -11930 : -7697782);
      g.vtext(label, x + (100 - g.vwidth(label)) / 2, y + 6, hover ? -96 : -2039584, true);
      this.buttons.add(new int[]{x, y, index});
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (button != 0) {
         return false;
      } else {
         for (int[] b : this.buttons) {
            if (x >= b[0] && x < b[0] + 100 && y >= b[1] && y < b[1] + 20) {
               this.choose(switch (b[2]) {
                  case 0 -> PackSession.Consent.FULL;
                  case 1 -> PackSession.Consent.INFORMATIVE;
                  default -> PackSession.Consent.NONE;
               });
               return true;
            }
         }

         return false;
      }
   }
}
