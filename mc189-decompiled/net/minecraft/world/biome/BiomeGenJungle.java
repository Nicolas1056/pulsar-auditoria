package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraft.world.gen.feature.WorldGenMelon;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenTallGrass;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;

public class BiomeGenJungle extends BiomeGenBase {
   private boolean aD;
   private static final IBlockState aE = Blocks.r.Q().a(BlockOldLog.b, BlockPlanks.EnumType.d);
   private static final IBlockState aF = Blocks.t.Q().a(BlockOldLeaf.Q, BlockPlanks.EnumType.d).a(BlockLeaves.b, false);
   private static final IBlockState aG = Blocks.t.Q().a(BlockOldLeaf.Q, BlockPlanks.EnumType.a).a(BlockLeaves.b, false);

   public BiomeGenJungle(int var1, boolean var2) {
      super(var1);
      this.aD = var2;
      if (var2) {
         this.as.A = 2;
      } else {
         this.as.A = 50;
      }

      this.as.C = 25;
      this.as.B = 4;
      if (!var2) {
         this.at.add(new BiomeGenBase.SpawnListEntry(EntityOcelot.class, 2, 1, 1));
      }

      this.au.add(new BiomeGenBase.SpawnListEntry(EntityChicken.class, 10, 4, 4));
   }

   @Override
   public WorldGenAbstractTree a(Random var1) {
      if (var1.nextInt(10) == 0) {
         return this.aB;
      } else if (var1.nextInt(2) == 0) {
         return new WorldGenShrub(aE, aG);
      } else {
         return (WorldGenAbstractTree)(!this.aD && var1.nextInt(3) == 0
            ? new WorldGenMegaJungle(false, 10, 20, aE, aF)
            : new WorldGenTrees(false, 4 + var1.nextInt(7), aE, aF, true));
      }
   }

   @Override
   public WorldGenerator b(Random var1) {
      return var1.nextInt(4) == 0 ? new WorldGenTallGrass(BlockTallGrass.EnumType.c) : new WorldGenTallGrass(BlockTallGrass.EnumType.b);
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      super.a(var1, var2, var3);
      int var4 = var2.nextInt(16) + 8;
      int var5 = var2.nextInt(16) + 8;
      int var6 = var2.nextInt(var1.m(var3.a(var4, 0, var5)).o() * 2);
      new WorldGenMelon().b(var1, var2, var3.a(var4, var6, var5));
      WorldGenVines var9 = new WorldGenVines();

      for (int var10 = 0; var10 < 50; var10++) {
         var6 = var2.nextInt(16) + 8;
         short var7 = 128;
         int var8 = var2.nextInt(16) + 8;
         var9.b(var1, var2, var3.a(var6, 128, var8));
      }
   }
}
