package net.minecraft.command;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.storage.WorldInfo;

public class CommandToggleDownfall extends CommandBase {
   @Override
   public String c() {
      return "toggledownfall";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.downfall.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      this.d();
      a(var1, this, "commands.downfall.success", new Object[0]);
   }

   protected void d() {
      WorldInfo var1 = MinecraftServer.N().d[0].P();
      var1.b(!var1.p());
   }
}
