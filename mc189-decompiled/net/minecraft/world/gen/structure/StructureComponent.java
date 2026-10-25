package net.minecraft.world.gen.structure;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockDoor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemDoor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.WeightedRandomChestContent;
import net.minecraft.world.World;

public abstract class StructureComponent {
   protected StructureBoundingBox l;
   protected EnumFacing m;
   protected int n;

   public StructureComponent() {
   }

   protected StructureComponent(int var1) {
      this.n = var1;
   }

   public NBTTagCompound b() {
      NBTTagCompound var1 = new NBTTagCompound();
      var1.a("id", MapGenStructureIO.a(this));
      var1.a("BB", this.l.g());
      var1.a("O", this.m == null ? -1 : this.m.b());
      var1.a("GD", this.n);
      this.a(var1);
      return var1;
   }

   protected abstract void a(NBTTagCompound var1);

   public void a(World var1, NBTTagCompound var2) {
      if (var2.c("BB")) {
         this.l = new StructureBoundingBox(var2.l("BB"));
      }

      int var3 = var2.f("O");
      this.m = var3 == -1 ? null : EnumFacing.b(var3);
      this.n = var2.f("GD");
      this.b(var2);
   }

   protected abstract void b(NBTTagCompound var1);

   public void a(StructureComponent var1, List<StructureComponent> var2, Random var3) {
   }

   public abstract boolean a(World var1, Random var2, StructureBoundingBox var3);

   public StructureBoundingBox c() {
      return this.l;
   }

   public int d() {
      return this.n;
   }

   public static StructureComponent a(List<StructureComponent> var0, StructureBoundingBox var1) {
      for (StructureComponent var3 : var0) {
         if (var3.c() != null && var3.c().a(var1)) {
            return var3;
         }
      }

      return null;
   }

   public BlockPos a() {
      return new BlockPos(this.l.f());
   }

   protected boolean a(World var1, StructureBoundingBox var2) {
      int var3 = Math.max(this.l.a - 1, var2.a);
      int var4 = Math.max(this.l.b - 1, var2.b);
      int var5 = Math.max(this.l.c - 1, var2.c);
      int var6 = Math.min(this.l.d + 1, var2.d);
      int var7 = Math.min(this.l.e + 1, var2.e);
      int var8 = Math.min(this.l.f + 1, var2.f);
      BlockPos.MutableBlockPos var9 = new BlockPos.MutableBlockPos();

      for (int var10 = var3; var10 <= var6; var10++) {
         for (int var11 = var5; var11 <= var8; var11++) {
            if (var1.p(var9.c(var10, var4, var11)).c().t().d()) {
               return true;
            }

            if (var1.p(var9.c(var10, var7, var11)).c().t().d()) {
               return true;
            }
         }
      }

      for (int var12 = var3; var12 <= var6; var12++) {
         for (int var14 = var4; var14 <= var7; var14++) {
            if (var1.p(var9.c(var12, var14, var5)).c().t().d()) {
               return true;
            }

            if (var1.p(var9.c(var12, var14, var8)).c().t().d()) {
               return true;
            }
         }
      }

      for (int var13 = var5; var13 <= var8; var13++) {
         for (int var15 = var4; var15 <= var7; var15++) {
            if (var1.p(var9.c(var3, var15, var13)).c().t().d()) {
               return true;
            }

            if (var1.p(var9.c(var6, var15, var13)).c().t().d()) {
               return true;
            }
         }
      }

      return false;
   }

   protected int a(int var1, int var2) {
      if (this.m == null) {
         return var1;
      } else {
         switch (this.m) {
            case c:
            case d:
               return this.l.a + var1;
            case e:
               return this.l.d - var2;
            case f:
               return this.l.a + var2;
            default:
               return var1;
         }
      }
   }

   protected int d(int var1) {
      return this.m == null ? var1 : var1 + this.l.b;
   }

   protected int b(int var1, int var2) {
      if (this.m == null) {
         return var2;
      } else {
         switch (this.m) {
            case c:
               return this.l.f - var2;
            case d:
               return this.l.c + var2;
            case e:
            case f:
               return this.l.c + var1;
            default:
               return var2;
         }
      }
   }

   protected int a(Block var1, int var2) {
      if (var1 == Blocks.av) {
         if (this.m == EnumFacing.e || this.m == EnumFacing.f) {
            if (var2 == 1) {
               return 0;
            }

            return 1;
         }
      } else if (var1 instanceof BlockDoor) {
         if (this.m == EnumFacing.d) {
            if (var2 == 0) {
               return 2;
            }

            if (var2 == 2) {
               return 0;
            }
         } else {
            if (this.m == EnumFacing.e) {
               return var2 + 1 & 3;
            }

            if (this.m == EnumFacing.f) {
               return var2 + 3 & 3;
            }
         }
      } else if (var1 != Blocks.aw && var1 != Blocks.ad && var1 != Blocks.bA && var1 != Blocks.bv && var1 != Blocks.bO) {
         if (var1 == Blocks.au) {
            if (this.m == EnumFacing.d) {
               if (var2 == EnumFacing.c.a()) {
                  return EnumFacing.d.a();
               }

               if (var2 == EnumFacing.d.a()) {
                  return EnumFacing.c.a();
               }
            } else if (this.m == EnumFacing.e) {
               if (var2 == EnumFacing.c.a()) {
                  return EnumFacing.e.a();
               }

               if (var2 == EnumFacing.d.a()) {
                  return EnumFacing.f.a();
               }

               if (var2 == EnumFacing.e.a()) {
                  return EnumFacing.c.a();
               }

               if (var2 == EnumFacing.f.a()) {
                  return EnumFacing.d.a();
               }
            } else if (this.m == EnumFacing.f) {
               if (var2 == EnumFacing.c.a()) {
                  return EnumFacing.f.a();
               }

               if (var2 == EnumFacing.d.a()) {
                  return EnumFacing.e.a();
               }

               if (var2 == EnumFacing.e.a()) {
                  return EnumFacing.c.a();
               }

               if (var2 == EnumFacing.f.a()) {
                  return EnumFacing.d.a();
               }
            }
         } else if (var1 == Blocks.aG) {
            if (this.m == EnumFacing.d) {
               if (var2 == 3) {
                  return 4;
               }

               if (var2 == 4) {
                  return 3;
               }
            } else if (this.m == EnumFacing.e) {
               if (var2 == 3) {
                  return 1;
               }

               if (var2 == 4) {
                  return 2;
               }

               if (var2 == 2) {
                  return 3;
               }

               if (var2 == 1) {
                  return 4;
               }
            } else if (this.m == EnumFacing.f) {
               if (var2 == 3) {
                  return 2;
               }

               if (var2 == 4) {
                  return 1;
               }

               if (var2 == 2) {
                  return 3;
               }

               if (var2 == 1) {
                  return 4;
               }
            }
         } else if (var1 == Blocks.bR || var1 instanceof BlockDirectional) {
            EnumFacing var3 = EnumFacing.b(var2);
            if (this.m == EnumFacing.d) {
               if (var3 == EnumFacing.d || var3 == EnumFacing.c) {
                  return var3.d().b();
               }
            } else if (this.m == EnumFacing.e) {
               if (var3 == EnumFacing.c) {
                  return EnumFacing.e.b();
               }

               if (var3 == EnumFacing.d) {
                  return EnumFacing.f.b();
               }

               if (var3 == EnumFacing.e) {
                  return EnumFacing.c.b();
               }

               if (var3 == EnumFacing.f) {
                  return EnumFacing.d.b();
               }
            } else if (this.m == EnumFacing.f) {
               if (var3 == EnumFacing.c) {
                  return EnumFacing.f.b();
               }

               if (var3 == EnumFacing.d) {
                  return EnumFacing.e.b();
               }

               if (var3 == EnumFacing.e) {
                  return EnumFacing.c.b();
               }

               if (var3 == EnumFacing.f) {
                  return EnumFacing.d.b();
               }
            }
         } else if (var1 == Blocks.J || var1 == Blocks.F || var1 == Blocks.ay || var1 == Blocks.z) {
            if (this.m == EnumFacing.d) {
               if (var2 == EnumFacing.c.a() || var2 == EnumFacing.d.a()) {
                  return EnumFacing.a(var2).d().a();
               }
            } else if (this.m == EnumFacing.e) {
               if (var2 == EnumFacing.c.a()) {
                  return EnumFacing.e.a();
               }

               if (var2 == EnumFacing.d.a()) {
                  return EnumFacing.f.a();
               }

               if (var2 == EnumFacing.e.a()) {
                  return EnumFacing.c.a();
               }

               if (var2 == EnumFacing.f.a()) {
                  return EnumFacing.d.a();
               }
            } else if (this.m == EnumFacing.f) {
               if (var2 == EnumFacing.c.a()) {
                  return EnumFacing.f.a();
               }

               if (var2 == EnumFacing.d.a()) {
                  return EnumFacing.e.a();
               }

               if (var2 == EnumFacing.e.a()) {
                  return EnumFacing.c.a();
               }

               if (var2 == EnumFacing.f.a()) {
                  return EnumFacing.d.a();
               }
            }
         }
      } else if (this.m == EnumFacing.d) {
         if (var2 == 2) {
            return 3;
         }

         if (var2 == 3) {
            return 2;
         }
      } else if (this.m == EnumFacing.e) {
         if (var2 == 0) {
            return 2;
         }

         if (var2 == 1) {
            return 3;
         }

         if (var2 == 2) {
            return 0;
         }

         if (var2 == 3) {
            return 1;
         }
      } else if (this.m == EnumFacing.f) {
         if (var2 == 0) {
            return 2;
         }

         if (var2 == 1) {
            return 3;
         }

         if (var2 == 2) {
            return 1;
         }

         if (var2 == 3) {
            return 0;
         }
      }

      return var2;
   }

   protected void a(World var1, IBlockState var2, int var3, int var4, int var5, StructureBoundingBox var6) {
      BlockPos var7 = new BlockPos(this.a(var3, var5), this.d(var4), this.b(var3, var5));
      if (var6.b(var7)) {
         var1.a(var7, var2, 2);
      }
   }

   protected IBlockState a(World var1, int var2, int var3, int var4, StructureBoundingBox var5) {
      int var6 = this.a(var2, var4);
      int var7 = this.d(var3);
      int var8 = this.b(var2, var4);
      BlockPos var9 = new BlockPos(var6, var7, var8);
      return !var5.b(var9) ? Blocks.a.Q() : var1.p(var9);
   }

   protected void a(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      for (int var9 = var4; var9 <= var7; var9++) {
         for (int var10 = var3; var10 <= var6; var10++) {
            for (int var11 = var5; var11 <= var8; var11++) {
               this.a(var1, Blocks.a.Q(), var10, var9, var11, var2);
            }
         }
      }
   }

   protected void a(
      World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9, IBlockState var10, boolean var11
   ) {
      for (int var12 = var4; var12 <= var7; var12++) {
         for (int var13 = var3; var13 <= var6; var13++) {
            for (int var14 = var5; var14 <= var8; var14++) {
               if (!var11 || this.a(var1, var13, var12, var14, var2).c().t() != Material.a) {
                  if (var12 != var4 && var12 != var7 && var13 != var3 && var13 != var6 && var14 != var5 && var14 != var8) {
                     this.a(var1, var10, var13, var12, var14, var2);
                  } else {
                     this.a(var1, var9, var13, var12, var14, var2);
                  }
               }
            }
         }
      }
   }

   protected void a(
      World var1,
      StructureBoundingBox var2,
      int var3,
      int var4,
      int var5,
      int var6,
      int var7,
      int var8,
      boolean var9,
      Random var10,
      StructureComponent.BlockSelector var11
   ) {
      for (int var12 = var4; var12 <= var7; var12++) {
         for (int var13 = var3; var13 <= var6; var13++) {
            for (int var14 = var5; var14 <= var8; var14++) {
               if (!var9 || this.a(var1, var13, var12, var14, var2).c().t() != Material.a) {
                  var11.a(var10, var13, var12, var14, var12 == var4 || var12 == var7 || var13 == var3 || var13 == var6 || var14 == var5 || var14 == var8);
                  this.a(var1, var11.a(), var13, var12, var14, var2);
               }
            }
         }
      }
   }

   protected void a(
      World var1,
      StructureBoundingBox var2,
      Random var3,
      float var4,
      int var5,
      int var6,
      int var7,
      int var8,
      int var9,
      int var10,
      IBlockState var11,
      IBlockState var12,
      boolean var13
   ) {
      for (int var14 = var6; var14 <= var9; var14++) {
         for (int var15 = var5; var15 <= var8; var15++) {
            for (int var16 = var7; var16 <= var10; var16++) {
               if (!(var3.nextFloat() > var4) && (!var13 || this.a(var1, var15, var14, var16, var2).c().t() != Material.a)) {
                  if (var14 != var6 && var14 != var9 && var15 != var5 && var15 != var8 && var16 != var7 && var16 != var10) {
                     this.a(var1, var12, var15, var14, var16, var2);
                  } else {
                     this.a(var1, var11, var15, var14, var16, var2);
                  }
               }
            }
         }
      }
   }

   protected void a(World var1, StructureBoundingBox var2, Random var3, float var4, int var5, int var6, int var7, IBlockState var8) {
      if (var3.nextFloat() < var4) {
         this.a(var1, var8, var5, var6, var7, var2);
      }
   }

   protected void a(World var1, StructureBoundingBox var2, int var3, int var4, int var5, int var6, int var7, int var8, IBlockState var9, boolean var10) {
      float var11 = var6 - var3 + 1;
      float var12 = var7 - var4 + 1;
      float var13 = var8 - var5 + 1;
      float var14 = var3 + var11 / 2.0F;
      float var15 = var5 + var13 / 2.0F;

      for (int var16 = var4; var16 <= var7; var16++) {
         float var17 = (var16 - var4) / var12;

         for (int var18 = var3; var18 <= var6; var18++) {
            float var19 = (var18 - var14) / (var11 * 0.5F);

            for (int var20 = var5; var20 <= var8; var20++) {
               float var21 = (var20 - var15) / (var13 * 0.5F);
               if (!var10 || this.a(var1, var18, var16, var20, var2).c().t() != Material.a) {
                  float var22 = var19 * var19 + var17 * var17 + var21 * var21;
                  if (var22 <= 1.05F) {
                     this.a(var1, var9, var18, var16, var20, var2);
                  }
               }
            }
         }
      }
   }

   protected void b(World var1, int var2, int var3, int var4, StructureBoundingBox var5) {
      BlockPos var6 = new BlockPos(this.a(var2, var4), this.d(var3), this.b(var2, var4));
      if (var5.b(var6)) {
         while (!var1.d(var6) && var6.o() < 255) {
            var1.a(var6, Blocks.a.Q(), 2);
            var6 = var6.a();
         }
      }
   }

   protected void b(World var1, IBlockState var2, int var3, int var4, int var5, StructureBoundingBox var6) {
      int var7 = this.a(var3, var5);
      int var8 = this.d(var4);
      int var9 = this.b(var3, var5);
      if (var6.b(new BlockPos(var7, var8, var9))) {
         while ((var1.d(new BlockPos(var7, var8, var9)) || var1.p(new BlockPos(var7, var8, var9)).c().t().d()) && var8 > 1) {
            var1.a(new BlockPos(var7, var8, var9), var2, 2);
            var8--;
         }
      }
   }

   protected boolean a(World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, List<WeightedRandomChestContent> var7, int var8) {
      BlockPos var9 = new BlockPos(this.a(var4, var6), this.d(var5), this.b(var4, var6));
      if (var2.b(var9) && var1.p(var9).c() != Blocks.ae) {
         IBlockState var10 = Blocks.ae.Q();
         var1.a(var9, Blocks.ae.f(var1, var9, var10), 2);
         TileEntity var11 = var1.s(var9);
         if (var11 instanceof TileEntityChest) {
            WeightedRandomChestContent.a(var3, var7, (TileEntityChest)var11, var8);
         }

         return true;
      } else {
         return false;
      }
   }

   protected boolean a(
      World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, int var7, List<WeightedRandomChestContent> var8, int var9
   ) {
      BlockPos var10 = new BlockPos(this.a(var4, var6), this.d(var5), this.b(var4, var6));
      if (var2.b(var10) && var1.p(var10).c() != Blocks.z) {
         var1.a(var10, Blocks.z.a(this.a(Blocks.z, var7)), 2);
         TileEntity var11 = var1.s(var10);
         if (var11 instanceof TileEntityDispenser) {
            WeightedRandomChestContent.a(var3, var8, (TileEntityDispenser)var11, var9);
         }

         return true;
      } else {
         return false;
      }
   }

   protected void a(World var1, StructureBoundingBox var2, Random var3, int var4, int var5, int var6, EnumFacing var7) {
      BlockPos var8 = new BlockPos(this.a(var4, var6), this.d(var5), this.b(var4, var6));
      if (var2.b(var8)) {
         ItemDoor.a(var1, var8, var7.f(), Blocks.ao);
      }
   }

   public void a(int var1, int var2, int var3) {
      this.l.a(var1, var2, var3);
   }

   public abstract static class BlockSelector {
      protected IBlockState a = Blocks.a.Q();

      protected BlockSelector() {
      }

      public abstract void a(Random var1, int var2, int var3, int var4, boolean var5);

      public IBlockState a() {
         return this.a;
      }
   }
}
