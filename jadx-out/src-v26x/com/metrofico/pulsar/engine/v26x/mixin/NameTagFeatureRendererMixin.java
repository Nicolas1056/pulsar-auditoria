package com.metrofico.pulsar.engine.v26x.mixin;

import com.metrofico.pulsar.engine.v26x.perf.ParallelFeatures;
import com.metrofico.pulsar.engine.v26x.perf.TextBake;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Font.GlyphVisitor;
import net.minecraft.client.gui.Font.PreparedText;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer;
import net.minecraft.client.renderer.feature.NameTagFeatureRenderer.Submit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({NameTagFeatureRenderer.class})
abstract class NameTagFeatureRendererMixin {
   @Inject(
      method = {"prepareText"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private static void pulsar$cached(Font font, Submit submit, CallbackInfoReturnable<PreparedText> cir) {
      PreparedText text = ParallelFeatures.preparedText(submit);
      boolean cache = TextBake.enabled();
      if (text != null) {
         if (cache) {
            TextBake.attach(submit.text(), submit.x(), submit.y(), submit.color(), submit.backgroundColor(), text);
         }

         cir.setReturnValue(text);
      } else {
         if (cache) {
            text = TextBake.get(submit.text(), submit.x(), submit.y(), submit.color(), submit.backgroundColor());
            if (text != null) {
               cir.setReturnValue(text);
            }
         }
      }
   }

   @Inject(
      method = {"prepareText"},
      at = {@At("RETURN")},
      require = 1
   )
   private static void pulsar$store(Font font, Submit submit, CallbackInfoReturnable<PreparedText> cir) {
      if (TextBake.enabled() && !cir.isCancelled()) {
         TextBake.put(submit.text(), submit.x(), submit.y(), submit.color(), submit.backgroundColor(), (PreparedText)cir.getReturnValue());
      }
   }

   @Redirect(
      method = {"buildGroup"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/Font$PreparedText;visit(Lnet/minecraft/client/gui/Font$GlyphVisitor;)V"
      ),
      require = 1
   )
   private void pulsar$visitTag(PreparedText text, GlyphVisitor glyphRenderer) {
      TextBake.visitTag(text, glyphRenderer);
   }
}
