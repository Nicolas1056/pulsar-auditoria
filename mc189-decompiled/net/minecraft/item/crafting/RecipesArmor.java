package net.minecraft.item.crafting;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesArmor {
   private String[][] a = new String[][]{{"XXX", "X X"}, {"X X", "XXX", "XXX"}, {"XXX", "X X", "X X"}, {"X X", "X X"}};
   private Item[][] b = new Item[][]{
      {Items.aF, Items.j, Items.i, Items.k},
      {Items.Q, Items.Y, Items.ac, Items.ag},
      {Items.R, Items.Z, Items.ad, Items.ah},
      {Items.S, Items.aa, Items.ae, Items.ai},
      {Items.T, Items.ab, Items.af, Items.aj}
   };

   public void a(CraftingManager var1) {
      for (int var2 = 0; var2 < this.b[0].length; var2++) {
         Item var3 = this.b[0][var2];

         for (int var4 = 0; var4 < this.b.length - 1; var4++) {
            Item var5 = this.b[var4 + 1][var2];
            var1.a(new ItemStack(var5), this.a[var4], 'X', var3);
         }
      }
   }
}
