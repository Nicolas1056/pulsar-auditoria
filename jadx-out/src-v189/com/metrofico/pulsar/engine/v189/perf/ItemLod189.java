package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class ItemLod189 {
   private static final Set<Integer> REPRESENTATIVES = new HashSet<>();
   private static final Map<Long, Integer> CELL_MIN = new HashMap<>();
   private static double lodDistanceSq;
   private static double rangeSq;
   private static int cell = 2;
   private static boolean enabled;
   private static int seen;
   private static int grouped;

   private ItemLod189() {
   }

   private static long cellKey(double x, double y, double z) {
      long cx = (long)Math.floor(x / cell);
      long cy = (long)Math.floor(y / cell);
      long cz = (long)Math.floor(z / cell);
      return (cx & 2097151L) << 42 | (cy & 1048575L) << 21 | cz & 2097151L;
   }

   public static void beginFrame(bdb world, double ex, double ey, double ez) {
      ClientOptions o = ClientOptions.get();
      enabled = o.enabled("perf") && o.bool("perf.itemLod", true);
      REPRESENTATIVES.clear();
      CELL_MIN.clear();
      seen = 0;
      grouped = 0;
      if (enabled && world != null) {
         int lod = o.integer("perf.itemLodDistance", 24);
         int range = o.integer("perf.itemRange", 64);
         cell = Math.max(1, o.integer("perf.itemLodCell", 2));
         lodDistanceSq = (double)lod * lod;
         rangeSq = (double)range * range;

         for (pk e : world.k) {
            if (e instanceof uz) {
               double d = e.e(ex, ey, ez);
               if (!(d < lodDistanceSq) && !(d > rangeSq)) {
                  seen++;
                  long key = cellKey(e.s, e.t, e.u);
                  Integer min = CELL_MIN.get(key);
                  if (min == null || e.F() < min) {
                     CELL_MIN.put(key, e.F());
                  }
               }
            }
         }

         REPRESENTATIVES.addAll(CELL_MIN.values());
         grouped = seen - REPRESENTATIVES.size();
      }
   }

   public static ItemLod189.Verdict check(uz item, double ex, double ey, double ez) {
      if (!enabled) {
         return ItemLod189.Verdict.VANILLA;
      } else {
         double d = item.e(ex, ey, ez);
         if (d < lodDistanceSq) {
            return ItemLod189.Verdict.VANILLA;
         } else if (d > rangeSq) {
            return ItemLod189.Verdict.HIDE;
         } else {
            return REPRESENTATIVES.contains(item.F()) ? ItemLod189.Verdict.SHOW : ItemLod189.Verdict.HIDE;
         }
      }
   }

   public static int seen() {
      return seen;
   }

   public static int grouped() {
      return grouped;
   }

   public static enum Verdict {
      VANILLA,
      SHOW,
      HIDE;
   }
}
