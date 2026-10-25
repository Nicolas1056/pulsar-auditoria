package com.metrofico.pulsar.engine.v26x.keys;

import com.metrofico.pulsar.api.emote.EmoteWheel;
import com.metrofico.pulsar.api.hud.modules.Freecam;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.keys.PulsarKeys;
import com.metrofico.pulsar.api.pack.PackClient;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.hub.HubScreen;
import com.metrofico.pulsar.engine.v26x.hud.HudEditScreen;
import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import net.minecraft.client.Minecraft;

public final class PulsarKeyInput {
   private PulsarKeyInput() {
   }

   public static boolean handle(Minecraft mc, String name, boolean pressed, boolean repeat) {
      if (mc.gui.screen() != null) {
         Zoom.release();
         Freecam.release();
         return false;
      } else if (!repeat && Zoom.handleKey(name, pressed)) {
         return true;
      } else if (!repeat && Freecam.handleKey(name, pressed, mc.player.getYRot(), mc.player.getXRot())) {
         return true;
      } else if (!pressed || repeat || name == null) {
         return false;
      } else if (PulsarKeys.is("hub", name)) {
         BootstrapLog.info("pulsar hub: opened via key " + name);
         mc.setScreenAndShow(new HubScreen(null));
         return true;
      } else {
         PackClient runtime = PackRuntime.get();
         if (runtime.active() && PulsarKeys.is("hudEdit", name) && runtime.panels().size() > 0) {
            mc.setScreenAndShow(new HudEditScreen());
            return true;
         } else {
            return EmoteWheel.handleKey(name) ? true : PulsarKeys.toggleModule(name);
         }
      }
   }
}
