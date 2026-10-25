package com.metrofico.pulsar.engine.v189.gfx;

import com.metrofico.pulsar.api.gfx.Canvas;
import com.metrofico.pulsar.api.gfx.Font;
import com.metrofico.pulsar.api.gfx.QuadBatcher;
import com.metrofico.pulsar.api.gfx.QuadBatcher.Batch;
import com.metrofico.pulsar.api.gfx.TypeAtlas.Layout;
import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.GlState189;
import com.metrofico.pulsar.engine.v189.perf.HudImage189;
import java.util.ArrayDeque;
import java.util.Deque;
import org.lwjgl.opengl.GL11;

public final class Canvas189 implements Canvas {
   private final int width;
   private final int height;
   private final int scale;
   private final int windowH;
   private final Deque<int[]> clips = new ArrayDeque<>();
   private final QuadBatcher batcher = new QuadBatcher();
   private float tx;
   private float ty;
   private float sx = 1.0F;
   private float sy = 1.0F;
   private final Deque<float[]> poses = new ArrayDeque<>();
   public static volatile int lastQuads;
   public static volatile int lastDraws;
   private static int frameQuads;
   private static int frameDraws;

   public Canvas189() {
      ave mc = ave.A();
      avr w = new avr(mc);
      this.width = (int)w.c();
      this.height = (int)w.d();
      this.scale = Math.max(1, w.e());
      this.windowH = mc.e;
   }

   public int width() {
      return this.width;
   }

   public int height() {
      return this.height;
   }

   public static void frame() {
      lastQuads = frameQuads;
      lastDraws = frameDraws;
      frameQuads = 0;
      frameDraws = 0;
   }

   private static boolean batching() {
      return FrameFlags.uiBatch;
   }

   private void quad(Batch b, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int argb) {
      this.batcher.quad(b, x0, y0, x1, y1, u0, v0, u1, v1, argb);
      frameQuads++;
   }

   public void flush() {
      if (!this.batcher.isEmpty()) {
         bfl.l();
         bfl.a(770, 771, 1, 0);
         bfl.c();
         bfx tess = bfx.a();
         bfd bb = tess.c();

         for (Batch b : this.batcher.batches()) {
            float[] v = b.data();
            int n = b.floats();
            if (b.tex == 0) {
               bfl.x();
               bb.a(7, bms.f);

               for (int i = 0; i < n; i += 5) {
                  int c = Float.floatToRawIntBits(v[i + 4]);
                  bb.b(v[i], v[i + 1], 0.0).b(c >> 16 & 0xFF, c >> 8 & 0xFF, c & 0xFF, c >>> 24).d();
               }
            } else {
               bfl.w();
               bfl.i(b.tex);
               bb.a(7, bms.i);

               for (int i = 0; i < n; i += 5) {
                  int c = Float.floatToRawIntBits(v[i + 4]);
                  bb.b(v[i], v[i + 1], 0.0).a(v[i + 2], v[i + 3]).b(c >> 16 & 0xFF, c >> 8 & 0xFF, c & 0xFF, c >>> 24).d();
               }
            }

            tess.b();
            frameDraws++;
         }

         this.batcher.clear();
         bfl.w();
         bfl.d();
         GlState189.reaffirmColor();
      }
   }

   public void direct(Runnable r) {
      this.flush();
      boolean posed = this.tx != 0.0F || this.ty != 0.0F || this.sx != 1.0F || this.sy != 1.0F;
      if (posed) {
         bfl.E();
         bfl.b(this.tx, this.ty, 0.0F);
         bfl.a(this.sx, this.sy, 1.0F);
      }

      r.run();
      if (posed) {
         bfl.F();
      }
   }

   public void fill(int x0, int y0, int x1, int y1, int argb) {
      if (x0 > x1) {
         int t = x0;
         x0 = x1;
         x1 = t;
      }

      if (y0 > y1) {
         int t = y0;
         y0 = y1;
         y1 = t;
      }

      if (argb >>> 24 != 0) {
         float fx0 = x0 * this.sx + this.tx;
         float fy0 = y0 * this.sy + this.ty;
         float fx1 = x1 * this.sx + this.tx;
         float fy1 = y1 * this.sy + this.ty;
         this.quad(this.batcher.target(0, fx0, fy0, fx1, fy1), fx0, fy0, fx1, fy1, 0.0F, 0.0F, 0.0F, 0.0F, argb);
         if (!batching()) {
            this.flush();
         }
      }
   }

   private void quads(int glTexture, float[] q, int count, int argb) {
      if (count > 0 && argb >>> 24 != 0) {
         float inv = 1.0F / this.scale;
         float bx0 = Float.MAX_VALUE;
         float by0 = Float.MAX_VALUE;
         float bx1 = -Float.MAX_VALUE;
         float by1 = -Float.MAX_VALUE;

         for (int i = 0; i < count; i++) {
            int o = i * 8;
            float x0 = q[o] * inv * this.sx + this.tx;
            float y0 = q[o + 1] * inv * this.sy + this.ty;
            float x1 = q[o + 2] * inv * this.sx + this.tx;
            float y1 = q[o + 3] * inv * this.sy + this.ty;
            if (x0 < bx0) {
               bx0 = x0;
            }

            if (y0 < by0) {
               by0 = y0;
            }

            if (x1 > bx1) {
               bx1 = x1;
            }

            if (y1 > by1) {
               by1 = y1;
            }
         }

         Batch b = this.batcher.target(glTexture, bx0, by0, bx1, by1);

         for (int i = 0; i < count; i++) {
            int ox = i * 8;
            this.quad(
               b,
               q[ox] * inv * this.sx + this.tx,
               q[ox + 1] * inv * this.sy + this.ty,
               q[ox + 2] * inv * this.sx + this.tx,
               q[ox + 3] * inv * this.sy + this.ty,
               q[ox + 4],
               q[ox + 5],
               q[ox + 6],
               q[ox + 7],
               argb
            );
         }

         if (!batching()) {
            this.flush();
         }
      }
   }

   public int text(Font font, String text, int x, int y, float size, int argb) {
      PulsarAtlases.Type t = PulsarAtlases.type(font);
      if (t == null) {
         return 0;
      } else {
         Layout l = t.atlas().layout(text, x, y, size, this.scale);
         if (l.count() > 0) {
            int level = t.atlas().mipLevel(size, this.scale, t.levels());
            this.quads(t.texture(level), l.quads(), l.count(), argb);
         }

         return l.widthGui();
      }
   }

   public int textWidth(Font font, String text, float size) {
      PulsarAtlases.Type t = PulsarAtlases.type(font);
      return t == null ? 0 : t.atlas().width(text, size);
   }

   public void icon(String name, int x, int y, int size, int argb) {
      PulsarAtlases.Icons ic = PulsarAtlases.icons();
      if (ic != null) {
         int col = ic.atlas().column(name);
         if (col >= 0) {
            float cell = ic.atlas().cell();
            float u0 = col * cell / ic.texW();
            float u1 = (col + 1) * cell / ic.texW();
            float v1 = cell / ic.texH();
            float px = x * this.scale;
            float py = y * this.scale;
            float ps = size * this.scale;
            this.quads(ic.texture(), new float[]{px, py, px + ps, py + ps, u0, 0.0F, u1, v1}, 1, argb);
         }
      }
   }

   public void logo(int x, int y, int size) {
      int tex = PulsarAtlases.logo();
      if (tex != 0) {
         float px = x * this.scale;
         float py = y * this.scale;
         float ps = size * this.scale;
         this.quads(tex, new float[]{px, py, px + ps, py + ps, 0.0F, 0.0F, 1.0F, 1.0F}, 1, -1);
      }
   }

   public void clip(int x0, int y0, int x1, int y1) {
      this.flush();
      this.clips.push(new int[]{x0, y0, x1, y1});
      this.applyClip();
   }

   public void unclip() {
      this.flush();
      if (!this.clips.isEmpty()) {
         this.clips.pop();
      }

      this.applyClip();
   }

   private void applyClip() {
      if (this.clips.isEmpty()) {
         GL11.glDisable(3089);
      } else {
         int[] c = this.clips.peek();
         GL11.glEnable(3089);
         GL11.glScissor(c[0] * this.scale, this.windowH - c[3] * this.scale, (c[2] - c[0]) * this.scale, (c[3] - c[1]) * this.scale);
      }
   }

   public void push() {
      this.poses.push(new float[]{this.tx, this.ty, this.sx, this.sy});
   }

   public void translate(float dx, float dy) {
      this.tx = this.tx + dx * this.sx;
      this.ty = this.ty + dy * this.sy;
   }

   public void pop() {
      float[] p = this.poses.poll();
      if (p != null) {
         this.tx = p[0];
         this.ty = p[1];
         this.sx = p[2];
         this.sy = p[3];
      }
   }

   public void textured(jy id, int x, int y, int w, int h, float u0, float v0, float u1, float v1, int argb) {
      bmj tm = ave.A().P();
      bmk t = tm.b(id);
      if (t == null) {
         tm.a(id);
         t = tm.b(id);
         if (t == null) {
            return;
         }
      }

      float px = x * this.scale;
      float py = y * this.scale;
      this.quads(t.b(), new float[]{px, py, px + w * this.scale, py + h * this.scale, u0, v0, u1, v1}, 1, argb);
   }

   public void texturedBound(int x, int y, int w, int h, float u0, float v0, float u1, float v1) {
      this.direct(() -> texturedNow(x, y, w, h, u0, v0, u1, v1));
   }

   private static void texturedNow(int x, int y, int w, int h, float u0, float v0, float u1, float v1) {
      bfl.l();
      bfl.w();
      bfl.a(770, 771, 1, 0);
      bfl.d();
      bfx tess = bfx.a();
      bfd b = tess.c();
      b.a(7, bms.i);
      b.b(x, y + h, 0.0).a(u0, v1).b(255, 255, 255, 255).d();
      b.b(x + w, y + h, 0.0).a(u1, v1).b(255, 255, 255, 255).d();
      b.b(x + w, y, 0.0).a(u1, v0).b(255, 255, 255, 255).d();
      b.b(x, y, 0.0).a(u0, v0).b(255, 255, 255, 255).d();
      tess.b();
   }

   public int vtext(String text, int x, int y, int argb, boolean shadow) {
      avn tr = ave.A().k;
      int[] w = new int[1];
      this.direct(() -> {
         bfl.w();
         w[0] = tr.a(text, x, y, argb, shadow) - x;
      });
      return w[0];
   }

   public void cached(Object slot, Object key, int x0, int y0, int x1, int y1, Runnable draw) {
      this.flush();
      int px0 = (int)Math.floor(x0 * this.sx + this.tx);
      int py0 = (int)Math.floor(y0 * this.sy + this.ty);
      int px1 = (int)Math.ceil(x1 * this.sx + this.tx);
      int py1 = (int)Math.ceil(y1 * this.sy + this.ty);
      if (!HudImage189.draw(slot, key, px0, py0, px1, py1, this.scale, draw, this::flush)) {
         draw.run();
      }
   }

   public int guiScale() {
      return this.scale;
   }

   public int vwidth(String text) {
      return ave.A().k.a(text);
   }

   public int vlineHeight() {
      return ave.A().k.a;
   }

   public boolean image(String ref, int x, int y, int w, int h) {
      return Textures189.draw(this, ref, x, y, w, h);
   }

   public boolean imageInline(String key, String base64, int x, int y, int w, int h) {
      Textures189.Texture t = Textures189.inline(key, base64);
      if (t == null) {
         return false;
      } else {
         this.direct(() -> {
            bfl.i(t.id());
            texturedNow(x, y, w, h, 0.0F, 0.0F, 1.0F, 1.0F);
         });
         return true;
      }
   }

   public void scale(float sx, float sy) {
      this.sx *= sx;
      this.sy *= sy;
   }

   public void pointer(boolean pointing) {
   }
}
