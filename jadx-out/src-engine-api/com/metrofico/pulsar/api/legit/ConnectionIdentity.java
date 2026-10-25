package com.metrofico.pulsar.api.legit;

import com.metrofico.pulsar.api.social.PulsarPlayers;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HexFormat;
import java.util.Locale;

public final class ConnectionIdentity {
   private static volatile String host;
   private static volatile String typedHost;
   private static volatile String keyHash;

   private ConnectionIdentity() {
   }

   public static void connecting(String typed) {
      keyHash = null;
      typedHost = addressOf(typed);
      host = hostOf(typed);
      PulsarPlayers.room(typedHost);
   }

   public static void encryptingWith(PublicKey key) {
      keyHash = key == null ? null : sha256(key.getEncoded());
   }

   public static String host() {
      return host;
   }

   public static String keyHash() {
      return keyHash;
   }

   public static String typedHost() {
      return typedHost;
   }

   public static void left() {
      reset();
      PulsarPlayers.room(null);
   }

   static void reset() {
      host = null;
      typedHost = null;
      keyHash = null;
   }

   static String addressOf(String typed) {
      if (typed != null && !typed.isBlank()) {
         String h = typed.trim().toLowerCase(Locale.ROOT);
         if (h.startsWith("[")) {
            int end = h.indexOf(93);
            return end > 1 ? h.substring(1, end) : null;
         } else {
            int colon = h.lastIndexOf(58);
            if (colon > 0 && h.indexOf(58) == colon) {
               h = h.substring(0, colon);
            }

            while (h.endsWith(".")) {
               h = h.substring(0, h.length() - 1);
            }

            return h.isEmpty() ? null : h;
         }
      } else {
         return null;
      }
   }

   static String hostOf(String typed) {
      if (typed == null) {
         return null;
      } else {
         String h = typed.trim();
         if (!h.isEmpty() && !h.startsWith("[")) {
            int colon = h.lastIndexOf(58);
            if (colon > 0 && h.indexOf(58) == colon && h.substring(colon + 1).chars().allMatch(Character::isDigit)) {
               h = h.substring(0, colon);
            }

            while (h.endsWith(".")) {
               h = h.substring(0, h.length() - 1);
            }

            h = h.toLowerCase(Locale.ROOT);
            return !h.isEmpty() && h.indexOf(46) >= 0 && !h.chars().allMatch(c -> Character.isDigit(c) || c == 46) && !h.contains(":") ? h : null;
         } else {
            return null;
         }
      }
   }

   static String sha256(byte[] data) {
      try {
         return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(data));
      } catch (NoSuchAlgorithmException var2) {
         throw new IllegalStateException(var2);
      }
   }
}
