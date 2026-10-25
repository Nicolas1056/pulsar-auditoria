package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.NameCache189;
import com.metrofico.pulsar.engine.v189.perf.NameOnly189;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin({pk.class})
public abstract class EntityMixin implements NameCache189, NameOnly189 {
   @Unique
   private String pulsar$nameKey;
   @Unique
   private eu pulsar$nameText;
   @Unique
   private String pulsar$nameFormatted;
   @Unique
   private int pulsar$nameOnly = -1;

   @Shadow
   public abstract String e_();

   @Shadow
   public abstract eu f_();

   @Override
   public int pulsar$nameOnlyFrame() {
      return this.pulsar$nameOnly;
   }

   @Override
   public void pulsar$nameOnlyFrame(int frame) {
      this.pulsar$nameOnly = frame;
   }

   @Override
   public String pulsar$cachedFormatted() {
      eu text = this.pulsar$cachedName();
      if (this.pulsar$nameFormatted == null) {
         this.pulsar$nameFormatted = text.d();
      }

      return this.pulsar$nameFormatted;
   }

   @Override
   public eu pulsar$cachedName() {
      String key = this.e_();
      if (this.pulsar$nameText == null || !key.equals(this.pulsar$nameKey)) {
         this.pulsar$nameKey = key;
         this.pulsar$nameText = this.f_();
         this.pulsar$nameFormatted = null;
      }

      return this.pulsar$nameText;
   }
}
