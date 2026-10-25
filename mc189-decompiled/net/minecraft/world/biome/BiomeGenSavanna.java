package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenSavannaTree;

public class BiomeGenSavanna extends BiomeGenBase {
   private static final WorldGenSavannaTree aD = new WorldGenSavannaTree(false);

   protected BiomeGenSavanna(int var1) {
      super(var1);
      this.au.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 1, 2, 6));
      this.as.A = 1;
      this.as.B = 4;
      this.as.C = 20;
   }

   @Override
   public WorldGenAbstractTree a(Random var1) {
      return (WorldGenAbstractTree)(var1.nextInt(5) > 0 ? aD : this.aA);
   }

   @Override
   protected BiomeGenBase d(int var1) {
      BiomeGenSavanna.Mutated var2 = new BiomeGenSavanna.Mutated(var1, this);
      var2.ap = (this.ap + 1.0F) * 0.5F;
      var2.an = this.an * 0.5F + 0.3F;
      var2.ao = this.ao * 0.5F + 1.2F;
      return var2;
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      ag.a(BlockDoublePlant.EnumPlantType.c);

      for (int var4 = 0; var4 < 7; var4++) {
         int var5 = var2.nextInt(16) + 8;
         int var6 = var2.nextInt(16) + 8;
         int var7 = var2.nextInt(var1.m(var3.a(var5, 0, var6)).o() + 32);
         ag.b(var1, var2, var3.a(var5, var7, var6));
      }

      super.a(var1, var2, var3);
   }

   public static class Mutated extends BiomeGenMutated {
      public Mutated(int var1, BiomeGenBase var2) {
         super(var1, var2);
         this.as.A = 2;
         this.as.B = 2;
         this.as.C = 5;
      }

      @Override
      public void a(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
         this.ak = Blocks.c.Q();
         this.al = Blocks.d.Q();
         if (var6 > 1.75) {
            this.ak = Blocks.b.Q();
            this.al = Blocks.b.Q();
         } else if (var6 > -0.5) {
            this.ak = Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.b);
         }

         this.b(var1, var2, var3, var4, var5, var6);
      }

      @Override
      public void a(World var1, Random var2, BlockPos var3) {
         this.as.a(var1, var2, this, var3);
      }
   }
}
