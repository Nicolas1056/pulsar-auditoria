package net.minecraft.util;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityDispenser;

public class WeightedRandomChestContent extends WeightedRandom.Item {
   private ItemStack b;
   private int c;
   private int d;

   public WeightedRandomChestContent(Item var1, int var2, int var3, int var4, int var5) {
      super(var5);
      this.b = new ItemStack(var1, 1, var2);
      this.c = var3;
      this.d = var4;
   }

   public WeightedRandomChestContent(ItemStack var1, int var2, int var3, int var4) {
      super(var4);
      this.b = var1;
      this.c = var2;
      this.d = var3;
   }

   public static void a(Random var0, List<WeightedRandomChestContent> var1, IInventory var2, int var3) {
      for (int var4 = 0; var4 < var3; var4++) {
         WeightedRandomChestContent var5 = WeightedRandom.a(var0, var1);
         int var6 = var5.c + var0.nextInt(var5.d - var5.c + 1);
         if (var5.b.c() >= var6) {
            ItemStack var9 = var5.b.k();
            var9.b = var6;
            var2.a(var0.nextInt(var2.o_()), var9);
         } else {
            for (int var7 = 0; var7 < var6; var7++) {
               ItemStack var8 = var5.b.k();
               var8.b = 1;
               var2.a(var0.nextInt(var2.o_()), var8);
            }
         }
      }
   }

   public static void a(Random var0, List<WeightedRandomChestContent> var1, TileEntityDispenser var2, int var3) {
      for (int var4 = 0; var4 < var3; var4++) {
         WeightedRandomChestContent var5 = WeightedRandom.a(var0, var1);
         int var6 = var5.c + var0.nextInt(var5.d - var5.c + 1);
         if (var5.b.c() >= var6) {
            ItemStack var9 = var5.b.k();
            var9.b = var6;
            var2.a(var0.nextInt(var2.o_()), var9);
         } else {
            for (int var7 = 0; var7 < var6; var7++) {
               ItemStack var8 = var5.b.k();
               var8.b = 1;
               var2.a(var0.nextInt(var2.o_()), var8);
            }
         }
      }
   }

   public static List<WeightedRandomChestContent> a(List<WeightedRandomChestContent> var0, WeightedRandomChestContent... var1) {
      ArrayList var2 = Lists.newArrayList(var0);
      Collections.addAll(var2, var1);
      return var2;
   }
}
