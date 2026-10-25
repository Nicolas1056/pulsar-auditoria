package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPackedIce extends Block {
   public BlockPackedIce() {
      super(Material.x);
      this.L = 0.98F;
      this.a(CreativeTabs.b);
   }

   @Override
   public int a(Random var1) {
      return 0;
   }
}
