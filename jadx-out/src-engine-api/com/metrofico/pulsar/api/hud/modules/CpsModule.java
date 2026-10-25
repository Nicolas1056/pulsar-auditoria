package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudLang;

public final class CpsModule extends TextModule {
   @Override
   public String id() {
      return "cps";
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
      return ClientOptions.get().bool("cps.background", true);
   }

   @Override
   protected String[] lines() {
      ClientOptions o = ClientOptions.get();
      int l = ClickCounter.left();
      int r = ClickCounter.right();
      boolean right = o.bool("cps.right", true);
      return "labels".equals(o.string("cps.format", "short"))
         ? new String[]{right ? l + " " + HudLang.left() + " | " + r + " " + HudLang.right() : l + " " + HudLang.left()}
         : new String[]{right ? l + " | " + r + " CPS" : l + " CPS"};
   }
}
