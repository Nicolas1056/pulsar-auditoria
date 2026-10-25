package com.metrofico.pulsar.engine.v189.mixin;

import com.metrofico.pulsar.engine.v189.perf.SignBoards189;
import com.metrofico.pulsar.engine.v189.perf.SignText189;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin({aln.class})
public abstract class SignBlockEntityMixin implements SignText189.SignExt, SignBoards189.SignSection {
   @Unique
   private SignBoards189.Section pulsar$section;
   @Unique
   private boolean pulsar$textBaked;
   @Unique
   private final eu[] pulsar$source = new eu[4];
   @Unique
   private final List<eu>[] pulsar$lines = new List[4];
   @Unique
   private final String[] pulsar$formatted = new String[4];
   @Unique
   private final eu[] pulsar$blankSource = new eu[4];
   @Unique
   private final boolean[] pulsar$blank = new boolean[1];

   @Override
   public SignBoards189.Section pulsar$section() {
      return this.pulsar$section;
   }

   @Override
   public void pulsar$section(SignBoards189.Section section) {
      this.pulsar$section = section;
   }

   @Override
   public boolean pulsar$textBaked() {
      return this.pulsar$textBaked;
   }

   @Override
   public void pulsar$textBaked(boolean baked) {
      this.pulsar$textBaked = baked;
   }

   @Override
   public eu[] pulsar$blankSource() {
      return this.pulsar$blankSource;
   }

   @Override
   public boolean[] pulsar$blank() {
      return this.pulsar$blank;
   }

   @Override
   public eu[] pulsar$source() {
      return this.pulsar$source;
   }

   @Override
   public List<eu>[] pulsar$lines() {
      return this.pulsar$lines;
   }

   @Override
   public String[] pulsar$formatted() {
      return this.pulsar$formatted;
   }
}
