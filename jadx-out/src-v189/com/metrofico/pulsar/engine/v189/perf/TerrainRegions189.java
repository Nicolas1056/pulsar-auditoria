package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.perf.CompactCoords;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.bootstrap.BootstrapLog;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GL43;
import org.lwjgl.opengl.GLContext;

public final class TerrainRegions189 {
   public static final int STRIDE = 28;
   public static final int COMPACT_STRIDE = 16;
   private static final float POS_SCALE = 2048.0F;
   private static Boolean compactSupported;
   private static int offsetsVbo;
   private static ByteBuffer offsets = ByteBuffer.allocateDirect(49152).order(ByteOrder.nativeOrder());
   static volatile int lastCompactSections;
   private static int frameCompactSections;
   private static final int REGION_SHIFT = 7;
   private static final float SCALE = 1.000001F;
   public static final int GROUPS = 7;
   private static final int[] GROUP_COUNT = new int[7];
   private static int[] quadGroup = new int[4096];
   private static final int PLANE_BUCKETS = 36;
   private static final int[] BUCKET_CURSOR = new int[252];
   private static int[] quadBucket = new int[4096];
   private static int[] quadDest = new int[4096];
   private static long frameVerticesDrawn;
   private static long frameVerticesTotal;
   static volatile int lastFacesCulledPct;
   private static final Map<Long, TerrainRegions189.RegionBuffer> REGIONS = new HashMap<>();
   private static ByteBuffer scratch = ByteBuffer.allocateDirect(1048576).order(ByteOrder.nativeOrder());
   private static Boolean supported;
   private static Boolean indirectSupported;
   private static int indirectVbo;
   private static ByteBuffer commands = ByteBuffer.allocateDirect(65536).order(ByteOrder.nativeOrder());
   private static int commandCount;
   private static int frameIndirectCalls;
   static volatile int lastIndirectCalls;
   private static int frameSections;
   private static int frameBinds;
   static volatile int lastSections;
   static volatile int lastBinds;
   private static int offsetCount;
   private static float quadUMin;
   private static float quadUMax;
   private static float quadVMin;
   private static float quadVMax;
   private static boolean loggedPartialQuad;
   private static final List<TerrainRegions189.RegionBuffer> GROUP_ORDER = new ArrayList<>();
   private static final Map<TerrainRegions189.RegionBuffer, ArrayList<bht>> GROUP_LISTS = new IdentityHashMap<>();
   private static final ArrayList<bht> VANILLA = new ArrayList<>();
   private static final ArrayList<ArrayList<bht>> LIST_POOL = new ArrayList<>();

   static boolean ownsVbo(int id) {
      if (id == offsetsVbo) {
         return true;
      } else {
         for (TerrainRegions189.RegionBuffer r : REGIONS.values()) {
            if (r.vbo == id) {
               return true;
            }
         }

         return false;
      }
   }

   private TerrainRegions189() {
   }

   public static boolean enabled() {
      if (FrameFlags.terrainRegions && bqs.f()) {
         if (supported == null) {
            try {
               supported = GLContext.getCapabilities().OpenGL31;
            } catch (Throwable var1) {
               supported = false;
            }

            if (!supported) {
               ClientOptions.LOG.accept("perf: terrain regions need OpenGL 3.1 (glCopyBufferSubData); off");
            }
         }

         return supported;
      } else {
         return false;
      }
   }

   public static boolean indirect() {
      if (!FrameFlags.terrainMultidraw) {
         return false;
      } else {
         if (indirectSupported == null) {
            try {
               ContextCapabilities caps = GLContext.getCapabilities();
               indirectSupported = caps.OpenGL43 || caps.GL_ARB_multi_draw_indirect;
            } catch (Throwable var1) {
               indirectSupported = false;
            }

            if (!indirectSupported) {
               ClientOptions.LOG.accept("perf: terrain multidraw needs GL 4.3 (glMultiDrawArraysIndirect); off");
            }
         }

         return indirectSupported;
      }
   }

   public static boolean compact() {
      if (FrameFlags.compactVertices && indirect()) {
         if (compactSupported == null) {
            try {
               ContextCapabilities caps = GLContext.getCapabilities();
               compactSupported = caps.OpenGL33 && (caps.OpenGL42 || caps.GL_ARB_base_instance) && TerrainShader189.ready();
            } catch (Throwable var1) {
               compactSupported = false;
            }

            if (!compactSupported) {
               ClientOptions.LOG.accept("perf: compact terrain vertices need GL 3.3 + base instance + shader; off");
            }
         }

         return compactSupported;
      } else {
         return false;
      }
   }

   private static void sectionOffset(int x, int y, int z) {
      if ((offsetCount + 1) * 12 > offsets.capacity()) {
         ByteBuffer bigger = ByteBuffer.allocateDirect(offsets.capacity() * 2).order(ByteOrder.nativeOrder());
         offsets.clear();
         bigger.put(offsets);
         offsets = bigger;
      }

      int o = offsetCount * 12;
      offsets.putFloat(o, x);
      offsets.putFloat(o + 4, y);
      offsets.putFloat(o + 8, z);
      offsetCount++;
   }

   private static void command(int first, int count) {
      if ((commandCount + 1) * 16 > commands.capacity()) {
         ByteBuffer bigger = ByteBuffer.allocateDirect(commands.capacity() * 2).order(ByteOrder.nativeOrder());
         commands.clear();
         bigger.put(commands);
         commands = bigger;
      }

      int o = commandCount * 16;
      commands.putInt(o, count);
      commands.putInt(o + 4, 1);
      commands.putInt(o + 8, first);
      commands.putInt(o + 12, commandCount);
      commandCount++;
   }

   private static void flushCommands() {
      if (commandCount != 0) {
         if (indirectVbo == 0) {
            indirectVbo = bqs.e();
         }

         if (offsetCount > 0) {
            if (offsetsVbo == 0) {
               offsetsVbo = bqs.e();
            }

            offsets.position(0).limit(offsetCount * 12);
            bqs.g(bqs.R, offsetsVbo);
            GL15.glBufferData(bqs.R, offsets, 35040);
            GL20.glVertexAttribPointer(4, 3, 5126, false, 12, 0L);
            offsets.clear();
            offsetCount = 0;
            bqs.g(bqs.R, 0);
         }

         commands.position(0).limit(commandCount * 16);
         GL15.glBindBuffer(36671, indirectVbo);
         GL15.glBufferData(36671, commands, 35040);
         GL43.glMultiDrawArraysIndirect(7, 0L, commandCount, 0);
         GL15.glBindBuffer(36671, 0);
         commands.clear();
         frameIndirectCalls++;
         commandCount = 0;
      }
   }

   private static long key(int rx, int rz, int layer) {
      return (long)(rx & 1048575) << 24 | (long)(rz & 1048575) << 4 | layer;
   }

   private static long regionKey(cj pos, int layer) {
      return compact() ? key(0, 0, layer) : key(pos.n() >> 7, pos.p() >> 7, layer);
   }

   private static TerrainRegions189.RegionBuffer region(cj pos, int layer) {
      long k = regionKey(pos, layer);
      TerrainRegions189.RegionBuffer r = REGIONS.get(k);
      if (r == null) {
         boolean arena = compact();
         int rx = arena ? 0 : pos.n() >> 7;
         int rz = arena ? 0 : pos.p() >> 7;
         r = new TerrainRegions189.RegionBuffer(rx << 7, rz << 7, arena ? 16 : 28);
         REGIONS.put(k, r);
      }

      return r;
   }

   public static void upload(bht chunk, int layer, ByteBuffer data) {
      cj pos = chunk.j();
      TerrainRegions189.RegionBuffer r = region(pos, layer);
      int inBytes = data.limit() - data.position();
      int vertices = inBytes / 28;
      int count = vertices & -4;
      if (count != vertices && !loggedPartialQuad) {
         loggedPartialQuad = true;
         BootstrapLog.info(
            "terrain: section "
               + pos
               + " layer "
               + layer
               + " has "
               + vertices
               + " vertices (not whole quads); the last "
               + (vertices - count)
               + " are skipped like GL_QUADS does"
         );
      }

      int bytes = count * r.stride;
      if (scratch.capacity() < bytes) {
         scratch = ByteBuffer.allocateDirect(Integer.highestOneBit(Math.max(bytes, 1)) << 1).order(ByteOrder.nativeOrder());
      }

      scratch.clear();
      int[] groups = layer == 3 ? null : classify(data, count);
      if (r.stride == 16) {
         transformCompact(data, scratch, count, groups);
      } else {
         transform(data, scratch, count, pos.n() - r.originX, pos.o(), pos.p() - r.originZ, groups);
      }

      scratch.position(0).limit(bytes);
      TerrainRegions189.Slot s = r.slots.get(chunk);
      if (s != null && s.bytes >= bytes) {
         s.count = count;
         s.groups = groups;
         s.sectionX = pos.n();
         s.sectionY = pos.o();
         s.sectionZ = pos.p();
         if (bytes > 0) {
            bqs.g(bqs.R, r.vbo);
            GL15.glBufferSubData(bqs.R, s.offset, scratch);
            bqs.g(bqs.R, 0);
         }
      } else {
         if (s != null) {
            r.free = r.free + s.bytes;
            r.slots.remove(chunk);
            ((SectionData189)chunk).pulsar$slots()[layer] = null;
         }

         if (r.used + bytes > r.capacity) {
            grow(r, bytes);
         }

         s = new TerrainRegions189.Slot();
         s.offset = r.used;
         s.bytes = bytes;
         s.first = r.used / r.stride;
         s.count = count;
         s.groups = groups;
         s.sectionX = pos.n();
         s.sectionY = pos.o();
         s.sectionZ = pos.p();
         s.region = r;
         r.used += bytes;
         r.slots.put(chunk, s);
         ((SectionData189)chunk).pulsar$slots()[layer] = s;
         if (bytes > 0) {
            bqs.g(bqs.R, r.vbo);
            GL15.glBufferSubData(bqs.R, s.offset, scratch);
            bqs.g(bqs.R, 0);
         }
      }
   }

   private static int[] classify(ByteBuffer in, int count) {
      int quads = count / 4;
      if (quadGroup.length < quads) {
         quadGroup = new int[Integer.highestOneBit(quads) << 1];
         quadBucket = new int[quadGroup.length];
         quadDest = new int[quadGroup.length];
      }

      Arrays.fill(GROUP_COUNT, 0);
      Arrays.fill(BUCKET_CURSOR, 0);
      int base = in.position();

      for (int q = 0; q < quads; q++) {
         int o = base + q * 4 * 28;
         float x0 = in.getFloat(o);
         float y0 = in.getFloat(o + 4);
         float z0 = in.getFloat(o + 8);
         float x1 = in.getFloat(o + 28);
         float y1 = in.getFloat(o + 28 + 4);
         float z1 = in.getFloat(o + 28 + 8);
         float x2 = in.getFloat(o + 56);
         float y2 = in.getFloat(o + 56 + 4);
         float z2 = in.getFloat(o + 56 + 8);
         float ux = x1 - x0;
         float uy = y1 - y0;
         float uz = z1 - z0;
         float vx = x2 - x0;
         float vy = y2 - y0;
         float vz = z2 - z0;
         float nx = uy * vz - uz * vy;
         float ny = uz * vx - ux * vz;
         float nz = ux * vy - uy * vx;
         float ax = Math.abs(nx);
         float ay = Math.abs(ny);
         float az = Math.abs(nz);
         float len = ax + ay + az;
         int g;
         if (len < 1.0E-9F) {
            g = 6;
         } else if (ax > 0.999F * len) {
            g = nx > 0.0F ? 1 : 0;
         } else if (ay > 0.999F * len) {
            g = ny > 0.0F ? 3 : 2;
         } else if (az > 0.999F * len) {
            g = nz > 0.0F ? 5 : 4;
         } else {
            g = 6;
         }

         quadGroup[q] = g;
         quadBucket[q] = planeBucket(g, x0, y0, z0);
         BUCKET_CURSOR[g * 36 + quadBucket[q]]++;
         GROUP_COUNT[g] = GROUP_COUNT[g] + 4;
      }

      int start = 0;

      for (int i = 0; i < BUCKET_CURSOR.length; i++) {
         int n = BUCKET_CURSOR[i];
         BUCKET_CURSOR[i] = start;
         start += n;
      }

      for (int q = 0; q < quads; q++) {
         quadDest[q] = BUCKET_CURSOR[quadGroup[q] * 36 + quadBucket[q]]++;
      }

      return (int[])GROUP_COUNT.clone();
   }

   private static int planeBucket(int group, float x, float y, float z) {
      if (group == 6) {
         return 0;
      } else {
         float plane = group < 2 ? x : (group < 4 ? y : z);
         int bucket = Math.max(0, Math.min(35, (int)Math.floor(plane * 2.0F) + 2));
         boolean facesPositive = (group & 1) == 1;
         return facesPositive ? 35 - bucket : bucket;
      }
   }

   private static void transform(ByteBuffer in, ByteBuffer out, int count, int dx, int dy, int dz, int[] groups) {
      int base = in.position();
      float ox = dx + 8;
      float oy = dy + 8;
      float oz = dz + 8;

      for (int i = 0; i < count; i++) {
         int o = base + i * 28;
         int p;
         if (groups != null) {
            p = (quadDest[i >> 2] * 4 + (i & 3)) * 28;
         } else {
            p = i * 28;
         }

         out.putFloat(p, ox + (in.getFloat(o) - 8.0F) * 1.000001F);
         out.putFloat(p + 4, oy + (in.getFloat(o + 4) - 8.0F) * 1.000001F);
         out.putFloat(p + 8, oz + (in.getFloat(o + 8) - 8.0F) * 1.000001F);
         out.putLong(p + 12, in.getLong(o + 12));
         out.putLong(p + 20, in.getLong(o + 20));
      }
   }

   private static void transformCompact(ByteBuffer in, ByteBuffer out, int count, int[] groups) {
      int base = in.position();

      for (int i = 0; i < count; i++) {
         int o = base + i * 28;
         int p;
         if (groups != null) {
            p = (quadDest[i >> 2] * 4 + (i & 3)) * 16;
         } else {
            p = i * 16;
         }

         if ((i & 3) == 0) {
            float u0 = in.getFloat(o + 16);
            float u1 = in.getFloat(o + 28 + 16);
            float u2 = in.getFloat(o + 56 + 16);
            float u3 = in.getFloat(o + 84 + 16);
            float v0 = in.getFloat(o + 20);
            float v1 = in.getFloat(o + 28 + 20);
            float v2 = in.getFloat(o + 56 + 20);
            float v3 = in.getFloat(o + 84 + 20);
            quadUMin = Math.min(Math.min(u0, u1), Math.min(u2, u3));
            quadUMax = Math.max(Math.max(u0, u1), Math.max(u2, u3));
            quadVMin = Math.min(Math.min(v0, v1), Math.min(v2, v3));
            quadVMax = Math.max(Math.max(v0, v1), Math.max(v2, v3));
         }

         out.putShort(p, (short)CompactCoords.encodePos(8.0F + (in.getFloat(o) - 8.0F) * 1.000001F));
         out.putShort(p + 2, (short)CompactCoords.encodePos(8.0F + (in.getFloat(o + 4) - 8.0F) * 1.000001F));
         out.putShort(p + 4, (short)CompactCoords.encodePos(8.0F + (in.getFloat(o + 8) - 8.0F) * 1.000001F));
         out.put(p + 6, (byte)Math.max(0, Math.min(255, in.getShort(o + 24))));
         out.put(p + 7, (byte)Math.max(0, Math.min(255, in.getShort(o + 26))));
         out.putInt(p + 8, in.getInt(o + 12));
         out.putShort(p + 12, uvInward(in.getFloat(o + 16), quadUMin, quadUMax));
         out.putShort(p + 14, uvInward(in.getFloat(o + 20), quadVMin, quadVMax));
      }
   }

   private static short uvInward(float value, float min, float max) {
      return (short)CompactCoords.encodeUvInward(value, min, max);
   }

   private static short u16(float v) {
      return (short)Math.max(0, Math.min(65535, Math.round(v)));
   }

   private static void grow(TerrainRegions189.RegionBuffer r, int extra) {
      int live = r.used - r.free;
      int capacity = Math.max(1048576, Math.max(r.capacity * 2, live + extra));
      int vbo = bqs.e();
      bqs.g(bqs.R, vbo);
      GL15.glBufferData(bqs.R, capacity, 35048);
      bqs.g(bqs.R, 0);
      int cursor = 0;
      if (r.vbo != 0 && live > 0) {
         GL15.glBindBuffer(36662, r.vbo);
         GL15.glBindBuffer(36663, vbo);

         for (TerrainRegions189.Slot s : r.slots.values()) {
            if (s.bytes > 0) {
               GL31.glCopyBufferSubData(36662, 36663, s.offset, cursor, s.bytes);
            }

            s.offset = cursor;
            s.first = cursor / r.stride;
            cursor += s.bytes;
         }

         GL15.glBindBuffer(36662, 0);
         GL15.glBindBuffer(36663, 0);
      }

      if (r.vbo != 0) {
         bqs.g(r.vbo);
      }

      r.vbo = vbo;
      r.capacity = capacity;
      r.used = cursor;
      r.free = 0;
   }

   public static void free(bht chunk) {
      Arrays.fill(((SectionData189)chunk).pulsar$slots(), null);
      if (!REGIONS.isEmpty()) {
         for (TerrainRegions189.RegionBuffer r : REGIONS.values()) {
            TerrainRegions189.Slot s = r.slots.remove(chunk);
            if (s != null) {
               r.free = r.free + s.bytes;
            }
         }
      }
   }

   public static void clear() {
      if (indirectVbo != 0) {
         bqs.g(indirectVbo);
         indirectVbo = 0;
      }

      if (offsetsVbo != 0) {
         bqs.g(offsetsVbo);
         offsetsVbo = 0;
      }

      for (TerrainRegions189.RegionBuffer r : REGIONS.values()) {
         if (r.vbo != 0) {
            bqs.g(r.vbo);
         }
      }

      REGIONS.clear();
   }

   public static TerrainRegions189.Slot slot(bht chunk, int layer) {
      TerrainRegions189.RegionBuffer r = REGIONS.get(regionKey(chunk.j(), layer));
      return r == null ? null : r.slots.get(chunk);
   }

   public static void compactPointers() {
      GL20.glVertexAttribPointer(0, 3, 5123, false, 16, 0L);
      GL20.glVertexAttribPointer(1, 2, 5121, true, 16, 6L);
      GL20.glVertexAttribPointer(2, 4, 5121, true, 16, 8L);
      GL20.glVertexAttribPointer(3, 2, 5123, true, 16, 12L);
   }

   private static void compactBegin() {
      GL11.glDisableClientState(32884);
      GL11.glDisableClientState(32886);
      bqs.l(bqs.r);
      GL11.glDisableClientState(32888);
      bqs.l(bqs.q);
      GL11.glDisableClientState(32888);

      for (int a = 0; a <= 4; a++) {
         GL20.glEnableVertexAttribArray(a);
      }

      GL33.glVertexAttribDivisor(4, 1);
      TerrainShader189.use();
   }

   private static void compactEnd() {
      TerrainShader189.stop();
      GL33.glVertexAttribDivisor(4, 0);

      for (int a = 0; a <= 4; a++) {
         GL20.glDisableVertexAttribArray(a);
      }

      GL11.glEnableClientState(32884);
      GL11.glEnableClientState(32886);
      bqs.l(bqs.r);
      GL11.glEnableClientState(32888);
      bqs.l(bqs.q);
      GL11.glEnableClientState(32888);
   }

   public static void pointers() {
      GL11.glVertexPointer(3, 5126, 28, 0L);
      GL11.glColorPointer(4, 5121, 28, 12L);
      GL11.glTexCoordPointer(2, 5126, 28, 16L);
      bqs.l(bqs.r);
      GL11.glTexCoordPointer(2, 5122, 28, 24L);
      bqs.l(bqs.q);
   }

   public static List<bht> drawGrouped(List<bht> chunks, int layer, double viewX, double viewY, double viewZ) {
      VANILLA.clear();

      for (bht chunk : chunks) {
         TerrainRegions189.Slot s = slot(chunk, layer);
         if (s == null) {
            VANILLA.add(chunk);
         } else {
            ArrayList<bht> group = GROUP_LISTS.get(s.region);
            if (group == null) {
               group = LIST_POOL.isEmpty() ? new ArrayList<>() : LIST_POOL.remove(LIST_POOL.size() - 1);
               GROUP_LISTS.put(s.region, group);
               GROUP_ORDER.add(s.region);
            }

            group.add(chunk);
         }
      }

      TerrainRegions189.Pass pass = new TerrainRegions189.Pass(viewX, viewY, viewZ);

      for (TerrainRegions189.RegionBuffer r : GROUP_ORDER) {
         ArrayList<bht> group = GROUP_LISTS.get(r);

         for (bht chunkx : group) {
            pass.draw(chunkx, layer);
         }

         group.clear();
         LIST_POOL.add(group);
      }

      pass.end();
      GROUP_ORDER.clear();
      GROUP_LISTS.clear();
      return VANILLA;
   }

   public static int drawLayer(int layer, bht[] sections, int count, boolean reverse, double viewX, double viewY, double viewZ) {
      TerrainRegions189.Pass pass = new TerrainRegions189.Pass(viewX, viewY, viewZ);
      int drawn = 0;
      int bit = 1 << layer;

      for (int k = 0; k < count; k++) {
         bht chunk = sections[reverse ? count - 1 - k : k];
         SectionData189 data = (SectionData189)chunk;
         if ((data.pulsar$layers() & bit) != 0) {
            TerrainRegions189.Slot s = data.pulsar$slots()[layer];
            if (s != null && s.count != 0) {
               pass.drawSlot(chunk, s);
               drawn++;
            }
         }
      }

      pass.end();
      return drawn;
   }

   public static void frame() {
      lastSections = frameSections;
      lastBinds = frameBinds;
      lastFacesCulledPct = frameVerticesTotal > 0L ? (int)((frameVerticesTotal - frameVerticesDrawn) * 100L / frameVerticesTotal) : 0;
      lastIndirectCalls = frameIndirectCalls;
      frameIndirectCalls = 0;
      lastCompactSections = frameCompactSections;
      frameCompactSections = 0;
      frameSections = 0;
      frameBinds = 0;
      frameVerticesDrawn = 0L;
      frameVerticesTotal = 0L;
   }

   public static final class Pass {
      private final double viewX;
      private final double viewY;
      private final double viewZ;
      private TerrainRegions189.RegionBuffer current;
      private int bound;
      private final boolean useIndirect;
      private boolean compactActive;
      private int sectionX;
      private int sectionOriginY;
      private int sectionZ;
      private final int anchorX;
      private final int anchorY;
      private final int anchorZ;
      private static final double MARGIN = 4.5;
      private static final boolean[] VISIBLE_GROUPS = new boolean[7];

      public Pass(double viewX, double viewY, double viewZ) {
         this.viewX = viewX;
         this.viewY = viewY;
         this.viewZ = viewZ;
         this.useIndirect = TerrainRegions189.indirect();
         this.anchorX = (int)Math.floor(viewX);
         this.anchorY = (int)Math.floor(viewY);
         this.anchorZ = (int)Math.floor(viewZ);
      }

      public boolean draw(bht chunk, int layer) {
         TerrainRegions189.Slot s = TerrainRegions189.slot(chunk, layer);
         if (s == null) {
            this.end();
            return false;
         } else {
            this.drawSlot(chunk, s);
            return true;
         }
      }

      public boolean drawSlot(bht chunk, TerrainRegions189.Slot s) {
         if (GpuOcclusion189.hidden(chunk)) {
            return true;
         } else {
            TerrainRegions189.RegionBuffer r = s.region;
            if (r != this.current) {
               if (this.current != null) {
                  TerrainRegions189.flushCommands();
                  this.bound = 0;
                  bfl.F();
               }

               this.current = r;
               bfl.E();
               if (r.stride == 16) {
                  bfl.b((float)(this.anchorX - this.viewX), (float)(this.anchorY - this.viewY), (float)(this.anchorZ - this.viewZ));
               } else {
                  bfl.b((float)(r.originX - this.viewX), (float)(-this.viewY), (float)(r.originZ - this.viewZ));
               }

               if (r.vbo != this.bound) {
                  bqs.g(bqs.R, r.vbo);
                  this.bound = r.vbo;
                  if (r.stride == 16) {
                     if (!this.compactActive) {
                        TerrainRegions189.compactBegin();
                        this.compactActive = true;
                     }

                     TerrainRegions189.compactPointers();
                  } else {
                     if (this.compactActive) {
                        TerrainRegions189.compactEnd();
                        this.compactActive = false;
                     }

                     TerrainRegions189.pointers();
                  }

                  TerrainRegions189.frameBinds++;
               }
            }

            if (r.stride == 16) {
               this.sectionX = s.sectionX - this.anchorX;
               this.sectionOriginY = s.sectionY - this.anchorY;
               this.sectionZ = s.sectionZ - this.anchorZ;
            } else {
               this.sectionX = s.sectionX - r.originX;
               this.sectionOriginY = s.sectionY;
               this.sectionZ = s.sectionZ - r.originZ;
            }

            if (s.count > 0) {
               TerrainRegions189.frameSections++;
               if (r.stride == 16) {
                  TerrainRegions189.frameCompactSections++;
               }

               TerrainRegions189.frameVerticesTotal = TerrainRegions189.frameVerticesTotal + s.count;
               if (s.groups != null && FrameFlags.faceCulling) {
                  this.drawGroups(s);
               } else {
                  this.emit(s.first, s.count);
               }
            }

            return true;
         }
      }

      private void drawGroups(TerrainRegions189.Slot s) {
         boolean minusX = this.viewX <= s.sectionX + 16 + 4.5;
         boolean plusX = this.viewX >= s.sectionX - 4.5;
         boolean minusY = this.viewY <= s.sectionY + 16 + 4.5;
         boolean plusY = this.viewY + 1.62 >= s.sectionY - 4.5;
         boolean minusZ = this.viewZ <= s.sectionZ + 16 + 4.5;
         boolean plusZ = this.viewZ >= s.sectionZ - 4.5;
         boolean[] visible = VISIBLE_GROUPS;
         visible[0] = minusX;
         visible[1] = plusX;
         visible[2] = minusY;
         visible[3] = plusY;
         visible[4] = minusZ;
         visible[5] = plusZ;
         visible[6] = true;
         int runStart = -1;
         int runCount = 0;
         int cursor = s.first;

         for (int g = 0; g < 7; g++) {
            int n = s.groups[g];
            if (n > 0 && visible[g]) {
               if (runStart < 0) {
                  runStart = cursor;
               }

               runCount += n;
            } else if (runStart >= 0 && n > 0) {
               this.emit(runStart, runCount);
               runStart = -1;
               runCount = 0;
            }

            cursor += n;
         }

         if (runStart >= 0) {
            this.emit(runStart, runCount);
         }
      }

      private void emit(int first, int count) {
         TerrainRegions189.frameVerticesDrawn += count;
         if (this.useIndirect) {
            if (this.compactActive) {
               TerrainRegions189.sectionOffset(this.sectionX, this.sectionOriginY, this.sectionZ);
            }

            TerrainRegions189.command(first, count);
         } else {
            GL11.glDrawArrays(7, first, count);
         }
      }

      public void end() {
         if (this.current != null) {
            TerrainRegions189.flushCommands();
            bfl.F();
            this.current = null;
         }

         if (this.compactActive) {
            TerrainRegions189.compactEnd();
            this.compactActive = false;
         }

         if (this.bound != 0) {
            bqs.g(bqs.R, 0);
            this.bound = 0;
         }
      }
   }

   public static final class RegionBuffer {
      public final int originX;
      public final int originZ;
      final int stride;
      int vbo;
      int capacity;
      int used;
      int free;
      final Map<bht, TerrainRegions189.Slot> slots = new IdentityHashMap<>();

      RegionBuffer(int originX, int originZ, int stride) {
         this.originX = originX;
         this.originZ = originZ;
         this.stride = stride;
      }
   }

   public static final class Slot {
      int offset;
      int bytes;
      public int first;
      public int count;
      TerrainRegions189.RegionBuffer region;
      int[] groups;
      int sectionX;
      int sectionY;
      int sectionZ;
   }
}
