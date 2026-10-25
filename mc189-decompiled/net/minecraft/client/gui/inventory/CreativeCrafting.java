package net.minecraft.client.gui.inventory;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

public class CreativeCrafting implements ICrafting {
   private final Minecraft a;

   public CreativeCrafting(Minecraft var1) {
      this.a = var1;
   }

   @Override
   public void a(Container var1, List<ItemStack> var2) {
   }

   @Override
   public void a(Container var1, int var2, ItemStack var3) {
      this.a.c.a(var3, var2);
   }

   @Override
   public void a(Container var1, int var2, int var3) {
   }

   @Override
   public void a(Container var1, IInventory var2) {
   }
}
