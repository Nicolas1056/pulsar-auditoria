package com.metrofico.pulsar.engine.v189.hub;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.emote.Emotes;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hub.HubOptions;
import com.metrofico.pulsar.api.hub.HubPreview;
import com.metrofico.pulsar.api.hud.HudLang;
import com.metrofico.pulsar.api.hud.modules.CrosshairRenderer;
import com.metrofico.pulsar.api.hud.modules.Freecam;
import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.hud.modules.Zoom.Hooks;
import com.metrofico.pulsar.api.keys.KeyCode;
import com.metrofico.pulsar.api.keys.PulsarKeys;
import com.metrofico.pulsar.api.keys.KeyCode.Key;
import com.metrofico.pulsar.engine.v189.Engine189;
import com.metrofico.pulsar.engine.v189.V189Backend;
import com.metrofico.pulsar.engine.v189.emote.V189EmoteHost;
import com.metrofico.pulsar.engine.v189.hud.Hud189;
import com.metrofico.pulsar.engine.v189.pack.Pack189;
import java.util.ArrayList;
import java.util.List;

public final class HubSetup189 {
   private static boolean done;

   private HubSetup189() {
   }

   public static synchronized void install() {
      if (!done) {
         done = true;
         ClientOptions.get().hooks(new V189OptionHooks());
         HubOptions.available(o -> Engine189.MODULES.contains(o.id()));
         HubOptions.paramAvailable(id -> !id.startsWith("perf.") || V189Backend.PERF_PARAMS.contains(id));
         Hud189.install();
         HudLang.source(() -> {
            avh o = ave.A().t;
            return o == null ? null : o.aN;
         });
         PulsarKeys.vanilla(name -> {
            avh o = ave.A().t;
            Key k = KeyCode.byName(name);
            if (o != null && k != null) {
               int code = KeyCode.mouse(name) ? Integer.parseInt(name.substring(5)) - 1 - 100 : k.lwjgl2();
               List<String> out = new ArrayList<>();

               for (avb b : o.ax) {
                  if (b.i() == code) {
                     out.add(bnq.a(b.g(), new Object[0]));
                  }
               }

               return out;
            } else {
               return List.of();
            }
         });
         V189EmoteHost emoteHost = new V189EmoteHost();
         Emotes.install(emoteHost, Pack189.get());
         Freecam.hooks(emoteHost::cameraType, emoteHost::cameraType);
         CameraDirector.get().chunkLoaded(f -> {
            ave mc = ave.A();
            return mc.f != null && mc.f.e(new cj(f[0], f[1], f[2]));
         });
         Zoom.hooks(new Hooks() {
            public boolean smoothCamera() {
               avh o = ave.A().t;
               return o != null && o.aG;
            }

            public void smoothCamera(boolean on) {
               avh o = ave.A().t;
               if (o != null) {
                  o.aG = on;
               }
            }
         });
         HubPreview.register("crosshair", CrosshairRenderer::preview);
         ClientOptions.get().apply(null);
      }
   }
}
