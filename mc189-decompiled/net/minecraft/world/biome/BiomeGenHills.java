package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenTaiga2;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenHills extends BiomeGenBase {
   private WorldGenerator aD = new WorldGenMinable(Blocks.be.Q().a(BlockSilverfish.a, BlockSilverfish.EnumType.a), 9);
   private WorldGenTaiga2 aE = new WorldGenTaiga2(false);
   private int aF = 0;
   private int aG = 1;
   private int aH = 2;
   private int aI = this.aF;

   protected BiomeGenHills(int var1, boolean var2) {
      super(var1);
      if (var2) {
         this.as.A = 3;
         this.aI = this.aG;
      }
   }

   @Override
   public WorldGenAbstractTree a(Random var1) {
      return (WorldGenAbstractTree)(var1.nextInt(3) > 0 ? this.aE : super.a(var1));
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      super.a(var1, var2, var3);
      int var4 = 3 + var2.nextInt(6);

      for (int var5 = 0; var5 < var4; var5++) {
         int var6 = var2.nextInt(16);
         int var7 = var2.nextInt(28) + 4;
         int var8 = var2.nextInt(16);
         BlockPos var9 = var3.a(var6, var7, var8);
         if (var1.p(var9).c() == Blocks.b) {
            var1.a(var9, Blocks.bP.Q(), 2);
         }
      }

      for (int var10 = 0; var10 < 7; var10++) {
         int var11 = var2.nextInt(16);
         int var12 = var2.nextInt(64);
         int var13 = var2.nextInt(16);
         this.aD.b(var1, var2, var3.a(var11, var12, var13));
      }
   }

   @Override
   public void a(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      this.ak = Blocks.c.Q();
      this.al = Blocks.d.Q();
      if ((var6 < -1.0 || var6 > 2.0) && this.aI == this.aH) {
         this.ak = Blocks.n.Q();
         this.al = Blocks.n.Q();
      } else if (var6 > 1.0 && this.aI != this.aG) {
         this.ak = Blocks.b.Q();
         this.al = Blocks.b.Q();
      }

      this.b(var1, var2, var3, var4, var5, var6);
   }

   private BiomeGenHills b(BiomeGenBase var1) {
      this.aI = this.aH;
      this.a(var1.ai, true);
      this.a(var1.ah + " M");
      this.a(new BiomeGenBase.Height(var1.an, var1.ao));
      this.a(var1.ap, var1.aq);
      return this;
   }

   @Override
   protected BiomeGenBase d(int var1) {
      return new BiomeGenHills(var1, false).b(this);
   }
}
