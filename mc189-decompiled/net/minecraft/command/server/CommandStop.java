package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public class CommandStop extends CommandBase {
   @Override
   public String c() {
      return "stop";
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.stop.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (MinecraftServer.N().d != null) {
         a(var1, this, "commands.stop.start", new Object[0]);
      }

      MinecraftServer.N().w();
   }
}
