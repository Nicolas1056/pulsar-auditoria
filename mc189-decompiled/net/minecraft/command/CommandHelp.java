package net.minecraft.command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.event.ClickEvent;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

public class CommandHelp extends CommandBase {
   @Override
   public String c() {
      return "help";
   }

   @Override
   public int a() {
      return 0;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.help.usage";
   }

   @Override
   public List<String> b() {
      return Arrays.asList("?");
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      List var3 = this.d(var1);
      byte var4 = 7;
      int var5 = (var3.size() - 1) / 7;
      int var6 = 0;

      try {
         var6 = var2.length == 0 ? 0 : a(var2[0], 1, var5 + 1) - 1;
      } catch (NumberInvalidException var12) {
         Map var8 = this.d();
         ICommand var9 = (ICommand)var8.get(var2[0]);
         if (var9 != null) {
            throw new WrongUsageException(var9.c(var1));
         }

         if (MathHelper.a(var2[0], -1) != -1) {
            throw var12;
         }

         throw new CommandNotFoundException();
      }

      int var7 = Math.min((var6 + 1) * 7, var3.size());
      ChatComponentTranslation var14 = new ChatComponentTranslation("commands.help.header", var6 + 1, var5 + 1);
      var14.b().a(EnumChatFormatting.c);
      var1.a(var14);

      for (int var15 = var6 * 7; var15 < var7; var15++) {
         ICommand var10 = (ICommand)var3.get(var15);
         ChatComponentTranslation var11 = new ChatComponentTranslation(var10.c(var1));
         var11.b().a(new ClickEvent(ClickEvent.Action.e, "/" + var10.c() + " "));
         var1.a(var11);
      }

      if (var6 == 0 && var1 instanceof EntityPlayer) {
         ChatComponentTranslation var16 = new ChatComponentTranslation("commands.help.footer");
         var16.b().a(EnumChatFormatting.k);
         var1.a(var16);
      }
   }

   protected List<ICommand> d(ICommandSender var1) {
      List var2 = MinecraftServer.N().P().a(var1);
      Collections.sort(var2);
      return var2;
   }

   protected Map<String, ICommand> d() {
      return MinecraftServer.N().P().a();
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         Set var4 = this.d().keySet();
         return a(var2, var4.toArray(new String[var4.size()]));
      } else {
         return null;
      }
   }
}
