package net.minecraft.scoreboard;

public class ScoreObjective {
   private final Scoreboard a;
   private final String b;
   private final IScoreObjectiveCriteria c;
   private IScoreObjectiveCriteria.EnumRenderType d;
   private String e;

   public ScoreObjective(Scoreboard var1, String var2, IScoreObjectiveCriteria var3) {
      this.a = var1;
      this.b = var2;
      this.c = var3;
      this.e = var2;
      this.d = var3.c();
   }

   public Scoreboard a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   public IScoreObjectiveCriteria c() {
      return this.c;
   }

   public String d() {
      return this.e;
   }

   public void a(String var1) {
      this.e = var1;
      this.a.b(this);
   }

   public IScoreObjectiveCriteria.EnumRenderType e() {
      return this.d;
   }

   public void a(IScoreObjectiveCriteria.EnumRenderType var1) {
      this.d = var1;
      this.a.b(this);
   }
}
