package com.metrofico.pulsar.engine.v26x.hub;

import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public final class PulsarCursor {
   private static long hand;
   private static long arrow;
   private static boolean isHand;

   private PulsarCursor() {
   }

   public static void set(boolean pointing) {
      if (pointing != isHand) {
         long window = Minecraft.getInstance().getWindow().handle();
         if (hand == 0L) {
            hand = GLFW.glfwCreateStandardCursor(221188);
            arrow = GLFW.glfwCreateStandardCursor(221185);
         }

         GLFW.glfwSetCursor(window, pointing ? hand : arrow);
         isHand = pointing;
      }
   }

   public static void reset() {
      set(false);
   }
}
