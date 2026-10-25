package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenHellLava extends WorldGenerator {
   private final Block a;
   private final boolean b;

   public WorldGenHellLava(Block var1, boolean var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      if (var1.p(var3.a()).c() != Blocks.aV) {
         return false;
      } else if (var1.p(var3).c().t() != Material.a && var1.p(var3).c() != Blocks.aV) {
         return false;
      } else {
         int var4 = 0;
         if (var1.p(var3.e()).c() == Blocks.aV) {
            var4++;
         }

         if (var1.p(var3.f()).c() == Blocks.aV) {
            var4++;
         }

         if (var1.p(var3.c()).c() == Blocks.aV) {
            var4++;
         }

         if (var1.p(var3.d()).c() == Blocks.aV) {
            var4++;
         }

         if (var1.p(var3.b()).c() == Blocks.aV) {
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

         if (var1.d(var3.b())) {
            var5++;
         }

         if (!this.b && var4 == 4 && var5 == 1 || var4 == 5) {
            var1.a(var3, this.a.Q(), 2);
            var1.a(this.a, var3, var2);
         }

         return true;
      }
   }
}
