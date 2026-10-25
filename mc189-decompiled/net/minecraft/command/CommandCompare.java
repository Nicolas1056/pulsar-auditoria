package net.minecraft.command;

import java.util.List;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;

public class CommandCompare extends CommandBase {
   @Override
   public String c() {
      return "testforblocks";
   }

   @Override
   public int a() {
      return 2;
   }

   @Override
   public String c(ICommandSender var1) {
      return "commands.compare.usage";
   }

   @Override
   public void a(ICommandSender var1, String[] var2) throws CommandException {
      if (var2.length < 9) {
         throw new WrongUsageException("commands.compare.usage");
      } else {
         var1.a(CommandResultStats.Type.b, 0);
         BlockPos var3 = a(var1, var2, 0, false);
         BlockPos var4 = a(var1, var2, 3, false);
         BlockPos var5 = a(var1, var2, 6, false);
         StructureBoundingBox var6 = new StructureBoundingBox(var3, var4);
         StructureBoundingBox var7 = new StructureBoundingBox(var5, var5.a(var6.b()));
         int var8 = var6.c() * var6.d() * var6.e();
         if (var8 > 524288) {
            throw new CommandException("commands.compare.tooManyBlocks", var8, 524288);
         } else if (var6.b >= 0 && var6.e < 256 && var7.b >= 0 && var7.e < 256) {
            World var9 = var1.e();
            if (var9.a(var6) && var9.a(var7)) {
               boolean var10 = false;
               if (var2.length > 9 && var2[9].equals("masked")) {
                  var10 = true;
               }

               var8 = 0;
               BlockPos var11 = new BlockPos(var7.a - var6.a, var7.b - var6.b, var7.c - var6.c);
               BlockPos.MutableBlockPos var12 = new BlockPos.MutableBlockPos();
               BlockPos.MutableBlockPos var13 = new BlockPos.MutableBlockPos();

               for (int var14 = var6.c; var14 <= var6.f; var14++) {
                  for (int var15 = var6.b; var15 <= var6.e; var15++) {
                     for (int var16 = var6.a; var16 <= var6.d; var16++) {
                        var12.c(var16, var15, var14);
                        var13.c(var16 + var11.n(), var15 + var11.o(), var14 + var11.p());
                        boolean var17 = false;
                        IBlockState var18 = var9.p(var12);
                        if (!var10 || var18.c() != Blocks.a) {
                           if (var18 == var9.p(var13)) {
                              TileEntity var19 = var9.s(var12);
                              TileEntity var20 = var9.s(var13);
                              if (var19 != null && var20 != null) {
                                 NBTTagCompound var21 = new NBTTagCompound();
                                 var19.b(var21);
                                 var21.o("x");
                                 var21.o("y");
                                 var21.o("z");
                                 NBTTagCompound var22 = new NBTTagCompound();
                                 var20.b(var22);
                                 var22.o("x");
                                 var22.o("y");
                                 var22.o("z");
                                 if (!var21.equals(var22)) {
                                    var17 = true;
                                 }
                              } else if (var19 != null) {
                                 var17 = true;
                              }
                           } else {
                              var17 = true;
                           }

                           var8++;
                           if (var17) {
                              throw new CommandException("commands.compare.failed");
                           }
                        }
                     }
                  }
               }

               var1.a(CommandResultStats.Type.b, var8);
               a(var1, this, "commands.compare.success", new Object[]{var8});
            } else {
               throw new CommandException("commands.compare.outOfWorld");
            }
         } else {
            throw new CommandException("commands.compare.outOfWorld");
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
      } else {
         return var2.length == 10 ? a(var2, new String[]{"masked", "all"}) : null;
      }
   }
}
