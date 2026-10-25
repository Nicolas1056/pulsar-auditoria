package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.SignBoards189;
import com.metrofico.pulsar.engine.v189.perf.SignText189;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin({bhj.class})
public abstract class SignBlockEntityRendererMixin {
   @Redirect(
      method = {"a(Laln;DDDFI)V"},
      at = @At(
         value = "INVOKE",
         target = "Lavu;a(Leu;ILavn;ZZ)Ljava/util/List;"
      ),
      require = 1
   )
   private List<eu> pulsar$lines(
      eu text, int width, avn renderer, boolean a, boolean b, aln sign, double x, double y, double z, float tickDelta, int destroyStage
   ) {
      return !SignText189.enabled() ? avu.a(text, width, renderer, a, b) : SignText189.lines((SignText189.SignExt)sign, sign.a, text, width, renderer, x, y, z);
   }

   @Redirect(
      method = {"a(Laln;DDDFI)V"},
      at = @At(
         value = "INVOKE",
         target = "Leu;d()Ljava/lang/String;"
      ),
      require = 1
   )
   private String pulsar$formatted(eu line, aln sign, double x, double y, double z, float tickDelta, int destroyStage) {
      return SignText189.enabled() ? SignText189.formatted((SignText189.SignExt)sign, line) : line.d();
   }

   @Redirect(
      method = {"a(Laln;DDDFI)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbbx;a()V"
      ),
      require = 1
   )
   private void pulsar$board(bbx model) {
      if (!SignBoards189.textOnly()) {
         model.a();
      }
   }
}
