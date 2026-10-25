package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenReed extends WorldGenerator {
   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      for (int var4 = 0; var4 < 20; var4++) {
         BlockPos var5 = var3.a(var2.nextInt(4) - var2.nextInt(4), 0, var2.nextInt(4) - var2.nextInt(4));
         if (var1.d(var5)) {
            BlockPos var6 = var5.b();
            if (var1.p(var6.e()).c().t() == Material.h
               || var1.p(var6.f()).c().t() == Material.h
               || var1.p(var6.c()).c().t() == Material.h
               || var1.p(var6.d()).c().t() == Material.h) {
               int var7 = 2 + var2.nextInt(var2.nextInt(3) + 1);

               for (int var8 = 0; var8 < var7; var8++) {
                  if (Blocks.aM.e(var1, var5)) {
                     var1.a(var5.b(var8), Blocks.aM.Q(), 2);
                  }
               }
            }
         }
      }

      return true;
   }
}
