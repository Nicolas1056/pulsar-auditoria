package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecipesMapCloning implements IRecipe {
   @Override
   public boolean a(InventoryCrafting var1, World var2) {
      int var3 = 0;
      ItemStack var4 = null;

      for (int var5 = 0; var5 < var1.o_(); var5++) {
         ItemStack var6 = var1.a(var5);
         if (var6 != null) {
            if (var6.b() == Items.bd) {
               if (var4 != null) {
                  return false;
               }

               var4 = var6;
            } else {
               if (var6.b() != Items.bV) {
                  return false;
               }

               var3++;
            }
         }
      }

      return var4 != null && var3 > 0;
   }

   @Override
   public ItemStack a(InventoryCrafting var1) {
      int var2 = 0;
      ItemStack var3 = null;

      for (int var4 = 0; var4 < var1.o_(); var4++) {
         ItemStack var5 = var1.a(var4);
         if (var5 != null) {
            if (var5.b() == Items.bd) {
               if (var3 != null) {
                  return null;
               }

               var3 = var5;
            } else {
               if (var5.b() != Items.bV) {
                  return null;
               }

               var2++;
            }
         }
      }

      if (var3 != null && var2 >= 1) {
         ItemStack var6 = new ItemStack(Items.bd, var2 + 1, var3.i());
         if (var3.s()) {
            var6.c(var3.q());
         }

         return var6;
      } else {
         return null;
      }
   }

   @Override
   public int a() {
      return 9;
   }

   @Override
   public ItemStack b() {
      return null;
   }

   @Override
   public ItemStack[] b(InventoryCrafting var1) {
      ItemStack[] var2 = new ItemStack[var1.o_()];

      for (int var3 = 0; var3 < var2.length; var3++) {
         ItemStack var4 = var1.a(var3);
         if (var4 != null && var4.b().r()) {
            var2[var3] = new ItemStack(var4.b().q());
         }
      }

      return var2;
   }
}
