package com.metrofico.pulsar.launcher.runtime;

import java.util.List;

public final class JavaPolicy {
   public static final int PLATFORM_MAJOR = 25;
   public static final List<String> LEGACY_JVM_OPENS = List.of(
      "--add-opens=java.base/java.nio=ALL-UNNAMED",
      "--add-opens=java.base/sun.nio.ch=ALL-UNNAMED",
      "--add-opens=java.base/java.lang=ALL-UNNAMED",
      "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
      "--add-opens=java.base/java.util=ALL-UNNAMED",
      "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED",
      "--add-exports=java.base/sun.nio.ch=ALL-UNNAMED",
      "--add-exports=jdk.naming.dns/com.sun.jndi.dns=ALL-UNNAMED",
      "-Dio.netty.tryReflectionSetAccessible=true"
   );

   private JavaPolicy() {
   }

   public static int requiredMajor(String versionId, int declared) {
      return Math.max(declared, 25);
   }

   public static boolean isLegacy(String versionId) {
      if (versionId != null && versionId.startsWith("1.")) {
         String rest = versionId.substring(2);
         int end = 0;

         while (end < rest.length() && Character.isDigit(rest.charAt(end))) {
            end++;
         }

         return end == 0 ? false : Integer.parseInt(rest.substring(0, end)) < 13;
      } else {
         return false;
      }
   }
}
