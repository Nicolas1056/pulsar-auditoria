package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class CommandResultStats {
   private static final int a = CommandResultStats.Type.values().length;
   private static final String[] b = new String[a];
   private String[] c = b;
   private String[] d = b;

   public void a(final ICommandSender var1, CommandResultStats.Type var2, int var3) {
      String var4 = this.c[var2.a()];
      if (var4 != null) {
         ICommandSender var5 = new ICommandSender() {
            @Override
            public String e_() {
               return var1.e_();
            }

            @Override
            public IChatComponent f_() {
               return var1.f_();
            }

            @Override
            public void a(IChatComponent var1x) {
               var1.a(var1x);
            }

            @Override
            public boolean a(int var1x, String var2x) {
               return true;
            }

            @Override
            public BlockPos c() {
               return var1.c();
            }

            @Override
            public Vec3 d() {
               return var1.d();
            }

            @Override
            public World e() {
               return var1.e();
            }

            @Override
            public Entity f() {
               return var1.f();
            }

            @Override
            public boolean u_() {
               return var1.u_();
            }

            @Override
            public void a(CommandResultStats.Type var1x, int var2x) {
               var1.a(var1x, var2x);
            }
         };

         String var6;
         try {
            var6 = CommandBase.e(var5, var4);
         } catch (EntityNotFoundException var11) {
            return;
         }

         String var7 = this.d[var2.a()];
         if (var7 != null) {
            Scoreboard var8 = var1.e().Z();
            ScoreObjective var9 = var8.b(var7);
            if (var9 != null) {
               if (var8.b(var6, var9)) {
                  Score var10 = var8.c(var6, var9);
                  var10.c(var3);
               }
            }
         }
      }
   }

   public void a(NBTTagCompound var1) {
      if (var1.b("CommandStats", 10)) {
         NBTTagCompound var2 = var1.m("CommandStats");

         for (CommandResultStats.Type var6 : CommandResultStats.Type.values()) {
            String var7 = var6.b() + "Name";
            String var8 = var6.b() + "Objective";
            if (var2.b(var7, 8) && var2.b(var8, 8)) {
               String var9 = var2.j(var7);
               String var10 = var2.j(var8);
               a(this, var6, var9, var10);
            }
         }
      }
   }

   public void b(NBTTagCompound var1) {
      NBTTagCompound var2 = new NBTTagCompound();

      for (CommandResultStats.Type var6 : CommandResultStats.Type.values()) {
         String var7 = this.c[var6.a()];
         String var8 = this.d[var6.a()];
         if (var7 != null && var8 != null) {
            var2.a(var6.b() + "Name", var7);
            var2.a(var6.b() + "Objective", var8);
         }
      }

      if (!var2.c_()) {
         var1.a("CommandStats", var2);
      }
   }

   public static void a(CommandResultStats var0, CommandResultStats.Type var1, String var2, String var3) {
      if (var2 != null && var2.length() != 0 && var3 != null && var3.length() != 0) {
         if (var0.c == b || var0.d == b) {
            var0.c = new String[a];
            var0.d = new String[a];
         }

         var0.c[var1.a()] = var2;
         var0.d[var1.a()] = var3;
      } else {
         a(var0, var1);
      }
   }

   private static void a(CommandResultStats var0, CommandResultStats.Type var1) {
      if (var0.c != b && var0.d != b) {
         var0.c[var1.a()] = null;
         var0.d[var1.a()] = null;
         boolean var2 = true;

         for (CommandResultStats.Type var6 : CommandResultStats.Type.values()) {
            if (var0.c[var6.a()] != null && var0.d[var6.a()] != null) {
               var2 = false;
               break;
            }
         }

         if (var2) {
            var0.c = b;
            var0.d = b;
         }
      }
   }

   public void a(CommandResultStats var1) {
      for (CommandResultStats.Type var5 : CommandResultStats.Type.values()) {
         a(this, var5, var1.c[var5.a()], var1.d[var5.a()]);
      }
   }

   public static enum Type {
      a(0, "SuccessCount"),
      b(1, "AffectedBlocks"),
      c(2, "AffectedEntities"),
      d(3, "AffectedItems"),
      e(4, "QueryResult");

      final int f;
      final String g;

      private Type(int var3, String var4) {
         this.f = var3;
         this.g = var4;
      }

      public int a() {
         return this.f;
      }

      public String b() {
         return this.g;
      }

      public static String[] c() {
         String[] var0 = new String[values().length];
         int var1 = 0;

         for (CommandResultStats.Type var5 : values()) {
            var0[var1++] = var5.b();
         }

         return var0;
      }

      public static CommandResultStats.Type a(String var0) {
         for (CommandResultStats.Type var4 : values()) {
            if (var4.b().equals(var0)) {
               return var4;
            }
         }

         return null;
      }
   }
}
