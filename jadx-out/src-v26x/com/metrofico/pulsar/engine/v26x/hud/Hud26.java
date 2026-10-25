package com.metrofico.pulsar.engine.v26x.hud;

import com.metrofico.pulsar.api.hud.HudData;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.hud.HudRenderer;
import com.metrofico.pulsar.api.hud.HudData.CameraPose;
import com.metrofico.pulsar.api.hud.HudData.PlayerExact;
import com.metrofico.pulsar.api.hud.HudData.PlayerPos;
import com.metrofico.pulsar.api.hud.modules.CoordsModule;
import com.metrofico.pulsar.api.hud.modules.CpsModule;
import com.metrofico.pulsar.api.hud.modules.DiagModule;
import com.metrofico.pulsar.api.hud.modules.FpsModule;
import com.metrofico.pulsar.api.hud.modules.LegitIndicatorModule;
import com.metrofico.pulsar.api.hud.modules.PingModule;
import com.metrofico.pulsar.api.hud.modules.ScoreboardModule;
import com.metrofico.pulsar.api.hud.modules.TogglesModule;
import com.metrofico.pulsar.api.ui.NodeOverlays;
import com.metrofico.pulsar.api.ui.TabListView;
import com.metrofico.pulsar.engine.v26x.hud.scoreboard.Sidebar26;
import com.metrofico.pulsar.engine.v26x.modules.ArmorModule;
import com.metrofico.pulsar.engine.v26x.modules.EffectsModule;
import com.metrofico.pulsar.engine.v26x.modules.SneakGuard;
import com.metrofico.pulsar.engine.v26x.pack.PackRuntime;
import com.metrofico.pulsar.engine.v26x.perf.NetStats;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import java.util.List;
import net.minecraft.client.AttackIndicatorStatus;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.protocol.ping.ServerboundPingRequestPacket;
import net.minecraft.util.Util;
import net.minecraft.world.phys.Vec3;
import org.joml.Quaternionf;

public final class Hud26 {
   public static final HudRenderer RENDERER = new HudRenderer(PackRuntime.get());
   public static TabListView TABLIST;
   private static boolean installed;

   private Hud26() {
   }

   public static synchronized void install() {
      if (!installed) {
         installed = true;
         HudModules.data(new Hud26.Data());
         RENDERER.extras(new NodeOverlays(PackRuntime.get()));
         TABLIST = new TabListView(PackRuntime.get());
         HudModules.register(new FpsModule());
         HudModules.register(new PingModule());
         HudModules.register(new CpsModule());
         HudModules.register(new CoordsModule());
         HudModules.register(new EffectsModule());
         HudModules.register(new ArmorModule());
         HudModules.register(new ScoreboardModule(new Sidebar26()));
         HudModules.register(new TogglesModule());
         HudModules.register(new LegitIndicatorModule());
         HudModules.register(new DiagModule());
      }
   }

   private static final class Data implements HudData {
      private int lastSentTick = -1000;

      public int fps() {
         return Minecraft.getInstance().getFps();
      }

      public long latencyMs() {
         Minecraft mc = Minecraft.getInstance();
         long value = NetStats.latencyMs();
         if (value < 0L && mc.player != null && mc.getConnection() != null) {
            PlayerInfo info = mc.getConnection().getPlayerInfo(mc.player.getUUID());
            if (info != null) {
               value = info.getLatency();
            }
         }

         return value;
      }

      public long[] latencyMinAvgMax() {
         return NetStats.latencyMinAvgMax();
      }

      public long recentGapMs() {
         return NetStats.recentGapMs();
      }

      public void sendPing() {
         Minecraft mc = Minecraft.getInstance();
         int tick = HudModules.TICK;
         if (mc.getConnection() != null && tick - this.lastSentTick >= 20 && !mc.getDebugOverlay().showNetworkCharts()) {
            this.lastSentTick = tick;
            mc.getConnection().send(new ServerboundPingRequestPacket(Util.getMillis()));
         }
      }

      public boolean connected() {
         Minecraft mc = Minecraft.getInstance();
         return mc.player != null && mc.getConnection() != null;
      }

      public boolean inGame() {
         return Minecraft.getInstance().player != null;
      }

      public PlayerPos player() {
         LocalPlayer p = Minecraft.getInstance().player;
         if (p == null) {
            return null;
         } else {
            String dir = switch (p.getDirection()) {
               case NORTH -> "north";
               case SOUTH -> "south";
               case WEST -> "west";
               case EAST -> "east";
               default -> "";
            };
            return new PlayerPos(p.getBlockX(), p.getBlockY(), p.getBlockZ(), dir);
         }
      }

      public PlayerExact playerExact() {
         LocalPlayer p = Minecraft.getInstance().player;
         if (p == null) {
            return null;
         } else {
            Vec3 pos = p.position();
            return new PlayerExact(pos.x, pos.y, pos.z, p.getYRot(1.0F), p.getUUID().toString());
         }
      }

      public boolean sprinting() {
         LocalPlayer p = Minecraft.getInstance().player;
         return p != null && p.isSprinting();
      }

      public boolean crouching() {
         LocalPlayer p = Minecraft.getInstance().player;
         return p != null && p.isCrouching();
      }

      public boolean toggleSprint() {
         Options o = Minecraft.getInstance().options;
         return o != null && (Boolean)o.toggleSprint().get();
      }

      public boolean toggleCrouch() {
         Options o = Minecraft.getInstance().options;
         return o != null && (Boolean)o.toggleCrouch().get();
      }

      public boolean sprintToggled() {
         Options o = Minecraft.getInstance().options;
         return o != null && (Boolean)o.toggleSprint().get() && o.keySprint.isDown();
      }

      public boolean crouchToggled() {
         Options o = Minecraft.getInstance().options;
         return o != null && (Boolean)o.toggleCrouch().get() && o.keyShift.isDown() && !SneakGuard.holdOnly(o.keyShift);
      }

      public float attackStrength() {
         LocalPlayer p = Minecraft.getInstance().player;
         return p == null ? 1.0F : p.getAttackStrengthScale(0.0F);
      }

      public boolean attackIndicatorCrosshair() {
         Options o = Minecraft.getInstance().options;
         return o != null && o.attackIndicator().get() == AttackIndicatorStatus.CROSSHAIR;
      }

      public boolean showDebug() {
         return Minecraft.getInstance().getDebugOverlay().showDebugScreen();
      }

      public boolean tabKeyDown() {
         Options o = Minecraft.getInstance().options;
         return o != null && o.keyPlayerList.isDown();
      }

      public CameraPose camera() {
         Minecraft mc = Minecraft.getInstance();
         if (mc.gameRenderer == null) {
            return null;
         } else {
            Camera camera = mc.gameRenderer.mainCamera();
            Vec3 pos = camera.position();
            Quaternionf q = camera.rotation();
            return new CameraPose(pos.x, pos.y, pos.z, q.x, q.y, q.z, q.w, camera.getFov(), camera.yRot());
         }
      }

      public List<String> diagLines() {
         return List.of(PerfStats.line(), PerfStats.phasesLine());
      }

      public int textWidth(String text) {
         return Minecraft.getInstance().font.width(text);
      }

      public int lineHeight() {
         return 9;
      }
   }
}
