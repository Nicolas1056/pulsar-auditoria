package net.minecraft.command.server;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.MinecraftException;
import net.minecraft.world.WorldServer;

public class CommandSaveAll extends CommandBase {
   @Override
   public String c() {
      return "save-all";
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.save.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      MinecraftServer var3 = MinecraftServer.N();
      var1.a(new ChatComponentTranslation("commands.save.start"));
      if (var3.ap() != null) {
         var3.ap().j();
      }

      try {
         for (int var4 = 0; var4 < var3.d.length; var4++) {
            if (var3.d[var4] != null) {
               WorldServer var5 = var3.d[var4];
               boolean var6 = var5.c;
               var5.c = false;
               var5.a(true, null);
               var5.c = var6;
            }
         }

         if (var2.length > 0 && "flush".equals(var2[0])) {
            var1.a(new ChatComponentTranslation("commands.save.flushStart"));

            for (int var8 = 0; var8 < var3.d.length; var8++) {
               if (var3.d[var8] != null) {
                  WorldServer var9 = var3.d[var8];
                  boolean var10 = var9.c;
                  var9.c = false;
                  var9.n();
                  var9.c = var10;
               }
            }

            var1.a(new ChatComponentTranslation("commands.save.flushEnd"));
         }
      } catch (MinecraftException var7) {
         a(var1, this, "commands.save.failed", new Object[]{var7.getMessage()});
         return;
      }

      a(var1, this, "commands.save.success", new Object[0]);
   }
}
