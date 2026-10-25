package com.metrofico.pulsar.launcher.auth;

public final class AuthException extends Exception {
   private final String code;
   private final String title;

   public AuthException(String code, String title, String message) {
      this(code, title, message, null);
   }

   public AuthException(String code, String title, String message, Throwable cause) {
      super(message, cause);
      this.code = code;
      this.title = title;
   }

   public String code() {
      return this.code;
   }

   public boolean retryable() {
      String var1 = this.code;

      return switch (var1) {
         case "offline", "network", "xbox_error", "xsts_error", "minecraft_error", "msa_unavailable" -> true;
         default -> false;
      };
   }

   public String title() {
      return this.title;
   }
}
