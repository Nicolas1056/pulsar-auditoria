package net.minecraft.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;

public class BlockChest extends BlockContainer {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   public final int b;

   protected BlockChest(int var1) {
      super(Material.d);
      this.j(this.M.b().a(a, EnumFacing.c));
      this.b = var1;
      this.a(CreativeTabs.c);
      this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
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
   public int b() {
      return 2;
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      if (var1.p(var2.c()).c() == this) {
         this.a(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
      } else if (var1.p(var2.d()).c() == this) {
         this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
      } else if (var1.p(var2.e()).c() == this) {
         this.a(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      } else if (var1.p(var2.f()).c() == this) {
         this.a(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
      } else {
         this.a(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      this.e(var1, var2, var3);

      for (EnumFacing var5 : EnumFacing.Plane.a) {
         BlockPos var6 = var2.a(var5);
         IBlockState var7 = var1.p(var6);
         if (var7.c() == this) {
            this.e(var1, var6, var7);
         }
      }
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(a, var8.aP());
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      EnumFacing var6 = EnumFacing.b(MathHelper.c(var4.y * 4.0F / 360.0F + 0.5) & 3).d();
      var3 = var3.a(a, var6);
      BlockPos var7 = var2.c();
      BlockPos var8 = var2.d();
      BlockPos var9 = var2.e();
      BlockPos var10 = var2.f();
      boolean var11 = this == var1.p(var7).c();
      boolean var12 = this == var1.p(var8).c();
      boolean var13 = this == var1.p(var9).c();
      boolean var14 = this == var1.p(var10).c();
      if (!var11 && !var12 && !var13 && !var14) {
         var1.a(var2, var3, 3);
      } else if (var6.k() != EnumFacing.Axis.a || !var11 && !var12) {
         if (var6.k() == EnumFacing.Axis.c && (var13 || var14)) {
            if (var13) {
               var1.a(var9, var3, 3);
            } else {
               var1.a(var10, var3, 3);
            }

            var1.a(var2, var3, 3);
         }
      } else {
         if (var11) {
            var1.a(var7, var3, 3);
         } else {
            var1.a(var8, var3, 3);
         }

         var1.a(var2, var3, 3);
      }

      if (var5.s()) {
         TileEntity var15 = var1.s(var2);
         if (var15 instanceof TileEntityChest) {
            ((TileEntityChest)var15).a(var5.q());
         }
      }
   }

   public IBlockState e(World var1, BlockPos var2, IBlockState var3) {
      if (var1.D) {
         return var3;
      } else {
         IBlockState var4 = var1.p(var2.c());
         IBlockState var5 = var1.p(var2.d());
         IBlockState var6 = var1.p(var2.e());
         IBlockState var7 = var1.p(var2.f());
         EnumFacing var8 = var3.b(a);
         Block var9 = var4.c();
         Block var10 = var5.c();
         Block var11 = var6.c();
         Block var12 = var7.c();
         if (var9 != this && var10 != this) {
            boolean var22 = var9.o();
            boolean var23 = var10.o();
            if (var11 == this || var12 == this) {
               BlockPos var24 = var11 == this ? var2.e() : var2.f();
               IBlockState var25 = var1.p(var24.c());
               IBlockState var26 = var1.p(var24.d());
               var8 = EnumFacing.d;
               EnumFacing var27;
               if (var11 == this) {
                  var27 = var6.b(a);
               } else {
                  var27 = var7.b(a);
               }

               if (var27 == EnumFacing.c) {
                  var8 = EnumFacing.c;
               }

               Block var19 = var25.c();
               Block var20 = var26.c();
               if ((var22 || var19.o()) && !var23 && !var20.o()) {
                  var8 = EnumFacing.d;
               }

               if ((var23 || var20.o()) && !var22 && !var19.o()) {
                  var8 = EnumFacing.c;
               }
            }
         } else {
            BlockPos var13 = var9 == this ? var2.c() : var2.d();
            IBlockState var14 = var1.p(var13.e());
            IBlockState var15 = var1.p(var13.f());
            var8 = EnumFacing.f;
            EnumFacing var16;
            if (var9 == this) {
               var16 = var4.b(a);
            } else {
               var16 = var5.b(a);
            }

            if (var16 == EnumFacing.e) {
               var8 = EnumFacing.e;
            }

            Block var17 = var14.c();
            Block var18 = var15.c();
            if ((var11.o() || var17.o()) && !var12.o() && !var18.o()) {
               var8 = EnumFacing.f;
            }

            if ((var12.o() || var18.o()) && !var11.o() && !var17.o()) {
               var8 = EnumFacing.e;
            }
         }

         var3 = var3.a(a, var8);
         var1.a(var2, var3, 3);
         return var3;
      }
   }

   public IBlockState f(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = null;

      for (EnumFacing var6 : EnumFacing.Plane.a) {
         IBlockState var7 = var1.p(var2.a(var6));
         if (var7.c() == this) {
            return var3;
         }

         if (var7.c().o()) {
            if (var4 != null) {
               var4 = null;
               break;
            }

            var4 = var6;
         }
      }

      if (var4 != null) {
         return var3.a(a, var4.d());
      } else {
         EnumFacing var8 = var3.b(a);
         if (var1.p(var2.a(var8)).c().o()) {
            var8 = var8.d();
         }

         if (var1.p(var2.a(var8)).c().o()) {
            var8 = var8.e();
         }

         if (var1.p(var2.a(var8)).c().o()) {
            var8 = var8.d();
         }

         return var3.a(a, var8);
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      int var3 = 0;
      BlockPos var4 = var2.e();
      BlockPos var5 = var2.f();
      BlockPos var6 = var2.c();
      BlockPos var7 = var2.d();
      if (var1.p(var4).c() == this) {
         if (this.m(var1, var4)) {
            return false;
         }

         var3++;
      }

      if (var1.p(var5).c() == this) {
         if (this.m(var1, var5)) {
            return false;
         }

         var3++;
      }

      if (var1.p(var6).c() == this) {
         if (this.m(var1, var6)) {
            return false;
         }

         var3++;
      }

      if (var1.p(var7).c() == this) {
         if (this.m(var1, var7)) {
            return false;
         }

         var3++;
      }

      return var3 <= 1;
   }

   private boolean m(World var1, BlockPos var2) {
      if (var1.p(var2).c() != this) {
         return false;
      } else {
         for (EnumFacing var4 : EnumFacing.Plane.a) {
            if (var1.p(var2.a(var4)).c() == this) {
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      super.a(var1, var2, var3, var4);
      TileEntity var5 = var1.s(var2);
      if (var5 instanceof TileEntityChest) {
         var5.E();
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.s(var2);
      if (var4 instanceof IInventory) {
         InventoryHelper.a(var1, var2, (IInventory)var4);
         var1.e(var2, this);
      }

      super.b(var1, var2, var3);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         ILockableContainer var9 = this.f(var1, var2);
         if (var9 != null) {
            var4.a((IInventory)var9);
            if (this.b == 0) {
               var4.b(StatList.aa);
            } else if (this.b == 1) {
               var4.b(StatList.U);
            }
         }

         return true;
      }
   }

   public ILockableContainer f(World var1, BlockPos var2) {
      TileEntity var3 = var1.s(var2);
      if (!(var3 instanceof TileEntityChest)) {
         return null;
      } else {
         Object var4 = (TileEntityChest)var3;
         if (this.n(var1, var2)) {
            return null;
         } else {
            for (EnumFacing var6 : EnumFacing.Plane.a) {
               BlockPos var7 = var2.a(var6);
               Block var8 = var1.p(var7).c();
               if (var8 == this) {
                  if (this.n(var1, var7)) {
                     return null;
                  }

                  TileEntity var9 = var1.s(var7);
                  if (var9 instanceof TileEntityChest) {
                     if (var6 != EnumFacing.e && var6 != EnumFacing.c) {
                        var4 = new InventoryLargeChest("container.chestDouble", (ILockableContainer)var4, (TileEntityChest)var9);
                     } else {
                        var4 = new InventoryLargeChest("container.chestDouble", (TileEntityChest)var9, (ILockableContainer)var4);
                     }
                  }
               }
            }

            return (ILockableContainer)var4;
         }
      }
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityChest();
   }

   @Override
   public boolean i() {
      return this.b == 1;
   }

   @Override
   public int a(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!this.i()) {
         return 0;
      } else {
         int var5 = 0;
         TileEntity var6 = var1.s(var2);
         if (var6 instanceof TileEntityChest) {
            var5 = ((TileEntityChest)var6).l;
         }

         return MathHelper.a(var5, 0, 15);
      }
   }

   @Override
   public int b(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var4 == EnumFacing.b ? this.a(var1, var2, var3, var4) : 0;
   }

   private boolean n(World var1, BlockPos var2) {
      return this.o(var1, var2) || this.p(var1, var2);
   }

   private boolean o(World var1, BlockPos var2) {
      return var1.p(var2.a()).c().v();
   }

   private boolean p(World var1, BlockPos var2) {
      for (Entity var4 : var1.a(EntityOcelot.class, new AxisAlignedBB(var2.n(), var2.o() + 1, var2.p(), var2.n() + 1, var2.o() + 2, var2.p() + 1))) {
         EntityOcelot var5 = (EntityOcelot)var4;
         if (var5.cn()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public int l(World var1, BlockPos var2) {
      return Container.b(this.f(var1, var2));
   }

   @Override
   public IBlockState a(int var1) {
      EnumFacing var2 = EnumFacing.a(var1);
      if (var2.k() == EnumFacing.Axis.b) {
         var2 = EnumFacing.c;
      }

      return this.Q().a(a, var2);
   }

   @Override
   public int c(IBlockState var1) {
      return var1.b(a).a();
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a);
   }
}
