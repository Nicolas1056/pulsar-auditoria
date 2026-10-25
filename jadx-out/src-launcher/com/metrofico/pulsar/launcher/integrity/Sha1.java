package com.metrofico.pulsar.launcher.integrity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

public final class Sha1 {
   public static String hex(byte[] data) {
      return HexFormat.of().formatHex(digest().digest(data));
   }

   public static boolean matches(byte[] data, String expectedHex) {
      return hex(data).equalsIgnoreCase(expectedHex);
   }

   public static MessageDigest digest() {
      try {
         return MessageDigest.getInstance("SHA-1");
      } catch (NoSuchAlgorithmException var1) {
         throw new IllegalStateException("SHA-1 is mandatory in every JVM", var1);
      }
   }

   private Sha1() {
   }
}
