package com.metrofico.pulsar.engine.v189.gfx;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public final class PlayerSkins189 {
   private static final Map<UUID, bdc> RETAINED = new HashMap<>();
   private static final Set<UUID> PRESENT = new HashSet<>();
   private static bdb retainedWorld;

   private PlayerSkins189() {
   }

   public static void retainPresentPlayers(bdb world, bcy network) {
      if (world != retainedWorld) {
         RETAINED.clear();
         retainedWorld = world;
      }

      PRESENT.clear();

      for (Object entity : world.j) {
         if (entity instanceof wn player) {
            UUID uuid = player.aK();
            PRESENT.add(uuid);
            bdc entry = network.a(uuid);
            if (entry != null && RETAINED.get(uuid) != entry) {
               entry.g();
               RETAINED.put(uuid, entry);
            }
         }
      }

      RETAINED.keySet().retainAll(PRESENT);
   }

   public static bdc retainedEntry(UUID uuid) {
      return RETAINED.get(uuid);
   }
}
