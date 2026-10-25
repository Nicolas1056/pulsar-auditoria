package net.minecraft.world;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

public final class SpawnerAnimals {
   private static final int a = (int)Math.pow(17.0, 2.0);
   private final Set<ChunkCoordIntPair> b = Sets.newHashSet();

   public int a(WorldServer var1, boolean var2, boolean var3, boolean var4) {
      if (!var2 && !var3) {
         return 0;
      } else {
         this.b.clear();
         int var5 = 0;

         for (EntityPlayer var7 : var1.j) {
            if (!var7.v()) {
               int var8 = MathHelper.c(var7.s / 16.0);
               int var9 = MathHelper.c(var7.u / 16.0);
               byte var10 = 8;

               for (int var11 = -var10; var11 <= var10; var11++) {
                  for (int var12 = -var10; var12 <= var10; var12++) {
                     boolean var13 = var11 == -var10 || var11 == var10 || var12 == -var10 || var12 == var10;
                     ChunkCoordIntPair var14 = new ChunkCoordIntPair(var11 + var8, var12 + var9);
                     if (!this.b.contains(var14)) {
                        var5++;
                        if (!var13 && var1.af().a(var14)) {
                           this.b.add(var14);
                        }
                     }
                  }
               }
            }
         }

         int var36 = 0;
         BlockPos var37 = var1.M();

         for (EnumCreatureType var41 : EnumCreatureType.values()) {
            if ((!var41.d() || var3) && (var41.d() || var2) && (!var41.e() || var4)) {
               int var42 = var1.a(var41.a());
               int var43 = var41.b() * var5 / a;
               if (var42 <= var43) {
                  label129:
                  for (ChunkCoordIntPair var15 : this.b) {
                     BlockPos var16 = a(var1, var15.a, var15.b);
                     int var17 = var16.n();
                     int var18 = var16.o();
                     int var19 = var16.p();
                     Block var20 = var1.p(var16).c();
                     if (!var20.v()) {
                        int var21 = 0;

                        for (int var22 = 0; var22 < 3; var22++) {
                           int var23 = var17;
                           int var24 = var18;
                           int var25 = var19;
                           byte var26 = 6;
                           BiomeGenBase.SpawnListEntry var27 = null;
                           IEntityLivingData var28 = null;

                           for (int var29 = 0; var29 < 4; var29++) {
                              var23 += var1.s.nextInt(var26) - var1.s.nextInt(var26);
                              var24 += var1.s.nextInt(1) - var1.s.nextInt(1);
                              var25 += var1.s.nextInt(var26) - var1.s.nextInt(var26);
                              BlockPos var30 = new BlockPos(var23, var24, var25);
                              float var31 = var23 + 0.5F;
                              float var32 = var25 + 0.5F;
                              if (!var1.b(var31, var24, var32, 24.0) && !(var37.c(var31, var24, var32) < 576.0)) {
                                 if (var27 == null) {
                                    var27 = var1.a(var41, var30);
                                    if (var27 == null) {
                                       break;
                                    }
                                 }

                                 if (var1.a(var41, var27, var30) && a(EntitySpawnPlacementRegistry.a(var27.b), var1, var30)) {
                                    EntityLiving var33;
                                    try {
                                       var33 = var27.b.getConstructor(World.class).newInstance(var1);
                                    } catch (Exception var35) {
                                       var35.printStackTrace();
                                       return var36;
                                    }

                                    var33.b(var31, var24, var32, var1.s.nextFloat() * 360.0F, 0.0F);
                                    if (var33.bR() && var33.bS()) {
                                       var28 = var33.a(var1.E(new BlockPos(var33)), var28);
                                       if (var33.bS()) {
                                          var21++;
                                          var1.d(var33);
                                       }

                                       if (var21 >= var33.bV()) {
                                          continue label129;
                                       }
                                    }

                                    var36 += var21;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }

         return var36;
      }
   }

   protected static BlockPos a(World var0, int var1, int var2) {
      Chunk var3 = var0.a(var1, var2);
      int var4 = var1 * 16 + var0.s.nextInt(16);
      int var5 = var2 * 16 + var0.s.nextInt(16);
      int var6 = MathHelper.c(var3.f(new BlockPos(var4, 0, var5)) + 1, 16);
      int var7 = var0.s.nextInt(var6 > 0 ? var6 : var3.g() + 16 - 1);
      return new BlockPos(var4, var7, var5);
   }

   public static boolean a(EntityLiving.SpawnPlacementType var0, World var1, BlockPos var2) {
      if (!var1.af().a(var2)) {
         return false;
      } else {
         Block var3 = var1.p(var2).c();
         if (var0 == EntityLiving.SpawnPlacementType.c) {
            return var3.t().d() && var1.p(var2.b()).c().t().d() && !var1.p(var2.a()).c().v();
         } else {
            BlockPos var4 = var2.b();
            if (!World.a(var1, var4)) {
               return false;
            } else {
               Block var5 = var1.p(var4).c();
               boolean var6 = var5 != Blocks.h && var5 != Blocks.cv;
               return var6 && !var3.v() && !var3.t().d() && !var1.p(var2.a()).c().v();
            }
         }
      }
   }

   public static void a(World var0, BiomeGenBase var1, int var2, int var3, int var4, int var5, Random var6) {
      List var7 = var1.a(EnumCreatureType.b);
      if (!var7.isEmpty()) {
         while (var6.nextFloat() < var1.g()) {
            BiomeGenBase.SpawnListEntry var8 = WeightedRandom.a(var0.s, var7);
            int var9 = var8.c + var6.nextInt(1 + var8.d - var8.c);
            IEntityLivingData var10 = null;
            int var11 = var2 + var6.nextInt(var4);
            int var12 = var3 + var6.nextInt(var5);
            int var13 = var11;
            int var14 = var12;

            for (int var15 = 0; var15 < var9; var15++) {
               boolean var16 = false;

               for (int var17 = 0; !var16 && var17 < 4; var17++) {
                  BlockPos var18 = var0.r(new BlockPos(var11, 0, var12));
                  if (a(EntityLiving.SpawnPlacementType.a, var0, var18)) {
                     EntityLiving var19;
                     try {
                        var19 = var8.b.getConstructor(World.class).newInstance(var0);
                     } catch (Exception var21) {
                        var21.printStackTrace();
                        continue;
                     }

                     var19.b(var11 + 0.5F, var18.o(), var12 + 0.5F, var6.nextFloat() * 360.0F, 0.0F);
                     var0.d(var19);
                     var10 = var19.a(var0.E(new BlockPos(var19)), var10);
                     var16 = true;
                  }

                  var11 += var6.nextInt(5) - var6.nextInt(5);

                  for (var12 += var6.nextInt(5) - var6.nextInt(5);
                     var11 < var2 || var11 >= var2 + var4 || var12 < var3 || var12 >= var3 + var4;
                     var12 = var14 + var6.nextInt(5) - var6.nextInt(5)
                  ) {
                     var11 = var13 + var6.nextInt(5) - var6.nextInt(5);
                  }
               }
            }
         }
      }
   }
}
