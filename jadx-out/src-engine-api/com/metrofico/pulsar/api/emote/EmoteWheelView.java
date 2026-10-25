package com.metrofico.pulsar.api.emote;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.gfx.Font;
import java.util.Locale;

public final class EmoteWheelView {
   public static final char ESC = '\u001b';
   public static final char WHEEL = '\u0001';
   private static final int VEIL = 1493172224;
   private static final int TILE_BG = -1039265514;
   private static final int TILE_BG_ON = -434758878;
   private static final int TILE_EMPTY = 1712724770;
   private static final int EDGE = 872415231;
   private static final int EDGE_ON = -855638017;
   private static final int EDGE_EMPTY = 452984831;
   private static final int INK = -1512206;
   private static final int SECONDARY = -6642769;
   private static final int MUTED = -10853262;
   public static final int[] ACCENTS = new int[]{-9525028, -10240087, -7356562, -2509974, -2520454, -3571800, -6518055, -7694424};
   private static final int TILE = 52;
   private static final int RADIUS = 96;
   private static final int DEAD = 28;
   private static final long POP_MS = 90L;
   private static final long TAP_MS = 250L;
   private final Runnable close;
   private final EmoteWheelView.Tile[] tiles = new EmoteWheelView.Tile[8];
   private boolean keyDown;
   private boolean keyArmed;
   private final long openedAt;
   private final long animStart;
   private int highlight = -1;
   private int width;
   private int height;

   public EmoteWheelView(boolean hold, Runnable close) {
      this.keyDown = hold;
      this.keyArmed = !hold;
      this.close = close;
      Canvas m = Emotes.host().measure();
      this.width = m.width();
      this.height = m.height();
      this.refresh();
      this.openedAt = System.nanoTime();
      this.animStart = this.openedAt;
   }

   void refresh() {
      String[] slots = EmoteWheel.slots();

      for (int i = 0; i < 8; i++) {
         EmoteLibrary.Entry e = EmoteLibrary.byId(slots[i]);
         this.tiles[i] = e == null ? null : EmoteWheelView.Tile.of(e);
      }
   }

   private int tileX(int i) {
      double c = Math.cos(angle(i));
      return this.width / 2 + (int)Math.round(c * 96.0) - 26;
   }

   private int tileY(int i) {
      double sn = Math.sin(angle(i));
      return this.height / 2 + (int)Math.round(sn * 96.0) - 26;
   }

   private static double angle(int i) {
      return (Math.PI * 2) * i / 8.0 - (Math.PI / 2);
   }

   public void render(Canvas g, int mouseX, int mouseY) {
      this.width = g.width();
      this.height = g.height();
      g.fill(0, 0, this.width, this.height, 1493172224);
      int cx = this.width / 2;
      int cy = this.height / 2;
      this.highlight = -1;
      double dx = mouseX - cx;
      double dy = mouseY - cy;

      for (int i = 0; i < 8; i++) {
         int x = this.tileX(i);
         int y = this.tileY(i);
         if (mouseX >= x && mouseX < x + 52 && mouseY >= y && mouseY < y + 52) {
            this.highlight = i;
         }
      }

      if (this.highlight < 0 && dx * dx + dy * dy > 784.0) {
         this.highlight = Math.floorMod((int)Math.round((Math.atan2(dy, dx) + (Math.PI / 2)) / (Math.PI / 4)), 8);
      }

      long now = System.nanoTime();

      for (int ix = 0; ix < 8; ix++) {
         float t = Math.min(1.0F, Math.max(0.0F, (float)(now - this.animStart) / 1000000.0F - ix * 18.0F) / 90.0F);
         t = t * t * (3.0F - 2.0F * t);
         this.drawTile(g, ix, this.tileX(ix), this.tileY(ix) + Math.round((1.0F - t) * 8.0F), ix == this.highlight, t);
      }

      this.drawCenter(g, cx, cy);
      String hint = "1–8 directo · clic derecho o B: biblioteca · " + EmoteWheel.keyLabel() + "/Esc: cerrar";
      g.text(Font.REGULAR, hint, cx - g.textWidth(Font.REGULAR, hint, 8.0F) / 2, this.height - 24, 8.0F, -10853262);
      g.pointer(this.highlight >= 0);
   }

   private void drawTile(Canvas g, int slot, int x, int y, boolean on, float pop) {
      EmoteWheelView.Tile tile = this.tiles[slot];
      int grow = on ? 3 : 0;
      int x0 = x - grow;
      int y0 = y - grow;
      int s = 52 + grow * 2;
      int bg = tile == null ? 1712724770 : (on ? -434758878 : -1039265514);
      int edge = tile == null ? 452984831 : (on ? -855638017 : 872415231);
      if (pop < 1.0F) {
         bg = fade(bg, pop);
         edge = fade(edge, pop);
      }

      cutSquare(g, x0, y0, s, bg);
      g.fill(x0 + 2, y0, x0 + s - 2, y0 + 1, edge);
      g.fill(x0 + 2, y0 + s - 1, x0 + s - 2, y0 + s, edge);
      g.fill(x0, y0 + 2, x0 + 1, y0 + s - 2, edge);
      g.fill(x0 + s - 1, y0 + 2, x0 + s, y0 + s - 2, edge);
      if (tile == null) {
         g.text(Font.MEDIUM, "+", x0 + s / 2 - g.textWidth(Font.MEDIUM, "+", 14.0F) / 2, y0 + s / 2 - 8, 14.0F, fade(-10853262, pop));
      } else {
         g.fill(x0 + 2, y0 + 2, x0 + s - 2, y0 + s - 2, tile.accent() & 16777215 | 301989888);
         int mw = g.textWidth(Font.MEDIUM, tile.monogram(), 18.0F);
         g.text(Font.MEDIUM, tile.monogram(), x0 + s / 2 - mw / 2, y0 + s / 2 - 11, 18.0F, fade(tile.accent(), pop));
         g.text(Font.REGULAR, String.valueOf(slot + 1), x0 + 5, y0 + 4, 7.0F, fade(on ? -6642769 : -10853262, pop));
      }
   }

   static void cutSquare(Canvas g, int x, int y, int s, int argb) {
      g.fill(x + 2, y, x + s - 2, y + s, argb);
      g.fill(x, y + 2, x + 2, y + s - 2, argb);
      g.fill(x + s - 2, y + 2, x + s, y + s - 2, argb);
   }

   private void drawCenter(Canvas g, int cx, int cy) {
      EmotePlayer.Active own = EmotePlayer.own();
      boolean running = own != null && own.running();
      EmoteWheelView.Tile t = this.highlight >= 0 ? this.tiles[this.highlight] : null;
      String main;
      String sub;
      if (t != null) {
         main = t.name();
         sub = t.meta() + " · " + (this.keyDown ? "suelta para hacerlo" : "clic para hacerlo");
      } else if (this.highlight >= 0) {
         main = "Hueco vacío";
         sub = "clic para elegir un emote";
      } else if (running) {
         main = "■ Parar";
         sub = this.keyDown ? "suelta para parar" : "clic aquí para parar";
      } else {
         main = "Emotes";
         sub = "elige con el ratón";
      }

      int mw = g.textWidth(Font.MEDIUM, main, 11.0F);
      g.text(Font.MEDIUM, main, cx - mw / 2, cy - 10, 11.0F, -1512206);
      int sw = g.textWidth(Font.REGULAR, sub, 8.0F);
      g.text(Font.REGULAR, sub, cx - sw / 2, cy + 4, 8.0F, -6642769);
   }

   static int fade(int argb, float t) {
      int a = (int)((argb >>> 24 & 0xFF) * t);
      return a << 24 | argb & 16777215;
   }

   private void play(int slot) {
      if (EmoteWheel.play(slot)) {
         this.close.run();
      } else {
         Emotes.host().openLibrary(slot);
      }
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (button == 1) {
         Emotes.host().openLibrary(Math.max(0, this.highlight));
         return true;
      } else if (button != 0) {
         return false;
      } else {
         double dx = x - this.width / 2.0;
         double dy = y - this.height / 2.0;
         if (dx * dx + dy * dy <= 784.0) {
            if (EmotePlayer.stopOwn()) {
               this.close.run();
            }

            return true;
         } else if (this.highlight >= 0) {
            this.play(this.highlight);
            return true;
         } else {
            return false;
         }
      }
   }

   public boolean keyPressed(char key) {
      if (key == 27) {
         this.close.run();
         return true;
      } else if (key >= '1' && key <= '8') {
         this.play(key - '1');
         return true;
      } else if (key == 'B' || key == 'E') {
         Emotes.host().openLibrary(Math.max(0, this.highlight));
         return true;
      } else if (key == 1) {
         if (!this.keyDown && this.keyArmed) {
            this.close.run();
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean keyReleased(char key) {
      if (key != 1) {
         return false;
      } else {
         this.keyArmed = true;
         if (!this.keyDown) {
            return true;
         } else {
            this.keyDown = false;
            long heldMs = (System.nanoTime() - this.openedAt) / 1000000L;
            if (this.highlight >= 0 && this.tiles[this.highlight] != null) {
               this.play(this.highlight);
               return true;
            } else {
               if (heldMs > 250L) {
                  EmotePlayer.stopOwn();
               }

               this.close.run();
               return true;
            }
         }
      }
   }

   record Tile(String id, String name, String monogram, String meta, int accent) {
      static EmoteWheelView.Tile of(EmoteLibrary.Entry e) {
         String name = e.emote().name();
         String meta = (e.emote().duration() >= 10.0 ? Math.round(e.emote().duration()) + " s" : String.format(Locale.ROOT, "%.1f s", e.emote().duration()))
            + (e.emote().loop() ? " · ∞" : "")
            + (e.dev() ? " · dev" : "")
            + (e.network() != null ? " · exclusivo" : "");
         return new EmoteWheelView.Tile(
            e.id(),
            name,
            name.isEmpty() ? "?" : name.substring(0, 1).toUpperCase(Locale.ROOT),
            meta,
            EmoteWheelView.ACCENTS[Math.floorMod(e.id().hashCode(), EmoteWheelView.ACCENTS.length)]
         );
      }
   }
}
