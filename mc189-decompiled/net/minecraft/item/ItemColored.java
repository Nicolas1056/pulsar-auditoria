package net.minecraft.item;

import net.minecraft.block.Block;

public class ItemColored extends ItemBlock {
   private final Block b;
   private String[] c;

   public ItemColored(Block var1, boolean var2) {
      super(var1);
      this.b = var1;
      if (var2) {
         this.d(0);
         this.a(true);
      }
   }

   @Override
   public int a(ItemStack var1, int var2) {
      return this.b.h(this.b.a(var1.i()));
   }

   @Override
   public int a(int var1) {
      return var1;
   }

   public ItemColored a(String[] var1) {
      this.c = var1;
      return this;
   }

   @Override
   public String e_(ItemStack var1) {
      if (this.c == null) {
         return super.e_(var1);
      } else {
         int var2 = var1.i();
         return var2 >= 0 && var2 < this.c.length ? super.e_(var1) + "." + this.c[var2] : super.e_(var1);
      }
   }
}
