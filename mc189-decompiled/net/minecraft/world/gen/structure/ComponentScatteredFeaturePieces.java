package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.block.BlockLever;
import net.minecraft.block.BlockPlanks;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.BlockTripWire;
import net.minecraft.block.BlockTripWireHook;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class ComponentScatteredFeaturePieces {
   public static void a() {
      MapGenStructureIO.a(ComponentScatteredFeaturePieces.DesertPyramid.class, "TeDP");
      MapGenStructureIO.a(ComponentScatteredFeaturePieces.JunglePyramid.class, "TeJP");
      MapGenStructureIO.a(ComponentScatteredFeaturePieces.SwampHut.class, "TeSH");
   }

   public static class DesertPyramid extends ComponentScatteredFeaturePieces.Feature {
      private boolean[] e = new boolean[4];
      private static final List<WeightedRandomChestContent> f = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.i, 0, 1, 3, 3),
            new WeightedRandomChestContent(Items.j, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.k, 0, 2, 7, 15),
            new WeightedRandomChestContent(Items.bO, 0, 1, 3, 2),
            new WeightedRandomChestContent(Items.aX, 0, 4, 6, 20),
            new WeightedRandomChestContent(Items.bt, 0, 3, 7, 16),
            new WeightedRandomChestContent(Items.aA, 0, 1, 1, 3),
            new WeightedRandomChestContent(Items.ck, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.cl, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.cm, 0, 1, 1, 1)
         }
      );

      public DesertPyramid() {
      }

      public DesertPyramid(Random var1, int var2, int var3) {
         super(var1, var2, 64, var3, 21, 15, 21);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("hasPlacedChest0", this.e[0]);
         var1.a("hasPlacedChest1", this.e[1]);
         var1.a("hasPlacedChest2", this.e[2]);
         var1.a("hasPlacedChest3", this.e[3]);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.e[0] = var1.n("hasPlacedChest0");
         this.e[1] = var1.n("hasPlacedChest1");
         this.e[2] = var1.n("hasPlacedChest2");
         this.e[3] = var1.n("hasPlacedChest3");
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, -4, 0, this.a - 1, 0, this.c - 1, Blocks.A.Q(), Blocks.A.Q(), false);

         for (int var4 = 1; var4 <= 9; var4++) {
            this.a(var1, var3, var4, var4, var4, this.a - 1 - var4, var4, this.c - 1 - var4, Blocks.A.Q(), Blocks.A.Q(), false);
            this.a(var1, var3, var4 + 1, var4, var4 + 1, this.a - 2 - var4, var4, this.c - 2 - var4, Blocks.a.Q(), Blocks.a.Q(), false);
         }

         for (int var14 = 0; var14 < this.a; var14++) {
            for (int var5 = 0; var5 < this.c; var5++) {
               byte var6 = -5;
               this.b(var1, Blocks.A.Q(), var14, var6, var5, var3);
            }
         }

         int var15 = this.a(Blocks.bO, 3);
         int var16 = this.a(Blocks.bO, 2);
         int var17 = this.a(Blocks.bO, 0);
         int var7 = this.a(Blocks.bO, 1);
         int var8 = ~EnumDyeColor.b.b() & 15;
         int var9 = ~EnumDyeColor.l.b() & 15;
         this.a(var1, var3, 0, 0, 0, 4, 9, 4, Blocks.A.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 1, 10, 1, 3, 10, 3, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, Blocks.bO.a(var15), 2, 10, 0, var3);
         this.a(var1, Blocks.bO.a(var16), 2, 10, 4, var3);
         this.a(var1, Blocks.bO.a(var17), 0, 10, 2, var3);
         this.a(var1, Blocks.bO.a(var7), 4, 10, 2, var3);
         this.a(var1, var3, this.a - 5, 0, 0, this.a - 1, 9, 4, Blocks.A.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, this.a - 4, 10, 1, this.a - 2, 10, 3, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, Blocks.bO.a(var15), this.a - 3, 10, 0, var3);
         this.a(var1, Blocks.bO.a(var16), this.a - 3, 10, 4, var3);
         this.a(var1, Blocks.bO.a(var17), this.a - 5, 10, 2, var3);
         this.a(var1, Blocks.bO.a(var7), this.a - 1, 10, 2, var3);
         this.a(var1, var3, 8, 0, 0, 12, 4, 4, Blocks.A.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 9, 1, 0, 11, 3, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 9, 1, 1, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 9, 2, 1, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 9, 3, 1, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 10, 3, 1, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 11, 3, 1, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 11, 2, 1, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 11, 1, 1, var3);
         this.a(var1, var3, 4, 1, 1, 8, 3, 3, Blocks.A.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 4, 1, 2, 8, 2, 2, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 12, 1, 1, 16, 3, 3, Blocks.A.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 12, 1, 2, 16, 2, 2, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 5, 4, 5, this.a - 6, 4, this.c - 6, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, 9, 4, 9, 11, 4, 11, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 8, 1, 8, 8, 3, 8, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, var3, 12, 1, 8, 12, 3, 8, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, var3, 8, 1, 12, 8, 3, 12, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, var3, 12, 1, 12, 12, 3, 12, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, var3, 1, 1, 5, 4, 4, 11, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, this.a - 5, 1, 5, this.a - 2, 4, 11, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, 6, 7, 9, 6, 7, 11, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, this.a - 7, 7, 9, this.a - 7, 7, 11, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, 5, 5, 9, 5, 7, 11, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, var3, this.a - 6, 5, 9, this.a - 6, 7, 11, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, Blocks.a.Q(), 5, 5, 10, var3);
         this.a(var1, Blocks.a.Q(), 5, 6, 10, var3);
         this.a(var1, Blocks.a.Q(), 6, 6, 10, var3);
         this.a(var1, Blocks.a.Q(), this.a - 6, 5, 10, var3);
         this.a(var1, Blocks.a.Q(), this.a - 6, 6, 10, var3);
         this.a(var1, Blocks.a.Q(), this.a - 7, 6, 10, var3);
         this.a(var1, var3, 2, 4, 4, 2, 6, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, this.a - 3, 4, 4, this.a - 3, 6, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, Blocks.bO.a(var15), 2, 4, 5, var3);
         this.a(var1, Blocks.bO.a(var15), 2, 3, 4, var3);
         this.a(var1, Blocks.bO.a(var15), this.a - 3, 4, 5, var3);
         this.a(var1, Blocks.bO.a(var15), this.a - 3, 3, 4, var3);
         this.a(var1, var3, 1, 1, 3, 2, 2, 3, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, this.a - 3, 1, 3, this.a - 2, 2, 3, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, Blocks.bO.Q(), 1, 1, 2, var3);
         this.a(var1, Blocks.bO.Q(), this.a - 2, 1, 2, var3);
         this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.b.a()), 1, 2, 2, var3);
         this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.b.a()), this.a - 2, 2, 2, var3);
         this.a(var1, Blocks.bO.a(var7), 2, 1, 2, var3);
         this.a(var1, Blocks.bO.a(var17), this.a - 3, 1, 2, var3);
         this.a(var1, var3, 4, 3, 5, 4, 3, 18, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, this.a - 5, 3, 5, this.a - 5, 3, 17, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, 3, 1, 5, 4, 2, 16, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, this.a - 6, 1, 5, this.a - 5, 2, 16, Blocks.a.Q(), Blocks.a.Q(), false);

         for (byte var10 = 5; var10 <= 17; var10 += 2) {
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 4, 1, var10, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), 4, 2, var10, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), this.a - 5, 1, var10, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), this.a - 5, 2, var10, var3);
         }

         this.a(var1, Blocks.cu.a(var8), 10, 0, 7, var3);
         this.a(var1, Blocks.cu.a(var8), 10, 0, 8, var3);
         this.a(var1, Blocks.cu.a(var8), 9, 0, 9, var3);
         this.a(var1, Blocks.cu.a(var8), 11, 0, 9, var3);
         this.a(var1, Blocks.cu.a(var8), 8, 0, 10, var3);
         this.a(var1, Blocks.cu.a(var8), 12, 0, 10, var3);
         this.a(var1, Blocks.cu.a(var8), 7, 0, 10, var3);
         this.a(var1, Blocks.cu.a(var8), 13, 0, 10, var3);
         this.a(var1, Blocks.cu.a(var8), 9, 0, 11, var3);
         this.a(var1, Blocks.cu.a(var8), 11, 0, 11, var3);
         this.a(var1, Blocks.cu.a(var8), 10, 0, 12, var3);
         this.a(var1, Blocks.cu.a(var8), 10, 0, 13, var3);
         this.a(var1, Blocks.cu.a(var9), 10, 0, 10, var3);

         for (int var18 = 0; var18 <= this.a - 1; var18 += this.a - 1) {
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 2, 1, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 2, 2, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 2, 3, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 3, 1, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 3, 2, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 3, 3, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 4, 1, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), var18, 4, 2, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 4, 3, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 5, 1, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 5, 2, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 5, 3, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 6, 1, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), var18, 6, 2, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 6, 3, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 7, 1, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 7, 2, var3);
            this.a(var1, Blocks.cu.a(var8), var18, 7, 3, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 8, 1, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 8, 2, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var18, 8, 3, var3);
         }

         for (int var19 = 2; var19 <= this.a - 3; var19 += this.a - 3 - 2) {
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19 - 1, 2, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19, 2, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19 + 1, 2, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19 - 1, 3, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19, 3, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19 + 1, 3, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19 - 1, 4, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), var19, 4, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19 + 1, 4, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19 - 1, 5, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19, 5, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19 + 1, 5, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19 - 1, 6, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), var19, 6, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19 + 1, 6, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19 - 1, 7, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19, 7, 0, var3);
            this.a(var1, Blocks.cu.a(var8), var19 + 1, 7, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19 - 1, 8, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19, 8, 0, var3);
            this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), var19 + 1, 8, 0, var3);
         }

         this.a(var1, var3, 8, 4, 0, 12, 6, 0, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, Blocks.a.Q(), 8, 6, 0, var3);
         this.a(var1, Blocks.a.Q(), 12, 6, 0, var3);
         this.a(var1, Blocks.cu.a(var8), 9, 5, 0, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), 10, 5, 0, var3);
         this.a(var1, Blocks.cu.a(var8), 11, 5, 0, var3);
         this.a(var1, var3, 8, -14, 8, 12, -11, 12, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, var3, 8, -10, 8, 12, -10, 12, Blocks.A.a(BlockSandStone.EnumType.b.a()), Blocks.A.a(BlockSandStone.EnumType.b.a()), false);
         this.a(var1, var3, 8, -9, 8, 12, -9, 12, Blocks.A.a(BlockSandStone.EnumType.c.a()), Blocks.A.a(BlockSandStone.EnumType.c.a()), false);
         this.a(var1, var3, 8, -8, 8, 12, -1, 12, Blocks.A.Q(), Blocks.A.Q(), false);
         this.a(var1, var3, 9, -11, 9, 11, -1, 11, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, Blocks.az.Q(), 10, -11, 10, var3);
         this.a(var1, var3, 9, -13, 9, 11, -13, 11, Blocks.W.Q(), Blocks.a.Q(), false);
         this.a(var1, Blocks.a.Q(), 8, -11, 10, var3);
         this.a(var1, Blocks.a.Q(), 8, -10, 10, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), 7, -10, 10, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 7, -11, 10, var3);
         this.a(var1, Blocks.a.Q(), 12, -11, 10, var3);
         this.a(var1, Blocks.a.Q(), 12, -10, 10, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), 13, -10, 10, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 13, -11, 10, var3);
         this.a(var1, Blocks.a.Q(), 10, -11, 8, var3);
         this.a(var1, Blocks.a.Q(), 10, -10, 8, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), 10, -10, 7, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 10, -11, 7, var3);
         this.a(var1, Blocks.a.Q(), 10, -11, 12, var3);
         this.a(var1, Blocks.a.Q(), 10, -10, 12, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.b.a()), 10, -10, 13, var3);
         this.a(var1, Blocks.A.a(BlockSandStone.EnumType.c.a()), 10, -11, 13, var3);

         for (EnumFacing var11 : EnumFacing.Plane.a) {
            if (!this.e[var11.b()]) {
               int var12 = var11.g() * 2;
               int var13 = var11.i() * 2;
               this.e[var11.b()] = this.a(var1, var3, var2, 10 + var12, -11, 10 + var13, WeightedRandomChestContent.a(f, Items.cd.b(var2)), 2 + var2.nextInt(5));
            }
         }

         return true;
      }
   }

   abstract static class Feature extends StructureComponent {
      protected int a;
      protected int b;
      protected int c;
      protected int d = -1;

      public Feature() {
      }

      protected Feature(Random var1, int var2, int var3, int var4, int var5, int var6, int var7) {
         super(0);
         this.a = var5;
         this.b = var6;
         this.c = var7;
         this.m = EnumFacing.Plane.a.a(var1);
         switch (this.m) {
            case c:
            case d:
               this.l = new StructureBoundingBox(var2, var3, var4, var2 + var5 - 1, var3 + var6 - 1, var4 + var7 - 1);
               break;
            default:
               this.l = new StructureBoundingBox(var2, var3, var4, var2 + var7 - 1, var3 + var6 - 1, var4 + var5 - 1);
         }
      }

      @Override
      protected void a(NBTTagCompound var1) {
         var1.a("Width", this.a);
         var1.a("Height", this.b);
         var1.a("Depth", this.c);
         var1.a("HPos", this.d);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         this.a = var1.f("Width");
         this.b = var1.f("Height");
         this.c = var1.f("Depth");
         this.d = var1.f("HPos");
      }

      protected boolean a(World var1, StructureBoundingBox var2, int var3) {
         if (this.d >= 0) {
            return true;
         } else {
            int var4 = 0;
            int var5 = 0;
            BlockPos.MutableBlockPos var6 = new BlockPos.MutableBlockPos();

            for (int var7 = this.l.c; var7 <= this.l.f; var7++) {
               for (int var8 = this.l.a; var8 <= this.l.d; var8++) {
                  var6.c(var8, 64, var7);
                  if (var2.b(var6)) {
                     var4 += Math.max(var1.r(var6).o(), var1.t.i());
                     var5++;
                  }
               }
            }

            if (var5 == 0) {
               return false;
            } else {
               this.d = var4 / var5;
               this.l.a(0, this.d - this.l.b + var3, 0);
               return true;
            }
         }
      }
   }

   public static class JunglePyramid extends ComponentScatteredFeaturePieces.Feature {
      private boolean e;
      private boolean f;
      private boolean g;
      private boolean h;
      private static final List<WeightedRandomChestContent> i = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.i, 0, 1, 3, 3),
            new WeightedRandomChestContent(Items.j, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.k, 0, 2, 7, 15),
            new WeightedRandomChestContent(Items.bO, 0, 1, 3, 2),
            new WeightedRandomChestContent(Items.aX, 0, 4, 6, 20),
            new WeightedRandomChestContent(Items.bt, 0, 3, 7, 16),
            new WeightedRandomChestContent(Items.aA, 0, 1, 1, 3),
            new WeightedRandomChestContent(Items.ck, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.cl, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.cm, 0, 1, 1, 1)
         }
      );
      private static final List<WeightedRandomChestContent> j = Lists.newArrayList(
         new WeightedRandomChestContent[]{new WeightedRandomChestContent(Items.g, 0, 2, 7, 30)}
      );
      private static ComponentScatteredFeaturePieces.JunglePyramid.Stones k = new ComponentScatteredFeaturePieces.JunglePyramid.Stones();

      public JunglePyramid() {
      }

      public JunglePyramid(Random var1, int var2, int var3) {
         super(var1, var2, 64, var3, 12, 10, 15);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("placedMainChest", this.e);
         var1.a("placedHiddenChest", this.f);
         var1.a("placedTrap1", this.g);
         var1.a("placedTrap2", this.h);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.e = var1.n("placedMainChest");
         this.f = var1.n("placedHiddenChest");
         this.g = var1.n("placedTrap1");
         this.h = var1.n("placedTrap2");
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (!this.a(var1, var3, 0)) {
            return false;
         } else {
            int var4 = this.a(Blocks.aw, 3);
            int var5 = this.a(Blocks.aw, 2);
            int var6 = this.a(Blocks.aw, 0);
            int var7 = this.a(Blocks.aw, 1);
            this.a(var1, var3, 0, -4, 0, this.a - 1, 0, this.c - 1, false, var2, k);
            this.a(var1, var3, 2, 1, 2, 9, 2, 2, false, var2, k);
            this.a(var1, var3, 2, 1, 12, 9, 2, 12, false, var2, k);
            this.a(var1, var3, 2, 1, 3, 2, 2, 11, false, var2, k);
            this.a(var1, var3, 9, 1, 3, 9, 2, 11, false, var2, k);
            this.a(var1, var3, 1, 3, 1, 10, 6, 1, false, var2, k);
            this.a(var1, var3, 1, 3, 13, 10, 6, 13, false, var2, k);
            this.a(var1, var3, 1, 3, 2, 1, 6, 12, false, var2, k);
            this.a(var1, var3, 10, 3, 2, 10, 6, 12, false, var2, k);
            this.a(var1, var3, 2, 3, 2, 9, 3, 12, false, var2, k);
            this.a(var1, var3, 2, 6, 2, 9, 6, 12, false, var2, k);
            this.a(var1, var3, 3, 7, 3, 8, 7, 11, false, var2, k);
            this.a(var1, var3, 4, 8, 4, 7, 8, 10, false, var2, k);
            this.a(var1, var3, 3, 1, 3, 8, 2, 11);
            this.a(var1, var3, 4, 3, 6, 7, 3, 9);
            this.a(var1, var3, 2, 4, 2, 9, 5, 12);
            this.a(var1, var3, 4, 6, 5, 7, 6, 9);
            this.a(var1, var3, 5, 7, 6, 6, 7, 8);
            this.a(var1, var3, 5, 1, 2, 6, 2, 2);
            this.a(var1, var3, 5, 2, 12, 6, 2, 12);
            this.a(var1, var3, 5, 5, 1, 6, 5, 1);
            this.a(var1, var3, 5, 5, 13, 6, 5, 13);
            this.a(var1, Blocks.a.Q(), 1, 5, 5, var3);
            this.a(var1, Blocks.a.Q(), 10, 5, 5, var3);
            this.a(var1, Blocks.a.Q(), 1, 5, 9, var3);
            this.a(var1, Blocks.a.Q(), 10, 5, 9, var3);

            for (byte var8 = 0; var8 <= 14; var8 += 14) {
               this.a(var1, var3, 2, 4, var8, 2, 5, var8, false, var2, k);
               this.a(var1, var3, 4, 4, var8, 4, 5, var8, false, var2, k);
               this.a(var1, var3, 7, 4, var8, 7, 5, var8, false, var2, k);
               this.a(var1, var3, 9, 4, var8, 9, 5, var8, false, var2, k);
            }

            this.a(var1, var3, 5, 6, 0, 6, 6, 0, false, var2, k);

            for (byte var10 = 0; var10 <= 11; var10 += 11) {
               for (byte var9 = 2; var9 <= 12; var9 += 2) {
                  this.a(var1, var3, var10, 4, var9, var10, 5, var9, false, var2, k);
               }

               this.a(var1, var3, var10, 6, 5, var10, 6, 5, false, var2, k);
               this.a(var1, var3, var10, 6, 9, var10, 6, 9, false, var2, k);
            }

            this.a(var1, var3, 2, 7, 2, 2, 9, 2, false, var2, k);
            this.a(var1, var3, 9, 7, 2, 9, 9, 2, false, var2, k);
            this.a(var1, var3, 2, 7, 12, 2, 9, 12, false, var2, k);
            this.a(var1, var3, 9, 7, 12, 9, 9, 12, false, var2, k);
            this.a(var1, var3, 4, 9, 4, 4, 9, 4, false, var2, k);
            this.a(var1, var3, 7, 9, 4, 7, 9, 4, false, var2, k);
            this.a(var1, var3, 4, 9, 10, 4, 9, 10, false, var2, k);
            this.a(var1, var3, 7, 9, 10, 7, 9, 10, false, var2, k);
            this.a(var1, var3, 5, 9, 7, 6, 9, 7, false, var2, k);
            this.a(var1, Blocks.aw.a(var4), 5, 9, 6, var3);
            this.a(var1, Blocks.aw.a(var4), 6, 9, 6, var3);
            this.a(var1, Blocks.aw.a(var5), 5, 9, 8, var3);
            this.a(var1, Blocks.aw.a(var5), 6, 9, 8, var3);
            this.a(var1, Blocks.aw.a(var4), 4, 0, 0, var3);
            this.a(var1, Blocks.aw.a(var4), 5, 0, 0, var3);
            this.a(var1, Blocks.aw.a(var4), 6, 0, 0, var3);
            this.a(var1, Blocks.aw.a(var4), 7, 0, 0, var3);
            this.a(var1, Blocks.aw.a(var4), 4, 1, 8, var3);
            this.a(var1, Blocks.aw.a(var4), 4, 2, 9, var3);
            this.a(var1, Blocks.aw.a(var4), 4, 3, 10, var3);
            this.a(var1, Blocks.aw.a(var4), 7, 1, 8, var3);
            this.a(var1, Blocks.aw.a(var4), 7, 2, 9, var3);
            this.a(var1, Blocks.aw.a(var4), 7, 3, 10, var3);
            this.a(var1, var3, 4, 1, 9, 4, 1, 9, false, var2, k);
            this.a(var1, var3, 7, 1, 9, 7, 1, 9, false, var2, k);
            this.a(var1, var3, 4, 1, 10, 7, 2, 10, false, var2, k);
            this.a(var1, var3, 5, 4, 5, 6, 4, 5, false, var2, k);
            this.a(var1, Blocks.aw.a(var6), 4, 4, 5, var3);
            this.a(var1, Blocks.aw.a(var7), 7, 4, 5, var3);

            for (int var11 = 0; var11 < 4; var11++) {
               this.a(var1, Blocks.aw.a(var5), 5, 0 - var11, 6 + var11, var3);
               this.a(var1, Blocks.aw.a(var5), 6, 0 - var11, 6 + var11, var3);
               this.a(var1, var3, 5, 0 - var11, 7 + var11, 6, 0 - var11, 9 + var11);
            }

            this.a(var1, var3, 1, -3, 12, 10, -1, 13);
            this.a(var1, var3, 1, -3, 1, 3, -1, 13);
            this.a(var1, var3, 1, -3, 1, 9, -1, 5);

            for (byte var12 = 1; var12 <= 13; var12 += 2) {
               this.a(var1, var3, 1, -3, var12, 1, -2, var12, false, var2, k);
            }

            for (byte var13 = 2; var13 <= 12; var13 += 2) {
               this.a(var1, var3, 1, -1, var13, 3, -1, var13, false, var2, k);
            }

            this.a(var1, var3, 2, -2, 1, 5, -2, 1, false, var2, k);
            this.a(var1, var3, 7, -2, 1, 9, -2, 1, false, var2, k);
            this.a(var1, var3, 6, -3, 1, 6, -3, 1, false, var2, k);
            this.a(var1, var3, 6, -1, 1, 6, -1, 1, false, var2, k);
            this.a(var1, Blocks.bR.a(this.a(Blocks.bR, EnumFacing.f.b())).a(BlockTripWireHook.N, true), 1, -3, 8, var3);
            this.a(var1, Blocks.bR.a(this.a(Blocks.bR, EnumFacing.e.b())).a(BlockTripWireHook.N, true), 4, -3, 8, var3);
            this.a(var1, Blocks.bS.Q().a(BlockTripWire.N, true), 2, -3, 8, var3);
            this.a(var1, Blocks.bS.Q().a(BlockTripWire.N, true), 3, -3, 8, var3);
            this.a(var1, Blocks.af.Q(), 5, -3, 7, var3);
            this.a(var1, Blocks.af.Q(), 5, -3, 6, var3);
            this.a(var1, Blocks.af.Q(), 5, -3, 5, var3);
            this.a(var1, Blocks.af.Q(), 5, -3, 4, var3);
            this.a(var1, Blocks.af.Q(), 5, -3, 3, var3);
            this.a(var1, Blocks.af.Q(), 5, -3, 2, var3);
            this.a(var1, Blocks.af.Q(), 5, -3, 1, var3);
            this.a(var1, Blocks.af.Q(), 4, -3, 1, var3);
            this.a(var1, Blocks.Y.Q(), 3, -3, 1, var3);
            if (!this.g) {
               this.g = this.a(var1, var3, var2, 3, -2, 1, EnumFacing.c.a(), j, 2);
            }

            this.a(var1, Blocks.bn.a(15), 3, -2, 2, var3);
            this.a(var1, Blocks.bR.a(this.a(Blocks.bR, EnumFacing.c.b())).a(BlockTripWireHook.N, true), 7, -3, 1, var3);
            this.a(var1, Blocks.bR.a(this.a(Blocks.bR, EnumFacing.d.b())).a(BlockTripWireHook.N, true), 7, -3, 5, var3);
            this.a(var1, Blocks.bS.Q().a(BlockTripWire.N, true), 7, -3, 2, var3);
            this.a(var1, Blocks.bS.Q().a(BlockTripWire.N, true), 7, -3, 3, var3);
            this.a(var1, Blocks.bS.Q().a(BlockTripWire.N, true), 7, -3, 4, var3);
            this.a(var1, Blocks.af.Q(), 8, -3, 6, var3);
            this.a(var1, Blocks.af.Q(), 9, -3, 6, var3);
            this.a(var1, Blocks.af.Q(), 9, -3, 5, var3);
            this.a(var1, Blocks.Y.Q(), 9, -3, 4, var3);
            this.a(var1, Blocks.af.Q(), 9, -2, 4, var3);
            if (!this.h) {
               this.h = this.a(var1, var3, var2, 9, -2, 3, EnumFacing.e.a(), j, 2);
            }

            this.a(var1, Blocks.bn.a(15), 8, -1, 3, var3);
            this.a(var1, Blocks.bn.a(15), 8, -2, 3, var3);
            if (!this.e) {
               this.e = this.a(var1, var3, var2, 8, -3, 3, WeightedRandomChestContent.a(i, Items.cd.b(var2)), 2 + var2.nextInt(5));
            }

            this.a(var1, Blocks.Y.Q(), 9, -3, 2, var3);
            this.a(var1, Blocks.Y.Q(), 8, -3, 1, var3);
            this.a(var1, Blocks.Y.Q(), 4, -3, 5, var3);
            this.a(var1, Blocks.Y.Q(), 5, -2, 5, var3);
            this.a(var1, Blocks.Y.Q(), 5, -1, 5, var3);
            this.a(var1, Blocks.Y.Q(), 6, -3, 5, var3);
            this.a(var1, Blocks.Y.Q(), 7, -2, 5, var3);
            this.a(var1, Blocks.Y.Q(), 7, -1, 5, var3);
            this.a(var1, Blocks.Y.Q(), 8, -3, 5, var3);
            this.a(var1, var3, 9, -1, 1, 9, -1, 5, false, var2, k);
            this.a(var1, var3, 8, -3, 8, 10, -1, 10);
            this.a(var1, Blocks.bf.a(BlockStoneBrick.P), 8, -2, 11, var3);
            this.a(var1, Blocks.bf.a(BlockStoneBrick.P), 9, -2, 11, var3);
            this.a(var1, Blocks.bf.a(BlockStoneBrick.P), 10, -2, 11, var3);
            this.a(var1, Blocks.ay.a(BlockLever.a(EnumFacing.a(this.a(Blocks.ay, EnumFacing.c.a())))), 8, -2, 12, var3);
            this.a(var1, Blocks.ay.a(BlockLever.a(EnumFacing.a(this.a(Blocks.ay, EnumFacing.c.a())))), 9, -2, 12, var3);
            this.a(var1, Blocks.ay.a(BlockLever.a(EnumFacing.a(this.a(Blocks.ay, EnumFacing.c.a())))), 10, -2, 12, var3);
            this.a(var1, var3, 8, -3, 8, 8, -3, 10, false, var2, k);
            this.a(var1, var3, 10, -3, 8, 10, -3, 10, false, var2, k);
            this.a(var1, Blocks.Y.Q(), 10, -2, 9, var3);
            this.a(var1, Blocks.af.Q(), 8, -2, 9, var3);
            this.a(var1, Blocks.af.Q(), 8, -2, 10, var3);
            this.a(var1, Blocks.af.Q(), 10, -1, 9, var3);
            this.a(var1, Blocks.F.a(EnumFacing.b.a()), 9, -2, 8, var3);
            this.a(var1, Blocks.F.a(this.a(Blocks.F, EnumFacing.e.a())), 10, -2, 8, var3);
            this.a(var1, Blocks.F.a(this.a(Blocks.F, EnumFacing.e.a())), 10, -1, 8, var3);
            this.a(var1, Blocks.bb.a(this.a(Blocks.bb, EnumFacing.c.b())), 10, -2, 10, var3);
            if (!this.f) {
               this.f = this.a(var1, var3, var2, 9, -3, 10, WeightedRandomChestContent.a(i, Items.cd.b(var2)), 2 + var2.nextInt(5));
            }

            return true;
         }
      }

      static class Stones extends StructureComponent.BlockSelector {
         private Stones() {
         }

         @Override
         public void a(Random var1, int var2, int var3, int var4, boolean var5) {
            if (var1.nextFloat() < 0.4F) {
               this.a = Blocks.e.Q();
            } else {
               this.a = Blocks.Y.Q();
            }
         }
      }
   }

   public static class SwampHut extends ComponentScatteredFeaturePieces.Feature {
      private boolean e;

      public SwampHut() {
      }

      public SwampHut(Random var1, int var2, int var3) {
         super(var1, var2, 64, var3, 7, 7, 9);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Witch", this.e);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.e = var1.n("Witch");
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (!this.a(var1, var3, 0)) {
            return false;
         } else {
            this.a(var1, var3, 1, 1, 1, 5, 1, 7, Blocks.f.a(BlockPlanks.EnumType.b.a()), Blocks.f.a(BlockPlanks.EnumType.b.a()), false);
            this.a(var1, var3, 1, 4, 2, 5, 4, 7, Blocks.f.a(BlockPlanks.EnumType.b.a()), Blocks.f.a(BlockPlanks.EnumType.b.a()), false);
            this.a(var1, var3, 2, 1, 0, 4, 1, 0, Blocks.f.a(BlockPlanks.EnumType.b.a()), Blocks.f.a(BlockPlanks.EnumType.b.a()), false);
            this.a(var1, var3, 2, 2, 2, 3, 3, 2, Blocks.f.a(BlockPlanks.EnumType.b.a()), Blocks.f.a(BlockPlanks.EnumType.b.a()), false);
            this.a(var1, var3, 1, 2, 3, 1, 3, 6, Blocks.f.a(BlockPlanks.EnumType.b.a()), Blocks.f.a(BlockPlanks.EnumType.b.a()), false);
            this.a(var1, var3, 5, 2, 3, 5, 3, 6, Blocks.f.a(BlockPlanks.EnumType.b.a()), Blocks.f.a(BlockPlanks.EnumType.b.a()), false);
            this.a(var1, var3, 2, 2, 7, 4, 3, 7, Blocks.f.a(BlockPlanks.EnumType.b.a()), Blocks.f.a(BlockPlanks.EnumType.b.a()), false);
            this.a(var1, var3, 1, 0, 2, 1, 3, 2, Blocks.r.Q(), Blocks.r.Q(), false);
            this.a(var1, var3, 5, 0, 2, 5, 3, 2, Blocks.r.Q(), Blocks.r.Q(), false);
            this.a(var1, var3, 1, 0, 7, 1, 3, 7, Blocks.r.Q(), Blocks.r.Q(), false);
            this.a(var1, var3, 5, 0, 7, 5, 3, 7, Blocks.r.Q(), Blocks.r.Q(), false);
            this.a(var1, Blocks.aO.Q(), 2, 3, 2, var3);
            this.a(var1, Blocks.aO.Q(), 3, 3, 7, var3);
            this.a(var1, Blocks.a.Q(), 1, 3, 4, var3);
            this.a(var1, Blocks.a.Q(), 5, 3, 4, var3);
            this.a(var1, Blocks.a.Q(), 5, 3, 5, var3);
            this.a(var1, Blocks.ca.Q().a(BlockFlowerPot.b, BlockFlowerPot.EnumFlowerType.r), 1, 3, 5, var3);
            this.a(var1, Blocks.ai.Q(), 3, 2, 6, var3);
            this.a(var1, Blocks.bE.Q(), 4, 2, 6, var3);
            this.a(var1, Blocks.aO.Q(), 1, 2, 1, var3);
            this.a(var1, Blocks.aO.Q(), 5, 2, 1, var3);
            int var4 = this.a(Blocks.ad, 3);
            int var5 = this.a(Blocks.ad, 1);
            int var6 = this.a(Blocks.ad, 0);
            int var7 = this.a(Blocks.ad, 2);
            this.a(var1, var3, 0, 4, 1, 6, 4, 1, Blocks.bU.a(var4), Blocks.bU.a(var4), false);
            this.a(var1, var3, 0, 4, 2, 0, 4, 7, Blocks.bU.a(var6), Blocks.bU.a(var6), false);
            this.a(var1, var3, 6, 4, 2, 6, 4, 7, Blocks.bU.a(var5), Blocks.bU.a(var5), false);
            this.a(var1, var3, 0, 4, 8, 6, 4, 8, Blocks.bU.a(var7), Blocks.bU.a(var7), false);

            for (byte var8 = 2; var8 <= 7; var8 += 5) {
               for (byte var9 = 1; var9 <= 5; var9 += 4) {
                  this.b(var1, Blocks.r.Q(), var9, -1, var8, var3);
               }
            }

            if (!this.e) {
               int var12 = this.a(2, 5);
               int var13 = this.d(2);
               int var10 = this.b(2, 5);
               if (var3.b(new BlockPos(var12, var13, var10))) {
                  this.e = true;
                  EntityWitch var11 = new EntityWitch(var1);
                  var11.b(var12 + 0.5, var13, var10 + 0.5, 0.0F, 0.0F);
                  var11.a(var1.E(new BlockPos(var12, var13, var10)), null);
                  var1.d(var11);
               }
            }

            return true;
         }
      }
   }
}
