package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandSetDefaultSpawnpoint extends CommandBase {
   @Override
   public String c() {
      return "setworldspawn";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.setworldspawn.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      BlockPos var3;
      if (var2.length == 0) {
         var3 = b(var1).c();
      } else {
         if (var2.length != 3 || var1.e() == null) {
            throw new WrongUsageException("commands.setworldspawn.usage");
         }

         var3 = a(var1, var2, 0, true);
      }

      var1.e().B(var3);
      MinecraftServer.N().ap().a(new S05PacketSpawnPosition(var3));
      a(var1, this, "commands.setworldspawn.success", new Object[]{var3.n(), var3.o(), var3.p()});
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length > 0 && var2.length <= 3 ? a(var2, 0, var3) : null;
   }
}
