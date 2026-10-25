package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.gfx.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class EmoteLibraryView {
   public static final char ESC = '\u001b';
   public static final char BACKSPACE = '\b';
   private static final int BG = -233959146;
   private static final int EDGE = -14341066;
   private static final int ROW = -15460064;
   private static final int ROW_HOVER = -14998996;
   private static final int HOVER_STRONG = -14472394;
   private static final int INK = -1512206;
   private static final int SECONDARY = -6642769;
   private static final int MUTED = -10853262;
   private static final int SCRIM = 1711276032;
   private static final int SEL = -855638017;
   private static final int HEAD_H = 44;
   private static final int CARD_W = 84;
   private static final int CARD_H = 104;
   private static final int GAP = 12;
   private static final int NAME_H = 15;
   private static final int SLOT_W = 168;
   private static final int SLOT_H = 34;
   private static final int MINI = 26;
   private List<EmoteLibrary.Entry> all = List.of();
   private final List<EmoteLibrary.Entry> filtered = new ArrayList<>();
   private final List<EmoteLibraryView.Hit> hits = new ArrayList<>();
   private String query = "";
   private boolean searching;
   private String selected;
   private int pendingSlot;
   private boolean keyArmed;
   private double scroll;
   private String previewId;
   private int panelX;
   private int panelY;
   private int panelW;
   private int panelH;
   private int gridX;
   private int gridY;
   private int gridW;
   private int gridH;
   private int cols;
   private int slotsX;
   private int width;
   private int height;
   private final Runnable close;
   private boolean closed;

   public EmoteLibraryView(int slot, Runnable close) {
      this.pendingSlot = slot;
      this.close = close;
      Canvas m = Emotes.host().measure();
      this.layout(m.width(), m.height());
   }

   private void layout(int w, int h) {
      this.width = w;
      this.height = h;
      this.all = EmoteLibrary.all();
      this.filter();
      this.panelW = Math.min(640, this.width - 40);
      this.panelH = Math.min(312, this.height - 40);
      this.panelH = Math.max(this.panelH, 410);
      this.panelH = Math.min(this.panelH, this.height - 24);
      this.panelX = (this.width - this.panelW) / 2;
      this.panelY = (this.height - this.panelH) / 2;
      this.slotsX = this.panelX + this.panelW - 16 - 168;
      this.gridX = this.panelX + 16;
      this.gridY = this.panelY + 44 + 14;
      this.gridW = this.slotsX - 18 - this.gridX;
      this.gridH = this.panelY + this.panelH - 16 - this.gridY;
      this.cols = Math.max(1, (this.gridW + 12) / 96);
   }

   private void filter() {
      this.filtered.clear();
      String q = this.query.toLowerCase(Locale.ROOT).trim();

      for (EmoteLibrary.Entry e : this.all) {
         if (q.isEmpty() || e.emote().name().toLowerCase(Locale.ROOT).contains(q) || e.id().contains(q)) {
            this.filtered.add(e);
         }
      }

      this.scroll = 0.0;
   }

   private void hit(int x, int y, int w, int h, Runnable action, boolean clipped) {
      this.hits.add(new EmoteLibraryView.Hit(x, y, w, h, action, clipped));
   }

   private static int accentOf(EmoteLibrary.Entry e) {
      return EmoteWheelView.ACCENTS[Math.floorMod(e.id().hashCode(), EmoteWheelView.ACCENTS.length)];
   }

   private static String monoOf(EmoteLibrary.Entry e) {
      String n = e.emote().name();
      return n.isEmpty() ? "?" : n.substring(0, 1).toUpperCase(Locale.ROOT);
   }

   private static void tile(Canvas g, int x, int y, int w, int h, int argb) {
      g.fill(x + 2, y, x + w - 2, y + h, argb);
      g.fill(x, y + 2, x + 2, y + h - 2, argb);
      g.fill(x + w - 2, y + 2, x + w, y + h - 2, argb);
   }

   private static void ring(Canvas g, int x, int y, int w, int h, int argb) {
      g.fill(x + 2, y, x + w - 2, y + 1, argb);
      g.fill(x + 2, y + h - 1, x + w - 2, y + h, argb);
      g.fill(x, y + 2, x + 1, y + h - 2, argb);
      g.fill(x + w - 1, y + 2, x + w, y + h - 2, argb);
   }

   public void render(Canvas g, int mouseX, int mouseY) {
      if (g.width() != this.width || g.height() != this.height) {
         this.layout(g.width(), g.height());
      }

      this.hits.clear();
      g.fill(0, 0, this.width, this.height, 1711276032);
      g.fill(this.panelX, this.panelY, this.panelX + this.panelW, this.panelY + this.panelH, -233959146);
      ring(g, this.panelX, this.panelY, this.panelW, this.panelH, -14341066);
      int cyHead = this.panelY + 22;
      int tx = this.panelX + 16;
      tx += g.text(Font.MEDIUM, "Emotes", tx, cyHead - 8, 13.0F, -1512206) + 12;
      String count = this.query.isEmpty() ? this.all.size() + " emotes comprados" : this.filtered.size() + " de " + this.all.size();
      g.text(Font.MEDIUM, count, tx, cyHead - 6, 10.0F, -6642769);
      int sw = 190;
      int sx = this.panelX + this.panelW - 16 - sw;
      int sy = cyHead - 11;
      int sh = 22;
      g.fill(sx, sy, sx + sw, sy + sh, this.searching ? -14998996 : -15460064);
      g.fill(sx, sy + sh - 1, sx + sw, sy + sh, this.searching ? -855638017 : -14341066);
      g.icon("search", sx + 6, sy + 5, 11, this.searching ? -6642769 : -10853262);
      String shown = this.query.isEmpty() && !this.searching ? "Buscar" : this.query + (this.searching ? "_" : "");
      g.text(Font.REGULAR, shown, sx + 22, sy + 6, 9.0F, this.query.isEmpty() && !this.searching ? -10853262 : -1512206);
      this.hit(sx, sy, sw, sh, () -> this.searching = true, false);
      g.fill(this.panelX + 1, this.panelY + 44, this.panelX + this.panelW - 1, this.panelY + 44 + 1, -14341066);
      this.drawGrid(g, mouseX, mouseY);
      this.drawSlots(g, mouseX, mouseY);
      boolean hovering = false;

      for (EmoteLibraryView.Hit h : this.hits) {
         if (h.contains(mouseX, mouseY) && (!h.clipped() || mouseY >= this.gridY && mouseY < this.gridY + this.gridH)) {
            hovering = true;
            break;
         }
      }

      g.pointer(hovering);
   }

   private void drawGrid(Canvas g, int mouseX, int mouseY) {
      if (this.filtered.isEmpty()) {
         this.stopPreview();
         String main = this.all.isEmpty() ? "Tu guardarropa está vacío" : "Nada coincide con la búsqueda";
         String sub = this.all.isEmpty() ? "Consigue emotes en el Marketplace del launcher o deja un archivo en tu carpeta dev" : "Prueba con otro nombre";
         int cy = this.gridY + this.gridH / 2;
         g.text(Font.MEDIUM, main, this.gridX + (this.gridW - g.textWidth(Font.MEDIUM, main, 11.0F)) / 2, cy - 12, 11.0F, -6642769);
         g.text(Font.REGULAR, sub, this.gridX + (this.gridW - g.textWidth(Font.REGULAR, sub, 9.0F)) / 2, cy + 4, 9.0F, -10853262);
      } else {
         int rowH = 131;
         int rows = (this.filtered.size() + this.cols - 1) / this.cols;
         int maxScroll = Math.max(0, rows * rowH - 12 - this.gridH);
         this.scroll = Math.max(0.0, Math.min(this.scroll, (double)maxScroll));
         int inset = (this.gridW - (this.cols * 96 - 12)) / 2;
         EmoteLibrary.Entry hovered = null;
         g.clip(this.gridX, this.gridY, this.gridX + this.gridW, this.gridY + this.gridH);
         g.push();
         g.translate(0.0F, (float)(-this.scroll));
         int first = Math.max(0, (int)(this.scroll / rowH) * this.cols);
         int last = Math.min(this.filtered.size(), first + (this.gridH / rowH + 3) * this.cols);

         for (int i = first; i < last; i++) {
            EmoteLibrary.Entry e = this.filtered.get(i);
            int x = this.gridX + inset + i % this.cols * 96;
            int y = this.gridY + i / this.cols * rowH;
            boolean sel = e.id().equals(this.selected);
            boolean hov = mouseX >= x
               && mouseX < x + 84
               && mouseY + this.scroll >= y
               && mouseY + this.scroll < y + 104
               && mouseY >= this.gridY
               && mouseY < this.gridY + this.gridH;
            if (hov) {
               hovered = e;
            }

            int accent = accentOf(e);
            tile(g, x, y, 84, 104, !sel && !hov ? -15460064 : -14998996);
            tile(g, x, y, 84, 104, accent & 16777215 | 234881024);
            ring(g, x, y, 84, 104, sel ? -855638017 : (hov ? 1442840575 : 587202559));
            if (!hov || !Emotes.host().drawOwnPlayer(g, x + 3, y + 3, x + 84 - 3, y + 104 - 3, 34, x + 42.0F, y + 52.0F)) {
               String mono = monoOf(e);
               g.text(Font.MEDIUM, mono, x + (84 - g.textWidth(Font.MEDIUM, mono, 24.0F)) / 2, y + 52 - 14, 24.0F, accent);
            }

            if (e.network() != null) {
               g.icon("shield", x + 5, y + 5, 8, -6642769);
            } else if (e.dev()) {
               g.text(Font.REGULAR, "dev", x + 6, y + 5, 7.0F, -6642769);
            }

            if (e.emote().loop()) {
               g.text(Font.REGULAR, "∞", x + 84 - 6 - g.textWidth(Font.REGULAR, "∞", 8.0F), y + 5, 8.0F, -6642769);
            }

            int inSlot = EmoteWheel.slotOf(e.id());
            if (inSlot >= 0) {
               String n = String.valueOf(inSlot + 1);
               tile(g, x + 84 - 15, y + 104 - 15, 13, 13, -14472394);
               g.text(Font.MEDIUM, n, x + 84 - 15 + (13 - g.textWidth(Font.MEDIUM, n, 8.0F)) / 2, y + 104 - 12, 8.0F, -1512206);
            }

            String name = ellipsis(g, e.emote().name(), 9.0F, 92);
            g.text(Font.REGULAR, name, x + (84 - g.textWidth(Font.REGULAR, name, 9.0F)) / 2, y + 104 + 4, 9.0F, !hov && !sel ? -6642769 : -1512206);
            String id = e.id();
            this.hit(x, (int)(y - this.scroll), 84, 104, () -> this.pick(id), true);
         }

         g.pop();
         g.unclip();
         this.preview(hovered);
         if (maxScroll > 0) {
            int barH = Math.max(20, this.gridH * this.gridH / (rows * rowH));
            int barY = this.gridY + (int)((this.gridH - barH) * (this.scroll / maxScroll));
            g.fill(this.gridX + this.gridW + 5, this.gridY, this.gridX + this.gridW + 7, this.gridY + this.gridH, -15460064);
            g.fill(this.gridX + this.gridW + 5, barY, this.gridX + this.gridW + 7, barY + barH, -14341066);
         }
      }
   }

   private void preview(EmoteLibrary.Entry hovered) {
      int id = Emotes.host().ownEntityId();
      if (id >= 0) {
         if (hovered == null) {
            this.stopPreview();
         } else {
            EmotePlayer.Active active = EmotePlayer.active(id);
            boolean playing = active != null && active.running() && active.emote.id().equals(hovered.id());
            if (!playing) {
               EmotePlayer.play(id, hovered.emote(), 0L, false);
               this.previewId = hovered.id();
            }
         }
      }
   }

   private void stopPreview() {
      int id = Emotes.host().ownEntityId();
      if (this.previewId != null && id >= 0) {
         EmotePlayer.Active active = EmotePlayer.active(id);
         if (active != null && active.emote.id().equals(this.previewId)) {
            EmotePlayer.stop(id);
         }

         this.previewId = null;
      }
   }

   private void drawSlots(Canvas g, int mouseX, int mouseY) {
      int y0 = this.gridY;
      g.text(Font.MEDIUM, "Acceso rápido", this.slotsX, y0 - 4, 10.0F, -6642769);
      String[] slots = EmoteWheel.slots();

      for (int i = 0; i < 8; i++) {
         int sy = y0 + 12 + i * 40;
         EmoteLibrary.Entry e = EmoteLibrary.byId(slots[i]);
         boolean hov = mouseX >= this.slotsX && mouseX < this.slotsX + 168 && mouseY >= sy && mouseY < sy + 34;
         boolean target = this.selected != null || this.pendingSlot == i;
         tile(g, this.slotsX, sy, 168, 34, hov ? -14998996 : -15460064);
         ring(g, this.slotsX, sy, 168, 34, this.pendingSlot == i ? -855638017 : (target && hov ? -855638017 : (hov ? 1442840575 : 587202559)));
         int mx = this.slotsX + 4;
         int my = sy + 4;
         if (e != null) {
            int accent = accentOf(e);
            tile(g, mx, my, 26, 26, -14998996);
            tile(g, mx, my, 26, 26, accent & 16777215 | 335544320);
            String mono = monoOf(e);
            g.text(Font.MEDIUM, mono, mx + (26 - g.textWidth(Font.MEDIUM, mono, 11.0F)) / 2, my + 13 - 7, 11.0F, accent);
         } else {
            tile(g, mx, my, 26, 26, -15460064);
            ring(g, mx, my, 26, 26, 452984831);
            g.text(Font.REGULAR, "+", mx + (26 - g.textWidth(Font.REGULAR, "+", 10.0F)) / 2, my + 13 - 6, 10.0F, -10853262);
         }

         String num = String.valueOf(i + 1);
         g.text(Font.MEDIUM, num, this.slotsX + 168 - 8 - g.textWidth(Font.MEDIUM, num, 10.0F), sy + 17 - 6, 10.0F, -6642769);
         String label = e == null ? (target ? "ponerlo aquí" : "vacío") : ellipsis(g, e.emote().name(), 9.0F, 116);
         g.text(Font.REGULAR, label, mx + 26 + 6, sy + 17 - 5, 9.0F, e == null ? -10853262 : -1512206);
         int slot = i;
         this.hit(this.slotsX, sy, 168, 34, () -> this.assign(slot), false);
      }
   }

   private void pick(String id) {
      if (this.pendingSlot >= 0) {
         EmoteWheel.assign(this.pendingSlot, id);
         this.pendingSlot = -1;
         this.selected = null;
      } else {
         this.selected = id.equals(this.selected) ? null : id;
      }
   }

   private void assign(int slot) {
      if (this.selected != null) {
         EmoteWheel.assign(slot, this.selected);
         this.selected = null;
      } else {
         this.pendingSlot = this.pendingSlot == slot ? -1 : slot;
      }
   }

   private String nameOf(String id) {
      EmoteLibrary.Entry e = EmoteLibrary.byId(id);
      return e == null ? id : e.emote().name();
   }

   private static String ellipsis(Canvas g, String s, float size, int max) {
      if (g.textWidth(Font.REGULAR, s, size) <= max) {
         return s;
      } else {
         String e = s;

         while (e.length() > 1 && g.textWidth(Font.REGULAR, e + "…", size) > max) {
            e = e.substring(0, e.length() - 1);
         }

         return e + "…";
      }
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (button == 1) {
         for (int i = 0; i < 8; i++) {
            int sy = this.gridY + 12 + i * 40;
            if (x >= this.slotsX && x < this.slotsX + 168 && y >= sy && y < sy + 34) {
               EmoteWheel.assign(i, null);
               return true;
            }
         }

         return true;
      } else if (button != 0) {
         return false;
      } else {
         this.searching = false;

         for (int ix = this.hits.size() - 1; ix >= 0; ix--) {
            EmoteLibraryView.Hit h = this.hits.get(ix);
            if ((!h.clipped() || !(y < this.gridY) && !(y >= this.gridY + this.gridH)) && h.contains(x, y)) {
               h.action().run();
               return true;
            }
         }

         return false;
      }
   }

   public boolean mouseScrolled(double dy) {
      this.scroll -= dy * 30.0;
      return true;
   }

   public boolean keyPressed(char key) {
      if (key == 27) {
         this.close();
         return true;
      } else if (this.searching && key == '\b') {
         if (!this.query.isEmpty()) {
            this.query = this.query.substring(0, this.query.length() - 1);
            this.filter();
         }

         return true;
      } else if (!this.searching && this.selected != null && key >= '1' && key <= '8') {
         this.assign(key - '1');
         return true;
      } else if (!this.searching && (key == 'B' || key == 1)) {
         if (key != 1 || this.keyArmed) {
            this.stopPreview();
            Emotes.host().openWheel(false);
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean keyReleased(char key) {
      if (key == 1) {
         this.keyArmed = true;
         return true;
      } else {
         return false;
      }
   }

   public boolean charTyped(int codepoint) {
      char c = (char)codepoint;
      if (this.searching && c >= ' ' && this.query.length() < 32) {
         this.query = this.query + c;
         this.filter();
         return true;
      } else {
         return false;
      }
   }

   public void close() {
      if (!this.closed) {
         this.closed = true;
         this.stopPreview();
         this.close.run();
      }
   }

   public void dispose() {
      this.closed = true;
      this.stopPreview();
   }

   private record Hit(int x, int y, int w, int h, Runnable action, boolean clipped) {
      boolean contains(double px, double py) {
         return px >= this.x && px < this.x + this.w && py >= this.y && py < this.y + this.h;
      }
   }
}
