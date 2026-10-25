package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.api.ui.TitleView;
import com.metrofico.pulsar.api.ui.TitleView.Key;
import com.metrofico.pulsar.engine.v189.gfx.Canvas189;
import com.metrofico.pulsar.engine.v189.hub.TitleHost189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({aya.class})
abstract class TitleScreenMixin extends axu {
   @Unique
   private TitleView pulsar$view;

   @Shadow
   private void c(int mouseX, int mouseY, float tickDelta) {
   }

   @Inject(
      method = {"b()V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$init(CallbackInfo ci) {
      if (ClientOptions.get().pulsarTitleScreen()) {
         this.n.clear();
         this.pulsar$view = new TitleView(new TitleHost189(this));
      } else {
         this.pulsar$view = null;
      }
   }

   @Inject(
      method = {"a(IIF)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$render(int mouseX, int mouseY, float tickDelta, CallbackInfo ci) {
      if (this.pulsar$view != null) {
         bfl.c();
         this.c(mouseX, mouseY, tickDelta);
         bfl.d();
         Canvas189 g = new Canvas189();
         this.pulsar$view.render(g, mouseX, mouseY);
         g.flush();
         ci.cancel();
      }
   }

   @Inject(
      method = {"a(III)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$click(int x, int y, int button, CallbackInfo ci) {
      if (this.pulsar$view != null) {
         this.pulsar$view.mouseClicked(x, y, button);
         ci.cancel();
      }
   }

   @Inject(
      method = {"a(CI)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$key(char character, int code, CallbackInfo ci) {
      if (this.pulsar$view != null) {
         Key key = switch (code) {
            case 15 -> Key.TAB;
            case 28, 156 -> Key.ENTER;
            case 200 -> Key.UP;
            case 208 -> Key.DOWN;
            default -> Key.OTHER;
         };
         this.pulsar$view.keyPressed(key);
         ci.cancel();
      }
   }

   @Inject(
      method = {"m"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$removed(CallbackInfo ci) {
      if (this.pulsar$view != null) {
         this.pulsar$view.closed();
      }
   }
}
