package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.api.camera.CameraDirector;
import com.metrofico.pulsar.engine.v26x.modules.SneakGuard;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({KeyboardInput.class})
abstract class KeyboardInputMixin {
   @Inject(
      method = {"tick"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$sneakGuard(CallbackInfo ci) {
      SneakGuard.tick();
   }

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$freeze(CallbackInfo ci) {
      if (CameraDirector.get().active()) {
         ((ClientInput)this).keyPresses = Input.EMPTY;
      }
   }
}
