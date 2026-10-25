package com.metrofico.pulsar.engine.v26x.hub;

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
import com.metrofico.pulsar.engine.v26x.emote.V26xEmoteHost;
import com.metrofico.pulsar.engine.v26x.hud.Hud26;
import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import com.mojang.blaze3d.platform.InputConstants.Type;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.core.BlockPos;

public final class HubSetup {
   private static boolean done;

   private HubSetup() {
   }

   public static synchronized void install() {
      if (!done) {
         done = true;
         ClientOptions.get().hooks(new V26xOptionHooks());
         HubPreview.register("crosshair", CrosshairRenderer::preview);
         HubOptions.available(o -> !HubOptions.LEGACY_ONLY_MODULES.contains(o.id()));
         HubOptions.paramAvailable(id -> !HubOptions.LEGACY_ONLY_PARAMS.contains(id));
         Hud26.install();
         HudLang.source(() -> {
            Minecraft mc = Minecraft.getInstance();
            return mc.options == null ? null : mc.options.languageCode;
         });
         PulsarKeys.vanilla(
            name -> {
               Minecraft mc = Minecraft.getInstance();
               Key k = KeyCode.byName(name);
               if (mc.options != null && k != null) {
                  com.mojang.blaze3d.platform.InputConstants.Key key = KeyCode.mouse(name)
                     ? Type.MOUSE.getOrCreate(Integer.parseInt(name.substring(5)) - 1)
                     : Type.KEYSYM.getOrCreate(k.glfw());
                  List<String> out = new ArrayList<>();

                  for (KeyMapping m : mc.options.keyMappings) {
                     if (m.matches(key)) {
                        out.add(I18n.get(m.getName(), new Object[0]));
                     }
                  }

                  return out;
               } else {
                  return List.of();
               }
            }
         );
         V26xEmoteHost emoteHost = new V26xEmoteHost();
         Emotes.install(emoteHost, PackRuntime.get());
         Freecam.hooks(emoteHost::cameraType, emoteHost::cameraType);
         CameraDirector.get().chunkLoaded(f -> {
            Minecraft mc = Minecraft.getInstance();
            return mc.level != null && mc.level.hasChunkAt(BlockPos.containing(f[0], f[1], f[2]));
         });
         Zoom.hooks(new Hooks() {
            public boolean smoothCamera() {
               Minecraft mc = Minecraft.getInstance();
               return mc.options != null && mc.options.smoothCamera;
            }

            public void smoothCamera(boolean on) {
               Minecraft mc = Minecraft.getInstance();
               if (mc.options != null) {
                  mc.options.smoothCamera = on;
               }
            }
         });
      }
   }
}
