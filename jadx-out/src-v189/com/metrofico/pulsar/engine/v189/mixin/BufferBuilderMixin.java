package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hub.ClientOptions;
import com.metrofico.pulsar.engine.v189.perf.EntityBatch189;
import com.metrofico.pulsar.engine.v189.perf.SpriteCulling189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bfd.class})
public abstract class BufferBuilderMixin {
   @Shadow
   private boolean n;
   private static int pulsar$recovered;
   private static final boolean TRACE_BEGIN = System.getenv("PULSAR_TRACE_BEGIN") != null;
   private static Throwable pulsar$lastBegin;

   @Shadow
   public abstract void e();

   @Shadow
   public abstract void b();

   @Inject(
      method = {"a(ILbmu;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$flushBatch(int mode, bmu format, CallbackInfo ci) {
      if (EntityBatch189.onRenderThread()) {
         if (EntityBatch189.pending()) {
            EntityBatch189.flush();
         }

         if (this.n) {
            if (pulsar$recovered++ < 3) {
               ClientOptions.LOG
                  .accept(
                     "perf: buffer compartido a medio construir al pedir begin; se descarta. Origen:\n"
                        + pulsar$trace()
                        + (
                           pulsar$lastBegin != null
                              ? "  El begin sin draw se pidió desde:\n" + pulsar$trace(pulsar$lastBegin)
                              : "  (PULSAR_TRACE_BEGIN=1 para saber quién lo dejó abierto)"
                        )
                  );
            }

            this.e();
            this.b();
         }

         if (TRACE_BEGIN) {
            pulsar$lastBegin = new Throwable();
         }
      }
   }

   private static String pulsar$trace(Throwable t) {
      StringBuilder sb = new StringBuilder();
      StackTraceElement[] st = t.getStackTrace();

      for (int i = 1; i < Math.min(st.length, 14); i++) {
         sb.append("    at ").append(st[i]).append('\n');
      }

      return sb.toString();
   }

   private static String pulsar$trace() {
      StringBuilder sb = new StringBuilder();
      StackTraceElement[] st = new Throwable().getStackTrace();

      for (int i = 2; i < Math.min(st.length, 14); i++) {
         sb.append("    at ").append(st[i]).append('\n');
      }

      return sb.toString();
   }

   @Inject(
      method = {"a([I)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$quad(int[] data, CallbackInfo ci) {
      if (data.length >= 6 && SpriteCulling189.enabled()) {
         SpriteCulling189.quad(Float.intBitsToFloat(data[4]), Float.intBitsToFloat(data[5]), SpriteCulling189.onRenderThread());
      }
   }
}
