package com.metrofico.pulsar.launcher.daemon;

public record GameSession(String state, Long pid, Long startedAtEpochMs, String logFile, Integer exitCode, String profileId, Boolean crashed, String crashReport) {
   public static GameSession idle() {
      return new GameSession("idle", null, null, null, null, null, null, null);
   }

   public static GameSession started(long pid, String log, String profileId) {
      return new GameSession("running", pid, System.currentTimeMillis(), log, null, profileId, null, null);
   }

   public GameSession exited(int code, String crashReport) {
      return new GameSession("exited", this.pid, this.startedAtEpochMs, this.logFile, code, this.profileId, code != 0 || crashReport != null, crashReport);
   }
}
