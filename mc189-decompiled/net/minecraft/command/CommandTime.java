package net.minecraft.command;

import java.util.List;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.WorldServer;

public class CommandTime extends CommandBase {
   @Override
   public String c() {
      return "time";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.time.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length > 1) {
         if (var2[0].equals("set")) {
            int var6;
            if (var2[1].equals("day")) {
               var6 = 1000;
            } else if (var2[1].equals("night")) {
               var6 = 13000;
            } else {
               var6 = a(var2[1], 0);
            }

            this.a(var1, var6);
            a(var1, this, "commands.time.set", new Object[]{var6});
            return;
         }

         if (var2[0].equals("add")) {
            int var5 = a(var2[1], 0);
            this.b(var1, var5);
            a(var1, this, "commands.time.added", new Object[]{var5});
            return;
         }

         if (var2[0].equals("query")) {
            if (var2[1].equals("daytime")) {
               int var4 = (int)(var1.e().L() % 2147483647L);
               var1.a(CommandResultStats.Type.e, var4);
               a(var1, this, "commands.time.query", new Object[]{var4});
               return;
            }

            if (var2[1].equals("gametime")) {
               int var3 = (int)(var1.e().K() % 2147483647L);
               var1.a(CommandResultStats.Type.e, var3);
               a(var1, this, "commands.time.query", new Object[]{var3});
               return;
            }
         }
      }

      throw new WrongUsageException("commands.time.usage");
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, new String[]{"set", "add", "query"});
      } else if (var2.length == 2 && var2[0].equals("set")) {
         return a(var2, new String[]{"day", "night"});
      } else {
         return var2.length == 2 && var2[0].equals("query") ? a(var2, new String[]{"daytime", "gametime"}) : null;
      }
   }

   protected void a(ICommandSender var1, int var2) {
      for (int var3 = 0; var3 < MinecraftServer.N().d.length; var3++) {
         MinecraftServer.N().d[var3].b(var2);
      }
   }

   protected void b(ICommandSender var1, int var2) {
      for (int var3 = 0; var3 < MinecraftServer.N().d.length; var3++) {
         WorldServer var4 = MinecraftServer.N().d[var3];
         var4.b(var4.L() + var2);
      }
   }
}
