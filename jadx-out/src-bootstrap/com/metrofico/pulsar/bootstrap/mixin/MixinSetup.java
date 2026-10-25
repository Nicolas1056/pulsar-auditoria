package com.metrofico.pulsar.bootstrap.mixin;

import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.bootstrap.loader.GameClassLoader;
import java.lang.reflect.Method;
import java.util.List;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.MixinEnvironment.Phase;
import org.spongepowered.asm.mixin.transformer.IMixinTransformer;

public final class MixinSetup {
   private static boolean initialized;

   public static synchronized void init(GameClassLoader loader, List<String> mixinConfigs) {
      if (initialized) {
         throw new IllegalStateException("Mixin is already initialized in this JVM");
      } else {
         PulsarMixinService.attach(loader);
         new MixinTargetValidator(loader).validate(mixinConfigs);
         MixinBootstrap.init();
         mixinConfigs.forEach(Mixins::addConfiguration);
         gotoPhase(Phase.INIT);
         gotoPhase(Phase.DEFAULT);
         IMixinTransformer transformer = PulsarMixinService.transformer();
         if (transformer == null) {
            throw new IllegalStateException("Mixin did not offer a transformer: the Pulsar service was not selected");
         } else {
            loader.transformers().addFirst(new MixinTransformer(transformer));
            initialized = true;
            BootstrapLog.info(
               "Mixin " + MixinEnvironment.getCurrentEnvironment().getVersion() + " ready with " + mixinConfigs.size() + " config(s): " + mixinConfigs
            );
         }
      }
   }

   private static void gotoPhase(Phase phase) {
      try {
         Method gotoPhase = MixinEnvironment.class.getDeclaredMethod("gotoPhase", Phase.class);
         gotoPhase.setAccessible(true);
         gotoPhase.invoke(null, phase);
      } catch (ReflectiveOperationException var2) {
         throw new IllegalStateException("Cannot move Mixin to phase " + phase, var2);
      }
   }

   private MixinSetup() {
   }
}
