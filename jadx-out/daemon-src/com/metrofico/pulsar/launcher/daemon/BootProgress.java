package com.metrofico.pulsar.launcher.daemon;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Consumer;

final class BootProgress {
   private final Consumer<String> emit;
   private volatile String stage = "starting";
   private volatile long stageSince = System.nanoTime();
   private volatile boolean ready;

   BootProgress(Consumer<String> emit) {
      this.emit = emit;
   }

   void step(String next) {
      LauncherLog.info("boot · " + this.stage + " " + (System.nanoTime() - this.stageSince) / 1000000L + " ms → " + next);
      this.stage = next;
      this.stageSince = System.nanoTime();
      this.emit.accept(Protocol.event("boot", this.status()));
   }

   void ready() {
      LauncherLog.info("boot · " + this.stage + " " + (System.nanoTime() - this.stageSince) / 1000000L + " ms → ready");
      this.ready = true;
      this.emit.accept(Protocol.event("boot", this.status()));
   }

   Map<String, Object> status() {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("stage", this.stage);
      m.put("ready", this.ready);
      m.put("stageMs", (System.nanoTime() - this.stageSince) / 1000000L);
      return m;
   }
}
