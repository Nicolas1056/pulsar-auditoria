package com.metrofico.pulsar.engine.v189.hud;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudBackground;
import com.metrofico.pulsar.api.hud.HudDefaults;
import com.metrofico.pulsar.api.hud.HudModule;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.PixelInfinity;
import com.metrofico.pulsar.api.hud.Zone;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class Effects189 implements HudModule {
   private static final jy INVENTORY = new jy("textures/gui/container/inventory.png");
   private static final String[] ROMAN = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
   private static final int GAP = 2;
   private static final int ENDLESS = 32147;
   private static List<pf> sample;
   private static List<pf> memo = List.of();
   private static int memoTick = -1;
   private static boolean memoEditing;

   public String id() {
      return "effects";
   }

   public Zone zone() {
      return ClientOptions.get().zone("effects.zone", HudDefaults.zone("effects", Zone.TOP_RIGHT));
   }

   private static List<pf> sample() {
      if (sample == null) {
         sample = List.of(new pf(pe.c.H, 1200, 1));
      }

      return sample;
   }

   private static List<pf> effects() {
      int tick = HudModules.TICK;
      if (tick != memoTick || memoEditing != HudModules.editing) {
         memo = computeEffects();
         memoTick = tick;
         memoEditing = HudModules.editing;
      }

      return memo;
   }

   private static List<pf> computeEffects() {
      ave mc = ave.A();
      if (mc.h == null) {
         return HudModules.editing ? sample() : List.of();
      } else {
         List<pf> out = new ArrayList<>();

         for (pf e : mc.h.bl()) {
            if (effect(e).e()) {
               out.add(e);
            }
         }

         return out.isEmpty() && HudModules.editing ? sample() : out;
      }
   }

   private static pe effect(pf e) {
      return pe.a[e.a()];
   }

   public boolean present() {
      return !effects().isEmpty();
   }

   private boolean vertical() {
      return !"horizontal".equals(ClientOptions.get().string("effects.layout", "vertical"));
   }

   private boolean texts() {
      ClientOptions o = ClientOptions.get();
      return o.bool("effects.name", true) || o.bool("effects.time", true);
   }

   private int entryWidth(Canvas c, pf e) {
      if (!this.texts()) {
         return 24;
      } else {
         boolean bold = "bold".equals(ClientOptions.get().string("effects.textWeight", "normal"));
         String t = time(e);
         int tw = Math.max(c.vwidth(styled(name(e), bold)), "∞".equals(t) ? 10 : c.vwidth(styled(t, bold)));
         return 28 + tw + 4;
      }
   }

   private static String name(pf e) {
      ClientOptions o = ClientOptions.get();
      if (!o.bool("effects.name", true)) {
         return "";
      } else {
         String n = bnq.a(e.g(), new Object[0]);
         int amp = e.c();
         if (o.bool("effects.level", true) && amp > 0) {
            n = n + " " + (amp + 1 < ROMAN.length ? ROMAN[amp + 1] : Integer.toString(amp + 1));
         }

         return n.toUpperCase(Locale.ROOT);
      }
   }

   private static String time(pf e) {
      if (!ClientOptions.get().bool("effects.time", true)) {
         return "";
      } else if (e.b() >= 32147) {
         return "∞";
      } else {
         int s = e.b() / 20;
         return s >= 3600 ? String.format("%d:%02d:%02d", s / 3600, s / 60 % 60, s % 60) : String.format("%d:%02d", s / 60, s % 60);
      }
   }

   public int width(Canvas c) {
      List<pf> list = effects();
      int w = 0;

      for (pf e : list) {
         w = this.vertical() ? Math.max(w, this.entryWidth(c, e)) : w + this.entryWidth(c, e) + 2;
      }

      if (!this.vertical() && !list.isEmpty()) {
         w -= 2;
      }

      return (int)Math.ceil(w * this.scale());
   }

   public int height(Canvas c) {
      int n = effects().size();
      int h = this.vertical() ? n * 24 + Math.max(0, n - 1) * 2 : 24;
      return (int)Math.ceil(h * this.scale());
   }

   private static String styled(String s, boolean bold) {
      return bold && !s.isEmpty() ? "§l" + s : s;
   }

   private static void text(Canvas c, String s, int x, int y, int argb, boolean bold, boolean shadow) {
      if ("∞".equals(s)) {
         PixelInfinity.draw(c, x, y, argb, shadow);
      } else {
         c.vtext(styled(s, bold), x, y, argb, shadow);
      }
   }

   public void draw(Canvas c, int x, int y) {
      Canvas189 g = (Canvas189)c;
      ClientOptions o = ClientOptions.get();
      float s = (float)this.scale();
      boolean bg = o.bool("effects.background", true);
      boolean border = o.bool("effects.border", false);
      int fill = HudBackground.color("effects.opacity");
      int ambientFill = HudBackground.color("effects.opacity", 2107962);
      boolean bold = "bold".equals(o.string("effects.textWeight", "normal"));
      int timeColor = bold ? 13160666 : 10134447;
      boolean blink = o.bool("effects.blink", true);
      c.push();
      c.translate(x, y);
      c.scale(s, s);
      int ex = 0;
      int ey = 0;

      for (pf e : effects()) {
         int w = this.entryWidth(c, e);
         float alpha = 1.0F;
         if (blink && e.b() < 32147 && e.b() <= 200) {
            int d = e.b();
            alpha = d / 5 % 2 == 0 ? 1.0F : 0.35F;
         }

         int a = Math.round(alpha * 255.0F) << 24;
         if (bg) {
            c.fill(ex, ey, ex + 24, ey + 24, e.e() ? ambientFill : fill);
            if (w > 24) {
               c.fill(ex + 24, ey, ex + w, ey + 24, fill);
            }
         }

         if (border) {
            c.outline(ex, ey, 24, 24, e.e() ? -12957088 : -14013910);
         }

         int idx = effect(e).f();
         float u = idx % 8 * 18 / 256.0F;
         float v = (198 + idx / 8 * 18) / 256.0F;
         g.textured(INVENTORY, ex + 3, ey + 3, 18, 18, u, v, u + 0.0703125F, v + 0.0703125F, a | 16777215);
         if (w > 24) {
            String n = name(e);
            String t = time(e);
            if (!n.isEmpty() && !t.isEmpty()) {
               text(c, n, ex + 28, ey + 3, a | 15265010, bold, !bg);
               text(c, t, ex + 28, ey + 13, a | timeColor, bold, !bg);
            } else {
               String only = n.isEmpty() ? t : n;
               text(c, only, ex + 28, ey + 8, a | 15265010, bold, !bg);
            }
         }

         if (this.vertical()) {
            ey += 26;
         } else {
            ex += w + 2;
         }
      }

      c.pop();
   }
}
