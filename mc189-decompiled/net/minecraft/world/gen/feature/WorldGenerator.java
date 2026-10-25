package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class WorldGenerator {
   private final boolean a;

   public WorldGenerator() {
      this(false);
   }

   public WorldGenerator(boolean var1) {
      this.a = var1;
   }

   public abstract boolean b(World var1, Random var2, BlockPos var3);

   public void e() {
   }

   protected void a(World var1, BlockPos var2, IBlockState var3) {
      if (this.a) {
         var1.a(var2, var3, 3);
      } else {
         var1.a(var2, var3, 2);
      }
   }
}
