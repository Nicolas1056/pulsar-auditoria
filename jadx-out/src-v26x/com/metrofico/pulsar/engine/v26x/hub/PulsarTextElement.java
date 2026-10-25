package com.metrofico.pulsar.engine.v26x.hub;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.render.TextureSetup;
import net.minecraft.client.renderer.state.gui.GuiElementRenderState;
import org.joml.Matrix3x2f;
import org.joml.Vector2f;

public final class PulsarTextElement implements GuiElementRenderState {
   private final RenderPipeline pipeline;
   private final TextureSetup texture;
   private final Matrix3x2f pose;
   private final float[] quads;
   private final int count;
   private final int color;
   private final ScreenRectangle scissor;
   private final ScreenRectangle bounds;

   public PulsarTextElement(RenderPipeline pipeline, TextureSetup texture, Matrix3x2f pose, float[] quads, int count, int color, ScreenRectangle scissor) {
      this.pipeline = pipeline;
      this.texture = texture;
      this.pose = pose;
      this.quads = quads;
      this.count = count;
      this.color = color;
      this.scissor = scissor;
      this.bounds = bounds(pose, quads, count, scissor);
   }

   private static ScreenRectangle bounds(Matrix3x2f pose, float[] q, int n, ScreenRectangle scissor) {
      if (n == 0) {
         return ScreenRectangle.empty();
      } else {
         float minX = Float.MAX_VALUE;
         float minY = Float.MAX_VALUE;
         float maxX = -Float.MAX_VALUE;
         float maxY = -Float.MAX_VALUE;
         Vector2f v = new Vector2f();

         for (int i = 0; i < n; i++) {
            int o = i * 8;

            for (int c = 0; c < 4; c++) {
               float x = c != 0 && c != 1 ? q[o + 2] : q[o];
               float y = c != 0 && c != 3 ? q[o + 3] : q[o + 1];
               pose.transformPosition(x, y, v);
               minX = Math.min(minX, v.x);
               minY = Math.min(minY, v.y);
               maxX = Math.max(maxX, v.x);
               maxY = Math.max(maxY, v.y);
            }
         }

         ScreenRectangle r = new ScreenRectangle((int)Math.floor(minX), (int)Math.floor(minY), (int)Math.ceil(maxX - minX) + 1, (int)Math.ceil(maxY - minY) + 1);
         return scissor == null ? r : r.intersection(scissor);
      }
   }

   public void buildVertices(VertexConsumer vc) {
      for (int i = 0; i < this.count; i++) {
         int o = i * 8;
         float x0 = this.quads[o];
         float y0 = this.quads[o + 1];
         float x1 = this.quads[o + 2];
         float y1 = this.quads[o + 3];
         float u0 = this.quads[o + 4];
         float v0 = this.quads[o + 5];
         float u1 = this.quads[o + 6];
         float v1 = this.quads[o + 7];
         vc.addVertexWith2DPose(this.pose, x0, y0).setUv(u0, v0).setColor(this.color);
         vc.addVertexWith2DPose(this.pose, x0, y1).setUv(u0, v1).setColor(this.color);
         vc.addVertexWith2DPose(this.pose, x1, y1).setUv(u1, v1).setColor(this.color);
         vc.addVertexWith2DPose(this.pose, x1, y0).setUv(u1, v0).setColor(this.color);
      }
   }

   public RenderPipeline pipeline() {
      return this.pipeline;
   }

   public TextureSetup textureSetup() {
      return this.texture;
   }

   public ScreenRectangle scissorArea() {
      return this.scissor;
   }

   public ScreenRectangle bounds() {
      return this.bounds;
   }
}
