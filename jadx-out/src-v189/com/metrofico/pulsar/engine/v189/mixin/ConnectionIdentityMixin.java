package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.legit.ConnectionIdentity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({awz.class})
public abstract class ConnectionIdentityMixin {
   @Inject(
      method = {"<init>(Laxu;Lave;Lbde;)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lawz;a(Ljava/lang/String;I)V"
      )},
      require = 1
   )
   private void pulsar$typedFromList(axu parent, ave client, bde entry, CallbackInfo ci) {
      ConnectionIdentity.connecting(entry.b);
   }

   @Inject(
      method = {"<init>(Laxu;Lave;Ljava/lang/String;I)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lawz;a(Ljava/lang/String;I)V"
      )},
      require = 1
   )
   private void pulsar$typedFromArgs(axu parent, ave client, String host, int port, CallbackInfo ci) {
      ConnectionIdentity.connecting(host);
   }
}
