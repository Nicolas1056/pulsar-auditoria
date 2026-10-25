package net.minecraft.world;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public class ChunkCache implements IBlockAccess {
   protected int a;
   protected int b;
   protected Chunk[][] c;
   protected boolean d;
   protected World e;

   public ChunkCache(World var1, BlockPos var2, BlockPos var3, int var4) {
      this.e = var1;
      this.a = var2.n() - var4 >> 4;
      this.b = var2.p() - var4 >> 4;
      int var5 = var3.n() + var4 >> 4;
      int var6 = var3.p() + var4 >> 4;
      this.c = new Chunk[var5 - this.a + 1][var6 - this.b + 1];
      this.d = true;

      for (int var7 = this.a; var7 <= var5; var7++) {
         for (int var8 = this.b; var8 <= var6; var8++) {
            this.c[var7 - this.a][var8 - this.b] = var1.a(var7, var8);
         }
      }

      for (int var10 = var2.n() >> 4; var10 <= var3.n() >> 4; var10++) {
         for (int var11 = var2.p() >> 4; var11 <= var3.p() >> 4; var11++) {
            Chunk var9 = this.c[var10 - this.a][var11 - this.b];
            if (var9 != null && !var9.c(var2.o(), var3.o())) {
               this.d = false;
            }
         }
      }
   }

   @Override
   public boolean W() {
      return this.d;
   }

   @Override
   public TileEntity s(BlockPos var1) {
      int var2 = (var1.n() >> 4) - this.a;
      int var3 = (var1.p() >> 4) - this.b;
      return this.c[var2][var3].a(var1, Chunk.EnumCreateEntityType.a);
   }

   @Override
   public int b(BlockPos var1, int var2) {
      int var3 = this.a(EnumSkyBlock.a, var1);
      int var4 = this.a(EnumSkyBlock.b, var1);
      if (var4 < var2) {
         var4 = var2;
      }

      return var3 << 20 | var4 << 4;
   }

   @Override
   public IBlockState p(BlockPos var1) {
      if (var1.o() >= 0 && var1.o() < 256) {
         int var2 = (var1.n() >> 4) - this.a;
         int var3 = (var1.p() >> 4) - this.b;
         if (var2 >= 0 && var2 < this.c.length && var3 >= 0 && var3 < this.c[var2].length) {
            Chunk var4 = this.c[var2][var3];
            if (var4 != null) {
               return var4.g(var1);
            }
         }
      }

      return Blocks.a.Q();
   }

   @Override
   public BiomeGenBase b(BlockPos var1) {
      return this.e.b(var1);
   }

   private int a(EnumSkyBlock var1, BlockPos var2) {
      if (var1 == EnumSkyBlock.a && this.e.t.o()) {
         return 0;
      } else if (var2.o() >= 0 && var2.o() < 256) {
         if (this.p(var2).c().s()) {
            int var9 = 0;

            for (EnumFacing var7 : EnumFacing.values()) {
               int var8 = this.b(var1, var2.a(var7));
               if (var8 > var9) {
                  var9 = var8;
               }

               if (var9 >= 15) {
                  return var9;
               }
            }

            return var9;
         } else {
            int var3 = (var2.n() >> 4) - this.a;
            int var4 = (var2.p() >> 4) - this.b;
            return this.c[var3][var4].a(var1, var2);
         }
      } else {
         return var1.c;
      }
   }

   @Override
   public boolean d(BlockPos var1) {
      return this.p(var1).c().t() == Material.a;
   }

   public int b(EnumSkyBlock var1, BlockPos var2) {
      if (var2.o() >= 0 && var2.o() < 256) {
         int var3 = (var2.n() >> 4) - this.a;
         int var4 = (var2.p() >> 4) - this.b;
         return this.c[var3][var4].a(var1, var2);
      } else {
         return var1.c;
      }
   }

   @Override
   public int a(BlockPos var1, EnumFacing var2) {
      IBlockState var3 = this.p(var1);
      return var3.c().b(this, var1, var3, var2);
   }

   @Override
   public WorldType G() {
      return this.e.G();
   }
}
