package com.metrofico.pulsar.engine.v189.controls;

import com.metrofico.pulsar.api.emote.EmoteWheel;
import com.metrofico.pulsar.api.hud.modules.Freecam;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.keys.PulsarKeys;
import com.metrofico.pulsar.api.pack.PackClient;
import com.metrofico.pulsar.engine.v189.hub.HubScreen189;
import com.metrofico.pulsar.engine.v189.hud.HudEditScreen189;
import com.metrofico.pulsar.engine.v189.pack.Pack189;

public final class PulsarKeyInput189 {
   private PulsarKeyInput189() {
   }

   public static boolean handle(ave mc, String name, boolean pressed, boolean repeat) {
      if (mc.m != null) {
         Zoom.release();
         Freecam.release();
         return false;
      } else if (!repeat && Zoom.handleKey(name, pressed)) {
         return true;
      } else if (!repeat && Freecam.handleKey(name, pressed, mc.h.y, mc.h.z)) {
         return true;
      } else if (!pressed || repeat || name == null) {
         return false;
      } else if (PulsarKeys.is("hub", name)) {
         mc.a(new HubScreen189(null));
         return true;
      } else {
         PackClient pack = Pack189.get();
         if (pack.active() && PulsarKeys.is("hudEdit", name) && pack.panels().size() > 0) {
            mc.a(new HudEditScreen189(null));
            return true;
         } else {
            return EmoteWheel.handleKey(name) ? true : PulsarKeys.toggleModule(name);
         }
      }
   }
}
