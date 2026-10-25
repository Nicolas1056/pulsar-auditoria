package net.minecraft.command.server;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.IPBanEntry;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;

public class CommandBanIp extends CommandBase {
   public static final Pattern a = Pattern.compile(
      "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
   );

   @Override
   public String c() {
      return "ban-ip";
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
      return "commands.banip.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length >= 1 && var2[0].length() > 1) {
         IChatComponent var3 = var2.length >= 2 ? a(var1, var2, 1) : null;
         Matcher var4 = a.matcher(var2[0]);
         if (var4.matches()) {
            this.a(var1, var2[0], var3 == null ? null : var3.c());
         } else {
            EntityPlayerMP var5 = MinecraftServer.N().ap().a(var2[0]);
            if (var5 == null) {
               throw new PlayerNotFoundException("commands.banip.invalid");
            }

            this.a(var1, var5.w(), var3 == null ? null : var3.c());
         }
      } else {
         throw new WrongUsageException("commands.banip.usage");
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? a(var2, MinecraftServer.N().K()) : null;
   }

   protected void a(ICommandSender var1, String var2, String var3) {
      IPBanEntry var4 = new IPBanEntry(var2, null, var1.e_(), null, var3);
      MinecraftServer.N().ap().i().a(var4);
      List var5 = MinecraftServer.N().ap().b(var2);
      String[] var6 = new String[var5.size()];
      int var7 = 0;

      for (EntityPlayerMP var9 : var5) {
         var9.a.c("You have been IP banned.");
         var6[var7++] = var9.e_();
      }

      if (var5.isEmpty()) {
         a(var1, this, "commands.banip.success", new Object[]{var2});
      } else {
         a(var1, this, "commands.banip.success.players", new Object[]{var2, a(var6)});
      }
   }
}
