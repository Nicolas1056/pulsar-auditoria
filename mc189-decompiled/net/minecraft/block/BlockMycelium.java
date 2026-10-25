package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockMycelium extends Block {
   public static final PropertyBool a = PropertyBool.a("snowy");

   protected BlockMycelium() {
      super(Material.b, MapColor.z);
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
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         if (var1.l(var2.a()) < 4 && var1.p(var2.a()).c().p() > 2) {
            var1.a(var2, Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.a));
         } else {
            if (var1.l(var2.a()) >= 9) {
               for (int var5 = 0; var5 < 4; var5++) {
                  BlockPos var6 = var2.a(var4.nextInt(3) - 1, var4.nextInt(5) - 3, var4.nextInt(3) - 1);
                  IBlockState var7 = var1.p(var6);
                  Block var8 = var1.p(var6.a()).c();
                  if (var7.c() == Blocks.d && var7.b(BlockDirt.a) == BlockDirt.DirtType.a && var1.l(var6.a()) >= 4 && var8.p() <= 2) {
                     var1.a(var6, this.Q());
                  }
               }
            }
         }
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.c(var1, var2, var3, var4);
      if (var4.nextInt(10) == 0) {
         var1.a(EnumParticleTypes.w, var2.n() + var4.nextFloat(), var2.o() + 1.1F, var2.p() + var4.nextFloat(), 0.0, 0.0, 0.0);
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Blocks.d.a(Blocks.d.Q().a(BlockDirt.a, BlockDirt.DirtType.a), var2, var3);
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
