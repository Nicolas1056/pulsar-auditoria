package com.metrofico.pulsar.engine.v189.perf;

public record CachedChunk189(long key, amy chunk) {
   public static long keyOf(int chunkX, int chunkZ) {
      return (long)chunkX << 32 ^ chunkZ & 4294967295L;
   }

   public static int slotOf(int chunkX, int chunkZ) {
      return (chunkX & 31) << 5 | chunkZ & 31;
   }
}
