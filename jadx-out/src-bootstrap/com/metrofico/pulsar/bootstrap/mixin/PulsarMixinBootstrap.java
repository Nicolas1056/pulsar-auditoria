package com.metrofico.pulsar.bootstrap.mixin;

import org.spongepowered.asm.service.IMixinServiceBootstrap;

public final class PulsarMixinBootstrap implements IMixinServiceBootstrap {
   public String getName() {
      return "Pulsar";
   }

   public String getServiceClassName() {
      return PulsarMixinService.class.getName();
   }

   public void bootstrap() {
   }
}
