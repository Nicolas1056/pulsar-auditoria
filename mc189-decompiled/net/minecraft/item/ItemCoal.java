package net.minecraft.item;

import java.util.List;
import net.minecraft.creativetab.CreativeTabs;

public class ItemCoal extends Item {
   public ItemCoal() {
      this.a(true);
      this.d(0);
      this.a(CreativeTabs.l);
   }

   @Override
   public String e_(ItemStack var1) {
      return var1.i() == 1 ? "item.charcoal" : "item.coal";
   }

   @Override
   public void a(Item var1, CreativeTabs var2, List<ItemStack> var3) {
      var3.add(new ItemStack(var1, 1, 0));
      var3.add(new ItemStack(var1, 1, 1));
   }
}
