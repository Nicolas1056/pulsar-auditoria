package net.minecraft.command.server;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandResultStats;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.NumberInvalidException;
import net.minecraft.command.WrongUsageException;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandTestForBlock extends CommandBase {
   @Override
   public String c() {
      return "testforblock";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.testforblock.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 4) {
         throw new WrongUsageException("commands.testforblock.usage");
      } else {
         var1.a(CommandResultStats.Type.b, 0);
         BlockPos var3 = a(var1, var2, 0, false);
         Block var4 = Block.b(var2[3]);
         if (var4 == null) {
            throw new NumberInvalidException("commands.setblock.notFound", var2[3]);
         } else {
            int var5 = -1;
            if (var2.length >= 5) {
               var5 = a(var2[4], -1, 15);
            }

            World var6 = var1.e();
            if (!var6.e(var3)) {
               throw new CommandException("commands.testforblock.outOfWorld");
            } else {
               NBTTagCompound var7 = new NBTTagCompound();
               boolean var8 = false;
               if (var2.length >= 6 && var4.z()) {
                  String var9 = a(var1, var2, 5).c();

                  try {
                     var7 = JsonToNBT.a(var9);
                     var8 = true;
                  } catch (NBTException var13) {
                     throw new CommandException("commands.setblock.tagError", var13.getMessage());
                  }
               }

               IBlockState var14 = var6.p(var3);
               Block var10 = var14.c();
               if (var10 != var4) {
                  throw new CommandException("commands.testforblock.failed.tile", var3.n(), var3.o(), var3.p(), var10.f(), var4.f());
               } else {
                  if (var5 > -1) {
                     int var11 = var14.c().c(var14);
                     if (var11 != var5) {
                        throw new CommandException("commands.testforblock.failed.data", var3.n(), var3.o(), var3.p(), var11, var5);
                     }
                  }

                  if (var8) {
                     TileEntity var15 = var6.s(var3);
                     if (var15 == null) {
                        throw new CommandException("commands.testforblock.failed.tileEntity", var3.n(), var3.o(), var3.p());
                     }

                     NBTTagCompound var12 = new NBTTagCompound();
                     var15.b(var12);
                     if (!NBTUtil.a(var7, var12, true)) {
                        throw new CommandException("commands.testforblock.failed.nbt", var3.n(), var3.o(), var3.p());
                     }
                  }

                  var1.a(CommandResultStats.Type.b, 1);
                  a(var1, this, "commands.testforblock.success", new Object[]{var3.n(), var3.o(), var3.p()});
               }
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length > 0 && var2.length <= 3) {
         return a(var2, 0, var3);
      } else {
         return var2.length == 4 ? a(var2, Block.c.c()) : null;
      }
   }
}
