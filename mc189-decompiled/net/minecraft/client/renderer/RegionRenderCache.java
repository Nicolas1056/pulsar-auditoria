package net.minecraft.client.renderer;

import java.util.Arrays;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.Vec3i;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class RegionRenderCache extends ChunkCache {
   private static final IBlockState f = Blocks.a.Q();
   private final BlockPos g;
   private int[] h;
   private IBlockState[] i;

   public RegionRenderCache(World var1, BlockPos var2, BlockPos var3, int var4) {
      super(var1, var2, var3, var4);
      this.g = var2.b(new Vec3i(var4, var4, var4));
      short var5 = 8000;
      this.h = new int[8000];
      Arrays.fill(this.h, -1);
      this.i = new IBlockState[8000];
   }

   @Override
   public TileEntity s(BlockPos var1) {
      int var2 = (var1.n() >> 4) - this.a;
      int var3 = (var1.p() >> 4) - this.b;
      return this.c[var2][var3].a(var1, Chunk.EnumCreateEntityType.b);
   }

   @Override
   public int b(BlockPos var1, int var2) {
      int var3 = this.e(var1);
      int var4 = this.h[var3];
      if (var4 == -1) {
         var4 = super.b(var1, var2);
         this.h[var3] = var4;
      }

      return var4;
   }

   @Override
   public IBlockState p(BlockPos var1) {
      int var2 = this.e(var1);
      IBlockState var3 = this.i[var2];
      if (var3 == null) {
         var3 = this.c(var1);
         this.i[var2] = var3;
      }

      return var3;
   }

   private IBlockState c(BlockPos var1) {
      if (var1.o() >= 0 && var1.o() < 256) {
         int var2 = (var1.n() >> 4) - this.a;
         int var3 = (var1.p() >> 4) - this.b;
         return this.c[var2][var3].g(var1);
      } else {
         return f;
      }
   }

   private int e(BlockPos var1) {
      int var2 = var1.n() - this.g.n();
      int var3 = var1.o() - this.g.o();
      int var4 = var1.p() - this.g.p();
      return var2 * 400 + var4 * 20 + var3;
   }
}
