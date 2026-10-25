package com.metrofico.pulsar.engine.v26x.perf;

import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.mixin.BufferBuilderAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.GlyphRendererAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.RenderTypeFeatureRendererAccessor;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.blaze3d.vertex.VertexFormatElement;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.gui.Font.DisplayMode;
import net.minecraft.client.gui.Font.GlyphVisitor;
import net.minecraft.client.gui.Font.PreparedText;
import net.minecraft.client.gui.font.TextRenderable;
import net.minecraft.client.renderer.rendertype.RenderType;
import net.minecraft.util.ARGB;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.system.MemoryUtil;

public final class TextReplay {
   private static final int SENTINEL_LIGHT = 2061400798;
   private static final boolean LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;
   private static final int POS = 0;
   private static final int COLOR = 1;
   private static final int UV0 = 2;
   private static final int UV2 = 4;
   private static final Matrix4f IDENTITY = new Matrix4f();
   private static volatile boolean broken;

   private TextReplay() {
   }

   static boolean enabled() {
      return !broken;
   }

   static TextReplay.Baked recordAndRender(PreparedText text, Object glyphRenderer) {
      GlyphRendererAccessor gr = (GlyphRendererAccessor)glyphRenderer;
      final Matrix4f pose = gr.pulsar$pose();
      final int lightCoords = gr.pulsar$lightCoords();
      final DisplayMode mode = gr.pulsar$displayMode();
      final RenderTypeFeatureRendererAccessor outer = (RenderTypeFeatureRendererAccessor)gr.pulsar$outer();
      final List<TextReplay.Segment> segments = new ArrayList<>(4);
      final TextReplay.Recorder recorder = new TextReplay.Recorder();
      text.visit(new GlyphVisitor() {
         public void acceptRenderable(TextRenderable renderable) {
            RenderType type = renderable.renderType(mode);
            renderable.render(pose, outer.pulsar$vertexBuilder(type), lightCoords, false);
            recorder.start();
            renderable.render(TextReplay.IDENTITY, recorder, 2061400798, false);
            segments.add(new TextReplay.Segment(type, recorder.finish()));
         }
      });
      return new TextReplay.Baked(segments);
   }

   static void replay(TextReplay.Baked baked, Object glyphRenderer) {
      GlyphRendererAccessor gr = (GlyphRendererAccessor)glyphRenderer;
      Matrix4f pose = gr.pulsar$pose();
      int frameLight = gr.pulsar$lightCoords();
      RenderTypeFeatureRendererAccessor outer = (RenderTypeFeatureRendererAccessor)gr.pulsar$outer();
      Vector3f p = new Vector3f();

      for (TextReplay.Segment s : baked.segments) {
         VertexConsumer out = outer.pulsar$vertexBuilder(s.type);
         if (!(out instanceof BufferBuilderAccessor bb && fast(s, bb, pose, frameLight, p))) {
            slow(s, out, pose, frameLight, p);
         }
      }
   }

   private static boolean fast(TextReplay.Segment s, BufferBuilderAccessor bb, Matrix4f pose, int frameLight, Vector3f p) {
      VertexFormatElement[] el = bb.pulsar$elements();
      if (el.length > 4 && el[0] != null && el[1] != null && el[2] != null && el[4] != null) {
         int filled = bb.pulsar$initialElementsToFill() & -24;
         if (filled != 0) {
            return false;
         } else {
            int offPos = el[0].offset();
            int offColor = el[1].offset();
            int offUv = el[2].offset();
            int offUv2 = el[4].offset();
            int n = s.argb.length;

            for (int i = 0; i < n; i++) {
               pose.transformPosition(s.pos[i * 3], s.pos[i * 3 + 1], s.pos[i * 3 + 2], p);
               long v = bb.pulsar$beginVertex();
               bb.pulsar$setElementsToFill(0);
               MemoryUtil.memPutFloat(v + offPos, p.x);
               MemoryUtil.memPutFloat(v + offPos + 4L, p.y);
               MemoryUtil.memPutFloat(v + offPos + 8L, p.z);
               MemoryUtil.memPutInt(v + offColor, s.abgr[i]);
               MemoryUtil.memPutFloat(v + offUv, s.uv[i * 2]);
               MemoryUtil.memPutFloat(v + offUv + 4L, s.uv[i * 2 + 1]);
               int l = s.light[i] == 2061400798 ? frameLight : s.light[i];
               MemoryUtil.memPutShort(v + offUv2, (short)l);
               MemoryUtil.memPutShort(v + offUv2 + 2L, (short)(l >> 16));
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private static void slow(TextReplay.Segment s, VertexConsumer out, Matrix4f pose, int frameLight, Vector3f p) {
      int n = s.argb.length;

      for (int i = 0; i < n; i++) {
         pose.transformPosition(s.pos[i * 3], s.pos[i * 3 + 1], s.pos[i * 3 + 2], p);
         VertexConsumer v = out.addVertex(p.x, p.y, p.z);
         if (s.hasColor[i]) {
            v = v.setColor(s.argb[i]);
         }

         if (s.hasUv[i]) {
            v = v.setUv(s.uv[i * 2], s.uv[i * 2 + 1]);
         }

         int l = s.light[i] == 2061400798 ? frameLight : s.light[i];
         v.setUv2(l & 65535, l >> 16 & 65535);
      }
   }

   static void disable(Throwable t) {
      if (!broken) {
         broken = true;
         BootstrapLog.info("perf: letreros horneados desactivados: " + t);
      }
   }

   static final class Baked {
      final List<TextReplay.Segment> segments;

      Baked(List<TextReplay.Segment> segments) {
         this.segments = segments;
      }
   }

   private static final class Recorder implements VertexConsumer {
      private final List<float[]> verts = new ArrayList<>(64);
      private float[] cur;

      void start() {
         this.verts.clear();
         this.cur = null;
      }

      List<float[]> finish() {
         this.cur = null;
         return new ArrayList<>(this.verts);
      }

      public VertexConsumer addVertex(float x, float y, float z) {
         this.cur = new float[]{x, y, z, 0.0F, 0.0F, 0.0F, 0.0F, Float.intBitsToFloat(2061400798), 0.0F};
         this.verts.add(this.cur);
         return this;
      }

      public VertexConsumer setColor(int r, int g, int b, int a) {
         return this.setColor(a << 24 | r << 16 | g << 8 | b);
      }

      public VertexConsumer setColor(int argb) {
         if (this.cur != null) {
            this.cur[6] = Float.intBitsToFloat(argb);
            this.cur[8] = 1.0F;
         }

         return this;
      }

      public VertexConsumer setUv(float u, float v) {
         if (this.cur != null) {
            this.cur[3] = u;
            this.cur[4] = v;
            this.cur[5] = 1.0F;
         }

         return this;
      }

      public VertexConsumer setUv1(int u, int v) {
         return this;
      }

      public VertexConsumer setUv2(int u, int v) {
         if (this.cur != null) {
            this.cur[7] = Float.intBitsToFloat((v & 65535) << 16 | u & 65535);
         }

         return this;
      }

      public VertexConsumer setNormal(float x, float y, float z) {
         return this;
      }

      public VertexConsumer setLineWidth(float width) {
         return this;
      }
   }

   static final class Segment {
      final RenderType type;
      final float[] pos;
      final float[] uv;
      final int[] abgr;
      final int[] argb;
      final int[] light;
      final boolean[] hasUv;
      final boolean[] hasColor;

      Segment(RenderType type, List<float[]> verts) {
         this.type = type;
         int n = verts.size();
         this.pos = new float[n * 3];
         this.uv = new float[n * 2];
         this.abgr = new int[n];
         this.argb = new int[n];
         this.light = new int[n];
         this.hasUv = new boolean[n];
         this.hasColor = new boolean[n];

         for (int i = 0; i < n; i++) {
            float[] v = verts.get(i);
            this.pos[i * 3] = v[0];
            this.pos[i * 3 + 1] = v[1];
            this.pos[i * 3 + 2] = v[2];
            this.uv[i * 2] = v[3];
            this.uv[i * 2 + 1] = v[4];
            this.hasUv[i] = v[5] != 0.0F;
            this.hasColor[i] = v[8] != 0.0F;
            this.argb[i] = this.hasColor[i] ? Float.floatToRawIntBits(v[6]) : -1;
            int a = ARGB.toABGR(this.argb[i]);
            this.abgr[i] = TextReplay.LITTLE_ENDIAN ? a : Integer.reverseBytes(a);
            this.light[i] = Float.floatToRawIntBits(v[7]);
         }
      }
   }
}
