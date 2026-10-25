package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ShapelessRecipes implements IRecipe {
   private final ItemStack a;
   private final List<ItemStack> b;

   public ShapelessRecipes(ItemStack var1, List<ItemStack> var2) {
      this.a = var1;
      this.b = var2;
   }

   @Override
   public ItemStack b() {
      return this.a;
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

   @Override
   public boolean a(InventoryCrafting var1, World var2) {
      ArrayList var3 = Lists.newArrayList(this.b);

      for (int var4 = 0; var4 < var1.h(); var4++) {
         for (int var5 = 0; var5 < var1.i(); var5++) {
            ItemStack var6 = var1.c(var5, var4);
            if (var6 != null) {
               boolean var7 = false;

               for (ItemStack var9 : var3) {
                  if (var6.b() == var9.b() && (var9.i() == 32767 || var6.i() == var9.i())) {
                     var7 = true;
                     var3.remove(var9);
                     break;
                  }
               }

               if (!var7) {
                  return false;
               }
            }
         }
      }

      return var3.isEmpty();
   }

   @Override
   public ItemStack a(InventoryCrafting var1) {
      return this.a.k();
   }

   @Override
   public int a() {
      return this.b.size();
   }
}
