package com.metrofico.pulsar.api.legit;

public final class LegitWatch {
   private static volatile boolean watched;

   private LegitWatch() {
   }

   public static void set(boolean on) {
      watched = on;
   }

   public static boolean watched() {
      return watched;
   }

   public static String host() {
      String pass = EntryPass.activeHost();
      if (pass != null) {
         return pass;
      } else {
         return watched ? ConnectionIdentity.typedHost() : null;
      }
   }
}
