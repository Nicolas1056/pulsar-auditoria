package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

public class ContainerHopper extends Container {
   private final IInventory a;

   public ContainerHopper(InventoryPlayer var1, IInventory var2, EntityPlayer var3) {
      this.a = var2;
      var2.b(var3);
      byte var4 = 51;

      for (int var5 = 0; var5 < var2.o_(); var5++) {
         this.a(new Slot(var2, var5, 44 + var5 * 18, 20));
      }

      for (int var7 = 0; var7 < 3; var7++) {
         for (int var6 = 0; var6 < 9; var6++) {
            this.a(new Slot(var1, var6 + var7 * 9 + 9, 8 + var6 * 18, var7 * 18 + var4));
         }
      }

      for (int var8 = 0; var8 < 9; var8++) {
         this.a(new Slot(var1, var8, 8 + var8 * 18, 58 + var4));
      }
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.a.a(var1);
   }

   @Override
   public ItemStack b(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = this.c.get(var2);
      if (var4 != null && var4.e()) {
         ItemStack var5 = var4.d();
         var3 = var5.k();
         if (var2 < this.a.o_()) {
            if (!this.a(var5, this.a.o_(), this.c.size(), true)) {
               return null;
            }
         } else if (!this.a(var5, 0, this.a.o_(), false)) {
            return null;
         }

         if (var5.b == 0) {
            var4.d(null);
         } else {
            var4.f();
         }
      }

      return var3;
   }

   @Override
   public void b(EntityPlayer var1) {
      super.b(var1);
      this.a.c(var1);
   }
}
