package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v189.perf.ChunkUpdates189;
import java.util.Queue;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({bho.class})
public abstract class ChunkUpdatesMixin {
   @Redirect(
      method = {"a(J)Z"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/Queue;isEmpty()Z"
      ),
      require = 1
   )
   private boolean pulsar$cap(Queue<?> queue) {
      return queue.isEmpty() || !ChunkUpdates189.allowUpload();
   }

   @ModifyConstant(
      method = {"<init>"},
      constant = {@Constant(
         intValue = 2
      )},
      require = 1
   )
   private static int pulsar$threads(int vanilla) {
      ClientOptions o = ClientOptions.get();
      return o.enabled("perf") ? Math.max(1, Math.min(5, o.integer("perf.chunkThreads", vanilla))) : vanilla;
   }
}
