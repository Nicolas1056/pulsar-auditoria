package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.modules.SneakGuard;
import java.util.function.BooleanSupplier;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.ToggleKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({ToggleKeyMapping.class})
abstract class ToggleKeyMappingMixin {
   @Redirect(
      method = {"setDown"},
      at = @At(
         value = "INVOKE",
         target = "Ljava/util/function/BooleanSupplier;getAsBoolean()Z"
      ),
      require = 1
   )
   private boolean pulsar$needsToggle(BooleanSupplier needsToggle) {
      return needsToggle.getAsBoolean() && !SneakGuard.holdOnly((KeyMapping)this);
   }
}
