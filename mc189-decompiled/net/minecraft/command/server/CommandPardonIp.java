package net.minecraft.command.server;

import java.util.List;
import java.util.regex.Matcher;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.SyntaxErrorException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandPardonIp extends CommandBase {
   @Override
   public String c() {
      return "pardon-ip";
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public boolean a(ICommandSender var1) {
      return MinecraftServer.N().ap().i().b() && super.a(var1);
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.unbanip.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length == 1 && var2[0].length() > 1) {
         Matcher var3 = CommandBanIp.a.matcher(var2[0]);
         if (var3.matches()) {
            MinecraftServer.N().ap().i().c(var2[0]);
            a(var1, this, "commands.unbanip.success", new Object[]{var2[0]});
         } else {
            throw new SyntaxErrorException("commands.unbanip.invalid");
         }
      } else {
         throw new WrongUsageException("commands.unbanip.usage");
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? a(var2, MinecraftServer.N().ap().i().a()) : null;
   }
}
