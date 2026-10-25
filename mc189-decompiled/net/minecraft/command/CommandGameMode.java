package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.WorldSettings;

public class CommandGameMode extends CommandBase {
   @Override
   public String c() {
      return "gamemode";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.gamemode.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length <= 0) {
         throw new WrongUsageException("commands.gamemode.usage");
      } else {
         WorldSettings.GameType var3 = this.h(var1, var2[0]);
         EntityPlayerMP var4 = var2.length >= 2 ? a(var1, var2[1]) : b(var1);
         var4.a(var3);
         var4.O = 0.0F;
         if (var1.e().Q().b("sendCommandFeedback")) {
            var4.a(new ChatComponentTranslation("gameMode.changed"));
         }

         ChatComponentTranslation var5 = new ChatComponentTranslation("gameMode." + var3.b());
         if (var4 != var1) {
            a(var1, this, 1, "commands.gamemode.success.other", new Object[]{var4.e_(), var5});
         } else {
            a(var1, this, 1, "commands.gamemode.success.self", new Object[]{var5});
         }
      }
   }

   protected WorldSettings.GameType h(ICommandSender var1, String var2) throws NumberInvalidException {
      if (var2.equalsIgnoreCase(WorldSettings.GameType.b.b()) || var2.equalsIgnoreCase("s")) {
         return WorldSettings.GameType.b;
      } else if (var2.equalsIgnoreCase(WorldSettings.GameType.c.b()) || var2.equalsIgnoreCase("c")) {
         return WorldSettings.GameType.c;
      } else if (var2.equalsIgnoreCase(WorldSettings.GameType.d.b()) || var2.equalsIgnoreCase("a")) {
         return WorldSettings.GameType.d;
      } else {
         return !var2.equalsIgnoreCase(WorldSettings.GameType.e.b()) && !var2.equalsIgnoreCase("sp")
            ? WorldSettings.a(a(var2, 0, WorldSettings.GameType.values().length - 2))
            : WorldSettings.GameType.e;
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, new String[]{"survival", "creative", "adventure", "spectator"});
      } else {
         return var2.length == 2 ? a(var2, this.d()) : null;
      }
   }

   protected String[] d() {
      return MinecraftServer.N().K();
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 1;
   }
}
