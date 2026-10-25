package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.hud.modules.Visuals17;
import java.util.IdentityHashMap;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({bkn.class})
public abstract class ArmorFeatureRendererMixin {
   private static final Map<Object, String[]> PULSAR$PATHS = new IdentityHashMap<>();

   @Redirect(
      method = {"a(Lyj;ZLjava/lang/String;)Ljy;"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/lang/String;format(Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;"
      ),
      require = 1
   )
   private String pulsar$path(String format, Object[] args) {
      if (args.length == 3 && "".equals(args[2]) && args[1] instanceof Integer layer && layer >= 1 && layer <= 2) {
         String[] byLayer = PULSAR$PATHS.computeIfAbsent(args[0], k -> new String[3]);
         String path = byLayer[layer];
         if (path == null) {
            path = String.format(format, args);
            byLayer[layer] = path;
         }

         return path;
      } else {
         return String.format(format, args);
      }
   }

   @Inject(
      method = {"b()Z"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$armorHit(CallbackInfoReturnable<Boolean> cir) {
      if (Visuals17.armorHit()) {
         cir.setReturnValue(true);
      }
   }
}
