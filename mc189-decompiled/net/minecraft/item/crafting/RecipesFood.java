package net.minecraft.item.crafting;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class RecipesFood {
   public void a(CraftingManager var1) {
      var1.b(new ItemStack(Items.A), Blocks.P, Blocks.Q, Items.z);
      var1.a(new ItemStack(Items.bc, 8), "#X#", 'X', new ItemStack(Items.aW, 1, EnumDyeColor.m.b()), '#', Items.O);
      var1.a(new ItemStack(Items.bq), " R ", "CPM", " B ", 'R', new ItemStack(Items.bp), 'C', Items.bR, 'P', Items.bT, 'M', Blocks.P, 'B', Items.z);
      var1.a(new ItemStack(Items.bq), " R ", "CPD", " B ", 'R', new ItemStack(Items.bp), 'C', Items.bR, 'P', Items.bT, 'D', Blocks.Q, 'B', Items.z);
      var1.a(new ItemStack(Blocks.bk), "MMM", "MMM", "MMM", 'M', Items.bf);
      var1.a(new ItemStack(Items.bh), "M", 'M', Items.bf);
      var1.a(new ItemStack(Items.bg, 4), "M", 'M', Blocks.aU);
      var1.b(new ItemStack(Items.ca), Blocks.aU, Items.aY, Items.aP);
      var1.b(new ItemStack(Items.bC), Items.bB, Blocks.P, Items.aY);
      var1.b(new ItemStack(Items.bD, 2), Items.bv);
      var1.b(new ItemStack(Items.bE), Items.bD, Items.aM);
   }
}
