package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.perf.FramePacer;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class OcclusionCuller {
   private static final long RESULT_TTL_NS = 300000000L;
   private static final long PASS_INTERVAL_NS = 20000000L;
   private static final long VISIBLE_HOLD_NS = 500000000L;
   private static final Map<Long, AABB> REQUESTS = new ConcurrentHashMap<>();
   private static final Map<Long, OcclusionCuller.Result> RESULTS = new ConcurrentHashMap<>();
   private static final OpacityCache CACHE = new OpacityCache();
   private static volatile Vec3 eye = Vec3.ZERO;
   private static volatile long frameNow;
   private static volatile ClientLevel level;
   private static volatile boolean eyeInOpaque;
   private static Thread thread;

   private OcclusionCuller() {
   }

   public static long entityKey(int id) {
      return id;
   }

   public static long blockKey(long posLong) {
      return posLong | 4611686018427387904L;
   }

   public static void invalidateSection(int sx, int sy, int sz) {
      CACHE.invalidate(sx, sy, sz);
   }

   public static void levelChanged(ClientLevel l) {
      level = l;
      CACHE.clear();
      REQUESTS.clear();
      RESULTS.clear();
   }

   public static void frame(Vec3 eyePos, ClientLevel l) {
      eye = eyePos;
      frameNow = System.nanoTime();
      level = l;
      if (thread == null || !thread.isAlive()) {
         thread = new Thread(OcclusionCuller::run, "pulsar-occlusion");
         thread.setDaemon(true);
         thread.setPriority(4);
         thread.start();
         BootstrapLog.info("perf: occlusion thread started");
      }
   }

   public static boolean hidden(long key, AABB box) {
      REQUESTS.put(key, box);
      if (eyeInOpaque) {
         return false;
      } else {
         OcclusionCuller.Result r = RESULTS.get(key);
         return r != null && r.hidden() && frameNow - r.at() < 300000000L;
      }
   }

   private static void run() {
      while (true) {
         long start = System.nanoTime();

         try {
            pass();
         } catch (Throwable var8) {
            BootstrapLog.info("perf: occlusion pass failed: " + var8);
            RESULTS.clear();
         }

         long elapsed = System.nanoTime() - start;
         long sleep = (20000000L * FramePacer.occlusionFactor() - elapsed) / 1000000L;

         try {
            Thread.sleep(Math.max(1L, sleep));
         } catch (InterruptedException var7) {
            return;
         }
      }
   }

   private static void pass() {
      ClientLevel l = level;
      if (l != null && ClientOptions.get().enabled("perf")) {
         CACHE.sync();
         Vec3 e = eye;
         eyeInOpaque = CACHE.opaque(l, (int)Math.floor(e.x), (int)Math.floor(e.y), (int)Math.floor(e.z));
         if (eyeInOpaque) {
            REQUESTS.clear();
            RESULTS.clear();
         } else {
            List<Entry<Long, AABB>> batch = new ArrayList<>(REQUESTS.entrySet());
            REQUESTS.clear();
            long now = System.nanoTime();

            for (Entry<Long, AABB> entry : batch) {
               boolean visible = anyRayReaches(l, e, entry.getValue());
               OcclusionCuller.Result prev = RESULTS.get(entry.getKey());
               int streak = visible ? 0 : (prev == null ? 1 : prev.streak() + 1);
               long lastVisible = visible ? now : (prev == null ? now - 500000000L : prev.lastVisible());
               RESULTS.put(entry.getKey(), new OcclusionCuller.Result(!visible && streak >= 2 && now - lastVisible >= 500000000L, now, streak, lastVisible));
            }

            if (RESULTS.size() > 4096) {
               RESULTS.entrySet().removeIf(r -> now - r.getValue().at() > 300000000L);
            }
         }
      } else {
         REQUESTS.clear();
         RESULTS.clear();
      }
   }

   private static boolean anyRayReaches(ClientLevel l, Vec3 eye, AABB box) {
      double cx = (box.minX + box.maxX) / 2.0;
      double cy = (box.minY + box.maxY) / 2.0;
      double cz = (box.minZ + box.maxZ) / 2.0;
      if (clear(l, eye, cx, cy, cz)) {
         return true;
      } else {
         double ix = (box.maxX - box.minX) * 0.1;
         double iy = (box.maxY - box.minY) * 0.1;
         double iz = (box.maxZ - box.minZ) * 0.1;
         double[] xs = new double[]{box.minX + ix, box.maxX - ix};
         double[] ys = new double[]{box.minY + iy, box.maxY - iy};
         double[] zs = new double[]{box.minZ + iz, box.maxZ - iz};
         if (!clear(l, eye, xs[0], cy, cz)
            && !clear(l, eye, xs[1], cy, cz)
            && !clear(l, eye, cx, ys[0], cz)
            && !clear(l, eye, cx, ys[1], cz)
            && !clear(l, eye, cx, cy, zs[0])
            && !clear(l, eye, cx, cy, zs[1])) {
            for (double x : xs) {
               for (double y : ys) {
                  for (double z : zs) {
                     if (clear(l, eye, x, y, z)) {
                        return true;
                     }
                  }
               }
            }

            return false;
         } else {
            return true;
         }
      }
   }

   private static boolean clear(ClientLevel l, Vec3 from, double tx, double ty, double tz) {
      PerfStats.RAYS.incrementAndGet();
      double dx = tx - from.x;
      double dy = ty - from.y;
      double dz = tz - from.z;
      double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
      if (len < 1.0E-6) {
         return true;
      } else {
         dx /= len;
         dy /= len;
         dz /= len;
         int x = (int)Math.floor(from.x);
         int y = (int)Math.floor(from.y);
         int z = (int)Math.floor(from.z);
         int ex = (int)Math.floor(tx);
         int ey = (int)Math.floor(ty);
         int ez = (int)Math.floor(tz);
         int stepX = dx > 0.0 ? 1 : (dx < 0.0 ? -1 : 0);
         int stepY = dy > 0.0 ? 1 : (dy < 0.0 ? -1 : 0);
         int stepZ = dz > 0.0 ? 1 : (dz < 0.0 ? -1 : 0);
         double tDeltaX = stepX == 0 ? Double.MAX_VALUE : Math.abs(1.0 / dx);
         double tDeltaY = stepY == 0 ? Double.MAX_VALUE : Math.abs(1.0 / dy);
         double tDeltaZ = stepZ == 0 ? Double.MAX_VALUE : Math.abs(1.0 / dz);
         double tMaxX = stepX == 0 ? Double.MAX_VALUE : (stepX > 0 ? x + 1 - from.x : from.x - x) * tDeltaX;
         double tMaxY = stepY == 0 ? Double.MAX_VALUE : (stepY > 0 ? y + 1 - from.y : from.y - y) * tDeltaY;
         double tMaxZ = stepZ == 0 ? Double.MAX_VALUE : (stepZ > 0 ? z + 1 - from.z : from.z - z) * tDeltaZ;
         int guard = (int)(len * 3.0) + 8;

         while (guard-- > 0) {
            if (tMaxX < tMaxY) {
               if (tMaxX < tMaxZ) {
                  x += stepX;
                  if (tMaxX > len) {
                     return true;
                  }

                  tMaxX += tDeltaX;
               } else {
                  z += stepZ;
                  if (tMaxZ > len) {
                     return true;
                  }

                  tMaxZ += tDeltaZ;
               }
            } else if (tMaxY < tMaxZ) {
               y += stepY;
               if (tMaxY > len) {
                  return true;
               }

               tMaxY += tDeltaY;
            } else {
               z += stepZ;
               if (tMaxZ > len) {
                  return true;
               }

               tMaxZ += tDeltaZ;
            }

            if (x == ex && y == ey && z == ez) {
               return true;
            }

            if (Math.min(Math.min(tMaxX, tMaxY), tMaxZ) > len - 1.0) {
               return true;
            }

            if (CACHE.opaque(l, x, y, z)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean candidate(AABB box, Vec3 eyePos) {
      return !box.contains(eyePos) && !box.inflate(6.0).contains(eyePos)
         ? box.maxX - box.minX <= 8.0 && box.maxY - box.minY <= 8.0 && box.maxZ - box.minZ <= 8.0
         : false;
   }

   public static Vec3 eye() {
      return eye;
   }

   static Minecraft mc() {
      return Minecraft.getInstance();
   }

   private record Result(boolean hidden, long at, int streak, long lastVisible) {
   }
}
