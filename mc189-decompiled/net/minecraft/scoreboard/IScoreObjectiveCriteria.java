package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

public interface IScoreObjectiveCriteria {
   Map<String, IScoreObjectiveCriteria> a = Maps.newHashMap();
   IScoreObjectiveCriteria b = new ScoreDummyCriteria("dummy");
   IScoreObjectiveCriteria c = new ScoreDummyCriteria("trigger");
   IScoreObjectiveCriteria d = new ScoreDummyCriteria("deathCount");
   IScoreObjectiveCriteria e = new ScoreDummyCriteria("playerKillCount");
   IScoreObjectiveCriteria f = new ScoreDummyCriteria("totalKillCount");
   IScoreObjectiveCriteria g = new ScoreHealthCriteria("health");
   IScoreObjectiveCriteria[] h = new IScoreObjectiveCriteria[]{
      new GoalColor("teamkill.", EnumChatFormatting.a),
      new GoalColor("teamkill.", EnumChatFormatting.b),
      new GoalColor("teamkill.", EnumChatFormatting.c),
      new GoalColor("teamkill.", EnumChatFormatting.d),
      new GoalColor("teamkill.", EnumChatFormatting.e),
      new GoalColor("teamkill.", EnumChatFormatting.f),
      new GoalColor("teamkill.", EnumChatFormatting.g),
      new GoalColor("teamkill.", EnumChatFormatting.h),
      new GoalColor("teamkill.", EnumChatFormatting.i),
      new GoalColor("teamkill.", EnumChatFormatting.j),
      new GoalColor("teamkill.", EnumChatFormatting.k),
      new GoalColor("teamkill.", EnumChatFormatting.l),
      new GoalColor("teamkill.", EnumChatFormatting.m),
      new GoalColor("teamkill.", EnumChatFormatting.n),
      new GoalColor("teamkill.", EnumChatFormatting.o),
      new GoalColor("teamkill.", EnumChatFormatting.p)
   };
   IScoreObjectiveCriteria[] i = new IScoreObjectiveCriteria[]{
      new GoalColor("killedByTeam.", EnumChatFormatting.a),
      new GoalColor("killedByTeam.", EnumChatFormatting.b),
      new GoalColor("killedByTeam.", EnumChatFormatting.c),
      new GoalColor("killedByTeam.", EnumChatFormatting.d),
      new GoalColor("killedByTeam.", EnumChatFormatting.e),
      new GoalColor("killedByTeam.", EnumChatFormatting.f),
      new GoalColor("killedByTeam.", EnumChatFormatting.g),
      new GoalColor("killedByTeam.", EnumChatFormatting.h),
      new GoalColor("killedByTeam.", EnumChatFormatting.i),
      new GoalColor("killedByTeam.", EnumChatFormatting.j),
      new GoalColor("killedByTeam.", EnumChatFormatting.k),
      new GoalColor("killedByTeam.", EnumChatFormatting.l),
      new GoalColor("killedByTeam.", EnumChatFormatting.m),
      new GoalColor("killedByTeam.", EnumChatFormatting.n),
      new GoalColor("killedByTeam.", EnumChatFormatting.o),
      new GoalColor("killedByTeam.", EnumChatFormatting.p)
   };

   String a();

   int a(List<EntityPlayer> var1);

   boolean b();

   IScoreObjectiveCriteria.EnumRenderType c();

   public static enum EnumRenderType {
      a("integer"),
      b("hearts");

      private static final Map<String, IScoreObjectiveCriteria.EnumRenderType> c = Maps.newHashMap();
      private final String d;

      private EnumRenderType(String var3) {
         this.d = var3;
      }

      public String a() {
         return this.d;
      }

      public static IScoreObjectiveCriteria.EnumRenderType a(String var0) {
         IScoreObjectiveCriteria.EnumRenderType var1 = c.get(var0);
         return var1 == null ? a : var1;
      }

      static {
         for (IScoreObjectiveCriteria.EnumRenderType var3 : values()) {
            c.put(var3.a(), var3);
         }
      }
   }
}
