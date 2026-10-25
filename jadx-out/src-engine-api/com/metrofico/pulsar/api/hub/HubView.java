package com.metrofico.pulsar.api.hub;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.gfx.Font;
import com.metrofico.pulsar.api.hud.Zone;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.api.keys.PulsarKeys;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Consumer;

public final class HubView {
   static final int BG = -233959146;
   static final int ROW_HOVER = -15328478;
   static final int HOVER_STRONG = -14472394;
   static final int LINE = -14341066;
   static final int INK = -1512206;
   static final int SECONDARY = -6642769;
   static final int MUTED = -10853262;
   static final int SCRIM = -1509949440;
   static final int INK_ON_INK = -15855338;
   private static final int HEAD_H = 28;
   private static final int RAIL_W = 88;
   private static final int ROW_H = 34;
   private static final int PARAM_H = 22;
   private static final int[] SWATCHES = new int[]{-1, -16777216, -45747, -24765, -7859, -10688932, -11673345, -11698945, -1683969, -34125};
   private final HubHost host;
   private int width;
   private int height;
   private String category = "Combate";
   private String query = "";
   private HubView.Focus focus = HubView.Focus.NONE;
   private String hexDraft = "";
   private String hexParam;
   private HubOption detail;
   private double scroll;
   private int contentHeight;
   private int contentTop;
   private int contentBottom;
   private final List<HubView.Hit> hits = new ArrayList<>();
   private boolean pointer;
   private int mouseX;
   private int mouseY;
   private String resetArmed;
   private long resetArmedAt;
   private Param.Range dragRange;
   private int dragX0;
   private int dragW;
   private PulsarKeys.Action capturing;
   private static final int KEY_W = 74;
   private static final long PILL_ANIM_MS = 220L;
   private static final int PILL_W = 34;
   private static final int PILL_H = 16;
   private static final int KNOB = 12;
   private final Map<String, Boolean> pillState = new HashMap<>();
   private final Map<String, Long> pillFlipAt = new HashMap<>();

   public HubView(HubHost host) {
      this.host = host;
      ClientOptions.LOG.accept("pulsar hub: opened");
      HubNavigation last = HubNavigation.last();
      this.category = last.category();
      this.detail = last.detail();
      this.scroll = last.scroll();
   }

   public HubView(HubHost host, String optionId) {
      this(host);
      HubOption o = HubOptions.byId(optionId);
      if (o != null) {
         this.category = o.category();
         this.detail = o;
         this.scroll = 0.0;
      }
   }

   private int panelX() {
      return (this.width - this.panelW()) / 2;
   }

   private int panelY() {
      return (this.height - this.panelH()) / 2;
   }

   private int panelW() {
      return Math.min(380, this.width - 24);
   }

   private int panelH() {
      return Math.min(240, this.height - 24);
   }

   private List<HubOption> visible() {
      String q = this.query.toLowerCase(Locale.ROOT).trim();
      List<HubOption> out = new ArrayList<>();

      for (HubOption o : HubOptions.all()) {
         boolean match = q.isEmpty() ? o.category().equals(this.category) : (o.title() + " " + o.desc()).toLowerCase(Locale.ROOT).contains(q);
         if (match) {
            out.add(o);
         }
      }

      return out;
   }

   private void hit(int x, int y, int w, int h, Runnable action) {
      this.hits.add(new HubView.Hit(x, y, w, h, action, false));
   }

   private void chit(int x, int y, int w, int h, Runnable action) {
      this.hits.add(new HubView.Hit(x, y - (int)this.scroll, w, h, action, true));
   }

   private boolean over(int x, int y, int w, int h) {
      return this.mouseX >= x && this.mouseX < x + w && this.mouseY >= y && this.mouseY < y + h;
   }

   private boolean cover(int x, int y, int w, int h) {
      int my = this.mouseY + (int)this.scroll;
      return this.mouseY >= this.contentTop && this.mouseY < this.contentBottom && this.mouseX >= x && this.mouseX < x + w && my >= y && my < y + h;
   }

   public void render(Canvas g, int mouseX, int mouseY) {
      this.width = g.width();
      this.height = g.height();
      this.mouseX = mouseX;
      this.mouseY = mouseY;
      this.hits.clear();
      this.pointer = false;
      g.fill(0, 0, this.width, this.height, -1509949440);
      int x = this.panelX();
      int y = this.panelY();
      int w = this.panelW();
      int h = this.panelH();
      g.fill(x, y, x + w, y + h, -233959146);
      outline(g, x, y, w, h, -14341066);
      this.header(g, x, y, w);
      this.rail(g, x, y, h);
      int cx = x + 88 + 14;
      int cw = w - 88 - 28;
      this.contentTop = y + 28 + 1;
      this.contentBottom = y + h - 1;
      int visibleH = this.contentBottom - this.contentTop;
      g.clip(x + 88 + 1, this.contentTop, x + w - 1, this.contentBottom);
      g.push();
      g.translate(0.0F, (float)(-this.scroll));
      int oy = y + 28 + 12;
      int endY = this.detail != null ? this.detailView(g, this.detail, cx, cw, oy) : this.listView(g, cx, cw, oy);
      g.pop();
      g.unclip();
      this.contentHeight = endY - this.contentTop + 6;
      double maxScroll = Math.max(0, this.contentHeight - visibleH);
      if (this.scroll > maxScroll) {
         this.scroll = maxScroll;
      }

      if (maxScroll > 0.0) {
         int barH = Math.max(12, (int)((long)visibleH * visibleH / this.contentHeight));
         int barY = this.contentTop + (int)((visibleH - barH) * (this.scroll / maxScroll));
         g.fill(x + w - 4, barY, x + w - 2, barY + barH, -10853262);
      }

      g.pointer(this.pointer);
   }

   private void header(Canvas g, int x, int y, int w) {
      g.logo(x + 11, y + 7, 14);
      g.text(Font.MEDIUM, "PULSAR", x + 28, y + 8, 12.0F, -1512206);
      int closeX = x + w - 22;
      boolean overClose = this.over(closeX, y + 6, 15, 16);
      this.pointer |= overClose;
      if (overClose) {
         g.fill(closeX, y + 6, closeX + 15, y + 22, -14472394);
      }

      g.icon("x", closeX + 2, y + 8, 11, overClose ? -1512206 : -10853262);
      this.hit(closeX, y + 6, 15, 16, this::close);
      int sw = 120;
      int sx = closeX - sw - 10;
      boolean overSearch = this.over(sx, y + 6, sw, 16);
      boolean focused = this.focus == HubView.Focus.SEARCH;
      this.pointer |= overSearch;
      g.fill(sx, y + 6, sx + sw, y + 22, !overSearch && !focused ? -15328478 : -14472394);
      outline(g, sx, y + 6, sw, 16, focused ? -6642769 : -14341066);
      g.icon("search", sx + 5, y + 10, 9, -10853262);
      String shown = this.query.isEmpty() && !focused ? "Buscar" : this.query + (focused ? "_" : "");
      g.text(Font.REGULAR, shown, sx + 17, y + 9, 9.0F, this.query.isEmpty() && !focused ? -10853262 : -1512206);
      this.hit(sx, y + 6, sw, 16, () -> this.focus = HubView.Focus.SEARCH);
      g.fill(x, y + 28, x + w, y + 28 + 1, -14341066);
   }

   private void rail(Canvas g, int x, int y, int h) {
      int cy = y + 28 + 10;

      for (String c : HubOptions.CATEGORIES) {
         boolean active = c.equals(this.category) && this.query.isEmpty();
         boolean hover = this.over(x, cy - 3, 88, 16);
         if (hover && !active) {
            g.fill(x + 4, cy - 3, x + 88 - 6, cy + 13, -15328478);
            this.pointer = true;
         }

         if (active) {
            g.fill(x, cy - 3, x + 2, cy + 13, -1512206);
         }

         g.icon(HubOptions.categoryIcon(c), x + 11, cy, 11, active ? -1512206 : (hover ? -6642769 : -10853262));
         g.text(Font.MEDIUM, c, x + 27, cy, 9.0F, active ? -1512206 : (hover ? -6642769 : -10853262));
         this.hit(x, cy - 3, 88, 16, () -> {
            this.capturing = null;
            this.category = c;
            this.query = "";
            this.detail = null;
            this.scroll = 0.0;
            this.focus = HubView.Focus.NONE;
         });
         cy += 22;
      }

      cy += 6;
      g.fill(x + 8, cy - 6, x + 88 - 8, cy - 5, -14341066);
      cy += 4;
      boolean hoverEdit = this.over(x, cy - 3, 88, 16);
      if (hoverEdit) {
         g.fill(x + 4, cy - 3, x + 88 - 6, cy + 13, -15328478);
         this.pointer = true;
      }

      g.icon("move", x + 11, cy, 11, hoverEdit ? -1512206 : -6642769);
      String editLabel = "Editar HUD";
      float editSize = g.textWidth(Font.MEDIUM, editLabel, 9.0F) <= 56 ? 9.0F : 8.0F;
      g.text(Font.MEDIUM, editLabel, x + 27, cy, editSize, hoverEdit ? -1512206 : -6642769);
      this.hit(x, cy - 3, 88, 16, this::openHudEditor);
      g.fill(x + 88, y + 28, x + 88 + 1, y + h, -14341066);
   }

   private void openHudEditor() {
      this.host.openHudEditor();
   }

   private int listView(Canvas g, int cx, int cw, int oy) {
      List<HubOption> options = this.visible();
      if ("Acerca".equals(this.category) && this.query.isEmpty()) {
         g.text(Font.MEDIUM, "Pulsar Client", cx, oy, 10.0F, -1512206);
         g.text(Font.REGULAR, "Opciones del cliente, siempre contigo.", cx, oy + 14, 8.0F, -6642769);
         g.text(Font.REGULAR, "Los packs de cada servidor se gestionan en el launcher.", cx, oy + 25, 8.0F, -6642769);
         List<String> all = new ArrayList<>();

         for (HubOption o : HubOptions.all()) {
            all.addAll(o.resetIds());
         }

         all.addAll(PulsarKeys.resetIds());
         return this.resetButton(g, "all", "Restablecer todas las opciones", cx, oy + 44, all) + 8;
      } else if ("Teclas".equals(this.category) && this.query.isEmpty()) {
         return this.keysView(g, cx, cw, oy);
      } else if (options.isEmpty()) {
         g.text(
            Font.REGULAR,
            this.query.isEmpty() ? "Aún no hay opciones en esta categoría." : "Sin resultados para “" + this.query + "”.",
            cx,
            oy + 4,
            9.0F,
            -10853262
         );
         return oy + 16;
      } else {
         for (HubOption o : options) {
            boolean hover = this.cover(cx - 6, oy - 4, cw + 12, 30);
            if (hover) {
               g.fill(cx - 6, oy - 4, cx + cw + 6, oy + 26, -15328478);
            }

            if (!o.params().isEmpty()) {
               this.chit(cx - 6, oy - 4, cw + 12, 30, () -> this.open(o));
               if (hover) {
                  this.pointer = true;
               }
            }

            g.icon(o.icon(), cx, oy + 2, 13, -1512206);
            g.text(Font.MEDIUM, o.title(), cx + 20, oy, 10.0F, -1512206);
            int controlW;
            if (o.kind() == HubOption.Kind.SEGMENTED) {
               controlW = this.segmentedWidth(g, o.segments()) + 4;
               this.segmented(
                  g,
                  o.segments(),
                  o.segmentIds(),
                  ClientOptions.get().string(o.id(), o.segmentIds()[1]),
                  cx + cw,
                  oy + 1,
                  this.locked(o),
                  v -> ClientOptions.get().set(o.id(), v)
               );
            } else {
               int px = cx + cw - 34;
               boolean on = ClientOptions.get().enabled(o.id());
               this.pill(g, o.enabledKey(), px, oy + 2, on, () -> ClientOptions.get().set(o.enabledKey(), !on));
               controlW = 38;
               if (!o.params().isEmpty()) {
                  int gx = px - 24;
                  boolean gh = this.cover(gx - 3, oy - 1, 20, 20);
                  if (gh) {
                     g.fill(gx - 3, oy - 1, gx + 17, oy + 19, -14472394);
                     this.pointer = true;
                  }

                  g.icon("settings", gx, oy + 2, 14, gh ? -1512206 : -6642769);
                  this.chit(gx - 3, oy - 1, 20, 20, () -> this.open(o));
                  controlW += 26;
               }
            }

            String desc = this.liveDesc(o);
            int maxDesc = cw - 20 - controlW - 6;
            String shown = desc;

            while (g.textWidth(Font.REGULAR, shown, 7.0F) > maxDesc && shown.length() > 4) {
               shown = shown.substring(0, shown.length() - 2);
            }

            g.text(Font.REGULAR, shown.equals(desc) ? desc : shown + "…", cx + 20, oy + 13, 7.0F, -6642769);
            oy += 34;
            g.fill(cx - 6, oy - 8, cx + cw + 6, oy - 7, -14341066);
         }

         if (!this.query.isEmpty()) {
            return oy - 8;
         } else {
            List<String> ids = new ArrayList<>();

            for (HubOption o : options) {
               ids.addAll(o.resetIds());
            }

            return this.resetButton(g, "cat:" + this.category, "Restablecer " + this.category, cx, oy - 2, ids) + 4;
         }
      }
   }

   private int resetButton(Canvas g, String key, String label, int x, int y, List<String> ids) {
      boolean armed = key.equals(this.resetArmed) && System.currentTimeMillis() - this.resetArmedAt < 3000L;
      String text = armed ? "¿Seguro? Pulsa otra vez" : label;
      int w = g.textWidth(Font.REGULAR, text, 8.0F) + 16;
      boolean h = this.cover(x, y, w, 16);
      this.pointer |= h;
      if (armed || h) {
         g.fill(x, y, x + w, y + 16, armed ? -14472394 : -15328478);
      }

      outline(g, x, y, w, 16, armed ? -6642769 : -14341066);
      g.text(Font.REGULAR, text, x + 8, y + 4, 8.0F, armed ? -1512206 : (h ? -6642769 : -10853262));
      this.chit(x, y, w, 16, () -> {
         if (key.equals(this.resetArmed) && System.currentTimeMillis() - this.resetArmedAt < 3000L) {
            ClientOptions.get().reset(ids);
            this.resetArmed = null;
         } else {
            this.resetArmed = key;
            this.resetArmedAt = System.currentTimeMillis();
         }
      });
      return y + 16;
   }

   private String liveDesc(HubOption o) {
      return o.id().equals("perf") && this.host.perfLabel() != null ? this.host.perfLabel() + " · " + o.desc() : o.desc();
   }

   private int keysView(Canvas g, int cx, int cw, int oy) {
      g.text(Font.REGULAR, "Pulsa el botón y luego la tecla que quieras. Esc cancela.", cx, oy, 8.0F, -6642769);
      oy += 16;
      List<PulsarKeys.Action> actions = PulsarKeys.actions();

      for (String group : PulsarKeys.GROUPS) {
         boolean first = true;

         for (PulsarKeys.Action a : actions) {
            if (a.group().equals(group)) {
               if (first) {
                  g.text(Font.MEDIUM, group, cx, oy + 2, 9.0F, -1512206);
                  oy += 16;
                  first = false;
               }

               oy = this.keyRow(g, a, a.label(), cx, cw, oy);
            }
         }

         oy += 6;
      }

      return this.resetButton(g, "keys", "Restablecer Teclas", cx, oy, PulsarKeys.resetIds()) + 4;
   }

   private int keyRow(Canvas g, PulsarKeys.Action a, String label, int cx, int cw, int oy) {
      List<PulsarKeys.Action> shared = PulsarKeys.sharing(a);
      List<String> minecraft = PulsarKeys.vanillaUsers(PulsarKeys.key(a.id()));
      boolean clash = !shared.isEmpty() || !minecraft.isEmpty();
      int ty = clash ? oy + 2 : oy + 7 - 1;
      g.text(Font.REGULAR, fit(g, label, cw - 74 - 30), cx, ty, 8.0F, -6642769);
      if (clash) {
         StringBuilder also = new StringBuilder("También: ");
         int n = 0;

         for (PulsarKeys.Action o : shared) {
            also.append(n++ == 0 ? "" : ", ").append(o.label());
         }

         for (String m : minecraft) {
            also.append(n++ == 0 ? "" : ", ").append(m).append(" (Minecraft)");
         }

         g.text(Font.REGULAR, fit7(g, also.toString(), cw - 74 - 30), cx, oy + 12, 7.0F, -10853262);
      }

      this.keyButton(g, a, cx + cw, oy + 3);
      oy += 22;
      g.fill(cx - 6, oy - 1, cx + cw + 6, oy, -14341066);
      return oy;
   }

   private void keyButton(Canvas g, PulsarKeys.Action a, int right, int y) {
      boolean waiting = this.capturing != null && this.capturing.id().equals(a.id());
      String key = PulsarKeys.key(a.id());
      String text = waiting ? "Pulsa una tecla…" : KeyCode.label(key);
      int w = Math.max(74, g.textWidth(Font.MEDIUM, text, 8.0F) + 12);
      int x = right - w;
      if (key != null && !a.required() && !waiting) {
         int qx = x - 16;
         boolean qh = this.cover(qx, y + 1, 14, 14);
         this.pointer |= qh;
         g.icon("x", qx + 2, y + 3, 10, qh ? -1512206 : -10853262);
         this.chit(qx, y + 1, 14, 14, () -> PulsarKeys.assign(a, null));
      }

      boolean h = this.cover(x, y, w, 16);
      this.pointer |= h;
      g.fill(x, y, x + w, y + 16, waiting ? -1512206 : (h ? -14472394 : -15328478));
      outline(g, x, y, w, 16, waiting ? -1512206 : (h ? -6642769 : -14341066));
      int tw = g.textWidth(Font.MEDIUM, text, 8.0F);
      g.text(Font.MEDIUM, text, x + (w - tw) / 2, y + 4, 8.0F, waiting ? -15855338 : (key == null ? -10853262 : -1512206));
      this.chit(x, y, w, 16, () -> this.capturing = waiting ? null : a);
   }

   public boolean capturing() {
      return this.capturing != null;
   }

   private static String fit7(Canvas g, String text, int max) {
      String shown = text;

      while (shown.length() > 1 && g.textWidth(Font.REGULAR, shown + "…", 7.0F) > max) {
         shown = shown.substring(0, shown.length() - 1);
      }

      return shown.equals(text) ? text : shown + "…";
   }

   private void open(HubOption o) {
      this.detail = o;
      this.scroll = 0.0;
      this.focus = HubView.Focus.NONE;
      this.capturing = null;
   }

   private void back() {
      this.capturing = null;
      this.detail = null;
      this.scroll = 0.0;
      this.focus = HubView.Focus.NONE;
   }

   private int detailView(Canvas g, HubOption o, int cx, int cw, int oy) {
      boolean bh = this.cover(cx - 4, oy - 2, 60, 16);
      this.pointer |= bh;
      g.icon("chevron-left", cx - 2, oy, 11, bh ? -1512206 : -6642769);
      int bw = g.text(Font.REGULAR, "Volver", cx + 10, oy + 1, 8.0F, bh ? -1512206 : -6642769);
      this.chit(cx - 4, oy - 2, 60, 16, this::back);
      g.text(Font.REGULAR, "·", cx + 14 + bw, oy + 1, 8.0F, -10853262);
      g.text(Font.MEDIUM, o.title(), cx + 22 + bw, oy, 10.0F, -1512206);
      boolean on = ClientOptions.get().enabled(o.id());
      this.pill(g, "detail:" + o.enabledKey(), cx + cw - 34, oy - 1, on, () -> ClientOptions.get().set(o.enabledKey(), !on));
      oy += 16;
      g.fill(cx - 6, oy, cx + cw + 6, oy + 1, -14341066);
      oy += 6;
      oy = this.preview(g, o, cx, cw, oy);

      for (Param p : o.params()) {
         int rowH = p instanceof Param.ZonePick ? 34 : 22;
         boolean disabled = HubOptions.paramDisabled(o, p);
         g.text(Font.REGULAR, fit(g, p.label(), cw - this.controlWidth(g, p) - 10), cx, oy + (rowH - 8) / 2 - 1, 8.0F, disabled ? -10853262 : -6642769);
         int right = cx + cw;
         if (disabled) {
            String v = p instanceof Param.Range r
               ? r.format(ClientOptions.get().dbl(r.id(), r.def()))
               : (p instanceof Param.Toggle t ? (ClientOptions.get().bool(t.id(), t.def()) ? "sí" : "no") : "—");
            g.text(Font.REGULAR, v, right - g.textWidth(Font.REGULAR, v, 8.0F), oy + (rowH - 8) / 2 - 1, 8.0F, -10853262);
            oy += rowH;
            g.fill(cx - 6, oy - 1, cx + cw + 6, oy, -14341066);
         } else {
            switch (p) {
               case Param.Toggle t:
                  boolean v = ClientOptions.get().bool(t.id(), t.def());
                  this.pill(g, t.id(), right - 34, oy + (rowH - 16) / 2, v, () -> ClientOptions.get().set(t.id(), !v));
                  break;
               case Param.Choice c:
                  this.segmented(
                     g,
                     c.labels(),
                     c.ids(),
                     ClientOptions.get().string(c.id(), c.def()),
                     right,
                     oy + (rowH - 16) / 2,
                     false,
                     vx -> ClientOptions.get().set(c.id(), vx)
                  );
                  break;
               case Param.Range r:
                  this.slider(g, r, right, oy + (rowH - 16) / 2);
                  break;
               case Param.ZonePick z:
                  this.zonePicker(g, z, right, oy + (rowH - 31) / 2);
                  break;
               case Param.Color cxx:
                  this.colorPicker(g, cxx, right, oy + (rowH - 14) / 2);
                  break;
               case Param.KeyBind k:
                  PulsarKeys.Action a = PulsarKeys.byId(k.action());
                  if (a != null) {
                     this.keyButton(g, a, right, oy + (rowH - 16) / 2);
                  }
                  break;
               default:
                  throw new MatchException(null, null);
            }

            oy += rowH;
            g.fill(cx - 6, oy - 1, cx + cw + 6, oy, -14341066);
         }
      }

      if (o.kind() == HubOption.Kind.TOGGLE) {
         PulsarKeys.Action toggle = PulsarKeys.byId("toggle:" + o.id());
         if (toggle != null) {
            oy = this.keyRow(g, toggle, "Tecla para encender o apagar", cx, cw, oy);
         }
      }

      return oy + this.extras(g, o, cx, cw, oy + 4);
   }

   private int controlWidth(Canvas g, Param p) {
      return switch (p) {
         case Param.Toggle t -> 34;
         case Param.Range r -> 130;
         case Param.ZonePick z -> 31;
         case Param.Color c -> 54 + SWATCHES.length * 12 - 3;
         case Param.KeyBind k -> 92;
         case Param.Choice cx -> {
            int total = 0;

            for (String label : cx.labels()) {
               total += g.textWidth(Font.MEDIUM, label, 8.0F) + 14;
            }

            yield total;
         }
         default -> throw new MatchException(null, null);
      };
   }

   private static String fit(Canvas g, String text, int max) {
      if (g.textWidth(Font.REGULAR, text, 8.0F) <= max) {
         return text;
      } else {
         String shown = text;

         while (shown.length() > 1 && g.textWidth(Font.REGULAR, shown + "…", 8.0F) > max) {
            shown = shown.substring(0, shown.length() - 1);
         }

         return shown + "…";
      }
   }

   private int preview(Canvas g, HubOption o, int cx, int cw, int oy) {
      HubPreview p = HubPreview.of(o.id());
      if (p == null) {
         return oy;
      } else {
         int ph = 40;
         g.fill(cx - 6, oy, cx + cw + 6, oy + ph, -15328478);
         p.draw(g, cx - 6, oy, cw + 12, ph);
         return oy + ph + 6;
      }
   }

   private int extras(Canvas g, HubOption o, int cx, int cw, int oy) {
      boolean hasZone = o.params().stream().anyMatch(p -> p instanceof Param.ZonePick);
      String resetLabel = "Restablecer " + o.title();
      int resetW = Math.max(g.textWidth(Font.REGULAR, resetLabel, 8.0F), g.textWidth(Font.REGULAR, "¿Seguro? Pulsa otra vez", 8.0F)) + 16;
      int ry = this.resetButton(g, "mod:" + o.id(), resetLabel, cx + cw - resetW, oy, o.resetIds());
      if (!hasZone) {
         return ry - oy + 8;
      } else {
         String label = "Colocar en el HUD";
         int tw = g.textWidth(Font.MEDIUM, label, 8.0F) + 24;
         boolean h = this.cover(cx, oy, tw, 16);
         this.pointer |= h;
         g.fill(cx, oy, cx + tw, oy + 16, h ? -14472394 : -15328478);
         outline(g, cx, oy, tw, 16, -14341066);
         g.icon("move", cx + 5, oy + 3, 10, h ? -1512206 : -6642769);
         g.text(Font.MEDIUM, label, cx + 18, oy + 3, 8.0F, h ? -1512206 : -6642769);
         this.chit(cx, oy, tw, 16, this::openHudEditor);
         return 24;
      }
   }

   private void pill(Canvas g, String key, int x, int y, boolean on, Runnable toggle) {
      long now = System.currentTimeMillis();
      Boolean prev = this.pillState.put(key, on);
      if (prev != null && prev != on) {
         this.pillFlipAt.put(key, now);
      }

      long since = now - this.pillFlipAt.getOrDefault(key, 0L);
      float t = prev != null && since < 220L ? (float)since / 220.0F : 1.0F;
      t = 1.0F - (1.0F - t) * (1.0F - t);
      float pos = on ? t : 1.0F - t;
      boolean h = this.cover(x - 2, y - 2, 38, 20);
      this.pointer |= h;
      int travel = 18;
      int knobX = x + 2 + Math.round(pos * travel);
      if (pos > 0.5F) {
         g.fill(x, y, x + 34, y + 16, h ? -1 : -1512206);
         g.fill(knobX, y + 2, knobX + 12, y + 16 - 2, -15855338);
      } else {
         outline(g, x, y, 34, 16, h ? -6642769 : -14341066);
         g.fill(knobX, y + 2, knobX + 12, y + 16 - 2, h ? -6642769 : -10853262);
      }

      this.chit(x - 2, y - 2, 38, 20, toggle);
   }

   private int segmentedWidth(Canvas g, String[] segments) {
      int total = 0;

      for (String seg : segments) {
         total += g.textWidth(Font.MEDIUM, seg, 8.0F) + 14;
      }

      return total;
   }

   private boolean locked(HubOption o) {
      return o.id().equals("pvp.style") && ClientOptions.get().serverCombatActive();
   }

   private void segmented(Canvas g, String[] labels, String[] ids, String current, int right, int y, boolean locked, Consumer<String> onPick) {
      if (locked) {
         current = ClientOptions.get().classicPvp() ? "classic" : "modern";
      }

      int[] widths = new int[labels.length];
      int total = 0;

      for (int i = 0; i < labels.length; i++) {
         widths[i] = g.textWidth(Font.MEDIUM, labels[i], 8.0F) + 14;
         total += widths[i];
      }

      int sx = right - total;
      if (locked) {
         g.text(Font.REGULAR, "servidor", sx - g.textWidth(Font.REGULAR, "servidor", 7.0F) - 8, y + 5, 7.0F, -10853262);
      }

      outline(g, sx, y, total, 16, -14341066);
      int px = sx;

      for (int i = 0; i < labels.length; i++) {
         boolean on = ids[i].equals(current);
         boolean hover = !locked && this.cover(px, y, widths[i], 16);
         if (on) {
            g.fill(px, y, px + widths[i], y + 16, -1512206);
         } else if (hover) {
            g.fill(px, y, px + widths[i], y + 16, -14472394);
            this.pointer = true;
         }

         int tw = g.textWidth(Font.MEDIUM, labels[i], 8.0F);
         g.text(Font.MEDIUM, labels[i], px + (widths[i] - tw) / 2, y + 3, 8.0F, on ? -15855338 : (hover ? -1512206 : -6642769));
         if (!locked && !on) {
            String id = ids[i];
            this.chit(px, y, widths[i], 16, () -> onPick.accept(id));
         }

         px += widths[i];
         if (i < labels.length - 1) {
            g.fill(px, y, px + 1, y + 16, -14341066);
         }
      }
   }

   private void slider(Canvas g, Param.Range r, int right, int y) {
      int valueW = 30;
      int w = 96;
      int x = right - valueW - w;
      double v = ClientOptions.get().dbl(r.id(), r.def());
      double frac = (v - r.min()) / (r.max() - r.min());
      int kx = x + (int)Math.round(frac * w);
      boolean h = this.dragRange == r || this.cover(x - 4, y, w + 8, 16);
      this.pointer |= h;
      g.fill(x, y + 8, x + w, y + 9, -14341066);
      g.fill(x, y + 8, kx, y + 9, h ? -1512206 : -6642769);
      g.fill(kx - 1, y + 3, kx + 2, y + 13, h ? -1 : -1512206);
      String label = r.format(v);
      g.text(Font.MEDIUM, label, right - g.textWidth(Font.MEDIUM, label, 8.0F), y + 3, 8.0F, -1512206);
      this.chit(x - 4, y, w + 8, 16, () -> {
         this.dragRange = r;
         this.dragX0 = x;
         this.dragW = w;
         this.dragTo(this.mouseX);
      });
   }

   private void dragTo(double px) {
      if (this.dragRange != null) {
         double frac = Math.max(0.0, Math.min(1.0, (px - this.dragX0) / this.dragW));
         double v = this.dragRange.clamp(this.dragRange.min() + frac * (this.dragRange.max() - this.dragRange.min()));
         ClientOptions.get().preview(this.dragRange.id(), this.dragRange.integer() ? (int)Math.round(v) : v);
      }
   }

   private void zonePicker(Canvas g, Param.ZonePick z, int right, int y) {
      int cell = 9;
      int gap = 2;
      int size = cell * 3 + gap * 2;
      int x = right - size;
      Zone current = ClientOptions.get().zone(z.id(), z.def());
      String name = current.id().replace('-', ' ');
      g.text(Font.REGULAR, name, x - g.textWidth(Font.REGULAR, name, 7.0F) - 8, y + 12, 7.0F, -10853262);

      for (Zone zone : Zone.values()) {
         int zx = x + zone.column() * (cell + gap);
         int zy = y + zone.row() * (cell + gap);
         boolean on = zone == current;
         boolean h = this.cover(zx, zy, cell, cell);
         this.pointer |= h;
         if (on) {
            g.fill(zx, zy, zx + cell, zy + cell, -1512206);
         } else {
            g.fill(zx, zy, zx + cell, zy + cell, h ? -14472394 : -15328478);
            outline(g, zx, zy, cell, cell, h ? -6642769 : -14341066);
         }

         this.chit(zx, zy, cell, cell, () -> ClientOptions.get().set(z.id(), zone.id()));
      }
   }

   private void colorPicker(Canvas g, Param.Color c, int right, int y) {
      int current = ClientOptions.get().color(c.id(), c.def());
      int hexW = 46;
      int hx = right - hexW;
      boolean editing = this.focus == HubView.Focus.HEX && c.id().equals(this.hexParam);
      boolean hh = this.cover(hx, y, hexW, 14);
      this.pointer |= hh;
      g.fill(hx, y, hx + hexW, y + 14, !hh && !editing ? -15328478 : -14472394);
      outline(g, hx, y, hexW, 14, editing ? -6642769 : -14341066);
      String text = editing ? "#" + this.hexDraft + "_" : String.format(Locale.ROOT, "#%06X", current & 16777215);
      g.text(Font.REGULAR, text, hx + 4, y + 3, 7.0F, editing ? -1512206 : -6642769);
      this.chit(hx, y, hexW, 14, () -> {
         this.focus = HubView.Focus.HEX;
         this.hexParam = c.id();
         this.hexDraft = "";
      });
      int sw = 9;
      int gap = 3;
      int x = hx - 8 - SWATCHES.length * (sw + gap) + gap;

      for (int color : SWATCHES) {
         boolean on = (color & 16777215) == (current & 16777215);
         boolean h = this.cover(x - 1, y + 1, sw + 2, sw + 2);
         this.pointer |= h;
         if (on || h) {
            outline(g, x - 2, y, sw + 4, sw + 4, on ? -1512206 : -6642769);
         }

         g.fill(x, y + 2, x + sw, y + 2 + sw, color);
         if ((color & 16777215) == 0) {
            outline(g, x, y + 2, sw, sw, -14341066);
         }

         this.chit(x - 1, y + 1, sw + 2, sw + 2, () -> ClientOptions.get().set(c.id(), String.format(Locale.ROOT, "#%06X", color & 16777215)));
         x += sw + gap;
      }
   }

   private static void outline(Canvas g, int x, int y, int w, int h, int color) {
      g.outline(x, y, w, h, color);
   }

   public boolean mouseClicked(double x, double y, int button) {
      if (this.capturing != null && KeyCode.fromMouse(button) != null) {
         PulsarKeys.assign(this.capturing, KeyCode.fromMouse(button));
         this.capturing = null;
         return true;
      } else if (button != 0) {
         return false;
      } else {
         PulsarKeys.Action wasCapturing = this.capturing;
         this.capturing = null;
         this.commitHex();
         this.focus = HubView.Focus.NONE;

         for (int i = this.hits.size() - 1; i >= 0; i--) {
            HubView.Hit h = this.hits.get(i);
            if ((!h.clipped() || !(y < this.contentTop) && !(y >= this.contentBottom)) && h.contains(x, y)) {
               this.capturing = wasCapturing;
               h.action().run();
               if (this.capturing == wasCapturing && wasCapturing != null) {
                  this.capturing = null;
               }

               return true;
            }
         }

         return false;
      }
   }

   public boolean mouseDragged(double x) {
      if (this.dragRange != null) {
         this.dragTo(x);
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseReleased(double x) {
      if (this.dragRange != null) {
         Param.Range r = this.dragRange;
         this.dragTo(x);
         this.dragRange = null;
         double v = ClientOptions.get().dbl(r.id(), r.def());
         ClientOptions.get().set(r.id(), r.integer() ? (int)Math.round(v) : v);
         return true;
      } else {
         return false;
      }
   }

   public boolean mouseScrolled(double dy) {
      this.scroll = Math.max(0.0, this.scroll - dy * 12.0);
      return true;
   }

   public boolean keyPressed(HubView.Key key, String name) {
      if (this.capturing != null) {
         if (key != HubView.Key.ESC && name != null) {
            PulsarKeys.assign(this.capturing, name);
         }

         this.capturing = null;
         return true;
      } else if (key == HubView.Key.ESC) {
         if (this.focus != HubView.Focus.NONE) {
            this.commitHex();
            this.focus = HubView.Focus.NONE;
            return true;
         } else if (this.detail != null) {
            this.back();
            return true;
         } else {
            this.close();
            return true;
         }
      } else {
         if (key == HubView.Key.BACKSPACE) {
            if (this.focus == HubView.Focus.SEARCH && !this.query.isEmpty()) {
               this.query = this.query.substring(0, this.query.length() - 1);
               return true;
            }

            if (this.focus == HubView.Focus.HEX && !this.hexDraft.isEmpty()) {
               this.hexDraft = this.hexDraft.substring(0, this.hexDraft.length() - 1);
               return true;
            }
         }

         if (key == HubView.Key.ENTER && this.focus == HubView.Focus.HEX) {
            this.commitHex();
            this.focus = HubView.Focus.NONE;
            return true;
         } else if (key == HubView.Key.TAB) {
            this.focus = this.focus == HubView.Focus.SEARCH ? HubView.Focus.NONE : HubView.Focus.SEARCH;
            return true;
         } else if (this.focus == HubView.Focus.NONE && PulsarKeys.is("hudEdit", name)) {
            this.openHudEditor();
            return true;
         } else {
            return false;
         }
      }
   }

   private void commitHex() {
      if (this.focus == HubView.Focus.HEX && this.hexDraft.length() == 6 && this.hexParam != null) {
         ClientOptions.get().set(this.hexParam, "#" + this.hexDraft.toUpperCase(Locale.ROOT));
      }

      this.hexDraft = "";
   }

   public boolean charTyped(int codepoint) {
      String ch = new String(Character.toChars(codepoint));
      if (this.focus == HubView.Focus.SEARCH && this.query.length() < 32) {
         this.query = this.query + ch;
         return true;
      } else if (this.focus == HubView.Focus.HEX && this.hexDraft.length() < 6 && ch.matches("[0-9a-fA-F]")) {
         this.hexDraft = this.hexDraft + ch;
         if (this.hexDraft.length() == 6) {
            this.commitHex();
            this.focus = HubView.Focus.NONE;
         }

         return true;
      } else {
         return false;
      }
   }

   public void close() {
      this.host.close();
   }

   public void closed() {
      this.commitHex();
      new HubNavigation(this.category, this.detail == null ? null : this.detail.id(), this.scroll).save();
   }

   private static enum Focus {
      NONE,
      SEARCH,
      HEX;
   }

   private record Hit(int x, int y, int w, int h, Runnable action, boolean clipped) {
      boolean contains(double px, double py) {
         return px >= this.x && px < this.x + this.w && py >= this.y && py < this.y + this.h;
      }
   }

   public static enum Key {
      ESC,
      BACKSPACE,
      ENTER,
      TAB,
      OTHER;
   }
}
