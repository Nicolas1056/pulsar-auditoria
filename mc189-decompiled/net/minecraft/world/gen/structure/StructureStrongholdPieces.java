package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.BlockEndPortalFrame;
import net.minecraft.block.BlockSilverfish;
import net.minecraft.block.BlockStoneBrick;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureStrongholdPieces {
   private static final StructureStrongholdPieces.PieceWeight[] b = new StructureStrongholdPieces.PieceWeight[]{
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Straight.class, 40, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Prison.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.LeftTurn.class, 20, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RightTurn.class, 20, 0),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.RoomCrossing.class, 10, 6),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.StairsStraight.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Stairs.class, 5, 5),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Crossing.class, 5, 4),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.ChestCorridor.class, 5, 4),
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.Library.class, 10, 2) {
         @Override
         public boolean a(int var1) {
            return super.a(var1) && var1 > 4;
         }
      },
      new StructureStrongholdPieces.PieceWeight(StructureStrongholdPieces.PortalRoom.class, 20, 1) {
         @Override
         public boolean a(int var1) {
            return super.a(var1) && var1 > 5;
         }
      }
   };
   private static List<StructureStrongholdPieces.PieceWeight> c;
   private static Class<? extends StructureStrongholdPieces.Stronghold> d;
   static int a;
   private static final StructureStrongholdPieces.Stones e = new StructureStrongholdPieces.Stones();

   public static void a() {
      MapGenStructureIO.a(StructureStrongholdPieces.ChestCorridor.class, "SHCC");
      MapGenStructureIO.a(StructureStrongholdPieces.Corridor.class, "SHFC");
      MapGenStructureIO.a(StructureStrongholdPieces.Crossing.class, "SH5C");
      MapGenStructureIO.a(StructureStrongholdPieces.LeftTurn.class, "SHLT");
      MapGenStructureIO.a(StructureStrongholdPieces.Library.class, "SHLi");
      MapGenStructureIO.a(StructureStrongholdPieces.PortalRoom.class, "SHPR");
      MapGenStructureIO.a(StructureStrongholdPieces.Prison.class, "SHPH");
      MapGenStructureIO.a(StructureStrongholdPieces.RightTurn.class, "SHRT");
      MapGenStructureIO.a(StructureStrongholdPieces.RoomCrossing.class, "SHRC");
      MapGenStructureIO.a(StructureStrongholdPieces.Stairs.class, "SHSD");
      MapGenStructureIO.a(StructureStrongholdPieces.Stairs2.class, "SHStart");
      MapGenStructureIO.a(StructureStrongholdPieces.Straight.class, "SHS");
      MapGenStructureIO.a(StructureStrongholdPieces.StairsStraight.class, "SHSSD");
   }

   public static void b() {
      c = Lists.newArrayList();

      for (StructureStrongholdPieces.PieceWeight var3 : b) {
         var3.c = 0;
         c.add(var3);
      }

      d = null;
   }

   private static boolean d() {
      boolean var0 = false;
      a = 0;

      for (StructureStrongholdPieces.PieceWeight var2 : c) {
         if (var2.d > 0 && var2.c < var2.d) {
            var0 = true;
         }

         a = a + var2.b;
      }

      return var0;
   }

   private static StructureStrongholdPieces.Stronghold a(
      Class<? extends StructureStrongholdPieces.Stronghold> var0,
      List<StructureComponent> var1,
      Random var2,
      int var3,
      int var4,
      int var5,
      EnumFacing var6,
      int var7
   ) {
      Object var8 = null;
      if (var0 == StructureStrongholdPieces.Straight.class) {
         var8 = StructureStrongholdPieces.Straight.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.Prison.class) {
         var8 = StructureStrongholdPieces.Prison.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.LeftTurn.class) {
         var8 = StructureStrongholdPieces.LeftTurn.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.RightTurn.class) {
         var8 = StructureStrongholdPieces.RightTurn.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.RoomCrossing.class) {
         var8 = StructureStrongholdPieces.RoomCrossing.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.StairsStraight.class) {
         var8 = StructureStrongholdPieces.StairsStraight.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.Stairs.class) {
         var8 = StructureStrongholdPieces.Stairs.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.Crossing.class) {
         var8 = StructureStrongholdPieces.Crossing.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.ChestCorridor.class) {
         var8 = StructureStrongholdPieces.ChestCorridor.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.Library.class) {
         var8 = StructureStrongholdPieces.Library.a(var1, var2, var3, var4, var5, var6, var7);
      } else if (var0 == StructureStrongholdPieces.PortalRoom.class) {
         var8 = StructureStrongholdPieces.PortalRoom.a(var1, var2, var3, var4, var5, var6, var7);
      }

      return (StructureStrongholdPieces.Stronghold)var8;
   }

   private static StructureStrongholdPieces.Stronghold b(
      StructureStrongholdPieces.Stairs2 var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (!d()) {
         return null;
      } else {
         if (d != null) {
            StructureStrongholdPieces.Stronghold var8 = a(d, var1, var2, var3, var4, var5, var6, var7);
            d = null;
            if (var8 != null) {
               return var8;
            }
         }

         int var13 = 0;

         while (var13 < 5) {
            var13++;
            int var9 = var2.nextInt(a);

            for (StructureStrongholdPieces.PieceWeight var11 : c) {
               var9 -= var11.b;
               if (var9 < 0) {
                  if (!var11.a(var7) || var11 == var0.a) {
                     break;
                  }

                  StructureStrongholdPieces.Stronghold var12 = a(var11.a, var1, var2, var3, var4, var5, var6, var7);
                  if (var12 != null) {
                     var11.c++;
                     var0.a = var11;
                     if (!var11.a()) {
                        c.remove(var11);
                     }

                     return var12;
                  }
               }
            }
         }

         StructureBoundingBox var14 = StructureStrongholdPieces.Corridor.a(var1, var2, var3, var4, var5, var6);
         return var14 != null && var14.b > 1 ? new StructureStrongholdPieces.Corridor(var7, var2, var14, var6) : null;
      }
   }

   private static StructureComponent c(
      StructureStrongholdPieces.Stairs2 var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (var7 > 50) {
         return null;
      } else if (Math.abs(var3 - var0.c().a) <= 112 && Math.abs(var5 - var0.c().c) <= 112) {
         StructureStrongholdPieces.Stronghold var8 = b(var0, var1, var2, var3, var4, var5, var6, var7 + 1);
         if (var8 != null) {
            var1.add(var8);
            var0.c.add(var8);
         }

         return var8;
      } else {
         return null;
      }
   }

   public static class ChestCorridor extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> a = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.bu, 0, 1, 1, 10),
            new WeightedRandomChestContent(Items.i, 0, 1, 3, 3),
            new WeightedRandomChestContent(Items.j, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.k, 0, 1, 3, 5),
            new WeightedRandomChestContent(Items.aC, 0, 4, 9, 5),
            new WeightedRandomChestContent(Items.P, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.e, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.b, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.l, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.Z, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.Y, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.aa, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.ab, 0, 1, 1, 5),
            new WeightedRandomChestContent(Items.ao, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.aA, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.ck, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.cl, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.cm, 0, 1, 1, 1)
         }
      );
      private boolean b;

      public ChestCorridor() {
      }

      public ChestCorridor(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
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
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
      }

      public static StructureStrongholdPieces.ChestCorridor a(
         List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6
      ) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 7, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.ChestCorridor(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 4, 4, 6, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 1, 1, 0);
            this.a(var1, var2, var3, StructureStrongholdPieces.Stronghold.Door.a, 1, 1, 6);
            this.a(var1, var3, 3, 1, 2, 3, 1, 4, Blocks.bf.Q(), Blocks.bf.Q(), false);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.f.a()), 3, 1, 1, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.f.a()), 3, 1, 5, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.f.a()), 3, 2, 2, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.f.a()), 3, 2, 4, var3);

            for (int var4 = 2; var4 <= 4; var4++) {
               this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.f.a()), 2, 1, var4, var3);
            }

            if (!this.b && var3.b(new BlockPos(this.a(3, 3), this.d(2), this.b(3, 3)))) {
               this.b = true;
               this.a(var1, var3, var2, 3, 2, 3, WeightedRandomChestContent.a(a, Items.cd.b(var2)), 2 + var2.nextInt(2));
            }

            return true;
         }
      }
   }

   public static class Corridor extends StructureStrongholdPieces.Stronghold {
      private int a;

      public Corridor() {
      }

      public Corridor(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
         this.a = var4 != EnumFacing.c && var4 != EnumFacing.d ? var3.c() : var3.e();
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Steps", this.a);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = var1.f("Steps");
      }

      public static StructureBoundingBox a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         byte var6 = 3;
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 4, var5);
         StructureComponent var8 = StructureComponent.a(var0, var7);
         if (var8 == null) {
            return null;
         } else {
            if (var8.c().b == var7.b) {
               for (int var9 = 3; var9 >= 1; var9--) {
                  var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, var9 - 1, var5);
                  if (!var8.c().a(var7)) {
                     return StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, var9, var5);
                  }
               }
            }

            return null;
         }
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            for (int var4 = 0; var4 < this.a; var4++) {
               this.a(var1, Blocks.bf.Q(), 0, 0, var4, var3);
               this.a(var1, Blocks.bf.Q(), 1, 0, var4, var3);
               this.a(var1, Blocks.bf.Q(), 2, 0, var4, var3);
               this.a(var1, Blocks.bf.Q(), 3, 0, var4, var3);
               this.a(var1, Blocks.bf.Q(), 4, 0, var4, var3);

               for (int var5 = 1; var5 <= 3; var5++) {
                  this.a(var1, Blocks.bf.Q(), 0, var5, var4, var3);
                  this.a(var1, Blocks.a.Q(), 1, var5, var4, var3);
                  this.a(var1, Blocks.a.Q(), 2, var5, var4, var3);
                  this.a(var1, Blocks.a.Q(), 3, var5, var4, var3);
                  this.a(var1, Blocks.bf.Q(), 4, var5, var4, var3);
               }

               this.a(var1, Blocks.bf.Q(), 0, 4, var4, var3);
               this.a(var1, Blocks.bf.Q(), 1, 4, var4, var3);
               this.a(var1, Blocks.bf.Q(), 2, 4, var4, var3);
               this.a(var1, Blocks.bf.Q(), 3, 4, var4, var3);
               this.a(var1, Blocks.bf.Q(), 4, 4, var4, var3);
            }

            return true;
         }
      }
   }

   public static class Crossing extends StructureStrongholdPieces.Stronghold {
      private boolean a;
      private boolean b;
      private boolean c;
      private boolean e;

      public Crossing() {
      }

      public Crossing(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
         this.a = var2.nextBoolean();
         this.b = var2.nextBoolean();
         this.c = var2.nextBoolean();
         this.e = var2.nextInt(3) > 0;
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("leftLow", this.a);
         var1.a("leftHigh", this.b);
         var1.a("rightLow", this.c);
         var1.a("rightHigh", this.e);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = var1.n("leftLow");
         this.b = var1.n("leftHigh");
         this.c = var1.n("rightLow");
         this.e = var1.n("rightHigh");
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         int var4 = 3;
         int var5 = 5;
         if (this.m == EnumFacing.e || this.m == EnumFacing.c) {
            var4 = 8 - var4;
            var5 = 8 - var5;
         }

         this.a((StructureStrongholdPieces.Stairs2)var1, var2, var3, 5, 1);
         if (this.a) {
            this.b((StructureStrongholdPieces.Stairs2)var1, var2, var3, var4, 1);
         }

         if (this.b) {
            this.b((StructureStrongholdPieces.Stairs2)var1, var2, var3, var5, 7);
         }

         if (this.c) {
            this.c((StructureStrongholdPieces.Stairs2)var1, var2, var3, var4, 1);
         }

         if (this.e) {
            this.c((StructureStrongholdPieces.Stairs2)var1, var2, var3, var5, 7);
         }
      }

      public static StructureStrongholdPieces.Crossing a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -3, 0, 10, 9, 11, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.Crossing(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 9, 8, 10, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 4, 3, 0);
            if (this.a) {
               this.a(var1, var3, 0, 3, 1, 0, 5, 3, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            if (this.c) {
               this.a(var1, var3, 9, 3, 1, 9, 5, 3, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            if (this.b) {
               this.a(var1, var3, 0, 5, 7, 0, 7, 9, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            if (this.e) {
               this.a(var1, var3, 9, 5, 7, 9, 7, 9, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            this.a(var1, var3, 5, 1, 10, 7, 3, 10, Blocks.a.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, 1, 2, 1, 8, 2, 6, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 4, 1, 5, 4, 4, 9, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 8, 1, 5, 8, 4, 9, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 1, 4, 7, 3, 4, 9, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 1, 3, 5, 3, 3, 6, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 1, 3, 4, 3, 3, 4, Blocks.U.Q(), Blocks.U.Q(), false);
            this.a(var1, var3, 1, 4, 6, 3, 4, 6, Blocks.U.Q(), Blocks.U.Q(), false);
            this.a(var1, var3, 5, 1, 7, 7, 1, 8, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 5, 1, 9, 7, 1, 9, Blocks.U.Q(), Blocks.U.Q(), false);
            this.a(var1, var3, 5, 2, 7, 7, 2, 7, Blocks.U.Q(), Blocks.U.Q(), false);
            this.a(var1, var3, 4, 5, 7, 4, 5, 9, Blocks.U.Q(), Blocks.U.Q(), false);
            this.a(var1, var3, 8, 5, 7, 8, 5, 9, Blocks.U.Q(), Blocks.U.Q(), false);
            this.a(var1, var3, 5, 5, 7, 7, 5, 9, Blocks.T.Q(), Blocks.T.Q(), false);
            this.a(var1, Blocks.aa.Q(), 6, 5, 6, var3);
            return true;
         }
      }
   }

   public static class LeftTurn extends StructureStrongholdPieces.Stronghold {
      public LeftTurn() {
      }

      public LeftTurn(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         if (this.m != EnumFacing.c && this.m != EnumFacing.f) {
            this.c((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
         } else {
            this.b((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
         }
      }

      public static StructureStrongholdPieces.LeftTurn a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 5, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.LeftTurn(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 4, 4, 4, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 1, 1, 0);
            if (this.m != EnumFacing.c && this.m != EnumFacing.f) {
               this.a(var1, var3, 4, 1, 1, 4, 3, 3, Blocks.a.Q(), Blocks.a.Q(), false);
            } else {
               this.a(var1, var3, 0, 1, 1, 0, 3, 3, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            return true;
         }
      }
   }

   public static class Library extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> a = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.aL, 0, 1, 3, 20),
            new WeightedRandomChestContent(Items.aK, 0, 2, 7, 20),
            new WeightedRandomChestContent(Items.bV, 0, 1, 1, 1),
            new WeightedRandomChestContent(Items.aQ, 0, 1, 1, 1)
         }
      );
      private boolean b;

      public Library() {
      }

      public Library(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
         this.b = var3.d() > 6;
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Tall", this.b);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.b = var1.n("Tall");
      }

      public static StructureStrongholdPieces.Library a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 14, 11, 15, var5);
         if (!a(var7) || StructureComponent.a(var0, var7) != null) {
            var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 14, 6, 15, var5);
            if (!a(var7) || StructureComponent.a(var0, var7) != null) {
               return null;
            }
         }

         return new StructureStrongholdPieces.Library(var6, var1, var7, var5);
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            byte var4 = 11;
            if (!this.b) {
               var4 = 6;
            }

            this.a(var1, var3, 0, 0, 0, 13, var4 - 1, 14, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 4, 1, 0);
            this.a(var1, var3, var2, 0.07F, 2, 1, 1, 11, 4, 13, Blocks.G.Q(), Blocks.G.Q(), false);
            boolean var5 = true;
            byte var6 = 12;

            for (int var7 = 1; var7 <= 13; var7++) {
               if ((var7 - 1) % 4 == 0) {
                  this.a(var1, var3, 1, 1, var7, 1, 4, var7, Blocks.f.Q(), Blocks.f.Q(), false);
                  this.a(var1, var3, 12, 1, var7, 12, 4, var7, Blocks.f.Q(), Blocks.f.Q(), false);
                  this.a(var1, Blocks.aa.Q(), 2, 3, var7, var3);
                  this.a(var1, Blocks.aa.Q(), 11, 3, var7, var3);
                  if (this.b) {
                     this.a(var1, var3, 1, 6, var7, 1, 9, var7, Blocks.f.Q(), Blocks.f.Q(), false);
                     this.a(var1, var3, 12, 6, var7, 12, 9, var7, Blocks.f.Q(), Blocks.f.Q(), false);
                  }
               } else {
                  this.a(var1, var3, 1, 1, var7, 1, 4, var7, Blocks.X.Q(), Blocks.X.Q(), false);
                  this.a(var1, var3, 12, 1, var7, 12, 4, var7, Blocks.X.Q(), Blocks.X.Q(), false);
                  if (this.b) {
                     this.a(var1, var3, 1, 6, var7, 1, 9, var7, Blocks.X.Q(), Blocks.X.Q(), false);
                     this.a(var1, var3, 12, 6, var7, 12, 9, var7, Blocks.X.Q(), Blocks.X.Q(), false);
                  }
               }
            }

            for (byte var10 = 3; var10 < 12; var10 += 2) {
               this.a(var1, var3, 3, 1, var10, 4, 3, var10, Blocks.X.Q(), Blocks.X.Q(), false);
               this.a(var1, var3, 6, 1, var10, 7, 3, var10, Blocks.X.Q(), Blocks.X.Q(), false);
               this.a(var1, var3, 9, 1, var10, 10, 3, var10, Blocks.X.Q(), Blocks.X.Q(), false);
            }

            if (this.b) {
               this.a(var1, var3, 1, 5, 1, 3, 5, 13, Blocks.f.Q(), Blocks.f.Q(), false);
               this.a(var1, var3, 10, 5, 1, 12, 5, 13, Blocks.f.Q(), Blocks.f.Q(), false);
               this.a(var1, var3, 4, 5, 1, 9, 5, 2, Blocks.f.Q(), Blocks.f.Q(), false);
               this.a(var1, var3, 4, 5, 12, 9, 5, 13, Blocks.f.Q(), Blocks.f.Q(), false);
               this.a(var1, Blocks.f.Q(), 9, 5, 11, var3);
               this.a(var1, Blocks.f.Q(), 8, 5, 11, var3);
               this.a(var1, Blocks.f.Q(), 9, 5, 10, var3);
               this.a(var1, var3, 3, 6, 2, 3, 6, 12, Blocks.aO.Q(), Blocks.aO.Q(), false);
               this.a(var1, var3, 10, 6, 2, 10, 6, 10, Blocks.aO.Q(), Blocks.aO.Q(), false);
               this.a(var1, var3, 4, 6, 2, 9, 6, 2, Blocks.aO.Q(), Blocks.aO.Q(), false);
               this.a(var1, var3, 4, 6, 12, 8, 6, 12, Blocks.aO.Q(), Blocks.aO.Q(), false);
               this.a(var1, Blocks.aO.Q(), 9, 6, 11, var3);
               this.a(var1, Blocks.aO.Q(), 8, 6, 11, var3);
               this.a(var1, Blocks.aO.Q(), 9, 6, 10, var3);
               int var11 = this.a(Blocks.au, 3);
               this.a(var1, Blocks.au.a(var11), 10, 1, 13, var3);
               this.a(var1, Blocks.au.a(var11), 10, 2, 13, var3);
               this.a(var1, Blocks.au.a(var11), 10, 3, 13, var3);
               this.a(var1, Blocks.au.a(var11), 10, 4, 13, var3);
               this.a(var1, Blocks.au.a(var11), 10, 5, 13, var3);
               this.a(var1, Blocks.au.a(var11), 10, 6, 13, var3);
               this.a(var1, Blocks.au.a(var11), 10, 7, 13, var3);
               byte var8 = 7;
               byte var9 = 7;
               this.a(var1, Blocks.aO.Q(), var8 - 1, 9, var9, var3);
               this.a(var1, Blocks.aO.Q(), var8, 9, var9, var3);
               this.a(var1, Blocks.aO.Q(), var8 - 1, 8, var9, var3);
               this.a(var1, Blocks.aO.Q(), var8, 8, var9, var3);
               this.a(var1, Blocks.aO.Q(), var8 - 1, 7, var9, var3);
               this.a(var1, Blocks.aO.Q(), var8, 7, var9, var3);
               this.a(var1, Blocks.aO.Q(), var8 - 2, 7, var9, var3);
               this.a(var1, Blocks.aO.Q(), var8 + 1, 7, var9, var3);
               this.a(var1, Blocks.aO.Q(), var8 - 1, 7, var9 - 1, var3);
               this.a(var1, Blocks.aO.Q(), var8 - 1, 7, var9 + 1, var3);
               this.a(var1, Blocks.aO.Q(), var8, 7, var9 - 1, var3);
               this.a(var1, Blocks.aO.Q(), var8, 7, var9 + 1, var3);
               this.a(var1, Blocks.aa.Q(), var8 - 2, 8, var9, var3);
               this.a(var1, Blocks.aa.Q(), var8 + 1, 8, var9, var3);
               this.a(var1, Blocks.aa.Q(), var8 - 1, 8, var9 - 1, var3);
               this.a(var1, Blocks.aa.Q(), var8 - 1, 8, var9 + 1, var3);
               this.a(var1, Blocks.aa.Q(), var8, 8, var9 - 1, var3);
               this.a(var1, Blocks.aa.Q(), var8, 8, var9 + 1, var3);
            }

            this.a(var1, var3, var2, 3, 3, 5, WeightedRandomChestContent.a(a, Items.cd.a(var2, 1, 5, 2)), 1 + var2.nextInt(4));
            if (this.b) {
               this.a(var1, Blocks.a.Q(), 12, 9, 1, var3);
               this.a(var1, var3, var2, 12, 8, 1, WeightedRandomChestContent.a(a, Items.cd.a(var2, 1, 5, 2)), 1 + var2.nextInt(4));
            }

            return true;
         }
      }
   }

   static class PieceWeight {
      public Class<? extends StructureStrongholdPieces.Stronghold> a;
      public final int b;
      public int c;
      public int d;

      public PieceWeight(Class<? extends StructureStrongholdPieces.Stronghold> var1, int var2, int var3) {
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

   public static class PortalRoom extends StructureStrongholdPieces.Stronghold {
      private boolean a;

      public PortalRoom() {
      }

      public PortalRoom(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Mob", this.a);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = var1.n("Mob");
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         if (var1 != null) {
            ((StructureStrongholdPieces.Stairs2)var1).b = this;
         }
      }

      public static StructureStrongholdPieces.PortalRoom a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 11, 8, 16, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.PortalRoom(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         this.a(var1, var3, 0, 0, 0, 10, 7, 15, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var2, var3, StructureStrongholdPieces.Stronghold.Door.c, 4, 1, 0);
         int var4 = 6;
         this.a(var1, var3, 1, var4, 1, 1, var4, 14, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 9, var4, 1, 9, var4, 14, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 2, var4, 1, 8, var4, 2, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 2, var4, 14, 8, var4, 14, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 1, 1, 1, 2, 1, 4, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 8, 1, 1, 9, 1, 4, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 1, 1, 1, 1, 1, 3, Blocks.k.Q(), Blocks.k.Q(), false);
         this.a(var1, var3, 9, 1, 1, 9, 1, 3, Blocks.k.Q(), Blocks.k.Q(), false);
         this.a(var1, var3, 3, 1, 8, 7, 1, 12, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 4, 1, 9, 6, 1, 11, Blocks.k.Q(), Blocks.k.Q(), false);

         for (byte var5 = 3; var5 < 14; var5 += 2) {
            this.a(var1, var3, 0, 3, var5, 0, 4, var5, Blocks.bi.Q(), Blocks.bi.Q(), false);
            this.a(var1, var3, 10, 3, var5, 10, 4, var5, Blocks.bi.Q(), Blocks.bi.Q(), false);
         }

         for (byte var13 = 2; var13 < 9; var13 += 2) {
            this.a(var1, var3, var13, 3, 15, var13, 4, 15, Blocks.bi.Q(), Blocks.bi.Q(), false);
         }

         int var14 = this.a(Blocks.bv, 3);
         this.a(var1, var3, 4, 1, 5, 6, 1, 7, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 4, 2, 6, 6, 2, 7, false, var2, StructureStrongholdPieces.e);
         this.a(var1, var3, 4, 3, 7, 6, 3, 7, false, var2, StructureStrongholdPieces.e);

         for (int var6 = 4; var6 <= 6; var6++) {
            this.a(var1, Blocks.bv.a(var14), var6, 1, 4, var3);
            this.a(var1, Blocks.bv.a(var14), var6, 2, 5, var3);
            this.a(var1, Blocks.bv.a(var14), var6, 3, 6, var3);
         }

         int var15 = EnumFacing.c.b();
         int var7 = EnumFacing.d.b();
         int var8 = EnumFacing.f.b();
         int var9 = EnumFacing.e.b();
         if (this.m != null) {
            switch (this.m) {
               case d:
                  var15 = EnumFacing.d.b();
                  var7 = EnumFacing.c.b();
                  break;
               case e:
                  var15 = EnumFacing.e.b();
                  var7 = EnumFacing.f.b();
                  var8 = EnumFacing.d.b();
                  var9 = EnumFacing.c.b();
                  break;
               case f:
                  var15 = EnumFacing.f.b();
                  var7 = EnumFacing.e.b();
                  var8 = EnumFacing.d.b();
                  var9 = EnumFacing.c.b();
            }
         }

         this.a(var1, Blocks.bG.a(var15).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 4, 3, 8, var3);
         this.a(var1, Blocks.bG.a(var15).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 5, 3, 8, var3);
         this.a(var1, Blocks.bG.a(var15).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 6, 3, 8, var3);
         this.a(var1, Blocks.bG.a(var7).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 4, 3, 12, var3);
         this.a(var1, Blocks.bG.a(var7).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 5, 3, 12, var3);
         this.a(var1, Blocks.bG.a(var7).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 6, 3, 12, var3);
         this.a(var1, Blocks.bG.a(var8).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 3, 3, 9, var3);
         this.a(var1, Blocks.bG.a(var8).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 3, 3, 10, var3);
         this.a(var1, Blocks.bG.a(var8).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 3, 3, 11, var3);
         this.a(var1, Blocks.bG.a(var9).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 7, 3, 9, var3);
         this.a(var1, Blocks.bG.a(var9).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 7, 3, 10, var3);
         this.a(var1, Blocks.bG.a(var9).a(BlockEndPortalFrame.b, var2.nextFloat() > 0.9F), 7, 3, 11, var3);
         if (!this.a) {
            var4 = this.d(3);
            BlockPos var10 = new BlockPos(this.a(5, 6), var4, this.b(5, 6));
            if (var3.b(var10)) {
               this.a = true;
               var1.a(var10, Blocks.ac.Q(), 2);
               TileEntity var11 = var1.s(var10);
               if (var11 instanceof TileEntityMobSpawner) {
                  ((TileEntityMobSpawner)var11).b().a("Silverfish");
               }
            }
         }

         return true;
      }
   }

   public static class Prison extends StructureStrongholdPieces.Stronghold {
      public Prison() {
      }

      public Prison(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
      }

      public static StructureStrongholdPieces.Prison a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 9, 5, 11, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.Prison(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 8, 4, 10, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 1, 1, 0);
            this.a(var1, var3, 1, 1, 10, 3, 3, 10, Blocks.a.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, 4, 1, 1, 4, 3, 1, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 4, 1, 3, 4, 3, 3, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 4, 1, 7, 4, 3, 7, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 4, 1, 9, 4, 3, 9, false, var2, StructureStrongholdPieces.e);
            this.a(var1, var3, 4, 1, 4, 4, 3, 6, Blocks.bi.Q(), Blocks.bi.Q(), false);
            this.a(var1, var3, 5, 1, 5, 7, 3, 5, Blocks.bi.Q(), Blocks.bi.Q(), false);
            this.a(var1, Blocks.bi.Q(), 4, 3, 2, var3);
            this.a(var1, Blocks.bi.Q(), 4, 3, 8, var3);
            this.a(var1, Blocks.aA.a(this.a(Blocks.aA, 3)), 4, 1, 2, var3);
            this.a(var1, Blocks.aA.a(this.a(Blocks.aA, 3) + 8), 4, 2, 2, var3);
            this.a(var1, Blocks.aA.a(this.a(Blocks.aA, 3)), 4, 1, 8, var3);
            this.a(var1, Blocks.aA.a(this.a(Blocks.aA, 3) + 8), 4, 2, 8, var3);
            return true;
         }
      }
   }

   public static class RightTurn extends StructureStrongholdPieces.LeftTurn {
      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         if (this.m != EnumFacing.c && this.m != EnumFacing.f) {
            this.b((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
         } else {
            this.c((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
         }
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 4, 4, 4, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 1, 1, 0);
            if (this.m != EnumFacing.c && this.m != EnumFacing.f) {
               this.a(var1, var3, 0, 1, 1, 0, 3, 3, Blocks.a.Q(), Blocks.a.Q(), false);
            } else {
               this.a(var1, var3, 4, 1, 1, 4, 3, 3, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            return true;
         }
      }
   }

   public static class RoomCrossing extends StructureStrongholdPieces.Stronghold {
      private static final List<WeightedRandomChestContent> b = Lists.newArrayList(
         new WeightedRandomChestContent[]{
            new WeightedRandomChestContent(Items.j, 0, 1, 5, 10),
            new WeightedRandomChestContent(Items.k, 0, 1, 3, 5),
            new WeightedRandomChestContent(Items.aC, 0, 4, 9, 5),
            new WeightedRandomChestContent(Items.h, 0, 3, 8, 10),
            new WeightedRandomChestContent(Items.P, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.e, 0, 1, 3, 15),
            new WeightedRandomChestContent(Items.b, 0, 1, 1, 1)
         }
      );
      protected int a;

      public RoomCrossing() {
      }

      public RoomCrossing(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
         this.a = var2.nextInt(5);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Type", this.a);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = var1.f("Type");
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureStrongholdPieces.Stairs2)var1, var2, var3, 4, 1);
         this.b((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 4);
         this.c((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 4);
      }

      public static StructureStrongholdPieces.RoomCrossing a(
         List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6
      ) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -4, -1, 0, 11, 7, 11, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.RoomCrossing(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 10, 6, 10, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 4, 1, 0);
            this.a(var1, var3, 4, 1, 10, 6, 3, 10, Blocks.a.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, 0, 1, 4, 0, 3, 6, Blocks.a.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, 10, 1, 4, 10, 3, 6, Blocks.a.Q(), Blocks.a.Q(), false);
            switch (this.a) {
               case 0:
                  this.a(var1, Blocks.bf.Q(), 5, 1, 5, var3);
                  this.a(var1, Blocks.bf.Q(), 5, 2, 5, var3);
                  this.a(var1, Blocks.bf.Q(), 5, 3, 5, var3);
                  this.a(var1, Blocks.aa.Q(), 4, 3, 5, var3);
                  this.a(var1, Blocks.aa.Q(), 6, 3, 5, var3);
                  this.a(var1, Blocks.aa.Q(), 5, 3, 4, var3);
                  this.a(var1, Blocks.aa.Q(), 5, 3, 6, var3);
                  this.a(var1, Blocks.U.Q(), 4, 1, 4, var3);
                  this.a(var1, Blocks.U.Q(), 4, 1, 5, var3);
                  this.a(var1, Blocks.U.Q(), 4, 1, 6, var3);
                  this.a(var1, Blocks.U.Q(), 6, 1, 4, var3);
                  this.a(var1, Blocks.U.Q(), 6, 1, 5, var3);
                  this.a(var1, Blocks.U.Q(), 6, 1, 6, var3);
                  this.a(var1, Blocks.U.Q(), 5, 1, 4, var3);
                  this.a(var1, Blocks.U.Q(), 5, 1, 6, var3);
                  break;
               case 1:
                  for (int var8 = 0; var8 < 5; var8++) {
                     this.a(var1, Blocks.bf.Q(), 3, 1, 3 + var8, var3);
                     this.a(var1, Blocks.bf.Q(), 7, 1, 3 + var8, var3);
                     this.a(var1, Blocks.bf.Q(), 3 + var8, 1, 3, var3);
                     this.a(var1, Blocks.bf.Q(), 3 + var8, 1, 7, var3);
                  }

                  this.a(var1, Blocks.bf.Q(), 5, 1, 5, var3);
                  this.a(var1, Blocks.bf.Q(), 5, 2, 5, var3);
                  this.a(var1, Blocks.bf.Q(), 5, 3, 5, var3);
                  this.a(var1, Blocks.i.Q(), 5, 4, 5, var3);
                  break;
               case 2:
                  for (int var4 = 1; var4 <= 9; var4++) {
                     this.a(var1, Blocks.e.Q(), 1, 3, var4, var3);
                     this.a(var1, Blocks.e.Q(), 9, 3, var4, var3);
                  }

                  for (int var5 = 1; var5 <= 9; var5++) {
                     this.a(var1, Blocks.e.Q(), var5, 3, 1, var3);
                     this.a(var1, Blocks.e.Q(), var5, 3, 9, var3);
                  }

                  this.a(var1, Blocks.e.Q(), 5, 1, 4, var3);
                  this.a(var1, Blocks.e.Q(), 5, 1, 6, var3);
                  this.a(var1, Blocks.e.Q(), 5, 3, 4, var3);
                  this.a(var1, Blocks.e.Q(), 5, 3, 6, var3);
                  this.a(var1, Blocks.e.Q(), 4, 1, 5, var3);
                  this.a(var1, Blocks.e.Q(), 6, 1, 5, var3);
                  this.a(var1, Blocks.e.Q(), 4, 3, 5, var3);
                  this.a(var1, Blocks.e.Q(), 6, 3, 5, var3);

                  for (int var6 = 1; var6 <= 3; var6++) {
                     this.a(var1, Blocks.e.Q(), 4, var6, 4, var3);
                     this.a(var1, Blocks.e.Q(), 6, var6, 4, var3);
                     this.a(var1, Blocks.e.Q(), 4, var6, 6, var3);
                     this.a(var1, Blocks.e.Q(), 6, var6, 6, var3);
                  }

                  this.a(var1, Blocks.aa.Q(), 5, 3, 5, var3);

                  for (int var7 = 2; var7 <= 8; var7++) {
                     this.a(var1, Blocks.f.Q(), 2, 3, var7, var3);
                     this.a(var1, Blocks.f.Q(), 3, 3, var7, var3);
                     if (var7 <= 3 || var7 >= 7) {
                        this.a(var1, Blocks.f.Q(), 4, 3, var7, var3);
                        this.a(var1, Blocks.f.Q(), 5, 3, var7, var3);
                        this.a(var1, Blocks.f.Q(), 6, 3, var7, var3);
                     }

                     this.a(var1, Blocks.f.Q(), 7, 3, var7, var3);
                     this.a(var1, Blocks.f.Q(), 8, 3, var7, var3);
                  }

                  this.a(var1, Blocks.au.a(this.a(Blocks.au, EnumFacing.e.a())), 9, 1, 3, var3);
                  this.a(var1, Blocks.au.a(this.a(Blocks.au, EnumFacing.e.a())), 9, 2, 3, var3);
                  this.a(var1, Blocks.au.a(this.a(Blocks.au, EnumFacing.e.a())), 9, 3, 3, var3);
                  this.a(var1, var3, var2, 3, 4, 8, WeightedRandomChestContent.a(b, Items.cd.b(var2)), 1 + var2.nextInt(4));
            }

            return true;
         }
      }
   }

   public static class Stairs extends StructureStrongholdPieces.Stronghold {
      private boolean a;

      public Stairs() {
      }

      public Stairs(int var1, Random var2, int var3, int var4) {
         super(var1);
         this.a = true;
         this.m = EnumFacing.Plane.a.a(var2);
         this.d = StructureStrongholdPieces.Stronghold.Door.a;
         switch (this.m) {
            case c:
            case d:
               this.l = new StructureBoundingBox(var3, 64, var4, var3 + 5 - 1, 74, var4 + 5 - 1);
               break;
            default:
               this.l = new StructureBoundingBox(var3, 64, var4, var3 + 5 - 1, 74, var4 + 5 - 1);
         }
      }

      public Stairs(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.a = false;
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Source", this.a);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = var1.n("Source");
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         if (this.a) {
            StructureStrongholdPieces.d = StructureStrongholdPieces.Crossing.class;
         }

         this.a((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
      }

      public static StructureStrongholdPieces.Stairs a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -7, 0, 5, 11, 5, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.Stairs(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 4, 10, 4, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 1, 7, 0);
            this.a(var1, var2, var3, StructureStrongholdPieces.Stronghold.Door.a, 1, 1, 4);
            this.a(var1, Blocks.bf.Q(), 2, 6, 1, var3);
            this.a(var1, Blocks.bf.Q(), 1, 5, 1, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.a.a()), 1, 6, 1, var3);
            this.a(var1, Blocks.bf.Q(), 1, 5, 2, var3);
            this.a(var1, Blocks.bf.Q(), 1, 4, 3, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.a.a()), 1, 5, 3, var3);
            this.a(var1, Blocks.bf.Q(), 2, 4, 3, var3);
            this.a(var1, Blocks.bf.Q(), 3, 3, 3, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.a.a()), 3, 4, 3, var3);
            this.a(var1, Blocks.bf.Q(), 3, 3, 2, var3);
            this.a(var1, Blocks.bf.Q(), 3, 2, 1, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.a.a()), 3, 3, 1, var3);
            this.a(var1, Blocks.bf.Q(), 2, 2, 1, var3);
            this.a(var1, Blocks.bf.Q(), 1, 1, 1, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.a.a()), 1, 2, 1, var3);
            this.a(var1, Blocks.bf.Q(), 1, 1, 2, var3);
            this.a(var1, Blocks.U.a(BlockStoneSlab.EnumType.a.a()), 1, 1, 3, var3);
            return true;
         }
      }
   }

   public static class Stairs2 extends StructureStrongholdPieces.Stairs {
      public StructureStrongholdPieces.PieceWeight a;
      public StructureStrongholdPieces.PortalRoom b;
      public List<StructureComponent> c = Lists.newArrayList();

      public Stairs2() {
      }

      public Stairs2(int var1, Random var2, int var3, int var4) {
         super(0, var2, var3, var4);
      }

      @Override
      public BlockPos a() {
         return this.b != null ? this.b.a() : super.a();
      }
   }

   public static class StairsStraight extends StructureStrongholdPieces.Stronghold {
      public StairsStraight() {
      }

      public StairsStraight(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
      }

      public static StructureStrongholdPieces.StairsStraight a(
         List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6
      ) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -7, 0, 5, 11, 8, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.StairsStraight(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 4, 10, 7, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 1, 7, 0);
            this.a(var1, var2, var3, StructureStrongholdPieces.Stronghold.Door.a, 1, 1, 7);
            int var4 = this.a(Blocks.aw, 2);

            for (int var5 = 0; var5 < 6; var5++) {
               this.a(var1, Blocks.aw.a(var4), 1, 6 - var5, 1 + var5, var3);
               this.a(var1, Blocks.aw.a(var4), 2, 6 - var5, 1 + var5, var3);
               this.a(var1, Blocks.aw.a(var4), 3, 6 - var5, 1 + var5, var3);
               if (var5 < 5) {
                  this.a(var1, Blocks.bf.Q(), 1, 5 - var5, 1 + var5, var3);
                  this.a(var1, Blocks.bf.Q(), 2, 5 - var5, 1 + var5, var3);
                  this.a(var1, Blocks.bf.Q(), 3, 5 - var5, 1 + var5, var3);
               }
            }

            return true;
         }
      }
   }

   static class Stones extends StructureComponent.BlockSelector {
      private Stones() {
      }

      @Override
      public void a(Random var1, int var2, int var3, int var4, boolean var5) {
         if (var5) {
            float var6 = var1.nextFloat();
            if (var6 < 0.2F) {
               this.a = Blocks.bf.a(BlockStoneBrick.O);
            } else if (var6 < 0.5F) {
               this.a = Blocks.bf.a(BlockStoneBrick.N);
            } else if (var6 < 0.55F) {
               this.a = Blocks.be.a(BlockSilverfish.EnumType.c.a());
            } else {
               this.a = Blocks.bf.Q();
            }
         } else {
            this.a = Blocks.a.Q();
         }
      }
   }

   public static class Straight extends StructureStrongholdPieces.Stronghold {
      private boolean a;
      private boolean b;

      public Straight() {
      }

      public Straight(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.d = this.a(var2);
         this.l = var3;
         this.a = var2.nextInt(2) == 0;
         this.b = var2.nextInt(2) == 0;
      }

      @Override
      protected void a(NBTTagCompound var1) {
         super.a(var1);
         var1.a("Left", this.a);
         var1.a("Right", this.b);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         super.b(var1);
         this.a = var1.n("Left");
         this.b = var1.n("Right");
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         this.a((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 1);
         if (this.a) {
            this.b((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 2);
         }

         if (this.b) {
            this.c((StructureStrongholdPieces.Stairs2)var1, var2, var3, 1, 2);
         }
      }

      public static StructureStrongholdPieces.Straight a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
         StructureBoundingBox var7 = StructureBoundingBox.a(var2, var3, var4, -1, -1, 0, 5, 5, 7, var5);
         return a(var7) && StructureComponent.a(var0, var7) == null ? new StructureStrongholdPieces.Straight(var6, var1, var7, var5) : null;
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 0, 0, 4, 4, 6, true, var2, StructureStrongholdPieces.e);
            this.a(var1, var2, var3, this.d, 1, 1, 0);
            this.a(var1, var2, var3, StructureStrongholdPieces.Stronghold.Door.a, 1, 1, 6);
            this.a(var1, var3, var2, 0.1F, 1, 2, 1, Blocks.aa.Q());
            this.a(var1, var3, var2, 0.1F, 3, 2, 1, Blocks.aa.Q());
            this.a(var1, var3, var2, 0.1F, 1, 2, 5, Blocks.aa.Q());
            this.a(var1, var3, var2, 0.1F, 3, 2, 5, Blocks.aa.Q());
            if (this.a) {
               this.a(var1, var3, 0, 1, 2, 0, 3, 4, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            if (this.b) {
               this.a(var1, var3, 4, 1, 2, 4, 3, 4, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            return true;
         }
      }
   }

   abstract static class Stronghold extends StructureComponent {
      protected StructureStrongholdPieces.Stronghold.Door d = StructureStrongholdPieces.Stronghold.Door.a;

      public Stronghold() {
      }

      protected Stronghold(int var1) {
         super(var1);
      }

      @Override
      protected void a(NBTTagCompound var1) {
         var1.a("EntryDoor", this.d.name());
      }

      @Override
      protected void b(NBTTagCompound var1) {
         this.d = StructureStrongholdPieces.Stronghold.Door.valueOf(var1.j("EntryDoor"));
      }

      protected void a(World var1, Random var2, StructureBoundingBox var3, StructureStrongholdPieces.Stronghold.Door var4, int var5, int var6, int var7) {
         switch (var4) {
            case a:
            default:
               this.a(var1, var3, var5, var6, var7, var5 + 3 - 1, var6 + 3 - 1, var7, Blocks.a.Q(), Blocks.a.Q(), false);
               break;
            case b:
               this.a(var1, Blocks.bf.Q(), var5, var6, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5, var6 + 1, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5, var6 + 2, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5 + 1, var6 + 2, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5 + 2, var6 + 2, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5 + 2, var6 + 1, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5 + 2, var6, var7, var3);
               this.a(var1, Blocks.ao.Q(), var5 + 1, var6, var7, var3);
               this.a(var1, Blocks.ao.a(8), var5 + 1, var6 + 1, var7, var3);
               break;
            case c:
               this.a(var1, Blocks.a.Q(), var5 + 1, var6, var7, var3);
               this.a(var1, Blocks.a.Q(), var5 + 1, var6 + 1, var7, var3);
               this.a(var1, Blocks.bi.Q(), var5, var6, var7, var3);
               this.a(var1, Blocks.bi.Q(), var5, var6 + 1, var7, var3);
               this.a(var1, Blocks.bi.Q(), var5, var6 + 2, var7, var3);
               this.a(var1, Blocks.bi.Q(), var5 + 1, var6 + 2, var7, var3);
               this.a(var1, Blocks.bi.Q(), var5 + 2, var6 + 2, var7, var3);
               this.a(var1, Blocks.bi.Q(), var5 + 2, var6 + 1, var7, var3);
               this.a(var1, Blocks.bi.Q(), var5 + 2, var6, var7, var3);
               break;
            case d:
               this.a(var1, Blocks.bf.Q(), var5, var6, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5, var6 + 1, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5, var6 + 2, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5 + 1, var6 + 2, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5 + 2, var6 + 2, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5 + 2, var6 + 1, var7, var3);
               this.a(var1, Blocks.bf.Q(), var5 + 2, var6, var7, var3);
               this.a(var1, Blocks.aA.Q(), var5 + 1, var6, var7, var3);
               this.a(var1, Blocks.aA.a(8), var5 + 1, var6 + 1, var7, var3);
               this.a(var1, Blocks.aG.a(this.a(Blocks.aG, 4)), var5 + 2, var6 + 1, var7 + 1, var3);
               this.a(var1, Blocks.aG.a(this.a(Blocks.aG, 3)), var5 + 2, var6 + 1, var7 - 1, var3);
         }
      }

      protected StructureStrongholdPieces.Stronghold.Door a(Random var1) {
         int var2 = var1.nextInt(5);
         switch (var2) {
            case 0:
            case 1:
            default:
               return StructureStrongholdPieces.Stronghold.Door.a;
            case 2:
               return StructureStrongholdPieces.Stronghold.Door.b;
            case 3:
               return StructureStrongholdPieces.Stronghold.Door.c;
            case 4:
               return StructureStrongholdPieces.Stronghold.Door.d;
         }
      }

      protected StructureComponent a(StructureStrongholdPieces.Stairs2 var1, List<StructureComponent> var2, Random var3, int var4, int var5) {
         if (this.m != null) {
            switch (this.m) {
               case c:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a + var4, this.l.b + var5, this.l.c - 1, this.m, this.d());
               case d:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a + var4, this.l.b + var5, this.l.f + 1, this.m, this.d());
               case e:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a - 1, this.l.b + var5, this.l.c + var4, this.m, this.d());
               case f:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.d + 1, this.l.b + var5, this.l.c + var4, this.m, this.d());
            }
         }

         return null;
      }

      protected StructureComponent b(StructureStrongholdPieces.Stairs2 var1, List<StructureComponent> var2, Random var3, int var4, int var5) {
         if (this.m != null) {
            switch (this.m) {
               case c:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a - 1, this.l.b + var4, this.l.c + var5, EnumFacing.e, this.d());
               case d:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a - 1, this.l.b + var4, this.l.c + var5, EnumFacing.e, this.d());
               case e:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.c - 1, EnumFacing.c, this.d());
               case f:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.c - 1, EnumFacing.c, this.d());
            }
         }

         return null;
      }

      protected StructureComponent c(StructureStrongholdPieces.Stairs2 var1, List<StructureComponent> var2, Random var3, int var4, int var5) {
         if (this.m != null) {
            switch (this.m) {
               case c:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.d + 1, this.l.b + var4, this.l.c + var5, EnumFacing.f, this.d());
               case d:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.d + 1, this.l.b + var4, this.l.c + var5, EnumFacing.f, this.d());
               case e:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.f + 1, EnumFacing.d, this.d());
               case f:
                  return StructureStrongholdPieces.c(var1, var2, var3, this.l.a + var5, this.l.b + var4, this.l.f + 1, EnumFacing.d, this.d());
            }
         }

         return null;
      }

      protected static boolean a(StructureBoundingBox var0) {
         return var0 != null && var0.b > 10;
      }

      public static enum Door {
         a,
         b,
         c,
         d;
      }
   }
}
