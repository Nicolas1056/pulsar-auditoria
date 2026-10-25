package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;

public class BlockBookshelf extends Block {
   public BlockBookshelf() {
      super(Material.d);
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(Random var1) {
      return 3;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.aL;
   }
}
