package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerGrass;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeColorHelper;

public class BlockGrass extends Block implements IGrowable {
   public static final PropertyBool a = PropertyBool.a("snowy");

   protected BlockGrass() {
      super(Material.b);
      this.j(this.M.b().a(a, false));
      this.a(true);
      this.a(CreativeTabs.b);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      Block var4 = var2.p(var3.a()).c();
      return var1.a(a, var4 == Blocks.aJ || var4 == Blocks.aH);
   }

   @Override
   public int H() {
      return ColorizerGrass.a(0.5, 1.0);
   }

   @Override
   public int h(IBlockState var1) {
      return this.H();
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return BiomeColorHelper.a(var1, var2);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         if (var1.l(var2.a()) < 4 && var1.p(var2.a()).c().p() > 2) {
            var1.a(var2, Blocks.d.Q());
         } else {
            if (var1.l(var2.a()) >= 9) {
               for (int var5 = 0; var5 < 4; var5++) {
                  BlockPos var6 = var2.a(var4.nextInt(3) - 1, var4.nextInt(5) - 3, var4.nextInt(3) - 1);
                  Block var7 = var1.p(var6.a()).c();
                  IBlockState var8 = var1.p(var6);
                  if (var8.c() == Blocks.d && var8.b(BlockDirt.a) == BlockDirt.DirtType.a && var1.l(var6.a()) >= 4 && var7.p() <= 2) {
                     var1.a(var6, Blocks.c.Q());
                  }
               }
            }
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Blocks.d.a(Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.a), var2, var3);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return true;
   }

   @Override
   public boolean a(World var1, Random var2, BlockPos var3, IBlockState var4) {
      return true;
   }

   @Override
   public void b(World var1, Random var2, BlockPos var3, IBlockState var4) {
      BlockPos var5 = var3.a();

      label38:
      for (int var6 = 0; var6 < 128; var6++) {
         BlockPos var7 = var5;

         for (int var8 = 0; var8 < var6 / 16; var8++) {
            var7 = var7.a(var2.nextInt(3) - 1, (var2.nextInt(3) - 1) * var2.nextInt(3) / 2, var2.nextInt(3) - 1);
            if (var1.p(var7.b()).c() != Blocks.c || var1.p(var7).c().v()) {
               continue label38;
            }
         }

         if (var1.p(var7).c().J == Material.a) {
            if (var2.nextInt(8) == 0) {
               BlockFlower.EnumFlowerType var11 = var1.b(var7).a(var2, var7);
               BlockFlower var9 = var11.a().a();
               IBlockState var10 = var9.Q().a(var9.n(), var11);
               if (var9.f(var1, var7, var10)) {
                  var1.a(var7, var10, 3);
               }
            } else {
               IBlockState var12 = Blocks.H.Q().a(BlockTallGrass.a, BlockTallGrass.EnumType.b);
               if (Blocks.H.f(var1, var7, var12)) {
                  var1.a(var7, var12, 3);
               }
            }
         }
      }
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.b;
   }

   @Override
   public int c(IBlockState var1) {
      return 0;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
