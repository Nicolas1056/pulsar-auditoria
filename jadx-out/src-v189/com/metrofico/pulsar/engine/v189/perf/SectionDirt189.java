package com.metrofico.pulsar.engine.v189.perf;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicInteger;

public final class SectionDirt189 {
   private static int blocks;
   private static int lights;
   private static int regions;
   private static long populateNs;
   private static long gapsNs;
   private static int created;
   private static int notStored;
   private static Object lastBlock;
   private static int lastX;
   private static int lastY;
   private static int lastZ;
   private static final AtomicInteger BUILDS = new AtomicInteger();

   private SectionDirt189() {
   }

   public static void block() {
      blocks++;
   }

   public static void light() {
      lights++;
   }

   public static void region() {
      regions++;
   }

   public static void populated(long ns) {
      populateNs += ns;
   }

   public static void gapsChecked(long ns) {
      gapsNs += ns;
   }

   public static void blockEntityCreated(cj pos, Object block, boolean stored) {
      created++;
      if (!stored) {
         notStored++;
         lastBlock = block;
         lastX = pos.n();
         lastY = pos.o();
         lastZ = pos.p();
      }
   }

   public static void built() {
      BUILDS.incrementAndGet();
   }

   public static String line() {
      int b = blocks;
      int l = lights;
      int r = regions;
      int c = BUILDS.getAndSet(0);
      long p = populateNs;
      long g = gapsNs;
      regions = 0;
      lights = 0;
      blocks = 0;
      gapsNs = 0L;
      populateNs = 0L;
      int e = created;
      int n = notStored;
      Object nb = lastBlock;
      notStored = 0;
      created = 0;
      lastBlock = null;
      String be = e == 0
         ? ""
         : " · entidades de bloque creadas " + e + (n == 0 ? "" : " (sin guardar " + n + ", p. ej. " + nb + " en " + lastX + " " + lastY + " " + lastZ + ")");
      return (b + l + r + c == 0 ? "" : " · compiladas " + c + " (marcas: bloques " + b + " · luz " + l + " · chunks " + r + ")")
         + (
            p + g == 0L
               ? ""
               : String.format(Locale.ROOT, " · luz del tick %.0f ms (poblar %.0f · huecos %.0f)", (p + g) / 1000000.0, p / 1000000.0, g / 1000000.0)
         )
         + be;
   }
}
