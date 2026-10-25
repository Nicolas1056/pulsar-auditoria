package net.minecraft.inventory;

import net.minecraft.entity.IMerchant;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.village.MerchantRecipe;

public class SlotMerchantResult extends Slot {
   private final InventoryMerchant a;
   private EntityPlayer b;
   private int c;
   private final IMerchant h;

   public SlotMerchantResult(EntityPlayer var1, IMerchant var2, InventoryMerchant var3, int var4, int var5, int var6) {
      super(var3, var4, var5, var6);
      this.b = var1;
      this.h = var2;
      this.a = var3;
   }

   @Override
   public boolean a(ItemStack var1) {
      return false;
   }

   @Override
   public ItemStack a(int var1) {
      if (this.e()) {
         this.c = this.c + Math.min(var1, this.d().b);
      }

      return super.a(var1);
   }

   @Override
   protected void a(ItemStack var1, int var2) {
      this.c += var2;
      this.c(var1);
   }

   @Override
   protected void c(ItemStack var1) {
      var1.a(this.b.o, this.b, this.c);
      this.c = 0;
   }

   @Override
   public void a(EntityPlayer var1, ItemStack var2) {
      this.c(var2);
      MerchantRecipe var3 = this.a.i();
      if (var3 != null) {
         ItemStack var4 = this.a.a(0);
         ItemStack var5 = this.a.a(1);
         if (this.a(var3, var4, var5) || this.a(var3, var5, var4)) {
            this.h.a(var3);
            var1.b(StatList.G);
            if (var4 != null && var4.b <= 0) {
               var4 = null;
            }

            if (var5 != null && var5.b <= 0) {
               var5 = null;
            }

            this.a.a(0, var4);
            this.a.a(1, var5);
         }
      }
   }

   private boolean a(MerchantRecipe var1, ItemStack var2, ItemStack var3) {
      ItemStack var4 = var1.a();
      ItemStack var5 = var1.b();
      if (var2 != null && var2.b() == var4.b()) {
         if (var5 != null && var3 != null && var5.b() == var3.b()) {
            var2.b = var2.b - var4.b;
            var3.b = var3.b - var5.b;
            return true;
         }

         if (var5 == null && var3 == null) {
            var2.b = var2.b - var4.b;
            return true;
         }
      }

      return false;
   }
}
