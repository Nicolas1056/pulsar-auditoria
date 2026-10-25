package com.metrofico.pulsar.launcher.daemon;

import java.util.List;

public record Preferences(
   String language,
   String theme,
   boolean minimizeWhilePlaying,
   boolean sendErrorReports,
   String playerName,
   boolean onboarded,
   String updateChannel,
   Boolean autoUpdate,
   Boolean pushEnabled,
   List<String> pushMuted,
   Boolean closeToTray,
   Boolean minimizeToTray,
   Boolean gamePriority,
   String gc,
   Boolean metricsOptIn,
   String changelogSeen,
   Boolean discordPresence
) {
   public String gcMode() {
      return "g1".equalsIgnoreCase(this.gc) ? "g1" : "zgc";
   }

   public boolean metricsOptInOn() {
      return Boolean.TRUE.equals(this.metricsOptIn);
   }

   public boolean gamePriorityOn() {
      return this.gamePriority == null || this.gamePriority;
   }

   public boolean closeToTrayOn() {
      return this.closeToTray == null || this.closeToTray;
   }

   public boolean minimizeToTrayOn() {
      return this.minimizeToTray != null && this.minimizeToTray;
   }

   public boolean pushOn() {
      return this.pushEnabled == null || this.pushEnabled;
   }

   public List<String> mutedServers() {
      return this.pushMuted == null ? List.of() : this.pushMuted;
   }

   public Preferences withPush(boolean enabled, List<String> muted) {
      return new Preferences(
         this.language,
         this.theme,
         this.minimizeWhilePlaying,
         this.sendErrorReports,
         this.playerName,
         this.onboarded,
         this.updateChannel,
         this.autoUpdate,
         enabled,
         List.copyOf(muted),
         this.closeToTray,
         this.minimizeToTray,
         this.gamePriority,
         this.gc,
         this.metricsOptIn,
         this.changelogSeen,
         this.discordPresence
      );
   }

   public static Preferences defaults() {
      return new Preferences("es", "system", true, false, "Dev", false, "stable", true, true, List.of(), true, false, true, "zgc", null, null, true);
   }

   public boolean discordPresenceOn() {
      return this.discordPresence == null || this.discordPresence;
   }

   public String channel() {
      return this.updateChannel != null && !this.updateChannel.isBlank() ? this.updateChannel : "stable";
   }

   public boolean autoUpdateOn() {
      return this.autoUpdate == null || this.autoUpdate;
   }
}
