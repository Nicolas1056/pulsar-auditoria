package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSandStone;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.BlockTorch;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.WorldChunkManager;

public class StructureVillagePieces {
   public static void a() {
      MapGenStructureIO.a(StructureVillagePieces.House1.class, "ViBH");
      MapGenStructureIO.a(StructureVillagePieces.Field1.class, "ViDF");
      MapGenStructureIO.a(StructureVillagePieces.Field2.class, "ViF");
      MapGenStructureIO.a(StructureVillagePieces.Torch.class, "ViL");
      MapGenStructureIO.a(StructureVillagePieces.Hall.class, "ViPH");
      MapGenStructureIO.a(StructureVillagePieces.House4Garden.class, "ViSH");
      MapGenStructureIO.a(StructureVillagePieces.WoodHut.class, "ViSmH");
      MapGenStructureIO.a(StructureVillagePieces.Church.class, "ViST");
      MapGenStructureIO.a(StructureVillagePieces.House2.class, "ViS");
      MapGenStructureIO.a(StructureVillagePieces.Start.class, "ViStart");
      MapGenStructureIO.a(StructureVillagePieces.Path.class, "ViSR");
      MapGenStructureIO.a(StructureVillagePieces.House3.class, "ViTRH");
      MapGenStructureIO.a(StructureVillagePieces.Well.class, "ViW");
   }

   public static List<StructureVillagePieces.PieceWeight> a(Random var0, int var1) {
      ArrayList var2 = Lists.newArrayList();
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House4Garden.class, 4, MathHelper.a(var0, 2 + var1, 4 + var1 * 2)));
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Church.class, 20, MathHelper.a(var0, 0 + var1, 1 + var1)));
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House1.class, 20, MathHelper.a(var0, 0 + var1, 2 + var1)));
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.WoodHut.class, 3, MathHelper.a(var0, 2 + var1, 5 + var1 * 3)));
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Hall.class, 15, MathHelper.a(var0, 0 + var1, 2 + var1)));
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field1.class, 3, MathHelper.a(var0, 1 + var1, 4 + var1)));
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.Field2.class, 3, MathHelper.a(var0, 2 + var1, 4 + var1 * 2)));
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House2.class, 15, MathHelper.a(var0, 0, 1 + var1)));
      var2.add(new StructureVillagePieces.PieceWeight(StructureVillagePieces.House3.class, 8, MathHelper.a(var0, 0 + var1, 3 + var1 * 2)));
      Iterator var3 = var2.iterator();

      while (var3.hasNext()) {
         if (((StructureVillagePieces.PieceWeight)var3.next()).d == 0) {
            var3.remove();
         }
      }

      return var2;
   }

   private static int a(List<StructureVillagePieces.PieceWeight> var0) {
      boolean var1 = false;
      int var2 = 0;

      for (StructureVillagePieces.PieceWeight var4 : var0) {
         if (var4.d > 0 && var4.c < var4.d) {
            var1 = true;
         }

         var2 += var4.b;
      }

      return var1 ? var2 : -1;
   }

   private static StructureVillagePieces.Village a(
      StructureVillagePieces.Start var0,
      StructureVillagePieces.PieceWeight var1,
      List<StructureComponent> var2,
      Random var3,
      int var4,
      int var5,
      int var6,
      EnumFacing var7,
      int var8
   ) {
      Class var9 = var1.a;
      Object var10 = null;
      if (var9 == StructureVillagePieces.House4Garden.class) {
         var10 = StructureVillagePieces.House4Garden.a(var0, var2, var3, var4, var5, var6, var7, var8);
      } else if (var9 == StructureVillagePieces.Church.class) {
         var10 = StructureVillagePieces.Church.a(var0, var2, var3, var4, var5, var6, var7, var8);
      } else if (var9 == StructureVillagePieces.House1.class) {
         var10 = StructureVillagePieces.House1.a(var0, var2, var3, var4, var5, var6, var7, var8);
      } else if (var9 == StructureVillagePieces.WoodHut.class) {
         var10 = StructureVillagePieces.WoodHut.a(var0, var2, var3, var4, var5, var6, var7, var8);
      } else if (var9 == StructureVillagePieces.Hall.class) {
         var10 = StructureVillagePieces.Hall.a(var0, var2, var3, var4, var5, var6, var7, var8);
      } else if (var9 == StructureVillagePieces.Field1.class) {
         var10 = StructureVillagePieces.Field1.a(var0, var2, var3, var4, var5, var6, var7, var8);
      } else if (var9 == StructureVillagePieces.Field2.class) {
         var10 = StructureVillagePieces.Field2.a(var0, var2, var3, var4, var5, var6, var7, var8);
      } else if (var9 == StructureVillagePieces.House2.class) {
         var10 = StructureVillagePieces.House2.a(var0, var2, var3, var4, var5, var6, var7, var8);
      } else if (var9 == StructureVillagePieces.House3.class) {
         var10 = StructureVillagePieces.House3.a(var0, var2, var3, var4, var5, var6, var7, var8);
      }

      return (StructureVillagePieces.Village)var10;
   }

   private static StructureVillagePieces.Village c(
      StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      int var8 = a(var0.e);
      if (var8 <= 0) {
         return null;
      } else {
         int var9 = 0;

         while (var9 < 5) {
            var9++;
            int var10 = var2.nextInt(var8);

            for (StructureVillagePieces.PieceWeight var12 : var0.e) {
               var10 -= var12.b;
               if (var10 < 0) {
                  if (!var12.a(var7) || var12 == var0.d && var0.e.size() > 1) {
                     break;
                  }

                  StructureVillagePieces.Village var13 = a(var0, var12, var1, var2, var3, var4, var5, var6, var7);
                  if (var13 != null) {
                     var12.c++;
                     var0.d = var12;
                     if (!var12.a()) {
                        var0.e.remove(var12);
                     }

                     return var13;
                  }
               }
            }
         }

         StructureBoundingBox var14 = StructureVillagePieces.Torch.a(var0, var1, var2, var3, var4, var5, var6);
         return var14 != null ? new StructureVillagePieces.Torch(var0, var7, var2, var14, var6) : null;
      }
   }

   private static StructureComponent d(
      StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (var7 > 50) {
         return null;
      } else if (Math.abs(var3 - var0.c().a) <= 112 && Math.abs(var5 - var0.c().c) <= 112) {
         StructureVillagePieces.Village var8 = c(var0, var1, var2, var3, var4, var5, var6, var7 + 1);
         if (var8 != null) {
            int var9 = (var8.l.a + var8.l.d) / 2;
            int var10 = (var8.l.c + var8.l.f) / 2;
            int var11 = var8.l.d - var8.l.a;
            int var12 = var8.l.f - var8.l.c;
            int var13 = var11 > var12 ? var11 : var12;
            if (var0.e().a(var9, var10, var13 / 2 + 4, MapGenVillage.d)) {
               var1.add(var8);
               var0.f.add(var8);
               return var8;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static StructureComponent e(
      StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (var7 > 3 + var0.c) {
         return null;
      } else if (Math.abs(var3 - var0.c().a) <= 112 && Math.abs(var5 - var0.c().c) <= 112) {
         StructureBoundingBox var8 = StructureVillagePieces.Path.a(var0, var1, var2, var3, var4, var5, var6);
         if (var8 != null && var8.b > 10) {
            StructureVillagePieces.Path var9 = new StructureVillagePieces.Path(var0, var7, var2, var8, var6);
            int var10 = (var9.l.a + var9.l.d) / 2;
            int var11 = (var9.l.c + var9.l.f) / 2;
            int var12 = var9.l.d - var9.l.a;
            int var13 = var9.l.f - var9.l.c;
            int var14 = var12 > var13 ? var12 : var13;
            if (var0.e().a(var10, var11, var14 / 2 + 4, MapGenVillage.d)) {
               var1.add(var9);
               var0.g.add(var9);
               return var9;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   public static class Church extends StructureVillagePieces.Village {
      public Church() {
      }

      public Church(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
      }

      public static StructureVillagePieces.Church a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 5, 12, 9, var6);
         return a(var8) && StructureComponent.a(var1, var8) == null ? new StructureVillagePieces.Church(var0, var7, var2, var8, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 12 - 1, 0);
         }

         this.a(var1, var3, 1, 1, 1, 3, 3, 7, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 1, 5, 1, 3, 9, 3, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 1, 0, 0, 3, 0, 8, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 1, 0, 3, 10, 0, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 1, 1, 0, 10, 3, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 4, 1, 1, 4, 10, 3, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 0, 4, 0, 4, 7, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 4, 0, 4, 4, 4, 7, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 1, 8, 3, 4, 8, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 5, 4, 3, 10, 4, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 5, 5, 3, 5, 7, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 9, 0, 4, 9, 4, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 4, 0, 4, 4, 4, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, Blocks.e.Q(), 0, 11, 2, var3);
         this.a(var1, Blocks.e.Q(), 4, 11, 2, var3);
         this.a(var1, Blocks.e.Q(), 2, 11, 0, var3);
         this.a(var1, Blocks.e.Q(), 2, 11, 4, var3);
         this.a(var1, Blocks.e.Q(), 1, 1, 6, var3);
         this.a(var1, Blocks.e.Q(), 1, 1, 7, var3);
         this.a(var1, Blocks.e.Q(), 2, 1, 7, var3);
         this.a(var1, Blocks.e.Q(), 3, 1, 6, var3);
         this.a(var1, Blocks.e.Q(), 3, 1, 7, var3);
         this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 1, 1, 5, var3);
         this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 2, 1, 6, var3);
         this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 3, 1, 5, var3);
         this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 1)), 1, 2, 7, var3);
         this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 0)), 3, 2, 7, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 0, 3, 2, var3);
         this.a(var1, Blocks.bj.Q(), 4, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 4, 3, 2, var3);
         this.a(var1, Blocks.bj.Q(), 0, 6, 2, var3);
         this.a(var1, Blocks.bj.Q(), 0, 7, 2, var3);
         this.a(var1, Blocks.bj.Q(), 4, 6, 2, var3);
         this.a(var1, Blocks.bj.Q(), 4, 7, 2, var3);
         this.a(var1, Blocks.bj.Q(), 2, 6, 0, var3);
         this.a(var1, Blocks.bj.Q(), 2, 7, 0, var3);
         this.a(var1, Blocks.bj.Q(), 2, 6, 4, var3);
         this.a(var1, Blocks.bj.Q(), 2, 7, 4, var3);
         this.a(var1, Blocks.bj.Q(), 0, 3, 6, var3);
         this.a(var1, Blocks.bj.Q(), 4, 3, 6, var3);
         this.a(var1, Blocks.bj.Q(), 2, 3, 8, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m.d()), 2, 4, 7, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m.e()), 1, 4, 6, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m.f()), 3, 4, 6, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m), 2, 4, 5, var3);
         int var4 = this.a(Blocks.au, 4);

         for (int var5 = 1; var5 <= 9; var5++) {
            this.a(var1, Blocks.au.a(var4), 3, var5, 3, var3);
         }

         this.a(var1, Blocks.a.Q(), 2, 1, 0, var3);
         this.a(var1, Blocks.a.Q(), 2, 2, 0, var3);
         this.a(var1, var3, var2, 2, 1, 0, EnumFacing.b(this.a(Blocks.ao, 1)));
         if (this.a(var1, 2, 0, -1, var3).c().t() == Material.a && this.a(var1, 2, -1, -1, var3).c().t() != Material.a) {
            this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 2, 0, -1, var3);
         }

         for (int var7 = 0; var7 < 9; var7++) {
            for (int var6 = 0; var6 < 5; var6++) {
               this.b(var1, var6, 12, var7, var3);
               this.b(var1, Blocks.e.Q(), var6, -1, var7, var3);
            }
         }

         this.a(var1, var3, 2, 1, 2, 1);
         return true;
      }

      @Override
      protected int c(int var1, int var2) {
         return 2;
      }
   }

   public static class Field1 extends StructureVillagePieces.Village {
      private Block a;
      private Block b;
      private Block c;
      private Block d;

      public Field1() {
      }

      public Field1(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
         this.a = this.a(var3);
         this.b = this.a(var3);
         this.c = this.a(var3);
         this.d = this.a(var3);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("CA", Block.c.b(this.a));
         var1.a("CB", Block.c.b(this.b));
         var1.a("CC", Block.c.b(this.c));
         var1.a("CD", Block.c.b(this.d));
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = Block.c(var1.f("CA"));
         this.b = Block.c(var1.f("CB"));
         this.c = Block.c(var1.f("CC"));
         this.d = Block.c(var1.f("CD"));
      }

      private Block a(Random var1) {
         switch (var1.nextInt(5)) {
            case 0:
               return Blocks.cb;
            case 1:
               return Blocks.cc;
            default:
               return Blocks.aj;
         }
      }

      public static StructureVillagePieces.Field1 a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 13, 4, 9, var6);
         return a(var8) && StructureComponent.a(var1, var8) == null ? new StructureVillagePieces.Field1(var0, var7, var2, var8, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 4 - 1, 0);
         }

         this.a(var1, var3, 0, 1, 0, 12, 4, 8, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 1, 0, 1, 2, 0, 7, Blocks.ak.Q(), Blocks.ak.Q(), false);
         this.a(var1, var3, 4, 0, 1, 5, 0, 7, Blocks.ak.Q(), Blocks.ak.Q(), false);
         this.a(var1, var3, 7, 0, 1, 8, 0, 7, Blocks.ak.Q(), Blocks.ak.Q(), false);
         this.a(var1, var3, 10, 0, 1, 11, 0, 7, Blocks.ak.Q(), Blocks.ak.Q(), false);
         this.a(var1, var3, 0, 0, 0, 0, 0, 8, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 6, 0, 0, 6, 0, 8, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 12, 0, 0, 12, 0, 8, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 1, 0, 0, 11, 0, 0, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 1, 0, 8, 11, 0, 8, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 3, 0, 1, 3, 0, 7, Blocks.j.Q(), Blocks.j.Q(), false);
         this.a(var1, var3, 9, 0, 1, 9, 0, 7, Blocks.j.Q(), Blocks.j.Q(), false);

         for (int var4 = 1; var4 <= 7; var4++) {
            this.a(var1, this.a.a(MathHelper.a(var2, 2, 7)), 1, 1, var4, var3);
            this.a(var1, this.a.a(MathHelper.a(var2, 2, 7)), 2, 1, var4, var3);
            this.a(var1, this.b.a(MathHelper.a(var2, 2, 7)), 4, 1, var4, var3);
            this.a(var1, this.b.a(MathHelper.a(var2, 2, 7)), 5, 1, var4, var3);
            this.a(var1, this.c.a(MathHelper.a(var2, 2, 7)), 7, 1, var4, var3);
            this.a(var1, this.c.a(MathHelper.a(var2, 2, 7)), 8, 1, var4, var3);
            this.a(var1, this.d.a(MathHelper.a(var2, 2, 7)), 10, 1, var4, var3);
            this.a(var1, this.d.a(MathHelper.a(var2, 2, 7)), 11, 1, var4, var3);
         }

         for (int var6 = 0; var6 < 9; var6++) {
            for (int var5 = 0; var5 < 13; var5++) {
               this.b(var1, var5, 4, var6, var3);
               this.b(var1, Blocks.d.Q(), var5, -1, var6, var3);
            }
         }

         return true;
      }
   }

   public static class Field2 extends StructureVillagePieces.Village {
      private Block a;
      private Block b;

      public Field2() {
      }

      public Field2(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
         this.a = this.a(var3);
         this.b = this.a(var3);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("CA", Block.c.b(this.a));
         var1.a("CB", Block.c.b(this.b));
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = Block.c(var1.f("CA"));
         this.b = Block.c(var1.f("CB"));
      }

      private Block a(Random var1) {
         switch (var1.nextInt(5)) {
            case 0:
               return Blocks.cb;
            case 1:
               return Blocks.cc;
            default:
               return Blocks.aj;
         }
      }

      public static StructureVillagePieces.Field2 a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 7, 4, 9, var6);
         return a(var8) && StructureComponent.a(var1, var8) == null ? new StructureVillagePieces.Field2(var0, var7, var2, var8, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 4 - 1, 0);
         }

         this.a(var1, var3, 0, 1, 0, 6, 4, 8, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 1, 0, 1, 2, 0, 7, Blocks.ak.Q(), Blocks.ak.Q(), false);
         this.a(var1, var3, 4, 0, 1, 5, 0, 7, Blocks.ak.Q(), Blocks.ak.Q(), false);
         this.a(var1, var3, 0, 0, 0, 0, 0, 8, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 6, 0, 0, 6, 0, 8, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 1, 0, 0, 5, 0, 0, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 1, 0, 8, 5, 0, 8, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 3, 0, 1, 3, 0, 7, Blocks.j.Q(), Blocks.j.Q(), false);

         for (int var4 = 1; var4 <= 7; var4++) {
            this.a(var1, this.a.a(MathHelper.a(var2, 2, 7)), 1, 1, var4, var3);
            this.a(var1, this.a.a(MathHelper.a(var2, 2, 7)), 2, 1, var4, var3);
            this.a(var1, this.b.a(MathHelper.a(var2, 2, 7)), 4, 1, var4, var3);
            this.a(var1, this.b.a(MathHelper.a(var2, 2, 7)), 5, 1, var4, var3);
         }

         for (int var6 = 0; var6 < 9; var6++) {
            for (int var5 = 0; var5 < 7; var5++) {
               this.b(var1, var5, 4, var6, var3);
               this.b(var1, Blocks.d.Q(), var5, -1, var6, var3);
            }
         }

         return true;
      }
   }

   public static class Hall extends StructureVillagePieces.Village {
      public Hall() {
      }

      public Hall(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
      }

      public static StructureVillagePieces.Hall a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 9, 7, 11, var6);
         return a(var8) && StructureComponent.a(var1, var8) == null ? new StructureVillagePieces.Hall(var0, var7, var2, var8, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 7 - 1, 0);
         }

         this.a(var1, var3, 1, 1, 1, 7, 4, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 2, 1, 6, 8, 4, 10, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 2, 0, 6, 8, 0, 10, Blocks.d.Q(), Blocks.d.Q(), false);
         this.a(var1, Blocks.e.Q(), 6, 0, 6, var3);
         this.a(var1, var3, 2, 1, 6, 2, 1, 10, Blocks.aO.Q(), Blocks.aO.Q(), false);
         this.a(var1, var3, 8, 1, 6, 8, 1, 10, Blocks.aO.Q(), Blocks.aO.Q(), false);
         this.a(var1, var3, 3, 1, 10, 7, 1, 10, Blocks.aO.Q(), Blocks.aO.Q(), false);
         this.a(var1, var3, 1, 0, 1, 7, 0, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 0, 0, 0, 3, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 8, 0, 0, 8, 3, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 0, 0, 7, 1, 0, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 0, 5, 7, 1, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 2, 0, 7, 3, 0, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 2, 5, 7, 3, 5, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 4, 1, 8, 4, 1, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 4, 4, 8, 4, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 5, 2, 8, 5, 3, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, Blocks.f.Q(), 0, 4, 2, var3);
         this.a(var1, Blocks.f.Q(), 0, 4, 3, var3);
         this.a(var1, Blocks.f.Q(), 8, 4, 2, var3);
         this.a(var1, Blocks.f.Q(), 8, 4, 3, var3);
         int var4 = this.a(Blocks.ad, 3);
         int var5 = this.a(Blocks.ad, 2);

         for (int var6 = -1; var6 <= 2; var6++) {
            for (int var7 = 0; var7 <= 8; var7++) {
               this.a(var1, Blocks.ad.a(var4), var7, 4 + var6, var6, var3);
               this.a(var1, Blocks.ad.a(var5), var7, 4 + var6, 5 - var6, var3);
            }
         }

         this.a(var1, Blocks.r.Q(), 0, 2, 1, var3);
         this.a(var1, Blocks.r.Q(), 0, 2, 4, var3);
         this.a(var1, Blocks.r.Q(), 8, 2, 1, var3);
         this.a(var1, Blocks.r.Q(), 8, 2, 4, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 3, var3);
         this.a(var1, Blocks.bj.Q(), 8, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 8, 2, 3, var3);
         this.a(var1, Blocks.bj.Q(), 2, 2, 5, var3);
         this.a(var1, Blocks.bj.Q(), 3, 2, 5, var3);
         this.a(var1, Blocks.bj.Q(), 5, 2, 0, var3);
         this.a(var1, Blocks.bj.Q(), 6, 2, 5, var3);
         this.a(var1, Blocks.aO.Q(), 2, 1, 3, var3);
         this.a(var1, Blocks.aB.Q(), 2, 2, 3, var3);
         this.a(var1, Blocks.f.Q(), 1, 1, 4, var3);
         this.a(var1, Blocks.ad.a(this.a(Blocks.ad, 3)), 2, 1, 4, var3);
         this.a(var1, Blocks.ad.a(this.a(Blocks.ad, 1)), 1, 1, 3, var3);
         this.a(var1, var3, 5, 0, 1, 7, 0, 3, Blocks.T.Q(), Blocks.T.Q(), false);
         this.a(var1, Blocks.T.Q(), 6, 1, 1, var3);
         this.a(var1, Blocks.T.Q(), 6, 1, 2, var3);
         this.a(var1, Blocks.a.Q(), 2, 1, 0, var3);
         this.a(var1, Blocks.a.Q(), 2, 2, 0, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m), 2, 3, 1, var3);
         this.a(var1, var3, var2, 2, 1, 0, EnumFacing.b(this.a(Blocks.ao, 1)));
         if (this.a(var1, 2, 0, -1, var3).c().t() == Material.a && this.a(var1, 2, -1, -1, var3).c().t() != Material.a) {
            this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 2, 0, -1, var3);
         }

         this.a(var1, Blocks.a.Q(), 6, 1, 5, var3);
         this.a(var1, Blocks.a.Q(), 6, 2, 5, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m.d()), 6, 3, 4, var3);
         this.a(var1, var3, var2, 6, 1, 5, EnumFacing.b(this.a(Blocks.ao, 1)));

         for (int var8 = 0; var8 < 5; var8++) {
            for (int var9 = 0; var9 < 9; var9++) {
               this.b(var1, var9, 7, var8, var3);
               this.b(var1, Blocks.e.Q(), var9, -1, var8, var3);
            }
         }

         this.a(var1, var3, 4, 1, 2, 2);
         return true;
      }

      @Override
      protected int c(int var1, int var2) {
         return var1 == 0 ? 4 : super.c(var1, var2);
      }
   }

   public static class House1 extends StructureVillagePieces.Village {
      public House1() {
      }

      public House1(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
      }

      public static StructureVillagePieces.House1 a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 9, 9, 6, var6);
         return a(var8) && StructureComponent.a(var1, var8) == null ? new StructureVillagePieces.House1(var0, var7, var2, var8, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 9 - 1, 0);
         }

         this.a(var1, var3, 1, 1, 1, 7, 5, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 0, 0, 8, 0, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 5, 0, 8, 5, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 6, 1, 8, 6, 4, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 7, 2, 8, 7, 3, Blocks.e.Q(), Blocks.e.Q(), false);
         int var4 = this.a(Blocks.ad, 3);
         int var5 = this.a(Blocks.ad, 2);

         for (int var6 = -1; var6 <= 2; var6++) {
            for (int var7 = 0; var7 <= 8; var7++) {
               this.a(var1, Blocks.ad.a(var4), var7, 6 + var6, var6, var3);
               this.a(var1, Blocks.ad.a(var5), var7, 6 + var6, 5 - var6, var3);
            }
         }

         this.a(var1, var3, 0, 1, 0, 0, 1, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 1, 5, 8, 1, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 8, 1, 0, 8, 1, 4, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 2, 1, 0, 7, 1, 0, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 2, 0, 0, 4, 0, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 2, 5, 0, 4, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 8, 2, 5, 8, 4, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 8, 2, 0, 8, 4, 0, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 2, 1, 0, 4, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 2, 5, 7, 4, 5, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 8, 2, 1, 8, 4, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 2, 0, 7, 4, 0, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, Blocks.bj.Q(), 4, 2, 0, var3);
         this.a(var1, Blocks.bj.Q(), 5, 2, 0, var3);
         this.a(var1, Blocks.bj.Q(), 6, 2, 0, var3);
         this.a(var1, Blocks.bj.Q(), 4, 3, 0, var3);
         this.a(var1, Blocks.bj.Q(), 5, 3, 0, var3);
         this.a(var1, Blocks.bj.Q(), 6, 3, 0, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 3, var3);
         this.a(var1, Blocks.bj.Q(), 0, 3, 2, var3);
         this.a(var1, Blocks.bj.Q(), 0, 3, 3, var3);
         this.a(var1, Blocks.bj.Q(), 8, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 8, 2, 3, var3);
         this.a(var1, Blocks.bj.Q(), 8, 3, 2, var3);
         this.a(var1, Blocks.bj.Q(), 8, 3, 3, var3);
         this.a(var1, Blocks.bj.Q(), 2, 2, 5, var3);
         this.a(var1, Blocks.bj.Q(), 3, 2, 5, var3);
         this.a(var1, Blocks.bj.Q(), 5, 2, 5, var3);
         this.a(var1, Blocks.bj.Q(), 6, 2, 5, var3);
         this.a(var1, var3, 1, 4, 1, 7, 4, 1, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 4, 4, 7, 4, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 3, 4, 7, 3, 4, Blocks.X.Q(), Blocks.X.Q(), false);
         this.a(var1, Blocks.f.Q(), 7, 1, 4, var3);
         this.a(var1, Blocks.ad.a(this.a(Blocks.ad, 0)), 7, 1, 3, var3);
         int var9 = this.a(Blocks.ad, 3);
         this.a(var1, Blocks.ad.a(var9), 6, 1, 4, var3);
         this.a(var1, Blocks.ad.a(var9), 5, 1, 4, var3);
         this.a(var1, Blocks.ad.a(var9), 4, 1, 4, var3);
         this.a(var1, Blocks.ad.a(var9), 3, 1, 4, var3);
         this.a(var1, Blocks.aO.Q(), 6, 1, 3, var3);
         this.a(var1, Blocks.aB.Q(), 6, 2, 3, var3);
         this.a(var1, Blocks.aO.Q(), 4, 1, 3, var3);
         this.a(var1, Blocks.aB.Q(), 4, 2, 3, var3);
         this.a(var1, Blocks.ai.Q(), 7, 1, 1, var3);
         this.a(var1, Blocks.a.Q(), 1, 1, 0, var3);
         this.a(var1, Blocks.a.Q(), 1, 2, 0, var3);
         this.a(var1, var3, var2, 1, 1, 0, EnumFacing.b(this.a(Blocks.ao, 1)));
         if (this.a(var1, 1, 0, -1, var3).c().t() == Material.a && this.a(var1, 1, -1, -1, var3).c().t() != Material.a) {
            this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 1, 0, -1, var3);
         }

         for (int var10 = 0; var10 < 6; var10++) {
            for (int var8 = 0; var8 < 9; var8++) {
               this.b(var1, var8, 9, var10, var3);
               this.b(var1, Blocks.e.Q(), var8, -1, var10, var3);
            }
         }

         this.a(var1, var3, 2, 1, 2, 1);
         return true;
      }

      @Override
      protected int c(int var1, int var2) {
         return 1;
      }
   }

   public static class House2 extends StructureVillagePieces.Village {
      private static final List<WeightedRandomChestContent> a = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.i, 0, 1, 3, 3),
            new WeightedRandomChestContent(Items.j, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.k, 0, 1, 3, 5),
            new WeightedRandomChestContent(Items.P, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.e, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.b, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.l, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.Z, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.Y, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.aa, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.ab, 0, 1, 1, 5),
            new WeightedRandomChestContent(Item.a(Blocks.Z), 0, 3, 7, 5),
            new WeightedRandomChestContent(Item.a(Blocks.g), 0, 3, 7, 5),
            new WeightedRandomChestContent(Items.aA, 0, 1, 1, 3),
            new WeightedRandomChestContent(Items.ck, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.cl, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.cm, 0, 1, 1, 1)
         }
      );
      private boolean b;

      public House2() {
      }

      public House2(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
      }

      public static StructureVillagePieces.House2 a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 10, 6, 7, var6);
         return a(var8) && StructureComponent.a(var1, var8) == null ? new StructureVillagePieces.House2(var0, var7, var2, var8, var6) : null;
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Chest", this.b);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.b = var1.n("Chest");
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 6 - 1, 0);
         }

         this.a(var1, var3, 0, 1, 0, 9, 4, 6, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 0, 0, 9, 0, 6, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 4, 0, 9, 4, 6, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 5, 0, 9, 5, 6, Blocks.U.Q(), Blocks.U.Q(), false);
         this.a(var1, var3, 1, 5, 1, 8, 5, 5, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 1, 1, 0, 2, 3, 0, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 1, 0, 0, 4, 0, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 3, 1, 0, 3, 4, 0, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 0, 1, 6, 0, 4, 6, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, Blocks.f.Q(), 3, 3, 1, var3);
         this.a(var1, var3, 3, 1, 2, 3, 3, 2, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 4, 1, 3, 5, 3, 3, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 1, 1, 0, 3, 5, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 1, 6, 5, 3, 6, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 5, 1, 0, 5, 3, 0, Blocks.aO.Q(), Blocks.aO.Q(), false);
         this.a(var1, var3, 9, 1, 0, 9, 3, 0, Blocks.aO.Q(), Blocks.aO.Q(), false);
         this.a(var1, var3, 6, 1, 4, 9, 4, 6, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, Blocks.k.Q(), 7, 1, 5, var3);
         this.a(var1, Blocks.k.Q(), 8, 1, 5, var3);
         this.a(var1, Blocks.bi.Q(), 9, 2, 5, var3);
         this.a(var1, Blocks.bi.Q(), 9, 2, 4, var3);
         this.a(var1, var3, 7, 2, 4, 8, 2, 5, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, Blocks.e.Q(), 6, 1, 3, var3);
         this.a(var1, Blocks.al.Q(), 6, 2, 3, var3);
         this.a(var1, Blocks.al.Q(), 6, 3, 3, var3);
         this.a(var1, Blocks.T.Q(), 8, 1, 1, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 4, var3);
         this.a(var1, Blocks.bj.Q(), 2, 2, 6, var3);
         this.a(var1, Blocks.bj.Q(), 4, 2, 6, var3);
         this.a(var1, Blocks.aO.Q(), 2, 1, 4, var3);
         this.a(var1, Blocks.aB.Q(), 2, 2, 4, var3);
         this.a(var1, Blocks.f.Q(), 1, 1, 5, var3);
         this.a(var1, Blocks.ad.a(this.a(Blocks.ad, 3)), 2, 1, 5, var3);
         this.a(var1, Blocks.ad.a(this.a(Blocks.ad, 1)), 1, 1, 4, var3);
         if (!this.b && var3.b(new BlockPos(this.a(5, 5), this.d(1), this.b(5, 5)))) {
            this.b = true;
            this.a(var1, var3, var2, 5, 1, 5, a, 3 + var2.nextInt(6));
         }

         for (int var4 = 6; var4 <= 8; var4++) {
            if (this.a(var1, var4, 0, -1, var3).c().t() == Material.a && this.a(var1, var4, -1, -1, var3).c().t() != Material.a) {
               this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), var4, 0, -1, var3);
            }
         }

         for (int var6 = 0; var6 < 7; var6++) {
            for (int var5 = 0; var5 < 10; var5++) {
               this.b(var1, var5, 6, var6, var3);
               this.b(var1, Blocks.e.Q(), var5, -1, var6, var3);
            }
         }

         this.a(var1, var3, 7, 1, 1, 1);
         return true;
      }

      @Override
      protected int c(int var1, int var2) {
         return 3;
      }
   }

   public static class House3 extends StructureVillagePieces.Village {
      public House3() {
      }

      public House3(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
      }

      public static StructureVillagePieces.House3 a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 9, 7, 12, var6);
         return a(var8) && StructureComponent.a(var1, var8) == null ? new StructureVillagePieces.House3(var0, var7, var2, var8, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 7 - 1, 0);
         }

         this.a(var1, var3, 1, 1, 1, 7, 4, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 2, 1, 6, 8, 4, 10, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 2, 0, 5, 8, 0, 10, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 0, 1, 7, 0, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 0, 0, 0, 3, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 8, 0, 0, 8, 3, 10, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 0, 0, 7, 2, 0, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 0, 5, 2, 1, 5, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 2, 0, 6, 2, 3, 10, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 3, 0, 10, 7, 3, 10, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 2, 0, 7, 3, 0, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 2, 5, 2, 3, 5, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 4, 1, 8, 4, 1, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 4, 4, 3, 4, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 0, 5, 2, 8, 5, 3, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, Blocks.f.Q(), 0, 4, 2, var3);
         this.a(var1, Blocks.f.Q(), 0, 4, 3, var3);
         this.a(var1, Blocks.f.Q(), 8, 4, 2, var3);
         this.a(var1, Blocks.f.Q(), 8, 4, 3, var3);
         this.a(var1, Blocks.f.Q(), 8, 4, 4, var3);
         int var4 = this.a(Blocks.ad, 3);
         int var5 = this.a(Blocks.ad, 2);

         for (int var6 = -1; var6 <= 2; var6++) {
            for (int var7 = 0; var7 <= 8; var7++) {
               this.a(var1, Blocks.ad.a(var4), var7, 4 + var6, var6, var3);
               if ((var6 > -1 || var7 <= 1) && (var6 > 0 || var7 <= 3) && (var6 > 1 || var7 <= 4 || var7 >= 6)) {
                  this.a(var1, Blocks.ad.a(var5), var7, 4 + var6, 5 - var6, var3);
               }
            }
         }

         this.a(var1, var3, 3, 4, 5, 3, 4, 10, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 7, 4, 2, 7, 4, 10, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 4, 5, 4, 4, 5, 10, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 6, 5, 4, 6, 5, 10, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 5, 6, 3, 5, 6, 10, Blocks.f.Q(), Blocks.f.Q(), false);
         int var10 = this.a(Blocks.ad, 0);

         for (int var11 = 4; var11 >= 1; var11--) {
            this.a(var1, Blocks.f.Q(), var11, 2 + var11, 7 - var11, var3);

            for (int var8 = 8 - var11; var8 <= 10; var8++) {
               this.a(var1, Blocks.ad.a(var10), var11, 2 + var11, var8, var3);
            }
         }

         int var12 = this.a(Blocks.ad, 1);
         this.a(var1, Blocks.f.Q(), 6, 6, 3, var3);
         this.a(var1, Blocks.f.Q(), 7, 5, 4, var3);
         this.a(var1, Blocks.ad.a(var12), 6, 6, 4, var3);

         for (int var13 = 6; var13 <= 8; var13++) {
            for (int var9 = 5; var9 <= 10; var9++) {
               this.a(var1, Blocks.ad.a(var12), var13, 12 - var13, var9, var3);
            }
         }

         this.a(var1, Blocks.r.Q(), 0, 2, 1, var3);
         this.a(var1, Blocks.r.Q(), 0, 2, 4, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 0, 2, 3, var3);
         this.a(var1, Blocks.r.Q(), 4, 2, 0, var3);
         this.a(var1, Blocks.bj.Q(), 5, 2, 0, var3);
         this.a(var1, Blocks.r.Q(), 6, 2, 0, var3);
         this.a(var1, Blocks.r.Q(), 8, 2, 1, var3);
         this.a(var1, Blocks.bj.Q(), 8, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 8, 2, 3, var3);
         this.a(var1, Blocks.r.Q(), 8, 2, 4, var3);
         this.a(var1, Blocks.f.Q(), 8, 2, 5, var3);
         this.a(var1, Blocks.r.Q(), 8, 2, 6, var3);
         this.a(var1, Blocks.bj.Q(), 8, 2, 7, var3);
         this.a(var1, Blocks.bj.Q(), 8, 2, 8, var3);
         this.a(var1, Blocks.r.Q(), 8, 2, 9, var3);
         this.a(var1, Blocks.r.Q(), 2, 2, 6, var3);
         this.a(var1, Blocks.bj.Q(), 2, 2, 7, var3);
         this.a(var1, Blocks.bj.Q(), 2, 2, 8, var3);
         this.a(var1, Blocks.r.Q(), 2, 2, 9, var3);
         this.a(var1, Blocks.r.Q(), 4, 4, 10, var3);
         this.a(var1, Blocks.bj.Q(), 5, 4, 10, var3);
         this.a(var1, Blocks.r.Q(), 6, 4, 10, var3);
         this.a(var1, Blocks.f.Q(), 5, 5, 10, var3);
         this.a(var1, Blocks.a.Q(), 2, 1, 0, var3);
         this.a(var1, Blocks.a.Q(), 2, 2, 0, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m), 2, 3, 1, var3);
         this.a(var1, var3, var2, 2, 1, 0, EnumFacing.b(this.a(Blocks.ao, 1)));
         this.a(var1, var3, 1, 0, -1, 3, 2, -1, Blocks.a.Q(), Blocks.a.Q(), false);
         if (this.a(var1, 2, 0, -1, var3).c().t() == Material.a && this.a(var1, 2, -1, -1, var3).c().t() != Material.a) {
            this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 2, 0, -1, var3);
         }

         for (int var14 = 0; var14 < 5; var14++) {
            for (int var16 = 0; var16 < 9; var16++) {
               this.b(var1, var16, 7, var14, var3);
               this.b(var1, Blocks.e.Q(), var16, -1, var14, var3);
            }
         }

         for (int var15 = 5; var15 < 11; var15++) {
            for (int var17 = 2; var17 < 9; var17++) {
               this.b(var1, var17, 7, var15, var3);
               this.b(var1, Blocks.e.Q(), var17, -1, var15, var3);
            }
         }

         this.a(var1, var3, 4, 1, 2, 2);
         return true;
      }
   }

   public static class House4Garden extends StructureVillagePieces.Village {
      private boolean a;

      public House4Garden() {
      }

      public House4Garden(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
         this.a = var3.nextBoolean();
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Terrace", this.a);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = var1.n("Terrace");
      }

      public static StructureVillagePieces.House4Garden a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 5, 6, 5, var6);
         return StructureComponent.a(var1, var8) != null ? null : new StructureVillagePieces.House4Garden(var0, var7, var2, var8, var6);
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 6 - 1, 0);
         }

         this.a(var1, var3, 0, 0, 0, 4, 0, 4, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 0, 4, 0, 4, 4, 4, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 1, 4, 1, 3, 4, 3, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, Blocks.e.Q(), 0, 1, 0, var3);
         this.a(var1, Blocks.e.Q(), 0, 2, 0, var3);
         this.a(var1, Blocks.e.Q(), 0, 3, 0, var3);
         this.a(var1, Blocks.e.Q(), 4, 1, 0, var3);
         this.a(var1, Blocks.e.Q(), 4, 2, 0, var3);
         this.a(var1, Blocks.e.Q(), 4, 3, 0, var3);
         this.a(var1, Blocks.e.Q(), 0, 1, 4, var3);
         this.a(var1, Blocks.e.Q(), 0, 2, 4, var3);
         this.a(var1, Blocks.e.Q(), 0, 3, 4, var3);
         this.a(var1, Blocks.e.Q(), 4, 1, 4, var3);
         this.a(var1, Blocks.e.Q(), 4, 2, 4, var3);
         this.a(var1, Blocks.e.Q(), 4, 3, 4, var3);
         this.a(var1, var3, 0, 1, 1, 0, 3, 3, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 4, 1, 1, 4, 3, 3, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 1, 4, 3, 3, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, Blocks.bj.Q(), 0, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 2, 2, 4, var3);
         this.a(var1, Blocks.bj.Q(), 4, 2, 2, var3);
         this.a(var1, Blocks.f.Q(), 1, 1, 0, var3);
         this.a(var1, Blocks.f.Q(), 1, 2, 0, var3);
         this.a(var1, Blocks.f.Q(), 1, 3, 0, var3);
         this.a(var1, Blocks.f.Q(), 2, 3, 0, var3);
         this.a(var1, Blocks.f.Q(), 3, 3, 0, var3);
         this.a(var1, Blocks.f.Q(), 3, 2, 0, var3);
         this.a(var1, Blocks.f.Q(), 3, 1, 0, var3);
         if (this.a(var1, 2, 0, -1, var3).c().t() == Material.a && this.a(var1, 2, -1, -1, var3).c().t() != Material.a) {
            this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 2, 0, -1, var3);
         }

         this.a(var1, var3, 1, 1, 1, 3, 3, 3, Blocks.a.Q(), Blocks.a.Q(), false);
         if (this.a) {
            this.a(var1, Blocks.aO.Q(), 0, 5, 0, var3);
            this.a(var1, Blocks.aO.Q(), 1, 5, 0, var3);
            this.a(var1, Blocks.aO.Q(), 2, 5, 0, var3);
            this.a(var1, Blocks.aO.Q(), 3, 5, 0, var3);
            this.a(var1, Blocks.aO.Q(), 4, 5, 0, var3);
            this.a(var1, Blocks.aO.Q(), 0, 5, 4, var3);
            this.a(var1, Blocks.aO.Q(), 1, 5, 4, var3);
            this.a(var1, Blocks.aO.Q(), 2, 5, 4, var3);
            this.a(var1, Blocks.aO.Q(), 3, 5, 4, var3);
            this.a(var1, Blocks.aO.Q(), 4, 5, 4, var3);
            this.a(var1, Blocks.aO.Q(), 4, 5, 1, var3);
            this.a(var1, Blocks.aO.Q(), 4, 5, 2, var3);
            this.a(var1, Blocks.aO.Q(), 4, 5, 3, var3);
            this.a(var1, Blocks.aO.Q(), 0, 5, 1, var3);
            this.a(var1, Blocks.aO.Q(), 0, 5, 2, var3);
            this.a(var1, Blocks.aO.Q(), 0, 5, 3, var3);
         }

         if (this.a) {
            int var4 = this.a(Blocks.au, 3);
            this.a(var1, Blocks.au.a(var4), 3, 1, 3, var3);
            this.a(var1, Blocks.au.a(var4), 3, 2, 3, var3);
            this.a(var1, Blocks.au.a(var4), 3, 3, 3, var3);
            this.a(var1, Blocks.au.a(var4), 3, 4, 3, var3);
         }

         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m), 2, 3, 1, var3);

         for (int var6 = 0; var6 < 5; var6++) {
            for (int var5 = 0; var5 < 5; var5++) {
               this.b(var1, var5, 6, var6, var3);
               this.b(var1, Blocks.e.Q(), var5, -1, var6, var3);
            }
         }

         this.a(var1, var3, 1, 1, 2, 1);
         return true;
      }
   }

   public static class Path extends StructureVillagePieces.Road {
      private int a;

      public Path() {
      }

      public Path(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
         this.a = Math.max(var4.c(), var4.e());
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Length", this.a);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = var1.f("Length");
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         boolean var4 = false;

         for (int var5 = var3.nextInt(5); var5 < this.a - 8; var5 += 2 + var3.nextInt(5)) {
            StructureComponent var6 = this.a((StructureVillagePieces.Start)var1, var2, var3, 0, var5);
            if (var6 != null) {
               var5 += Math.max(var6.l.c(), var6.l.e());
               var4 = true;
            }
         }

         for (int var7 = var3.nextInt(5); var7 < this.a - 8; var7 += 2 + var3.nextInt(5)) {
            StructureComponent var8 = this.b((StructureVillagePieces.Start)var1, var2, var3, 0, var7);
            if (var8 != null) {
               var7 += Math.max(var8.l.c(), var8.l.e());
               var4 = true;
            }
         }

         if (var4 && var3.nextInt(3) > 0 && this.m != null) {
            switch (this.m) {
               case c:
                  StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.a - 1, this.l.b, this.l.c, EnumFacing.e, this.d());
                  break;
               case d:
                  StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.a - 1, this.l.b, this.l.f - 2, EnumFacing.e, this.d());
                  break;
               case e:
                  StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.a, this.l.b, this.l.c - 1, EnumFacing.c, this.d());
                  break;
               case f:
                  StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.d - 2, this.l.b, this.l.c - 1, EnumFacing.c, this.d());
            }
         }

         if (var4 && var3.nextInt(3) > 0 && this.m != null) {
            switch (this.m) {
               case c:
                  StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.d + 1, this.l.b, this.l.c, EnumFacing.f, this.d());
                  break;
               case d:
                  StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.d + 1, this.l.b, this.l.f - 2, EnumFacing.f, this.d());
                  break;
               case e:
                  StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.a, this.l.b, this.l.f + 1, EnumFacing.d, this.d());
                  break;
               case f:
                  StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.d - 2, this.l.b, this.l.f + 1, EnumFacing.d, this.d());
            }
         }
      }

      public static StructureBoundingBox a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6
      ) {
         for (int var7 = 7 * MathHelper.a(var2, 3, 5); var7 >= 7; var7 -= 7) {
            StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 3, 3, var7, var6);
            if (StructureComponent.a(var1, var8) == null) {
               return var8;
            }
         }

         return null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         IBlockState var4 = this.a(Blocks.n.Q());
         IBlockState var5 = this.a(Blocks.e.Q());

         for (int var6 = this.l.a; var6 <= this.l.d; var6++) {
            for (int var7 = this.l.c; var7 <= this.l.f; var7++) {
               BlockPos var8 = new BlockPos(var6, 64, var7);
               if (var3.b(var8)) {
                  var8 = var1.r(var8).b();
                  var1.a(var8, var4, 2);
                  var1.a(var8.b(), var5, 2);
               }
            }
         }

         return true;
      }
   }

   public static class PieceWeight {
      public Class<? extends StructureVillagePieces.Village> a;
      public final int b;
      public int c;
      public int d;

      public PieceWeight(Class<? extends StructureVillagePieces.Village> var1, int var2, int var3) {
         this.a = var1;
         this.b = var2;
         this.d = var3;
      }

      public boolean a(int var1) {
         return this.d == 0 || this.c < this.d;
      }

      public boolean a() {
         return this.d == 0 || this.c < this.d;
      }
   }

   public abstract static class Road extends StructureVillagePieces.Village {
      public Road() {
      }

      protected Road(StructureVillagePieces.Start var1, int var2) {
         super(var1, var2);
      }
   }

   public static class Start extends StructureVillagePieces.Well {
      public WorldChunkManager a;
      public boolean b;
      public int c;
      public StructureVillagePieces.PieceWeight d;
      public List<StructureVillagePieces.PieceWeight> e;
      public List<StructureComponent> f = Lists.newArrayList();
      public List<StructureComponent> g = Lists.newArrayList();

      public Start() {
      }

      public Start(WorldChunkManager var1, int var2, Random var3, int var4, int var5, List<StructureVillagePieces.PieceWeight> var6, int var7) {
         super(null, 0, var3, var4, var5);
         this.a = var1;
         this.e = var6;
         this.c = var7;
         BiomeGenBase var8 = var1.a(new BlockPos(var4, 0, var5), BiomeGenBase.ad);
         this.b = var8 == BiomeGenBase.r || var8 == BiomeGenBase.G;
         this.a(this.b);
      }

      public WorldChunkManager e() {
         return this.a;
      }
   }

   public static class Torch extends StructureVillagePieces.Village {
      public Torch() {
      }

      public Torch(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
      }

      public static StructureBoundingBox a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6
      ) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 3, 4, 2, var6);
         return StructureComponent.a(var1, var7) != null ? null : var7;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 4 - 1, 0);
         }

         this.a(var1, var3, 0, 0, 0, 2, 3, 1, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, Blocks.aO.Q(), 1, 0, 0, var3);
         this.a(var1, Blocks.aO.Q(), 1, 1, 0, var3);
         this.a(var1, Blocks.aO.Q(), 1, 2, 0, var3);
         this.a(var1, Blocks.L.a(EnumDyeColor.a.b()), 1, 3, 0, var3);
         boolean var4 = this.m == EnumFacing.f || this.m == EnumFacing.c;
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m.e()), var4 ? 2 : 0, 3, 0, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m), 1, 3, 1, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m.f()), var4 ? 0 : 2, 3, 0, var3);
         this.a(var1, Blocks.aa.Q().a(BlockTorch.a, this.m.d()), 1, 3, -1, var3);
         return true;
      }
   }

   abstract static class Village extends StructureComponent {
      protected int h = -1;
      private int a;
      private boolean b;

      public Village() {
      }

      protected Village(StructureVillagePieces.Start var1, int var2) {
         super(var2);
         if (var1 != null) {
            this.b = var1.b;
         }
      }

      @Override
      protected void a(NBTTagCompound var1) {
         var1.a("HPos", this.h);
         var1.a("VCount", this.a);
         var1.a("Desert", this.b);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         this.h = var1.f("HPos");
         this.a = var1.f("VCount");
         this.b = var1.n("Desert");
      }

      protected StructureComponent a(StructureVillagePieces.Start var1, List<StructureComponent> var2, Random var3, int var4, int var5) {
         if (this.m != null) {
            switch (this.m) {
               case c:
                  return StructureVillagePieces.d(var1, var2, var3, this.l.a - 1, this.l.b + var4, this.l.c + var5, EnumFacing.e, this.d());
               case d:
                  return StructureVillagePieces.d(var1, var2, var3, this.l.a - 1, this.l.b + var4, this.l.c + var5, EnumFacing.e, this.d());
               case e:
                  return StructureVillagePieces.d(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.c - 1, EnumFacing.c, this.d());
               case f:
                  return StructureVillagePieces.d(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.c - 1, EnumFacing.c, this.d());
            }
         }

         return null;
      }

      protected StructureComponent b(StructureVillagePieces.Start var1, List<StructureComponent> var2, Random var3, int var4, int var5) {
         if (this.m != null) {
            switch (this.m) {
               case c:
                  return StructureVillagePieces.d(var1, var2, var3, this.l.d + 1, this.l.b + var4, this.l.c + var5, EnumFacing.f, this.d());
               case d:
                  return StructureVillagePieces.d(var1, var2, var3, this.l.d + 1, this.l.b + var4, this.l.c + var5, EnumFacing.f, this.d());
               case e:
                  return StructureVillagePieces.d(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.f + 1, EnumFacing.d, this.d());
               case f:
                  return StructureVillagePieces.d(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.f + 1, EnumFacing.d, this.d());
            }
         }

         return null;
      }

      protected int b(World var1, StructureBoundingBox var2) {
         int var3 = 0;
         int var4 = 0;
         BlockPos.MutableBlockPos var5 = new BlockPos.MutableBlockPos();

         for (int var6 = this.l.c; var6 <= this.l.f; var6++) {
            for (int var7 = this.l.a; var7 <= this.l.d; var7++) {
               var5.c(var7, 64, var6);
               if (var2.b(var5)) {
                  var3 += Math.max(var1.r(var5).o(), var1.t.i());
                  var4++;
               }
            }
         }

         return var4 == 0 ? -1 : var3 / var4;
      }

      protected static boolean a(StructureBoundingBox var0) {
         return var0 != null && var0.b > 10;
      }

      protected void a(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6) {
         if (this.a < var6) {
            for (int var7 = this.a; var7 < var6; var7++) {
               int var8 = this.a(var3 + var7, var5);
               int var9 = this.d(var4);
               int var10 = this.b(var3 + var7, var5);
               if (!var2.b(new BlockPos(var8, var9, var10))) {
                  break;
               }

               this.a++;
               EntityVillager var11 = new EntityVillager(var1);
               var11.b(var8 + 0.5, var9, var10 + 0.5, 0.0F, 0.0F);
               var11.a(var1.E(new BlockPos(var11)), null);
               var11.r(this.c(var7, var11.cl()));
               var1.d(var11);
            }
         }
      }

      protected int c(int var1, int var2) {
         return var2;
      }

      protected IBlockState a(IBlockState var1) {
         if (this.b) {
            if (var1.c() == Blocks.r || var1.c() == Blocks.s) {
               return Blocks.A.Q();
            }

            if (var1.c() == Blocks.e) {
               return Blocks.A.a(BlockSandStone.EnumType.a.a());
            }

            if (var1.c() == Blocks.f) {
               return Blocks.A.a(BlockSandStone.EnumType.c.a());
            }

            if (var1.c() == Blocks.ad) {
               return Blocks.bO.Q().a(BlockStairs.a, var1.b(BlockStairs.a));
            }

            if (var1.c() == Blocks.aw) {
               return Blocks.bO.Q().a(BlockStairs.a, var1.b(BlockStairs.a));
            }

            if (var1.c() == Blocks.n) {
               return Blocks.A.Q();
            }
         }

         return var1;
      }

      @Override
      protected void a(World var1, IBlockState var2, int var3, int var4, int var5, StructureBoundingBox var6) {
         IBlockState var7 = this.a(var2);
         super.a(var1, var7, var3, var4, var5, var6);
      }

      @Override
      protected void a(
         World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9, IBlockState var10, boolean var11
      ) {
         IBlockState var12 = this.a(var9);
         IBlockState var13 = this.a(var10);
         super.a(var1, var2, var3, var4, var5, var6, var7, var8, var12, var13, var11);
      }

      @Override
      protected void b(World var1, IBlockState var2, int var3, int var4, int var5, StructureBoundingBox var6) {
         IBlockState var7 = this.a(var2);
         super.b(var1, var7, var3, var4, var5, var6);
      }

      protected void a(boolean var1) {
         this.b = var1;
      }
   }

   public static class Well extends StructureVillagePieces.Village {
      public Well() {
      }

      public Well(StructureVillagePieces.Start var1, int var2, Random var3, int var4, int var5) {
         super(var1, var2);
         this.m = EnumFacing.Plane.a.a(var3);
         switch (this.m) {
            case c:
            case d:
               this.l = new StructureBoundingBox(var4, 64, var5, var4 + 6 - 1, 78, var5 + 6 - 1);
               break;
            default:
               this.l = new StructureBoundingBox(var4, 64, var5, var4 + 6 - 1, 78, var5 + 6 - 1);
         }
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.a - 1, this.l.e - 4, this.l.c + 1, EnumFacing.e, this.d());
         StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.d + 1, this.l.e - 4, this.l.c + 1, EnumFacing.f, this.d());
         StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.a + 1, this.l.e - 4, this.l.c - 1, EnumFacing.c, this.d());
         StructureVillagePieces.e((StructureVillagePieces.Start)var1, var2, var3, this.l.a + 1, this.l.e - 4, this.l.f + 1, EnumFacing.d, this.d());
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 3, 0);
         }

         this.a(var1, var3, 1, 0, 1, 4, 12, 4, Blocks.e.Q(), Blocks.i.Q(), false);
         this.a(var1, Blocks.a.Q(), 2, 12, 2, var3);
         this.a(var1, Blocks.a.Q(), 3, 12, 2, var3);
         this.a(var1, Blocks.a.Q(), 2, 12, 3, var3);
         this.a(var1, Blocks.a.Q(), 3, 12, 3, var3);
         this.a(var1, Blocks.aO.Q(), 1, 13, 1, var3);
         this.a(var1, Blocks.aO.Q(), 1, 14, 1, var3);
         this.a(var1, Blocks.aO.Q(), 4, 13, 1, var3);
         this.a(var1, Blocks.aO.Q(), 4, 14, 1, var3);
         this.a(var1, Blocks.aO.Q(), 1, 13, 4, var3);
         this.a(var1, Blocks.aO.Q(), 1, 14, 4, var3);
         this.a(var1, Blocks.aO.Q(), 4, 13, 4, var3);
         this.a(var1, Blocks.aO.Q(), 4, 14, 4, var3);
         this.a(var1, var3, 1, 15, 1, 4, 15, 4, Blocks.e.Q(), Blocks.e.Q(), false);

         for (int var4 = 0; var4 <= 5; var4++) {
            for (int var5 = 0; var5 <= 5; var5++) {
               if (var5 == 0 || var5 == 5 || var4 == 0 || var4 == 5) {
                  this.a(var1, Blocks.n.Q(), var5, 11, var4, var3);
                  this.b(var1, var5, 12, var4, var3);
               }
            }
         }

         return true;
      }
   }

   public static class WoodHut extends StructureVillagePieces.Village {
      private boolean a;
      private int b;

      public WoodHut() {
      }

      public WoodHut(StructureVillagePieces.Start var1, int var2, Random var3, StructureBoundingBox var4, EnumFacing var5) {
         super(var1, var2);
         this.m = var5;
         this.l = var4;
         this.a = var3.nextBoolean();
         this.b = var3.nextInt(3);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("T", this.b);
         var1.a("C", this.a);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.b = var1.f("T");
         this.a = var1.n("C");
      }

      public static StructureVillagePieces.WoodHut a(
         StructureVillagePieces.Start var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
      ) {
         StructureBoundingBox var8 = StructureBoundingBox.a(var3, var4, var5, 0, 0, 0, 4, 6, 5, var6);
         return a(var8) && StructureComponent.a(var1, var8) == null ? new StructureVillagePieces.WoodHut(var0, var7, var2, var8, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.h < 0) {
            this.h = this.b(var1, var3);
            if (this.h < 0) {
               return true;
            }

            this.l.a(0, this.h - this.l.e + 6 - 1, 0);
         }

         this.a(var1, var3, 1, 1, 1, 3, 5, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 0, 0, 3, 0, 4, Blocks.e.Q(), Blocks.e.Q(), false);
         this.a(var1, var3, 1, 0, 1, 2, 0, 3, Blocks.d.Q(), Blocks.d.Q(), false);
         if (this.a) {
            this.a(var1, var3, 1, 4, 1, 2, 4, 3, Blocks.r.Q(), Blocks.r.Q(), false);
         } else {
            this.a(var1, var3, 1, 5, 1, 2, 5, 3, Blocks.r.Q(), Blocks.r.Q(), false);
         }

         this.a(var1, Blocks.r.Q(), 1, 4, 0, var3);
         this.a(var1, Blocks.r.Q(), 2, 4, 0, var3);
         this.a(var1, Blocks.r.Q(), 1, 4, 4, var3);
         this.a(var1, Blocks.r.Q(), 2, 4, 4, var3);
         this.a(var1, Blocks.r.Q(), 0, 4, 1, var3);
         this.a(var1, Blocks.r.Q(), 0, 4, 2, var3);
         this.a(var1, Blocks.r.Q(), 0, 4, 3, var3);
         this.a(var1, Blocks.r.Q(), 3, 4, 1, var3);
         this.a(var1, Blocks.r.Q(), 3, 4, 2, var3);
         this.a(var1, Blocks.r.Q(), 3, 4, 3, var3);
         this.a(var1, var3, 0, 1, 0, 0, 3, 0, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 3, 1, 0, 3, 3, 0, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 0, 1, 4, 0, 3, 4, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 3, 1, 4, 3, 3, 4, Blocks.r.Q(), Blocks.r.Q(), false);
         this.a(var1, var3, 0, 1, 1, 0, 3, 3, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 3, 1, 1, 3, 3, 3, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 1, 0, 2, 3, 0, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, var3, 1, 1, 4, 2, 3, 4, Blocks.f.Q(), Blocks.f.Q(), false);
         this.a(var1, Blocks.bj.Q(), 0, 2, 2, var3);
         this.a(var1, Blocks.bj.Q(), 3, 2, 2, var3);
         if (this.b > 0) {
            this.a(var1, Blocks.aO.Q(), this.b, 1, 3, var3);
            this.a(var1, Blocks.aB.Q(), this.b, 2, 3, var3);
         }

         this.a(var1, Blocks.a.Q(), 1, 1, 0, var3);
         this.a(var1, Blocks.a.Q(), 1, 2, 0, var3);
         this.a(var1, var3, var2, 1, 1, 0, EnumFacing.b(this.a(Blocks.ao, 1)));
         if (this.a(var1, 1, 0, -1, var3).c().t() == Material.a && this.a(var1, 1, -1, -1, var3).c().t() != Material.a) {
            this.a(var1, Blocks.aw.a(this.a(Blocks.aw, 3)), 1, 0, -1, var3);
         }

         for (int var4 = 0; var4 < 5; var4++) {
            for (int var5 = 0; var5 < 4; var5++) {
               this.b(var1, var5, 6, var4, var3);
               this.b(var1, Blocks.e.Q(), var5, -1, var4, var3);
            }
         }

         this.a(var1, var3, 1, 1, 2, 1);
         return true;
      }
   }
}
