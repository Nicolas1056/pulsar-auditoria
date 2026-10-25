package com.metrofico.pulsar.launcher.daemon;

public final class DaemonException extends RuntimeException {
   private final String code;
   private final String title;
   private final String detail;

   public DaemonException(String code, String title, String message) {
      this(code, title, message, null, null);
   }

   public DaemonException(String code, String title, String message, String detail, Throwable cause) {
      super(message, cause);
      this.code = code;
      this.title = title;
      this.detail = detail;
   }

   public String code() {
      return this.code;
   }

   public String title() {
      return this.title;
   }

   public String detail() {
      return this.detail;
   }
}
