package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemCloth extends ItemBlock {
   public ItemCloth(Block var1) {
      super(var1);
      this.d(0);
      this.a(true);
   }

   @Override
   public int a(int var1) {
      return var1;
   }

   @Override
   public String e_(ItemStack var1) {
      return super.a() + "." + EnumDyeColor.b(var1.i()).d();
   }
}
