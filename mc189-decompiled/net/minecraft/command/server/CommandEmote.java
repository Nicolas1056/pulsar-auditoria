package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IChatComponent;

public class CommandEmote extends CommandBase {
   @Override
   public String c() {
      return "me";
   }

   @Override
   public int a() {
      return 0;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.me.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length <= 0) {
         throw new WrongUsageException("commands.me.usage");
      } else {
         IChatComponent var3 = b(var1, var2, 0, !(var1 instanceof EntityPlayer));
         MinecraftServer.N().ap().a(new ChatComponentTranslation("chat.type.emote", var1.f_(), var3));
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return a(var2, MinecraftServer.N().K());
   }
}
