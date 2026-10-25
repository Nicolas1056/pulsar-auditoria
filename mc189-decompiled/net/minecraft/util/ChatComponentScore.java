package net.minecraft.util;

import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;

public class ChatComponentScore extends ChatComponentStyle {
   private final String b;
   private final String c;
   private String d = "";

   public ChatComponentScore(String var1, String var2) {
      this.b = var1;
      this.c = var2;
   }

   public String g() {
      return this.b;
   }

   public String h() {
      return this.c;
   }

   public void b(String var1) {
      this.d = var1;
   }

   @Override
   public String e() {
      MinecraftServer var1 = MinecraftServer.N();
      if (var1 != null && var1.O() && StringUtils.b(this.d)) {
         Scoreboard var2 = var1.a(0).Z();
         ScoreObjective var3 = var2.b(this.c);
         if (var2.b(this.b, var3)) {
            Score var4 = var2.c(this.b, var3);
            this.b(String.format("%d", var4.c()));
         } else {
            this.d = "";
         }
      }

      return this.d;
   }

   public ChatComponentScore i() {
      ChatComponentScore var1 = new ChatComponentScore(this.b, this.c);
      var1.b(this.d);
      var1.a(this.b().m());

      for (IChatComponent var3 : this.a()) {
         var1.a(var3.f());
      }

      return var1;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ChatComponentScore)) {
         return false;
      } else {
         ChatComponentScore var2 = (ChatComponentScore)var1;
         return this.b.equals(var2.b) && this.c.equals(var2.c) && super.equals(var1);
      }
   }

   @Override
   public String toString() {
      return "ScoreComponent{name='" + this.b + '\'' + "objective='" + this.c + '\'' + ", siblings=" + this.a + ", style=" + this.b() + '}';
   }
}
