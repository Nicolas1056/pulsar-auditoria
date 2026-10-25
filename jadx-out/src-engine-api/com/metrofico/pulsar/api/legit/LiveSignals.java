package com.metrofico.pulsar.api.legit;

import com.metrofico.pulsar.api.input.MouseLock;
import com.metrofico.pulsar.api.pack.Json;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

public final class LiveSignals {
   static final long PERIOD_NS = 1000000000L;
   private static volatile Consumer<String> sink;
   private static long lastNs;
   private static long seq;
   private static String lastMouse;
   private static volatile String windowHost;
   private static final Object LOCK = new Object();
   private static int windowCpsLeft;
   private static int windowCpsRight;
   private static int windowCappedLeft;
   private static int windowCappedRight;
   private static boolean windowActive;

   private LiveSignals() {
   }

   public static void install(Consumer<String> out) {
      sink = out;
   }

   public static void tick(long nowNs) {
      String host = LegitWatch.host();
      if (host == null) {
         if (lastNs != 0L) {
            reset();
            if (GameSignals.serverId() != null) {
               GameSignals.stop();
            }
         }
      } else {
         windowHost = host;
         boolean toServer = EntryPass.activeHost() != null;
         if (!toServer && !host.equals(GameSignals.serverId())) {
            GameSignals.start(host);
         }

         if (lastNs == 0L) {
            lastNs = nowNs;
            ClickCap.drainPressed(0);
            ClickCap.drainPressed(1);
         } else if (nowNs - lastNs >= 1000000000L) {
            lastNs = nowNs;
            Map<String, Object> m = second();
            Consumer<String> out = sink;
            if (m != null && out != null && toServer) {
               out.accept(Json.write(m));
            }
         }
      }
   }

   static Map<String, Object> second() {
      int left = ClickCap.drainPressed(0);
      int right = ClickCap.drainPressed(1);
      int cappedLeft = ClickCap.drainCapped(0);
      int cappedRight = ClickCap.drainCapped(1);
      Map<String, Object> reach = ReachSensor.drainLive();
      Map<String, Object> mouse = mouse();
      String mouseKey = mouse == null ? null : mouse.toString();
      boolean mouseChanged = !Objects.equals(mouseKey, lastMouse);
      lastMouse = mouseKey;
      synchronized (LOCK) {
         windowCpsLeft = Math.max(windowCpsLeft, left);
         windowCpsRight = Math.max(windowCpsRight, right);
         windowCappedLeft += cappedLeft;
         windowCappedRight += cappedRight;
         windowActive |= left + right > 0 || reach != null;
      }

      if (left + right == 0 && reach == null && !mouseChanged) {
         return null;
      } else {
         Map<String, Object> m = new LinkedHashMap<>();
         m.put("t", "legit.live");
         m.put("seq", ++seq);
         m.put("cps", pair(left, right));
         m.put("capped", pair(cappedLeft, cappedRight));
         if (reach != null) {
            m.put("reach", reach);
         }

         if (mouse != null) {
            m.put("mouse", mouse);
         }

         return m;
      }
   }

   static Map<String, Object> drainWindow() {
      Map<String, Object> reach = ReachSensor.drainWindow();
      Map<String, Object> m = new LinkedHashMap<>();
      if (LegitWatch.host() == null) {
         windowHost = null;
      }

      synchronized (LOCK) {
         if (!windowActive && reach == null) {
            return null;
         }

         m.put("cpsMaxLeft", windowCpsLeft);
         m.put("cpsMaxRight", windowCpsRight);
         m.put("capped", pair(windowCappedLeft, windowCappedRight));
         windowCpsLeft = 0;
         windowCpsRight = 0;
         windowCappedLeft = 0;
         windowCappedRight = 0;
         windowActive = false;
      }

      m.put("caps", pair(ClickCap.cap(0), ClickCap.cap(1)));
      if (reach != null) {
         m.put("reach", reach);
      }

      return m;
   }

   static String windowHost() {
      return windowHost;
   }

   static void reset() {
      lastNs = 0L;
      seq = 0L;
      lastMouse = null;
      ReachSensor.drainLive();
      ClickCap.drainPressed(0);
      ClickCap.drainPressed(1);
   }

   private static Map<String, Object> mouse() {
      MouseLock lock = MouseLock.current();
      if (lock == null) {
         return null;
      } else {
         Map<String, Object> m = new LinkedHashMap<>();
         m.put("pinned", lock.pinnedVidPid());
         m.put("devices", lock.connectedCount());
         m.put("blocked", lock.blocked());
         return m;
      }
   }

   private static Map<String, Object> pair(int left, int right) {
      Map<String, Object> m = new LinkedHashMap<>();
      m.put("left", left);
      m.put("right", right);
      return m;
   }
}
