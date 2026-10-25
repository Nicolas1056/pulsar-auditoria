package net.minecraft.command.server;

import java.util.List;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.BlockPos;

public class CommandTestFor extends CommandBase {
   @Override
   public String c() {
      return "testfor";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.testfor.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 1) {
         throw new WrongUsageException("commands.testfor.usage");
      } else {
         Entity var3 = b(var1, var2[0]);
         NBTTagCompound var4 = null;
         if (var2.length >= 2) {
            try {
               var4 = JsonToNBT.a(a(var2, 1));
            } catch (NBTException var6) {
               throw new CommandException("commands.testfor.tagError", var6.getMessage());
            }
         }

         if (var4 != null) {
            NBTTagCompound var5 = new NBTTagCompound();
            var3.e(var5);
            if (!NBTUtil.a(var4, var5, true)) {
               throw new CommandException("commands.testfor.failure", var3.e_());
            }
         }

         a(var1, this, "commands.testfor.success", new Object[]{var3.e_()});
      }
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length == 1 ? a(var2, MinecraftServer.N().K()) : null;
   }
}
