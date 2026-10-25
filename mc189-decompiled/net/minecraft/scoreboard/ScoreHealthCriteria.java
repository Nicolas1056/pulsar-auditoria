package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;

public class ScoreHealthCriteria extends ScoreDummyCriteria {
   public ScoreHealthCriteria(String var1) {
      super(var1);
   }

   @Override
   public int a(List<EntityPlayer> var1) {
      float var2 = 0.0F;

      for (EntityPlayer var4 : var1) {
         var2 += var4.bn() + var4.bN();
      }

      if (var1.size() > 0) {
         var2 /= var1.size();
      }

      return MathHelper.f(var2);
   }

   @Override
   public boolean b() {
      return true;
   }

   @Override
   public IScoreObjectiveCriteria.EnumRenderType c() {
      return IScoreObjectiveCriteria.EnumRenderType.b;
   }
}
