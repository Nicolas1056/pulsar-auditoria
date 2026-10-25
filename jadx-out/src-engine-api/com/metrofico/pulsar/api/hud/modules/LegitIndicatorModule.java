package com.metrofico.pulsar.api.hud.modules;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.legit.EntryPass;
import com.metrofico.pulsar.api.legit.LegitSession;
import com.metrofico.pulsar.api.legit.LegitState;

public final class LegitIndicatorModule extends TextModule {
   private static final String[] NONE = new String[0];
   private static final String[] VERIFIED = new String[]{"Pulsar · Verificado"};
   private static final String[] OBSERVED = new String[]{"Pulsar · En observación"};
   private static final String[] UNVERIFIED = new String[]{"Pulsar · Sin verificar"};
   private static final String[] CHECKING = new String[]{"Pulsar · Verificando…"};
   private static final String[] OFFLINE = new String[]{"Pulsar · Sin conexión"};
   private Canvas paintCanvas;
   private int paintX;
   private int paintY;
   private final Runnable paint = () -> super.draw(this.paintCanvas, this.paintX, this.paintY);

   @Override
   public String id() {
      return "legit";
   }

   private static LegitState state() {
      LegitSession s = LegitSession.current();
      if (HudModules.editing) {
         return LegitState.INTEGRAL;
      } else {
         return s != null && EntryPass.activeHost() != null ? s.state() : null;
      }
   }

   @Override
   protected String[] lines() {
      LegitState st = state();
      if (st == null) {
         return NONE;
      } else {
         return switch (st) {
            case INTEGRAL -> VERIFIED;
            case OBSERVATION -> OBSERVED;
            case NOT_INTEGRAL -> UNVERIFIED;
            case UNKNOWN -> CHECKING;
            case OFFLINE -> OFFLINE;
         };
      }
   }

   @Override
   protected int color() {
      LegitState st = state();
      return st == LegitState.INTEGRAL ? -864230517 : (st == LegitState.OBSERVATION ? -855649946 : (st == LegitState.NOT_INTEGRAL ? -855676053 : -860638285));
   }

   @Override
   public void draw(Canvas g, int x, int y) {
      LegitState st = state();
      if (st != null) {
         this.paintCanvas = g;
         this.paintX = x;
         this.paintY = y;
         g.cached(this, st, x, y, x + this.width(g), y + this.height(g), this.paint);
         this.paintCanvas = null;
      }
   }
}
