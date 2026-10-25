package com.metrofico.pulsar.api.hud;

import java.util.List;

public interface HudData {
   int fps();

   long latencyMs();

   long[] latencyMinAvgMax();

   long recentGapMs();

   void sendPing();

   boolean connected();

   boolean inGame();

   HudData.PlayerPos player();

   default HudData.PlayerExact playerExact() {
      return null;
   }

   boolean sprinting();

   boolean crouching();

   boolean toggleSprint();

   boolean toggleCrouch();

   default boolean sprintToggled() {
      return false;
   }

   default boolean crouchToggled() {
      return false;
   }

   float attackStrength();

   boolean attackIndicatorCrosshair();

   boolean showDebug();

   boolean tabKeyDown();

   HudData.CameraPose camera();

   List<String> diagLines();

   int textWidth(String var1);

   int lineHeight();

   public record CameraPose(double x, double y, double z, float qx, float qy, float qz, float qw, double fov, float yaw) {
   }

   public record PlayerExact(double x, double y, double z, float yaw, String uuid) {
   }

   public record PlayerPos(int x, int y, int z, String direction) {
   }
}
