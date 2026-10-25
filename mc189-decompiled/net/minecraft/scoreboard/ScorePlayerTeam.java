package net.minecraft.scoreboard;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.Set;
import net.minecraft.util.EnumChatFormatting;

public class ScorePlayerTeam extends Team {
   private final Scoreboard a;
   private final String b;
   private final Set<String> c = Sets.newHashSet();
   private String d;
   private String e = "";
   private String f = "";
   private boolean g = true;
   private boolean h = true;
   private Team.EnumVisible i = Team.EnumVisible.a;
   private Team.EnumVisible j = Team.EnumVisible.a;
   private EnumChatFormatting k = EnumChatFormatting.v;

   public ScorePlayerTeam(Scoreboard var1, String var2) {
      this.a = var1;
      this.b = var2;
      this.d = var2;
   }

   @Override
   public String b() {
      return this.b;
   }

   public String c() {
      return this.d;
   }

   public void a(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Name cannot be null");
      } else {
         this.d = var1;
         this.a.b(this);
      }
   }

   @Override
   public Collection<String> d() {
      return this.c;
   }

   public String e() {
      return this.e;
   }

   public void b(String var1) {
      if (var1 == null) {
         throw new IllegalArgumentException("Prefix cannot be null");
      } else {
         this.e = var1;
         this.a.b(this);
      }
   }

   public String f() {
      return this.f;
   }

   public void c(String var1) {
      this.f = var1;
      this.a.b(this);
   }

   @Override
   public String d(String var1) {
      return this.e() + var1 + this.f();
   }

   public static String a(Team var0, String var1) {
      return var0 == null ? var1 : var0.d(var1);
   }

   @Override
   public boolean g() {
      return this.g;
   }

   public void a(boolean var1) {
      this.g = var1;
      this.a.b(this);
   }

   @Override
   public boolean h() {
      return this.h;
   }

   public void b(boolean var1) {
      this.h = var1;
      this.a.b(this);
   }

   @Override
   public Team.EnumVisible i() {
      return this.i;
   }

   @Override
   public Team.EnumVisible j() {
      return this.j;
   }

   public void a(Team.EnumVisible var1) {
      this.i = var1;
      this.a.b(this);
   }

   public void b(Team.EnumVisible var1) {
      this.j = var1;
      this.a.b(this);
   }

   public int k() {
      byte var1 = 0;
      if (this.g()) {
         var1 |= 1;
      }

      if (this.h()) {
         var1 |= 2;
      }

      return var1;
   }

   public void a(int var1) {
      this.a((var1 & 1) > 0);
      this.b((var1 & 2) > 0);
   }

   public void a(EnumChatFormatting var1) {
      this.k = var1;
   }

   public EnumChatFormatting l() {
      return this.k;
   }
}
