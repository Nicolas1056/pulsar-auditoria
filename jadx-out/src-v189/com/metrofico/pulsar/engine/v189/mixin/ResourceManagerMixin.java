package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.emote.EmoteSounds;
import com.metrofico.pulsar.engine.v189.emote.EmoteResource189;
import java.io.IOException;
import java.io.InputStream;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bnn.class})
public abstract class ResourceManagerMixin {
   @Inject(
      method = {"a(Ljy;)Lbnh;"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$emoteSound(jy id, CallbackInfoReturnable<bnh> cir) throws IOException {
      if ("pulsar-emote".equals(id.b())) {
         String sha = EmoteSounds.shaOf(id.a());
         InputStream in = EmoteSounds.open(sha);
         cir.setReturnValue(new EmoteResource189(id, in));
      }
   }
}
