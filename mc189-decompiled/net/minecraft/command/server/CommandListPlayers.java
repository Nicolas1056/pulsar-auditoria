package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListPlayers extends CommandBase {
   @Override
   public String c() {
      return "list";
   }

   @Override
   public int a() {
      return 0;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.players.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      int var3 = MinecraftServer.N().I();
      var1.a(new ChatComponentTranslation("commands.players.list", var3, MinecraftServer.N().J()));
      var1.a(new ChatComponentText(MinecraftServer.N().ap().b(var2.length > 0 && "uuids".equalsIgnoreCase(var2[0]))));
      var1.a(CommandResultStats.Type.e, var3);
   }
}
