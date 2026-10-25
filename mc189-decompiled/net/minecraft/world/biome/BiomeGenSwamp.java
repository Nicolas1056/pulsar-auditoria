package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenSwamp extends BiomeGenBase {
   protected BiomeGenSwamp(int var1) {
      super(var1);
      this.as.A = 2;
      this.as.B = 1;
      this.as.D = 1;
      this.as.E = 8;
      this.as.F = 10;
      this.as.J = 1;
      this.as.z = 4;
      this.as.I = 0;
      this.as.H = 0;
      this.as.C = 5;
      this.ar = 14745518;
      this.at.add(new BiomeGenBase.SpawnListEntry(EntitySlime.class, 1, 1, 1));
   }

   @Override
   public WorldGenAbstractTree a(Random var1) {
      return this.aC;
   }

   @Override
   public int b(BlockPos var1) {
      double var2 = af.a(var1.n() * 0.0225, var1.p() * 0.0225);
      return var2 < -0.1 ? 5011004 : 6975545;
   }

   @Override
   public int c(BlockPos var1) {
      return 6975545;
   }

   @Override
   public BlockFlower.EnumFlowerType a(Random var1, BlockPos var2) {
      return BlockFlower.EnumFlowerType.c;
   }

   @Override
   public void a(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      double var8 = af.a(var4 * 0.25, var5 * 0.25);
      if (var8 > 0.0) {
         int var10 = var4 & 15;
         int var11 = var5 & 15;

         for (int var12 = 255; var12 >= 0; var12--) {
            if (var3.a(var11, var12, var10).c().t() != Material.a) {
               if (var12 == 62 && var3.a(var11, var12, var10).c() != Blocks.j) {
                  var3.a(var11, var12, var10, Blocks.j.Q());
                  if (var8 < 0.12) {
                     var3.a(var11, var12 + 1, var10, Blocks.bx.Q());
                  }
               }
               break;
            }
         }
      }

      this.b(var1, var2, var3, var4, var5, var6);
   }
}
