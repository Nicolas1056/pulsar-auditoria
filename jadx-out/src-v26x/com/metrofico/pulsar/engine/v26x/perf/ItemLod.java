package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import it.unimi.dsi.fastutil.ints.IntIterator;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.phys.Vec3;

public final class ItemLod {
   private static final IntOpenHashSet REPRESENTATIVES = new IntOpenHashSet();
   private static final Long2IntOpenHashMap CELL_MIN = new Long2IntOpenHashMap();
   private static double lodDistanceSq;
   private static double rangeSq;
   private static int cell = 2;
   private static boolean enabled;
   private static int seen;
   private static int grouped;

   private ItemLod() {
   }

   private static long cellKey(double x, double y, double z) {
      long cx = (long)Math.floor(x / cell);
      long cy = (long)Math.floor(y / cell);
      long cz = (long)Math.floor(z / cell);
      return (cx & 2097151L) << 42 | (cy & 1048575L) << 21 | cz & 2097151L;
   }

   public static void beginFrame(ClientLevel level, Vec3 eye) {
      ClientOptions o = ClientOptions.get();
      enabled = o.enabled("perf") && o.bool("perf.itemLod", true);
      REPRESENTATIVES.clear();
      CELL_MIN.clear();
      seen = 0;
      grouped = 0;
      if (enabled && level != null) {
         int lod = o.integer("perf.itemLodDistance", 24);
         int range = o.integer("perf.itemRange", 64);
         cell = Math.max(1, o.integer("perf.itemLodCell", 2));
         lodDistanceSq = (double)lod * lod;
         rangeSq = (double)range * range;
         CELL_MIN.defaultReturnValue(Integer.MAX_VALUE);

         for (Entity e : level.entitiesForRendering()) {
            if (e instanceof ItemEntity) {
               double d = e.distanceToSqr(eye);
               if (!(d < lodDistanceSq) && !(d > rangeSq)) {
                  seen++;
                  long key = cellKey(e.getX(), e.getY(), e.getZ());
                  int min = CELL_MIN.get(key);
                  if (e.getId() < min) {
                     CELL_MIN.put(key, e.getId());
                  }
               }
            }
         }

         IntIterator var12 = CELL_MIN.values().iterator();

         while (var12.hasNext()) {
            int id = (Integer)var12.next();
            REPRESENTATIVES.add(id);
         }

         grouped = seen - REPRESENTATIVES.size();
      }
   }

   public static ItemLod.Verdict check(ItemEntity item, Vec3 eye) {
      if (!enabled) {
         return ItemLod.Verdict.VANILLA;
      } else {
         double d = item.distanceToSqr(eye);
         if (d < lodDistanceSq) {
            return ItemLod.Verdict.VANILLA;
         } else if (d > rangeSq) {
            return ItemLod.Verdict.HIDE;
         } else {
            return REPRESENTATIVES.contains(item.getId()) ? ItemLod.Verdict.SHOW : ItemLod.Verdict.HIDE;
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
