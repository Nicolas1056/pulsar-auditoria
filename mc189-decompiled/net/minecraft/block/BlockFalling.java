package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockFalling extends Block {
   public static boolean N;

   public BlockFalling() {
      super(Material.p);
      this.a(CreativeTabs.b);
   }

   public BlockFalling(Material var1) {
      super(var1);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      var1.a(var2, this, this.a(var1));
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      var1.a(var2, this, this.a(var1));
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         this.f(var1, var2);
      }
   }

   private void f(World var1, BlockPos var2) {
      if (e(var1, var2.b()) && var2.o() >= 0) {
         byte var3 = 32;
         if (N || !var1.a(var2.a(-var3, -var3, -var3), var2.a((int)var3, (int)var3, (int)var3))) {
            var1.g(var2);
            BlockPos var5 = var2.b();

            while (e(var1, var5) && var5.o() > 0) {
               var5 = var5.b();
            }

            if (var5.o() > 0) {
               var1.a(var5.a(), this.Q());
            }
         } else if (!var1.D) {
            EntityFallingBlock var4 = new EntityFallingBlock(var1, var2.n() + 0.5, var2.o(), var2.p() + 0.5, var1.p(var2));
            this.a(var4);
            var1.d(var4);
         }
      }
   }

   protected void a(EntityFallingBlock var1) {
   }

   @Override
   public int a(World var1) {
      return 2;
   }

   public static boolean e(World var0, BlockPos var1) {
      Block var2 = var0.p(var1).c();
      Material var3 = var2.J;
      return var2 == Blocks.ab || var3 == Material.a || var3 == Material.h || var3 == Material.i;
   }

   public void a_(World var1, BlockPos var2) {
   }
}
