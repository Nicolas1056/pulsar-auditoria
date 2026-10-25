package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public abstract class BlockWoodSlab extends BlockSlab {
   public static final PropertyEnum<BlockPlanks.EnumType> b = PropertyEnum.a("variant", BlockPlanks.EnumType.class);

   public BlockWoodSlab() {
      super(Material.d);
      IBlockState var1 = this.M.b();
      if (!this.l()) {
         var1 = var1.a(a, BlockSlab.EnumBlockHalf.b);
      }

      this.j(var1.a(b, BlockPlanks.EnumType.a));
      this.a(CreativeTabs.b);
   }

   @Override
   public MapColor g(IBlockState var1) {
      return var1.b(b).c();
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.bM);
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(Blocks.bM);
   }

   @Override
   public String b(int var1) {
      return super.a() + "." + BlockPlanks.EnumType.a(var1).d();
   }

   @Override
   public IProperty<?> n() {
      return b;
   }

   @Override
   public Object a(ItemStack var1) {
      return BlockPlanks.EnumType.a(var1.i() & 7);
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      if (var1 != Item.a(Blocks.bL)) {
         for (BlockPlanks.EnumType var7 : BlockPlanks.EnumType.values()) {
            var3.add(new ItemStack(var1, 1, var7.a()));
         }
      }
   }

   @Override
   public IBlockState a(int var1) {
      IBlockState var2 = this.Q().a(b, BlockPlanks.EnumType.a(var1 & 7));
      if (!this.l()) {
         var2 = var2.a(a, (var1 & 8) == 0 ? BlockSlab.EnumBlockHalf.b : BlockSlab.EnumBlockHalf.a);
      }

      return var2;
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(b).a();
      if (!this.l() && var1.b(a) == BlockSlab.EnumBlockHalf.a) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return this.l() ? new BlockState(this, b) : new BlockState(this, a, b);
   }

   @Override
   public int a(IBlockState var1) {
      return var1.b(b).a();
   }
}
