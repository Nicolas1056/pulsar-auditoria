package net.minecraft.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ContainerPlayer extends Container {
   public InventoryCrafting a = new InventoryCrafting(this, 2, 2);
   public IInventory f = new InventoryCraftResult();
   public boolean g;
   private final EntityPlayer h;

   public ContainerPlayer(InventoryPlayer var1, boolean var2, EntityPlayer var3) {
      this.g = var2;
      this.h = var3;
      this.a(new SlotCrafting(var1.d, this.a, this.f, 0, 144, 36));

      for (int var4 = 0; var4 < 2; var4++) {
         for (int var5 = 0; var5 < 2; var5++) {
            this.a(new Slot(this.a, var5 + var4 * 2, 88 + var5 * 18, 26 + var4 * 18));
         }
      }

      for (final int var6 = 0; var6 < 4; var6++) {
         this.a(new Slot(var1, var1.o_() - 1 - var6, 8, 8 + var6 * 18) {
            @Override
            public int a() {
               return 1;
            }

            @Override
            public boolean a(ItemStack var1) {
               if (var1 == null) {
                  return false;
               } else if (var1.b() instanceof ItemArmor) {
                  return ((ItemArmor)var1.b()).b == var6;
               } else {
                  return var1.b() != Item.a(Blocks.aU) && var1.b() != Items.bX ? false : var6 == 0;
               }
            }

            @Override
            public String c() {
               return ItemArmor.a[var6];
            }
         });
      }

      for (int var7 = 0; var7 < 3; var7++) {
         for (int var9 = 0; var9 < 9; var9++) {
            this.a(new Slot(var1, var9 + (var7 + 1) * 9, 8 + var9 * 18, 84 + var7 * 18));
         }
      }

      for (int var8 = 0; var8 < 9; var8++) {
         this.a(new Slot(var1, var8, 8 + var8 * 18, 142));
      }

      this.a(this.a);
   }

   @Override
   public void a(IInventory var1) {
      this.f.a(0, CraftingManager.a().a(this.a, this.h.o));
   }

   @Override
   public void b(EntityPlayer var1) {
      super.b(var1);

      for (int var2 = 0; var2 < 4; var2++) {
         ItemStack var3 = this.a.b(var2);
         if (var3 != null) {
            var1.a(var3, false);
         }
      }

      this.f.a(0, null);
   }

   @Override
   public boolean a(EntityPlayer var1) {
      return true;
   }

   @Override
   public ItemStack b(EntityPlayer var1, int var2) {
      ItemStack var3 = null;
      Slot var4 = this.c.get(var2);
      if (var4 != null && var4.e()) {
         ItemStack var5 = var4.d();
         var3 = var5.k();
         if (var2 == 0) {
            if (!this.a(var5, 9, 45, true)) {
               return null;
            }

            var4.a(var5, var3);
         } else if (var2 >= 1 && var2 < 5) {
            if (!this.a(var5, 9, 45, false)) {
               return null;
            }
         } else if (var2 >= 5 && var2 < 9) {
            if (!this.a(var5, 9, 45, false)) {
               return null;
            }
         } else if (var3.b() instanceof ItemArmor && !this.c.get(5 + ((ItemArmor)var3.b()).b).e()) {
            int var6 = 5 + ((ItemArmor)var3.b()).b;
            if (!this.a(var5, var6, var6 + 1, false)) {
               return null;
            }
         } else if (var2 >= 9 && var2 < 36) {
            if (!this.a(var5, 36, 45, false)) {
               return null;
            }
         } else if (var2 >= 36 && var2 < 45) {
            if (!this.a(var5, 9, 36, false)) {
               return null;
            }
         } else if (!this.a(var5, 9, 45, false)) {
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
   public boolean a(ItemStack var1, Slot var2) {
      return var2.d != this.f && super.a(var1, var2);
   }
}
