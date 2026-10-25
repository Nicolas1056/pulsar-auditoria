package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.net.NetMeter;
import com.metrofico.pulsar.engine.v189.pack.LegacyChannel;
import com.metrofico.pulsar.engine.v189.pack.Pack189;
import java.nio.charset.StandardCharsets;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bcy.class})
public abstract class ClientPlayNetworkHandlerMixin {
   @Inject(
      method = {"a(Lgt;)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$joined(gt packet, CallbackInfo ci) {
      ave.A().a(() -> Pack189.get().onJoined());
   }

   @Inject(
      method = {"a(Leu;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$left(eu reason, CallbackInfo ci) {
      Pack189.get().onLeft();
      NetMeter.CLIENT.disconnected();
   }

   @Inject(
      method = {"a(Lgg;)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$payload(gg packet, CallbackInfo ci) {
      byte[] data = LegacyChannel.payload(packet.a(), packet.b());
      if (data != null) {
         String json = new String(data, StandardCharsets.UTF_8);
         ave mc = ave.A();
         if (mc.aJ()) {
            Pack189.get().onMessage(json);
         } else {
            mc.a(() -> Pack189.get().onMessage(json));
         }

         ci.cancel();
      }
   }
}
