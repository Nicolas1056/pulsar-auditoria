package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ContainerMerchant extends Container {
   private IMerchant a;
   private InventoryMerchant f;
   private final World g;

   public ContainerMerchant(InventoryPlayer var1, IMerchant var2, World var3) {
      this.a = var2;
      this.g = var3;
      this.f = new InventoryMerchant(var1.d, var2);
      this.a(new Slot(this.f, 0, 36, 53));
      this.a(new Slot(this.f, 1, 62, 53));
      this.a(new SlotMerchantResult(var1.d, var2, this.f, 2, 120, 53));

      for (int var4 = 0; var4 < 3; var4++) {
         for (int var5 = 0; var5 < 9; var5++) {
            this.a(new Slot(var1, var5 + var4 * 9 + 9, 8 + var5 * 18, 84 + var4 * 18));
         }
      }

      for (int var6 = 0; var6 < 9; var6++) {
         this.a(new Slot(var1, var6, 8 + var6 * 18, 142));
      }
   }

   public InventoryMerchant e() {
      return this.f;
   }

   @Override
   public void a(ICrafting var1) {
      super.a(var1);
   }

   @Override
   public void b() {
      super.b();
   }

   @Override
   public void a(IInventory var1) {
      this.f.h();
      super.a(var1);
   }

   public void d(int var1) {
      this.f.d(var1);
   }

   @Override
   public void b(int var1, int var2) {
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return this.a.v_() == var1;
   }

   @Override
   public ItemStack b(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = this.c.get(var2);
      if (var4 != null && var4.e()) {
         ItemStack var5 = var4.d();
         var3 = var5.k();
         if (var2 == 2) {
            if (!this.a(var5, 3, 39, true)) {
               return null;
            }

            var4.a(var5, var3);
         } else if (var2 != 0 && var2 != 1) {
            if (var2 >= 3 && var2 < 30) {
               if (!this.a(var5, 30, 39, false)) {
                  return null;
               }
            } else if (var2 >= 30 && var2 < 39 && !this.a(var5, 3, 30, false)) {
               return null;
            }
         } else if (!this.a(var5, 3, 39, false)) {
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

   @Override
   public void b(EntityPlayer var1) {
      super.b(var1);
      this.a.a_(null);
      super.b(var1);
      if (!this.g.D) {
         ItemStack var2 = this.f.b(0);
         if (var2 != null) {
            var1.a(var2, false);
         }

         var2 = this.f.b(1);
         if (var2 != null) {
            var1.a(var2, false);
         }
      }
   }
}
