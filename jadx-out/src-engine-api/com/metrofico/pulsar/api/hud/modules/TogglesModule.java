package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudData;
import com.metrofico.pulsar.api.hud.HudLang;
import com.metrofico.pulsar.api.hud.HudModules;

public final class TogglesModule extends TextModule {
   private static final String SPRINT = "SPRINT";
   private static final String SNEAK = "SNEAK";
   private String[] memo = new String[0];
   private int memoTick = -1;
   private int memoRev = -1;

   @Override
   public String id() {
      return "toggles";
   }

   @Override
   protected boolean pixel() {
      return true;
   }

   @Override
   protected boolean background() {
      return ClientOptions.get().bool("toggles.background", true);
   }

   @Override
   protected String[] lines() {
      int tick = HudModules.TICK;
      int rev = ClientOptions.revision;
      if (tick != this.memoTick || rev != this.memoRev) {
         this.memo = this.computeLines();
         this.memoTick = tick;
         this.memoRev = rev;
      }

      return this.memo;
   }

   private String[] computeLines() {
      HudData d = HudModules.data();
      if (!d.inGame()) {
         return new String[0];
      } else {
         String on = " " + HudLang.toggled();
         boolean sprintOn = d.sprintToggled();
         boolean sneakOn = d.crouchToggled();
         if (HudModules.editing && !sprintOn && !sneakOn) {
            return new String[]{"SPRINT" + on, "SNEAK" + on};
         } else {
            boolean onlyToggle = ClientOptions.get().bool("toggles.onlyToggle", true);
            String sprint = sprintOn ? "SPRINT" + on : (!onlyToggle && d.sprinting() ? "SPRINT" : null);
            String sneak = sneakOn ? "SNEAK" + on : (!onlyToggle && d.crouching() ? "SNEAK" : null);
            if (sprint != null && sneak != null) {
               return new String[]{sprint, sneak};
            } else if (sprint != null) {
               return new String[]{sprint};
            } else {
               return sneak != null ? new String[]{sneak} : new String[0];
            }
         }
      }
   }

   @Override
   public boolean present() {
      return this.lines().length > 0;
   }
}
