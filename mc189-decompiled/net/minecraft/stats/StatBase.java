package net.minecraft.stats;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.event.HoverEvent;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IJsonSerializable;

public class StatBase {
   public final String e;
   private final IChatComponent a;
   public boolean f;
   private final IStatType b;
   private final IScoreObjectiveCriteria c;
   private Class<? extends IJsonSerializable> d;
   private static NumberFormat k = NumberFormat.getIntegerInstance(Locale.US);
   public static IStatType g = new IStatType() {
      @Override
      public String a(int var1) {
         return StatBase.k.format((long)var1);
      }
   };
   private static DecimalFormat l = new DecimalFormat("########0.00");
   public static IStatType h = new IStatType() {
      @Override
      public String a(int var1) {
         double var2 = var1 / 20.0;
         double var4 = var2 / 60.0;
         double var6 = var4 / 60.0;
         double var8 = var6 / 24.0;
         double var10 = var8 / 365.0;
         if (var10 > 0.5) {
            return StatBase.l.format(var10) + " y";
         } else if (var8 > 0.5) {
            return StatBase.l.format(var8) + " d";
         } else if (var6 > 0.5) {
            return StatBase.l.format(var6) + " h";
         } else {
            return var4 > 0.5 ? StatBase.l.format(var4) + " m" : var2 + " s";
         }
      }
   };
   public static IStatType i = new IStatType() {
      @Override
      public String a(int var1) {
         double var2 = var1 / 100.0;
         double var4 = var2 / 1000.0;
         if (var4 > 0.5) {
            return StatBase.l.format(var4) + " km";
         } else {
            return var2 > 0.5 ? StatBase.l.format(var2) + " m" : var1 + " cm";
         }
      }
   };
   public static IStatType j = new IStatType() {
      @Override
      public String a(int var1) {
         return StatBase.l.format(var1 * 0.1);
      }
   };

   public StatBase(String var1, IChatComponent var2, IStatType var3) {
      this.e = var1;
      this.a = var2;
      this.b = var3;
      this.c = new ObjectiveStat(this);
      IScoreObjectiveCriteria.a.put(this.c.a(), this.c);
   }

   public StatBase(String var1, IChatComponent var2) {
      this(var1, var2, g);
   }

   public StatBase i() {
      this.f = true;
      return this;
   }

   public StatBase h() {
      if (StatList.a.containsKey(this.e)) {
         throw new RuntimeException("Duplicate stat id: \"" + StatList.a.get(this.e).a + "\" and \"" + this.a + "\" at id " + this.e);
      } else {
         StatList.b.add(this);
         StatList.a.put(this.e, this);
         return this;
      }
   }

   public boolean d() {
      return false;
   }

   public String a(int var1) {
      return this.b.a(var1);
   }

   public IChatComponent e() {
      IChatComponent var1 = this.a.f();
      var1.b().a(EnumChatFormatting.h);
      var1.b().a(new HoverEvent(HoverEvent.Action.b, new ChatComponentText(this.e)));
      return var1;
   }

   public IChatComponent j() {
      IChatComponent var1 = this.e();
      IChatComponent var2 = new ChatComponentText("[").a(var1).a("]");
      var2.a(var1.b());
      return var2;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (var1 != null && this.getClass() == var1.getClass()) {
         StatBase var2 = (StatBase)var1;
         return this.e.equals(var2.e);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.e.hashCode();
   }

   @Override
   public String toString() {
      return "Stat{id=" + this.e + ", nameId=" + this.a + ", awardLocallyOnly=" + this.f + ", formatter=" + this.b + ", objectiveCriteria=" + this.c + '}';
   }

   public IScoreObjectiveCriteria k() {
      return this.c;
   }

   public Class<? extends IJsonSerializable> l() {
      return this.d;
   }

   public StatBase b(Class<? extends IJsonSerializable> var1) {
      this.d = var1;
      return this;
   }
}
