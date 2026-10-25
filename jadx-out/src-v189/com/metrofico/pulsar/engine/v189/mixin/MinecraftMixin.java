package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.emote.EmotePlayer;
import com.metrofico.pulsar.api.event.lifecycle.ClientReadyEvent;
import com.metrofico.pulsar.api.event.lifecycle.ClientStartingEvent;
import com.metrofico.pulsar.api.event.lifecycle.ClientTickEvent;
import com.metrofico.pulsar.api.hud.HudModules;
import com.metrofico.pulsar.api.legit.AimSensor;
import com.metrofico.pulsar.api.legit.GameSignals;
import com.metrofico.pulsar.api.legit.LiveSignals;
import com.metrofico.pulsar.bootstrap.engine.PulsarClient;
import com.metrofico.pulsar.engine.v189.Engine189;
import com.metrofico.pulsar.engine.v189.gfx.PulsarAtlases;
import com.metrofico.pulsar.engine.v189.hub.HubSetup189;
import com.metrofico.pulsar.engine.v189.perf.BufferDeletes189;
import com.metrofico.pulsar.engine.v189.perf.Hitches189;
import com.metrofico.pulsar.engine.v189.perf.PerfStats189;
import com.metrofico.pulsar.engine.v189.ui.MouseLockScreen189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({ave.class})
public abstract class MinecraftMixin {
   @Inject(
      method = {"<init>"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$starting(CallbackInfo ci) {
      PulsarAtlases.prefetch();
      HubSetup189.install();
      MouseLockScreen189.install();
      PulsarClient.engine().events().post(new ClientStartingEvent());
   }

   @Inject(
      method = {"a()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$ready(CallbackInfo ci) {
      PulsarClient.engine().events().post(new ClientReadyEvent());
   }

   @Inject(
      method = {"av"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$frameStart(CallbackInfo ci) {
      Hitches189.frameStart();
      BufferDeletes189.frame();
   }

   @Inject(
      method = {"s"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$tickEnd(CallbackInfo ci) {
      PerfStats189.end(7);
   }

   @Inject(
      method = {"s"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$tick(CallbackInfo ci) {
      PerfStats189.begin(7);
      HudModules.TICK++;
      EmotePlayer.tick();
      PulsarClient.engine().events().post(new ClientTickEvent());
      LiveSignals.tick(System.nanoTime());
      if (GameSignals.active()) {
         bew p = ave.A().h;
         if (p != null) {
            AimSensor.tick(p.y, p.z);
         }
      }
   }

   @Redirect(
      method = {"s"},
      at = @At(
         value = "INVOKE",
         target = "Lek;a()V"
      ),
      require = 1
   )
   private void pulsar$subNet(ek c) {
      PerfStats189.subBegin(0);

      try {
         c.a();
      } finally {
         PerfStats189.subEnd(0);
      }
   }

   @Redirect(
      method = {"s"},
      at = @At(
         value = "INVOKE",
         target = "Lbdb;i()V"
      ),
      require = 1
   )
   private void pulsar$subEntities(bdb w) {
      PerfStats189.subBegin(1);

      try {
         w.i();
      } finally {
         PerfStats189.subEnd(1);
      }
   }

   @Redirect(
      method = {"s"},
      at = @At(
         value = "INVOKE",
         target = "Lbdb;c()V"
      ),
      require = 1
   )
   private void pulsar$subWorld(bdb w) {
      PerfStats189.subBegin(2);

      try {
         w.c();
      } finally {
         PerfStats189.subEnd(2);
      }
   }

   @Inject(
      method = {"g"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$stopping(CallbackInfo ci) {
      Engine189.stopping(PulsarClient.engine());
   }
}
