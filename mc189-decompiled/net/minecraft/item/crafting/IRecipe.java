package net.minecraft.item.crafting;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public interface IRecipe {
   boolean a(InventoryCrafting var1, World var2);

   ItemStack a(InventoryCrafting var1);

   int a();

   ItemStack b();

   ItemStack[] b(InventoryCrafting var1);
}
