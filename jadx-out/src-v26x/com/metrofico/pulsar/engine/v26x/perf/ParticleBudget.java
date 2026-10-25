package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.perf.FramePacer;
import net.minecraft.client.particle.Particle;
import net.minecraft.world.phys.Vec3;

public final class ParticleBudget {
   private ParticleBudget() {
   }

   public static boolean reject(Particle p, int currentCount) {
      ClientOptions o = ClientOptions.get();
      if (!o.enabled("perf")) {
         return false;
      } else {
         int budget = (int)(o.integer("perf.particleBudget", 1024) * FramePacer.particleScale());
         if (currentCount >= budget) {
            PerfStats.PARTICLES_DROPPED.incrementAndGet();
            return true;
         } else {
            int maxDist = o.integer("perf.particleDistance", 32);
            Vec3 eye = OcclusionCuller.eye();
            Vec3 c = p.getBoundingBox().getCenter();
            double d = c.distanceToSqr(eye);
            if (d > (double)maxDist * maxDist) {
               PerfStats.PARTICLES_DROPPED.incrementAndGet();
               return true;
            } else {
               return false;
            }
         }
      }
   }
}
