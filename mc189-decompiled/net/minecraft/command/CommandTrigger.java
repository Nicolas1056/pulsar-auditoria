package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandTrigger extends CommandBase {
   @Override
   public String c() {
      return "trigger";
   }

   @Override
   public int a() {
      return 0;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.trigger.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 3) {
         throw new WrongUsageException("commands.trigger.usage");
      } else {
         EntityPlayerMP var3;
         if (var1 instanceof EntityPlayerMP) {
            var3 = (EntityPlayerMP)var1;
         } else {
            Entity var4 = var1.f();
            if (!(var4 instanceof EntityPlayerMP)) {
               throw new CommandException("commands.trigger.invalidPlayer");
            }

            var3 = (EntityPlayerMP)var4;
         }

         Scoreboard var8 = MinecraftServer.N().a(0).Z();
         ScoreObjective var5 = var8.b(var2[0]);
         if (var5 != null && var5.c() == IScoreObjectiveCriteria.c) {
            int var6 = a(var2[2]);
            if (!var8.b(var3.e_(), var5)) {
               throw new CommandException("commands.trigger.invalidObjective", var2[0]);
            } else {
               Score var7 = var8.c(var3.e_(), var5);
               if (var7.g()) {
                  throw new CommandException("commands.trigger.disabled", var2[0]);
               } else {
                  if ("set".equals(var2[1])) {
                     var7.c(var6);
                  } else {
                     if (!"add".equals(var2[1])) {
                        throw new CommandException("commands.trigger.invalidMode", var2[1]);
                     }

                     var7.a(var6);
                  }

                  var7.a(true);
                  if (var3.c.d()) {
                     a(var1, this, "commands.trigger.success", new Object[]{var2[0], var2[1], var2[2]});
                  }
               }
            }
         } else {
            throw new CommandException("commands.trigger.invalidObjective", var2[0]);
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         Scoreboard var4 = MinecraftServer.N().a(0).Z();
         ArrayList var5 = Lists.newArrayList();

         for (ScoreObjective var7 : var4.c()) {
            if (var7.c() == IScoreObjectiveCriteria.c) {
               var5.add(var7.b());
            }
         }

         return a(var2, var5.toArray(new String[var5.size()]));
      } else {
         return var2.length == 2 ? a(var2, new String[]{"add", "set"}) : null;
      }
   }
}
