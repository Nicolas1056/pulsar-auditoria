package net.minecraft.command;

import java.util.List;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;

public class CommandClearInventory extends CommandBase {
   @Override
   public String c() {
      return "clear";
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.clear.usage";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      EntityPlayerMP var3 = var2.length == 0 ? b(var1) : a(var1, var2[0]);
      Item var4 = var2.length >= 2 ? f(var1, var2[1]) : null;
      int var5 = var2.length >= 3 ? a(var2[2], -1) : -1;
      int var6 = var2.length >= 4 ? a(var2[3], -1) : -1;
      NBTTagCompound var7 = null;
      if (var2.length >= 5) {
         try {
            var7 = JsonToNBT.a(a(var2, 4));
         } catch (NBTException var9) {
            throw new CommandException("commands.clear.tagError", var9.getMessage());
         }
      }

      if (var2.length >= 2 && var4 == null) {
         throw new CommandException("commands.clear.failure", var3.e_());
      } else {
         int var8 = var3.bi.a(var4, var5, var6, var7);
         var3.bj.b();
         if (!var3.bA.d) {
            var3.o();
         }

         var1.a(CommandResultStats.Type.d, var8);
         if (var8 == 0) {
            throw new CommandException("commands.clear.failure", var3.e_());
         } else {
            if (var6 == 0) {
               var1.a(new ChatComponentTranslation("commands.clear.testing", var3.e_(), var8));
            } else {
               a(var1, this, "commands.clear.success", new Object[]{var3.e_(), var8});
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length == 1) {
         return a(var2, this.d());
      } else {
         return var2.length == 2 ? a(var2, Item.e.c()) : null;
      }
   }

   protected String[] d() {
      return MinecraftServer.N().K();
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }
}
