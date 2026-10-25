package com.metrofico.pulsar.engine.v26x.hub;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hub.ClientOptions.Hooks;
import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;
import com.metrofico.pulsar.engine.v26x.modules.PerfBooster;
import com.metrofico.pulsar.engine.v26x.modules.SneakGuard;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Minecraft;
import net.minecraft.client.tutorial.TutorialSteps;

public final class V26xOptionHooks implements Hooks {
   private static ClientOptions o() {
      return ClientOptions.get();
   }

   private static boolean classicPvp() {
      return o().classicPvp();
   }

   private static boolean enabled(String m) {
      return o().enabled(m);
   }

   private static boolean bool(String k, boolean d) {
      return o().bool(k, d);
   }

   private static boolean hideTutorial() {
      return o().hideTutorial();
   }

   public void apply(String id) {
      Minecraft mc = Minecraft.getInstance();
      if ((id == null || id.equals("pvp.style")) && mc.options != null) {
         mc.options.attackIndicator().set(classicPvp() ? AttackIndicatorStatus.OFF : AttackIndicatorStatus.CROSSHAIR);
      }

      if (id == null || id.startsWith("perf.") && !id.equals("perf.backup")) {
         PerfBooster.apply();
      }

      if (id == null || id.startsWith("controls.")) {
         ToggleSneakSprint.refresh();
      }

      if ((id == null || id.startsWith("controls.")) && mc.options != null) {
         boolean on = enabled("controls");
         boolean sneak = on && bool("controls.toggleSneak", true);
         boolean sprint = on && bool("controls.toggleSprint", true);
         mc.options.toggleCrouch().set(sneak);
         mc.options.toggleSprint().set(sprint);
         if (!sneak) {
            SneakGuard.releaseLatched(mc.options.keyShift);
         }

         if (!sprint) {
            SneakGuard.releaseLatched(mc.options.keySprint);
         }
      }

      if ((id == null || id.equals("ui.tutorial")) && hideTutorial() && mc.getTutorial() != null) {
         mc.getTutorial().setStep(TutorialSteps.NONE);
      }
   }
}
