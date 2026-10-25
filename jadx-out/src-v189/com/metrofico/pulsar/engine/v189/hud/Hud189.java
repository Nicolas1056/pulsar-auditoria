package com.metrofico.pulsar.engine.v189.hud;

import com.metrofico.pulsar.api.camera.CameraDirector;
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
import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;
import com.metrofico.pulsar.api.hud.modules.TogglesModule;
import com.metrofico.pulsar.api.ui.NodeOverlays;
import com.metrofico.pulsar.api.ui.TabListView;
import com.metrofico.pulsar.engine.v189.controls.ToggleKeys189;
import com.metrofico.pulsar.engine.v189.hud.scoreboard.Sidebar189;
import com.metrofico.pulsar.engine.v189.pack.Pack189;
import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import java.util.List;

public final class Hud189 {
   public static final HudRenderer RENDERER = new HudRenderer(Pack189.get());
   public static final TabListView TABLIST = new TabListView(Pack189.get());
   private static boolean installed;
   public static float tickDelta;
   public static double frameMs;

   private Hud189() {
   }

   public static synchronized void install() {
      if (!installed) {
         installed = true;
         HudModules.data(new Hud189.Data());
         RENDERER.extras(new NodeOverlays(Pack189.get()));
         HudModules.register(new FpsModule());
         HudModules.register(new PingModule());
         HudModules.register(new CpsModule());
         HudModules.register(new CoordsModule());
         HudModules.register(new TogglesModule());
         HudModules.register(new LegitIndicatorModule());
         HudModules.register(new Armor189());
         HudModules.register(new Effects189());
         HudModules.register(new ScoreboardModule(new Sidebar189()));
         HudModules.register(new DiagModule());
      }
   }

   private static final class Data implements HudData {
      public int fps() {
         return ave.ai();
      }

      public long latencyMs() {
         ave mc = ave.A();
         if (mc.h != null && mc.u() != null) {
            bdc info = mc.u().a(mc.h.aK());
            return info == null ? -1L : info.c();
         } else {
            return -1L;
         }
      }

      public long[] latencyMinAvgMax() {
         return null;
      }

      public long recentGapMs() {
         return 0L;
      }

      public void sendPing() {
      }

      public boolean connected() {
         ave mc = ave.A();
         return mc.h != null && mc.u() != null;
      }

      public boolean inGame() {
         return ave.A().h != null;
      }

      public PlayerPos player() {
         bew p = ave.A().h;
         if (p == null) {
            return null;
         } else {
            String dir = p.aP().l();
            return new PlayerPos((int)Math.floor(p.s), (int)Math.floor(p.t), (int)Math.floor(p.u), dir);
         }
      }

      public PlayerExact playerExact() {
         bew p = ave.A().h;
         return p == null ? null : new PlayerExact(p.s, p.t, p.u, p.y, p.aK().toString());
      }

      public boolean sprinting() {
         bew p = ave.A().h;
         return p != null && p.aw();
      }

      public boolean crouching() {
         bew p = ave.A().h;
         return p != null && p.av();
      }

      public boolean toggleSprint() {
         return ToggleSneakSprint.sprint();
      }

      public boolean toggleCrouch() {
         return ToggleSneakSprint.sneak();
      }

      public boolean sprintToggled() {
         return ToggleKeys189.sprintActive();
      }

      public boolean crouchToggled() {
         return ToggleKeys189.sneakActive();
      }

      public float attackStrength() {
         return 1.0F;
      }

      public boolean attackIndicatorCrosshair() {
         return false;
      }

      public boolean showDebug() {
         avh o = ave.A().t;
         return o != null && o.aC;
      }

      public boolean tabKeyDown() {
         avh o = ave.A().t;
         return o != null && o.al.d();
      }

      public CameraPose camera() {
         ave mc = ave.A();
         bew p = mc.h;
         if (p == null) {
            return null;
         } else {
            pk e = (pk)(mc.ac() != null ? mc.ac() : p);
            aui off = auz.a();
            double x = e.p + (e.s - e.p) * Hud189.tickDelta + (off != null ? off.a : 0.0);
            double y = e.q + (e.t - e.q) * Hud189.tickDelta + (off != null ? off.b : 0.0);
            double z = e.r + (e.u - e.r) * Hud189.tickDelta + (off != null ? off.c : 0.0);
            double yawDeg = e.A + (e.y - e.A) * Hud189.tickDelta;
            double pitchDeg = e.B + (e.z - e.B) * Hud189.tickDelta;
            double[] cine = CameraDirector.get().last();
            if (cine != null) {
               x = cine[0];
               y = cine[1];
               z = cine[2];
               yawDeg = cine[3];
               pitchDeg = cine[4];
            }

            double a = Math.PI - Math.toRadians(yawDeg);
            double b = -Math.toRadians(pitchDeg);
            double sy = Math.sin(a / 2.0);
            double cy = Math.cos(a / 2.0);
            double sx = Math.sin(b / 2.0);
            double cx = Math.cos(b / 2.0);
            float qx = (float)(cy * sx);
            float qy = (float)(sy * cx);
            float qz = (float)(-sy * sx);
            float qw = (float)(cy * cx);
            double fov = mc.t != null ? mc.t.aI : 70.0;
            return new CameraPose(x, y, z, qx, qy, qz, qw, fov, (float)yawDeg);
         }
      }

      public List<String> diagLines() {
         return PerfStats189.lines();
      }

      public int textWidth(String text) {
         return ave.A().k.a(text);
      }

      public int lineHeight() {
         return ave.A().k.a;
      }
   }
}
