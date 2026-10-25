package com.metrofico.pulsar.engine.v26x.pack;

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
import com.metrofico.pulsar.engine.v26x.ui.MenuScreen;
import com.metrofico.pulsar.engine.v26x.ui.PackTextures;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;

final class V26xPackHost implements PackHost {
   V26xPackHost() {
      CameraDirector.get().events(e -> {
         if (PackRuntime.get().active()) {
            PackRuntime.get().sendEvent(e);
         }
      });
   }

   public void runOnMain(Runnable task) {
      Minecraft.getInstance().execute(task);
   }

   public void send(String fragment) {
      PulsarChannel.send(fragment);
   }

   public void registerChannel() {
      PulsarChannel.register();
   }

   public Set<Capability> capabilities() {
      return EnumSet.allOf(Capability.class);
   }

   public void showConsent(ServerRecord server, PackManifest manifest, Consumer<Consent> decision) {
      Minecraft.getInstance().setScreenAndShow(new ConsentScreen(server, manifest, decision));
   }

   public void openMenu(String screenId) {
      Minecraft.getInstance().setScreenAndShow(new MenuScreen(screenId));
   }

   public void closeMenu() {
      if (Minecraft.getInstance().gui.screen() instanceof MenuScreen) {
         Minecraft.getInstance().setScreenAndShow(null);
      }
   }

   public boolean applyExtra(Capability capability, Map<String, Object> fields) {
      return switch (capability) {
         case CAMERA_CINEMATIC -> CameraDirector.get().apply(fields);
         case XP_COMBAT -> {
            ClientOptions.get().serverCombat(fields.get("style") instanceof String st ? st : null);
            yield true;
         }
         case XP_EMOTES -> EmoteSync.apply(fields);
         default -> false;
      };
   }

   public void joined() {
      ClientOptions.get().apply(null);
      EmoteRelay.get().joined();
      PulsarChannel.clearInbound();
   }

   public void left() {
      EmoteRelay.get().left();
      EmotePlayer.clear();
      CameraDirector.get().clear();
      ClientOptions.get().serverCombat(null);
      PackTextures.releaseAll();
      this.closeMenu();
      PulsarChannel.clearInbound();
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
      Minecraft mc = Minecraft.getInstance();
      return mc.getUser() != null && mc.getUser().getProfileId() != null ? mc.getUser().getProfileId().toString() : null;
   }
}
