package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandPardonPlayer extends CommandBase {
   @Override
   public String c() {
      return "pardon";
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.unban.usage";
   }

   @Override
   public boolean a(ICommandSender var1) {
      return MinecraftServer.N().ap().h().b() && super.a(var1);
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length == 1 && var2[0].length() > 0) {
         MinecraftServer var3 = MinecraftServer.N();
         GameProfile var4 = var3.ap().h().a(var2[0]);
         if (var4 == null) {
            throw new CommandException("commands.unban.failed", var2[0]);
         } else {
            var3.ap().h().c(var4);
            a(var1, this, "commands.unban.success", new Object[]{var2[0]});
         }
      } else {
         throw new WrongUsageException("commands.unban.usage");
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? a(var2, MinecraftServer.N().ap().h().a()) : null;
   }
}
