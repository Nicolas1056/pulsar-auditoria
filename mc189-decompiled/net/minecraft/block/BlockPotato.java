package net.minecraft.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class BlockPotato extends BlockCrops {
   @Override
   protected Item l() {
      return Items.bS;
   }

   @Override
   protected Item n() {
      return Items.bS;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, float var4, int var5) {
      super.a(var1, var2, var3, var4, var5);
      if (!var1.D) {
         if (var3.b(a) >= 7 && var1.s.nextInt(50) == 0) {
            a(var1, var2, new ItemStack(Items.bU));
         }
      }
   }
}
