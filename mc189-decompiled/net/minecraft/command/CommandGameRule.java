package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.GameRules;

public class CommandGameRule extends CommandBase {
   @Override
   public String c() {
      return "gamerule";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.gamerule.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      GameRules var3 = this.d();
      String var4 = var2.length > 0 ? var2[0] : "";
      String var5 = var2.length > 1 ? a(var2, 1) : "";
      switch (var2.length) {
         case 0:
            var1.a(new ChatComponentText(a(var3.b())));
            break;
         case 1:
            if (!var3.e(var4)) {
               throw new CommandException("commands.gamerule.norule", var4);
            }

            String var6 = var3.a(var4);
            var1.a(new ChatComponentText(var4).a(" = ").a(var6));
            var1.a(CommandResultStats.Type.e, var3.c(var4));
            break;
         default:
            if (var3.a(var4, GameRules.ValueType.b) && !"true".equals(var5) && !"false".equals(var5)) {
               throw new CommandException("commands.generic.boolean.invalid", var5);
            }

            var3.a(var4, var5);
            a(var3, var4);
            a(var1, this, "commands.gamerule.success", new Object[0]);
      }
   }

   public static void a(GameRules var0, String var1) {
      if ("reducedDebugInfo".equals(var1)) {
         int var2 = var0.b(var1) ? 22 : 23;

         for (EntityPlayerMP var4 : MinecraftServer.N().ap().v()) {
            var4.a.a(new S19PacketEntityStatus(var4, (byte)var2));
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, this.d().b());
      } else {
         if (var2.length == 2) {
            GameRules var4 = this.d();
            if (var4.a(var2[0], GameRules.ValueType.b)) {
               return a(var2, new String[]{"true", "false"});
            }
         }

         return null;
      }
   }

   private GameRules d() {
      return MinecraftServer.N().a(0).Q();
   }
}
