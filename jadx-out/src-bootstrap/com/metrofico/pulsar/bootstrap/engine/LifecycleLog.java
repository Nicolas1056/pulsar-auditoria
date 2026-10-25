package com.metrofico.pulsar.bootstrap.engine;

import com.metrofico.pulsar.api.engine.Engine;
import com.metrofico.pulsar.api.event.Priority;
import com.metrofico.pulsar.api.event.lifecycle.ClientReadyEvent;
import com.metrofico.pulsar.api.event.lifecycle.ClientStartingEvent;
import com.metrofico.pulsar.api.event.lifecycle.ClientStoppingEvent;
import com.metrofico.pulsar.api.event.lifecycle.ClientTickEvent;
import com.metrofico.pulsar.api.model.EngineInfo;
import java.util.concurrent.atomic.AtomicLong;

final class LifecycleLog {
   static void attach(Engine engine) {
      EngineInfo log = engine.info();
      AtomicLong ticks = new AtomicLong();
      engine.events()
         .subscribe(
            ClientStartingEvent.class, Priority.MONITOR, e -> PulsarClient.context().logger().info("Lifecycle: client starting (" + log.backendId() + ")")
         );
      engine.events().subscribe(ClientReadyEvent.class, Priority.MONITOR, e -> PulsarClient.context().logger().info("Lifecycle: client ready"));
      engine.events().subscribe(ClientTickEvent.class, Priority.MONITOR, e -> {
         long n = ticks.incrementAndGet();
         if (n == 1L || n % 1200L == 0L) {
            PulsarClient.context().logger().info("Lifecycle: tick " + n);
         }
      });
      engine.events()
         .subscribe(
            ClientStoppingEvent.class,
            Priority.MONITOR,
            e -> PulsarClient.context().logger().info("Lifecycle: client stopping after " + ticks.get() + " ticks")
         );
   }

   private LifecycleLog() {
   }
}
