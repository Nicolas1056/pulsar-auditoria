package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerDispenser extends Container {
   private IInventory a;

   public ContainerDispenser(IInventory var1, IInventory var2) {
      this.a = var2;

      for (int var3 = 0; var3 < 3; var3++) {
         for (int var4 = 0; var4 < 3; var4++) {
            this.a(new Slot(var2, var4 + var3 * 3, 62 + var4 * 18, 17 + var3 * 18));
         }
      }

      for (int var5 = 0; var5 < 3; var5++) {
         for (int var7 = 0; var7 < 9; var7++) {
            this.a(new Slot(var1, var7 + var5 * 9 + 9, 8 + var7 * 18, 84 + var5 * 18));
         }
      }

      for (int var6 = 0; var6 < 9; var6++) {
         this.a(new Slot(var1, var6, 8 + var6 * 18, 142));
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
         if (var2 < 9) {
            if (!this.a(var5, 9, 45, true)) {
               return null;
            }
         } else if (!this.a(var5, 0, 9, false)) {
            return null;
         }

         if (var5.b == 0) {
            var4.d(null);
         } else {
            var4.f();
         }

         if (var5.b == var3.b) {
            return null;
         }

         var4.a(var1, var5);
      }

      return var3;
   }
}
