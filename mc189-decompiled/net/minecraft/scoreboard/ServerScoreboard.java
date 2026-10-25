package net.minecraft.scoreboard;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.server.MinecraftServer;

public class ServerScoreboard extends Scoreboard {
   private final MinecraftServer a;
   private final Set<ScoreObjective> b = Sets.newHashSet();
   private ScoreboardSaveData c;

   public ServerScoreboard(MinecraftServer var1) {
      this.a = var1;
   }

   @Override
   public void a(Score var1) {
      super.a(var1);
      if (this.b.contains(var1.d())) {
         this.a.ap().a(new S3CPacketUpdateScore(var1));
      }

      this.b();
   }

   @Override
   public void a(String var1) {
      super.a(var1);
      this.a.ap().a(new S3CPacketUpdateScore(var1));
      this.b();
   }

   @Override
   public void a(String var1, ScoreObjective var2) {
      super.a(var1, var2);
      this.a.ap().a(new S3CPacketUpdateScore(var1, var2));
      this.b();
   }

   @Override
   public void a(int var1, ScoreObjective var2) {
      ScoreObjective var3 = this.a(var1);
      super.a(var1, var2);
      if (var3 != var2 && var3 != null) {
         if (this.h(var3) > 0) {
            this.a.ap().a(new S3DPacketDisplayScoreboard(var1, var2));
         } else {
            this.g(var3);
         }
      }

      if (var2 != null) {
         if (this.b.contains(var2)) {
            this.a.ap().a(new S3DPacketDisplayScoreboard(var1, var2));
         } else {
            this.e(var2);
         }
      }

      this.b();
   }

   @Override
   public boolean a(String var1, String var2) {
      if (super.a(var1, var2)) {
         ScorePlayerTeam var3 = this.d(var2);
         this.a.ap().a(new S3EPacketTeams(var3, Arrays.asList(var1), 3));
         this.b();
         return true;
      } else {
         return false;
      }
   }

   @Override
   public void a(String var1, ScorePlayerTeam var2) {
      super.a(var1, var2);
      this.a.ap().a(new S3EPacketTeams(var2, Arrays.asList(var1), 4));
      this.b();
   }

   @Override
   public void a(ScoreObjective var1) {
      super.a(var1);
      this.b();
   }

   @Override
   public void b(ScoreObjective var1) {
      super.b(var1);
      if (this.b.contains(var1)) {
         this.a.ap().a(new S3BPacketScoreboardObjective(var1, 2));
      }

      this.b();
   }

   @Override
   public void c(ScoreObjective var1) {
      super.c(var1);
      if (this.b.contains(var1)) {
         this.g(var1);
      }

      this.b();
   }

   @Override
   public void a(ScorePlayerTeam var1) {
      super.a(var1);
      this.a.ap().a(new S3EPacketTeams(var1, 0));
      this.b();
   }

   @Override
   public void b(ScorePlayerTeam var1) {
      super.b(var1);
      this.a.ap().a(new S3EPacketTeams(var1, 2));
      this.b();
   }

   @Override
   public void c(ScorePlayerTeam var1) {
      super.c(var1);
      this.a.ap().a(new S3EPacketTeams(var1, 1));
      this.b();
   }

   public void a(ScoreboardSaveData var1) {
      this.c = var1;
   }

   protected void b() {
      if (this.c != null) {
         this.c.c();
      }
   }

   public List<Packet> d(ScoreObjective var1) {
      ArrayList var2 = Lists.newArrayList();
      var2.add(new S3BPacketScoreboardObjective(var1, 0));

      for (int var3 = 0; var3 < 19; var3++) {
         if (this.a(var3) == var1) {
            var2.add(new S3DPacketDisplayScoreboard(var3, var1));
         }
      }

      for (Score var4 : this.i(var1)) {
         var2.add(new S3CPacketUpdateScore(var4));
      }

      return var2;
   }

   public void e(ScoreObjective var1) {
      List var2 = this.d(var1);

      for (EntityPlayerMP var4 : this.a.ap().v()) {
         for (Packet var6 : var2) {
            var4.a.a(var6);
         }
      }

      this.b.add(var1);
   }

   public List<Packet> f(ScoreObjective var1) {
      ArrayList var2 = Lists.newArrayList();
      var2.add(new S3BPacketScoreboardObjective(var1, 1));

      for (int var3 = 0; var3 < 19; var3++) {
         if (this.a(var3) == var1) {
            var2.add(new S3DPacketDisplayScoreboard(var3, var1));
         }
      }

      return var2;
   }

   public void g(ScoreObjective var1) {
      List var2 = this.f(var1);

      for (EntityPlayerMP var4 : this.a.ap().v()) {
         for (Packet var6 : var2) {
            var4.a.a(var6);
         }
      }

      this.b.remove(var1);
   }

   public int h(ScoreObjective var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < 19; var3++) {
         if (this.a(var3) == var1) {
            var2++;
         }
      }

      return var2;
   }
}
