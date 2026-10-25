package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.net.NetMeter;
import com.metrofico.pulsar.engine.v189.net.MainTasks189;
import java.util.Queue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ave.class})
public abstract class MinecraftClientTasksMixin {
   @Shadow
   @Final
   private Queue<FutureTask<?>> aM;
   @Shadow
   @Final
   private static Logger K;

   @Inject(
      method = {"av"},
      at = {@At(
         value = "FIELD",
         target = "Lave;aM:Ljava/util/Queue;",
         opcode = 180,
         ordinal = 0
      )},
      require = 1
   )
   private void pulsar$drainTasks(CallbackInfo ci) {
      MainTasks189.run(this.aM, K);
   }

   @Redirect(
      method = {"av"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/Queue;isEmpty()Z"
      ),
      require = 1
   )
   private boolean pulsar$vanillaSeesEmpty(Queue<?> queue) {
      return MainTasks189.vanillaSeesEmpty(queue);
   }

   @Inject(
      method = {"a(Ljava/util/concurrent/Callable;)Lcom/google/common/util/concurrent/ListenableFuture;"},
      at = {@At(
         value = "INVOKE",
         target = "Ljava/util/Queue;add(Ljava/lang/Object;)Z",
         shift = Shift.AFTER
      )},
      require = 1
   )
   private void pulsar$enqueued(Callable<?> task, CallbackInfoReturnable<?> cir) {
      NetMeter.CLIENT.enqueued(System.nanoTime());
   }
}
