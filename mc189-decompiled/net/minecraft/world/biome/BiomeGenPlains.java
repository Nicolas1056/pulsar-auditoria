package net.minecraft.world.biome;

import java.util.Random;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BiomeGenPlains extends BiomeGenBase {
   protected boolean aD;

   protected BiomeGenPlains(int var1) {
      super(var1);
      this.a(0.8F, 0.4F);
      this.a(e);
      this.au.add(new BiomeGenBase.SpawnListEntry(EntityHorse.class, 5, 2, 6));
      this.as.A = -999;
      this.as.B = 4;
      this.as.C = 10;
   }

   @Override
   public BlockFlower.EnumFlowerType a(Random var1, BlockPos var2) {
      double var3 = af.a(var2.n() / 200.0, var2.p() / 200.0);
      if (var3 < -0.8) {
         int var6 = var1.nextInt(4);
         switch (var6) {
            case 0:
               return BlockFlower.EnumFlowerType.g;
            case 1:
               return BlockFlower.EnumFlowerType.f;
            case 2:
               return BlockFlower.EnumFlowerType.i;
            case 3:
            default:
               return BlockFlower.EnumFlowerType.h;
         }
      } else if (var1.nextInt(3) > 0) {
         int var5 = var1.nextInt(3);
         if (var5 == 0) {
            return BlockFlower.EnumFlowerType.b;
         } else {
            return var5 == 1 ? BlockFlower.EnumFlowerType.e : BlockFlower.EnumFlowerType.j;
         }
      } else {
         return BlockFlower.EnumFlowerType.a;
      }
   }

   @Override
   public void a(World var1, Random var2, BlockPos var3) {
      double var4 = af.a((var3.n() + 8) / 200.0, (var3.p() + 8) / 200.0);
      if (var4 < -0.8) {
         this.as.B = 15;
         this.as.C = 5;
      } else {
         this.as.B = 4;
         this.as.C = 10;
         ag.a(BlockDoublePlant.EnumPlantType.c);

         for (int var6 = 0; var6 < 7; var6++) {
            int var7 = var2.nextInt(16) + 8;
            int var8 = var2.nextInt(16) + 8;
            int var9 = var2.nextInt(var1.m(var3.a(var7, 0, var8)).o() + 32);
            ag.b(var1, var2, var3.a(var7, var9, var8));
         }
      }

      if (this.aD) {
         ag.a(BlockDoublePlant.EnumPlantType.a);

         for (int var10 = 0; var10 < 10; var10++) {
            int var11 = var2.nextInt(16) + 8;
            int var12 = var2.nextInt(16) + 8;
            int var13 = var2.nextInt(var1.m(var3.a(var11, 0, var12)).o() + 32);
            ag.b(var1, var2, var3.a(var11, var13, var12));
         }
      }

      super.a(var1, var2, var3);
   }

   @Override
   protected BiomeGenBase d(int var1) {
      BiomeGenPlains var2 = new BiomeGenPlains(var1);
      var2.a("Sunflower Plains");
      var2.aD = true;
      var2.b(9286496);
      var2.aj = 14273354;
      return var2;
   }
}
