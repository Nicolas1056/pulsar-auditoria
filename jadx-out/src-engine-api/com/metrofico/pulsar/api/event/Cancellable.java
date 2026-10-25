package com.metrofico.pulsar.api.event;

public interface Cancellable extends Event {
   boolean isCancelled();

   void setCancelled(boolean var1);

   default void cancel() {
      this.setCancelled(true);
   }
}
