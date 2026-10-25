package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureNetherBridgePieces {
   private static final StructureNetherBridgePieces.PieceWeight[] a = new StructureNetherBridgePieces.PieceWeight[]{
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Straight.class, 30, 0, true),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing3.class, 10, 4),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing.class, 10, 4),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Stairs.class, 10, 3),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Throne.class, 5, 2),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Entrance.class, 5, 1)
   };
   private static final StructureNetherBridgePieces.PieceWeight[] b = new StructureNetherBridgePieces.PieceWeight[]{
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor5.class, 25, 0, true),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Crossing2.class, 15, 5),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor2.class, 5, 10),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor.class, 5, 10),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor3.class, 10, 3, true),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.Corridor4.class, 7, 2),
      new StructureNetherBridgePieces.PieceWeight(StructureNetherBridgePieces.NetherStalkRoom.class, 5, 2)
   };

   public static void a() {
      MapGenStructureIO.a(StructureNetherBridgePieces.Crossing3.class, "NeBCr");
      MapGenStructureIO.a(StructureNetherBridgePieces.End.class, "NeBEF");
      MapGenStructureIO.a(StructureNetherBridgePieces.Straight.class, "NeBS");
      MapGenStructureIO.a(StructureNetherBridgePieces.Corridor3.class, "NeCCS");
      MapGenStructureIO.a(StructureNetherBridgePieces.Corridor4.class, "NeCTB");
      MapGenStructureIO.a(StructureNetherBridgePieces.Entrance.class, "NeCE");
      MapGenStructureIO.a(StructureNetherBridgePieces.Crossing2.class, "NeSCSC");
      MapGenStructureIO.a(StructureNetherBridgePieces.Corridor.class, "NeSCLT");
      MapGenStructureIO.a(StructureNetherBridgePieces.Corridor5.class, "NeSC");
      MapGenStructureIO.a(StructureNetherBridgePieces.Corridor2.class, "NeSCRT");
      MapGenStructureIO.a(StructureNetherBridgePieces.NetherStalkRoom.class, "NeCSR");
      MapGenStructureIO.a(StructureNetherBridgePieces.Throne.class, "NeMT");
      MapGenStructureIO.a(StructureNetherBridgePieces.Crossing.class, "NeRC");
      MapGenStructureIO.a(StructureNetherBridgePieces.Stairs.class, "NeSR");
      MapGenStructureIO.a(StructureNetherBridgePieces.Start.class, "NeStart");
   }

   private static StructureNetherBridgePieces.Piece b(
      StructureNetherBridgePieces.PieceWeight var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      Class var8 = var0.a;
      Object var9 = null;
      if (var8 == StructureNetherBridgePieces.Straight.class) {
         var9 = StructureNetherBridgePieces.Straight.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Crossing3.class) {
         var9 = StructureNetherBridgePieces.Crossing3.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Crossing.class) {
         var9 = StructureNetherBridgePieces.Crossing.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Stairs.class) {
         var9 = StructureNetherBridgePieces.Stairs.a(var1, var2, var3, var4, var5, var7, var6);
      } else if (var8 == StructureNetherBridgePieces.Throne.class) {
         var9 = StructureNetherBridgePieces.Throne.a(var1, var2, var3, var4, var5, var7, var6);
      } else if (var8 == StructureNetherBridgePieces.Entrance.class) {
         var9 = StructureNetherBridgePieces.Entrance.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Corridor5.class) {
         var9 = StructureNetherBridgePieces.Corridor5.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Corridor2.class) {
         var9 = StructureNetherBridgePieces.Corridor2.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Corridor.class) {
         var9 = StructureNetherBridgePieces.Corridor.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Corridor3.class) {
         var9 = StructureNetherBridgePieces.Corridor3.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Corridor4.class) {
         var9 = StructureNetherBridgePieces.Corridor4.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.Crossing2.class) {
         var9 = StructureNetherBridgePieces.Crossing2.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var8 == StructureNetherBridgePieces.NetherStalkRoom.class) {
         var9 = StructureNetherBridgePieces.NetherStalkRoom.a(var1, var2, var3, var4, var5, var6, var7);
      }

      return (StructureNetherBridgePieces.Piece)var9;
   }

   public static class Corridor extends StructureNetherBridgePieces.Piece {
      private boolean b;

      public Corridor() {
      }

      public Corridor(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
         this.b = var2.nextInt(3) == 0;
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.b = var1.n("Chest");
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Chest", this.b);
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.b((StructureNetherBridgePieces.Start)var1, var2, var3, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Corridor a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, 0, 0, 5, 7, 5, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Corridor(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 0, 0, 4, 1, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 4, 5, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 4, 2, 0, 4, 5, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 3, 1, 4, 4, 1, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 4, 3, 3, 4, 4, 3, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 2, 0, 0, 5, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 4, 3, 5, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 3, 4, 1, 4, 4, Blocks.bz.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 3, 3, 4, 3, 4, 4, Blocks.bz.Q(), Blocks.by.Q(), false);
         if (this.b && var3.b(new BlockPos(this.a(3, 3), this.d(2), this.b(3, 3)))) {
            this.b = false;
            this.a(var1, var3, var2, 3, 2, 3, a, 2 + var2.nextInt(4));
         }

         this.a(var1, var3, 0, 6, 0, 4, 6, 4, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var4 = 0; var4 <= 4; var4++) {
            for (int var5 = 0; var5 <= 4; var5++) {
               this.b(var1, Blocks.by.Q(), var4, -1, var5, var3);
            }
         }

         return true;
      }
   }

   public static class Corridor2 extends StructureNetherBridgePieces.Piece {
      private boolean b;

      public Corridor2() {
      }

      public Corridor2(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
         this.b = var2.nextInt(3) == 0;
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.b = var1.n("Chest");
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Chest", this.b);
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.c((StructureNetherBridgePieces.Start)var1, var2, var3, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Corridor2 a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, 0, 0, 5, 7, 5, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Corridor2(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 0, 0, 4, 1, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 4, 5, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 2, 0, 0, 5, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 3, 1, 0, 4, 1, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 3, 3, 0, 4, 3, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 4, 2, 0, 4, 5, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 2, 4, 4, 5, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 3, 4, 1, 4, 4, Blocks.bz.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 3, 3, 4, 3, 4, 4, Blocks.bz.Q(), Blocks.by.Q(), false);
         if (this.b && var3.b(new BlockPos(this.a(1, 3), this.d(2), this.b(1, 3)))) {
            this.b = false;
            this.a(var1, var3, var2, 1, 2, 3, a, 2 + var2.nextInt(4));
         }

         this.a(var1, var3, 0, 6, 0, 4, 6, 4, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var4 = 0; var4 <= 4; var4++) {
            for (int var5 = 0; var5 <= 4; var5++) {
               this.b(var1, Blocks.by.Q(), var4, -1, var5, var3);
            }
         }

         return true;
      }
   }

   public static class Corridor3 extends StructureNetherBridgePieces.Piece {
      public Corridor3() {
      }

      public Corridor3(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 1, 0, true);
      }

      public static StructureNetherBridgePieces.Corridor3 a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -7, 0, 5, 14, 10, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Corridor3(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         int var4 = this.a(Blocks.bA, 2);

         for (int var5 = 0; var5 <= 9; var5++) {
            int var6 = Math.max(1, 7 - var5);
            int var7 = Math.min(Math.max(var6 + 5, 14 - var5), 13);
            int var8 = var5;
            this.a(var1, var3, 0, 0, var5, 4, var6, var5, Blocks.by.Q(), Blocks.by.Q(), false);
            this.a(var1, var3, 1, var6 + 1, var5, 3, var7 - 1, var5, Blocks.a.Q(), Blocks.a.Q(), false);
            if (var5 <= 6) {
               this.a(var1, Blocks.bA.a(var4), 1, var6 + 1, var5, var3);
               this.a(var1, Blocks.bA.a(var4), 2, var6 + 1, var5, var3);
               this.a(var1, Blocks.bA.a(var4), 3, var6 + 1, var5, var3);
            }

            this.a(var1, var3, 0, var7, var5, 4, var7, var5, Blocks.by.Q(), Blocks.by.Q(), false);
            this.a(var1, var3, 0, var6 + 1, var5, 0, var7 - 1, var5, Blocks.by.Q(), Blocks.by.Q(), false);
            this.a(var1, var3, 4, var6 + 1, var5, 4, var7 - 1, var5, Blocks.by.Q(), Blocks.by.Q(), false);
            if ((var5 & 1) == 0) {
               this.a(var1, var3, 0, var6 + 2, var5, 0, var6 + 3, var5, Blocks.bz.Q(), Blocks.bz.Q(), false);
               this.a(var1, var3, 4, var6 + 2, var5, 4, var6 + 3, var5, Blocks.bz.Q(), Blocks.bz.Q(), false);
            }

            for (int var9 = 0; var9 <= 4; var9++) {
               this.b(var1, Blocks.by.Q(), var9, -1, var8, var3);
            }
         }

         return true;
      }
   }

   public static class Corridor4 extends StructureNetherBridgePieces.Piece {
      public Corridor4() {
      }

      public Corridor4(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         byte var4 = 1;
         if (this.m == EnumFacing.e || this.m == EnumFacing.c) {
            var4 = 5;
         }

         this.b((StructureNetherBridgePieces.Start)var1, var2, var3, 0, var4, var3.nextInt(8) > 0);
         this.c((StructureNetherBridgePieces.Start)var1, var2, var3, 0, var4, var3.nextInt(8) > 0);
      }

      public static StructureNetherBridgePieces.Corridor4 a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -3, 0, 0, 9, 7, 9, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Corridor4(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 0, 0, 8, 1, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 8, 5, 8, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 6, 0, 8, 6, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 2, 5, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 2, 0, 8, 5, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 3, 0, 1, 4, 0, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 7, 3, 0, 7, 4, 0, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 2, 4, 8, 2, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 1, 4, 2, 2, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 6, 1, 4, 7, 2, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 3, 8, 8, 3, 8, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 3, 6, 0, 3, 7, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 8, 3, 6, 8, 3, 7, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 3, 4, 0, 5, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 8, 3, 4, 8, 5, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 3, 5, 2, 5, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 3, 5, 7, 5, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 4, 5, 1, 5, 5, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 7, 4, 5, 7, 5, 5, Blocks.bz.Q(), Blocks.bz.Q(), false);

         for (int var4 = 0; var4 <= 5; var4++) {
            for (int var5 = 0; var5 <= 8; var5++) {
               this.b(var1, Blocks.by.Q(), var5, -1, var4, var3);
            }
         }

         return true;
      }
   }

   public static class Corridor5 extends StructureNetherBridgePieces.Piece {
      public Corridor5() {
      }

      public Corridor5(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 1, 0, true);
      }

      public static StructureNetherBridgePieces.Corridor5 a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, 0, 0, 5, 7, 5, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Corridor5(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 0, 0, 4, 1, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 4, 5, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 2, 0, 0, 5, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 2, 0, 4, 5, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 3, 1, 0, 4, 1, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 3, 3, 0, 4, 3, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 4, 3, 1, 4, 4, 1, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 4, 3, 3, 4, 4, 3, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 6, 0, 4, 6, 4, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var4 = 0; var4 <= 4; var4++) {
            for (int var5 = 0; var5 <= 4; var5++) {
               this.b(var1, Blocks.by.Q(), var4, -1, var5, var3);
            }
         }

         return true;
      }
   }

   public static class Crossing extends StructureNetherBridgePieces.Piece {
      public Crossing() {
      }

      public Crossing(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 2, 0, false);
         this.b((StructureNetherBridgePieces.Start)var1, var2, var3, 0, 2, false);
         this.c((StructureNetherBridgePieces.Start)var1, var2, var3, 0, 2, false);
      }

      public static StructureNetherBridgePieces.Crossing a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -2, 0, 0, 7, 9, 7, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Crossing(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 0, 0, 6, 1, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 6, 7, 6, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 2, 0, 1, 6, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 6, 1, 6, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 2, 0, 6, 6, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 2, 6, 6, 6, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 0, 6, 1, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 5, 0, 6, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 2, 0, 6, 6, 1, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 2, 5, 6, 6, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 6, 0, 4, 6, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 0, 4, 5, 0, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 2, 6, 6, 4, 6, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 6, 4, 5, 6, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 6, 2, 0, 6, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 5, 2, 0, 5, 4, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 6, 6, 2, 6, 6, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 5, 2, 6, 5, 4, Blocks.bz.Q(), Blocks.bz.Q(), false);

         for (int var4 = 0; var4 <= 6; var4++) {
            for (int var5 = 0; var5 <= 6; var5++) {
               this.b(var1, Blocks.by.Q(), var4, -1, var5, var3);
            }
         }

         return true;
      }
   }

   public static class Crossing2 extends StructureNetherBridgePieces.Piece {
      public Crossing2() {
      }

      public Crossing2(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 1, 0, true);
         this.b((StructureNetherBridgePieces.Start)var1, var2, var3, 0, 1, true);
         this.c((StructureNetherBridgePieces.Start)var1, var2, var3, 0, 1, true);
      }

      public static StructureNetherBridgePieces.Crossing2 a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, 0, 0, 5, 7, 5, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Crossing2(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 0, 0, 4, 1, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 4, 5, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 2, 0, 0, 5, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 2, 0, 4, 5, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 4, 0, 5, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 2, 4, 4, 5, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 6, 0, 4, 6, 4, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var4 = 0; var4 <= 4; var4++) {
            for (int var5 = 0; var5 <= 4; var5++) {
               this.b(var1, Blocks.by.Q(), var4, -1, var5, var3);
            }
         }

         return true;
      }
   }

   public static class Crossing3 extends StructureNetherBridgePieces.Piece {
      public Crossing3() {
      }

      public Crossing3(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      protected Crossing3(Random var1, int var2, int var3) {
         super(0);
         this.m = EnumFacing.Plane.a.a(var1);
         switch (this.m) {
            case c:
            case d:
               this.l = new StructureBoundingBox(var2, 64, var3, var2 + 19 - 1, 73, var3 + 19 - 1);
               break;
            default:
               this.l = new StructureBoundingBox(var2, 64, var3, var2 + 19 - 1, 73, var3 + 19 - 1);
         }
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 8, 3, false);
         this.b((StructureNetherBridgePieces.Start)var1, var2, var3, 3, 8, false);
         this.c((StructureNetherBridgePieces.Start)var1, var2, var3, 3, 8, false);
      }

      public static StructureNetherBridgePieces.Crossing3 a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -8, -3, 0, 19, 10, 19, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Crossing3(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 7, 3, 0, 11, 4, 18, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 3, 7, 18, 4, 11, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 8, 5, 0, 10, 7, 18, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 5, 8, 18, 7, 10, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 7, 5, 0, 7, 5, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 7, 5, 11, 7, 5, 18, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 11, 5, 0, 11, 5, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 11, 5, 11, 11, 5, 18, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 5, 7, 7, 5, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 11, 5, 7, 18, 5, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 5, 11, 7, 5, 11, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 11, 5, 11, 18, 5, 11, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 7, 2, 0, 11, 2, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 7, 2, 13, 11, 2, 18, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 7, 0, 0, 11, 1, 3, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 7, 0, 15, 11, 1, 18, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var4 = 7; var4 <= 11; var4++) {
            for (int var5 = 0; var5 <= 2; var5++) {
               this.b(var1, Blocks.by.Q(), var4, -1, var5, var3);
               this.b(var1, Blocks.by.Q(), var4, -1, 18 - var5, var3);
            }
         }

         this.a(var1, var3, 0, 2, 7, 5, 2, 11, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 13, 2, 7, 18, 2, 11, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 0, 7, 3, 1, 11, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 15, 0, 7, 18, 1, 11, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var6 = 0; var6 <= 2; var6++) {
            for (int var7 = 7; var7 <= 11; var7++) {
               this.b(var1, Blocks.by.Q(), var6, -1, var7, var3);
               this.b(var1, Blocks.by.Q(), 18 - var6, -1, var7, var3);
            }
         }

         return true;
      }
   }

   public static class End extends StructureNetherBridgePieces.Piece {
      private int b;

      public End() {
      }

      public End(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
         this.b = var2.nextInt();
      }

      public static StructureNetherBridgePieces.End a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -3, 0, 5, 10, 8, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.End(var6, var1, var7, var5) : null;
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.b = var1.f("Seed");
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Seed", this.b);
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         Random var4 = new Random(this.b);

         for (int var5 = 0; var5 <= 4; var5++) {
            for (int var6 = 3; var6 <= 4; var6++) {
               int var7 = var4.nextInt(8);
               this.a(var1, var3, var5, var6, 0, var5, var6, var7, Blocks.by.Q(), Blocks.by.Q(), false);
            }
         }

         int var8 = var4.nextInt(8);
         this.a(var1, var3, 0, 5, 0, 0, 5, var8, Blocks.by.Q(), Blocks.by.Q(), false);
         var8 = var4.nextInt(8);
         this.a(var1, var3, 4, 5, 0, 4, 5, var8, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var10 = 0; var10 <= 4; var10++) {
            int var12 = var4.nextInt(5);
            this.a(var1, var3, var10, 2, 0, var10, 2, var12, Blocks.by.Q(), Blocks.by.Q(), false);
         }

         for (int var11 = 0; var11 <= 4; var11++) {
            for (int var13 = 0; var13 <= 1; var13++) {
               int var14 = var4.nextInt(3);
               this.a(var1, var3, var11, var13, 0, var11, var13, var14, Blocks.by.Q(), Blocks.by.Q(), false);
            }
         }

         return true;
      }
   }

   public static class Entrance extends StructureNetherBridgePieces.Piece {
      public Entrance() {
      }

      public Entrance(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 5, 3, true);
      }

      public static StructureNetherBridgePieces.Entrance a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -5, -3, 0, 13, 14, 13, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Entrance(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 3, 0, 12, 4, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 5, 0, 12, 13, 12, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 5, 0, 1, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 11, 5, 0, 12, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 11, 4, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 8, 5, 11, 10, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 9, 11, 7, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 0, 4, 12, 1, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 8, 5, 0, 10, 12, 1, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 9, 0, 7, 12, 1, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 11, 2, 10, 12, 10, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 8, 0, 7, 8, 0, Blocks.bz.Q(), Blocks.bz.Q(), false);

         for (byte var4 = 1; var4 <= 11; var4 += 2) {
            this.a(var1, var3, var4, 10, 0, var4, 11, 0, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, var3, var4, 10, 12, var4, 11, 12, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, var3, 0, 10, var4, 0, 11, var4, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, var3, 12, 10, var4, 12, 11, var4, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, Blocks.by.Q(), var4, 13, 0, var3);
            this.a(var1, Blocks.by.Q(), var4, 13, 12, var3);
            this.a(var1, Blocks.by.Q(), 0, 13, var4, var3);
            this.a(var1, Blocks.by.Q(), 12, 13, var4, var3);
            this.a(var1, Blocks.bz.Q(), var4 + 1, 13, 0, var3);
            this.a(var1, Blocks.bz.Q(), var4 + 1, 13, 12, var3);
            this.a(var1, Blocks.bz.Q(), 0, 13, var4 + 1, var3);
            this.a(var1, Blocks.bz.Q(), 12, 13, var4 + 1, var3);
         }

         this.a(var1, Blocks.bz.Q(), 0, 13, 0, var3);
         this.a(var1, Blocks.bz.Q(), 0, 13, 12, var3);
         this.a(var1, Blocks.bz.Q(), 0, 13, 0, var3);
         this.a(var1, Blocks.bz.Q(), 12, 13, 0, var3);

         for (byte var6 = 3; var6 <= 9; var6 += 2) {
            this.a(var1, var3, 1, 7, var6, 1, 8, var6, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, var3, 11, 7, var6, 11, 8, var6, Blocks.bz.Q(), Blocks.bz.Q(), false);
         }

         this.a(var1, var3, 4, 2, 0, 8, 2, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 4, 12, 2, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 0, 0, 8, 1, 3, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 0, 9, 8, 1, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 0, 4, 3, 1, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 9, 0, 4, 12, 1, 8, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var7 = 4; var7 <= 8; var7++) {
            for (int var5 = 0; var5 <= 2; var5++) {
               this.b(var1, Blocks.by.Q(), var7, -1, var5, var3);
               this.b(var1, Blocks.by.Q(), var7, -1, 12 - var5, var3);
            }
         }

         for (int var8 = 0; var8 <= 2; var8++) {
            for (int var10 = 4; var10 <= 8; var10++) {
               this.b(var1, Blocks.by.Q(), var8, -1, var10, var3);
               this.b(var1, Blocks.by.Q(), 12 - var8, -1, var10, var3);
            }
         }

         this.a(var1, var3, 5, 5, 5, 7, 5, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 1, 6, 6, 4, 6, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, Blocks.by.Q(), 6, 0, 6, var3);
         this.a(var1, Blocks.k.Q(), 6, 5, 6, var3);
         BlockPos var9 = new BlockPos(this.a(6, 6), this.d(5), this.b(6, 6));
         if (var3.b(var9)) {
            var1.a(Blocks.k, var9, var2);
         }

         return true;
      }
   }

   public static class NetherStalkRoom extends StructureNetherBridgePieces.Piece {
      public NetherStalkRoom() {
      }

      public NetherStalkRoom(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 5, 3, true);
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 5, 11, true);
      }

      public static StructureNetherBridgePieces.NetherStalkRoom a(
         List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6
      ) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -5, -3, 0, 13, 14, 13, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.NetherStalkRoom(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 3, 0, 12, 4, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 5, 0, 12, 13, 12, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 5, 0, 1, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 11, 5, 0, 12, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 11, 4, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 8, 5, 11, 10, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 9, 11, 7, 12, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 0, 4, 12, 1, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 8, 5, 0, 10, 12, 1, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 9, 0, 7, 12, 1, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 11, 2, 10, 12, 10, Blocks.by.Q(), Blocks.by.Q(), false);

         for (byte var4 = 1; var4 <= 11; var4 += 2) {
            this.a(var1, var3, var4, 10, 0, var4, 11, 0, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, var3, var4, 10, 12, var4, 11, 12, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, var3, 0, 10, var4, 0, 11, var4, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, var3, 12, 10, var4, 12, 11, var4, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, Blocks.by.Q(), var4, 13, 0, var3);
            this.a(var1, Blocks.by.Q(), var4, 13, 12, var3);
            this.a(var1, Blocks.by.Q(), 0, 13, var4, var3);
            this.a(var1, Blocks.by.Q(), 12, 13, var4, var3);
            this.a(var1, Blocks.bz.Q(), var4 + 1, 13, 0, var3);
            this.a(var1, Blocks.bz.Q(), var4 + 1, 13, 12, var3);
            this.a(var1, Blocks.bz.Q(), 0, 13, var4 + 1, var3);
            this.a(var1, Blocks.bz.Q(), 12, 13, var4 + 1, var3);
         }

         this.a(var1, Blocks.bz.Q(), 0, 13, 0, var3);
         this.a(var1, Blocks.bz.Q(), 0, 13, 12, var3);
         this.a(var1, Blocks.bz.Q(), 0, 13, 0, var3);
         this.a(var1, Blocks.bz.Q(), 12, 13, 0, var3);

         for (byte var9 = 3; var9 <= 9; var9 += 2) {
            this.a(var1, var3, 1, 7, var9, 1, 8, var9, Blocks.bz.Q(), Blocks.bz.Q(), false);
            this.a(var1, var3, 11, 7, var9, 11, 8, var9, Blocks.bz.Q(), Blocks.bz.Q(), false);
         }

         int var10 = this.a(Blocks.bA, 3);

         for (int var5 = 0; var5 <= 6; var5++) {
            int var6 = var5 + 4;

            for (int var7 = 5; var7 <= 7; var7++) {
               this.a(var1, Blocks.bA.a(var10), var7, 5 + var5, var6, var3);
            }

            if (var6 >= 5 && var6 <= 8) {
               this.a(var1, var3, 5, 5, var6, 7, var5 + 4, var6, Blocks.by.Q(), Blocks.by.Q(), false);
            } else if (var6 >= 9 && var6 <= 10) {
               this.a(var1, var3, 5, 8, var6, 7, var5 + 4, var6, Blocks.by.Q(), Blocks.by.Q(), false);
            }

            if (var5 >= 1) {
               this.a(var1, var3, 5, 6 + var5, var6, 7, 9 + var5, var6, Blocks.a.Q(), Blocks.a.Q(), false);
            }
         }

         for (int var11 = 5; var11 <= 7; var11++) {
            this.a(var1, Blocks.bA.a(var10), var11, 12, 11, var3);
         }

         this.a(var1, var3, 5, 6, 7, 5, 7, 7, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 7, 6, 7, 7, 7, 7, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 5, 13, 12, 7, 13, 12, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 2, 5, 2, 3, 5, 3, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 9, 3, 5, 10, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 4, 2, 5, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 9, 5, 2, 10, 5, 3, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 9, 5, 9, 10, 5, 10, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 10, 5, 4, 10, 5, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         int var12 = this.a(Blocks.bA, 0);
         int var13 = this.a(Blocks.bA, 1);
         this.a(var1, Blocks.bA.a(var13), 4, 5, 2, var3);
         this.a(var1, Blocks.bA.a(var13), 4, 5, 3, var3);
         this.a(var1, Blocks.bA.a(var13), 4, 5, 9, var3);
         this.a(var1, Blocks.bA.a(var13), 4, 5, 10, var3);
         this.a(var1, Blocks.bA.a(var12), 8, 5, 2, var3);
         this.a(var1, Blocks.bA.a(var12), 8, 5, 3, var3);
         this.a(var1, Blocks.bA.a(var12), 8, 5, 9, var3);
         this.a(var1, Blocks.bA.a(var12), 8, 5, 10, var3);
         this.a(var1, var3, 3, 4, 4, 4, 4, 8, Blocks.aW.Q(), Blocks.aW.Q(), false);
         this.a(var1, var3, 8, 4, 4, 9, 4, 8, Blocks.aW.Q(), Blocks.aW.Q(), false);
         this.a(var1, var3, 3, 5, 4, 4, 5, 8, Blocks.bB.Q(), Blocks.bB.Q(), false);
         this.a(var1, var3, 8, 5, 4, 9, 5, 8, Blocks.bB.Q(), Blocks.bB.Q(), false);
         this.a(var1, var3, 4, 2, 0, 8, 2, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 4, 12, 2, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 0, 0, 8, 1, 3, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 0, 9, 8, 1, 12, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 0, 4, 3, 1, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 9, 0, 4, 12, 1, 8, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var14 = 4; var14 <= 8; var14++) {
            for (int var8 = 0; var8 <= 2; var8++) {
               this.b(var1, Blocks.by.Q(), var14, -1, var8, var3);
               this.b(var1, Blocks.by.Q(), var14, -1, 12 - var8, var3);
            }
         }

         for (int var15 = 0; var15 <= 2; var15++) {
            for (int var16 = 4; var16 <= 8; var16++) {
               this.b(var1, Blocks.by.Q(), var15, -1, var16, var3);
               this.b(var1, Blocks.by.Q(), 12 - var15, -1, var16, var3);
            }
         }

         return true;
      }
   }

   abstract static class Piece extends StructureComponent {
      protected static final List<WeightedRandomChestContent> a = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.i, 0, 1, 3, 5),
            new WeightedRandomChestContent(Items.j, 0, 1, 5, 5),
            new WeightedRandomChestContent(Items.k, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.B, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.ah, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.d, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.by, 0, 3, 7, 5),
            new WeightedRandomChestContent(Items.aA, 0, 1, 1, 10),
            new WeightedRandomChestContent(Items.cl, 0, 1, 1, 8),
            new WeightedRandomChestContent(Items.ck, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.cm, 0, 1, 1, 3),
            new WeightedRandomChestContent(Item.a(Blocks.Z), 0, 2, 4, 2)
         }
      );

      public Piece() {
      }

      protected Piece(int var1) {
         super(var1);
      }

      @Override
      protected void b(NBTTagCompound var1) {
      }

      @Override
      protected void a(NBTTagCompound var1) {
      }

      private int a(List<StructureNetherBridgePieces.PieceWeight> var1) {
         boolean var2 = false;
         int var3 = 0;

         for (StructureNetherBridgePieces.PieceWeight var5 : var1) {
            if (var5.d > 0 && var5.c < var5.d) {
               var2 = true;
            }

            var3 += var5.b;
         }

         return var2 ? var3 : -1;
      }

      private StructureNetherBridgePieces.Piece a(
         StructureNetherBridgePieces.Start var1,
         List<StructureNetherBridgePieces.PieceWeight> var2,
         List<StructureComponent> var3,
         Random var4,
         int var5,
         int var6,
         int var7,
         EnumFacing var8,
         int var9
      ) {
         int var10 = this.a(var2);
         boolean var11 = var10 > 0 && var9 <= 30;
         int var12 = 0;

         while (var12 < 5 && var11) {
            var12++;
            int var13 = var4.nextInt(var10);

            for (StructureNetherBridgePieces.PieceWeight var15 : var2) {
               var13 -= var15.b;
               if (var13 < 0) {
                  if (!var15.a(var9) || var15 == var1.b && !var15.e) {
                     break;
                  }

                  StructureNetherBridgePieces.Piece var16 = StructureNetherBridgePieces.b(var15, var3, var4, var5, var6, var7, var8, var9);
                  if (var16 != null) {
                     var15.c++;
                     var1.b = var15;
                     if (!var15.a()) {
                        var2.remove(var15);
                     }

                     return var16;
                  }
               }
            }
         }

         return StructureNetherBridgePieces.End.a(var3, var4, var5, var6, var7, var8, var9);
      }

      private StructureComponent a(
         StructureNetherBridgePieces.Start var1,
         List<StructureComponent> var2,
         Random var3,
         int var4,
         int var5,
         int var6,
         EnumFacing var7,
         int var8,
         boolean var9
      ) {
         if (Math.abs(var4 - var1.c().a) <= 112 && Math.abs(var6 - var1.c().c) <= 112) {
            List var10 = var1.c;
            if (var9) {
               var10 = var1.d;
            }

            StructureNetherBridgePieces.Piece var11 = this.a(var1, var10, var2, var3, var4, var5, var6, var7, var8 + 1);
            if (var11 != null) {
               var2.add(var11);
               var1.e.add(var11);
            }

            return var11;
         } else {
            return StructureNetherBridgePieces.End.a(var2, var3, var4, var5, var6, var7, var8);
         }
      }

      protected StructureComponent a(StructureNetherBridgePieces.Start var1, List<StructureComponent> var2, Random var3, int var4, int var5, boolean var6) {
         if (this.m != null) {
            switch (this.m) {
               case c:
                  return this.a(var1, var2, var3, this.l.a + var4, this.l.b + var5, this.l.c - 1, this.m, this.d(), var6);
               case d:
                  return this.a(var1, var2, var3, this.l.a + var4, this.l.b + var5, this.l.f + 1, this.m, this.d(), var6);
               case e:
                  return this.a(var1, var2, var3, this.l.a - 1, this.l.b + var5, this.l.c + var4, this.m, this.d(), var6);
               case f:
                  return this.a(var1, var2, var3, this.l.d + 1, this.l.b + var5, this.l.c + var4, this.m, this.d(), var6);
            }
         }

         return null;
      }

      protected StructureComponent b(StructureNetherBridgePieces.Start var1, List<StructureComponent> var2, Random var3, int var4, int var5, boolean var6) {
         if (this.m != null) {
            switch (this.m) {
               case c:
                  return this.a(var1, var2, var3, this.l.a - 1, this.l.b + var4, this.l.c + var5, EnumFacing.e, this.d(), var6);
               case d:
                  return this.a(var1, var2, var3, this.l.a - 1, this.l.b + var4, this.l.c + var5, EnumFacing.e, this.d(), var6);
               case e:
                  return this.a(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.c - 1, EnumFacing.c, this.d(), var6);
               case f:
                  return this.a(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.c - 1, EnumFacing.c, this.d(), var6);
            }
         }

         return null;
      }

      protected StructureComponent c(StructureNetherBridgePieces.Start var1, List<StructureComponent> var2, Random var3, int var4, int var5, boolean var6) {
         if (this.m != null) {
            switch (this.m) {
               case c:
                  return this.a(var1, var2, var3, this.l.d + 1, this.l.b + var4, this.l.c + var5, EnumFacing.f, this.d(), var6);
               case d:
                  return this.a(var1, var2, var3, this.l.d + 1, this.l.b + var4, this.l.c + var5, EnumFacing.f, this.d(), var6);
               case e:
                  return this.a(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.f + 1, EnumFacing.d, this.d(), var6);
               case f:
                  return this.a(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.f + 1, EnumFacing.d, this.d(), var6);
            }
         }

         return null;
      }

      protected static boolean a(StructureBoundingBox var0) {
         return var0 != null && var0.b > 10;
      }
   }

   static class PieceWeight {
      public Class<? extends StructureNetherBridgePieces.Piece> a;
      public final int b;
      public int c;
      public int d;
      public boolean e;

      public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> var1, int var2, int var3, boolean var4) {
         this.a = var1;
         this.b = var2;
         this.d = var3;
         this.e = var4;
      }

      public PieceWeight(Class<? extends StructureNetherBridgePieces.Piece> var1, int var2, int var3) {
         this(var1, var2, var3, false);
      }

      public boolean a(int var1) {
         return this.d == 0 || this.c < this.d;
      }

      public boolean a() {
         return this.d == 0 || this.c < this.d;
      }
   }

   public static class Stairs extends StructureNetherBridgePieces.Piece {
      public Stairs() {
      }

      public Stairs(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.c((StructureNetherBridgePieces.Start)var1, var2, var3, 6, 2, false);
      }

      public static StructureNetherBridgePieces.Stairs a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, int var5, EnumFacing var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -2, 0, 0, 7, 11, 7, var6);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Stairs(var5, var1, var7, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 0, 0, 6, 1, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 6, 10, 6, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 2, 0, 1, 8, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 2, 0, 6, 8, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 1, 0, 8, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 2, 1, 6, 8, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 2, 6, 5, 8, 6, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 3, 2, 0, 5, 4, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 6, 3, 2, 6, 5, 2, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 6, 3, 4, 6, 5, 4, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, Blocks.by.Q(), 5, 2, 5, var3);
         this.a(var1, var3, 4, 2, 5, 4, 3, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 3, 2, 5, 3, 4, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 2, 5, 2, 5, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 2, 5, 1, 6, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 7, 1, 5, 7, 4, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 8, 2, 6, 8, 4, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 2, 6, 0, 4, 8, 0, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 2, 5, 0, 4, 5, 0, Blocks.bz.Q(), Blocks.bz.Q(), false);

         for (int var4 = 0; var4 <= 6; var4++) {
            for (int var5 = 0; var5 <= 6; var5++) {
               this.b(var1, Blocks.by.Q(), var4, -1, var5, var3);
            }
         }

         return true;
      }
   }

   public static class Start extends StructureNetherBridgePieces.Crossing3 {
      public StructureNetherBridgePieces.PieceWeight b;
      public List<StructureNetherBridgePieces.PieceWeight> c;
      public List<StructureNetherBridgePieces.PieceWeight> d;
      public List<StructureComponent> e = Lists.newArrayList();

      public Start() {
      }

      public Start(Random var1, int var2, int var3) {
         super(var1, var2, var3);
         this.c = Lists.newArrayList();

         for (StructureNetherBridgePieces.PieceWeight var7 : StructureNetherBridgePieces.a) {
            var7.c = 0;
            this.c.add(var7);
         }

         this.d = Lists.newArrayList();

         for (StructureNetherBridgePieces.PieceWeight var11 : StructureNetherBridgePieces.b) {
            var11.c = 0;
            this.d.add(var11);
         }
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
      }
   }

   public static class Straight extends StructureNetherBridgePieces.Piece {
      public Straight() {
      }

      public Straight(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureNetherBridgePieces.Start)var1, var2, var3, 1, 3, false);
      }

      public static StructureNetherBridgePieces.Straight a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -3, 0, 5, 10, 19, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Straight(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 3, 0, 4, 4, 18, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 5, 0, 3, 7, 18, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 0, 5, 0, 0, 5, 18, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 4, 5, 0, 4, 5, 18, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 0, 4, 2, 5, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 2, 13, 4, 2, 18, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 0, 0, 4, 1, 3, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 0, 15, 4, 1, 18, Blocks.by.Q(), Blocks.by.Q(), false);

         for (int var4 = 0; var4 <= 4; var4++) {
            for (int var5 = 0; var5 <= 2; var5++) {
               this.b(var1, Blocks.by.Q(), var4, -1, var5, var3);
               this.b(var1, Blocks.by.Q(), var4, -1, 18 - var5, var3);
            }
         }

         this.a(var1, var3, 0, 1, 1, 0, 4, 1, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 3, 4, 0, 4, 4, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 3, 14, 0, 4, 14, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 0, 1, 17, 0, 4, 17, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 4, 1, 1, 4, 4, 1, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 4, 3, 4, 4, 4, 4, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 4, 3, 14, 4, 4, 14, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 4, 1, 17, 4, 4, 17, Blocks.bz.Q(), Blocks.bz.Q(), false);
         return true;
      }
   }

   public static class Throne extends StructureNetherBridgePieces.Piece {
      private boolean b;

      public Throne() {
      }

      public Throne(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.b = var1.n("Mob");
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Mob", this.b);
      }

      public static StructureNetherBridgePieces.Throne a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, int var5, EnumFacing var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -2, 0, 0, 7, 8, 9, var6);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureNetherBridgePieces.Throne(var5, var1, var7, var6) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 2, 0, 6, 7, 7, Blocks.a.Q(), Blocks.a.Q(), false);
         this.a(var1, var3, 1, 0, 0, 5, 1, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 2, 1, 5, 2, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 3, 2, 5, 3, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 4, 3, 5, 4, 7, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 2, 0, 1, 4, 2, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 2, 0, 5, 4, 2, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 5, 2, 1, 5, 3, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 5, 5, 2, 5, 5, 3, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 0, 5, 3, 0, 5, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 6, 5, 3, 6, 5, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, var3, 1, 5, 8, 5, 5, 8, Blocks.by.Q(), Blocks.by.Q(), false);
         this.a(var1, Blocks.bz.Q(), 1, 6, 3, var3);
         this.a(var1, Blocks.bz.Q(), 5, 6, 3, var3);
         this.a(var1, var3, 0, 6, 3, 0, 6, 8, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 6, 6, 3, 6, 6, 8, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 1, 6, 8, 5, 7, 8, Blocks.bz.Q(), Blocks.bz.Q(), false);
         this.a(var1, var3, 2, 8, 8, 4, 8, 8, Blocks.bz.Q(), Blocks.bz.Q(), false);
         if (!this.b) {
            BlockPos var4 = new BlockPos(this.a(3, 5), this.d(5), this.b(3, 5));
            if (var3.b(var4)) {
               this.b = true;
               var1.a(var4, Blocks.ac.Q(), 2);
               TileEntity var5 = var1.s(var4);
               if (var5 instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)var5).b().a("Blaze");
               }
            }
         }

         for (int var6 = 0; var6 <= 6; var6++) {
            for (int var7 = 0; var7 <= 6; var7++) {
               this.b(var1, Blocks.by.Q(), var6, -1, var7, var3);
            }
         }

         return true;
      }
   }
}
