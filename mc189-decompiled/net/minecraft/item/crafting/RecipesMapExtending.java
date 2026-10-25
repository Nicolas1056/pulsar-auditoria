package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapData;

public class RecipesMapExtending extends ShapedRecipes {
   public RecipesMapExtending() {
      super(
         3,
         3,
         new ItemStack[]{
            new ItemStack(Items.aK),
            new ItemStack(Items.aK),
            new ItemStack(Items.aK),
            new ItemStack(Items.aK),
            new ItemStack(Items.bd, 0, 32767),
            new ItemStack(Items.aK),
            new ItemStack(Items.aK),
            new ItemStack(Items.aK),
            new ItemStack(Items.aK)
         },
         new ItemStack(Items.bV, 0, 0)
      );
   }

   @Override
   public boolean a(InventoryCrafting var1, World var2) {
      if (!super.a(var1, var2)) {
         return false;
      } else {
         ItemStack var3 = null;

         for (int var4 = 0; var4 < var1.o_() && var3 == null; var4++) {
            ItemStack var5 = var1.a(var4);
            if (var5 != null && var5.b() == Items.bd) {
               var3 = var5;
            }
         }

         if (var3 == null) {
            return false;
         } else {
            MapData var6 = Items.bd.a(var3, var2);
            return var6 == null ? false : var6.e < 4;
         }
      }
   }

   @Override
   public ItemStack a(InventoryCrafting var1) {
      ItemStack var2 = null;

      for (int var3 = 0; var3 < var1.o_() && var2 == null; var3++) {
         ItemStack var4 = var1.a(var3);
         if (var4 != null && var4.b() == Items.bd) {
            var2 = var4;
         }
      }

      var2 = var2.k();
      var2.b = 1;
      if (var2.o() == null) {
         var2.d(new NBTTagCompound());
      }

      var2.o().a("map_is_scaling", true);
      return var2;
   }
}
