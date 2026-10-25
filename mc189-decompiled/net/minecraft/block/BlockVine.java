package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockVine extends Block {
   public static final PropertyBool a = PropertyBool.a("up");
   public static final PropertyBool b = PropertyBool.a("north");
   public static final PropertyBool N = PropertyBool.a("east");
   public static final PropertyBool O = PropertyBool.a("south");
   public static final PropertyBool P = PropertyBool.a("west");
   public static final PropertyBool[] Q = new PropertyBool[]{a, b, O, P, N};

   public BlockVine() {
      super(Material.l);
      this.j(this.M.b().a(a, false).a(b, false).a(N, false).a(O, false).a(P, false));
      this.a(true);
      this.a(CreativeTabs.c);
   }

   @Override
   public IBlockState a(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.a(a, var2.p(var3.a()).c().u());
   }

   @Override
   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
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
   public boolean a(World var1, BlockPos var2) {
      return true;
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      float var3 = 0.0625F;
      float var4 = 1.0F;
      float var5 = 1.0F;
      float var6 = 1.0F;
      float var7 = 0.0F;
      float var8 = 0.0F;
      float var9 = 0.0F;
      boolean var10 = false;
      if (var1.p(var2).b(P)) {
         var7 = Math.max(var7, 0.0625F);
         var4 = 0.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
         var10 = true;
      }

      if (var1.p(var2).b(N)) {
         var4 = Math.min(var4, 0.9375F);
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
         var10 = true;
      }

      if (var1.p(var2).b(b)) {
         var9 = Math.max(var9, 0.0625F);
         var6 = 0.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var10 = true;
      }

      if (var1.p(var2).b(O)) {
         var6 = Math.min(var6, 0.9375F);
         var9 = 1.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var5 = 0.0F;
         var8 = 1.0F;
         var10 = true;
      }

      if (!var10 && this.c(var1.p(var2.a()).c())) {
         var5 = Math.min(var5, 0.9375F);
         var8 = 1.0F;
         var4 = 0.0F;
         var7 = 1.0F;
         var6 = 0.0F;
         var9 = 1.0F;
      }

      this.a(var4, var5, var6, var7, var8, var9);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public boolean b(World var1, BlockPos var2, EnumFacing var3) {
      switch (var3) {
         case b:
            return this.c(var1.p(var2.a()).c());
         case c:
         case d:
         case f:
         case e:
            return this.c(var1.p(var2.a(var3.d())).c());
         default:
            return false;
      }
   }

   private boolean c(Block var1) {
      return var1.d() && var1.J.c();
   }

   private boolean e(World var1, BlockPos var2, IBlockState var3) {
      IBlockState var4 = var3;

      for (EnumFacing var6 : EnumFacing.Plane.a) {
         PropertyBool var7 = a(var6);
         if (var3.b(var7) && !this.c(var1.p(var2.a(var6)).c())) {
            IBlockState var8 = var1.p(var2.a());
            if (var8.c() != this || !var8.b(var7)) {
               var3 = var3.a(var7, false);
            }
         }
      }

      if (d(var3) == 0) {
         return false;
      } else {
         if (var4 != var3) {
            var1.a(var2, var3, 2);
         }

         return true;
      }
   }

   @Override
   public int H() {
      return ColorizerFoliage.c();
   }

   @Override
   public int h(IBlockState var1) {
      return ColorizerFoliage.c();
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, int var3) {
      return var1.b(var2).c(var2);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.D && !this.e(var1, var2, var3)) {
         this.b(var1, var2, var3, 0);
         var1.g(var2);
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.D) {
         if (var1.s.nextInt(4) == 0) {
            byte var5 = 4;
            int var6 = 5;
            boolean var7 = false;

            label189:
            for (int var8 = -var5; var8 <= var5; var8++) {
               for (int var9 = -var5; var9 <= var5; var9++) {
                  for (int var10 = -1; var10 <= 1; var10++) {
                     if (var1.p(var2.a(var8, var10, var9)).c() == this) {
                        if (--var6 <= 0) {
                           var7 = true;
                           break label189;
                        }
                     }
                  }
               }
            }

            EnumFacing var18 = EnumFacing.a(var4);
            BlockPos var19 = var2.a();
            if (var18 == EnumFacing.b && var2.o() < 255 && var1.d(var19)) {
               if (!var7) {
                  IBlockState var22 = var3;

                  for (EnumFacing var26 : EnumFacing.Plane.a) {
                     if (var4.nextBoolean() || !this.c(var1.p(var19.a(var26)).c())) {
                        var22 = var22.a(a(var26), false);
                     }
                  }

                  if (var22.b(b) || var22.b(N) || var22.b(O) || var22.b(P)) {
                     var1.a(var19, var22, 2);
                  }
               }
            } else if (!var18.k().c() || var3.b(a(var18))) {
               if (var2.o() > 1) {
                  BlockPos var21 = var2.b();
                  IBlockState var23 = var1.p(var21);
                  Block var25 = var23.c();
                  if (var25.J == Material.a) {
                     IBlockState var27 = var3;

                     for (EnumFacing var31 : EnumFacing.Plane.a) {
                        if (var4.nextBoolean()) {
                           var27 = var27.a(a(var31), false);
                        }
                     }

                     if (var27.b(b) || var27.b(N) || var27.b(O) || var27.b(P)) {
                        var1.a(var21, var27, 2);
                     }
                  } else if (var25 == this) {
                     IBlockState var28 = var23;

                     for (EnumFacing var32 : EnumFacing.Plane.a) {
                        PropertyBool var33 = a(var32);
                        if (var4.nextBoolean() && var3.b(var33)) {
                           var28 = var28.a(var33, true);
                        }
                     }

                     if (var28.b(b) || var28.b(N) || var28.b(O) || var28.b(P)) {
                        var1.a(var21, var28, 2);
                     }
                  }
               }
            } else if (!var7) {
               BlockPos var20 = var2.a(var18);
               Block var11 = var1.p(var20).c();
               if (var11.J == Material.a) {
                  EnumFacing var12 = var18.e();
                  EnumFacing var13 = var18.f();
                  boolean var14 = var3.b(a(var12));
                  boolean var15 = var3.b(a(var13));
                  BlockPos var16 = var20.a(var12);
                  BlockPos var17 = var20.a(var13);
                  if (var14 && this.c(var1.p(var16).c())) {
                     var1.a(var20, this.Q().a(a(var12), true), 2);
                  } else if (var15 && this.c(var1.p(var17).c())) {
                     var1.a(var20, this.Q().a(a(var13), true), 2);
                  } else if (var14 && var1.d(var16) && this.c(var1.p(var2.a(var12)).c())) {
                     var1.a(var16, this.Q().a(a(var18.d()), true), 2);
                  } else if (var15 && var1.d(var17) && this.c(var1.p(var2.a(var13)).c())) {
                     var1.a(var17, this.Q().a(a(var18.d()), true), 2);
                  } else if (this.c(var1.p(var20.a()).c())) {
                     var1.a(var20, this.Q(), 2);
                  }
               } else if (var11.J.k() && var11.d()) {
                  var1.a(var2, var3.a(a(var18), true), 2);
               }
            }
         }
      }
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = this.Q().a(a, false).a(b, false).a(N, false).a(O, false).a(P, false);
      return var3.k().c() ? var9.a(a(var3.d()), true) : var9;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return null;
   }

   @Override
   public int a(Random var1) {
      return 0;
   }

   @Override
   public void a(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      if (!var1.D && var2.bZ() != null && var2.bZ().b() == Items.be) {
         var2.b(StatList.ab[Block.a(this)]);
         a(var1, var3, new ItemStack(Blocks.bn, 1, 0));
      } else {
         super.a(var1, var2, var3, var4, var5);
      }
   }

   @Override
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(O, (var1 & 1) > 0).a(P, (var1 & 2) > 0).a(b, (var1 & 4) > 0).a(N, (var1 & 8) > 0);
   }

   @Override
   public int c(IBlockState var1) {
      byte var2 = 0;
      if (var1.b(O)) {
         var2 |= 1;
      }

      if (var1.b(P)) {
         var2 |= 2;
      }

      if (var1.b(b)) {
         var2 |= 4;
      }

      if (var1.b(N)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b, N, O, P);
   }

   public static PropertyBool a(EnumFacing var0) {
      switch (var0) {
         case b:
            return a;
         case c:
            return b;
         case d:
            return O;
         case f:
            return N;
         case e:
            return P;
         default:
            throw new IllegalArgumentException(var0 + " is an invalid choice");
      }
   }

   public static int d(IBlockState var0) {
      int var1 = 0;

      for (PropertyBool var5 : Q) {
         if (var0.b(var5)) {
            var1++;
         }
      }

      return var1;
   }
}
