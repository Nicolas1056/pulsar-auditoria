package net.minecraft.command.server;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandSetBlock extends CommandBase {
   @Override
   public String c() {
      return "setblock";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.setblock.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 4) {
         throw new WrongUsageException("commands.setblock.usage");
      } else {
         var1.a(CommandResultStats.Type.b, 0);
         BlockPos var3 = a(var1, var2, 0, false);
         Block var4 = CommandBase.g(var1, var2[3]);
         int var5 = 0;
         if (var2.length >= 5) {
            var5 = a(var2[4], 0, 15);
         }

         World var6 = var1.e();
         if (!var6.e(var3)) {
            throw new CommandException("commands.setblock.outOfWorld");
         } else {
            NBTTagCompound var7 = new NBTTagCompound();
            boolean var8 = false;
            if (var2.length >= 7 && var4.z()) {
               String var9 = a(var1, var2, 6).c();

               try {
                  var7 = JsonToNBT.a(var9);
                  var8 = true;
               } catch (NBTException var12) {
                  throw new CommandException("commands.setblock.tagError", var12.getMessage());
               }
            }

            if (var2.length >= 6) {
               if (var2[5].equals("destroy")) {
                  var6.b(var3, true);
                  if (var4 == Blocks.a) {
                     a(var1, this, "commands.setblock.success", new Object[0]);
                     return;
                  }
               } else if (var2[5].equals("keep") && !var6.d(var3)) {
                  throw new CommandException("commands.setblock.noChange");
               }
            }

            TileEntity var13 = var6.s(var3);
            if (var13 != null) {
               if (var13 instanceof IInventory) {
                  ((IInventory)var13).l();
               }

               var6.a(var3, Blocks.a.Q(), var4 == Blocks.a ? 2 : 4);
            }

            IBlockState var10 = var4.a(var5);
            if (!var6.a(var3, var10, 2)) {
               throw new CommandException("commands.setblock.noChange");
            } else {
               if (var8) {
                  TileEntity var11 = var6.s(var3);
                  if (var11 != null) {
                     var7.a("x", var3.n());
                     var7.a("y", var3.o());
                     var7.a("z", var3.p());
                     var11.a(var7);
                  }
               }

               var6.b(var3, var10.c());
               var1.a(CommandResultStats.Type.b, 1);
               a(var1, this, "commands.setblock.success", new Object[0]);
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length > 0 && var2.length <= 3) {
         return a(var2, 0, var3);
      } else if (var2.length == 4) {
         return a(var2, Block.c.c());
      } else {
         return var2.length == 6 ? a(var2, new String[]{"replace", "destroy", "keep"}) : null;
      }
   }
}
