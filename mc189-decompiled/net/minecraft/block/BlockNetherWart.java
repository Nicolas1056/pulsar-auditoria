package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockNetherWart extends BlockBush {
   public static final PropertyInteger a = PropertyInteger.a("age", 0, 3);

   protected BlockNetherWart() {
      super(Material.k, MapColor.D);
      this.j(this.M.b().a(a, 0));
      this.a(true);
      float var1 = 0.5F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, 0.25F, 0.5F + var1);
      this.a(null);
   }

   @Override
   protected boolean c(Block var1) {
      return var1 == Blocks.aW;
   }

   @Override
   public boolean f(World var1, BlockPos var2, IBlockState var3) {
      return this.c(var1.p(var2.b()).c());
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = var3.b(a);
      if (var5 < 3 && var4.nextInt(10) == 0) {
         var3 = var3.a(a, var5 + 1);
         var1.a(var2, var3, 2);
      }

      super.b(var1, var2, var3, var4);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      if (!var1.D) {
         int var6 = 1;
         if (var3.b(a) >= 3) {
            var6 = 2 + var1.s.nextInt(3);
            if (var5 > 0) {
               var6 += var1.s.nextInt(var5 + 1);
            }
         }

         for (int var7 = 0; var7 < var6; var7++) {
            a(var1, var2, new ItemStack(Items.by));
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return null;
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.by;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, var1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
