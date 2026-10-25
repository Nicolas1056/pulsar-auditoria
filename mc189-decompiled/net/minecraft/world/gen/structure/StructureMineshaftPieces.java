package net.minecraft.world.gen.structure;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityMinecartChest;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public class StructureMineshaftPieces {
   private static final List<WeightedRandomChestContent> a = Lists.newArrayList(
      new WeightedRandomChestContent[]{
         new WeightedRandomChestContent(Items.j, 0, 1, 5, 10),
         new WeightedRandomChestContent(Items.k, 0, 1, 3, 5),
         new WeightedRandomChestContent(Items.aC, 0, 4, 9, 5),
         new WeightedRandomChestContent(Items.aW, EnumDyeColor.l.b(), 4, 9, 5),
         new WeightedRandomChestContent(Items.i, 0, 1, 2, 3),
         new WeightedRandomChestContent(Items.h, 0, 3, 8, 10),
         new WeightedRandomChestContent(Items.P, 0, 1, 3, 15),
         new WeightedRandomChestContent(Items.b, 0, 1, 1, 1),
         new WeightedRandomChestContent(Item.a(Blocks.av), 0, 4, 8, 1),
         new WeightedRandomChestContent(Items.bh, 0, 2, 4, 10),
         new WeightedRandomChestContent(Items.bg, 0, 2, 4, 10),
         new WeightedRandomChestContent(Items.aA, 0, 1, 1, 3),
         new WeightedRandomChestContent(Items.ck, 0, 1, 1, 1)
      }
   );

   public static void a() {
      MapGenStructureIO.a(StructureMineshaftPieces.Corridor.class, "MSCorridor");
      MapGenStructureIO.a(StructureMineshaftPieces.Cross.class, "MSCrossing");
      MapGenStructureIO.a(StructureMineshaftPieces.Room.class, "MSRoom");
      MapGenStructureIO.a(StructureMineshaftPieces.Stairs.class, "MSStairs");
   }

   private static StructureComponent a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5, int var6) {
      int var7 = var1.nextInt(100);
      if (var7 >= 80) {
         StructureBoundingBox var8 = StructureMineshaftPieces.Cross.a(var0, var1, var2, var3, var4, var5);
         if (var8 != null) {
            return new StructureMineshaftPieces.Cross(var6, var1, var8, var5);
         }
      } else if (var7 >= 70) {
         StructureBoundingBox var9 = StructureMineshaftPieces.Stairs.a(var0, var1, var2, var3, var4, var5);
         if (var9 != null) {
            return new StructureMineshaftPieces.Stairs(var6, var1, var9, var5);
         }
      } else {
         StructureBoundingBox var10 = StructureMineshaftPieces.Corridor.a(var0, var1, var2, var3, var4, var5);
         if (var10 != null) {
            return new StructureMineshaftPieces.Corridor(var6, var1, var10, var5);
         }
      }

      return null;
   }

   private static StructureComponent b(
      StructureComponent var0, List<StructureComponent> var1, Random var2, int var3, int var4, int var5, EnumFacing var6, int var7
   ) {
      if (var7 > 8) {
         return null;
      } else if (Math.abs(var3 - var0.c().a) <= 80 && Math.abs(var5 - var0.c().c) <= 80) {
         StructureComponent var8 = a(var1, var2, var3, var4, var5, var6, var7 + 1);
         if (var8 != null) {
            var1.add(var8);
            var8.a(var0, var1, var2);
         }

         return var8;
      } else {
         return null;
      }
   }

   public static class Corridor extends StructureComponent {
      private boolean a;
      private boolean b;
      private boolean c;
      private int d;

      public Corridor() {
      }

      @Override
      protected void a(NBTTagCompound var1) {
         var1.a("hr", this.a);
         var1.a("sc", this.b);
         var1.a("hps", this.c);
         var1.a("Num", this.d);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         this.a = var1.n("hr");
         this.b = var1.n("sc");
         this.c = var1.n("hps");
         this.d = var1.f("Num");
      }

      public Corridor(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
         this.a = var2.nextInt(3) == 0;
         this.b = !this.a && var2.nextInt(23) == 0;
         if (this.m != EnumFacing.c && this.m != EnumFacing.d) {
            this.d = var3.c() / 5;
         } else {
            this.d = var3.e() / 5;
         }
      }

      public static StructureBoundingBox a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         StructureBoundingBox var6 = new StructureBoundingBox(var2, var3, var4, var2, var3 + 2, var4);

         int var7;
         for (var7 = var1.nextInt(3) + 2; var7 > 0; var7--) {
            int var8 = var7 * 5;
            switch (var5) {
               case c:
                  var6.d = var2 + 2;
                  var6.c = var4 - (var8 - 1);
                  break;
               case d:
                  var6.d = var2 + 2;
                  var6.f = var4 + (var8 - 1);
                  break;
               case e:
                  var6.a = var2 - (var8 - 1);
                  var6.f = var4 + 2;
                  break;
               case f:
                  var6.d = var2 + (var8 - 1);
                  var6.f = var4 + 2;
            }

            if (StructureComponent.a(var0, var6) == null) {
               break;
            }
         }

         return var7 > 0 ? var6 : null;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         int var4 = this.d();
         int var5 = var3.nextInt(4);
         if (this.m != null) {
            switch (this.m) {
               case c:
                  if (var5 <= 1) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.a, this.l.b - 1 + var3.nextInt(3), this.l.c - 1, this.m, var4);
                  } else if (var5 == 2) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b - 1 + var3.nextInt(3), this.l.c, EnumFacing.e, var4);
                  } else {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b - 1 + var3.nextInt(3), this.l.c, EnumFacing.f, var4);
                  }
                  break;
               case d:
                  if (var5 <= 1) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.a, this.l.b - 1 + var3.nextInt(3), this.l.f + 1, this.m, var4);
                  } else if (var5 == 2) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b - 1 + var3.nextInt(3), this.l.f - 3, EnumFacing.e, var4);
                  } else {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b - 1 + var3.nextInt(3), this.l.f - 3, EnumFacing.f, var4);
                  }
                  break;
               case e:
                  if (var5 <= 1) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b - 1 + var3.nextInt(3), this.l.c, this.m, var4);
                  } else if (var5 == 2) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.a, this.l.b - 1 + var3.nextInt(3), this.l.c - 1, EnumFacing.c, var4);
                  } else {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.a, this.l.b - 1 + var3.nextInt(3), this.l.f + 1, EnumFacing.d, var4);
                  }
                  break;
               case f:
                  if (var5 <= 1) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b - 1 + var3.nextInt(3), this.l.c, this.m, var4);
                  } else if (var5 == 2) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.d - 3, this.l.b - 1 + var3.nextInt(3), this.l.c - 1, EnumFacing.c, var4);
                  } else {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.d - 3, this.l.b - 1 + var3.nextInt(3), this.l.f + 1, EnumFacing.d, var4);
                  }
            }
         }

         if (var4 < 8) {
            if (this.m != EnumFacing.c && this.m != EnumFacing.d) {
               for (int var8 = this.l.a + 3; var8 + 3 <= this.l.d; var8 += 5) {
                  int var9 = var3.nextInt(5);
                  if (var9 == 0) {
                     StructureMineshaftPieces.b(var1, var2, var3, var8, this.l.b, this.l.c - 1, EnumFacing.c, var4 + 1);
                  } else if (var9 == 1) {
                     StructureMineshaftPieces.b(var1, var2, var3, var8, this.l.b, this.l.f + 1, EnumFacing.d, var4 + 1);
                  }
               }
            } else {
               for (int var6 = this.l.c + 3; var6 + 3 <= this.l.f; var6 += 5) {
                  int var7 = var3.nextInt(5);
                  if (var7 == 0) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b, var6, EnumFacing.e, var4 + 1);
                  } else if (var7 == 1) {
                     StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b, var6, EnumFacing.f, var4 + 1);
                  }
               }
            }
         }
      }

      @Override
      protected boolean a(World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, List<WeightedRandomChestContent> var7, int var8) {
         BlockPos var9 = new BlockPos(this.a(var4, var6), this.d(var5), this.b(var4, var6));
         if (var2.b(var9) && var1.p(var9).c().t() == Material.a) {
            int var10 = var3.nextBoolean() ? 1 : 0;
            var1.a(var9, Blocks.av.a(this.a(Blocks.av, var10)), 2);
            EntityMinecartChest var11 = new EntityMinecartChest(var1, var9.n() + 0.5F, var9.o() + 0.5F, var9.p() + 0.5F);
            WeightedRandomChestContent.a(var3, var7, var11, var8);
            var1.d(var11);
            return true;
         } else {
            return false;
         }
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            boolean var4 = false;
            byte var5 = 2;
            boolean var6 = false;
            byte var7 = 2;
            int var8 = this.d * 5 - 1;
            this.a(var1, var3, 0, 0, 0, 2, 1, var8, Blocks.a.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, var2, 0.8F, 0, 2, 0, 2, 2, var8, Blocks.a.Q(), Blocks.a.Q(), false);
            if (this.b) {
               this.a(var1, var3, var2, 0.6F, 0, 0, 0, 2, 1, var8, Blocks.G.Q(), Blocks.a.Q(), false);
            }

            for (int var9 = 0; var9 < this.d; var9++) {
               int var10 = 2 + var9 * 5;
               this.a(var1, var3, 0, 0, var10, 0, 1, var10, Blocks.aO.Q(), Blocks.a.Q(), false);
               this.a(var1, var3, 2, 0, var10, 2, 1, var10, Blocks.aO.Q(), Blocks.a.Q(), false);
               if (var2.nextInt(4) == 0) {
                  this.a(var1, var3, 0, 2, var10, 0, 2, var10, Blocks.f.Q(), Blocks.a.Q(), false);
                  this.a(var1, var3, 2, 2, var10, 2, 2, var10, Blocks.f.Q(), Blocks.a.Q(), false);
               } else {
                  this.a(var1, var3, 0, 2, var10, 2, 2, var10, Blocks.f.Q(), Blocks.a.Q(), false);
               }

               this.a(var1, var3, var2, 0.1F, 0, 2, var10 - 1, Blocks.G.Q());
               this.a(var1, var3, var2, 0.1F, 2, 2, var10 - 1, Blocks.G.Q());
               this.a(var1, var3, var2, 0.1F, 0, 2, var10 + 1, Blocks.G.Q());
               this.a(var1, var3, var2, 0.1F, 2, 2, var10 + 1, Blocks.G.Q());
               this.a(var1, var3, var2, 0.05F, 0, 2, var10 - 2, Blocks.G.Q());
               this.a(var1, var3, var2, 0.05F, 2, 2, var10 - 2, Blocks.G.Q());
               this.a(var1, var3, var2, 0.05F, 0, 2, var10 + 2, Blocks.G.Q());
               this.a(var1, var3, var2, 0.05F, 2, 2, var10 + 2, Blocks.G.Q());
               this.a(var1, var3, var2, 0.05F, 1, 2, var10 - 1, Blocks.aa.a(EnumFacing.b.a()));
               this.a(var1, var3, var2, 0.05F, 1, 2, var10 + 1, Blocks.aa.a(EnumFacing.b.a()));
               if (var2.nextInt(100) == 0) {
                  this.a(var1, var3, var2, 2, 0, var10 - 1, WeightedRandomChestContent.a(StructureMineshaftPieces.a, Items.cd.b(var2)), 3 + var2.nextInt(4));
               }

               if (var2.nextInt(100) == 0) {
                  this.a(var1, var3, var2, 0, 0, var10 + 1, WeightedRandomChestContent.a(StructureMineshaftPieces.a, Items.cd.b(var2)), 3 + var2.nextInt(4));
               }

               if (this.b && !this.c) {
                  int var11 = this.d(0);
                  int var12 = var10 - 1 + var2.nextInt(3);
                  int var13 = this.a(1, var12);
                  var12 = this.b(1, var12);
                  BlockPos var14 = new BlockPos(var13, var11, var12);
                  if (var3.b(var14)) {
                     this.c = true;
                     var1.a(var14, Blocks.ac.Q(), 2);
                     TileEntity var15 = var1.s(var14);
                     if (var15 instanceof TileEntityMobSpawner) {
                        ((TileEntityMobSpawner)var15).b().a("CaveSpider");
                     }
                  }
               }
            }

            for (int var16 = 0; var16 <= 2; var16++) {
               for (int var18 = 0; var18 <= var8; var18++) {
                  byte var20 = -1;
                  IBlockState var22 = this.a(var1, var16, var20, var18, var3);
                  if (var22.c().t() == Material.a) {
                     byte var23 = -1;
                     this.a(var1, Blocks.f.Q(), var16, var23, var18, var3);
                  }
               }
            }

            if (this.a) {
               for (int var17 = 0; var17 <= var8; var17++) {
                  IBlockState var19 = this.a(var1, 1, -1, var17, var3);
                  if (var19.c().t() != Material.a && var19.c().o()) {
                     this.a(var1, var3, var2, 0.7F, 1, 0, var17, Blocks.av.a(this.a(Blocks.av, 0)));
                  }
               }
            }

            return true;
         }
      }
   }

   public static class Cross extends StructureComponent {
      private EnumFacing a;
      private boolean b;

      public Cross() {
      }

      @Override
      protected void a(NBTTagCompound var1) {
         var1.a("tf", this.b);
         var1.a("D", this.a.b());
      }

      @Override
      protected void b(NBTTagCompound var1) {
         this.b = var1.n("tf");
         this.a = EnumFacing.b(var1.f("D"));
      }

      public Cross(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.a = var4;
         this.l = var3;
         this.b = var3.d() > 3;
      }

      public static StructureBoundingBox a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         StructureBoundingBox var6 = new StructureBoundingBox(var2, var3, var4, var2, var3 + 2, var4);
         if (var1.nextInt(4) == 0) {
            var6.e += 4;
         }

         switch (var5) {
            case c:
               var6.a = var2 - 1;
               var6.d = var2 + 3;
               var6.c = var4 - 4;
               break;
            case d:
               var6.a = var2 - 1;
               var6.d = var2 + 3;
               var6.f = var4 + 4;
               break;
            case e:
               var6.a = var2 - 4;
               var6.c = var4 - 1;
               var6.f = var4 + 3;
               break;
            case f:
               var6.d = var2 + 4;
               var6.c = var4 - 1;
               var6.f = var4 + 3;
         }

         return StructureComponent.a(var0, var6) != null ? null : var6;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         int var4 = this.d();
         switch (this.a) {
            case c:
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a + 1, this.l.b, this.l.c - 1, EnumFacing.c, var4);
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b, this.l.c + 1, EnumFacing.e, var4);
               StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b, this.l.c + 1, EnumFacing.f, var4);
               break;
            case d:
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a + 1, this.l.b, this.l.f + 1, EnumFacing.d, var4);
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b, this.l.c + 1, EnumFacing.e, var4);
               StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b, this.l.c + 1, EnumFacing.f, var4);
               break;
            case e:
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a + 1, this.l.b, this.l.c - 1, EnumFacing.c, var4);
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a + 1, this.l.b, this.l.f + 1, EnumFacing.d, var4);
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b, this.l.c + 1, EnumFacing.e, var4);
               break;
            case f:
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a + 1, this.l.b, this.l.c - 1, EnumFacing.c, var4);
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a + 1, this.l.b, this.l.f + 1, EnumFacing.d, var4);
               StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b, this.l.c + 1, EnumFacing.f, var4);
         }

         if (this.b) {
            if (var3.nextBoolean()) {
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a + 1, this.l.b + 3 + 1, this.l.c - 1, EnumFacing.c, var4);
            }

            if (var3.nextBoolean()) {
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b + 3 + 1, this.l.c + 1, EnumFacing.e, var4);
            }

            if (var3.nextBoolean()) {
               StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b + 3 + 1, this.l.c + 1, EnumFacing.f, var4);
            }

            if (var3.nextBoolean()) {
               StructureMineshaftPieces.b(var1, var2, var3, this.l.a + 1, this.l.b + 3 + 1, this.l.f + 1, EnumFacing.d, var4);
            }
         }
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            if (this.b) {
               this.a(var1, var3, this.l.a + 1, this.l.b, this.l.c, this.l.d - 1, this.l.b + 3 - 1, this.l.f, Blocks.a.Q(), Blocks.a.Q(), false);
               this.a(var1, var3, this.l.a, this.l.b, this.l.c + 1, this.l.d, this.l.b + 3 - 1, this.l.f - 1, Blocks.a.Q(), Blocks.a.Q(), false);
               this.a(var1, var3, this.l.a + 1, this.l.e - 2, this.l.c, this.l.d - 1, this.l.e, this.l.f, Blocks.a.Q(), Blocks.a.Q(), false);
               this.a(var1, var3, this.l.a, this.l.e - 2, this.l.c + 1, this.l.d, this.l.e, this.l.f - 1, Blocks.a.Q(), Blocks.a.Q(), false);
               this.a(var1, var3, this.l.a + 1, this.l.b + 3, this.l.c + 1, this.l.d - 1, this.l.b + 3, this.l.f - 1, Blocks.a.Q(), Blocks.a.Q(), false);
            } else {
               this.a(var1, var3, this.l.a + 1, this.l.b, this.l.c, this.l.d - 1, this.l.e, this.l.f, Blocks.a.Q(), Blocks.a.Q(), false);
               this.a(var1, var3, this.l.a, this.l.b, this.l.c + 1, this.l.d, this.l.e, this.l.f - 1, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            this.a(var1, var3, this.l.a + 1, this.l.b, this.l.c + 1, this.l.a + 1, this.l.e, this.l.c + 1, Blocks.f.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, this.l.a + 1, this.l.b, this.l.f - 1, this.l.a + 1, this.l.e, this.l.f - 1, Blocks.f.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, this.l.d - 1, this.l.b, this.l.c + 1, this.l.d - 1, this.l.e, this.l.c + 1, Blocks.f.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, this.l.d - 1, this.l.b, this.l.f - 1, this.l.d - 1, this.l.e, this.l.f - 1, Blocks.f.Q(), Blocks.a.Q(), false);

            for (int var4 = this.l.a; var4 <= this.l.d; var4++) {
               for (int var5 = this.l.c; var5 <= this.l.f; var5++) {
                  if (this.a(var1, var4, this.l.b - 1, var5, var3).c().t() == Material.a) {
                     this.a(var1, Blocks.f.Q(), var4, this.l.b - 1, var5, var3);
                  }
               }
            }

            return true;
         }
      }
   }

   public static class Room extends StructureComponent {
      private List<StructureBoundingBox> a = Lists.newLinkedList();

      public Room() {
      }

      public Room(int var1, Random var2, int var3, int var4) {
         super(var1);
         this.l = new StructureBoundingBox(var3, 50, var4, var3 + 7 + var2.nextInt(6), 54 + var2.nextInt(6), var4 + 7 + var2.nextInt(6));
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         int var4 = this.d();
         int var6 = this.l.d() - 3 - 1;
         if (var6 <= 0) {
            var6 = 1;
         }

         int var5 = 0;

         while (var5 < this.l.c()) {
            var5 += var3.nextInt(this.l.c());
            if (var5 + 3 > this.l.c()) {
               break;
            }

            StructureComponent var7 = StructureMineshaftPieces.b(
               var1, var2, var3, this.l.a + var5, this.l.b + var3.nextInt(var6) + 1, this.l.c - 1, EnumFacing.c, var4
            );
            if (var7 != null) {
               StructureBoundingBox var8 = var7.c();
               this.a.add(new StructureBoundingBox(var8.a, var8.b, this.l.c, var8.d, var8.e, this.l.c + 1));
            }

            var5 += 4;
         }

         var5 = 0;

         while (var5 < this.l.c()) {
            var5 += var3.nextInt(this.l.c());
            if (var5 + 3 > this.l.c()) {
               break;
            }

            StructureComponent var16 = StructureMineshaftPieces.b(
               var1, var2, var3, this.l.a + var5, this.l.b + var3.nextInt(var6) + 1, this.l.f + 1, EnumFacing.d, var4
            );
            if (var16 != null) {
               StructureBoundingBox var19 = var16.c();
               this.a.add(new StructureBoundingBox(var19.a, var19.b, this.l.f - 1, var19.d, var19.e, this.l.f));
            }

            var5 += 4;
         }

         var5 = 0;

         while (var5 < this.l.e()) {
            var5 += var3.nextInt(this.l.e());
            if (var5 + 3 > this.l.e()) {
               break;
            }

            StructureComponent var17 = StructureMineshaftPieces.b(
               var1, var2, var3, this.l.a - 1, this.l.b + var3.nextInt(var6) + 1, this.l.c + var5, EnumFacing.e, var4
            );
            if (var17 != null) {
               StructureBoundingBox var20 = var17.c();
               this.a.add(new StructureBoundingBox(this.l.a, var20.b, var20.c, this.l.a + 1, var20.e, var20.f));
            }

            var5 += 4;
         }

         var5 = 0;

         while (var5 < this.l.e()) {
            var5 += var3.nextInt(this.l.e());
            if (var5 + 3 > this.l.e()) {
               break;
            }

            StructureComponent var18 = StructureMineshaftPieces.b(
               var1, var2, var3, this.l.d + 1, this.l.b + var3.nextInt(var6) + 1, this.l.c + var5, EnumFacing.f, var4
            );
            if (var18 != null) {
               StructureBoundingBox var21 = var18.c();
               this.a.add(new StructureBoundingBox(this.l.d - 1, var21.b, var21.c, this.l.d, var21.e, var21.f));
            }

            var5 += 4;
         }
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, this.l.a, this.l.b, this.l.c, this.l.d, this.l.b, this.l.f, Blocks.d.Q(), Blocks.a.Q(), true);
            this.a(var1, var3, this.l.a, this.l.b + 1, this.l.c, this.l.d, Math.min(this.l.b + 3, this.l.e), this.l.f, Blocks.a.Q(), Blocks.a.Q(), false);

            for (StructureBoundingBox var5 : this.a) {
               this.a(var1, var3, var5.a, var5.e - 2, var5.c, var5.d, var5.e, var5.f, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            this.a(var1, var3, this.l.a, this.l.b + 4, this.l.c, this.l.d, this.l.e, this.l.f, Blocks.a.Q(), false);
            return true;
         }
      }

      @Override
      public void a(int var1, int var2, int var3) {
         super.a(var1, var2, var3);

         for (StructureBoundingBox var5 : this.a) {
            var5.a(var1, var2, var3);
         }
      }

      @Override
      protected void a(NBTTagCompound var1) {
         NBTTagList var2 = new NBTTagList();

         for (StructureBoundingBox var4 : this.a) {
            var2.a(var4.g());
         }

         var1.a("Entrances", var2);
      }

      @Override
      protected void b(NBTTagCompound var1) {
         NBTTagList var2 = var1.c("Entrances", 11);

         for (int var3 = 0; var3 < var2.c(); var3++) {
            this.a.add(new StructureBoundingBox(var2.c(var3)));
         }
      }
   }

   public static class Stairs extends StructureComponent {
      public Stairs() {
      }

      public Stairs(int var1, Random var2, StructureBoundingBox var3, EnumFacing var4) {
         super(var1);
         this.m = var4;
         this.l = var3;
      }

      @Override
      protected void a(NBTTagCompound var1) {
      }

      @Override
      protected void b(NBTTagCompound var1) {
      }

      public static StructureBoundingBox a(List<StructureComponent> var0, Random var1, int var2, int var3, int var4, EnumFacing var5) {
         StructureBoundingBox var6 = new StructureBoundingBox(var2, var3 - 5, var4, var2, var3 + 2, var4);
         switch (var5) {
            case c:
               var6.d = var2 + 2;
               var6.c = var4 - 8;
               break;
            case d:
               var6.d = var2 + 2;
               var6.f = var4 + 8;
               break;
            case e:
               var6.a = var2 - 8;
               var6.f = var4 + 2;
               break;
            case f:
               var6.d = var2 + 8;
               var6.f = var4 + 2;
         }

         return StructureComponent.a(var0, var6) != null ? null : var6;
      }

      @Override
      public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
         int var4 = this.d();
         if (this.m != null) {
            switch (this.m) {
               case c:
                  StructureMineshaftPieces.b(var1, var2, var3, this.l.a, this.l.b, this.l.c - 1, EnumFacing.c, var4);
                  break;
               case d:
                  StructureMineshaftPieces.b(var1, var2, var3, this.l.a, this.l.b, this.l.f + 1, EnumFacing.d, var4);
                  break;
               case e:
                  StructureMineshaftPieces.b(var1, var2, var3, this.l.a - 1, this.l.b, this.l.c, EnumFacing.e, var4);
                  break;
               case f:
                  StructureMineshaftPieces.b(var1, var2, var3, this.l.d + 1, this.l.b, this.l.c, EnumFacing.f, var4);
            }
         }
      }

      @Override
      public boolean a(World var1, Random var2, StructureBoundingBox var3) {
         if (this.a(var1, var3)) {
            return false;
         } else {
            this.a(var1, var3, 0, 5, 0, 2, 7, 1, Blocks.a.Q(), Blocks.a.Q(), false);
            this.a(var1, var3, 0, 0, 7, 2, 2, 8, Blocks.a.Q(), Blocks.a.Q(), false);

            for (int var4 = 0; var4 < 5; var4++) {
               this.a(var1, var3, 0, 5 - var4 - (var4 < 4 ? 1 : 0), 2 + var4, 2, 7 - var4, 2 + var4, Blocks.a.Q(), Blocks.a.Q(), false);
            }

            return true;
         }
      }
   }
}
