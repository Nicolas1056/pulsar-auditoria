package com.metrofico.pulsar.bootstrap.engine;

import com.metrofico.pulsar.api.engine.EngineLogger;
import com.metrofico.pulsar.bootstrap.BootstrapLog;

final class BootstrapEngineLogger implements EngineLogger {
   public void info(String message) {
      BootstrapLog.info(message);
   }

   public void warn(String message) {
      BootstrapLog.info("WARN " + message);
   }

   public void error(String message, Throwable cause) {
      BootstrapLog.error(message, cause);
   }
}
