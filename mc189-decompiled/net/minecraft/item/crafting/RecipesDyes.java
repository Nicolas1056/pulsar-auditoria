package net.minecraft.item.crafting;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class RecipesDyes {
   public void a(CraftingManager var1) {
      for (int var2 = 0; var2 < 16; var2++) {
         var1.b(new ItemStack(Blocks.L, 1, var2), new ItemStack(Items.aW, 1, 15 - var2), new ItemStack(Item.a(Blocks.L), 1, 0));
         var1.a(new ItemStack(Blocks.cu, 8, 15 - var2), "###", "#X#", "###", '#', new ItemStack(Blocks.cz), 'X', new ItemStack(Items.aW, 1, var2));
         var1.a(new ItemStack(Blocks.cG, 8, 15 - var2), "###", "#X#", "###", '#', new ItemStack(Blocks.w), 'X', new ItemStack(Items.aW, 1, var2));
         var1.a(new ItemStack(Blocks.cH, 16, var2), "###", "###", '#', new ItemStack(Blocks.cG, 1, var2));
      }

      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.e.b()), new ItemStack(Blocks.N, 1, BlockFlower.EnumFlowerType.a.b()));
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.o.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.b.b()));
      var1.b(new ItemStack(Items.aW, 3, EnumDyeColor.a.b()), Items.aX);
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.g.b()), new ItemStack(Items.aW, 1, EnumDyeColor.o.b()), new ItemStack(Items.aW, 1, EnumDyeColor.a.b()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.b.b()), new ItemStack(Items.aW, 1, EnumDyeColor.o.b()), new ItemStack(Items.aW, 1, EnumDyeColor.e.b()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.f.b()), new ItemStack(Items.aW, 1, EnumDyeColor.n.b()), new ItemStack(Items.aW, 1, EnumDyeColor.a.b()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.h.b()), new ItemStack(Items.aW, 1, EnumDyeColor.p.b()), new ItemStack(Items.aW, 1, EnumDyeColor.a.b()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.i.b()), new ItemStack(Items.aW, 1, EnumDyeColor.h.b()), new ItemStack(Items.aW, 1, EnumDyeColor.a.b()));
      var1.b(
         new ItemStack(Items.aW, 3, EnumDyeColor.i.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.p.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.a.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.a.b())
      );
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.d.b()), new ItemStack(Items.aW, 1, EnumDyeColor.l.b()), new ItemStack(Items.aW, 1, EnumDyeColor.a.b()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.j.b()), new ItemStack(Items.aW, 1, EnumDyeColor.l.b()), new ItemStack(Items.aW, 1, EnumDyeColor.n.b()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.k.b()), new ItemStack(Items.aW, 1, EnumDyeColor.l.b()), new ItemStack(Items.aW, 1, EnumDyeColor.o.b()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.c.b()), new ItemStack(Items.aW, 1, EnumDyeColor.k.b()), new ItemStack(Items.aW, 1, EnumDyeColor.g.b()));
      var1.b(
         new ItemStack(Items.aW, 3, EnumDyeColor.c.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.l.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.o.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.g.b())
      );
      var1.b(
         new ItemStack(Items.aW, 4, EnumDyeColor.c.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.l.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.o.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.o.b()),
         new ItemStack(Items.aW, 1, EnumDyeColor.a.b())
      );
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.d.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.c.b()));
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.c.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.d.b()));
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.i.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.e.b()));
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.o.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.f.b()));
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.b.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.g.b()));
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.i.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.h.b()));
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.g.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.i.b()));
      var1.b(new ItemStack(Items.aW, 1, EnumDyeColor.i.b()), new ItemStack(Blocks.O, 1, BlockFlower.EnumFlowerType.j.b()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.e.b()), new ItemStack(Blocks.cF, 1, BlockDoublePlant.EnumPlantType.a.a()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.c.b()), new ItemStack(Blocks.cF, 1, BlockDoublePlant.EnumPlantType.b.a()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.o.b()), new ItemStack(Blocks.cF, 1, BlockDoublePlant.EnumPlantType.e.a()));
      var1.b(new ItemStack(Items.aW, 2, EnumDyeColor.g.b()), new ItemStack(Blocks.cF, 1, BlockDoublePlant.EnumPlantType.f.a()));

      for (int var3 = 0; var3 < 16; var3++) {
         var1.a(new ItemStack(Blocks.cy, 3, var3), "##", '#', new ItemStack(Blocks.L, 1, var3));
      }
   }
}
