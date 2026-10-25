package com.metrofico.pulsar.engine.v26x.perf;

import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.level.chunk.status.ChunkStatus;

final class OpacityCache {
   private static final int MAX_SECTIONS = 8192;
   private static final long[] EMPTY = new long[0];
   private final Long2ObjectOpenHashMap<long[]> sections = new Long2ObjectOpenHashMap();
   private final ConcurrentLinkedQueue<Long> dirty = new ConcurrentLinkedQueue<>();
   private volatile boolean clearAll;

   void invalidate(int sx, int sy, int sz) {
      this.dirty.add(SectionPos.asLong(sx, sy, sz));
   }

   void clear() {
      this.clearAll = true;
   }

   void sync() {
      if (this.clearAll) {
         this.clearAll = false;
         this.sections.clear();
         this.dirty.clear();
      } else {
         Long key;
         while ((key = this.dirty.poll()) != null) {
            this.sections.remove(key);
         }

         if (this.sections.size() > 8192) {
            this.sections.clear();
         }
      }
   }

   boolean opaque(ClientLevel level, int x, int y, int z) {
      int sx = x >> 4;
      int sy = y >> 4;
      int sz = z >> 4;
      long key = SectionPos.asLong(sx, sy, sz);
      long[] bits = (long[])this.sections.get(key);
      if (bits == null) {
         bits = build(level, sx, sy, sz);
         this.sections.put(key, bits);
      }

      if (bits.length == 0) {
         return false;
      } else {
         int i = (y & 15) << 8 | (z & 15) << 4 | x & 15;
         return (bits[i >> 6] & 1L << (i & 63)) != 0L;
      }
   }

   private static long[] build(ClientLevel level, int sx, int sy, int sz) {
      try {
         LevelChunk chunk = level.getChunkSource().getChunk(sx, sz, ChunkStatus.FULL, false);
         if (chunk == null) {
            return EMPTY;
         } else {
            int index = chunk.getSectionIndexFromSectionY(sy);
            LevelChunkSection[] all = chunk.getSections();
            if (index >= 0 && index < all.length) {
               LevelChunkSection section = all[index];
               if (section != null && !section.hasOnlyAir()) {
                  long[] bits = new long[64];
                  boolean any = false;

                  for (int y = 0; y < 16; y++) {
                     for (int z = 0; z < 16; z++) {
                        for (int x = 0; x < 16; x++) {
                           if (section.getBlockState(x, y, z).isSolidRender()) {
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
         }
      } catch (RuntimeException var14) {
         return EMPTY;
      }
   }
}
