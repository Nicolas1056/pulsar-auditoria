package net.minecraft.block;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStairs extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   public static final PropertyEnum<BlockStairs.EnumHalf> b = PropertyEnum.a("half", BlockStairs.EnumHalf.class);
   public static final PropertyEnum<BlockStairs.EnumShape> N = PropertyEnum.a("shape", BlockStairs.EnumShape.class);
   private static final int[][] O = new int[][]{{4, 5}, {5, 7}, {6, 7}, {4, 6}, {0, 1}, {1, 3}, {2, 3}, {0, 2}};
   private final Block P;
   private final IBlockState Q;
   private boolean R;
   private int S;

   protected BlockStairs(IBlockState var1) {
      super(var1.c().J);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, BlockStairs.EnumHalf.b).a(N, BlockStairs.EnumShape.a));
      this.P = var1.c();
      this.Q = var1;
      this.c(this.P.w);
      this.b(this.P.x / 3.0F);
      this.a(this.P.H);
      this.e(255);
      this.a(CreativeTabs.b);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      if (this.R) {
         this.a(
            0.5F * (this.S % 2),
            0.5F * (this.S / 4 % 2),
            0.5F * (this.S / 2 % 2),
            0.5F + 0.5F * (this.S % 2),
            0.5F + 0.5F * (this.S / 4 % 2),
            0.5F + 0.5F * (this.S / 2 % 2)
         );
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean d() {
      return false;
   }

   public void e(IBlockAccess var1, BlockPos var2) {
      if (var1.p(var2).b(b) == BlockStairs.EnumHalf.a) {
         this.a(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }
   }

   public static boolean c(Block var0) {
      return var0 instanceof BlockStairs;
   }

   public static boolean a(IBlockAccess var0, BlockPos var1, IBlockState var2) {
      IBlockState var3 = var0.p(var1);
      Block var4 = var3.c();
      return c(var4) && var3.b(b) == var2.b(b) && var3.b(a) == var2.b(a);
   }

   public int f(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      EnumFacing var4 = var3.b(a);
      BlockStairs.EnumHalf var5 = var3.b(b);
      boolean var6 = var5 == BlockStairs.EnumHalf.a;
      if (var4 == EnumFacing.f) {
         IBlockState var7 = var1.p(var2.f());
         Block var8 = var7.c();
         if (c(var8) && var5 == var7.b(b)) {
            EnumFacing var9 = var7.b(a);
            if (var9 == EnumFacing.c && !a(var1, var2.d(), var3)) {
               return var6 ? 1 : 2;
            }

            if (var9 == EnumFacing.d && !a(var1, var2.c(), var3)) {
               return var6 ? 2 : 1;
            }
         }
      } else if (var4 == EnumFacing.e) {
         IBlockState var10 = var1.p(var2.e());
         Block var13 = var10.c();
         if (c(var13) && var5 == var10.b(b)) {
            EnumFacing var16 = var10.b(a);
            if (var16 == EnumFacing.c && !a(var1, var2.d(), var3)) {
               return var6 ? 2 : 1;
            }

            if (var16 == EnumFacing.d && !a(var1, var2.c(), var3)) {
               return var6 ? 1 : 2;
            }
         }
      } else if (var4 == EnumFacing.d) {
         IBlockState var11 = var1.p(var2.d());
         Block var14 = var11.c();
         if (c(var14) && var5 == var11.b(b)) {
            EnumFacing var17 = var11.b(a);
            if (var17 == EnumFacing.e && !a(var1, var2.f(), var3)) {
               return var6 ? 2 : 1;
            }

            if (var17 == EnumFacing.f && !a(var1, var2.e(), var3)) {
               return var6 ? 1 : 2;
            }
         }
      } else if (var4 == EnumFacing.c) {
         IBlockState var12 = var1.p(var2.c());
         Block var15 = var12.c();
         if (c(var15) && var5 == var12.b(b)) {
            EnumFacing var18 = var12.b(a);
            if (var18 == EnumFacing.e && !a(var1, var2.f(), var3)) {
               return var6 ? 1 : 2;
            }

            if (var18 == EnumFacing.f && !a(var1, var2.e(), var3)) {
               return var6 ? 2 : 1;
            }
         }
      }

      return 0;
   }

   public int g(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      EnumFacing var4 = var3.b(a);
      BlockStairs.EnumHalf var5 = var3.b(b);
      boolean var6 = var5 == BlockStairs.EnumHalf.a;
      if (var4 == EnumFacing.f) {
         IBlockState var7 = var1.p(var2.e());
         Block var8 = var7.c();
         if (c(var8) && var5 == var7.b(b)) {
            EnumFacing var9 = var7.b(a);
            if (var9 == EnumFacing.c && !a(var1, var2.c(), var3)) {
               return var6 ? 1 : 2;
            }

            if (var9 == EnumFacing.d && !a(var1, var2.d(), var3)) {
               return var6 ? 2 : 1;
            }
         }
      } else if (var4 == EnumFacing.e) {
         IBlockState var10 = var1.p(var2.f());
         Block var13 = var10.c();
         if (c(var13) && var5 == var10.b(b)) {
            EnumFacing var16 = var10.b(a);
            if (var16 == EnumFacing.c && !a(var1, var2.c(), var3)) {
               return var6 ? 2 : 1;
            }

            if (var16 == EnumFacing.d && !a(var1, var2.d(), var3)) {
               return var6 ? 1 : 2;
            }
         }
      } else if (var4 == EnumFacing.d) {
         IBlockState var11 = var1.p(var2.c());
         Block var14 = var11.c();
         if (c(var14) && var5 == var11.b(b)) {
            EnumFacing var17 = var11.b(a);
            if (var17 == EnumFacing.e && !a(var1, var2.e(), var3)) {
               return var6 ? 2 : 1;
            }

            if (var17 == EnumFacing.f && !a(var1, var2.f(), var3)) {
               return var6 ? 1 : 2;
            }
         }
      } else if (var4 == EnumFacing.c) {
         IBlockState var12 = var1.p(var2.d());
         Block var15 = var12.c();
         if (c(var15) && var5 == var12.b(b)) {
            EnumFacing var18 = var12.b(a);
            if (var18 == EnumFacing.e && !a(var1, var2.e(), var3)) {
               return var6 ? 1 : 2;
            }

            if (var18 == EnumFacing.f && !a(var1, var2.f(), var3)) {
               return var6 ? 2 : 1;
            }
         }
      }

      return 0;
   }

   public boolean h(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      EnumFacing var4 = var3.b(a);
      BlockStairs.EnumHalf var5 = var3.b(b);
      boolean var6 = var5 == BlockStairs.EnumHalf.a;
      float var7 = 0.5F;
      float var8 = 1.0F;
      if (var6) {
         var7 = 0.0F;
         var8 = 0.5F;
      }

      float var9 = 0.0F;
      float var10 = 1.0F;
      float var11 = 0.0F;
      float var12 = 0.5F;
      boolean var13 = true;
      if (var4 == EnumFacing.f) {
         var9 = 0.5F;
         var12 = 1.0F;
         IBlockState var14 = var1.p(var2.f());
         Block var15 = var14.c();
         if (c(var15) && var5 == var14.b(b)) {
            EnumFacing var16 = var14.b(a);
            if (var16 == EnumFacing.c && !a(var1, var2.d(), var3)) {
               var12 = 0.5F;
               var13 = false;
            } else if (var16 == EnumFacing.d && !a(var1, var2.c(), var3)) {
               var11 = 0.5F;
               var13 = false;
            }
         }
      } else if (var4 == EnumFacing.e) {
         var10 = 0.5F;
         var12 = 1.0F;
         IBlockState var17 = var1.p(var2.e());
         Block var20 = var17.c();
         if (c(var20) && var5 == var17.b(b)) {
            EnumFacing var23 = var17.b(a);
            if (var23 == EnumFacing.c && !a(var1, var2.d(), var3)) {
               var12 = 0.5F;
               var13 = false;
            } else if (var23 == EnumFacing.d && !a(var1, var2.c(), var3)) {
               var11 = 0.5F;
               var13 = false;
            }
         }
      } else if (var4 == EnumFacing.d) {
         var11 = 0.5F;
         var12 = 1.0F;
         IBlockState var18 = var1.p(var2.d());
         Block var21 = var18.c();
         if (c(var21) && var5 == var18.b(b)) {
            EnumFacing var24 = var18.b(a);
            if (var24 == EnumFacing.e && !a(var1, var2.f(), var3)) {
               var10 = 0.5F;
               var13 = false;
            } else if (var24 == EnumFacing.f && !a(var1, var2.e(), var3)) {
               var9 = 0.5F;
               var13 = false;
            }
         }
      } else if (var4 == EnumFacing.c) {
         IBlockState var19 = var1.p(var2.c());
         Block var22 = var19.c();
         if (c(var22) && var5 == var19.b(b)) {
            EnumFacing var25 = var19.b(a);
            if (var25 == EnumFacing.e && !a(var1, var2.f(), var3)) {
               var10 = 0.5F;
               var13 = false;
            } else if (var25 == EnumFacing.f && !a(var1, var2.e(), var3)) {
               var9 = 0.5F;
               var13 = false;
            }
         }
      }

      this.a(var9, var7, var11, var10, var8, var12);
      return var13;
   }

   public boolean i(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      EnumFacing var4 = var3.b(a);
      BlockStairs.EnumHalf var5 = var3.b(b);
      boolean var6 = var5 == BlockStairs.EnumHalf.a;
      float var7 = 0.5F;
      float var8 = 1.0F;
      if (var6) {
         var7 = 0.0F;
         var8 = 0.5F;
      }

      float var9 = 0.0F;
      float var10 = 0.5F;
      float var11 = 0.5F;
      float var12 = 1.0F;
      boolean var13 = false;
      if (var4 == EnumFacing.f) {
         IBlockState var14 = var1.p(var2.e());
         Block var15 = var14.c();
         if (c(var15) && var5 == var14.b(b)) {
            EnumFacing var16 = var14.b(a);
            if (var16 == EnumFacing.c && !a(var1, var2.c(), var3)) {
               var11 = 0.0F;
               var12 = 0.5F;
               var13 = true;
            } else if (var16 == EnumFacing.d && !a(var1, var2.d(), var3)) {
               var11 = 0.5F;
               var12 = 1.0F;
               var13 = true;
            }
         }
      } else if (var4 == EnumFacing.e) {
         IBlockState var17 = var1.p(var2.f());
         Block var20 = var17.c();
         if (c(var20) && var5 == var17.b(b)) {
            var9 = 0.5F;
            var10 = 1.0F;
            EnumFacing var23 = var17.b(a);
            if (var23 == EnumFacing.c && !a(var1, var2.c(), var3)) {
               var11 = 0.0F;
               var12 = 0.5F;
               var13 = true;
            } else if (var23 == EnumFacing.d && !a(var1, var2.d(), var3)) {
               var11 = 0.5F;
               var12 = 1.0F;
               var13 = true;
            }
         }
      } else if (var4 == EnumFacing.d) {
         IBlockState var18 = var1.p(var2.c());
         Block var21 = var18.c();
         if (c(var21) && var5 == var18.b(b)) {
            var11 = 0.0F;
            var12 = 0.5F;
            EnumFacing var24 = var18.b(a);
            if (var24 == EnumFacing.e && !a(var1, var2.e(), var3)) {
               var13 = true;
            } else if (var24 == EnumFacing.f && !a(var1, var2.f(), var3)) {
               var9 = 0.5F;
               var10 = 1.0F;
               var13 = true;
            }
         }
      } else if (var4 == EnumFacing.c) {
         IBlockState var19 = var1.p(var2.d());
         Block var22 = var19.c();
         if (c(var22) && var5 == var19.b(b)) {
            EnumFacing var25 = var19.b(a);
            if (var25 == EnumFacing.e && !a(var1, var2.e(), var3)) {
               var13 = true;
            } else if (var25 == EnumFacing.f && !a(var1, var2.f(), var3)) {
               var9 = 0.5F;
               var10 = 1.0F;
               var13 = true;
            }
         }
      }

      if (var13) {
         this.a(var9, var7, var11, var10, var8, var12);
      }

      return var13;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      this.e(var1, var2);
      super.a(var1, var2, var3, var4, var5, var6);
      boolean var7 = this.h(var1, var2);
      super.a(var1, var2, var3, var4, var5, var6);
      if (var7 && this.i(var1, var2)) {
         super.a(var1, var2, var3, var4, var5, var6);
      }

      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.P.c(var1, var2, var3, var4);
   }

   @Override
   public void a(World var1, BlockPos var2, EntityPlayer var3) {
      this.P.a(var1, var2, var3);
   }

   @Override
   public void d(World var1, BlockPos var2, IBlockState var3) {
      this.P.d(var1, var2, var3);
   }

   @Override
   public int c(IBlockAccess var1, BlockPos var2) {
      return this.P.c(var1, var2);
   }

   @Override
   public float a(Entity var1) {
      return this.P.a(var1);
   }

   @Override
   public EnumWorldBlockLayer m() {
      return this.P.m();
   }

   @Override
   public int a(World var1) {
      return this.P.a(var1);
   }

   @Override
   public AxisAlignedBB b(World var1, BlockPos var2) {
      return this.P.b(var1, var2);
   }

   @Override
   public Vec3 a(World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return this.P.a(var1, var2, var3, var4);
   }

   @Override
   public boolean A() {
      return this.P.A();
   }

   @Override
   public boolean a(IBlockState var1, boolean var2) {
      return this.P.a(var1, var2);
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return this.P.d(var1, var2);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      this.a(var1, var2, this.Q, Blocks.a);
      this.P.c(var1, var2, this.Q);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      this.P.b(var1, var2, this.Q);
   }

   @Override
   public void a(World var1, BlockPos var2, Entity var3) {
      this.P.a(var1, var2, var3);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.P.b(var1, var2, var3, var4);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      return this.P.a(var1, var2, this.Q, var4, EnumFacing.a, 0.0F, 0.0F, 0.0F);
   }

   @Override
   public void a(World var1, BlockPos var2, Explosion var3) {
      this.P.a(var1, var2, var3);
   }

   @Override
   public MapColor g(IBlockState var1) {
      return this.P.g(this.Q);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = super.a(var1, var2, var3, var4, var5, var6, var7, var8);
      var9 = var9.a(a, var8.aP()).a(N, BlockStairs.EnumShape.a);
      return var3 != EnumFacing.a && (var3 == EnumFacing.b || !(var5 > 0.5)) ? var9.a(b, BlockStairs.EnumHalf.b) : var9.a(b, BlockStairs.EnumHalf.a);
   }

   @Override
   public MovingObjectPosition a(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      MovingObjectPosition[] var5 = new MovingObjectPosition[8];
      IBlockState var6 = var1.p(var2);
      int var7 = var6.b(a).b();
      boolean var8 = var6.b(b) == BlockStairs.EnumHalf.a;
      int[] var9 = O[var7 + (var8 ? 4 : 0)];
      this.R = true;

      for (int var10 = 0; var10 < 8; var10++) {
         this.S = var10;
         if (Arrays.binarySearch(var9, var10) < 0) {
            var5[var10] = super.a(var1, var2, var3, var4);
         }
      }

      for (int var13 : var9) {
         var5[var13] = null;
      }

      MovingObjectPosition var20 = null;
      double var21 = 0.0;

      for (MovingObjectPosition var16 : var5) {
         if (var16 != null) {
            double var17 = var16.c.g(var4);
            if (var17 > var21) {
               var20 = var16;
               var21 = var17;
            }
         }
      }

      return var20;
   }

   @Override
   public IBlockState a(int var1) {
      IBlockState var2 = this.Q().a(b, (var1 & 4) > 0 ? BlockStairs.EnumHalf.a : BlockStairs.EnumHalf.b);
      return var2.a(a, EnumFacing.a(5 - (var1 & 3)));
   }

   @Override
   public int c(IBlockState var1) {
      byte var2 = 0;
      if (var1.b(b) == BlockStairs.EnumHalf.a) {
         var2 |= 4;
      }

      return var2 | 5 - var1.b(a).a();
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if (this.h(var2, var3)) {
         switch (this.g(var2, var3)) {
            case 0:
               var1 = var1.a(N, BlockStairs.EnumShape.a);
               break;
            case 1:
               var1 = var1.a(N, BlockStairs.EnumShape.c);
               break;
            case 2:
               var1 = var1.a(N, BlockStairs.EnumShape.b);
         }
      } else {
         switch (this.f(var2, var3)) {
            case 0:
               var1 = var1.a(N, BlockStairs.EnumShape.a);
               break;
            case 1:
               var1 = var1.a(N, BlockStairs.EnumShape.e);
               break;
            case 2:
               var1 = var1.a(N, BlockStairs.EnumShape.d);
         }
      }

      return var1;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, N);
   }

   public static enum EnumHalf implements IStringSerializable {
      a("top"),
      b("bottom");

      private final String c;

      private EnumHalf(String var3) {
         this.c = var3;
      }

      @Override
      public String toString() {
         return this.c;
      }

      @Override
      public String l() {
         return this.c;
      }
   }

   public static enum EnumShape implements IStringSerializable {
      a("straight"),
      b("inner_left"),
      c("inner_right"),
      d("outer_left"),
      e("outer_right");

      private final String f;

      private EnumShape(String var3) {
         this.f = var3;
      }

      @Override
      public String toString() {
         return this.f;
      }

      @Override
      public String l() {
         return this.f;
      }
   }
}
