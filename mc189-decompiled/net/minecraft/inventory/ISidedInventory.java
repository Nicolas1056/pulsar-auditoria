package net.minecraft.inventory;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;

public interface ISidedInventory extends IInventory {
   int[] a(EnumFacing var1);

   boolean a(int var1, ItemStack var2, EnumFacing var3);

   boolean b(int var1, ItemStack var2, EnumFacing var3);
}
