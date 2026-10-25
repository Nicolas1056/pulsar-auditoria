package com.metrofico.pulsar.engine.v189.net;

import com.metrofico.pulsar.api.net.NetMeter;
import com.metrofico.pulsar.api.net.TaskDrain;
import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.FutureTask;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.function.LongSupplier;
import org.apache.logging.log4j.Logger;

public final class MainTasks189 {
   static final long BUDGET_NS = 4000000L;
   private static final TaskDrain<FutureTask<?>> DRAIN = new TaskDrain(256);
   private static final LongSupplier CLOCK = System::nanoTime;
   private static boolean handled;
   private static Logger logger;
   private static final Consumer<FutureTask<?>> RUN = task -> g.a(task, logger);
   private static final IntConsumer DRAINED = n -> NetMeter.CLIENT.drained(System.nanoTime());

   private MainTasks189() {
   }

   public static void run(Queue<FutureTask<?>> tasks, Logger log) {
      handled = false;
      if (tasks instanceof Deque<FutureTask<?>> deque) {
         handled = true;
         logger = log;
         long start = System.nanoTime();
         PerfStats189.begin(11);

         try {
            DRAIN.run(deque, tasks, DRAINED, RUN, 4000000L, CLOCK);
         } finally {
            PerfStats189.end(11);
            if (DRAIN.deferred() > 0) {
               synchronized (tasks) {
                  NetMeter.CLIENT.requeued(start);
               }
            }
         }
      }
   }

   public static boolean vanillaSeesEmpty(Queue<?> tasks) {
      return handled || tasks.isEmpty();
   }
}
