package com.metrofico.pulsar.api.camera;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.hud.HudData;
import com.metrofico.pulsar.api.hud.HudModules;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

public final class CameraDirector {
   private static final CameraDirector INSTANCE = new CameraDirector();
   private CameraPath active;
   private long startedAt;
   private long lastLoggedSecond = -1L;
   private Consumer<Map<String, Object>> events = e -> {};
   private Predicate<double[]> chunkLoaded;
   private double[] last;

   public static CameraDirector get() {
      return INSTANCE;
   }

   public void events(Consumer<Map<String, Object>> sink) {
      this.events = sink;
   }

   public void chunkLoaded(Predicate<double[]> check) {
      this.chunkLoaded = check;
   }

   public double[] last() {
      return this.active == null ? null : this.last;
   }

   public boolean apply(Map<String, Object> fields) {
      if (Boolean.TRUE.equals(fields.get("stop"))) {
         if (this.active != null) {
            this.finish("stopped");
         }

         return true;
      } else {
         CameraPath path = CameraPath.from(fields);
         if (path == null) {
            return false;
         } else {
            this.active = path;
            this.startedAt = System.currentTimeMillis();
            this.lastLoggedSecond = -1L;
            ClientOptions.LOG.accept("pulsar:v1 cinematic started: " + path.id() + " (" + path.durationMs() + " ms)");
            return true;
         }
      }
   }

   public boolean active() {
      return this.active != null;
   }

   public boolean hideHud() {
      return this.active != null && this.active.hideHud();
   }

   public boolean hideSelf() {
      return this.active != null && this.active.hideSelf();
   }

   public double[] frame() {
      CameraPath path = this.active;
      if (path == null) {
         return null;
      } else {
         double progress = (double)(System.currentTimeMillis() - this.startedAt) / path.durationMs();
         if (progress >= 1.0) {
            this.finish("finished");
            return null;
         } else {
            double[] f = path.at(progress);
            this.last = f;
            long second = (System.currentTimeMillis() - this.startedAt) / 1000L;
            if (second != this.lastLoggedSecond) {
               this.lastLoggedSecond = second;
               String chunk = this.chunkLoaded == null ? "?" : Boolean.toString(this.chunkLoaded.test(f));
               HudData.PlayerExact p = HudModules.data().playerExact();
               String player = p == null ? "?" : String.format("%.0f,%.0f,%.0f", p.x(), p.y(), p.z());
               ClientOptions.LOG
                  .accept(
                     String.format(
                        "pulsar:v1 cine t=%ds cam=%.0f,%.0f,%.0f yaw=%.0f pitch=%.0f chunk=%s body=%s", second, f[0], f[1], f[2], f[3], f[4], chunk, player
                     )
                  );
            }

            return f;
         }
      }
   }

   public boolean skip() {
      if (this.active != null && this.active.skippable()) {
         this.finish("skipped");
         return true;
      } else {
         return false;
      }
   }

   public void clear() {
      this.active = null;
   }

   private void finish(String how) {
      CameraPath path = this.active;
      this.active = null;
      if (path != null) {
         ClientOptions.LOG.accept("pulsar:v1 cinematic " + how + ": " + path.id());
         this.events.accept(Map.of("t", "event", "cap", "camera.cinematic", "cinematic", path.id(), "action", how));
      }
   }
}
