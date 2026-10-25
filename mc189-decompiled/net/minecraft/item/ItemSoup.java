package net.minecraft.item;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.world.World;

public class ItemSoup extends ItemFood {
   public ItemSoup(int var1) {
      super(var1, false);
      this.c(1);
   }

   @Override
   public ItemStack b(ItemStack var1, World var2, EntityPlayer var3) {
      super.b(var1, var2, var3);
      return new ItemStack(Items.z);
   }
}
