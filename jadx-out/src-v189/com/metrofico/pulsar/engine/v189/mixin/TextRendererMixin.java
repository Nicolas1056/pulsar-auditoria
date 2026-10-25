package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.GlyphBatch189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({avn.class})
public abstract class TextRendererMixin {
   private TextRendererAccessor pulsar$self() {
      return (TextRendererAccessor)this;
   }

   @Inject(
      method = {"a(IZ)F"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$ascii(int ch, boolean italic, CallbackInfoReturnable<Float> cir) {
      if (FrameFlags.textBatch) {
         TextRendererAccessor s = this.pulsar$self();
         int i = ch % 16 * 8;
         int j = ch / 16 * 8;
         float f = italic ? 1.0F : 0.0F;
         float w = s.pulsar$characterWidths()[ch];
         float f1 = w - 0.01F;
         float x = s.pulsar$x();
         float y = s.pulsar$y();
         GlyphBatch189.add(
            s.pulsar$fontTexture(),
            x + f,
            y,
            i / 128.0F,
            j / 128.0F,
            x - f,
            y + 7.99F,
            i / 128.0F,
            (j + 7.99F) / 128.0F,
            x + f1 - 1.0F + f,
            y,
            (i + f1 - 1.0F) / 128.0F,
            j / 128.0F,
            x + f1 - 1.0F - f,
            y + 7.99F,
            (i + f1 - 1.0F) / 128.0F,
            (j + 7.99F) / 128.0F
         );
         cir.setReturnValue(w);
      }
   }

   @Inject(
      method = {"b(CZ)F"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$unicode(char ch, boolean italic, CallbackInfoReturnable<Float> cir) {
      if (FrameFlags.textBatch) {
         TextRendererAccessor s = this.pulsar$self();
         byte gw = s.pulsar$glyphWidths()[ch];
         if (gw == 0) {
            cir.setReturnValue(0.0F);
         } else {
            int page = ch / 256;
            float f = gw >>> 4;
            float f1 = (gw & 15) + 1;
            float f2 = ch % 16 * 16 + f;
            float f3 = (ch & 255) / 16 * 16;
            float f4 = f1 - f - 0.02F;
            float f5 = italic ? 1.0F : 0.0F;
            float x = s.pulsar$x();
            float y = s.pulsar$y();
            GlyphBatch189.add(
               s.pulsar$fontPage(page),
               x + f5,
               y,
               f2 / 256.0F,
               f3 / 256.0F,
               x - f5,
               y + 7.99F,
               f2 / 256.0F,
               (f3 + 15.98F) / 256.0F,
               x + f4 / 2.0F + f5,
               y,
               (f2 + f4) / 256.0F,
               f3 / 256.0F,
               x + f4 / 2.0F - f5,
               y + 7.99F,
               (f2 + f4) / 256.0F,
               (f3 + 15.98F) / 256.0F
            );
            cir.setReturnValue((f1 - f) / 2.0F + 1.0F);
         }
      }
   }

   @Redirect(
      method = {"a(Ljava/lang/String;Z)V"},
      at = @At(
         value = "INVOKE",
         target = "Lbfl;c(FFFF)V"
      ),
      require = 2
   )
   private void pulsar$colorCode(float r, float g, float b, float a) {
      GlyphBatch189.color(r, g, b, a);
      bfl.c(r, g, b, a);
   }

   @Redirect(
      method = {"b(Ljava/lang/String;FFIZ)I"},
      at = @At(
         value = "INVOKE",
         target = "Lbfl;c(FFFF)V"
      ),
      require = 1
   )
   private void pulsar$colorBase(float r, float g, float b, float a) {
      GlyphBatch189.color(r, g, b, a);
      bfl.c(r, g, b, a);
   }

   @Inject(
      method = {"a(Ljava/lang/String;Z)V"},
      at = {@At(
         value = "INVOKE",
         target = "Lbfx;a()Lbfx;"
      )},
      require = 2
   )
   private void pulsar$beforeLines(String text, boolean shadow, CallbackInfo ci) {
      GlyphBatch189.flush();
   }

   @Inject(
      method = {"a(Ljava/lang/String;Z)V"},
      at = {@At("TAIL")},
      require = 1
   )
   private void pulsar$end(String text, boolean shadow, CallbackInfo ci) {
      GlyphBatch189.flush();
   }
}
