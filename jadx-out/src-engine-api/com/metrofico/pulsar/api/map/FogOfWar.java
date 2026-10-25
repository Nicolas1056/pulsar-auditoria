package com.metrofico.pulsar.api.map;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class FogOfWar {
   public static final int DEFAULT_CELL = 16;
   public static final int MAX_REVEALED = 65536;

   private FogOfWar() {
   }

   public static long key(int cx, int cz) {
      return (long)cx << 32 ^ cz & 4294967295L;
   }

   public static Set<Long> parse(Object revealed) {
      Set<Long> out = new HashSet<>();
      if (revealed instanceof List) {
         for (Object o : (List)revealed) {
            if (o instanceof List<?> p && p.size() == 2 && p.get(0) instanceof Number a && p.get(1) instanceof Number b && out.size() < 65536) {
               out.add(key(a.intValue(), b.intValue()));
            }
         }
      }

      return out;
   }

   public static List<Object> union(Object previous, Object added) {
      Set<Long> seen = new HashSet<>();
      List<Object> out = new ArrayList<>();

      for (Object src : new Object[]{previous, added}) {
         if (src instanceof List) {
            for (Object o : (List)src) {
               if (o instanceof List<?> p
                  && p.size() == 2
                  && p.get(0) instanceof Number a
                  && p.get(1) instanceof Number b
                  && seen.add(key(a.intValue(), b.intValue()))
                  && out.size() < 65536) {
                  out.add(List.of((long)a.intValue(), (long)b.intValue()));
               }
            }
         }
      }

      return out;
   }

   public static int cellOf(double block, int cell) {
      return (int)Math.floor(block / cell);
   }

   public static List<double[]> hiddenRects(MapDefinition map, Set<Long> revealed, int cell) {
      List<double[]> out = new ArrayList<>();
      int c0 = cellOf(map.minX(), cell);
      int c1 = cellOf(map.maxX() - 1.0E-9, cell);
      int r0 = cellOf(map.minZ(), cell);
      int r1 = cellOf(map.maxZ() - 1.0E-9, cell);

      for (int r = r0; r <= r1; r++) {
         int runStart = Integer.MIN_VALUE;

         for (int c = c0; c <= c1 + 1; c++) {
            boolean hidden = c <= c1 && !revealed.contains(key(c, r));
            if (hidden && runStart == Integer.MIN_VALUE) {
               runStart = c;
            } else if (!hidden && runStart != Integer.MIN_VALUE) {
               out.add(
                  new double[]{
                     Math.max(map.minX(), (double)runStart * cell),
                     Math.max(map.minZ(), (double)r * cell),
                     Math.min(map.maxX(), (double)c * cell),
                     Math.min(map.maxZ(), (double)(r + 1) * cell)
                  }
               );
               runStart = Integer.MIN_VALUE;
            }
         }
      }

      return out;
   }

   public static boolean enabled(Map<String, Object> props) {
      return Boolean.TRUE.equals(props.get("fog"));
   }

   public static int cell(Map<String, Object> props) {
      return props.get("cell") instanceof Number n ? Math.max(1, Math.min(256, n.intValue())) : 16;
   }
}
