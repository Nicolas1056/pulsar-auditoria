package net.minecraft.item.crafting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.block.BlockWall;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class CraftingManager {
   private static final CraftingManager a = new CraftingManager();
   private final List<IRecipe> b = Lists.newArrayList();

   public static CraftingManager a() {
      return a;
   }

   private CraftingManager() {
      new RecipesTools().a(this);
      new RecipesWeapons().a(this);
      new RecipesIngots().a(this);
      new RecipesFood().a(this);
      new RecipesCrafting().a(this);
      new RecipesArmor().a(this);
      new RecipesDyes().a(this);
      this.b.add(new RecipesArmorDyes());
      this.b.add(new RecipeBookCloning());
      this.b.add(new RecipesMapCloning());
      this.b.add(new RecipesMapExtending());
      this.b.add(new RecipeFireworks());
      this.b.add(new RecipeRepairItem());
      new RecipesBanners().a(this);
      this.a(new ItemStack(Items.aK, 3), "###", '#', Items.aJ);
      this.b(new ItemStack(Items.aL, 1), Items.aK, Items.aK, Items.aK, Items.aF);
      this.b(new ItemStack(Items.bM, 1), Items.aL, new ItemStack(Items.aW, 1, EnumDyeColor.p.b()), Items.G);
      this.a(new ItemStack(Blocks.aO, 3), "W#W", "W#W", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.a.a()));
      this.a(new ItemStack(Blocks.aQ, 3), "W#W", "W#W", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.c.a()));
      this.a(new ItemStack(Blocks.aP, 3), "W#W", "W#W", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.b.a()));
      this.a(new ItemStack(Blocks.aR, 3), "W#W", "W#W", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.d.a()));
      this.a(new ItemStack(Blocks.aT, 3), "W#W", "W#W", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.e.a() - 4));
      this.a(new ItemStack(Blocks.aS, 3), "W#W", "W#W", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.f.a() - 4));
      this.a(new ItemStack(Blocks.bZ, 6, BlockWall.EnumType.a.a()), "###", "###", '#', Blocks.e);
      this.a(new ItemStack(Blocks.bZ, 6, BlockWall.EnumType.b.a()), "###", "###", '#', Blocks.Y);
      this.a(new ItemStack(Blocks.bz, 6), "###", "###", '#', Blocks.by);
      this.a(new ItemStack(Blocks.bo, 1), "#W#", "#W#", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.a.a()));
      this.a(new ItemStack(Blocks.bq, 1), "#W#", "#W#", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.c.a()));
      this.a(new ItemStack(Blocks.bp, 1), "#W#", "#W#", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.b.a()));
      this.a(new ItemStack(Blocks.br, 1), "#W#", "#W#", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.d.a()));
      this.a(new ItemStack(Blocks.bt, 1), "#W#", "#W#", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.e.a() - 4));
      this.a(new ItemStack(Blocks.bs, 1), "#W#", "#W#", '#', Items.y, 'W', new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.f.a() - 4));
      this.a(new ItemStack(Blocks.aN, 1), "###", "#X#", "###", '#', Blocks.f, 'X', Items.i);
      this.a(new ItemStack(Items.cn, 2), "~~ ", "~O ", "  ~", '~', Items.F, 'O', Items.aM);
      this.a(new ItemStack(Blocks.B, 1), "###", "#X#", "###", '#', Blocks.f, 'X', Items.aC);
      this.a(new ItemStack(Blocks.X, 1), "###", "XXX", "###", '#', Blocks.f, 'X', Items.aL);
      this.a(new ItemStack(Blocks.aJ, 1), "##", "##", '#', Items.aD);
      this.a(new ItemStack(Blocks.aH, 6), "###", '#', Blocks.aJ);
      this.a(new ItemStack(Blocks.aL, 1), "##", "##", '#', Items.aI);
      this.a(new ItemStack(Blocks.V, 1), "##", "##", '#', Items.aH);
      this.a(new ItemStack(Blocks.aX, 1), "##", "##", '#', Items.aT);
      this.a(new ItemStack(Blocks.cq, 1), "##", "##", '#', Items.cg);
      this.a(new ItemStack(Blocks.L, 1), "##", "##", '#', Items.F);
      this.a(new ItemStack(Blocks.W, 1), "X#X", "#X#", "X#X", 'X', Items.H, '#', Blocks.m);
      this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.d.a()), "###", '#', Blocks.e);
      this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.a.a()), "###", '#', new ItemStack(Blocks.b, BlockStone.EnumType.a.a()));
      this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.b.a()), "###", '#', Blocks.A);
      this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.e.a()), "###", '#', Blocks.V);
      this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.f.a()), "###", '#', Blocks.bf);
      this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.g.a()), "###", '#', Blocks.by);
      this.a(new ItemStack(Blocks.U, 6, BlockStoneSlab.EnumType.h.a()), "###", '#', Blocks.cq);
      this.a(new ItemStack(Blocks.cP, 6, BlockStoneSlabNew.EnumType.a.a()), "###", '#', Blocks.cM);
      this.a(new ItemStack(Blocks.bM, 6, 0), "###", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.a.a()));
      this.a(new ItemStack(Blocks.bM, 6, BlockPlanks.EnumType.c.a()), "###", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.c.a()));
      this.a(new ItemStack(Blocks.bM, 6, BlockPlanks.EnumType.b.a()), "###", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.b.a()));
      this.a(new ItemStack(Blocks.bM, 6, BlockPlanks.EnumType.d.a()), "###", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.d.a()));
      this.a(new ItemStack(Blocks.bM, 6, 4 + BlockPlanks.EnumType.e.a() - 4), "###", '#', new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.e.a() - 4));
      this.a(new ItemStack(Blocks.bM, 6, 4 + BlockPlanks.EnumType.f.a() - 4), "###", '#', new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.f.a() - 4));
      this.a(new ItemStack(Blocks.au, 3), "# #", "###", "# #", '#', Items.y);
      this.a(new ItemStack(Items.aq, 3), "##", "##", "##", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.a.a()));
      this.a(new ItemStack(Items.ar, 3), "##", "##", "##", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.b.a()));
      this.a(new ItemStack(Items.as, 3), "##", "##", "##", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.c.a()));
      this.a(new ItemStack(Items.at, 3), "##", "##", "##", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.d.a()));
      this.a(new ItemStack(Items.au, 3), "##", "##", "##", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.e.a()));
      this.a(new ItemStack(Items.av, 3), "##", "##", "##", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.f.a()));
      this.a(new ItemStack(Blocks.bd, 2), "###", "###", '#', Blocks.f);
      this.a(new ItemStack(Items.aB, 3), "##", "##", "##", '#', Items.j);
      this.a(new ItemStack(Blocks.cw, 1), "##", "##", '#', Items.j);
      this.a(new ItemStack(Items.ap, 3), "###", "###", " X ", '#', Blocks.f, 'X', Items.y);
      this.a(new ItemStack(Items.aZ, 1), "AAA", "BEB", "CCC", 'A', Items.aG, 'B', Items.aY, 'C', Items.O, 'E', Items.aP);
      this.a(new ItemStack(Items.aY, 1), "#", '#', Items.aJ);
      this.a(new ItemStack(Blocks.f, 4, BlockPlanks.EnumType.a.a()), "#", '#', new ItemStack(Blocks.r, 1, BlockPlanks.EnumType.a.a()));
      this.a(new ItemStack(Blocks.f, 4, BlockPlanks.EnumType.b.a()), "#", '#', new ItemStack(Blocks.r, 1, BlockPlanks.EnumType.b.a()));
      this.a(new ItemStack(Blocks.f, 4, BlockPlanks.EnumType.c.a()), "#", '#', new ItemStack(Blocks.r, 1, BlockPlanks.EnumType.c.a()));
      this.a(new ItemStack(Blocks.f, 4, BlockPlanks.EnumType.d.a()), "#", '#', new ItemStack(Blocks.r, 1, BlockPlanks.EnumType.d.a()));
      this.a(new ItemStack(Blocks.f, 4, 4 + BlockPlanks.EnumType.e.a() - 4), "#", '#', new ItemStack(Blocks.s, 1, BlockPlanks.EnumType.e.a() - 4));
      this.a(new ItemStack(Blocks.f, 4, 4 + BlockPlanks.EnumType.f.a() - 4), "#", '#', new ItemStack(Blocks.s, 1, BlockPlanks.EnumType.f.a() - 4));
      this.a(new ItemStack(Items.y, 4), "#", "#", '#', Blocks.f);
      this.a(new ItemStack(Blocks.aa, 4), "X", "#", 'X', Items.h, '#', Items.y);
      this.a(new ItemStack(Blocks.aa, 4), "X", "#", 'X', new ItemStack(Items.h, 1, 1), '#', Items.y);
      this.a(new ItemStack(Items.z, 4), "# #", " # ", '#', Blocks.f);
      this.a(new ItemStack(Items.bA, 3), "# #", " # ", '#', Blocks.w);
      this.a(new ItemStack(Blocks.av, 16), "X X", "X#X", "X X", 'X', Items.j, '#', Items.y);
      this.a(new ItemStack(Blocks.D, 6), "X X", "X#X", "XRX", 'X', Items.k, 'R', Items.aC, '#', Items.y);
      this.a(new ItemStack(Blocks.cs, 6), "XSX", "X#X", "XSX", 'X', Items.j, '#', Blocks.aF, 'S', Items.y);
      this.a(new ItemStack(Blocks.E, 6), "X X", "X#X", "XRX", 'X', Items.j, 'R', Items.aC, '#', Blocks.az);
      this.a(new ItemStack(Items.az, 1), "# #", "###", '#', Items.j);
      this.a(new ItemStack(Items.bG, 1), "# #", "# #", "###", '#', Items.j);
      this.a(new ItemStack(Items.bF, 1), " B ", "###", '#', Blocks.e, 'B', Items.bv);
      this.a(new ItemStack(Blocks.aZ, 1), "A", "B", 'A', Blocks.aU, 'B', Blocks.aa);
      this.a(new ItemStack(Items.aN, 1), "A", "B", 'A', Blocks.ae, 'B', Items.az);
      this.a(new ItemStack(Items.aO, 1), "A", "B", 'A', Blocks.al, 'B', Items.az);
      this.a(new ItemStack(Items.ch, 1), "A", "B", 'A', Blocks.W, 'B', Items.az);
      this.a(new ItemStack(Items.ci, 1), "A", "B", 'A', Blocks.cp, 'B', Items.az);
      this.a(new ItemStack(Items.aE, 1), "# #", "###", '#', Blocks.f);
      this.a(new ItemStack(Items.aw, 1), "# #", " # ", '#', Items.j);
      this.a(new ItemStack(Items.bQ, 1), "# #", " # ", '#', Items.aH);
      this.b(new ItemStack(Items.d, 1), new ItemStack(Items.j, 1), new ItemStack(Items.ak, 1));
      this.a(new ItemStack(Items.P, 1), "###", '#', Items.O);
      this.a(new ItemStack(Blocks.ad, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.a.a()));
      this.a(new ItemStack(Blocks.bV, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.c.a()));
      this.a(new ItemStack(Blocks.bU, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.b.a()));
      this.a(new ItemStack(Blocks.bW, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.f, 1, BlockPlanks.EnumType.d.a()));
      this.a(new ItemStack(Blocks.cC, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.e.a() - 4));
      this.a(new ItemStack(Blocks.cD, 4), "#  ", "## ", "###", '#', new ItemStack(Blocks.f, 1, 4 + BlockPlanks.EnumType.f.a() - 4));
      this.a(new ItemStack(Items.aR, 1), "  #", " #X", "# X", '#', Items.y, 'X', Items.F);
      this.a(new ItemStack(Items.bY, 1), "# ", " X", '#', Items.aR, 'X', Items.bR);
      this.a(new ItemStack(Blocks.aw, 4), "#  ", "## ", "###", '#', Blocks.e);
      this.a(new ItemStack(Blocks.bu, 4), "#  ", "## ", "###", '#', Blocks.V);
      this.a(new ItemStack(Blocks.bv, 4), "#  ", "## ", "###", '#', Blocks.bf);
      this.a(new ItemStack(Blocks.bA, 4), "#  ", "## ", "###", '#', Blocks.by);
      this.a(new ItemStack(Blocks.bO, 4), "#  ", "## ", "###", '#', Blocks.A);
      this.a(new ItemStack(Blocks.cN, 4), "#  ", "## ", "###", '#', Blocks.cM);
      this.a(new ItemStack(Blocks.cr, 4), "#  ", "## ", "###", '#', Blocks.cq);
      this.a(new ItemStack(Items.an, 1), "###", "#X#", "###", '#', Items.y, 'X', Blocks.L);
      this.a(new ItemStack(Items.bP, 1), "###", "#X#", "###", '#', Items.y, 'X', Items.aF);
      this.a(new ItemStack(Items.ao, 1, 0), "###", "#X#", "###", '#', Items.k, 'X', Items.e);
      this.a(new ItemStack(Items.ao, 1, 1), "###", "#X#", "###", '#', Blocks.R, 'X', Items.e);
      this.a(new ItemStack(Items.bW, 1, 0), "###", "#X#", "###", '#', Items.bx, 'X', Items.bR);
      this.a(new ItemStack(Items.bI, 1), "###", "#X#", "###", '#', Items.bx, 'X', Items.bf);
      this.a(new ItemStack(Blocks.ay, 1), "X", "#", '#', Blocks.e, 'X', Items.y);
      this.a(new ItemStack(Blocks.bR, 2), "I", "S", "#", '#', Blocks.f, 'S', Items.y, 'I', Items.j);
      this.a(new ItemStack(Blocks.aF, 1), "X", "#", '#', Items.y, 'X', Items.aC);
      this.a(new ItemStack(Items.bb, 1), "#X#", "III", '#', Blocks.aF, 'X', Items.aC, 'I', new ItemStack(Blocks.b, 1, BlockStone.EnumType.a.a()));
      this.a(new ItemStack(Items.ce, 1), " # ", "#X#", "III", '#', Blocks.aF, 'X', Items.cg, 'I', new ItemStack(Blocks.b, 1, BlockStone.EnumType.a.a()));
      this.a(new ItemStack(Items.aS, 1), " # ", "#X#", " # ", '#', Items.k, 'X', Items.aC);
      this.a(new ItemStack(Items.aQ, 1), " # ", "#X#", " # ", '#', Items.j, 'X', Items.aC);
      this.a(new ItemStack(Items.bV, 1), "###", "#X#", "###", '#', Items.aK, 'X', Items.aQ);
      this.a(new ItemStack(Blocks.aG, 1), "#", '#', new ItemStack(Blocks.b, 1, BlockStone.EnumType.a.a()));
      this.a(new ItemStack(Blocks.cd, 1), "#", '#', Blocks.f);
      this.a(new ItemStack(Blocks.az, 1), "##", '#', new ItemStack(Blocks.b, 1, BlockStone.EnumType.a.a()));
      this.a(new ItemStack(Blocks.aB, 1), "##", '#', Blocks.f);
      this.a(new ItemStack(Blocks.ci, 1), "##", '#', Items.j);
      this.a(new ItemStack(Blocks.ch, 1), "##", '#', Items.k);
      this.a(new ItemStack(Blocks.z, 1), "###", "#X#", "#R#", '#', Blocks.e, 'X', Items.f, 'R', Items.aC);
      this.a(new ItemStack(Blocks.ct, 1), "###", "# #", "#R#", '#', Blocks.e, 'R', Items.aC);
      this.a(new ItemStack(Blocks.J, 1), "TTT", "#X#", "#R#", '#', Blocks.e, 'X', Items.j, 'R', Items.aC, 'T', Blocks.f);
      this.a(new ItemStack(Blocks.F, 1), "S", "P", 'S', Items.aM, 'P', Blocks.J);
      this.a(new ItemStack(Items.ba, 1), "###", "XXX", '#', Blocks.L, 'X', Blocks.f);
      this.a(new ItemStack(Blocks.bC, 1), " B ", "D#D", "###", '#', Blocks.Z, 'B', Items.aL, 'D', Items.i);
      this.a(new ItemStack(Blocks.cf, 1), "III", " i ", "iii", 'I', Blocks.S, 'i', Items.j);
      this.a(new ItemStack(Items.aF), "##", "##", '#', Items.bs);
      this.b(new ItemStack(Items.bH, 1), Items.bu, Items.bD);
      this.b(new ItemStack(Items.bL, 3), Items.H, Items.bD, Items.h);
      this.b(new ItemStack(Items.bL, 3), Items.H, Items.bD, new ItemStack(Items.h, 1, 1));
      this.a(new ItemStack(Blocks.cl), "GGG", "QQQ", "WWW", 'G', Blocks.w, 'Q', Items.cg, 'W', Blocks.bM);
      this.a(new ItemStack(Blocks.cp), "I I", "ICI", " I ", 'I', Items.j, 'C', Blocks.ae);
      this.a(new ItemStack(Items.cj, 1), "///", " / ", "/_/", '/', Items.y, '_', new ItemStack(Blocks.U, 1, BlockStoneSlab.EnumType.a.a()));
      Collections.sort(this.b, new Comparator<IRecipe>() {
         public int a(IRecipe var1, IRecipe var2) {
            if (var1 instanceof ShapelessRecipes && var2 instanceof ShapedRecipes) {
               return 1;
            } else if (var2 instanceof ShapelessRecipes && var1 instanceof ShapedRecipes) {
               return -1;
            } else if (var2.a() < var1.a()) {
               return -1;
            } else {
               return var2.a() > var1.a() ? 1 : 0;
            }
         }
      });
   }

   public ShapedRecipes a(ItemStack var1, Object... var2) {
      String var3 = "";
      int var4 = 0;
      int var5 = 0;
      int var6 = 0;
      if (var2[var4] instanceof String[]) {
         String[] var11 = (String[])var2[var4++];

         for (int var8 = 0; var8 < var11.length; var8++) {
            String var9 = var11[var8];
            var6++;
            var5 = var9.length();
            var3 = var3 + var9;
         }
      } else {
         while (var2[var4] instanceof String) {
            String var7 = (String)var2[var4++];
            var6++;
            var5 = var7.length();
            var3 = var3 + var7;
         }
      }

      HashMap var12;
      for (var12 = Maps.newHashMap(); var4 < var2.length; var4 += 2) {
         Character var13 = (Character)var2[var4];
         ItemStack var15 = null;
         if (var2[var4 + 1] instanceof Item) {
            var15 = new ItemStack((Item)var2[var4 + 1]);
         } else if (var2[var4 + 1] instanceof Block) {
            var15 = new ItemStack((Block)var2[var4 + 1], 1, 32767);
         } else if (var2[var4 + 1] instanceof ItemStack) {
            var15 = (ItemStack)var2[var4 + 1];
         }

         var12.put(var13, var15);
      }

      ItemStack[] var14 = new ItemStack[var5 * var6];

      for (int var16 = 0; var16 < var5 * var6; var16++) {
         char var10 = var3.charAt(var16);
         if (var12.containsKey(var10)) {
            var14[var16] = ((ItemStack)var12.get(var10)).k();
         } else {
            var14[var16] = null;
         }
      }

      ShapedRecipes var17 = new ShapedRecipes(var5, var6, var14, var1);
      this.b.add(var17);
      return var17;
   }

   public void b(ItemStack var1, Object... var2) {
      ArrayList var3 = Lists.newArrayList();

      for (Object var7 : var2) {
         if (var7 instanceof ItemStack) {
            var3.add(((ItemStack)var7).k());
         } else if (var7 instanceof Item) {
            var3.add(new ItemStack((Item)var7));
         } else {
            if (!(var7 instanceof Block)) {
               throw new IllegalArgumentException("Invalid shapeless recipe: unknown type " + var7.getClass().getName() + "!");
            }

            var3.add(new ItemStack((Block)var7));
         }
      }

      this.b.add(new ShapelessRecipes(var1, var3));
   }

   public void a(IRecipe var1) {
      this.b.add(var1);
   }

   public ItemStack a(InventoryCrafting var1, World var2) {
      for (IRecipe var4 : this.b) {
         if (var4.a(var1, var2)) {
            return var4.a(var1);
         }
      }

      return null;
   }

   public ItemStack[] b(InventoryCrafting var1, World var2) {
      for (IRecipe var4 : this.b) {
         if (var4.a(var1, var2)) {
            return var4.b(var1);
         }
      }

      ItemStack[] var5 = new ItemStack[var1.o_()];

      for (int var6 = 0; var6 < var5.length; var6++) {
         var5[var6] = var1.a(var6);
      }

      return var5;
   }

   public List<IRecipe> b() {
      return this.b;
   }
}
