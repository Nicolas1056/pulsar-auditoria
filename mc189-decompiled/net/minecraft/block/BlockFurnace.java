package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockFurnace extends BlockContainer {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   private final boolean b;
   private static boolean N;

   protected BlockFurnace(boolean var1) {
      super(Material.e);
      this.j(this.M.b().a(a, EnumFacing.c));
      this.b = var1;
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.al);
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      this.e(var1, var2, var3);
   }

   private void e(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.D) {
         Block var4 = var1.p(var2.c()).c();
         Block var5 = var1.p(var2.d()).c();
         Block var6 = var1.p(var2.e()).c();
         Block var7 = var1.p(var2.f()).c();
         EnumFacing var8 = var3.b(a);
         if (var8 == EnumFacing.c && var4.o() && !var5.o()) {
            var8 = EnumFacing.d;
         } else if (var8 == EnumFacing.d && var5.o() && !var4.o()) {
            var8 = EnumFacing.c;
         } else if (var8 == EnumFacing.e && var6.o() && !var7.o()) {
            var8 = EnumFacing.f;
         } else if (var8 == EnumFacing.f && var7.o() && !var6.o()) {
            var8 = EnumFacing.e;
         }

         var1.a(var2, var3.a(a, var8), 2);
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.b) {
         EnumFacing var5 = var3.b(a);
         double var6 = var2.n() + 0.5;
         double var8 = var2.o() + var4.nextDouble() * 6.0 / 16.0;
         double var10 = var2.p() + 0.5;
         double var12 = 0.52;
         double var14 = var4.nextDouble() * 0.6 - 0.3;
         switch (var5) {
            case e:
               var1.a(EnumParticleTypes.l, var6 - var12, var8, var10 + var14, 0.0, 0.0, 0.0);
               var1.a(EnumParticleTypes.A, var6 - var12, var8, var10 + var14, 0.0, 0.0, 0.0);
               break;
            case f:
               var1.a(EnumParticleTypes.l, var6 + var12, var8, var10 + var14, 0.0, 0.0, 0.0);
               var1.a(EnumParticleTypes.A, var6 + var12, var8, var10 + var14, 0.0, 0.0, 0.0);
               break;
            case c:
               var1.a(EnumParticleTypes.l, var6 + var14, var8, var10 - var12, 0.0, 0.0, 0.0);
               var1.a(EnumParticleTypes.A, var6 + var14, var8, var10 - var12, 0.0, 0.0, 0.0);
               break;
            case d:
               var1.a(EnumParticleTypes.l, var6 + var14, var8, var10 + var12, 0.0, 0.0, 0.0);
               var1.a(EnumParticleTypes.A, var6 + var14, var8, var10 + var12, 0.0, 0.0, 0.0);
         }
      }
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         TileEntity var9 = var1.s(var2);
         if (var9 instanceof TileEntityFurnace) {
            var4.a((IInventory)((TileEntityFurnace)var9));
            var4.b(StatList.Y);
         }

         return true;
      }
   }

   public static void a(boolean var0, World var1, BlockPos var2) {
      IBlockState var3 = var1.p(var2);
      TileEntity var4 = var1.s(var2);
      N = true;
      if (var0) {
         var1.a(var2, Blocks.am.Q().a(a, var3.b(a)), 3);
         var1.a(var2, Blocks.am.Q().a(a, var3.b(a)), 3);
      } else {
         var1.a(var2, Blocks.al.Q().a(a, var3.b(a)), 3);
         var1.a(var2, Blocks.al.Q().a(a, var3.b(a)), 3);
      }

      N = false;
      if (var4 != null) {
         var4.D();
         var1.a(var2, var4);
      }
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityFurnace();
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(a, var8.aP().d());
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.a(var2, var3.a(a, var4.aP().d()), 2);
      if (var5.s()) {
         TileEntity var6 = var1.s(var2);
         if (var6 instanceof TileEntityFurnace) {
            ((TileEntityFurnace)var6).a(var5.q());
         }
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      if (!N) {
         TileEntity var4 = var1.s(var2);
         if (var4 instanceof TileEntityFurnace) {
            InventoryHelper.a(var1, var2, (TileEntityFurnace)var4);
            var1.e(var2, this);
         }
      }

      super.b(var1, var2, var3);
   }

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public int l(World var1, BlockPos var2) {
      return Container.a(var1.s(var2));
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Item.a(Blocks.al);
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public IBlockState b(IBlockState var1) {
      return this.Q().a(a, EnumFacing.d);
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
