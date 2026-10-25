package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockBrewingStand extends BlockContainer {
   public static final PropertyBool[] a = new PropertyBool[]{PropertyBool.a("has_bottle_0"), PropertyBool.a("has_bottle_1"), PropertyBool.a("has_bottle_2")};

   public BlockBrewingStand() {
      super(Material.f);
      this.j(this.M.b().a(a[0], false).a(a[1], false).a(a[2], false));
   }

   @Override
   public String f() {
      return StatCollector.a("item.brewingStand.name");
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public int b() {
      return 3;
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityBrewingStand();
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      this.a(0.4375F, 0.0F, 0.4375F, 0.5625F, 0.875F, 0.5625F);
      super.a(var1, var2, var3, var4, var5, var6);
      this.j();
      super.a(var1, var2, var3, var4, var5, var6);
   }

   @Override
   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         TileEntity var9 = var1.s(var2);
         if (var9 instanceof TileEntityBrewingStand) {
            var4.a((IInventory)((TileEntityBrewingStand)var9));
            var4.b(StatList.M);
         }

         return true;
      }
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      if (var5.s()) {
         TileEntity var6 = var1.s(var2);
         if (var6 instanceof TileEntityBrewingStand) {
            ((TileEntityBrewingStand)var6).a(var5.q());
         }
      }
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      double var5 = var2.n() + 0.4F + var4.nextFloat() * 0.2F;
      double var7 = var2.o() + 0.7F + var4.nextFloat() * 0.3F;
      double var9 = var2.p() + 0.4F + var4.nextFloat() * 0.2F;
      var1.a(EnumParticleTypes.l, var5, var7, var9, 0.0, 0.0, 0.0);
   }

   @Override
   public void b(World var1, BlockPos var2, IBlockState var3) {
      TileEntity var4 = var1.s(var2);
      if (var4 instanceof TileEntityBrewingStand) {
         InventoryHelper.a(var1, var2, (TileEntityBrewingStand)var4);
      }

      super.b(var1, var2, var3);
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.bF;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.bF;
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
      return EnumWorldBlockLayer.c;
   }

   @Override
   public IBlockState a(int var1) {
      IBlockState var2 = this.Q();

      for (int var3 = 0; var3 < 3; var3++) {
         var2 = var2.a(a[var3], (var1 & 1 << var3) > 0);
      }

      return var2;
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;

      for (int var3 = 0; var3 < 3; var3++) {
         if (var1.b(a[var3])) {
            var2 |= 1 << var3;
         }
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a[0], a[1], a[2]);
   }
}
