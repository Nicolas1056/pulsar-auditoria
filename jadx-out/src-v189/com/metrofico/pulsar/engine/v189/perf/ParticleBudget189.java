package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.perf.FramePacer;
import java.util.concurrent.atomic.AtomicLong;

public final class ParticleBudget189 {
   public static final AtomicLong DROPPED = new AtomicLong();
   public static final Runnable RENDER_BEGIN = () -> PerfStats189.begin(2);
   public static final Runnable RENDER_END = () -> PerfStats189.end(2);

   private ParticleBudget189() {
   }

   public static boolean reject(beb p, int currentCount) {
      ClientOptions o = ClientOptions.get();
      if (!o.enabled("perf")) {
         return false;
      } else {
         int budget = (int)(o.integer("perf.particleBudget", 1024) * FramePacer.particleScale());
         if (currentCount >= budget) {
            DROPPED.incrementAndGet();
            return true;
         } else {
            int maxDist = o.integer("perf.particleDistance", 32);
            pk eye = ave.A().ac();
            if (eye == null) {
               return false;
            } else {
               double dx = p.s - eye.s;
               double dy = p.t - (eye.t + eye.aS());
               double dz = p.u - eye.u;
               if (dx * dx + dy * dy + dz * dz > (double)maxDist * maxDist) {
                  DROPPED.incrementAndGet();
                  return true;
               } else {
                  return false;
               }
            }
         }
      }
   }
}
