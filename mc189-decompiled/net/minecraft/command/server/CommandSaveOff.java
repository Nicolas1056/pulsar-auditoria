package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class CommandSaveOff extends CommandBase {
   @Override
   public String c() {
      return "save-off";
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.save-off.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      MinecraftServer var3 = MinecraftServer.N();
      boolean var4 = false;

      for (int var5 = 0; var5 < var3.d.length; var5++) {
         if (var3.d[var5] != null) {
            WorldServer var6 = var3.d[var5];
            if (!var6.c) {
               var6.c = true;
               var4 = true;
            }
         }
      }

      if (var4) {
         a(var1, this, "commands.save.disabled", new Object[0]);
      } else {
         throw new CommandException("commands.save-off.alreadyOff");
      }
   }
}
