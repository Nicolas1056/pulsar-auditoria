package com.metrofico.pulsar.api.engine;

public final class PulsarVersion {
   public static final String BRAND = "Pulsar Client";
   private static final String VERSION;

   private PulsarVersion() {
   }

   public static String version() {
      return VERSION;
   }

   public static String full() {
      return "Pulsar Client " + VERSION;
   }

   static {
      String v = PulsarVersion.class.getPackage() == null ? null : PulsarVersion.class.getPackage().getImplementationVersion();
      VERSION = v != null && !v.isBlank() ? v : "dev";
   }
}
