package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hud.modules.Zoom;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.api.perf.FramePacer;
import com.metrofico.pulsar.engine.v189.mixin.TextRendererAccessor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public final class SignBoards189 {
   private static final jy TEXTURE = new jy("textures/entity/sign.png");
   private static final float SCALE = 0.6666667F;
   private static final int EVICT_FRAMES = 1200;
   private static final Map<Long, SignBoards189.Section> SECTIONS = new HashMap<>();
   private static final bbx MODEL = new bbx();
   private static int frame;
   private static boolean textOnly;
   private static int frameBoards;
   private static int frameLists;
   private static int frameCompiles;
   private static int frameTexts;
   private static int statBoards;
   private static int statLists;
   private static int statCompiles;
   private static int statFrames;
   private static int statTexts;
   private static int statBySet;
   private static int statByLight;
   private static final String CODES = "0123456789abcdefklmnor";
   private static int lastSx0;
   private static int lastSx1;
   private static int lastSy0;
   private static int lastSy1;
   private static int lastSz0;
   private static int lastSz1;
   private static long lastMarkFrame = -1L;

   private SignBoards189() {
   }

   public static boolean enabled() {
      return FrameFlags.signBatch;
   }

   public static boolean textOnly() {
      return textOnly;
   }

   public static void textOnly(boolean on) {
      textOnly = on;
   }

   public static boolean board(aln sign) {
      SignBoards189.Section s = section(sign);
      if (s.frame != frame) {
         begin(s);
      }

      s.seen++;
      s.seenSum = s.seenSum + signature(sign);
      if (s.outOfRange) {
         return true;
      } else if (s.drawn) {
         frameBoards++;
         return true;
      } else {
         if (s.pendingFrame == frame) {
            s.pending.add(sign);
         }

         return false;
      }
   }

   private static long signature(aln sign) {
      long h = System.identityHashCode(sign);

      for (eu t : sign.a) {
         h = h * 31L + System.identityHashCode(t);
      }

      return h;
   }

   private static void begin(SignBoards189.Section s) {
      boolean consecutive = s.frame == frame - 1;
      if (consecutive && s.drawn && (s.seen != s.compiledCount || s.seenSum != s.compiledSum)) {
         s.dirty = true;
         statBySet++;
      }

      if (s.verify && !s.dirty && s.list >= 0) {
         s.verify = false;
         lastMarkFrame = -1L;
         if (lightChanged(s)) {
            s.dirty = true;
            statByLight++;
         }
      }

      if (s.dirty && consecutive && !s.drawn && s.pendingFrame == frame - 1 && s.seen > 0 && s.pending.size() == s.seen) {
         compile(s, s.seen, s.seenSum);
      }

      s.frame = frame;
      s.seen = 0;
      s.seenSum = 0L;
      s.outOfRange = nearestSq(s) > maxDistanceSq();
      s.drawn = !s.outOfRange && s.list >= 0 && !s.dirty;
      if (!s.outOfRange) {
         if (s.drawn) {
            draw(s);
         } else {
            s.pending.clear();
            s.pendingFrame = frame;
         }
      }
   }

   private static double maxDistanceSq() {
      double vanilla = 4096.0;
      return FrameFlags.cullBlockEntities && !Zoom.active() ? Math.min(vanilla, FrameFlags.beDistanceSq * FramePacer.blockEntityScaleSq()) : vanilla;
   }

   private static double nearestSq(SignBoards189.Section s) {
      double dx = axis(bhc.b, s.ox);
      double dy = axis(bhc.c, s.oy);
      double dz = axis(bhc.d, s.oz);
      return dx * dx + dy * dy + dz * dz;
   }

   private static double axis(double eye, int origin) {
      return eye < origin ? origin - eye : (eye > origin + 16 ? eye - origin - 16.0 : 0.0);
   }

   private static boolean lightChanged(SignBoards189.Section s) {
      adm world = ave.A().f;
      if (world == null) {
         return false;
      } else {
         for (int i = 0; i < s.positions.length; i++) {
            if (world.b(s.positions[i], 0) != s.lights[i]) {
               return true;
            }
         }

         return false;
      }
   }

   private static SignBoards189.Section section(aln sign) {
      SignBoards189.SignSection ext = (SignBoards189.SignSection)sign;
      SignBoards189.Section s = ext.pulsar$section();
      if (s != null && !s.removed) {
         return s;
      } else {
         cj p = sign.v();
         int sx = p.n() >> 4;
         int sy = p.o() >> 4;
         int sz = p.p() >> 4;
         long key = key(sx, sy, sz);
         s = SECTIONS.get(key);
         if (s == null) {
            s = new SignBoards189.Section(key, sx << 4, sy << 4, sz << 4);
            SECTIONS.put(key, s);
         }

         ext.pulsar$section(s);
         return s;
      }
   }

   private static long key(int sx, int sy, int sz) {
      return (sx & 4194303L) << 42 | (sy & 1048575L) << 22 | sz & 4194303L;
   }

   private static void compile(SignBoards189.Section s, int count, long sum) {
      if (EntityBatch189.pending()) {
         EntityBatch189.flush();
      }

      if (s.list < 0) {
         s.list = GL11.glGenLists(1);
      }

      adm world = ave.A().f;
      float[] plate = EntityBatch189.vertices(MODEL.a);
      float[] stick = EntityBatch189.vertices(MODEL.b);
      int n = s.pending.size();
      if (s.positions.length != n) {
         s.positions = new cj[n];
         s.lights = new int[n];
      }

      GL11.glNewList(s.list, 4864);
      GL11.glBegin(7);

      for (int i = 0; i < n; i++) {
         aln sign = s.pending.get(i);
         cj p = sign.v();
         int light = world == null ? 0 : world.b(p, 0);
         s.positions[i] = p;
         s.lights[i] = light;
         bqs.a(bqs.r, light % 65536, light / 65536);
         boolean standing = sign.w() == afi.an;
         int data = sign.u();
         float angle = standing ? -(data * 360 / 16.0F) : -(data == 2 ? 180.0F : (data == 4 ? 90.0F : (data == 5 ? -90.0F : 0.0F)));
         double rad = Math.toRadians(angle);
         float cos = (float)Math.cos(rad);
         float sin = (float)Math.sin(rad);
         float tx = p.n() - s.ox + 0.5F;
         float ty = p.o() - s.oy + 0.5F;
         float tz = p.p() - s.oz + 0.5F;
         float wy = standing ? 0.0F : -0.3125F;
         float wz = standing ? 0.0F : -0.4375F;
         quads(plate, MODEL.a, cos, sin, tx, ty, tz, wy, wz);
         if (standing) {
            quads(stick, MODEL.b, cos, sin, tx, ty, tz, wy, wz);
         }
      }

      GL11.glEnd();
      GL11.glEndList();
      compileText(s, world);
      s.compiledCount = count;
      s.compiledSum = sum;
      s.dirty = false;
      s.verify = false;
      lastMarkFrame = -1L;
      s.pending.clear();
      frameCompiles++;
   }

   private static void quads(float[] v, bct part, float cos, float sin, float tx, float ty, float tz, float wy, float wz) {
      float px = part.c * 0.0625F;
      float py = part.d * 0.0625F;
      float pz = part.e * 0.0625F;

      for (int i = 0; i + 7 < v.length; i += 8) {
         float nx = v[i + 5];
         float ny = -v[i + 6];
         float nz = -v[i + 7];
         GL11.glNormal3f(nx * cos + nz * sin, ny, -nx * sin + nz * cos);
         GL11.glTexCoord2f(v[i + 3], v[i + 4]);
         float x = (v[i] * 0.0625F + px) * 0.6666667F;
         float y = -(v[i + 1] * 0.0625F + py) * 0.6666667F + wy;
         float z = -(v[i + 2] * 0.0625F + pz) * 0.6666667F + wz;
         GL11.glVertex3f(tx + x * cos + z * sin, ty + y, tz - x * sin + z * cos);
      }
   }

   private static void draw(SignBoards189.Section s) {
      ave mc = ave.A();
      mc.P().a(TEXTURE);
      bfl.c(1.0F, 1.0F, 1.0F, 1.0F);
      bfl.B();
      bfl.E();
      bfl.b((float)(s.ox - bhc.b), (float)(s.oy - bhc.c), (float)(s.oz - bhc.d));
      bfl.o(s.list);
      if (s.textList >= 0 && nearestSq(s) <= SignText189.legibleDistanceSq()) {
         mc.P().a(((TextRendererAccessor)mc.k).pulsar$fontTexture());
         bfl.a(false);
         bfl.o(s.textList);
         bfl.a(true);
         frameTexts++;
      }

      bfl.F();
      frameLists++;
   }

   private static void compileText(SignBoards189.Section s, adm world) {
      ave mc = ave.A();
      avn tr = mc.k;
      TextRendererAccessor acc = (TextRendererAccessor)tr;
      int[] widths = acc.pulsar$characterWidths();
      int[] colors = acc.pulsar$colorCodes();
      boolean any = false;
      String[][] lines = new String[s.pending.size()][];

      for (int i = 0; i < s.pending.size(); i++) {
         aln sign = s.pending.get(i);
         String[] l = tr.a() ? null : bakeableLines(sign, tr);
         lines[i] = l;
         ((SignBoards189.SignSection)sign).pulsar$textBaked(l != null);
         any |= l != null && (!l[0].isEmpty() || !l[1].isEmpty() || !l[2].isEmpty() || !l[3].isEmpty());
      }

      if (!any) {
         if (s.textList >= 0) {
            GL11.glDeleteLists(s.textList, 1);
            s.textList = -1;
         }
      } else {
         if (s.textList < 0) {
            s.textList = GL11.glGenLists(1);
         }

         float f3 = 0.010416667F;
         GL11.glNewList(s.textList, 4864);
         GL11.glBegin(7);

         for (int i = 0; i < s.pending.size(); i++) {
            String[] l = lines[i];
            if (l != null) {
               aln sign = s.pending.get(i);
               cj p = sign.v();
               boolean standing = sign.w() == afi.an;
               int data = sign.u();
               float angle = standing ? -(data * 360 / 16.0F) : -(data == 2 ? 180.0F : (data == 4 ? 90.0F : (data == 5 ? -90.0F : 0.0F)));
               double rad = Math.toRadians(angle);
               float cos = (float)Math.cos(rad);
               float sin = (float)Math.sin(rad);
               SignBoards189.Glyphs g = new SignBoards189.Glyphs(
                  widths,
                  cos,
                  sin,
                  p.n() - s.ox + 0.5F,
                  p.o() - s.oy + 0.5F,
                  p.p() - s.oz + 0.5F,
                  0.33333334F + (standing ? 0.0F : -0.3125F),
                  0.046666667F + (standing ? 0.0F : -0.4375F),
                  f3
               );
               int light = world == null ? 0 : world.b(p, 0);
               bqs.a(bqs.r, light % 65536, light / 65536);
               GL11.glNormal3f(-sin, 0.0F, -cos);

               for (int j = 0; j < 4; j++) {
                  if (!l[j].isEmpty()) {
                     g.line(l[j], -tr.a(l[j]) / 2, j * 10 - sign.a.length * 5, colors);
                  }
               }
            }
         }

         GL11.glEnd();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glEndList();
      }
   }

   private static String[] bakeableLines(aln sign, avn tr) {
      if (sign.a.length != 4) {
         return null;
      } else {
         String[] out = new String[4];

         for (int j = 0; j < 4; j++) {
            eu t = sign.a[j];
            String line = "";
            if (t != null) {
               List<eu> wrapped = avu.a(t, 90, tr, false, true);
               line = wrapped != null && !wrapped.isEmpty() ? wrapped.get(0).d() : "";
            }

            for (int c = 0; c < line.length(); c++) {
               char ch = line.charAt(c);
               if (ch == 167 && c + 1 < line.length()) {
                  int code = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(line.charAt(c + 1)));
                  if (code == 16 || code == 18 || code == 19) {
                     return null;
                  }

                  c++;
               } else if (ch < ' ' || ch > '~') {
                  return null;
               }
            }

            out[j] = line;
         }

         return out;
      }
   }

   public static void invalidate(int x0, int y0, int z0, int x1, int y1, int z1) {
      if (!SECTIONS.isEmpty()) {
         int a = x0 >> 4;
         int b = x1 >> 4;
         int c = y0 >> 4;
         int d = y1 >> 4;
         int e = z0 >> 4;
         int f = z1 >> 4;
         if (frame != lastMarkFrame || a != lastSx0 || b != lastSx1 || c != lastSy0 || d != lastSy1 || e != lastSz0 || f != lastSz1) {
            lastSx0 = a;
            lastSx1 = b;
            lastSy0 = c;
            lastSy1 = d;
            lastSz0 = e;
            lastSz1 = f;
            lastMarkFrame = frame;

            for (int sx = x0 >> 4; sx <= x1 >> 4; sx++) {
               for (int sy = y0 >> 4; sy <= y1 >> 4; sy++) {
                  for (int sz = z0 >> 4; sz <= z1 >> 4; sz++) {
                     SignBoards189.Section s = SECTIONS.get(key(sx, sy, sz));
                     if (s != null) {
                        s.verify = true;
                     }
                  }
               }
            }
         }
      }
   }

   public static void frame() {
      statBoards = statBoards + frameBoards;
      statLists = statLists + frameLists;
      statCompiles = statCompiles + frameCompiles;
      statTexts = statTexts + frameTexts;
      statFrames++;
      frameTexts = 0;
      frameCompiles = 0;
      frameLists = 0;
      frameBoards = 0;
      frame++;
      if ((frame & 1023) == 0 && !SECTIONS.isEmpty()) {
         Iterator<SignBoards189.Section> it = SECTIONS.values().iterator();

         while (it.hasNext()) {
            SignBoards189.Section s = it.next();
            if (frame - s.frame > 1200) {
               release(s);
               it.remove();
            }
         }
      }
   }

   private static void release(SignBoards189.Section s) {
      if (s.list >= 0) {
         GL11.glDeleteLists(s.list, 1);
         s.list = -1;
      }

      if (s.textList >= 0) {
         GL11.glDeleteLists(s.textList, 1);
         s.textList = -1;
      }

      s.removed = true;
      s.pending.clear();
   }

   public static void clear() {
      for (SignBoards189.Section s : SECTIONS.values()) {
         release(s);
      }

      SECTIONS.clear();
   }

   public static String line() {
      int frames = Math.max(1, statFrames);
      String out = statBoards == 0
         ? ""
         : " · tablas "
            + statBoards / frames
            + " en "
            + statLists / frames
            + " dibujos"
            + (statTexts > 0 ? " + " + statTexts / frames + " de texto" : "")
            + (statCompiles > 0 ? " (" + statCompiles + " grabadas: " + statBySet + " por carteles, " + statByLight + " por luz)" : "");
      statTexts = 0;
      statByLight = 0;
      statBySet = 0;
      statFrames = 0;
      statCompiles = 0;
      statLists = 0;
      statBoards = 0;
      return out;
   }

   private static final class Glyphs {
      final int[] widths;
      final float cos;
      final float sin;
      final float tx;
      final float ty;
      final float tz;
      final float oy;
      final float oz;
      final float f3;

      Glyphs(int[] widths, float cos, float sin, float tx, float ty, float tz, float oy, float oz, float f3) {
         this.widths = widths;
         this.cos = cos;
         this.sin = sin;
         this.tx = tx;
         this.ty = ty;
         this.tz = tz;
         this.oy = oy;
         this.oz = oz;
         this.f3 = f3;
      }

      void line(String text, int x0, int y0, int[] colors) {
         float x = x0;
         float y = y0;
         boolean bold = false;
         boolean italic = false;
         GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);

         for (int c = 0; c < text.length(); c++) {
            char ch = text.charAt(c);
            if (ch == 167 && c + 1 < text.length()) {
               int code = "0123456789abcdefklmnor".indexOf(Character.toLowerCase(text.charAt(++c)));
               if (code < 16) {
                  italic = false;
                  bold = false;
                  int color = colors[code >= 0 && code <= 15 ? code : 15];
                  GL11.glColor4f((color >> 16) / 255.0F, (color >> 8 & 0xFF) / 255.0F, (color & 0xFF) / 255.0F, 1.0F);
               } else if (code == 17) {
                  bold = true;
               } else if (code == 20) {
                  italic = true;
               } else if (code == 21) {
                  italic = false;
                  bold = false;
                  GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
               }
            } else {
               float w;
               if (ch == ' ') {
                  w = 4.0F;
               } else {
                  w = this.glyph(ch, x, y, italic);
                  if (bold) {
                     this.glyph(ch, x + 1.0F, y, italic);
                     w++;
                  }
               }

               x += (int)w;
            }
         }
      }

      private float glyph(char ch, float x, float y, boolean italic) {
         int u = ch % 16 * 8;
         int v = ch / 16 * 8;
         int k = italic ? 1 : 0;
         int l = this.widths[ch];
         float f = l - 0.01F;
         this.vertex(u / 128.0F, v / 128.0F, x + k, y);
         this.vertex(u / 128.0F, (v + 7.99F) / 128.0F, x - k, y + 7.99F);
         this.vertex((u + f - 1.0F) / 128.0F, (v + 7.99F) / 128.0F, x + f - 1.0F - k, y + 7.99F);
         this.vertex((u + f - 1.0F) / 128.0F, v / 128.0F, x + f - 1.0F + k, y);
         return l;
      }

      private void vertex(float u, float v, float gx, float gy) {
         float lx = this.f3 * gx;
         float ly = this.oy - this.f3 * gy;
         float lz = this.oz;
         GL11.glTexCoord2f(u, v);
         GL11.glVertex3f(this.tx + lx * this.cos + lz * this.sin, this.ty + ly, this.tz - lx * this.sin + lz * this.cos);
      }
   }

   public static final class Section {
      final long key;
      final int ox;
      final int oy;
      final int oz;
      int list = -1;
      int textList = -1;
      int compiledCount;
      long compiledSum;
      boolean dirty = true;
      boolean verify;
      cj[] positions = new cj[0];
      int[] lights = new int[0];
      boolean removed;
      int frame = Integer.MIN_VALUE;
      int seen;
      long seenSum;
      boolean drawn;
      boolean outOfRange;
      int pendingFrame = Integer.MIN_VALUE;
      final ArrayList<aln> pending = new ArrayList<>();

      Section(long key, int ox, int oy, int oz) {
         this.key = key;
         this.ox = ox;
         this.oy = oy;
         this.oz = oz;
      }
   }

   public interface SignSection {
      SignBoards189.Section pulsar$section();

      void pulsar$section(SignBoards189.Section var1);

      boolean pulsar$textBaked();

      void pulsar$textBaked(boolean var1);
   }
}
