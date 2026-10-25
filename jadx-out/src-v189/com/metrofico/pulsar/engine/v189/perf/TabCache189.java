package com.metrofico.pulsar.engine.v189.perf;

import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.perf.FrameFlags;
import java.nio.IntBuffer;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public final class TabCache189 {
   private static final long REOPEN_GAP_NANOS = 100000000L;
   private static final int[] SAVED_STATE = new int[24];
   private static Boolean supported;
   private static int framebuffer;
   private static int texture;
   private static int pixelWidth;
   private static int pixelHeight;
   private static boolean capturing;
   private static int capturedTick = Integer.MIN_VALUE;
   private static int capturedWidth;
   private static auk capturedObjective;
   private static long lastDrawNanos;

   private TabCache189() {
   }

   public static boolean capturing() {
      return capturing;
   }

   public static boolean draw(awh hud, int width, auo scoreboard, auk objective) {
      if (!capturing && FrameFlags.tabCache && supported()) {
         int windowWidth = Display.getWidth();
         int windowHeight = Display.getHeight();
         long now = System.nanoTime();
         boolean reopened = now - lastDrawNanos > 100000000L;
         lastDrawNanos = now;
         boolean stale = reopened
            || capturedTick != HudModules.TICK
            || width != capturedWidth
            || objective != capturedObjective
            || windowWidth != pixelWidth
            || windowHeight != pixelHeight;
         if (stale) {
            if (!prepareTarget(windowWidth, windowHeight)) {
               return false;
            }

            capture(hud, width, scoreboard, objective);
            capturedTick = HudModules.TICK;
            capturedWidth = width;
            capturedObjective = objective;
         }

         paste();
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

   private static boolean prepareTarget(int width, int height) {
      if (framebuffer != 0 && width == pixelWidth && height == pixelHeight) {
         return true;
      } else {
         release();
         int previousTexture = GlState189.boundTexture();
         texture = GL11.glGenTextures();
         GL11.glBindTexture(3553, texture);
         GL11.glTexParameteri(3553, 10241, 9728);
         GL11.glTexParameteri(3553, 10240, 9728);
         GL11.glTexImage2D(3553, 0, 32856, width, height, 0, 6408, 5121, (IntBuffer)null);
         GL11.glBindTexture(3553, previousTexture);
         int previousFramebuffer = GL11.glGetInteger(36006);
         framebuffer = GL30.glGenFramebuffers();
         GL30.glBindFramebuffer(36160, framebuffer);
         GL30.glFramebufferTexture2D(36160, 36064, 3553, texture, 0);
         boolean complete = GL30.glCheckFramebufferStatus(36160) == 36053;
         GL30.glBindFramebuffer(36160, previousFramebuffer);
         if (!complete) {
            release();
            supported = false;
            return false;
         } else {
            pixelWidth = width;
            pixelHeight = height;
            return true;
         }
      }
   }

   private static void release() {
      if (framebuffer != 0) {
         GL30.glDeleteFramebuffers(framebuffer);
         framebuffer = 0;
      }

      if (texture != 0) {
         GL11.glDeleteTextures(texture);
         texture = 0;
      }

      pixelHeight = 0;
      pixelWidth = 0;
   }

   private static void capture(awh hud, int width, auo scoreboard, auk objective) {
      int previousFramebuffer = GL11.glGetInteger(36006);
      GL30.glBindFramebuffer(36160, framebuffer);
      makeTransparent();
      capturing = true;
      keepAlphaAccumulating();

      try {
         hud.a(width, scoreboard, objective);
      } finally {
         capturing = false;
         GL14.glBlendFuncSeparate(GlState189.blendSrc(), GlState189.blendDst(), GlState189.blendSrcAlpha(), GlState189.blendDstAlpha());
         GL30.glBindFramebuffer(36160, previousFramebuffer);
      }
   }

   private static void makeTransparent() {
      GlState189.snapshot(SAVED_STATE);
      bfl.x();
      bfl.k();
      bfl.c();
      bfl.c(0.0F, 0.0F, 0.0F, 0.0F);
      fullScreenQuad(false);
      GlState189.apply(SAVED_STATE);
   }

   private static void fullScreenQuad(boolean textured) {
      int guiScale = Math.max(1, new avr(ave.A()).e());
      float guiWidth = (float)pixelWidth / guiScale;
      float guiHeight = (float)pixelHeight / guiScale;
      GL11.glBegin(7);
      vertex(textured, 0.0F, 1.0F, 0.0F, 0.0F);
      vertex(textured, 0.0F, 0.0F, 0.0F, guiHeight);
      vertex(textured, 1.0F, 0.0F, guiWidth, guiHeight);
      vertex(textured, 1.0F, 1.0F, guiWidth, 0.0F);
      GL11.glEnd();
   }

   private static void vertex(boolean textured, float u, float v, float x, float y) {
      if (textured) {
         GL11.glTexCoord2f(u, v);
      }

      GL11.glVertex2f(x, y);
   }

   private static void paste() {
      GlState189.snapshot(SAVED_STATE);
      bfl.w();
      bfl.i(texture);
      bfl.l();
      bfl.a(1, 771, 1, 771);
      bfl.c();
      bfl.c(1.0F, 1.0F, 1.0F, 1.0F);
      fullScreenQuad(true);
      GlState189.apply(SAVED_STATE);
   }
}
