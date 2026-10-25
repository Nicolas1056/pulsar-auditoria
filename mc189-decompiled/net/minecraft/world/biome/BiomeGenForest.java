package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCanopyTree;
import net.minecraft.world.gen.feature.WorldGenForest;

public class BiomeGenForest extends BiomeGenBase {
   private int aG;
   protected static final WorldGenForest aD = new WorldGenForest(false, true);
   protected static final WorldGenForest aE = new WorldGenForest(false, false);
   protected static final WorldGenCanopyTree aF = new WorldGenCanopyTree(false);

   public BiomeGenForest(int var1, int var2) {
      super(var1);
      this.aG = var2;
      this.as.A = 10;
      this.as.C = 2;
      if (this.aG == 1) {
         this.as.A = 6;
         this.as.B = 100;
         this.as.C = 1;
      }

      this.a(5159473);
      this.a(0.7F, 0.8F);
      if (this.aG == 2) {
         this.aj = 353825;
         this.ai = 3175492;
         this.a(0.6F, 0.6F);
      }

      if (this.aG == 0) {
         this.au.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 5, 4, 4));
      }

      if (this.aG == 3) {
         this.as.A = -999;
      }
   }

   @Override
   protected BiomeGenBase a(int var1, boolean var2) {
      if (this.aG == 2) {
         this.aj = 353825;
         this.ai = var1;
         if (var2) {
            this.aj = (this.aj & 16711422) >> 1;
         }

         return this;
      } else {
         return super.a(var1, var2);
      }
   }

   @Override
   public WorldGenAbstractTree a(Random var1) {
      if (this.aG == 3 && var1.nextInt(3) > 0) {
         return aF;
      } else {
         return (WorldGenAbstractTree)(this.aG != 2 && var1.nextInt(5) != 0 ? this.aA : aE);
      }
   }

   @Override
   public BlockFlower.EnumFlowerType a(Random var1, BlockPos var2) {
      if (this.aG == 1) {
         double var3 = MathHelper.a((1.0 + af.a(var2.n() / 48.0, var2.p() / 48.0)) / 2.0, 0.0, 0.9999);
         BlockFlower.EnumFlowerType var5 = BlockFlower.EnumFlowerType.values()[(int)(var3 * BlockFlower.EnumFlowerType.values().length)];
         return var5 == BlockFlower.EnumFlowerType.c ? BlockFlower.EnumFlowerType.b : var5;
      } else {
         return super.a(var1, var2);
      }
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      if (this.aG == 3) {
         for (int var4 = 0; var4 < 4; var4++) {
            for (int var5 = 0; var5 < 4; var5++) {
               int var6 = var4 * 4 + 1 + 8 + var2.nextInt(3);
               int var7 = var5 * 4 + 1 + 8 + var2.nextInt(3);
               BlockPos var8 = var1.m(var3.a(var6, 0, var7));
               if (var2.nextInt(20) == 0) {
                  WorldGenBigMushroom var9 = new WorldGenBigMushroom();
                  var9.b(var1, var2, var8);
               } else {
                  WorldGenAbstractTree var16 = this.a(var2);
                  var16.e();
                  if (var16.b(var1, var2, var8)) {
                     var16.a(var1, var2, var8);
                  }
               }
            }
         }
      }

      int var11 = var2.nextInt(5) - 3;
      if (this.aG == 1) {
         var11 += 2;
      }

      for (int var12 = 0; var12 < var11; var12++) {
         int var13 = var2.nextInt(3);
         if (var13 == 0) {
            ag.a(BlockDoublePlant.EnumPlantType.b);
         } else if (var13 == 1) {
            ag.a(BlockDoublePlant.EnumPlantType.e);
         } else if (var13 == 2) {
            ag.a(BlockDoublePlant.EnumPlantType.f);
         }

         for (int var14 = 0; var14 < 5; var14++) {
            int var15 = var2.nextInt(16) + 8;
            int var17 = var2.nextInt(16) + 8;
            int var10 = var2.nextInt(var1.m(var3.a(var15, 0, var17)).o() + 32);
            if (ag.b(var1, var2, new BlockPos(var3.n() + var15, var10, var3.p() + var17))) {
               break;
            }
         }
      }

      super.a(var1, var2, var3);
   }

   @Override
   public int b(BlockPos var1) {
      int var2 = super.b(var1);
      return this.aG == 3 ? (var2 & 16711422) + 2634762 >> 1 : var2;
   }

   @Override
   protected BiomeGenBase d(int var1) {
      if (this.az == BiomeGenBase.t.az) {
         BiomeGenForest var2 = new BiomeGenForest(var1, 1);
         var2.a(new BiomeGenBase.Height(this.an, this.ao + 0.2F));
         var2.a("Flower Forest");
         var2.a(6976549, true);
         var2.a(8233509);
         return var2;
      } else {
         return this.az != BiomeGenBase.Q.az && this.az != BiomeGenBase.R.az ? new BiomeGenMutated(var1, this) {
            @Override
            public void a(World var1, Random var2, BlockPos var3) {
               this.aE.a(var1, var2, var3);
            }
         } : new BiomeGenMutated(var1, this) {
            @Override
            public WorldGenAbstractTree a(Random var1) {
               return var1.nextBoolean() ? BiomeGenForest.aD : BiomeGenForest.aE;
            }
         };
      }
   }
}
