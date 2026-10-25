package com.metrofico.pulsar.engine.v26x.modules;

import com.metrofico.pulsar.api.hud.modules.ToggleSneakSprint;
import com.metrofico.pulsar.engine.v26x.mixin.KeyMappingAccessor;
import com.metrofico.pulsar.engine.v26x.mixin.ToggleKeyMappingInvoker;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.InputConstants.Key;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.lwjgl.glfw.GLFW;

public final class SneakGuard {
   private static boolean armed;

   private SneakGuard() {
   }

   public static boolean holdOnly(KeyMapping key) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.options != null && key == mc.options.keyShift) {
         LocalPlayer p = mc.player;
         return p != null && (p.getAbilities().flying || inWater(p) && ToggleSneakSprint.sneakInWater());
      } else {
         return false;
      }
   }

   static boolean inWater(LocalPlayer p) {
      return p != null && (p.isInWater() || p.isUnderWater());
   }

   public static void tick() {
      Minecraft mc = Minecraft.getInstance();
      if (mc.options != null && mc.player != null) {
         KeyMapping shift = mc.options.keyShift;
         if (shift instanceof ToggleKeyMappingInvoker toggle && (Boolean)mc.options.toggleCrouch().get()) {
            boolean down = shift.isDown();
            if (holdOnly(shift)) {
               if (down && !physicallyDown(mc, shift)) {
                  toggle.pulsar$reset();
                  down = false;
               }

               armed = down;
            } else {
               if (armed) {
                  if (!down) {
                     armed = false;
                  } else if (!physicallyDown(mc, shift)) {
                     toggle.pulsar$reset();
                     armed = false;
                  }
               }
            }
         } else {
            armed = false;
         }
      } else {
         armed = false;
      }
   }

   public static void releaseLatched(KeyMapping key) {
      Minecraft mc = Minecraft.getInstance();
      if (key instanceof ToggleKeyMappingInvoker toggle && key.isDown() && !physicallyDown(mc, key)) {
         toggle.pulsar$reset();
      }
   }

   private static boolean physicallyDown(Minecraft mc, KeyMapping mapping) {
      Key key = ((KeyMappingAccessor)mapping).pulsar$key();
      if (key != null && mc.getWindow() != null) {
         long handle = mc.getWindow().handle();

         return switch (key.getType()) {
            case KEYSYM -> InputConstants.isKeyDown(mc.getWindow(), key.getValue());
            case MOUSE -> GLFW.glfwGetMouseButton(handle, key.getValue()) == 1;
            case SCANCODE -> false;
            default -> throw new MatchException(null, null);
         };
      } else {
         return false;
      }
   }
}
