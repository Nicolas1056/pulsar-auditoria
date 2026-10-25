package net.minecraft.scoreboard;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.WorldSavedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ScoreboardSaveData extends WorldSavedData {
   private static final Logger b = LogManager.getLogger();
   private Scoreboard c;
   private NBTTagCompound d;

   public ScoreboardSaveData() {
      this("scoreboard");
   }

   public ScoreboardSaveData(String var1) {
      super(var1);
   }

   public void a(Scoreboard var1) {
      this.c = var1;
      if (this.d != null) {
         this.a(this.d);
      }
   }

   @Override
   public void a(NBTTagCompound var1) {
      if (this.c == null) {
         this.d = var1;
      } else {
         this.b(var1.c("Objectives", 10));
         this.c(var1.c("PlayerScores", 10));
         if (var1.b("DisplaySlots", 10)) {
            this.c(var1.m("DisplaySlots"));
         }

         if (var1.b("Teams", 9)) {
            this.a(var1.c("Teams", 10));
         }
      }
   }

   protected void a(NBTTagList var1) {
      for (int var2 = 0; var2 < var1.c(); var2++) {
         NBTTagCompound var3 = var1.b(var2);
         String var4 = var3.j("Name");
         if (var4.length() > 16) {
            var4 = var4.substring(0, 16);
         }

         ScorePlayerTeam var5 = this.c.e(var4);
         String var6 = var3.j("DisplayName");
         if (var6.length() > 32) {
            var6 = var6.substring(0, 32);
         }

         var5.a(var6);
         if (var3.b("TeamColor", 8)) {
            var5.a(EnumChatFormatting.b(var3.j("TeamColor")));
         }

         var5.b(var3.j("Prefix"));
         var5.c(var3.j("Suffix"));
         if (var3.b("AllowFriendlyFire", 99)) {
            var5.a(var3.n("AllowFriendlyFire"));
         }

         if (var3.b("SeeFriendlyInvisibles", 99)) {
            var5.b(var3.n("SeeFriendlyInvisibles"));
         }

         if (var3.b("NameTagVisibility", 8)) {
            Team.EnumVisible var7 = Team.EnumVisible.a(var3.j("NameTagVisibility"));
            if (var7 != null) {
               var5.a(var7);
            }
         }

         if (var3.b("DeathMessageVisibility", 8)) {
            Team.EnumVisible var8 = Team.EnumVisible.a(var3.j("DeathMessageVisibility"));
            if (var8 != null) {
               var5.b(var8);
            }
         }

         this.a(var5, var3.c("Players", 8));
      }
   }

   protected void a(ScorePlayerTeam var1, NBTTagList var2) {
      for (int var3 = 0; var3 < var2.c(); var3++) {
         this.c.a(var2.f(var3), var1.b());
      }
   }

   protected void c(NBTTagCompound var1) {
      for (int var2 = 0; var2 < 19; var2++) {
         if (var1.b("slot_" + var2, 8)) {
            String var3 = var1.j("slot_" + var2);
            ScoreObjective var4 = this.c.b(var3);
            this.c.a(var2, var4);
         }
      }
   }

   protected void b(NBTTagList var1) {
      for (int var2 = 0; var2 < var1.c(); var2++) {
         NBTTagCompound var3 = var1.b(var2);
         IScoreObjectiveCriteria var4 = IScoreObjectiveCriteria.a.get(var3.j("CriteriaName"));
         if (var4 != null) {
            String var5 = var3.j("Name");
            if (var5.length() > 16) {
               var5 = var5.substring(0, 16);
            }

            ScoreObjective var6 = this.c.a(var5, var4);
            var6.a(var3.j("DisplayName"));
            var6.a(IScoreObjectiveCriteria.EnumRenderType.a(var3.j("RenderType")));
         }
      }
   }

   protected void c(NBTTagList var1) {
      for (int var2 = 0; var2 < var1.c(); var2++) {
         NBTTagCompound var3 = var1.b(var2);
         ScoreObjective var4 = this.c.b(var3.j("Objective"));
         String var5 = var3.j("Name");
         if (var5.length() > 40) {
            var5 = var5.substring(0, 40);
         }

         Score var6 = this.c.c(var5, var4);
         var6.c(var3.f("Score"));
         if (var3.c("Locked")) {
            var6.a(var3.n("Locked"));
         }
      }
   }

   @Override
   public void b(NBTTagCompound var1) {
      if (this.c == null) {
         b.warn("Tried to save scoreboard without having a scoreboard...");
      } else {
         var1.a("Objectives", this.b());
         var1.a("PlayerScores", this.e());
         var1.a("Teams", this.a());
         this.d(var1);
      }
   }

   protected NBTTagList a() {
      NBTTagList var1 = new NBTTagList();

      for (ScorePlayerTeam var4 : this.c.g()) {
         NBTTagCompound var5 = new NBTTagCompound();
         var5.a("Name", var4.b());
         var5.a("DisplayName", var4.c());
         if (var4.l().b() >= 0) {
            var5.a("TeamColor", var4.l().e());
         }

         var5.a("Prefix", var4.e());
         var5.a("Suffix", var4.f());
         var5.a("AllowFriendlyFire", var4.g());
         var5.a("SeeFriendlyInvisibles", var4.h());
         var5.a("NameTagVisibility", var4.i().e);
         var5.a("DeathMessageVisibility", var4.j().e);
         NBTTagList var6 = new NBTTagList();

         for (String var8 : var4.d()) {
            var6.a(new NBTTagString(var8));
         }

         var5.a("Players", var6);
         var1.a(var5);
      }

      return var1;
   }

   protected void d(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();
      boolean var3 = false;

      for (int var4 = 0; var4 < 19; var4++) {
         ScoreObjective var5 = this.c.a(var4);
         if (var5 != null) {
            var2.a("slot_" + var4, var5.b());
            var3 = true;
         }
      }

      if (var3) {
         var1.a("DisplaySlots", var2);
      }
   }

   protected NBTTagList b() {
      NBTTagList var1 = new NBTTagList();

      for (ScoreObjective var4 : this.c.c()) {
         if (var4.c() != null) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.a("Name", var4.b());
            var5.a("CriteriaName", var4.c().a());
            var5.a("DisplayName", var4.d());
            var5.a("RenderType", var4.e().a());
            var1.a(var5);
         }
      }

      return var1;
   }

   protected NBTTagList e() {
      NBTTagList var1 = new NBTTagList();

      for (Score var4 : this.c.e()) {
         if (var4.d() != null) {
            NBTTagCompound var5 = new NBTTagCompound();
            var5.a("Name", var4.e());
            var5.a("Objective", var4.d().b());
            var5.a("Score", var4.c());
            var5.a("Locked", var4.g());
            var1.a(var5);
         }
      }

      return var1;
   }
}
