package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudData;
import com.metrofico.pulsar.api.hud.HudDefaults;
import com.metrofico.pulsar.api.hud.HudLang;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.Zone;

public final class PingModule extends TextModule {
   private long lastKnown = -1L;
   private long lastKnownAt;

   @Override
   public String id() {
      return "ping";
   }

   @Override
   public Zone zone() {
      return ClientOptions.get().zone("ping.zone", HudDefaults.zone("ping", Zone.BOTTOM_LEFT));
   }

   @Override
   protected boolean pixel() {
      return true;
   }

   @Override
   protected boolean smallWords() {
      return true;
   }

   @Override
   protected boolean background() {
      return ClientOptions.get().bool("ping.background", true);
   }

   private long latency() {
      HudData d = HudModules.data();
      if (!d.connected()) {
         this.lastKnown = -1L;
         return -1L;
      } else {
         long value = d.latencyMs();
         long now = System.currentTimeMillis();
         if (value >= 0L) {
            this.lastKnown = value;
            this.lastKnownAt = now;
            return value;
         } else {
            return now - this.lastKnownAt < 30000L ? this.lastKnown : -1L;
         }
      }
   }

   @Override
   protected String[] lines() {
      HudData d = HudModules.data();
      long ms = this.latency();
      String base;
      if (ms < 0L) {
         base = HudModules.editing ? "42 MS" : "-- MS";
      } else if (ClientOptions.get().bool("ping.detail", false)) {
         long[] m = d.latencyMinAvgMax();
         base = m == null ? ms + " MS" : m[1] + " MS (" + m[0] + "-" + m[2] + ")";
      } else {
         base = ms + " MS";
      }

      if (ClientOptions.get().bool("ping.gaps", true)) {
         long gap = d.recentGapMs();
         if (gap > 0L) {
            base = base + "  " + gap + " MS " + HudLang.noData();
         } else if (HudModules.editing) {
            base = base + "  320 MS " + HudLang.noData();
         }
      }

      return new String[]{base};
   }

   @Override
   public boolean present() {
      return HudModules.data().connected() || HudModules.editing;
   }

   @Override
   public void draw(Canvas g, int x, int y) {
      if (this.enabled()) {
         HudModules.data().sendPing();
      }

      super.draw(g, x, y);
   }
}
