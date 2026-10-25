package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.world.EnumDifficulty;

public class CommandDifficulty extends CommandBase {
   @Override
   public String c() {
      return "difficulty";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.difficulty.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length <= 0) {
         throw new WrongUsageException("commands.difficulty.usage");
      } else {
         EnumDifficulty var3 = this.e(var2[0]);
         MinecraftServer.N().a(var3);
         a(var1, this, "commands.difficulty.success", new Object[]{new ChatComponentTranslation(var3.b())});
      }
   }

   protected EnumDifficulty e(String var1) throws NumberInvalidException {
      if (var1.equalsIgnoreCase("peaceful") || var1.equalsIgnoreCase("p")) {
         return EnumDifficulty.a;
      } else if (var1.equalsIgnoreCase("easy") || var1.equalsIgnoreCase("e")) {
         return EnumDifficulty.b;
      } else if (var1.equalsIgnoreCase("normal") || var1.equalsIgnoreCase("n")) {
         return EnumDifficulty.c;
      } else {
         return !var1.equalsIgnoreCase("hard") && !var1.equalsIgnoreCase("h") ? EnumDifficulty.a(a(var1, 0, 3)) : EnumDifficulty.d;
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? a(var2, new String[]{"peaceful", "easy", "normal", "hard"}) : null;
   }
}
