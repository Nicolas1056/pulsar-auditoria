package net.minecraft.item;

import com.google.common.base.Function;
import net.minecraft.block.Block;

public class ItemMultiTexture extends ItemBlock {
   protected final Block b;
   protected final Function<ItemStack, String> c;

   public ItemMultiTexture(Block var1, Block var2, Function<ItemStack, String> var3) {
      super(var1);
      this.b = var2;
      this.c = var3;
      this.d(0);
      this.a(true);
   }

   public ItemMultiTexture(Block var1, Block var2, final String[] var3) {
      this(var1, var2, new Function<ItemStack, String>() {
         public String a(ItemStack var1) {
            int var2x = var1.i();
            if (var2x < 0 || var2x >= var3.length) {
               var2x = 0;
            }

            return var3[var2x];
         }
      });
   }

   @Override
   public int a(int var1) {
      return var1;
   }

   @Override
   public String e_(ItemStack var1) {
      return super.a() + "." + (String)this.c.apply(var1);
   }
}
