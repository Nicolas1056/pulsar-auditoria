package net.minecraft.scoreboard;

import java.util.List;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public class GoalColor implements IScoreObjectiveCriteria {
   private final String j;

   public GoalColor(String var1, EnumChatFormatting var2) {
      this.j = var1 + var2.e();
      IScoreObjectiveCriteria.a.put(this.j, this);
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
