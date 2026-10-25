package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class ScoreDummyCriteria implements IScoreObjectiveCriteria {
   private final String j;

   public ScoreDummyCriteria(String var1) {
      this.j = var1;
      IScoreObjectiveCriteria.a.put(var1, this);
   }

   @Override
   public String a() {
      return this.j;
   }

   @Override
   public int a(List<EntityPlayer> var1) {
      return 0;
   }

   @Override
   public boolean b() {
      return false;
   }

   @Override
   public IScoreObjectiveCriteria.EnumRenderType c() {
      return IScoreObjectiveCriteria.EnumRenderType.a;
   }
}
