package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenAbstractTree extends WorldGenerator {
   public WorldGenAbstractTree(boolean var1) {
      super(var1);
   }

   protected boolean a(Block var1) {
      Material var2 = var1.t();
      return var2 == Material.a
         || var2 == Material.j
         || var1 == Blocks.c
         || var1 == Blocks.d
         || var1 == Blocks.r
         || var1 == Blocks.s
         || var1 == Blocks.g
         || var1 == Blocks.bn;
   }

   public void a(World var1, Random var2, BlockPos var3) {
   }

   protected void a(World var1, BlockPos var2) {
      if (var1.p(var2).c() != Blocks.d) {
         this.a(var1, var2, Blocks.d.Q());
      }
   }
}
