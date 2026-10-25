package com.metrofico.pulsar.engine.v26x;

import com.metrofico.pulsar.api.engine.Engine;
import com.metrofico.pulsar.api.engine.EngineContext;
import com.metrofico.pulsar.api.event.EventBus;
import com.metrofico.pulsar.api.event.SimpleEventBus;
import com.metrofico.pulsar.api.event.lifecycle.ClientStoppingEvent;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.model.EngineInfo;
import java.util.concurrent.atomic.AtomicBoolean;

public final class V26xEngine implements Engine {
   private final EngineInfo info;
   private final EventBus events;
   private final AtomicBoolean stopping = new AtomicBoolean();
   private final Thread shutdownHook;
   private volatile boolean closed;

   V26xEngine(V26xBackend backend, EngineContext context) {
      this.info = new EngineInfo(backend.id(), context.gameVersion(), Runtime.version().feature());
      this.events = new SimpleEventBus((event, error) -> context.logger().error("Listener failed for " + event.getClass().getSimpleName(), error));
      ClientOptions.LOG = context.logger()::info;
      this.shutdownHook = new Thread(this::stopping, "pulsar-engine-stopping");
      Runtime.getRuntime().addShutdownHook(this.shutdownHook);
   }

   void stopping() {
      if (this.stopping.compareAndSet(false, true)) {
         this.events.post(new ClientStoppingEvent());
      }
   }

   public static void stopping(Engine engine) {
      if (engine instanceof V26xEngine e) {
         e.stopping();
      } else if (engine != null) {
         engine.events().post(new ClientStoppingEvent());
      }
   }

   public EngineInfo info() {
      return this.info;
   }

   public EventBus events() {
      return this.events;
   }

   public void close() {
      this.closed = true;

      try {
         Runtime.getRuntime().removeShutdownHook(this.shutdownHook);
      } catch (IllegalStateException var2) {
      }
   }

   boolean isClosed() {
      return this.closed;
   }

   Thread shutdownHook() {
      return this.shutdownHook;
   }
}
