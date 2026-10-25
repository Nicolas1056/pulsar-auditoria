package net.minecraft.command;

import net.minecraft.server.MinecraftServer;

public class CommandSetPlayerTimeout extends CommandBase {
   @Override
   public String c() {
      return "setidletimeout";
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.setidletimeout.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length != 1) {
         throw new WrongUsageException("commands.setidletimeout.usage");
      } else {
         int var3 = a(var2[0], 0);
         MinecraftServer.N().d(var3);
         a(var1, this, "commands.setidletimeout.success", new Object[]{var3});
      }
   }
}
