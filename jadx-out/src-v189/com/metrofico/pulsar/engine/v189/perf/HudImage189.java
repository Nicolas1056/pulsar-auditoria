package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.perf.FrameFlags;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public final class HudImage189 {
   private static final int[] SAVED_STATE = new int[24];
   private static final Map<Object, HudImage189> SLOTS = new HashMap<>();
   private static Boolean supported;
   private static boolean capturing;
   private int framebuffer;
   private int texture;
   private int pixelWidth;
   private int pixelHeight;
   private Object key;
   private long longKey;
   private int keyX0;
   private int keyY0;
   private int keyX1;
   private int keyY1;
   private static final Object LONG_KEY = new Object();

   private HudImage189() {
   }

   public static boolean capturing() {
      return capturing;
   }

   public static boolean draw(Object slot, Object key, int x0, int y0, int x1, int y1, int guiScale, Runnable draw, Runnable flush) {
      return draw(slot, key, 0L, x0, y0, x1, y1, guiScale, draw, flush);
   }

   public static boolean draw(Object slot, long key, int x0, int y0, int x1, int y1, int guiScale, Runnable draw, Runnable flush) {
      return draw(slot, LONG_KEY, key, x0, y0, x1, y1, guiScale, draw, flush);
   }

   private static boolean draw(Object slot, Object key, long longKey, int x0, int y0, int x1, int y1, int guiScale, Runnable draw, Runnable flush) {
      if (!capturing && FrameFlags.uiBatch && supported() && x1 > x0 && y1 > y0) {
         HudImage189 image = SLOTS.computeIfAbsent(slot, s -> new HudImage189());
         int windowWidth = Display.getWidth();
         int windowHeight = Display.getHeight();
         if (!key.equals(image.key)
            || longKey != image.longKey
            || x0 != image.keyX0
            || y0 != image.keyY0
            || x1 != image.keyX1
            || y1 != image.keyY1
            || windowWidth != image.pixelWidth
            || windowHeight != image.pixelHeight) {
            if (!image.prepareTarget(windowWidth, windowHeight)) {
               return false;
            }

            image.capture(x0, y0, x1, y1, draw, flush);
            image.key = key;
            image.longKey = longKey;
            image.keyX0 = x0;
            image.keyY0 = y0;
            image.keyX1 = x1;
            image.keyY1 = y1;
         }

         image.paste(x0, y0, x1, y1, guiScale);
         return true;
      } else {
         return false;
      }
   }

   public static void keepAlphaAccumulating() {
      GL14.glBlendFuncSeparate(GlState189.blendSrc(), GlState189.blendDst(), 1, 771);
   }

   private static boolean supported() {
      if (supported == null) {
         supported = GLContext.getCapabilities().OpenGL30;
      }

      return supported;
   }

   private boolean prepareTarget(int width, int height) {
      if (this.framebuffer != 0 && width == this.pixelWidth && height == this.pixelHeight) {
         return true;
      } else {
         this.release();
         int previousTexture = GlState189.boundTexture();
         this.texture = GL11.glGenTextures();
         GL11.glBindTexture(3553, this.texture);
         GL11.glTexParameteri(3553, 10241, 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
         GL11.glTexImage2D(3553, 0, 32856, width, height, 0, 6408, 5121, (IntBuffer)null);
         GL11.glBindTexture(3553, previousTexture);
         int previousFramebuffer = GL11.glGetInteger(36006);
         this.framebuffer = GL30.glGenFramebuffers();
         GL30.glBindFramebuffer(36160, this.framebuffer);
         GL30.glFramebufferTexture2D(36160, 36064, 3553, this.texture, 0);
         boolean complete = GL30.glCheckFramebufferStatus(36160) == 36053;
         GL30.glBindFramebuffer(36160, previousFramebuffer);
         if (!complete) {
            this.release();
            supported = false;
            return false;
         } else {
            this.pixelWidth = width;
            this.pixelHeight = height;
            this.key = null;
            return true;
         }
      }
   }

   private void release() {
      if (this.framebuffer != 0) {
         GL30.glDeleteFramebuffers(this.framebuffer);
         this.framebuffer = 0;
      }

      if (this.texture != 0) {
         GL11.glDeleteTextures(this.texture);
         this.texture = 0;
      }

      this.pixelWidth = this.pixelHeight = 0;
   }

   private void capture(int x0, int y0, int x1, int y1, Runnable draw, Runnable flush) {
      int previousFramebuffer = GL11.glGetInteger(36006);
      GL30.glBindFramebuffer(36160, this.framebuffer);
      clear(x0, y0, x1, y1);
      capturing = true;
      keepAlphaAccumulating();

      try {
         draw.run();
         flush.run();
      } finally {
         capturing = false;
         GL14.glBlendFuncSeparate(GlState189.blendSrc(), GlState189.blendDst(), GlState189.blendSrcAlpha(), GlState189.blendDstAlpha());
         GL30.glBindFramebuffer(36160, previousFramebuffer);
      }
   }

   private static void clear(int x0, int y0, int x1, int y1) {
      GlState189.snapshot(SAVED_STATE);
      bfl.x();
      bfl.k();
      bfl.c();
      bfl.c(0.0F, 0.0F, 0.0F, 0.0F);
      GL11.glBegin(7);
      GL11.glVertex2f(x0, y1);
      GL11.glVertex2f(x1, y1);
      GL11.glVertex2f(x1, y0);
      GL11.glVertex2f(x0, y0);
      GL11.glEnd();
      GlState189.apply(SAVED_STATE);
   }

   private void paste(int x0, int y0, int x1, int y1, int guiScale) {
      float u0 = (float)(x0 * guiScale) / this.pixelWidth;
      float u1 = (float)(x1 * guiScale) / this.pixelWidth;
      float v0 = 1.0F - (float)(y0 * guiScale) / this.pixelHeight;
      float v1 = 1.0F - (float)(y1 * guiScale) / this.pixelHeight;
      GlState189.snapshot(SAVED_STATE);
      bfl.w();
      bfl.i(this.texture);
      bfl.l();
      bfl.a(1, 771, 1, 771);
      bfl.c();
      bfl.c(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glBegin(7);
      GL11.glTexCoord2f(u0, v1);
      GL11.glVertex2f(x0, y1);
      GL11.glTexCoord2f(u1, v1);
      GL11.glVertex2f(x1, y1);
      GL11.glTexCoord2f(u1, v0);
      GL11.glVertex2f(x1, y0);
      GL11.glTexCoord2f(u0, v0);
      GL11.glVertex2f(x0, y0);
      GL11.glEnd();
      GlState189.apply(SAVED_STATE);
   }
}
