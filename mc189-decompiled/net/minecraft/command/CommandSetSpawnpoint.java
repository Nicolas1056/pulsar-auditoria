package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandSetSpawnpoint extends CommandBase {
   @Override
   public String c() {
      return "spawnpoint";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.spawnpoint.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length > 1 && var2.length < 4) {
         throw new WrongUsageException("commands.spawnpoint.usage");
      } else {
         EntityPlayerMP var3 = var2.length > 0 ? a(var1, var2[0]) : b(var1);
         BlockPos var4 = var2.length > 3 ? a(var1, var2, 1, true) : var3.c();
         if (var3.o != null) {
            var3.a(var4, true);
            a(var1, this, "commands.spawnpoint.success", new Object[]{var3.e_(), var4.n(), var4.o(), var4.p()});
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, MinecraftServer.N().K());
      } else {
         return var2.length > 1 && var2.length <= 4 ? a(var2, 1, var3) : null;
      }
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }
}
