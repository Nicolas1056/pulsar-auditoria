package net.minecraft.scoreboard;

import com.google.common.collect.Maps;
import java.util.Collection;
import java.util.Map;

public abstract class Team {
   public boolean a(Team var1) {
      return var1 == null ? false : this == var1;
   }

   public abstract String b();

   public abstract String d(String var1);

   public abstract boolean h();

   public abstract boolean g();

   public abstract Team.EnumVisible i();

   public abstract Collection<String> d();

   public abstract Team.EnumVisible j();

   public static enum EnumVisible {
      a("always", 0),
      b("never", 1),
      c("hideForOtherTeams", 2),
      d("hideForOwnTeam", 3);

      private static Map<String, Team.EnumVisible> g = Maps.newHashMap();
      public final String e;
      public final int f;

      public static String[] a() {
         return g.keySet().toArray(new String[g.size()]);
      }

      public static Team.EnumVisible a(String var0) {
         return g.get(var0);
      }

      private EnumVisible(String var3, int var4) {
         this.e = var3;
         this.f = var4;
      }

      static {
         for (Team.EnumVisible var3 : values()) {
            g.put(var3.e, var3);
         }
      }
   }
}
