package net.minecraft.world.gen.feature;

import com.google.common.base.Predicates;
import java.util.Random;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockStateHelper;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class WorldGenDesertWells extends WorldGenerator {
   private static final BlockStateHelper a = BlockStateHelper.a(Blocks.m).a(BlockSand.a, Predicates.equalTo(BlockSand.EnumType.a));
   private final IBlockState b = Blocks.U.Q().a(BlockStoneSlab.N, BlockStoneSlab.EnumType.b).a(BlockSlab.a, BlockSlab.EnumBlockHalf.b);
   private final IBlockState c = Blocks.A.Q();
   private final IBlockState d = Blocks.i.Q();

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      while (var1.d(var3) && var3.o() > 2) {
         var3 = var3.b();
      }

      if (!a.a(var1.p(var3))) {
         return false;
      } else {
         for (int var4 = -2; var4 <= 2; var4++) {
            for (int var5 = -2; var5 <= 2; var5++) {
               if (var1.d(var3.a(var4, -1, var5)) && var1.d(var3.a(var4, -2, var5))) {
                  return false;
               }
            }
         }

         for (int var7 = -1; var7 <= 0; var7++) {
            for (int var12 = -2; var12 <= 2; var12++) {
               for (int var6 = -2; var6 <= 2; var6++) {
                  var1.a(var3.a(var12, var7, var6), this.c, 2);
               }
            }
         }

         var1.a(var3, this.d, 2);

         for (EnumFacing var13 : EnumFacing.Plane.a) {
            var1.a(var3.a(var13), this.d, 2);
         }

         for (int var9 = -2; var9 <= 2; var9++) {
            for (int var14 = -2; var14 <= 2; var14++) {
               if (var9 == -2 || var9 == 2 || var14 == -2 || var14 == 2) {
                  var1.a(var3.a(var9, 1, var14), this.c, 2);
               }
            }
         }

         var1.a(var3.a(2, 1, 0), this.b, 2);
         var1.a(var3.a(-2, 1, 0), this.b, 2);
         var1.a(var3.a(0, 1, 2), this.b, 2);
         var1.a(var3.a(0, 1, -2), this.b, 2);

         for (int var10 = -1; var10 <= 1; var10++) {
            for (int var15 = -1; var15 <= 1; var15++) {
               if (var10 == 0 && var15 == 0) {
                  var1.a(var3.a(var10, 4, var15), this.c, 2);
               } else {
                  var1.a(var3.a(var10, 4, var15), this.b, 2);
               }
            }
         }

         for (int var11 = 1; var11 <= 3; var11++) {
            var1.a(var3.a(-1, var11, -1), this.c, 2);
            var1.a(var3.a(-1, var11, 1), this.c, 2);
            var1.a(var3.a(1, var11, -1), this.c, 2);
            var1.a(var3.a(1, var11, 1), this.c, 2);
         }

         return true;
      }
   }
}
