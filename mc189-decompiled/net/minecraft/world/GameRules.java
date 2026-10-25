package net.minecraft.world;

import java.util.Set;
import java.util.TreeMap;
import net.minecraft.nbt.NBTTagCompound;

public class GameRules {
   private TreeMap<String, GameRules.Value> a = new TreeMap<>();

   public GameRules() {
      this.a("doFireTick", "true", GameRules.ValueType.b);
      this.a("mobGriefing", "true", GameRules.ValueType.b);
      this.a("keepInventory", "false", GameRules.ValueType.b);
      this.a("doMobSpawning", "true", GameRules.ValueType.b);
      this.a("doMobLoot", "true", GameRules.ValueType.b);
      this.a("doTileDrops", "true", GameRules.ValueType.b);
      this.a("doEntityDrops", "true", GameRules.ValueType.b);
      this.a("commandBlockOutput", "true", GameRules.ValueType.b);
      this.a("naturalRegeneration", "true", GameRules.ValueType.b);
      this.a("doDaylightCycle", "true", GameRules.ValueType.b);
      this.a("logAdminCommands", "true", GameRules.ValueType.b);
      this.a("showDeathMessages", "true", GameRules.ValueType.b);
      this.a("randomTickSpeed", "3", GameRules.ValueType.c);
      this.a("sendCommandFeedback", "true", GameRules.ValueType.b);
      this.a("reducedDebugInfo", "false", GameRules.ValueType.b);
   }

   public void a(String var1, String var2, GameRules.ValueType var3) {
      this.a.put(var1, new GameRules.Value(var2, var3));
   }

   public void a(String var1, String var2) {
      GameRules.Value var3 = this.a.get(var1);
      if (var3 != null) {
         var3.a(var2);
      } else {
         this.a(var1, var2, GameRules.ValueType.a);
      }
   }

   public String a(String var1) {
      GameRules.Value var2 = this.a.get(var1);
      return var2 != null ? var2.a() : "";
   }

   public boolean b(String var1) {
      GameRules.Value var2 = this.a.get(var1);
      return var2 != null ? var2.b() : false;
   }

   public int c(String var1) {
      GameRules.Value var2 = this.a.get(var1);
      return var2 != null ? var2.c() : 0;
   }

   public NBTTagCompound a() {
      NBTTagCompound var1 = new NBTTagCompound();

      for (String var3 : this.a.keySet()) {
         GameRules.Value var4 = this.a.get(var3);
         var1.a(var3, var4.a());
      }

      return var1;
   }

   public void a(NBTTagCompound var1) {
      for (String var4 : var1.c()) {
         String var6 = var1.j(var4);
         this.a(var4, var6);
      }
   }

   public String[] b() {
      Set var1 = this.a.keySet();
      return var1.toArray(new String[var1.size()]);
   }

   public boolean e(String var1) {
      return this.a.containsKey(var1);
   }

   public boolean a(String var1, GameRules.ValueType var2) {
      GameRules.Value var3 = this.a.get(var1);
      return var3 != null && (var3.e() == var2 || var2 == GameRules.ValueType.a);
   }

   static class Value {
      private String a;
      private boolean b;
      private int c;
      private double d;
      private final GameRules.ValueType e;

      public Value(String var1, GameRules.ValueType var2) {
         this.e = var2;
         this.a(var1);
      }

      public void a(String var1) {
         this.a = var1;
         this.b = Boolean.parseBoolean(var1);
         this.c = this.b ? 1 : 0;

         try {
            this.c = Integer.parseInt(var1);
         } catch (NumberFormatException var4) {
         }

         try {
            this.d = Double.parseDouble(var1);
         } catch (NumberFormatException var3) {
         }
      }

      public String a() {
         return this.a;
      }

      public boolean b() {
         return this.b;
      }

      public int c() {
         return this.c;
      }

      public GameRules.ValueType e() {
         return this.e;
      }
   }

   public static enum ValueType {
      a,
      b,
      c;
   }
}
