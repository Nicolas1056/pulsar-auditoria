package net.minecraft.item;

import net.minecraft.block.BlockLeaves;

public class ItemLeaves extends ItemBlock {
   private final BlockLeaves b;

   public ItemLeaves(BlockLeaves var1) {
      super(var1);
      this.b = var1;
      this.d(0);
      this.a(true);
   }

   @Override
   public int a(int var1) {
      return var1 | 4;
   }

   @Override
   public int a(ItemStack var1, int var2) {
      return this.b.h(this.b.a(var1.i()));
   }

   @Override
   public String e_(ItemStack var1) {
      return super.a() + "." + this.b.b(var1.i()).d();
   }
}
