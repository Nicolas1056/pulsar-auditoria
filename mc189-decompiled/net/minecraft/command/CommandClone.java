package net.minecraft.command;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.NextTickListEntry;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class CommandClone extends CommandBase {
   @Override
   public String c() {
      return "clone";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.clone.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 9) {
         throw new WrongUsageException("commands.clone.usage");
      } else {
         var1.a(CommandResultStats.Type.b, 0);
         BlockPos var3 = a(var1, var2, 0, false);
         BlockPos var4 = a(var1, var2, 3, false);
         BlockPos var5 = a(var1, var2, 6, false);
         StructureBoundingBox var6 = new StructureBoundingBox(var3, var4);
         StructureBoundingBox var7 = new StructureBoundingBox(var5, var5.a(var6.b()));
         int var8 = var6.c() * var6.d() * var6.e();
         if (var8 > 32768) {
            throw new CommandException("commands.clone.tooManyBlocks", var8, 32768);
         } else {
            boolean var9 = false;
            Block var10 = null;
            int var11 = -1;
            if ((var2.length < 11 || !var2[10].equals("force") && !var2[10].equals("move")) && var6.a(var7)) {
               throw new CommandException("commands.clone.noOverlap");
            } else {
               if (var2.length >= 11 && var2[10].equals("move")) {
                  var9 = true;
               }

               if (var6.b >= 0 && var6.e < 256 && var7.b >= 0 && var7.e < 256) {
                  World var12 = var1.e();
                  if (var12.a(var6) && var12.a(var7)) {
                     boolean var13 = false;
                     if (var2.length >= 10) {
                        if (var2[9].equals("masked")) {
                           var13 = true;
                        } else if (var2[9].equals("filtered")) {
                           if (var2.length < 12) {
                              throw new WrongUsageException("commands.clone.usage");
                           }

                           var10 = g(var1, var2[11]);
                           if (var2.length >= 13) {
                              var11 = a(var2[12], 0, 15);
                           }
                        }
                     }

                     ArrayList var14 = Lists.newArrayList();
                     ArrayList var15 = Lists.newArrayList();
                     ArrayList var16 = Lists.newArrayList();
                     LinkedList var17 = Lists.newLinkedList();
                     BlockPos var18 = new BlockPos(var7.a - var6.a, var7.b - var6.b, var7.c - var6.c);

                     for (int var19 = var6.c; var19 <= var6.f; var19++) {
                        for (int var20 = var6.b; var20 <= var6.e; var20++) {
                           for (int var21 = var6.a; var21 <= var6.d; var21++) {
                              BlockPos var22 = new BlockPos(var21, var20, var19);
                              BlockPos var23 = var22.a(var18);
                              IBlockState var24 = var12.p(var22);
                              if ((!var13 || var24.c() != Blocks.a) && (var10 == null || var24.c() == var10 && (var11 < 0 || var24.c().c(var24) == var11))) {
                                 TileEntity var25 = var12.s(var22);
                                 if (var25 != null) {
                                    NBTTagCompound var26 = new NBTTagCompound();
                                    var25.b(var26);
                                    var15.add(new CommandClone.StaticCloneData(var23, var24, var26));
                                    var17.addLast(var22);
                                 } else if (!var24.c().o() && !var24.c().d()) {
                                    var16.add(new CommandClone.StaticCloneData(var23, var24, null));
                                    var17.addFirst(var22);
                                 } else {
                                    var14.add(new CommandClone.StaticCloneData(var23, var24, null));
                                    var17.addLast(var22);
                                 }
                              }
                           }
                        }
                     }

                     if (var9) {
                        for (BlockPos var31 : var17) {
                           TileEntity var34 = var12.s(var31);
                           if (var34 instanceof IInventory) {
                              ((IInventory)var34).l();
                           }

                           var12.a(var31, Blocks.cv.Q(), 2);
                        }

                        for (BlockPos var32 : var17) {
                           var12.a(var32, Blocks.a.Q(), 3);
                        }
                     }

                     ArrayList var30 = Lists.newArrayList();
                     var30.addAll(var14);
                     var30.addAll(var15);
                     var30.addAll(var16);
                     List var33 = Lists.reverse(var30);

                     for (CommandClone.StaticCloneData var40 : var33) {
                        TileEntity var45 = var12.s(var40.a);
                        if (var45 instanceof IInventory) {
                           ((IInventory)var45).l();
                        }

                        var12.a(var40.a, Blocks.cv.Q(), 2);
                     }

                     var8 = 0;

                     for (CommandClone.StaticCloneData var41 : var30) {
                        if (var12.a(var41.a, var41.b, 2)) {
                           var8++;
                        }
                     }

                     for (CommandClone.StaticCloneData var42 : var15) {
                        TileEntity var46 = var12.s(var42.a);
                        if (var42.c != null && var46 != null) {
                           var42.c.a("x", var42.a.n());
                           var42.c.a("y", var42.a.o());
                           var42.c.a("z", var42.a.p());
                           var46.a(var42.c);
                           var46.p_();
                        }

                        var12.a(var42.a, var42.b, 2);
                     }

                     for (CommandClone.StaticCloneData var43 : var33) {
                        var12.b(var43.a, var43.b.c());
                     }

                     List var39 = var12.a(var6, false);
                     if (var39 != null) {
                        for (NextTickListEntry var47 : var39) {
                           if (var6.b(var47.a)) {
                              BlockPos var48 = var47.a.a(var18);
                              var12.b(var48, var47.a(), (int)(var47.b - var12.P().f()), var47.c);
                           }
                        }
                     }

                     if (var8 <= 0) {
                        throw new CommandException("commands.clone.failed");
                     } else {
                        var1.a(CommandResultStats.Type.b, var8);
                        a(var1, this, "commands.clone.success", new Object[]{var8});
                     }
                  } else {
                     throw new CommandException("commands.clone.outOfWorld");
                  }
               } else {
                  throw new CommandException("commands.clone.outOfWorld");
               }
            }
         }
      }
   }

   @Override
   public List<String> a(ICommandSender var1, String[] var2, BlockPos var3) {
      if (var2.length > 0 && var2.length <= 3) {
         return a(var2, 0, var3);
      } else if (var2.length > 3 && var2.length <= 6) {
         return a(var2, 3, var3);
      } else if (var2.length > 6 && var2.length <= 9) {
         return a(var2, 6, var3);
      } else if (var2.length == 10) {
         return a(var2, new String[]{"replace", "masked", "filtered"});
      } else if (var2.length == 11) {
         return a(var2, new String[]{"normal", "force", "move"});
      } else {
         return var2.length == 12 && "filtered".equals(var2[9]) ? a(var2, Block.c.c()) : null;
      }
   }

   static class StaticCloneData {
      public final BlockPos a;
      public final IBlockState b;
      public final NBTTagCompound c;

      public StaticCloneData(BlockPos var1, IBlockState var2, NBTTagCompound var3) {
         this.a = var1;
         this.b = var2;
         this.c = var3;
      }
   }
}
