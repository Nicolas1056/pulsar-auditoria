package net.minecraft.item;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class ItemShears extends Item {
   public ItemShears() {
      this.c(1);
      this.d(238);
      this.a(CreativeTabs.i);
   }

   @Override
   public boolean a(ItemStack var1, World var2, Block var3, BlockPos var4, EntityLivingBase var5) {
      if (var3.t() != Material.j && var3 != Blocks.G && var3 != Blocks.H && var3 != Blocks.bn && var3 != Blocks.bS && var3 != Blocks.L) {
         return super.a(var1, var2, var3, var4, var5);
      } else {
         var1.a(1, var5);
         return true;
      }
   }

   @Override
   public boolean b(Block var1) {
      return var1 == Blocks.G || var1 == Blocks.af || var1 == Blocks.bS;
   }

   @Override
   public float a(ItemStack var1, Block var2) {
      if (var2 == Blocks.G || var2.t() == Material.j) {
         return 15.0F;
      } else {
         return var2 == Blocks.L ? 5.0F : super.a(var1, var2);
      }
   }
}
