package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBlockBlob;
import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
import net.minecraft.world.gen.feature.WorldGenTaiga1;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenTaiga extends BiomeGenBase {
   private static final WorldGenTaiga1 aD = new WorldGenTaiga1();
   private static final WorldGenTaiga2 aE = new WorldGenTaiga2(false);
   private static final WorldGenMegaPineTree aF = new WorldGenMegaPineTree(false, false);
   private static final WorldGenMegaPineTree aG = new WorldGenMegaPineTree(false, true);
   private static final WorldGenBlockBlob aH = new WorldGenBlockBlob(Blocks.Y, 0);
   private int aI;

   public BiomeGenTaiga(int var1, int var2) {
      super(var1);
      this.aI = var2;
      this.au.add(new BiomeGenBase.SpawnListEntry(EntityWolf.class, 8, 4, 4));
      this.as.A = 10;
      if (var2 != 1 && var2 != 2) {
         this.as.C = 1;
         this.as.E = 1;
      } else {
         this.as.C = 7;
         this.as.D = 1;
         this.as.E = 3;
      }
   }

   @Override
   public WorldGenAbstractTree a(Random var1) {
      if ((this.aI == 1 || this.aI == 2) && var1.nextInt(3) == 0) {
         return this.aI != 2 && var1.nextInt(13) != 0 ? aF : aG;
      } else {
         return (WorldGenAbstractTree)(var1.nextInt(3) == 0 ? aD : aE);
      }
   }

   @Override
   public WorldGenerator b(Random var1) {
      return var1.nextInt(5) > 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.c) : new WorldGenTallGrass(BlockTallGrass.EnumType.b);
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      if (this.aI == 1 || this.aI == 2) {
         int var4 = var2.nextInt(3);

         for (int var5 = 0; var5 < var4; var5++) {
            int var6 = var2.nextInt(16) + 8;
            int var7 = var2.nextInt(16) + 8;
            BlockPos var8 = var1.m(var3.a(var6, 0, var7));
            aH.b(var1, var2, var8);
         }
      }

      ag.a(BlockDoublePlant.EnumPlantType.d);

      for (int var9 = 0; var9 < 7; var9++) {
         int var10 = var2.nextInt(16) + 8;
         int var11 = var2.nextInt(16) + 8;
         int var12 = var2.nextInt(var1.m(var3.a(var10, 0, var11)).o() + 32);
         ag.b(var1, var2, var3.a(var10, var12, var11));
      }

      super.a(var1, var2, var3);
   }

   @Override
   public void a(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      if (this.aI == 1 || this.aI == 2) {
         this.ak = Blocks.c.Q();
         this.al = Blocks.d.Q();
         if (var6 > 1.75) {
            this.ak = Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.b);
         } else if (var6 > -0.95) {
            this.ak = Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.c);
         }
      }

      this.b(var1, var2, var3, var4, var5, var6);
   }

   @Override
   protected BiomeGenBase d(int var1) {
      return this.az == BiomeGenBase.V.az
         ? new BiomeGenTaiga(var1, 2).a(5858897, true).a("Mega Spruce Taiga").a(5159473).a(0.25F, 0.8F).a(new BiomeGenBase.Height(this.an, this.ao))
         : super.d(var1);
   }
}
