package com.metrofico.pulsar.engine.v189.pack;

import com.metrofico.pulsar.api.pack.PackClient;

public final class Pack189 {
   private static final PackClient INSTANCE = new PackClient(new V189PackHost());

   private Pack189() {
   }

   public static PackClient get() {
      return INSTANCE;
   }
}
