package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.EntityBatch189;
import com.metrofico.pulsar.engine.v189.perf.EntityCache189;
import com.metrofico.pulsar.engine.v189.perf.GlState189;
import com.metrofico.pulsar.engine.v189.perf.HudImage189;
import com.metrofico.pulsar.engine.v189.perf.MatrixMirror189;
import com.metrofico.pulsar.engine.v189.perf.TabCache189;
import java.nio.FloatBuffer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({bfl.class})
public abstract class GlStateManagerMixin {
   @Inject(
      method = {"i(I)V", "c(FFFF)V", "c(FFF)V", "l", "k", "b(II)V", "a(IIII)V", "a(IF)V", "d()V", "c()V", "w", "x", "g(I)V", "a(Z)V", "c(I)V", "e()V", "f()V", "o()V", "p", "e(I)V", "j()V", "i()V", "B", "C", "z", "A", "a(ZZZZ)V", "n(I)V", "D", "m()V", "n()V", "g()V", "h()V", "j(I)V", "o(I)V", "q", "r"},
      at = {@At("HEAD")},
      require = 37
   )
   private static void pulsar$flushOnState(CallbackInfo ci) {
      if (EntityBatch189.pending()) {
         EntityBatch189.flush();
      }
   }

   @Inject(
      method = {"b(II)V", "a(IIII)V"},
      at = {@At("RETURN")},
      require = 2
   )
   private static void pulsar$tabAlpha(CallbackInfo ci) {
      if (TabCache189.capturing()) {
         TabCache189.keepAlphaAccumulating();
      } else if (HudImage189.capturing()) {
         HudImage189.keepAlphaAccumulating();
      }
   }

   @Inject(
      method = {"E"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$push(CallbackInfo ci) {
      EntityBatch189.push();
      MatrixMirror189.push();
      EntityCache189.texturePush();
   }

   @Inject(
      method = {"n(I)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorMode(int mode, CallbackInfo ci) {
      EntityCache189.matrixMode(mode);
      MatrixMirror189.matrixMode(mode);
   }

   @Inject(
      method = {"D"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorIdentity(CallbackInfo ci) {
      MatrixMirror189.loadIdentity();
      EntityCache189.textureIdentity();
   }

   @Inject(
      method = {"b(FFF)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorTranslate(float x, float y, float z, CallbackInfo ci) {
      MatrixMirror189.translate(x, y, z);
      EntityCache189.textureTranslate(x, y, z);
   }

   @Inject(
      method = {"b(DDD)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorTranslateD(double x, double y, double z, CallbackInfo ci) {
      MatrixMirror189.translate((float)x, (float)y, (float)z);
      EntityCache189.textureTranslate((float)x, (float)y, (float)z);
   }

   @Inject(
      method = {"a(FFF)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorScale(float x, float y, float z, CallbackInfo ci) {
      MatrixMirror189.scale(x, y, z);
      EntityCache189.textureScale(x, y, z);
   }

   @Inject(
      method = {"a(DDD)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorScaleD(double x, double y, double z, CallbackInfo ci) {
      MatrixMirror189.scale((float)x, (float)y, (float)z);
      EntityCache189.textureScale((float)x, (float)y, (float)z);
   }

   @Inject(
      method = {"b(FFFF)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorRotate(float angle, float x, float y, float z, CallbackInfo ci) {
      MatrixMirror189.rotate(angle, x, y, z);
      EntityCache189.textureRotate(angle, x, y, z);
   }

   @Inject(
      method = {"a(Ljava/nio/FloatBuffer;)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorMult(FloatBuffer matrix, CallbackInfo ci) {
      MatrixMirror189.mult(matrix);
      EntityCache189.textureOp(99, 0.0F, 0.0F, 0.0F, 0.0F);
   }

   @Inject(
      method = {"a(DDDDDD)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$mirrorOrtho(double l, double r, double b, double t, double n, double f, CallbackInfo ci) {
      MatrixMirror189.unknown();
   }

   @Inject(
      method = {"F"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$pop(CallbackInfo ci) {
      EntityBatch189.pop();
      MatrixMirror189.pop();
      EntityCache189.texturePop();
   }

   @Inject(
      method = {"g(I)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sActive(int unit, CallbackInfo ci) {
      GlState189.activeTexture(unit);
   }

   @Inject(
      method = {"i(I)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sBind(int id, CallbackInfo ci) {
      GlState189.bindTexture(id);
   }

   @Inject(
      method = {"c(FFFF)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sColor(float r, float g, float b, float a, CallbackInfo ci) {
      GlState189.color(r, g, b, a);
   }

   @Inject(
      method = {"G()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sClearColor(CallbackInfo ci) {
      GlState189.clearColor();
   }

   @Inject(
      method = {"l"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sBlendOn(CallbackInfo ci) {
      GlState189.blend(true);
   }

   @Inject(
      method = {"k"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sBlendOff(CallbackInfo ci) {
      GlState189.blend(false);
   }

   @Inject(
      method = {"b(II)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sBlendFunc(int src, int dst, CallbackInfo ci) {
      GlState189.blendFunc(src, dst, src, dst);
   }

   @Inject(
      method = {"a(IIII)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sBlendSep(int src, int dst, int srcA, int dstA, CallbackInfo ci) {
      GlState189.blendFunc(src, dst, srcA, dstA);
   }

   @Inject(
      method = {"d()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sAlphaOn(CallbackInfo ci) {
      GlState189.alphaTest(true);
   }

   @Inject(
      method = {"c()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sAlphaOff(CallbackInfo ci) {
      GlState189.alphaTest(false);
   }

   @Inject(
      method = {"a(IF)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sAlphaFunc(int func, float ref, CallbackInfo ci) {
      GlState189.alphaFunc(func, ref);
   }

   @Inject(
      method = {"o()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sCullOn(CallbackInfo ci) {
      GlState189.cull(true);
   }

   @Inject(
      method = {"p"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sCullOff(CallbackInfo ci) {
      GlState189.cull(false);
   }

   @Inject(
      method = {"e()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sLightOn(CallbackInfo ci) {
      GlState189.lighting(true);
   }

   @Inject(
      method = {"f()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sLightOff(CallbackInfo ci) {
      GlState189.lighting(false);
   }

   @Inject(
      method = {"j()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sDepthOn(CallbackInfo ci) {
      GlState189.depthTest(true);
   }

   @Inject(
      method = {"i()V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sDepthOff(CallbackInfo ci) {
      GlState189.depthTest(false);
   }

   @Inject(
      method = {"a(Z)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sDepthMask(boolean on, CallbackInfo ci) {
      GlState189.depthMask(on);
   }

   @Inject(
      method = {"c(I)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sDepthFunc(int func, CallbackInfo ci) {
      GlState189.depthFunc(func);
   }

   @Inject(
      method = {"B"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sRescaleOn(CallbackInfo ci) {
      GlState189.rescale(true);
   }

   @Inject(
      method = {"C"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sRescaleOff(CallbackInfo ci) {
      GlState189.rescale(false);
   }

   @Inject(
      method = {"z"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sNormOn(CallbackInfo ci) {
      GlState189.normalize(true);
   }

   @Inject(
      method = {"A"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sNormOff(CallbackInfo ci) {
      GlState189.normalize(false);
   }

   @Inject(
      method = {"w"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sTexOn(CallbackInfo ci) {
      GlState189.texture2d(true);
   }

   @Inject(
      method = {"x"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sTexOff(CallbackInfo ci) {
      GlState189.texture2d(false);
   }

   @Inject(
      method = {"q"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sPolyOn(CallbackInfo ci) {
      GlState189.polyOffset(true);
   }

   @Inject(
      method = {"r"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sPolyOff(CallbackInfo ci) {
      GlState189.polyOffset(false);
   }

   @Inject(
      method = {"a(FF)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private static void pulsar$sPolyVal(float factor, float units, CallbackInfo ci) {
      GlState189.polygonOffset(factor, units);
   }

   @Inject(
      method = {"o(I)V", "a(ZZZZ)V", "j(I)V", "g()V", "h()V", "m()V", "n()V"},
      at = {@At("HEAD")},
      require = 7
   )
   private static void pulsar$sUnsupported(CallbackInfo ci) {
      EntityCache189.unsupported("estado GL fuera de la foto");
   }
}
