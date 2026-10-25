package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class CommandFill extends CommandBase {
   @Override
   public String c() {
      return "fill";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.fill.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 7) {
         throw new WrongUsageException("commands.fill.usage");
      } else {
         var1.a(CommandResultStats.Type.b, 0);
         BlockPos var3 = a(var1, var2, 0, false);
         BlockPos var4 = a(var1, var2, 3, false);
         Block var5 = CommandBase.g(var1, var2[6]);
         int var6 = 0;
         if (var2.length >= 8) {
            var6 = a(var2[7], 0, 15);
         }

         BlockPos var7 = new BlockPos(Math.min(var3.n(), var4.n()), Math.min(var3.o(), var4.o()), Math.min(var3.p(), var4.p()));
         BlockPos var8 = new BlockPos(Math.max(var3.n(), var4.n()), Math.max(var3.o(), var4.o()), Math.max(var3.p(), var4.p()));
         int var9 = (var8.n() - var7.n() + 1) * (var8.o() - var7.o() + 1) * (var8.p() - var7.p() + 1);
         if (var9 > 32768) {
            throw new CommandException("commands.fill.tooManyBlocks", var9, 32768);
         } else if (var7.o() >= 0 && var8.o() < 256) {
            World var10 = var1.e();

            for (int var11 = var7.p(); var11 < var8.p() + 16; var11 += 16) {
               for (int var12 = var7.n(); var12 < var8.n() + 16; var12 += 16) {
                  if (!var10.e(new BlockPos(var12, var8.o() - var7.o(), var11))) {
                     throw new CommandException("commands.fill.outOfWorld");
                  }
               }
            }

            NBTTagCompound var23 = new NBTTagCompound();
            boolean var24 = false;
            if (var2.length >= 10 && var5.z()) {
               String var13 = a(var1, var2, 9).c();

               try {
                  var23 = JsonToNBT.a(var13);
                  var24 = true;
               } catch (NBTException var21) {
                  throw new CommandException("commands.fill.tagError", var21.getMessage());
               }
            }

            ArrayList var25 = Lists.newArrayList();
            var9 = 0;

            for (int var14 = var7.p(); var14 <= var8.p(); var14++) {
               for (int var15 = var7.o(); var15 <= var8.o(); var15++) {
                  for (int var16 = var7.n(); var16 <= var8.n(); var16++) {
                     BlockPos var17 = new BlockPos(var16, var15, var14);
                     if (var2.length >= 9) {
                        if (!var2[8].equals("outline") && !var2[8].equals("hollow")) {
                           if (var2[8].equals("destroy")) {
                              var10.b(var17, true);
                           } else if (var2[8].equals("keep")) {
                              if (!var10.d(var17)) {
                                 continue;
                              }
                           } else if (var2[8].equals("replace") && !var5.z()) {
                              if (var2.length > 9) {
                                 Block var18 = CommandBase.g(var1, var2[9]);
                                 if (var10.p(var17).c() != var18) {
                                    continue;
                                 }
                              }

                              if (var2.length > 10) {
                                 int var29 = CommandBase.a(var2[10]);
                                 IBlockState var19 = var10.p(var17);
                                 if (var19.c().c(var19) != var29) {
                                    continue;
                                 }
                              }
                           }
                        } else if (var16 != var7.n() && var16 != var8.n() && var15 != var7.o() && var15 != var8.o() && var14 != var7.p() && var14 != var8.p()) {
                           if (var2[8].equals("hollow")) {
                              var10.a(var17, Blocks.a.Q(), 2);
                              var25.add(var17);
                           }
                           continue;
                        }
                     }

                     TileEntity var30 = var10.s(var17);
                     if (var30 != null) {
                        if (var30 instanceof IInventory) {
                           ((IInventory)var30).l();
                        }

                        var10.a(var17, Blocks.cv.Q(), var5 == Blocks.cv ? 2 : 4);
                     }

                     IBlockState var31 = var5.a(var6);
                     if (var10.a(var17, var31, 2)) {
                        var25.add(var17);
                        var9++;
                        if (var24) {
                           TileEntity var20 = var10.s(var17);
                           if (var20 != null) {
                              var23.a("x", var17.n());
                              var23.a("y", var17.o());
                              var23.a("z", var17.p());
                              var20.a(var23);
                           }
                        }
                     }
                  }
               }
            }

            for (BlockPos var27 : var25) {
               Block var28 = var10.p(var27).c();
               var10.b(var27, var28);
            }

            if (var9 <= 0) {
               throw new CommandException("commands.fill.failed");
            } else {
               var1.a(CommandResultStats.Type.b, var9);
               a(var1, this, "commands.fill.success", new Object[]{var9});
            }
         } else {
            throw new CommandException("commands.fill.outOfWorld");
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length > 0 && var2.length <= 3) {
         return a(var2, 0, var3);
      } else if (var2.length > 3 && var2.length <= 6) {
         return a(var2, 3, var3);
      } else if (var2.length == 7) {
         return a(var2, Block.c.c());
      } else if (var2.length == 9) {
         return a(var2, new String[]{"replace", "destroy", "keep", "hollow", "outline"});
      } else {
         return var2.length == 10 && "replace".equals(var2[8]) ? a(var2, Block.c.c()) : null;
      }
   }
}
