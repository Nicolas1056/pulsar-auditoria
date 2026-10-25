package net.minecraft.item;

public class ItemBook extends Item {
   @Override
   public boolean f_(ItemStack var1) {
      return var1.b == 1;
   }

   @Override
   public int b() {
      return 1;
   }
}
