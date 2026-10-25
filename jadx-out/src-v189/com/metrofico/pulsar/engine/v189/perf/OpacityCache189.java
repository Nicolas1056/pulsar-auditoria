package com.metrofico.pulsar.engine.v189.perf;

import java.util.HashMap;
import java.util.Map;

final class OpacityCache189 {
   private static final int MAX_SECTIONS = 8192;
   private static final long[] EMPTY = new long[0];
   private final Map<Long, long[]> sections = new HashMap<>();
   private final long[] pending = new long[8192];
   private int pendingCount;
   private final long[] drained = new long[8192];
   volatile int generation;
   private volatile boolean clearAll;

   static long key(int sx, int sy, int sz) {
      return (long)(sx & 4194303) << 42 | (long)(sy & 1048575) << 22 | sz & 4194303;
   }

   void invalidate(int sx, int sy, int sz) {
      long k = key(sx, sy, sz);
      synchronized (this.pending) {
         if (this.pendingCount <= 0 || this.pending[this.pendingCount - 1] != k) {
            if (this.pendingCount == this.pending.length) {
               this.pendingCount = 0;
               this.clearAll = true;
            } else {
               this.pending[this.pendingCount++] = k;
            }
         }
      }
   }

   void clear() {
      this.clearAll = true;
   }

   void sync() {
      if (this.clearAll) {
         this.clearAll = false;
         this.sections.clear();
         synchronized (this.pending) {
            this.pendingCount = 0;
            this.generation++;
         }
      } else {
         int n;
         synchronized (this.pending) {
            n = this.pendingCount;
            System.arraycopy(this.pending, 0, this.drained, 0, n);
            this.pendingCount = 0;
            this.generation++;
         }

         if (!this.sections.isEmpty()) {
            for (int i = 0; i < n; i++) {
               this.sections.remove(this.drained[i]);
            }
         }

         if (this.sections.size() > 8192) {
            this.sections.clear();
         }
      }
   }

   boolean opaque(bdb world, int x, int y, int z) {
      if (y >= 0 && y < 256) {
         int sx = x >> 4;
         int sy = y >> 4;
         int sz = z >> 4;
         long k = key(sx, sy, sz);
         long[] bits = this.sections.get(k);
         if (bits == null) {
            bits = build(world, sx, sy, sz);
            this.sections.put(k, bits);
         }

         if (bits.length == 0) {
            return false;
         } else {
            int i = (y & 15) << 8 | (z & 15) << 4 | x & 15;
            return (bits[i >> 6] & 1L << (i & 63)) != 0L;
         }
      } else {
         return false;
      }
   }

   private static long[] build(bdb world, int sx, int sy, int sz) {
      try {
         amy chunk = world.a(sx, sz);
         if (chunk != null && chunk.o()) {
            amz[] all = chunk.h();
            if (sy >= 0 && sy < all.length) {
               amz section = all[sy];
               if (section != null && !section.a()) {
                  long[] bits = new long[64];
                  boolean any = false;

                  for (int y = 0; y < 16; y++) {
                     for (int z = 0; z < 16; z++) {
                        for (int x = 0; x < 16; x++) {
                           if (section.a(x, y, z).c().c()) {
                              int i = y << 8 | z << 4 | x;
                              bits[i >> 6] = bits[i >> 6] | 1L << (i & 63);
                              any = true;
                           }
                        }
                     }
                  }

                  return any ? bits : EMPTY;
               } else {
                  return EMPTY;
               }
            } else {
               return EMPTY;
            }
         } else {
            return EMPTY;
         }
      } catch (RuntimeException var13) {
         return EMPTY;
      }
   }
}
