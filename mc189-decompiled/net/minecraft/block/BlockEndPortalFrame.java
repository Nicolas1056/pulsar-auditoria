package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockEndPortalFrame extends Block {
   public static final PropertyDirection a = PropertyDirection.a("facing", EnumFacing.Plane.a);
   public static final PropertyBool b = PropertyBool.a("eye");

   public BlockEndPortalFrame() {
      super(Material.e, MapColor.C);
      this.j(this.M.b().a(a, EnumFacing.c).a(b, false));
   }

   @Override
   public boolean c() {
      return false;
   }

   @Override
   public void j() {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
   }

   @Override
   public void a(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List<AxisAlignedBB> var5, Entity var6) {
      this.a(0.0F, 0.0F, 0.0F, 1.0F, 0.8125F, 1.0F);
      super.a(var1, var2, var3, var4, var5, var6);
      if (var1.p(var2).b(b)) {
         this.a(0.3125F, 0.8125F, 0.3125F, 0.6875F, 1.0F, 0.6875F);
         super.a(var1, var2, var3, var4, var5, var6);
      }

      this.j();
   }

   @Override
   public Item a(IBlockState var1, Random var2, int var3) {
      return null;
   }

   @Override
   public IBlockState a(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return this.Q().a(a, var8.aP().d()).a(b, false);
   }

   @Override
   public boolean O() {
      return true;
   }

   @Override
   public int l(World var1, BlockPos var2) {
      return var1.p(var2).b(b) ? 15 : 0;
   }

   @Override
   public IBlockState a(int var1) {
      return this.Q().a(b, (var1 & 4) != 0).a(a, EnumFacing.b(var1 & 3));
   }

   @Override
   public int c(IBlockState var1) {
      int var2 = 0;
      var2 |= var1.b(a).b();
      if (var1.b(b)) {
         var2 |= 4;
      }

      return var2;
   }

   @Override
   protected BlockState e() {
      return new BlockState(this, a, b);
   }
}
