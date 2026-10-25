package com.metrofico.pulsar.engine.v189.hub;

import com.metrofico.pulsar.api.ui.TitleHost;

public final class TitleHost189 implements TitleHost {
   private final axu title;

   public TitleHost189(axu title) {
      this.title = title;
   }

   private static ave mc() {
      return ave.A();
   }

   public void singleplayer() {
      mc().a(new axv(this.title));
   }

   public void multiplayer() {
      mc().a(new azh(this.title));
   }

   public void options() {
      mc().a(new axn(this.title, mc().t));
   }

   public void hub() {
      mc().a(new HubScreen189(this.title));
   }

   public void quit() {
      mc().m();
   }

   public String translate(String key) {
      return bnq.a(key, new Object[0]);
   }

   public String gameVersion() {
      return "1.8.9";
   }

   public String playerName() {
      return mc().L() == null ? null : mc().L().c();
   }
}
