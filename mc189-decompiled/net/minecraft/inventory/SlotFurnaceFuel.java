package net.minecraft.inventory;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

public class SlotFurnaceFuel extends Slot {
   public SlotFurnaceFuel(IInventory var1, int var2, int var3, int var4) {
      super(var1, var2, var3, var4);
   }

   @Override
   public boolean a(ItemStack var1) {
      return TileEntityFurnace.c(var1) || c_(var1);
   }

   @Override
   public int b(ItemStack var1) {
      return c_(var1) ? 1 : super.b(var1);
   }

   public static boolean c_(ItemStack var0) {
      return var0 != null && var0.b() != null && var0.b() == Items.aw;
   }
}
