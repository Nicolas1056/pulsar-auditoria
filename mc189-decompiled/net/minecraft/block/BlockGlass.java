package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.EnumWorldBlockLayer;

public class BlockGlass extends BlockBreakable {
   public BlockGlass(Material var1, boolean var2) {
      super(var1, var2);
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   protected boolean I() {
      return true;
   }
}
