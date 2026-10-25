package net.minecraft.command.server;

import com.mojang.authlib.GameProfile;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;

public class CommandWhitelist extends CommandBase {
   @Override
   public String c() {
      return "whitelist";
   }

   @Override
   public int a() {
      return 3;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.whitelist.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 1) {
         throw new WrongUsageException("commands.whitelist.usage");
      } else {
         MinecraftServer var3 = MinecraftServer.N();
         if (var2[0].equals("on")) {
            var3.ap().a(true);
            a(var1, this, "commands.whitelist.enabled", new Object[0]);
         } else if (var2[0].equals("off")) {
            var3.ap().a(false);
            a(var1, this, "commands.whitelist.disabled", new Object[0]);
         } else if (var2[0].equals("list")) {
            var1.a(new ChatComponentTranslation("commands.whitelist.list", var3.ap().l().length, var3.ap().q().length));
            String[] var4 = var3.ap().l();
            var1.a(new ChatComponentText(a(var4)));
         } else if (var2[0].equals("add")) {
            if (var2.length < 2) {
               throw new WrongUsageException("commands.whitelist.add.usage");
            }

            GameProfile var5 = var3.aF().a(var2[1]);
            if (var5 == null) {
               throw new CommandException("commands.whitelist.add.failed", var2[1]);
            }

            var3.ap().d(var5);
            a(var1, this, "commands.whitelist.add.success", new Object[]{var2[1]});
         } else if (var2[0].equals("remove")) {
            if (var2.length < 2) {
               throw new WrongUsageException("commands.whitelist.remove.usage");
            }

            GameProfile var6 = var3.ap().k().a(var2[1]);
            if (var6 == null) {
               throw new CommandException("commands.whitelist.remove.failed", var2[1]);
            }

            var3.ap().c(var6);
            a(var1, this, "commands.whitelist.remove.success", new Object[]{var2[1]});
         } else if (var2[0].equals("reload")) {
            var3.ap().a();
            a(var1, this, "commands.whitelist.reloaded", new Object[0]);
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, new String[]{"on", "off", "list", "add", "remove", "reload"});
      } else {
         if (var2.length == 2) {
            if (var2[0].equals("remove")) {
               return a(var2, MinecraftServer.N().ap().l());
            }

            if (var2[0].equals("add")) {
               return a(var2, MinecraftServer.N().aF().a());
            }
         }

         return null;
      }
   }
}
