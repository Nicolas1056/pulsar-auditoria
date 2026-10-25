package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldSettings;

public class CommandPublishLocalServer extends CommandBase {
   @Override
   public String c() {
      return "publish";
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.publish.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      String var3 = MinecraftServer.N().a(WorldSettings.GameType.b, false);
      if (var3 != null) {
         a(var1, this, "commands.publish.started", new Object[]{var3});
      } else {
         a(var1, this, "commands.publish.failed", new Object[0]);
      }
   }
}
