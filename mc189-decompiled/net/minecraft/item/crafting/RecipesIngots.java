package net.minecraft.item.crafting;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class RecipesIngots {
   private Object[][] a = new Object[][]{
      {Blocks.R, new ItemStack(Items.k, 9)},
      {Blocks.S, new ItemStack(Items.j, 9)},
      {Blocks.ah, new ItemStack(Items.i, 9)},
      {Blocks.bT, new ItemStack(Items.bO, 9)},
      {Blocks.y, new ItemStack(Items.aW, 9, EnumDyeColor.l.b())},
      {Blocks.cn, new ItemStack(Items.aC, 9)},
      {Blocks.cA, new ItemStack(Items.h, 9, 0)},
      {Blocks.cx, new ItemStack(Items.O, 9)},
      {Blocks.cE, new ItemStack(Items.aM, 9)}
   };

   public void a(CraftingManager var1) {
      for (int var2 = 0; var2 < this.a.length; var2++) {
         Block var3 = (Block)this.a[var2][0];
         ItemStack var4 = (ItemStack)this.a[var2][1];
         var1.a(new ItemStack(var3), "###", "###", "###", '#', var4);
         var1.a(var4, "#", '#', var3);
      }

      var1.a(new ItemStack(Items.k), "###", "###", "###", '#', Items.bx);
      var1.a(new ItemStack(Items.bx, 9), "#", '#', Items.k);
   }
}
