package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSign extends BlockContainer {
   protected BlockSign() {
      super(Material.d);
      float var1 = 0.25F;
      float var2 = 1.0F;
      this.a(0.5F - var1, 0.0F, 0.5F - var1, 0.5F + var1, var2, 0.5F + var1);
   }

   @Override
   public AxisAlignedBB a(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   @Override
   public AxisAlignedBB b(World var1, BlockPos var2) {
      this.a((IBlockAccess)var1, var2);
      return super.b(var1, var2);
   }

   @Override
   public boolean d() {
      return false;
   }

   @Override
   public boolean b(IBlockAccess var1, BlockPos var2) {
      return true;
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public boolean g() {
      return true;
   }

   @Override
   public TileEntity a(World var1, int var2) {
      return new TileEntitySign();
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return Items.ap;
   }

   @Override
   public Item c(World var1, BlockPos var2) {
      return Items.ap;
   }

   @Override
   public boolean a(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (var1.D) {
         return true;
      } else {
         TileEntity var9 = var1.s(var2);
         return var9 instanceof TileEntitySign ? ((TileEntitySign)var9).b(var4) : false;
      }
   }

   @Override
   public boolean d(World var1, BlockPos var2) {
      return !this.e(var1, var2) && super.d(var1, var2);
   }
}
