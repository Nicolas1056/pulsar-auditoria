package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudModules;

public final class FpsModule extends TextModule {
   @Override
   public String id() {
      return "fps";
   }

   @Override
   protected boolean pixel() {
      return true;
   }

   @Override
   protected boolean smallWords() {
      return true;
   }

   @Override
   protected boolean background() {
      return ClientOptions.get().bool("fps.background", true);
   }

   @Override
   protected String[] lines() {
      return new String[]{HudModules.data().fps() + " FPS"};
   }
}
