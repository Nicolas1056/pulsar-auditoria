package net.minecraft.world.chunk;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;

public class ChunkPrimer {
   private final short[] a = new short[65536];
   private final IBlockState b = Blocks.a.Q();

   public IBlockState a(int var1, int var2, int var3) {
      int var4 = var1 << 12 | var3 << 8 | var2;
      return this.a(var4);
   }

   public IBlockState a(int var1) {
      if (var1 >= 0 && var1 < this.a.length) {
         IBlockState var2 = Block.d.a(this.a[var1]);
         return var2 != null ? var2 : this.b;
      } else {
         throw new IndexOutOfBoundsException("The coordinate is out of range");
      }
   }

   public void a(int var1, int var2, int var3, IBlockState var4) {
      int var5 = var1 << 12 | var3 << 8 | var2;
      this.a(var5, var4);
   }

   public void a(int var1, IBlockState var2) {
      if (var1 >= 0 && var1 < this.a.length) {
         this.a[var1] = (short)Block.d.b(var2);
      } else {
         throw new IndexOutOfBoundsException("The coordinate is out of range");
      }
   }
}
