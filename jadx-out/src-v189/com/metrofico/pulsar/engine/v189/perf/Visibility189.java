package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.api.perf.FrustumMath;
import com.metrofico.pulsar.engine.v189.mixin.BuiltChunkStorageAccessor;
import com.metrofico.pulsar.engine.v189.mixin.ChunkInfoAccessor;
import com.metrofico.pulsar.engine.v189.mixin.CullingCameraViewAccessor;
import com.metrofico.pulsar.engine.v189.mixin.WorldRendererAccessor;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.lwjgl.util.vector.Vector3f;

public final class Visibility189 {
   private static final cq[] DIRS = cq.values();
   private static int[] queue = new int[4096];
   private static byte[] mask = new byte[4096];
   private static byte[] from = new byte[4096];
   private static int[] level = new int[4096];
   private static bht[] chunkOf = new bht[4096];
   private static final List<Object> POOL = new ArrayList<>();
   private static int visibleCount;
   private static final List<Object> VISIBLE = new AbstractList<Object>() {
      @Override
      public Object get(int index) {
         if (index >= Visibility189.visibleCount) {
            throw new IndexOutOfBoundsException(index);
         } else {
            return Visibility189.POOL.get(index);
         }
      }

      @Override
      public int size() {
         return Visibility189.visibleCount;
      }
   };
   private static final double[] PLANES = new double[24];
   private static final int[] DX = new int[6];
   private static final int[] DY = new int[6];
   private static final int[] DZ = new int[6];
   private static bht[] sections;
   private static int sectionCount;
   private static final ArrayList<Object> WITH_BLOCK_ENTITIES;
   private static final ArrayList<Object> WITH_ENTITIES;
   private static int entityFrame;
   private static int openFaces;
   private static long openFacesKey;
   private static int openFacesEdits;
   private static int edits;
   static volatile int lastVisible;
   private static int traced;
   private static boolean broken;
   private static long searchNanos;
   private static int searches;
   private static MethodHandle ctor;
   private static bht[] poolChunk;

   public static bht[] sections() {
      return sections;
   }

   public static int sectionCount() {
      return sectionCount;
   }

   public static List<Object> withBlockEntities() {
      return WITH_BLOCK_ENTITIES;
   }

   public static List<Object> withEntities(adm world, bfr renderer) {
      WITH_ENTITIES.clear();
      bga storage = ((WorldRendererAccessor)renderer).pulsar$chunks();
      BuiltChunkStorageAccessor st = (BuiltChunkStorageAccessor)storage;
      bht[] cells = storage.f;
      int sizeX = st.pulsar$sizeX();
      int sizeY = st.pulsar$sizeY();
      int sizeZ = st.pulsar$sizeZ();
      int frame = ++entityFrame;
      List<pk> entities = world.f;
      int i = 0;

      for (int n = entities.size(); i < n; i++) {
         pk e = entities.get(i);
         if (e.ad) {
            int cy = e.af < 0 ? 0 : Math.min(e.af, 15);
            bht section = cell(cells, sizeX, sizeY, sizeZ, e.ae << 4, cy << 4, e.ag << 4);
            if (section != null) {
               SectionData189 data = (SectionData189)section;
               int at = data.pulsar$index();
               if (at >= 0 && at < sectionCount && sections[at] == section && data.pulsar$entityStamp() != frame) {
                  cj p = section.j();
                  if (p.n() >> 4 == e.ae && p.p() >> 4 == e.ag && p.o() >> 4 == cy) {
                     data.pulsar$entityStamp(frame);
                     WITH_ENTITIES.add(data.pulsar$info());
                  }
               }
            }
         }
      }

      return WITH_ENTITIES;
   }

   public static boolean owns(List<?> visibleChunks) {
      return visibleChunks == VISIBLE && enabled();
   }

   private Visibility189() {
   }

   public static boolean enabled() {
      return FrameFlags.visibility && !broken;
   }

   public static void disable(Throwable t) {
      broken = true;
      ClientOptions.LOG.accept("perf: visibilidad propia apagada: " + t);
   }

   public static void blockChanged() {
      edits++;
   }

   public static String searchLine(int frames) {
      String s = searches != 0 && frames > 0 ? String.format(Locale.ROOT, " (búsqueda %.2f ms × %d)", searchNanos / 1000000.0 / searches, searches) : "";
      searchNanos = 0L;
      searches = 0;
      return s;
   }

   public static boolean compute(bfr renderer, pk entity, double tickDelta, bia view, int frame, boolean spectator, boolean cullingEnabled) {
      long t0 = System.nanoTime();

      boolean var10;
      try {
         var10 = compute0(renderer, entity, tickDelta, view, frame, spectator, cullingEnabled);
      } finally {
         searchNanos = searchNanos + (System.nanoTime() - t0);
         searches++;
      }

      return var10;
   }

   private static boolean compute0(bfr renderer, pk entity, double tickDelta, bia view, int frame, boolean spectator, boolean cullingEnabled) {
      if (!(view instanceof bic)) {
         return false;
      } else {
         WorldRendererAccessor wr = (WorldRendererAccessor)renderer;
         bga storage = wr.pulsar$chunks();
         BuiltChunkStorageAccessor st = (BuiltChunkStorageAccessor)storage;
         bht[] cells = storage.f;
         int sizeX = st.pulsar$sizeX();
         int sizeY = st.pulsar$sizeY();
         int sizeZ = st.pulsar$sizeZ();
         int renderDistance = ((RenderDistanceAccess)renderer).pulsar$renderDistance();
         double h = entity.P + (entity.s - entity.P) * tickDelta;
         double j = entity.Q + (entity.t - entity.Q) * tickDelta;
         double k = entity.R + (entity.u - entity.R) * tickDelta;
         int eyeX = ns.c(h);
         int eyeY = ns.c(j + entity.aS());
         int eyeZ = ns.c(k);
         bht start = cell(cells, sizeX, sizeY, sizeZ, eyeX, eyeY, eyeZ);
         if (start == null) {
            return false;
         } else {
            int camX = ns.c(h / 16.0) * 16;
            int camZ = ns.c(k / 16.0) * 16;
            planes((bic)view);
            ensure(cells.length);
            boolean cull = cullingEnabled;
            long key = (long)eyeX << 40 ^ (long)(eyeY & 65535) << 24 ^ eyeZ & 16777215;
            if (openFacesKey != key || openFacesEdits != edits) {
               openFaces = 0;

               for (cq d : wr.pulsar$openFaces(new cj(eyeX, eyeY, eyeZ))) {
                  openFaces = openFaces | 1 << d.ordinal();
               }

               openFacesKey = key;
               openFacesEdits = edits;
            }

            int open = openFaces;
            if (Integer.bitCount(open) == 1) {
               Vector3f v = wr.pulsar$facing(entity, tickDelta);
               open &= ~(1 << cq.a(v.x, v.y, v.z).d().ordinal());
            }

            visibleCount = 0;
            WITH_BLOCK_ENTITIES.clear();
            sectionCount = 0;
            if (sections.length < cells.length) {
               sections = new bht[cells.length];
            }

            int used = 0;
            if (open == 0 && !spectator) {
               if (traced < 4) {
                  traced++;
                  ClientOptions.LOG
                     .accept(
                        "perf: visibilidad #"
                           + traced
                           + " encerrado: start="
                           + start.j()
                           + " ojo="
                           + eyeX
                           + ","
                           + eyeY
                           + ","
                           + eyeZ
                           + " openMask="
                           + Integer.toBinaryString(openFaces)
                     );
               }

               info(renderer, used++, start, null, 0);
               visibleCount = 1;
               sections[sectionCount++] = start;
            } else {
               if (spectator && wr.pulsar$world().p(new cj(eyeX, eyeY, eyeZ)).c().c()) {
                  cull = false;
               }

               start.a(frame);
               if (traced < 4) {
                  traced++;
                  StringBuilder sb = new StringBuilder(
                     "perf: visibilidad #"
                        + traced
                        + " start="
                        + start.j()
                        + " ojo="
                        + eyeX
                        + ","
                        + eyeY
                        + ","
                        + eyeZ
                        + " open="
                        + Integer.toBinaryString(open)
                        + " cull="
                        + cull
                        + " spectator="
                        + spectator
                        + " rd="
                        + renderDistance
                        + " cam="
                        + camX
                        + ","
                        + camZ
                        + " planos0="
                        + PLANES[0]
                        + ","
                        + PLANES[1]
                        + ","
                        + PLANES[2]
                        + ","
                        + PLANES[3]
                        + " size="
                        + sizeX
                        + "x"
                        + sizeY
                        + "x"
                        + sizeZ
                  );

                  for (int d = 0; d < 6; d++) {
                     cj np = start.a(DIRS[d]);
                     boolean far = Math.abs(camX - np.n()) > renderDistance * 16
                        || np.o() < 0
                        || np.o() >= 256
                        || Math.abs(camZ - np.p()) > renderDistance * 16;
                     bht nb = far ? null : cell(cells, sizeX, sizeY, sizeZ, np.n(), np.o(), np.p());
                     sb.append(" | ")
                        .append(DIRS[d])
                        .append(" np=")
                        .append(np.n())
                        .append(',')
                        .append(np.o())
                        .append(',')
                        .append(np.p())
                        .append(" far=")
                        .append(far);
                     if (nb != null) {
                        sb.append(" nbPos=")
                           .append(nb.j())
                           .append(" box=")
                           .append(nb.c.a)
                           .append(',')
                           .append(nb.c.b)
                           .append(',')
                           .append(nb.c.c)
                           .append(" frustum=")
                           .append(inFrustum(nb.c));
                     }
                  }

                  ClientOptions.LOG.accept(sb.toString());
               }

               int head = 0;
               int tail = 0;
               chunkOf[0] = start;
               mask[0] = 0;
               from[0] = -1;
               level[0] = 0;
               queue[tail++] = 0;
               used = 1;

               while (head < tail) {
                  int n = queue[head++];
                  bht chunk = chunkOf[n];
                  int fromDir = from[n];
                  info(renderer, n, chunk, fromDir < 0 ? null : DIRS[fromDir], level[n]);
                  visibleCount = n + 1;
                  sections[sectionCount++] = chunk;
                  long vis = ((SectionData189)chunk).pulsar$visibility();
                  int through = fromDir < 0 ? 0 : (fromDir ^ 1) * 6;
                  cj pos = chunk.j();
                  int px = pos.n();
                  int py = pos.o();
                  int pz = pos.p();
                  int limit = renderDistance * 16;

                  for (int dx = 0; dx < 6; dx++) {
                     if ((!cull || (mask[n] & 1 << (dx ^ 1)) == 0) && (!cull || fromDir < 0 || (vis >> through + dx & 1L) != 0L)) {
                        int nx = px + DX[dx];
                        int ny = py + DY[dx];
                        int nz = pz + DZ[dx];
                        if (Math.abs(camX - nx) <= limit && ny >= 0 && ny < 256 && Math.abs(camZ - nz) <= limit) {
                           bht nb = cell(cells, sizeX, sizeY, sizeZ, nx, ny, nz);
                           if (nb != null && nb.a(frame) && inFrustum(nb.c)) {
                              int m = used++;
                              chunkOf[m] = nb;
                              mask[m] = (byte)(mask[n] | 1 << dx);
                              from[m] = (byte)dx;
                              level[m] = level[n] + 1;
                              queue[tail++] = m;
                           }
                        }
                     }
                  }
               }
            }

            for (int i = 0; i < used; i++) {
               chunkOf[i] = null;
            }

            if (wr.pulsar$visibleChunks() != VISIBLE) {
               wr.pulsar$visibleChunks(VISIBLE);
            }

            lastVisible = VISIBLE.size();
            return true;
         }
      }
   }

   private static bht cell(bht[] cells, int sizeX, int sizeY, int sizeZ, int x, int y, int z) {
      int cy = y >> 4;
      if (cy >= 0 && cy < sizeY) {
         int cx = (x >> 4) % sizeX;
         if (cx < 0) {
            cx += sizeX;
         }

         int cz = (z >> 4) % sizeZ;
         if (cz < 0) {
            cz += sizeZ;
         }

         return cells[(cz * sizeY + cy) * sizeX + cx];
      } else {
         return null;
      }
   }

   private static MethodHandle ctor() throws Throwable {
      if (ctor == null) {
         for (Class<?> inner : bfr.class.getDeclaredClasses()) {
            for (Constructor<?> c : inner.getDeclaredConstructors()) {
               Class<?>[] p = c.getParameterTypes();
               if (p.length == 4 && p[0] == bfr.class && p[1] == bht.class && p[2] == cq.class && p[3] == int.class) {
                  c.setAccessible(true);
                  ctor = MethodHandles.lookup().unreflectConstructor(c);
               }
            }
         }

         if (ctor == null) {
            throw new IllegalStateException("WorldRenderer.ChunkInfo no encontrado");
         }
      }

      return ctor;
   }

   private static Object info(bfr renderer, int i, bht chunk, cq dir, int lvl) {
      Object o;
      if (i < POOL.size()) {
         o = POOL.get(i);
         if (poolChunk[i] != chunk) {
            ChunkInfoAccessor ci = (ChunkInfoAccessor)o;
            ci.pulsar$chunk(chunk);
            ci.pulsar$direction(dir);
            ci.pulsar$level(lvl);
            poolChunk[i] = chunk;
            SectionData189 moved = (SectionData189)chunk;
            moved.pulsar$index(i);
            moved.pulsar$info(o);
         }
      } else {
         try {
            o = (Object)ctor().invoke((bfr)renderer, (bht)chunk, (cq)dir, (int)lvl);
         } catch (Throwable var8) {
            throw new IllegalStateException(var8);
         }

         POOL.add(o);
         if (poolChunk.length <= i) {
            poolChunk = Arrays.copyOf(poolChunk, Math.max(poolChunk.length * 2, i + 1));
         }

         poolChunk[i] = chunk;
         SectionData189 fresh = (SectionData189)chunk;
         fresh.pulsar$index(i);
         fresh.pulsar$info(o);
      }

      SectionData189 data = (SectionData189)chunk;
      if ((data.pulsar$layers() & 256) != 0) {
         WITH_BLOCK_ENTITIES.add(o);
      }

      return o;
   }

   private static void planes(bic view) {
      CullingCameraViewAccessor a = (CullingCameraViewAccessor)view;
      bid f = a.pulsar$clipper();
      double x = a.pulsar$x();
      double y = a.pulsar$y();
      double z = a.pulsar$z();

      for (int p = 0; p < 6; p++) {
         float[] fs = f.a[p];
         PLANES[p * 4] = fs[0];
         PLANES[p * 4 + 1] = fs[1];
         PLANES[p * 4 + 2] = fs[2];
         PLANES[p * 4 + 3] = fs[3] - (fs[0] * x + fs[1] * y + fs[2] * z);
      }
   }

   private static boolean inFrustum(aug b) {
      return FrustumMath.inside(PLANES, b.a, b.b, b.c, b.d, b.e, b.f);
   }

   private static void ensure(int n) {
      if (queue.length < n) {
         queue = new int[n];
         mask = new byte[n];
         from = new byte[n];
         level = new int[n];
         chunkOf = new bht[n];
      }
   }

   public static void clear() {
      POOL.clear();
      Arrays.fill(poolChunk, null);
      visibleCount = 0;
      WITH_BLOCK_ENTITIES.clear();
      WITH_ENTITIES.clear();
      openFacesKey = Long.MIN_VALUE;
   }

   static {
      for (int d = 0; d < 6; d++) {
         DX[d] = DIRS[d].g() * 16;
         DY[d] = DIRS[d].h() * 16;
         DZ[d] = DIRS[d].i() * 16;
      }

      sections = new bht[4096];
      WITH_BLOCK_ENTITIES = new ArrayList<>();
      WITH_ENTITIES = new ArrayList<>();
      openFacesKey = Long.MIN_VALUE;
      openFacesEdits = -1;
      poolChunk = new bht[4096];
   }
}
