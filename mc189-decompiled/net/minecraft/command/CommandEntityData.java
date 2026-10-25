package net.minecraft.command;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;

public class CommandEntityData extends CommandBase {
   @Override
   public String c() {
      return "entitydata";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.entitydata.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 2) {
         throw new WrongUsageException("commands.entitydata.usage");
      } else {
         Entity var3 = b(var1, var2[0]);
         if (var3 instanceof EntityPlayer) {
            throw new CommandException("commands.entitydata.noPlayers", var3.f_());
         } else {
            NBTTagCompound var4 = new NBTTagCompound();
            var3.e(var4);
            NBTTagCompound var5 = (NBTTagCompound)var4.b();

            NBTTagCompound var6;
            try {
               var6 = JsonToNBT.a(a(var1, var2, 1).c());
            } catch (NBTException var8) {
               throw new CommandException("commands.entitydata.tagError", var8.getMessage());
            }

            var6.o("UUIDMost");
            var6.o("UUIDLeast");
            var4.a(var6);
            if (var4.equals(var5)) {
               throw new CommandException("commands.entitydata.failed", var4.toString());
            } else {
               var3.f(var4);
               a(var1, this, "commands.entitydata.success", new Object[]{var4.toString()});
            }
         }
      }
   }

   @Override
   public boolean b(String[] var1, int var2) {
      return var2 == 0;
   }
}
