package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;

public class BlockWeb extends Block {
   public BlockWeb() {
      super(Material.G);
      this.a(CreativeTabs.c);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      var4.aA();
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.F;
   }

   @Override
   protected boolean I() {
      return true;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }
}
