package net.minecraft.command;

import java.util.List;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandBlockData extends CommandBase {
   @Override
   public String c() {
      return "blockdata";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.blockdata.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 4) {
         throw new WrongUsageException("commands.blockdata.usage");
      } else {
         var1.a(CommandResultStats.Type.b, 0);
         BlockPos var3 = a(var1, var2, 0, false);
         World var4 = var1.e();
         if (!var4.e(var3)) {
            throw new CommandException("commands.blockdata.outOfWorld");
         } else {
            TileEntity var5 = var4.s(var3);
            if (var5 == null) {
               throw new CommandException("commands.blockdata.notValid");
            } else {
               NBTTagCompound var6 = new NBTTagCompound();
               var5.b(var6);
               NBTTagCompound var7 = (NBTTagCompound)var6.b();

               NBTTagCompound var8;
               try {
                  var8 = JsonToNBT.a(a(var1, var2, 3).c());
               } catch (NBTException var10) {
                  throw new CommandException("commands.blockdata.tagError", var10.getMessage());
               }

               var6.a(var8);
               var6.a("x", var3.n());
               var6.a("y", var3.o());
               var6.a("z", var3.p());
               if (var6.equals(var7)) {
                  throw new CommandException("commands.blockdata.failed", var6.toString());
               } else {
                  var5.a(var6);
                  var5.p_();
                  var4.h(var3);
                  var1.a(CommandResultStats.Type.b, 1);
                  a(var1, this, "commands.blockdata.success", new Object[]{var6.toString()});
               }
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      return var2.length > 0 && var2.length <= 3 ? a(var2, 0, var3) : null;
   }
}
