package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.perf.FramePacer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public final class OcclusionCuller189 {
   private static final long RESULT_TTL_NS = 300000000L;
   private static final long PASS_INTERVAL_NS = 20000000L;
   static final AtomicLong RAYS = new AtomicLong();
   private static final long VISIBLE_HOLD_NS = 500000000L;
   private static final Map<Long, aug> REQUESTS = new ConcurrentHashMap<>();
   private static final Map<Long, OcclusionCuller189.Result> RESULTS = new ConcurrentHashMap<>();
   private static final OpacityCache189 CACHE = new OpacityCache189();
   private static volatile double eyeX;
   private static volatile double eyeY;
   private static volatile double eyeZ;
   private static volatile long frameNow;
   private static volatile bdb world;
   private static volatile boolean eyeInOpaque;
   private static Thread thread;
   private static int lastSx0;
   private static int lastSx1;
   private static int lastSy0;
   private static int lastSy1;
   private static int lastSz0;
   private static int lastSz1;
   private static long lastInvalidate;

   private OcclusionCuller189() {
   }

   public static long entityKey(int id) {
      return id;
   }

   public static long blockKey(int x, int y, int z) {
      return (x & 67108863L) << 38 | (y & 4095L) << 26 | z & 67108863L | 4611686018427387904L;
   }

   public static void invalidateBlocks(int x0, int y0, int z0, int x1, int y1, int z1) {
      int a = x0 >> 4;
      int b = x1 >> 4;
      int c = Math.max(0, y0 >> 4);
      int d = Math.min(15, y1 >> 4);
      int e = z0 >> 4;
      int f = z1 >> 4;
      long now = CACHE.generation;
      if (a != lastSx0 || b != lastSx1 || c != lastSy0 || d != lastSy1 || e != lastSz0 || f != lastSz1 || now != lastInvalidate) {
         lastSx0 = a;
         lastSx1 = b;
         lastSy0 = c;
         lastSy1 = d;
         lastSz0 = e;
         lastSz1 = f;
         lastInvalidate = now;

         for (int sx = x0 >> 4; sx <= x1 >> 4; sx++) {
            for (int sy = Math.max(0, y0 >> 4); sy <= Math.min(15, y1 >> 4); sy++) {
               for (int sz = z0 >> 4; sz <= z1 >> 4; sz++) {
                  CACHE.invalidate(sx, sy, sz);
               }
            }
         }
      }
   }

   public static void worldChanged(bdb w) {
      world = w;
      CACHE.clear();
      REQUESTS.clear();
      RESULTS.clear();
   }

   public static void frame(double x, double y, double z, bdb w) {
      eyeX = x;
      eyeY = y;
      eyeZ = z;
      frameNow = System.nanoTime();
      world = w;
      if (thread == null || !thread.isAlive()) {
         thread = new Thread(OcclusionCuller189::run, "pulsar-occlusion");
         thread.setDaemon(true);
         thread.setPriority(4);
         thread.start();
         ClientOptions.LOG.accept("perf: occlusion thread started");
      }
   }

   public static boolean hidden(long key, aug box) {
      REQUESTS.put(key, box);
      if (eyeInOpaque) {
         return false;
      } else {
         OcclusionCuller189.Result r = RESULTS.get(key);
         return r != null && r.hidden() && frameNow - r.at() < 300000000L;
      }
   }

   private static void run() {
      while (true) {
         long start = System.nanoTime();

         try {
            pass();
         } catch (Throwable var6) {
            ClientOptions.LOG.accept("perf: occlusion pass failed: " + var6);
            RESULTS.clear();
         }

         long sleep = (20000000L * FramePacer.occlusionFactor() - (System.nanoTime() - start)) / 1000000L;

         try {
            Thread.sleep(Math.max(1L, sleep));
         } catch (InterruptedException var5) {
            return;
         }
      }
   }

   private static void pass() {
      bdb w = world;
      if (w != null && ClientOptions.get().enabled("perf")) {
         CACHE.sync();
         double ex = eyeX;
         double ey = eyeY;
         double ez = eyeZ;
         eyeInOpaque = CACHE.opaque(w, (int)Math.floor(ex), (int)Math.floor(ey), (int)Math.floor(ez));
         if (eyeInOpaque) {
            REQUESTS.clear();
            RESULTS.clear();
         } else {
            List<Entry<Long, aug>> batch = new ArrayList<>(REQUESTS.entrySet());
            REQUESTS.clear();
            long now = System.nanoTime();

            for (Entry<Long, aug> entry : batch) {
               boolean visible = anyRayReaches(w, ex, ey, ez, entry.getValue());
               OcclusionCuller189.Result prev = RESULTS.get(entry.getKey());
               int streak = visible ? 0 : (prev == null ? 1 : prev.streak() + 1);
               long lastVisible = visible ? now : (prev == null ? now - 500000000L : prev.lastVisible());
               RESULTS.put(entry.getKey(), new OcclusionCuller189.Result(!visible && streak >= 2 && now - lastVisible >= 500000000L, now, streak, lastVisible));
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

   private static boolean anyRayReaches(bdb w, double ex, double ey, double ez, aug box) {
      double cx = (box.a + box.d) / 2.0;
      double cy = (box.b + box.e) / 2.0;
      double cz = (box.c + box.f) / 2.0;
      if (clear(w, ex, ey, ez, cx, cy, cz)) {
         return true;
      } else {
         double ix = (box.d - box.a) * 0.1;
         double iy = (box.e - box.b) * 0.1;
         double iz = (box.f - box.c) * 0.1;
         double[] xs = new double[]{box.a + ix, box.d - ix};
         double[] ys = new double[]{box.b + iy, box.e - iy};
         double[] zs = new double[]{box.c + iz, box.f - iz};
         if (!clear(w, ex, ey, ez, xs[0], cy, cz)
            && !clear(w, ex, ey, ez, xs[1], cy, cz)
            && !clear(w, ex, ey, ez, cx, ys[0], cz)
            && !clear(w, ex, ey, ez, cx, ys[1], cz)
            && !clear(w, ex, ey, ez, cx, cy, zs[0])
            && !clear(w, ex, ey, ez, cx, cy, zs[1])) {
            for (double x : xs) {
               for (double y : ys) {
                  for (double z : zs) {
                     if (clear(w, ex, ey, ez, x, y, z)) {
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

   private static boolean clear(bdb w, double fx, double fy, double fz, double tx, double ty, double tz) {
      RAYS.incrementAndGet();
      double dx = tx - fx;
      double dy = ty - fy;
      double dz = tz - fz;
      double len = Math.sqrt(dx * dx + dy * dy + dz * dz);
      if (len < 1.0E-6) {
         return true;
      } else {
         dx /= len;
         dy /= len;
         dz /= len;
         int x = (int)Math.floor(fx);
         int y = (int)Math.floor(fy);
         int z = (int)Math.floor(fz);
         int ex = (int)Math.floor(tx);
         int ey = (int)Math.floor(ty);
         int ez = (int)Math.floor(tz);
         int stepX = dx > 0.0 ? 1 : (dx < 0.0 ? -1 : 0);
         int stepY = dy > 0.0 ? 1 : (dy < 0.0 ? -1 : 0);
         int stepZ = dz > 0.0 ? 1 : (dz < 0.0 ? -1 : 0);
         double tDeltaX = stepX == 0 ? Double.MAX_VALUE : Math.abs(1.0 / dx);
         double tDeltaY = stepY == 0 ? Double.MAX_VALUE : Math.abs(1.0 / dy);
         double tDeltaZ = stepZ == 0 ? Double.MAX_VALUE : Math.abs(1.0 / dz);
         double tMaxX = stepX == 0 ? Double.MAX_VALUE : (stepX > 0 ? x + 1 - fx : fx - x) * tDeltaX;
         double tMaxY = stepY == 0 ? Double.MAX_VALUE : (stepY > 0 ? y + 1 - fy : fy - y) * tDeltaY;
         double tMaxZ = stepZ == 0 ? Double.MAX_VALUE : (stepZ > 0 ? z + 1 - fz : fz - z) * tDeltaZ;
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

            if (CACHE.opaque(w, x, y, z)) {
               return false;
            }
         }

         return true;
      }
   }

   public static boolean candidate(aug box) {
      double ex = eyeX;
      double ey = eyeY;
      double ez = eyeZ;
      return ex >= box.a - 6.0 && ex <= box.d + 6.0 && ey >= box.b - 6.0 && ey <= box.e + 6.0 && ez >= box.c - 6.0 && ez <= box.f + 6.0
         ? false
         : box.d - box.a <= 8.0 && box.e - box.b <= 8.0 && box.f - box.c <= 8.0;
   }

   public static double eyeX() {
      return eyeX;
   }

   public static double eyeY() {
      return eyeY;
   }

   public static double eyeZ() {
      return eyeZ;
   }

   private record Result(boolean hidden, long at, int streak, long lastVisible) {
   }
}
