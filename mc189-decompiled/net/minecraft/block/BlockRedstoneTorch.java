package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneTorch extends BlockTorch {
   private static Map<World, List<BlockRedstoneTorch.Toggle>> b = Maps.newHashMap();
   private final boolean N;

   private boolean a(World var1, BlockPos var2, boolean var3) {
      if (!b.containsKey(var1)) {
         b.put(var1, Lists.newArrayList());
      }

      List var4 = b.get(var1);
      if (var3) {
         var4.add(new BlockRedstoneTorch.Toggle(var2, var1.K()));
      }

      int var5 = 0;

      for (int var6 = 0; var6 < var4.size(); var6++) {
         BlockRedstoneTorch.Toggle var7 = (BlockRedstoneTorch.Toggle)var4.get(var6);
         if (var7.a.equals(var2)) {
            if (++var5 >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   protected BlockRedstoneTorch(boolean var1) {
      this.N = var1;
      this.a(true);
      this.a(null);
   }

   @Override
   public int a(World var1) {
      return 2;
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      if (this.N) {
         for (EnumFacing var7 : EnumFacing.values()) {
            var1.c(var2.a(var7), this);
         }
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      if (this.N) {
         for (EnumFacing var7 : EnumFacing.values()) {
            var1.c(var2.a(var7), this);
         }
      }
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return this.N && var3.b(a) != var4 ? 15 : 0;
   }

   private boolean g(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = var3.b(a).d();
      return var1.b(var2.a(var4), var4);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      boolean var5 = this.g(var1, var2, var3);
      List var6 = b.get(var1);

      while (var6 != null && !var6.isEmpty() && var1.K() - ((BlockRedstoneTorch.Toggle)var6.get(0)).b > 60L) {
         var6.remove(0);
      }

      if (this.N) {
         if (var5) {
            var1.a(var2, Blocks.aE.Q().a(a, var3.b(a)), 3);
            if (this.a(var1, var2, true)) {
               var1.a(var2.n() + 0.5F, var2.o() + 0.5F, var2.p() + 0.5F, "random.fizz", 0.5F, 2.6F + (var1.s.nextFloat() - var1.s.nextFloat()) * 0.8F);

               for (int var7 = 0; var7 < 5; var7++) {
                  double var8 = var2.n() + var4.nextDouble() * 0.6 + 0.2;
                  double var10 = var2.o() + var4.nextDouble() * 0.6 + 0.2;
                  double var12 = var2.p() + var4.nextDouble() * 0.6 + 0.2;
                  var1.a(EnumParticleTypes.l, var8, var10, var12, 0.0, 0.0, 0.0);
               }

               var1.a(var2, var1.p(var2).c(), 160);
            }
         }
      } else if (!var5 && !this.a(var1, var2, false)) {
         var1.a(var2, Blocks.aF.Q().a(a, var3.b(a)), 3);
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!this.e(var1, var2, var3)) {
         if (this.N == this.g(var1, var2, var3)) {
            var1.a(var2, this, this.a(var1));
         }
      }
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var4 == EnumFacing.a ? this.a(var1, var2, var3, var4) : 0;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.aF);
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.N) {
         double var5 = var2.n() + 0.5 + (var4.nextDouble() - 0.5) * 0.2;
         double var7 = var2.o() + 0.7 + (var4.nextDouble() - 0.5) * 0.2;
         double var9 = var2.p() + 0.5 + (var4.nextDouble() - 0.5) * 0.2;
         EnumFacing var11 = var3.b(a);
         if (var11.k().c()) {
            EnumFacing var12 = var11.d();
            double var13 = 0.27;
            var5 += 0.27 * var12.g();
            var7 += 0.22;
            var9 += 0.27 * var12.i();
         }

         var1.a(EnumParticleTypes.E, var5, var7, var9, 0.0, 0.0, 0.0);
      }
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(Blocks.aF);
   }

   @Override
   public boolean b(Block var1) {
      return var1 == Blocks.aE || var1 == Blocks.aF;
   }

   static class Toggle {
      BlockPos a;
      long b;

      public Toggle(BlockPos var1, long var2) {
         this.a = var1;
         this.b = var2;
      }
   }
}
