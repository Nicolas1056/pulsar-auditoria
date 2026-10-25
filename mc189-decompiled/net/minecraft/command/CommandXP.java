package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandXP extends CommandBase {
   @Override
   public String c() {
      return "xp";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.xp.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length <= 0) {
         throw new WrongUsageException("commands.xp.usage");
      } else {
         String var3 = var2[0];
         boolean var4 = var3.endsWith("l") || var3.endsWith("L");
         if (var4 && var3.length() > 1) {
            var3 = var3.substring(0, var3.length() - 1);
         }

         int var5 = a(var3);
         boolean var6 = var5 < 0;
         if (var6) {
            var5 *= -1;
         }

         EntityPlayerMP var7 = var2.length > 1 ? a(var1, var2[1]) : b(var1);
         if (var4) {
            var1.a(CommandResultStats.Type.e, var7.bB);
            if (var6) {
               var7.a(-var5);
               a(var1, this, "commands.xp.success.negative.levels", new Object[]{var5, var7.e_()});
            } else {
               var7.a(var5);
               a(var1, this, "commands.xp.success.levels", new Object[]{var5, var7.e_()});
            }
         } else {
            var1.a(CommandResultStats.Type.e, var7.bC);
            if (var6) {
               throw new CommandException("commands.xp.failure.widthdrawXp");
            }

            var7.u(var5);
            a(var1, this, "commands.xp.success", new Object[]{var5, var7.e_()});
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 2 ? a(var2, this.d()) : null;
   }

   protected String[] d() {
      return MinecraftServer.N().K();
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 1;
   }
}
