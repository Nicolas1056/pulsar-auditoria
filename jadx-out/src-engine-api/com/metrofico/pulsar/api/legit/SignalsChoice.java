package com.metrofico.pulsar.api.legit;

import java.util.concurrent.CompletableFuture;

@FunctionalInterface
public interface SignalsChoice {
   CompletableFuture<Boolean> shares(String var1, boolean var2);

   static SignalsChoice mandatory() {
      return (serverId, requested) -> CompletableFuture.completedFuture(requested);
   }
}
