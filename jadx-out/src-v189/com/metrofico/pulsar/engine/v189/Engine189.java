package com.metrofico.pulsar.engine.v189;

import com.metrofico.pulsar.api.engine.Engine;
import com.metrofico.pulsar.api.engine.EngineContext;
import com.metrofico.pulsar.api.event.EventBus;
import com.metrofico.pulsar.api.event.SimpleEventBus;
import com.metrofico.pulsar.api.event.lifecycle.ClientStoppingEvent;
import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.model.EngineInfo;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public final class Engine189 implements Engine {
   public static final Set<String> MODULES = Set.of(
      "fps",
      "ping",
      "cps",
      "coords",
      "crosshair",
      "fullbright",
      "armor",
      "effects",
      "zoom",
      "emotes",
      "perf",
      "scoreboard",
      "nametag",
      "freecam",
      "controls",
      "visuals17",
      "rawInput",
      "pulsarMark"
   );
   private final EngineInfo info;
   private final EventBus events;
   private final AtomicBoolean stopping = new AtomicBoolean();
   private final Thread shutdownHook;
   private volatile boolean closed;

   Engine189(V189Backend backend, EngineContext context) {
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
      if (engine instanceof Engine189 e) {
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
