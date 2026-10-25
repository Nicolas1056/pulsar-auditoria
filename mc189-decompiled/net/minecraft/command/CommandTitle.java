package net.minecraft.command;

import com.google.gson.JsonParseException;
import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentProcessor;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandTitle extends CommandBase {
   private static final Logger a = LogManager.getLogger();

   @Override
   public String c() {
      return "title";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.title.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 2) {
         throw new WrongUsageException("commands.title.usage");
      } else {
         if (var2.length < 3) {
            if ("title".equals(var2[1]) || "subtitle".equals(var2[1])) {
               throw new WrongUsageException("commands.title.usage.title");
            }

            if ("times".equals(var2[1])) {
               throw new WrongUsageException("commands.title.usage.times");
            }
         }

         EntityPlayerMP var3 = a(var1, var2[0]);
         S45PacketTitle.Type var4 = S45PacketTitle.Type.a(var2[1]);
         if (var4 != S45PacketTitle.Type.d && var4 != S45PacketTitle.Type.e) {
            if (var4 == S45PacketTitle.Type.c) {
               if (var2.length != 5) {
                  throw new WrongUsageException("commands.title.usage");
               } else {
                  int var11 = a(var2[2]);
                  int var12 = a(var2[3]);
                  int var13 = a(var2[4]);
                  S45PacketTitle var14 = new S45PacketTitle(var11, var12, var13);
                  var3.a.a(var14);
                  a(var1, this, "commands.title.success", new Object[0]);
               }
            } else if (var2.length < 3) {
               throw new WrongUsageException("commands.title.usage");
            } else {
               String var10 = a(var2, 2);

               IChatComponent var6;
               try {
                  var6 = IChatComponent.Serializer.a(var10);
               } catch (JsonParseException var9) {
                  Throwable var8 = ExceptionUtils.getRootCause(var9);
                  throw new SyntaxErrorException("commands.tellraw.jsonException", var8 == null ? "" : var8.getMessage());
               }

               S45PacketTitle var7 = new S45PacketTitle(var4, ChatComponentProcessor.a(var1, var6, var3));
               var3.a.a(var7);
               a(var1, this, "commands.title.success", new Object[0]);
            }
         } else if (var2.length != 2) {
            throw new WrongUsageException("commands.title.usage");
         } else {
            S45PacketTitle var5 = new S45PacketTitle(var4, null);
            var3.a.a(var5);
            a(var1, this, "commands.title.success", new Object[0]);
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, MinecraftServer.N().K());
      } else {
         return var2.length == 2 ? a(var2, S45PacketTitle.Type.a()) : null;
      }
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }
}
