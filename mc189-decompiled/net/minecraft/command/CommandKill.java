package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandKill extends CommandBase {
   @Override
   public String c() {
      return "kill";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.kill.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length == 0) {
         EntityPlayerMP var4 = b(var1);
         var4.G();
         a(var1, this, "commands.kill.successful", new Object[]{var4.f_()});
      } else {
         Entity var3 = b(var1, var2[0]);
         var3.G();
         a(var1, this, "commands.kill.successful", new Object[]{var3.f_()});
      }
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? a(var2, MinecraftServer.N().K()) : null;
   }
}
