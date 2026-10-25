package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.MathHelper;

public class BlockSeaLantern extends Block {
   public BlockSeaLantern(Material var1) {
      super(var1);
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(Random var1) {
      return 2 + var1.nextInt(2);
   }

   @Override
   public int a(int var1, Random var2) {
      return MathHelper.a(this.a(var2) + var2.nextInt(var1 + 1), 1, 5);
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.cD;
   }

   @Override
   public MapColor g(IBlockState var1) {
      return MapColor.p;
   }

   @Override
   protected boolean I() {
      return true;
   }
}
