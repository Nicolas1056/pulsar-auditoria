package net.minecraft.world.biome;

import com.google.common.collect.Lists;
import java.util.Random;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class BiomeGenMutated extends BiomeGenBase {
   protected BiomeGenBase aE;

   public BiomeGenMutated(int var1, BiomeGenBase var2) {
      super(var1);
      this.aE = var2;
      this.a(var2.ai, true);
      this.ah = var2.ah + " M";
      this.ak = var2.ak;
      this.al = var2.al;
      this.am = var2.am;
      this.an = var2.an;
      this.ao = var2.ao;
      this.ap = var2.ap;
      this.aq = var2.aq;
      this.ar = var2.ar;
      this.ax = var2.ax;
      this.ay = var2.ay;
      this.au = Lists.newArrayList(var2.au);
      this.at = Lists.newArrayList(var2.at);
      this.aw = Lists.newArrayList(var2.aw);
      this.av = Lists.newArrayList(var2.av);
      this.ap = var2.ap;
      this.aq = var2.aq;
      this.an = var2.an + 0.1F;
      this.ao = var2.ao + 0.2F;
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      this.aE.as.a(var1, var2, this, var3);
   }

   @Override
   public void a(World var1, Random var2, ChunkPrimer var3, int var4, int var5, double var6) {
      this.aE.a(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public float g() {
      return this.aE.g();
   }

   @Override
   public WorldGenAbstractTree a(Random var1) {
      return this.aE.a(var1);
   }

   @Override
   public int c(BlockPos var1) {
      return this.aE.c(var1);
   }

   @Override
   public int b(BlockPos var1) {
      return this.aE.b(var1);
   }

   @Override
   public Class<? extends BiomeGenBase> l() {
      return this.aE.l();
   }

   @Override
   public boolean a(BiomeGenBase var1) {
      return this.aE.a(var1);
   }

   @Override
   public BiomeGenBase.TempCategory m() {
      return this.aE.m();
   }
}
