package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRailBase extends Block {
   protected final boolean a;

   public static boolean e(World var0, BlockPos var1) {
      return d(var0.p(var1));
   }

   public static boolean d(IBlockState var0) {
      Block var1 = var0.c();
      return var1 == Blocks.av || var1 == Blocks.D || var1 == Blocks.E || var1 == Blocks.cs;
   }

   protected BlockRailBase(boolean var1) {
      super(Material.q);
      this.a = var1;
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.a(CreativeTabs.e);
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
   public MovingObjectPosition a(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.a(var1, var2);
      return super.a(var1, var2, var3, var4);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      BlockRailBase.EnumRailDirection var4 = var3.c() == this ? var3.b(this.n()) : null;
      if (var4 != null && var4.c()) {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      }
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return World.a(var1, var2.b());
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         var3 = this.a(var1, var2, var3, true);
         if (this.a) {
            this.a(var1, var2, var3, this);
         }
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D) {
         BlockRailBase.EnumRailDirection var5 = var3.b(this.n());
         boolean var6 = false;
         if (!World.a(var1, var2.b())) {
            var6 = true;
         }

         if (var5 == BlockRailBase.EnumRailDirection.c && !World.a(var1, var2.f())) {
            var6 = true;
         } else if (var5 == BlockRailBase.EnumRailDirection.d && !World.a(var1, var2.e())) {
            var6 = true;
         } else if (var5 == BlockRailBase.EnumRailDirection.e && !World.a(var1, var2.c())) {
            var6 = true;
         } else if (var5 == BlockRailBase.EnumRailDirection.f && !World.a(var1, var2.d())) {
            var6 = true;
         }

         if (var6) {
            this.b(var1, var2, var3, 0);
            var1.g(var2);
         } else {
            this.b(var1, var2, var3, var4);
         }
      }
   }

   protected void b(World var1, BlockPos var2, IBlockState var3, Block var4) {
   }

   protected IBlockState a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var1.D ? var3 : new BlockRailBase.Rail(var1, var2, var3).a(var1.z(var2), var4).b();
   }

   @Override
   public int k() {
      return 0;
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      super.b(var1, var2, var3);
      if (var3.b(this.n()).c()) {
         var1.c(var2.a(), this);
      }

      if (this.a) {
         var1.c(var2, this);
         var1.c(var2.b(), this);
      }
   }

   public abstract IProperty<BlockRailBase.EnumRailDirection> n();

   public static enum EnumRailDirection implements IStringSerializable {
      a(0, "north_south"),
      b(1, "east_west"),
      c(2, "ascending_east"),
      d(3, "ascending_west"),
      e(4, "ascending_north"),
      f(5, "ascending_south"),
      g(6, "south_east"),
      h(7, "south_west"),
      i(8, "north_west"),
      j(9, "north_east");

      private static final BlockRailBase.EnumRailDirection[] k = new BlockRailBase.EnumRailDirection[values().length];
      private final int l;
      private final String m;

      private EnumRailDirection(int var3, String var4) {
         this.l = var3;
         this.m = var4;
      }

      public int a() {
         return this.l;
      }

      @Override
      public String toString() {
         return this.m;
      }

      public boolean c() {
         return this == e || this == c || this == f || this == d;
      }

      public static BlockRailBase.EnumRailDirection a(int var0) {
         if (var0 < 0 || var0 >= k.length) {
            var0 = 0;
         }

         return k[var0];
      }

      @Override
      public String l() {
         return this.m;
      }

      static {
         for (BlockRailBase.EnumRailDirection var3 : values()) {
            k[var3.a()] = var3;
         }
      }
   }

   public class Rail {
      private final World b;
      private final BlockPos c;
      private final BlockRailBase d;
      private IBlockState e;
      private final boolean f;
      private final List<BlockPos> g = Lists.newArrayList();

      public Rail(World var2, BlockPos var3, IBlockState var4) {
         this.b = var2;
         this.c = var3;
         this.e = var4;
         this.d = (BlockRailBase)var4.c();
         BlockRailBase.EnumRailDirection var5 = var4.b(BlockRailBase.this.n());
         this.f = this.d.a;
         this.a(var5);
      }

      private void a(BlockRailBase.EnumRailDirection var1) {
         this.g.clear();
         switch (var1) {
            case a:
               this.g.add(this.c.c());
               this.g.add(this.c.d());
               break;
            case b:
               this.g.add(this.c.e());
               this.g.add(this.c.f());
               break;
            case c:
               this.g.add(this.c.e());
               this.g.add(this.c.f().a());
               break;
            case d:
               this.g.add(this.c.e().a());
               this.g.add(this.c.f());
               break;
            case e:
               this.g.add(this.c.c().a());
               this.g.add(this.c.d());
               break;
            case f:
               this.g.add(this.c.c());
               this.g.add(this.c.d().a());
               break;
            case g:
               this.g.add(this.c.f());
               this.g.add(this.c.d());
               break;
            case h:
               this.g.add(this.c.e());
               this.g.add(this.c.d());
               break;
            case i:
               this.g.add(this.c.e());
               this.g.add(this.c.c());
               break;
            case j:
               this.g.add(this.c.f());
               this.g.add(this.c.c());
         }
      }

      private void c() {
         for (int var1 = 0; var1 < this.g.size(); var1++) {
            BlockRailBase.Rail var2 = this.b(this.g.get(var1));
            if (var2 != null && var2.a(this)) {
               this.g.set(var1, var2.c);
            } else {
               this.g.remove(var1--);
            }
         }
      }

      private boolean a(BlockPos var1) {
         return BlockRailBase.e(this.b, var1) || BlockRailBase.e(this.b, var1.a()) || BlockRailBase.e(this.b, var1.b());
      }

      private BlockRailBase.Rail b(BlockPos var1) {
         IBlockState var3 = this.b.p(var1);
         if (BlockRailBase.d(var3)) {
            return BlockRailBase.this.new Rail(this.b, var1, var3);
         } else {
            BlockPos var2 = var1.a();
            var3 = this.b.p(var2);
            if (BlockRailBase.d(var3)) {
               return BlockRailBase.this.new Rail(this.b, var2, var3);
            } else {
               var2 = var1.b();
               var3 = this.b.p(var2);
               return BlockRailBase.d(var3) ? BlockRailBase.this.new Rail(this.b, var2, var3) : null;
            }
         }
      }

      private boolean a(BlockRailBase.Rail var1) {
         return this.c(var1.c);
      }

      private boolean c(BlockPos var1) {
         for (int var2 = 0; var2 < this.g.size(); var2++) {
            BlockPos var3 = this.g.get(var2);
            if (var3.n() == var1.n() && var3.p() == var1.p()) {
               return true;
            }
         }

         return false;
      }

      protected int a() {
         int var1 = 0;

         for (EnumFacing var3 : EnumFacing.Plane.a) {
            if (this.a(this.c.a(var3))) {
               var1++;
            }
         }

         return var1;
      }

      private boolean b(BlockRailBase.Rail var1) {
         return this.a(var1) || this.g.size() != 2;
      }

      private void c(BlockRailBase.Rail var1) {
         this.g.add(var1.c);
         BlockPos var2 = this.c.c();
         BlockPos var3 = this.c.d();
         BlockPos var4 = this.c.e();
         BlockPos var5 = this.c.f();
         boolean var6 = this.c(var2);
         boolean var7 = this.c(var3);
         boolean var8 = this.c(var4);
         boolean var9 = this.c(var5);
         BlockRailBase.EnumRailDirection var10 = null;
         if (var6 || var7) {
            var10 = BlockRailBase.EnumRailDirection.a;
         }

         if (var8 || var9) {
            var10 = BlockRailBase.EnumRailDirection.b;
         }

         if (!this.f) {
            if (var7 && var9 && !var6 && !var8) {
               var10 = BlockRailBase.EnumRailDirection.g;
            }

            if (var7 && var8 && !var6 && !var9) {
               var10 = BlockRailBase.EnumRailDirection.h;
            }

            if (var6 && var8 && !var7 && !var9) {
               var10 = BlockRailBase.EnumRailDirection.i;
            }

            if (var6 && var9 && !var7 && !var8) {
               var10 = BlockRailBase.EnumRailDirection.j;
            }
         }

         if (var10 == BlockRailBase.EnumRailDirection.a) {
            if (BlockRailBase.e(this.b, var2.a())) {
               var10 = BlockRailBase.EnumRailDirection.e;
            }

            if (BlockRailBase.e(this.b, var3.a())) {
               var10 = BlockRailBase.EnumRailDirection.f;
            }
         }

         if (var10 == BlockRailBase.EnumRailDirection.b) {
            if (BlockRailBase.e(this.b, var5.a())) {
               var10 = BlockRailBase.EnumRailDirection.c;
            }

            if (BlockRailBase.e(this.b, var4.a())) {
               var10 = BlockRailBase.EnumRailDirection.d;
            }
         }

         if (var10 == null) {
            var10 = BlockRailBase.EnumRailDirection.a;
         }

         this.e = this.e.a(this.d.n(), var10);
         this.b.a(this.c, this.e, 3);
      }

      private boolean d(BlockPos var1) {
         BlockRailBase.Rail var2 = this.b(var1);
         if (var2 == null) {
            return false;
         } else {
            var2.c();
            return var2.b(this);
         }
      }

      public BlockRailBase.Rail a(boolean var1, boolean var2) {
         BlockPos var3 = this.c.c();
         BlockPos var4 = this.c.d();
         BlockPos var5 = this.c.e();
         BlockPos var6 = this.c.f();
         boolean var7 = this.d(var3);
         boolean var8 = this.d(var4);
         boolean var9 = this.d(var5);
         boolean var10 = this.d(var6);
         BlockRailBase.EnumRailDirection var11 = null;
         if ((var7 || var8) && !var9 && !var10) {
            var11 = BlockRailBase.EnumRailDirection.a;
         }

         if ((var9 || var10) && !var7 && !var8) {
            var11 = BlockRailBase.EnumRailDirection.b;
         }

         if (!this.f) {
            if (var8 && var10 && !var7 && !var9) {
               var11 = BlockRailBase.EnumRailDirection.g;
            }

            if (var8 && var9 && !var7 && !var10) {
               var11 = BlockRailBase.EnumRailDirection.h;
            }

            if (var7 && var9 && !var8 && !var10) {
               var11 = BlockRailBase.EnumRailDirection.i;
            }

            if (var7 && var10 && !var8 && !var9) {
               var11 = BlockRailBase.EnumRailDirection.j;
            }
         }

         if (var11 == null) {
            if (var7 || var8) {
               var11 = BlockRailBase.EnumRailDirection.a;
            }

            if (var9 || var10) {
               var11 = BlockRailBase.EnumRailDirection.b;
            }

            if (!this.f) {
               if (var1) {
                  if (var8 && var10) {
                     var11 = BlockRailBase.EnumRailDirection.g;
                  }

                  if (var9 && var8) {
                     var11 = BlockRailBase.EnumRailDirection.h;
                  }

                  if (var10 && var7) {
                     var11 = BlockRailBase.EnumRailDirection.j;
                  }

                  if (var7 && var9) {
                     var11 = BlockRailBase.EnumRailDirection.i;
                  }
               } else {
                  if (var7 && var9) {
                     var11 = BlockRailBase.EnumRailDirection.i;
                  }

                  if (var10 && var7) {
                     var11 = BlockRailBase.EnumRailDirection.j;
                  }

                  if (var9 && var8) {
                     var11 = BlockRailBase.EnumRailDirection.h;
                  }

                  if (var8 && var10) {
                     var11 = BlockRailBase.EnumRailDirection.g;
                  }
               }
            }
         }

         if (var11 == BlockRailBase.EnumRailDirection.a) {
            if (BlockRailBase.e(this.b, var3.a())) {
               var11 = BlockRailBase.EnumRailDirection.e;
            }

            if (BlockRailBase.e(this.b, var4.a())) {
               var11 = BlockRailBase.EnumRailDirection.f;
            }
         }

         if (var11 == BlockRailBase.EnumRailDirection.b) {
            if (BlockRailBase.e(this.b, var6.a())) {
               var11 = BlockRailBase.EnumRailDirection.c;
            }

            if (BlockRailBase.e(this.b, var5.a())) {
               var11 = BlockRailBase.EnumRailDirection.d;
            }
         }

         if (var11 == null) {
            var11 = BlockRailBase.EnumRailDirection.a;
         }

         this.a(var11);
         this.e = this.e.a(this.d.n(), var11);
         if (var2 || this.b.p(this.c) != this.e) {
            this.b.a(this.c, this.e, 3);

            for (int var12 = 0; var12 < this.g.size(); var12++) {
               BlockRailBase.Rail var13 = this.b(this.g.get(var12));
               if (var13 != null) {
                  var13.c();
                  if (var13.b(this)) {
                     var13.c(this);
                  }
               }
            }
         }

         return this;
      }

      public IBlockState b() {
         return this.e;
      }
   }
}
