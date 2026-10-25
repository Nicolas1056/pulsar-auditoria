package net.minecraft.command;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class CommandSpreadPlayers extends CommandBase {
   @Override
   public String c() {
      return "spreadplayers";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.spreadplayers.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 6) {
         throw new WrongUsageException("commands.spreadplayers.usage");
      } else {
         int var3 = 0;
         BlockPos var4 = var1.c();
         double var5 = b(var4.n(), var2[var3++], true);
         double var7 = b(var4.p(), var2[var3++], true);
         double var9 = a(var2[var3++], 0.0);
         double var11 = a(var2[var3++], var9 + 1.0);
         boolean var13 = d(var2[var3++]);
         ArrayList var14 = Lists.newArrayList();

         while (var3 < var2.length) {
            String var15 = var2[var3++];
            if (PlayerSelector.b(var15)) {
               List var16 = PlayerSelector.b(var1, var15, Entity.class);
               if (var16.size() == 0) {
                  throw new EntityNotFoundException();
               }

               var14.addAll(var16);
            } else {
               EntityPlayerMP var22 = MinecraftServer.N().ap().a(var15);
               if (var22 == null) {
                  throw new PlayerNotFoundException();
               }

               var14.add(var22);
            }
         }

         var1.a(CommandResultStats.Type.c, var14.size());
         if (var14.isEmpty()) {
            throw new EntityNotFoundException();
         } else {
            var1.a(new ChatComponentTranslation("commands.spreadplayers.spreading." + (var13 ? "teams" : "players"), var14.size(), var11, var5, var7, var9));
            this.a(var1, var14, new CommandSpreadPlayers.Position(var5, var7), var9, var11, ((Entity)var14.get(0)).o, var13);
         }
      }
   }

   private void a(ICommandSender var1, List<Entity> var2, CommandSpreadPlayers.Position var3, double var4, double var6, World var8, boolean var9) throws CommandException {
      Random var10 = new Random();
      double var11 = var3.a - var6;
      double var13 = var3.b - var6;
      double var15 = var3.a + var6;
      double var17 = var3.b + var6;
      CommandSpreadPlayers.Position[] var19 = this.a(var10, var9 ? this.b(var2) : var2.size(), var11, var13, var15, var17);
      int var20 = this.a(var3, var4, var8, var10, var11, var13, var15, var17, var19, var9);
      double var21 = this.a(var2, var8, var19, var9);
      a(var1, this, "commands.spreadplayers.success." + (var9 ? "teams" : "players"), new Object[]{var19.length, var3.a, var3.b});
      if (var19.length > 1) {
         var1.a(new ChatComponentTranslation("commands.spreadplayers.info." + (var9 ? "teams" : "players"), String.format("%.2f", var21), var20));
      }
   }

   private int b(List<Entity> var1) {
      HashSet var2 = Sets.newHashSet();

      for (Entity var4 : var1) {
         if (var4 instanceof EntityPlayer) {
            var2.add(((EntityPlayer)var4).bO());
         } else {
            var2.add(null);
         }
      }

      return var2.size();
   }

   private int a(
      CommandSpreadPlayers.Position var1,
      double var2,
      World var4,
      Random var5,
      double var6,
      double var8,
      double var10,
      double var12,
      CommandSpreadPlayers.Position[] var14,
      boolean var15
   ) throws CommandException {
      boolean var16 = true;
      double var18 = Float.MAX_VALUE;

      int var17;
      for (var17 = 0; var17 < 10000 && var16; var17++) {
         var16 = false;
         var18 = Float.MAX_VALUE;

         for (int var20 = 0; var20 < var14.length; var20++) {
            CommandSpreadPlayers.Position var21 = var14[var20];
            int var22 = 0;
            CommandSpreadPlayers.Position var23 = new CommandSpreadPlayers.Position();

            for (int var24 = 0; var24 < var14.length; var24++) {
               if (var20 != var24) {
                  CommandSpreadPlayers.Position var25 = var14[var24];
                  double var26 = var21.a(var25);
                  var18 = Math.min(var26, var18);
                  if (var26 < var2) {
                     var22++;
                     var23.a = var23.a + (var25.a - var21.a);
                     var23.b = var23.b + (var25.b - var21.b);
                  }
               }
            }

            if (var22 > 0) {
               var23.a /= var22;
               var23.b /= var22;
               double var32 = var23.b();
               if (var32 > 0.0) {
                  var23.a();
                  var21.b(var23);
               } else {
                  var21.a(var5, var6, var8, var10, var12);
               }

               var16 = true;
            }

            if (var21.a(var6, var8, var10, var12)) {
               var16 = true;
            }
         }

         if (!var16) {
            for (CommandSpreadPlayers.Position var31 : var14) {
               if (!var31.b(var4)) {
                  var31.a(var5, var6, var8, var10, var12);
                  var16 = true;
               }
            }
         }
      }

      if (var17 >= 10000) {
         throw new CommandException(
            "commands.spreadplayers.failure." + (var15 ? "teams" : "players"), var14.length, var1.a, var1.b, String.format("%.2f", var18)
         );
      } else {
         return var17;
      }
   }

   private double a(List<Entity> var1, World var2, CommandSpreadPlayers.Position[] var3, boolean var4) {
      double var5 = 0.0;
      int var7 = 0;
      HashMap var8 = Maps.newHashMap();

      for (int var9 = 0; var9 < var1.size(); var9++) {
         Entity var10 = (Entity)var1.get(var9);
         CommandSpreadPlayers.Position var11;
         if (var4) {
            Team var12 = var10 instanceof EntityPlayer ? ((EntityPlayer)var10).bO() : null;
            if (!var8.containsKey(var12)) {
               var8.put(var12, var3[var7++]);
            }

            var11 = (CommandSpreadPlayers.Position)var8.get(var12);
         } else {
            var11 = var3[var7++];
         }

         var10.a((double)(MathHelper.c(var11.a) + 0.5F), (double)var11.a(var2), MathHelper.c(var11.b) + 0.5);
         double var17 = Double.MAX_VALUE;

         for (int var14 = 0; var14 < var3.length; var14++) {
            if (var11 != var3[var14]) {
               double var15 = var11.a(var3[var14]);
               var17 = Math.min(var15, var17);
            }
         }

         var5 += var17;
      }

      return var5 / var1.size();
   }

   private CommandSpreadPlayers.Position[] a(Random var1, int var2, double var3, double var5, double var7, double var9) {
      CommandSpreadPlayers.Position[] var11 = new CommandSpreadPlayers.Position[var2];

      for (int var12 = 0; var12 < var11.length; var12++) {
         CommandSpreadPlayers.Position var13 = new CommandSpreadPlayers.Position();
         var13.a(var1, var3, var5, var7, var9);
         var11[var12] = var13;
      }

      return var11;
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length >= 1 && var2.length <= 2 ? b(var2, 0, var3) : null;
   }

   static class Position {
      double a;
      double b;

      Position() {
      }

      Position(double var1, double var3) {
         this.a = var1;
         this.b = var3;
      }

      double a(CommandSpreadPlayers.Position var1) {
         double var2 = this.a - var1.a;
         double var4 = this.b - var1.b;
         return Math.sqrt(var2 * var2 + var4 * var4);
      }

      void a() {
         double var1 = this.b();
         this.a /= var1;
         this.b /= var1;
      }

      float b() {
         return MathHelper.a(this.a * this.a + this.b * this.b);
      }

      public void b(CommandSpreadPlayers.Position var1) {
         this.a = this.a - var1.a;
         this.b = this.b - var1.b;
      }

      public boolean a(double var1, double var3, double var5, double var7) {
         boolean var9 = false;
         if (this.a < var1) {
            this.a = var1;
            var9 = true;
         } else if (this.a > var5) {
            this.a = var5;
            var9 = true;
         }

         if (this.b < var3) {
            this.b = var3;
            var9 = true;
         } else if (this.b > var7) {
            this.b = var7;
            var9 = true;
         }

         return var9;
      }

      public int a(World var1) {
         BlockPos var2 = new BlockPos(this.a, 256.0, this.b);

         while (var2.o() > 0) {
            var2 = var2.b();
            if (var1.p(var2).c().t() != Material.a) {
               return var2.o() + 1;
            }
         }

         return 257;
      }

      public boolean b(World var1) {
         BlockPos var2 = new BlockPos(this.a, 256.0, this.b);

         while (var2.o() > 0) {
            var2 = var2.b();
            Material var3 = var1.p(var2).c().t();
            if (var3 != Material.a) {
               return !var3.d() && var3 != Material.o;
            }
         }

         return false;
      }

      public void a(Random var1, double var2, double var4, double var6, double var8) {
         this.a = MathHelper.a(var1, var2, var6);
         this.b = MathHelper.a(var1, var4, var8);
      }
   }
}
