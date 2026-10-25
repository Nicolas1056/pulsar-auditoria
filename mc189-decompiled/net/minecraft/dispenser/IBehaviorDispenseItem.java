package net.minecraft.dispenser;

import net.minecraft.item.ItemStack;

public interface IBehaviorDispenseItem {
   IBehaviorDispenseItem a = new IBehaviorDispenseItem() {
      @Override
      public ItemStack a(IBlockSource var1, ItemStack var2) {
         return var2;
      }
   };

   ItemStack a(IBlockSource var1, ItemStack var2);
}
