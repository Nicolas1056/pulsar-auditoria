package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandServerKick extends CommandBase {
   @Override
   public String c() {
      return "kick";
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.kick.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length > 0 && var2[0].length() > 1) {
         EntityPlayerMP var3 = MinecraftServer.N().ap().a(var2[0]);
         String var4 = "Kicked by an operator.";
         boolean var5 = false;
         if (var3 == null) {
            throw new PlayerNotFoundException();
         } else {
            if (var2.length >= 2) {
               var4 = a(var1, var2, 1).c();
               var5 = true;
            }

            var3.a.c(var4);
            if (var5) {
               a(var1, this, "commands.kick.success.reason", new Object[]{var3.e_(), var4});
            } else {
               a(var1, this, "commands.kick.success", new Object[]{var3.e_()});
            }
         }
      } else {
         throw new WrongUsageException("commands.kick.usage");
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length >= 1 ? a(var2, MinecraftServer.N().K()) : null;
   }
}
