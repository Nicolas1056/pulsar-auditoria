package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;

public class BlockMushroom extends BlockBush implements IGrowable {
   protected BlockMushroom() {
      float var1 = 0.2F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var1 * 2.0F, 0.5F + var1);
      this.a(true);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var4.nextInt(25) == 0) {
         int var5 = 5;
         byte var6 = 4;

         for (BlockPos var8 : BlockPos.b(var2.a(-4, -1, -4), var2.a(4, 1, 4))) {
            if (var1.p(var8).c() == this) {
               if (--var5 <= 0) {
                  return;
               }
            }
         }

         BlockPos var9 = var2.a(var4.nextInt(3) - 1, var4.nextInt(2) - var4.nextInt(2), var4.nextInt(3) - 1);

         for (int var10 = 0; var10 < 4; var10++) {
            if (var1.d(var9) && this.f(var1, var9, this.Q())) {
               var2 = var9;
            }

            var9 = var2.a(var4.nextInt(3) - 1, var4.nextInt(2) - var4.nextInt(2), var4.nextInt(3) - 1);
         }

         if (var1.d(var9) && this.f(var1, var9, this.Q())) {
            var1.a(var9, this.Q(), 2);
         }
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return super.d(var1, var2) && this.f(var1, var2, this.Q());
   }

   @Override
   protected boolean c(Block var1) {
      return var1.o();
   }

   @Override
   public boolean f(World var1, BlockPos var2, IBlockState var3) {
      if (var2.o() >= 0 && var2.o() < 256) {
         IBlockState var4 = var1.p(var2.b());
         if (var4.c() == Blocks.bw) {
            return true;
         } else {
            return var4.c() == Blocks.d && var4.b(BlockDirt.a) == BlockDirt.DirtType.c ? true : var1.k(var2) < 13 && this.c(var4.c());
         }
      } else {
         return false;
      }
   }

   public boolean d(World var1, BlockPos var2, IBlockState var3, Random var4) {
      var1.g(var2);
      WorldGenBigMushroom var5 = null;
      if (this == Blocks.P) {
         var5 = new WorldGenBigMushroom(Blocks.bg);
      } else if (this == Blocks.Q) {
         var5 = new WorldGenBigMushroom(Blocks.bh);
      }

      if (var5 != null && var5.b(var1, var4, var2)) {
         return true;
      } else {
         var1.a(var2, var3, 3);
         return false;
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return var2.nextFloat() < 0.4;
   }

   @Override
   public void b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      this.d(var1, var3, var4, var2);
   }
}
