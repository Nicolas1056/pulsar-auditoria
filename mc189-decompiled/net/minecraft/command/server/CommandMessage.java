package net.minecraft.command.server;

import java.util.Arrays;
import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;

public class CommandMessage extends CommandBase {
   @Override
   public List<String> b() {
      return Arrays.asList("w", "msg");
   }

   @Override
   public String c() {
      return "tell";
   }

   @Override
   public int a() {
      return 0;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.message.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 2) {
         throw new WrongUsageException("commands.message.usage");
      } else {
         EntityPlayerMP var3 = a(var1, var2[0]);
         if (var3 == var1) {
            throw new PlayerNotFoundException("commands.message.sameTarget");
         } else {
            IChatComponent var4 = b(var1, var2, 1, !(var1 instanceof EntityPlayer));
            ChatComponentTranslation var5 = new ChatComponentTranslation("commands.message.display.incoming", var1.f_(), var4.f());
            ChatComponentTranslation var6 = new ChatComponentTranslation("commands.message.display.outgoing", var3.f_(), var4.f());
            var5.b().a(EnumChatFormatting.h).b(true);
            var6.b().a(EnumChatFormatting.h).b(true);
            var3.a(var5);
            var1.a(var6);
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return a(var2, MinecraftServer.N().K());
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }
}
