package com.metrofico.pulsar.api.pack;

import com.metrofico.pulsar.api.hud.Placement;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public interface PackHost {
   void runOnMain(Runnable var1);

   void send(String var1);

   void registerChannel();

   Set<Capability> capabilities();

   void showConsent(ServerRecord var1, PackManifest var2, Consumer<PackSession.Consent> var3);

   void openMenu(String var1);

   void closeMenu();

   boolean applyExtra(Capability var1, Map<String, Object> var2);

   void joined();

   void left();

   boolean placeModule(String var1, Placement var2);

   void log(String var1);

   default String accountUuid() {
      return null;
   }
}
