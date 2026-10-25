package com.metrofico.pulsar.engine.v189.pack;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.emote.EmoteRelay;
import com.metrofico.pulsar.api.emote.EmoteSync;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.Placement;
import com.metrofico.pulsar.api.pack.Capability;
import com.metrofico.pulsar.api.pack.PackHost;
import com.metrofico.pulsar.api.pack.PackManifest;
import com.metrofico.pulsar.api.pack.ServerRecord;
import com.metrofico.pulsar.api.pack.PackSession.Consent;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v189.V189Backend;
import com.metrofico.pulsar.engine.v189.gfx.Textures189;
import com.metrofico.pulsar.engine.v189.ui.MenuScreen189;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

final class V189PackHost implements PackHost {
   V189PackHost() {
      CameraDirector.get().events(e -> {
         if (Pack189.get().active()) {
            Pack189.get().sendEvent(e);
         }
      });
   }

   public void runOnMain(Runnable task) {
      ave mc = ave.A();
      if (mc.aJ()) {
         task.run();
      } else {
         mc.a(task);
      }
   }

   public void send(String fragment) {
      LegacyChannel.send(fragment);
   }

   public void registerChannel() {
      LegacyChannel.register();
   }

   public Set<Capability> capabilities() {
      return V189Backend.CAPABILITIES;
   }

   public void showConsent(ServerRecord server, PackManifest manifest, Consumer<Consent> decision) {
      ave.A().a(new ConsentScreen189(server, manifest, decision));
   }

   public void openMenu(String screenId) {
      ave.A().a(new MenuScreen189(screenId));
   }

   public void closeMenu() {
      ave mc = ave.A();
      if (mc.m instanceof MenuScreen189) {
         mc.a(null);
      }
   }

   public boolean applyExtra(Capability capability, Map<String, Object> fields) {
      return switch (capability) {
         case CAMERA_CINEMATIC -> CameraDirector.get().apply(fields);
         case XP_COMBAT -> {
            String style = fields.get("style") instanceof String st ? st : null;
            if ("modern".equals(style)) {
               this.log("pulsar:v1 xp.combat modern is not available on 1.8.9; ignored");
               yield false;
            } else {
               ClientOptions.get().serverCombat(style);
               yield true;
            }
         }
         case XP_EMOTES -> EmoteSync.apply(fields);
         default -> false;
      };
   }

   public void joined() {
      EmoteRelay.get().joined();
   }

   public void left() {
      Textures189.releaseAll();
      EmoteRelay.get().left();
      EmotePlayer.clear();
      CameraDirector.get().clear();
      ClientOptions.get().serverCombat(null);
      ave mc = ave.A();
      if (mc.m instanceof ConsentScreen189) {
         mc.a(null);
      }
   }

   public boolean placeModule(String panelId, Placement placement) {
      if (!HudModules.isModule(panelId)) {
         return false;
      } else {
         String module = panelId.substring("pulsar:".length());
         ClientOptions co = ClientOptions.get();
         co.set(module + ".zone", placement.anchor().id());
         co.set(module + ".pos", placement.free() ? placement.serialize() : null);
         return true;
      }
   }

   public void log(String message) {
      BootstrapLog.info(message);
   }

   public String accountUuid() {
      avm s = ave.A().L();
      String uuid = s == null ? null : s.b();
      return uuid != null && !uuid.isEmpty() ? uuid : null;
   }
}
