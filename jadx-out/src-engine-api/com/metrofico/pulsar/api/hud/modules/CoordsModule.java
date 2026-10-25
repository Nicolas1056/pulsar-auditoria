package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudData;
import com.metrofico.pulsar.api.hud.HudDefaults;
import com.metrofico.pulsar.api.hud.HudLang;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.Zone;

public final class CoordsModule extends TextModule {
   @Override
   public String id() {
      return "coords";
   }

   @Override
   public Zone zone() {
      return ClientOptions.get().zone("coords.zone", HudDefaults.zone("coords", Zone.TOP_LEFT));
   }

   @Override
   protected boolean pixel() {
      return true;
   }

   @Override
   protected boolean background() {
      return ClientOptions.get().bool("coords.background", true);
   }

   @Override
   protected String[] lines() {
      HudData.PlayerPos p = HudModules.data().player();
      int x;
      int y;
      int z;
      String dir;
      if (p != null) {
         x = p.x();
         y = p.y();
         z = p.z();
         dir = HudLang.direction(p.direction());
      } else {
         if (!HudModules.editing) {
            return new String[0];
         }

         x = 120;
         y = 64;
         z = -42;
         dir = HudLang.direction("south");
      }

      boolean rumbo = ClientOptions.get().bool("coords.direction", true) && !dir.isEmpty();
      if ("stack".equals(ClientOptions.get().string("coords.layout", "line"))) {
         return rumbo ? new String[]{"X " + x, "Y " + y, "Z " + z, dir} : new String[]{"X " + x, "Y " + y, "Z " + z};
      } else {
         String base = "X " + x + "  Y " + y + "  Z " + z;
         return new String[]{rumbo ? base + "  " + dir : base};
      }
   }

   @Override
   public boolean present() {
      return HudModules.data().inGame() || HudModules.editing;
   }
}
