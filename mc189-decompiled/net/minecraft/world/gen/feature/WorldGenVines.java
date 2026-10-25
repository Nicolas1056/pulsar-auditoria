package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.BlockVine;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenVines extends WorldGenerator {
   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      while (var3.o() < 128) {
         if (var1.d(var3)) {
            for (EnumFacing var7 : EnumFacing.Plane.a.a()) {
               if (Blocks.bn.b(var1, var3, var7)) {
                  IBlockState var8 = Blocks.bn
                     .Q()
                     .a(BlockVine.b, var7 == EnumFacing.c)
                     .a(BlockVine.N, var7 == EnumFacing.f)
                     .a(BlockVine.O, var7 == EnumFacing.d)
                     .a(BlockVine.P, var7 == EnumFacing.e);
                  var1.a(var3, var8, 2);
                  break;
               }
            }
         } else {
            var3 = var3.a(var2.nextInt(4) - var2.nextInt(4), 0, var2.nextInt(4) - var2.nextInt(4));
         }

         var3 = var3.a();
      }

      return true;
   }
}
