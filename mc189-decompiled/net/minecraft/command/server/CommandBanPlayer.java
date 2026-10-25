package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.util.BlockPos;

public class CommandBanPlayer extends CommandBase {
   @Override
   public String c() {
      return "ban";
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.ban.usage";
   }

   @Override
   public boolean a(ICommandSender var1) {
      return MinecraftServer.N().ap().h().b() && super.a(var1);
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length >= 1 && var2[0].length() > 0) {
         MinecraftServer var3 = MinecraftServer.N();
         GameProfile var4 = var3.aF().a(var2[0]);
         if (var4 == null) {
            throw new CommandException("commands.ban.failed", var2[0]);
         } else {
            String var5 = null;
            if (var2.length >= 2) {
               var5 = a(var1, var2, 1).c();
            }

            UserListBansEntry var6 = new UserListBansEntry(var4, null, var1.e_(), null, var5);
            var3.ap().h().a(var6);
            EntityPlayerMP var7 = var3.ap().a(var2[0]);
            if (var7 != null) {
               var7.a.c("You are banned from this server.");
            }

            a(var1, this, "commands.ban.success", new Object[]{var2[0]});
         }
      } else {
         throw new WrongUsageException("commands.ban.usage");
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length >= 1 ? a(var2, MinecraftServer.N().K()) : null;
   }
}
