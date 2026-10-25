package net.minecraft.scoreboard;

import java.util.Comparator;
import java.util.List;
import net.minecraft.entity.player.EntityPlayer;

public class Score {
   public static final Comparator<Score> a = new Comparator<Score>() {
      public int a(Score var1, Score var2) {
         if (var1.c() > var2.c()) {
            return 1;
         } else {
            return var1.c() < var2.c() ? -1 : var2.e().compareToIgnoreCase(var1.e());
         }
      }
   };
   private final Scoreboard b;
   private final ScoreObjective c;
   private final String d;
   private int e;
   private boolean f;
   private boolean g;

   public Score(Scoreboard var1, ScoreObjective var2, String var3) {
      this.b = var1;
      this.c = var2;
      this.d = var3;
      this.g = true;
   }

   public void a(int var1) {
      if (this.c.c().b()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.c(this.c() + var1);
      }
   }

   public void b(int var1) {
      if (this.c.c().b()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.c(this.c() - var1);
      }
   }

   public void a() {
      if (this.c.c().b()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.a(1);
      }
   }

   public int c() {
      return this.e;
   }

   public void c(int var1) {
      int var2 = this.e;
      this.e = var1;
      if (var2 != var1 || this.g) {
         this.g = false;
         this.f().a(this);
      }
   }

   public ScoreObjective d() {
      return this.c;
   }

   public String e() {
      return this.d;
   }

   public Scoreboard f() {
      return this.b;
   }

   public boolean g() {
      return this.f;
   }

   public void a(boolean var1) {
      this.f = var1;
   }

   public void a(List<EntityPlayer> var1) {
      this.c(this.c.c().a(var1));
   }
}
