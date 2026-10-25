package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.MathHelper;
import net.minecraft.world.border.WorldBorder;

public class CommandWorldBorder extends CommandBase {
   @Override
   public String c() {
      return "worldborder";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.worldborder.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 1) {
         throw new WrongUsageException("commands.worldborder.usage");
      } else {
         WorldBorder var3 = this.d();
         if (var2[0].equals("set")) {
            if (var2.length != 2 && var2.length != 3) {
               throw new WrongUsageException("commands.worldborder.set.usage");
            }

            double var4 = var3.j();
            double var6 = a(var2[1], 1.0, 6.0E7);
            long var8 = var2.length > 2 ? a(var2[2], 0L, 9223372036854775L) * 1000L : 0L;
            if (var8 > 0L) {
               var3.a(var4, var6, var8);
               if (var4 > var6) {
                  a(
                     var1,
                     this,
                     "commands.worldborder.setSlowly.shrink.success",
                     new Object[]{String.format("%.1f", var6), String.format("%.1f", var4), Long.toString(var8 / 1000L)}
                  );
               } else {
                  a(
                     var1,
                     this,
                     "commands.worldborder.setSlowly.grow.success",
                     new Object[]{String.format("%.1f", var6), String.format("%.1f", var4), Long.toString(var8 / 1000L)}
                  );
               }
            } else {
               var3.a(var6);
               a(var1, this, "commands.worldborder.set.success", new Object[]{String.format("%.1f", var6), String.format("%.1f", var4)});
            }
         } else if (var2[0].equals("add")) {
            if (var2.length != 2 && var2.length != 3) {
               throw new WrongUsageException("commands.worldborder.add.usage");
            }

            double var10 = var3.h();
            double var18 = var10 + a(var2[1], -var10, 6.0E7 - var10);
            long var21 = var3.i() + (var2.length > 2 ? a(var2[2], 0L, 9223372036854775L) * 1000L : 0L);
            if (var21 > 0L) {
               var3.a(var10, var18, var21);
               if (var10 > var18) {
                  a(
                     var1,
                     this,
                     "commands.worldborder.setSlowly.shrink.success",
                     new Object[]{String.format("%.1f", var18), String.format("%.1f", var10), Long.toString(var21 / 1000L)}
                  );
               } else {
                  a(
                     var1,
                     this,
                     "commands.worldborder.setSlowly.grow.success",
                     new Object[]{String.format("%.1f", var18), String.format("%.1f", var10), Long.toString(var21 / 1000L)}
                  );
               }
            } else {
               var3.a(var18);
               a(var1, this, "commands.worldborder.set.success", new Object[]{String.format("%.1f", var18), String.format("%.1f", var10)});
            }
         } else if (var2[0].equals("center")) {
            if (var2.length != 3) {
               throw new WrongUsageException("commands.worldborder.center.usage");
            }

            BlockPos var11 = var1.c();
            double var5 = b(var11.n() + 0.5, var2[1], true);
            double var7 = b(var11.p() + 0.5, var2[2], true);
            var3.c(var5, var7);
            a(var1, this, "commands.worldborder.center.success", new Object[]{var5, var7});
         } else if (var2[0].equals("damage")) {
            if (var2.length < 2) {
               throw new WrongUsageException("commands.worldborder.damage.usage");
            }

            if (var2[1].equals("buffer")) {
               if (var2.length != 3) {
                  throw new WrongUsageException("commands.worldborder.damage.buffer.usage");
               }

               double var12 = a(var2[2], 0.0);
               double var19 = var3.m();
               var3.b(var12);
               a(var1, this, "commands.worldborder.damage.buffer.success", new Object[]{String.format("%.1f", var12), String.format("%.1f", var19)});
            } else if (var2[1].equals("amount")) {
               if (var2.length != 3) {
                  throw new WrongUsageException("commands.worldborder.damage.amount.usage");
               }

               double var13 = a(var2[2], 0.0);
               double var20 = var3.n();
               var3.c(var13);
               a(var1, this, "commands.worldborder.damage.amount.success", new Object[]{String.format("%.2f", var13), String.format("%.2f", var20)});
            }
         } else if (var2[0].equals("warning")) {
            if (var2.length < 2) {
               throw new WrongUsageException("commands.worldborder.warning.usage");
            }

            int var14 = a(var2[2], 0);
            if (var2[1].equals("time")) {
               if (var2.length != 3) {
                  throw new WrongUsageException("commands.worldborder.warning.time.usage");
               }

               int var16 = var3.p();
               var3.b(var14);
               a(var1, this, "commands.worldborder.warning.time.success", new Object[]{var14, var16});
            } else if (var2[1].equals("distance")) {
               if (var2.length != 3) {
                  throw new WrongUsageException("commands.worldborder.warning.distance.usage");
               }

               int var17 = var3.q();
               var3.c(var14);
               a(var1, this, "commands.worldborder.warning.distance.success", new Object[]{var14, var17});
            }
         } else {
            if (!var2[0].equals("get")) {
               throw new WrongUsageException("commands.worldborder.usage");
            }

            double var15 = var3.h();
            var1.a(CommandResultStats.Type.e, MathHelper.c(var15 + 0.5));
            var1.a(new ChatComponentTranslation("commands.worldborder.get.success", String.format("%.0f", var15)));
         }
      }
   }

   protected WorldBorder d() {
      return MinecraftServer.N().d[0].af();
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, new String[]{"set", "center", "damage", "warning", "add", "get"});
      } else if (var2.length == 2 && var2[0].equals("damage")) {
         return a(var2, new String[]{"buffer", "amount"});
      } else if (var2.length >= 2 && var2.length <= 3 && var2[0].equals("center")) {
         return b(var2, 1, var3);
      } else {
         return var2.length == 2 && var2[0].equals("warning") ? a(var2, new String[]{"time", "distance"}) : null;
      }
   }
}
