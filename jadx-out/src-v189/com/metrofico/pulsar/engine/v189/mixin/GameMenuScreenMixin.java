package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.hub.HubScreen189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({axp.class})
public abstract class GameMenuScreenMixin extends axu {
   private static final int PULSAR_BUTTON = 8129;
   private static final int BUTTON_WIDTH = 56;
   private static final int BUTTON_HEIGHT = 20;
   private static final int EDGE_MARGIN = 8;

   @Inject(
      method = {"b"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$button(CallbackInfo ci) {
      this.n.add(new avs(8129, this.l - 56 - 8, 8, 56, 20, "Pulsar"));
   }

   @Inject(
      method = {"a(Lavs;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$open(avs button, CallbackInfo ci) {
      if (button.k == 8129) {
         this.j.a(new HubScreen189(this));
         ci.cancel();
      }
   }
}
