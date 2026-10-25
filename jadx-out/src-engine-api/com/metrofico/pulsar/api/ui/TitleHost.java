package com.metrofico.pulsar.api.ui;

public interface TitleHost {
   void singleplayer();

   void multiplayer();

   void options();

   void hub();

   void quit();

   String translate(String var1);

   String gameVersion();

   String playerName();
}
