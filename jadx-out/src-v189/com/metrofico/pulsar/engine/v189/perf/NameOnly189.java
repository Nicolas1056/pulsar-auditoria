package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;

public interface NameOnly189 {
   int pulsar$nameOnlyFrame();

   void pulsar$nameOnlyFrame(int var1);

   static void test(wn player) {
      if (FrameFlags.occludePlayers) {
         aug b = player.aR();
         double vx = (player.s - player.p) * 4.0;
         double vy = (player.t - player.q) * 4.0;
         double vz = (player.u - player.r) * 4.0;
         aug box = new aug(
            Math.min(b.a, b.a + vx) - 0.6,
            Math.min(b.b, b.b + vy) - 0.4,
            Math.min(b.c, b.c + vz) - 0.6,
            Math.max(b.d, b.d + vx) + 0.6,
            Math.max(b.e, b.e + vy) + 0.4,
            Math.max(b.f, b.f + vz) + 0.6
         );
         if (OcclusionCuller189.candidate(box) && OcclusionCuller189.hidden(OcclusionCuller189.entityKey(player.F()), box)) {
            ((NameOnly189)player).pulsar$nameOnlyFrame(EntityCache189.currentFrame());
            PerfStats189.PLAYERS_NAME_ONLY.incrementAndGet();
         }
      }
   }

   static boolean active(Object entity) {
      return entity instanceof NameOnly189 n && n.pulsar$nameOnlyFrame() == EntityCache189.currentFrame() && FrameFlags.occludePlayers;
   }
}
