package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class RecipeRepairItem implements IRecipe {
   @Override
   public boolean a(InventoryCrafting var1, World var2) {
      ArrayList var3 = Lists.newArrayList();

      for (int var4 = 0; var4 < var1.o_(); var4++) {
         ItemStack var5 = var1.a(var4);
         if (var5 != null) {
            var3.add(var5);
            if (var3.size() > 1) {
               ItemStack var6 = (ItemStack)var3.get(0);
               if (var5.b() != var6.b() || var6.b != 1 || var5.b != 1 || !var6.b().m()) {
                  return false;
               }
            }
         }
      }

      return var3.size() == 2;
   }

   @Override
   public ItemStack a(InventoryCrafting var1) {
      ArrayList var2 = Lists.newArrayList();

      for (int var3 = 0; var3 < var1.o_(); var3++) {
         ItemStack var4 = var1.a(var3);
         if (var4 != null) {
            var2.add(var4);
            if (var2.size() > 1) {
               ItemStack var5 = (ItemStack)var2.get(0);
               if (var4.b() != var5.b() || var5.b != 1 || var4.b != 1 || !var5.b().m()) {
                  return null;
               }
            }
         }
      }

      if (var2.size() == 2) {
         ItemStack var10 = (ItemStack)var2.get(0);
         ItemStack var11 = (ItemStack)var2.get(1);
         if (var10.b() == var11.b() && var10.b == 1 && var11.b == 1 && var10.b().m()) {
            Item var12 = var10.b();
            int var6 = var12.l() - var10.h();
            int var7 = var12.l() - var11.h();
            int var8 = var6 + var7 + var12.l() * 5 / 100;
            int var9 = var12.l() - var8;
            if (var9 < 0) {
               var9 = 0;
            }

            return new ItemStack(var10.b(), 1, var9);
         }
      }

      return null;
   }

   @Override
   public int a() {
      return 4;
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
