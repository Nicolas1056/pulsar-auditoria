package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockHardenedClay extends Block {
   public BlockHardenedClay() {
      super(Material.e);
      this.a(CreativeTabs.b);
   }

   @Override
   public MapColor g(IBlockState var1) {
      return MapColor.q;
   }
}
