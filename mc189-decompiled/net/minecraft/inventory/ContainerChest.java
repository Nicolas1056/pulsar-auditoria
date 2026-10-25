package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class ContainerChest extends Container {
   private IInventory a;
   private int f;

   public ContainerChest(IInventory var1, IInventory var2, EntityPlayer var3) {
      this.a = var2;
      this.f = var2.o_() / 9;
      var2.b(var3);
      int var4 = (this.f - 4) * 18;

      for (int var5 = 0; var5 < this.f; var5++) {
         for (int var6 = 0; var6 < 9; var6++) {
            this.a(new Slot(var2, var6 + var5 * 9, 8 + var6 * 18, 18 + var5 * 18));
         }
      }

      for (int var7 = 0; var7 < 3; var7++) {
         for (int var9 = 0; var9 < 9; var9++) {
            this.a(new Slot(var1, var9 + var7 * 9 + 9, 8 + var9 * 18, 103 + var7 * 18 + var4));
         }
      }

      for (int var8 = 0; var8 < 9; var8++) {
         this.a(new Slot(var1, var8, 8 + var8 * 18, 161 + var4));
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
         if (var2 < this.f * 9) {
            if (!this.a(var5, this.f * 9, this.c.size(), true)) {
               return null;
            }
         } else if (!this.a(var5, 0, this.f * 9, false)) {
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

   public IInventory e() {
      return this.a;
   }
}
