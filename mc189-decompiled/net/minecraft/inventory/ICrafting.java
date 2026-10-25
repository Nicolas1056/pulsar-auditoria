package net.minecraft.inventory;

import java.util.List;
import net.minecraft.item.ItemStack;

public interface ICrafting {
   void a(Container var1, List<ItemStack> var2);

   void a(Container var1, int var2, ItemStack var3);

   void a(Container var1, int var2, int var3);

   void a(Container var1, IInventory var2);
}
