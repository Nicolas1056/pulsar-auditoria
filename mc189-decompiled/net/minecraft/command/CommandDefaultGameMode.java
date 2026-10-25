package net.minecraft.command;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

public class CommandDefaultGameMode extends CommandGameMode {
   @Override
   public String c() {
      return "defaultgamemode";
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.defaultgamemode.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length <= 0) {
         throw new WrongUsageException("commands.defaultgamemode.usage");
      } else {
         WorldSettings.GameType var3 = this.h(var1, var2[0]);
         this.a(var3);
         a(var1, this, "commands.defaultgamemode.success", new Object[]{new ChatComponentTranslation("gameMode." + var3.b())});
      }
   }

   protected void a(WorldSettings.GameType var1) {
      MinecraftServer var2 = MinecraftServer.N();
      var2.a(var1);
      if (var2.ax()) {
         for (EntityPlayerMP var4 : MinecraftServer.N().ap().v()) {
            var4.a(var1);
            var4.O = 0.0F;
         }
      }
   }
}
