package net.minecraft.block;

import java.util.List;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCarpet extends Block {
   public static final PropertyEnum<EnumDyeColor> a = PropertyEnum.a("color", EnumDyeColor.class);

   protected BlockCarpet() {
      super(Material.r);
      this.j(this.M.b().a(a, EnumDyeColor.a));
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
      this.a(true);
      this.a(CreativeTabs.c);
      this.b(0);
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(a).e();
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public void j() {
      this.b(0);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.b(0);
   }

   protected void b(int var1) {
      byte var2 = 0;
      float var3 = 1 * (1 + var2) / 16.0F;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, var3, 1.0F);
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return super.d(var1, var2) && this.e(var1, var2);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.e(var1, var2, var3);
   }

   private boolean e(World var1, BlockPos var2, IBlockState var3) {
      if (!this.e(var1, var2)) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
         return false;
      } else {
         return true;
      }
   }

   private boolean e(World var1, BlockPos var2) {
      return !var1.d(var2.b());
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.b ? true : super.a(var1, var2, var3);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      for (int var4 = 0; var4 < 16; var4++) {
         var3.add(new ItemStack(var1, 1, var4));
      }
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, EnumDyeColor.b(var1));
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
