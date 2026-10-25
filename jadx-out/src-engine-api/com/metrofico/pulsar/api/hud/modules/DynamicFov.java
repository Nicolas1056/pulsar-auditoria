package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;

public final class DynamicFov {
   private DynamicFov() {
   }

   public static boolean enabled() {
      return ClientOptions.get().enabled("dynamicFov");
   }

   public static void toggle() {
      ClientOptions.get().set("dynamicFov.enabled", !enabled());
   }

   public static float multiplier(float vanilla) {
      return enabled() ? vanilla : 1.0F;
   }
}
