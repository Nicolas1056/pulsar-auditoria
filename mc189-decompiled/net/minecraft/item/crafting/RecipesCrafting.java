package net.minecraft.item.crafting;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockPrismarine;
import net.minecraft.block.BlockQuartz;
import net.minecraft.block.BlockRedSandstone;
import net.minecraft.block.BlockSand;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockStoneSlabNew;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;

public class RecipesCrafting {
   public void a(CraftingManager var1) {
      var1.a(new ItemStack(Blocks.ae), "###", "# #", "###", '#', Blocks.f);
      var1.a(new ItemStack(Blocks.cg), "#-", '#', Blocks.ae, '-', Blocks.bR);
      var1.a(new ItemStack(Blocks.bQ), "###", "#E#", "###", '#', Blocks.Z, 'E', Items.bH);
      var1.a(new ItemStack(Blocks.al), "###", "# #", "###", '#', Blocks.e);
      var1.a(new ItemStack(Blocks.ai), "##", "##", '#', Blocks.f);
      var1.a(new ItemStack(Blocks.A), "##", "##", '#', new ItemStack(Blocks.m, 1, BlockSand.EnumType.a.a()));
      var1.a(new ItemStack(Blocks.cM), "##", "##", '#', new ItemStack(Blocks.m, 1, BlockSand.EnumType.b.a()));
      var1.a(new ItemStack(Blocks.A, 4, BlockSandStone.EnumType.c.a()), "##", "##", '#', new ItemStack(Blocks.A, 1, BlockSandStone.EnumType.a.a()));
      var1.a(new ItemStack(Blocks.cM, 4, BlockRedSandstone.EnumType.c.a()), "##", "##", '#', new ItemStack(Blocks.cM, 1, BlockRedSandstone.EnumType.a.a()));
      var1.a(new ItemStack(Blocks.A, 1, BlockSandStone.EnumType.b.a()), "#", "#", '#', new ItemStack(Blocks.U, 1, BlockStoneSlab.EnumType.b.a()));
      var1.a(new ItemStack(Blocks.cM, 1, BlockRedSandstone.EnumType.b.a()), "#", "#", '#', new ItemStack(Blocks.cP, 1, BlockStoneSlabNew.EnumType.a.a()));
      var1.a(new ItemStack(Blocks.cq, 1, BlockQuartz.EnumType.b.a()), "#", "#", '#', new ItemStack(Blocks.U, 1, BlockStoneSlab.EnumType.h.a()));
      var1.a(new ItemStack(Blocks.cq, 2, BlockQuartz.EnumType.c.a()), "#", "#", '#', new ItemStack(Blocks.cq, 1, BlockQuartz.EnumType.a.a()));
      var1.a(new ItemStack(Blocks.bf, 4), "##", "##", '#', new ItemStack(Blocks.b, 1, BlockStone.EnumType.a.a()));
      var1.a(new ItemStack(Blocks.bf, 1, BlockStoneBrick.P), "#", "#", '#', new ItemStack(Blocks.U, 1, BlockStoneSlab.EnumType.f.a()));
      var1.b(new ItemStack(Blocks.bf, 1, BlockStoneBrick.N), Blocks.bf, Blocks.bn);
      var1.b(new ItemStack(Blocks.Y, 1), Blocks.e, Blocks.bn);
      var1.a(new ItemStack(Blocks.bi, 16), "###", "###", '#', Items.j);
      var1.a(new ItemStack(Blocks.bj, 16), "###", "###", '#', Blocks.w);
      var1.a(new ItemStack(Blocks.bJ, 1), " R ", "RGR", " R ", 'R', Items.aC, 'G', Blocks.aX);
      var1.a(new ItemStack(Blocks.bY, 1), "GGG", "GSG", "OOO", 'G', Blocks.w, 'S', Items.bZ, 'O', Blocks.Z);
      var1.a(new ItemStack(Blocks.by, 1), "NN", "NN", 'N', Items.cf);
      var1.a(new ItemStack(Blocks.b, 2, BlockStone.EnumType.d.a()), "CQ", "QC", 'C', Blocks.e, 'Q', Items.cg);
      var1.b(new ItemStack(Blocks.b, 1, BlockStone.EnumType.b.a()), new ItemStack(Blocks.b, 1, BlockStone.EnumType.d.a()), Items.cg);
      var1.b(new ItemStack(Blocks.b, 2, BlockStone.EnumType.f.a()), new ItemStack(Blocks.b, 1, BlockStone.EnumType.d.a()), Blocks.e);
      var1.a(new ItemStack(Blocks.d, 4, BlockDirt.DirtType.b.a()), "DG", "GD", 'D', new ItemStack(Blocks.d, 1, BlockDirt.DirtType.a.a()), 'G', Blocks.n);
      var1.a(new ItemStack(Blocks.b, 4, BlockStone.EnumType.e.a()), "SS", "SS", 'S', new ItemStack(Blocks.b, 1, BlockStone.EnumType.d.a()));
      var1.a(new ItemStack(Blocks.b, 4, BlockStone.EnumType.c.a()), "SS", "SS", 'S', new ItemStack(Blocks.b, 1, BlockStone.EnumType.b.a()));
      var1.a(new ItemStack(Blocks.b, 4, BlockStone.EnumType.g.a()), "SS", "SS", 'S', new ItemStack(Blocks.b, 1, BlockStone.EnumType.f.a()));
      var1.a(new ItemStack(Blocks.cI, 1, BlockPrismarine.b), "SS", "SS", 'S', Items.cC);
      var1.a(new ItemStack(Blocks.cI, 1, BlockPrismarine.N), "SSS", "SSS", "SSS", 'S', Items.cC);
      var1.a(new ItemStack(Blocks.cI, 1, BlockPrismarine.O), "SSS", "SIS", "SSS", 'S', Items.cC, 'I', new ItemStack(Items.aW, 1, EnumDyeColor.p.b()));
      var1.a(new ItemStack(Blocks.cJ, 1, 0), "SCS", "CCC", "SCS", 'S', Items.cC, 'C', Items.cD);
   }
}
