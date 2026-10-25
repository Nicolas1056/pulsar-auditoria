package net.minecraft.world.gen.feature;

import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHugeMushroom;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class WorldGenBigMushroom extends WorldGenerator {
   private Block a;

   public WorldGenBigMushroom(Block var1) {
      super(true);
      this.a = var1;
   }

   public WorldGenBigMushroom() {
      super(false);
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      if (this.a == null) {
         this.a = var2.nextBoolean() ? Blocks.bg : Blocks.bh;
      }

      int var4 = var2.nextInt(3) + 4;
      boolean var5 = true;
      if (var3.o() >= 1 && var3.o() + var4 + 1 < 256) {
         for (int var6 = var3.o(); var6 <= var3.o() + 1 + var4; var6++) {
            byte var7 = 3;
            if (var6 <= var3.o() + 3) {
               var7 = 0;
            }

            BlockPos.MutableBlockPos var8 = new BlockPos.MutableBlockPos();

            for (int var9 = var3.n() - var7; var9 <= var3.n() + var7 && var5; var9++) {
               for (int var10 = var3.p() - var7; var10 <= var3.p() + var7 && var5; var10++) {
                  if (var6 >= 0 && var6 < 256) {
                     Block var11 = var1.p(var8.c(var9, var6, var10)).c();
                     if (var11.t() != Material.a && var11.t() != Material.j) {
                        var5 = false;
                     }
                  } else {
                     var5 = false;
                  }
               }
            }
         }

         if (!var5) {
            return false;
         } else {
            Block var19 = var1.p(var3.b()).c();
            if (var19 != Blocks.d && var19 != Blocks.c && var19 != Blocks.bw) {
               return false;
            } else {
               int var20 = var3.o() + var4;
               if (this.a == Blocks.bh) {
                  var20 = var3.o() + var4 - 3;
               }

               for (int var21 = var20; var21 <= var3.o() + var4; var21++) {
                  int var23 = 1;
                  if (var21 < var3.o() + var4) {
                     var23++;
                  }

                  if (this.a == Blocks.bg) {
                     var23 = 3;
                  }

                  int var25 = var3.n() - var23;
                  int var26 = var3.n() + var23;
                  int var12 = var3.p() - var23;
                  int var13 = var3.p() + var23;

                  for (int var14 = var25; var14 <= var26; var14++) {
                     for (int var15 = var12; var15 <= var13; var15++) {
                        int var16 = 5;
                        if (var14 == var25) {
                           var16--;
                        } else if (var14 == var26) {
                           var16++;
                        }

                        if (var15 == var12) {
                           var16 -= 3;
                        } else if (var15 == var13) {
                           var16 += 3;
                        }

                        BlockHugeMushroom.EnumType var17 = BlockHugeMushroom.EnumType.a(var16);
                        if (this.a == Blocks.bg || var21 < var3.o() + var4) {
                           if ((var14 == var25 || var14 == var26) && (var15 == var12 || var15 == var13)) {
                              continue;
                           }

                           if (var14 == var3.n() - (var23 - 1) && var15 == var12) {
                              var17 = BlockHugeMushroom.EnumType.a;
                           }

                           if (var14 == var25 && var15 == var3.p() - (var23 - 1)) {
                              var17 = BlockHugeMushroom.EnumType.a;
                           }

                           if (var14 == var3.n() + (var23 - 1) && var15 == var12) {
                              var17 = BlockHugeMushroom.EnumType.c;
                           }

                           if (var14 == var26 && var15 == var3.p() - (var23 - 1)) {
                              var17 = BlockHugeMushroom.EnumType.c;
                           }

                           if (var14 == var3.n() - (var23 - 1) && var15 == var13) {
                              var17 = BlockHugeMushroom.EnumType.g;
                           }

                           if (var14 == var25 && var15 == var3.p() + (var23 - 1)) {
                              var17 = BlockHugeMushroom.EnumType.g;
                           }

                           if (var14 == var3.n() + (var23 - 1) && var15 == var13) {
                              var17 = BlockHugeMushroom.EnumType.i;
                           }

                           if (var14 == var26 && var15 == var3.p() + (var23 - 1)) {
                              var17 = BlockHugeMushroom.EnumType.i;
                           }
                        }

                        if (var17 == BlockHugeMushroom.EnumType.e && var21 < var3.o() + var4) {
                           var17 = BlockHugeMushroom.EnumType.k;
                        }

                        if (var3.o() >= var3.o() + var4 - 1 || var17 != BlockHugeMushroom.EnumType.k) {
                           BlockPos var18 = new BlockPos(var14, var21, var15);
                           if (!var1.p(var18).c().o()) {
                              this.a(var1, var18, this.a.Q().a(BlockHugeMushroom.a, var17));
                           }
                        }
                     }
                  }
               }

               for (int var22 = 0; var22 < var4; var22++) {
                  Block var24 = var1.p(var3.b(var22)).c();
                  if (!var24.o()) {
                     this.a(var1, var3.b(var22), this.a.Q().a(BlockHugeMushroom.a, BlockHugeMushroom.EnumType.j));
                  }
               }

               return true;
            }
         }
      } else {
         return false;
      }
   }
}
