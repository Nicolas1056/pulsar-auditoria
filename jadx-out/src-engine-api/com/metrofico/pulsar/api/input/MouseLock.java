package com.metrofico.pulsar.api.input;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.HexFormat;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;

public final class MouseLock {
   static final long MATCH_WINDOW_MS = 300L;
   public static final long SILENCE_MS = 10000L;
   private static final int MAX_EVENTS = 64;
   private static final int MAX_PENDING = 256;
   private static volatile MouseLock current;
   private final Object lock = new Object();
   private final BooleanSupplier enforce;
   private volatile Consumer<MouseLock.Block> listener;
   private volatile Consumer<String> localLog = line -> {};
   private final Map<String, InputDevice> connected = new LinkedHashMap<>();
   private final Set<String> keyboards = new HashSet<>();
   private final Set<String> used = new HashSet<>();
   private final List<Map<String, Object>> events = new ArrayList<>();
   private final ArrayDeque<MouseLock.Physical> physical = new ArrayDeque<>();
   private final long[] game = new long[256];
   private int gameHead;
   private int gameCount;
   private String os = "";
   private InputDevice pinned;
   private String pinnedId;
   private long lastBeatMs;
   private int injected;
   private int clicks;
   private boolean wouldBlockLogged;
   private volatile MouseLock.Block block;

   public MouseLock(BooleanSupplier enforce, long nowMs) {
      this.enforce = enforce;
      this.lastBeatMs = nowMs;
   }

   public static MouseLock current() {
      return current;
   }

   public static void install(MouseLock lock) {
      current = lock;
   }

   public static long clockMs() {
      return System.nanoTime() / 1000000L;
   }

   public void onBlock(Consumer<MouseLock.Block> l) {
      this.listener = l;
      MouseLock.Block b = this.block;
      if (b != null) {
         l.accept(b);
      }
   }

   public void localLog(Consumer<String> sink) {
      this.localLog = sink;
   }

   public boolean blocked() {
      return this.block != null;
   }

   public MouseLock.Block block() {
      return this.block;
   }

   public String pinnedVidPid() {
      synchronized (this.lock) {
         return this.pinned == null ? null : this.pinned.vid() + ":" + this.pinned.pid();
      }
   }

   public int connectedCount() {
      synchronized (this.lock) {
         return this.connected.size();
      }
   }

   public static void onGameClick() {
      MouseLock l = current;
      if (l != null) {
         l.gameClick(clockMs());
      }
   }

   public static boolean suppressed() {
      MouseLock l = current;
      return l != null && l.blocked();
   }

   public void gameClick(long nowMs) {
      MouseLock.Block b;
      synchronized (this.lock) {
         if (this.block != null) {
            return;
         }

         this.clicks++;
         if (this.gameCount == 256) {
            this.gameHead = (this.gameHead + 1) % 256;
            this.gameCount--;
            this.injected++;
         }

         this.game[(this.gameHead + this.gameCount) % 256] = nowMs;
         this.gameCount++;
         b = this.match(nowMs);
      }

      this.trip(b);
   }

   public void onLine(String line, long nowMs) {
      String[] t = line.split(" ", 7);
      String var5 = t[0];
      switch (var5) {
         case "hb":
            this.beat(nowMs);
            break;
         case "click":
            if (t.length >= 2) {
               this.physicalClick(t[1], nowMs);
            }
            break;
         case "cap":
            if (t.length >= 3 && t[2].equals("keyboard")) {
               synchronized (this.lock) {
                  this.keyboards.add(t[1]);
               }
            }
            break;
         case "dev":
            if (t.length >= 3 && t[1].equals("-")) {
               this.removed(t[2]);
            } else if (t.length == 7 && t[1].equals("+")) {
               this.added(device(t[2], t[3], t[4], t[5], t[6]));
            }
            break;
         case "blind":
            String[] b = line.split(" ", 6);
            if (b.length == 6) {
               this.trip(new MouseLock.Block(MouseLock.Reason.DETECTOR_BLIND, device(b[1], b[2], b[3], b[4], b[5])));
            }
            break;
         case "hello":
            synchronized (this.lock) {
               this.os = t.length >= 3 ? t[2] : "";
            }

            this.beat(nowMs);
      }
   }

   public void detectorGone() {
      this.trip(new MouseLock.Block(MouseLock.Reason.DETECTOR_GONE, null));
   }

   public void tick(long nowMs) {
      MouseLock.Block b;
      synchronized (this.lock) {
         b = this.block == null ? this.match(nowMs) : null;
         if (b == null && this.block == null && nowMs - this.lastBeatMs > 10000L) {
            b = new MouseLock.Block(MouseLock.Reason.DETECTOR_SILENT, null);
         }
      }

      this.trip(b);
   }

   private void beat(long nowMs) {
      synchronized (this.lock) {
         this.lastBeatMs = nowMs;
      }
   }

   private void physicalClick(String id, long nowMs) {
      MouseLock.Block b;
      synchronized (this.lock) {
         if (this.block != null) {
            return;
         }

         if (id.equals("0")) {
            return;
         }

         if (this.physical.size() == 256) {
            this.physical.pollFirst();
         }

         this.physical.addLast(new MouseLock.Physical(nowMs, id));
         b = this.match(nowMs);
      }

      this.trip(b);
   }

   private MouseLock.Block match(long nowMs) {
      this.dropStalePhysical(nowMs);

      while (this.gameCount > 0) {
         long g = this.game[this.gameHead];
         MouseLock.Physical own = null;
         MouseLock.Physical other = null;

         for (MouseLock.Physical p : this.physical) {
            if (Math.abs(p.atMs() - g) <= 300L) {
               if (p.id().equals(this.pinnedId)) {
                  own = p;
                  break;
               }

               if (other == null) {
                  other = p;
               }
            }
         }

         boolean windowClosed = nowMs - g > 300L;
         if (own == null && !windowClosed) {
            return null;
         }

         this.gameHead = (this.gameHead + 1) % 256;
         this.gameCount--;
         if (own != null) {
            this.physical.remove(own);
            this.used.add(own.id());
         } else if (other == null) {
            this.injected++;
         } else {
            if (this.pinned != null) {
               this.used.add(other.id());
               return new MouseLock.Block(MouseLock.Reason.OTHER_DEVICE, this.deviceOf(other.id()));
            }

            this.physical.remove(other);
            this.used.add(other.id());
            InputDevice d = this.deviceOf(other.id());
            this.pinned = d;
            this.pinnedId = other.id();
            this.event("pinned", d);
         }
      }

      return null;
   }

   private void dropStalePhysical(long nowMs) {
      long oldestGame = this.gameCount > 0 ? this.game[this.gameHead] : nowMs;
      long horizon = Math.min(oldestGame, nowMs) - 300L;
      Iterator<MouseLock.Physical> it = this.physical.iterator();

      while (it.hasNext() && it.next().atMs() < horizon) {
         it.remove();
      }
   }

   private InputDevice deviceOf(String id) {
      return this.connected.getOrDefault(id, new InputDevice(id, "other", "", "", "-", "", ""));
   }

   private void added(InputDevice d) {
      synchronized (this.lock) {
         this.connected.put(d.id(), d);
         this.event("connected", d);
         if (this.pinned != null && this.pinnedId == null && this.pinned.physicalKey().equals(d.physicalKey())) {
            this.pinnedId = d.id();
            this.event("reconnected", d);
         }
      }
   }

   private void removed(String id) {
      synchronized (this.lock) {
         InputDevice d = this.connected.remove(id);
         this.keyboards.remove(id);
         if (d != null) {
            this.event("disconnected", d);
         }

         if (id.equals(this.pinnedId)) {
            this.pinnedId = null;
         }
      }
   }

   private static InputDevice device(String id, String kind, String vidPid, String serial, String names) {
      String[] vp = vidPid.split(":", 2);
      String[] n = names.split("\t", 2);
      return new InputDevice(id, kind, vp[0], vp.length > 1 ? vp[1] : "", sha256(serial), n[0], n.length > 1 ? n[1] : "");
   }

   static String sha256(String serial) {
      if (!serial.isEmpty() && !serial.equals("-")) {
         try {
            byte[] h = MessageDigest.getInstance("SHA-256").digest(serial.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(h);
         } catch (NoSuchAlgorithmException var2) {
            return "-";
         }
      } else {
         return "-";
      }
   }

   private void trip(MouseLock.Block b) {
      if (b != null) {
         boolean enforcing = this.enforce.getAsBoolean();
         synchronized (this.lock) {
            if (this.block != null || !enforcing && this.wouldBlockLogged) {
               return;
            }

            Map<String, Object> e = this.event((enforcing ? "blocked " : "would-block ") + reasonName(b.reason()), b.device());
            e.put("type", enforcing ? "blocked" : "would-block");
            e.put("reason", reasonName(b.reason()));
            if (!enforcing) {
               this.wouldBlockLogged = true;
               return;
            }

            this.block = b;
            this.gameCount = 0;
            this.physical.clear();
         }

         Consumer<MouseLock.Block> l = this.listener;
         if (l != null) {
            l.accept(b);
         }
      }
   }

   private Map<String, Object> event(String type, InputDevice d) {
      Map<String, Object> e = new LinkedHashMap<>();
      e.put("type", type);
      if (d != null) {
         e.put("device", d.toJson());
      }

      if (this.events.size() < 64) {
         this.events.add(e);
      }

      if (!type.equals("connected") && !type.equals("disconnected")) {
         this.localLog.accept("game: " + type + (d == null ? "" : " " + d.vid() + ":" + d.pid() + " " + d.displayName() + " [" + d.kind() + "]"));
      }

      return e;
   }

   private static String reasonName(MouseLock.Reason r) {
      return r.name().toLowerCase(Locale.ROOT).replace('_', '-');
   }

   private Map<String, Object> inventoryJson(InputDevice d, String id) {
      Map<String, Object> o = d.toJson();
      o.put("keyboard", this.keyboards.contains(id));
      o.put("used", this.used.contains(id));
      return o;
   }

   public Map<String, Object> report() {
      synchronized (this.lock) {
         Map<String, Object> out = new LinkedHashMap<>();
         out.put("os", this.os);
         List<Map<String, Object>> inv = new ArrayList<>(this.connected.size());

         for (InputDevice d : this.connected.values()) {
            inv.add(this.inventoryJson(d, d.id()));
         }

         out.put("devices", inv);
         out.put("pinned", this.pinned == null ? null : this.inventoryJson(this.pinned, this.pinnedId != null ? this.pinnedId : this.pinned.id()));
         out.put("blocked", this.block == null ? null : reasonName(this.block.reason()));
         out.put("injected", this.injected);
         out.put("clicks", this.clicks);
         out.put("inputSha256", InputPipe.binarySha256());
         out.put("events", List.copyOf(this.events));
         this.events.clear();
         this.injected = 0;
         this.clicks = 0;
         return out;
      }
   }

   public record Block(MouseLock.Reason reason, InputDevice device) {
   }

   private record Physical(long atMs, String id) {
   }

   public static enum Reason {
      OTHER_DEVICE,
      DETECTOR_GONE,
      DETECTOR_SILENT,
      DETECTOR_BLIND;
   }
}
