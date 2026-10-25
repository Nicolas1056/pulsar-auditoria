package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenLiquids extends WorldGenerator {
   private Block a;

   public WorldGenLiquids(Block var1) {
      this.a = var1;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      if (var1.p(var3.a()).c() != Blocks.b) {
         return false;
      } else if (var1.p(var3.b()).c() != Blocks.b) {
         return false;
      } else if (var1.p(var3).c().t() != Material.a && var1.p(var3).c() != Blocks.b) {
         return false;
      } else {
         int var4 = 0;
         if (var1.p(var3.e()).c() == Blocks.b) {
            var4++;
         }

         if (var1.p(var3.f()).c() == Blocks.b) {
            var4++;
         }

         if (var1.p(var3.c()).c() == Blocks.b) {
            var4++;
         }

         if (var1.p(var3.d()).c() == Blocks.b) {
            var4++;
         }

         int var5 = 0;
         if (var1.d(var3.e())) {
            var5++;
         }

         if (var1.d(var3.f())) {
            var5++;
         }

         if (var1.d(var3.c())) {
            var5++;
         }

         if (var1.d(var3.d())) {
            var5++;
         }

         if (var4 == 3 && var5 == 1) {
            var1.a(var3, this.a.Q(), 2);
            var1.a(this.a, var3, var2);
         }

         return true;
      }
   }
}
