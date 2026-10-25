package com.metrofico.pulsar.engine.v26x.modules;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudBackground;
import com.metrofico.pulsar.api.hud.HudDefaults;
import com.metrofico.pulsar.api.hud.HudModule;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.PixelInfinity;
import com.metrofico.pulsar.api.hud.Zone;
import com.metrofico.pulsar.engine.v26x.hub.Canvas26;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.Hud;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;

public final class EffectsModule implements HudModule {
   private static final Identifier BG = Identifier.withDefaultNamespace("hud/effect_background");
   private static final Identifier BG_AMBIENT = Identifier.withDefaultNamespace("hud/effect_background_ambient");
   private static final String[] ROMAN = new String[]{"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X"};
   private static final int GAP = 2;
   private static List<MobEffectInstance> sample;
   private static List<MobEffectInstance> memo = List.of();
   private static int memoTick = -1;
   private static boolean memoEditing;

   public String id() {
      return "effects";
   }

   public Zone zone() {
      return ClientOptions.get().zone("effects.zone", HudDefaults.zone("effects", Zone.TOP_RIGHT));
   }

   private static List<MobEffectInstance> sample() {
      if (sample == null) {
         sample = List.of(new MobEffectInstance(MobEffects.SPEED, 1200, 1));
      }

      return sample;
   }

   private static List<MobEffectInstance> effects() {
      int tick = HudModules.TICK;
      if (tick != memoTick || memoEditing != HudModules.editing) {
         memo = computeEffects();
         memoTick = tick;
         memoEditing = HudModules.editing;
      }

      return memo;
   }

   private static List<MobEffectInstance> computeEffects() {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player == null) {
         return HudModules.editing ? sample() : List.of();
      } else {
         List<MobEffectInstance> out = new ArrayList<>();

         for (MobEffectInstance e : mc.player.getActiveEffects()) {
            if (e.showIcon()) {
               out.add(e);
            }
         }

         out.sort((a, b) -> b.compareTo(a));
         return out.isEmpty() && HudModules.editing ? sample() : out;
      }
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

   private int entryWidth(Canvas c, MobEffectInstance e) {
      if (!this.texts()) {
         return 24;
      } else {
         boolean bold = "bold".equals(ClientOptions.get().string("effects.textWeight", "normal"));
         String t = time(e);
         int tw = Math.max(c.vwidth(styled(name(e), bold)), "∞".equals(t) ? 10 : c.vwidth(styled(t, bold)));
         return 28 + tw + 4;
      }
   }

   private static String name(MobEffectInstance e) {
      ClientOptions o = ClientOptions.get();
      if (!o.bool("effects.name", true)) {
         return "";
      } else {
         String n = ((MobEffect)e.getEffect().value()).getDisplayName().getString();
         int amp = e.getAmplifier();
         if (o.bool("effects.level", true) && amp > 0) {
            n = n + " " + (amp + 1 < ROMAN.length ? ROMAN[amp + 1] : Integer.toString(amp + 1));
         }

         return n.toUpperCase(Locale.ROOT);
      }
   }

   private static String time(MobEffectInstance e) {
      if (!ClientOptions.get().bool("effects.time", true)) {
         return "";
      } else if (e.isInfiniteDuration()) {
         return "∞";
      } else {
         int s = e.getDuration() / 20;
         return s >= 3600 ? String.format("%d:%02d:%02d", s / 3600, s / 60 % 60, s % 60) : String.format("%d:%02d", s / 60, s % 60);
      }
   }

   public int width(Canvas c) {
      List<MobEffectInstance> list = effects();
      int w = 0;

      for (MobEffectInstance e : list) {
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
      GuiGraphicsExtractor g = ((Canvas26)c).gfx();
      ClientOptions o = ClientOptions.get();
      float s = (float)this.scale();
      boolean bg = o.bool("effects.background", true);
      boolean border = o.bool("effects.border", false);
      int fill = HudBackground.color("effects.opacity");
      int ambientFill = HudBackground.color("effects.opacity", 2107962);
      boolean bold = "bold".equals(o.string("effects.textWeight", "normal"));
      int timeColor = bold ? 13160666 : 10134447;
      boolean blink = o.bool("effects.blink", true);
      g.pose().pushMatrix();
      g.pose().translate(x, y);
      g.pose().scale(s, s);
      int ex = 0;
      int ey = 0;

      for (MobEffectInstance e : effects()) {
         int w = this.entryWidth(c, e);
         float alpha = 1.0F;
         if (blink && !e.isInfiniteDuration() && e.endsWithin(200)) {
            int d = e.getDuration();
            alpha = d / 5 % 2 == 0 ? 1.0F : 0.35F;
         }

         int a = Math.round(alpha * 255.0F) << 24;
         if (bg) {
            if (border) {
               g.blitSprite(RenderPipelines.GUI_TEXTURED, e.isAmbient() ? BG_AMBIENT : BG, ex, ey, 24, 24);
            } else {
               g.fill(ex, ey, ex + 24, ey + 24, e.isAmbient() ? ambientFill : fill);
            }

            if (w > 24) {
               g.fill(ex + 24, ey, ex + w, ey + 24, fill);
            }
         }

         g.blitSprite(RenderPipelines.GUI_TEXTURED, Hud.getMobEffectSprite(e.getEffect()), ex + 3, ey + 3, 18, 18, a | 16777215);
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

      g.pose().popMatrix();
   }
}
