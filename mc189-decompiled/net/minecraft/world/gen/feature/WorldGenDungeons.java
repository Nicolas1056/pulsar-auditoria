package net.minecraft.world.gen.feature;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorldGenDungeons extends WorldGenerator {
   private static final Logger a = LogManager.getLogger();
   private static final String[] b = new String[]{"Skeleton", "Zombie", "Zombie", "Spider"};
   private static final List<WeightedRandomChestContent> c = Lists.newArrayList(
      new WeightedRandomChestContent[]{
         new WeightedRandomChestContent(Items.aA, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.j, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.P, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.O, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.H, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.F, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.aw, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.ao, 0, 1, 1, 1),
         new WeightedRandomChestContent(Items.aC, 0, 1, 4, 10),
         new WeightedRandomChestContent(Items.cq, 0, 1, 1, 4),
         new WeightedRandomChestContent(Items.cr, 0, 1, 1, 4),
         new WeightedRandomChestContent(Items.co, 0, 1, 1, 10),
         new WeightedRandomChestContent(Items.cl, 0, 1, 1, 2),
         new WeightedRandomChestContent(Items.ck, 0, 1, 1, 5),
         new WeightedRandomChestContent(Items.cm, 0, 1, 1, 1)
      }
   );

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      byte var4 = 3;
      int var5 = var2.nextInt(2) + 2;
      int var6 = -var5 - 1;
      int var7 = var5 + 1;
      byte var8 = -1;
      byte var9 = 4;
      int var10 = var2.nextInt(2) + 2;
      int var11 = -var10 - 1;
      int var12 = var10 + 1;
      int var13 = 0;

      for (int var14 = var6; var14 <= var7; var14++) {
         for (int var15 = -1; var15 <= 4; var15++) {
            for (int var16 = var11; var16 <= var12; var16++) {
               BlockPos var17 = var3.a(var14, var15, var16);
               Material var18 = var1.p(var17).c().t();
               boolean var19 = var18.a();
               if (var15 == -1 && !var19) {
                  return false;
               }

               if (var15 == 4 && !var19) {
                  return false;
               }

               if ((var14 == var6 || var14 == var7 || var16 == var11 || var16 == var12) && var15 == 0 && var1.d(var17) && var1.d(var17.a())) {
                  var13++;
               }
            }
         }
      }

      if (var13 >= 1 && var13 <= 5) {
         for (int var23 = var6; var23 <= var7; var23++) {
            for (int var26 = 3; var26 >= -1; var26--) {
               for (int var28 = var11; var28 <= var12; var28++) {
                  BlockPos var30 = var3.a(var23, var26, var28);
                  if (var23 != var6 && var26 != -1 && var28 != var11 && var23 != var7 && var26 != 4 && var28 != var12) {
                     if (var1.p(var30).c() != Blocks.ae) {
                        var1.g(var30);
                     }
                  } else if (var30.o() >= 0 && !var1.p(var30.b()).c().t().a()) {
                     var1.g(var30);
                  } else if (var1.p(var30).c().t().a() && var1.p(var30).c() != Blocks.ae) {
                     if (var26 == -1 && var2.nextInt(4) != 0) {
                        var1.a(var30, Blocks.Y.Q(), 2);
                     } else {
                        var1.a(var30, Blocks.e.Q(), 2);
                     }
                  }
               }
            }
         }

         for (int var24 = 0; var24 < 2; var24++) {
            for (int var27 = 0; var27 < 3; var27++) {
               int var29 = var3.n() + var2.nextInt(var5 * 2 + 1) - var5;
               int var31 = var3.o();
               int var32 = var3.p() + var2.nextInt(var10 * 2 + 1) - var10;
               BlockPos var33 = new BlockPos(var29, var31, var32);
               if (var1.d(var33)) {
                  int var20 = 0;

                  for (EnumFacing var22 : EnumFacing.Plane.a) {
                     if (var1.p(var33.a(var22)).c().t().a()) {
                        var20++;
                     }
                  }

                  if (var20 == 1) {
                     var1.a(var33, Blocks.ae.f(var1, var33, Blocks.ae.Q()), 2);
                     List var34 = WeightedRandomChestContent.a(c, Items.cd.b(var2));
                     TileEntity var35 = var1.s(var33);
                     if (var35 instanceof TileEntityChest) {
                        WeightedRandomChestContent.a(var2, var34, (TileEntityChest)var35, 8);
                     }
                     break;
                  }
               }
            }
         }

         var1.a(var3, Blocks.ac.Q(), 2);
         TileEntity var25 = var1.s(var3);
         if (var25 instanceof TileEntityMobSpawner) {
            ((TileEntityMobSpawner)var25).b().a(this.a(var2));
         } else {
            a.error("Failed to fetch mob spawner entity at (" + var3.n() + ", " + var3.o() + ", " + var3.p() + ")");
         }

         return true;
      } else {
         return false;
      }
   }

   private String a(Random var1) {
      return b[var1.nextInt(b.length)];
   }
}
