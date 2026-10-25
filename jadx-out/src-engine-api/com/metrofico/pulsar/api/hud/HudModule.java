package com.metrofico.pulsar.api.hud;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hub.ClientOptions;

public interface HudModule {
   String id();

   default boolean enabled() {
      return ClientOptions.get().enabled(this.id());
   }

   default Zone zone() {
      return ClientOptions.get().zone(this.id() + ".zone", HudDefaults.zone(this.id(), Zone.BOTTOM_LEFT));
   }

   default double scale() {
      return ClientOptions.get().dbl(this.id() + ".scale", HudDefaults.scale(this.id()));
   }

   default Placement home(Canvas g) {
      return null;
   }

   default boolean present() {
      return true;
   }

   int width(Canvas var1);

   int height(Canvas var1);

   void draw(Canvas var1, int var2, int var3);
}
