package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.net.NetMeter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({en.class})
public abstract class DecoderHandlerMixin {
   @Shadow
   @Final
   private fg c;
   @Unique
   private long pulsar$start;
   @Unique
   private int pulsar$bytes;

   @Inject(
      method = {"decode"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$begin(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out, CallbackInfo ci) {
      this.pulsar$bytes = buf.readableBytes();
      this.pulsar$start = System.nanoTime();
   }

   @Inject(
      method = {"decode"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$end(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out, CallbackInfo ci) {
      if (this.c == fg.b && this.pulsar$bytes > 0) {
         long now = System.nanoTime();
         NetMeter.CLIENT.received(this.pulsar$bytes, now - this.pulsar$start, now);
      }
   }
}
