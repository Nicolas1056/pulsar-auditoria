package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class NameOnly26 {
   private static final IntOpenHashSet IDS = new IntOpenHashSet();
   static volatile int last;
   private static int count;

   private NameOnly26() {
   }

   public static void beginFrame() {
      IDS.clear();
   }

   public static void test(Entity player) {
      if (FrameFlags.occludePlayers) {
         AABB b = player.getBoundingBox();
         double vx = (player.getX() - player.xo) * 4.0;
         double vy = (player.getY() - player.yo) * 4.0;
         double vz = (player.getZ() - player.zo) * 4.0;
         AABB box = new AABB(
            Math.min(b.minX, b.minX + vx) - 0.6,
            Math.min(b.minY, b.minY + vy) - 0.4,
            Math.min(b.minZ, b.minZ + vz) - 0.6,
            Math.max(b.maxX, b.maxX + vx) + 0.6,
            Math.max(b.maxY, b.maxY + vy) + 0.4,
            Math.max(b.maxZ, b.maxZ + vz) + 0.6
         );
         Vec3 eye = OcclusionCuller.eye();
         if (eye != null && OcclusionCuller.candidate(box, eye) && OcclusionCuller.hidden(OcclusionCuller.entityKey(player.getId()), box)) {
            IDS.add(player.getId());
            count++;
         }
      }
   }

   public static boolean active(int id) {
      return !IDS.isEmpty() && IDS.contains(id) && FrameFlags.occludePlayers;
   }

   public static String line() {
      int c = count;
      count = 0;
      return c == 0 ? "" : " · solo nombre " + c;
   }
}
