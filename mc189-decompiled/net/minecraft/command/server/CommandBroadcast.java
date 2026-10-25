package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandBroadcast extends CommandBase {
   @Override
   public String c() {
      return "say";
   }

   @Override
   public int a() {
      return 1;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.say.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length > 0 && var2[0].length() > 0) {
         IChatComponent var3 = b(var1, var2, 0, true);
         MinecraftServer.N().ap().a(new ChatComponentTranslation("chat.type.announcement", var1.f_(), var3));
      } else {
         throw new WrongUsageException("commands.say.usage");
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length >= 1 ? a(var2, MinecraftServer.N().K()) : null;
   }
}
