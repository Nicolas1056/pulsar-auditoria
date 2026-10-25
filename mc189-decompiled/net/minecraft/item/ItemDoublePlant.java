package net.minecraft.item;

import com.google.common.base.Function;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.world.ColorizerGrass;

public class ItemDoublePlant extends ItemMultiTexture {
   public ItemDoublePlant(Block var1, Block var2, Function<ItemStack, String> var3) {
      super(var1, var2, var3);
   }

   @Override
   public int a(ItemStack var1, int var2) {
      BlockDoublePlant.EnumPlantType var3 = BlockDoublePlant.EnumPlantType.a(var1.i());
      return var3 != BlockDoublePlant.EnumPlantType.c && var3 != BlockDoublePlant.EnumPlantType.d ? super.a(var1, var2) : ColorizerGrass.a(0.5, 1.0);
   }
}
