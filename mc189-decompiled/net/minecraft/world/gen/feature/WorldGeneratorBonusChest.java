package net.minecraft.world.gen.feature;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class WorldGeneratorBonusChest extends WorldGenerator {
   private final List<WeightedRandomChestContent> a;
   private final int b;

   public WorldGeneratorBonusChest(List<WeightedRandomChestContent> var1, int var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public boolean b(World var1, Random var2, BlockPos var3) {
      Block var4;
      while (((var4 = var1.p(var3).c()).t() == Material.a || var4.t() == Material.j) && var3.o() > 1) {
         var3 = var3.b();
      }

      if (var3.o() < 1) {
         return false;
      } else {
         var3 = var3.a();

         for (int var5 = 0; var5 < 4; var5++) {
            BlockPos var6 = var3.a(var2.nextInt(4) - var2.nextInt(4), var2.nextInt(3) - var2.nextInt(3), var2.nextInt(4) - var2.nextInt(4));
            if (var1.d(var6) && World.a(var1, var6.b())) {
               var1.a(var6, Blocks.ae.Q(), 2);
               TileEntity var7 = var1.s(var6);
               if (var7 instanceof TileEntityChest) {
                  WeightedRandomChestContent.a(var2, this.a, (TileEntityChest)var7, this.b);
               }

               BlockPos var8 = var6.f();
               BlockPos var9 = var6.e();
               BlockPos var10 = var6.c();
               BlockPos var11 = var6.d();
               if (var1.d(var9) && World.a(var1, var9.b())) {
                  var1.a(var9, Blocks.aa.Q(), 2);
               }

               if (var1.d(var8) && World.a(var1, var8.b())) {
                  var1.a(var8, Blocks.aa.Q(), 2);
               }

               if (var1.d(var10) && World.a(var1, var10.b())) {
                  var1.a(var10, Blocks.aa.Q(), 2);
               }

               if (var1.d(var11) && World.a(var1, var11.b())) {
                  var1.a(var11, Blocks.aa.Q(), 2);
               }

               return true;
            }
         }

         return false;
      }
   }
}
