package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class HudModules {
   public static final String PREFIX = "pulsar:";
   public static volatile boolean editing;
   public static volatile int TICK;
   private static final List<HudModule> MODULES = new ArrayList<>();
   private static volatile HudData data = HudModules.NoData.INSTANCE;
   private static long[] drawNanos = new long[0];

   private HudModules() {
   }

   public static void data(HudData d) {
      data = (HudData)(d == null ? HudModules.NoData.INSTANCE : d);
   }

   public static HudData data() {
      return data;
   }

   public static void register(HudModule m) {
      MODULES.removeIf(o -> o.id().equals(m.id()));
      MODULES.add(m);
   }

   public static List<HudModule> all() {
      return MODULES;
   }

   public static List<PanelLayout.Widget> widgets(Canvas g) {
      List<PanelLayout.Widget> out = new ArrayList<>();

      for (HudModule m : MODULES) {
         if (m.enabled() && (m.present() || editing)) {
            out.add(new PanelLayout.Widget("pulsar:" + m.id(), m.zone(), m.width(g), m.height(g)));
         }
      }

      return out;
   }

   public static Map<String, Placement> freePlacements(Canvas g) {
      ClientOptions options = ClientOptions.get();
      Map<String, Placement> out = new LinkedHashMap<>();

      for (HudModule m : MODULES) {
         Placement placement = Placement.parse(options.string(m.id() + ".pos", null)).filter(Placement::free).orElse(null);
         if (placement == null && options.string(m.id() + ".zone", null) == null && m.enabled()) {
            placement = m.home(g);
            if (placement == null) {
               placement = HudDefaults.placement(m.id());
            }
         }

         if (placement != null) {
            out.put("pulsar:" + m.id(), placement);
         }
      }

      return out;
   }

   public static boolean isModule(String widgetId) {
      return widgetId != null && widgetId.startsWith("pulsar:");
   }

   public static HudModule byWidget(String widgetId) {
      String id = widgetId.substring("pulsar:".length());

      for (HudModule m : MODULES) {
         if (m.id().equals(id)) {
            return m;
         }
      }

      return null;
   }

   public static void draw(Canvas g, String widgetId, int x, int y) {
      String id = widgetId.substring("pulsar:".length());

      for (int i = 0; i < MODULES.size(); i++) {
         HudModule m = MODULES.get(i);
         if (m.id().equals(id)) {
            long t0 = System.nanoTime();
            m.draw(g, x, y);
            if (drawNanos.length <= i) {
               drawNanos = Arrays.copyOf(drawNanos, MODULES.size());
            }

            drawNanos[i] = drawNanos[i] + (System.nanoTime() - t0);
            return;
         }
      }
   }

   public static Map<String, Long> takeDrawNanos() {
      Map<String, Long> out = new LinkedHashMap<>();

      for (int i = 0; i < drawNanos.length && i < MODULES.size(); i++) {
         if (drawNanos[i] > 0L) {
            out.put(MODULES.get(i).id(), drawNanos[i]);
         }

         drawNanos[i] = 0L;
      }

      return out;
   }

   private static enum NoData implements HudData {
      INSTANCE;

      @Override
      public int fps() {
         return 0;
      }

      @Override
      public long latencyMs() {
         return -1L;
      }

      @Override
      public long[] latencyMinAvgMax() {
         return null;
      }

      @Override
      public long recentGapMs() {
         return 0L;
      }

      @Override
      public void sendPing() {
      }

      @Override
      public boolean connected() {
         return false;
      }

      @Override
      public boolean inGame() {
         return false;
      }

      @Override
      public HudData.PlayerPos player() {
         return null;
      }

      @Override
      public boolean sprinting() {
         return false;
      }

      @Override
      public boolean crouching() {
         return false;
      }

      @Override
      public boolean toggleSprint() {
         return false;
      }

      @Override
      public boolean toggleCrouch() {
         return false;
      }

      @Override
      public float attackStrength() {
         return 1.0F;
      }

      @Override
      public boolean attackIndicatorCrosshair() {
         return false;
      }

      @Override
      public boolean showDebug() {
         return false;
      }

      @Override
      public boolean tabKeyDown() {
         return false;
      }

      @Override
      public HudData.CameraPose camera() {
         return null;
      }

      @Override
      public List<String> diagLines() {
         return List.of();
      }

      @Override
      public int textWidth(String text) {
         return text.length() * 6;
      }

      @Override
      public int lineHeight() {
         return 9;
      }
   }
}
