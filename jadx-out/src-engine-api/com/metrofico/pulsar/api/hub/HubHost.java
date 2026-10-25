package com.metrofico.pulsar.api.hub;

public interface HubHost {
   void close();

   void openHudEditor();

   default String perfLabel() {
      return null;
   }
}
