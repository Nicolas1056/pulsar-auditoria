package net.minecraft.item;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multisets;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockStone;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.network.Packet;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;

public class ItemMap extends ItemMapBase {
   protected ItemMap() {
      this.a(true);
   }

   public static MapData a(int var0, World var1) {
      String var2 = "map_" + var0;
      MapData var3 = (MapData)var1.a(MapData.class, var2);
      if (var3 == null) {
         var3 = new MapData(var2);
         var1.a(var2, var3);
      }

      return var3;
   }

   public MapData a(ItemStack var1, World var2) {
      String var3 = "map_" + var1.i();
      MapData var4 = (MapData)var2.a(MapData.class, var3);
      if (var4 == null && !var2.D) {
         var1.b(var2.b("map"));
         var3 = "map_" + var1.i();
         var4 = new MapData(var3);
         var4.e = 3;
         var4.a(var2.P().c(), var2.P().e(), var4.e);
         var4.d = (byte)var2.t.q();
         var4.c();
         var2.a(var3, var4);
      }

      return var4;
   }

   public void a(World var1, Entity var2, MapData var3) {
      if (var1.t.q() == var3.d && var2 instanceof EntityPlayer) {
         int var4 = 1 << var3.e;
         int var5 = var3.b;
         int var6 = var3.c;
         int var7 = MathHelper.c(var2.s - var5) / var4 + 64;
         int var8 = MathHelper.c(var2.u - var6) / var4 + 64;
         int var9 = 128 / var4;
         if (var1.t.o()) {
            var9 /= 2;
         }

         MapData.MapInfo var10 = var3.a((EntityPlayer)var2);
         var10.b++;
         boolean var11 = false;

         for (int var12 = var7 - var9 + 1; var12 < var7 + var9; var12++) {
            if ((var12 & 15) == (var10.b & 15) || var11) {
               var11 = false;
               double var13 = 0.0;

               for (int var15 = var8 - var9 - 1; var15 < var8 + var9; var15++) {
                  if (var12 >= 0 && var15 >= -1 && var12 < 128 && var15 < 128) {
                     int var16 = var12 - var7;
                     int var17 = var15 - var8;
                     boolean var18 = var16 * var16 + var17 * var17 > (var9 - 2) * (var9 - 2);
                     int var19 = (var5 / var4 + var12 - 64) * var4;
                     int var20 = (var6 / var4 + var15 - 64) * var4;
                     HashMultiset var21 = HashMultiset.create();
                     Chunk var22 = var1.f(new BlockPos(var19, 0, var20));
                     if (!var22.f()) {
                        int var23 = var19 & 15;
                        int var24 = var20 & 15;
                        int var25 = 0;
                        double var26 = 0.0;
                        if (var1.t.o()) {
                           int var28 = var19 + var20 * 231871;
                           var28 = var28 * var28 * 31287121 + var28 * 11;
                           if ((var28 >> 20 & 1) == 0) {
                              var21.add(Blocks.d.g(Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.a)), 10);
                           } else {
                              var21.add(Blocks.b.g(Blocks.b.Q().a(BlockStone.a, BlockStone.EnumType.a)), 100);
                           }

                           var26 = 100.0;
                        } else {
                           BlockPos.MutableBlockPos var37 = new BlockPos.MutableBlockPos();

                           for (int var29 = 0; var29 < var4; var29++) {
                              for (int var30 = 0; var30 < var4; var30++) {
                                 int var31 = var22.b(var29 + var23, var30 + var24) + 1;
                                 IBlockState var32 = Blocks.a.Q();
                                 if (var31 > 1) {
                                    do {
                                       var32 = var22.g(var37.c(var29 + var23, --var31, var30 + var24));
                                    } while (var32.c().g(var32) == MapColor.b && var31 > 0);

                                    if (var31 > 0 && var32.c().t().d()) {
                                       int var33 = var31 - 1;

                                       Block var34;
                                       do {
                                          var34 = var22.a(var29 + var23, var33--, var30 + var24);
                                          var25++;
                                       } while (var33 > 0 && var34.t().d());
                                    }
                                 }

                                 var26 += (double)var31 / (var4 * var4);
                                 var21.add(var32.c().g(var32));
                              }
                           }
                        }

                        var25 /= var4 * var4;
                        double var38 = (var26 - var13) * 4.0 / (var4 + 4) + ((var12 + var15 & 1) - 0.5) * 0.4;
                        byte var40 = 1;
                        if (var38 > 0.6) {
                           var40 = 2;
                        }

                        if (var38 < -0.6) {
                           var40 = 0;
                        }

                        MapColor var41 = (MapColor)Iterables.getFirst(Multisets.copyHighestCountFirst(var21), MapColor.b);
                        if (var41 == MapColor.n) {
                           var38 = var25 * 0.1 + (var12 + var15 & 1) * 0.2;
                           var40 = 1;
                           if (var38 < 0.5) {
                              var40 = 2;
                           }

                           if (var38 > 0.9) {
                              var40 = 0;
                           }
                        }

                        var13 = var26;
                        if (var15 >= 0 && var16 * var16 + var17 * var17 < var9 * var9 && (!var18 || (var12 + var15 & 1) != 0)) {
                           byte var42 = var3.f[var12 + var15 * 128];
                           byte var43 = (byte)(var41.M * 4 + var40);
                           if (var42 != var43) {
                              var3.f[var12 + var15 * 128] = var43;
                              var3.a(var12, var15);
                              var11 = true;
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public void a(ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
      if (!var2.D) {
         MapData var6 = this.a(var1, var2);
         if (var3 instanceof EntityPlayer) {
            EntityPlayer var7 = (EntityPlayer)var3;
            var6.a(var7, var1);
         }

         if (var5) {
            this.a(var2, var3, var6);
         }
      }
   }

   @Override
   public Packet c(ItemStack var1, World var2, EntityPlayer var3) {
      return this.a(var1, var2).a(var1, var2, var3);
   }

   @Override
   public void d(ItemStack var1, World var2, EntityPlayer var3) {
      if (var1.n() && var1.o().n("map_is_scaling")) {
         MapData var4 = Items.bd.a(var1, var2);
         var1.b(var2.b("map"));
         MapData var5 = new MapData("map_" + var1.i());
         var5.e = (byte)(var4.e + 1);
         if (var5.e > 4) {
            var5.e = 4;
         }

         var5.a(var4.b, var4.c, var5.e);
         var5.d = var4.d;
         var5.c();
         var2.a("map_" + var1.i(), var5);
      }
   }

   @Override
   public void a(ItemStack var1, EntityPlayer var2, List<String> var3, boolean var4) {
      MapData var5 = this.a(var1, var2.o);
      if (var4) {
         if (var5 == null) {
            var3.add("Unknown map");
         } else {
            var3.add("Scaling at 1:" + (1 << var5.e));
            var3.add("(Level " + var5.e + "/" + 4 + ")");
         }
      }
   }
}
