package com.metrofico.pulsar.bootstrap.engine;

import com.metrofico.pulsar.api.engine.Engine;
import com.metrofico.pulsar.api.engine.EngineBackend;
import com.metrofico.pulsar.api.engine.EngineContext;

public final class PulsarClient {
   private static volatile EngineBackend backend;
   private static volatile EngineContext context;
   private static volatile Engine engine;

   static synchronized void install(EngineBackend selected, EngineContext ctx) {
      backend = selected;
      context = ctx;
   }

   public static EngineContext context() {
      EngineContext c = context;
      if (c == null) {
         throw new IllegalStateException("Pulsar Client is not initialized");
      } else {
         return c;
      }
   }

   public static synchronized Engine engine() {
      if (engine == null) {
         if (backend == null) {
            throw new IllegalStateException("No engine backend was selected for this game version");
         }

         engine = backend.createEngine(context());
         if (engine == null) {
            throw new IllegalStateException("Backend " + backend.id() + " returned a null engine");
         }

         context().logger().info("Engine started: " + engine.info());
         LifecycleLog.attach(engine);
      }

      return engine;
   }

   public static boolean hasBackend() {
      return backend != null;
   }

   private PulsarClient() {
   }
}
