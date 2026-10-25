package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.bootstrap.BootstrapLog;
import com.metrofico.pulsar.engine.v26x.perf.PerfStats;
import com.metrofico.pulsar.engine.v26x.perf.TerrainMultidraw;
import com.mojang.blaze3d.shaders.ShaderType;
import net.minecraft.client.renderer.ShaderManager;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ShaderManager.class})
abstract class ShaderManagerMixin {
   @Inject(
      method = {"getShader"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$shader(Identifier id, ShaderType type, CallbackInfoReturnable<String> cir) {
      boolean compact = TerrainMultidraw.SHADER_COMPACT.equals(id);
      if (compact || TerrainMultidraw.SHADER.equals(id)) {
         String vanilla = ((ShaderManager)this).getShader(Identifier.withDefaultNamespace("core/terrain"), type);
         cir.setReturnValue(vanilla == null ? null : TerrainMultidraw.patchShader(vanilla, type == ShaderType.VERTEX, compact));
         if (vanilla != null) {
            BootstrapLog.info("perf: terrain shader " + id + " (" + type + ") patched" + (compact ? " compact" : "") + " for " + PerfStats.backend());
         }
      }
   }
}
