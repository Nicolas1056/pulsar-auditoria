package com.metrofico.pulsar.api.event;

import java.util.function.Consumer;

public interface EventBus {
   <E extends Event> Subscription subscribe(Class<E> var1, Priority var2, Consumer<? super E> var3);

   default <E extends Event> Subscription subscribe(Class<E> type, Consumer<? super E> listener) {
      return this.subscribe(type, Priority.NORMAL, listener);
   }

   <E extends Event> E post(E var1);
}
