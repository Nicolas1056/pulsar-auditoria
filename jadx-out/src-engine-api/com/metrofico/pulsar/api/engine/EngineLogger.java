package com.metrofico.pulsar.api.engine;

public interface EngineLogger {
   void info(String var1);

   void warn(String var1);

   void error(String var1, Throwable var2);

   default void error(String message) {
      this.error(message, null);
   }
}
