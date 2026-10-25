package com.metrofico.pulsar.api.event;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class SimpleEventBus implements EventBus {
   private static final Comparator<SimpleEventBus.Listener> ORDER = Comparator.comparing(SimpleEventBus.Listener::priority)
      .thenComparingLong(SimpleEventBus.Listener::order);
   private final Map<Class<? extends Event>, List<SimpleEventBus.Listener>> listeners = new ConcurrentHashMap<>();
   private final AtomicLong counter = new AtomicLong();
   private final BiConsumer<Event, Throwable> errorHandler;

   public SimpleEventBus() {
      this((event, error) -> {
         System.err.println("Event listener failed for " + event.getClass().getName() + ": " + error);
         error.printStackTrace();
      });
   }

   public SimpleEventBus(BiConsumer<Event, Throwable> errorHandler) {
      this.errorHandler = errorHandler;
   }

   @Override
   public <E extends Event> Subscription subscribe(Class<E> type, Priority priority, Consumer<? super E> listener) {
      SimpleEventBus.Listener entry = new SimpleEventBus.Listener(type, priority, this.counter.getAndIncrement(), listener);
      this.listeners.computeIfAbsent(type, t -> new CopyOnWriteArrayList<>()).add(entry);
      return () -> this.listeners.getOrDefault(type, List.of()).remove(entry);
   }

   @Override
   public <E extends Event> E post(E event) {
      List<SimpleEventBus.Listener> targets = new ArrayList<>();

      for (Entry<Class<? extends Event>, List<SimpleEventBus.Listener>> e : this.listeners.entrySet()) {
         if (e.getKey().isInstance(event)) {
            targets.addAll(e.getValue());
         }
      }

      targets.sort(ORDER);

      for (SimpleEventBus.Listener target : targets) {
         if (!(event instanceof Cancellable c && c.isCancelled() && target.priority != Priority.MONITOR)) {
            try {
               target.consumer.accept(event);
            } catch (RuntimeException var6) {
               this.errorHandler.accept(event, var6);
            }
         }
      }

      return event;
   }

   public int listenerCount() {
      return this.listeners.values().stream().mapToInt(List::size).sum();
   }

   private record Listener(Class<? extends Event> type, Priority priority, long order, Consumer<Event> consumer) {
   }
}
