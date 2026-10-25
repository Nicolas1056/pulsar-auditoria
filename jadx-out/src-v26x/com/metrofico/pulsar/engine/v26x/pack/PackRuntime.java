package com.metrofico.pulsar.engine.v26x.pack;

import com.metrofico.pulsar.api.pack.PackClient;
import java.security.PublicKey;

public final class PackRuntime {
   private static final PackClient INSTANCE = new PackClient(new V26xPackHost());

   private PackRuntime() {
   }

   public static PackClient get() {
      return INSTANCE;
   }

   public static PublicKey pulsarKey() throws Exception {
      return PackClient.pulsarKey(PackRuntime.class);
   }
}
