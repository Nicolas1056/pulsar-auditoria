package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class BlockEnderChest extends BlockContainer {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);

   protected BlockEnderChest() {
      super(Material.e);
      this.j(this.M.b().a(a, EnumFacing.c));
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
   public Item a(IBlockState var1, Random var2, int var3) {
      return Item.a(Blocks.Z);
   }

   @Override
   public int a(Random var1) {
      return 8;
   }

   @Override
   protected boolean I() {
      return true;
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(a, var8.aP().d());
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      var1.a(var2, var3.a(a, var4.aP().d()), 2);
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      InventoryEnderChest var9 = var4.co();
      TileEntity var10 = var1.s(var2);
      if (var9 == null || !(var10 instanceof TileEntityEnderChest)) {
         return true;
      } else if (var1.p(var2.a()).c().v()) {
         return true;
      } else if (var1.D) {
         return true;
      } else {
         var9.a((TileEntityEnderChest)var10);
         var4.a(var9);
         var4.b(StatList.V);
         return true;
      }
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntityEnderChest();
   }

   @Override
   public void c(World var1, BlockPos var2, IBlockState var3, Random var4) {
      for (int var5 = 0; var5 < 3; var5++) {
         int var6 = var4.nextInt(2) * 2 - 1;
         int var7 = var4.nextInt(2) * 2 - 1;
         double var8 = var2.n() + 0.5 + 0.25 * var6;
         double var10 = var2.o() + var4.nextFloat();
         double var12 = var2.p() + 0.5 + 0.25 * var7;
         double var14 = var4.nextFloat() * var6;
         double var16 = (var4.nextFloat() - 0.5) * 0.125;
         double var18 = var4.nextFloat() * var7;
         var1.a(EnumParticleTypes.y, var8, var10, var12, var14, var16, var18);
      }
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
