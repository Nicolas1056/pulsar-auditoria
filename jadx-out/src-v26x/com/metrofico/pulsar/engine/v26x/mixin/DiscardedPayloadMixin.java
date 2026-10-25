package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.pack.PulsarChannel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.DiscardedPayload;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({DiscardedPayload.class})
abstract class DiscardedPayloadMixin {
   @Inject(
      method = {"lambda$codec$1"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$decode(int maxSize, Identifier id, FriendlyByteBuf buf, CallbackInfoReturnable<DiscardedPayload> cir) {
      if (PulsarChannel.isPulsar(id)) {
         int n = buf.readableBytes();
         if (n > 0 && n <= 1048576) {
            byte[] data = new byte[n];
            buf.getBytes(buf.readerIndex(), data);
            PulsarChannel.onDecoded(data);
         }
      }
   }

   @Inject(
      method = {"lambda$codec$0"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$encode(DiscardedPayload payload, FriendlyByteBuf buf, CallbackInfo ci) {
      byte[] data = PulsarChannel.takeOutbound(payload);
      if (data != null) {
         buf.writeBytes(data);
      }
   }
}
