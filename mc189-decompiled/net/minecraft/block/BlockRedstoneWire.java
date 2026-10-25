package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneWire extends Block {
   public static final PropertyEnum<BlockRedstoneWire.EnumAttachPosition> a = PropertyEnum.a("north", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyEnum<BlockRedstoneWire.EnumAttachPosition> b = PropertyEnum.a("east", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyEnum<BlockRedstoneWire.EnumAttachPosition> N = PropertyEnum.a("south", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyEnum<BlockRedstoneWire.EnumAttachPosition> O = PropertyEnum.a("west", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyInteger P = PropertyInteger.a("power", 0, 15);
   private boolean Q = true;
   private final Set<BlockPos> R = Sets.newHashSet();

   public BlockRedstoneWire() {
      super(Material.q);
      this.j(
         this.M
            .b()
            .a(a, BlockRedstoneWire.EnumAttachPosition.c)
            .a(b, BlockRedstoneWire.EnumAttachPosition.c)
            .a(N, BlockRedstoneWire.EnumAttachPosition.c)
            .a(O, BlockRedstoneWire.EnumAttachPosition.c)
            .a(P, 0)
      );
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      var1 = var1.a(O, this.c(var2, var3, EnumFacing.e));
      var1 = var1.a(b, this.c(var2, var3, EnumFacing.f));
      var1 = var1.a(a, this.c(var2, var3, EnumFacing.c));
      return var1.a(N, this.c(var2, var3, EnumFacing.d));
   }

   private BlockRedstoneWire.EnumAttachPosition c(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.a(var3);
      Block var5 = var1.p(var2.a(var3)).c();
      if (!a(var1.p(var4), var3) && (var5.u() || !d(var1.p(var4.b())))) {
         Block var6 = var1.p(var2.a()).c();
         return !var6.u() && var5.u() && d(var1.p(var4.a())) ? BlockRedstoneWire.EnumAttachPosition.a : BlockRedstoneWire.EnumAttachPosition.c;
      } else {
         return BlockRedstoneWire.EnumAttachPosition.b;
      }
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      IBlockState var4 = var1.p(var2);
      return var4.c() != this ? super.a(var1, var2, var3) : this.b(var4.b(P));
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return World.a(var1, var2.b()) || var1.p(var2.b()).c() == Blocks.aX;
   }

   private IBlockState e(World var1, BlockPos var2, IBlockState var3) {
      var3 = this.a(var1, var2, var2, var3);
      ArrayList var4 = Lists.newArrayList(this.R);
      this.R.clear();

      for (BlockPos var6 : var4) {
         var1.c(var6, this);
      }

      return var3;
   }

   private IBlockState a(World var1, BlockPos var2, BlockPos var3, IBlockState var4) {
      IBlockState var5 = var4;
      int var6 = var4.b(P);
      int var7 = 0;
      var7 = this.a(var1, var3, var7);
      this.Q = false;
      int var8 = var1.A(var2);
      this.Q = true;
      if (var8 > 0 && var8 > var7 - 1) {
         var7 = var8;
      }

      int var9 = 0;

      for (EnumFacing var11 : EnumFacing.Plane.a) {
         BlockPos var12 = var2.a(var11);
         boolean var13 = var12.n() != var3.n() || var12.p() != var3.p();
         if (var13) {
            var9 = this.a(var1, var12, var9);
         }

         if (var1.p(var12).c().v() && !var1.p(var2.a()).c().v()) {
            if (var13 && var2.o() >= var3.o()) {
               var9 = this.a(var1, var12.a(), var9);
            }
         } else if (!var1.p(var12).c().v() && var13 && var2.o() <= var3.o()) {
            var9 = this.a(var1, var12.b(), var9);
         }
      }

      if (var9 > var7) {
         var7 = var9 - 1;
      } else if (var7 > 0) {
         var7--;
      } else {
         var7 = 0;
      }

      if (var8 > var7 - 1) {
         var7 = var8;
      }

      if (var6 != var7) {
         var4 = var4.a(P, var7);
         if (var1.p(var2) == var5) {
            var1.a(var2, var4, 2);
         }

         this.R.add(var2);

         for (EnumFacing var19 : EnumFacing.values()) {
            this.R.add(var2.a(var19));
         }
      }

      return var4;
   }

   private void e(World var1, BlockPos var2) {
      if (var1.p(var2).c() == this) {
         var1.c(var2, this);

         for (EnumFacing var6 : EnumFacing.values()) {
            var1.c(var2.a(var6), this);
         }
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         this.e(var1, var2, var3);

         for (EnumFacing var5 : EnumFacing.Plane.b) {
            var1.c(var2.a(var5), this);
         }

         for (EnumFacing var9 : EnumFacing.Plane.a) {
            this.e(var1, var2.a(var9));
         }

         for (EnumFacing var10 : EnumFacing.Plane.a) {
            BlockPos var6 = var2.a(var10);
            if (var1.p(var6).c().v()) {
               this.e(var1, var6.a());
            } else {
               this.e(var1, var6.b());
            }
         }
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      super.b(var1, var2, var3);
      if (!var1.D) {
         for (EnumFacing var7 : EnumFacing.values()) {
            var1.c(var2.a(var7), this);
         }

         this.e(var1, var2, var3);

         for (EnumFacing var10 : EnumFacing.Plane.a) {
            this.e(var1, var2.a(var10));
         }

         for (EnumFacing var11 : EnumFacing.Plane.a) {
            BlockPos var12 = var2.a(var11);
            if (var1.p(var12).c().v()) {
               this.e(var1, var12.a());
            } else {
               this.e(var1, var12.b());
            }
         }
      }
   }

   private int a(World var1, BlockPos var2, int var3) {
      if (var1.p(var2).c() != this) {
         return var3;
      } else {
         int var4 = var1.p(var2).b(P);
         return var4 > var3 ? var4 : var3;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D) {
         if (this.d(var1, var2)) {
            this.e(var1, var2, var3);
         } else {
            this.b(var1, var2, var3, 0);
            var1.g(var2);
         }
      }
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.aC;
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !this.Q ? 0 : this.a(var1, var2, var3, var4);
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!this.Q) {
         return 0;
      } else {
         int var5 = var3.b(P);
         if (var5 == 0) {
            return 0;
         } else if (var4 == EnumFacing.b) {
            return var5;
         } else {
            EnumSet var6 = EnumSet.noneOf(EnumFacing.class);

            for (EnumFacing var8 : EnumFacing.Plane.a) {
               if (this.d(var1, var2, var8)) {
                  var6.add(var8);
               }
            }

            if (var4.k().c() && var6.isEmpty()) {
               return var5;
            } else {
               return var6.contains(var4) && !var6.contains(var4.f()) && !var6.contains(var4.e()) ? var5 : 0;
            }
         }
      }
   }

   private boolean d(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.a(var3);
      IBlockState var5 = var1.p(var4);
      Block var6 = var5.c();
      boolean var7 = var6.v();
      boolean var8 = var1.p(var2.a()).c().v();
      if (!var8 && var7 && e(var1, var4.a())) {
         return true;
      } else if (a(var5, var3)) {
         return true;
      } else {
         return var6 == Blocks.bc && var5.b(BlockRedstoneDiode.O) == var3 ? true : !var7 && e(var1, var4.b());
      }
   }

   protected static boolean e(IBlockAccess var0, BlockPos var1) {
      return d(var0.p(var1));
   }

   protected static boolean d(IBlockState var0) {
      return a(var0, null);
   }

   protected static boolean a(IBlockState var0, EnumFacing var1) {
      Block var2 = var0.c();
      if (var2 == Blocks.af) {
         return true;
      } else if (Blocks.bb.e(var2)) {
         EnumFacing var3 = var0.b(BlockRedstoneRepeater.O);
         return var3 == var1 || var3.d() == var1;
      } else {
         return var2.i() && var1 != null;
      }
   }

   @Override
   public boolean i() {
      return this.Q;
   }

   private int b(int var1) {
      float var2 = var1 / 15.0F;
      float var3 = var2 * 0.6F + 0.4F;
      if (var1 == 0) {
         var3 = 0.3F;
      }

      float var4 = var2 * var2 * 0.7F - 0.5F;
      float var5 = var2 * var2 * 0.6F - 0.7F;
      if (var4 < 0.0F) {
         var4 = 0.0F;
      }

      if (var5 < 0.0F) {
         var5 = 0.0F;
      }

      int var6 = MathHelper.a((int)(var3 * 255.0F), 0, 255);
      int var7 = MathHelper.a((int)(var4 * 255.0F), 0, 255);
      int var8 = MathHelper.a((int)(var5 * 255.0F), 0, 255);
      return 0xFF000000 | var6 << 16 | var7 << 8 | var8;
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = var3.b(P);
      if (var5 != 0) {
         double var6 = var2.n() + 0.5 + (var4.nextFloat() - 0.5) * 0.2;
         double var8 = var2.o() + 0.0625F;
         double var10 = var2.p() + 0.5 + (var4.nextFloat() - 0.5) * 0.2;
         float var12 = var5 / 15.0F;
         float var13 = var12 * 0.6F + 0.4F;
         float var14 = Math.max(0.0F, var12 * var12 * 0.7F - 0.5F);
         float var15 = Math.max(0.0F, var12 * var12 * 0.6F - 0.7F);
         var1.a(EnumParticleTypes.E, var6, var8, var10, var13, var14, var15);
      }
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.aC;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(P, var1);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(P);
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, N, O, P);
   }

   static enum EnumAttachPosition implements IStringSerializable {
      a("up"),
      b("side"),
      c("none");

      private final String d;

      private EnumAttachPosition(String var3) {
         this.d = var3;
      }

      @Override
      public String toString() {
         return this.l();
      }

      @Override
      public String l() {
         return this.d;
      }
   }
}
