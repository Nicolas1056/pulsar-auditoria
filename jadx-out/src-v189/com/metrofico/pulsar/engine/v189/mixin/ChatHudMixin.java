package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.api.perf.FrameFlags;
import com.metrofico.pulsar.engine.v189.perf.ChatCache189;
import com.metrofico.pulsar.engine.v189.perf.HudImage189;
import com.metrofico.pulsar.engine.v189.perf.MatrixMirror189;
import com.metrofico.pulsar.engine.v189.perf.ObfuscatedLine189;
import java.util.List;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wn.b;

@Mixin({avt.class})
public abstract class ChatHudMixin {
   @Shadow
   @Final
   private ave f;
   @Shadow
   @Final
   private List<ava> h;
   @Shadow
   @Final
   private List<ava> i;
   @Shadow
   private int j;
   @Shadow
   private boolean k;
   @Unique
   private int pulsar$version;
   @Unique
   private int pulsar$pending = -1;
   @Unique
   private boolean pulsar$capturing;
   @Unique
   private int pulsar$ticks;
   @Unique
   private float pulsar$offsetX;
   @Unique
   private float pulsar$offsetY;
   @Unique
   private Runnable pulsar$draw;

   @Shadow
   public abstract void a(int var1);

   @Shadow
   public abstract void d();

   @Shadow
   public abstract boolean e();

   @Shadow
   public abstract int f();

   @Shadow
   public abstract float h();

   @Shadow
   public abstract int i();

   @ModifyConstant(
      method = {"a(Leu;IIZ)V"},
      constant = {@Constant(
         intValue = 100
      )},
      require = 2
   )
   private int pulsar$history(int vanilla) {
      return 10000;
   }

   @Inject(
      method = {"a(Leu;IIZ)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$beforeAdd(eu text, int id, int tick, boolean refresh, CallbackInfo ci) {
      if (!refresh && this.pulsar$pending >= 0) {
         this.pulsar$pending++;
      }
   }

   @Inject(
      method = {"a(Leu;IIZ)V", "b()V"},
      at = {@At("RETURN")},
      require = 2
   )
   private void pulsar$changed(CallbackInfo ci) {
      this.pulsar$version++;
   }

   @Inject(
      method = {"a()V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$cleared(CallbackInfo ci) {
      this.pulsar$pending = -1;
      this.pulsar$version++;
   }

   @Inject(
      method = {"c(I)V"},
      at = {@At("HEAD")},
      require = 1
   )
   private void pulsar$beforeRemove(int id, CallbackInfo ci) {
      if (this.pulsar$pending >= 0) {
         this.pulsar$rewrap(Integer.MAX_VALUE);
      }
   }

   @Inject(
      method = {"c(I)V"},
      at = {@At("RETURN")},
      require = 1
   )
   private void pulsar$removed(int id, CallbackInfo ci) {
      this.pulsar$version++;
   }

   @Inject(
      method = {"b()V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$reset(CallbackInfo ci) {
      if (!FrameFlags.chatRewrap) {
         this.pulsar$pending = -1;
      } else {
         this.i.clear();
         this.d();
         this.pulsar$pending = 0;
         this.pulsar$rewrap(100);
         this.pulsar$version++;
         ci.cancel();
      }
   }

   @Unique
   private void pulsar$rewrap(int budget) {
      int before = this.i.size();
      int width = ns.d(this.f() / this.h());

      while (budget-- > 0 && this.pulsar$pending >= 0) {
         if (this.pulsar$pending >= this.h.size() || this.i.size() >= 10000) {
            this.pulsar$pending = -1;
            break;
         }

         ava message = this.h.get(this.pulsar$pending++);
         List<eu> lines = avu.a(message.a(), width, this.f.k, false, false);

         for (int j = lines.size() - 1; j >= 0; j--) {
            this.i.add(new ava(message.b(), lines.get(j), message.c()));
         }
      }

      if (this.i.size() != before && this.j + this.i() > before) {
         this.pulsar$version++;
      }
   }

   @Inject(
      method = {"a(I)V"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 1
   )
   private void pulsar$render(int ticks, CallbackInfo ci) {
      if (!this.pulsar$capturing) {
         if (this.pulsar$pending >= 0) {
            this.pulsar$rewrap(200);
         }

         if (FrameFlags.chatCache && this.f.t.m != b.c) {
            int size = this.i.size();
            if (size == 0) {
               ci.cancel();
            } else {
               boolean focused = this.e();
               int lineCount = this.i();
               float scale = this.h();
               int width = this.f();
               long key = ChatCache189.mix(this.pulsar$version, this.j);
               key = ChatCache189.mix(key, (focused ? 1 : 0) | (this.k ? 2 : 0));
               key = ChatCache189.mix(key, Float.floatToIntBits(this.f.t.q));
               key = ChatCache189.mix(key, Float.floatToIntBits(scale));
               key = ChatCache189.mix(key, (long)width << 32 | lineCount);
               boolean anyShown = focused;

               for (int i = 0; i < lineCount && i + this.j < size; i++) {
                  ava line = this.i.get(i + this.j);
                  if (((ObfuscatedLine189)line).pulsar$obfuscated()) {
                     return;
                  }

                  if (!focused) {
                     int fade = ChatCache189.fadeState(ticks - line.b());
                     key = ChatCache189.mix(key, fade);
                     anyShown |= fade >= 0;
                  }
               }

               if (!anyShown) {
                  ci.cancel();
               } else {
                  float[] m = MatrixMirror189.matrix();
                  float offsetX = m[12];
                  float offsetY = m[13];
                  int guiScale = ChatCache189.guiScale(this.f);
                  int columns = ns.f(width / scale);
                  int x1 = (int)Math.ceil(offsetX + 2.0F + (columns + 4) * scale) + 2;
                  int y0 = Math.max(0, (int)Math.floor(offsetY + 20.0F - (lineCount + 2) * 9 * scale) - 2);
                  int y1 = (int)Math.ceil(offsetY + 20.0F) + 2;
                  int x0 = Math.max(0, (int)Math.floor(offsetX));
                  key = ChatCache189.mix(key, (long)Float.floatToIntBits(offsetX) << 32 | Float.floatToIntBits(offsetY));
                  this.pulsar$ticks = ticks;
                  this.pulsar$offsetX = offsetX;
                  this.pulsar$offsetY = offsetY;
                  if (this.pulsar$draw == null) {
                     this.pulsar$draw = this::pulsar$drawVanilla;
                  }

                  bfl.E();
                  bfl.b(-offsetX, -offsetY, 0.0F);
                  boolean cached = HudImage189.draw(ChatCache189.SLOT, key, x0, y0, x1, y1, guiScale, this.pulsar$draw, ChatCache189.NO_FLUSH);
                  bfl.F();
                  if (cached) {
                     ci.cancel();
                  }
               }
            }
         }
      }
   }

   @Unique
   private void pulsar$drawVanilla() {
      bfl.E();
      bfl.b(this.pulsar$offsetX, this.pulsar$offsetY, 0.0F);
      this.pulsar$capturing = true;

      try {
         this.a(this.pulsar$ticks);
      } finally {
         this.pulsar$capturing = false;
         bfl.F();
      }
   }
}
