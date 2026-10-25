package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandListBans extends CommandBase {
   @Override
   public String c() {
      return "banlist";
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public boolean a(ICommandSender var1) {
      return (MinecraftServer.N().ap().i().b() || MinecraftServer.N().ap().h().b()) && super.a(var1);
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.banlist.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length >= 1 && var2[0].equalsIgnoreCase("ips")) {
         var1.a(new ChatComponentTranslation("commands.banlist.ips", MinecraftServer.N().ap().i().a().length));
         var1.a(new ChatComponentText(a(MinecraftServer.N().ap().i().a())));
      } else {
         var1.a(new ChatComponentTranslation("commands.banlist.players", MinecraftServer.N().ap().h().a().length));
         var1.a(new ChatComponentText(a(MinecraftServer.N().ap().h().a())));
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? a(var2, new String[]{"players", "ips"}) : null;
   }
}
