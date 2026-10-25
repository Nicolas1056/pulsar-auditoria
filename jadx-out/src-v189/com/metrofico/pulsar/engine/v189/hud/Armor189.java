package com.metrofico.pulsar.engine.v189.hud;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudBackground;
import com.metrofico.pulsar.api.hud.HudDefaults;
import com.metrofico.pulsar.api.hud.HudModule;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.HudText;
import com.metrofico.pulsar.api.hud.Zone;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.perf.GlState189;
import java.util.ArrayList;
import java.util.List;

public final class Armor189 implements HudModule {
   private static final int ROW = 18;
   private static final int GAP = 2;
   static final int TEXT_SIZE_PERCENT = 75;
   private static List<zx> sample;
   private static List<zx> memo = List.of();
   private static int memoTick = -1;
   private static boolean memoEditing;

   public String id() {
      return "armor";
   }

   public Zone zone() {
      return ClientOptions.get().zone("armor.zone", HudDefaults.zone("armor", Zone.LEFT));
   }

   private static List<zx> sample() {
      if (sample == null) {
         sample = List.of(new zx(zy.ac), new zx(zy.ad), new zx(zy.ae), new zx(zy.af));
      }

      return sample;
   }

   private static List<zx> items() {
      int tick = HudModules.TICK;
      if (tick != memoTick || memoEditing != HudModules.editing) {
         memo = computeItems();
         memoTick = tick;
         memoEditing = HudModules.editing;
      }

      return memo;
   }

   private static List<zx> computeItems() {
      ave mc = ave.A();
      if (mc.h == null) {
         return HudModules.editing ? sample() : List.of();
      } else {
         ClientOptions o = ClientOptions.get();
         boolean hideEmpty = o.bool("armor.hideEmpty", true);
         List<zx> out = new ArrayList<>();

         for (int slot = 3; slot >= 0; slot--) {
            zx st = mc.h.bi.e(slot);
            if (st != null || !hideEmpty) {
               out.add(st);
            }
         }

         if (o.bool("armor.hands", false)) {
            zx st = mc.h.bi.h();
            if (st != null || !hideEmpty) {
               out.add(st);
            }
         }

         boolean any = false;

         for (zx st : out) {
            any |= st != null;
         }

         return !any && HudModules.editing ? sample() : out;
      }
   }

   public boolean present() {
      for (zx s : items()) {
         if (s != null) {
            return true;
         }
      }

      return false;
   }

   private boolean vertical() {
      return !"horizontal".equals(ClientOptions.get().string("armor.layout", "vertical"));
   }

   private static String label(zx s) {
      if (s == null) {
         return "";
      } else if (!s.e()) {
         return s.b > 1 ? Integer.toString(s.b) : "";
      } else {
         int left = s.j() - s.h();
         String var2 = ClientOptions.get().string("armor.durability", "number");

         return switch (var2) {
            case "percent" -> Math.round(100.0F * left / s.j()) + "%";
            case "bar" -> "";
            default -> Integer.toString(left);
         };
      }
   }

   private float textRatio(Canvas c) {
      return HudText.crispRatio(c, this.scale(), ClientOptions.get().integer("armor.textSize", 75) / 100.0);
   }

   private int entryWidth(Canvas c, zx s) {
      String l = label(s);
      return 16 + (l.isEmpty() ? 0 : 3 + (int)Math.ceil(c.vwidth(l) * this.textRatio(c))) + 2;
   }

   private int innerWidth(Canvas c) {
      int w = 0;
      List<zx> list = items();

      for (zx s : list) {
         w = this.vertical() ? Math.max(w, this.entryWidth(c, s)) : w + this.entryWidth(c, s) + 2;
      }

      if (!this.vertical() && !list.isEmpty()) {
         w -= 2;
      }

      return w;
   }

   private int innerHeight() {
      int n = items().size();
      return this.vertical() ? n * 18 + Math.max(0, n - 1) * 2 : 18;
   }

   public int width(Canvas c) {
      return (int)Math.ceil(this.innerWidth(c) * this.scale());
   }

   public int height(Canvas c) {
      return (int)Math.ceil(this.innerHeight() * this.scale());
   }

   private static int wearColor(zx s) {
      if (ClientOptions.get().bool("armor.colorize", true) && s.e()) {
         float f = 1.0F - (float)s.h() / s.j();
         int r = f < 0.5F ? 255 : Math.round(255.0F * (1.0F - f) * 2.0F);
         int gr = f > 0.5F ? 255 : Math.round(255.0F * f * 2.0F);
         return 0xFF000000 | r << 16 | gr << 8 | 64;
      } else {
         return -1512206;
      }
   }

   public void draw(Canvas c, int x, int y) {
      boolean bg = ClientOptions.get().bool("armor.background", true);
      int fill = HudBackground.color("armor.opacity");
      ave mc = ave.A();
      float s = (float)this.scale();
      boolean bar = "bar".equals(ClientOptions.get().string("armor.durability", "number"));
      c.push();
      c.translate(x, y);
      c.scale(s, s);
      if (bg) {
         c.fill(0, 0, this.innerWidth(c), this.innerHeight(), fill);
      }

      int ex = 0;
      int ey = 0;

      for (zx st : items()) {
         int w = this.entryWidth(c, st);
         if (st != null) {
            int ix = ex;
            int iy = ey + 1;
            ((Canvas189)c).direct(() -> {
               boolean depthWasOn = GlState189.depthTestEnabled();
               bfl.B();
               bfl.l();
               bfl.a(770, 771, 1, 0);
               bfl.j();
               avc.c();
               mc.ag().b(st, ix, iy);
               if (!bar) {
                  mc.ag().a(mc.k, st, ix, iy, "");
               }

               avc.a();
               bfl.C();
               if (!depthWasOn) {
                  bfl.i();
               }
            });
            if (bar && st.e()) {
               float f = 1.0F - (float)st.h() / st.j();
               c.fill(ex + 1, ey + 15, ex + 15, ey + 17, -1073741824);
               c.fill(ex + 1, ey + 15, ex + 1 + Math.round(14.0F * f), ey + 17, wearColor(st));
            }

            String l = label(st);
            if (!l.isEmpty()) {
               float r = this.textRatio(c);
               c.push();
               c.translate(ex + 19, ey + 1 + (16.0F - 7.0F * r) / 2.0F);
               c.scale(r, r);
               c.vtext(l, 0, 0, wearColor(st), !bg);
               c.pop();
            }
         } else {
            c.fill(ex + 2, ey + 3, ex + 14, ey + 15, 1090519039);
         }

         if (this.vertical()) {
            ey += 20;
         } else {
            ex += w + 2;
         }
      }

      c.pop();
   }
}
