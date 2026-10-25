package net.minecraft.enchantment;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;

public enum EnumEnchantmentType {
   a,
   b,
   c,
   d,
   e,
   f,
   g,
   h,
   i,
   j,
   k;

   public boolean a(Item var1) {
      if (this == a) {
         return true;
      } else if (this == j && var1.m()) {
         return true;
      } else if (var1 instanceof ItemArmor) {
         if (this == b) {
            return true;
         } else {
            ItemArmor var2 = (ItemArmor)var1;
            if (var2.b == 0) {
               return this == f;
            } else if (var2.b == 2) {
               return this == d;
            } else if (var2.b == 1) {
               return this == e;
            } else {
               return var2.b == 3 ? this == c : false;
            }
         }
      } else if (var1 instanceof ItemSword) {
         return this == g;
      } else if (var1 instanceof ItemTool) {
         return this == h;
      } else if (var1 instanceof ItemBow) {
         return this == k;
      } else {
         return var1 instanceof ItemFishingRod ? this == i : false;
      }
   }
}
