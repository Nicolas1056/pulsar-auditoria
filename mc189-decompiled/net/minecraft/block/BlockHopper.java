package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockHopper extends BlockContainer {
   public static final PropertyDirection a = PropertyDirection.a("facing", new Predicate<EnumFacing>() {
      public boolean a(EnumFacing var1) {
         return var1 != EnumFacing.b;
      }
   });
   public static final PropertyBool b = PropertyBool.a("enabled");

   public BlockHopper() {
      super(Material.f, MapColor.m);
      this.j(this.M.b().a(a, EnumFacing.a).a(b, true));
      this.a(CreativeTabs.d);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void a(IBlockAccess var1, BlockPos var2) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      float var7 = 0.125F;
      this.a(0.0F, 0.0F, 0.0F, var7, 1.0F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, var7);
      super.a(var1, var2, var3, var4, var5, var6);
      this.a(1.0F - var7, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      this.a(0.0F, 0.0F, 1.0F - var7, 1.0F, 1.0F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      EnumFacing var9 = var3.d();
      if (var9 == EnumFacing.b) {
         var9 = EnumFacing.a;
      }

      return this.Q().a(a, var9).a(b, true);
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityHopper();
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      super.a(var1, var2, var3, var4, var5);
      if (var5.s()) {
         TileEntity var6 = var1.s(var2);
         if (var6 instanceof TileEntityHopper) {
            ((TileEntityHopper)var6).a(var5.q());
         }
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3) {
      this.e(var1, var2, var3);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         TileEntity var9 = var1.s(var2);
         if (var9 instanceof TileEntityHopper) {
            var4.a((IInventory)((TileEntityHopper)var9));
            var4.b(StatList.P);
         }

         return true;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.e(var1, var2, var3);
   }

   private void e(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = !var1.z(var2);
      if (var4 != var3.b(b)) {
         var1.a(var2, var3.a(b, var4), 4);
      }
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.s(var2);
      if (var4 instanceof TileEntityHopper) {
         InventoryHelper.a(var1, var2, (TileEntityHopper)var4);
         var1.e(var2, this);
      }

      super.b(var1, var2, var3);
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean a(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   public static EnumFacing b(int var0) {
      return EnumFacing.a(var0 & 7);
   }

   public static boolean f(int var0) {
      return (var0 & 8) != 8;
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
   public EnumWorldBlockLayer m() {
      return EnumWorldBlockLayer.b;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(a, b(var1)).a(b, f(var1));
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).a();
      if (!var1.b(b)) {
         var2 |= 8;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }
}
