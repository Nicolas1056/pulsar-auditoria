package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockStainedGlass extends BlockBreakable {
   public static final PropertyEnum<EnumDyeColor> a = PropertyEnum.a("color", EnumDyeColor.class);

   public BlockStainedGlass(Material var1) {
      super(var1, false);
      this.j(this.M.b().a(a, EnumDyeColor.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (EnumDyeColor var7 : EnumDyeColor.values()) {
         var3.add(new ItemStack(var1, 1, var7.a()));
      }
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(a).e();
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.d;
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   protected boolean I() {
      return true;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, EnumDyeColor.b(var1));
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         BlockBeacon.f(var1, var2);
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         BlockBeacon.f(var1, var2);
      }
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
