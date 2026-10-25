package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class ContainerBeacon extends Container {
   private IInventory a;
   private final ContainerBeacon.BeaconSlot f;

   public ContainerBeacon(IInventory var1, IInventory var2) {
      this.a = var2;
      this.a(this.f = new ContainerBeacon.BeaconSlot(var2, 0, 136, 110));
      byte var3 = 36;
      short var4 = 137;

      for (int var5 = 0; var5 < 3; var5++) {
         for (int var6 = 0; var6 < 9; var6++) {
            this.a(new Slot(var1, var6 + var5 * 9 + 9, var3 + var6 * 18, var4 + var5 * 18));
         }
      }

      for (int var7 = 0; var7 < 9; var7++) {
         this.a(new Slot(var1, var7, var3 + var7 * 18, 58 + var4));
      }
   }

   @Override
   public void a(ICrafting var1) {
      super.a(var1);
      var1.a(this, this.a);
   }

   @Override
   public void b(int var1, int var2) {
      this.a.b(var1, var2);
   }

   public IInventory e() {
      return this.a;
   }

   @Override
   public void b(EntityPlayer var1) {
      super.b(var1);
      if (var1 != null && !var1.o.D) {
         ItemStack var2 = this.f.a(this.f.a());
         if (var2 != null) {
            var1.a(var2, false);
         }
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
         if (var2 == 0) {
            if (!this.a(var5, 1, 37, true)) {
               return null;
            }

            var4.a(var5, var3);
         } else if (!this.f.e() && this.f.a(var5) && var5.b == 1) {
            if (!this.a(var5, 0, 1, false)) {
               return null;
            }
         } else if (var2 >= 1 && var2 < 28) {
            if (!this.a(var5, 28, 37, false)) {
               return null;
            }
         } else if (var2 >= 28 && var2 < 37) {
            if (!this.a(var5, 1, 28, false)) {
               return null;
            }
         } else if (!this.a(var5, 1, 37, false)) {
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

   class BeaconSlot extends Slot {
      public BeaconSlot(IInventory var2, int var3, int var4, int var5) {
         super(var2, var3, var4, var5);
      }

      @Override
      public boolean a(ItemStack var1) {
         return var1 == null ? false : var1.b() == Items.bO || var1.b() == Items.i || var1.b() == Items.k || var1.b() == Items.j;
      }

      @Override
      public int a() {
         return 1;
      }
   }
}
