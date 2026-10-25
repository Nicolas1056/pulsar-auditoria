package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.engine.PulsarVersion;
import com.metrofico.pulsar.api.event.lifecycle.ClientReadyEvent;
import com.metrofico.pulsar.api.event.lifecycle.ClientStartingEvent;
import com.metrofico.pulsar.api.event.lifecycle.ClientTickEvent;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.legit.AimSensor;
import com.metrofico.pulsar.api.legit.GameSignals;
import com.metrofico.pulsar.api.legit.LiveSignals;
import com.metrofico.pulsar.bootstrap.engine.PulsarClient;
import com.metrofico.pulsar.engine.v26x.V26xEngine;
import com.metrofico.pulsar.engine.v26x.hub.HubSetup;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import com.metrofico.pulsar.engine.v26x.ui.MouseLockScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Minecraft.class})
public abstract class MinecraftMixin {
   @Inject(
      method = {"<init>"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$starting(CallbackInfo ci) {
      HubSetup.install();
      MouseLockScreen.install();
      PulsarClient.engine().events().post(new ClientStartingEvent());
   }

   @Inject(
      method = {"createTitle"},
      at = {@At("RETURN")},
      cancellable = true,
      require = 1
   )
   private void pulsar$windowTitle(CallbackInfoReturnable<String> cir) {
      cir.setReturnValue(PulsarVersion.full() + " · " + (String)cir.getReturnValue());
   }

   @Inject(
      method = {"run"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$ready(CallbackInfo ci) {
      PulsarClient.engine().events().post(new ClientReadyEvent());
   }

   @Inject(
      method = {"tick"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$tick(CallbackInfo ci) {
      HudModules.TICK++;
      EmotePlayer.tick();
      PulsarClient.engine().events().post(new ClientTickEvent());
      LiveSignals.tick(System.nanoTime());
      if (GameSignals.active()) {
         LocalPlayer p = Minecraft.getInstance().player;
         if (p != null) {
            AimSensor.tick(p.getYRot(), p.getXRot());
         }
      }
   }

   @Inject(
      method = {"close"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$stopping(CallbackInfo ci) {
      V26xEngine.stopping(PulsarClient.engine());
   }

   @Inject(
      method = {"renderFrame"},
      at = {@At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/systems/GpuSurface;present()V"
      )},
      require = 1
   )
   private void pulsar$presentBegin(boolean advanceGameTime, CallbackInfo ci) {
      PerfStats.presentBegin();
   }

   @Inject(
      method = {"renderFrame"},
      at = {@At(
         value = "INVOKE",
         target = "Lcom/mojang/blaze3d/systems/GpuSurface;present()V",
         shift = Shift.AFTER
      )},
      require = 1
   )
   private void pulsar$presentEnd(boolean advanceGameTime, CallbackInfo ci) {
      PerfStats.presentEnd();
   }
}
