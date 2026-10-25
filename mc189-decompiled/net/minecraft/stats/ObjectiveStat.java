package net.minecraft.stats;

import net.minecraft.scoreboard.ScoreDummyCriteria;

public class ObjectiveStat extends ScoreDummyCriteria {
   private final StatBase j;

   public ObjectiveStat(StatBase var1) {
      super(var1.e);
      this.j = var1;
   }
}
