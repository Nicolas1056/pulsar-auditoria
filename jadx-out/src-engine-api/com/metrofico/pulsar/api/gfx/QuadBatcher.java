package com.metrofico.pulsar.api.gfx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class QuadBatcher {
   private final List<QuadBatcher.Batch> batches = new ArrayList<>();
   private int quads;

   public QuadBatcher.Batch target(int tex, float x0, float y0, float x1, float y1) {
      for (int i = this.batches.size() - 1; i >= 0; i--) {
         QuadBatcher.Batch b = this.batches.get(i);
         if (b.tex == tex) {
            b.cover(x0, y0, x1, y1);
            return b;
         }

         if (b.overlaps(x0, y0, x1, y1)) {
            break;
         }
      }

      QuadBatcher.Batch bx = new QuadBatcher.Batch(tex);
      bx.cover(x0, y0, x1, y1);
      this.batches.add(bx);
      return bx;
   }

   public void quad(QuadBatcher.Batch b, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int argb) {
      b.vertex(x0, y1, u0, v1, argb);
      b.vertex(x1, y1, u1, v1, argb);
      b.vertex(x1, y0, u1, v0, argb);
      b.vertex(x0, y0, u0, v0, argb);
      this.quads++;
   }

   public void quad(int tex, float x0, float y0, float x1, float y1, float u0, float v0, float u1, float v1, int argb) {
      this.quad(this.target(tex, x0, y0, x1, y1), x0, y0, x1, y1, u0, v0, u1, v1, argb);
   }

   public List<QuadBatcher.Batch> batches() {
      return this.batches;
   }

   public int quads() {
      return this.quads;
   }

   public boolean isEmpty() {
      return this.batches.isEmpty();
   }

   public void clear() {
      this.batches.clear();
      this.quads = 0;
   }

   public static final class Batch {
      public final int tex;
      private float[] v = new float[320];
      private int n;
      private float minX = Float.MAX_VALUE;
      private float minY = Float.MAX_VALUE;
      private float maxX = -Float.MAX_VALUE;
      private float maxY = -Float.MAX_VALUE;

      Batch(int tex) {
         this.tex = tex;
      }

      boolean overlaps(float x0, float y0, float x1, float y1) {
         return x0 < this.maxX && x1 > this.minX && y0 < this.maxY && y1 > this.minY;
      }

      void cover(float x0, float y0, float x1, float y1) {
         if (x0 < this.minX) {
            this.minX = x0;
         }

         if (y0 < this.minY) {
            this.minY = y0;
         }

         if (x1 > this.maxX) {
            this.maxX = x1;
         }

         if (y1 > this.maxY) {
            this.maxY = y1;
         }
      }

      void vertex(float x, float y, float u, float vv, int argb) {
         if (this.n + 5 > this.v.length) {
            this.v = Arrays.copyOf(this.v, this.v.length * 2);
         }

         this.v[this.n++] = x;
         this.v[this.n++] = y;
         this.v[this.n++] = u;
         this.v[this.n++] = vv;
         this.v[this.n++] = Float.intBitsToFloat(argb);
      }

      public float[] data() {
         return this.v;
      }

      public int floats() {
         return this.n;
      }

      public int quads() {
         return this.n / 20;
      }
   }
}
